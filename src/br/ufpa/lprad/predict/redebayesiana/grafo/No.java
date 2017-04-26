package br.ufpa.lprad.predict.redebayesiana.grafo;
import java.util.*;
import java.awt.geom.*;
import java.io.Serializable;

public class No extends RoundRectangle2D.Double implements Serializable{
    
    // CONSTANTES
    final double ALT_RET = 50;             // Largura dos Nos
    final double LARG_RET = 100;           // Altura dos Nos
    final double ARC_W = 50;               // Largura do arco dos Nos
    final double ARC_H = 50;               // Altura do arco dos Nos
    
    // DADOS DO NO
    String nome;                // nome do no
    int quantidade;             //  quantidade de posicoes do no
    Vector valor;               // probabilidades instanciados do no
    Object probabilidades;      // matriz do no; recuperar com (double[][])

    /** Creates a new instance of Atrcelpa */
    public No() {
        super();
        width = LARG_RET;
        height = ALT_RET;
        arcwidth = ARC_W;
        archeight = ARC_H;
     
        setRandom();
    }
    
    public void setTamanho(){
        
    }

    public No(String t) {
        super();
        
        width = ((t.length()+10)*4) + LARG_RET;
        
        height = ALT_RET;
        arcwidth = ARC_W+ (2 * t.length()*100);
        archeight = ARC_H;
        nome = t;
        setRandom();
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public int getQuantidade() {
        return quantidade;
    }
    
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Vector getValor() {
        return valor;
    }
    
    public void setValor(Vector valor) {
        this.valor = valor;
    }
    
    public void setPosicao(int xn, int yn) {
        this.x = (double) x;
        this.y = (double) y;
    }
    
    public Object getprobabilidades() {
        return probabilidades;
    }
    
    public void setprobabilidades(Object probabilidades) {
        this.probabilidades = probabilidades;
    }
    
    public String getLabel() {
        return nome;    
    }
    
    public int pegaX (){
        return (int) x;
    }
    
    public int pegaY (){
        return (int) y;
    }
    
    public void setLocation (double xn, double yn){
        x = xn;
        y = yn;
    }
    
    public void setRandom() {
        int x = 100+ (int) (Math.random() *300);
        int y = 100+(int) (Math.random() * 300);
         this.setRoundRect(x,y,width,height,arcwidth,archeight);
        setPosicao(x, y);
    }
}
