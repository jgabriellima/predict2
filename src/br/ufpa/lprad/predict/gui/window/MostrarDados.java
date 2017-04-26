package br.ufpa.lprad.predict.gui.window;

import br.ufpa.lprad.predict.util.Imprimir;
import br.ufpa.lprad.predict.util.io.ExportarXLS;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author  Gabriel
 */
public class MostrarDados extends javax.swing.JInternalFrame {

    private String[][] matriz;
    private String[] cabecalho;
    private String tipoConsumo;
    private String topo;

    /** Creates new form MostrarDados */
    public MostrarDados(String[][] matriz, String[] cabecalho, String tipoConsumo, String topo, int tipo) {
        initComponents();
        this.matriz = matriz;
        this.cabecalho = cabecalho;
        this.tipoConsumo = tipoConsumo;
        this.topo = tipoConsumo;
        if (topo.contains("Sócio") || topo.contains("Climáticos")) {
            this.topo = topo;
        }

        setTitle(topo);
        setModeloTabela(matriz, cabecalho);
    }

    public void setModeloTabela(String[][] matriz, String[] cabecalho) {

        jTable1.setModel(new DefaultTableModel(matriz, cabecalho));
        setCorLinhas(jTable1);
        jTable1.updateUI();
    }

    private void setCorLinhas(JTable jTable1) {

        Vector<Integer> indices = new Vector<Integer>();

        for (int j = 0; j <
                jTable1.getRowCount(); j++) {
            if (String.valueOf(jTable1.getValueAt(j, 0)).contains("TOTAL")) {
                indices.add(j);
            }
        }

        int posicoes[] = new int[indices.size()];
        for (int i = 0; i <
                posicoes.length; i++) {
            posicoes[i] = indices.get(i);
        }

        for (int i = 0; i <
                jTable1.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(new ColorCellRenderer(new Color(/*247, 241, 179*/255, 255, 180), posicoes));
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        imprimir = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/predict.gif"))); // NOI18N
        setVisible(true);

        jScrollPane1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable1.setEnabled(false);
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        imprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gpl/printer.png"))); // NOI18N
        imprimir.setText("Imprimir");
        imprimir.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        imprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imprimirActionPerformed(evt);
            }
        });
        jPanel1.add(imprimir);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gpl/gnome-mime-application-vnd.ms-excel.png"))); // NOI18N
        jButton2.setText("Exportar");
        jButton2.setToolTipText("Clique aqui para exportar para XLS");
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gpl/dialog-close.png"))); // NOI18N
        jButton3.setText("Fechar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);
        setJMenuBar(jMenuBar1);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-728)/2, (screenSize.height-342)/2, 728, 342);
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

    /**
     * botao para exportacao da planilha de dados
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            ExportarXLS.exportarPlanilhaXLS(this.cabecalho, this.matriz, this.topo);
            Desktop.getDesktop().open(new File(ExportarXLS.getPathXLS()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    //botao imprimir planilha de dados
    private void imprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imprimirActionPerformed
        try {
            imprimir.setEnabled(false);
//           System.out.println("matriz");
//           for(int i=0; i<matriz.length; i++){
//               for(int j=0; j<matriz[i].length; j++){
//                   System.out.print(matriz[i][j]+"  ");
//               }
//               System.out.println("");
//           }
//
//           System.out.println("\n\n\n");
//
//           System.out.println("cabecalho.length ="+cabecalho.length);
//           for(int i=0; i<cabecalho.length; i++)
//               System.out.println("cabecalho["+i+"]: "+cabecalho[i]);
//
//           System.out.println("\n\n\n");
//
//            System.out.println("tipoConsumo =" + tipoConsumo + ".");//aba se for Dados de Consumo, senao eh vazio
//
//           System.out.println("\n\n\n");
//
//            System.out.println("topo =" + topo + ".");//texto do topo do frame
//
//           System.out.println("\n\n\n");

            new Imprimir(matriz, cabecalho, topo, tipoConsumo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
}//GEN-LAST:event_imprimirActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton imprimir;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
