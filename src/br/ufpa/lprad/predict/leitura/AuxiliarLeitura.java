/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.leitura;

import br.ufpa.lprad.predict.exception.PredictException;
import jxl.Cell;
import jxl.Sheet;

/**
 *
 * @author J. Gabriel Lima
 */
public class AuxiliarLeitura {

    public static String formataMes(String data) {

        if (data.equalsIgnoreCase("Janeiro") || data.equalsIgnoreCase("jan") || data.equalsIgnoreCase("1")) {
            return "1";

        } else if (data.equalsIgnoreCase("Fevereiro") || data.equalsIgnoreCase("fev") || data.equalsIgnoreCase("2")) {
            return "2";

        } else if (data.equalsIgnoreCase("Março") || data.equalsIgnoreCase("mar") || data.equalsIgnoreCase("3")) {
            return "3";

        } else if (data.equalsIgnoreCase("Abril") || data.equalsIgnoreCase("abr") || data.equalsIgnoreCase("4")) {
            return "4";

        } else if (data.equalsIgnoreCase("Maio") || data.equalsIgnoreCase("mai") || data.equalsIgnoreCase("5")) {
            return "5";

        } else if (data.equalsIgnoreCase("Junho") || data.equalsIgnoreCase("jun") || data.equalsIgnoreCase("6")) {
            return "6";

        } else if (data.equalsIgnoreCase("Julho") || data.equalsIgnoreCase("jul") || data.equalsIgnoreCase("7")) {
            return "7";

        } else if (data.equalsIgnoreCase("Agosto") || data.equalsIgnoreCase("ago") || data.equalsIgnoreCase("8")) {
            return "8";

        } else if (data.equalsIgnoreCase("Setembro") || data.equalsIgnoreCase("set") || data.equalsIgnoreCase("9")) {
            return "9";

        } else if (data.equalsIgnoreCase("Outubro") || data.equalsIgnoreCase("out")) {
            return "10";

        } else if (data.equalsIgnoreCase("Novembro") || data.equalsIgnoreCase("nov")) {
            return "11";

        } else {
            return "12";
        }
    }

    public static int getColunaConsumo(String consumo, Sheet aba) throws Exception {

        try {
            int coluna = 0;
            Cell[] consumos = aba.getRow(1);
            for (Cell c : consumos) {
                if (c.getContents().equalsIgnoreCase(consumo)) {
                    coluna = c.getColumn();
                    break;
                }
            }

            return coluna;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    public static int getLinha(String valor, int coluna, Sheet aba) {
        int resultado = 0;
        try {

            Cell[] dados = aba.getColumn(coluna);

            for (int i = 0; i < dados.length; i++) {
                //System.out.println(""+dados[i].getContents());
                if (dados[i].getContents().equalsIgnoreCase(valor)) {
                    resultado = i;
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public static int getIndiceAnoLeitura(Cell[] dados, String data)
    {
        int resultado =0;
        try{

            
         
            for(int i=0; i<dados.length; i++)
            {
               
                if(dados[i].getContents().contains(data))
                {
                    resultado = i;
                    break;
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
}
