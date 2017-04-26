/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpa.lprad.predict.util;

import java.util.Date;

/**
 *
 * @author Gabriel
 */
public class ListaArquivos {

    String nome;
    String data_modificacao;
    long tam;

    public ListaArquivos(String nome, String data_modificacao, long tam) {
        this.nome = nome;
        this.data_modificacao = data_modificacao;
        this.tam = tam;
    }

    public ListaArquivos(String nome, String data_modificacao) {
        this.nome = nome;
        this.data_modificacao = data_modificacao;
    }

    
    public ListaArquivos() {
    }

    public String getData_modificacao() {
        return data_modificacao;
    }

    public void setData_modificacao(String data_modificacao) {
        this.data_modificacao = data_modificacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getTam() {
        return tam;
    }

    public void setTam(long tam) {
        this.tam = tam;
    }
}

    