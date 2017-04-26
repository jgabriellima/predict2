/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PreferenciasGUI.java
 *
 * Created on 31/05/2009, 00:27:16
 */
package br.ufpa.lprad.predict.gui.window;

import br.ufpa.lprad.predict.exception.PredictException;
import br.ufpa.lprad.predict.propriedades.PredictPropriedades;
import br.ufpa.lprad.predict.util.io.AbrirArquivo;
import java.beans.PropertyVetoException;

/**
 *
 * @author J. Gabriel Lima
 */
public class PreferenciasGUI extends javax.swing.JInternalFrame {

    public PreferenciasGUI() throws PredictException {
        initComponents();
        inicializarComponentes();
        // jPanel9.setVisible(false);
        setVisible(true);
        jButton7.setEnabled(false);

    }

    public void inicializarComponentes() throws PredictException {
        //  jcb_aparencia.setModel(new DefaultComboBoxModel(BackPreferenciasGUI.getLookAndFeel()));

        //  System.out.println("" + PredictPropriedades.getnomeLookAndFeel());
        //   jcb_aparencia.setSelectedItem(PredictPropriedades.getnomeLookAndFeel());

        jtf_principal.setText(PredictPropriedades.getCaminhoPrincipal());
        jtf_caminhoDadosConsumo.setText(PredictPropriedades.getCaminhoPlanilhaConsumo());
        jtf_dadosSocioEconomico.setText(PredictPropriedades.getCaminhoPlanilhaSocioEconomico());
        jtf_dadosClimaticos.setText(PredictPropriedades.getCaminhoPlanilhaClimaticos());

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jtf_principal = new javax.swing.JTextField();
        btn_abrir0 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btn_abrir1 = new javax.swing.JButton();
        jtf_caminhoDadosConsumo = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        btn_abrir2 = new javax.swing.JButton();
        jtf_dadosSocioEconomico = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        btn_abrir3 = new javax.swing.JButton();
        jtf_dadosClimaticos = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Preferências");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/predict.gif"))); // NOI18N

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Principal"));

        btn_abrir0.setText("...");
        btn_abrir0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_abrir0ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jtf_principal, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_abrir0, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btn_abrir0)
                .addComponent(jtf_principal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Caminho de Dados de Consumo"));

        btn_abrir1.setText("...");
        btn_abrir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_abrir1ActionPerformed(evt);
            }
        });

        jtf_caminhoDadosConsumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtf_caminhoDadosConsumoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jtf_caminhoDadosConsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_abrir1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jtf_caminhoDadosConsumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btn_abrir1))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Caminho de Dados Sócio-Econômicos"));

        btn_abrir2.setText("...");
        btn_abrir2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_abrir2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jtf_dadosSocioEconomico, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_abrir2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jtf_dadosSocioEconomico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btn_abrir2))
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Caminho de Dados Climáticos"));

        btn_abrir3.setText("...");
        btn_abrir3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_abrir3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jtf_dadosClimaticos, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_abrir3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jtf_dadosClimaticos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btn_abrir3))
        );

        jButton1.setText("Restaurar Padrões");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addComponent(jButton1))
        );

        jTabbedPane1.addTab("Configurações", jPanel1);

        jButton3.setText("OK");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton6.setText("Cancelar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Aplicar");
        jButton7.setEnabled(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton7)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(jButton6)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try {
            setClosed(true);
        } catch (PropertyVetoException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        salvarAlteracoes();
        jButton6ActionPerformed(evt);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        salvarAlteracoes();
        jButton7.setEnabled(false);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void btn_abrir0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_abrir0ActionPerformed
        AbrirArquivo.abrirCaminhoArquivo();
        jtf_principal.setText(AbrirArquivo.getCaminhoArquivo());
        jButton7.setEnabled(true);
    }//GEN-LAST:event_btn_abrir0ActionPerformed

    private void btn_abrir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_abrir1ActionPerformed
        AbrirArquivo.abrirArquivo();
        jtf_caminhoDadosConsumo.setText(AbrirArquivo.getCaminhoArquivo());
        jButton7.setEnabled(true);
    }//GEN-LAST:event_btn_abrir1ActionPerformed

    private void btn_abrir2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_abrir2ActionPerformed
        AbrirArquivo.abrirArquivo();
        jtf_dadosSocioEconomico.setText(AbrirArquivo.getCaminhoArquivo());
        jButton7.setEnabled(true);
    }//GEN-LAST:event_btn_abrir2ActionPerformed

    private void btn_abrir3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_abrir3ActionPerformed
        AbrirArquivo.abrirArquivo();
        jtf_dadosClimaticos.setText(AbrirArquivo.getCaminhoArquivo());
        jButton7.setEnabled(true);
    }//GEN-LAST:event_btn_abrir3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            PredictPropriedades.setCaminhoPadrao();
            jtf_principal.setText(PredictPropriedades.getCaminhoPrincipal());
            jtf_caminhoDadosConsumo.setText(PredictPropriedades.getCaminhoPlanilhaConsumo());
            jtf_dadosSocioEconomico.setText(PredictPropriedades.getCaminhoPlanilhaSocioEconomico());
            jtf_dadosClimaticos.setText(PredictPropriedades.getCaminhoPlanilhaClimaticos());
            PredictPropriedades.gravaProperties();
        } catch (PredictException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jtf_caminhoDadosConsumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtf_caminhoDadosConsumoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtf_caminhoDadosConsumoActionPerformed

    public void salvarAlteracoes() {
        try {

            PredictPropriedades.setCaminhoPlanilhaConsumo(jtf_caminhoDadosConsumo.getText());
            PredictPropriedades.setCaminhoPlanilhaClimaticos(jtf_dadosClimaticos.getText());
            PredictPropriedades.setCaminhoPlanilhaSocioEconomico(jtf_dadosSocioEconomico.getText());
            PredictPropriedades.setCaminhoPrincipal(jtf_principal.getText());
            // PredictPropriedades.setNomeLookAndFeel(String.valueOf(jcb_aparencia.getSelectedItem()));
            //       PredictPropriedades.setClasseLookAndFeel(BackPreferenciasGUI.getNomeClasseLookAndFeel(String.valueOf(jcb_aparencia.getSelectedItem())));

            // PredictPropriedades.setLookAndFeel();
            PredictPropriedades.gravaProperties();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_abrir0;
    private javax.swing.JButton btn_abrir1;
    private javax.swing.JButton btn_abrir2;
    private javax.swing.JButton btn_abrir3;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jtf_caminhoDadosConsumo;
    private javax.swing.JTextField jtf_dadosClimaticos;
    private javax.swing.JTextField jtf_dadosSocioEconomico;
    private javax.swing.JTextField jtf_principal;
    // End of variables declaration//GEN-END:variables
}
