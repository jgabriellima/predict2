/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.actions;

import br.ufpa.lprad.predict.exception.PredictException;
import br.ufpa.lprad.predict.gui.window.EscolherVisualizarConsumo;
import br.ufpa.lprad.predict.gui.window.FrameAdminBaseDados;
import br.ufpa.lprad.predict.gui.window.correlacao.FrameCorrelacao;
import br.ufpa.lprad.predict.gui.window.correlacao.FrameDiscretizacao;
import br.ufpa.lprad.predict.gui.window.FrameSobre;
import br.ufpa.lprad.predict.gui.window.MostrarDados;
import br.ufpa.lprad.predict.gui.window.PreferenciasGUI;
import br.ufpa.lprad.predict.gui.window.previsao.PrevisaoGUI;
import br.ufpa.lprad.predict.gui.window.Principal;
import br.ufpa.lprad.predict.gui.window.VisualizaRB;
import br.ufpa.lprad.predict.gui.window.VisualizarRedeFrame;
import br.ufpa.lprad.predict.gui.window.backstage.GerenciadorDesktop;
import br.ufpa.lprad.predict.leitura.ControleLeitura;
import br.ufpa.lprad.predict.leitura.interfaces.InterfaceLeituraClimaticos;
import br.ufpa.lprad.predict.leitura.interfaces.InterfacerLeituraSocioEconomico;
import br.ufpa.lprad.predict.propriedades.PredictPropriedades;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JInternalFrame;

/**
 *
 * @author J. Gabriel Lima
 */
public class Action {

    public static FrameDiscretizacao fd;

    public static void AbrirRedeBayesiana() {
        GerenciadorDesktop.remove(Action.fd);
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    VisualizarRedeFrame vrf = new VisualizarRedeFrame("");
                    VisualizaRB vrf_ = new VisualizaRB();
                    GerenciadorDesktop.add(vrf_);
//                    vrf.setLayer(GerenciadorDesktop.getId_camada());
//                    GerenciadorDesktop.setId_camada(GerenciadorDesktop.getId_camada() + 1);
//                    Principal.getDesktopPane().add(vrf);
//                    Principal.getDesktopPane().getDesktopManager().maximizeFrame(vrf);
//                    Principal.getDesktopPane().updateUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void ActionFazerPrevisao(ActionEvent e) throws PredictException {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    GerenciadorDesktop.add(new PrevisaoGUI());
                } catch (PredictException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void ActionSair(ActionEvent e) {
        System.exit(0);
    }

    public static void ActionfazerCorrelacao(ActionEvent e) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
//                    CorrelacaoGUI m = new CorrelacaoGUI();
                    FrameCorrelacao m = new FrameCorrelacao();
                    m.setVisible(true);
                    GerenciadorDesktop.add(m);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void abrirFrameDiscretizacao(final String nome) {

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    //
                    fd = new FrameDiscretizacao(nome);
                    fd.setVisible(true);
                    GerenciadorDesktop.add(fd);
                //
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void abrirManual(ActionEvent evt) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    Desktop.getDesktop().open(new File(PredictPropriedades.getCaminhoPrincipal() + "doc\\Manual.pdf"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void abrirSobre(ActionEvent evt) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    Principal.getDesktopPane().add(new FrameSobre());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void actionAbrirConsumo(ActionEvent e, final int tipo) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    EscolherVisualizarConsumo m = new EscolherVisualizarConsumo(tipo);
                    m.setVisible(true);
//                    Principal.getDesktopPane().add(m);
                    GerenciadorDesktop.add(m);
                //  Principal.getDesktopPane().getDesktopManager().maximizeFrame(m);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void actionAbrirPreferencias(ActionEvent e) throws PredictException {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    GerenciadorDesktop.add(new PreferenciasGUI());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void actionAbrirSocioEconomico(ActionEvent evt, int tipo) {
        try {

            InterfacerLeituraSocioEconomico ise = new ControleLeitura();
            String[][] planilhaSE = ise.getPlanilhaSocioEconomico();

            String[] cabecalhoPlanilhaSocioEconomico = ise.getCabecalhoPlanilhaSocioEconomico();
            String topo = ise.getTopoSE();

            MostrarDados m = new MostrarDados(planilhaSE, cabecalhoPlanilhaSocioEconomico, "", topo, tipo);
            GerenciadorDesktop.add(m);

            ise = null;

        } catch (Exception e) {
        }
    }

    public static void actionAbrirVisualizaConsumo(ActionEvent evt, String[][] planilhaConsumo, String[] cabecalhoPlanilhaConsumo, String tipoConsumo, String topo, int tipo) {


        try {
            MostrarDados m = new MostrarDados(planilhaConsumo, cabecalhoPlanilhaConsumo, tipoConsumo, topo, tipo);
            GerenciadorDesktop.add(m);



        } catch (Exception e) {
        }


    }

    public static void actionAbrirclimaticos(ActionEvent e, int tipo) {
        try {
            InterfaceLeituraClimaticos ilc = new ControleLeitura();
            String[][] planilhaClimaticos = ilc.getPlanilhaClimaticos();
            String[] cabecalhoPlanilhaClimaticos = ilc.getCabecalhoPlanilhaClimaticos();
            String topo = ilc.getTopoPlanilhaClimaticos();

            MostrarDados m = new MostrarDados(planilhaClimaticos, cabecalhoPlanilhaClimaticos, "", topo, tipo);
            GerenciadorDesktop.add(m);

            ilc = null;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void actionIniciarTreinamento(final JInternalFrame jif) {
        new Thread() {
            public void run() {
                try {
                    GerenciadorDesktop.add(jif);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }.start();
    }

    public static void administrarBaseDeDados(ActionEvent evt) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    GerenciadorDesktop.add(new FrameAdminBaseDados());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
