/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.gui.window.previsao.backstage;

import br.ufpa.lprad.predict.exception.PredictException;
import br.ufpa.lprad.predict.gui.window.previsao.PrevisaoGUI;
import br.ufpa.lprad.predict.leitura.ControleLeitura;
import br.ufpa.lprad.predict.leitura.interfaces.InterfaceLeituraConsumo;
import br.ufpa.lprad.predict.leitura.interfaces.InterfaceLeituraPrevisao;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Arilene
 */
public class BackPrevisaoGUI {

    private static int indiceMesInicial;
    private static int indiceMesFinal;
    private static int indiceAnoInicial;
    private static int indiceAnoFinal;
    private static boolean anosDiferentes;
    private static int anoInicialSelecionadoParaPrevisao;
    private static int anoFinalSelecionadoParaPrevisao;
    private static File arquivoExogenas;
    private static boolean exogena;
    private static String[][] matrizExogena;

    public static boolean isExogena() {
        return exogena;
    }

    public static void setExogena(boolean exogena) {
        BackPrevisaoGUI.exogena = exogena;
    }

    public static File getArquivoExogenas() {
        return arquivoExogenas;
    }

    public static void setArquivoExogenas(File arquivoExogenas) {
        BackPrevisaoGUI.arquivoExogenas = arquivoExogenas;
    }

    public static int getAnoFinalSelecionadoParaPrevisao() {
        return anoFinalSelecionadoParaPrevisao;
    }

    public static void setAnoFinalSelecionadoParaPrevisao(int anoFinalSelecionadoParaPrevisao) {
        BackPrevisaoGUI.anoFinalSelecionadoParaPrevisao = anoFinalSelecionadoParaPrevisao;
    }

    public static int getAnoInicialSelecionadoParaPrevisao() {
        return anoInicialSelecionadoParaPrevisao;
    }

    public static void setAnoInicialSelecionadoParaPrevisao(int anoInicialSelecionadoParaPrevisao) {
        BackPrevisaoGUI.anoInicialSelecionadoParaPrevisao = anoInicialSelecionadoParaPrevisao;
    }

    public static int qntsAnosDesejaPrever() {
        return getAnoFinalSelecionadoParaPrevisao() - getAnoInicialSelecionadoParaPrevisao();
    }

    public static boolean isAnosDiferentes() {
        return anosDiferentes;
    }

    public static int[] getPeriodo() {

        int periodo[] = new int[4];
        periodo[0] = getIndiceMesInicial();
        periodo[1] = getIndiceMesFinal();
        periodo[2] = getIndiceAnoInicial();
        periodo[3] = getIndiceAnoFinal();

        return periodo;
    }

    public static void setAnosDiferentes(int tam) {
        if (tam > 12) {
            BackPrevisaoGUI.anosDiferentes = true;
        } else {
            BackPrevisaoGUI.anosDiferentes = false;
        }
    }

    public static int getIndiceAnoFinal() {
        return indiceAnoFinal;
    }

    public static void setIndiceAnoFinal(int indiceAnoFinal) {
        BackPrevisaoGUI.indiceAnoFinal = indiceAnoFinal;
    }

    public static int getIndiceAnoInicial() {
        return indiceAnoInicial;
    }

    public static void setIndiceAnoInicial(int indiceAnoInicial) {
        BackPrevisaoGUI.indiceAnoInicial = indiceAnoInicial;
    }

    public static int getIndiceMesFinal() {
        return indiceMesFinal;
    }

    public static void setIndiceMesFinal(int indiceMesFinal) {
        BackPrevisaoGUI.indiceMesFinal = indiceMesFinal;
    }

    public static int getIndiceMesInicial() {
        return indiceMesInicial;
    }

    public static void setIndiceMesInicial(int indiceMesInicial) {
        BackPrevisaoGUI.indiceMesInicial = indiceMesInicial;
    }

    public static String[] getTipoConsumo() throws PredictException {

        try {
            InterfaceLeituraConsumo ic = new ControleLeitura();
            String retorno[] = ic.getTipoConsumo();

            ic = null;
            return retorno;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PredictException("Ocorreu um erro no backstage da previão no tipo de consumo\n" + ex.getMessage());
        }
    }

    public static String[] getConsumo(String tipoConsumo) throws PredictException {
        try {

            InterfaceLeituraConsumo ic = new ControleLeitura();
            String retorno[] = ic.getCabecalhoPlanilhaConsumo(tipoConsumo);
            String ret[] = new String[retorno.length - 1];
            for (int i = 1; i < retorno.length; i++) {
                ret[i - 1] = retorno[i];
            }
            ic = null;
            return ret;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PredictException("Ocorreu um erro no backstage da previão no tipo de consumo\n" + ex.getMessage());
        }
    }

    public static String[] getAnos(String tipoDeConsumo) throws PredictException {
        try {

            InterfaceLeituraPrevisao ic = new ControleLeitura();
            String retorno[] = ic.getAnos(tipoDeConsumo);
            ic = null;
            return retorno;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PredictException("Erro ao ler os anos no backstage da previsão: " + ex.getMessage());
        }
    }

    public static JCheckBox[] getAnosJChBox(String tipoDeConsumo) throws PredictException {
        try {

            String[] anos = getAnos(tipoDeConsumo);
            final JCheckBox[] jcbx = new JCheckBox[anos.length];

            for (int i = 0; i < jcbx.length; i++) {
                final JCheckBox aux = new JCheckBox(anos[i]);
                aux.setSelected(true);
                aux.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        if (!aux.isSelected()) {
                            PrevisaoGUI.jCBMarcarTodos.setSelected(false);
                        }
                    }
                });
                jcbx[i] = aux;
            }

            return jcbx;


        } catch (Exception ex) {
            throw new PredictException("Erro ao ler os anos no backstage da previsão: " + ex.getMessage());
        }
    }

    public static void desmarcarTodos(JPanel painel) {
        try {

            Component[] comps = painel.getComponents();

            for (Component c : comps) {
                if (c instanceof JCheckBox) {
                    ((JCheckBox) c).setSelected(false);
                }
            }

        } catch (Exception e) {
        }

    }

    public static void marcarTodos(JPanel painel) {
        try {

            Component[] comps = painel.getComponents();

            for (Component c : comps) {
                if (c instanceof JCheckBox) {
                    ((JCheckBox) c).setSelected(true);
                }
            }

        } catch (Exception e) {
        }

    }

    public static String[] getAnosPrevisao() throws PredictException {
        try {

            int inicio = 2005;
            int fim = 2005 + 20;//Integer.valueOf(new SimpleDateFormat("yyyy").format(new Date())).intValue();

            String[] anos = new String[(fim - inicio) + 4];

            anos[0] = "";
            for (int i = 1; i < anos.length; i++) {

                anos[i] = String.valueOf(inicio);
                inicio++;
            }


            return anos;

        } catch (Exception e) {
            throw new PredictException("Erro ao gerar os anos para a previsao: " + e.getMessage());
        }
    }

    public static String[] getAnosSelecionados(JPanel panel) throws PredictException {
        try {

            Vector anos = new Vector();
            String[] anosSelecionados;

            Component[] comps = panel.getComponents();
            for (Component c : comps) {
                if (c instanceof JCheckBox) {
                    if (((JCheckBox) c).isSelected()) {

                        anos.add(((JCheckBox) c).getText());
                    }
                }
            }

            anosSelecionados = new String[anos.size()];

            for (int i = 0; i < anosSelecionados.length; i++) {
                anosSelecionados[i] = String.valueOf(anos.get(i));
//                System.out.println("anosSelecionados["+i+"]: "+anosSelecionados[i]);
            }


            return anosSelecionados;


        } catch (Exception e) {
            throw new PredictException("Erro ao passar os anos selecionados do painel. " + e.getMessage());
        }
    }

    public static void abrirArquivoExogenas() {

        JFileChooser escolher = new JFileChooser(System.getProperty("user.home"));

        int result = escolher.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            if (escolher.getSelectedFile().getName().endsWith(".txt")) {
                setArquivoExogenas(escolher.getSelectedFile());
                PrevisaoGUI.jtf_varExogena.setText(getArquivoExogenas().getAbsolutePath());
                setExogena(true);
                geraMatrizExogena();
            } else {
                JOptionPane.showMessageDialog(null, "Escolha um arquivo com extensão .txt", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static String[][] getMatrizExogena() {
        return matrizExogena;
    }

    public static void setMatrizExogena(String[][] matrizExogena) {
        BackPrevisaoGUI.matrizExogena = matrizExogena;
    }

    private static void geraMatrizExogena() {

        Vector<String[]> dados = new Vector<String[]>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(getArquivoExogenas()));
            String linha;
            while ((linha = br.readLine()) != null) {
//                System.out.println("LINHA: " + linha);
                dados.add(linha.split("\t"));
            }

            br.close();
            matrizExogena = new String[dados.size()][];

            for (int i = 0; i < dados.size(); i++) {
                matrizExogena[i] = dados.get(i);
            }

//            System.out.println("MATRIZ EXOGENA ");
//            for (int i = 0; i < matrizExogena.length; i++) {
//                for (int j = 0; j < matrizExogena[i].length; j++) {
//                    System.out.print(matrizExogena[i][j] + "\t");
//                }
//                System.out.println("");
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
