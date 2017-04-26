/*
 * AuxiliarK2.java
 *
 * Created on 9 de Setembro de 2008, 19:34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.redebayesiana.aprendizado;

import br.ufpa.lprad.predict.gui.window.correlacao.backstage.BackDiscretizacaoGUI;
import br.ufpa.lprad.predict.propriedades.PredictPropriedades;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Vector;

/**
 *
 * @author Gabriel
 */
public class AuxiliarK2 {

    private static int faixas = 5;

    public static int retornaIndicePai(double[] resultado) {
        int i;
        double maior = 0;
        for (i = 0; i < resultado.length; i++) {
            if (maior < resultado[i]) {
                maior = resultado[i];
            }
        }
        p:
        for (i = 0; i < resultado.length; i++) {
            if (resultado[i] == maior) {
                break p;
            }

        }
        return i;
    }

    public static Vector[] retornaCasos(Vertice vertice_i) {
        // cria um array de vector com o numero de estados do vertice
        // coloquei 5 pois quero que as probabilidades sejam montadas corretamente na matriz

        // obtem o indice do vertice no vertor geral
        if (K2.variaveisOrdenadas == null) {
            K2.montaEstrutura(PredictPropriedades.getCaminhoCorrelacao());
        }
        int id = K2.getRedeBayesiana().getVertices().indexOf(vertice_i);

        Vector[] resultado = new Vector[BackDiscretizacaoGUI.atribDisc[id].getNFaixas()];//faixas];//vertice_i.getNEstados()];

        for (int i = 0; i < resultado.length; i++) {
            resultado[i] = new Vector();
        }
        for (int i = 0; i < /*vertice_i.getNEstados()*/ /*faixas*/ resultado.length; i++) {
            try {
                String aux = vertice_i.getEstados()[i];
                int indiceEstado = Integer.valueOf(vertice_i.getEstados()[i]);
                for (int j = 1; j < K2.Matriz.length; j++) {
                    if (aux.equalsIgnoreCase(K2.Matriz[j][id])) {
                        Integer jj = new Integer(j);
                        resultado[indiceEstado].add(jj);
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
//                e.printStackTrace();
            }

        }

        return resultado;
    }

    public static Vector[] retornaCombinacoes(Vector[] vertice_precedente, Vector[] vertice_atual) {
//        System.out.println("\ntam: "+vertice_atual.length*vertice_precedente.length);
        Vector[] vetorCombinacoes = new Vector[vertice_atual.length * vertice_precedente.length];


        int cont = 0;
        for (int i = 0; i < vertice_precedente.length; i++) {//1
            for (int j = 0; j < vertice_atual.length; j++) {//2

                vetorCombinacoes[cont] = new Vector();

                for (int k = 0; k < vertice_atual[j].size(); k++) {//3
                    if (vertice_precedente[i].contains(vertice_atual[j].elementAt(k))) {
                        vetorCombinacoes[cont].add(vertice_atual[j].elementAt(k));
                    }
                }//3

                if (vetorCombinacoes[cont].size() != 0) {
                    cont++;
                }

            }//2
        }//1
        int contaux = 0;
        for (int i = 0; i < vetorCombinacoes.length; i++) {
            if (vetorCombinacoes[i] != null) {
                contaux++;
            }
        }
        Vector[] result = new Vector[contaux];
        for (int i = 0; i < vetorCombinacoes.length; i++) {
            if (vetorCombinacoes[i] != null) {
                result[i] = vetorCombinacoes[i];
            }
        }
        //  return vetorCombinacoes;
        return result;
    }

    public static double getMaior(double[] c) {
        double aux = 0;
        for (int i = 0; i < c.length; i++) {
            if (c[i] > aux) {
                aux = c[i];
            }
        }
        return aux;

    }

    public static Vector retornaCombinacao(Vector xi, Vector xii) {

        Vector maior = new Vector();
        Vector menor = new Vector();
        Vector comb = new Vector();

        // verifica o maior vetor
        if (xi.size() > xii.size()) {
            maior = xi;
            menor = xii;
        } else {
            maior = xii;
            menor = xi;
        }

        for (int j = 0; j < maior.size(); j++) {
            if (menor.contains(maior.elementAt(j))) {
                comb.add(maior.elementAt(j));
            }
        }
        return comb;

    }

    public static int[] retornaNij(Vector vetor_precedentes, Vector[] vertice_Atual) {
        Vector[] combinacoes;

        combinacoes = retornaCasos((Vertice) vetor_precedentes.lastElement());

        for (int i = 0; i < vetor_precedentes.size() - 1; i++) {
            combinacoes = retornaCombinacoes(combinacoes, retornaCasos((Vertice) vetor_precedentes.elementAt(i)));
        }

        int[] resultado = new int[combinacoes.length];

        int cont = 0, aux = 0;
        for (int i = 0; i < combinacoes.length; i++) {
            for (int j = 0; j < vertice_Atual.length; j++) {
                aux += retornaCombinacao(combinacoes[i], vertice_Atual[j]).size();
            }
            resultado[cont] = aux;
            cont++;
            aux = 0;
        }

        return resultado;
    }

    public static Vector precedentes(Vertice verticeI, Vector vetor) {
        Vector precedentes = new Vector();
        int cont = 0;
        Vertice vertice = (Vertice) vetor.elementAt(cont);

        while (!vertice.getNome().equalsIgnoreCase(verticeI.getNome())) {
            precedentes.add(vertice);
            cont++;
            vertice = (Vertice) vetor.elementAt(cont);
        }
        return precedentes;
    }

    public static double semPais(Vertice vertice_i, int id) {
        double resultado;
        /////////////////calcula aijk  e Nij////////////////////
        Vector[] freq = new Vector[vertice_i.getNEstados()];

        for (int teste = 0; teste < freq.length; teste++) {
            freq[teste] = new Vector();
        }
        int nij = 0;
        double aijk = 1;

        for (int caso = 0; caso < vertice_i.getNEstados(); caso++) {

            for (int j = 1; j < K2.Matriz.length; j++)//  for (int j = 0; j <Discretizar.n_linhas; j++)
            {

                String estado = K2.Matriz[j][id];
                if (vertice_i.getEstados()[caso].equalsIgnoreCase(estado)) {
                    Integer jj = new Integer(j);

                    freq[caso].add(jj);
                }
            }
            nij += freq[caso].size();  /// calcula Nij
        }
        for (int a = 0; a < freq.length; a++) {
            aijk *= fatorial(freq[a].size()); /// calcula o fatorial do aijk
        }
        ////////////////////////////////////////////////

        resultado = (fatorial(vertice_i.getNEstados() - 1) / fatorial(nij + vertice_i.getNEstados() - 1)) * aijk;
        return resultado;
    }

    public static double[][] retornaTabela(Vertice vertice) {

        /*
         * Formata os valores numericos
         */
        Locale locale = new Locale("pt", "BR");
        Locale.setDefault(locale);
        NumberFormat nf = NumberFormat.getNumberInstance(locale);

        Vector pais = new Vector();
        pais = vertice.getPais();

        int total = 1;
        Vector[] comb = null;
        // vetor que contem a frequencia de cada estado
        // comb[nº de faixas]
        comb = retornaCasos(vertice);

        //loop que fará as combinações entre o vertice estudado e seus respectivos pais
        for (int i = 0; i < pais.size(); i++) {
            // total é o produto dos numeros de estados dos pais
            total *= ((Vertice) pais.elementAt(i)).getNEstados();
            comb = retornaCombinacoesInt(comb, retornaCasos((Vertice) pais.elementAt(i)));
        }
        int id = K2.getRedeBayesiana().getVertices().indexOf(vertice);
        /*
         * Matriz das probabilidades condicionais [produto do numeros de estados dos pais][estados do vertice]
         */
        double TabProbCond[][] = new double[total][BackDiscretizacaoGUI.atribDisc[id].getNFaixas()];//faixas];//vertice.getNEstados()];

        // se o vertice tiver pais, então faz a combinação dos estados dos pais
        if (!pais.isEmpty()) {
            Vector[] comb_pais = retornaCasos((Vertice) pais.elementAt(0));
            for (int i = 1; i < pais.size(); i++) {
                comb_pais = retornaCombinacoesInt(comb_pais, retornaCasos((Vertice) pais.elementAt(i)));
            }

            for (int j = 0; j < vertice.getNEstados(); j++) {
                for (int i = 0; i < total; i++) {
//                    System.out.println("comb[(j * comb_pais.length) + i].size(): "+((j * comb_pais.length) + i)+" -> "+comb[(j * comb_pais.length) + i].size());
                    double numerador = ((double) comb[(j * comb_pais.length) + i].size());

                    double denominador = ((double) comb_pais[i].size());

//                    System.out.println("numerador: "+numerador+"\tdenominador: "+denominador);
                    if (denominador != 0) {
                        TabProbCond[i][j] = (numerador / denominador);
                    } else {
                        TabProbCond[i][j] = 0;
                    }
//                System.out.println("TabProbCond["+i+"]["+j+"]: "+TabProbCond[i][j]);
                }
            }
        }
        return TabProbCond;

    }

    public static Vector[] retornaCombinacoesInt(Vector[] vertice_precedente, Vector[] vertice_atual) {

        Vector[] vetorCombinacoes = new Vector[vertice_atual.length * vertice_precedente.length];

        int cont = 0;
        for (int i = 0; i < vertice_precedente.length; i++) {//1
            for (int j = 0; j < vertice_atual.length; j++) {//2

                vetorCombinacoes[cont] = new Vector();

                for (int k = 0; k < vertice_atual[j].size(); k++) {//3
                    if (vertice_precedente[i] != null) {
                        if (vertice_precedente[i].contains(vertice_atual[j].elementAt(k))) {
                            vetorCombinacoes[cont].add(vertice_atual[j].elementAt(k));
                        }
                    }
                    System.gc();
                }//3
                cont++;
                System.gc();
            }//2
            System.gc();
        }//1
        return vetorCombinacoes;
    }

    public static double[] retornaProbabilidade(Vertice vertice) {
        int id = K2.getRedeBayesiana().getVertices().indexOf(vertice);
        double probabilidades[] = new double[BackDiscretizacaoGUI.atribDisc[id].getNFaixas()];//faixas];//vertice.getNEstados()];

        Vector[] casos = retornaCasos(vertice);
        for (int i = 0; i < probabilidades.length; i++) {
            probabilidades[i] = (((double) casos[i].size()) / (K2.Matriz.length));
        }
        return probabilidades;
    }

    /*
     * Método usado no final da aprendizagem da Rede ou após a leitura dos arquivos
     * para setar as probabilidades Marginais de cada vertice na rede
     */
    public static void calculaProbabilidadesMarginais() {

        K2.montaEstrutura(PredictPropriedades.getCaminhoCorrelacao());
        for (int i = 0; i < K2.getRedeBayesiana().getVertices().size(); i++) {
            double prob[] = retornaProbabilidade(K2.getRedeBayesiana().getVertices().get(i));
            K2.getRedeBayesiana().getVertices().get(i).setProbabilidade(prob);
        }
    }

    public static double fatorial(double f) {

        double var = 1;
        if (f == 0) {
            return 1;
        } else {
            while (f > 0) {
                var *= f;
                f--;
            }
        }
        return var;
    }

    public static void calculaProbabilidadesCondicionais() {
        for (int i = 0; i < K2.getRedeBayesiana().getVertices().size(); i++) {
            double prob[][] = retornaTabela(K2.getRedeBayesiana().getVertices().get(i));
            K2.getRedeBayesiana().getVertices().get(i).setTabProbCond(prob);
        }
    }
}
