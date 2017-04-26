/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.rna;

import java.util.Vector;

/**
 *
 * @author Gabriel
 */
public class Treinamento {

    private Vector<CamadaIntermediaria> ci;
    private Neuronio saidaPrevista;
    private double saidaReal;
    private int nEntradas;
    private int nCamadas;
    private CamadaEntrada cEntrada;
    private double erro;
    private double n = 0.7;
    private static RNA rna;
    public static Vector<RNA> redes;
    public static Vector<Double> erros;

    public static RNA getRna() {
        return rna;
    }

    public static void setRna(RNA rna) {
        Treinamento.rna = rna;
    }

    public Treinamento(double[][] entradas, int nNeuroniosCamada[]) {
        redes = new Vector<RNA>();
        erros = new Vector<Double>();
        
        nEntradas = entradas.length;
        nCamadas = nNeuroniosCamada.length;
        ci = getNeuroniosCamada(nNeuroniosCamada);
        saidaPrevista = new Neuronio("saida");
        
        erro = 1;
        
        for (double[] ent : entradas) {
            saidaReal = ent[0];//1;
            cEntrada = getCamadaEntradaByEntradas(ent);
            montaRede();
            iniciaTreinamento();
        }
    }
    /*
    atribui os pesos iniciais
    Ao término da execução desta função a primeira camada já está pronta para retornar a função de ativação
     */
    public void montaRede() {
        // monta conexao entre as entradas e a primeira camada
        for (Neuronio n : ci.get(0).getVNeuronios()) {
            n.setNEntradas(cEntrada.getVEntradas());
        }
        //adiciona pesos
        for (int i = 0; i < ci.size(); i++) {
            for (int j = 0; j < ci.get(i).getVNeuronios().size(); j++) {
                if (i > 0) {
                    ci.get(i).getVNeuronios().get(j).setPesos(definiPesosAleatoriamente(ci.get(i).getVNeuronios().size()));
                } else {
                    ci.get(i).getVNeuronios().get(j).setPesos(definiPesosAleatoriamente(nEntradas));
                }
            }
        }
        saidaPrevista.setPesos(definiPesosAleatoriamente(ci.get(ci.lastIndexOf(ci.lastElement())).getVNeuronios().size()));

    }

    private Vector<Double> definePesosBackPropagation(Neuronio nn, Vector<Double> pesos) {

        Vector<Double> newPesos = new Vector<Double>();
        for (int i = 0; i < pesos.size(); i++) {
            newPesos.add(((nn.getnEntradas().get(i) * nn.getSigma() * n) * -1) + pesos.get(i));
        }
        return newPesos;
    }

    private Vector<Double> definiPesosAleatoriamente(int nEntradas) {
        Vector<Double> p = new Vector<Double>();
        for (int i = 0; i < nEntradas; i++) {
            p.add(Math.random() * 1);
        }
        return p;
    }

    private CamadaEntrada getCamadaEntradaByEntradas(double[] entradas) {
        CamadaEntrada ce = new CamadaEntrada();
        Vector<Double> vD = new Vector<Double>();
        for (int i = 0; i < entradas.length; i++) {
            vD.add(entradas[i]);
        }
        ce.setVEntradas(vD);
        return ce;
    }

    private Vector<CamadaIntermediaria> getNeuroniosCamada(int[] nNeuroniosCamada) {
        Vector<CamadaIntermediaria> cam = new Vector<CamadaIntermediaria>();
        Vector<Neuronio> vN;
        for (int i = 0; i < nNeuroniosCamada.length; i++) {
            CamadaIntermediaria camInter = new CamadaIntermediaria();
            camInter.setId(i);
            vN = new Vector<Neuronio>();
            for (int j = 0; j < nNeuroniosCamada[i]; j++) {
                Neuronio n = new Neuronio("C_" + i + "_N_" + j);
                vN.add(n);
            }
            camInter.setVNeuronios(vN);
            cam.add(camInter);
        }
        return cam;
    }

    public static void main(String[] args) {
//        Treinamento t = new Treinamento(new double[]{2, 3}, new int[]{4}, 1);
    }

    private void iniciaTreinamento() {
        int inter = 0;
        while (erro > 0.01 || inter < 300) {
            inter++;
            /* faz os calculos do net(X) e da função de ativação - sigmóide
             * tornando-os as novas entradas
             */
            Vector<Double> novasEntradas = new Vector();
            for (int i = 0; i < ci.size(); i++) {
                if (i < 1) {
                    for (int j = 0; j < ci.get(i).getVNeuronios().size(); j++) {
                        novasEntradas.add(ci.get(i).getVNeuronios().get(j).getFTranferencia());
                    }
                } else {
                    adicionaEntradas(ci.get(i), novasEntradas);
                    novasEntradas = new Vector();
                    for (int j = 0; j < ci.get(i).getVNeuronios().size(); j++) {
                        novasEntradas.add(ci.get(i).getVNeuronios().get(j).getFTranferencia());
                    }
                }
            }
            /*
             * Vai para a saída
             */
            saidaPrevista.setnEntradas(novasEntradas);
            erro = calculaErro(saidaPrevista.getFTranferencia(), saidaReal);
            /*
             * BackPropagation
             */
            /*
             * Calculo do sigma para todas as camadas
             */
            for (int i = 0; i < ci.size(); i++) {
                for (int j = 0; j < ci.get(i).getVNeuronios().size(); j++) {
                    double sigma;
                    sigma = (-1 * erro * Math.exp(-1 * ci.get(i).getVNeuronios().get(j).getNet())) / (1 + 2 * Math.exp(-1 * ci.get(i).getVNeuronios().get(j).getNet() + Math.exp(-2 * ci.get(i).getVNeuronios().get(j).getNet())));
                    ci.get(i).getVNeuronios().get(j).setSigma(sigma);
                }
            }

            //atualiza Pesos
            saidaPrevista.setPesos(definePesosBackPropagation(saidaPrevista, saidaPrevista.getPesos()));
            for (int i = 0; i < ci.size(); i++) {
                for (int j = 0; j < ci.get(i).getVNeuronios().size(); j++) {
                    ci.get(i).getVNeuronios().get(j).setPesos(definePesosBackPropagation(ci.get(i).getVNeuronios().get(j), ci.get(i).getVNeuronios().get(j).getPesos()));
                }
            }
        }

        rna = new RNA(cEntrada, ci, saidaPrevista);
        redes.add(rna);
        erros.add(erro);
    }

    private void adicionaEntradas(CamadaIntermediaria camadaInter, Vector<Double> novasEntradas) {
        for (int i = 0; i < camadaInter.getVNeuronios().size(); i++) {
            camadaInter.getVNeuronios().get(i).setNEntradas(novasEntradas);
        }
    }

    private double calculaErro(double fTranferencia, double saidaReal) {
        double e;
        e = (saidaReal - fTranferencia);
        return e;
    }

    public CamadaEntrada getCEntrada() {
        return cEntrada;
    }

    public void setCEntrada(CamadaEntrada cEntrada) {
        this.cEntrada = cEntrada;
    }

    public Vector<CamadaIntermediaria> getCi() {
        return ci;
    }

    public void setCi(Vector<CamadaIntermediaria> ci) {
        this.ci = ci;
    }

    public int getNCamadas() {
        return nCamadas;
    }

    public void setNCamadas(int nCamadas) {
        this.nCamadas = nCamadas;
    }

    public int getNEntradas() {
        return nEntradas;
    }

    public void setNEntradas(int nEntradas) {
        this.nEntradas = nEntradas;
    }
}
