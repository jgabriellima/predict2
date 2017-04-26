/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.gui.window.backstage;

import br.ufpa.lprad.predict.gui.window.correlacao.FrameRelatorioInferencias;
import br.ufpa.lprad.predict.gui.window.Principal;
import java.util.Vector;
import javax.swing.JInternalFrame;

/**
 *
 * @author Arilene
 */
public class GerenciadorDesktop {

    private static int id_camada = 10;
    private static Vector camada = new Vector();
    private static int x,  y;

    public static void add(final JInternalFrame jif) {


        Thread t = new Thread() {

            public void run() {
                try {
                    if (validaFrame(jif)) {
                        while (camada.contains(id_camada)) {
                            id_camada = id_camada + 2;
                        }
                        jif.setLayer(id_camada);
                        setLocalizacaoIF(jif);

                        if (!jif.isVisible()) {
                            jif.setVisible(true);
                        }

                        Principal.getDesktopPane().add(jif);
                        if (jif.isMaximizable()) {
                            Principal.getDesktopPane().getDesktopManager().maximizeFrame(jif);
                        }
                        Principal.getDesktopPane().updateUI();
                        id_camada++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();


    }

    public static void add(final JInternalFrame jif, final boolean b) {
        Thread t = new Thread() {

            public void run() {
                try {
                    if (validaFrame(jif)) {
                        while (camada.contains(id_camada)) {
                            id_camada = id_camada + 2;
                        }
                        jif.setLayer(id_camada);
                        setLocalizacaoIF(jif);

                        Principal.getDesktopPane().add(jif);
                        if (b) {
                            Principal.getDesktopPane().getDesktopManager().maximizeFrame(jif);
                        }
                        Principal.getDesktopPane().updateUI();
                        id_camada++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    public static boolean validaFrame(JInternalFrame jif) {
        boolean retorno = true;
        try {
            JInternalFrame[] ifs = Principal.getDesktopPane().getAllFrames();
            for (JInternalFrame itf : ifs) {
                if (itf.getTitle().equalsIgnoreCase(jif.getTitle())) {
                    try {
                        retorno = false;
                        itf.setFocusable(true);
                        itf.setSelected(true);
                        itf.setLayer(id_camada);
                        //  itf.setLayeredPane(new JLayeredPane());
                        id_camada++;
                        itf.updateUI();
                        break;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    retorno = true;
                }
            }
        } catch (Exception e) {
        }

        return retorno;
    }

    public static void remove(JInternalFrame jif) {

        camada.removeElement(jif.getLayer());
        Principal.getDesktopPane().remove(jif);
    }

    private static void setLocalizacaoIF(JInternalFrame jif) {

        double metadeTelaHorizontal = (Principal.getDesktopPane().getWidth() / 2);
        double metadeTelaVertical = (Principal.getDesktopPane().getHeight() / 2);

        double metadeFrameHorizontal = jif.getWidth() / 2;
        double metadeFrameVertical = jif.getHeight() / 2;



        int largura = (int) (metadeTelaHorizontal - metadeFrameHorizontal);//Principal.getDesktopPane().getWidth();
        int altura = (int) (metadeTelaVertical - metadeFrameVertical);//Principal.getDesktopPane().getHeight();

        jif.setBounds(/*largura / 5*/largura, /*altura / 5*/ altura, jif.getWidth(), jif.getHeight());
        jif.updateUI();

    }

    public static int getId_camada() {
        return id_camada;
    }

    public static void setId_camada(int id_camada) {
        GerenciadorDesktop.id_camada = id_camada;
    }
}
