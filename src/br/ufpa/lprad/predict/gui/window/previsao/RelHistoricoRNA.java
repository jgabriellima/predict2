/*
 * DadosConsumoFrame.java
 *
 * Created on 26 de Abril de 2006, 10:39
 */
package br.ufpa.lprad.predict.gui.window.previsao;

import br.ufpa.lprad.predict.gui.window.*;
import br.ufpa.lprad.predict.bean.RelHistorico;
import br.ufpa.lprad.predict.bean.RelHistorico_total;
import br.ufpa.lprad.predict.graficos.GeradorGrafico;
import br.ufpa.lprad.predict.graficos.modelo.ComposicaoGraficoErro;
import br.ufpa.lprad.predict.gui.window.backstage.GerenciadorDesktop;
import br.ufpa.lprad.predict.previsao.bean.PrevisaoBean;
import br.ufpa.lprad.predict.previsao.multipla.PrevisaoMultivariada;
import br.ufpa.lprad.predict.previsao.neural.PrevisaoNeural;
import br.ufpa.lprad.predict.previsao.neural.grafico.GraficoPrevisaoRNA;
import br.ufpa.lprad.predict.previsao.taxadecrescimento.PrevisaoTaxadeCrescimento;
import br.ufpa.lprad.predict.propriedades.PredictPropriedades;
import br.ufpa.lprad.predict.util.io.ExportarXLS;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author J. Gabriel Lima
 */
public class RelHistoricoRNA extends javax.swing.JInternalFrame {

    private static String[] nomeAtributos = {"Período", "Dados Históricos(MWh)", "Dados Previstos(MWh)", "Erro(%)=(Real-Previsto)/Previsto"}, nomeAtributos_xls = {"Período", "Dados Históricos(MWh)", "Dados Previstos(MWh)", "Erro(%)=(Real-Previsto)/Previsto", "Real/Previsao", "Variação %"};
    private static String unidadeConsumo = "";
    private static String Mediaerro;
    private static String nomeConsumo;
    private static String labelEixoY;
    private static int difMeses;
    private static JCheckBox mostrar_anos;
    private Vector Conteudo_tabela;
    /////////////////////////////////
    private static String[][] valor;
    private static String[] cabecalho;
    private static int[] periodo;
    private static int dimensao;                            //valor com o numero de meses para realizar previsao
    private static int numAnos;                             //numero de anos diferentes para previsao    
    //int difMeses;    
    private static boolean flagAnosDifer = false;            //variavel que define se a previsao contem valores de anos diferentes --> false: nao possui valores de anos diferentes
    private static boolean flagNumAnos = false;              //variavel que define se a previsao possui valores de 1 ou 2 anos diferentes -->false: possui apenas 1 ano de previsao      
    static NumberFormat nf;
    static Locale locale;
    public static Vector<String> periodoDados;
    public static Vector<String> historicoDados;
    public static Vector<String> previstoDados;

    public RelHistoricoRNA() {
        setTitle("Relatório de Projeção  -  Rede Neural Artificial : " + PrevisaoBean.getNomeConsumo());
        initComponents();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, (screenSize.width) / 2 + 30, (screenSize.height) / 2 + 273);
        jLabel1.setText(defineErro());
        Conteudo_tabela = new Vector();

        Conteudo_tabela.add(this.nomeAtributos);
        Conteudo_tabela.add(this.nomeAtributos_xls);

        //----------------------------------------------------
        valor = PrevisaoNeural.getRelatorioSimples();//.getRelatorioSimples();//Previsao2Anos.getDadosRelatorio();
        cabecalho = (String[]) Conteudo_tabela.elementAt(0);
        DefaultTableModel dtf = new DefaultTableModel(valor, (String[]) Conteudo_tabela.elementAt(0));

        jTable1.setModel(dtf);
        jTable1.setEnabled(false);
        jScrollPane1.setViewportView(jTable1);
        setCorLinhas(jTable1);
        jTable1.updateUI();


        jScrollPane1.updateUI();
        //----------------------------------------------------

        mostrar_anos = new JCheckBox("Mostrar todos", false);
        mostrar_anos.setVisible(false);
        mostrar_anos.setBorderPainted(false);
        mostrar_anos.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                mostraAnos(e);
            }
        });
        jPanel1.add(mostrar_anos, 0);
    }

    private String defineErro() {
        try {

            if (!PrevisaoNeural.erroGeral.equalsIgnoreCase("inexistente")) {
                return "Erro Percentual: " + PrevisaoNeural.erroGeral + "%  ";
            } else {
                return "Erro Percentual: Inexistente";
            }
        } catch (Exception e) {
            return "Erro Percentual: Inexistente";

        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/predict.gif"))); // NOI18N
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        setVisible(true);

        jScrollPane1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setEnabled(false);
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gpl/chart_16.png"))); // NOI18N
        jButton5.setText("Erro");
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton5.setPreferredSize(new java.awt.Dimension(99, 31));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gpl/chart_16.png"))); // NOI18N
        jButton4.setText("Previsao");
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton4.setPreferredSize(new java.awt.Dimension(99, 31));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gpl/printer.png"))); // NOI18N
        jButton1.setText("Imprimir");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

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

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Erro Percentual: "+PrevisaoTaxadeCrescimento.getErroGeral()+"%  ");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(jLabel1, java.awt.BorderLayout.PAGE_START);
        setJMenuBar(jMenuBar1);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-695)/2, (screenSize.height-342)/2, 695, 342);
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
            ExportarXLS.exportarPlanilhaXLS(cabecalho, valor, "Relatório de Projeção");
            Desktop.getDesktop().open(new File(ExportarXLS.getPathXLS()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    //executa acao de imprimir planilha de consumo
    private void imprimir() {
        List linhaTab = new LinkedList();

        RelHistorico rh = null;
        RelHistorico_total rht = null;
        String tipoConsumo = PrevisaoBean.getNomeConsumo();
        String unidConsumo = PrevisaoBean.getUnidadeConsumo();
        String nomePrevisao = PrevisaoBean.getTipoCons();
        String caminho = null;
        caminho =
                "\\layouts\\RelHistorico.jrxml";
//        String[][] dados = Previsao2Anos.getDadosRelatorio();
        for (int i = 0; i < valor.length; i++) {
            rh = new RelHistorico();
            rh.setPeriodo(valor[i][0]);
            rh.setHistorico(valor[i][1]);
            rh.setPrevisto(valor[i][2]);
            rh.setErro(valor[i][3]);
            rh.setTipoCons(tipoConsumo);
            rh.setUnidadeConsumo(unidConsumo);
            linhaTab.add(rh);
        }

        Map parametros = new HashMap();

        try {
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(linhaTab);
            JasperReport jr = JasperCompileManager.compileReport(PredictPropriedades.getCaminhoRelatorioPrevisao());//"\\layouts\\RelHistorico.jrxml");
            JasperPrint jp = JasperFillManager.fillReport(jr, parametros, ds);

            int opcao = JOptionPane.showOptionDialog(this, "Deseja visualizar a impressão?\nCaso escolha 'Não', o relatório será enviado diretamente para a impressora padrão.", "Impressão de Relatório", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/gpl/document-print-preview.png")), new String[]{"Sim", "Não"}, null);

            if (opcao == JOptionPane.YES_OPTION) {
                RelatorioViewer viewer = new RelatorioViewer(jp, false);
                viewer.viewReport(jp, false);
            } else if (opcao == JOptionPane.NO_OPTION) {
                JasperPrintManager.printReport(jp, true);
            }

        } catch (JRException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }

    //botao imprimir planilha de dados
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        imprimir();

    }//GEN-LAST:event_jButton1ActionPerformed
    public int formataData(String mes) {
        int mesInt;

        if (mes.equalsIgnoreCase("Janeiro")) {
            mesInt = 1;
        } else if (mes.equalsIgnoreCase("Fevereiro")) {
            mesInt = 2;
        } else if (mes.equalsIgnoreCase("Março")) {
            mesInt = 3;
        } else if (mes.equalsIgnoreCase("Abril")) {
            mesInt = 4;
        } else if (mes.equalsIgnoreCase("Maio")) {
            mesInt = 5;
        } else if (mes.equalsIgnoreCase("Junho")) {
            mesInt = 6;
        } else if (mes.equalsIgnoreCase("Julho")) {
            mesInt = 7;
        } else if (mes.equalsIgnoreCase("Agosto")) {
            mesInt = 8;
        } else if (mes.equalsIgnoreCase("Setembro")) {
            mesInt = 9;
        } else if (mes.equalsIgnoreCase("Outubro")) {
            mesInt = 10;
        } else if (mes.equalsIgnoreCase("Novembro")) {
            mesInt = 11;
        } else {
            mesInt = 12;
        }

        return mesInt;
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
//        this.periodo = new int[4];   //--> posicao 0:mesInicial
//        //--> posicao 1:mesFinal
//        //--> posicao 2:anoInicial
//        //--> posicao 3:anoFinal
//
//        this.periodo[0] = formataData(PrevisaoBean.getMesIni());
//        this.periodo[1] = formataData(PrevisaoBean.getMesFim());
//        this.periodo[2] = PrevisaoBean.getAnoIni();
//        this.periodo[3] = PrevisaoBean.getAnoFim();
//
//        this.difMeses = periodo[1] - periodo[0];
//
//        definePrevisao();
//
//        fazPrevisao();
        carregaDados();

        GerenciadorDesktop.add(
                new EscolherAnosGraficoRNA("Grafico de Previsão \n" + PrevisaoBean.getTipoConsumo() + "\n" + PrevisaoBean.getNomeConsumo(),
                "Período", "Valor", periodoDados, previstoDados, historicoDados, PrevisaoNeural.erroGeral));

//        new GraficoPrevisaoRNA("Grafico de Previsão \n" + PrevisaoBean.getTipoConsumo() + "\n" + PrevisaoBean.getNomeConsumo(), "Período", "Valor", periodoDados, previstoDados, historicoDados, PrevisaoNeural.erroGeral);

    }//GEN-LAST:event_jButton4ActionPerformed

    public void carregaDados() {
        periodoDados = new Vector<String>();
        previstoDados = new Vector<String>();
        historicoDados = new Vector<String>();

        for (int i = 0; i < valor.length; i++) {
            if (!valor[i][0].equalsIgnoreCase("TOTAL")) {
                periodoDados.add(valor[i][0]);
                historicoDados.add(valor[i][1]);
                previstoDados.add(valor[i][2]);
            }
        }
    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        if (!PrevisaoNeural.erroGeral.equalsIgnoreCase("inexistente")) {

            try {
                ComposicaoGraficoErro cp = new ComposicaoGraficoErro(PrevisaoNeural.getRelatorioSimples());
                GeradorGrafico.gerarGraficoBarraVertical3D("Gráfico de Erros", "Período", "Valor", cp.getLista(), "Erro Percentual: " + PrevisaoNeural.erroGeral + "%  ");
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else {
            JOptionPane.showMessageDialog(this, "Erro Inexistente!", "Erro", JOptionPane.WARNING_MESSAGE, null);
        }

    }//GEN-LAST:event_jButton5ActionPerformed

    public void definePrevisao() {

        //se o periodo for de 2 anos
        if ((this.periodo[3] - this.periodo[2]) == 2) {
            if (this.periodo[0] != this.periodo[1]) {
                dimensao = (this.periodo[1] + (13 - this.periodo[0]) + 12);
                //System.out.println("dimensao2anos= "+dimensao);
            } else {
                dimensao = 25;
                //System.out.println("dimensao2anos= "+dimensao);
            }

            this.flagNumAnos = true;
            this.flagAnosDifer = true;
            numAnos =
                    3;

            //se o periodo for maior ou igual a 1 ano e menor que 2 anos
        } else if ((this.periodo[3] - this.periodo[2]) == 1) {
            //se os anos inicial e final sao diferentes
            if (this.periodo[1] < this.periodo[0]) {
                dimensao = (this.periodo[1] + (13 - this.periodo[0]));
                //System.out.println("dimensao1ano= "+dimensao);

                //se os anos inicial e final sao iguais
            } else if (this.periodo[1] == this.periodo[0]) {
                dimensao = 13;
                //System.out.println("dimensao1ano= "+dimensao);
            } else {
                dimensao = (13 - this.periodo[0] + this.periodo[1]);
                //System.out.println("dimensao2anos1= "+dimensao);
            }

            this.flagAnosDifer = true;
            this.flagNumAnos = false;
            numAnos =
                    2;
            //se o periodo for do mesmo ano ou deseja-se apenas a previsao de 1 mes
        } else {
            if (this.periodo[0] != this.periodo[1]) {
                dimensao = (this.periodo[1] - this.periodo[0] + 1);
                //System.out.println("dimensaoAnosIguais= "+dimensao);
            } else {
                dimensao = 1;
                //System.out.println("dimensaoAnosIguais= "+dimensao);
            }

            this.flagAnosDifer = false;
            numAnos =
                    1;
        }

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    public void mostraAnos(ItemEvent e) {
        if (e.getStateChange() == e.DESELECTED) {
            valor = PrevisaoTaxadeCrescimento.getRelatorioSimples();
            cabecalho = (String[]) Conteudo_tabela.elementAt(0);
            DefaultTableModel dtm = new DefaultTableModel(valor, (String[]) Conteudo_tabela.elementAt(0));

            jTable1.setModel(dtm);
            setCorLinhas(jTable1);
            jTable1.setEnabled(false);
            jScrollPane1.setViewportView(jTable1);
            jTable1.updateUI();
            jScrollPane1.updateUI();
        } else {
            valor = PrevisaoTaxadeCrescimento.getRelatorioCompleto();
            cabecalho = (String[]) Conteudo_tabela.elementAt(1);
            DefaultTableModel dtm = new DefaultTableModel(valor, (String[]) Conteudo_tabela.elementAt(1));
            jTable1.setModel(dtm);
            setCorLinhas(jTable1);
            jTable1.setEnabled(false);
            jScrollPane1.setViewportView(jTable1);
            jTable1.updateUI();
            jScrollPane1.updateUI();
        }
    }

    private void setCorLinhas(JTable jTable1) {

        Vector<Integer> indices = new Vector<Integer>();

        for (int j = 0; j
                < jTable1.getRowCount(); j++) {
            if (String.valueOf(jTable1.getValueAt(j, 0)).contains("TOTAL")) {
                indices.add(j);
            }

        }

        int posicoes[] = new int[indices.size()];
        for (int i = 0; i
                < posicoes.length; i++) {
            posicoes[i] = indices.get(i);
        }

        for (int i = 0; i
                < jTable1.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(new ColorCellRenderer(new Color(/*247, 241, 179*/255, 255, 180), posicoes));
        }
    }
}
