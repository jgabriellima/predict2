/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpa.lprad.predict.redebayesiana.algoritmogenetico;

import java.util.Vector;

/**
 *
 * @author gabriel
 */
public class Cromossomo {

    public Vector<Integer> genes;
    public int tam;

    public int getTam() {
        if(tam<genes.size())
        {
            tam = genes.size();
        }
        return tam;
    }

    public void setTam(int tam) {
        this.tam = tam;
    }

    public Cromossomo(int tam) {
          genes = new Vector<Integer>();
        this.tam = tam;
    }

    
     
    public Cromossomo() {
        genes = new Vector<Integer>();
    }

    public Vector<Integer> getGenes() {
        return genes;
    }

    public void setGenes(Vector<Integer> genes) {
        this.genes = genes;
    }

    public void add(int i)
    {

        if(genes.size()<=tam)
        {
             genes.add(i);
        }
       
    }
}
