/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.graficos;

import br.ufpa.lprad.predict.graficos.modelo.ModeloGraficoErro;
import br.ufpa.lprad.predict.util.BigDecimalUtility;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

/**
 *
 * @author Arilene
 */
public class GeradorGrafico {

    public GeradorGrafico() {
    }

    /**

     * Gera um gráfico de barras 3D Vertical

     * @param tituloGrafico String, o titulo do gráfico

     * @param tituloEixoX String, o titulo do eixo X

     * @param tituloEixoY String, o titulo do eixo Y

     * @param arrayValores ArrayList, a lista com os valores para o gráfico

     * @return BufferedImage, a imagem do Gráfico gerada

     *

     * A classe DefaultCategoryDataset recebe os valores que irão gerar o gráfico

     * DefaultCategoryDataset.addValue(Number, Comparable, Comparable)

     * DefaultCategoryDataset.addValue(Double, Comparable, Comparable)

     */
    public static BufferedImage gerarGraficoBarraVertical3D(String tituloGrafico, String tituloEixoX, String tituloEixoY, ArrayList arrayValores, String erro) throws Exception {

        BufferedImage buf = null;

        try {

            DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();

            Iterator iterator = arrayValores.iterator();

            while (iterator.hasNext()) {
                ModeloGraficoErro modelo = (ModeloGraficoErro) iterator.next();
                defaultCategoryDataset.addValue(BigDecimalUtility.setFormattedCurrency(modelo.getValor()).doubleValue(),
                        modelo.getAno(), modelo.getPeriodo());

            }

            defaultCategoryDataset.addValue(Math.abs(Double.valueOf(erro.replace("Erro Percentual: ", "").replace("%", "").replace(",", ".")).doubleValue()), "Erro Percentual", "Erro Percentual");

            JFreeChart chart = ChartFactory.createBarChart3D(tituloGrafico + "\n" + erro, tituloEixoX,
                    tituloEixoY, defaultCategoryDataset, PlotOrientation.VERTICAL,
                    true, true, true);

            CategoryPlot cp = chart.getCategoryPlot();
            NumberAxis rangeAxis = (NumberAxis) cp.getRangeAxis();
//            rangeAxis.setUpperMargin(1);

            cp.setBackgroundPaint(Color.WHITE);
            cp.setDomainGridlinePaint(Color.black);
            cp.setDomainGridlinesVisible(true);
            cp.setRangeGridlinePaint(Color.white);
            Marker markerPositivo = new ValueMarker(3.0);
            markerPositivo.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, new float[]{5.0f, 6.0f}, 0.0f));
//            markerPositivo.setPaint(Color.lightGray);
            markerPositivo.setPaint(Color.getHSBColor(10, 0, 50));
            Marker markerNegativo = new ValueMarker(-3.0);
            markerNegativo.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, new float[]{5.0f, 6.0f}, 0.0f));
            markerNegativo.setPaint(Color.getHSBColor(10, 0, 50));
            cp.addRangeMarker(markerPositivo);
            cp.addRangeMarker(markerNegativo);

            CategoryItemRenderer renderer = cp.getRenderer();
            CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator("{0}", NumberFormat.getInstance());
            renderer.setItemLabelGenerator(generator);
            renderer.setItemLabelFont(new Font("SansSerif", Font.PLAIN, 12));
            renderer.setItemLabelsVisible(true);
            renderer.setPositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER, TextAnchor.CENTER,
                    -Math.PI / 9));
//            //////////////////////////////////////////////////////////////////////////////////
            CategoryAxis domainAxis = cp.getDomainAxis();
            domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 3.0));

            //////////////////////////////////////////////////////////////////////////////////
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

            throw new Exception(e);

        }

        return buf;

    }

    /**

     * Gera um gráfico de barras Vertical

    //     */
//    public static BufferedImage gerarGraficoBarraVertical(String tituloGrafico, String tituloEixoX, String tituloEixoY, ArrayList arrayValores) throws Exception {
//
//        BufferedImage buf = null;
//
//        try {
//
//            DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();
//
//            Iterator iterator = arrayValores.iterator();
//
//            while (iterator.hasNext()) {
//
//                ModeloGraficoItem modelo = (ModeloGraficoItem) iterator.next();
//
//                defaultCategoryDataset.addValue(modelo.getQuantidade(),
//                        modelo.getProduto(), modelo.getMes().substring(0, 3));
//
//            }
//
//            JFreeChart chart = ChartFactory.createBarChart(tituloGrafico, tituloEixoX,
//                    tituloEixoY, defaultCategoryDataset, PlotOrientation.VERTICAL, true, false, false);
//
//            chart.setBorderVisible(true);
//
//            chart.setBorderPaint(Color.black);
//
//            buf = chart.createBufferedImage(400, 250);
//
//        } catch (Exception e) {
//
//            throw new Exception(e);
//
//        }
//
//        return buf;
//
//    }
    /**

     * Gera um Grafico de Linhas

     */
//    public static BufferedImage gerarGraficoLinha(String tituloGrafico, String tituloEixoX,
//            String tituloEixoY, ArrayList arrayValores) throws Exception {
//
//        BufferedImage buf = null;
//
//        try {
//
//            DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();
//
//            Iterator iterator = arrayValores.iterator();
//
//            while (iterator.hasNext()) {
//
//                ModeloGraficoItem modelo = (ModeloGraficoItem) iterator.next();
//
//                defaultCategoryDataset.addValue(modelo.getQuantidade(),
//                        modelo.getProduto(), modelo.getMes().substring(0, 3));
//
//            }
//
//            JFreeChart chart = ChartFactory.createLineChart(tituloGrafico, tituloEixoX,
//                    tituloEixoY, defaultCategoryDataset, PlotOrientation.VERTICAL,
//                    true, false, false);
//
//            chart.setBorderVisible(true);
//
//            chart.setBorderPaint(Color.black);
//
//            buf = chart.createBufferedImage(400, 250);
//
//        } catch (Exception e) {
//
//            throw new Exception(e);
//
//        }
//
//        return buf;
//
//    }
    /**

     * Gera um grafico de linhas 3D

     */
//    public static BufferedImage gerarGraficoLinha3D(String tituloGrafico, String tituloEixoX, String tituloEixoY, ArrayList arrayValores) throws Exception {
//
//        BufferedImage buf = null;
//
//        try {
//
//            DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();
//
//            Iterator iterator = arrayValores.iterator();
//
//            while (iterator.hasNext()) {
//
//                ModeloGraficoItem modelo = (ModeloGraficoItem) iterator.next();
//
//                defaultCategoryDataset.addValue(modelo.getQuantidade(),
//                        modelo.getProduto(), modelo.getMes().substring(0, 3));
//
//            }
//
//            JFreeChart chart = ChartFactory.createLineChart3D(tituloGrafico, tituloEixoX,
//                    tituloEixoY, defaultCategoryDataset, PlotOrientation.VERTICAL,
//                    true, false, false);
//
//            chart.setBorderVisible(true);
//
//            chart.setBorderPaint(Color.black);
//            ChartPanel chartPanel = new ChartPanel(chart);
//            chartPanel.setPreferredSize(new java.awt.Dimension(400, 200));
//            JPanel painel = new JPanel();
//            painel.add(chartPanel);
//            painel.setPreferredSize(new java.awt.Dimension(400, 200));
//            ChartFrame frame = new ChartFrame("Predição de Consumo de Energia", chart);
//            java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
//            frame.setBounds((screenSize.width - 555) / 2, (screenSize.height - 288) / 2, 555, 448);
//            frame.setVisible(true);
//            frame.setAlwaysOnTop(true);
//
//            buf = chart.createBufferedImage(400, 250);
//
//        } catch (Exception e) {
//
//            throw new Exception(e);
//
//        }
//
//        return buf;
//
//    }
    public static void main(String[] args) {
//        try {
//            ComposicaoDadosItemGrafico cp = new ComposicaoDadosItemGrafico();
//            gerarGraficoBarraVertical3D("Teste", "x", "Y", cp.getArray());
//        } catch (Exception ex) {
//            Logger.getLogger(GeradorGrafico.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}