/*
 * NovoClass.java
 *
 * Created on 13 de Agosto de 2008, 15:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.ufpa.lprad.predict.redebayesiana.io;

import br.ufpa.lprad.predict.exception.PredictException;
import br.ufpa.lprad.predict.propriedades.PredictPropriedades;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


   
 public class VerificaBaseDados {  
   
     Vector vetorDatas;
      private FileInputStream input;
    private DataInputStream in;
       private int n_linhas = 0;
     public boolean foiModificado=false;
     
     public VerificaBaseDados(){
         
         lerDataEstadual();
       //  lerDataSocioEconomico();
         
     }
    public void lerDataEstadual()
    {
       
      
        try {
            
            vetorDatas =  new Vector();
            File arq = new File(PredictPropriedades.getCaminhoPlanilhaConsumo());
            if (arq.exists()) {
                //imprime a data
                DateFormat formatData = new SimpleDateFormat("dd/MM/yyyy");
                String data = formatData.format(new Date(arq.lastModified()));
                vetorDatas.add(data);
//                System.out.println(data+"\n");
                //imprime a data
                SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
                String hora = formatHora.format(new Date(arq.lastModified()));
                vetorDatas.add(hora);
//                System.out.println(hora+"\n");
            } else {
                System.out.println("arquivo '" + arq.getName() + "' nao encontrado");
            }
            try {
                this.foiModificado = lerHistoricoData(PredictPropriedades.getCaminhoArquivoDatasConsumo(), vetorDatas, 0);
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        } catch (PredictException ex) {
            ex.printStackTrace();
        }
       
    }
    public boolean lerHistoricoData(String str,Vector datas,int tipo) throws FileNotFoundException, IOException
    {
        boolean resultado;
        String leituraHistorico = null;
        String linha;
         input = new FileInputStream(str);
        in = new DataInputStream(input);
          while((linha= proxLinha()) != null){    
           
            n_linhas++;  
        }        
        in.close();
        input = new FileInputStream(str);
        in = new DataInputStream(input);
        
        for(int i=0; i<n_linhas;i++)
        {
            leituraHistorico=proxLinha();
        }
        
        
        StringBuffer data = new StringBuffer();
        for(int i=0; i<datas.size(); i++)
            data.append((String)datas.elementAt(i));
        
        String dataF = data.toString();
       // System.out.println("tipo: "+tipo);
       // System.out.println("1- "+data);
      //  System.out.println("2- "+leituraHistorico);
        try{
        if(leituraHistorico.equalsIgnoreCase(dataF)){
          //   System.out.println("Tá Igual POrra");
             resultado=true;
        }else{
            resultado=false;
            gravaArquivo(tipo);
          //   System.out.println("FOI MODIFICADO");
        }
        }catch(Exception ex){
        
            resultado=false;
             gravaArquivo(tipo);
        }
        
        return resultado;
    }
    public String proxLinha() 
    {
        try {
            return in.readLine();
        } catch (Exception e) {
            return null;
        }
    }
//    public void lerDataSocioEconomico()
//    {
//         vetorDatas = new Vector();
//       File arq = new File(PredictBayesConstants.DIR_SOCIO_ECON);
//
//       if (arq.exists()) {
//          //imprime a data
//          DateFormat formatData = new SimpleDateFormat("dd/MM/yyyy");
//          String data = formatData.format(new Date(arq.lastModified()));
//          vetorDatas.add(data);
//        // System.out.println(data);
//
//          //imprime a data
//          SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
//          String hora = formatHora.format(new Date(arq.lastModified()));
//          vetorDatas.add(hora);
//          //System.out.println(hora);
//
//       }
//       else
//          System.out.println("arquivo '" + arq.getName() + "' nao encontrado");
//
//        try {
//
//           this.foiModificado =  lerHistoricoData(PredictBayesConstants.DIR_SOCIO_ECON_DATAS, vetorDatas,1);
//        } catch (FileNotFoundException ex) {
//            ex.printStackTrace();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//
//
//    }
    
    public void gravaArquivo(int tipo){
        FileWriter grava = null;
        String strTipo = null;
        try {
            switch(tipo){
                case 0: strTipo=PredictPropriedades.getCaminhoArquivoDatasConsumo();
                break;
                case 1: strTipo=PredictPropriedades.getCaminhoArquivoDatasConsumo();
            }
            grava = new FileWriter(strTipo);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        for(int i=0; i<vetorDatas.size(); i++)
        {
            
            try {
                grava.write((String)vetorDatas.elementAt(i));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        
        
            }
        try {
            grava.close();
            System.out.println("Arquivo gravado com sucesso");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    public static void main(String[] args) {
        new VerificaBaseDados();
    }
   
 } 