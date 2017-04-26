/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.redebayesiana.algoritmogenetico;

import java.util.Vector;

/**
 *
 * @author gabriel
 */
public class Individuo {

    public int tamanho;
    public Cromossomo cromossomo;
    public double aptidao;

    public Individuo() {
    }

    public Individuo(int tamanho, Cromossomo cromossomo, double aptidao) {
        this.tamanho = tamanho;
        this.cromossomo = cromossomo;
        this.aptidao = aptidao;
    }

    public Individuo(int tamanho, Cromossomo cromossomo) {
        this.tamanho = tamanho;
        this.cromossomo = cromossomo;
    }

    public Cromossomo getCromossomo() {
        return cromossomo;
    }

    public void setCromossomo(Cromossomo cromossomo) {
        this.cromossomo = cromossomo;
    }

    public double getAptidao() {
        return aptidao;
    }

    public void setAptidao(double aptidao) {
        this.aptidao = aptidao;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }
}
