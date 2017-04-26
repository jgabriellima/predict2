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
import java.util.*;
import java.io.*;
import java.lang.*;
/**
 *
 * @author FADESP
 */
public class Matlab {
    
    /** Creates a new instance of Main */
    public Matlab() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
                 
        int Npad=13;
        int In=7;  int Out=1;
        String[] Mes={"JAN","FEV","MAR","ABR","MAIO","JUN","JUL","AGO","SET","OUT","NOV","DEZ"};      
        String diretorio="Dados/Execucao";
        double Dados[][] = new double [Npad][In+Out];
        //double[][][] entrada=new double [12][14][8];    //{0.2,0.4,0.750209201,0.717738682}; 
        double DadosTrIN[][] = new double [In][Npad];
        double DadosTrOUT[][] = new double [Out][Npad];
        
        for(int M=0;M<Mes.length;M++){
          //entrada[id] = Arq(diretorio+"/teste"+Mes[id]+".txt",7); 
          Dados=Arq(diretorio+"/treino"+Mes[M]+".txt",Npad,(In+Out));             
          for(int padrao=0;padrao<Npad;padrao++){
              
              for(int ColIN=0;ColIN<In;ColIN++){
                  
                  DadosTrIN[ColIN][padrao] = Dados[padrao][ColIN];
              }
              
              for(int ColOUT=In;ColOUT<Dados[0].length;ColOUT++){
                  
                  DadosTrOUT[ColOUT-In][padrao] = Dados[padrao][ColOUT];
              }
              
          }
          
          GravaArq(diretorio,"treino"+Mes[M]+"IN",DadosTrIN);
          GravaArq(diretorio,"treino"+Mes[M]+"OUT",DadosTrOUT); 
        }
        
        GeraArqTeste(diretorio,1,In,Out);
        
    }
    
    
    public static double[][] Arq(String NomeArq, int lin, int col){
        String linha = null;
        double[][] Dados = new double[lin][col];
         try{    
           
           FileReader reader = new FileReader(new File(NomeArq));
           BufferedReader leitor = new BufferedReader(reader);
           StringTokenizer tok;           
           leitor.readLine();           
           for(int L=0;L<lin;L++){            
           
           linha=leitor.readLine();
             tok = new StringTokenizer(linha,"\t");             
             for(int C=0;C<col;C++){
                Dados[L][C]= Double.valueOf(tok.nextToken()).doubleValue();               
             }
                         
           }
       
         leitor.close();
         reader.close();
      
         }catch (IOException x){   }
        
        return(Dados);
    }
    
    public static void GravaArq(String dir,String NomeArq,double[][] info ){
       
       try{
        FileWriter writer = new FileWriter(new File(dir+"/"+NomeArq+".txt"));
        PrintWriter saida = new PrintWriter(writer);   
        String Saida="";
        
        //Saida=NomeArq+" =[";
        for(int linha=0;linha<info.length;linha++){
            for(int col=0;col<info[0].length;col++){
                Saida+=info[linha][col]+" ";
            }
            //Saida+=" ;";
            saida.println(Saida);
            Saida="";
        }
        
        //Saida=" ];";
        saida.println(Saida);
       
       saida.close();   writer.close();    
       }catch (IOException x){}      
        
        
    }
    
    public static void GeraArqTeste(String Dir, int Numpad, int NumIn, int NumOut){
        
        //int Npad=4;
        //int In=7;  int Out=1;
        String[] Mes={"JAN","FEV","MAR","ABR","MAIO","JUN","JUL","AGO","SET","OUT","NOV","DEZ"};      
        //String diretorio="Dados/Execucao";
        double Dados[][] = new double [Numpad][NumIn+NumOut];
        //double[][][] entrada=new double [12][14][8];    //{0.2,0.4,0.750209201,0.717738682}; 
        double DadosTrIN[][] = new double [NumIn][Numpad];
        double DadosTrOUT[][] = new double [NumOut][Numpad];
        
        for(int M=0;M<Mes.length;M++){
          //entrada[id] = Arq(diretorio+"/teste"+Mes[id]+".txt",7); 
          Dados=Arq(Dir+"/teste"+Mes[M]+".txt",Numpad,(NumIn+NumOut));             
          for(int padrao=0;padrao<Numpad;padrao++){
              
              for(int ColIN=0;ColIN<NumIn;ColIN++){
                  
                  DadosTrIN[ColIN][padrao] = Dados[padrao][ColIN];
              }
              
              for(int ColOUT=NumIn;ColOUT<Dados[0].length;ColOUT++){
                  
                  DadosTrOUT[ColOUT-NumIn][padrao] = Dados[padrao][ColOUT];
              }
              
          }
          
          GravaArq(Dir,"teste"+Mes[M]+"IN",DadosTrIN);
          GravaArq(Dir,"teste"+Mes[M]+"OUT",DadosTrOUT); 
        }
        
        
    }
    
}
