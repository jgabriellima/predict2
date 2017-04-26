/*
 * DadosSocioEconomicos.java
 *
 * Created on 28 de Junho de 2006, 03:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.ufpa.lprad.predict.bean;

/**
 *
 * @author Pedro Vitor
 */
public class DadosSocioEconomicos {
    
    private String periodo;
    private String totalReceita;
    private String industriaTrans;
    private String agropecuaria;
    private String realDolar;
    private String concessionaria;
    
    /** Creates a new instance of DadosSocioEconomicos */
    public DadosSocioEconomicos() {
    }

    public String getAgropecuaria() {
        return agropecuaria;
    }

    public void setAgropecuaria(String agropecuaria) {
        this.agropecuaria = agropecuaria;
    }

    public String getConcessionaria() {
        return concessionaria;
    }

    public void setConcessionaria(String concessionaria) {
        this.concessionaria = concessionaria;
    }

    public String getIndustriaTrans() {
        return industriaTrans;
    }

    public void setIndustriaTrans(String industriaTrans) {
        this.industriaTrans = industriaTrans;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getRealDolar() {
        return realDolar;
    }

    public void setRealDolar(String realDolar) {
        this.realDolar = realDolar;
    }

    public String getTotalReceita() {
        return totalReceita;
    }

    public void setTotalReceita(String totalReceita) {
        this.totalReceita = totalReceita;
    }
    
}