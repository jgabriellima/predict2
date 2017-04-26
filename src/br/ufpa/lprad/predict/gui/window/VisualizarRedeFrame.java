package br.ufpa.lprad.predict.gui.window;

import br.ufpa.lprad.predict.redebayesiana.grafo.Objetos;
import br.ufpa.lprad.predict.redebayesiana.grafo.Tela;
import java.awt.Container;
import java.beans.PropertyVetoException;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.InternalFrameEvent;

public class VisualizarRedeFrame extends javax.swing.JInternalFrame {

    private Container container;
    private Objetos objetos;
    public static JPanel panelInformacoes;
    public MenuBar_Rede menu_bar;
    private JPanel panelScroll;
    private JScrollPane scroll;
    public static JSplitPane split;
    public PainelInferencia pn;

    // Variaveis de manipulacao de arquivos
//    public boolean arquivoAberto = false;
    public VisualizarRedeFrame(String tipoCorrelacao) {
        setVisible(true);
//        super("Correlação" + tipoCorrelacao, true, true, true, true);
//        FrameDiscretizacao.jProgressBar1.setIndeterminate(false);
//
//        menu_bar = new MenuBar_Rede(this);
////        new Thread() {
////
////            @Override
////            public void run() {
//                initComponents();
//                carregarIcone();
//                container = getContentPane();
//
//                setJMenuBar(menu_bar);
//                objetos = new Objetos(BackCorrelacaoGUI.rb.getVertices());
//                iniciaTela();
//                setSize(new Dimension(800, 600));
//                show();
////            }
////        }.start(); //        setVisible(true);
//        updateUI();
    }

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
        //  System.out.println("Agora tá iniciando a tela ....");
//        panelInformacoes = new javax.swing.JPanel();
//        panelInformacoes.setLayout(null);
//        panelInformacoes.setBorder(new javax.swing.border.TitledBorder(null, "Inferência Bayesiana", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("MS Sans Serif", 1, 11)));
//        panelInformacoes.setPreferredSize(new java.awt.Dimension(320, 1000));

        JPanel panelGrafico = new javax.swing.JPanel();

        panelGrafico.setLayout(new java.awt.BorderLayout());
        panelGrafico.setBackground(java.awt.Color.white);
        panelGrafico.setBorder(new javax.swing.border.TitledBorder(null, "Rede Bayesiana", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("MS Sans Serif", 1, 11)));

        Tela t = new Tela(objetos);
        // Adiciona os Nos e Arcos na tela
        panelGrafico.add(t);

        pn = new PainelInferencia();

        scroll = new JScrollPane(pn);
        scroll.setSize(300, 10000);

        split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scroll, panelGrafico);
        split.setDividerLocation(400);
        split.setDividerSize(10);
        /**
         *
         */
        container.add(split);
        pn.comecar.start();
        pn.updateUI();
        split.updateUI();

        setVisible(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

    }

    public void dispose() {
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

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/predict.gif"))); // NOI18N
        setLayer(10);
        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 400, 300);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
