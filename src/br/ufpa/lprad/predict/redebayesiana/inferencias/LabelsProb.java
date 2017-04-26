/*
 * LabelsAtrib.java
 *
 * Created on 28 de Abril de 2005, 18:09
 */
package br.ufpa.lprad.predict.redebayesiana.inferencias;

import java.awt.Color;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.*;
import javax.swing.JLabel;

public class LabelsProb extends JLabel implements Serializable{
    
    //atributos basicos da classe
    private int atributo;       // Qual indice do atributo que a progressBar representa
    private int faixa;          //faixa q esta sendo trabalhada
    private String valorProb;
    //constantes de layout
    private final int ALT_LABEL = 14; 
    private final int LAR_LABEL = 50;
    private final int X_LABEL = 129;
    
    private double prob;
    
    String teste;

    // Referencias a outras classes
    private MetaDados meta;     
    
    /** Creates a new instance of LabelsAtrib */
    public LabelsProb(int atr, int fx, MetaDados md){//, Dados dd) {      
        atributo = atr;
        faixa = fx;
        meta = md;        
    }
    
    /**
     * seta as informacoes padroes da classe
     */
    public void setInfoPadrao(int posicao) {
        super.setText(getNomeFaixa());
        super.setForeground(Color.BLACK);
        super.setBounds(X_LABEL, posicao, LAR_LABEL, ALT_LABEL);             
    }     
    
    /**
     * recebe o valor de Probabilidade da faixa e converte-o para
     * e string e formata para um valor com 2 casas decimais
     */    
    public String getNomeFaixa(){
        String valorProb=null;
        double aux = 0;
        
        //System.out.println("Probabilidade do atributo -"+atributo+" na faixa - "+faixa+" é: "+meta.getProb(atributo,faixa));
        setProbabilidade(meta.getProb(atributo,faixa));
        
       // aux = meta.getProb(atributo,faixa)*100; 
        aux = getProbabilidade()*100; 
        DecimalFormat Converte = new DecimalFormat("0.00");           
        valorProb = String.valueOf(Converte.format(aux));
        valorProb = String.valueOf(valorProb)+"%";     
        this.valorProb = valorProb;
        return (valorProb);
    } 
    
    public String getValorProb(){
        return this.valorProb;
    }
    
    // probabilidade que será setada nos diretamente nos vertices
    public double getProbabilidade()
    {
        return this.prob;
    }
    
    public void setProbabilidade(double novaProb)
    {
        this.prob = novaProb;
    }
}

