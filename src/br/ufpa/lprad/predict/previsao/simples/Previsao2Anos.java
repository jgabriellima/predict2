package br.ufpa.lprad.predict.previsao.simples;

import br.ufpa.lprad.predict.previsao.taxadecrescimento.Previsao;
import br.ufpa.lprad.predict.exception.PredictException;
import br.ufpa.lprad.predict.gui.window.previsao.backstage.BackPrevisaoGUI;
import br.ufpa.lprad.predict.leitura.ControleLeitura;
import br.ufpa.lprad.predict.leitura.interfaces.InterfaceLeituraConsumo;
import br.ufpa.lprad.predict.previsao.bean.PrevisaoBean;
import br.ufpa.lprad.predict.propriedades.PredictPropriedades;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class Previsao2Anos {

    private static String[][] dadosRelatorio;              //valores mostrados no relatorio de previsao (tabela)  --> dadosRelatorio[meses][4]
    // private static String[][] dadosHistoricosAnosPrevS;    //valores historicos de consumo apenas dos anos que se dejesa prever-->dadosHistoricosD[meses][anoFim-anoIni]
    //  public static String[][] dadosHistoricos;               //matriz que armazena todos os dados historicos
    // public static String[] meses;            // tb num sei pq usei isso...
    public static String[] anos;                // esse mto menos
    public static String[][] matrizResultado;
    private static String[] dadosHistoricosS;              //valores historicos de consumo - String -->dadosHistoricosS[meses]
    private static String[] dadosPrevistosS;               //valores previstos de consumo - String
    private static String[] erroMensalS;                   //valores de erros mensais - String
    private static String[] erroAnualS;                    //valores medios anuais - String
    private static String[] periodoFomatado;               //valores MES/ANO do periodo total de previsao
    private static String[] anosSelecionados;              //anos selecionados para realizar a previsão
    private static String erroTotalS;                      //valor do erro medio total da predicao - String
    static String naoExisteMsg = "0";//não aferido!";             //mensagem de que o valor não existe na base de dados lida
    private String InexistenteMsg = "0";//inexistente";           //mensagem de erro erro inexistente
    private String nadaConstaMsg = "-";                      //mensagem de erro erro inexistente
    private String erroAnualMsg = "Erro médio anual";        //mensagem de erro erro inexistente
    private double[][] dadosHistoricosD;             //valores historicos de consumo -->dadosHistoricosD[meses][numero de anos da base]
    private double[][] dadosHistoricosAnosPrevD;     //valores historicos de consumo apenas dos anos que se dejesa prever-->dadosHistoricosD[meses][anoFim-anoIni]
    private double[][] dadosAnosPrevistos;           //valores historicos de consumo -->dadosAnosPrevistos[meses][numero de anos de previsao]
    private double[] dadosPrevistosD;                //valores previstos de consumo
    private double[] erroMensalD;                    //valores de erros mensais
    private double[] erroAnualD;                     //valores medios anuais
    private double erroTotalD;                       //valor do erro medio total da predicao
//    private int[] periodo;                           //valores referentes ao periodo da previsao --> periodo[mesInicial][mesFinal][anoInicial][anoFinal]
//    private int numAnos;                             //valor com o numero de anos presentes na base de dados
    private int anoIni;                              //valor com o ano inicial presente na tabela
    //  private int anoFim;                              //valor com o ano final presente na tabela
    private int anoAtual;                            //valor do ano corrente
//    private int mesAtual;
    private int anoIniPrevisao = 2005;                 //ano minimo para previsao
    //   private int dimensao;
    public static Vector[] vetor_historicos;
    public static Vector total_anos_historico;
    static NumberFormat nf;
    static Locale locale;
    int a;
    DecimalFormat df = null;
    private InterfaceLeituraConsumo ilc;

    public Previsao2Anos() {
        try {

                df = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());

            df.applyPattern("#.##");
            anos = BackPrevisaoGUI.getAnos(PrevisaoBean.getNomePlanilha());
            //define data de hj
            Date data = new Date();
            String formato = "dd/MM/yyyy";
            SimpleDateFormat formatter = new SimpleDateFormat(formato);
            this.anoAtual = (Integer.parseInt(formatter.format(data).substring(6)));
            //this.mes =
            // Set the default locale to custom locale
            locale = new Locale("pt", "BR");
            Locale.setDefault(locale);
            nf = NumberFormat.getNumberInstance(locale);
//            System.out.println("DIMENSAO: " + Previsao.getDimensao());
//            System.out.println("PREVISAO ANOS SELECIONADOS: " + PrevisaoBean.getAnosSelecionados().length);
            this.dadosPrevistosD = new double[Previsao.getDimensao()];
            this.dadosAnosPrevistos = new double[12][this.anoAtual - anoIniPrevisao + 3]; //-->"anoAtual - anoIniPrevisao + 3" = previsao do ano de 2005 ate 2 anos alem do ano atual
            Previsao2Anos.anosSelecionados = PrevisaoBean.getAnosSelecionados();
            Previsao2Anos.dadosPrevistosS = new String[Previsao.getDimensao()];
            Previsao2Anos.dadosHistoricosS = new String[Previsao.getDimensao()];
            this.dadosHistoricosD = new double[12][Previsao2Anos.anosSelecionados.length];
//        this.dadosHistoricos = new String[(12+1)][this.anosSelecionados.length + 2];
            this.erroMensalD = new double[Previsao.getDimensao()];
            Previsao2Anos.erroMensalS = new String[Previsao.getDimensao()];
            Previsao2Anos.periodoFomatado = new String[Previsao.getDimensao()];
            Previsao2Anos.total_anos_historico = new Vector();
        } catch (Exception ex) {
           ex.printStackTrace();
        }

    }

    public void leArq() {
        int ponto;
        String teste;
        String nomeConsumo = PrevisaoBean.getNomeConsumo();    //nome do consumo
        String nomePlanilha = PrevisaoBean.getNomePlanilha();  //nome da planilha

        try {


            Workbook workbook = Workbook.getWorkbook(new File(PredictPropriedades.getCaminhoPlanilhaConsumo()));//"C:/Arquivos de programas/Predict/arquivos/estadual/Dados_Consumo-Celpa.xls"));

            //procura a planilha a ser lida através de seu nome
            Sheet aba = workbook.getSheet(nomePlanilha);

            ilc = new ControleLeitura();



            // instancia o vetor_historico com todos anos do histórico
            String[] Anos_Todos = BackPrevisaoGUI.getAnos(nomePlanilha);
            vetor_historicos = new Vector[Anos_Todos.length];
            total_anos_historico = new Vector();
            for (int i = 0; i < vetor_historicos.length; i++) {
                vetor_historicos[i] = new Vector();
            }

            // --------------Grava todo o histórico -------------
            Cell[] periodo = aba.getColumn(0);

            // --- Vetor com os valores do consumo escolhido
            String valores[] = ilc.consumoEspecifico(nomePlanilha, nomeConsumo);

            int indice = 2;
            for (int i = 0; i < Anos_Todos.length; i++) {

                loopInterno:
                for (int j = indice; j < periodo.length; j++) {
                    if (!periodo[j].getContents().equalsIgnoreCase("TOTAL")) {
                        vetor_historicos[i].add(/*valores[j]);//*/valores[j]);//.getContents());
                        indice++;
                    } else {
                        total_anos_historico.add(valores[j]);//periodo[j].getContents());
                        indice++;
                        break loopInterno;
                    }

                }

            }

            // -------------------------------------------------------------
            // --------------Grava dados dos anos selecionados -------------

//            System.out.println("anosSelecionados.length: " + anosSelecionados.length);
//            System.out.println("ANOS SELECIONADOS:");
//            for(String c: anosSelecionados)
//                System.out.println(""+c);
//            System.out.println("periodo.length: " + periodo.length);
            int ind = 0;
            indice = 2;
            int k = indice;
            for (int i = 0; i < anosSelecionados.length; i++) {
//                  System.out.println("Ano: " + anosSelecionados[i]);
                for (k = 2; k < periodo.length; k++) {
                    // System.out.println("periodo[" + k + "].getContents(): " + periodo[k].getContents());

                    if (!periodo[k].getContents().equalsIgnoreCase("TOTAL")) {
                        if (periodo[k].getContents().split("/")[1].equalsIgnoreCase(anosSelecionados[i])) {
                            try {
                                // System.out.println("periodo[" + k + "].getContents(): "+periodo[k].getContents()+" ------------- valores[" + k + "]: " + valores[k]);

                                dadosHistoricosD[ind][i] = Double.valueOf(valores[k].replace(".", "").replace(",", ".")).doubleValue();

                                ind++;
                            } catch (Exception e) {
                               // e.printStackTrace();
                            }
                        }
                    }
                }
                //  System.out.println("ind: "+ind);
                ind = 0;

            }

             // -------------------------------------------------------------
            // --------------Grava dados dos anos selecionados para previsão -------------

            this.dadosHistoricosAnosPrevD = new double[12][Previsao.getNumAnos()];
             String[] anosPredicao;
                int anoIni = Previsao.getPerido()[2];         //ano inicial da previsao
//                System.out.println("anoIni = Previsao.getPerido()[2]: "+Previsao.getPerido()[2]);

                anosPredicao = new String[Previsao.getNumAnos()];
                this.dadosHistoricosAnosPrevD = new double[12][Previsao.getNumAnos()];
//                System.out.println("Previsao.getNumAnos(): "+Previsao.getNumAnos());

               for(int i=0;i<anosPredicao.length;i++){
                    anosPredicao[i] = String.valueOf(anoIni+i);
//                    System.out.println("anosPredicao["+i+"]: "+anosPredicao[i]);
               }

            ind = 0;
            indice = 2;
            k = indice;
            for (int i = 0; i < anosPredicao.length; i++) {
                 // System.out.println("Ano....: " + anosPredicao[i]);
                for (k = 2; k < periodo.length; k++) {
//                     System.out.println("periodo[" + k + "].getContents(): " + periodo[k].getContents());

                    if (!periodo[k].getContents().equalsIgnoreCase("TOTAL")) {
                        if (periodo[k].getContents().split("/")[1].equalsIgnoreCase(anosPredicao[i])) {
                            try {
                                // System.out.println("periodo[" + k + "].getContents(): "+periodo[k].getContents()+" ------------- valores[" + k + "]: " + valores[k]);
                               // System.out.println("valores["+k+"]: "+valores[k]+" Substituido por: "+valores[k].replace(".", "").replace(",", "."));
                                this.dadosHistoricosAnosPrevD[ind][i] = Double.valueOf(valores[k].replace(".", "").replace(",", ".")).doubleValue();

                                ind++;
                            } catch(NumberFormatException e){
                                this.dadosHistoricosAnosPrevD[ind][i] = 0;//Double.valueOf(valores[k].replace(".", "").replace(",", ".")).doubleValue();

                            }
                        }
                    }
                }
                //  System.out.println("ind: "+ind);
                ind = 0;

            }

//            for (int i = 0; i < dadosHistoricosAnosPrevD.length; i++) {
//                for (int j = 0; j < dadosHistoricosAnosPrevD[0].length; j++) {
//                    System.out.print(dadosHistoricosAnosPrevD[i][j] + "\t");
//                }
//                System.out.println("");
//            }

        GeraMatrizHistorico(periodo);
        } catch (Exception ex) {
        }
    }

    public void GeraMatrizHistorico(Cell[] periodo) {
        // matriz que aparecerá quando o usuário solicitar a vizualização de todo histórico
        matrizResultado = new String[500/*(anos.length*12)+anos.length*/][6];
        // System.out.println("anos.length*12: "+anos.length*12+" so anos: "+anos.length);
        String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        int aux = 0;

//        for(String a:anos)
//            System.out.println("ANOS: "+a);

        /*
         * Monta a coluna 0 que terá os meses do histórico
         */
        for (int i = 0; i < anos.length; i++) {
            try {
                for (int j = 0; j < 12; j++) {

                    matrizResultado[aux][0] = formataMes(meses[j]) + "/" + anos[i];
                    aux++;

                }
                matrizResultado[aux][0] = "TOTAL - ANO " + anos[i];
                matrizResultado[aux][2] = " - ";
                matrizResultado[aux][3] = " - ";
                matrizResultado[aux][4] = " - ";
                matrizResultado[aux][5] = " - ";
                aux++;
            } catch (ArrayIndexOutOfBoundsException ex) {
            }
        }
        //  System.out.println("vetor_historicos.length: "+vetor_historicos.length+" total_anos_historico.size(): "+total_anos_historico.size());

        /*
         *  Monta a Coluna 1 com os dados do histórico
         */
        Locale locBrazil = new Locale("pt", "BR");
        NumberFormat nf = NumberFormat.getInstance(locBrazil);
        aux = 0;
        for (int i = 0; i < vetor_historicos.length; i++) {
            for (int j = 0; j < vetor_historicos[i].size(); j++) {
                try {//if(!String.valueOf(vetor_historicos[i].elementAt(j)).isEmpty()){

//                    System.out.println("String.valueOf(vetor_historicos["+i+"].elementAt("+j+")): "+String.valueOf(vetor_historicos[i].elementAt(j)));
                    matrizResultado[aux][1] = nf.format(Double.parseDouble(String.valueOf(vetor_historicos[i].elementAt(j)).replace(",", ".")));//);

                    matrizResultado[aux][4] = matrizResultado[aux][1];
                    matrizResultado[aux][2] = "inexistente";
                    matrizResultado[aux][3] = "inexistente";
                    matrizResultado[aux][5] = "inexistente";
                    aux++;

                } catch (NumberFormatException ex) {

                    matrizResultado[aux][1] = "não aferido!";//naoExisteMsg;
                    matrizResultado[aux][4] = "não aferido!";//
                    matrizResultado[aux][2] = "inexistente";
                    matrizResultado[aux][3] = "inexistente";
                    matrizResultado[aux][5] = "inexistente";
                    aux++;
                }

            }
            matrizResultado[aux][1] = String.valueOf(total_anos_historico.elementAt(i));
            aux++;
        }
    }


//       for(int i=0; i<matrizResultado.length; i++)
//            System.out.println("matrizResultado["+i+"]["+1+"]: "+matrizResultado[i][1]);
    public void geraDadosFinais() {
        int indice = 0;

        p:
        for (int i = 0; i < this.matrizResultado.length; i++) {
            if (this.matrizResultado[i][0].equalsIgnoreCase("TOTAL - Ano " + (Previsao.getPerido()[2] - 1))) {
                indice = i + 1;
                for (int j = i + 1; j < this.matrizResultado.length; j++) {
                    this.matrizResultado[j][0] = null;
                    this.matrizResultado[j][1] = null;

                }
                break p;
            }
        }

        // System.out.println("indice = "+indice);
        int auxiliar = indice;
        for (int i = 0; i < this.dadosRelatorio.length; i++) {
            try {
                this.matrizResultado[indice][0] = this.dadosRelatorio[i][0];
                this.matrizResultado[indice][1] = this.dadosRelatorio[i][1];
                this.matrizResultado[indice][2] = this.dadosRelatorio[i][2];
                this.matrizResultado[indice][3] = this.dadosRelatorio[i][3];
                indice++;
            } catch (ArrayIndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(null, "O programa realizou uma operação inesperada./n Tente novamente, se o problema persistir, contate o Administrador", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }


        int cont = 0;
        for (int i = 0; i < this.matrizResultado.length; i++) {
            try {
                if (!(this.matrizResultado[i][0].equals(null))) {
                    cont++;
                }
            } catch (Exception ex) {
            }
        }

        String[][] aux = new String[cont][6];


        /*
         * Garante que a coluna Real/Previsão tenha os valores da previsão( quando existir previsao)
         * enão os valores a serem atribuidos são o da coluna de dados Históricos
         */
        for (int i = auxiliar; i < cont; i++) {
            if (!matrizResultado[i][1].equalsIgnoreCase(naoExisteMsg)) {
                matrizResultado[i][4] = matrizResultado[i][1];
            } else {
                matrizResultado[i][4] = matrizResultado[i][2];
            }
        }

        df.applyPattern("#.##");

        for (int i = 0/*auxiliar*/; i < this.matrizResultado.length; i++) {
            Locale locBrazil = new Locale("pt", "BR");
            NumberFormat nf = NumberFormat.getInstance(locBrazil);
            Number n = null;
            Number m = null;
            try {
                try {
                    //  System.out.println("n: "+matrizResultado[i][4]);
                    n = nf.parse(matrizResultado[i][4]);
                    m = nf.parse(matrizResultado[i - 13][4]);
                // System.out.println("n - matrizResultado["+i+"]["+4+"]/m - matrizResultado["+(i-13)+"][4]: "+matrizResultado[i][4]+" / "+matrizResultado[i-13][4] +" = "+((n.doubleValue()/m.doubleValue())-1));
                //  System.out.println("m: "+m);
                } catch (ParseException ex) {
                    //ex.printStackTrace();
                    //System.out.println(""+ ex.getMessage());
                } catch (ArrayIndexOutOfBoundsException ex) {
                    try {
                        m = nf.parse("0");
                    } catch (ParseException ex1) {
                        ex1.printStackTrace();
                    }
                }

                if (m.doubleValue() != 0) {
                    matrizResultado[i][5] = df.format((n.doubleValue() / (m.doubleValue()) - 1) * 100).concat("%");
                }
            //System.out.println(">>>>>>>>>>>>>>>> "+ n.doubleValue()+ "/"+ m.doubleValue()+")/100");
            } catch (NullPointerException ex) {
                matrizResultado[i][5] = "0";
            } catch (NumberFormatException ex) {
                matrizResultado[i][5] = "inexistente";//InexistenteMsg;
            }
        }



        for (int i = 0; i < aux.length; i++) {
            aux[i][0] = this.matrizResultado[i][0];

            if (this.matrizResultado[i][1].equalsIgnoreCase("0")) {
                aux[i][1] = "não aferido!";
            } else {
                aux[i][1] = this.matrizResultado[i][1];
            }

            aux[i][2] = this.matrizResultado[i][2];
            aux[i][3] = this.matrizResultado[i][3];
            aux[i][4] = this.matrizResultado[i][4];
            aux[i][5] = this.matrizResultado[i][5];

        }

        matrizResultado = aux;

    }

    public static String formataMes(String data) {
        if (data.equalsIgnoreCase("Janeiro") || data.equalsIgnoreCase("01")) {
            //return "jan";
            return "01";

        } else if (data.equalsIgnoreCase("Fevereiro") || data.equalsIgnoreCase("02")) {
            // return "fev";
            return "02";
        } else if (data.equalsIgnoreCase("Março") || data.equalsIgnoreCase("03")) {
//            return "mar";
            return "03";
        } else if (data.equalsIgnoreCase("Abril") || data.equalsIgnoreCase("04")) {
//            return "abr";
            return "04";
        } else if (data.equalsIgnoreCase("Maio") || data.equalsIgnoreCase("05")) {
//            return "mai";
            return "05";
        } else if (data.equalsIgnoreCase("Junho") || data.equalsIgnoreCase("06")) {
//            return "jun";
            return "06";
        } else if (data.equalsIgnoreCase("Julho") || data.equalsIgnoreCase("07")) {
//            return "jul";
            return "07";
        } else if (data.equalsIgnoreCase("Agosto") || data.equalsIgnoreCase("08")) {
//            return "ago";
            return "08";
        } else if (data.equalsIgnoreCase("Setembro") || data.equalsIgnoreCase("09")) {
//            return "set";
            return "09";
        } else if (data.equalsIgnoreCase("Outubro") || data.equalsIgnoreCase("10")) {
//            return "out";
            return "10";
        } else if (data.equalsIgnoreCase("Novembro") || data.equalsIgnoreCase("11")) {
//            return "nov";
            return "11";
        } else {
//            return "dez";
            return "12";
        }
    }

    /**
     * formata o periodo a ser colocado
     */
    public void definePeriodo() {

        String[] mes = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

        int[] periodo = Previsao.getPerido();


        int contador = 0;

        int mesIni = periodo[0];
        int mesFim = periodo[1];

//        for(int i=0; i<periodo.length; i++)
//            System.out.println("periodo["+i+"]: "+periodo[i]);


        //se a previsao possui meses de anos diferentes
        if (Previsao.getFlagAnosDifer()) {
            int i = mesIni - 1;
            while (i < 12) {
                periodoFomatado[contador] = mes[i] + "/" + String.valueOf(periodo[2]);
                contador++;
                i++;
            }

            int z = 0;
            //se o intervalo de previsao eh maior que 12 meses
            if (Previsao.getFlagNumAnos()) {
                for (int j = 0; j < 12; j++) {
                    periodoFomatado[contador] = mes[j] + "/" + String.valueOf(periodo[2] + 1);
                    contador++;
                }
            //se o intervalo de previsao for menor que 12 meses
            }

            while (z < mesFim) {
                periodoFomatado[contador] = mes[z] + "/" + String.valueOf(periodo[3]);
                contador++;
                z++;
            }
        //se a previsao possui apenas meses do mesmo ano
        } else {
            int z = mesIni - 1;
            while (z < mesFim) {
                periodoFomatado[contador] = mes[z] + "/" + String.valueOf(periodo[3]);
                contador++;
                z++;
            }
        }

//        for(String p:periodoFomatado)
//            System.out.println(""+p);
    }

    /**
     * preve do ano de 2005 ate dois anos alem do ano atual
     */
    public void preveTodosAnos() {

        double taxa = 0,
                predicao = 0,
                b = 0, ln, prod, soma,
                somaTemp, somaTempQuad;
        int tempo;
        int count = 0;    //conta o numero do mes
        int cont = 0;     //conta o numero de anos
        int contadorAnos = 1;
        int contZ = 0;
        int teste = 0;
        boolean flag = true;
        //int anoFim = this.anoAtual - this.anoIni - 1;

        //calculo da previsao para os anos que estao presentes na base
        for (int z = 0; z < (this.dadosAnosPrevistos[1].length - 2); z++) {
            contZ = z;
            for (int i = 0; i < 12; i++) {
                tempo = 1;
                prod = 0;
                soma = 0;
                somaTemp = 0;
                somaTempQuad = 0;
                flag = true;
                cont = 0;
                contadorAnos = 1;

                while (flag) {
                    if (cont < this.anosSelecionados.length) {
                        //    System.out.println("********* entrou aqui");
                        //se o ano selecionado vem antes que o previsto (de 2005 ate 2 anos apos o ano atual)
                        if (Integer.parseInt(this.anosSelecionados[cont]) < (this.anoIniPrevisao + z)) {

                            if ((this.dadosHistoricosD[i][cont] == 0) && (cont < this.dadosHistoricosD[1].length) &&
                                    (Integer.parseInt(this.anosSelecionados[cont]) >= this.anoIniPrevisao)) {
                                //System.out.println("valorHistoricoD[" + i + "][" + cont + "]= "+ this.dadosAnosPrevistos[i][z-1]);
                                ln = Math.log(dadosAnosPrevistos[i][z - 1]);
                                prod = ln * tempo + prod;                               // prod = Y*T2
                                soma = ln + soma;                                       // soma="soma"Y
                                somaTemp = somaTemp + tempo;                            // somaTemp = "soma"tempo
                                somaTempQuad = Math.pow(tempo, 2) + somaTempQuad;       // somaTempQuad = "soma"T2
                                tempo++;
                                predicao = this.dadosAnosPrevistos[i][z - 1];
                                contadorAnos++;

                            //precisa melhorar...eh p qundo os valores antes do ano de 2005 n estao na base
                            } else if ((this.dadosHistoricosD[i][cont] == 0) && (Integer.parseInt(this.anosSelecionados[cont]) < this.anoIniPrevisao)) {
//                                teste++;
//                                System.out.println("teste "+teste);
                                //  continue;
                            } else {
                                //System.out.println("valorHistorico1[" + i + "][" + cont + "]= "+ this.dadosHistoricosD[i][cont]);
                                ln = Math.log(this.dadosHistoricosD[i][cont]);          // ln(consumo)
                                prod = ln * tempo + prod;                               // prod = Y*T2
                                soma = ln + soma;                                       // soma="soma"Y
                                somaTemp = somaTemp + tempo;                            // somaTemp = "soma"tempo
                                somaTempQuad = Math.pow(tempo, 2) + somaTempQuad;       // somaTempQuad = "soma"T2
                                tempo++;
                                predicao = this.dadosHistoricosD[i][cont];
                                contadorAnos++;
                            }
                            cont++;

                        } else {
                            flag = false;
                            cont--;
                            contadorAnos--;
                        }
                    } else {
                        flag = false;
                    }
                }
//                System.out.println("(contadorAnos) * prod): "+(contadorAnos) * prod);
//                System.out.println("(soma * somaTemp)): "+(soma * somaTemp));
//                System.out.println("((contadorAnos) * somaTempQuad) - Math.pow(somaTemp, 2)): "+(((contadorAnos) * somaTempQuad) - Math.pow(somaTemp, 2)));

                double d = ((contadorAnos) * somaTempQuad) - Math.pow(somaTemp, 2);
                if (((contadorAnos) * somaTempQuad) - Math.pow(somaTemp, 2) == 0.0) {
                    d = 1;
                }

                b = (((contadorAnos) * prod) - (soma * somaTemp)) / d;//((((contadorAnos) * somaTempQuad) - Math.pow(somaTemp, 2)));
                //   System.out.println(">>>>>>>>>>>>>>>>>>> b: "+b);
                taxa = Math.exp(b);
                //System.out.println("cont "+contadorAnos);
                //System.out.println("b "+b);
                //   System.out.println("TAXA: "+taxa+"   i: "+ i+"  z: "+z+"  predicao: "+predicao);

                proxValorTotal(taxa, i, z, predicao);
            }
        }

        //calculo da previsao para o ano seguinte ao ano atual
        for (int i = 0; i < 12; i++) {
            tempo = 1;
            prod = 0;
            soma = 0;
            somaTemp = 0;
            somaTempQuad = 0;
            contadorAnos = 0;

            for (int j = 0; j < this.anosSelecionados.length; j++) {
                if (this.dadosHistoricosD[i][j] != 0) {
                    ln = Math.log(this.dadosHistoricosD[i][j]);             // ln(consumo)
                    prod = ln * tempo + prod;                               // prod = Y*T2
                    soma = ln + soma;                                       // soma="soma"Y
                    somaTemp = somaTemp + tempo;                            // somaTemp = "soma"tempo
                    somaTempQuad = Math.pow(tempo, 2) + somaTempQuad;       // somaTempQuad = "soma"T2
                    tempo++;
                    predicao = this.dadosHistoricosD[i][j];
                //System.out.println("2008[" + i + "][" + j + "]= "+ this.dadosHistoricosD[i][j]);
                //calculo a partir dos valores previsto
                } else {
                    //System.out.println("2008Prev[" + i + "][" + j + "]= "+ this.dadosAnosPrevistos[i][this.dadosAnosPrevistos[1].length-3]);
                    ln = Math.log(dadosAnosPrevistos[i][this.dadosAnosPrevistos[1].length - 3]);
                    prod = ln * tempo + prod;                               // prod = Y*T2
                    soma = ln + soma;                                       // soma="soma"Y
                    somaTemp = somaTemp + tempo;                            // somaTemp = "soma"tempo
                    somaTempQuad = Math.pow(tempo, 2) + somaTempQuad;       // somaTempQuad = "soma"T2
                    tempo++;
                    predicao = this.dadosAnosPrevistos[i][this.dadosAnosPrevistos[1].length - 3];
                }
                contadorAnos++;
            }
            b = (((contadorAnos) * prod) - (soma * somaTemp)) / ((((contadorAnos) * somaTempQuad) - Math.pow(somaTemp, 2)));
            taxa = Math.exp(b);
            proxValorTotal(taxa, i, (this.dadosAnosPrevistos[1].length - 2), predicao);
        //System.out.println("b "+b);
        //System.out.println("cont2008 "+contadorAnos);
        }


        //calculo da previsao para o 2ª ano apos o ano atual
        for (int i = 0; i < 12; i++) {
            tempo = 1;
            prod = 0;
            soma = 0;
            somaTemp = 0;
            somaTempQuad = 0;

            contadorAnos = 0;
            for (int j = 0; j < this.anosSelecionados.length; j++) {
                if (this.dadosHistoricosD[i][j] != 0) {
                    ln = Math.log(this.dadosHistoricosD[i][j]);             // ln(consumo)
                    prod = ln * tempo + prod;                               // prod = Y*T2
                    soma = ln + soma;                                       // soma="soma"Y
                    somaTemp = somaTemp + tempo;                            // somaTemp = "soma"tempo
                    somaTempQuad = Math.pow(tempo, 2) + somaTempQuad;       // somaTempQuad = "soma"T2
                    tempo++;
//                    System.out.println("valorHistoricoD[" + i + "][" + j + "]= "+ this.dadosHistoricosD[i][j]);
//                    System.out.println("ln "+ln);
                } else {
                    ln = Math.log(dadosAnosPrevistos[i][this.dadosAnosPrevistos[1].length - 3]);
                    prod = ln * tempo + prod;                               // prod = Y*T2
                    soma = ln + soma;                                       // soma="soma"Y
                    somaTemp = somaTemp + tempo;                            // somaTemp = "soma"tempo
                    somaTempQuad = Math.pow(tempo, 2) + somaTempQuad;       // somaTempQuad = "soma"T2
                    tempo++;
                //System.out.println("Prev[" + i + "][" + j + "]= "+ this.dadosAnosPrevistos[i][this.dadosAnosPrevistos[1].length-3]);
                //System.out.println("ln "+ln);
                }
                contadorAnos++;
            }

            //utilizando o o proximo ano apos o ano atual
            ln = Math.log(this.dadosAnosPrevistos[i][this.dadosAnosPrevistos[1].length - 2]);   // ln(consumo)
            prod = ln * tempo + prod;                                                         // prod = Y*T2
            soma = ln + soma;                                                                 // soma="soma"Y
            somaTemp = somaTemp + tempo;                                                      // somaTemp = "soma"tempo
            somaTempQuad = Math.pow(tempo, 2) + somaTempQuad;                                 // somaTempQuad = "soma"T2
            predicao = this.dadosAnosPrevistos[i][this.dadosAnosPrevistos[1].length - 2];
            contadorAnos++;

            b = ((contadorAnos * prod) - (soma * somaTemp)) / (((contadorAnos * somaTempQuad) - Math.pow(somaTemp, 2)));
            taxa = Math.exp(b);
            proxValorTotal(taxa, i, (this.dadosAnosPrevistos[1].length - 1), predicao);

        }
    }

    /**
     * transfere para as matrizes "dadosPrevistosD" e "dadosPrevistosS"
     * os valores previstos para o periodo desejado que esta na matriz "dadosAnosPrevistos"
     */
    public void defineValoresPrevistos() {
        int[] periodo = Previsao.getPerido();
        int mesIni = periodo[0] - 1;                             //mes inicial da previsao

       // System.out.println("mesIni: " + mesIni);

        int mesFim = periodo[1];                               //mes final da previsao
      //  System.out.println("mesFim: " + mesFim);
        int anoIni = periodo[2] - this.anoIniPrevisao;         //ano inicial da previsao
      //  System.out.println("anoIni: " + anoIni);
        int anoFim = periodo[3] - this.anoIniPrevisao;         //ano final da previsao
      //  System.out.println("anoFim: " + anoFim);
        int count = 0;
        int anoFimHist = 1;

        //se a previsao eh para meses do mesmo ano
        if (!Previsao.getFlagAnosDifer()) {
            for (int i = mesIni; i < mesFim; i++) {
                //System.out.println("this.dadosAnosPrevistos["+i+"]["+anoFim+"]: "+this.dadosAnosPrevistos[i][anoFim]);
                this.dadosPrevistosD[count] = this.dadosAnosPrevistos[i][anoFim];
                this.dadosPrevistosS[count] = String.valueOf(this.dadosAnosPrevistos[i][anoFim]);

                //coloca os valores dos dados historicos da predicao na matriz de dados historicos
                if (this.dadosHistoricosAnosPrevD[i][0] != 0) {
                    dadosHistoricosS[count] = String.valueOf(this.dadosHistoricosAnosPrevD[i][0]);
                } else {
                    dadosHistoricosS[count] = this.naoExisteMsg;
                }
                //System.out.println("1- dadosPrevistosD["+count+"]= "+this.dadosPrevistosD[count]);
                //System.out.println("dadosHistoricosS["+count+"]= "+this.dadosHistoricosS[count]);
                count++;
            }
        //se a previsao possui meses de anos diferentes
        } else {
            for (int i = mesIni; i < 12; i++) {
                //   System.out.println("this.dadosAnosPrevistos["+i+"]["+anoFim+"]: "+this.dadosAnosPrevistos[i][anoFim]);
                this.dadosPrevistosD[count] = this.dadosAnosPrevistos[i][anoIni];
                this.dadosPrevistosS[count] = String.valueOf(this.dadosAnosPrevistos[i][anoIni]);

                //coloca os valores dos dados historicos da predicao na matriz de dados historicos
                if (this.dadosHistoricosAnosPrevD[i][0] != 0) {
                    dadosHistoricosS[count] = String.valueOf(this.dadosHistoricosAnosPrevD[i][0]);
                } else {
                    dadosHistoricosS[count] = this.naoExisteMsg;
                }
                //  System.out.println("2- dadosPrevistosD["+count+"]= "+this.dadosPrevistosD[count]);
                //System.out.println("dadosHistoricosS["+count+"]= "+this.dadosHistoricosS[count]);
                count++;
            }
            //se a previsao for de 2 anos ou mais de 1 ano
            if (Previsao.getFlagNumAnos()) {
                for (int i = 0; i < 12; i++) {
                    //System.out.println("this.dadosAnosPrevistos["+i+"]["+(anoIni+1)+"] :"+this.dadosAnosPrevistos[i][anoIni+1]);
                    this.dadosPrevistosD[count] = this.dadosAnosPrevistos[i][anoIni + 1];
                    this.dadosPrevistosS[count] = String.valueOf(this.dadosAnosPrevistos[i][anoIni + 1]);

                    //coloca os valores dos dados historicos da predicao na matriz de dados historicos
                    if (this.dadosHistoricosAnosPrevD[i][anoFimHist] != 0) {
                        dadosHistoricosS[count] = String.valueOf(this.dadosHistoricosAnosPrevD[i][anoFimHist]);
                    } else {
                        dadosHistoricosS[count] = this.naoExisteMsg;
                    }
                    // System.out.println("3- dadosPrevistosD["+count+"]= "+this.dadosPrevistosD[count]);
                    //System.out.println("dadosHistoricosS["+count+"]= "+this.dadosHistoricosS[count]);
                    count++;
                }
                anoFimHist++;
            }

            //faz o restante da previsao
            for (int i = 0; i < mesFim; i++) {
                this.dadosPrevistosD[count] = this.dadosAnosPrevistos[i][anoFim];
                this.dadosPrevistosS[count] = String.valueOf(this.dadosAnosPrevistos[i][anoFim]);

                //coloca os valores dos dados historicos da predicao na matriz de dados historicos
                if (this.dadosHistoricosAnosPrevD[i][anoFimHist] != 0) {
                    dadosHistoricosS[count] = String.valueOf(this.dadosHistoricosAnosPrevD[i][anoFimHist]);
                } else {
                    dadosHistoricosS[count] = this.naoExisteMsg;
                }
                //  System.out.println("4- dadosPrevistosD["+count+"]= "+this.dadosPrevistosD[count]);
                //System.out.println("dadosHistoricosS["+count+"]= "+this.dadosHistoricosS[count]);
                count++;
            }
        }


    }

    /**
     * define o valor de previsao para todos os anos
     */
    public void proxValorTotal(double taxa, int i, int j, double predicao) {
        this.dadosAnosPrevistos[i][j] = taxa * predicao;

    //System.out.println("valorPrevisto[" + i + "]["+ j + "]= "+ this.dadosAnosPrevistos[i][j]);
    }

    /**
     *calcula o erro mensal da previsao em percentagem
     */
    public void defErro() {
        double erro;
        String valorErro, valorErro1;

        nf.setMaximumFractionDigits(2);

        for (int i = 0; i < this.dadosHistoricosS.length; i++) {
            if (!this.dadosHistoricosS[i].equals(this.naoExisteMsg)) {
                erro = (Double.parseDouble(this.dadosHistoricosS[i]) - this.dadosPrevistosD[i]) / this.dadosPrevistosD[i];
                erro = (erro) * 100;
                valorErro = String.valueOf(erro);
                valorErro.replace(",", ".");
                valorErro = String.valueOf(nf.format(Double.parseDouble(valorErro)));
                valorErro1 = valorErro.replace(",", ".");
                try {
                    erro = nf.parse(valorErro1).doubleValue();//Double.parseDouble(valorErro1);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }//Double.parseDouble(valorErro1);
                this.erroMensalD[i] = erro;
                valorErro1 = valorErro.replace(".", ",");
                this.erroMensalS[i] = String.valueOf(valorErro1);
            } else {
                this.erroMensalS[i] = String.valueOf(0);//this.InexistenteMsg;
            }
        }
    }

    /**
     * calcula o erro medio de cada ano em percentagem
     */
    public void calcErroAnual() {

        //double somaErroD = 0;
        double somaErroPrevisto = 0;
        double somaErroHistorico = 0;
        double erro;
        int[] periodo = Previsao.getPerido();
        int mesIni = periodo[0] - 1;                             //mes inicial da previsao
        int mesFim = periodo[1];                               //mes final da previsao
        int anoIni = periodo[2] - this.anoIniPrevisao;         //ano inicial da previsao
        int anoFim = periodo[3] - this.anoIniPrevisao;         //ano final da previsao
        int anoIniHist = periodo[2] - this.anoIni;             //ano inicial para dados historicos
        int anoFimHist = periodo[3] - this.anoIni;             //ano final para dados historicos
        int countI = 0;
        int countDim = 0;                                      //conta a dimensao do erro anual
        String somaErroPrevistoS = "";
        String somaErroHistoricoS = "";
        String valorErro;
        String valorErro1;
        boolean soma = true;//false;
        boolean flagDim = true;//false;

        nf.setMaximumFractionDigits(2);

        //se a previsao eh para meses do mesmo ano
        if (!Previsao.getFlagAnosDifer()) {
            for (int i = mesIni; i < mesFim; i++) {
                if (!this.erroMensalS[countI].equalsIgnoreCase(this.InexistenteMsg)) {
                    somaErroPrevisto = somaErroPrevisto + this.dadosPrevistosD[countI];
                    somaErroHistorico = somaErroHistorico + Double.parseDouble(this.dadosHistoricosS[countI]);
                    //System.out.println(this.dadosPrevistosD[countI]);
                    countI++;
                //   flagDim = true;

                //System.out.println(this.dadosHistoricosS[countI]);

                //System.out.println("somaErroPrevisto "+ somaErroPrevisto);
                //System.out.println("somaErroHistorico "+somaErroHistorico);
                }
            }
            somaErroPrevistoS = somaErroPrevisto + "#";
            somaErroHistoricoS = somaErroHistorico + "#";
            if (flagDim) {
                countDim++;
            }
        //   flagDim = false;

        //se a previsao possui meses de anos diferentes
        } else {
            for (int i = mesIni; i < 12; i++) {
                //  System.out.println("this.dadosHistoricosS["+countI+"] :"+this.dadosHistoricosS[countI]);
                if (!this.erroMensalS[countI].equalsIgnoreCase(this.InexistenteMsg)) {
                    somaErroPrevisto = somaErroPrevisto + this.dadosPrevistosD[countI];
                    try {
                        somaErroHistorico = somaErroHistorico + Double.parseDouble(this.dadosHistoricosS[countI]);
                    } catch (NumberFormatException ex) {
                        // somaErroHistorico = somaErroHistorico;
                    }
                    // System.out.println("somaErroHistorico: "+somaErroHistorico);
                    //System.out.println(this.dadosPrevistosD[countI]);
                    countI++;
                //  flagDim = true;

                //System.out.println("somaErroPrevisto "+ somaErroPrevisto);
                //System.out.println("somaErroHistorico "+somaErroHistorico);
                }
            }
            somaErroPrevistoS = somaErroPrevisto + "#";
            somaErroHistoricoS = somaErroHistorico + "#";
            if (flagDim) {
                countDim++;
            }
            // flagDim = false;

            //se a previsao for de 2 anos ou mais de 1 ano
            if (Previsao.getFlagNumAnos()) {
                somaErroPrevisto = 0;
                somaErroHistorico = 0;
                for (int i = 0; i < 12; i++) {
                    if (!this.erroMensalS[countI].equals(this.InexistenteMsg)) {
                        somaErroPrevisto = somaErroPrevisto + this.dadosPrevistosD[countI];
                        try {
                            somaErroHistorico = somaErroHistorico + Double.parseDouble(this.dadosHistoricosS[countI]);
                        } catch (NumberFormatException ex) {
                            //somaErroHistorico = somaErroHistorico;
                        }
                        //   System.out.println("somaErroHistorico: "+somaErroHistorico);
                        //System.out.println(this.dadosPrevistosD[countI]);
                        countI++;
                    //   soma = true;
                    //  flagDim = true;
                    //System.out.println("somaErroPrevisto "+ somaErroPrevisto);
                    //System.out.println("somaErroHistorico "+somaErroHistorico);

                    }
                }

                if (soma) {
                    somaErroPrevistoS = somaErroPrevistoS + somaErroPrevisto + "#";
                    somaErroHistoricoS = somaErroHistoricoS + somaErroHistorico + "#";
                    countDim++;
                }
            //  soma = false;
            }
            somaErroPrevisto = 0;
            somaErroHistorico = 0;
            //faz o restante da previsao
            for (int i = 0; i < mesFim; i++) {
                if (!this.erroMensalS[countI].equals(this.InexistenteMsg)) {
                    somaErroPrevisto = somaErroPrevisto + this.dadosPrevistosD[countI];
                    try {
                        somaErroHistorico = somaErroHistorico + Double.parseDouble(this.dadosHistoricosS[countI]);
                    } catch (NumberFormatException ex) {
                    }

                    //System.out.println("somaErroHistorico: "+somaErroHistorico);
                    // somaErroHistorico =somaErroHistorico;}
                    //System.out.println(this.dadosPrevistosD[countI]);
                    countI++;
                //   soma = true;

                //System.out.println("somaErroPrevisto "+ somaErroPrevisto);
                //System.out.println("somaErroHistorico "+somaErroHistorico);
                }
            }
            if (soma) {
                somaErroPrevistoS = somaErroPrevistoS + somaErroPrevisto + "#";
                somaErroHistoricoS = somaErroHistoricoS + somaErroHistorico + "#";
                countDim++;
            }
        }

        //System.out.println("ErrosPrev "+somaErroPrevistoS);
        //System.out.println("ErrosHist "+somaErroHistoricoS);

        String[] somaErroPrev = somaErroPrevistoS.split("#");
        String[] somaErroHist = somaErroHistoricoS.split("#");

        //System.out.println("ErrosPrev "+somaErroPrev.length);
        //System.out.println("ErrosHist "+somaErroHist.length);

        //System.out.println("dim "+countDim);

        this.erroAnualD = new double[countDim];
        this.erroAnualS = new String[countDim];

        if (countDim > 0) {
            for (int i = 0; i < countDim; i++) {

                this.erroAnualD[i] = (Double.parseDouble(somaErroHist[i]) - Double.parseDouble(somaErroPrev[i])) / Double.parseDouble(somaErroPrev[i]);

                // System.out.println("this.erroAnualD["+i+"] " + this.erroAnualD[i]);
                erro = (this.erroAnualD[i]) * 100;
                valorErro = String.valueOf(erro);
                valorErro.replace(",", ".");

                valorErro = String.valueOf(nf.format(Double.parseDouble(valorErro)));
                valorErro1 = valorErro.replace(",", ".");
                //
                try {

                    erro = nf.parse(valorErro1).doubleValue();//Double.parseDouble(valorErro1);
                } catch (ParseException ex) {
                    //erro = 0;
                    ex.printStackTrace();
                }//Double.parseDouble(valorErro1);
                this.erroAnualD[i] = erro;
                valorErro1 = valorErro.replace(".", ",");
                this.erroAnualS[i] = valorErro1;

//                System.out.println("somaErroPrev["+i+"] " + somaErroPrev[i]);
//                System.out.println("somaErroHist["+i+"] " + somaErroHist[i]);
//                System.out.println("erroAnualD["+i+"] "+this.erroAnualS[i]);
            }
        }


//        this.erroAnualS[1]=String.valueOf(0);
//        this.erroAnualS[2]=String.valueOf(0);
//       for(int i=0; i<this.erroAnualS.length; i++){
//          // if(this.erroAnualS[i].equalsIgnoreCase("?"))
//           System.out.println("this.erroAnualS["+i+"]: "+this.erroAnualS[i]);
//        //   else
//             //  this.erroAnualS[i]=String.valueOf(0);
//       }

    }

    /**
     * calculo do erro total da predicao em percentagem
     */
    public void mediaErro() {
        double erro;
        double somaErroPrevisto = 0;
        double somaErroHistorico = 0;
        String valorErro, valorErro1;

        nf.setMaximumFractionDigits(2);

        for (int i = 0; i < this.dadosHistoricosS.length; i++) {
            //  System.out.println("this.Historico["+i+"] "+this.dadosHistoricosS[i]);
            if (!this.dadosHistoricosS[i].equals(this.naoExisteMsg)) {
                somaErroPrevisto = somaErroPrevisto + this.dadosPrevistosD[i];
                somaErroHistorico = somaErroHistorico + Double.parseDouble(this.dadosHistoricosS[i]);
            }
        }
        if (somaErroPrevisto != 0) {
            //calculo do erro = (real - prev)/prev
            erro = (somaErroHistorico - somaErroPrevisto) / somaErroPrevisto;
            erro = (erro) * 100;
            valorErro = String.valueOf(erro);
            valorErro.replace(",", ".");
            valorErro = String.valueOf(nf.format(Double.parseDouble(valorErro)));
            valorErro1 = valorErro.replace(",", ".");
            try {
                erro = nf.parse(valorErro1).doubleValue();//Double.parseDouble(valorErro1);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }//Double.parseDouble(valorErro1);
            this.erroTotalD = erro;
            valorErro1 = valorErro.replace(".", ",");
            this.erroTotalS = String.valueOf(valorErro1);
        } else {
            this.erroTotalS = this.naoExisteMsg;
        }


    }

    public void formataRelatorio() {
        int[] periodo = Previsao.getPerido();
        int mesIni = periodo[0] - 1;                             //mes inicial da previsao
        int mesFim = periodo[1];                               //mes final da previsao
        int anoIni = periodo[2] - this.anoIniPrevisao;         //ano inicial da previsao
        int anoFim = periodo[3] - this.anoIniPrevisao;         //ano final da previsao
        int anoIniHist = periodo[2] - this.anoIni;             //ano inicial para dados historicos
        int anoFimHist = periodo[3] - this.anoIni;             //ano final para dados historicos
        int countI = 0;
        int countII = 0;
        int countErro = 0;
        boolean soma = false;
        String aux1, aux2, aux3;

        this.dadosRelatorio = new String[this.dadosHistoricosS.length + this.erroAnualS.length][4];

        //se a previsao eh para meses do mesmo ano
        if (!Previsao.getFlagAnosDifer()) {
            for (int i = mesIni; i < mesFim; i++) {
                this.dadosRelatorio[countI][0] = this.periodoFomatado[countII];
                this.dadosRelatorio[countI][1] = this.dadosHistoricosS[countII];
                this.dadosRelatorio[countI][2] = this.dadosPrevistosS[countII];
                this.dadosRelatorio[countI][3] = this.erroMensalS[countII];


//               System.out.println("this.dadosRelatorio["+countI+"]["+0+"]= "+this.dadosRelatorio[countI][0]);
//                System.out.println("this.dadosRelatorio["+countI+"]["+1+"]= "+this.dadosRelatorio[countI][1]);
//                System.out.println("this.dadosRelatorio["+countI+"]["+2+"]= "+this.dadosRelatorio[countI][2]);
//                System.out.println("this.dadosRelatorio["+countI+"]["+3+"]= "+this.dadosRelatorio[countI][3]);
//                System.out.println("periodoFormatado["+countII+"]= "+this.periodoFomatado[countII]);
                countI++;
                countII++;
            }
            if (this.erroAnualS.length > 0) {
                this.dadosRelatorio[countI][0] = this.erroAnualMsg;
                this.dadosRelatorio[countI][1] = this.nadaConstaMsg;
                this.dadosRelatorio[countI][2] = this.nadaConstaMsg;
                this.dadosRelatorio[countI][3] = this.erroAnualS[0];
            }


        //se a previsao possui meses de anos diferentes
        } else {
            for (int i = mesIni; i < 12; i++) {
                this.dadosRelatorio[countI][0] = this.periodoFomatado[countII];
                this.dadosRelatorio[countI][1] = this.dadosHistoricosS[countII];
                this.dadosRelatorio[countI][2] = this.dadosPrevistosS[countII];
                this.dadosRelatorio[countI][3] = this.erroMensalS[countII];
                countI++;
                countII++;
//                System.out.println("somaErroPrevisto "+ somaErroPrevisto);
//                System.out.println("somaErroHistorico "+somaErroHistorico);
            }
            //System.out.println("countErro "+countErro);
            //System.out.println("erroAnualL "+erroAnualS.length);
            if (countErro < this.erroAnualS.length) {
                this.dadosRelatorio[countI][0] = this.erroAnualMsg;
                this.dadosRelatorio[countI][1] = this.nadaConstaMsg;
                this.dadosRelatorio[countI][2] = this.nadaConstaMsg;
                this.dadosRelatorio[countI][3] = this.erroAnualS[countErro];
//                System.out.println("dadosRelatorio["+countI+"]["+0+"] ="+ this.dadosRelatorio[countI][0]);
                countI++;
                countErro++;
            }

            //se a previsao for de 2 anos ou mais de 1 ano
            if (Previsao.getFlagNumAnos()) {
                for (int i = 0; i < 12; i++) {
                    this.dadosRelatorio[countI][0] = this.periodoFomatado[countII];
                    this.dadosRelatorio[countI][1] = this.dadosHistoricosS[countII];
                    this.dadosRelatorio[countI][2] = this.dadosPrevistosS[countII];
                    this.dadosRelatorio[countI][3] = this.erroMensalS[countII];
                    countI++;
                    countII++;
                    soma = true;
                //System.out.println("somaErroPrevisto "+ somaErroPrevisto);
                //System.out.println("somaErroHistorico "+somaErroHistorico);
                }

                //System.out.println("countErro "+countErro);
                //System.out.println("erroAnualL "+erroAnualS.length);
                if ((soma) && (countErro < this.erroAnualS.length)) {
                    this.dadosRelatorio[countI][0] = this.erroAnualMsg;
                    this.dadosRelatorio[countI][1] = this.nadaConstaMsg;
                    this.dadosRelatorio[countI][2] = this.nadaConstaMsg;
                    this.dadosRelatorio[countI][3] = this.erroAnualS[countErro];
                    countErro++;
                    countI++;
                }
                soma = false;
            }

            //faz o restante da previsao
            for (int i = 0; i < mesFim; i++) {
                this.dadosRelatorio[countI][0] = this.periodoFomatado[countII];
                this.dadosRelatorio[countI][1] = this.dadosHistoricosS[countII];
                this.dadosRelatorio[countI][2] = this.dadosPrevistosS[countII];
                this.dadosRelatorio[countI][3] = this.erroMensalS[countII];
                countI++;
                countII++;
                soma = true;

            //System.out.println("somaErroPrevisto "+ somaErroPrevisto);
            //System.out.println("somaErroHistorico "+somaErroHistorico);

            }
            //System.out.println("countErro "+countErro);
            //System.out.println("erroAnualL "+erroAnualS.length);
            if ((soma) && (countErro < this.erroAnualS.length)) {
                this.dadosRelatorio[countI][0] = this.erroAnualMsg;
                this.dadosRelatorio[countI][1] = this.nadaConstaMsg;
                this.dadosRelatorio[countI][2] = this.nadaConstaMsg;
                this.dadosRelatorio[countI][3] = this.erroAnualS[countErro];
                countErro++;
                countI++;
            }
        }
        for (int i = 0; i < this.dadosRelatorio.length; i++) {
            if ((!this.dadosRelatorio[i][1].equals(this.naoExisteMsg)) && (!this.dadosRelatorio[i][1].equals(this.nadaConstaMsg))) {
                aux2 = this.dadosRelatorio[i][1];
                //System.out.println("aux2 " + aux2);
                aux2.replace(",", ".");
                aux2 = String.valueOf(nf.format(Double.parseDouble(aux2)));
                //System.out.println("aux21 " + aux2);
                this.dadosRelatorio[i][1] = aux2;
            }

            if ((!this.dadosRelatorio[i][2].equals(this.naoExisteMsg)) && (!this.dadosRelatorio[i][2].equals(this.nadaConstaMsg))) {
                aux2 = this.dadosRelatorio[i][2];
                //System.out.println("aux2 " + aux2);
                aux2.replace(",", ".");
                aux2 = String.valueOf(nf.format(Double.parseDouble(aux2)));
                //System.out.println("aux21 " + aux2);
                //aux3 = aux2.replace(".",",");
                this.dadosRelatorio[i][2] = aux2;
            }
        }
        geraDadosFinais();
    }

//    public void geraDadosFinais()
//    {
//        int indice=0;
//
//        p: for(int i=0; i<this.matrizResultado.length;i++)
//        {
//            if(this.matrizResultado[i][0].equalsIgnoreCase("TOTAL - Ano "+(Previsao.getPerido()[2]-1)))
//            {
//                indice = i+1;
//                for(int j=i+1; j<this.matrizResultado.length; j++)
//                {
//                    this.matrizResultado[j][0]= null;
//                    this.matrizResultado[j][1]= null;
//
//                }
//                  break p;
//            }
//        }
//        for(int i=0; i<matrizResultado.length; i++)
//        {
//            System.out.println("matrizResultado["+i+"][1]: "+matrizResultado[i][1] );
//        }
//
//       // System.out.println("indice = "+indice);
//        int auxiliar = indice;
//        for(int i=0; i<this.dadosRelatorio.length-1;i++)
//        {
//            try{
//            this.matrizResultado[indice][0]=this.dadosRelatorio[i][0];
//            this.matrizResultado[indice][1]=this.dadosRelatorio[i][1];
//            this.matrizResultado[indice][2]=this.dadosRelatorio[i][2];
//            this.matrizResultado[indice][3]=this.dadosRelatorio[i][3];
//            indice++;
//            }catch(ArrayIndexOutOfBoundsException ex){
//                JOptionPane.showMessageDialog(null,"O programa realizou uma operação inesperada./n Tente novamente, se o problema persistir, contate o Administrador","Erro",JOptionPane.ERROR_MESSAGE);
//            }
//        }
//
//
//
//
//        for(int i=0; i<this.matrizResultado.length; i++)
//        {
//            try{
//            String auxi= String.valueOf(this.matrizResultado[i][0].charAt(1));
//            if(this.matrizResultado[i][3]==null&&!(auxi.startsWith("TOTAL - Ano")))
//            {
//                this.matrizResultado[i][3]= "inexistente";
//            }
//            }catch(Exception ex){}
//        }
//
//        int cont=0;
//        for(int i=0; i<this.matrizResultado.length; i++)
//        {try{
//            if(!(this.matrizResultado[i][0].equals(null)))
//                cont++;
//         }catch(Exception ex){}
//        }
//
//        String[][] aux = new String[cont][6];
//
//
//	for(int i=auxiliar; i<cont;i++)
//        {
//             Locale locBrazil = new Locale("pt", "BR");
//             NumberFormat nf = NumberFormat.getInstance(locBrazil);
//            Number n = null;
//            Number m= null;
//        try{
//            try {
//                n = nf.parse(matrizResultado[i][1]);
//                m  = nf.parse(matrizResultado[i][2]);
//            } catch (ParseException ex) {
//                //ex.printStackTrace();
//            }
//
//
//             matrizResultado[i][4]=String.valueOf(n.doubleValue()/m.doubleValue());
//          //  matrizResultado[i][4]= String.valueOf(Double.parseDouble(matrizResultado[i][1].replace(",", "."))/Double.parseDouble(matrizResultado[i][2].replace(",", ".")));
//        }catch(Exception ex){
//            matrizResultado[i][4]=String.valueOf(0);
//        }
//        }
//
//
//	for(int i=auxiliar; i<this.matrizResultado.length; i++)
//	{
//		Locale locBrazil = new Locale("pt", "BR");
//             NumberFormat nf = NumberFormat.getInstance(locBrazil);
//            Number n = null;
//            Number m= null;
//           try{
//            try {
//               //  System.out.println("n: "+matrizResultado[i][4]);
//                n = nf.parse(matrizResultado[i][4]);
//
//                m  = nf.parse(matrizResultado[i-13][4]);
//              //  System.out.println("m: "+m);
//            } catch (Exception ex) {
//               // ex.printStackTrace();
//            }
//             String auxi= String.valueOf(this.matrizResultado[i][0]);
//            if(m.doubleValue()!=0)
//            {
//
//             matrizResultado[i][5]=df.format((n.doubleValue()/m.doubleValue())/100).concat("%");
//             //System.out.println(">>>>>>>>>>>>>>>> "+ n.doubleValue()+ "/"+ m.doubleValue()+")/100");
//            }
//           }catch(NullPointerException ex)
//           {
//               matrizResultado[i][5]=String.valueOf(0);
//           }
//	}
//
//
//
//        for(int i=0; i<aux.length; i++)
//        {
//            aux[i][0]=this.matrizResultado[i][0];
//            aux[i][1]=this.matrizResultado[i][1];
//            aux[i][2]=this.matrizResultado[i][2];
//            aux[i][3]=this.matrizResultado[i][3];
//            aux[i][4]=this.matrizResultado[i][2];
//            aux[i][5]=this.matrizResultado[i][5];
//
//        }
//
//        matrizResultado = aux;
//
//    }
    public static String[] getDadosHistoricos() {
        return dadosHistoricosS;
    }

    public static String[][] getDadosRelatorio() {
        return dadosRelatorio;
//        return matrizResultado;

    }

    public static String[][] getDadosRelatorioModif() {

        return matrizResultado;
    }

    public static String[] getDadosPrevistos() {
        return dadosPrevistosS;
    }

    public static String[] getErroMensal() {
        return erroMensalS;
    }

    public static String[] getErroAnual() {
        return erroAnualS;
    }

    public static String[] getPeriodo() {
        return periodoFomatado;
    }

    public static String getErroTotal() {
        return erroTotalS;
    }
}
