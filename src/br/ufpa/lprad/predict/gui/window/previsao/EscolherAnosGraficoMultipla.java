/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EscolherAnosGraficoTaxaCrescimento.java
 *
 * Created on 07/10/2010, 10:58:17
 */
package br.ufpa.lprad.predict.gui.window.previsao;

import br.ufpa.lprad.predict.exception.PredictException;
import br.ufpa.lprad.predict.gui.window.previsao.backstage.BackPrevisaoGUI;
import br.ufpa.lprad.predict.leitura.interfaces.InterfaceLeituraConsumo;
import br.ufpa.lprad.predict.previsao.bean.PrevisaoBean;
import br.ufpa.lprad.predict.previsao.multipla.grafico.GraficoPrevisaoMultipla;
import br.ufpa.lprad.predict.previsao.taxadecrescimento.PrevisaoTaxadeCrescimento;
import br.ufpa.lprad.predict.previsao.taxadecrescimento.grafico.GraficoPrevisaoTaxaDeCrescimento;
import br.ufpa.lprad.predict.previsao.taxadecrescimento.grafico.ModeloGraficoTaxaCrescimento;
import br.ufpa.lprad.predict.propriedades.PredictPropriedades;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JCheckBox;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 *
 * @author jgabriellima
 */
public class EscolherAnosGraficoMultipla extends javax.swing.JInternalFrame {

    private Vector<String>[] vetorHistorico;
    public static Vector<Vector<String>> dadosHistoricos;
    public static Vector<String> anos;
    public static Vector<String> periodoPrevisto;
    public static Vector<String> dadosHistoricosPrevisao;
    private JCheckBox[] boxs;
    private Vector<String> previsto;
    private Vector<String> historico;
    private Vector<String> periodo;
    private String[] anosAnteriores;
    private String[] anosAnterioresPeriodo;
    private String erro;
    private ModeloGraficoTaxaCrescimento modelo;
    private ArrayList<ModeloGraficoTaxaCrescimento> lista;
    private String naoExisteMsg = "não aferido!";
    private InterfaceLeituraConsumo ilc;
    private String tituloGrafico, tituloEixoX, tituloEixoY;
    private String[] anosSelecionados;

    /** Creates new form EscolherAnosGraficoTaxaCrescimento */
    public EscolherAnosGraficoMultipla() {
        initComponents();
        init();
    }

    public EscolherAnosGraficoMultipla(String tituloGrafico, String tituloEixoX,
            String tituloEixoY, Vector<String> periodo, Vector<String> previsto, Vector<String> historico, String erro) {
        this.previsto = previsto;
        this.historico = historico;
        this.periodo = periodo;
        this.erro = erro;
        this.tituloEixoX = tituloEixoX;
        this.tituloEixoY = tituloEixoY;
        this.tituloGrafico = tituloGrafico;


        initComponents();
        init();
    }

    void init() {
        panelAnosPrevisao.setLayout(new java.awt.GridLayout(0, 1));
        try {
            boxs = getAnosJChBox(String.valueOf(PrevisaoGUI.cmbxTipoDeConsumo.getSelectedItem()));
        } catch (PredictException ex) {
        }
        for (JCheckBox box : boxs) {
            panelAnosPrevisao.add(box);
        }
        panelAnosPrevisao.updateUI();
    }

    public static JCheckBox[] getAnosJChBox(String tipoDeConsumo) throws PredictException {
        try {

            String[] anos = BackPrevisaoGUI.getAnos(tipoDeConsumo);
            final JCheckBox[] jcbx = new JCheckBox[anos.length];

            for (int i = 0; i < jcbx.length; i++) {
                final JCheckBox aux = new JCheckBox(anos[i]);
                aux.setSelected(false);
                aux.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        if (!aux.isSelected()) {
                            PrevisaoGUI.jCBMarcarTodos.setSelected(false);
                        }
                    }
                });
                jcbx[i] = aux;
            }

            return jcbx;


        } catch (Exception ex) {
            throw new PredictException("Erro ao ler os anos no backstage da previsão: " + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCBMarcarTodos = new javax.swing.JCheckBox();
        scrollAnosPrev = new javax.swing.JScrollPane();
        panelAnosPrevisao = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Escolha os Anos para o Gráfico da Previsão");
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        setVisible(true);

        jCBMarcarTodos.setText("Marcar Todos");
        jCBMarcarTodos.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCBMarcarTodos.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jCBMarcarTodos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCBMarcarTodosMouseClicked(evt);
            }
        });

        scrollAnosPrev.setBackground(new java.awt.Color(255, 255, 255));
        scrollAnosPrev.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(204, 204, 204), null));
        scrollAnosPrev.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollAnosPrev.setAutoscrolls(true);

        panelAnosPrevisao.setBackground(new java.awt.Color(255, 255, 255));
        panelAnosPrevisao.setLayout(new java.awt.GridLayout(1, 0));
        scrollAnosPrev.setViewportView(panelAnosPrevisao);

        jButton1.setText("Gerar Gráfico");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancelar");
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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCBMarcarTodos, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2))
                    .addComponent(scrollAnosPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollAnosPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCBMarcarTodos, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCBMarcarTodosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCBMarcarTodosMouseClicked
        if (!jCBMarcarTodos.isSelected()) {
            BackPrevisaoGUI.desmarcarTodos(panelAnosPrevisao);
        } else {
            BackPrevisaoGUI.marcarTodos(panelAnosPrevisao);
        }
}//GEN-LAST:event_jCBMarcarTodosMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            setClosed(true);
        } catch (PropertyVetoException ex) {
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            anosSelecionados = BackPrevisaoGUI.getAnosSelecionados(panelAnosPrevisao);
        } catch (PredictException ex) {
            ex.printStackTrace();
        }
        new GraficoPrevisaoMultipla("Grafico de Previsão \n" + PrevisaoBean.getTipoConsumo() + "\n" + PrevisaoBean.getNomeConsumo(), "Período", "Valor", PrevisaoTaxadeCrescimento.periodoPrevisto, PrevisaoTaxadeCrescimento.dadoPrevisto, PrevisaoTaxadeCrescimento.dadosHistoricosPrevisao, PrevisaoTaxadeCrescimento.getErroGeral(), anosSelecionados, planilha());
    }//GEN-LAST:event_jButton1ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    public static javax.swing.JCheckBox jCBMarcarTodos;
    private javax.swing.JPanel panelAnosPrevisao;
    private javax.swing.JScrollPane scrollAnosPrev;
    // End of variables declaration//GEN-END:variables

    private double[][] planilha() {
        try {

            this.dadosHistoricos = new Vector<Vector<String>>();
            this.dadosHistoricosPrevisao = new Vector<String>();

            // pega a planilha de dados
            Workbook planilha = Workbook.getWorkbook(new File(PredictPropriedades.getCaminhoPlanilhaConsumo()));
            //seleciona a aba desejada
            Sheet aba = planilha.getSheet(PrevisaoBean.getTipoConsumo());

            /*
             * pega o indice da coluna do consumo estudado
             */
            // vetor com a coluna dos dados do consumo estudado
            Cell[] consumo = getConsumo(aba, PrevisaoBean.getNomeConsumo());
            // vetor com os periodos da planilha
            Cell[] periodo = aba.getColumn(0);
            /*
             * cria o vector de anos cada indice do vetor é um ano..
             */
            vetorHistorico = new Vector[anosSelecionados.length];
            for (int i = 0; i < vetorHistorico.length; i++) {
                vetorHistorico[i] = new Vector<String>();
            }
            /*
             * faz a leitura da base de dados
             */
            // vetor que irá auxiliar para a leitura dos anos
            Vector<String> aux = new Vector<String>();

            int cont = 0;

            for (int i = 0; i < anosSelecionados.length; i++) {
                for (int j = 2; j < consumo.length; j++) {
                    if (!periodo[j].getContents().equalsIgnoreCase("TOTAL")) {
                        if (periodo[j].getContents().split("/")[1].equalsIgnoreCase(anosSelecionados[i])) {
                            vetorHistorico[i].add(consumo[j].getContents());
                        }
                    }
                }
                dadosHistoricos.add(vetorHistorico[i]);
            }

            return converteVectorMatriz(dadosHistoricos);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Cell[] getConsumo(Sheet aba, String nomeConsumo) {

        Cell[] consumo = null;
        try {

            Cell[] tiposDeConsumo = aba.getRow(1);

            for (int i = 0; i < tiposDeConsumo.length; i++) {
                if (tiposDeConsumo[i].getContents().equalsIgnoreCase(nomeConsumo)) {
                    consumo = aba.getColumn(i);
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return consumo;
    }

    private double[][] converteVectorMatriz(Vector<Vector<String>> dadosHistoricos) {
        double[][] retorno;
        try {
            retorno = new double[dadosHistoricos.get(0).size()][dadosHistoricos.size()];

            for (int i = 0; i < retorno.length; i++) {
                for (int j = 0; j < retorno[0].length; j++) {
                    try {
                        retorno[i][j] = Double.valueOf(dadosHistoricos.get(j).get(i).replace(",", ".")).doubleValue();
                    } catch (NumberFormatException e) {
                        retorno[i][j] = 0;
                    }
                }
            }
            return retorno;
        } catch (Exception e) {
        }
        return null;
    }
}
