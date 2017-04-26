/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpa.lprad.predict.correlacao.discretizacao;

/**
 *
 * @author gabriel
 */
public class AtributoDiscreto {

    private String nome;
    private Faixa[] faixas;
    private boolean personalizado;
    private int nFaixas;

    public AtributoDiscreto() {
    }

    public Faixa[] getFaixas() {
        return faixas;
    }

    public void setFaixas(Faixa[] faixas) {
        this.faixas = faixas;
    }

    public int getNFaixas() {
        return nFaixas;
    }

    public void setNFaixas(int nFaixas) {
        this.nFaixas = nFaixas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isPersonalizado() {
        return personalizado;
    }

    public void setPersonalizado(boolean personalizado) {
        this.personalizado = personalizado;
    }

    
    

}
