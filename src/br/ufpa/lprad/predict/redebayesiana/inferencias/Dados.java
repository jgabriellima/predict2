/*
 * ### Programa Celpa 1.5
 *
 * Dados.java
 *
 * Funcao:  Conjunto de registros a serem trabalhados na Rede. Podem ser todos
 *          os registros da rede ou entao a rede original apos a utilizacao de
 *          um 'filtro', como forma verificacao da propagacao da determinacao de
 *          um atributo pela rede bayesiana.
 *          Todos os objetos de Dados devem utilizar uma referencia ao objeto
 *          MetaDados para manipular seus atributos.
 *
 * Created on 23 de Abril de 2005, 13:35
 */
package br.ufpa.lprad.predict.redebayesiana.inferencias;

import java.io.Serializable;

public class Dados implements Serializable {

    // Conjunto de Dados 
    private int entradas;           // Quantidade de registros nesse conjunto de Dados
    private String[][] dados;       // dados[x][y]         --> atributo y da entrada x
    private int[][] numRegFxAtrib;  // numRegFxAtrib[x][y] --> qntd de entradas que a 
    //                         faixa y do atributo x tem
    // Inferencia Bayesiana para este conjunto de Dados
    //private ProbabilidadeNo prob;
    // Referencia para trabalho dos Dados
    private MetaDados meta;

    /** Creates a new instance of Dados */
    public Dados(int ent, int atribs, MetaDados metdad) {
        // System.out.println("Classe Dados");

        entradas = ent;

        dados = new String[atribs][entradas];
        numRegFxAtrib = new int[atribs][entradas - 1];

        meta = metdad;


    }

    public int getEntradas() {
        return entradas;
    }

    public String getDado(int x, int y) {
        return dados[x][y];
    }

    public void setDado(int x, int y, String valor) {
        dados[x][y] = valor.replace(",", ".");
    }

    public int getNumRegFxAtrib(int x, int y) {
        return numRegFxAtrib[x][y];
    }

    public void setNumRegFxAtrib(int x, int y, int valor) {
        numRegFxAtrib[x][y] = valor;
    }

    public int[][] meDaTeuNRFA() {
        return numRegFxAtrib;
    }

    public String[][] getVetorDados() {
        return dados;
    }
}