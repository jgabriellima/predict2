/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.gui.window.previsao.backstage;

import br.ufpa.lprad.predict.rna.RNA;

/**
 *
 * @author gabriel
 */
public class BackTreinamentoRNA {

    public static RNA rna;
  


    public static double[][] defasagem(double[][] matrizEntrada) {
        // matriz que conterá os dados historicos [anos*12][12(defasagens)]
        double[][] matriz = new double[matrizEntrada.length * matrizEntrada[0].length][13];//new double[324][13];
        double def[];
        int indice = 0;
        int iAux = 0;


        // loop temporário  -> cria dados
//        System.out.println("\n\n");
        for (int j = 0; j < matrizEntrada[0].length; j++) {
            for (int i = 0; i < matrizEntrada.length; i++) {

                matriz[iAux][0] = matrizEntrada[i][j];
                iAux++;
            }
        }


        /*
         * Loop que fraz a defasagem dos valores
         */

        for (int i = 0; i < matriz.length; i++) {

            def = new double[i + 1];
            for (int k = i; k >= 0; k--) {
                def[i - k] = matriz[k][0];
            }


            for (int j = 1; j < matriz[0].length; j++) {
                if (j < def.length) {
                    matriz[i][j] = def[j];
                } else {
                    matriz[i][j] = 0;
                }
            }

        }

//        for (int i = 0; i < matriz.length; i++) {
//            for (int j = 0; j < matriz[0].length; j++) {
//                System.out.print("" + matriz[i][j] + "  ");
//            }
//            System.out.println("");
//        }


        /*
         * Loop que procura o indice de inicio válido dos dados
         */
        for (int i = 0; i < matriz.length; i++) {
            if (matriz[i][matriz[0].length - 1] != 0) {
                indice = i;
                break;
            }
        }

        /*
         * Matriz de dados defasados - dados finais
         */
        double[][] dados = new double[matriz.length - indice][13];

        for (int i = 0; i < dados.length; i++) {

            for (int j = 0; j < dados[0].length; j++) {
                dados[i][j] = matriz[i + indice][j];
            }
        }


//        System.out.println("\n\n\n");
//
//        for (int i = 0; i < dados.length; i++) {
//            for (int j = 0; j < dados[0].length; j++) {
//                System.out.print(" " + dados[i][j] + "\t");
//            }
//            System.out.println("");
//        }

        return dados;

    }
}
