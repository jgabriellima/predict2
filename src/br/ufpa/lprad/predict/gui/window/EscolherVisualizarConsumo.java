/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EscolherVisualizarConsumo.java
 *
 * Created on 04/06/2009, 01:56:08
 */
package br.ufpa.lprad.predict.gui.window;

import br.ufpa.lprad.predict.actions.Action;
import br.ufpa.lprad.predict.exception.PredictException;
import br.ufpa.lprad.predict.leitura.ControleLeitura;
import br.ufpa.lprad.predict.leitura.interfaces.InterfaceLeituraConsumo;
import java.beans.PropertyVetoException;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author J. Gabriel Lima
 */
public class EscolherVisualizarConsumo extends javax.swing.JInternalFrame {

    private InterfaceLeituraConsumo ilc;
    private int tipo;

    public EscolherVisualizarConsumo(int tipo) {
        try {
            initComponents();
            ilc = new ControleLeitura();
            this.tipo = tipo;
            setModeloConsumo();

            int largura = Principal.getDesktopPane().getWidth();
            int altura = Principal.getDesktopPane().getHeight();

            setBounds(largura / 3, altura / 4, getWidth(), getHeight());
            updateUI();

        } catch (Exception ex) {
        }


    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setClosable(true);
        setTitle("Definir Consumo");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/predict.gif"))); // NOI18N
        setLayer(9);
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        setVisible(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Planilha Desejada"));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gpl/gnome-monitor.png"))); // NOI18N
        jButton1.setText("Gerar Planilha");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gpl/dialog-close.png"))); // NOI18N
        jButton2.setText("Fechar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            setClosed(true);
        } catch (PropertyVetoException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            String tipoConsumo = String.valueOf(jComboBox1.getSelectedItem());
//            System.out.println("tipoConsumo: " + tipoConsumo);
            String topo;
            try {
                topo = ilc.getNomeDaPlanilha(String.valueOf(jComboBox1.getSelectedItem()));
            } catch (NullPointerException ex) {
                topo = "Dados de Consumo";
            }
            System.out.println("");
            Action.actionAbrirVisualizaConsumo(evt, ilc.getPlanilhaConsumo(tipoConsumo), ilc.getCabecalhoPlanilhaConsumo(tipoConsumo), tipoConsumo, topo, 0);
           
        } catch (PredictException ex) {
            ex.printStackTrace();
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

    private void setModeloConsumo() {
        try {
            jComboBox1.setModel(new DefaultComboBoxModel(ilc.getTipoConsumo()));
        } catch (PredictException ex) {
            ex.printStackTrace();
        }

    }
}
