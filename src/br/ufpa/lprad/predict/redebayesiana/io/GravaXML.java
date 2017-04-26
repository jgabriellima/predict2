/*
 * GravaXML.java
 *
 * Created on 13 nPais Agosto nPais 2008, 12:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.redebayesiana.io;

import br.ufpa.lprad.predict.propriedades.PredictPropriedades;
import br.ufpa.lprad.predict.redebayesiana.aprendizado.RedeBayesiana;
import br.ufpa.lprad.predict.redebayesiana.aprendizado.Vertice;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author Gabriel
 */
public class GravaXML {

    String tipo;
    RedeBayesiana rb;
    String ver = "vertice";

    public GravaXML(String correlacao, RedeBayesiana rb) {
        this.tipo = correlacao.replaceAll(" ", "").replace("ó", "o").replace("ô", "o").replace("-", "");

        // System.out.println(""+tipo);
        this.rb = rb;
        Element bayes = new Element(tipo);


        //Element tpc = new Element("tpc");  

        Element[] vertice = new Element[this.rb.getVertices().size()];

        Vector vetor = rb.getVertices();
        for (int i = 0; i < vertice.length; i++) {
            Element nome = new Element("nome");
            Element nPais = new Element("nPais");
            Element nFilhos = new Element("nFilhos");
            Element estados = new Element("estados");
            Element pais = new Element("pais");
            Element filhos = new Element("filhos");

            nome.addContent(((Vertice) vetor.elementAt(i)).getNome());
            nPais.addContent(String.valueOf(((Vertice) vetor.elementAt(i)).getPais().size()));
            nFilhos.addContent(String.valueOf(((Vertice) vetor.elementAt(i)).getFilhos().size()));
            estados.addContent(retornaEstados(vetor.elementAt(i)));
            pais.addContent(retornaPais(vetor.elementAt(i)));
            filhos.addContent(retornaFilhos(vetor.elementAt(i)));

            vertice[i] = new Element("vertice");
            //vertice[i].setAttribute("id", String.valueOf(i));

            vertice[i].addContent(nome);
            vertice[i].addContent(nPais);
            vertice[i].addContent(nFilhos);
            vertice[i].addContent(estados);
            vertice[i].addContent(pais);
            vertice[i].addContent(filhos);

            bayes.addContent(vertice[i]);

        }


        //Criando o documento XML (montado)
        Document doc = new Document();

        doc.setRootElement(bayes);
        FileWriter out = null;
        try {
            out = new FileWriter(PredictPropriedades.getCaminhoDiretorioXML() + "\\" + tipo.concat(".xml"));
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

    public String retornaEstados(Object vertice) {

        StringBuffer retorno = new StringBuffer();

        Vertice v = (Vertice) vertice;
        // System.out.println("tam: "+v.getNEstados());
        for (int i = 0; i < v.getNEstados(); i++) {
            try {
                //   System.out.println("fdf: "+v.getEstados()[i]);
                retorno.append(v.getEstados()[i] + ",");
            } catch (Exception ex) {
                //  System.out.println("fdf: "+v.getEstados()[i]);
            }
        }

        return retorno.toString();
    }

    public String retornaPais(Object vertice) {

        StringBuffer retorno = new StringBuffer();

        Vertice v = (Vertice) vertice;

        for (int i = 0; i < v.getNPais(); i++) {
            retorno.append(((Vertice) v.getPais().elementAt(i)).getNome() + ",");
        }

        // System.out.println(""+retorno.toString());
        return retorno.toString();
    }

    public String retornaFilhos(Object vertice) {

        StringBuffer retorno = new StringBuffer();

        Vertice v = (Vertice) vertice;

        for (int i = 0; i < v.getFilhos().size(); i++) {

            retorno.append(((Vertice) v.getFilhos().elementAt(i)).getNome() + ",");

        }
        // System.out.println(""+retorno.toString());
        return retorno.toString();
    }
}
