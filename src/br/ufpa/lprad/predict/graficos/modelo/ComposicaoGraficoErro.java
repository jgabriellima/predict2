/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.graficos.modelo;

import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author gabriel
 */
public class ComposicaoGraficoErro {

    private ArrayList lista;
    private String[][] matriz;
    private Vector anos = new Vector();
    private String ano;

    public ComposicaoGraficoErro(String[][] matriz) {
        this.matriz = matriz;
        lista = new ArrayList();
        compoeLista();
    }

    public void compoeLista() {

        for (int i = 0; i < this.matriz.length; i++) {
            try {
                if (!matriz[i][0].equalsIgnoreCase("TOTAL")) {
                    if (!anos.contains(matriz[i][0].split("/")[1])) {
                        ano = matriz[i][0].split("/")[1];

                        //     System.out.println("ano: "+ano);
                        if (!matriz[i][3].equalsIgnoreCase("inexistente")) {
                            lista.add(new ModeloGraficoErro(formataMes(matriz[i][0].split("/")[0]), matriz[i][3].replace("%", ""), ano));
                        }
                    }
                }


            } catch (Exception e) {
            }
        }
    }

    public ArrayList getLista() {
        return lista;
    }

    public void setLista(ArrayList lista) {
        this.lista = lista;
    }

    public String[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(String[][] matriz) {
        this.matriz = matriz;
    }

    public static String formataMes(String data) {
        if (data.equalsIgnoreCase("Janeiro") || data.equalsIgnoreCase("01") || data.equalsIgnoreCase("1")) {
            return "jan";
        // return "01";

        } else if (data.equalsIgnoreCase("Fevereiro") || data.equalsIgnoreCase("02") || data.equalsIgnoreCase("2")) {
            return "fev";
        // return "02";
        } else if (data.equalsIgnoreCase("Março") || data.equalsIgnoreCase("03") || data.equalsIgnoreCase("3")) {
            return "mar";
//            return "03";
        } else if (data.equalsIgnoreCase("Abril") || data.equalsIgnoreCase("04") || data.equalsIgnoreCase("4")) {
            return "abr";
//            return "04";
        } else if (data.equalsIgnoreCase("Maio") || data.equalsIgnoreCase("05") || data.equalsIgnoreCase("5")) {
            return "mai";
//            return "05";
        } else if (data.equalsIgnoreCase("Junho") || data.equalsIgnoreCase("06") || data.equalsIgnoreCase("6")) {
            return "jun";
//            return "06";
        } else if (data.equalsIgnoreCase("Julho") || data.equalsIgnoreCase("07") || data.equalsIgnoreCase("7")) {
            return "jul";
//            return "07";
        } else if (data.equalsIgnoreCase("Agosto") || data.equalsIgnoreCase("08") || data.equalsIgnoreCase("8")) {
            return "ago";
//            return "08";
        } else if (data.equalsIgnoreCase("Setembro") || data.equalsIgnoreCase("09") || data.equalsIgnoreCase("9")) {
            return "set";
//            return "09";
        } else if (data.equalsIgnoreCase("Outubro") || data.equalsIgnoreCase("10")) {
            return "out";
//            return "10";
        } else if (data.equalsIgnoreCase("Novembro") || data.equalsIgnoreCase("11")) {
            return "nov";
//            return "11";
        } else {
            return "dez";
//            return "12";
        }
    }
}
