/*
 * AdrirXML.java
 *
 * Created on 13 de Agosto de 2008, 11:11
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.redebayesiana.io;

import br.ufpa.lprad.predict.redebayesiana.aprendizado.AuxiliarK2;
import br.ufpa.lprad.predict.redebayesiana.aprendizado.K2;
import br.ufpa.lprad.predict.redebayesiana.aprendizado.RedeBayesiana;
import java.io.File;
import java.util.Vector;

/**
 *
 * @author Gabriel
 */
public class AbrirXML {

    public static RedeBayesiana rb;
    public InterfaceXML inte;
    public File f;
    String caminho;
//    

    public AbrirXML(File f) {

        rb = new RedeBayesiana();
        inte = new InterfaceXML();
        this.f = f;// new File(PredictBayesConstants.DIR_XML+caminho.replaceAll(" ","").replace("-","")+".xml");

        inte.XMLparaRedebayesiana(f, rb);
        K2.setRedeBayesiana(rb);
        
        AuxiliarK2.calculaProbabilidadesMarginais();
        AuxiliarK2.calculaProbabilidadesCondicionais();

    }

    public static RedeBayesiana getRedeBaysiana() {
        return rb;
    }
}
