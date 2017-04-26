/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.correlacao.io;

import br.ufpa.lprad.predict.correlacao.*;
import br.ufpa.lprad.predict.gui.window.correlacao.backstage.BackCorrelacaoGUI;
import br.ufpa.lprad.predict.propriedades.PredictPropriedades;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author J. Gabriel Lima
 */
public class GeraXMLCorrelacao {

    public static Vector correc = new Vector();

    public static void gerarXmlCorrelacao() {
        Element bayes = new Element("root");

        correc = BackCorrelacaoGUI.getCorrelacoes();
        
        for (int i = 0; i < correc.size(); i++) {
            Element correlacao1 = new Element("c"+i);
            Element nome = new Element("nome");
            nome.addContent(((CorrelacaoBean) correc.get(i)).getNome());

            Element atributos = new Element("atributos");

            atributos.addContent(((CorrelacaoBean) correc.get(i)).getAtributos());

            correlacao1.addContent(nome);
            correlacao1.addContent(atributos);

            bayes.addContent(correlacao1);

        }

        Document doc = new Document();

        doc.setRootElement(bayes);
        FileWriter out = null;
        try {
            out = new FileWriter(PredictPropriedades.getCorrelacaoXML());///*PredictPropriedades.getCaminhoDiretorioXML()*/"C:\\Documents and Settings\\J. Gabriel Lima\\Desktop" + "\\" + "Correlacao".concat(".xml"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        //Imptrimindo o XML
        XMLOutputter xout = new XMLOutputter();
        Format formatXML = Format.getPrettyFormat();

        formatXML.setEncoding("ISO-8859-1");
        xout.setFormat(formatXML);
        try {
            xout.output(doc, out);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static Vector getCorrec() {
        return correc;
    }

    public static void setCorrec(Vector correc) {
        GeraXMLCorrelacao.correc = correc;
    }
    

    public static void main(String[] args) {


        CorrelacaoBean cb1 = new CorrelacaoBean("Classe", "tipo2,tipo3,tipo4,tipo5");
        CorrelacaoBean cb2 = new CorrelacaoBean("Classe2", "tipo8,tipo3,tipo1,tipo9");
        CorrelacaoBean cb3 = new CorrelacaoBean("Classe3", "tipo6,tipo3,tipo2,tipo1");


        getCorrec().add(cb1);
        getCorrec().add(cb2);
        getCorrec().add(cb3);

        gerarXmlCorrelacao();

    }
}
