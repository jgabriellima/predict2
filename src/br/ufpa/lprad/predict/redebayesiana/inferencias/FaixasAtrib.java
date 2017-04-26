/*
 * FaixasAtrib.java
 *
 * Created on 27 de Abril de 2005, 16:52
 */

package br.ufpa.lprad.predict.redebayesiana.inferencias;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class FaixasAtrib extends JProgressBar implements Serializable{
    
    private int atributo;           //Qual indice do atributo que a progressBar representa
    private String nome;    
    private int faixa;              //a faixa q esta sendo inferenciada
    private String probabilidade;   //probabilidade da faixa
    
    //atributos dos JProgressBar  
    private final int LAR_PROGBAR = 100;
    private final int ALT_PROGBAR = 14; 
    private final int X_PROGBAR = 27;
    private final int Y_INIT_PROGBAR = 2;
    private final int DELAY = 2; 
    
    //Referencias dos dados e atributos
    private MetaDados meta;
    
    
    /** Creates a new instance of FaixasAtrib */
    public FaixasAtrib(int atr, int fx, MetaDados md){
        super(0,100);
        atributo = atr;
        meta = md;     
        faixa = fx;
        nome = meta.getAtributo(atributo);       
    }    
    
    public void setInfoPadrao(int posicao) {        
        super.setBorderPainted(true);
        super.setBackground(Color.white);
        super.setForeground(Color.blue);
        
        super.setValue(getValueFaixa());
        super.setMaximum(100); 
        super.setBounds(X_PROGBAR, posicao, LAR_PROGBAR, ALT_PROGBAR);      
        
    }
    
    //pega os valores das probabilidades de cada faixa;
    public int getValueFaixa(){
        int valorJprogBar=0;        
        valorJprogBar=(int)(meta.getProb(atributo, faixa)*100);        
        return valorJprogBar;
    }   
    
    public void setProbabilidade(String prob){
        this.probabilidade = prob;
    }
    
    public String getProbabilidade(){
        return probabilidade;
    }
}
