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
public class AbreXMLCorrelacaoPadrao {

    private static File file;
    private static Document document;
    private static int nCorrelacaoLida;

    public static void abrirXmlCorrelacao() {
        File f = new File(PredictPropriedades.getCorrelacaoXMLPadrao());
        file = f;
        //Criamos uma classe SAXBuilder que vai processar o XML
        BackCorrelacaoGUI.correlacoes = new Vector();
        SAXBuilder sb = new SAXBuilder();

        try {
            //Este documento agora possui toda a estrutura do arquivo.
            Document d = sb.build(f);
            document = d;
           // System.out.println("..." + f.getAbsolutePath());

            //Recuperamos o elemento root
            Element elementoRoot = d.getRootElement();

            //Recuperamos os elementos filhos (children)
            List listaElementos = elementoRoot.getChildren();
            //Recuperando o n�mero de correlacoes
            int nCorrelacoes = listaElementos.size();

            BackCorrelacaoGUI.setNCorrelacaoPadrao(nCorrelacoes);

            Iterator i = listaElementos.iterator();
           

            //Elementos da itera��o para recuperar as informa��es do XML


            while (i.hasNext()) {

                Element element = (Element)i.next();

                CorrelacaoBean cb = new CorrelacaoBean();
                cb.setNome(element.getChildText("nome"));
                cb.setAtributos(element.getChildText("atributos"));
                cb.setAtrib(element.getChildText("atributos").split(","));

                BackCorrelacaoGUI.getCorrelacoesPadrao().add(cb);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
