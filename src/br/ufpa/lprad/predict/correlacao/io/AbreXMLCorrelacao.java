/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.correlacao.io;

import br.ufpa.lprad.predict.correlacao.*;
import br.ufpa.lprad.predict.gui.window.correlacao.backstage.BackCorrelacaoGUI;
import br.ufpa.lprad.predict.propriedades.PredictPropriedades;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author J. Gabriel Lima
 */
public class AbreXMLCorrelacao {

    private static File file;
    private static Document document;
    private static int nCorrelacaoLida;

    public static void abrirXmlCorrelacao() {
        File f = new File(PredictPropriedades.getCorrelacaoXML());
        file = f;
        //Criamos uma classe SAXBuilder que vai processar o XML
        BackCorrelacaoGUI.correlacoes = new Vector();
        SAXBuilder sb = new SAXBuilder();

        try {
            //Este documento agora possui toda a estrutura do arquivo.
            Document d = sb.build(f);
            document = d;
         //   System.out.println("..." + f.getAbsolutePath());

            //Recuperamos o elemento root
            Element elementoRoot = d.getRootElement();

            //Recuperamos os elementos filhos (children)
            List listaElementos = elementoRoot.getChildren();
            //Recuperando o número de correlacoes
            int nCorrelacoes = listaElementos.size();

            BackCorrelacaoGUI.setNCorrelacoes(nCorrelacoes);

            Iterator i = listaElementos.iterator();
            //Aqui declaramos um vector com o número de vertices da rede

            //Elementos da iteração para recuperar as informações do XML


            while (i.hasNext()) {

                Element element = (Element)i.next();

                CorrelacaoBean cb = new CorrelacaoBean();
                cb.setNome(element.getChildText("nome"));
                cb.setAtributos(element.getChildText("atributos"));
                cb.setAtrib(element.getChildText("atributos").split(","));

                BackCorrelacaoGUI.getCorrelacoes().add(cb);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
