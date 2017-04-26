package br.ufpa.lprad.predict.gui.window.previsao;

import br.ufpa.lprad.predict.exception.PredictException;
import br.ufpa.lprad.predict.gui.window.ComponentTitledBorder;
import br.ufpa.lprad.predict.gui.window.previsao.backstage.BackPrevisaoGUI;
import br.ufpa.lprad.predict.previsao.bean.PrevisaoBean;
import br.ufpa.lprad.predict.previsao.taxadecrescimento.ThreadGeraPrevisao;
import br.ufpa.lprad.predict.previsao.multipla.ThreadGeraPrevisaoMultipla;
import br.ufpa.lprad.predict.previsao.neural.ThreadGeraPrevisaoRNA;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

/**
 *
 * @author J. Gabriel Lima
 */
public class PrevisaoGUI extends javax.swing.JInternalFrame {

    private static String anoIni;
    private static String anoFim;
    private static String mesIni;
    private static String mesFim;
    private static String nomeConsumo;              //nome do consumo a ser previsto
    private static String nomePlanilha;             //nome da planilha a ser lida
    private static String labelTipoConsumo;         //como o nome do consumo deve aparecer nos frames
    private static String tipoConsumo;              //se a previsao eh celpa total, interligado ou isolado
    private static String unidadeConsumo;
    private static String anosSelecionados;
    private static String nomeLabelPrevisao;      //nome da previsao realizada
    private static String labelEixoY;
    private static int mesIniInt;
    private static int mesFimInt;
    PrevisaoBean prevBean;
    public static JCheckBox[] boxs;
    private ButtonGroup bg;

    /** Creates new form PrevisaoGUI */
    public PrevisaoGUI() throws PredictException {
        initComponents();
        setVisible(true);
        inicializaCampos();
        panelAnosPrevisao.setLayout(new java.awt.GridLayout(0, 1));
        bg = new ButtonGroup();
        jRb_regressaoSimples.setVisible(true);
        jRb_regressaoSimples.setSelected(true);
//        jRB_rna.setEnabled(false);
        bg.add(jRb_regressaoSimples);
        bg.add(jRb_regressaoMultipla);
        bg.add(jRB_rna);

    }

    private void inicializaCampos() throws PredictException {


        cmbxTipoDeConsumo.setModel(new DefaultComboBoxModel(BackPrevisaoGUI.getTipoConsumo()));
        cmbxNomeConsumo.setModel(new DefaultComboBoxModel(BackPrevisaoGUI.getConsumo(String.valueOf(cmbxTipoDeConsumo.getSelectedItem()))));
        boxs = BackPrevisaoGUI.getAnosJChBox(String.valueOf(cmbxTipoDeConsumo.getSelectedItem()));
        for (JCheckBox box : boxs) {
            panelAnosPrevisao.add(box);
        }
        panelAnosPrevisao.updateUI();

        cmbxAnoIni.setModel(new DefaultComboBoxModel(BackPrevisaoGUI.getAnosPrevisao()));
        cmbxAnoFim.setModel(new DefaultComboBoxModel(BackPrevisaoGUI.getAnosPrevisao()));
        desabilitaVarExogena();
        updateUI();
        initVarExogena();

    }

    public void initVarExogena() {
        /**
         *
         */
        final JCheckBox checkBox = new JCheckBox("Escolher arquivo com as variáveis exógenas", false);
        checkBox.setFocusPainted(false);
        ComponentTitledBorder componentBorder = new ComponentTitledBorder(checkBox, jl_varExogena, BorderFactory.createEtchedBorder());
        new Thread() {

            public void run() {
                Component comp[] = jl_varExogena.getComponents();
                for (int i = 0; i < comp.length; i++) {
                    comp[i].setEnabled(false);
                }
            }
        }.start();
        /**
         * 
         */
        checkBox.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                final boolean enable = checkBox.isSelected();
                new Thread() {

                    public void run() {
                        Component comp[] = jl_varExogena.getComponents();
                        for (int i = 0; i < comp.length; i++) {
                            comp[i].setEnabled(enable);
                        }
                    }
                }.start();

            }
        });
        jl_varExogena.setBorder(componentBorder);
        jl_varExogena.updateUI();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelParametros = new javax.swing.JPanel();
        cmbxNomeConsumo = new javax.swing.JComboBox();
        cmbxTipoDeConsumo = new javax.swing.JComboBox();
        lblLivres = new javax.swing.JLabel();
        scrollAnosPrev = new javax.swing.JScrollPane();
        panelAnosPrevisao = new javax.swing.JPanel();
        jCBMarcarTodos = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        panelPeriodo = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cmbxMesIni = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cmbxMesFim = new javax.swing.JComboBox();
        cmbxAnoIni = new javax.swing.JComboBox();
        cmbxAnoFim = new javax.swing.JComboBox();
        btnGerarGraf = new javax.swing.JButton();
        btnFechar = new javax.swing.JButton();
        jl_varExogena = new javax.swing.JPanel();
        jtf_varExogena = new javax.swing.JTextField();
        btn_varExogena = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jRb_regressaoSimples = new javax.swing.JRadioButton();
        jRb_regressaoMultipla = new javax.swing.JRadioButton();
        jRB_rna = new javax.swing.JRadioButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Previsão");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/predict.gif"))); // NOI18N
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        setVisible(true);

        panelParametros.setBorder(javax.swing.BorderFactory.createTitledBorder("Parâmetros da Previsão"));
        panelParametros.setLayout(null);

        cmbxNomeConsumo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbxNomeConsumoItemStateChanged(evt);
            }
        });
        cmbxNomeConsumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbxNomeConsumoActionPerformed(evt);
            }
        });
        cmbxNomeConsumo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbxNomeConsumoKeyPressed(evt);
            }
        });
        panelParametros.add(cmbxNomeConsumo);
        cmbxNomeConsumo.setBounds(10, 110, 240, 20);

        cmbxTipoDeConsumo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbxTipoDeConsumoItemStateChanged(evt);
            }
        });
        panelParametros.add(cmbxTipoDeConsumo);
        cmbxTipoDeConsumo.setBounds(10, 50, 240, 20);

        lblLivres.setText("Tipo de Consumo:");
        panelParametros.add(lblLivres);
        lblLivres.setBounds(10, 30, 180, 14);

        scrollAnosPrev.setBackground(new java.awt.Color(255, 255, 255));
        scrollAnosPrev.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(204, 204, 204), null));
        scrollAnosPrev.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollAnosPrev.setAutoscrolls(true);

        panelAnosPrevisao.setBackground(new java.awt.Color(255, 255, 255));
        panelAnosPrevisao.setLayout(new java.awt.GridLayout(1, 0));
        scrollAnosPrev.setViewportView(panelAnosPrevisao);

        panelParametros.add(scrollAnosPrev);
        scrollAnosPrev.setBounds(300, 20, 190, 170);

        jCBMarcarTodos.setSelected(true);
        jCBMarcarTodos.setText("Marcar Todos");
        jCBMarcarTodos.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCBMarcarTodos.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jCBMarcarTodos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCBMarcarTodosMouseClicked(evt);
            }
        });
        panelParametros.add(jCBMarcarTodos);
        jCBMarcarTodos.setBounds(300, 200, 120, 15);

        jLabel1.setText("Nome do Consumo:");
        panelParametros.add(jLabel1);
        jLabel1.setBounds(10, 90, 130, 14);

        panelPeriodo.setBorder(javax.swing.BorderFactory.createTitledBorder("Período da Previsão"));
        panelPeriodo.setLayout(null);

        jLabel6.setText("Ano Final:");
        panelPeriodo.add(jLabel6);
        jLabel6.setBounds(270, 70, 70, 14);

        jLabel3.setText("Mês Inicial:");
        panelPeriodo.add(jLabel3);
        jLabel3.setBounds(10, 20, 120, 14);

        cmbxMesIni.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- ESCOLHA UM PERÍODO DE PREVISÃO --", "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro" }));
        cmbxMesIni.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbxMesIniItemStateChanged(evt);
            }
        });
        cmbxMesIni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbxMesIniKeyPressed(evt);
            }
        });
        panelPeriodo.add(cmbxMesIni);
        cmbxMesIni.setBounds(10, 40, 230, 20);

        jLabel5.setText("Ano Inicial:");
        panelPeriodo.add(jLabel5);
        jLabel5.setBounds(270, 20, 70, 14);

        jLabel4.setText("Mês Final:");
        panelPeriodo.add(jLabel4);
        jLabel4.setBounds(10, 70, 120, 14);

        cmbxMesFim.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- ESCOLHA UM PERÍODO DE PREVISÃO --", "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro" }));
        cmbxMesFim.setEnabled(false);
        cmbxMesFim.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbxMesFimItemStateChanged(evt);
            }
        });
        cmbxMesFim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbxMesFimKeyPressed(evt);
            }
        });
        panelPeriodo.add(cmbxMesFim);
        cmbxMesFim.setBounds(10, 90, 229, 20);

        cmbxAnoIni.setEnabled(false);
        cmbxAnoIni.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbxAnoIniItemStateChanged(evt);
            }
        });
        panelPeriodo.add(cmbxAnoIni);
        cmbxAnoIni.setBounds(270, 40, 80, 20);

        cmbxAnoFim.setEnabled(false);
        cmbxAnoFim.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbxAnoFimItemStateChanged(evt);
            }
        });
        panelPeriodo.add(cmbxAnoFim);
        cmbxAnoFim.setBounds(270, 90, 80, 20);

        btnGerarGraf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gpl/gnome-monitor.png"))); // NOI18N
        btnGerarGraf.setText("Gerar Previsão");
        btnGerarGraf.setEnabled(false);
        btnGerarGraf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarGrafActionPerformed(evt);
            }
        });

        btnFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gpl/dialog-close.png"))); // NOI18N
        btnFechar.setText("Fechar");
        btnFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharActionPerformed(evt);
            }
        });

        jl_varExogena.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Escolher Arquivo com as variáveis exógenas: ")));

        btn_varExogena.setText("...");
        btn_varExogena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_varExogenaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jl_varExogenaLayout = new javax.swing.GroupLayout(jl_varExogena);
        jl_varExogena.setLayout(jl_varExogenaLayout);
        jl_varExogenaLayout.setHorizontalGroup(
            jl_varExogenaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jl_varExogenaLayout.createSequentialGroup()
                .addComponent(jtf_varExogena, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btn_varExogena, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(69, Short.MAX_VALUE))
        );
        jl_varExogenaLayout.setVerticalGroup(
            jl_varExogenaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtf_varExogena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btn_varExogena, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Modelo"));

        jRb_regressaoSimples.setText("Análise da Taxa de Crescimento");
        jRb_regressaoSimples.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRb_regressaoSimplesItemStateChanged(evt);
            }
        });

        jRb_regressaoMultipla.setText("Regressão Multivariada");
        jRb_regressaoMultipla.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRb_regressaoMultiplaItemStateChanged(evt);
            }
        });
        jRb_regressaoMultipla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRb_regressaoMultiplaActionPerformed(evt);
            }
        });

        jRB_rna.setText("Rede Neural Artificial");
        jRB_rna.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRB_rnaItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jRb_regressaoSimples)
                .addGap(18, 18, 18)
                .addComponent(jRb_regressaoMultipla)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(jRB_rna)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRb_regressaoSimples)
                    .addComponent(jRB_rna)
                    .addComponent(jRb_regressaoMultipla))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelPeriodo, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelParametros, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jl_varExogena, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addComponent(btnGerarGraf)
                        .addGap(18, 18, 18)
                        .addComponent(btnFechar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelParametros, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jl_varExogena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGerarGraf)
                    .addComponent(btnFechar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbxNomeConsumoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbxNomeConsumoItemStateChanged
}//GEN-LAST:event_cmbxNomeConsumoItemStateChanged

    private void cmbxNomeConsumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbxNomeConsumoActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_cmbxNomeConsumoActionPerformed

    private void cmbxNomeConsumoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbxNomeConsumoKeyPressed
}//GEN-LAST:event_cmbxNomeConsumoKeyPressed

    private void jCBMarcarTodosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCBMarcarTodosMouseClicked
        if (!jCBMarcarTodos.isSelected()) {
            BackPrevisaoGUI.desmarcarTodos(panelAnosPrevisao);
        } else {
            BackPrevisaoGUI.marcarTodos(panelAnosPrevisao);
        }
}//GEN-LAST:event_jCBMarcarTodosMouseClicked

    private void cmbxMesIniItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbxMesIniItemStateChanged
        if (!String.valueOf(cmbxMesIni.getSelectedItem()).equalsIgnoreCase("-- ESCOLHA UM PERÍODO DE PREVISÃO --")) {
            cmbxAnoIni.setEnabled(true);
        } else {
            cmbxAnoIni.setEnabled(false);
            btnGerarGraf.setEnabled(false);
        }

}//GEN-LAST:event_cmbxMesIniItemStateChanged

    private void cmbxMesIniKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbxMesIniKeyPressed
}//GEN-LAST:event_cmbxMesIniKeyPressed
    private void cmbxMesFimKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbxMesFimKeyPressed
}//GEN-LAST:event_cmbxMesFimKeyPressed
    private void btnGerarGrafActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarGrafActionPerformed
        try {

            if (!jtf_varExogena.getText().isEmpty()) {
                BackPrevisaoGUI.setExogena(true);
            }

            if (jRb_regressaoSimples.isSelected()) {
                gerarPrevisao();
            } else if (jRb_regressaoMultipla.isSelected()) {
                gerarPrevisaoMultipla();
            } else if (jRB_rna.isSelected()) {
//                if (Boolean.valueOf(PredictPropriedades.getRedeTreinada())) {
                gerarPrevisaoRNA();
//                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
}//GEN-LAST:event_btnGerarGrafActionPerformed

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        // TODO add your handling code here:
        try {
            setClosed(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
}//GEN-LAST:event_btnFecharActionPerformed

    private void cmbxTipoDeConsumoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbxTipoDeConsumoItemStateChanged

        try {

            cmbxNomeConsumo.setModel(new DefaultComboBoxModel(BackPrevisaoGUI.getConsumo(String.valueOf(cmbxTipoDeConsumo.getSelectedItem()))));
            updateUI();
        } catch (PredictException ex) {
            ex.printStackTrace();
        }

    }//GEN-LAST:event_cmbxTipoDeConsumoItemStateChanged

    private void cmbxAnoIniItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbxAnoIniItemStateChanged
        if (!String.valueOf(cmbxAnoIni.getSelectedItem()).trim().isEmpty()&&!jRB_rna.isSelected()) {
            cmbxMesFim.setEnabled(true);
        } else {
            cmbxMesFim.setEnabled(false);
            btnGerarGraf.setEnabled(false);
        }
    }//GEN-LAST:event_cmbxAnoIniItemStateChanged

    private void cmbxMesFimItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbxMesFimItemStateChanged
        if (!String.valueOf(cmbxMesFim.getSelectedItem()).equalsIgnoreCase("-- ESCOLHA UM PERÍODO DE PREVISÃO --")) {
            cmbxAnoFim.setEnabled(true);
        } else {
            cmbxAnoFim.setEnabled(false);
            btnGerarGraf.setEnabled(false);
        }
    }//GEN-LAST:event_cmbxMesFimItemStateChanged

    private void cmbxAnoFimItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbxAnoFimItemStateChanged
        if (!String.valueOf(cmbxAnoFim.getSelectedItem()).trim().isEmpty()) {
            btnGerarGraf.setEnabled(true);
        } else {
            btnGerarGraf.setEnabled(false);
        }
    }//GEN-LAST:event_cmbxAnoFimItemStateChanged

    private void jRb_regressaoMultiplaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRb_regressaoMultiplaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRb_regressaoMultiplaActionPerformed

    private void btn_varExogenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_varExogenaActionPerformed

        BackPrevisaoGUI.abrirArquivoExogenas();
//        JFileChooser escolher = new JFileChooser(new File(System.getProperty("user.home")));
//
//        File f = null;
//        int result = escolher.showOpenDialog(this);
//        if (result == JFileChooser.APPROVE_OPTION) {
//            f = escolher.getSelectedFile();
//            BackPrevisaoGUI.setArquivoExogenas(f);
//            jtf_varExogena.setText(f.getAbsolutePath());
//        }
}//GEN-LAST:event_btn_varExogenaActionPerformed

    private void jRb_regressaoSimplesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRb_regressaoSimplesItemStateChanged

        if (jRb_regressaoSimples.isSelected()) {
            desabilitaVarExogena();
            desabilitaRNA();
            jtf_varExogena.setText("");
            BackPrevisaoGUI.setArquivoExogenas(null);
            BackPrevisaoGUI.setExogena(false);
            BackPrevisaoGUI.setMatrizExogena(null);
        }
        updateUI();

    }//GEN-LAST:event_jRb_regressaoSimplesItemStateChanged

    private void jRb_regressaoMultiplaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRb_regressaoMultiplaItemStateChanged
        if (jRb_regressaoMultipla.isSelected()) {
            habilitaVarExogena();
            desabilitaRNA();
        }
        updateUI();
    }//GEN-LAST:event_jRb_regressaoMultiplaItemStateChanged

    private void jRB_rnaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRB_rnaItemStateChanged
        if (jRB_rna.isSelected()) {
            desabilitaVarExogena();
            habilitaRNA();
        }
        updateUI();
    }//GEN-LAST:event_jRB_rnaItemStateChanged

    void habilitaRNA() {
        panelAnosPrevisao.setEnabled(false);
        jCBMarcarTodos.setEnabled(false);
        jLabel3.setEnabled(false);
        cmbxMesIni.setEnabled(false);
        cmbxMesFim.setEnabled(false);
        //
        cmbxAnoIni.setEnabled(true);
        cmbxAnoFim.setEnabled(true);
        updateUI();
    }

    void desabilitaRNA() {
        panelAnosPrevisao.setEnabled(true);
        jCBMarcarTodos.setEnabled(true);
        jLabel3.setEnabled(true);
        cmbxMesIni.setEnabled(true);
        cmbxMesFim.setEnabled(true);
        updateUI();
    }

    void habilitaVarExogena() {
        jl_varExogena.setVisible(true);
        jtf_varExogena.setVisible(true);
        btn_varExogena.setVisible(true);
    }

    void desabilitaVarExogena() {
        jl_varExogena.setVisible(false);
        jtf_varExogena.setVisible(false);
        btn_varExogena.setVisible(false);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnGerarGraf;
    private javax.swing.JButton btn_varExogena;
    private javax.swing.JComboBox cmbxAnoFim;
    private javax.swing.JComboBox cmbxAnoIni;
    private javax.swing.JComboBox cmbxMesFim;
    private javax.swing.JComboBox cmbxMesIni;
    public static javax.swing.JComboBox cmbxNomeConsumo;
    public static javax.swing.JComboBox cmbxTipoDeConsumo;
    public static javax.swing.JCheckBox jCBMarcarTodos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRB_rna;
    private javax.swing.JRadioButton jRb_regressaoMultipla;
    private javax.swing.JRadioButton jRb_regressaoSimples;
    private javax.swing.JPanel jl_varExogena;
    public static javax.swing.JTextField jtf_varExogena;
    private javax.swing.JLabel lblLivres;
    private javax.swing.JPanel panelAnosPrevisao;
    private javax.swing.JPanel panelParametros;
    private javax.swing.JPanel panelPeriodo;
    private javax.swing.JScrollPane scrollAnosPrev;
    // End of variables declaration//GEN-END:variables

    private void gerarPrevisaoRNA() {
        try {

            tipoConsumo = String.valueOf(cmbxTipoDeConsumo.getSelectedItem());
            nomePlanilha = String.valueOf(cmbxTipoDeConsumo.getSelectedItem());
            nomeConsumo = String.valueOf(cmbxNomeConsumo.getSelectedItem());
            labelTipoConsumo = String.valueOf(cmbxTipoDeConsumo.getSelectedItem());
            anoIni = null;
            anoFim = null;
            mesIni = null;
            mesFim = null;
            unidadeConsumo = null;
            labelEixoY = null;
            nomeLabelPrevisao = null;
            anosSelecionados = "";
            for (String ano : BackPrevisaoGUI.getAnosSelecionados(panelAnosPrevisao)) {
                anosSelecionados += ano + "#";
            }
            //obtem o valor do ano inicial assinalado
            anoIni = (String) cmbxAnoIni.getSelectedItem();
            //obtem o valor do ano final assinalado
            anoFim = (String) cmbxAnoFim.getSelectedItem();
            //obtem o valor do mes inicial assinalado
            mesIni = (String) cmbxMesIni.getSelectedItem();
            //obtem o valor do ano final assinalado
            mesFim = (String) cmbxMesFim.getSelectedItem();
            //obtem o numero do mes inicial selecionado
            mesIniInt = cmbxMesIni.getSelectedIndex();
            //obtem o numero do mes final selecionado
            mesFimInt = cmbxMesFim.getSelectedIndex();
            try {
//                if (verificarLimiteAnos()) {
                PrevisaoBean.setAnoFim(Integer.parseInt(anoFim));
                PrevisaoBean.setAnoIni(Integer.parseInt(anoIni));
                PrevisaoBean.setMesFim(mesFim);
                PrevisaoBean.setMesIni(mesIni);
                PrevisaoBean.setLabelConsumo(labelTipoConsumo);
                PrevisaoBean.setNomeConsumo(nomeConsumo);
                PrevisaoBean.setNomePlanilha(tipoConsumo);
                PrevisaoBean.setTipoConsumo(tipoConsumo);
                PrevisaoBean.setUnidadeConsumo(unidadeConsumo);
                PrevisaoBean.setAnosSelecionados(anosSelecionados);
                PrevisaoBean.setLabelEixoY(labelEixoY);
                PrevisaoBean.setTipoCons(nomeLabelPrevisao);

                ThreadGeraPrevisaoRNA geraPrevisao = new ThreadGeraPrevisaoRNA();
                geraPrevisao.start();
//                }
            } catch (NumberFormatException nfex) {
                nfex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Para que a previsão seja realizada,\né necessário que o campo ano inicial\ne o campo final estejam preenchidos.", "Atenção.", JOptionPane.WARNING_MESSAGE);
            }
        } catch (PredictException ex) {
            ex.printStackTrace();
        }
    }

    private void gerarPrevisao() {

        try {

            tipoConsumo = String.valueOf(cmbxTipoDeConsumo.getSelectedItem());
            nomePlanilha = String.valueOf(cmbxTipoDeConsumo.getSelectedItem());
            nomeConsumo = String.valueOf(cmbxNomeConsumo.getSelectedItem());
            labelTipoConsumo = String.valueOf(cmbxTipoDeConsumo.getSelectedItem());
            anoIni = null;
            anoFim = null;
            mesIni = null;
            mesFim = null;
            unidadeConsumo = null;
            labelEixoY = null;
            nomeLabelPrevisao = null;
            anosSelecionados = "";
            for (String ano : BackPrevisaoGUI.getAnosSelecionados(panelAnosPrevisao)) {
                anosSelecionados += ano + "#";
            }
            //obtem o valor do ano inicial assinalado
            anoIni = (String) cmbxAnoIni.getSelectedItem();
            //obtem o valor do ano final assinalado
            anoFim = (String) cmbxAnoFim.getSelectedItem();
            //obtem o valor do mes inicial assinalado
            mesIni = (String) cmbxMesIni.getSelectedItem();
            //obtem o valor do ano final assinalado
            mesFim = (String) cmbxMesFim.getSelectedItem();
            //obtem o numero do mes inicial selecionado
            mesIniInt = cmbxMesIni.getSelectedIndex();
            //obtem o numero do mes final selecionado
            mesFimInt = cmbxMesFim.getSelectedIndex();
            try {
//                if (verificarLimiteAnos()) {
                PrevisaoBean.setAnoFim(Integer.parseInt(anoFim));
                PrevisaoBean.setAnoIni(Integer.parseInt(anoIni));
                PrevisaoBean.setMesFim(mesFim);
                PrevisaoBean.setMesIni(mesIni);
                PrevisaoBean.setLabelConsumo(labelTipoConsumo);
                PrevisaoBean.setNomeConsumo(nomeConsumo);
                //  PrevisaoBean.setNomeInterligado(nomeInterligado);
                PrevisaoBean.setNomePlanilha(nomePlanilha);
                PrevisaoBean.setTipoConsumo(tipoConsumo);
                PrevisaoBean.setUnidadeConsumo(unidadeConsumo);
                PrevisaoBean.setAnosSelecionados(anosSelecionados);
                PrevisaoBean.setLabelEixoY(labelEixoY);
                PrevisaoBean.setTipoCons(nomeLabelPrevisao);

                ThreadGeraPrevisao geraPrevisao = new ThreadGeraPrevisao();
                geraPrevisao.start();

//                }
            } catch (NumberFormatException nfex) {
                nfex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Para que a previsão seja realizada,\né necessário que o campo ano inicial\ne o campo final estejam preenchidos.", "Atenção.", JOptionPane.WARNING_MESSAGE);
            }
        //}
        } catch (PredictException ex) {
            ex.printStackTrace();
        }
    //}
    }

    private void gerarPrevisaoMultipla() {

        try {

            tipoConsumo = String.valueOf(cmbxTipoDeConsumo.getSelectedItem());
            nomePlanilha = String.valueOf(cmbxTipoDeConsumo.getSelectedItem());
            nomeConsumo = String.valueOf(cmbxNomeConsumo.getSelectedItem());
            labelTipoConsumo = String.valueOf(cmbxTipoDeConsumo.getSelectedItem());
            anoIni = null;
            anoFim = null;
            mesIni = null;
            mesFim = null;
            unidadeConsumo = null;
            labelEixoY = null;
            nomeLabelPrevisao = null;
            anosSelecionados = "";
            for (String ano : BackPrevisaoGUI.getAnosSelecionados(panelAnosPrevisao)) {
                anosSelecionados += ano + "#";
            }
            //obtem o valor do ano inicial assinalado
            anoIni = (String) cmbxAnoIni.getSelectedItem();
            //obtem o valor do ano final assinalado
            anoFim = (String) cmbxAnoFim.getSelectedItem();
            //obtem o valor do mes inicial assinalado
            mesIni = (String) cmbxMesIni.getSelectedItem();
            //obtem o valor do ano final assinalado
            mesFim = (String) cmbxMesFim.getSelectedItem();
            //obtem o numero do mes inicial selecionado
            mesIniInt = cmbxMesIni.getSelectedIndex();
            //obtem o numero do mes final selecionado
            mesFimInt = cmbxMesFim.getSelectedIndex();
            try {
//                if (verificarLimiteAnos()) {
                PrevisaoBean.setAnoFim(Integer.parseInt(anoFim));
                PrevisaoBean.setAnoIni(Integer.parseInt(anoIni));
                PrevisaoBean.setMesFim(mesFim);
                PrevisaoBean.setMesIni(mesIni);
                PrevisaoBean.setLabelConsumo(labelTipoConsumo);
                PrevisaoBean.setNomeConsumo(nomeConsumo);
                //  PrevisaoBean.setNomeInterligado(nomeInterligado);
                PrevisaoBean.setNomePlanilha(tipoConsumo);
                PrevisaoBean.setTipoConsumo(tipoConsumo);
                PrevisaoBean.setUnidadeConsumo(unidadeConsumo);
                PrevisaoBean.setAnosSelecionados(anosSelecionados);
                PrevisaoBean.setLabelEixoY(labelEixoY);
                PrevisaoBean.setTipoCons(nomeLabelPrevisao);

                ThreadGeraPrevisaoMultipla geraPrevisao = new ThreadGeraPrevisaoMultipla();
                geraPrevisao.start();

//                }
            } catch (NumberFormatException nfex) {
                nfex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Para que a previsão seja realizada,\né necessário que o campo ano inicial\ne o campo final estejam preenchidos.", "Atenção.", JOptionPane.WARNING_MESSAGE);
            }
        //}
        } catch (PredictException ex) {
            ex.printStackTrace();
        }
    //}
    }

    public boolean verificarLimiteAnos() throws NumberFormatException {

        // testa se o ano final é menor que o ano inicial
        if (Integer.parseInt(anoIni) < 2005) {
            JOptionPane.showMessageDialog(this, "O ano mínimo para previsão é " + 2005 + ".", "Atenção", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (Integer.parseInt(anoFim) < Integer.parseInt(anoIni)) {
            JOptionPane.showMessageDialog(this, "O ano final não pode ser menor que o ano inicial.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (Integer.parseInt(anoFim) - Integer.parseInt(anoIni) > 2) {
            JOptionPane.showMessageDialog(this, "O período máximo de previsão é até os 2 anos seguintes ao ano inicial.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return false;
//        } else if (Integer.parseInt(anoFim) > (this.anoAtual + 2)) {
//            JOptionPane.showMessageDialog(this, "O Predict realiza predições de até 2 anos além do ano atual.", "Atenção", JOptionPane.WARNING_MESSAGE);
//            return false;
        } else if ((Integer.parseInt(anoFim) == Integer.parseInt(anoIni)) && (mesIniInt > mesFimInt)) {
            JOptionPane.showMessageDialog(this, "O período de previsão está incorreto!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}
