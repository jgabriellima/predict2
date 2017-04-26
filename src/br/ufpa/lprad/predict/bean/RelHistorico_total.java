/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpa.lprad.predict.bean;

/**
 *
 * @author Gabriel
 */
public class RelHistorico_total {

    private String unidadeConsumo;
    
    private String tipoConsumo;
    
    private String periodo;
    
    private String historico;
    
    private String previsto;
    
    private String erro; 
    
    private String tipoCons;  
    
    private String historico_previsto;
    
    private String variacao;

     public RelHistorico_total() {        
    }  
    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public String getHistorico_previsto() {
        return historico_previsto;
    }

    public void setHistorico_previsto(String historico_previsto) {
        this.historico_previsto = historico_previsto;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getPrevisto() {
        return previsto;
    }

    public void setPrevisto(String previsto) {
        this.previsto = previsto;
    }

    public String getTipoCons() {
        return tipoCons;
    }

    public void setTipoCons(String tipoCons) {
        this.tipoCons = tipoCons;
    }

    public String getTipoConsumo() {
        return tipoConsumo;
    }

    public void setTipoConsumo(String tipoConsumo) {
        this.tipoConsumo = tipoConsumo;
    }

    public String getUnidadeConsumo() {
        return unidadeConsumo;
    }

    public void setUnidadeConsumo(String unidadeConsumo) {
        this.unidadeConsumo = unidadeConsumo;
    }

    public String getVariacao() {
        return variacao;
    }

    public void setVariacao(String variacao) {
        this.variacao = variacao;
    }
    
    
}
