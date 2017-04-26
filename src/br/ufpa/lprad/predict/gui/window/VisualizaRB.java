/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * VisualizaRB.java
 *
 * Created on 21/05/2010, 10:25:12
 */
package br.ufpa.lprad.predict.gui.window;

import br.ufpa.lprad.predict.gui.window.correlacao.FrameDiscretizacao;
import br.ufpa.lprad.predict.gui.window.correlacao.backstage.BackCorrelacaoGUI;
import br.ufpa.lprad.predict.redebayesiana.grafo.Objetos;
import br.ufpa.lprad.predict.redebayesiana.grafo.Tela;
import java.awt.Dimension;
import java.beans.PropertyVetoException;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameEvent;

/**
 *
 * @author Develope
 */
public class VisualizaRB extends javax.swing.JInternalFrame {

    private Objetos objetos;
    public static JPanel panelInformacoes;
    public MenuBar_Rede menu_bar;
//    private JPanel panelScroll;
//    private JScrollPane scroll;
//    public static JSplitPane split;
    public PainelInferencia pn;

    /** Creates new form VisualizaRB */
    public VisualizaRB() {
        super("Correlação", true, true, true, true);
        initComponents();
        FrameDiscretizacao.jProgressBar1.setIndeterminate(false);
        /**
         * 
         */
        menu_bar = new MenuBar_Rede(this);
        carregarIcone();
        setJMenuBar(menu_bar);
        objetos = new Objetos(BackCorrelacaoGUI.rb.getVertices());
        iniciaTela();
        /**
         *
         */
        setSize(new Dimension(800, 600));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
        updateUI();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        split = new javax.swing.JSplitPane();

        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        getContentPane().add(split, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void carregarIcone() {
        // tenta carregar os ícones da aplicação
        try {
            setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/predict.gif")));
            // carregar o ícone padrão de java
        } catch (Exception ex) {
            setFrameIcon(null);
        }
    }

    public void iniciaTela() {
        split.setDividerLocation(400);
        split.setDividerSize(10);
        //  System.out.println("Agora tá iniciando a tela ....");
        panelInformacoes = new javax.swing.JPanel();
        panelInformacoes.setLayout(null);
        panelInformacoes.setBorder(new javax.swing.border.TitledBorder(null, "Inferência Bayesiana", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("MS Sans Serif", 1, 11)));
        panelInformacoes.setPreferredSize(new java.awt.Dimension(320, 1000));

        JPanel panelGrafico = new javax.swing.JPanel();
        panelGrafico.setLayout(new java.awt.BorderLayout());
        panelGrafico.setBackground(java.awt.Color.white);
        panelGrafico.setBorder(new javax.swing.border.TitledBorder(null, "Rede Bayesiana", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("MS Sans Serif", 1, 11)));
        Tela t = new Tela(objetos);
        panelGrafico.add(t);
        split.setRightComponent(panelGrafico);
//
//        // Adiciona os Nos e Arcos na tela
//
        pn = new PainelInferencia();

        split.setLeftComponent(pn);
        pn.comecar.start();
        pn.updateUI();
        split.updateUI();

//        show();

    }

    @Override
    public void dispose() {
        try {
            pn.comecar.interrupt();
        } catch (Exception e) {
        }
        pn.comecar.stop();
        if (isVisible()) {
            setVisible(false);
        }
        if (isSelected()) {
            try {
                setSelected(false);
            } catch (PropertyVetoException pve) {
            }
        }
        if (!isClosed) {
            firePropertyChange(IS_CLOSED_PROPERTY, Boolean.FALSE, Boolean.TRUE);
            isClosed = true;
        }
        fireInternalFrameEvent(InternalFrameEvent.INTERNAL_FRAME_CLOSED);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JSplitPane split;
    // End of variables declaration//GEN-END:variables
}
