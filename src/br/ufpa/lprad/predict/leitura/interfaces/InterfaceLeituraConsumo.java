/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.leitura.interfaces;

import br.ufpa.lprad.predict.exception.PredictException;

/**
 *
 * @author Arilene
 */
public interface InterfaceLeituraConsumo {

    public String[][] getPlanilhaConsumo(String tipoConsumo) throws PredictException;

    public String[][] getPlanilhaConsumo(int tipoConsumo) throws PredictException;

    public String[][] getPlanilhaConsumoCorrec(String tipoConsumo);

    public String[] getTipoConsumo() throws PredictException;

    public String[] getCabecalhoPlanilhaConsumo(String tipoConsumo) throws PredictException;

    public String getNomeDaPlanilha(String tipoConsumo);

    public String[] consumoEspecifico(String tipoConsumo, String nomeConsumo, String anoInicial) throws PredictException;

    public String[][] consumoEspecifico(String tipoConsumo, String nomeConsumo, String anoInicial, String anoFinal);
    public String[] PeriodoEspecifico(String tipoConsumo, String nomeConsumo, String anoInicial, String anoFinal);

    public String[] consumoEspecifico(String tipoConsumo, String nomeConsumo) throws PredictException;
}
