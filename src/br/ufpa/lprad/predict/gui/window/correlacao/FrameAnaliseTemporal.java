/*
 * FrameAnaliseTemporal.java
 *
 * Created on 15 de Setembro de 2008, 15:18
 */

package br.ufpa.lprad.predict.gui.window.correlacao;


import br.ufpa.lprad.predict.actions.Actions_Rede;
import br.ufpa.lprad.predict.redebayesiana.analisetemporal.PreparaAnalise;
import br.ufpa.lprad.predict.redebayesiana.aprendizado.K2;
import br.ufpa.lprad.predict.redebayesiana.aprendizado.Vertice;
import br.ufpa.lprad.predict.redebayesiana.inferencias.Discretizar;
import java.beans.PropertyVetoException;
import java.text.DecimalFormat;

/**
 *
 * @author Pedro Vitor
 */
public class FrameAnaliseTemporal extends javax.swing.JInternalFrame {
    
    private Vertice vertice,            //vertice estudado
                   paiInferenciado;     //pai inferenciado
    private String estado;              //estado do pai inferenciado
    private int tempo;                  //tempo para analise
    private PreparaAnalise preparador;  //para preparar os dados parar a analise
    
    public FrameAnaliseTemporal(Vertice vertice) {
        initComponents();
        this.vertice = vertice;
        String meses[] = new String[24];
        for(int i=0; i<meses.length;i++)
            meses[i]=String.valueOf(i+1);
            
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(meses));
        
        
    }
  
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setTitle("Análise temporal Markoviana");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/predict.gif"))); // NOI18N
        setLayer(10);
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        setVisible(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Escolha o número de meses"));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox1, 0, 213, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addContainerGap(71, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(15, Short.MAX_VALUE))
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
        fazAnalise();
        try {
            setClosed(true);
        } catch (PropertyVetoException ex) {
            ex.printStackTrace();
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        tempo = Integer.parseInt((String) evt.getItem());
//        jComboBox3.setEnabled(true);
    }//GEN-LAST:event_jComboBox1ItemStateChanged

private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_jComboBox1ActionPerformed
    
    private void setPaiInferenciado(int indice){
        if(indice != -1){
            String estados[] = new String[5];
            
            Vertice pai = ((Vertice)vertice.getPais().elementAt(indice));
            int i;
            for(i=0; i<K2.getRedeBayesiana().getVertices().size(); i++)
                if( ((Vertice)K2.getRedeBayesiana().getVertices().elementAt(i)).getNome().equalsIgnoreCase( pai.getNome() ) )
                    break;
            
            for(int j=0; j<5; j++)
                estados[j] = Discretizar.getFxInf(i, j);
            
            paiInferenciado = pai;
//            jComboBox4.setModel(new DefaultComboBoxModel(estados));
//            jComboBox4.setEnabled(true);
            estado = "0";
        }
//        else
//            jComboBox4.setEnabled(false);
        
    }


    private void fazAnalise(){
        preparador = new PreparaAnalise(vertice);
        double probs[] = preparador.fazAnalise(tempo, paiInferenciado, estado);
        int i;
        for(i=0; i<K2.getRedeBayesiana().getVertices().size(); i++)
            if( ((Vertice)K2.getRedeBayesiana().getVertices().elementAt(i)).getNome().equalsIgnoreCase( vertice.getNome() ) )
                break;
        
        String estadosVertice[] = new String[5];
        for(int j=0; j<5; j++)
                estadosVertice[j] = Discretizar.getFxInf(i, j);
        
        StringBuilder texto = new StringBuilder();
        texto.append("<br><br>A probabilidade do vertice <strong>");
        texto.append(corrigeCofificacao(vertice.getNome()));
        texto.append("</strong> em "+jComboBox1.getSelectedItem()+" meses estar no estado:<br><br>");
        for(int j=0; j<probs.length; j++){
            texto.append("<strong>"+estadosVertice[j]+"</strong>");
            texto.append("    "+corrigeCofificacao("é")+  " de    ");
            DecimalFormat Converte = new DecimalFormat("0.00");
            double aux = probs[j]*100;
            texto.append("<strong>"+Converte.format(aux)+"%</strong>");
            texto.append("<br>");
        }

        Actions_Rede.mostrarRelatorioAnaliseTemporal(texto.toString());

//        System.out.println("total = "+total);
//        JOptionPane.showMessageDialog(null,texto.toString(),"Análise Temporal Markoviana",JOptionPane.PLAIN_MESSAGE);
    }    
        private static String corrigeCofificacao(String nome) {

        String aux = "";
        for (int i = 0; i < nome.length(); i++) {
            if (nome.charAt(i) == 'á') {
                aux += "&aacute;";
            } else if (nome.charAt(i) == 'ã') {
                aux += "&atilde;";
            } else if (nome.charAt(i) == 'ç') {
                aux += "&ccedil;";
            } else if (nome.charAt(i) == 'ú') {
                aux += "&uacute;";
            } else if(nome.charAt(i)=='é'){
                aux+="&eacute;";
            }else{
                aux += nome.charAt(i);
            }
        }
        return aux;

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
    
}
