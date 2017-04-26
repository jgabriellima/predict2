/*
 * Main.java
 *
 * Created on November 29, 2005, 11:05 AM
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
public class MainAnual {
    
    /** Creates a new instance of Main */
    public MainAnual() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String melhorRede;
      for(double txh=0.1;txh<1.0;txh=txh+0.1){  
       for(double txout=0.01;txout<0.1;txout=txout+0.01){     
        // TODO code application logic here                         0.7,0.07   
        RedeMLPanual MLP1 = new RedeMLPanual(12, 10, 12, 12, 30000, txh,txout);        
        //RedeMLP MLP1 = new RedeMLP("rede_2532.txt");
        
        //RedeMLPextend MLP1 = new RedeMLPextend("rede_2705.txt");
        
        MLP1.CarregaArqTreino("dados/treinoanual.txt");
        MLP1.CarregaArqCV("dados/testeanual.txt");
        MLP1.Calcnet();
        MLP1.Treinamento();
        MLP1.Calcnet();
        melhorRede=MLP1.GetMelhorRede();
        
        MLP1 = new RedeMLPanual(melhorRede);
        System.out.println("Melhor Rede "+melhorRede);
        MLP1.CarregaArqCV("dados/testeanual.txt");
        //MLP1.CarregaArqCVextend("dados/testeExtend.txt", 12, 13);
        System.out.println("ErrosMSECV = "+MLP1.CalcnetCVimp());
        //MLP1.CalcnetCVimpExtend(12,2);
        //MLP1.SalvaRede("rede.txt");
        
       }//for txout 
      }//for txh 
    }
    
}
