package br.ufpa.lprad.predict.previsao.taxadecrescimento;

import br.ufpa.lprad.predict.previsao.bean.PrevisaoBean;
import br.ufpa.lprad.predict.propriedades.PredictPropriedades;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 *
 * @author Microsoft
 */
public class PrevisaoTaxadeCrescimento {

    private Vector<String>[] vetorHistorico;
    public static Vector<Vector<String>> dadosHistoricos;
    public static Vector<String> anos;
    public static Vector<String> periodoPrevisto;
    public static Vector<String> dadoPrevisto;
    public static Vector<String> dadosHistoricosPrevisao;
    private Vector<Double> logs;
    private String[] anosSelecionados;
    public static String[][] relatorioSimples;
    public static String[][] relatorioCompleto;
    private String naoExisteMsg = "não aferido!";                               //mensagem de que o valor não existe na base de dados lida
    private String InexistenteMsg = "inexistente";                              //mensagem de erro erro inexistente
    private String nadaConstaMsg = "-";                                         //mensagem de erro erro inexistente
    private static NumberFormat nf;
    private static Locale locale;
    private static DecimalFormat df;
    private int anoInicial;
    private int mesInicial;
    private int anoFinal;
    private int mesFinal;
    private String tipoConsumo;
    private String nomeConsumo;
    public static String erroGeral;
    private Vector<String> erros;

    public PrevisaoTaxadeCrescimento() {

        /*
         *  Instancia as estruturas de formato
         */
        df = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
        df.applyPattern("#.##");
        locale = new Locale("pt", "BR");
        Locale.setDefault(locale);
        nf = NumberFormat.getNumberInstance(locale);


        /*
         * Inicializa dados de leitura
         */

        anoInicial = PrevisaoBean.getAnoIni();
        anoFinal = PrevisaoBean.getAnoFim();
        mesInicial = formataData(PrevisaoBean.getMesIni());
        mesFinal = formataData(PrevisaoBean.getMesFim());
        anosSelecionados = filtraAnos(PrevisaoBean.getAnosSelecionados(), anoInicial);
        nomeConsumo = PrevisaoBean.getNomeConsumo();
        tipoConsumo = PrevisaoBean.getTipoConsumo();
        logs = new Vector<Double>();
        dadosHistoricos = new Vector<Vector<String>>();
        anos = new Vector<String>();
        periodoPrevisto = new Vector<String>();
        dadoPrevisto = new Vector<String>();
        dadosHistoricosPrevisao = new Vector<String>();
        /*
         * Faz Leitura
         */
        leitura();
        //leituraTESTE();
        /*
         * faz previsao
         */
        fazPrevisao();
    }

    /*
     * metodo que seleciona os anos que servirão de base para a previsao
     * percorre todos os anos até o ano inicial escolhido para previsao e os armazena em um vetor de Strings
     */
    private String[] filtraAnos(String[] anosSelecionados, int anoInicial) {

        String[] anosValidos;
        Vector<String> anos = new Vector<String>();
        for (int i = 0; i < anosSelecionados.length; i++) {
            // se o ano for menor que o ano inicial para a previsao adiciona no vetor
            if (Integer.valueOf(anosSelecionados[i]).intValue() < anoInicial) {
                anos.add(anosSelecionados[i]);
            }
        }
        anosValidos = new String[anos.size()];

        for (int i = 0; i < anos.size(); i++) {
            anosValidos[i] = anos.get(i);
        }
        // retorna vetor de anos válidos
        return anosValidos;

    }

    /*
     *  podendo-se escolher mais de um ano para a previsao,
     *  esse método conta  o numero de meses que farão parte da privisão
     */
    public int contaMesesPrevisao() {
        int result;

        int mesIni = Integer.valueOf(formataData(PrevisaoBean.getMesIni()));
        int mesFim = Integer.valueOf(formataData(PrevisaoBean.getMesFim()));
        int anoIni = PrevisaoBean.getAnoIni();
        int anoFim = PrevisaoBean.getAnoFim();

        result = Math.abs(mesFim - mesIni) + (anoFim - anoIni) * 12;

        return result + 1;

    }

    private double getCoefA(double[] Y, double coefB, double[] T) {
        double coefA = 0;
        try {

            double mediaY = media(Y);
            double mediaT = media(T);

            coefA = mediaY - coefB * mediaT;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return coefA;
    }

    private double getCoefB(double[] Y, double[] T, double[] YT, double[] T2) {
        double coefB = 0;

        try {

            double somaY = soma(Y);
            double somaT = soma(T);
            double somaYT = soma(YT);
            double somaT2 = soma(T2);
            double n = T.length;

            coefB = (n * somaYT - somaY * somaT) / (n * somaT2 - Math.pow(somaT, 2));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return coefB;
    }

    /*
     * metodo que procura pelo consumol escolhido
     * retorna a coluna de celulas que contem os dados do consumo
     */
    private Cell[] getConsumo(Sheet aba, String nomeConsumo) {

        Cell[] consumo = null;
        try {

            Cell[] tiposDeConsumo = aba.getRow(1);

            for (int i = 0; i < tiposDeConsumo.length; i++) {
                if (tiposDeConsumo[i].getContents().equalsIgnoreCase(nomeConsumo)) {
                    consumo = aba.getColumn(i);
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return consumo;
    }

    private double[] getT(Vector<Vector<String>> vetorHistorico) {
        double[] retorno = new double[vetorHistorico.size()];
        try {

            for (int i = 0; i < vetorHistorico.size(); i++) {
                retorno[i] = i;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }

    private double[] getT2(double[] T) {
        double[] retorno = new double[T.length];

        try {

            for (int i = 0; i < retorno.length; i++) {
                retorno[i] = Math.pow(T[i], 2);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }

    private double[] getYT(double[] Y, double[] T) {
        double[] retorno = new double[Y.length];
        try {

            for (int i = 0; i < retorno.length; i++) {
                retorno[i] = Y[i] * T[i];
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }

    public void fazPrevisao() {
        double[] Y;
        double[] T;
        double[] YT;
        double[] T2;
        double[] Ycoef;
        double[] erro;
        double[] erro2;
        double[] erroMedido;
        double coefA;
        double coefB;
        double taxa;
        double previsto;
        int loop = contaMesesPrevisao();
        Vector<String> vetorHist = new Vector<String>();


        // cria vetor que contera os anos para a previsao
        int[] anosAux = new int[(anoFinal - anoInicial) + 1];
        // inclui ano inicial no primeiro indice
        anosAux[0] = anoInicial;
        // loop que povoa o vetor com os anos para a previsao
        for (int i = 1; i < anosAux.length; i++) {
            anosAux[i] = anoInicial + i;
        }
        //adiciona o ano final no ultimo indice
        anosAux[anosAux.length - 1] = anoFinal;

        /*
         * variaveis auxiliares
         */
        int auxFinal = 12;
        int auxInicial = mesInicial;

        // percorre o vetor dos anos para a previsao
        for (int j = 0; j < anosAux.length; j++) {
            // se tiver mais de 1 ano o mes inicial passa a ser janeiro
            if (j > 0) {
                auxInicial = 1;
            }
            // se for o ultimo ano, o mes final passa a ser o escolhido pelo usuário e não mais 12 como foi definido no inicio
            if (j == anosAux.length - 1) {
                auxFinal = mesFinal;
            }
            // vai do mes inicial até o final
            for (int i = auxInicial - 1; i <= auxFinal - 1; i++) {
                //define mes que esta sendo estudado
                int mes = i;

                Y = log(dadosHistoricos, mes);
                T = getT(dadosHistoricos);
                YT = getYT(Y, T);
                T2 = getT2(T);
                coefB = getCoefB(Y, T, YT, T2);
                coefA = getCoefA(Y, coefB, T);
                taxa = Math.exp(coefB);

                previsto = taxa * Double.valueOf(dadosHistoricos.get(dadosHistoricos.size() - 1).get(mes).replace(",", "."));
//                previsto = taxa * BigDecimalUtility.setFormattedCurrency(dadosHistoricos.get(dadosHistoricos.size() - 1).get(mes)).doubleValue();

                vetorHist.add(String.valueOf(previsto));

                if (vetorHist.size() == 12) {
                    dadosHistoricos.add(vetorHist);
                    vetorHist = new Vector<String>();
                }

                dadoPrevisto.add(String.valueOf(previsto));
                periodoPrevisto.add(String.valueOf((i + 1) + "/" + anosAux[j]));
            }
        }

        calculaErro(periodoPrevisto, dadosHistoricosPrevisao, dadoPrevisto);
        calculaErroMedioPercentual(dadosHistoricosPrevisao, dadoPrevisto);
        gerarRelatorioSimples();
        gerarRelatorioCompleto();
    }

    public void calculaErro(Vector<String> periodo, Vector<String> historico, Vector<String> previsto) {

        // vetor que conterá todos os erros
        erros = new Vector<String>();
        Vector<Double> erroAnual = new Vector<Double>();

        double erro;
        for (int i = 0; i < periodo.size(); i++) {
            try {
                if (!historico.get(i).equalsIgnoreCase(naoExisteMsg)) {
                    double hist = Double.valueOf(historico.get(i).replace(",", "."));
                    double prev = Double.valueOf(previsto.get(i).replace(",", "."));
                    erro = (hist - prev) / prev;
                    erros.add(String.valueOf(erro));
                } else {
                    erros.add(InexistenteMsg);
                }
            } catch (Exception e) {
                erros.add(InexistenteMsg);
            }
        }


    }

    public void calculaErroMedioPercentual(Vector<String> historico, Vector<String> previsto) {


        double hist = 0, prev = 0;
        int cont = 0;
        for (String d : historico) {
            try {
                hist += Double.valueOf(d.replace(",", "."));//nf.parse(d).doubleValue();
                cont++;
            } catch (Exception e) {
                hist += 0;
            }
        }

        for (int i = 0; i < cont; i++) {
            try {
                prev += Double.valueOf(previsto.get(i).replace(",", "."));//nf.parse(previsto.get(i)).doubleValue();
            } catch (Exception e) {
                prev += 0;
            }
        }

        if (hist != 0 && prev != 0) {
            double result = (Math.abs(hist - prev) / prev) * 100;
            erroGeral = df.format(result);
        } else {
            erroGeral = InexistenteMsg;
        }

    }

    public static String getErroGeral() {
        return erroGeral;
    }

    public Vector<String> getErros() {
        return erros;
    }

    public Vector<String> getPeriodoPrevisto() {
        return periodoPrevisto;
    }

    public Vector<String>[] getVetorHistorico() {
        return vetorHistorico;
    }

    public String calcErro(String real, String previsto) {
        double r = Double.valueOf(real.replace(".", "").replace(",", ".")).doubleValue();
        double p = Double.valueOf(previsto.replace(".", "").replace(",", ".")).doubleValue();
        double media;
        media = ((r - p) / p) * 100;
        String result = nf.format(media);
        if (media == -100) {
            result = InexistenteMsg;
        }
        return result;
    }

    private String somaP(Vector<String> totalPrevisto) {

        double soma = 0;
        for (String s : totalPrevisto) {
            try {
                soma += Double.valueOf(s.replace(".", "").replace(",", ".")).doubleValue();
            } catch (Exception e) {
                e.printStackTrace();
                soma += 0;
            }
        }

        return String.valueOf(soma);

    }

    private String somaH(Vector<Double> totalHistorico) {

        double soma = 0;
        for (Double d : totalHistorico) {
            try {
                soma += d.doubleValue();
            } catch (Exception e) {
                e.printStackTrace();
                soma += 0;
            }
        }
        return String.valueOf(soma);
    }

    private void gerarRelatorioSimples() {

        relatorioSimples = new String[dadoPrevisto.size() + ((int) dadoPrevisto.size() / 12)][4];
        Vector<Double> totalHistorico = new Vector<Double>();
        Vector<String> totalPrevisto = new Vector<String>();

        int index = 0;
        int index_ = 0;

        for (int i = 0; i < relatorioSimples.length; i++) {
            try {
                if (i > 0) {
                    if (periodoPrevisto.get(index - 1).split("/")[0].equalsIgnoreCase("12")) {
                        relatorioSimples[index_][0] = "TOTAL";
                        relatorioSimples[index_][1] = nf.format(Double.valueOf(somaH(totalHistorico)));
                        relatorioSimples[index_][2] = nf.format(Double.valueOf(somaP(totalPrevisto)));
                        relatorioSimples[index_][3] = calcErro(relatorioSimples[index_][1], relatorioSimples[index_][2]);
                        totalHistorico.clear();
                        totalPrevisto.clear();
                        index_++;
                    }
                }
                // PERIDO
                relatorioSimples[index_][0] = periodoPrevisto.get(index);

                //DADOS HISTORICOS
                try {
                    if (!dadosHistoricosPrevisao.get(index).equalsIgnoreCase(naoExisteMsg)) {

//                    relatorioSimples[i][1] = BigDecimalUtility.setFormattedCurrency(dadosHistoricosPrevisao.get(i)).toString();
                        relatorioSimples[index_][1] = nf.format(Double.valueOf(dadosHistoricosPrevisao.get(index).replace(",", ".")));
                        totalHistorico.add(Double.valueOf(dadosHistoricosPrevisao.get(index).replace(",", ".")));
                    } else {
                        relatorioSimples[index_][1] = dadosHistoricosPrevisao.get(index);

                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    relatorioSimples[index_][1] = naoExisteMsg;
                }

                //DADOS PREVISTOS
                relatorioSimples[index_][2] = nf.format(Double.valueOf(dadoPrevisto.get(index).replace(",", ".")));
                totalPrevisto.add(relatorioSimples[index_][2]);
                if (!erros.get(i).equalsIgnoreCase(InexistenteMsg)) {

                    relatorioSimples[index_][3] = df.format(Double.valueOf(erros.get(index).replace(",", ".")) * 100);
                } else {
                    relatorioSimples[index_][3] = erros.get(index);
                }
                index_++;
                index++;
            } catch (Exception e) {
            }
        }

    }

    private void gerarRelatorioCompleto() {

        DecimalFormat df_ = new DecimalFormat("#,###.00");

        relatorioCompleto = new String[dadoPrevisto.size() + ((int) dadoPrevisto.size() / 12)][6];
        Vector<Double> totalHistorico = new Vector<Double>();
        Vector<String> totalPrevisto = new Vector<String>();
        Vector<String> totalVariacao = new Vector<String>();
        int index = 0;
        int index_ = 0;

        for (int i = 0; i < relatorioCompleto.length; i++) {
            try {
                if (i > 0) {
                    if (periodoPrevisto.get(index - 1).split("/")[0].equalsIgnoreCase("12")) {
                        relatorioCompleto[index_][0] = "TOTAL";
                        relatorioCompleto[index_][1] = nf.format(Double.valueOf(somaH(totalHistorico)));
                        relatorioCompleto[index_][2] = nf.format(Double.valueOf(somaP(totalPrevisto)));
                        relatorioCompleto[index_][3] = calcErro(relatorioSimples[index_][1], relatorioSimples[index_][2]);
                        relatorioCompleto[index_][4] = nf.format(Double.valueOf(somaP(totalVariacao)));
                        totalHistorico.clear();
                        totalPrevisto.clear();
                        totalVariacao.clear();
                        index_++;
                    }
                }
                // PERIDO
                relatorioCompleto[index_][0] = periodoPrevisto.get(index);

                //DADOS HISTORICOS
                try {
                    if (!dadosHistoricosPrevisao.get(index).equalsIgnoreCase(naoExisteMsg)) {
                        relatorioCompleto[index_][1] = nf.format(Double.valueOf(dadosHistoricosPrevisao.get(index).replace(",", ".")));
//                        relatorioCompleto[i][1] = dadosHistoricosPrevisao.get(i);/*.replace(",", ".")))*/
                        totalHistorico.add(Double.valueOf(dadosHistoricosPrevisao.get(index).replace(",", ".")));
                    } else {
                        relatorioCompleto[index_][1] = dadosHistoricosPrevisao.get(index);

                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    relatorioCompleto[index_][1] = naoExisteMsg;
                }

                //DADOS PREVISTOS
                relatorioCompleto[index_][2] = nf.format(Double.valueOf(dadoPrevisto.get(index).replace(",", ".")));
                totalPrevisto.add(relatorioCompleto[index_][2]);
                //ERRO
                if (!erros.get(i).equalsIgnoreCase(InexistenteMsg)) {

                    relatorioCompleto[index_][3] = df.format(Double.valueOf(erros.get(index).replace(",", ".")) * 100);
                } else {
                    relatorioCompleto[index_][3] = erros.get(index);
                }


                // DADOS REAL/PREVISTO
                if (!relatorioCompleto[index_][1].equalsIgnoreCase(naoExisteMsg)) {
                    relatorioCompleto[index_][4] = relatorioCompleto[index_][1];
                } else {
                    relatorioCompleto[index_][4] = relatorioCompleto[index_][2];
                }

                // DADOS VARIACAO
                try {
                    relatorioCompleto[index_][5] = calculaVariacao(relatorioCompleto[index_][4], relatorioCompleto[index_ - 13][4]);
                    totalVariacao.add(relatorioCompleto[index_][4]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    totalVariacao.add(relatorioCompleto[index_][4]);
                    relatorioCompleto[index_][5] = InexistenteMsg;
                }
                index_++;
                index++;
            } catch (Exception e) {
            }
        }
        corrigeRelatorioCompleto();


    }

    public void corrigeRelatorioCompleto() {
        List<String> hist = new ArrayList<String>();
        List<String> per = new ArrayList<String>();

        try {
            // pega a planilha de dados
            Workbook planilha = Workbook.getWorkbook(new File(PredictPropriedades.getCaminhoPlanilhaConsumo()));
            //seleciona a aba desejada
            Sheet aba = planilha.getSheet(PrevisaoBean.getTipoConsumo());

            /*
             * pega o indice da coluna do consumo estudado
             */
            // vetor com a coluna dos dados do consumo estudado
            Cell[] consumo = getConsumo(aba, PrevisaoBean.getNomeConsumo());
            // vetor com os periodos da planilha
            Cell[] periodo = aba.getColumn(0);

            System.out.println("PERIODO PREVISTO INICIAL: " + periodoPrevisto.get(0));
            for (int i = 2; i < periodo.length; i++) {
                if (!periodo[i].getContents().equalsIgnoreCase(periodoPrevisto.get(0))) {
                    hist.add(nf.format(Double.valueOf(consumo[i].getContents().replace(".", "").replace(",", "."))));
                    per.add(periodo[i].getContents());
                } else {
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //
        String[][] relComp = new String[per.size() + relatorioCompleto.length][relatorioCompleto[0].length];
        //

        /**
         * Inclui os dados historicos
         */
        int i;
        for (i = 0; i < hist.size(); i++) {
            relComp[i][0] = per.get(i);
            relComp[i][1] = hist.get(i);
            relComp[i][2] = InexistenteMsg;
            relComp[i][3] = InexistenteMsg;
            relComp[i][4] = hist.get(i);
            relComp[i][5] = InexistenteMsg;
        }
        /**
         *
         */
        for (int j = 0; j < relatorioCompleto.length; j++) {
            relComp[j + i][0] = relatorioCompleto[j][0];
            relComp[j + i][1] = relatorioCompleto[j][1];
            relComp[j + i][2] = relatorioCompleto[j][2];
            relComp[j + i][3] = relatorioCompleto[j][3];
            relComp[j + i][4] = relatorioCompleto[j][4];
            relComp[j + i][5] = relatorioCompleto[j][5];
        }

        setRelatorioCompleto(relComp);
    }

    private String calculaVariacao(String v1, String v0) {
        df.applyPattern("##.##");
        try {
            double anterior = nf.parse(v0).doubleValue();//Double.parseDouble(v0.replace(",", "."));
            double post = nf.parse(v1).doubleValue();//Double.parseDouble(v1.replace(",", "."));

            double result;

            result =
                    ((post / anterior) - 1) * 100;

            return df.format(result);//.concat("%");
        } catch (ParseException e) {
            return null;
        }

    }

    public static String[][] getRelatorioCompleto() {
        return relatorioCompleto;
    }

    public static void setRelatorioCompleto(String[][] relatorioCompleto) {
        PrevisaoTaxadeCrescimento.relatorioCompleto = relatorioCompleto;
    }

    public static String[][] getRelatorioSimples() {
        return relatorioSimples;
    }

    public static void setRelatorioSimples(String[][] relatorioSimples) {
        PrevisaoTaxadeCrescimento.relatorioSimples = relatorioSimples;
    }

    public double soma(double[] valores) {
        double result = 0;

        for (int i = 0; i < valores.length; i++) {
            result += valores[i];
        }
        return result;
    }

    public double media(double[] valores) {
        double media = 0;

        for (int i = 0; i < valores.length; i++) {
            media += valores[i];
        }

        media /= valores.length;

        return media;

    }

    public int formataData(String mes) {
        int mesInt;

        if (mes.equalsIgnoreCase("Janeiro")) {
            mesInt = 1;
        } else if (mes.equalsIgnoreCase("Fevereiro")) {
            mesInt = 2;
        } else if (mes.equalsIgnoreCase("Março")) {
            mesInt = 3;
        } else if (mes.equalsIgnoreCase("Abril")) {
            mesInt = 4;
        } else if (mes.equalsIgnoreCase("Maio")) {
            mesInt = 5;
        } else if (mes.equalsIgnoreCase("Junho")) {
            mesInt = 6;
        } else if (mes.equalsIgnoreCase("Julho")) {
            mesInt = 7;
        } else if (mes.equalsIgnoreCase("Agosto")) {
            mesInt = 8;
        } else if (mes.equalsIgnoreCase("Setembro")) {
            mesInt = 9;
        } else if (mes.equalsIgnoreCase("Outubro")) {
            mesInt = 10;
        } else if (mes.equalsIgnoreCase("Novembro")) {
            mesInt = 11;
        } else {
            mesInt = 12;
        }
        return mesInt;
    }

    private void leituraTESTE() {

        /*
         * i = numero de anos,  j = numero de meses
         */

        vetorHistorico = new Vector[teste.length];

        for (int i = 0; i < vetorHistorico.length; i++) {
            vetorHistorico[i] = new Vector<String>();
        }

        for (int i = 0; i < teste.length; i++) {
            for (int j = 0; j < teste[0].length; j++) {
                vetorHistorico[i].add(teste[i][j]);
            }
            dadosHistoricos.add(vetorHistorico[i]);
        }

    }

    private double[] log(Vector<Vector<String>> vetorHistorico, int mes) {

        double[] retorno = new double[vetorHistorico.size()];

        try {

            for (int i = 0; i < retorno.length; i++) {
                retorno[i] = Math.log(Double.parseDouble(dadosHistoricos.get(i).get(mes).replace(",", ".")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }

    public static void main(String[] args) {
        new PrevisaoTaxadeCrescimento();
    }

    private void leitura() {
        try {
            // pega a planilha de dados
            Workbook planilha = Workbook.getWorkbook(new File(PredictPropriedades.getCaminhoPlanilhaConsumo()));
            //seleciona a aba desejada
            Sheet aba = planilha.getSheet(tipoConsumo);
            /*
             * pega o indice da coluna do consumo estudado
             */
            // vetor com a coluna dos dados do consumo estudado
            Cell[] consumo = getConsumo(aba, nomeConsumo);
            // vetor com os periodos da planilha
            Cell[] periodo = aba.getColumn(0);
            /*
             * cria o vector de anos cada indice do vetor é um ano..
             */
            vetorHistorico = new Vector[anosSelecionados.length];
            for (int i = 0; i < vetorHistorico.length; i++) {
                vetorHistorico[i] = new Vector<String>();
            }
            /*
             * faz a leitura da base de dados
             */
            // vetor que irá auxiliar para a leitura dos anos
            Vector<String> aux = new Vector<String>();

            int cont = 0;

            for (int i = 0; i < anosSelecionados.length; i++) {
                for (int j = 2; j < consumo.length; j++) {
                    if (!periodo[j].getContents().equalsIgnoreCase("TOTAL")) {
                        if (periodo[j].getContents().split("/")[1].equalsIgnoreCase(anosSelecionados[i])) {
                            vetorHistorico[i].add(consumo[j].getContents());
                        }
                    }
                }
                dadosHistoricos.add(vetorHistorico[i]);
            }

            lePeriodoHistorico(aba, consumo);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void lePeriodoHistorico(Sheet aba, Cell[] consumo) {

        String periodoIni = mesInicial + "/" + anoInicial;
        String periodoFim = mesFinal + "/" + anoFinal;


        Cell[] periodo = aba.getColumn(0);

        int indiceInicial = 0;
        int indiceFinal = 0;
        for (int i = 0; i < periodo.length; i++) {
            if (periodo[i].getContents().equalsIgnoreCase(periodoIni)) {
                indiceInicial = i;
            }
            if (periodo[i].getContents().equalsIgnoreCase(periodoFim)) {
                indiceFinal = i;
                break;
            }
        }

        System.out.println("periodoIni:  " + periodoIni + "   indiceInicial: " + indiceInicial);
        System.out.println("periodoFim:  " + periodoFim + "    indiceFinal: " + indiceFinal);

        if (indiceFinal == 0) {
            indiceFinal = consumo.length;
        }

        for (int i = indiceInicial; i <= indiceFinal; i++) {
            try {
                if (!periodo[i].getContents().equalsIgnoreCase("TOTAL")) {
                    try {
                        if (!consumo[i].getContents().isEmpty()) {

                            dadosHistoricosPrevisao.add(consumo[i].getContents());
                        } else {
                            dadosHistoricosPrevisao.add(naoExisteMsg);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        dadosHistoricosPrevisao.add(naoExisteMsg);
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }

    }
    private String[][] teste = {{"189858", "171522", "188118", "192085", "198861", "201826", "208663", "211939", "207879", "215017", "207246", "209512"},
        {"204044", "186531", "198345", "196143", "208521", "203257", "206194", "209262", "205964", "213854", "210686", "217616"},
        {"207477", "186454", "215978", "209741", "223671", "234136", "223398", "224317", "221851", "232751", "227516", "236938"},
        {"229248", "205539", "233006", "223961", "242017", "236473", "239046", "256228", "253446", "260834", "260707", "268813"},
        {"260565", "233008", "268683", "252660", "269979", "268532", "267908", "286937", "276902", "288503", "278166", "287744"},
        {"280285", "262635", "283775", "275847", "294717", "287312", "297339", "310686", "305785", "314590", "309936", "319590"},
        {"304269", "273504", "306410", "301935", "321548", "321856", "326474", "332665", "333824", "350498", "334893", "342902"},
        {"335237,8201", "313470,6874", "347096,36", "341712,397", "360418,677", "349718,955", "354483,254", "367981,637", "365716,123", "380631,835", "363673,05", "373077,4"},
        {"355342", "318371,3", "364686,7", "350101,687", "361399,35", "366833,269", "375853,939", "402236,256", "389178,996", "400516,442", "397454,389", "407247,196"},
        {"388266,348", "363445,3295", "391650,986", "377204,944", "404828,917", "396596,348", "392754,7377", "417842,387", "405118,535", "422306,724", "416762,162", "417217,291"},
        {"395508,503", "352375,591", "402953,739", "384967,19", "411211,367", "372165,672", "364844,395", "368773,233", "344829,833", "367753,609", "359727,053", "377129,326"},
        {"358709,499", "334927,347", "371577,003", "371848,093", "391209,963", "376031,612", "394349,1184", "411866,05", "407171,625", "423478,5128", "411088,455", "418351,346"},
        {"412964,5326", "361698,166", "397408,7867", "396835,728", "423493,073", "416184,0442", "429738,512", "438712,736", "436382,662", "454061,852", "435875,151", "445771,325"}
    };
}
