package br.ufpa.lprad.predict.previsao.multipla.grafico;

import br.ufpa.lprad.predict.leitura.ControleLeitura;
import br.ufpa.lprad.predict.leitura.interfaces.InterfaceLeituraConsumo;
import br.ufpa.lprad.predict.previsao.taxadecrescimento.grafico.ModeloGraficoTaxaCrescimento;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * @author gabriel
 */
public class GraficoPrevisaoMultipla {

    private Vector<String> previsto;
    private Vector<String> historico;
    private Vector<String> periodo;
    private String erro;
    private ArrayList<ModeloGraficoMultipla> lista;
    private String naoExisteMsg = "não aferido!";
    private InterfaceLeituraConsumo ilc;
    private String tituloGrafico,  tituloEixoX,  tituloEixoY;
    private String[] anosAnteriores;
    private String[] anosAnterioresPeriodo;
    private ModeloGraficoMultipla modelo;
    private String[] anosSelecionados;
    private double[][] planilha;

    public GraficoPrevisaoMultipla() {
    }
    public GraficoPrevisaoMultipla(String tituloGrafico, String tituloEixoX,
            String tituloEixoY, Vector<String> periodo, Vector<String> previsto, Vector<String> historico, String erro, String[] anosSelecionados, double[][] planilha) {
        this.previsto = previsto;
        this.historico = historico;
        this.periodo = periodo;
        this.erro = erro;
        this.tituloEixoX = tituloEixoX;
        this.tituloEixoY = tituloEixoY;
        this.tituloGrafico = tituloGrafico;
        this.anosSelecionados = anosSelecionados;
        this.planilha = planilha;


        lista = new ArrayList<ModeloGraficoMultipla>();
        try {
            ilc = new ControleLeitura();
        } catch (Exception e) {
        }
        criaLista();
    }

    public GraficoPrevisaoMultipla(String tituloGrafico, String tituloEixoX, String tituloEixoY,
            Vector<String> periodo, Vector<String> previsto, Vector<String> historico, String erro) {

        this.previsto = previsto;
        this.historico = historico;
        this.periodo = periodo;
        this.erro = erro;
        this.tituloEixoX = tituloEixoX;
        this.tituloEixoY = tituloEixoY;
        this.tituloGrafico = tituloGrafico;

        lista = new ArrayList<ModeloGraficoMultipla>();
        try {
            ilc = new ControleLeitura();
        } catch (Exception e) {
        }
        criaLista();
    }

    private void criaLista() {

        // DADOS PREVISTOS
        for (int i = 0; i < previsto.size(); i++) {
            lista.add(new ModeloGraficoMultipla(formataMes(periodo.get(i).split("/")[0]), previsto.get(i), periodo.get(i).split("/")[1] + " - Previsto"));
        }

          if (anosSelecionados != null) {
            for (int i = 0; i < anosSelecionados.length; i++) {
                for (int j = 0; j < planilha.length; j++) {
                    lista.add(new ModeloGraficoMultipla(formataMes(String.valueOf(j)), String.valueOf(planilha[j][i]), anosSelecionados[i] + " - Histórico"));
                }
            }
        }

//        System.out.println("historico.size(): "+historico.size()+" - previsto.size(): "+previsto.size());
//
//        for (int i = 0; i < historico.size(); i++) {
//            System.out.println("historico.get("+i+"): "+historico.get(i));
//        }

        // DADOS HISTORICO - PREVISTO
//        for (int i = 0; i < historico.size(); i++) {
//            if (!historico.get(i).equalsIgnoreCase(naoExisteMsg)) {
//                lista.add(new ModeloGraficoMultipla(formataMes(periodo.get(i).split("/")[0]), historico.get(i), periodo.get(i).split("/")[1] + " - Histórico"));
//            } else {
//                break;
//            }
//        }

        /*
         * DADOS HISTORICO ANTERIORES
         */
//        int anoFinal = Integer.valueOf(periodo.get(0).split("/")[1]).intValue();
//        --anoFinal;
//
//        anosAnteriores = ilc.consumoEspecifico(PrevisaoBean.getTipoConsumo(), PrevisaoBean.getNomeConsumo(), String.valueOf(anoFinal - 3), String.valueOf(anoFinal));
//        anosAnterioresPeriodo = ilc.PeriodoEspecifico(PrevisaoBean.getTipoConsumo(), PrevisaoBean.getNomeConsumo(), String.valueOf(anoFinal - 3), String.valueOf(anoFinal));
//        if (anosAnteriores != null) {
//            for (int i = 0; i < anosAnteriores.length; i++) {
//                lista.add(new ModeloGraficoTaxaCrescimento(anosAnterioresPeriodo[i].split("/")[1], anosAnteriores[i], anosAnterioresPeriodo[i].split("/")[0]));
//            }
//        }

        gerarGraficoLinha(tituloGrafico, tituloEixoX, tituloEixoY, lista);

    }

    public static BufferedImage gerarGraficoLinha(String tituloGrafico, String tituloEixoX,
            String tituloEixoY, ArrayList<ModeloGraficoMultipla> arrayValores) {

        BufferedImage buf = null;

        try {

            DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();

            Iterator iterator = arrayValores.iterator();

            while (iterator.hasNext()) {

                ModeloGraficoMultipla modelo = (ModeloGraficoMultipla) iterator.next();
                defaultCategoryDataset.addValue(Math.abs(Double.valueOf(modelo.getValor().replace(",", ".")).doubleValue()),
                        modelo.getAno(), modelo.getPeriodo());
            }

            JFreeChart chart = ChartFactory.createLineChart(tituloGrafico, tituloEixoX,
                    tituloEixoY, defaultCategoryDataset, PlotOrientation.VERTICAL,
                    true, true, false);

            CategoryPlot cp = chart.getCategoryPlot();
            NumberAxis rangeAxis = (NumberAxis) cp.getRangeAxis();
            rangeAxis.setAutoRangeIncludesZero(false);

            cp.setBackgroundPaint(Color.WHITE);
            cp.setDomainGridlinePaint(Color.black);
            cp.setDomainGridlinesVisible(true);
            cp.setRangeGridlinePaint(Color.white);


            //////////////////////////////////////////////////////////////////////////////////
            chart.setBorderPaint(Color.black);

            buf = chart.createBufferedImage(400, 250);
            chart.setBorderVisible(true);
            chart.setBorderPaint(Color.black);
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new java.awt.Dimension(500, 300));
            JPanel painel = new JPanel();
            painel.add(chartPanel);
            painel.setPreferredSize(new java.awt.Dimension(500, 300));
            ChartFrame frame = new ChartFrame("Predição de Consumo de Energia", chart);
            java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            frame.setBounds((screenSize.width - 555) / 3, (screenSize.height - 288) / 3, 600, 460);
            frame.setIconImage(new ImageIcon().getImage());
            frame.setVisible(true);
            frame.setAlwaysOnTop(true);

            buf = chart.createBufferedImage(400, 250);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao gerar o gráfico: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return buf;

    }

    public static String formataMes(String data) {
        if (data.equalsIgnoreCase("Janeiro") || data.equalsIgnoreCase("01") || data.equalsIgnoreCase("1")) {
            return "jan";
        } else if (data.equalsIgnoreCase("Fevereiro") || data.equalsIgnoreCase("02") || data.equalsIgnoreCase("2")) {
            return "fev";
        } else if (data.equalsIgnoreCase("Março") || data.equalsIgnoreCase("03") || data.equalsIgnoreCase("3")) {
            return "mar";
        } else if (data.equalsIgnoreCase("Abril") || data.equalsIgnoreCase("04") || data.equalsIgnoreCase("4")) {
            return "abr";
        } else if (data.equalsIgnoreCase("Maio") || data.equalsIgnoreCase("05") || data.equalsIgnoreCase("5")) {
            return "mai";
        } else if (data.equalsIgnoreCase("Junho") || data.equalsIgnoreCase("06") || data.equalsIgnoreCase("6")) {
            return "jun";
        } else if (data.equalsIgnoreCase("Julho") || data.equalsIgnoreCase("07") || data.equalsIgnoreCase("7")) {
            return "jul";
        } else if (data.equalsIgnoreCase("Agosto") || data.equalsIgnoreCase("08") || data.equalsIgnoreCase("8")) {
            return "ago";
        } else if (data.equalsIgnoreCase("Setembro") || data.equalsIgnoreCase("09") || data.equalsIgnoreCase("9")) {
            return "set";
        } else if (data.equalsIgnoreCase("Outubro") || data.equalsIgnoreCase("10")) {
            return "out";
        } else if (data.equalsIgnoreCase("Novembro") || data.equalsIgnoreCase("11")) {
            return "nov";
        } else {
            return "dez";
        }
    }
}
