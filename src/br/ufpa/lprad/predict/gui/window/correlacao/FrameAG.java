/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FrameAG.java
 *
 * Created on 30/08/2009, 18:23:17
 */
package br.ufpa.lprad.predict.gui.window.correlacao;

import br.ufpa.lprad.predict.actions.Actions_Rede;
import br.ufpa.lprad.predict.gui.window.correlacao.backstage.BackAG;
import br.ufpa.lprad.predict.redebayesiana.algoritmogenetico.AG;
import br.ufpa.lprad.predict.redebayesiana.aprendizado.K2;
import br.ufpa.lprad.predict.redebayesiana.aprendizado.Vertice;
import br.ufpa.lprad.predict.redebayesiana.grafo.Tela;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author gabriel
 */
public class FrameAG extends javax.swing.JInternalFrame {

    public AG ag;
    public int indice;
    public Thread t;
    public String nom_faixa;

    public FrameAG() {
        initComponents();
        comboFaixas.setModel(new DefaultComboBoxModel(BackAG.obtemFaixasDoAtributo(procuraIndice())));
        setVisible(true);
        setLayer(100);
    }

    int procuraIndice() {
        Vertice v = K2.getRedeBayesiana().getVerticeByNome(Tela.selecionados.get(0).getNome());
        indice = K2.getRedeBayesiana().getIndiceByVertice(v);

        return indice;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        comboFaixas = new javax.swing.JComboBox();
        btn_iniciar = new javax.swing.JButton();
        btn_iniciar1 = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        status = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Análise de Cenários");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/celpa_logo copy.gif"))); // NOI18N
        setVisible(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Selecionar Faixa"));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboFaixas, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(comboFaixas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btn_iniciar.setText("Iniciar");
        btn_iniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_iniciarActionPerformed(evt);
            }
        });

        btn_iniciar1.setText("Fechar");
        btn_iniciar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_iniciar1ActionPerformed(evt);
            }
        });

        status.setFont(new java.awt.Font("Tahoma", 1, 11));
        status.setText(" ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(btn_iniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(btn_iniciar1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_iniciar)
                    .addComponent(btn_iniciar1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(status))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_iniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_iniciarActionPerformed
        btn_iniciar.setEnabled(false);
        int faixa = comboFaixas.getSelectedIndex();
        nom_faixa = (String) comboFaixas.getSelectedItem();
        ag = new AG(indice, faixa,nom_faixa);
        Actions_Rede.vizualiza_relatorio = false;
        jProgressBar1.setIndeterminate(true);
        t = new Thread() {

            @Override
            public void run() {
                
                ag.run();

            }
        };
        t.start();


    }//GEN-LAST:event_btn_iniciarActionPerformed

    private void btn_iniciar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_iniciar1ActionPerformed
        try {
            try {
                ag.interrupt();
                ag.stop();
                t.interrupt();
            } catch (Exception e) {
            }
            setClosed(true);
            Actions_Rede.vizualiza_relatorio = true;
        } catch (PropertyVetoException ex) {
            Logger.getLogger(FrameAG.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_iniciar1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_iniciar;
    private javax.swing.JButton btn_iniciar1;
    private javax.swing.JComboBox comboFaixas;
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JProgressBar jProgressBar1;
    public static javax.swing.JLabel status;
    // End of variables declaration//GEN-END:variables
}
