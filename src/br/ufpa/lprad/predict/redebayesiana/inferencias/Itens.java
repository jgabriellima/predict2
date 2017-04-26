/*
 * Itens.java
 *
 * Created on 26 de Abril de 2005, 10:05
 */
package br.ufpa.lprad.predict.redebayesiana.inferencias;

import br.ufpa.lprad.predict.gui.window.correlacao.FrameRelatorioInferencias;
import br.ufpa.lprad.predict.gui.window.backstage.GerenciadorDesktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.Serializable;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

public class Itens extends JPanel implements Serializable {

    private static String corrigeCofificacao(String nome) {

        String aux = "";
        for (int i = 0; i < nome.length(); i++) {
            if (nome.charAt(i) == 'á') {
                aux += "&aacute;";
            } else if (nome.charAt(i) == 'ã') {
                aux += "&atilde;";
            } else if (nome.charAt(i) == 'ç') {
                aux += "&ccedil;";
            } else if (nome.charAt(i) == 'ú') {
                aux += "&uacute;";
            } else {
                aux += nome.charAt(i);
            }
        }
        return aux;

    }

    // Constantes para layout
    private final int INICIO_Y = 17;
    // Referencias
    public static MetaDados meta;
    public static Dados dado;
    public static JPanel panel;
    private JPanel panelGraf;
    public static ProbabilidadeNo no;
    public static BotAtrib[] botoes;
    private FrameRelatorioInferencias frameInfer;
    private static int largPainelFx;
    public static String textoInferencia;
    public static String textoNaoInferencia = "";
    boolean flag = false; //flag para definir o texto de inferências
    //scrollbar
    JScrollBar hbarI;
    JScrollBar vbarI;

    public static JPanel getPanelInfo() {
        panel.setSize(largPainelFx, 500);
        return panel;
    }

    /** Creates a new instance of Itens */
    //public Itens(MetaDados md, Dados dd, JPanel pn, Container org) {
    public Itens(MetaDados md, Dados dd, JPanel pn, JPanel jpGraf) {

        meta = md;
        dado = dd;
        panel = pn;
        panelGraf = jpGraf;

        botoes = new BotAtrib[meta.getQntdAtrib()];

        this.frameInfer = new FrameRelatorioInferencias();
        this.frameInfer.setVisible(false);
        panel.setLayout(null);
        // Instancia botoes e os adiciona no JPanel
        criaBotAtribs(panel);

        // Zera informacoes dos botoes
        for (int i = 0; i < meta.getQntdAtrib(); i++) {
            botoes[i].setInfoPadrao(this.largPainelFx);
        }
    }

    /** 
     * cria os botoes 
     */
    public void criaBotAtribs(JPanel panel) {
        for (int i = 0; i < meta.getQntdAtrib(); i++) {
            botoes[i] = new BotAtrib(i, meta, dado, panel, this);
            botoes[i].addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    for (int i = 0; i < meta.getQntdAtrib(); i++) {
                        if (evt.getSource() == botoes[i]) {
                            botoes[i].click();
                            atualizaItens();
                        }
                    }
                }
            });
            panel.add(botoes[i]);
        }

        for (int i = 0; i < meta.getQntdAtrib(); i++) {
            if (this.largPainelFx < botoes[i].getLargPainelFx()) {
                this.largPainelFx = botoes[i].getLargPainelFx();
            }
        }
    }

    public int getLargPainelFx() {
        return this.largPainelFx;
    }

    // **** Classe Necessaria para as Barras de Rolagem ****
    class MyAdjustmentListener implements AdjustmentListener {

        public void adjustmentValueChanged(AdjustmentEvent e) {
            repaint();
        }
    }
    // *****************************************************    

    public void atualizaItens() {
        int ultima_pos = INICIO_Y;

        for (int i = 0; i < meta.getQntdAtrib(); i++) {
            ultima_pos = botoes[i].atualiza(ultima_pos + 2);
        }
    }

    public void atualizaInfer(int atrib) {
//        System.out.println("****************************************************************************: "+atrib);
        boolean infer;
        meta.setNumInfer();
        meta.defineInfer(botoes[atrib].atrInfer, botoes[atrib].fxInfer);
        no = new ProbabilidadeNo(meta.getAtrInfer(), meta.getFxInfer(), meta, dado, meta.getNumInfer());

    }

    public /*void*/ static String textoInfer() {
        int[] atrInfer = meta.getAtrInfer();
        int[] fxInfer = meta.getFxInfer();
        boolean[] atrNaoInferenciados = new boolean[atrInfer.length];
        boolean flag = true;
        String txtInfer1;
        String txtInfer2 = "";
        String textoNaoInferenciaFinal = "";
        textoInferencia = "";

        if (meta.getNumInfer() > 1) {
            txtInfer1 = "<br><br><strong>Se a vari&aacute;vel " + corrigeCofificacao(botoes[atrInfer[0]].getNome()) +
                    " estiver na faixa <strong>(" + botoes[atrInfer[0]].getNomeFaixa(fxInfer[0]) + ")</strong>";

            for (int j = 1; j < meta.getNumInfer(); j++) {
                textoInferencia = textoInferencia + " e a vari&aacute;vel " + corrigeCofificacao(botoes[atrInfer[j]].getNome()) + " estiver na faixa <strong>(" + botoes[atrInfer[j]].getNomeFaixa(fxInfer[j]) + ")</strong><br><br>";
            }
            textoInferencia = txtInfer1 + textoInferencia;
            textoInferencia += "</strong>";
        } else if (meta.getNumInfer() == 0) {
            textoInferencia = "";
            textoInferencia += "</strong>";
        //se tem apenas 1 atributo inferenciado
        } else {
            textoInferencia = "Se a vari&aacute;vel " + corrigeCofificacao(botoes[atrInfer[0]].getNome()) +
                    " estiver na faixa <strong>(" + botoes[atrInfer[0]].getNomeFaixa(botoes[atrInfer[0]].fxInfer) + ")</strong><br>";
        }
        if (meta.getNumInfer() > 0) {
            for (int i = 0; i < meta.getQntdAtrib(); i++) {
                flag = true;
                for (int z = 0; z < meta.getNumInfer(); z++) {
                    if (atrInfer[z] == i) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    atrNaoInferenciados[i] = flag;
                }
            }

            for (int i = 0; i < meta.getQntdAtrib(); i++) {
                if (atrNaoInferenciados[i]) {
                    txtInfer2 = "";
                    textoNaoInferencia = "<br><br>A probabilidade da vari&aacute;vel " + corrigeCofificacao(botoes[i].getNome()) + " estar:<br>";
                    for (int j = 0; j < meta.getQntdFaixas(i); j++) {
                        txtInfer2 = txtInfer2 + " na faixa <strong>(" + botoes[i].getNomeFaixa(j) + ")</strong> &eacute; de <strong>" + botoes[i].getValorProb(j) + "</strong>;<br>";
                    }
                    textoNaoInferencia = textoNaoInferencia + txtInfer2;
                    textoNaoInferenciaFinal = textoNaoInferenciaFinal + textoNaoInferencia;
                }
            }
            textoInferencia = "" + textoInferencia + "" + "" + textoNaoInferenciaFinal + "";

//            this.frameInfer = new FrameRelatorioInferencias();
//            this.frameInfer.setVisible(Actions_Rede.vizualiza_relatorio);//true);
//            this.frameInfer.setTextoInferencia(this.textoInferencia);
//            GerenciadorDesktop.add(this.frameInfer);
//            this.frameInfer.toFront();

        } else {
            // System.out.println("desfez inferrrrrr");
            textoInferencia = "";
//            this.frameInfer.setTextoInferencia(textoInferencia);
//            this.frameInfer.setVisible(false);
//            GerenciadorDesktop.remove(this.frameInfer);

        }
        return textoInferencia;

    }

    public void paintFrameRel() {
        GerenciadorDesktop.add(this.frameInfer);
    }

    /**
     * metodo que desfaz a inferencia
     */
    public void atualizaInferencia(int atrib) {
        meta.desfazInfer(botoes[atrib].atrInfer);
        fazInfer();
    }

    public void fazInfer() {
        no = new ProbabilidadeNo(meta.getAtrInfer(), meta.getFxInfer(), meta, dado, meta.getNumInfer());
        atualizaItens();
    }

    public void fazAtualizacoes(int atrib) {


        for (int i = 0; i < meta.getQntdAtrib(); i++) {
            botoes[i].atualizaObj();
            botoes[i].atualizaColorInfer();
        }

        textoInfer();

    }

    public void setExpansao() {
        for (int i = 0; i < meta.getQntdAtrib(); i++) {
            botoes[i].fazExpansao();
            atualizaItens();
        }
    }

    public void setTextoInfer(String txtInfer) {
        textoInferencia = txtInfer;
    }

    public void setRetracao() {
        for (int i = 0; i < meta.getQntdAtrib(); i++) {
            botoes[i].fazRetracao();
            atualizaItens();
        }
    }
}