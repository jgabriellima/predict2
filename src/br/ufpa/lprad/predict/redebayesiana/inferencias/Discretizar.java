/*
 * Discretizar.java
 *
 * **Classe que discretiza os dados de um atributo, 
 * ***caso este tenha mais de 15 registros de valores diferentes!!
 *
 * Created on 17 de Maio de 2005, 10:38
 */
package br.ufpa.lprad.predict.redebayesiana.inferencias;

import br.ufpa.lprad.predict.correlacao.discretizacao.Faixa;
import br.ufpa.lprad.predict.gui.window.correlacao.backstage.BackDiscretizacaoGUI;
import java.io.Serializable;
import java.text.DecimalFormat;
import javax.swing.JTable;

public class Discretizar implements Serializable {

    //referencias a outras classes
    Dados dad;
    MetaDados m;
    //variaveis utilizadas
    double[][] valor, //valor cuja faixa deseja-se saber qual é a faixa;
            fDiscretas, //valor da faixa;
            limInferior, //valores inferiores de faixas;
            limSuperior, //valores superiores de faixas;
            valFaixas, //armazena todas as faixas do atributo;
            valFaixasSup, //armazena todas as faixas superiores do atributo;
            valFaixasInf;                   //armazena todas as faixas inferiores do atributo;
    double[] w, //largura da faixa;
            fxDisc, //vetor que define quais faixas sao discretas(se 0 e continuo,se 1 e discreto);
            maior;                             //menor valor do atributo
    public static JTable tabela;
    double[] menor;
    int nFaixas = 5;                             //numero total de faixas do atributo
    public static String[][] saida_faixas;

    /** Creates a new instance of Discretizar */
    public Discretizar(Dados z, MetaDados f) {
        // System.out.println("Classe Discretizar");
        dad = z;
        m = f;

        valor = new double[m.getQntdAtrib()][dad.getEntradas()];

        menor = new double[m.getQntdAtrib()];

        maior = new double[m.getQntdAtrib()];

        w = new double[m.getQntdAtrib()];

        valFaixasSup = new double[m.getQntdAtrib()][];

        valFaixasInf = new double[m.getQntdAtrib()][];

        saida_faixas = new String[m.getQntdAtrib()][];

        inicializa();

    }

    private void inicializa() {

        for (int i = 0; i < m.getQntdAtrib(); i++) {
            Faixa[] fxs = BackDiscretizacaoGUI.atribDisc[i].getFaixas();
            valFaixasInf[i] = new double[fxs.length];
            valFaixasSup[i] = new double[fxs.length];
            saida_faixas[i] = new String[fxs.length];
        }
    }

    //converte os valores do atributo de string para double
    public void Converte(int z) {

//        for (int j = 0; j < dad.getEntradas() - 1; j++) {
//            valor[z][j] = Double.parseDouble(dad.getDado(z, j));
////        System.out.println("valor["+z+"]["+j+"]: "+valor[z][j]);
//        }
//        VMaior(z);
//        VMenor(z);
//        DefineLargFaixa(z);
        defineFxDisc(z);
    }

    //encontra o maior valor do atributo
    public void VMaior(int z) {
        maior[z] = valor[z][0];
        for (int j = 0; j < dad.getEntradas() - 1; j++) {
            if (maior[z] < valor[z][j]) {
                maior[z] = valor[z][j];
            }
        }
//     System.out.println("maior["+z+"]="+maior[z]);
    }

    //encontra o menor valor do atributo
    public void VMenor(int z) {
        menor[z] = maior[z];
        for (int j = 0; j < dad.getEntradas() - 1; j++) {
            if (menor[z] > valor[z][j]) {
                menor[z] = valor[z][j];
            }
        }
//    System.out.println("menor["+z+"]="+menor[z]);
    }

    //encontra a largura de cada faixa do atributo
    public void DefineLargFaixa(int z) {
        //largura da faixa
        w[z] = (maior[z] - menor[z]) / nFaixas;
//     System.out.println("w["+z+"]"+w[z]);
    }

    //metodo que define e armazena todas as faixas discretas superiores dos atributos na matriz valFaixasSup
    //e todas as faixas discretas inferiores dos atributos na matriz valFaixasInf
    public void defineFxDisc(int j) {

        Faixa[] fxs = BackDiscretizacaoGUI.atribDisc[j].getFaixas();

        for (int i = 0; i < fxs.length; i++) {
            valFaixasInf[j][i] = fxs[i].getLimiteInferior();
            valFaixasSup[j][i] = fxs[i].getLimiteSuperior();
        }


    }
//    public void defineFxDisc(int j) {
//        int count = 1;
//
//        for (int q = 0; q < nFaixas; q++) {
//
//            valFaixasSup[j][q] = 0.0;
//        }
//
//        valFaixasInf[j][0] = menor[j];
//        valFaixasSup[j][0] = valFaixasInf[j][0] + w[j];
//
////           System.out.println("valFaixasInf["+j+"]["+0+"]="+valFaixasInf[j][0]);
////           System.out.println("valFaixasSup["+j+"]["+0+"]="+valFaixasSup[j][0]);
//
//        for (int i = 1; i < nFaixas; i++) {
//            valFaixasInf[j][i] = valFaixasSup[j][i - 1];
//            valFaixasSup[j][i] = valFaixasInf[j][i] + w[j];
//
////         System.out.println("valFaixasInf["+j+"]["+i+"]="+valFaixasInf[j][i]);
////         System.out.println("valFaixasSup["+j+"]["+i+"]="+valFaixasSup[j][i]);
//        }
//    }

    //retorna qual a faixa do atributo
    public int getFx(double valor, int atrib) {
        int aux = 0;

        for (int i = 0; i < BackDiscretizacaoGUI.atribDisc[atrib].getNFaixas(); i++) {
            if ((valor > valFaixasInf[atrib][i]) && (valor >= valFaixasSup[atrib][i])) {
                aux = i;
                break;
            }
        }
        return (aux);
    }

    public void normalizaFaixa(int col) {
        String auxDado, valorFx;

        for (int i = 0; i < dad.getEntradas() - 1; i++) {
            for (int j = 0; j <BackDiscretizacaoGUI.atribDisc[col].getNFaixas(); j++) {
                auxDado = dad.getDado(col, i);
                //System.out.println("auxDado ="+auxDado);
                if (((Double.parseDouble(auxDado)) >= (getValInf(col, j))) &&
                        ((Double.parseDouble(auxDado)) < (getValSup(col, j))) ||
                        ((Double.parseDouble(auxDado)) > (getValInf(col, j))) &&
                        ((Double.parseDouble(auxDado)) <= (getValSup(col, j)))) {

                    valorFx = String.valueOf(((getValSup(col, j) - getValInf(col, j)) / 2) + getValInf(col, j));

                    dad.setDado(col, i, valorFx);

                }

            }
        }
    }

    //retorna a faixa inferior do atributo
    public double getValInf(int atrib, int fx) {
        return valFaixasInf[atrib][fx];
    }

    //retorna a faixa superior do atributo
    public double getValSup(int atrib, int fx) {
        return valFaixasSup[atrib][fx];
    }

    public void retornaFx() {
        DecimalFormat Converte = new DecimalFormat("0.00");
        for (int i = 0; i < m.getQntdAtrib(); i++) {
            String ent;
            for (int fx = 0; fx < BackDiscretizacaoGUI.atribDisc[i].getNFaixas(); fx++) {
                ent = getValInf(i, fx) + " - " + getValSup(i, fx);
                saida_faixas[i][fx] = ent;
            }
        }
    }

    public static String getFxInf(int atrib, int fx) {

        String aux = null;
        try {
            aux = saida_faixas[atrib][fx];
        } catch (ArrayIndexOutOfBoundsException ex) {
            aux = saida_faixas[atrib - 2][fx];
        //      System.out.println("passou atrib: "+atrib+" fx: "+fx);
        }
        return aux;
    }
}
        
        
    

