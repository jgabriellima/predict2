/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.correlacao.discretizacao;

/**
 *
 * @author gabriel
 */
public class Faixa {

    private String atributo;
    private double limiteInferior;
    private double limiteSuperior;

    public Faixa() {
    }

    public Faixa(String atributo, double limiteInferior, double limiteSuperior) {
        this.atributo = atributo;
        this.limiteInferior = limiteInferior;
        this.limiteSuperior = limiteSuperior;
    }

    public Faixa(double limiteInferior, double limiteSuperior) {
        this.limiteInferior = limiteInferior;
        this.limiteSuperior = limiteSuperior;
    }

    public Faixa(String atributo) {
        this.atributo = atributo;
    }

    public String getAtributo() {
        return atributo;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    public double getLimiteInferior() {
        return limiteInferior;
    }

    public void setLimiteInferior(double limiteInferior) {
        this.limiteInferior = limiteInferior;
    }

    public double getLimiteSuperior() {
        return limiteSuperior;
    }

    public void setLimiteSuperior(double limiteSuperior) {
        this.limiteSuperior = limiteSuperior;
    }
}
