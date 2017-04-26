/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.previsao.bean;

/**
 *
 * @author J. Gabriel Lima
 */
public class PrevisaoBean {

    private static String[] anosSelecionados;
    private static String nomePlanilha;
    private static String tipoConsumo;
    private static String nomeConsumo;
    private static String tipoCons;    //nome da previsao realizada
  //  private static String nomeInterligado;
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

//        for(String g:anosSelecionados)
//            System.out.println("......: "+g);

        return PrevisaoBean.anosSelecionados;
    }

    public static void setAnosSelecionados(String anosSelecionados) {
        PrevisaoBean.anosSelecionados = anosSelecionados.split("#");
       
        
    }

   public static String getTipoCons() {
        return tipoCons;
    }

    public static void setTipoCons(String tipoCons) {
        PrevisaoBean.tipoCons = tipoCons;
    }

    public static String getLabelEixoY() {
        return labelEixoY;
    }

    public static void setLabelEixoY (String labelEixoY) {
        PrevisaoBean.labelEixoY = labelEixoY;
    }

    public static String getNomePlanilha() {
        return nomePlanilha;
    }

    public static void setNomePlanilha(String nomePlanilha) {
        PrevisaoBean.nomePlanilha = nomePlanilha;
    }

    public static String getTipoConsumo() {
        return tipoConsumo;
    }

    public static void setTipoConsumo(String tipoConsumo) {
        PrevisaoBean.tipoConsumo = tipoConsumo;
    }

    public static String getUnidadeConsumo() {
        return unidadeConsumo;
    }

    public static void setUnidadeConsumo(String unidadeConsumo) {
        PrevisaoBean.unidadeConsumo = unidadeConsumo;
    }
    public static String getNomeConsumo() {
        return nomeConsumo;
    }

    public static void setNomeConsumo(String nomeConsumo) {
        PrevisaoBean.nomeConsumo = nomeConsumo;
    }

//    public static String getNomeInterligado() {
//        return nomeInterligado;
//    }
//
//    public static void setNomeInterligado(String nomeInterligado) {
//        PrevisaoBean.nomeInterligado = nomeInterligado;
//    }


    public static String getLabelConsumo() {
        return labelConsumo;
    }

    public static void setLabelConsumo(String labelConsumo) {
        PrevisaoBean.labelConsumo = labelConsumo;
    }

    public static String getMesIni() {
        return mesIni;
    }

    public static void setMesIni(String mesIni) {
        PrevisaoBean.mesIni = mesIni;
    }

    public static String getMesFim() {
        return mesFim;
    }

    public static void setMesFim(String mesFim) {
        PrevisaoBean.mesFim = mesFim;
    }

    public static int getAnoIni() {
        return anoIni;
    }

    public static void setAnoIni(int anoIni) {
        PrevisaoBean.anoIni = anoIni;
    }

    public static int getAnoFim() {
        return anoFim;
    }

    public static void setAnoFim(int anoFim) {
        PrevisaoBean.anoFim = anoFim;
    }
}
