package br.ufpa.lprad.predict.previsao.neural;

import br.ufpa.lprad.predict.previsao.bean.PrevisaoBean;
import java.text.NumberFormat;
import java.util.Locale;

public class PrevisaoRNA {

    private static String[][] valorHistorico;
    private static int[] periodo;
    private static int dimensao;                            //valor com o numero de meses para realizar previsao
    private static int numAnos;                             //numero de anos diferentes para previsao    
    int difMeses;
    private static boolean flagAnosDifer = false;            //variavel que define se a previsao contem valores de anos diferentes --> false: nao possui valores de anos diferentes
    private static boolean flagNumAnos = false;              //variavel que define se a previsao possui valores de 1 ou 2 anos diferentes -->false: possui apenas 1 ano de previsao      
    static NumberFormat nf;
    static Locale locale;
    public static String nomePlanilha;
    public static String tipoConsumo;
    public static String nomeConsumo;
    public static String[] anos;
    public static PrevisaoBean pb;
    public static String pathArquivoDados;
    public static String anoInicial;
    public static String anoFinal;
    public static String anoTeste;

    public PrevisaoRNA() {

        this.periodo = new int[4];   //--> posicao 0:mesInicial
        //--> posicao 1:mesFinal
        //--> posicao 2:anoInicial
        //--> posicao 3:anoFinal


        this.periodo[0] = formataData(PrevisaoBean.getMesIni());
        this.periodo[1] = formataData(PrevisaoBean.getMesFim());
        this.periodo[2] = PrevisaoBean.getAnoIni();
        this.periodo[3] = PrevisaoBean.getAnoFim();

        this.difMeses = periodo[1] - periodo[0];

        definePrevisao();
        fazPrevisao();
    }

    public void fazPrevisao() {
        try {
//            System.out.println("-----------------------------------------");
//            System.out.println("" + PrevisaoBean.getMesIni());
//            System.out.println("" + PrevisaoBean.getMesFim());
//            System.out.println("" + PrevisaoBean.getAnoIni());
//            System.out.println("" + PrevisaoBean.getAnoFim());
//            System.out.println("-----------------------------------------\n");
//            nomePlanilha = PrevisaoBean.getNomePlanilha();
            anos = PrevisaoBean.getAnosSelecionados();
            nomeConsumo = PrevisaoBean.getNomeConsumo();


            PrevisaoNeural p = new PrevisaoNeural(PrevisaoBean.getTipoConsumo(), PrevisaoBean.getNomeConsumo());//PrevisaoBean.getMesIni(), PrevisaoBean.getAnoIni(), PrevisaoBean.getMesFim(), PrevisaoBean.getAnoFim(), PrevisaoBean.getAnosSelecionados(), PrevisaoBean.getTipoConsumo(), PrevisaoBean.getNomeConsumo());

//            String Erro = p.erroMedioPercentual(p.getPrevistosGrafico(), p.getHistoricoGrafico());

            ThreadGeraRelProjecaoRNA geraRelProjecao = new ThreadGeraRelProjecaoRNA();
            geraRelProjecao.start();
//            if (((PrevisaoBean.getAnoFim() - PrevisaoBean.getAnoIni()) == 2) || ((PrevisaoBean.getAnoFim() - PrevisaoBean.getAnoIni()) == 1) || (difMeses >= 1)) {
//                new Grafico(p.getPeriodoGrafico(), p.getHistoricoGrafico(), p.getPrevistosGrafico(), Erro, new String[]{});
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /** 
     * define o periodo para previsao, se eh tem menos de 1 ano, se tem 1 ou 2 anos 
     */
    public void definePrevisao() {

        //se o periodo for de 2 anos
        if ((this.periodo[3] - this.periodo[2]) == 2) {
            if (this.periodo[0] != this.periodo[1]) {
                dimensao = (this.periodo[1] + (13 - this.periodo[0]) + 12);
            //System.out.println("dimensao2anos= "+dimensao);
            } else {
                dimensao = 25;
            //System.out.println("dimensao2anos= "+dimensao);
            }
            this.flagNumAnos = true;
            this.flagAnosDifer = true;
            numAnos = 3;

        //se o periodo for maior ou igual a 1 ano e menor que 2 anos
        } else if ((this.periodo[3] - this.periodo[2]) == 1) {
            //se os anos inicial e final sao diferentes
            if (this.periodo[1] < this.periodo[0]) {
                dimensao = (this.periodo[1] + (13 - this.periodo[0]));
            //System.out.println("dimensao1ano= "+dimensao);

            //se os anos inicial e final sao iguais
            } else if (this.periodo[1] == this.periodo[0]) {
                dimensao = 13;
            //System.out.println("dimensao1ano= "+dimensao);
            } else {
                dimensao = (13 - this.periodo[0] + this.periodo[1]);
            //System.out.println("dimensao2anos1= "+dimensao);
            }
            this.flagAnosDifer = true;
            this.flagNumAnos = false;
            numAnos = 2;
        //se o periodo for do mesmo ano ou deseja-se apenas a previsao de 1 mes
        } else {
            if (this.periodo[0] != this.periodo[1]) {
                dimensao = (this.periodo[1] - this.periodo[0] + 1);
            //System.out.println("dimensaoAnosIguais= "+dimensao);
            } else {
                dimensao = 1;
            //System.out.println("dimensaoAnosIguais= "+dimensao);
            }
            this.flagAnosDifer = false;
            numAnos = 1;
        }
    }

    /**
     * retorna o valor de dimensao
     */
    public static int getDimensao() {
        return dimensao;
    }

    public static int getNumAnos() {
        return numAnos;
    }

    /**
     * metodo que formata o mes do periodo de previsao
     */
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

    public static String[][] getValorHistorico() {
        return valorHistorico;
    }

    public static int[] getPerido() {
        return periodo;
    }

    public static boolean getFlagNumAnos() {
        return flagNumAnos;
    }

    public static boolean getFlagAnosDifer() {
        return flagAnosDifer;
    }

    private void setValorHistorico(String[][] valorHistorico) {
        this.valorHistorico = valorHistorico;
    }

    public static String[][] getHistTable() {

        String[][] str = getValorHistorico();
        String[][] valores = new String[str.length][4];

        // Set the default locale to custom locale
        locale = new Locale("pt", "BR");
        Locale.setDefault(locale);
        nf = NumberFormat.getNumberInstance(locale);
        nf.setMaximumFractionDigits(2);

        int indexPonto = 0;

        for (int i = 0; i < str.length; i++) {
            for (int j = 0; j < 4; j++) {

                valores[i][0] = str[0][i];

                try {
                    indexPonto = str[i][1].indexOf(".");
                    valores[i][1] = nf.format(Math.round(Double.parseDouble(str[1][i + 1].replaceAll(",", "."))));
                } catch (NumberFormatException nfrmtex) {
                    valores[i][1] = "_";
                }
                try {
                    indexPonto = str[i][1].indexOf(".");
                    valores[i][2] = nf.format(Math.round(Double.parseDouble(str[2][i + 1].replaceAll(",", "."))));
                } catch (NumberFormatException nfrmtex) {
                    valores[i][2] = "";
                }
                try {
                    indexPonto = str[i][1].indexOf(".");
                    valores[i][3] = nf.format(Math.round(Double.parseDouble(str[3][i + 1].replaceAll(",", "."))));
                } catch (NumberFormatException nfrmtex) {
                    valores[i][3] = "";
                }
            }
        }
        return valores;
    }
}
