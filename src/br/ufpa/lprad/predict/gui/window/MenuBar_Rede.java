/*
 * MenuBar_Rede.java
 *
 * Created on 7 de Setembro de 2008, 17:00
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.gui.window;

import br.ufpa.lprad.predict.actions.Actions_Rede;
import br.ufpa.lprad.predict.redebayesiana.inferencias.Itens;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

/**
 *
 * @author Gabriel
 */
public class MenuBar_Rede extends JMenuBar {

    public static JMenu inicio,  opcoes;
    public static JMenuItem analise_temp,  sair,  desfazer_Inf,  algoritmo_genetico,  mostrarRelatorio,  mostrarRelatorioAg,  expandirAtributos;

//    public static JCheckBoxMenuItem vizualiza_relatorio;
    public MenuBar_Rede(final JInternalFrame frame) {

        inicio = new JMenu("Início");
        opcoes = new JMenu("Opções");

        /*----------------Analise Temporal-------------------*/
        analise_temp = new JMenuItem("Analise Temporal Markoviana");
        analise_temp.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Actions_Rede.action_analise_temp(e, frame);
            }
        });

        /*---------------------Sair----------------------*/
        sair = new JMenuItem("Sair");
        sair.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Actions_Rede.action_sair(e, frame);
            }
        });
        /*-------------------Desfazer Inferencia----------------------*/
        desfazer_Inf = new JMenuItem("Desfazer Inferências");
        desfazer_Inf.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Actions_Rede.action_desfazer_infe(e, frame);
            }
        });

        /*-----------------------Vizualizar Relatorio-----------------------*/
//        vizualiza_relatorio = new JCheckBoxMenuItem("Vizualizar Relatório");
//        vizualiza_relatorio.setState(true);
//        vizualiza_relatorio.addItemListener(new ItemListener() {
//            public void itemStateChanged(ItemEvent e) {
//                Actions_Rede.action_vizualiza_relatorio(e,frame);
//            }
//        });

        mostrarRelatorio = new JMenuItem("Mostar Relatório");
        mostrarRelatorio.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Actions_Rede.mostrarRelatorio(e, frame);
            }
        });


        mostrarRelatorioAg = new JMenuItem("Mostar Relatório de Cenários");
        mostrarRelatorioAg.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Actions_Rede.mostrarRelatorioAg(e, frame);
            }
        });

        /*------------------- Algoritmo Genético ----------------------*/
        algoritmo_genetico = new JMenuItem("Análise de Cenários");
        algoritmo_genetico.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Actions_Rede.action_algoritmo_genetico(e, frame);
            }
        });
        /*------------------- Expandir Atributos ----------------------*/
        expandirAtributos = new JMenuItem("Expandir Atributos");
        expandirAtributos.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                
                Actions_Rede.expandir(e, frame);
            }
        });

        Actions_Rede.setAtalhos();
//        inicio.add(analise_temp);
        inicio.add(algoritmo_genetico);
        inicio.add(desfazer_Inf);
        inicio.add(new JSeparator());
        inicio.add(sair);
        opcoes.add(mostrarRelatorio);//vizualiza_relatorio);
        opcoes.add(mostrarRelatorioAg);//vizualiza_relatorio);
        opcoes.add(expandirAtributos);//expandirAtributos);

        add(inicio);
        add(opcoes);


    }
}
