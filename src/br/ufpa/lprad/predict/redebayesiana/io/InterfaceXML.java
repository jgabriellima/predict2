/*
 * Classe InterfaceXML
 *
 * Classe que faz a interface entre o arquivo XML e a Rede Bayesiana
 *
 *
 * @autor Gabriel Lima
 */

package br.ufpa.lprad.predict.redebayesiana.io;

import br.ufpa.lprad.predict.redebayesiana.aprendizado.RedeBayesiana;
import br.ufpa.lprad.predict.redebayesiana.aprendizado.Vertice;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;




public class InterfaceXML {
    private Document document;
    private File file;
    private RedeBayesiana rb;
    //Construtor Default
    public InterfaceXML() {
    }
    public InterfaceXML(RedeBayesiana rb, File file) 
    {
        this.file = file;
        this.rb = rb;
        
    }
    //Método que lê os dados do aqrquivo XML e retorna um Vetor com os Vertices da elementoRoot
    public void XMLparaRedebayesiana(File nomeArquivoXML,RedeBayesiana rede) {
        //Aqui você informa o nome do arquivo XML.
        
        File f = nomeArquivoXML;
        this.file = f;
        //Criamos uma classe SAXBuilder que vai processar o XML
        SAXBuilder sb = new SAXBuilder();
        
        try {
            //Este documento agora possui toda a estrutura do arquivo.
            Document d = sb.build(f);
            this.document = d;
           // System.out.println("..."+f.getAbsolutePath());
            
            //Recuperamos o elemento root
            Element elementoRoot = d.getRootElement();

            
            //Recuperamos os elementos filhos (children)
            List listaElementos = elementoRoot.getChildren();
            //Recuperando o número de Vertices da Rede Bayesiana
            int nVertices = contaVertices(listaElementos);
            rede.setNVertices(nVertices);
            Iterator i = listaElementos.iterator();
            //Aqui declaramos um vector com o número de vertices da rede
//            System.out.println("nVertices : "+nVertices);
            Vector vertices = new Vector(nVertices);
            //Aqui guardamos quantos vertices já foram adicionados à rede
            
            //Elementos da iteração para recuperar as informações do XML
            Element element = (Element) i.next();
            Element elementoInicial = element;
            
            
         //   System.out.println("... 1");
            
            String[] stringFilhos;
            String[] stringPais;
            double[] tabela_tpc;
            Vector filhos = new Vector(vertices.size());
            Vector pais = new Vector(vertices.size());
            Vector nomeVerticesCriados = new Vector(vertices.size());
            
              //  System.out.println("... 2");
             //   System.out.println("this.contaVertices(listaElementos): "+this.contaVertices(listaElementos));
    int nVerticesCriados =0;        
    //Iteramos com os elementos filhos, e filhos do dos filhos
                
            while (nVerticesCriados < (this.contaVertices(listaElementos))) {
                    
            //        System.out.println("nVerticesCriados: "+nVerticesCriados);
                    
                Vertice verticeAtual = new Vertice();
                verticeAtual= this.preencheVerticeAtual(element);
                
             //   System.out.println("Vertice: "+verticeAtual.getNome());
                
                //Recuperando os filhos do Vertice, em String, lido do XML
                if (verticeAtual.getNFilhos() ==0) {
                    stringFilhos =null;
                } else {
                    stringFilhos = element.getChildText("filhos").split(",");
                }
//                 try{
//                for(int a=0; a<stringFilhos.length; a++){
//                    System.out.println("filhos: "+stringFilhos[a]);
//                }}catch(Exception ex){}
//                
                //Recuperando os pais do Vertice, em String, lido do XML
                if (verticeAtual.getNPais() ==0) {
                    stringPais =null;
                } else {
                    stringPais = element.getChildText("pais").split(",");                    
                }
//                try{
//                for(int a=0; a<stringPais.length; a++){
//                    System.out.println("pais: "+stringPais[a]);
//                }}catch(Exception ex){}
//                
                //if ( ( ( this.VerificaCriado(verticeAtual.getNome(),nomeVerticesCriados)) && this.VerificaPais(verticeAtual,vertices, stringPais) )) {
                    Vertice vertice = new Vertice(verticeAtual.getNome(),verticeAtual.getNPais(),verticeAtual.getNFilhos(),verticeAtual.getNEstados(),verticeAtual.getEstados(),null,null);
                    vertices.insertElementAt(vertice, nVerticesCriados);
                    nomeVerticesCriados.add(verticeAtual.getNome());
                    filhos.insertElementAt(stringFilhos, nVerticesCriados);
                    pais.insertElementAt(stringPais,nVerticesCriados);
                    nVerticesCriados ++;
               // }else {
                    
                    if (i.hasNext()) //{
                        element = (Element)i.next();
                  //  } else {
                        // element = (Element)i.next();
//                        Element elementoRootInicial = d.getRootElement();
//                        List listaElementosInicial = elementoRoot.getChildren();
//                        Iterator iInicial = listaElementos.iterator();
//                        i = iInicial;
               //     }
//                }
             
            }
             //   System.out.println("... 3");
            //Percorre o Vector de vertices criados atribuindo os filhos de cada Vertice
            for (int j = 0; j < vertices.size(); j++) {
                String[] nomeFilhos ;
                String[] nomePais;
                nomeFilhos = (String[])filhos.get(j);
                nomePais = (String[])pais.get(j);
                Vertice v = (Vertice)vertices.get(j);
                int nFilhos = v.getNFilhos();
                int nPais = v.getNPais();
                
                v.setFilhos(this.StringToVector(nomeFilhos,nFilhos,vertices));
                v.setPais(this.StringToVector(nomePais,nPais,vertices));
            }
            rede.setVertices(vertices);
      //      System.out.println("finalizou leitura");
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
    //Método que recebe uma Tabela de String e retorna uma tabela
    //com os valores convertidos para o Tipo Double
    private double[] arrayStringToDouble(String[] stringTPC, Vertice verticeXi) {
        int tamanho = stringTPC.length;
        
        double[] tpc = new double[tamanho];
        if(tamanho>1)
        {   for(int i=0; i <stringTPC.length; i++) 
            {
                tpc[i] = Double.parseDouble(stringTPC[i]);
            }
        }
        else
            tpc = null;
        return tpc;
    }
    //Método que lê os atributos do Arquivo XML
    private String getXmlAtributos(String atributo, Element e) {
        return (e.getChildText(atributo));
    }
    //Método que conta quantos Vertices tem no Arquivo XML
    private int contaVertices(List listaElementos) {
        return (listaElementos.size());
    }
    //Método que preenche os atributos do vertice Atual
    private Vertice preencheVerticeAtual(Element element) {
        Vertice vertice = new Vertice();
        //Recuperando o nome do Vertice
        String nome = this.getXmlAtributos("nome", element);
        vertice.setNome(nome.trim());
        
        //Recuperando o número de Pais do vertice
        int nPais = Integer.parseInt(this.getXmlAtributos("nPais", element));
        vertice.setNPais(nPais);
        
        //Recuperando o número de Filhos do Vertice
        int nFilhos = Integer.valueOf(this.getXmlAtributos("nFilhos", element)).intValue();
        vertice.setNFilhos(nFilhos);
        
        //Recuperando os estados que o vertice pode assumir
        String[] estados = this.getXmlAtributos("estados", element).split(",");
        vertice.setEstados(estados);
        
        //Recuperando o número de Estados que o Vertice pode assumir
        int nEstados = estados.length;
        vertice.setNEstados(nEstados);
        
        try{
        //Recuperando a Tabela de Probabildade Condicional
        
        String[] stringTPC = this.getXmlAtributos("tpc", element).split(",");
        double[] tpc = new double[stringTPC.length];
        tpc = arrayStringToDouble(stringTPC,vertice);
        //vertice.setTpc(tpc);
        }catch(Exception ex){}
        return vertice;
    }
    //Método que recebr um array de String com os nomes e retorna
    //Um Vector com os vertices que tem aqueles nome
    public Vector StringToVector(String[] nomes, int nParentes,Vector vertices) {
        Vector vetorVertices = new Vector();
        
        Vertice v = new Vertice();
        if(nParentes==0) {
            vetorVertices = null;
        } else {
            for(int i=0; i < vertices.size(); i++) {
                v = (Vertice)vertices.get(i);
                for(int j=0; j < nParentes; j++) {
                    if(v.getNome().equals(nomes[j])) {
                        vetorVertices.add(v);
                    }
                }
            }
        }
        return vetorVertices;
    }
    //Método que verifica se um determinado vertice já foi criado
    private boolean VerificaCriado(String nome, Vector nomeVerticesCriados) {
        boolean resposta = true;
        if (nomeVerticesCriados.contains(nome)) {
            resposta = false;
        }
        return resposta;
    }
    //Método que verifica se os pais do vertice atual já foram Criados
    private boolean VerificaPais(Vertice verticeAtual,Vector verticesCriados, String[] stringPais) {
        boolean resposta = true;
        if(verticeAtual.getNPais() !=0) {
            String[] nomeVerticesCriados = new String[verticesCriados.size()];
            for (int j = 0; j < verticesCriados.size(); j++) {
                Vertice v = (Vertice)verticesCriados.get(j);
                nomeVerticesCriados[j] = v.getNome();
            }
            
            for (int i = 0; i < stringPais.length; i++) {
                if(!this.VerificaString(nomeVerticesCriados,stringPais[i])) {
                    resposta = false;
                }
            }
        }
        return resposta;
    }
    //Método que verifica se uma String está contida num array de Strings
    private boolean VerificaString(String[] arrayString, String string) {
        boolean contem =false;
        
        for (int i = 0; i < arrayString.length; i++) {
            if(arrayString[i].equals(string)) {
                contem = true;
            }
        }
        
        return contem;
    }    
     
}