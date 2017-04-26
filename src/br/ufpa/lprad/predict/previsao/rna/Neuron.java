/*
 * Neuron.java
 *
 * Created on November 29, 2005, 11:06 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package br.ufpa.lprad.predict.previsao.rna;

/**
 *
 * @author FADESP
 */
public class Neuron {
    
    double saida,erro,erromse;
        
    public Neuron() {
        saida = 0; erro=0; erromse=0;
    }
    
    public void CalAtivacaoEntrada(double PotencialAtivacao){
        saida = PotencialAtivacao;
    }
    
    public void CalAtivacao(double PotencialAtivacao){
        saida = this.FuncaoAtivacao(PotencialAtivacao);
    }
        
    public double FuncaoAtivacao(double PA){
      if(PA < -45){ return(0);}
      else if(PA > 45){return(1); } 
      else {return( 1 / (1+Math.exp(-PA)) ); }
          
      //return( 1 / (1+Math.exp(-PA)) );
    } 
    
    public double GetSaida(){
        return(saida);
    }
    
    public void CalErroSaida(double Esperado){
        double errotmp=Esperado - saida;
        erro = errotmp* (saida*(1-saida));
        erromse+=Math.pow(errotmp, 2);
    }
   
    public void CalErroSaidaACC(double Esperado){
        double errotmp=Esperado - saida;
        //erro += errotmp* (saida*(1-saida));
        erro += errotmp;
        erromse+=errotmp*errotmp;
    }
    
    public double GetErroGeralSaida(){
        
     //  return( (erro*(saida*(1-saida)) )/Numpadrao);
        return(erro);
    }
   
        
    public void CalErroEscondida(double Somatorio){
        erro = Somatorio* (saida*(1-saida));
    }
    
    public double GetErro(){
        return(erro);
    }
    
    public double GetErroMSE(){
        return(erromse);
    }
    
    public void ResetErros(){
        erro=0;
        erromse=0;
    }
}
