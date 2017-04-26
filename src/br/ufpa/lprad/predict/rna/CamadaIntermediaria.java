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
public class CamadaIntermediaria {

    private int id;
    private Vector<Neuronio> vNeuronios;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CamadaIntermediaria() {
    }

    public CamadaIntermediaria(Vector<Neuronio> vNeuronios) {
        this.vNeuronios = vNeuronios;
    }

    public Vector<Neuronio> getVNeuronios() {
        return vNeuronios;
    }

    public void setVNeuronios(Vector<Neuronio> vNeuronios) {
        this.vNeuronios = vNeuronios;
    }
}
