package br.ufpa.lprad.predict.gui.window.correlacao;

import br.ufpa.lprad.predict.actions.Action;
import br.ufpa.lprad.predict.correlacao.GeraArquivoCorrelacao;
import br.ufpa.lprad.predict.gui.window.backstage.BordaPersonalizada;
import br.ufpa.lprad.predict.gui.window.backstage.TextoVertical;
import br.ufpa.lprad.predict.exception.PredictException;
import br.ufpa.lprad.predict.gui.window.correlacao.backstage.BackCorrelacaoGUI;
import br.ufpa.lprad.predict.leitura.ControleLeitura;
import br.ufpa.lprad.predict.leitura.interfaces.InterfaceLeituraClimaticos;
import br.ufpa.lprad.predict.leitura.interfaces.InterfaceLeituraConsumo;
import br.ufpa.lprad.predict.leitura.interfaces.InterfacerLeituraSocioEconomico;
import java.awt.Color;
import java.awt.Component;
import java.util.Vector;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * 
 * @author Arilene
 */
public class FrameCorrelacao extends javax.swing.JInternalFrame {

    private static Vector<String> itensSelecionados = new Vector<String>();
    private static Vector<String> tipoItensSelecionados = new Vector<String>();
    private static InterfaceLeituraConsumo ilc;
    private static InterfaceLeituraConsumo ic;
    private static String[] tipos;
    private static JPanel[] consumos;
    private static JList lista = new JList();
    public static Vector<String> nomeAbas = new Vector<String>();
    public boolean isSalvo;

    public FrameCorrelacao() {
        initComponents();
        desabilitaAbaPersonalizar();
        formataAbas();
        geraAbasDinamicamente();
        atualizaListaCorrelacoesSalvas(-1);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        tabPane_CorrelacoePersonalizar = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        btn_editarCorrelacao = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        list_CorrelacoesSalvas = new javax.swing.JList();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        list_atributosCorrelacoesSalvas = new javax.swing.JList();
        btn_deletarCorrelacao = new javax.swing.JButton();
        btn_novaCorrelacao = new javax.swing.JButton();
        panel_personalizar = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        tabPane_abas = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        list_atributos = new javax.swing.JList();
        status = new javax.swing.JLabel();
        btn_addCorrelacao = new javax.swing.JButton();
        btn_movePraCima = new javax.swing.JButton();
        btn_movePraBaixo = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        txf_nomeDaCorrelacao = new javax.swing.JTextField();
        btn_salvarCorrelacao = new javax.swing.JButton();
        statusSalvaCorrelacao = new javax.swing.JLabel();
        btn_removeCorrelacao = new javax.swing.JButton();
        btn_limpar = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel7 = new javax.swing.JPanel();
        btn_chamaDiscretizacao = new javax.swing.JButton();
        btn_fechar = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconifiable(true);
        setTitle("Correlação");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/predict.gif"))); // NOI18N
        setName(""); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 704, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        btn_editarCorrelacao.setText("Editar");
        btn_editarCorrelacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editarCorrelacaoActionPerformed(evt);
            }
        });

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        list_CorrelacoesSalvas.setEnabled(false);
        list_CorrelacoesSalvas.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                list_CorrelacoesSalvasValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(list_CorrelacoesSalvas);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jScrollPane2.setViewportView(list_atributosCorrelacoesSalvas);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addContainerGap())
        );

        btn_deletarCorrelacao.setText("Deletar");
        btn_deletarCorrelacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deletarCorrelacaoActionPerformed(evt);
            }
        });

        btn_novaCorrelacao.setText("Nova");
        btn_novaCorrelacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_novaCorrelacaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btn_novaCorrelacao, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_editarCorrelacao, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_deletarCorrelacao, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_novaCorrelacao)
                            .addComponent(btn_editarCorrelacao)
                            .addComponent(btn_deletarCorrelacao))))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        tabPane_CorrelacoePersonalizar.addTab("Correlações ", jPanel5);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Lista de Atributos"));
        jPanel1.setLayout(new java.awt.BorderLayout());
        jPanel1.add(tabPane_abas, java.awt.BorderLayout.CENTER);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Atributos Ecolhidos"));

        list_atributos.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                list_atributosValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(list_atributos);

        status.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                    .addComponent(status, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(status, javax.swing.GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE))
        );

        btn_addCorrelacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/correlacao/addCorrelacao.png"))); // NOI18N
        btn_addCorrelacao.setBorder(null);
        btn_addCorrelacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addCorrelacaoActionPerformed(evt);
            }
        });

        btn_movePraCima.setIcon(new javax.swing.ImageIcon(getClass().getResource("/correlacao/moverPraCima.png"))); // NOI18N
        btn_movePraCima.setBorder(null);
        btn_movePraCima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_movePraCimaActionPerformed(evt);
            }
        });

        btn_movePraBaixo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/correlacao/MoverPraBaixo.png"))); // NOI18N
        btn_movePraBaixo.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_movePraBaixo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_movePraBaixoActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Salvar Correlação"));

        txf_nomeDaCorrelacao.setText("Digite o nome aqui...");
        txf_nomeDaCorrelacao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txf_nomeDaCorrelacaoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txf_nomeDaCorrelacaoFocusLost(evt);
            }
        });

        btn_salvarCorrelacao.setText("Salvar");
        btn_salvarCorrelacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salvarCorrelacaoActionPerformed(evt);
            }
        });

        statusSalvaCorrelacao.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(txf_nomeDaCorrelacao, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_salvarCorrelacao))
                    .addComponent(statusSalvaCorrelacao, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txf_nomeDaCorrelacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_salvarCorrelacao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(statusSalvaCorrelacao, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btn_removeCorrelacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/correlacao/removeCorrelacao.png"))); // NOI18N
        btn_removeCorrelacao.setBorder(null);
        btn_removeCorrelacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_removeCorrelacaoActionPerformed(evt);
            }
        });

        btn_limpar.setText("Limpar");
        btn_limpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_limparActionPerformed(evt);
            }
        });

        btn_cancelar.setText("Cancelar");
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_personalizarLayout = new javax.swing.GroupLayout(panel_personalizar);
        panel_personalizar.setLayout(panel_personalizarLayout);
        panel_personalizarLayout.setHorizontalGroup(
            panel_personalizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_personalizarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_personalizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_personalizarLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_personalizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_personalizarLayout.createSequentialGroup()
                                .addGroup(panel_personalizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btn_addCorrelacao, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                                    .addComponent(btn_removeCorrelacao, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_personalizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btn_movePraBaixo, 0, 0, Short.MAX_VALUE)
                                    .addComponent(btn_movePraCima))
                                .addGap(11, 11, 11))
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_personalizarLayout.createSequentialGroup()
                        .addComponent(btn_cancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_limpar)))
                .addContainerGap())
        );
        panel_personalizarLayout.setVerticalGroup(
            panel_personalizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_personalizarLayout.createSequentialGroup()
                .addGroup(panel_personalizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_personalizarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panel_personalizarLayout.createSequentialGroup()
                        .addGroup(panel_personalizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_personalizarLayout.createSequentialGroup()
                                .addGap(126, 126, 126)
                                .addComponent(btn_addCorrelacao)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_removeCorrelacao))
                            .addGroup(panel_personalizarLayout.createSequentialGroup()
                                .addGap(122, 122, 122)
                                .addComponent(btn_movePraCima)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_movePraBaixo))
                            .addGroup(panel_personalizarLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(11, 11, 11)
                .addGroup(panel_personalizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_limpar)
                    .addComponent(btn_cancelar))
                .addContainerGap())
        );

        tabPane_CorrelacoePersonalizar.addTab("Personalizar", panel_personalizar);

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btn_chamaDiscretizacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exp_24.png"))); // NOI18N
        btn_chamaDiscretizacao.setText("Avançar");
        btn_chamaDiscretizacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_chamaDiscretizacaoActionPerformed(evt);
            }
        });

        btn_fechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gpl/dialog-close.png"))); // NOI18N
        btn_fechar.setText("Fechar");
        btn_fechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_fecharActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(182, 182, 182)
                .addComponent(btn_chamaDiscretizacao, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(btn_fechar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(170, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_chamaDiscretizacao)
                    .addComponent(btn_fechar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabPane_CorrelacoePersonalizar, javax.swing.GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabPane_CorrelacoePersonalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-740)/2, (screenSize.height-617)/2, 740, 617);
    }// </editor-fold>//GEN-END:initComponents

    private void txf_nomeDaCorrelacaoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txf_nomeDaCorrelacaoFocusGained
        txf_nomeDaCorrelacao.setText("");
}//GEN-LAST:event_txf_nomeDaCorrelacaoFocusGained

    private void txf_nomeDaCorrelacaoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txf_nomeDaCorrelacaoFocusLost
        if (txf_nomeDaCorrelacao.getText().isEmpty()) {
            txf_nomeDaCorrelacao.setText("Digite o nome aqui...");
        }

}//GEN-LAST:event_txf_nomeDaCorrelacaoFocusLost

    private void btn_addCorrelacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addCorrelacaoActionPerformed
        try {

            /**
             *
             */
            int indiceSelecionado = tabPane_abas.getSelectedIndex();
            //retorna o JPanel
            Component comp = tabPane_abas.getComponentAt(indiceSelecionado);
            //Recebe todos os componentes
            Component[] comps = ((JPanel) comp).getComponents();
            //percorre o array de componentes
            for (int i = 0; i < comps.length; i++) {
                if (comps[i] instanceof JList) {
                    lista = (JList) comps[i];
                }
            }
            if (lista.getSelectedValue() != null) {
                if (!itensSelecionados.contains(String.valueOf(lista.getSelectedValue()))) {
                    itensSelecionados.add(String.valueOf(lista.getSelectedValue()));
//                    System.out.println("nomeAbas.get(" + indiceSelecionado + "): " + nomeAbas.get(indiceSelecionado));
                    tipoItensSelecionados.add(nomeAbas.get(indiceSelecionado));
                    list_atributos.setListData(itensSelecionados);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_btn_addCorrelacaoActionPerformed

    private void btn_removeCorrelacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_removeCorrelacaoActionPerformed
        try {
            int indice = list_atributos.getSelectedIndex();
//            tipoItensSelecionados.add(nomeAbas.get(indice));
            tipoItensSelecionados.remove(indice);
            itensSelecionados.remove(String.valueOf(list_atributos.getSelectedValue()));
            list_atributos.setListData(itensSelecionados);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_btn_removeCorrelacaoActionPerformed

    private void btn_movePraCimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_movePraCimaActionPerformed
        try {
            /**
             * Guarda o nome do atributo slecionado na variavel valor
             */
            String valor = String.valueOf(list_atributos.getSelectedValue());
            /**
             * Guardano indice do item selecionado
             */
            int indice = itensSelecionados.indexOf(valor);

            System.out.println("INDICE - ITEM SELECINADO: " + indice + "\tINDICE LISTA ATRIBUTOS: " + list_atributos.getSelectedIndex());

            /**
             * Guarda o nome do tipo de atributo selecionado
             */
            String tipo_valor = String.valueOf(tipoItensSelecionados.get(indice));
//            System.out.println("ATRIBUTO SELECIONADO: " + valor + "\tINDICE SELECIONADO: " + indice + "\tTIPO DO VALOR: " + tipo_valor);

            if (indice > 0) {
//                System.out.println("ITENS SELECIONADOS ANTES DA REMOCAO: " + itensSelecionados);
                itensSelecionados.remove(String.valueOf(list_atributos.getSelectedValue()));
//                System.out.println("ITENS SELECIONADOS APOS A REMOCAO: " + itensSelecionados);
                /**
                 * 
                 */
                itensSelecionados.insertElementAt(valor, indice - 1);
//                System.out.println("ITENS SELECIONADOS APOS A INCLUSAO: " + itensSelecionados);

                list_atributos.setListData(itensSelecionados);
                list_atributos.setSelectedIndex(indice - 1);
                /**
                 * Ajusta o tipo do item movido de posição
                 */
//                System.out.println("ITENS SELECIONADOS ANTES DA REMOCAO: " + tipoItensSelecionados);
                tipoItensSelecionados.remove(indice);
//                System.out.println("TIPO DE ITENS SELECIONADOS APOS A REMOCAO: " + tipoItensSelecionados);
                //tipoItensSelecionados.insertElementAt(tipo_valor_substituido, indice);
                tipoItensSelecionados.insertElementAt(tipo_valor, indice - 1);
//                System.out.println("TIPO DE ITENS SELECIONADOS APOS A INCLUSAO: " + tipoItensSelecionados);
                status.setText(tipoItensSelecionados.get(indice - 1));
//                System.out.println("MENSAGEM DE STATUS: " + status.getText());
            }
            //
        } catch (Exception e) {
        }

    }//GEN-LAST:event_btn_movePraCimaActionPerformed

    private void btn_movePraBaixoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_movePraBaixoActionPerformed
//        try {
//            String valor = String.valueOf(list_atributos.getSelectedValue());
//            int indice = itensSelecionados.indexOf(valor);
//            if (indice < itensSelecionados.size()) {
//                itensSelecionados.remove(String.valueOf(list_atributos.getSelectedValue()));
//                itensSelecionados.insertElementAt(valor, indice + 1);
//                list_atributos.setListData(itensSelecionados);
//                list_atributos.setSelectedIndex(indice + 1);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {
            /**
             * Guarda o nome do atributo slecionado na variavel valor
             */
            String valor = String.valueOf(list_atributos.getSelectedValue());
            /**
             * Guardano indice do item selecionado
             */
            int indice = itensSelecionados.indexOf(valor);

            System.out.println("INDICE - ITEM SELECINADO: " + indice + "\tINDICE LISTA ATRIBUTOS: " + list_atributos.getSelectedIndex());

            /**
             * Guarda o nome do tipo de atributo selecionado
             */
            String tipo_valor = String.valueOf(tipoItensSelecionados.get(indice));
//            System.out.println("ATRIBUTO SELECIONADO: " + valor + "\tINDICE SELECIONADO: " + indice + "\tTIPO DO VALOR: " + tipo_valor);

            if (indice < list_atributos.getSelectedValues().length) {
//                System.out.println("ITENS SELECIONADOS ANTES DA REMOCAO: " + itensSelecionados);
                itensSelecionados.remove(String.valueOf(list_atributos.getSelectedValue()));
//                System.out.println("ITENS SELECIONADOS APOS A REMOCAO: " + itensSelecionados);
                /**
                 *
                 */
                itensSelecionados.insertElementAt(valor, indice + 1);
//                System.out.println("ITENS SELECIONADOS APOS A INCLUSAO: " + itensSelecionados);

                list_atributos.setListData(itensSelecionados);
                list_atributos.setSelectedIndex(indice + 1);
                /**
                 * Ajusta o tipo do item movido de posição
                 */
//                System.out.println("ITENS SELECIONADOS ANTES DA REMOCAO: " + tipoItensSelecionados);
                tipoItensSelecionados.remove(indice);
//                System.out.println("TIPO DE ITENS SELECIONADOS APOS A REMOCAO: " + tipoItensSelecionados);
                //tipoItensSelecionados.insertElementAt(tipo_valor_substituido, indice);
                tipoItensSelecionados.insertElementAt(tipo_valor, indice + 1);
//                System.out.println("TIPO DE ITENS SELECIONADOS APOS A INCLUSAO: " + tipoItensSelecionados);
                status.setText(tipoItensSelecionados.get(indice + 1));
//                System.out.println("MENSAGEM DE STATUS: " + status.getText());
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btn_movePraBaixoActionPerformed

    private void btn_salvarCorrelacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvarCorrelacaoActionPerformed
        try {
            if (!BackCorrelacaoGUI.contem(txf_nomeDaCorrelacao.getText())) {
                if (!txf_nomeDaCorrelacao.getText().equalsIgnoreCase("Digite o nome aqui...") && !txf_nomeDaCorrelacao.getText().isEmpty()) {

                    BackCorrelacaoGUI.salvarCorrelacao(txf_nomeDaCorrelacao.getText(), itensSelecionados, tipoItensSelecionados);
                    tabPane_CorrelacoePersonalizar.setSelectedIndex(0);
                    list_CorrelacoesSalvas.setSelectedIndex(list_CorrelacoesSalvas.getLastVisibleIndex());
                    atualizaListaCorrelacoesSalvas(BackCorrelacaoGUI.getCorrelacoesArquivo().length - 1);
                    limpar();
                    desabilitaAbaPersonalizar();
                } else {
                    JOptionPane.showMessageDialog(null, "Digite um nome válido!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Já existe, escolha outro nome!", "Erro", JOptionPane.ERROR_MESSAGE);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        btn_cancelar.setEnabled(true);


    }//GEN-LAST:event_btn_salvarCorrelacaoActionPerformed

    private void btn_chamaDiscretizacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_chamaDiscretizacaoActionPerformed
        try {
            /*Verifica se alguma correlação foi selecionada para a discretização*/
            if (tabPane_CorrelacoePersonalizar.getSelectedIndex() == 0) {
                if (list_CorrelacoesSalvas.getSelectedValue() != null) {
                    new Thread() {

                        public void run() {
                            jProgressBar1.setIndeterminate(true);
                            jProgressBar1.setString("Processando...");
                            GeraArquivoCorrelacao gac = new GeraArquivoCorrelacao(String.valueOf(list_CorrelacoesSalvas.getSelectedValue()));
                            Action.abrirFrameDiscretizacao(String.valueOf(list_CorrelacoesSalvas.getSelectedValue()));
                            fechar();
                        }
                    }.start();
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione uma análise!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Para passar para a próxima etapa, é necessário salvar a correlação criada.", "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

}//GEN-LAST:event_btn_chamaDiscretizacaoActionPerformed

    private void btn_fecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_fecharActionPerformed
        fechar();

}//GEN-LAST:event_btn_fecharActionPerformed

private void btn_limparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limparActionPerformed
    limpar();

}//GEN-LAST:event_btn_limparActionPerformed

private void list_CorrelacoesSalvasValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_list_CorrelacoesSalvasValueChanged
    list_atributosCorrelacoesSalvas.setListData(BackCorrelacaoGUI.getAtributosCorrelacao(String.valueOf(list_CorrelacoesSalvas.getSelectedValue())));
    tipoItensSelecionados = BackCorrelacaoGUI.getTiposAtributosCorrelacao(String.valueOf(list_CorrelacoesSalvas.getSelectedValue()));

}//GEN-LAST:event_list_CorrelacoesSalvasValueChanged

private void btn_editarCorrelacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editarCorrelacaoActionPerformed
    // verifica se algum item da lista foi selecionado
    if (!list_CorrelacoesSalvas.isSelectionEmpty()) {
        // pega o nome do item selecionado
        String nomeCorrecSelecionada = String.valueOf(list_CorrelacoesSalvas.getSelectedValue());
        // obtem a lista de atrivutos da analise selecionada
        Object[] atributosCorrecSelecionada = BackCorrelacaoGUI.getAtributosCorrelacao(String.valueOf(list_CorrelacoesSalvas.getSelectedValue()));
        // preenche o vetor de itens selecionados
        preencheVetorSelecionados(atributosCorrecSelecionada);
        // preenche o vetor correspondente aos tipos dos itens selecionados
        tipoItensSelecionados = BackCorrelacaoGUI.getTiposAtributosCorrelacao(nomeCorrecSelecionada);
        // seta a lista de atributos da analise selecionada
        list_atributos.setListData(atributosCorrecSelecionada);
        // seta o nome no campo de salvar
        txf_nomeDaCorrelacao.setText(nomeCorrecSelecionada);
//        // desabilita para que usuario nao possa modificar o nome da correlacao
        txf_nomeDaCorrelacao.setEnabled(false);
        // exclui a correlacao antiga
        BackCorrelacaoGUI.excluirCorrelacao(nomeCorrecSelecionada);

        btn_cancelar.setEnabled(false);

        // desabilita a primeira aba
        tabPane_CorrelacoePersonalizar.setEnabledAt(0, false);
        //habilita a segunda aba
        tabPane_CorrelacoePersonalizar.setEnabledAt(1, true);
    }

    tabPane_CorrelacoePersonalizar.setSelectedIndex(1);


}//GEN-LAST:event_btn_editarCorrelacaoActionPerformed

private void btn_deletarCorrelacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deletarCorrelacaoActionPerformed
    if (!list_CorrelacoesSalvas.isSelectionEmpty()) {
        int escolha = JOptionPane.showOptionDialog(this, "Deseja realmente excuir essa análise ?", "Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Sim", "Não"}, null);
        if (escolha == JOptionPane.OK_OPTION) {
            int indice = list_CorrelacoesSalvas.getSelectedIndex();
            BackCorrelacaoGUI.excluirCorrelacao(String.valueOf(list_CorrelacoesSalvas.getSelectedValue()));
            list_CorrelacoesSalvas.setSelectedIndex(indice);
            atualizaListaCorrelacoesSalvas(indice);
        }
    }

}//GEN-LAST:event_btn_deletarCorrelacaoActionPerformed

private void btn_novaCorrelacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_novaCorrelacaoActionPerformed
    tabPane_CorrelacoePersonalizar.setEnabledAt(0, false);
    tabPane_CorrelacoePersonalizar.setEnabledAt(1, true);
    tabPane_CorrelacoePersonalizar.setSelectedIndex(1);
    limpar();

}//GEN-LAST:event_btn_novaCorrelacaoActionPerformed

private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
    tabPane_CorrelacoePersonalizar.setEnabledAt(0, true);
    tabPane_CorrelacoePersonalizar.setEnabledAt(1, false);
    tabPane_CorrelacoePersonalizar.setSelectedIndex(0);
    limpar();

}//GEN-LAST:event_btn_cancelarActionPerformed

private void list_atributosValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_list_atributosValueChanged
    if (!list_atributos.isSelectionEmpty()) {
        status.setText(tipoItensSelecionados.get(list_atributos.getSelectedIndex()));
    }

}//GEN-LAST:event_list_atributosValueChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_addCorrelacao;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_chamaDiscretizacao;
    private javax.swing.JButton btn_deletarCorrelacao;
    private javax.swing.JButton btn_editarCorrelacao;
    private javax.swing.JButton btn_fechar;
    private javax.swing.JButton btn_limpar;
    private javax.swing.JButton btn_movePraBaixo;
    private javax.swing.JButton btn_movePraCima;
    private javax.swing.JButton btn_novaCorrelacao;
    private javax.swing.JButton btn_removeCorrelacao;
    private javax.swing.JButton btn_salvarCorrelacao;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private static javax.swing.JList list_CorrelacoesSalvas;
    private javax.swing.JList list_atributos;
    private javax.swing.JList list_atributosCorrelacoesSalvas;
    private javax.swing.JPanel panel_personalizar;
    private javax.swing.JLabel status;
    private javax.swing.JLabel statusSalvaCorrelacao;
    private javax.swing.JTabbedPane tabPane_CorrelacoePersonalizar;
    private javax.swing.JTabbedPane tabPane_abas;
    private javax.swing.JTextField txf_nomeDaCorrelacao;
    // End of variables declaration//GEN-END:variables

    private void formataAbas() {/*Só pra ficar bonitinho =]*/
        tabPane_abas.setTabPlacement(JTabbedPane.LEFT);
        lista.setBackground(Color.WHITE);
        jPanel1.setBorder(new BordaPersonalizada("Lista de Atributos",
                new Color(0x418EDC), new Color(0x6B91B8), Color.WHITE, 16));
        jPanel2.setBorder(new BordaPersonalizada("Atributos Selecionados",
                new Color(0x418EDC), new Color(0x6B91B8), Color.WHITE, 16));
        jPanel3.setBorder(new BordaPersonalizada("Salvar Correlação",
                Color.LIGHT_GRAY, Color.WHITE, Color.BLACK, 20));
        jPanel8.setBorder(new BordaPersonalizada("Lista de Correlações"));
        jPanel9.setBorder(new BordaPersonalizada("Atributos"));

        tabPane_abas.updateUI();

        jPanel1.add(tabPane_abas);
        jPanel1.updateUI();

    }

    private void geraAbasDinamicamente() {
        try {
            /*Chama os métodos da leitura do consumo*/
            ilc = new ControleLeitura();
            ic = new ControleLeitura();

            /*Guarda os nomes das abas da planilha dos consumos*/
            tipos = ilc.getTipoConsumo();

            consumos = new JPanel[tipos.length];

            for (int i = 0; i < tipos.length; i++) {
                consumos[i] = new JPanel();
            }

            /*Adiciona as abas da tabela*/
            for (int i = 0; i < tipos.length; i++) {
                tabPane_abas.addTab(null, new TextoVertical(tipos[i], false), criaPainel(consumos[i], tipos[i]));
                nomeAbas.add(tipos[i]);
            }
            tabPane_abas.addTab(null, new TextoVertical("Sócio-Econômico", false), adicionaSocioEconomico());
            tabPane_abas.addTab(null, new TextoVertical("Climáticos", false), adicionaClimaticos());
            nomeAbas.add("Sócio-Econômico");
            nomeAbas.add("Climáticos");

            updateUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
        list_CorrelacoesSalvas.setEnabled(true);
    }

    /*=========Adiciona os dados de cada aba, que correspondem ao título de cada========
    ==========================coluna da planilha lida - INICIO=======================*/
    private Component adicionaSocioEconomico() {

        JPanel painelSocio = new JPanel();
        painelSocio.setLayout(new java.awt.GridLayout(0, 1));

        try {
            InterfacerLeituraSocioEconomico ise = new ControleLeitura();
            String[] cabecalho = BackCorrelacaoGUI.retiraPeriodo(ise.getCabecalhoPlanilhaSocioEconomico());
            painelSocio.add(getCabecalhoPlanilhaJlist(cabecalho));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return painelSocio;
    }

    private Component adicionaClimaticos() {
        JPanel painelClima = new JPanel();
        painelClima.setLayout(new java.awt.GridLayout(0, 1));
        try {
            InterfaceLeituraClimaticos ise = new ControleLeitura();
            String[] cabecalho = BackCorrelacaoGUI.retiraPeriodo(ise.getCabecalhoPlanilhaClimaticos());
            painelClima.add(getCabecalhoPlanilhaJlist(cabecalho));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return painelClima;
    }

    /* Cria Painel é a planilha maior, com várias abas*/
    private Component criaPainel(JPanel jPanel, String string) {
        try {
            jPanel.setLayout(new java.awt.GridLayout(0, 1));
            String[] cabecalho = BackCorrelacaoGUI.retiraPeriodo(ic.getCabecalhoPlanilhaConsumo(string));
            jPanel.add(getCabecalhoPlanilhaJlist(cabecalho));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return jPanel;
    }
    /*=========Adiciona os dados de cada aba, que correspondem ao título de cada========
    ==========================coluna da planilha lida - INICIO=======================*/

    /*Pega o Cabeçalho da Planilha, ou seja, título de cada coluna da planilha lida*/
    public static JList getCabecalhoPlanilhaJlist(String[] cabecalho) throws PredictException {
        try {
            JList lista = new JList(cabecalho);
            return lista;

        } catch (Exception ex) {
            throw new PredictException("Erro ao ler os anos no backstage da previsão: " + ex.getMessage());
        }
    }

    private void preencheVetorSelecionados(Object[] atributosCorrecSelecionada) {
        itensSelecionados = new Vector<String>();
        for (int i = 0; i < atributosCorrecSelecionada.length; i++) {
            itensSelecionados.add(String.valueOf(atributosCorrecSelecionada[i]));
        }
    }

    public void atualizaListaCorrelacoesSalvas(int indice) {
        list_CorrelacoesSalvas.setListData(BackCorrelacaoGUI.getCorrelacoesArquivo());
        try {
            list_CorrelacoesSalvas.setSelectedIndex(indice);
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        list_CorrelacoesSalvas.updateUI();
    }

    public void limpar() {
        tipoItensSelecionados = new Vector<String>();
        itensSelecionados = new Vector();
        list_atributos.setListData(itensSelecionados);
        txf_nomeDaCorrelacao.setText("");
        status.setText("");
    }

    public void desabilitaAbaPersonalizar() {
        tabPane_CorrelacoePersonalizar.setEnabledAt(1, false);
        tabPane_CorrelacoePersonalizar.setEnabledAt(0, true);

    }

    void fechar() {
        try {
            itensSelecionados = new Vector();
            setClosed(true);

        } catch (Exception e) {
        }
    }
}
