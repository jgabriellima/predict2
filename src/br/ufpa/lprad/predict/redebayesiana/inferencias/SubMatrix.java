/*
 * SubMatrix.java
 *
 * Created on 23 de Abril de 2005, 14:56
 */
package br.ufpa.lprad.predict.redebayesiana.inferencias;

import java.io.Serializable;
public class SubMatrix implements Serializable{
    
    double[][] matriz;
    
    /** Creates a new instance of SubMatriz */
    public SubMatrix(int coluna, int linha) {
        matriz = new double[coluna][linha];
    }
    
    public double getValor(int i, int j) {
        //System.out.println("matriz["+i+"]["+j+"]: "+matriz[i][j]);
       
        return matriz[i][j];
    }
    
    public void setValor(int i, int j, double x){
      //  System.out.println("Valor_pAssado_para_matriz["+i+"]["+j+"]: "+x);
        if(x==0)
            x=1;
        matriz[i][j] = x;
    }
    
    public void inc(int i, int j){
        matriz[i][j] = matriz[i][j] + 1;
    }
    
}