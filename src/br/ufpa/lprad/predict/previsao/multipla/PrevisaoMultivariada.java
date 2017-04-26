package br.ufpa.lprad.predict.previsao.multipla;

import Jama.Matrix;
import br.ufpa.lprad.predict.gui.window.previsao.backstage.BackPrevisaoGUI;
import br.ufpa.lprad.predict.previsao.bean.PrevisaoBean;
import br.ufpa.lprad.predict.propriedades.PredictPropriedades;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 *
 * @author Gabriel
 */
public class PrevisaoMultivariada {

    private Matrix matriz_A;
    private Matrix matriz_Y;
    private Matrix matriz_Yt;
    private Matrix matriz_X;
    private Matrix matrix_Xt;
    // matriz produto de X tranposto com X
    private Matrix matriz_XtxX;
    // matriz produto de X tranposto com Y
    private Matrix matriz_XtxY;
    // inversar da matriz produto de X tranposto com X
    private Matrix matriz_XtxX_1;
    private Matrix vetor_A;
    private Matrix vetor_At;
    private Matrix matriz_y;
    private Vector<String>[] vetorHistorico;
    public static Vector<Vector<String>> dadosHistoricos;
    public static Vector<String> anos;
    public static Vector<String> periodoPrevisto;
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
    private double[][] previsao;
    public static Vector<String> prev;

    public static String[][] getRelatorioSimples() {
        return relatorioSimples;
    }

    public static void setRelatorioSimples(String[][] relatorioSimples) {
        PrevisaoMultivariada.relatorioSimples = relatorioSimples;
    }

    public static String[][] getRelatorioCompleto() {
        return relatorioCompleto;
    }

    public static void setRelatorioCompleto(String[][] relatorioCompleto) {
        PrevisaoMultivariada.relatorioCompleto = relatorioCompleto;
    }
    private Vector<String> dadoPrevisto;

    public double soma(Vector<Double> valores) {
        double result = 0;

        for (Double d : valores) {
            result += d;
        }
        return result;
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

    public double media(Vector<Double> v) {
        double media = 0;
        for (int i = 0; i
                < v.size(); i++) {
            media += v.get(i);
        }

        media = media / v.size();

        return media;
    }

    private double[][] converteVectorMatriz(Vector<Vector<String>> dadosHistoricos) {
        double[][] retorno = new double[dadosHistoricos.get(0).size()][dadosHistoricos.size()];

        for (int i = 0; i < retorno.length; i++) {
            for (int j = 0; j < retorno[0].length; j++) {
                try {
                    retorno[i][j] = Double.valueOf(dadosHistoricos.get(j).get(i).replace(",", ".")).doubleValue();
                } catch (NumberFormatException e) {
                    retorno[i][j] = 0;
                }
            }
        }
        return retorno;
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

    private double[][] incluiExogenas(double[][] historicoDefasado) {

        String[][] matrizExogena = BackPrevisaoGUI.getMatrizExogena();

        if (matrizExogena == null) {
            System.out.println("MATRIZ É NULL");
        }

        double[][] result = new double[historicoDefasado.length][historicoDefasado[0].length + matrizExogena[0].length];

        for (int i = 0; i < historicoDefasado.length; i++) {
            for (int j = 0; j < historicoDefasado[0].length; j++) {

                result[i][j] = historicoDefasado[i][j];

            }
        }
        p:
        for (int i = 0; i < historicoDefasado.length; i++) {
            for (int j = 0; j < matrizExogena[0].length; j++) {
                try {
                    result[i][j + historicoDefasado[0].length] = Double.valueOf(matrizExogena[i][j].replace(",", "."));
                } catch (Exception e) {
                    break p;
                }
            }
        }
//        System.out.println("HITORICO SEM EXOGENA");
//        for (int i = 0; i < historicoDefasado.length; i++) {
//            for (int j = 0; j < historicoDefasado[i].length; j++) {
//                System.out.print(historicoDefasado[i][j] + "\t");
//            }
//            System.out.println("");
//        }
//
//        System.out.println("`RESULTADO FINAL");
//        for (int i = 0; i < result.length; i++) {
//            for (int j = 0; j < result[i].length; j++) {
//                System.out.print(result[i][j] + "\t");
//            }
//            System.out.println("");
//        }
        return result;
    }

    private double[][] planilha() {
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

            return converteVectorMatriz(dadosHistoricos);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int contaPeriodoPrevisao() {

        int periodo = 0;

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
                periodoPrevisto.add(String.valueOf((i + 1) + "/" + anosAux[j]));
                periodo++;
            }
        }

        return periodo;

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

//        System.out.println("periodoIni:  " + periodoIni + "   indiceInicial: " + indiceInicial);
//        System.out.println("periodoFim:  " + periodoFim + "    indiceFinal: " + indiceFinal);

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
                dadosHistoricosPrevisao.add(naoExisteMsg);

            }
        }
    }

    public double[][] defasagem(double[][] matrizEntrada) {
        // matriz que conterá os dados historicos [anos*12][12(defasagens)]
        double[][] matriz = new double[matrizEntrada.length * matrizEntrada[0].length][13];//new double[324][13];
        double def[];
        int indice = 0;
        int iAux = 0;

        // loop temporário  -> cria dados
        for (int j = 0; j
                < matrizEntrada[0].length; j++) {
            for (int i = 0; i
                    < matrizEntrada.length; i++) {
                matriz[iAux][0] = matrizEntrada[i][j];
                iAux++;
            }
        }

        /*
         * Loop que fraz a defasagem dos valores
         */

        for (int i = 0; i
                < matriz.length; i++) {

            def = new double[i + 1];
            for (int k = i; k >= 0; k--) {
                def[i - k] = matriz[k][0];
            }

            for (int j = 1; j
                    < matriz[0].length; j++) {
                if (j < def.length) {
                    matriz[i][j] = def[j];
                } else {
                    matriz[i][j] = 0;
                }
            }
        }

        /*
         * Loop que procura o indice de inicio válido dos dados
         */
        for (int i = 0; i < matriz.length; i++) {
            if (matriz[i][matriz[0].length - 1] != 0) {
                indice = i;
                break;
            }
        }

        /*
         * Matriz de dados defasados - dados finais
         */
        double[][] dados = new double[matriz.length - indice][13];

        for (int i = 0; i
                < dados.length; i++) {

            for (int j = 0; j
                    < dados[0].length; j++) {
                dados[i][j] = matriz[i + indice][j];
            }
        }
        return dados;

    }

    public Matrix gerarMatriz_Y(double[][] matriz) {

        double[] m = new double[matriz.length];

        for (int i = 0; i < m.length; i++) {
            m[i] = matriz[i][0];
        }

        return new Matrix(m, m.length);

    }

    public Matrix geraMatriz_X(double[][] matriz) {
        double[][] m = new double[matriz.length][13];

        for (int i = 0; i < m.length; i++) {
            for (int j = 1; j < m[0].length; j++) {
                m[i][j] = matriz[i][j];
            }

        }

        for (int i = 0; i < m.length; i++) {
            m[i][0] = 1;
        }

        return new Matrix(m);
    }

    public Matrix geraMatrix_y(
            double[][] matriz_Y, double media) {

        double[] y = new double[matriz_Y.length];

        for (int i = 0; i
                < y.length; i++) {
            y[i] = matriz_Y[i][0] - media;
        }

        return new Matrix(y, y.length);
    }

    public PrevisaoMultivariada() {


        /*
         *  Instancia as estruturas de formato
         */
        df = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
        df.applyPattern("#.##");
        locale = new Locale("pt", "BR");
        Locale.setDefault(locale);
        nf = NumberFormat.getNumberInstance(locale);

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

        double[][] historico = planilha();

        double[][] def = defasagem(historico);

        if (BackPrevisaoGUI.isExogena()) {
            def = incluiExogenas(def);
        }


        // Define matriz Y
        matriz_Y =
                gerarMatriz_Y(def);
        // imprime matriz Y

        // tranpoe a matriz Y
        matriz_Yt =
                matriz_Y.transpose();

        // define matriz X
        matriz_X =
                geraMatriz_X(def);
//        matriz_X.print(5, 4);

        matrix_Xt =
                matriz_X.transpose();


        matriz_y =
                geraMatrix_y(matriz_Y.getArray(), media(matriz_Y.getArray()));

//        System.out.println("Produto de (X'X)");
        matriz_XtxX =
                produto(matriz_X.transpose().getArray(), matriz_X.getArray());
//        matriz_XtxX.print(5, 2);
//

//        System.out.println("Produto de (X'Y)");
        matriz_XtxY =
                produto(matriz_X.transpose().getArray(), matriz_Y.getArray());
//        matriz_XtxY.print(5, 4);

//        System.out.println("Inversa do Produto da (X'X)");
        matriz_XtxX_1 =
                matriz_XtxX.inverse();
//        matriz_XtxX_1.print(5, 5);

//        System.out.println("Vetor A");
        vetor_A = produto(matriz_XtxX_1.getArray(), matriz_XtxY.getArray());
//        vetor_A.print(5, 10);

//        System.out.println("Vetor A'");
        vetor_At = vetor_A.transpose();
//        vetor_At.print(5, 4);

        double vetorH[] = geraVetor(historico);

        previsao = fazPrevisao(vetor_A, matriz_Y, vetorH);



//---------------------------------------------------------------
        calculaErro();
        calculaErroMedioPercentual(dadosHistoricosPrevisao, converteVetorPrevisto(previsao));
        geraRelatorioSimples();
        geraRelatorioCompleto();



    }

    private void calculaErro() {

        erros = new Vector<String>();

        for (int i = 0; i < previsao.length; i++) {
            try {
                double h = nf.parse(dadosHistoricosPrevisao.get(i)).doubleValue();
                double p = previsao[i][0];

                double err = ((h - p) / p) * 100;

                erros.add(df.format(err));

            } catch (Exception e) {
                erros.add(InexistenteMsg);
            }

        }
    }

    /*
     * Calcula o erro médio percentual
     * 
     */
    public void calculaErroMedioPercentual(Vector<String> historico, Vector<String> previsto) {


        double hist = 0, prev = 0;
        int cont = 0;
        for (String d : historico) {
            try {
                hist += Double.valueOf(d.replace(",", "."));
                cont++;
            } catch (Exception e) {
                hist += 0;
            }
        }

        for (int i = 0; i < cont; i++) {
            try {
                prev += Double.valueOf(previsto.get(i).replace(",", "."));
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

    public static void setErroGeral(String erroGeral) {
        PrevisaoMultivariada.erroGeral = erroGeral;
    }

    private void geraRelatorioSimples() {

        relatorioSimples = new String[previsao.length + ((int) previsao.length / 12)][4];
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
                relatorioSimples[index_][0] = periodoPrevisto.get(index);
                try {
                    relatorioSimples[index_][1] = nf.format(Double.valueOf(dadosHistoricosPrevisao.get(index).replace(",", ".")));//*/;
                    totalHistorico.add(Double.valueOf(dadosHistoricosPrevisao.get(index).replace(",", ".")));
                } catch (Exception e) {
                    relatorioSimples[index_][1] = naoExisteMsg;
                }
                relatorioSimples[index_][2] = nf.format(previsao[index][0]);
                totalPrevisto.add(relatorioSimples[index_][2]);
                relatorioSimples[index_][3] = erros.get(index);
                index_++;
                index++;
            } catch (Exception e) {
            }
        }
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

    public Vector<String> converteVetorPrevisto(double[][] previsto) {
        prev = new Vector<String>();

        for (int i = 0; i
                < previsto.length; i++) {
            prev.add(String.valueOf(previsto[i][0]));
        }

        return prev;
    }

    private void geraRelatorioCompleto() {

        relatorioCompleto = new String[previsao.length + ((int) previsao.length / 12)][6];
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
//                        relatorioCompleto[index_][5] = calcErro(relatorioSimples[index_][1], relatorioSimples[index_][2]);
                        totalHistorico.clear();
                        totalPrevisto.clear();
                        totalVariacao.clear();

                        index_++;
                    }
                }
                // PERIODO
                relatorioCompleto[index_][0] = periodoPrevisto.get(index);

                //DADOS HISTORICOS
                try {
                    relatorioCompleto[index_][1] = nf.format(Double.valueOf(dadosHistoricosPrevisao.get(index).replace(",", ".")));
                    totalHistorico.add(Double.valueOf(dadosHistoricosPrevisao.get(index).replace(",", ".")));
                } catch (Exception e) {
                    relatorioCompleto[index_][1] = naoExisteMsg;
                }
                //DADOS PREVISAO

                relatorioCompleto[index_][2] = nf.format(previsao[index][0]);
                totalPrevisto.add(relatorioCompleto[index_][2]);
                //ERRO
                relatorioCompleto[index_][3] = erros.get(index);

                // REAL / PREVISTO
                try {
                    if (!relatorioCompleto[index_][1].equalsIgnoreCase(naoExisteMsg)) {

                        relatorioCompleto[index_][4] = relatorioCompleto[index_][1];
                    } else {
                        relatorioCompleto[index_][4] = relatorioCompleto[index_][2];
                    }
                } catch (Exception e) {
                    relatorioCompleto[index_][4] = relatorioCompleto[index_][2];
                }

                //VARIACAO
                try {
                    //
                    relatorioCompleto[index_][5] = calculaVariacao(relatorioCompleto[index_][4], relatorioCompleto[(index_) - 13][4]);
                    totalVariacao.add(relatorioCompleto[index_][4]);
                    //
                } catch (ArrayIndexOutOfBoundsException e) {
//                    e.printStackTrace();
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

            for (int i = 2; i < periodo.length; i++) {
                if (!periodo[i].getContents().equalsIgnoreCase(periodoPrevisto.get(0))) {
                    hist.add(nf.format(Double.valueOf(consumo[i].getContents().replace(",", "."))));
                    per.add(periodo[i].getContents());
                } else {
                    break;
                }
            }

        } catch (Exception e) {
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

    public double[] geraVetor(double[][] d) {
        double result[] = new double[d.length * d[0].length];
        int aux = 0;

        for (int j = 0; j
                < d[0].length; j++) {
            for (int i = 0; i
                    < d.length; i++) {
                result[aux] = d[i][j];
                aux++;

            }


        }
        return result;
    }

    public Matrix produto(
            double a[][], double b[][]) {

        int row, column, i;
        double aux;
        double c[][] = new double[a.length][b[0].length];
        for (row = 0; row
                < c.length; row++) // multiplicação das matrizes
        {
            for (column = 0; column
                    < c[row].length; column++) {
                aux = 0;
                for (i = 0; i
                        < a[row].length; i++) {
                    try {
                        aux = aux + a[row][i] * b[i][column];
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                c[row][column] = aux;

            }

        }


        return new Matrix(c);

    }

    public double[][] fazPrevisao(Matrix A, Matrix Y, double[] historico) {
        double[][] mA = A.getArray();
        double[][] mY = Y.getArray();

        Vector<Double> v = new Vector<Double>();
        for (int i = 0; i
                < historico.length; i++) {
            v.add(historico[i]);
        }

        double[][] previsao = new double[contaPeriodoPrevisao()][1];

        for (int i = 0; i
                < previsao.length; i++) {
            previsao[i][0] += mA[0][0];
            for (int k = 1; k
                    < mA.length; k++) {
                previsao[i][0] += mA[k][0] * v.get(v.size() - 1 - (k - 1));
            }

            v.add(previsao[i][0]);
        }

//        for (int i = 0; i < previsao.length; i++) {
//            try {
//                System.out.println(i + "\t" + dadosHistoricosPrevisao.get(i) + "\t" + previsao[i][0]);
//            } catch (Exception e) {
//                System.out.println(i + "\t" + "-" + "\t" + previsao[i][0]);
//
//            }
//        }
        return previsao;
    }

    public double media(double valores[][]) {
        double resultado = 0;

        for (int i = 0; i
                < valores.length; i++) {
            resultado += valores[i][0];
        }

        resultado /= valores.length;

        return resultado;
    }

    public Matrix getMatriz_A() {
        return matriz_A;
    }

    public void setMatriz_A(Matrix matriz_A) {
        this.matriz_A = matriz_A;
    }

    public Matrix getMatriz_X() {
        return matriz_X;
    }

    public void setMatriz_X(Matrix matriz_X) {
        this.matriz_X = matriz_X;
    }

    public Matrix getMatriz_Y() {
        return matriz_Y;
    }

    public void setMatriz_Y(Matrix matriz_Y) {
        this.matriz_Y = matriz_Y;
    }

    public double[][] getPrevisao() {
        return previsao;
    }

    public void setPrevisao(double[][] previsao) {
        this.previsao = previsao;
    }

    public String[] converte(double[] vetor) {
        String[] result = new String[vetor.length];
        for (int i = 0; i
                < result.length; i++) {
            if (vetor[i] != 0) {
                result[i] = nf.format(vetor[i]);//String.valueOf(vetor[i]);
            } else {
                result[i] = naoExisteMsg;
            }

        }

        return result;
    }

    private String calculaVariacao(String v1, String v0) {

        df.applyPattern("##.##");
        String resultado;

        try {
            double anterior = Double.parseDouble(corrigeValores(v0.replace(".", "").replace(",", ".")));
            double post = Double.parseDouble(corrigeValores(v1.replace(".", "").replace(",", ".")));
            double result;

            result = ((post / anterior) - 1) * 100;

            resultado = df.format(result).concat("%");
        } catch (Exception e) {
            resultado = InexistenteMsg;
        }

        return resultado;

    }

    public String corrigeValores(
            String val) {
        boolean temVirgula = false;
        String result;

        for (int i = 0; i
                < val.length(); i++) {
            if (val.charAt(i) == ',') {
                temVirgula = true;
                break;

            }


        }

        if (temVirgula) {
            result = val.replace(".", "").replace(",", ".");
        } else {
            result = val;
        }

//        System.out.println("Val: "+val+"\tResult: "+result);
        return result;

    }
}
