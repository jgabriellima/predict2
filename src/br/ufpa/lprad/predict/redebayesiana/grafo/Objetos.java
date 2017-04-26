package br.ufpa.lprad.predict.redebayesiana.grafo;

import br.ufpa.lprad.predict.redebayesiana.aprendizado.Vertice;
import br.ufpa.lprad.predict.redebayesiana.inferencias.MetaDados;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.Vector;


public class Objetos implements Serializable{
    
    // DEFINICAO DE CONSTANTES
    final int ALT_RET = 50;                         // Largura dos Nos
    final int LARG_RET = 100;                       // Altura dos Nos
    Color azul = new Color(0, 102, 255);            //cor zul (definitiva)
    Color vermelhoEscuro = new Color(225,80,80);    //cor vermelha (definitiva)
    
    // Armazena os objetos do programa
    private Vector objetos = new Vector();          // Nos
    private Vector relacionamentos = new Vector();  // Relacionamentos
    private Tela t;                                 // Referencia tela   
    private MetaDados meta;
    public Vector v;
    
    public Objetos(Tela tl) {
        t = tl;
    }
    
    public Objetos(MetaDados m) {
        meta = m;
        descarregaPaisFilhos();
    }
    public Objetos(Vector v)
    {
        this.v=v;
        descarregaPaisFilhos();
    }
    
    public Objetos() {
    }
    
    public No getPos(int i){
        return (No) objetos.get(i);
    }
    
    public void adiciona(No r) {
        objetos.add(r);
    }
    
    public void adiciona(String nome, int qntd, Vector valores, int x, int y) {
        No lala = new No(nome);
        lala.setQuantidade(qntd);
        lala.setValor(valores);
        lala.setPosicao(x, y);
        objetos.add(lala);
    }
    
    public void setProbabilidadeDoNo(String nome, Object ma) {
        No aux;
        for (int i = 0; i < objetos.size(); i++) {
            aux = (No) objetos.get(i);
            if (nome.equals(aux.getLabel()))
                aux.setprobabilidades(ma);
        }
    }
    
    public boolean relaciona(String pai, String filho) {
        No p = null, f = null, aux;
        
        for (int i = 0; i < objetos.size(); i++) {
            aux = (No) objetos.get(i);
            if (pai.equals(aux.getLabel())) {
                p = aux;
            } else if (filho.equals(aux.getLabel())) {
                f = aux;
            }
        }
        
        if (p != null && f != null) {
            relacionamentos.addElement(new Arco(p,f));
            return true;
        } else
            return false;
    }
    
    public void desenhaObjetos(Graphics2D g){
        // Desenha os relacionamentos
        g.setPaint(Color.BLACK);
        g.setStroke(new BasicStroke(1.0f));
        Arco r;
        Seta arrow = new Seta(g);
        
        for (int i = 0; i < relacionamentos.size(); i++) {
            
            r = (Arco) relacionamentos.get(i);
            No pai = r.getPai();
            No filho = r.getFilho();
            
            if (Math.abs(pai.pegaY()-filho.pegaY()) > Math.abs(pai.pegaX()-filho.pegaX()))
                
                // Linha na vertical
                if (pai.pegaY() < filho.pegaY())
                    arrow.desenhaSeta(pai.pegaX()+(LARG_RET/2)/*(int)pai.getBounds().getX()*/, pai.pegaY()+ALT_RET, filho.pegaX()+(LARG_RET/2), filho.pegaY());
               else
                    arrow.desenhaSeta(pai.pegaX()+(LARG_RET/2)/*(int)pai.getBounds().getMinX()*/, pai.pegaY(), filho.pegaX()+(LARG_RET/2), filho.pegaY()+ALT_RET);
                
                // Linha na horizontal
                else if (pai.pegaX() < filho.pegaX())
                    arrow.desenhaSeta((int)pai.getBounds().getMaxX()/*pai.pegaX()+LARG_RET*/, pai.pegaY()+(ALT_RET/2), filho.pegaX(), filho.pegaY()+(ALT_RET/2));
                else
                    arrow.desenhaSeta((int)pai.getBounds().getMinX(), pai.pegaY()+(ALT_RET/2), (int)filho.getBounds().getMaxX()/*filho.pegaX()+LARG_RET*/, filho.pegaY()+(ALT_RET/2));
        }
        // Desenha os Nos
        for (int i = 0; i < objetos.size(); i++) {
            No rr = (No) objetos.get(i);
            
            // Desenha a borda dos objetos
             Color azulEscuro = new Color(0, 40, 102);

            // Preenche o miolo dos objetos
            //g.setPaint(azul);             
           if(!Tela.selecionados.contains(rr))
            g.setPaint(Color.WHITE);
             else
             g.setPaint(new Color(130,126,188));
            g.fill(rr);
            
            //desenha a borda dos retangulos
            g.setStroke(new BasicStroke(2.0f));
            g.setPaint(Color.BLACK);
            //g.setPaint(azulEscuro);
            //g.setPaint(vermelhoEscuro);
            g.draw(rr);
            
            // Escreve o texto de cada No
            g.setPaint(Color.BLACK);
            g.drawString(rr.nome, (float) rr.getX()+17, (float) rr.getY()+27);
        }
        
        
        
    }
    
    public No objetoClicado(MouseEvent e) {
        
        
        // Procura o No que esta no clique do mouse
        No r;
        for (int i=0; i < objetos.size(); i++) {
            r = (No) objetos.get(i);
            if(r.contains(e.getX(), e.getY()))
                return (No) objetos.get(i);
        }
       
        
        return null;
    }
    
   public String meDizQuemTaAi() {
       No r;
       String str = "";
       for (int i=0; i < objetos.size(); i++) {
           r = (No) objetos.get(i);
           str = str + r.getLabel() + " " + i + " ";
       }
       return str;
   }
   
   public String meDizQuemTaComQuem() {
       Arco r;
       String str = "";
       for (int i=0; i < relacionamentos.size(); i++) {
           r = (Arco) relacionamentos.get(i);
           str = str + r.getLabel() + " " + i + " ";
       }
       return str;
   }
   
//   public void descarregaPaisFilhos() {
//       // Instancia os Nos
//       for (int i=0; i<meta.getQntdAtrib(); i++) {
//           No novo = new No(meta.getAtributo(i));
//           adiciona(novo);
//       }
//
//       // Instancia os Arcos
//       // Para cada atributo...
//       for (int i=0; i<meta.getQntdAtrib(); i++) {
//           // ... verifique se ele tem pais ...
//           if (meta.getQntdPais(i) > 0)
//               // ... encontrando cada um se tiver ... 
//               for(int j=0; j<meta.getQntdPais(i); j++) {
//                    relaciona(meta.getAtributo(meta.getPais(i,j)), meta.getAtributo(i));
//               }
//       }
//   }
    public void descarregaPaisFilhos() {
       // Instancia os Nos
       for (int i=0; i<v.size(); i++) {
           No novo = new No(((Vertice)v.elementAt(i)).getNome());
           adiciona(novo);
       }

       for(int i=0; i<v.size();i++)
       {
           try{
           Vertice vertice= (Vertice)v.elementAt(i);
           Vector vetor = vertice.getPais();
           for(int j=0; j<vetor.size();j++)
           {
               relaciona(((Vertice)vetor.elementAt(j)).getNome(),vertice.getNome());
           }
           }catch(Exception ex){}
       }
   }
} 
