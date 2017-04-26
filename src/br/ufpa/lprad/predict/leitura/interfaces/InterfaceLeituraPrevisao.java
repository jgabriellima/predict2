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
public interface InterfaceLeituraPrevisao {

    public String[] getTiposDeConsumo() throws PredictException;

    public String[] getRestricao(String tipoDeConsumo) throws PredictException;

    public String[] getNomeDoConsumo(String tipoDeConsumo) throws PredictException;

    public String[] getAnos(String tipoDeConsumo) throws PredictException;

    public double[][] getDadosParaPrevisao(String anoInicial, String anoFinal, String mesInicial , String mesFinal, String tipoConsumo, String nomeConsumo) throws PredictException;

    public double[][] getDadosAnosSelecionados(String[] anosSelecionados,String tipoConsumo,String nomeConsumo)throws PredictException;
    
    public String[][] getLerTodoHistorico(String tipoConsumo) throws PredictException;

    public String[] getTotalAnosHistorico(String tipoConsumo) throws PredictException;

}
