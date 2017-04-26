/*
 * RedeMLP.java
 *
 * Created on December 1, 2005, 2:06 PM
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
public class RedeMLPanual {
    double CoefiAprendH, CoefiAprendO; double[][] Train,CV;
    int NumPadroes,NumEpocas; String MelhorRede,MelhorRedeArq;
    Neuron[] C1,C2,C3; Peso P1,P2;
    /** Creates a new instance of RedeMLP */
    public RedeMLPanual(int NIn,int NHide,int NOut,int NPadroes, int NEpocas, double CAprendH, double CAprendO) {
      NumPadroes=NPadroes; NumEpocas=NEpocas; MelhorRede=null;
      CoefiAprendH=CAprendH; CoefiAprendO=CAprendO;
      //Train = new double[NPadroes][NIn+NOut];
      C1 = new Neuron[NIn];
      for(int x=0;x<C1.length;x++){ C1[x] = new Neuron();  } 
      C2 = new Neuron[NHide];
      for(int x=0;x<C2.length;x++){ C2[x] = new Neuron();  } 
      C3 = new Neuron[NOut];
      for(int x=0;x<C3.length;x++){ C3[x] = new Neuron();  } 
      P1 = new Peso(NIn,NHide,true);
      P2 = new Peso(NHide, NOut,true);
    }
    
    public RedeMLPanual(String Nomearq){
        String linha = null;
        try{
           SetMelhorRede(Nomearq);
           FileReader reader = new FileReader(new File(Nomearq));
           BufferedReader leitor = new BufferedReader(reader);
           StringTokenizer tok;
           linha = leitor.readLine();//linha de cabeçalho
           linha = leitor.readLine();
           tok = new StringTokenizer(linha,",");
           
           C1 = new Neuron[Integer.valueOf(tok.nextToken())];//entrada
           C2 = new Neuron[Integer.valueOf(tok.nextToken())];//Hide
           C3 = new Neuron[Integer.valueOf(tok.nextToken())];//Saída
           for(int x=0;x<C1.length;x++){ C1[x] = new Neuron();  }            
           for(int x=0;x<C2.length;x++){ C2[x] = new Neuron();  } 
           for(int x=0;x<C3.length;x++){ C3[x] = new Neuron();  } 
           P1 = new Peso(C1.length,C2.length,false);
           P2 = new Peso(C2.length,C3.length,false);
           
           linha = leitor.readLine();//Comentários
           
           for(int lin=0;lin<C1.length;lin++){
             linha = leitor.readLine();     
             tok = new StringTokenizer(linha,",");
             for(int col=0;col<C2.length;col++){
               //Train[idlinha][col]= Double.valueOf(tok.nextToken()).doubleValue();  
               P1.SetPeso(lin,col,Double.valueOf(tok.nextToken()).doubleValue());
             }      
                 
           }
    
           linha = leitor.readLine();//Comentários
           for(int lin=0;lin<C2.length;lin++){
             linha = leitor.readLine();     
             tok = new StringTokenizer(linha,",");
             for(int col=0;col<C3.length;col++){
               //Train[idlinha][col]= Double.valueOf(tok.nextToken()).doubleValue();  
               P2.SetPeso(lin,col,Double.valueOf(tok.nextToken()).doubleValue());
             }      
                 
           }
           
         leitor.close();
         reader.close();  
            
          
        }catch (IOException x){   }   
    }
    
   
    
   /* 
    public void CarregaArq(){
        Train[0][0]=1; Train[0][1]=0; Train[0][2]=1;
        Train[1][0]=0; Train[1][1]=1; Train[1][2]=1;
        Train[2][0]=1; Train[2][1]=1; Train[2][2]=0;
        Train[3][0]=0; Train[3][1]=0; Train[3][2]=0;
        
    }
    */
    public void CarregaArqTreino(String Nometreino){
    
       //double [][] entrada;              
       String linha = null;
         try{    
           FileReader reader = new FileReader(new File(Nometreino));
           BufferedReader leitor = new BufferedReader(reader);
           StringTokenizer tok;
           linha = leitor.readLine();
           tok = new StringTokenizer(linha," ");
           
           Train = new double[Integer.valueOf(tok.nextToken())][Integer.valueOf(tok.nextToken())];
           //System.out.println(entrada.length+" "+entrada[0].length);
    
           int idlinha=0;
           while((linha = leitor.readLine()) !=null    ) {
             tok = new StringTokenizer(linha,"\t");
             for(int col=0;col<Train[0].length;col++){
               Train[idlinha][col]= Double.valueOf(tok.nextToken()).doubleValue();  
              
             }
       
            //System.out.println("linha "+idlinha);
            idlinha++;
       
           }
    
         leitor.close();
         reader.close();
      
         }catch (IOException x){   }
        
    }
   
    public void CarregaArqCV(String NomeCV){
    
       //double [][] entrada;              
       String linha = null;
         try{    
           FileReader reader = new FileReader(new File(NomeCV));
           BufferedReader leitor = new BufferedReader(reader);
           StringTokenizer tok;
           linha = leitor.readLine();
           tok = new StringTokenizer(linha," ");
    
           CV = new double[Integer.valueOf(tok.nextToken())][Integer.valueOf(tok.nextToken())];
           System.out.println(CV.length+" "+CV[0].length);
    
           int idlinha=0;
           while((linha = leitor.readLine()) !=null    ) {
             tok = new StringTokenizer(linha,"\t");
             for(int col=0;col<CV[0].length;col++){
                 //System.out.println(col);
                 CV[idlinha][col]= Double.valueOf(tok.nextToken()).doubleValue();
               
             }
       
            //System.out.println("linha "+idlinha);
            idlinha++;
       
           }
    
         leitor.close();
         reader.close();
      
         }catch (IOException x){   }
        
    }
    
    public void CarregaArqCVextend(String NomeCV,int Numpad,int NumpadExtend){
    
       //double [][] entrada;              
       String linha = null;
         try{    
           FileReader reader = new FileReader(new File(NomeCV));
           BufferedReader leitor = new BufferedReader(reader);
           StringTokenizer tok;
           linha = leitor.readLine();
           tok = new StringTokenizer(linha," ");
           
           CV = new double[Integer.valueOf(tok.nextToken())][Integer.valueOf(tok.nextToken())];
           System.out.println(CV.length+" "+CV[0].length);
    
           int idlinha=0;
           while((linha = leitor.readLine()) !=null    ) {
             tok = new StringTokenizer(linha,"\t");
             if (idlinha<Numpad){
             for(int col=0;col<CV[0].length;col++){
                 //System.out.println(col);
                 CV[idlinha][col]= Double.valueOf(tok.nextToken()).doubleValue();
               
             }
             }
             else{             //CV[0].length
                 for(int col=0;col<2;col++){
                 //System.out.println(col);
                 CV[idlinha][col]= Double.valueOf(tok.nextToken()).doubleValue();
                 }
                 
             }
            //System.out.println("linha "+idlinha);
            idlinha++;
       
           }
    
         leitor.close();
         reader.close();
      
         }catch (IOException x){   }
        
    }
    
    public void CarregaRecall(double [][] recall){
        Train = recall;
        
    }    
    
    public void SalvaRede(String Nomerede){
        
      try{  
         FileWriter writer = new FileWriter(new File(Nomerede));
         PrintWriter saida = new PrintWriter(writer);  
         String linhasaida="";
         saida.println("# Pesos da Rede Treinada "+"TxH=  "+CoefiAprendH+" TxOut="+CoefiAprendO);
         saida.println(C1.length+","+C2.length+","+C3.length);
         //pesos da camada hide e saída
         saida.println(P1.GetNumLinha()+","+P1.GetNumColuna());
         for(int x=0;x<P1.GetNumLinha();x++){ 
          
            for(int y=0;y<P1.GetNumColuna();y++){
              
              linhasaida=linhasaida+P1.GetPeso(x, y)+",";
              
            }
            saida.println(linhasaida);
            linhasaida="";
         }  
         //pesos da camada hide e saída
         saida.println(P2.GetNumLinha()+","+P2.GetNumColuna());
         linhasaida="";
         for(int x=0;x<P2.GetNumLinha();x++){ 
          
            for(int y=0;y<P2.GetNumColuna();y++){
              
              linhasaida=linhasaida+P2.GetPeso(x, y)+",";
              
            }
            saida.println(linhasaida);
            linhasaida="";
            
         }
         
         
        saida.close();
        writer.close();
       }catch (IOException x){} 
   
    }
    
    public void SetMelhorRede(String Nomearq){
        MelhorRede=Nomearq;
    }
    
    public void SetMelhorRedeArq(String Nomearq){
        MelhorRedeArq=Nomearq;
    }
    
    public String GetMelhorRede(){
        return(MelhorRede);
    }
    
     public String GetMelhorRedeArq(){
        return(MelhorRedeArq);
    }
    
    public void Treinamento(){
        double ErroMSE=0,ErroMSEmenor=1.0; int idout=0,epocamenor=0;
        String NomeRede=null; double[] VetErroMSE = new double[NumEpocas];
        for(int epocas=0;epocas<NumEpocas;epocas++){
            
            for(int cin=0; cin<C2.length;cin++){
                    C2[cin].ResetErros();
                }
            for(int cin=0; cin<C3.length;cin++){
                    C3[cin].ResetErros();
                }
            
            for(int padrao=0;padrao<NumPadroes;padrao++){
                //selecionar um padrao aleatoriamente
                for(int cin=0; cin<C1.length;cin++){
                    C1[cin].CalAtivacaoEntrada(Train[padrao][cin]);
                }
                
                P1.CalcAtivacaoHide(C1, C2);
                P2.CalcAtivacaoOut(C2, C3);
                idout=C1.length;
                for(int cout=0; cout<C3.length;cout++){
                    C3[cout].CalErroSaidaACC(Train[padrao][idout]);
                    //ErroMSE += C3[cout].GetErrosse();
                    idout++;                            
                }
                
                P2.CalcBackPropagationOut(C2, C3,CoefiAprendO);
                P1.CalcBackPropagationHide(C1, C2, CoefiAprendH);
                
            }//fecha ciclo de um padrao
            
            //P2.CalcBackPropagationOut(C2, C3, CoefiAprendO, NumPadroes);
            //P1.CalcBackPropagationHide(C1, C2, CoefiAprendH);
            ErroMSE=0;
            for(int pe=0;pe<C3.length;pe++){
                ErroMSE+=C3[pe].GetErroMSE();
                //System.out.println("ErroMSE= "+ErroMSE);
            }
            ErroMSE=ErroMSE/(C3.length*NumPadroes);
            //ErroMSE = ErroMSE/2;
            
            //System.out.println("Época = "+epocas+" ErroMSE = "+ErroMSE/2);
            //ErroMSE = ErroMSE/NumPadroes;
            //System.out.println("Época = "+epocas+" ErroMSEtr = "+ErroMSE);
            ErroMSE=CalcnetCV();
            //System.out.println("Época = "+epocas+" ErroMSECV = "+ErroMSE);
            VetErroMSE[epocas]=ErroMSE;
            if(ErroMSEmenor>ErroMSE){
               ErroMSEmenor=ErroMSE; epocamenor=epocas;
               System.out.println("Época = "+epocas+" ErroMSECV = "+ErroMSE);
               NomeRede="tempAnual/trab/rede_"+epocas+"_"+ErroMSEmenor+".txt";
               SalvaRede(NomeRede); SetMelhorRede(NomeRede);                 
            }
           // else { if(ErroMSEmenor*1.4<ErroMSE) {epocas=NumEpocas;} }
            
            
        }//fecha épocas
       try{ 
       FileWriter writer = new FileWriter(new File("MSEtestAnual.txt"));
       PrintWriter saida = new PrintWriter(writer);  
       String linhasaida="";
       saida.println("# Histórico do Erro MSE do conjunto de Teste");
       for(int loop=0;loop<NumEpocas;loop++){
         linhasaida=" epoca = "+loop+ ", MSEtest = "+VetErroMSE[loop];
         saida.println(linhasaida);
       }
       saida.close();
       writer.close();
       }catch (IOException x){} 
       System.out.println("Menor ErrroMSE = "+ErroMSEmenor+" Epoca = "+epocamenor);
    }
    
    public void Calcnet(){
        int idout=0; double ErroMSE =0;        
           
            for(int cin=0; cin<C3.length;cin++){
                    C3[cin].ResetErros();
                }
        
        for(int pad=0;pad<Train.length;pad++){
            for(int cin=0; cin<C1.length;cin++){
                    C1[cin].CalAtivacaoEntrada(Train[pad][cin]);
                }                        
                P1.CalcAtivacaoHide(C1, C2);
                P2.CalcAtivacaoOut(C2, C3);
                idout=C1.length;
                for(int cout=0; cout<C3.length;cout++){
                    C3[cout].CalErroSaidaACC(Train[pad][idout]);
                    //System.out.println("In0= "+Train[pad][0]+" In1= "+Train[pad][1]+"Desejada "+Train[pad][idout]+"obtida= "+C3[cout].GetSaida());
                    //System.out.println("Padrão= "+pad+" Desejada "+Train[pad][idout]+"obtida= "+C3[cout].GetSaida());
                    idout++;                            
                }
                 
        }//fecha ciclo de um padrão
        
        ErroMSE=0;
            for(int pe=0;pe<C3.length;pe++){
                ErroMSE+=C3[pe].GetErroMSE();
                //System.out.println("ErroMSE= "+ErroMSE);
            }
            //ErroMSE = ErroMSE/NumPadroes;             
            ErroMSE = ErroMSE/(Train.length*C3.length);        
            System.out.println(" ErroMSEavg = "+ErroMSE);
        
    }
    
     public double CalcnetCV(){
        int idout=0; double ErroMSE =0;        
           
            for(int cin=0; cin<C3.length;cin++){
                    C3[cin].ResetErros();
                }
        
        for(int pad=0;pad<CV.length;pad++){
            for(int cin=0; cin<C1.length;cin++){
                    C1[cin].CalAtivacaoEntrada(CV[pad][cin]);
                }                        
                P1.CalcAtivacaoHide(C1, C2);
                P2.CalcAtivacaoOut(C2, C3);
                idout=C1.length;
                for(int cout=0; cout<C3.length;cout++){
                    C3[cout].CalErroSaidaACC(CV[pad][idout]);                    
                    //System.out.println("Padrão= "+pad+" Desejada "+CV[pad][idout]+"obtida= "+C3[cout].GetSaida());
                    idout++;                            
                }
                 
        }//fecha ciclo de um padrão
        
        ErroMSE=0;
            for(int pe=0;pe<C3.length;pe++){
                ErroMSE+=C3[pe].GetErroMSE();
                //System.out.println("ErroMSE= "+ErroMSE);
            }
            //ErroMSE = ErroMSE/NumPadroes;         
            //ErroMSE = ErroMSE/CV.length;
            ErroMSE = ErroMSE/(CV.length*C3.length);
            //System.out.println(" ErroMSEavg = "+ErroMSE);
            return(ErroMSE);
        
    }
     
      public double CalcnetCVimp(){ 
        int idout=0; double ErroMSE =0,ErroPercent=0,ErroAcc=0;        
        //double[] ErroPercent= new double[C3.length];   
            for(int cin=0; cin<C3.length;cin++){
                    C3[cin].ResetErros();
                }
        try{
           String NomeArq = "tempAnualRMS/"+GetMelhorRede()+"_RMS.txt";
           FileWriter writer = new FileWriter(new File(NomeArq));
           PrintWriter saida = new PrintWriter(writer);  
           String linhasaida="";
           saida.println("# Erro MSE do conjunto de Teste");
           
        for(int pad=0;pad<CV.length;pad++){
            for(int cin=0; cin<C1.length;cin++){
                    C1[cin].CalAtivacaoEntrada(CV[pad][cin]);
                }                        
                P1.CalcAtivacaoHide(C1, C2);
                P2.CalcAtivacaoOut(C2, C3);
                idout=C1.length; ErroAcc=0;
                for(int cout=0; cout<C3.length;cout++){
                    C3[cout].CalErroSaidaACC(CV[pad][idout]);
                    linhasaida= "Padrão=, "+pad+" ,Desejada, "+CV[pad][idout]+", obtida=, "+C3[cout].GetSaida();
                    //System.out.println("Padrão=, "+pad+" ,Desejada, "+CV[pad][idout]+", obtida=, "+C3[cout].GetSaida());
                    System.out.println(linhasaida);   
                    saida.println(linhasaida);
                    ErroAcc+= Math.abs(CV[pad][idout]-C3[cout].GetSaida() ) / CV[pad][idout];
                    idout++;                            
                }
                ErroPercent+= ErroAcc/C3.length;                
                 
        }//fecha ciclo de um padrão
        ErroPercent = (ErroPercent/CV.length) * 100; 
        ErroMSE=0;
            for(int pe=0;pe<C3.length;pe++){
                ErroMSE+=C3[pe].GetErroMSE();
                //System.out.println("ErroMSE= "+ErroMSE);
            }
            //ErroMSE = ErroMSE/NumPadroes;         
            ErroMSE = ErroMSE/CV.length;
            //System.out.println(" ErroMSEavg = "+ErroMSE);
            linhasaida=" Erro Percentual Normalizado= "+ErroPercent+" %";
            saida.println(linhasaida);
            System.out.println(linhasaida);
            
        saida.close();
        writer.close();   
        }catch (IOException x){}     
            return(ErroMSE);
        
    }
      
      public void CalcnetCVimpExtend(int Numpad, int NumvarExtend){
        int idout=0; double ErroMSE =0,ErroPercent=0,ErroAcc=0;        
                   
        for(int pad=0;pad<CV.length;pad++){
            for(int cin=0; cin<C1.length;cin++){
                    C1[cin].CalAtivacaoEntrada(CV[pad][cin]);
                }                        
                P1.CalcAtivacaoHide(C1, C2);
                P2.CalcAtivacaoOut(C2, C3);
                if ((pad>=Numpad-1)&&(pad<CV.length-1) ){
                   CV[pad+1][NumvarExtend]= C3[0].GetSaida();     //  CV[pad][NumvarExtend+1];
                   CV[pad+1][NumvarExtend+1]= CV[pad][NumvarExtend];
                   //CV[pad][NumvarExtend+2]= C3[0].GetSaida();
                }
                
                idout=C1.length; ErroAcc=0;
                for(int cout=0; cout<C3.length;cout++){
                    //C3[cout].CalErroSaidaACC(CV[pad][idout]);                    
                    System.out.println("Padrão=, "+pad+" ,Desejada, "+CV[pad][idout]+", obtida=, "+C3[cout].GetSaida());
                    idout++;                            
                }
                
                 
        }//fecha ciclo de um padrão
        
        
    }
      
    public double [] CalcnetRecall(){
        
        int idout=0; double [] recall = new double[Train.length];
        for(int pad=0;pad<Train.length;pad++){
            for(int cin=0; cin<C1.length;cin++){
                    C1[cin].CalAtivacaoEntrada(Train[pad][cin]);
                }
                
                P1.CalcAtivacaoHide(C1, C2);
                P2.CalcAtivacaoOut(C2, C3);
                idout=C1.length;
                /*
                for(int cout=0; cout<C3.length;cout++){
       
                    System.out.println("Padrão= "+pad+" Desejada "+Train[pad][idout]+"obtida= "+C3[cout].GetSaida());
                    idout++;                            
                }
                 */
               
                recall[pad]=C3[0].GetSaida();
                
        }
        return(recall);
    }
    
}
