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
public class Neuronio {

    private String nome;
    private double net;
    private double fTranferencia;
    private Vector<Double> pesos;
    private Vector<Double> nEntradas;
    private double valorSaida;              //usado caso este seja a saída
    private double backTransferencia;
    private double sigma;

    public double getSigma() {
        return sigma;
    }

    public void setSigma(double sigma) {
        this.sigma = sigma;
    }

    public double getBackTransferencia() {
        return backTransferencia;
    }

    public void setBackTransferencia(double backTransferencia) {
        this.backTransferencia = backTransferencia;
    }

    public Vector<Double> getnEntradas() {
        return nEntradas;
    }

    public void setnEntradas(Vector<Double> nEntradas) {
        this.nEntradas = nEntradas;
    }

    public double getValorSaida() {
        return valorSaida;
    }

    public void setValorSaida(double valorSaida) {
        this.valorSaida = valorSaida;
    }

    public Neuronio(String nome) {
        this.nome = nome;
    }

    public Neuronio() {
    }

    public Neuronio(String nome, double net, double fSoma, double fTranferencia, Vector<Double> pesos, Vector<Double> nEntradas) {
        this.nome = nome;
        this.net = net;
        this.fTranferencia = fTranferencia;
        this.pesos = pesos;
        this.nEntradas = nEntradas;
    }

    public double getFSoma() {
//        System.out.println("N: " + getNome() + " ent: " + nEntradas.size());
        for (int i = 0; i < pesos.size(); i++) {
            net += pesos.get(i) * nEntradas.get(i);
        }
        return net;
    }

    public void setFSoma(double net) {
        this.net = net;
    }

    public double getFTranferencia() {
        //calcula a sigmóide
        fTranferencia = (1 / (1 + Math.exp(-1 * getFSoma())));
        return fTranferencia;
    }

    public void setFTranferencia(double fTranferencia) {

        this.fTranferencia = fTranferencia;
    }

    public Vector<Double> getNEntradas() {
        return nEntradas;
    }

    public void setNEntradas(Vector<Double> nEntradas) {
        this.nEntradas = nEntradas;
    }

    public double getNet() {
        return getFSoma();
    }

    public void setNet(double net) {
        this.net = net;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Vector<Double> getPesos() {
        return pesos;
    }

    public void setPesos(Vector<Double> pesos) {
        this.pesos = pesos;
    }
}
