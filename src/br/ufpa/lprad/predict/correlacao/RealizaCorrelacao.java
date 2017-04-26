package br.ufpa.lprad.predict.correlacao;

import br.ufpa.lprad.predict.correlacao.io.AbreXMLCorrelacaoPadrao;
import br.ufpa.lprad.predict.correlacao.io.AbreXMLCorrelacao;
import br.ufpa.lprad.predict.actions.Action;
import br.ufpa.lprad.predict.gui.window.correlacao.backstage.BackCorrelacaoGUI;
import br.ufpa.lprad.predict.leitura.ControleLeitura;
import br.ufpa.lprad.predict.leitura.interfaces.InterfaceLeituraClimaticos;
import br.ufpa.lprad.predict.leitura.interfaces.InterfaceLeituraConsumo;
import br.ufpa.lprad.predict.leitura.interfaces.InterfacerLeituraSocioEconomico;
import br.ufpa.lprad.predict.propriedades.PredictPropriedades;
import br.ufpa.lprad.predict.redebayesiana.aprendizado.K2;
import br.ufpa.lprad.predict.redebayesiana.aprendizado.RedeBayesiana;
import br.ufpa.lprad.predict.redebayesiana.io.AbrirXML;
import br.ufpa.lprad.predict.redebayesiana.io.GravaXML;
import br.ufpa.lprad.predict.redebayesiana.io.VerificaBaseDados;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author J. Gabriel Lima
 */
public class RealizaCorrelacao extends Thread {

    public K2 k2;
    public static RedeBayesiana rb;
    private File f;
    private String labelCorrelacao;
    public static Vector correlacao = new Vector();
    static InterfaceLeituraClimaticos ifclim;
    static InterfaceLeituraConsumo ilcons;
    static InterfacerLeituraSocioEconomico ilse;
    String s;
    static FileWriter writer;
    static PrintWriter saida;
    public static String path = /*"C:\\Documents and Settings\\J. Gabriel Lima\\Desktop\\ArqCorrelação.txt";//*/ PredictPropriedades.getCaminhoArquivoCorrelacao() + "\\" + "ArqCorrelação.txt";
    public static boolean ispadrao;
    public static Locale locale = new Locale("pt", "BR");
    public static NumberFormat nf = NumberFormat.getNumberInstance(locale);

    public RealizaCorrelacao(String s, boolean padrao) {
        this.s = s;
        ispadrao = padrao;
        try {
            ifclim = new ControleLeitura();
            ilcons = new ControleLeitura();
            ilse = new ControleLeitura();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        fazCorrelacao(s);
        go();
        Action.AbrirRedeBayesiana();
        ifclim = null;
        ilcons = null;
        ilse = null;
    }

    public void go() {
        VerificaBaseDados VBD = new VerificaBaseDados();
        f = new File(PredictPropriedades.getCaminhoDiretorioXML() + "\\" + s.replace("ó", "o").replace("ô", "o").replace("-", "") + ".xml");
        if (!VBD.foiModificado) {
            int escolha = JOptionPane.showOptionDialog(null, "A base de dados foi modificada. Deseja Atualizar a estrutura da Rede Bayesiana?", "Atenção", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]{"Sim", "Não"}, null);
            switch (escolha) {
                case 0:
                     {
                        JOptionPane.showMessageDialog(null, "Essa operação pode durar alguns minutos", "", JOptionPane.INFORMATION_MESSAGE);
                        refazAprendizagem();
                    }
                    break;
                case 1:
                    utilizaArquivo();
                    break;
            }
        } else {
            utilizaArquivo();
        }
    }

    // quando a base de dados não foi modificada
    public void utilizaArquivo() {
        if (f.exists()) {
            AbrirXML abxml = new AbrirXML(f);
            this.rb = AbrirXML.getRedeBaysiana();
        } else {
            k2 = new K2(PredictPropriedades.getCaminhoCorrelacao());
            this.rb = k2.getRedeBayesiana();
            new GravaXML(this.s, k2.getRedeBayesiana());
        }
    }

    public static RedeBayesiana getRb() {
        return rb;
    }

    public static void setRb(RedeBayesiana rb) {
        RealizaCorrelacao.rb = rb;
    }

    private static void gravarArquivoCorrelacao(String[][] matrizFinal, String[] nomes) {
        try {


//            System.out.println("/*******************************************************************");
//            for (int i = 0; i < matrizFinal.length; i++) {
//                for (int j = 0; j < matrizFinal[0].length; j++) {
//                    System.out.print(matrizFinal[i][j] + "\t");
//                }
//                System.out.println("");
//            }
//            System.out.println("/*******************************************************************");


            writer = new FileWriter(new File(path), false);
            saida = new PrintWriter(writer, true);

            String[] noms = corrigeNomes(nomes);

//            for (int t = 0; t < noms.length; t++) {
//                System.out.print(noms[t] + "\t");
//            }
            for (int i = 0; i < noms.length; i++) {
                saida.write(noms[i] + "\t");
            }
            saida.println();

            for (int j = 0; j < matrizFinal.length - 6; j++) {
                for (int k = 0; k < matrizFinal[0].length; k++) {
                    saida.write(matrizFinal[j][k] + "\t");

                }
                saida.println();
            }


        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private static int retornaMenor(Vector[] marizFinal) {
        int resultado = 1000;
        for (Vector v : marizFinal) {
            if (v.size() < resultado) {
                resultado = v.size();
            }
        }
//        System.out.println("indice menor: " + resultado);
        return resultado;
    }

    private static int retornaMaior(Vector[] marizFinal) {

        int resultado = 0;

        for (Vector v : marizFinal) {
            if (v.size() > resultado) {
                resultado = v.size();
            }
        }
        // System.out.println("indice maior: " + resultado);
        return resultado;
    }

    public void refazAprendizagem() {
        //   System.out.println("foi pelo caminho modificado");

        k2 = new K2(PredictPropriedades.getCaminhoArquivoCorrelacao() + "\\ArqCorrelação.txt");
        this.rb = k2.getRedeBayesiana();

        new GravaXML(s, this.rb);

    }

    public static void fazCorrelacao(String s) {
        try {
            if (ispadrao) {
                AbreXMLCorrelacaoPadrao.abrirXmlCorrelacao();
                correlacao = BackCorrelacaoGUI.getCorrelacoesPadrao();
            } else {
                AbreXMLCorrelacao.abrirXmlCorrelacao();
                correlacao = BackCorrelacaoGUI.getCorrelacoes();
            }

            System.out.println("S: " + s);
            CorrelacaoBean cb = null;
            for (Object correc : correlacao) {

                if (((CorrelacaoBean) correc).getNome().equalsIgnoreCase(s)) {
                    cb = ((CorrelacaoBean) correc);
                    break;
                }
            }

            String[] nomes = cb.getAtrib();
            for (String ss : nomes) {
                System.out.print(ss + "\t");
            }
            Vector[] marizFinal = new Vector[nomes.length];

            String matrizCorrec1[][] = null;
            String cabecalho[] = null;
            int id = 0;
            String anoDeInicio = getAnoDeInicio();
            System.out.println("anoDeInicio: " + anoDeInicio);

            for (int i = 0; i < marizFinal.length; i++) {
                marizFinal[i] = new Vector();
            }


            String[] aux = nomes[0].split("-");
            if (aux[1].trim().equalsIgnoreCase("cl")) {


                id = 1;
                matrizCorrec1 = ifclim.getPlanilhaClimaticos();
                cabecalho = ifclim.getCabecalhoPlanilhaClimaticos();
            //break;
            } else if (aux[1].trim().equalsIgnoreCase("se")) {
                id = 2;
                matrizCorrec1 = ilse.getPlanilhaSocioEconomico();
                cabecalho = ilse.getCabecalhoPlanilhaSocioEconomico();
            // break;
            }

//            for (int i = 0; i < cabecalho.length; i++) {
//                System.out.print(cabecalho[i] + "\t");
//            }
//            System.out.println("");
//            System.out.println("Numero de Atrib: " + marizFinal.length);
//            System.out.println("matrizCorrec1[j]: " + matrizCorrec1[0].length);
//            System.out.println("matrizCorrec1.length: " + matrizCorrec1.length);
//            System.out.println("cabecalho.length: " + cabecalho.length);
//            System.out.println("Passou 1");
//            for (int t = 0; t < nomes.length; t++) {
//                System.out.print(nomes[t] + "\t");
//            }
//            System.out.println("");

            int i;
            for (i = 0; i < nomes.length; i++) {
                String nome = nomes[i].split("-")[0];
                int indice = retornaIndice(nome, cabecalho);
//                    System.out.println("indice :" + indice);
                if (indice != 0) {
                    for (int j = 0; j < matrizCorrec1.length; j++) {
                        marizFinal[i].add(matrizCorrec1[j][indice]);
                    }

                } else {
//                    System.out.println("**************: "+getTipoConsumo(nomes[i].split("-")[1]));
                    cabecalho = ilcons.getCabecalhoPlanilhaConsumo(getTipoConsumo(nomes[i].split("-")[1]));
                    matrizCorrec1 = ilcons.getPlanilhaConsumoCorrec(getTipoConsumo(nomes[i].split("-")[1]));
                    indice = retornaIndice(nome, cabecalho);
                    if (indice != 0) {
                        for (int j = 0; j < matrizCorrec1.length; j++) {
//                            System.out.println("- " + matrizCorrec1[j][0]);
                            if (Integer.parseInt(matrizCorrec1[j][0].split("/")[1]) >= Integer.parseInt(anoDeInicio)) {
//                            System.out.println("-- " + matrizCorrec1[j][0]);
                                marizFinal[i].add(matrizCorrec1[j][indice]);
                            }
                        }
                    }
                }
            }
//
            System.out.println("****************************************************");

            for (int b = 0; b < marizFinal.length; b++) {
                System.out.println(marizFinal[b]);
            }
            System.out.println("****************************************************");

            int tamanhoDados = retornaMenor(marizFinal);
            System.out.println(""+tamanhoDados);
            String matrizDadosFinais[][] = new String[tamanhoDados][marizFinal.length + 1];
            for (int j = 1; j < marizFinal.length + 1; j++) {
                for (int k = 0; k < matrizDadosFinais.length; k++) {
                    if (!String.valueOf(marizFinal[j - 1].get(k)).equalsIgnoreCase(null) || !String.valueOf(marizFinal[j - 1].get(k)).equalsIgnoreCase("não aferido!")) {
                        matrizDadosFinais[k][j] = String.valueOf(marizFinal[j - 1].get(k)).replace(".", "").replace(",", ".");
                        System.out.println(""+matrizDadosFinais[k][j]);
                    }
                }
            }

            System.out.println("AQUI");
            matrizDadosFinais = validaDados(matrizDadosFinais);
            System.out.println("Aqui2");
            gravarArquivoCorrelacao(matrizDadosFinais, nomes);
            System.out.println("Aqui3");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String corrigeValores(String val) {
        boolean temVirgula = false;
        String result;

        for (int i = 0; i < val.length(); i++) {
            if (val.charAt(i) == ',') {
                temVirgula = true;
                break;
            }
        }

        if (temVirgula) {
            result = val.replace(".", "").replace(",", ".");
        } else {
            result = val.replace(".", "");
        }

        return result;

    }

    private static String[][] validaDados(String[][] matrizDadosFinais) {

        String[][] result;
        Locale locale = new Locale("pt", "BR");
        Locale.setDefault(locale);
        NumberFormat nf = NumberFormat.getNumberInstance(locale);
        int linhasValidas = 0;
        l:
        for (int i = 0; i < matrizDadosFinais.length; i++) {
            for (int j = 1; j < matrizDadosFinais[0].length; j++) {
                try {
                    nf.parse(matrizDadosFinais[i][j]).doubleValue();

                } catch (Exception e) {
//                    e.printStackTrace();
                    break l;
                }
            }
            linhasValidas++;
        }
//        System.out.println("" + linhasValidas);
        result = new String[linhasValidas][matrizDadosFinais[0].length];

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                result[i][j] = matrizDadosFinais[i][j];
            }
        }
        return result;
    }

    public static String[] corrigeNomes(String[] nomes) {

        String[] newNomes = new String[nomes.length + 1];
        newNomes[0] = "Período";
        for (int i = 1; i <= nomes.length; i++) {
            newNomes[i] = nomes[i - 1].split("-")[0].trim();
        }

        return newNomes;
    }

    private static String getTipoConsumo(String tipo) {
//        System.out.println("TIPO CONSUMO");
        String resultado = "";
        try {

//            System.out.println("Tipo Procurado: " + tipo);
            String[] tipos = ilcons.getTipoConsumo();


            for (int i = 0; i < tipos.length; i++) {
//                System.out.println("TIPO: " + tipos[i]);
                if (tipos[i].toUpperCase().contains(tipo.toUpperCase())) {
//                    System.out.println("" + tipos[i]);
                    resultado = tipos[i];
                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;

    }

    private static int retornaIndice(String nome, String[] vetor) {

//        for (int i = 0; i < vetor.length; i++) {
//            System.out.print(vetor[i] + "\t");
//        }
//        System.out.println("nome: " + nome);

        int resultado = 0;
        for (int i = 0; i < vetor.length; i++) {
//             System.out.println("Vetor[" + i + "]: " + vetor[i] + " Nome: " + nome);
            if (vetor[i].trim().equalsIgnoreCase(nome.trim())) {
//                  System.out.println("IGUAL");
                resultado = i;
                break;
            }
        }
        return resultado;
    }

    private static String getAnoDeInicio() {
        String resultado = "";
        try {
            System.out.println("");
            String MatrizConsumo[][] = ilcons.getPlanilhaConsumo("Interligado CATIVO");
            String MatrizClimaticos[][] = ifclim.getPlanilhaClimaticos();
            String MatrizSocioEconomico[][] = ilse.getPlanilhaSocioEconomico();

            loop:
            for (int i = 0; i < MatrizConsumo.length - 2; i++) {
                if (!MatrizConsumo[i][0].equalsIgnoreCase("TOTAL")) {
                    String ano = MatrizConsumo[i][0].split("/")[1];
                    for (int j = 0; j < MatrizSocioEconomico.length - 2; j++) {
                        String ano2 = MatrizSocioEconomico[j][0].split("/")[1];

                        for (int k = 0; k < MatrizClimaticos.length - 2; k++) {
                            String ano3 = MatrizClimaticos[k][0].split("/")[1];

                            if (ano.equalsIgnoreCase(ano2) && ano2.equalsIgnoreCase(ano3)) {

//                                System.out.println("Ano: " + ano + " Ano2: " + ano2 + " Ano3: " + ano3);
                                resultado = ano;
                                break loop;
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
//            e.printStackTrace();
        }

        //  System.out.println("RESULTADO: " + resultado);
        return resultado;

    }

    public static void main(String[] args) {

        PredictPropriedades.leProperties();


    }
}
