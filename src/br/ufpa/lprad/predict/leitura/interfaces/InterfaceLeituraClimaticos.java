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
public interface InterfaceLeituraClimaticos {

    public String[][] getPlanilhaClimaticos() throws PredictException;
    public String getTopoPlanilhaClimaticos();
    public String[] getCabecalhoPlanilhaClimaticos() throws PredictException;
    public String[] getEspecificoClimaticos(String nome);


}
