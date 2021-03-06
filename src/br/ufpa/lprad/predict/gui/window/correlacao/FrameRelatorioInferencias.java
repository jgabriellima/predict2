/*
 * DadosConsumoFrame.java
 *
 * Created on 26 de Abril de 2006, 10:39
 */
package br.ufpa.lprad.predict.gui.window.correlacao;

import br.ufpa.lprad.predict.propriedades.PredictPropriedades;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author  Ramon
 */
public class FrameRelatorioInferencias extends javax.swing.JInternalFrame {

    private String textoInferencia;

    /** Creates new form DadosConsumoFrame */
    public FrameRelatorioInferencias() {
        setTitle("Relat�rio de Proje��o ");//+FrameCorrelacao.getLabelCorrelacao());
        initComponents();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setSize(650, 450);
        editor.setContentType("txt/html");
        editor.setEditable(false);
        try {
            editor.setPage("file:///"+PredictPropriedades.getCaminhoRelatorioInferencias());//C:/Program Files/"+PredictPropriedades.getNomeConcessionaria()+"/Predict/html/RelatorioInferencias.html");//PredictPropriedades.getCaminhoRelatorioInferencias());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        editor = new javax.swing.JEditorPane();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Relat�rio de Proje��o: ");
        setAlignmentX(200.0F);
        setAlignmentY(300.0F);
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/predict.gif"))); // NOI18N
        setLayer(100);
        setPreferredSize(new java.awt.Dimension(400, 500));
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        setVisible(true);

        jScrollPane1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setFont(new java.awt.Font("Tahoma", 0, 12));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(23, 100));
        jScrollPane1.setPreferredSize(getPreferredSize());
        jScrollPane1.setViewportView(editor);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gpl/printer.png"))); // NOI18N
        jButton1.setText("Imprimir");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gpl/dialog-close.png"))); // NOI18N
        jButton3.setText("Fechar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-653)/2, (screenSize.height-342)/2, 653, 342);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * botao para fechar a planilha de dados
     */
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        try {
            setClosed(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jButton3ActionPerformed
    //Busca o texta da classe Itens.java e coloca no jTextPane1

    public void setTextoInferencia(String txtInferencia) {
        this.textoInferencia = txtInferencia;
//        jTextPane1.setText(this.textoInferencia);
    }

    //botao imprimir texto
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //Manda o Texto da tela para a Impressora.
        new Imprimir(editor);//jTextPane1);
//        String command = evt.getActionCommand();
//			if (command.equals("Imprimir")){
//				Imprimir.comp(jTextPane1);
//			}
    }//GEN-LAST:event_jButton1ActionPerformed

    //Classe respons�vel pela Impress�o.
    public class Imprimir implements Printable {

        private PageFormat pFormat;
        private PrinterJob pJob;
        private Component comp;
        boolean scaleWidthToFit = true;

        public Imprimir(Component comp) {
            pFormat = new PageFormat();
            pJob = PrinterJob.getPrinterJob();
            this.comp = comp;
            printDialog();
        }

        private void printDialog() {
            if (pJob.printDialog()) {
                pJob.setPrintable(this, pFormat);
                try {
                    pJob.print();
                } catch (PrinterException printerException) {
                    System.out.println("Erro ao imprimir documento.");
                }
            }
        }

        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
                throws PrinterException {

            double scale = 1.0;
            Graphics2D graphics2D;

            graphics2D = (Graphics2D) graphics;
            comp.setSize((int) pageFormat.getImageableWidth(), (int) pageFormat.getImageableHeight());
            comp.validate();

            if ((scaleWidthToFit) && (comp.getMinimumSize().getWidth() > pageFormat.getImageableWidth())) {
                scale = pageFormat.getImageableWidth() / comp.getMinimumSize().getWidth();
                graphics2D.scale(scale, scale);
            }

            graphics2D.setClip((int) (pageFormat.getImageableX() / scale),
                    (int) (pageFormat.getImageableY() / scale), (int) (pageFormat.getImageableWidth() / scale), (int) (pageFormat.getImageableHeight() / scale));

            if (pageIndex > 0) {
                return Printable.NO_SUCH_PAGE;
            }

            graphics2D.translate(graphics2D.getClipBounds().getX(), graphics2D.getClipBounds().getY());

            comp.paint(graphics2D);
            return Printable.PAGE_EXISTS;

        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane editor;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
