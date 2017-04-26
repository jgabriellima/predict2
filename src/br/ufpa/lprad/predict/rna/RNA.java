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
public class RNA {

    public CamadaEntrada vEntrada;
    public Vector<CamadaIntermediaria> vCIntermediaria;
    public Neuronio saida;
    public double erro;

    public RNA() {
    }

    public RNA(CamadaEntrada vEntrada, Vector<CamadaIntermediaria> vCIntermediaria, Neuronio saida) {
        this.vEntrada = vEntrada;
        this.vCIntermediaria = vCIntermediaria;
        this.saida = saida;
    }

    public Neuronio getSaida() {
        return saida;
    }

    public void setSaida(Neuronio saida) {
        this.saida = saida;
    }

    public double getErro() {
        return erro;
    }

    public void setErro(double erro) {
        this.erro = erro;
    }

    public CamadaEntrada getVEntrada() {
        return vEntrada;
    }

    public void setVEntrada(CamadaEntrada vEntrada) {
        this.vEntrada = vEntrada;
    }

    public Vector<CamadaIntermediaria> getVCIntermediaria() {
        return vCIntermediaria;
    }

    public void setVCIntermediaria(Vector<CamadaIntermediaria> vCIntermediaria) {
        this.vCIntermediaria = vCIntermediaria;
    }
//    public Vector
}
