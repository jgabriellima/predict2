/*
 * DadosClimaticos.java
 *
 * Created on 28 de Junho de 2006, 02:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.ufpa.lprad.predict.bean;

/**
 *
 * @author Pedro Vitor
 */
public class DadosClimaticos {
    
    private String periodo;
    private String indpluvio;
    private String tempmax;
    private String tempmin;
    private String umidadear;
    private String concessionaria;
    
    /** Creates a new instance of DadosClimaticos */
    public DadosClimaticos() {
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getIndpluvio() {
        return indpluvio;
    }

    public void setIndpluvio(String indpluvio) {
        this.indpluvio = indpluvio;
    }

    public String getTempmax() {
        return tempmax;
    }

    public void setTempmax(String tempmax) {
        this.tempmax = tempmax;
    }

    public String getTempmin() {
        return tempmin;
    }

    public void setTempmin(String tempmin) {
        this.tempmin = tempmin;
    }

    public String getUmidadear() {
        return umidadear;
    }

    public void setUmidadear(String umidadear) {
        this.umidadear = umidadear;
    }

    public String getConcessionaria() {
        return concessionaria;
    }

    public void setConcessionaria(String concessionaria) {
        this.concessionaria = concessionaria;
    }
    
}
