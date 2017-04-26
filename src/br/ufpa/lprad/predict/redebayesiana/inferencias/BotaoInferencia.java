/*
 * BotaoInferencia.java
 *
 * Created on 9 de Maio de 2005, 20:02
 */
package br.ufpa.lprad.predict.redebayesiana.inferencias;

import br.ufpa.lprad.predict.actions.Actions_Rede;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import javax.swing.JButton;

//import br.ufpa.predictbayes.gui.VisualizarRedeFrame;
public class BotaoInferencia extends JButton implements Serializable {

    //atributos da classe
    private int atributo;             // Qual indice do atributo que o botao representa
    private int faixa;                // Qual indice da faixa que o botao representa
    private boolean gerarRelInfer = false;
    private boolean status = false;     //define o status do bot Inferencia, se foi clicado ou nao
    private MouseListener sensor;     //define se houve ou nao evento sobre o botao de fx de atributos
    //referencia a outras classes
    private BotAtrib botAtrib;
    //constantes do botao
    private final int ALT_BOTAO = 14;
    private final int LAR_BOTAO = 20;
    private final int X_BOTAO = 5;

    /*metodo de instancia da classe*/
    public BotaoInferencia(int atr, int fx, BotAtrib botFaixa) {
        super();
        atributo = atr;
        faixa = fx;
        botAtrib = botFaixa;
        setName("teste atrib" + atr);
    }

    /*informações padroes de cada botao*/
    public void setInfoPadrao(int posicao) {
        super.setBounds(X_BOTAO, posicao, LAR_BOTAO, ALT_BOTAO);
    }

    public void atualizaActLst() {
        // Tratamento de excecao para a 1a vez onde nao ha Act Lst ainda
        try {
            removeMouseListener(sensor);
        } catch (Exception e) {
            //Do nothing            
        }
        sensor = new MouseListener() {
            //static boolean geraRelInfer = false;

            public void mouseClicked(MouseEvent e) {

                if (e.isMetaDown()) {
                    vai();
                    //se o botao de Inferencia jah foi inferenciado..desfazer inferencia
                    if (botAtrib.getJPBarValue(faixa)) {

//                        botAtrib.setColorInferAzul(atributo);
//                        botAtrib.desfazInfer(atributo);   
//                        
//                        gerarRelInfer = false;
                        vai();
                        desfaz();
                    }
                //fazer a inferência
                } else {
                    vai();
                    if (!botAtrib.getJPBarValue(faixa)) {
                        botAtrib.setColorInferLaranja(atributo);
                        botAtrib.fxInfer(faixa, atributo);
                        botAtrib.setJPBar(atributo, faixa);

                        gerarRelInfer = true;
                    }
                }
            }

            public void mouseExited(MouseEvent e) {
            }
            // Handles the event of the user pressing down the mouse button.

            public void mousePressed(MouseEvent e) {
            }
            // Handles the event of a user dragging the mouse while holding down the mouse button.

            public void mouseDragged(MouseEvent e) {
            }
            // Handles the event of a user releasing the mouse button.

            public void mouseReleased(MouseEvent e) {
            }
            // Definicao de metodos exigida por MouseListener e MouseMotionListerner

            public void mouseMoved(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }
        };
        this.addMouseListener(sensor);
    }

    public void fazInf() {
        vai();
        if (!botAtrib.getJPBarValue(faixa)) {
            botAtrib.setColorInferLaranja(atributo);
            botAtrib.fxInfer(faixa, atributo);
            botAtrib.setJPBar(atributo, faixa);

            gerarRelInfer = true;
        }
    }

    public void desfaz() {
        botAtrib.setColorInferAzul(atributo);
        botAtrib.desfazInfer(atributo);

        gerarRelInfer = false;
    }

    public boolean getGerarRelInfer() {
        return this.gerarRelInfer;
    }

    void vai() {
        Actions_Rede.setBotao(this);
    //  System.out.println("TESTEEEEEEEEEEEEEEEEEE");
    }
}
   

   