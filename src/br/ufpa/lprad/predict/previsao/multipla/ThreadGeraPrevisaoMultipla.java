package br.ufpa.lprad.predict.previsao.multipla;

/**
 *
 * @author ramon
 */
public class ThreadGeraPrevisaoMultipla extends Thread {
    
    /** Creates a new instance of ThreadGeraPrevisao */
    public ThreadGeraPrevisaoMultipla() {}
    
    public void run(){
        new PrevisaoMultipla();
    }     
}
