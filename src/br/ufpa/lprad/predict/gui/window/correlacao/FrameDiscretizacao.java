/*
 * FrameDiscretizacao.java
 */
package br.ufpa.lprad.predict.gui.window.correlacao;

import br.ufpa.lprad.predict.actions.Action;
import br.ufpa.lprad.predict.correlacao.discretizacao.AtributoDiscreto;
import br.ufpa.lprad.predict.correlacao.discretizacao.Discretizacao;
import br.ufpa.lprad.predict.gui.window.correlacao.backstage.BackCorrelacaoGUI;
import br.ufpa.lprad.predict.gui.window.correlacao.backstage.BackDiscretizacaoGUI;
import br.ufpa.lprad.predict.gui.window.backstage.BordaPersonalizada;
import br.ufpa.lprad.predict.propriedades.PredictPropriedades;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.beans.PropertyVetoException;
import java.io.File;
import javax.swing.ButtonGroup;
import javax.swing.JTable;

/**
 *
 * @author Microsoft
 */
public class FrameDiscretizacao extends javax.swing.JInternalFrame {

    private String nomeAnalise;
    private ButtonGroup btgroup;
    private int nfaixa;
    private String atrib;
    private int indiceAtrib;
    private static JTable tabela;
    public static String nomAtributoAnalisado;

    public FrameDiscretizacao() {
    }

    public FrameDiscretizacao(String nome) {

        initComponents();
        painelFaixas.setLayout(new BorderLayout());
        this.nomeAnalise = nome;
        nomAnalise.setText(this.nomeAnalise);
        setBordaPainel();
        inicializaListaAtributos();
        radioButtonGroup();
        inicializar();

        inicializarAtributosDiscretizados();

    }

    /*
     *  
     */
    private void inicializaListaAtributos() {
        list_atributos.setListData(BackDiscretizacaoGUI.getListaAnalise(this.nomeAnalise));
        list_atributos.updateUI();
    }

    /*
     * Inicia o vetor de atributos ..todos estão com discretizacaso automatica inicialmente
     */
    private void inicializarAtributosDiscretizados() {

        Object[] valores = BackDiscretizacaoGUI.getListaAnalise(this.nomeAnalise);
        BackDiscretizacaoGUI.atribDisc = new AtributoDiscreto[valores.length];

        for (int i = 0; i < BackDiscretizacaoGUI.atribDisc.length; i++) {
            BackDiscretizacaoGUI.atribDisc[i] = new AtributoDiscreto();
            BackDiscretizacaoGUI.atribDisc[i].setNome(String.valueOf(valores[i]));
            BackDiscretizacaoGUI.atribDisc[i].setNFaixas(5);
            BackDiscretizacaoGUI.atribDisc[i].setFaixas(BackDiscretizacaoGUI.getFaixas(BackDiscretizacaoGUI.atribDisc[i].getNome(), i, BackDiscretizacaoGUI.atribDisc[i].getNFaixas()));
            BackDiscretizacaoGUI.atribDisc[i].setPersonalizado(false);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        rbtn_discretizacaoAutomatica = new javax.swing.JRadioButton();
        rbtn_discretizacaoPersonalizada = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txf_maiorValorDaBase = new javax.swing.JTextField();
        txf_menorValorDaBase = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        spnr_numDeFaixas = new javax.swing.JSpinner();
        btn_restaurarPadraoDeCincoFaixas = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        painelFaixas = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        nomAnalise = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        list_atributos = new javax.swing.JList();
        jPanel5 = new javax.swing.JPanel();
        btn_GerarBayesiana = new javax.swing.JButton();
        btn_fechaJanela = new javax.swing.JButton();
        btn_discretizarDadosDoConsumoSelecionado1 = new javax.swing.JButton();
        visualizar_arquivos_dados = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Discretização");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/predict.gif"))); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipo de Discretização"));

        rbtn_discretizacaoAutomatica.setSelected(true);
        rbtn_discretizacaoAutomatica.setText("Automática");
        rbtn_discretizacaoAutomatica.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rbtn_discretizacaoAutomaticaItemStateChanged(evt);
            }
        });

        rbtn_discretizacaoPersonalizada.setText("Personalizada");
        rbtn_discretizacaoPersonalizada.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rbtn_discretizacaoPersonalizadaItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbtn_discretizacaoAutomatica)
                .addGap(18, 18, 18)
                .addComponent(rbtn_discretizacaoPersonalizada)
                .addContainerGap(95, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtn_discretizacaoAutomatica)
                    .addComponent(rbtn_discretizacaoPersonalizada))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jLabel1.setText("Maior valor:");

        jLabel2.setText("Menor valor:");

        txf_maiorValorDaBase.setBackground(new java.awt.Color(236, 233, 216));

        txf_menorValorDaBase.setBackground(new java.awt.Color(236, 233, 216));

        jLabel3.setText("Nº de Faixas:");

        spnr_numDeFaixas.setFont(new java.awt.Font("Tahoma", 1, 11));
        spnr_numDeFaixas.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
        spnr_numDeFaixas.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnr_numDeFaixasStateChanged(evt);
            }
        });

        btn_restaurarPadraoDeCincoFaixas.setText("Restaura Padrão");
        btn_restaurarPadraoDeCincoFaixas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_restaurarPadraoDeCincoFaixasActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Definir Intervalos"));
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel4MouseExited(evt);
            }
        });

        painelFaixas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                painelFaixasMouseExited(evt);
            }
        });

        javax.swing.GroupLayout painelFaixasLayout = new javax.swing.GroupLayout(painelFaixas);
        painelFaixas.setLayout(painelFaixasLayout);
        painelFaixasLayout.setHorizontalGroup(
            painelFaixasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 287, Short.MAX_VALUE)
        );
        painelFaixasLayout.setVerticalGroup(
            painelFaixasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 229, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(painelFaixas);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnr_numDeFaixas, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(141, 141, 141))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txf_menorValorDaBase, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txf_maiorValorDaBase)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(198, Short.MAX_VALUE)
                        .addComponent(btn_restaurarPadraoDeCincoFaixas)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txf_menorValorDaBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txf_maiorValorDaBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(spnr_numDeFaixas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_restaurarPadraoDeCincoFaixas)
                .addContainerGap())
        );

        jLabel4.setText("Nome da Análise:");

        nomAnalise.setFont(new java.awt.Font("Tahoma", 1, 11));
        nomAnalise.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        list_atributos.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                list_atributosValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(list_atributos);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btn_GerarBayesiana.setText("Gerar Rede Bayesiana");
        btn_GerarBayesiana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_GerarBayesianaActionPerformed(evt);
            }
        });

        btn_fechaJanela.setText("Sair");
        btn_fechaJanela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_fechaJanelaActionPerformed(evt);
            }
        });

        btn_discretizarDadosDoConsumoSelecionado1.setText("Voltar");
        btn_discretizarDadosDoConsumoSelecionado1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_discretizarDadosDoConsumoSelecionado1ActionPerformed(evt);
            }
        });

        visualizar_arquivos_dados.setText("Visualizar Arquivo de Dados");
        visualizar_arquivos_dados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                visualizar_arquivos_dadosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_discretizarDadosDoConsumoSelecionado1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addComponent(visualizar_arquivos_dados)
                .addGap(28, 28, 28)
                .addComponent(btn_GerarBayesiana, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_fechaJanela, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_discretizarDadosDoConsumoSelecionado1)
                    .addComponent(btn_fechaJanela)
                    .addComponent(btn_GerarBayesiana)
                    .addComponent(visualizar_arquivos_dados))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jProgressBar1.setStringPainted(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(231, 231, 231))
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nomAnalise, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nomAnalise, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_GerarBayesianaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_GerarBayesianaActionPerformed

//        for (int i = 0; i < BackDiscretizacaoGUI.atribDisc.length; i++) {
//
//            System.out.println("\n\natribDisc[" + i + "].getNome(): " + BackDiscretizacaoGUI.atribDisc[i].getNome());
//            System.out.println("atribDisc[" + i + "].getNFaixas(): " + BackDiscretizacaoGUI.atribDisc[i].getNFaixas());
//            System.out.println("FAIXAS");
//            for (int j = 0; j < BackDiscretizacaoGUI.atribDisc[i].getFaixas().length; j++) {
//                System.out.println("atribDisc[" + i + "].getFaixas()[" + j + "].getLimiteInferior(): " + BackDiscretizacaoGUI.atribDisc[i].getFaixas()[j].getLimiteInferior());
//                System.out.println("atribDisc[" + i + "].getFaixas()[" + j + "].getLimiteSuperior(): " + BackDiscretizacaoGUI.atribDisc[i].getFaixas()[j].getLimiteSuperior());
//            }
//
//        }
        System.gc();
        new Thread() {

            public void run() {
                jProgressBar1.setVisible(true);
                jProgressBar1.setStringPainted(true);
                jProgressBar1.setString("Carregando...");
                jProgressBar1.setIndeterminate(true);
            }
        }.start();

//        new Thread() {
//
//            @Override
//            public void run() {
                BackCorrelacaoGUI.go(nomeAnalise);
//            }
//        }.start();


}//GEN-LAST:event_btn_GerarBayesianaActionPerformed

    private void btn_fechaJanelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_fechaJanelaActionPerformed
        fechar();
}//GEN-LAST:event_btn_fechaJanelaActionPerformed

    private void btn_restaurarPadraoDeCincoFaixasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_restaurarPadraoDeCincoFaixasActionPerformed
        inicializar();
    }//GEN-LAST:event_btn_restaurarPadraoDeCincoFaixasActionPerformed

    private void rbtn_discretizacaoPersonalizadaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rbtn_discretizacaoPersonalizadaItemStateChanged
        if (rbtn_discretizacaoPersonalizada.isSelected()) {
            habilita();
            BackDiscretizacaoGUI.atribDisc[list_atributos.getSelectedIndex()].setPersonalizado(true);
        }
    }//GEN-LAST:event_rbtn_discretizacaoPersonalizadaItemStateChanged

    private void rbtn_discretizacaoAutomaticaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rbtn_discretizacaoAutomaticaItemStateChanged
        if (rbtn_discretizacaoAutomatica.isSelected()) {
            desabilita();
            BackDiscretizacaoGUI.atribDisc[list_atributos.getSelectedIndex()].setPersonalizado(false);
        }
    }//GEN-LAST:event_rbtn_discretizacaoAutomaticaItemStateChanged

    private void btn_discretizarDadosDoConsumoSelecionado1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_discretizarDadosDoConsumoSelecionado1ActionPerformed
        try {
            Action.ActionfazerCorrelacao(evt);
            setClosed(true);
        } catch (PropertyVetoException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btn_discretizarDadosDoConsumoSelecionado1ActionPerformed

    private void list_atributosValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_list_atributosValueChanged

        nomAtributoAnalisado = String.valueOf(list_atributos.getSelectedValue());
        povoaListaFaixas();
    }//GEN-LAST:event_list_atributosValueChanged

    public void povoaListaFaixas() {

        if (list_atributos.getSelectedValue() != null) {
            int indice = list_atributos.getSelectedIndex();
            // se a discretizacao for automatica
            if (!BackDiscretizacaoGUI.atribDisc[indice].isPersonalizado()) {

                painelFaixas.removeAll();
                rbtn_discretizacaoAutomatica.setSelected(true);
                atrib = String.valueOf(list_atributos.getSelectedValue());
                indiceAtrib = list_atributos.getSelectedIndex();
                nfaixa = Integer.valueOf(String.valueOf(spnr_numDeFaixas.getValue()));
                tabela = BackDiscretizacaoGUI.getFaixasAtributo(atrib, indiceAtrib, nfaixa);

            } else {

                painelFaixas.removeAll();

                spnr_numDeFaixas.setValue(BackDiscretizacaoGUI.atribDisc[indice].getNFaixas());
                tabela = BackDiscretizacaoGUI.getFaixas(BackDiscretizacaoGUI.atribDisc[indice].getFaixas());
                rbtn_discretizacaoPersonalizada.setSelected(true);

            }


            txf_menorValorDaBase.setText("" + Discretizacao.getMenor());
            txf_maiorValorDaBase.setText("" + Discretizacao.getMaior());

            painelFaixas.add(tabela);
            painelFaixas.updateUI();
        }
    }

    private void spnr_numDeFaixasStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnr_numDeFaixasStateChanged
        if (list_atributos.getSelectedValue() != null) {
            atrib = String.valueOf(list_atributos.getSelectedValue());
            indiceAtrib = list_atributos.getSelectedIndex();

            nfaixa = Integer.valueOf(String.valueOf(spnr_numDeFaixas.getValue()));

            tabela = BackDiscretizacaoGUI.getFaixasAtributo(atrib, indiceAtrib, nfaixa);

            painelFaixas.removeAll();
            painelFaixas.add(tabela);
            painelFaixas.updateUI();

            BackDiscretizacaoGUI.atribDisc[indiceAtrib].setNFaixas(nfaixa);
            BackDiscretizacaoGUI.atribDisc[indiceAtrib].setFaixas(BackDiscretizacaoGUI.getFaixasTabela(tabela));

        }

    }//GEN-LAST:event_spnr_numDeFaixasStateChanged

    private void painelFaixasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_painelFaixasMouseExited
    }//GEN-LAST:event_painelFaixasMouseExited

    private void jPanel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseExited

        if (list_atributos.getSelectedValue() != null) {
            System.out.println("" + list_atributos.getSelectedValue());
            BackDiscretizacaoGUI.atribDisc[list_atributos.getSelectedIndex()].setNFaixas(Integer.valueOf(String.valueOf(spnr_numDeFaixas.getValue())));
            BackDiscretizacaoGUI.atribDisc[list_atributos.getSelectedIndex()].setFaixas(BackDiscretizacaoGUI.getFaixasTabela(tabela));
        }
    }//GEN-LAST:event_jPanel4MouseExited

    private void visualizar_arquivos_dadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_visualizar_arquivos_dadosActionPerformed
        try {
            Desktop.getDesktop().open(new File(PredictPropriedades.getCaminhoCorrelacao()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
}//GEN-LAST:event_visualizar_arquivos_dadosActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_GerarBayesiana;
    private javax.swing.JButton btn_discretizarDadosDoConsumoSelecionado1;
    private javax.swing.JButton btn_fechaJanela;
    private javax.swing.JButton btn_restaurarPadraoDeCincoFaixas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    public static final javax.swing.JProgressBar jProgressBar1 = new javax.swing.JProgressBar();
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JList list_atributos;
    private javax.swing.JLabel nomAnalise;
    private javax.swing.JPanel painelFaixas;
    public static javax.swing.JRadioButton rbtn_discretizacaoAutomatica;
    public static javax.swing.JRadioButton rbtn_discretizacaoPersonalizada;
    private javax.swing.JSpinner spnr_numDeFaixas;
    private javax.swing.JTextField txf_maiorValorDaBase;
    private javax.swing.JTextField txf_menorValorDaBase;
    private javax.swing.JButton visualizar_arquivos_dados;
    // End of variables declaration//GEN-END:variables

    private void setBordaPainel() {
        jPanel1.setBorder(new BordaPersonalizada("Consumo",
                new Color(0x418EDC), new Color(0x6B91B8), Color.WHITE, 16));
        jPanel2.setBorder(new BordaPersonalizada("Configuração",
                new Color(0x418EDC), new Color(0x6B91B8), Color.WHITE, 16));
        jPanel1.updateUI();
    }

    private void radioButtonGroup() {
        btgroup = new ButtonGroup();
        btgroup.add(rbtn_discretizacaoAutomatica);
        btgroup.add(rbtn_discretizacaoPersonalizada);

    }

    void fechar() {
        /*Falta verificar se a pessoa realmente deseja sair ou se ela quer discretizar outra correlação
        Se ela quiser discretizar outra, volta pro frame da Correlação*/
        try {
            setClosed(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void inicializar() {
        /*Apenas apresenta o maior e menor valore da base sem permitir edição*/
        txf_maiorValorDaBase.setEditable(false);
        txf_menorValorDaBase.setEditable(false);

        rbtn_discretizacaoAutomatica.setSelected(true);
        desabilita();

    }

    /*Para o Spinner*/
    void habilita() {
        spnr_numDeFaixas.setEnabled(true);
        spnr_numDeFaixas.setValue(BackDiscretizacaoGUI.atribDisc[list_atributos.getSelectedIndex()].getNFaixas());
        painelFaixas.setEnabled(true);
    }

    void desabilita() {
        spnr_numDeFaixas.setEnabled(false);
        spnr_numDeFaixas.setValue(5);
        for (Component c : painelFaixas.getComponents()) {
            c.setEnabled(false);
        }
    }
}
