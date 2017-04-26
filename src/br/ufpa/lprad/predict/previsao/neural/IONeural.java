package br.ufpa.lprad.predict.previsao.neural;

import br.ufpa.lprad.predict.leitura.ControleLeitura;
import br.ufpa.lprad.predict.propriedades.PredictPropriedades;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import org.jfree.ui.about.ProjectInfo;

/**
 *
 * @author Develope
 */
public class IONeural {

    /**
     * 
     * @param tipoConsumo - > Interligado XXXX
     * @param nomeConsumo - > RESIDENCIAL, etc...
     * @param anoInicial  
     * @param anoFinal
     * @return
     */
    public static String[][] getConsumo(String tipoConsumo, String nomeConsumo) {
        try {
            ControleLeitura cons = new ControleLeitura();
            String[] anos = cons.getAnos(tipoConsumo);
            //String[][] dados = cons.consumoEspecifico("Interligado CATIVO", "RESIDENCIAL (MWh)", "1994", "2008");
            String[][] dados = cons.consumoEspecifico(tipoConsumo, nomeConsumo, anos[0], anos[anos.length - 1]);

            for (int i = 0; i < dados.length; i++) {
                System.out.println(dados[i][0] + " - " + dados[i][1]);
            }

            List<String> ano = new ArrayList<String>();
            for (int i = 0; i < dados.length; i++) {
                if (dados[i][0] != null) {
                    if (!ano.contains(dados[i][0].split("/")[1])) {
                        ano.add(dados[i][0].split("/")[1]);
                    }
                }
            }
            /**
             *
             */
            String dFinal[][] = new String[12][ano.size()];
            int cont = 0;
            for (int i = 0; i < ano.size(); i++) {
                for (int j = 0; j < dados.length; j++) {
                    if (dados[j][0].split("/")[1].equalsIgnoreCase(ano.get(i))) {
                        dFinal[cont][i] = dados[j][1];
                        cont++;
                    }
                }
                cont = 0;
            }
            return dFinal;

        } catch (Exception e) {
        }
        return null;
    }

    public static boolean gravaArquivo(String path_pasta, String tipoConsumo, String nomeConsumo, String[][] dados, int ano_final) {
        boolean retorno = false;
        try {
            PredictPropriedades.leProperties();
            String pasta = path_pasta.concat("\\" + nomeConsumo);
            //PredictPropriedades.getCaminhoArquivoEntradaNeural() + tipoConsumo.trim() + "";
            System.out.println("pasta: "+pasta);
            File f = new File(pasta);
            if (!f.exists()) {
                if (f.mkdirs()) {

                    File fTemp = new File(f.getAbsoluteFile() + "\\tempResult");
                    fTemp.mkdir();
                    File fTemp2 = new File(f.getAbsoluteFile() + "\\Execucao");
                    fTemp2.mkdir();
                    File fTemp3 = new File(f.getAbsoluteFile() + "\\tempredes");
                    fTemp3.mkdir();

                    String path = f.getAbsolutePath();
                    f = new File(path + "\\" + nomeConsumo.concat(".txt"));
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                    //
                    /**
                     * Verifica o intervalo até o ano final
                     */
                    //int cont = 2006-1991;
                    int cont = ano_final-1991+1;
                    for (int i = 0; i < dados.length; i++) {
                        for (int j = 0; j < cont; j++) {
                            bw.append(dados[i][j].replace(".", "").replace(",", "."));
                            if (j + 1 != cont) {
                                bw.append("\t");
                            }
                        }
                        bw.newLine();
                    }
                    bw.close();
                    retorno = true;
//                    System.out.println("f.getAbsolutePath(): "+f.getAbsolutePath());
                    //
//                    Desktop.getDesktop().open(f);

                }
            } else {
                String path = f.getAbsolutePath();
                f = new File(path + "/" + nomeConsumo.concat(".txt"));
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                //
                for (int i = 0; i < dados.length; i++) {
                    for (int j = 0; j < dados[i].length; j++) {
                        bw.append(dados[i][j].replace(".", "").replace(",", "."));
                        if (j + 1 != dados[i].length) {
                            bw.append("\t");
                        }
                    }
                    bw.newLine();
                }
                bw.close();
                //
                retorno = true;
//                Desktop.getDesktop().open(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }

    public static void main(String[] args) {

//        gravaArquivo("Interligado CATIVO","RESIDENCIAL (MWh)", getConsumo("Interligado CATIVO", "RESIDENCIAL (MWh)"),2006);
    }
}
