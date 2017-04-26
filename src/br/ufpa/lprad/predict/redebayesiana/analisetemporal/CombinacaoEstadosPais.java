/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpa.lprad.predict.redebayesiana.analisetemporal;

/**
 *
 * @author Pedro Vitor
 */
public class CombinacaoEstadosPais {
    private String estados[];
    private double probabilidadeDaCombinacao;
    
    public CombinacaoEstadosPais(){
        
    }
    
    public CombinacaoEstadosPais(String estados[]){
        this.estados = estados.clone();
    }

    public String[] getEstados() {
        return estados;
    }

    public void setEstados(String[] estados) {
        this.estados = estados;
    }

    public double getProbabilidadeDaCombinacao() {
        return probabilidadeDaCombinacao;
    }

    public void setProbabilidadeDaCombinacao(double probabilidadeDaCombinacao) {
        this.probabilidadeDaCombinacao = probabilidadeDaCombinacao;
    }

}
