/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpa.lprad.predict.correlacao;

/**
 *
 * @author J. Gabriel Lima
 */
public class CorrelacaoBean {

    private String nome;
    private String atributos;
    private String[] atrib;

    public CorrelacaoBean() {
    }

    

    public CorrelacaoBean(String nome, String atributos) {
        this.nome = nome;
        this.atributos = atributos;
    }

    public CorrelacaoBean(String nome, String[] atrib) {
        this.nome = nome;
        this.atrib = atrib;
    }

    

    public String getAtributos() {
        atributos="";
        for(String i:getAtrib())
            atributos+=i+",";
        return atributos;
    }

    public void setAtributos(String atributos) {
        this.atributos = atributos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String[] getAtrib() {
        return atrib;
    }

    public void setAtrib(String[] atrib) {
        this.atrib = atrib;
    }

    public void serAtrib(String atrib)
    {
        this.atrib = atrib.split(",");
    }

   



}
