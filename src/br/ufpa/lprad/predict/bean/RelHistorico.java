/*
 * RelHistorico.java
 *
 * Created on 12 de Novembro de 2007, 18:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.ufpa.lprad.predict.bean;

import java.util.ArrayList;

/**
 *
 * @author Livikinha
 */
public class RelHistorico {
    
    private String unidadeConsumo;
    
    private String tipoConsumo;
    
    private String periodo;
    
    private String historico;
    
    private String previsto;
    
    private String erro; 
    
    private String tipoCons;    //nome da previsao realizada
    
    
    /** Creates a new instance of RelHistorico */
    public RelHistorico() {        
    }   
    
    public String getTipoConsumo() {
        return tipoConsumo;
    }
    
    public void setTipoConsumo(String tipoCons){
        this.tipoConsumo = tipoCons;
    }
    
     public String getTipoCons() {
        return tipoCons;
    }

    public void setTipoCons(String tipoCons) {
        this.tipoCons = tipoCons;
    }
    
    public String getUnidadeConsumo() {
        return unidadeConsumo;
    }    
    
    public void setUnidadeConsumo(String unidCons){
        this.unidadeConsumo = unidCons;
    }
    
    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public String getPrevisto() {
        return previsto;
    }

    public void setPrevisto(String previsto) {
        this.previsto = previsto;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }        
}