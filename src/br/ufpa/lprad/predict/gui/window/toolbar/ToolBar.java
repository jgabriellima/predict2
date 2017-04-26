/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.gui.window.toolbar;

import br.ufpa.lprad.predict.actions.Action;
import br.ufpa.lprad.predict.exception.PredictException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 *
 * @author Arilene
 */
public class ToolBar extends JToolBar {

    public JButton abrirConsumo,  abrirClimaticos,  abrirSocioEconomico,  fazerCorrelacao,  abrirPreferencias,  fazerPrevisao,  sair;

    public ToolBar() {

        abrirConsumo = new JButton();
        abrirConsumo.setIcon(new ImageIcon(getClass().getResource("/gpl/drive-harddisk.png")));
        abrirConsumo.setToolTipText("Visualizar Dados de Consumo");
        abrirConsumo.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Action.actionAbrirConsumo(e,0);
            }
        });

        abrirClimaticos = new JButton();
        abrirClimaticos.setIcon(new ImageIcon(getClass().getResource("/gpl/drive-harddisk_2.png")));
        abrirClimaticos.setToolTipText("Visualizar Dados Climáticos");
        abrirClimaticos.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Action.actionAbrirclimaticos(e,2);
            }
        });

        abrirSocioEconomico = new JButton();
        abrirSocioEconomico.setIcon(new ImageIcon(getClass().getResource("/gpl/drive-harddisk_3.png")));
        abrirSocioEconomico.setToolTipText("Visualizar dados Sócio-Econôicos");
        abrirSocioEconomico.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Action.actionAbrirSocioEconomico(e,1);
            }
        });

        abrirPreferencias = new JButton();
        abrirPreferencias.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gpl/system-run.png")));
        abrirPreferencias.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    Action.actionAbrirPreferencias(e);
                } catch (PredictException ex) {
                    ex.printStackTrace();
                }
            }
        });

        fazerCorrelacao = new JButton();
        fazerCorrelacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gpl/package_network.png")));
        fazerCorrelacao.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Action.ActionfazerCorrelacao(e);
            }
        });

        fazerPrevisao = new JButton();
        fazerPrevisao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gpl/gnome-monitor.png")));
        fazerPrevisao.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    Action.ActionFazerPrevisao(e);
                } catch (PredictException ex) {
                }
            }
        });



        sair = new JButton();
        sair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gpl/exit.png")));
        sair.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Action.ActionSair(e);
            }
        });

        this.add(abrirConsumo);
        this.add(abrirSocioEconomico);
        this.add(abrirClimaticos);
        this.add(fazerCorrelacao);
        this.add(fazerPrevisao);
        this.add(abrirPreferencias);
        this.add(sair);

    }
}
