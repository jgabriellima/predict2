/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpa.lprad.predict.rna;

import java.util.Vector;

/**
 *
 * @author Gabriel
 */
public class CamadaSaida {

    private Vector<Double> vSaida;
    private Vector<Neuronio> vNeuronio;

    public CamadaSaida() {
    }

    public CamadaSaida(Vector<Double> vSaida, Vector<Neuronio> vNeuronio) {
        this.vSaida = vSaida;
        this.vNeuronio = vNeuronio;
    }

    public Vector<Neuronio> getVNeuronio() {
        return vNeuronio;
    }

    public void setVNeuronio(Vector<Neuronio> vNeuronio) {
        this.vNeuronio = vNeuronio;
    }

    public Vector<Double> getVSaida() {
        return vSaida;
    }

    public void setVSaida(Vector<Double> vSaida) {
        this.vSaida = vSaida;
    }

    



}
