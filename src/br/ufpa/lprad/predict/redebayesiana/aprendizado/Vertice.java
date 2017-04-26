/*
 * Classe Vertice
 *
 * Classe que modela os Vertices da Rede Bayesiana
 *
 *
 * @autor Felipe Leal Valentim
 */
package br.ufpa.lprad.predict.redebayesiana.aprendizado;

import java.util.*;

public class Vertice {

    private String nome;    // Nome de Identificação do Vertice
    private int nPais;      // Número de pais do Vertice
    private int nFilhos;    // Número de filhos do Vertice
    private String[] estados; //Quais estados os valores desse vertice podem assumir
    private int nEstados;   // Número de Estados que as variáveis da TPC podem assumir
    private Vector vetorPais = new Vector();   // Vertices pais do Vertice
    private Vector vetorFilhos = new Vector(); // Vertices filhos do Vertice
    private double Probabilidade[]; //tabela de probabilidades
    private double TabProbCond[][]; //tabela de Probabiliadades Condicionais
    double valor;
    private int faixaInferenciada = -1;
    private int estadoInferenciado = -1;
    private boolean inferenciado;
    private boolean paiInferenciado;


    //Contrutot Default
    public Vertice() {

        //vetorFilhos
    }
    //Construtor da Classe

    public Vertice(String nome, int nPais, int nFilhos, int nEstados, String[] estados, Vector pais, Vector filhos) {
        this.nome = nome;
        this.nPais = nPais;
        this.nFilhos = nFilhos;
        this.nEstados = nEstados;
        this.estados = estados;
        this.vetorPais = pais;
        this.vetorFilhos = filhos;

    }

    public boolean isInferenciado() {
        return inferenciado;
    }

    public void setInferenciado(boolean inferenciado) {
        this.inferenciado = inferenciado;
    }


    public int getEstadoInferenciado() {
        return estadoInferenciado;
    }

    public void setEstadoInferenciado(int estadoInferenciado) {
        this.estadoInferenciado = estadoInferenciado;
    }

    public int getFaixaInferenciada() {
        return faixaInferenciada;
    }

    public void setFaixaInferenciada(int faixaInferenciada) {
        this.faixaInferenciada = faixaInferenciada;
    }
    //Método para alterar o nome do Vertice

    public void setNome(String nome) {
        this.nome = nome;
    }
    //Método que retorna o nome do Vertice

    public String getNome() {
        return this.nome;
    }
    //Método para alterar o número de Pais do Vertice

    public void setNPais(int nPais) {
        this.nPais = nPais;
    }
    //Método que retorna o número de Pais do Vertice

    public int getNPais() {
//        return this.vetorPais.size();
        return this.nPais;
    }
    //Método que altera o número de Filhos do Vertice

    public void setNFilhos(int nFilhos) {
        this.nFilhos = nFilhos;
    }
    //Método que retorna o número de Filhos do Vertice

    public int getNFilhos() {
//        return this.vetorFilhos.size();
        return this.nFilhos;
    }
    //Método que altera o número de estados que os dados da tpc do vertice pode assumir

    public void setNEstados(int nEstados) {
        this.nEstados = nEstados;
    }
    //Método que retorna o número de estados que os dados da tpc do vertice pode assumir

    public int getNEstados() {
        return this.nEstados;
    }
    //Método que altera os pais do Vertice

    public void setPais(Vector pais) {
        this.vetorPais = pais;
    }

    public void setPais(Vertice pai) {
        if (this.vetorPais == null) {
            this.vetorPais = new Vector();
        }
        this.vetorPais.add(pai);
    }
    //Método que retortna os pais do Vertice

    public Vector<Vertice> getPais() {
        if (this.vetorPais == null) {
            this.vetorPais = new Vector<Vertice>();
        }
        return this.vetorPais;
    }
    //Método que altera os filhos do Vertice

    public void setFilhos(Vector<Vertice> filhos) {
        this.vetorFilhos = filhos;
    }

    public void setFilhos(Vertice filho) {
        if (this.vetorFilhos == null) {
            this.vetorFilhos = new Vector();
        }

        this.vetorFilhos.add(filho);
    }
    //Método que retortna os filhos do Vertice

    public Vector getFilhos() {
        return this.vetorFilhos;
    }
    //Método que altera os estados do vertice

    public void setEstados(String[] estados) {
        this.estados = estados;
    }
    //Método que retortna os estados do Vertice

    public String[] getEstados() {
        return this.estados;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    //Método que recebe um vetor de String de estados e o valor do estado o indice do estado
    //no veto de String
    private int getIndexEstadoValores(String[] estados, String valor) {
        int index = -1;
        for (int i = 0; i < estados.length; i++) {
            if (estados[i].equals(valor)) {
                index = i;
                i = estados.length;
            }
        }
        return index;
    }

    private int[] getNEstadosPais(Vector vetorPais) {
        int[] nEstadosPais = new int[vetorPais.size()];
        for (int i = 0; i < vetorPais.size(); i++) {
            Vertice va = (Vertice) vetorPais.get(i);
            nEstadosPais[i] = va.getNEstados();
        }
        return nEstadosPais;
    }

    public void setProbabilidade(double Probabilidade[]) {
        this.Probabilidade = Probabilidade;
    }

    public double[] getProbabilidade() {
        return this.Probabilidade;
    }

    public double getProbabilidade(int x) {
        return this.Probabilidade[x];
    }

    public void setTabProbCond(double TabProbCond[][]) {
        this.TabProbCond = TabProbCond;
    }

    public double[][] getTabProbCond() {
        return TabProbCond;
    }

    // x eh o indice das combinacoes dos pais e y eh o estado do vertice
    public double getTabProbCond(int x, int y) {
        return TabProbCond[x][y];
    }

    //retorna as combinacoes possiveis entre os estados do pais do vertice
    public Vector[] getCombinacoes() {
        Vector vetor = this.vetorPais;

        // descobre o total das combinacoes
        int tamanho = 1;

        for (int i = 0; i < nPais; i++) {
            tamanho *= ((Vertice) vetor.elementAt(i)).getNEstados();
        }

        //faz todas as combinacoes possiveis
        Vector comb[] = new Vector[tamanho];
        for (int i = 0; i < comb.length; i++) {
            comb[i] = new Vector();
        }

        int r = tamanho;
        for (int i = 0; i < vetor.size(); i++) {
            r /= ((Vertice) vetor.elementAt(i)).getNEstados();
            int cont = 1;
            int indice = 0;
            for (int j = 0; j < comb.length; j++, cont++) {
                comb[j].add(((Vertice) vetor.elementAt(i)).getEstados()[indice]);
                if (cont == r) {
                    indice++;
                    cont = 0;
                    if (indice == ((Vertice) vetor.elementAt(i)).getNEstados()) {
                        indice = 0;
                    }
                }
            }
        }

        return comb;
    }

    //retorna o indice na tabela de probabilidades condicionais para os estados passados dos pais
    public int getIndiceCombinacao(Vector combinacao) {
        int indice = -1;


        Vector comb[] = getCombinacoes();

        for (int i = 0; i < comb.length; i++) {
            boolean ehEsse = true;
            for (int j = 0; j < comb[i].size(); j++) {
                if (!(comb[i].elementAt(j).toString().equalsIgnoreCase(combinacao.elementAt(j).toString()))) {
                    ehEsse = false;
                }
            }
            if (ehEsse) {
                indice = i;
                break;
            }
        }

        return indice;
    }

    public void setPai(Vertice vertice_menos_um) {
        try {
            if (!this.vetorPais.contains(vertice_menos_um)) {
                this.vetorPais.add(vertice_menos_um);
            }

        } catch (Exception ex) {
            this.vetorPais = new Vector();
            this.vetorPais.add(vertice_menos_um);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vertice other = (Vertice) obj;
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        if (this.vetorPais != other.vetorPais && (this.vetorPais == null || !this.vetorPais.equals(other.vetorPais))) {
            return false;
        }
        if (this.vetorFilhos != other.vetorFilhos && (this.vetorFilhos == null || !this.vetorFilhos.equals(other.vetorFilhos))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (this.nome != null ? this.nome.hashCode() : 0);
        hash = 37 * hash + (this.vetorPais != null ? this.vetorPais.hashCode() : 0);
        hash = 37 * hash + (this.vetorFilhos != null ? this.vetorFilhos.hashCode() : 0);
        return hash;
    }

    public void tirar() {
        try {
            this.vetorPais.remove(this.vetorPais.lastElement());
        } catch (Exception ex) {
        }
    }
}