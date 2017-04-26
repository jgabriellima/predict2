package br.ufpa.lprad.predict.redebayesiana.grafo;

import java.io.Serializable;

public class Arco implements Serializable {

    No   pai,            // No que influencia
         filho;          // No que e influenciada

    /** Metodo construtor de relacionamentos */
    public Arco(No pai, No filho) {
        this.pai = pai;
        this.filho = filho;
    }
    
    public No getPai() {
        return pai;
    }
    
    public No getFilho() {
        return filho;
    }
    
    public String getLabel() {
        return pai.getLabel()+"-com-"+filho.getLabel();
    }
}
