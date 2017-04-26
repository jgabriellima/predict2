/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.correlacao.discretizacao;

import br.ufpa.lprad.predict.util.BigDecimalUtility;

/**
 *
 * @author gabriel
 */
public class Discretizacao {

    private static double maior;
    private static double menor;
    private static double[] valFaixasSup;
    private static double[] valFaixasInf;
    private static double largFaixas;

    public static Faixa[] getFaixasAtrib(String atributo, Object[] dados, int numFaixas) {

//        System.out.println("Atributo : "+atributo);
        Faixa[] fxs = new Faixa[numFaixas];
        valFaixasInf = new double[numFaixas];
        valFaixasSup = new double[numFaixas];

        maior(dados);
        menor(dados);
        defineLarguraFaixa(numFaixas);
        defineFxDisc(numFaixas);

        for (int i = 0; i < fxs.length; i++) {
            fxs[i] = new Faixa(atributo);
        }

        for (int i = 0; i < fxs.length; i++) {
            fxs[i].setLimiteSuperior(valFaixasSup[i]);
            fxs[i].setLimiteInferior(valFaixasInf[i]);
        }

        return fxs;
    }

    /*
     * pega o menor valor da base
     */
    public static double menor(Object[] dados) {

        Discretizacao.menor = maior;

        double valor;
        for (Object obj : dados) {
//            valor = Double.valueOf(String.valueOf(obj).replace(".", "").replace(",", "."));
            valor = BigDecimalUtility.setFormattedCurrency(String.valueOf(obj)).doubleValue();//.replace(".", "").replace(",", "."));
            if (valor < menor) {
                menor = valor;
            }
        }

        return menor;
    }

    /*
     * Pega o maior valor da base
     */
    public static double maior(Object[] dados) {

        Discretizacao.maior = 0;

        double valor;
        for (Object obj : dados) {
//            System.out.println(" - "+String.valueOf(obj));
//            valor = Double.valueOf(String.valueOf(obj).replace(".", "").replace(",", "."));
//            System.out.println("---------String.valueOf(obj): "+String.valueOf(obj));
            valor = BigDecimalUtility.setFormattedCurrency(String.valueOf(obj)).doubleValue();//.replace(".", "").replace(",", "."));
            if (valor > maior) {
                maior = valor;
            }
        }

        return maior;
    }

    public static void defineLarguraFaixa(int nFaixas) {
        largFaixas = (maior - menor) / nFaixas;
    }

    public static void defineFxDisc(int nFaixas) {
        int count = 1;

        for (int q = 0; q < nFaixas; q++) {

            valFaixasSup[q] = 0.0;
        }

        valFaixasInf[0] = menor;
        valFaixasSup[0] = valFaixasInf[0] + largFaixas;


        for (int i = 1; i < nFaixas; i++) {
            valFaixasInf[i] = valFaixasSup[i - 1];
            valFaixasSup[i] = valFaixasInf[i] + largFaixas;
        }
    }

    public static double getLargFaixas() {
        return largFaixas;
    }

    public static double getMaior() {
        return maior;
    }

    public static double getMenor() {
        return menor;
    }

    public static double[] getValFaixasInf() {
        return valFaixasInf;
    }

    public static double[] getValFaixasSup() {
        return valFaixasSup;
    }
}
