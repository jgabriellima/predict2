/*
 * ThreadGeraRelProjecao.java
 *
 * Created on 1 de Maio de 2006, 03:34
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */
package br.ufpa.lprad.predict.previsao.taxadecrescimento;

import br.ufpa.lprad.predict.gui.window.Principal;
import br.ufpa.lprad.predict.gui.window.previsao.RelHistoricoFrame;
import br.ufpa.lprad.predict.gui.window.backstage.GerenciadorDesktop;

/**
 *
 * @author ramon
 */
public class ThreadGeraRelProjecao extends Thread {

    /** Creates a new instance of ThreadGeraRelProjecao */
    public ThreadGeraRelProjecao() {
    }

    public void run() {
        RelHistoricoFrame relHistorico = new RelHistoricoFrame();
        GerenciadorDesktop.add(relHistorico);


        try {
            Principal.getDesktopPane().getDesktopManager().maximizeFrame(relHistorico);
        } catch (Exception ex) {
        }
    }
}
