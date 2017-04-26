/*
 * LabelsAtrib.java
 *
 * Created on 28 de Abril de 2005, 18:09
 */

package br.ufpa.lprad.predict.redebayesiana.inferencias;
import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.text.*;
import java.text.NumberFormat;
import java.util.Locale;
 
public class LabelsFaixa extends JLabel implements Serializable{
    
    //atributos basicos de processamento
    private int atributo;       // Qual indice do atributo que a progressBar representa
    private int faixa;          // faixa q esta sendo trabalhada
    private String nome;
    private String valorFxInf;
    private String valorFxSup;
    private String nomeFaixa=null;
 
    // Constantes para layout
    private final int ALT_LABEL = 14; 
    private final int LAR_LABEL = 100;
    private final int X_LABEL = 181;
    private final int Y_INIT_LABEL = 2; 
    private final int DELAY = 2; 
    
    static NumberFormat nf;
    static Locale locale;    
    
    //referencias a outras classes
    private MetaDados meta;     // Referencias dos dados e atributos
    
    /** 
     * Creates a new instance of LabelsAtrib 
     */
    public LabelsFaixa(int atr, int fx, MetaDados md){        
        super();
        atributo = atr;
        faixa = fx;
        meta = md;      
        
        // Set the default locale to custom locale
        locale = new Locale("pt", "BR");
        Locale.setDefault(locale);
        nf = NumberFormat.getNumberInstance(locale);
    }
    
    public String setInfoPadrao(int posicao) {
        this.nomeFaixa = getNomeFAixa();
        super.setText(this.nomeFaixa);
        super.setForeground(Color.BLACK);      
        super.setBounds(X_LABEL,posicao, LAR_LABEL+this.nomeFaixa.length()+100, ALT_LABEL);   
        //System.out.println(">>>>>>>>>>>>>>>>>   "+this.nomeFaixa);
        return this.nomeFaixa;
    }   
    
     public String getNomeFAixa(){
         String nomeFaixaIni=null, 
                nomeFaixaFim = null, 
                nomeFaixa=null;        
        
        if(meta.ehDiscreto(atributo)==0){
            nomeFaixa = meta.getFaixa(atributo,faixa);
        }else{
             nf.setMaximumFractionDigits(2);  
             valorFxInf = nf.format(meta.getDiscretizar().getValInf(atributo,faixa));     
             valorFxSup = nf.format(meta.getDiscretizar().getValSup(atributo,faixa));           
             nomeFaixa = valorFxInf+ "  - " + valorFxSup;
             this.nomeFaixa = valorFxInf+ " - " + valorFxSup;
             
             
            /*DecimalFormat Converte1 = new DecimalFormat("0.###E0");           
            valorFxInf = String.valueOf(Converte1.format(meta.getDiscretizar().getValInf(atributo,faixa)));
            DecimalFormat Converte2 = new DecimalFormat("0.###E0");           
            valorFxSup = String.valueOf(Converte2.format(meta.getDiscretizar().getValSup(atributo,faixa)));
            nomeFaixaIni = String.valueOf(valorFxInf);
            nomeFaixaFim = String.valueOf(valorFxSup);            
            nomeFaixa = nomeFaixaIni+ "-" + nomeFaixaFim;   */
        }
        return (nomeFaixa);
    }
     
     public String getFaixa(){
         return nomeFaixa;
     }
}
