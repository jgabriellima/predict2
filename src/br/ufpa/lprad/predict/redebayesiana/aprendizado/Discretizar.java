/*
 * Discretizar.java
 *
 * Created on 31 de Maio de 2006, 11:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.redebayesiana.aprendizado;

import br.ufpa.lprad.predict.propriedades.PredictPropriedades;
import br.ufpa.lprad.predict.redebayesiana.io.InterfaceTXT;
import java.io.File;
import javax.swing.JTable;

/**
 *
 * @author Gabriel
 */
public class Discretizar {

    public static JTable tabela;
    private static String[][] matriz;
    private static double valor[][];
    private static int nFaixas = 5;
    private static double[] maior;
    private static double[] menor;
    private static double[] w;
    public static double[][] //valor cuja faixa deseja-se saber qual  a faixa;
            fDiscretas, //valor da faixa;
            limInferior, //valores inferiores de faixas;
            limSuperior, //valores superiores de faixas;
            valFaixas, //armazena todas as faixas do atributo;
            valFaixasSup, //armazena todas as faixas superiores do atributo;
            valFaixasInf;                   //armazena todas as faixas inferiores do atributo;

    public Discretizar(JTable tabela) {
        this.tabela = tabela;

        valor = new double[this.tabela.getRowCount()][this.tabela.getColumnCount()];

        menor = new double[this.tabela.getColumnCount()];

        maior = new double[this.tabela.getColumnCount()];

        w = new double[this.tabela.getColumnCount()];

        valFaixasSup = new double[this.tabela.getColumnCount()][(int) nFaixas];

        valFaixasInf = new double[this.tabela.getColumnCount()][(int) nFaixas];

        for (int i = 0; i < this.tabela.getColumnCount(); i++) {
            //System.out.print(tabela.getColumnName(i)+"\t");
            Converte(i);
        //System.out.println();
        }

//        System.out.println();
//        for(int i=0; i<this.tabela.getRowCount(); i++){
//            for(int j=0; j<this.tabela.getColumnCount()-1; j++){
//                System.out.print(valor[i][j]+"\t");
//            }
//            System.out.println();
//         }


        matriz = ValoresDiscretos();

//        for(int i=0; i<this.tabela.getRowCount(); i++){
//            for(int j=0; j<this.tabela.getColumnCount()-1; j++){
//                System.out.print(matriz[i][j]+"\t");
//            }
//            System.out.println();
//         }

    }

    public static String[][] ValoresDiscretos() {
        String[][] matrizDiscreta = new String[tabela.getRowCount()][tabela.getColumnCount()];

        for (int j = 0; j < tabela.getColumnCount(); j++) {
            matrizDiscreta[0][j] = tabela.getColumnName(j).split("-")[0];
            for (int i = 1; i < tabela.getRowCount(); i++) {
                matrizDiscreta[i][j] = String.valueOf(getFx(valor[i][j], j));
            }
        }
//        for (int j = 1; j < tabela.getColumnCount(); j++) {
//            matrizDiscreta[0][j - 1] = tabela.getColumnName(j);
//            for (int i = 1; i < tabela.getRowCount(); i++) {
//                matrizDiscreta[i][j - 1] = String.valueOf(getFx(valor[i][j - 1], j - 1));
//            }
//        }

        return matrizDiscreta;
    }

    public void Converte(int z) {

        for (int i = 0; i < tabela.getRowCount(); i++) {
            //  System.out.println("Double.parseDouble((String)tabela.getValueAt("+i+" , "+z+"): "+(String)tabela.getValueAt(i,z));
            try {
                valor[i][z] = Double.valueOf(String.valueOf(tabela.getValueAt(i, z))).doubleValue();
            } catch (NumberFormatException e) {
                if (!String.valueOf(tabela.getValueAt(i, z)).isEmpty() && !String.valueOf(tabela.getValueAt(i, z)).equalsIgnoreCase("null")) {
                    valor[i][z] = Double.valueOf(String.valueOf(tabela.getValueAt(i, z)).replace(".", "").replace(",", ".")).doubleValue();
                }
            }
        //valor[i][z-1] =nf.format(Double.valueOf("101.428.263,34".replace(".", "").replace(",", ".")).doubleValue());
        }

        Maior(z);
        Menor(z);
        DefineLarguraFaixa(z);
    }

    //encontra o maior valor do atributo
    public void Maior(int z) {
        maior[z] = 0;
        for (int j = 0; j < tabela.getRowCount(); j++) {
            if (maior[z] < valor[j][z]) {
                maior[z] = valor[j][z];
            }
        }
    //System.out.println("maior["+z+"]="+maior[z]);
    }

    //encontra o menor valor do atributo
    public void Menor(int z) {
        menor[z] = maior[z];
        for (int j = 0; j < tabela.getRowCount(); j++) {
            if (menor[z] > valor[j][z]) {
                menor[z] = valor[j][z];
            }
        }
    //System.out.println("menor["+z+"]="+menor[z]);
    }

    //encontra a largura de cada faixa do atributo
    // o z é o id do atributo
    public void DefineLarguraFaixa(int z) {
        //largura da faixa
//        System.out.println("int z: "+z);
        w[z] = (maior[z] - menor[z]) / nFaixas;
    //System.out.println("w["+z+"]= "+w[z]);
    }
    //retorna qual a faixa do atributo

    public static int getFx(double valor, int coluna) {
        double valInfFaixa = menor[coluna];
        double valSupFaixa = valInfFaixa + w[coluna];

        int i;
        for (i = 0; i < nFaixas; i++) {
            if (i != (nFaixas - 1)) {
                if (valor >= valInfFaixa && valor < valSupFaixa) {
                    break;
                }
            } else {
                if (valor >= valInfFaixa && valor <= maior[coluna]) {
                    break;
                }
            }
            valInfFaixa = valSupFaixa;
            valSupFaixa += w[coluna];
        }
        return i;
    }

    public static void defineFaixas() {
        K2.montaEstrutura(PredictPropriedades.getCaminhoCorrelacao());

        for(int i=0; i<K2.getRedeBayesiana().getVertices().size(); i++)
        {
            defineFxDisc(i);
        }


    }

    public static void defineFxDisc(int j) {
        int count = 1;

        for (int q = 0; q < nFaixas; q++) {

            valFaixasSup[j][q] = 0.0;
        }

        valFaixasInf[j][0] = menor[j];
        valFaixasSup[j][0] = valFaixasInf[j][0] + w[j];

        //   System.out.println("valFaixasInf["+j+"]["+0+"]="+valFaixasInf[j][0]);
        //   System.out.println("valFaixasSup["+j+"]["+0+"]="+valFaixasSup[j][0]);

        for (int i = 1; i < nFaixas; i++) {
            valFaixasInf[j][i] = valFaixasSup[j][i - 1];
            valFaixasSup[j][i] = valFaixasInf[j][i] + w[j];

        // System.out.println("valFaixasInf["+j+"]["+i+"]="+valFaixasInf[j][i]);
        // System.out.println("valFaixasSup["+j+"]["+i+"]="+valFaixasSup[j][i]);
        }
    }

    public int getN_colunas() {
        return tabela.getColumnCount();
    }

    public static void main(String[] args) {

////          File arq = new File("C:\\Documents and Settings\\Gabriel\\Desktop\\testeaux2.txt");
//        //File arq = new File("C:\\Documents and Settings\\Gabriel\\Desktop\\ArqCorrelação.txt");
//
//        InterfaceTXT inter = new InterfaceTXT(arq);
//
//        new Discretizar(inter.dataToTable());
    }
}