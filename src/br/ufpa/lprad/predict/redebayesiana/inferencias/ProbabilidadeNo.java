/*
 * ### Programa Celpa 1.5
 *
 * ProbabilidadeNo.java
 *
 * Created on 23 de Abril de 2005, 14:53
 */
//package br.ufpa.predictbayes.src.bayes;
package br.ufpa.lprad.predict.redebayesiana.inferencias;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

public class ProbabilidadeNo implements Serializable {

    // O q q eh isso liviane?
//    private double[][][] tabProbAux;
    public static SubMatrix[] tabProbCond;
    private int[][] qtdAtributoCond;

    // Resultado da Bayesiana
    public static double[][] P;
    int atCond, auxProd;
    // Inferencias Bayesianas
    int numInfer = 0;
    private int[] atrInf,  atrFxInf;
//    private int atrInfo, atrValInfo;
    int j;
    // Referencias Externas
    private MetaDados meta;
    private Dados dado;
    private int metaAtrib;
    int cont = -666;
    NumberFormat nf;

    public ProbabilidadeNo(MetaDados met, Dados dad) {
        meta = met;
        dado = dad;
        metaAtrib = meta.getQntdAtrib();

        for (int i = 0; i < metaAtrib; i++) {
            System.out.println("meta.getQntdFaixas(" + i + "): " + meta.getQntdFaixas(i));
        }


        atrFxInf = new int[dado.getEntradas()];
        probCondicional();
        BBN();
    }

    public ProbabilidadeNo(int[] atrinf, int[] atrfxinf, MetaDados met, Dados dad, int nInfer) {
        // Paramentros tradicionais
        meta = met;
        dado = dad;


        metaAtrib = meta.getQntdAtrib();
        atrInf = new int[atrinf.length];
        atrFxInf = new int[atrinf.length];
        numInfer = nInfer;

        //Inferencia Bayesiana
        for (int i = 0; i < atrinf.length; i++) {
            atrInf[i] = atrinf[i];
            //System.out.println("atrInf :"+atrInf[i]);
            atrFxInf[i] = atrfxinf[i];
        //System.out.println("atrFxInf:"+atrFxInf[i]);

        }

        // GO! GO! GO!
        probCondicional();
        BBN();
    //printResults();
    }

    public void probCondicional() {
        String aux;
        int sizeCond, totFaixas;
        double valorAux; // variavel pra facilitar calculos

        tabProbCond = new SubMatrix[metaAtrib];

        //1
        for (int i = 0; i < metaAtrib; i++) {       //percorre as colunas(tot_atrib)
            totFaixas = meta.getQntdFaixas(i);
            sizeCond = meta.nFaixasPais(i);
            tabProbCond[i] = new SubMatrix(totFaixas, sizeCond);
            qtdAtributoCond = new int[metaAtrib][sizeCond];

            //System.out.println("i"+i);
            //System.out.println("sizeCond="+sizeCond);
            //2
            if (meta.getQntdPais(i) > 0) {//se o atributo tiver pais
                aux = null;
                //3
                for (int j = 0; j < meta.getQntdFaixas(i); j++) {//percorre cada faixa do atributo filho (qtdRgistro=qtd_atrib(Adamo))
                    aux = meta.getFaixa(i, j);
                    //4
                    for (int k = 0; k < (dado.getEntradas() - 1); k++) {
                        //5
                        if (meta.ehDiscreto(i) == 0) {
                            //System.out.println("continuo");
                            if (aux.equals(dado.getDado(i, k))) {//Liviane:m.registro[entradas][metaAtrib]//Adamo:valor_atrib[metaAtrib][entradas]
                                //modificado para analisar valores discretos

                                tipoDados(i, j, k);
                            }
                        } else {
                            if (meta.igualaFaixa(i, k, j)) {//Liviane:m.registro[entradas][metaAtrib]//Adamo:valor_atrib[metaAtrib][entradas]
//                                System.out.println("discreto");
                                tipoDados(i, j, k);
                            }
                        }
                    }//4
                }//3
                //12

                if (meta.getQntdPais(i) > 1) {
                    setAuxProd(1);
                    //13
                    for (int j = 0; j < meta.getQntdPais(i); j++) {
                        setAuxProd(getAuxProd() * meta.getQntdFaixas(meta.getPais(i, j)));
                    //System.out.println("auxProd="+auxProd);
                    }//13
                    //14
                    //System.out.println("yyyy "+meta.getQntdFaixas(i));
                    for (int j = 0; j < meta.getQntdFaixas(i); j++) {
                        //15
                        //System.out.println("zzzzz "+getAuxProd());
                        for (int k = 0; k < getAuxProd(); k++) {
                            //16
                            if (qtdAtributoCond[i][k] != 0) {
                                //System.out.println("qtdAtributoCond["+i+"]["+k+"]: "+(double)qtdAtributoCond[i][k]);
                                valorAux = (double) tabProbCond[i].getValor(j, k) / (double) qtdAtributoCond[i][k];
                                tabProbCond[i].setValor(j, k, valorAux);
                            }//16
                            //17
                            else {
                                tabProbCond[i].setValor(j, k, (1 / (double) meta.getQntdFaixas(i)));
                            }//17
                        //System.out.println("1 - tabProbCond[ Atrib "+i+"] - ["+j+"]["+k+"]="+tabProbCond[i].getValor(j,k));
                        }//15
                    }//14
                }//12
                //18
                else {
                    //19
                    if (meta.getQntdPais(i) == 1) {
                        //20
                        for (int j = 0; j < meta.getQntdFaixas(i); j++) {
                            //21
                            for (int k = 0; k < meta.getQntdFaixas(meta.getPais(i, 0)); k++) {
                                if (tabProbCond[i].getValor(j, k) != 0) {
                                    double var_aux = (double) dado.getNumRegFxAtrib(meta.getPais(i, 0), k);
                                    if (var_aux == 0.0) {
                                        var_aux = 1;
                                    }

                                    // System.out.println("(double)dado.getNumRegFxAtrib(meta.getPais("+i+","+0+"),"+k+"): "+(double)dado.getNumRegFxAtrib(meta.getPais(i,0),k));
                                    valorAux = (double) tabProbCond[i].getValor(j, k) / var_aux;//(double)dado.getNumRegFxAtrib(meta.getPais(i,0),k);
                                    tabProbCond[i].setValor(j, k, valorAux);
                                // System.out.println("2 - tabProbCond[ Atrib "+i+"] - ["+j+"]["+k+"]="+tabProbCond[i].getValor(j,k));
                                }
                            }//21
                        }//20
                    }//19
                }//18
            }//2
        }//1
        //22
        for (int i = 0; i < metaAtrib; i++) {
            //23
            if (meta.getQntdPais(i) > 0) {
                //24
                for (int j = 0; j < meta.getQntdFaixas(i); j++) {
                    //25
                    for (int k = 0; k < meta.nFaixasPais(i); k++) {
                        if (tabProbCond[i].getValor(j, k) == 0) {
                            tabProbCond[i].setValor(j, k, (double) 0.001);
                        } else if (tabProbCond[i].getValor(j, k) == 1.00) {
                            tabProbCond[i].setValor(j, k, (double) 0.999);
                        }
                    //System.out.println("3 - tabProbCond[ Atrib "+i+"] - ["+j+"]["+k+"]="+tabProbCond[i].getValor(j,k));
                    }//25
                }//24
            }//23
        }//22
    }

    public void setAtCond(int atCond) {
        this.atCond = atCond;
    }

    public int getAtCond() {
        return this.atCond;
    }

    public int getAuxProd() {
        return this.auxProd;
    }

    public void setAuxProd(int auxProd) {
        this.auxProd = auxProd;
    }

    public void tipoDados(int i, int j, int k) {
        this.setAtCond(0);
        //6
        for (int l = (meta.getQntdPais(i) - 1); l >= 0; l--) {
            //7
            for (int n = 0; n < meta.getQntdFaixas(l); n++) {
                //8
                if (dado.getDado(meta.getPais(i, l), k).equals(meta.getFaixa(meta.getPais(i, l), n))) {
                    //9
                    if (l != meta.getQntdPais(i) - 1) {
                        setAuxProd(1);
                        //10
                        for (int t = l + 1; t < meta.getQntdPais(i); t++) {
                            setAuxProd(getAuxProd() * meta.getQntdFaixas(meta.getPais(i, t)));
                        }//10
                        setAuxProd(getAuxProd() * n);
                        this.setAtCond(getAtCond() + getAuxProd());
                    }//9
                    else {
                        this.setAtCond(getAtCond() + n);
                    }
                }//8
            }//7
        }//6

        tabProbCond[i].inc(j, getAtCond());
        //11
        if (meta.getQntdPais(i) > 1) {
            qtdAtributoCond[i][getAtCond()]++;
        // System.out.println("qtdAtributoCond["+i+"]["+atCond+"]="+qtdAtributoCond[i][atCond]);
        }//11
    }

    /**************************************************************************************/
    /**************************** Calculo da Bayesiana ************************************/
    /**************************************************************************************/
    public void BBN() {
        int teste, cont, loop, contX, auxiliar, auxProd = 0;
        double tabAux, valor;
        int[] eviden, atribLoop;
        double[][] tabProbTemp, tabProbIni;
        Locale locale = new Locale("pt", "BR");
        Locale.setDefault(locale);
        nf = NumberFormat.getNumberInstance(locale);
        // Quebrando a orientação a objetos...
        // Vou me matar mais tarde por isso
        int entradas = dado.getEntradas();
        int[] qtdFaixas = meta.meDaQntFaixas();
        int[] qtdPais = meta.meDaQntdPais();
        int[][] pais = meta.meDaTeusPais();
        int[][] num_rg_fx_atrib = dado.meDaTeuNRFA();

        tabProbIni = new double[metaAtrib][entradas];
        P = new double[metaAtrib][entradas];
        tabProbTemp = new double[metaAtrib][entradas];
        atribLoop = new int[metaAtrib];
        eviden = new int[metaAtrib];

        for (int i = 0; i < metaAtrib; i++) {
//             System.out.println("qtdFaixas[ "+i+"]"+qtdFaixas[i]);
            for (int j = 0; j < qtdFaixas[i]; j++) {

                if (meta.ehDiscreto(i) == 0) {
                    tabProbIni[i][j] = (double) num_rg_fx_atrib[i][j] / (double) (entradas - 1);
                } else {
                    tabProbIni[i][j] = (double) dado.getNumRegFxAtrib(i, j) / (double) (entradas - 1);
                }
                meta.setProb(i, j, tabProbIni[i][j]);
//             System.out.println("tabProbIni["+i+"]["+j+"]="+tabProbIni[i][j]);
            }
        }

        //Bayesian Belief Network
        //prepara os atrib evidenciados
        //1
        for (int i = 0; i < metaAtrib; i++) {
            teste = 0;
            //2
            for (int j = 0; j < numInfer; j++) {
//                System.out.println("NUM_INFERENCIA: " + numInfer);
                //3
                if (i == atrInf[j]) {
                    eviden[i] = atrFxInf[j];
                    break;
                }//3
                else {
                    teste = teste + 1;
                }
            }//2
            if (teste == numInfer) {
                eviden[i] = -1;
            }
        }//1

//        System.out.println("EVIDEN: " + eviden.length);

        //prepara os atrib_loop
        //4
//        for (int j = 0; j < metaAtrib; j++) {
//            System.out.println("eviden[" + j + "]= " + eviden[j]);
//        }
        for (int i = 0; i < metaAtrib; i++) {
            if (eviden[i] != -1) {
                atribLoop[i] = eviden[i];
            } else {
                atribLoop[i] = 0;
            }
        }//4


//        for (int j = 0; j < metaAtrib; j++) {
//            System.out.println("atribLoop[" + j + "]= " + atribLoop[j]);
//        }

//        if (numInfer > 0) {

        //início em si
        //5
        for (int i = 0; i < metaAtrib; i++) {
            //System.out.println("atributo "+i);
            //6
            if (eviden[i] == -1) { //*** por um IF e ajeitar as variáveis! :)
                //System.out.println(m.atributo[i]);
                //calculo do cont
                cont = 1;
                //7
                for (loop = 0; loop < metaAtrib; loop++) {
                    teste = 0;
                    //8
                    for (int n = 0; n < numInfer; n++) {
                        if ((loop == atrInf[n]) || (loop == i)) {
                            break;
                        } else {
                            teste = teste + 1;
                        }
                    }//8
                    if ((teste == numInfer) && (loop != i)) {
                        cont = cont * qtdFaixas[loop];
                    }
                }//7
                //9
                for (int j = 0; j < qtdFaixas[i]; j++) {
                    //10
                    for (loop = 0; loop < cont; loop++) {
                        tabAux = 1;
                        atribLoop[i] = j;
                        contX = 0;
                        //11
                        for (int n = (metaAtrib - 1); n >= 0; n--) {
                            //12
                            if ((contX == 1) && (eviden[n] == -1) && (n != i)) {
                                atribLoop[n]++;
                                contX = 0;
                            }//12
                            //13
                            if ((atribLoop[n] == qtdFaixas[n]) && (n != i)) {
                                atribLoop[n] = 0;
                                contX = 1;
                            }//13
                        }//11
                        //14
                        for (int k = 0; k < metaAtrib; k++) {
                            //15
                            if (qtdPais[k] != 0) {
                                auxiliar = 0;
                                //16
                                for (int l = (qtdPais[k] - 1); l >= 0; l--) {
                                    //17
                                    if (l == (qtdPais[k] - 1)) { //último pai
                                        //18
                                        if (eviden[pais[k][l]] != -1) { //tem o valor setado
                                            auxiliar = auxiliar + eviden[pais[k][l]];
                                        }//18
                                        else {
                                            auxiliar = auxiliar + atribLoop[pais[k][l]];
                                        }
                                    }//17
                                    //19
                                    else {
                                        auxProd = 1;
                                        //20
                                        for (int t = l + 1; t < qtdPais[k]; t++) {
                                            auxProd = auxProd * qtdFaixas[pais[k][t]];
                                        }//20
                                        if (eviden[pais[k][l]] != -1) {
                                            auxProd = auxProd * eviden[pais[k][l]];
                                        } //21
                                        else {
                                            auxProd = auxProd * atribLoop[pais[k][l]];
                                        }//21
                                        auxiliar = auxiliar + auxProd;
                                    }//19
                                }//16

                                if (k == i) { //se as que estao sendo trabalhado no momento
                                    valor = tabProbCond[k].getValor(j, auxiliar);
                                //System.out.println("valor="+valor);
                                } else if (eviden[k] != -1) {
                                    valor = tabProbCond[k].getValor(eviden[k], auxiliar);
                                } else {
                                    valor = tabProbCond[k].getValor(atribLoop[k], auxiliar);
                                }
                            //System.out.println("tabProbCond2["+k+"]["+j+"]["+auxiliar+"]="+valor);
                            }//15
                            //22
                            else {
                                if (k == i) //se é o q está sendo trabalhado no momento
                                {
                                    valor = tabProbIni[k][j];
                                } else if (eviden[k] != -1) {
                                    valor = tabProbIni[k][eviden[k]];
                                } else {
                                    valor = tabProbIni[k][atribLoop[k]];
                                }
                            }//22

                            tabAux = tabAux * valor;
//                            System.out.println("valor="+String.valueOf(nf.format(valor)));
                        }//14

                        //23
                        for (int n = (metaAtrib - 1); n >= 0; n--) {
                            //24
                            if ((eviden[n] == -1) && (n != i)) {
                                atribLoop[n]++;
                                break;
                            }//24
                        }//23
                        //System.out.println("tabAux="+tabAux);
                        tabProbTemp[i][j] = tabProbTemp[i][j] + tabAux;
                    }//10
                }//9
                valor = 0;
                for (int j = 0; j < qtdFaixas[i]; j++) {
                    valor = valor + tabProbTemp[i][j];
                }


                //25
                for (int j = 0; j < qtdFaixas[i]; j++) {
                    // System.out.println("tabProbTemp["+i+"]["+j+"]="+tabProbTemp[i][j]);

                    P[i][j] = tabProbTemp[i][j] / valor;
                    meta.setProb(i, j, P[i][j]);
//                    System.out.println("P[" + i + "][" + j + "]: " + P[i][j]);
                }//25
            }//6
        }//5
        for (int i = 0; i < numInfer; i++) {
            //System.out.println("i"+i);
            //System.out.println("atrFxInf["+i+"]= "+atrFxInf[i]);
            for (int j = 0; j < meta.getQntdFaixas(atrInf[i]); j++) {
                //System.out.println("j"+j);

                if (atrFxInf[i] == j) {
                    //System.out.println("faixa inferenciada");
                    P[atrInf[i]][j] = 1;
                    meta.setProb(atrInf[i], j, 1);
                } else {
                    //System.out.println("faixa nao inferenciada");
                    P[atrInf[i]][j] = 0;
                    meta.setProb(atrInf[i], j, 0);
                //System.out.println("passou "+i);
                }
            }
        }
    }
//    }
}
