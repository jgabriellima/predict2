/*
 * PrevisaoBean.java
 *
 * Created on 30 de Novembro de 2007, 16:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.ufpa.lprad.predict.bean;

/**
 *
 * @author Administrador
 */
public class PrevisaoBean {
    
    private static String[] anosSelecionados;
    private static String nomePlanilha;    
    private static String tipoConsumo;
    private static String nomeConsumo;
    private static String tipoCons;    //nome da previsao realizada
    private static String nomeInterligado;   
    private static String labelConsumo;
    private static String labelEixoY;
    private static String unidadeConsumo;        
    private static String mesIni;
    private static String mesFim;        
    private static int anoIni;
    private static int anoFim;   
    
    /** Creates a new instance of PrevisaoBean */
    public PrevisaoBean() {
    }
    
    public static String[] getAnosSelecionados() {
        return anosSelecionados;
    }

    public void setAnosSelecionados(String anosSelecionados) {
        this.anosSelecionados = anosSelecionados.split("#"); 
    }    
    
   public static String getTipoCons() {
        return tipoCons;
    }

    public void setTipoCons(String tipoCons) {
        this.tipoCons = tipoCons;
    }
    
    public static String getLabelEixoY() {
        return labelEixoY;
    }

    public void setLabelEixoY (String labelEixoY) {
        this.labelEixoY = labelEixoY; 
    }    
    
    public static String getNomePlanilha() {
        return nomePlanilha;
    }

    public void setNomePlanilha(String nomePlanilha) {
        this.nomePlanilha = nomePlanilha;
    }
    
    public static String getTipoConsumo() {
        return tipoConsumo;
    }

    public void setTipoConsumo(String tipoConsumo) {
        this.tipoConsumo = tipoConsumo;
    }
    
    public static String getUnidadeConsumo() {
        return unidadeConsumo;
    }

    public void setUnidadeConsumo(String unidadeConsumo) {
        this.unidadeConsumo = unidadeConsumo;
    }
    public static String getNomeConsumo() {
        return nomeConsumo;
    }

    public void setNomeConsumo(String nomeConsumo) {
        this.nomeConsumo = nomeConsumo;
    }
    
    public static String getNomeInterligado() {
        return nomeInterligado;
    }

    public void setNomeInterligado(String nomeInterligado) {
        this.nomeInterligado = nomeInterligado;
    }    
   
    
    public static String getLabelConsumo() {
        return labelConsumo;
    }

    public void setLabelConsumo(String labelConsumo) {
        this.labelConsumo = labelConsumo;
    }  
    
    public static String getMesIni() {
        return mesIni;
    }

    public void setMesIni(String mesIni) {
        this.mesIni = mesIni;
    }  
    
    public static String getMesFim() {
        return mesFim;
    }

    public void setMesFim(String mesFim) {
        this.mesFim = mesFim;
    }  
    
    public static int getAnoIni() {
        return anoIni;
    }

    public void setAnoIni(int anoIni) {
        this.anoIni = anoIni;
    }  
    
    public static int getAnoFim() {
        return anoFim;
    }

    public void setAnoFim(int anoFim) {
        this.anoFim = anoFim;
    }  
}
