/*
 * Peso.java
 *
 * Created on November 29, 2005, 11:23 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package br.ufpa.lprad.predict.previsao.rna;

import java.util.*;
/**
 *
 * @author FADESP
 */
public class Peso {
    double[][] P;
    
    public Peso(int Ant, int Post, boolean Inicializa) {
       if(Inicializa){
           Random m_random = null; 
           P = new double[Ant][Post]; 
           for(int i=0;i<Ant;i++){
            for(int j=0;j<Post;j++){
                P[i][j] = (Math.random()-0.5)/2;
                //P[i][j] = m_random.nextDouble();
            }
           
           }
       }
       else {
           P = new double[Ant][Post]; 
       }
        
    }
    
    public void CalcAtivacaoHide(Neuron[] CamadaAnt, Neuron[] CamadaPost){
        double[] ativacao = new double[P[0].length];
        for(int j=0;j<P[0].length;j++){
           for(int i=0;i<P.length;i++){
               ativacao[j]+=P[i][j]*CamadaAnt[i].GetSaida(); 
           }
           
           CamadaPost[j].CalAtivacao(ativacao[j]);
           //System.out.println(CamadaPost[j].GetSaida());
       }
        
       
    }
    
    
    public void CalcAtivacaoOut(Neuron[] CamadaAnt, Neuron[] CamadaPost){
        double[] ativacao = new double[P[0].length];
        for(int j=0;j<P[0].length;j++){
           for(int i=0;i<P.length;i++){
               ativacao[j]+=P[i][j]*CamadaAnt[i].GetSaida(); 
           }
           
           CamadaPost[j].CalAtivacaoEntrada(ativacao[j]);
           //System.out.println(CamadaPost[j].GetSaida());
       }
        
       
    }
    
    
    public void CalcBackPropagationOut(Neuron[] CamadaAnt, Neuron[] CamadaPost, double txAprend){
        double backativacao=0;
        for(int i=0;i<CamadaAnt.length;i++){
            
            for(int j=0;j<CamadaPost.length;j++){
                backativacao+= P[i][j]*CamadaPost[j].GetErroGeralSaida();
                P[i][j] = P[i][j]+(txAprend*CamadaAnt[i].GetSaida()*CamadaPost[j].GetErroGeralSaida());
                if(P[i][j]<-5) P[i][j]=-5;
                else if(P[i][j]>5)P[i][j]=5;
            }
            
            CamadaAnt[i].CalErroEscondida(backativacao);
            backativacao=0;
            
        }
                          
    }
    
    
    
    public void CalcBackPropagationHide(Neuron[] CamadaAnt, Neuron[] CamadaPost, double txAprend){
        //double backativacao=0;
        for(int i=0;i<CamadaAnt.length;i++){
            
            for(int j=0;j<CamadaPost.length;j++){
               // backativacao+= P[i][j]*CamadaPost[j].GetErro();
                P[i][j] = P[i][j]+(txAprend*CamadaAnt[i].GetSaida()*CamadaPost[j].GetErro());
                
            }
            
            //CamadaAnt[i].CalErroEscondida(backativacao);
            //backativacao=0;
            
        }
                
    }
    
    public int GetNumLinha(){
        return(P.length);
    }
    
    public int GetNumColuna(){
        return(P[0].length);
    }
    
    public double GetPeso(int CAnt, int CPos){
        return(P[CAnt][CPos]);
    }
    
    public void SetPeso(int CAnt, int CPos, double Val){
        P[CAnt][CPos]=Val;
    }
    
    
}

