/*
 * NormalizacaoTeste.java
 *
 * Created on 18 de Agosto de 2006, 01:28
 */

package br.ufpa.lprad.predict.previsao.rna;
import java.util.*;
import java.io.*;
import java.lang.*;
/**
 *
 * @author Conde
 */
public class NormalizacaoTeste {
    
    /** Creates a new instance of NormalizacaoTeste */
    public NormalizacaoTeste() {
    }
    
    public static void main(String[] args) {
         double[][] matriz=new double[12][16];
         //double[][][] trJAN,trFEV,trMAR,trABR,trMAIO,trJUN,trJUL,trAGO,trSET,trOUT,trNOV,trDEZ; 
         //double[][][] tr= new double[12][14][3];
         double[][][] tr= new double[12][2][5];                  // [12][2][3];
         
       try{
          
         FileReader reader = new FileReader(new File("Rural/Rural.txt"));
         BufferedReader leitor = new BufferedReader(reader);        
         String linha=null;
         StringTokenizer tok;
         int idlinha=0;
          while((linha = leitor.readLine()) !=null    ) {
             tok = new StringTokenizer(linha,"\t");
             for(int col=0;col<matriz[0].length;col++){
               matriz[idlinha][col]= Double.valueOf(tok.nextToken()).doubleValue();  
              
             }
          idlinha++;
          }    
        
        for(int ano=13;ano<15;ano++){
             
           for(int mes=0;mes<12;mes++){

                tr[mes][ano-13][0]=matriz[mes][ano];
                tr[mes][ano-13][1]=matriz[mes][ano-1]; 
                tr[mes][ano-13][2]=matriz[mes][ano-2];     
                tr[mes][ano-13][3]=matriz[mes][ano-3];     
                tr[mes][ano-13][4]=matriz[mes][ano+1];     
                          
           }  
             
        } 
         
        for(int m=0;m<tr.length;m++){
                          //194813
            Grava(tr[m],m,9518); 
        } 
         
       leitor.close();  reader.close();
       //saida.close();   writer.close();
       }catch (IOException x){}      
     }
    
    public static void Grava(double[][] dados,int Idmes,double fator){
       double[] Decada = {0.1,0.2,0.3};
       double[] Ano = {0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9};
       String[] Mes={"JAN","FEV","MAR","ABR","MAIO","JUN","JUL","AGO","SET","OUT","NOV","DEZ"};    
       
       try{
        FileWriter writer = new FileWriter(new File("Rural/teste"+Mes[Idmes]+".txt"));
        PrintWriter saida = new PrintWriter(writer);   
        String Saida="";int dec=1,ano=4;
        Saida=dados.length+" "+(dados[0].length+2);
        saida.println(Saida);
        for(int lin=0;lin<dados.length;lin++){
          Saida=Decada[dec]+"\t"+Ano[ano]+"\t"+(dados[lin][0]/fator)+"\t"+(dados[lin][1]/fator)+"\t"+(dados[lin][2]/fator)+"\t"+(dados[lin][3]/fator)+"\t"+(dados[lin][4]/fator);
          if(lin<dados.length-1){
           saida.println(Saida);
           Saida="";
          }else{
                saida.print(Saida);
                Saida="";
              }
          ano++;
          if(ano==10){
              ano=0; dec++;
          }
          
        }  
       saida.close();   writer.close();    
       }catch (IOException x){}      
       
       
       
       
        
    }
    
    
}
