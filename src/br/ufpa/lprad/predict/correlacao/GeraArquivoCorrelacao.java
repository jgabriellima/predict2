/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.correlacao;

import br.ufpa.lprad.predict.exception.PredictException;
import br.ufpa.lprad.predict.gui.window.correlacao.backstage.BackCorrelacaoGUI;
import br.ufpa.lprad.predict.leitura.ControleLeitura;
import br.ufpa.lprad.predict.leitura.interfaces.InterfaceLeituraClimaticos;
import br.ufpa.lprad.predict.leitura.interfaces.InterfaceLeituraConsumo;
import br.ufpa.lprad.predict.leitura.interfaces.InterfacerLeituraSocioEconomico;
import br.ufpa.lprad.predict.propriedades.PredictPropriedades;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

/**
 *
 * @author gabriel
 */
public class GeraArquivoCorrelacao {

    private String nomeCorrelacao;
    private CorrelacaoBean correlacao;
    private InterfaceLeituraConsumo ilc;
    private InterfaceLeituraClimaticos ilclim;
    private InterfacerLeituraSocioEconomico ilse;
//    private static PrintWriter saida;
//    private static FileWriter writer;
    private static Vector<Object[]> dadosSelecionados;

    public static Vector<Object[]> getDadosSelecionados() {
        return dadosSelecionados;
    }

    public GeraArquivoCorrelacao(String nomeCorrelacao) {
        this.nomeCorrelacao = nomeCorrelacao;
        try {
            correlacao = BackCorrelacaoGUI.getCorrelacao(nomeCorrelacao);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar o arquivo de correlação.\nCorrelação " + nomeCorrelacao + "não encontrada!");
        }

        /* declara leituras */
        try {
            ilc = new ControleLeitura();
            ilclim = new ControleLeitura();
            ilse = new ControleLeitura();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            getDadosCorrelacao();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getDadosCorrelacao() throws PredictException {

        Vector<String[]> dados = new Vector<String[]>();
        String tipoConsumo;
        String nomeConsumo = null;
        String anoInicial = getAnoDeInicio();
        for (int i = 0; i < correlacao.getAtrib().length; i++) {

            tipoConsumo = correlacao.getAtrib()[i].split("-")[1];
//            System.out.println("tipoConsumo: " + tipoConsumo);
            //
            nomeConsumo = correlacao.getAtrib()[i].split("-")[0];
//            System.out.println("nomeConsumo: " + nomeConsumo);

            if (estadual(tipoConsumo)) {
//                System.out.println("ESTADUAL");
                dados.add(ilc.consumoEspecifico(tipoConsumo, nomeConsumo, anoInicial));

            } else if (socioEconomido(tipoConsumo)) {
//                System.out.println("SOCIO");
                dados.add(ilse.getEspecifico(nomeConsumo));

            } else if (climaticos(tipoConsumo)) {
//                System.out.println("CLIMA");
                dados.add(ilclim.getEspecificoClimaticos(nomeConsumo));
            }
        }

        int indiceMaximo = getIndiceMaximoLeitura(dados);
//        System.out.println("indiceMaximo: " + indiceMaximo);

        dadosSelecionados = new Vector<Object[]>();
        Vector<String> auxiliar = new Vector<String>();

//        System.out.println("dados.size(): " + dados.size());
        for (int i = 0; i < dados.size(); i++) {

            for (int j = 0; j < indiceMaximo; j++) {
                auxiliar.add(dados.get(i)[j]);
            }

            dadosSelecionados.add(auxiliar.toArray());
            auxiliar = new Vector<String>();
        }
//        System.out.println("dadosSelecionados.size(): " + dadosSelecionados.size());

        gravaArquivoCorrelacao(converteVectorMatriz(dadosSelecionados));

    }

    public int getIndiceMaximoLeitura(Vector<String[]> dados) {
        int indice[] = new int[dados.size()];

        int cont = 0;
        for (int i = 0; i < dados.size(); i++) {

            String[] consumo = dados.get(i);

            for (int j = 0; j < consumo.length; j++) {
                if (/*!consumo[j].equalsIgnoreCase("") || */consumo[j] != null) {
                    if (!consumo[j].isEmpty()) {
                        if (!consumo[j].equalsIgnoreCase("não aferido")) {
                            cont++;
                        }
                    }
                }
            }
            indice[i] = cont;
            cont = 0;
        }

        return menor(indice);
    }

    private boolean climaticos(String tipoConsumo) {
        if (tipoConsumo.contains("Climáticos")) {
            return true;
        }
        return false;
    }

    private String[][] converteVectorMatriz(Vector<Object[]> dadosSelecionados) {

        String[][] matriz = new String[dadosSelecionados.get(0).length][dadosSelecionados.size()];

        for (int j = 0; j < matriz[0].length; j++) {
            for (int i = 0; i < matriz.length; i++) {
                matriz[i][j] = String.valueOf(dadosSelecionados.get(j)[i]);
            }
        }
        return matriz;
    }

    private boolean estadual(String consumo) {
        if (consumo.toUpperCase().contains("FIO") || consumo.toUpperCase().contains("CATIVO") || consumo.toUpperCase().contains("Interligado".toUpperCase()) || consumo.toUpperCase().contains("Isolado".toUpperCase()) || consumo.toUpperCase().contains("Total".toUpperCase())) {
            return true;
        }
        return false;
    }

    private void gravaArquivoCorrelacao(String[][] matriz) {
        try {
            File f = new File(PredictPropriedades.getCaminhoCorrelacao());
//            System.out.println("CAMINHO ARQUIVO: "+f.getAbsolutePath());
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            //
            for (int i = 0; i < correlacao.getAtrib().length; i++) {
                bw.append(correlacao.getAtrib()[i]);
                if (i != correlacao.getAtrib().length - 1) {
                    bw.append("\t");
                }
            }
            bw.newLine();
            //
            int linhas = getNumLinhasValidas(matriz);

            for (int i = 0; i < linhas; i++) {
                for (int j = 0; j < matriz[0].length; j++) {
                    bw.append(matriz[i][j].replace(".", "").replace(",", ".") + "\t");
                }
                if (i < linhas) {
                    bw.newLine();
                }
            }

            bw.close();

//            Desktop.getDesktop().open(f);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao tentar criar o arquivos com as correlações. \nTente novamente!");
            e.printStackTrace();
        }

    }

    public int getNumLinhasValidas(String[][] matriz) {
        int result = 0;
        p:
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j].equalsIgnoreCase("não aferido") || matriz[i][j].isEmpty()) {
                    break p;
                }
            }
            result++;
        }
        return result;
    }

    private int menor(int[] indice) {

        int menor = 10000;
        for (int i = 0; i < indice.length; i++) {
            if (indice[i] < menor) {
                menor = indice[i];
            }
        }
        return menor;
    }

    private boolean socioEconomido(String string) {
        if (string.contains("Sócio")) {
            return true;
        }
        return false;
    }

    /*
     * Compara as bases de dados procurando os anos de interseção
     */
    private String getAnoDeInicio() {
        String resultado = "";
        try {
            System.out.println("");
            String MatrizConsumo[][] = ilc.getPlanilhaConsumo("Interligado CATIVO");
            String MatrizClimaticos[][] = ilclim.getPlanilhaClimaticos();
            String MatrizSocioEconomico[][] = ilse.getPlanilhaSocioEconomico();

            loop:
            for (int i = 0; i < MatrizConsumo.length - 2; i++) {
                if (!MatrizConsumo[i][0].equalsIgnoreCase("TOTAL")) {
                    String ano = MatrizConsumo[i][0].split("/")[1];
                    for (int j = 0; j < MatrizSocioEconomico.length - 2; j++) {
                        String ano2 = MatrizSocioEconomico[j][0].split("/")[1];

                        for (int k = 0; k < MatrizClimaticos.length - 2; k++) {
                            String ano3 = MatrizClimaticos[k][0].split("/")[1];

                            if (ano.equalsIgnoreCase(ano2) && ano2.equalsIgnoreCase(ano3)) {

                                System.out.println("Ano: " + ano + " Ano2: " + ano2 + " Ano3: " + ano3);
                                resultado = ano;
                                break loop;
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
//            e.printStackTrace();
        }

        //  System.out.println("RESULTADO: " + resultado);
        return resultado;

    }

    public static void main(String[] args) {
        try {

//            File f = new File(System.getenv("ProgramFiles") + "\\Celpa\\Predict\\arquivos\\correlações\\ArqCorrelação.txt");
//
////            PrintWriter pw = new PrintWriter(new File("C:\\Users\\gabriel\\Desktop\\ArqCorrelação.txt"));
//            PrintWriter pw = new PrintWriter(f);
//
//            pw.append("teste");
//            pw.close();
//
//            Desktop.getDesktop().open(f);
////            Desktop.getDesktop().open(new File("C:\\Users\\gabriel\\Desktop\\ArqCorrelação.txt"));

            System.out.println("" + System.getenv("ProgramFiles"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
