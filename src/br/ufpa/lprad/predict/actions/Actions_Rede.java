/*
 * Actions.java
 *
 * Created on 7 de Setembro de 2008, 17:14
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.actions;

import br.ufpa.lprad.predict.correlacao.RelatorioInferencias;
import br.ufpa.lprad.predict.gui.window.correlacao.FrameAG;
import br.ufpa.lprad.predict.gui.window.correlacao.FrameAnaliseTemporal;
import br.ufpa.lprad.predict.gui.window.correlacao.FrameRelatorioInferencias;
import br.ufpa.lprad.predict.gui.window.MenuBar_Rede;
import br.ufpa.lprad.predict.gui.window.PainelInferencia;
import br.ufpa.lprad.predict.gui.window.backstage.GerenciadorDesktop;
import br.ufpa.lprad.predict.redebayesiana.algoritmogenetico.AG;
import br.ufpa.lprad.predict.redebayesiana.aprendizado.K2;
import br.ufpa.lprad.predict.redebayesiana.aprendizado.Vertice;
import br.ufpa.lprad.predict.redebayesiana.grafo.No;
import br.ufpa.lprad.predict.redebayesiana.grafo.Tela;
import br.ufpa.lprad.predict.redebayesiana.inferencias.BotaoInferencia;
import br.ufpa.lprad.predict.redebayesiana.inferencias.Itens;
import br.ufpa.lprad.predict.redebayesiana.io.AbrirXML;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.Vector;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 *
 * @author J. Gabriel Lima
 */
public class Actions_Rede {

    private static Vertice vertice_estudado;
    private static Vector<BotaoInferencia> btnInf = new Vector<BotaoInferencia>();
    private static int fx;
    public static boolean vizualiza_relatorio = false;//true;
    private static int nbtn = 0;
    public static FrameAG FAT;
    public static boolean expandido = false;

    public static void action_algoritmo_genetico(ActionEvent e, JInternalFrame frame) {
        if (Tela.getSelecionado().size() > 1 || Tela.getSelecionado().size() == 0) {
            JOptionPane.showMessageDialog(null, "Selecione um nó para fazer a análise");
        } else {

            FAT = new FrameAG();
            GerenciadorDesktop.add(FAT);

        }

    }

    public static void expandir(ActionEvent e, JInternalFrame frame) {
        if (!expandido) {

            for (int i = 0; i < Itens.botoes.length; i++) {
                if (!Itens.botoes[i].status) {
                    Itens.botoes[i].click();
                    PainelInferencia.itens.atualizaItens();
                }
                expandido = true;
            }
        } else {
            for (int i = 0; i < Itens.botoes.length; i++) {
                if (Itens.botoes[i].status) {
                    Itens.botoes[i].click();
                    PainelInferencia.itens.atualizaItens();
                }
                expandido = false;
            }
        }
    }

    public static void mostrarRelatorio(ActionEvent e, JInternalFrame frame) {
        try {
            new RelatorioInferencias(Itens.textoInfer(), "Relat&oacute;rio de Proje&ccedil;&atilde;o");
            GerenciadorDesktop.add(new FrameRelatorioInferencias(), false);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void mostrarRelatorioAg(ActionEvent e, JInternalFrame frame) {
        try {
            new RelatorioInferencias(AG.relatorio, "An&aacute;lise de Cen&aacute;rios");
            GerenciadorDesktop.add(new FrameRelatorioInferencias(), false);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void mostrarRelatorioAnaliseTemporal(String texto) {
        try {
            new RelatorioInferencias(texto, "An&aacute;lise Temporal");
            GerenciadorDesktop.add(new FrameRelatorioInferencias(), false);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Vertice vertice_estudado() {
        return vertice_estudado;
    }

    public static void setfx(int i) {
        fx = i;

    }

    public static int getfx() {
        return fx;
    }

    public static void action_analise_temp(ActionEvent e, Component frame) {
        try {
            if (Tela.getSelecionado().size() > 1 || Tela.getSelecionado().size() == 0) {
                JOptionPane.showMessageDialog(null, "Selecione um nó para fazer a análise");
            } else {
                No no = (No) Tela.getSelecionado().elementAt(0);
                Vector vertices = null;
                try {
                    vertices = K2.getRedeBayesiana().getVertices();
                } catch (NullPointerException ex) {
                    vertices = AbrirXML.getRedeBaysiana().getVertices();
                }
                vertice_estudado = null;

                for (int i = 0; i < vertices.size(); i++) {
                    if (((Vertice) vertices.elementAt(i)).getNome().equalsIgnoreCase(no.getNome())) {
                        vertice_estudado = (Vertice) vertices.elementAt(i);
                        break;
                    }
                }

                // System.out.println(""+vertice_estudado.getNome());
//
//
//            JOptionPane.showMessageDialog(null, no.getNome());
//
//            StringBuilder sb = new StringBuilder();
//
//            for(int i=0; i<vertice_estudado.getProbabilidade().length;i++)
//            {
//                sb.append(vertice_estudado.getProbabilidade()[i]);
//                sb.append("\n");
//            }
//
//            JOptionPane.showMessageDialog(null,sb.toString());



                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                FrameAnaliseTemporal FAT = new FrameAnaliseTemporal(vertice_estudado);
                FAT.setLocation((int) (dim.getWidth() / 2 - FAT.getWidth() / 2), (int) (dim.getHeight() / 2 - FAT.getHeight() / 2));
                //Principal.getDesktopPane().add(FAT).setLocation((int)(dim.getWidth()/2-FAT.getWidth()/2),(int)(dim.getHeight()/2-FAT.getHeight()/2));
                GerenciadorDesktop.add(FAT);
                // int tempo = Integer.parseInt(JOptionPane.showInputDialog("Entre com o numero de meses"));



            }

        } catch (NullPointerException ex) {
            ex.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Para usar as opções selecione um nó e click no botão direito do mouse");
        }
    }

    public static void action_desfazer_infe(ActionEvent e, Component frame) {

//        Thread pe_na_porta = new Thread(){
//           public void run(){
//        try {
//            for (int i = 0; i < btnInf.length; i++) {
//                btnInf[i].desfaz();
//            }
//        } catch (NullPointerException ex) {
//        }
        try {
            for (int i = 0; i < btnInf.size(); i++) {
                btnInf.get(i).desfaz();
            }
        } catch (NullPointerException ex) {
        }

//           }
//        };
//        pe_na_porta.start();
//
        btnInf.removeAllElements();
        nbtn = 0;
    }

    public static void setBotao(BotaoInferencia btn) {
        btnInf.add(btn);
        //System.out.println("...................................."+btnInf[nbtn].getName());
        // nbtn++;

    }

    public static void action_sair(ActionEvent e, Component frame) {

        ((JInternalFrame) frame).dispose();
    }

    public static void action_vizualiza_relatorio(ItemEvent e, Component frame) {
        if (e.getStateChange() == e.DESELECTED) {
            vizualiza_relatorio = false;
            //System.out.println("deselecionado");
        } else {
            vizualiza_relatorio = true;
            //  System.out.println("selecionado");
        }
    }

    public static void setAtalhos() {
        MenuBar_Rede.analise_temp.setAccelerator(KeyStroke.getKeyStroke('A', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
        MenuBar_Rede.desfazer_Inf.setAccelerator(KeyStroke.getKeyStroke('Z', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
        MenuBar_Rede.sair.setAccelerator(KeyStroke.getKeyStroke('Q', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
        MenuBar_Rede.mostrarRelatorio.setAccelerator(KeyStroke.getKeyStroke('R', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
        MenuBar_Rede.mostrarRelatorioAg.setAccelerator(KeyStroke.getKeyStroke('C', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
        MenuBar_Rede.algoritmo_genetico.setAccelerator(KeyStroke.getKeyStroke('G', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
        MenuBar_Rede.expandirAtributos.setAccelerator(KeyStroke.getKeyStroke('E', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
    }

    public void pegaFx(Vertice vertice) {

        f:
        for (int i = 0; i < vertice.getProbabilidade().length; i++) {
            if (vertice.getProbabilidade()[i] == 1) {
                setfx(i);
                break f;
            }
        }

    }
}
