package br.ufpa.lprad.predict.previsao.multipla;
/*
 * Grafico.java
 *
 * Created on 23 de Abril de 2006
 */

import br.ufpa.lprad.predict.previsao.bean.PrevisaoBean;
import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class GraficoMultipla {

    JPanel painel;
    String Mediaerro;
    String nomeConsumo;
    String labelEixoY;
    String unidadeConsumo = "";
    ChartFrame frame;
    static Locale locale = new Locale("pt", "BR");
    static NumberFormat nf = NumberFormat.getNumberInstance(locale);

    /** Creates a new instance of Grafico */
    public GraficoMultipla(String[] periodo, String[] historico, String[] previsto, String erro, String[] erroMensal) {
        painel = new JPanel();
        TimeSeries s1 = new TimeSeries("Valor Previsto", Month.class);
        TimeSeries s2 = new TimeSeries("Valor Histórico", Month.class);
        int mes;
        int ano;

        for (int i = 0; i < historico.length; i++) {
            String[] per = periodo[i].split("/");
//            System.out.println(periodo[i]);
            mes = Integer.parseInt(per[0]);
            ano = Integer.parseInt(per[1]);
            if (!historico[i].equalsIgnoreCase("não aferido!")) {
                try {
                    s1.add(new Month(mes, ano), nf.parse(previsto[i]).doubleValue());//Double.parseDouble(corrigeValores(previsto[i])));
                } catch (ParseException e) {
                    s1.add(new Month(mes, ano), Double.parseDouble(corrigeValores(previsto[i])));
                }
                try {
                    s2.add(new Month(mes, ano), nf.parse(historico[i]).doubleValue());
                } catch (ParseException e) {
                    s2.add(new Month(mes, ano), Double.parseDouble(corrigeValores(historico[i])));
                }
            } else {
                try {
                    s1.add(new Month(mes, ano), nf.parse(previsto[i]).doubleValue());//Double.parseDouble(previsto[i]));
                } catch (ParseException ex) {
                    s1.add(new Month(mes, ano), Double.parseDouble(corrigeValores(previsto[i])));
                }
            }

        }

        Mediaerro = erro;

        if (Mediaerro.equals("não aferido!")) {
            Mediaerro = "inexistente";
        } else {
            Mediaerro = erro + "%";
        }

        unidadeConsumo = PrevisaoBean.getUnidadeConsumo();
        nomeConsumo = "Previsão de " + PrevisaoBean.getLabelConsumo() + " ";
        labelEixoY = PrevisaoBean.getLabelEixoY();

        TimeSeriesCollection data = new TimeSeriesCollection();
        data.addSeries(s1);
        data.addSeries(s2);
        JFreeChart chart = ChartFactory.createXYLineChart(
                nomeConsumo + "\n" + "Erro Percentual: " + Mediaerro,
                "Período (Mês/Ano)", labelEixoY,
                data,PlotOrientation.HORIZONTAL, true, true, false);


        XYPlot xyplot = chart.getXYPlot();
        XYItemRenderer renderer = xyplot.getRenderer();
        renderer.setSeriesStroke(0, new java.awt.BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[]{10.0f, 6.0f}, 0.0f));
        renderer.setSeriesPaint(0, Color.red);
        renderer.setSeriesPaint(1, Color.blue);
        java.awt.Color cor = new java.awt.Color(220, 220, 220);
        chart.setBackgroundPaint(cor);


        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(400, 200));

        painel.add(chartPanel);
        painel.setPreferredSize(new java.awt.Dimension(400, 200));
        frame = new ChartFrame("Predição de Consumo de Energia", chart);
        carregarIcone();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds((screenSize.width - 555) / 2, (screenSize.height - 288) / 2, 555, 448);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
    }

    public String corrigeValores(String val) {
        boolean temVirgula = false;
        String result;

        for (int i = 0; i < val.length(); i++) {
            if (val.charAt(i) == ',') {
                temVirgula = true;
                break;
            }
        }

        if (temVirgula) {
            result = val.replace(".", "").replace(",", ".");
        } else {
            result = val.replace(".", "");
        }

        return result;

    }

    public JPanel getPainel() {
        return painel;
    }

    private void carregarIcone() {
        // tenta carregar os ícones da aplicação
        try {
            frame.setIconImage(new ImageIcon(getClass().getResource("/predict.gif")).getImage());

        // carregar o ícone padrão de java
        } catch (Exception ex) {
            frame.setIconImage(null);
        }
    }
}