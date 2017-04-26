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
public interface InterfacerLeituraSocioEconomico {

    public String[][] getPlanilhaSocioEconomico() throws PredictException;

    public String[] getCabecalhoPlanilhaSocioEconomico() throws PredictException;

    public String[] getEspecifico(String nome);

    public String getTopoSE();


}
