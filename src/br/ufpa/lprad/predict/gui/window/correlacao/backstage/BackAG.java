/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.gui.window.correlacao.backstage;

import br.ufpa.lprad.predict.redebayesiana.aprendizado.K2;
import br.ufpa.lprad.predict.redebayesiana.inferencias.Itens;
import br.ufpa.lprad.predict.redebayesiana.inferencias.LabelsFaixa;
import br.ufpa.lprad.predict.redebayesiana.inferencias.MetaDados;

/**
 *
 * @author gabriel
 */
public class BackAG {

    public static MetaDados meta;// = Itens.meta;
    public static LabelsFaixa[] labelsFaixa;

    public static String[] obtemFaixasDoAtributo(int atributo) {

        meta = Itens.meta;
        String[] faixas = new String[meta.getQntdFaixas(atributo)];
        labelsFaixa = new LabelsFaixa[meta.getQntdFaixas(atributo)];
        for (int i = 0; i < meta.getQntdFaixas(atributo); i++) {
            //Cria os Labels das probabilidades das faixas dos atributos
            //Cria os Labels das faixas dos atributos
            labelsFaixa[i] = new LabelsFaixa(atributo, i, meta);
            faixas[i] = labelsFaixa[i].getNomeFAixa();
        }

        return faixas;
    }
}
