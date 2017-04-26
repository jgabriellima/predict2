/*
 * Classe Rede Bayesiana
 * 
 * Classe que modela a estrutura da Rede Bayesiana
 *
 *
 * @autor Gabriel
 */
package br.ufpa.lprad.predict.redebayesiana.aprendizado;

import java.util.*;

public class RedeBayesiana {

    private int nVertices;        //N�mero de Vertices que comp�e a Rede Bayesiana
    private Vector<Vertice> vertices;      //Verices que comp�es a Rede Bayesiana
    public static boolean statusProbabilidade = false;

    //Construtor default
    public RedeBayesiana() {
    }
    //M�todo para alterar o n�mero de Vertices da Rede Bayesiana

    public void setNVertices(int nVertices) {
        this.nVertices = nVertices;
    }
    //M�todo que retorna o n�mero de Vertices da Rede Bayesiana

    public int getNVertices() {
        return this.nVertices;
    }
    //M�todo que retorna os vertices da Rede Bayesiana

    public Vector<Vertice> getVertices() {
        return this.vertices;
    }
    //M�todo que seta os vertices da Rede Bayesiana

    public void setVertices(Vector<Vertice> vertices) {
        
        this.vertices = vertices;
    }
    //M�todo que retorna o �ndice de um vertice no vector vertice dado seu nome

    public int getIndexOfVerticeByNome(String nome) {
        int i = -1;
        for (int j = 0; j < this.nVertices; j++) {
            Vertice verticeAtual = (Vertice) this.vertices.get(j);
            if (verticeAtual.getNome().equals((nome.trim()))) {
                i = j;
                j = this.nVertices;
            }
        }
        return i;
    }

    //M�todo que retorna um vertice da Rede bayesiana pelo seu respectivo nome     
    public Vertice getVerticeByNome(String nomeVertice) {
        Vector vertices = this.getVertices();
        Vertice vertice = new Vertice();
        for (int i = 0; i < vertices.size(); i++) {
            Vertice va = (Vertice) vertices.get(i);
            if (va.getNome().equals(nomeVertice)) {
                vertice = va;
                i = vertices.size();
            }
        }
        return vertice;
    }

    public int getIdVerticeByNome(String nomeVertice) {

        int indice = 0;
        for (int i = 0; i < getVertices().size(); i++) {

            if (getVertices().get(i).getNome().equalsIgnoreCase(nomeVertice)) {
                indice = i;
                break;
            }

        }
        return indice;

    }

    public int getIndiceByVertice(Vertice v) {
        int indice = 0;
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).equals(v)) {
                indice = i;
                break;
            }
        }
        return indice;
    }

    //M�todo que retorna um vertice da Rede Bayesiana pelo seu respectivo index
    public Vertice getVerticeByIndex(int i) {
        Vector vertices = this.getVertices();
        Vertice vertice = new Vertice();
        for (int j = 0; j < this.getNVertices(); j++) {
            if (i == j) {
                vertice = (Vertice) vertices.get(i);
                j = this.getNVertices();
            }
        }
        return vertice;
    }
}