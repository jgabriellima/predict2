
package br.ufpa.lprad.predict.previsao.neural;

import br.ufpa.lprad.predict.gui.window.Principal;
import br.ufpa.lprad.predict.gui.window.previsao.RelHistoricoRNA;
import br.ufpa.lprad.predict.gui.window.backstage.GerenciadorDesktop;

/**
 *
 * @author Gabriel Lima
 */
public class ThreadGeraRelProjecaoRNA extends Thread {

    /** Creates a new instance of ThreadGeraRelProjecao */
    public ThreadGeraRelProjecaoRNA() {
    }

    public void run() {
        RelHistoricoRNA relHistorico = new RelHistoricoRNA();
        GerenciadorDesktop.add(relHistorico);


        try {
            Principal.getDesktopPane().getDesktopManager().maximizeFrame(relHistorico);
        } catch (Exception ex) {
        }
    }
}
