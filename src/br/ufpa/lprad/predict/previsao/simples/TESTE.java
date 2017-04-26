/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.previsao.simples;

/**
 *
 * @author gabriel
 */
public class TESTE {

    public static void main(String[] args) {


        int anoInicial = 2004;
        int anoFinal = 2005;
        int mesInicial = 3;
        int mesFinal = 6;


        int[] anosAux = new int[(anoFinal - anoInicial) + 1];
        anosAux[0] = anoInicial;
        for (int i = 1; i < anosAux.length; i++) {
            anosAux[i] = anoInicial + i;
        }
        anosAux[anosAux.length - 1] = anoFinal;


        int auxFinal = 12;//mesFinal;
        int auxInicial = mesInicial;
        for (int i = 0; i < anosAux.length; i++) {
            if (i > 0) {
                auxInicial = 1;
            }
            if (i == anosAux.length - 1) {
                auxFinal = mesFinal;
            }
            for (int j = auxInicial; j <= auxFinal; j++) {
                System.out.println("auxInicial: "+auxInicial+" - auxFinal: "+auxFinal);
                System.out.println(j + "/" + anosAux[i]);
            }
        }

    }
}
