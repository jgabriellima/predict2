/*
 * RelatorioViewer.java
 *
 * Created on 9 de Julho de 2006, 01:09
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.ufpa.lprad.predict.gui.window;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author ramon
 */
public class RelatorioViewer extends JasperViewer {
    
    /** Creates a new instance of RelatorioViewer */
    public RelatorioViewer(JasperPrint jp, boolean exitOnClose) {
        super(jp,exitOnClose);
        // if (exitOnClose)
        //    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //  else
        super.setTitle("Visualização do Relatório");
        super.setIconImage(new ImageIcon(getClass().getResource("/predict.gif")).getImage());
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
}
