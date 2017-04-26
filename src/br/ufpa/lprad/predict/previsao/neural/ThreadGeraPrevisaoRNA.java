package br.ufpa.lprad.predict.previsao.neural;

/**
 *
 * @author ramon
 */
public class ThreadGeraPrevisaoRNA extends Thread {
    
    /** Creates a new instance of ThreadGeraPrevisao */
    public ThreadGeraPrevisaoRNA() {}
    
    public void run(){
        new PrevisaoRNA();
    }     
}
