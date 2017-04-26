package br.ufpa.lprad.predict.previsao.multipla.grafico;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author gabriel
 */
public class ComposicaoGraficoPrevisaoMultipla {

    private List<ModeloGraficoMultipla> lista;
    private String[][] matriz;
    private Vector anos = new Vector();
    private String ano;

    public ComposicaoGraficoPrevisaoMultipla(String[][] matriz) {
        this.matriz = matriz;
        lista = new ArrayList<ModeloGraficoMultipla>();
        compoeLista();
    }

    public void compoeLista() {

        for (int i = 0; i < this.matriz.length; i++) {
            if (!matriz[i][0].equalsIgnoreCase("TOTAL")) {
                if (!anos.contains(matriz[i][0].split("/")[1])) {
                    ano = matriz[i][0].split("/")[1];
                    if (!matriz[i][2].equalsIgnoreCase("inexistente") && !matriz[i][2].isEmpty()) {
                        lista.add(new ModeloGraficoMultipla(formataMes(matriz[i][0].split("/")[0]), matriz[i][2], ano));
                    }
                }
            }
        }
    }

    public static String formataMes(String data) {
        if (data.equalsIgnoreCase("Janeiro") || data.equalsIgnoreCase("01") || data.equalsIgnoreCase("1")) {
            return "jan";
        } else if (data.equalsIgnoreCase("Fevereiro") || data.equalsIgnoreCase("02") || data.equalsIgnoreCase("2")) {
            return "fev";
        } else if (data.equalsIgnoreCase("Março") || data.equalsIgnoreCase("03") || data.equalsIgnoreCase("3")) {
            return "mar";
        } else if (data.equalsIgnoreCase("Abril") || data.equalsIgnoreCase("04") || data.equalsIgnoreCase("4")) {
            return "abr";
        } else if (data.equalsIgnoreCase("Maio") || data.equalsIgnoreCase("05") || data.equalsIgnoreCase("5")) {
            return "mai";
        } else if (data.equalsIgnoreCase("Junho") || data.equalsIgnoreCase("06") || data.equalsIgnoreCase("6")) {
            return "jun";
        } else if (data.equalsIgnoreCase("Julho") || data.equalsIgnoreCase("07") || data.equalsIgnoreCase("7")) {
            return "jul";
        } else if (data.equalsIgnoreCase("Agosto") || data.equalsIgnoreCase("08") || data.equalsIgnoreCase("8")) {
            return "ago";
        } else if (data.equalsIgnoreCase("Setembro") || data.equalsIgnoreCase("09") || data.equalsIgnoreCase("9")) {
            return "set";
        } else if (data.equalsIgnoreCase("Outubro") || data.equalsIgnoreCase("10")) {
            return "out";
        } else if (data.equalsIgnoreCase("Novembro") || data.equalsIgnoreCase("11")) {
            return "nov";
        } else {
            return "dez";
        }
    }
}
