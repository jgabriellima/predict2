/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpa.lprad.predict.util;

import br.ufpa.lprad.predict.leitura.Relatorio;

/**
 *
 * @author J. Gabriel Lima
 */
public class Imprimir {

    public Imprimir(String matriz[][], String cabecalho[], String topo, String tipoConsumo){
        new Relatorio(matriz, cabecalho, topo, tipoConsumo);
    }


}
