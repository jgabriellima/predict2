/*
 * K2.java
 *
 * Created on 17 de Junho de 2006, 18:20
 *
 */
package br.ufpa.lprad.predict.redebayesiana.aprendizado;

import br.ufpa.lprad.predict.redebayesiana.espacoCombinacional.EspacoCombinacional;
import br.ufpa.lprad.predict.redebayesiana.io.InterfaceTXT;
import java.io.File;
import java.util.Vector;

/**
 *
 * @author Gabriel
 */
public class K2 {

    public static Vector variaveisOrdenadas;
    public static RedeBayesiana rb;
    public static Discretizar discretizar;
    public static String[][] Matriz;
    Vector precedentes;
    double[] Resultado;

    public K2(String caminho) {

        montaEstrutura(caminho);

        rb = new RedeBayesiana();

        rb.setVertices(variaveisOrdenadas);
        rb.setNVertices(variaveisOrdenadas.size());

        iniciarAprendizagem();

    }

    public void iniciarAprendizagem() {

        for (int i = 0; i < variaveisOrdenadas.size(); i++) {
            Vertice vertice_i = (Vertice) variaveisOrdenadas.elementAt(i);
            System.out.println("VERTICE: "+vertice_i.getNome());

            double POld = AuxiliarK2.semPais(vertice_i, i);
             System.out.println("POld: "+POld);


            precedentes = AuxiliarK2.precedentes(vertice_i, variaveisOrdenadas);

            EspacoCombinacional espComb = new EspacoCombinacional(precedentes);

            Resultado = new double[espComb.comb.length];

            Vector[] precedentesEspComb = espComb.comb;
            int esp = 0;
            if (precedentes.size() != 0) {

//                System.out.println("precedentesEspComb.length: "+precedentesEspComb.length);

                for (esp = 0; esp < precedentesEspComb.length; esp++) {
                    int aux = 1;
                    for (int j = 0; j < precedentesEspComb[esp].size(); j++) {
                        aux *= ((Vertice) precedentes.elementAt(j)).getNEstados();
                    }

                    Vector[] COMB = null;

                    COMB = AuxiliarK2.retornaCombinacoes(AuxiliarK2.retornaCasos((Vertice) precedentesEspComb[esp].elementAt(0)), AuxiliarK2.retornaCasos(vertice_i));

                    for (int j = 1; j < precedentesEspComb[esp].size() - 1; j++) {
                        COMB = AuxiliarK2.retornaCombinacoes(COMB, AuxiliarK2.retornaCasos((Vertice) precedentesEspComb[esp].elementAt(j)));
                    }

//                    System.out.println("Num de Combinação: "+COMB.length);
                    double aijk_produt = 1;
                    for (int j = 0; j < COMB.length; j++) {
                        aijk_produt *= AuxiliarK2.fatorial(COMB[j].size());
                    }
                    int[] Nij_todos = AuxiliarK2.retornaNij(precedentesEspComb[esp], AuxiliarK2.retornaCasos(vertice_i));



                    double resultado = 1;
                    for (int j = 0; j < Nij_todos.length; j++) {
                        resultado *= (AuxiliarK2.fatorial(vertice_i.getNEstados() - 1) / AuxiliarK2.fatorial(Nij_todos[j] + vertice_i.getNEstados() - 1));
                    }
                    resultado *= aijk_produt;
                    Resultado[esp] = resultado;
                }
            }
            if (precedentes.size() != 0) {
                int indice = AuxiliarK2.retornaIndicePai(Resultado);
                
                Vector vetorPais = precedentesEspComb[indice];

                for (int g = 0; g < vetorPais.size(); g++) {
                    ((Vertice) vetorPais.elementAt(g)).setFilhos(vertice_i);
                }

                vertice_i.setNFilhos(vertice_i.getFilhos().size());
                vertice_i.setPais(vetorPais);
                vertice_i.setNPais(vertice_i.getPais().size());
            }
        }// variaveisOrdenadas.size();

        System.out.println("TERMINOU");
//        AuxiliarK2.calculaProbabilidadesMarginais();
//        AuxiliarK2.calculaProbabilidadesCondicionais();
//        pede_pra_sair();

    // soresultados();
    }

    void soresultados() {
        for (int i = 0; i < Resultado.length; i++) {
            System.out.println("Resultado[" + i + "]: " + Resultado[i]);
        }
        System.out.println("MAIOR: " + AuxiliarK2.retornaIndicePai(Resultado));
    }

    void pede_pra_sair() {
        System.out.println("PEDE p sair: "+rb.getVertices().size());
//        Vector<Vertice> vector = rb.getVertices();
//        for (int i = 0; i < vector.size(); i++) {
//        System.out.println("ENTOUR");
//
//            Vector vpais = ((Vertice) vector.elementAt(i)).getPais();
//            Vector vfilhos = ((Vertice) vector.elementAt(i)).getFilhos();
//            System.out.println("vertice:  " + ((Vertice) vector.elementAt(i)).getNome());
//            for (int j = 0; j < vpais.size(); j++) {
//                System.out.println("pais: " + ((Vertice) vpais.elementAt(j)).getNome());
//
//            }
//            for (int j = 0; j < vfilhos.size(); j++) {
//                System.out.println("filhos: " + ((Vertice) vfilhos.elementAt(j)).getNome());
//            }
//
////            double[] prob = AuxiliarK2.retornaProbabilidade(((Vertice) vector.elementAt(i)));
//            for (int j = 0; j < vector.get(i).getProbabilidade().length; j++) {
//                System.out.println("Faixa: " + j + " - " + vector.get(i).getProbabilidade()[j]);
//            }
//
////            double[][] probCond = vector.get(i).getTabProbCond();
////            System.out.println("COLUNAS: " + probCond[0].length);
////            System.out.println("LINHAS: " + probCond.length);
////            for (int ii = 0; ii < probCond.length; ii++) {
////                for (int j = 0; j < probCond[0].length; j++) {
////                    System.out.print(probCond[ii][j] + "\t");
////                }
////                System.out.println("");
////            }
//
//            System.out.println("");

//        }

    }

    public static RedeBayesiana getRedeBayesiana() {
        return rb;
    }

    public static void setRedeBayesiana(RedeBayesiana rbs) {
        rb = rbs;
    }

    public static void main(String[] args) {

//        new K2("C:\\Arquivos de programas\\Celpa\\Predict\\arquivos\\correlações\\ArqCorrelação.txt");
//             new K2("C:\\Documents and Settings\\Gabriel\\Desktop\\ArqCorrelação1.txt");
//         new K2("C:\\Documents and Settings\\Gabriel\\Desktop\\teste.txt");
    }

    public static void montaEstrutura(String caminho) {
        variaveisOrdenadas = new Vector();
        // obtém o caminho do arquivo
        File arq = new File(caminho);

        // converte em tabela e discretiza
        InterfaceTXT inter = new InterfaceTXT(arq);
        discretizar = new Discretizar(inter.dataToTable());

        // matriz de valores discretos
        Matriz = Discretizar.ValoresDiscretos();

        Vector estados = null;
        Vertice vertice = null;

        // cria estrutura dos vértices
        for (int i = 0; i < discretizar.getN_colunas(); i++) {//1                                                  //System.out.println("Nome: " +nome);
            estados = new Vector();
            vertice = new Vertice();

            String nome = Matriz[0][i];
//            System.out.println("NOME: "+nome);
            vertice.setNome(nome);
            for (int j = 1; j < (Matriz.length); j++) {
                String estado = Matriz[j][i];
                if (!estados.contains(estado)) {
                    estados.add(estado);
                }
            }
            String[] aux = new String[estados.size()];

            for (int a = 0; a < estados.size(); a++) {

                aux[a] = (String) estados.get(a);
            }

            vertice.setEstados(aux);
            vertice.setNEstados(estados.size());

            variaveisOrdenadas.add(vertice);
            aux = null;
        }//1
    }
}