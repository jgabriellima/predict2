/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.gui.window.correlacao.backstage;

import br.ufpa.lprad.predict.gui.window.backstage.*;
import br.ufpa.lprad.predict.correlacao.CorrelacaoBean;
import br.ufpa.lprad.predict.correlacao.GeraArquivoCorrelacao;
import br.ufpa.lprad.predict.correlacao.discretizacao.AtributoDiscreto;
import br.ufpa.lprad.predict.correlacao.discretizacao.Discretizacao;
import br.ufpa.lprad.predict.correlacao.discretizacao.Faixa;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author gabriel
 */
public class BackDiscretizacaoGUI {

    private static Vector<CorrelacaoBean> listaCorrelacao;
    public static AtributoDiscreto[] atribDisc;

    public static Faixa[] getFaixasTabela(JTable tabela) {

        Faixa[] fxs = new Faixa[tabela.getRowCount()];

        for (int i = 0; i < tabela.getRowCount(); i++) {
            fxs[i] = new Faixa();

            fxs[i].setLimiteInferior(Double.valueOf(String.valueOf(tabela.getValueAt(i, 0))));
            fxs[i].setLimiteSuperior(Double.valueOf(String.valueOf(tabela.getValueAt(i, 1))));

        }
        return fxs;

    }
//    private static Discretizacao disc;

    public static Object[] getListaAnalise(String analise) {

        Object[] objs = BackCorrelacaoGUI.getAtributosCorrelacao(analise);
        return objs;
    }

//    public static JPanel[] getFaixasAtributo(String atributo, int indiceAtrib, int faixas) {
    public static JTable getFaixasAtributo(String atributo, int indiceAtrib, int faixas) {

        JTable tabela = new JTable(faixas, 2);

        Object[] dados = GeraArquivoCorrelacao.getDadosSelecionados().get(indiceAtrib);

        Faixa[] fxs = Discretizacao.getFaixasAtrib(atributo, dados, faixas);

        tabela.setDefaultEditor(Object.class, new SpinnerTableCell());

        Object[][] d = new Object[faixas][2];

        for (int i = 0; i < faixas; i++) {
            d[i][0] = fxs[i].getLimiteInferior();
            d[i][1] = fxs[i].getLimiteSuperior();
        }

        tabela.setModel(new DefaultTableModel(d, new String[]{"Inferior", "Superior"}));
        tabela.updateUI();

        return tabela;
    }

    public static Faixa[] getFaixas(String atributo, int indiceAtrib, int faixas) {
        Object[] dados = GeraArquivoCorrelacao.getDadosSelecionados().get(indiceAtrib);
        return Discretizacao.getFaixasAtrib(atributo, dados, faixas);
    }

    public static JTable getFaixas(Faixa[] faixas) {
        JTable tabela = new JTable(faixas.length, 2);
        tabela.setDefaultEditor(Object.class, new SpinnerTableCell());
        Object[][] d = new Object[faixas.length][2];

        for (int i = 0; i < faixas.length; i++) {
            d[i][0] = faixas[i].getLimiteInferior();
            d[i][1] = faixas[i].getLimiteSuperior();
        }
        tabela.setModel(new DefaultTableModel(d, new String[]{"Inferior", "Superior"}));
        tabela.updateUI();

        return tabela;
    }
}
