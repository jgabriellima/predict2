/*
 * ThreadGeraPrevisao.java
 *
 * Created on 1 de Maio de 2006, 03:29
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package br.ufpa.lprad.predict.previsao.taxadecrescimento;

/**
 *
 * @author ramon
 */
public class ThreadGeraPrevisao extends Thread {
    
    /** Creates a new instance of ThreadGeraPrevisao */
    public ThreadGeraPrevisao() {}
    
    public void run(){
        new Previsao();            
    }     
}
