package br.ufpa.lprad.predict.redebayesiana.inferencias;

import br.ufpa.lprad.predict.redebayesiana.aprendizado.K2;
import br.ufpa.lprad.predict.redebayesiana.aprendizado.Vertice;
import br.ufpa.lprad.predict.redebayesiana.io.AbrirXML;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Vector;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/*
 * BotAtrib.java
 *
 * Created on 25 de Abril de 2005, 23:50
 */
public class BotAtrib extends JButton implements Serializable {

    //referencias a outras classes
    private FaixasAtrib[] faixas;
    private LabelsFaixa[] labelsFaixa;
    private LabelsProb[] labelsProb;
    public  BotaoInferencia[] botInfer;
    private Itens item;
    private MetaDados meta;     // Referencias dos dados e atributos
    private Dados dado;         // Conjunto de dados e probabilidades trabalhado
    private ProbabilidadeNo no;
    //atributos da classe
    private int[] colorInfer;
    private int atributo;       // Qual indice do atributo que o botao representa
    int fxInfer = -666;
    int atrInfer = -666;
    private String nome;
    public boolean status;     // True = clicado, False = livre
    private JPanel panel;
    private JPanel painelFaixas;
    private String nomeFaixa = "t";
    //constantes caracteristicas do Botao
    private final int ALT_BOTAO = 25;
    private final int LAR_BOTAO = 285;
    private final int X_BOTAO = 7;
    private final int Y_INIT_BOTAO = 17;
    private final int DELAY_BOTAO = 1;
    //constantes do painel que possui os atributos --> PanelAtrib
    private final int LAR_PANEL = 285;
    private final int X_PANEL = 7;
    private int ALT_PANEL;
    private int ALT_PROGBAR = 14;
    private int largPainelFx;
    private double[] prob;

    public BotAtrib(int atr, MetaDados md, Dados dd, JPanel p, Itens it) {
        super();
        atributo = atr;
        meta = md;
        dado = dd;
        panel = p;
        item = it;
        ALT_PANEL = (16 + DELAY_BOTAO) * (meta.getQntdFaixas(atributo));
        nome = meta.getAtributo(atr);
        colorInfer = new int[meta.getQntdAtrib()];
        createPanel();

    }

    /**
     * informacoes padroes de cada botao
     */
    public void setInfoPadrao(int largBotao) {
        super.setText(nome);
        super.setBounds(X_BOTAO, (Y_INIT_BOTAO + ((ALT_BOTAO + DELAY_BOTAO) * atributo)), largBotao, ALT_BOTAO);
    }

    /**
     * atualiza a posicao dos botoes, caso algum botao tenha sido clicado
     */
    public int atualiza(int inicial) {

        // Atualiza a propria posicao
        super.setLocation(super.getX(), inicial);
        int proximo = inicial + ALT_BOTAO + DELAY_BOTAO;
        int posBotao;

        // Se status = true... expanda as faixas
        if (status) {
            posBotao = displayFaixas(proximo) + DELAY_BOTAO;
            painelFaixas.setVisible(true);
            return posBotao;

        } else {
            painelFaixas.setVisible(false);
            return proximo;
        }
    }

    /**
     * retorna o nome do atributo
     */
    public String getNome() {
        return nome;
    }

    /**
     * define se o botao foi ou nao clicado --> se as abas estao expandidas ou nao 
     */
    public void click() {
        if (status) {
            status = false;
        } else {
            status = true;
        }
    }

    /**
     * metodo que faz a expansao do painel que possui as faixas dos atributos
     */
    public void fazExpansao() {
        if (status != true) {
            status = true;
        } else {
            status = true;
        }
    }

    /**
     * metodo que faz a retracao do painel que possui as faixas dos atributos
     */
    public void fazRetracao() {
        if (status != false) {
            status = false;
        } else {
            status = false;
        }
    }

    public void createPanel() {

        //painel que possui os atributos do Painel de Atributos;
        painelFaixas = new javax.swing.JPanel();
        painelFaixas.setLayout(null);
        painelFaixas.setVisible(false);

        faixas = new FaixasAtrib[meta.getQntdFaixas(atributo)];
        labelsFaixa = new LabelsFaixa[meta.getQntdFaixas(atributo)];
        labelsProb = new LabelsProb[meta.getQntdFaixas(atributo)];
        botInfer = new BotaoInferencia[meta.getQntdFaixas(atributo)];

        criaFx();
        panel.add(painelFaixas);
    }

    public void criaFx() {
        String nomeFaixa = "l";

        for (int i = 0; i < meta.getQntdFaixas(atributo); i++) {

            //Cria os botoes de inferencia das faixas dos atributos
            botInfer[i] = new BotaoInferencia(atributo, i, this);
            botInfer[i].setInfoPadrao((ALT_PROGBAR + 3) * i);
       
            painelFaixas.add(botInfer[i]);

            //Cria os JProgressBar
            faixas[i] = new FaixasAtrib(atributo, i, meta);
            faixas[i].setInfoPadrao((ALT_PROGBAR + 3) * i);
            painelFaixas.add(faixas[i]);

            //Cria os Labels das probabilidades das faixas dos atributos
            labelsProb[i] = new LabelsProb(atributo, i, meta);
            labelsProb[i].setInfoPadrao((ALT_PROGBAR + 3) * i);
            painelFaixas.add(labelsProb[i]);

            //Cria os Labels das faixas dos atributos
            labelsFaixa[i] = new LabelsFaixa(atributo, i, meta);
            nomeFaixa = labelsFaixa[i].setInfoPadrao((ALT_PROGBAR + 3) * i);

            painelFaixas.add(labelsFaixa[i]);

            faixas[i].setProbabilidade(labelsProb[i].getValorProb());

            if (nomeFaixa.length() > this.nomeFaixa.length()) {
                this.nomeFaixa = nomeFaixa;
            }
        }
        atualizaObj();
        this.largPainelFx = this.nomeFaixa.length() + LAR_PANEL + 50;
        painelFaixas.setSize(this.largPainelFx, ALT_PANEL);
    }

    public int displayFaixas(int proximo) {
        painelFaixas.setLocation(X_PANEL, proximo + 2);

        boolean gerarRelInfer;
        for (int i = 0; i < meta.getQntdFaixas(atributo); i++) {
            botInfer[i].atualizaActLst();
            faixas[i].setProbabilidade(labelsProb[i].getValorProb());
        }

        return (proximo + ALT_PANEL);
    }

    public void setJPBar(int atr, int fx) {
        for (int j = 0; j < meta.getQntdFaixas(atr); j++) {
            if (j == fx) {
                faixas[j].setValue(100);
                faixas[j].setForeground(Color.RED);
                faixas[j].updateUI();
            } else {
                faixas[j].setValue(0);
            }
        }
    }

    public boolean getJPBarValue(int fx) {
        if (faixas[fx].getValue() == 100) {
            return true;
        } else {
            return false;
        }
    }

    public void fxInfer(int fx, int atrib) {
        fxInfer = fx;
        atrInfer = atrib;
        item.atualizaInfer(atributo);
        item.fazAtualizacoes(atrib);
    }

    public void fxInfer(int fx, int atrib, int atribut) {
        fxInfer = fx;
        atrInfer = atrib;
        item.atualizaInfer(atribut);
        item.fazAtualizacoes(atrib);
    }

    public void atualizaObj() {
        prob = new double[meta.getQntdFaixas(atributo)];

//          System.out.println("meta.getQntdFaixas("+atributo+"): "+meta.getQntdFaixas(atributo));
        for (int i = 0; i < meta.getQntdFaixas(atributo); i++) {
            labelsFaixa[i].setInfoPadrao((ALT_PROGBAR + 3) * i);
            labelsProb[i].setInfoPadrao((ALT_PROGBAR + 3) * i);
            faixas[i].setInfoPadrao((ALT_PROGBAR + 3) * i);
            faixas[i].setProbabilidade(labelsProb[i].getValorProb());
            prob[i] = labelsProb[i].getProbabilidade();
        }
        gravaProbabilidade(atributo, prob);
    }

    // grava as probabilidades nos vertices da rede
    public void gravaProbabilidade(int atributo, double[] prob) {
        Vector vetor;
        try {
            vetor = K2.getRedeBayesiana().getVertices();
        } catch (NullPointerException ex) {
            vetor = AbrirXML.getRedeBaysiana().getVertices();
        }
        Vertice vertice = (Vertice) vetor.elementAt(atributo);
        vertice.setProbabilidade(prob);

    }

    public void desfazInfer(int atr) {
        item.atualizaInferencia(atributo);
        item.fazAtualizacoes(atributo);
    }

    public void setColorInferLaranja(int atr) {
        colorInfer[atr] = 1;
    }

    public void setColorInferAzul(int atr) {
        colorInfer[atr] = 0;
    }

    public void atualizaColorInfer() {
        for (int i = 0; i < meta.getQntdFaixas(atributo); i++) {
            if (colorInfer[atributo] == 1) {
                faixas[i].setForeground(Color.orange);
            } else {
                faixas[i].setForeground(Color.blue);
            }
        }
    }

    /**
     * retorna o valor da probabilidade de determinada faixa
     */
    public String getValorProb(int fx) {
        return faixas[fx].getProbabilidade();
    }

    public int getLargPainelFx() {
        return this.largPainelFx;
    }

    public String getNomeFaixa(int fx) {
        return labelsFaixa[fx].getFaixa();
    }

    //n sei se esse metodo eh usado....
    public String getNomeFaixa() {
        return this.nomeFaixa;
    }
}