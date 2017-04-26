/*
 * RedeMLP.java
 *
 * Created on December 1, 2005, 2:06 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */
package br.ufpa.lprad.predict.previsao.rna;

import br.ufpa.lprad.predict.gui.window.previsao.TreinamentoRNA;
import java.util.*;
import java.io.*;
import java.lang.*;

/**
 *
 * @author FADESP
 */
public class RedeMLP {

    double CoefiAprendH, CoefiAprendO;
    double[][] Train, CV;
    int NumPadroes, NumEpocas;
    String MelhorRede;
    String Dir = "tempredes/";
    Neuron[] C1, C2, C3;
    Peso P1, P2;
    public static String path;

    /** Creates a new instance of RedeMLP */
    //public RedeMLP(int NIn,int NHide,int NOut,int NPadroes, int NEpocas, double CAprendH, double CAprendO) {
    public RedeMLP(String path, int NIn, int NHide, int NOut, int NEpocas, double CAprendH, double CAprendO) {
        NumEpocas = NEpocas;
        MelhorRede = null; //NumPadroes=NPadroes;
        CoefiAprendH = CAprendH;
        CoefiAprendO = CAprendO;
        this.Dir = path + "\\tempredes\\";
        RedeMLP.path = path;
        //Train = new double[NPadroes][NIn+NOut];
        C1 = new Neuron[NIn];
        for (int x = 0; x < C1.length; x++) {
            C1[x] = new Neuron();
        }
        C2 = new Neuron[NHide];
        for (int x = 0; x < C2.length; x++) {
            C2[x] = new Neuron();
        }
        C3 = new Neuron[NOut];
        for (int x = 0; x < C3.length; x++) {
            C3[x] = new Neuron();
        }
        P1 = new Peso(NIn, NHide, true);
        P2 = new Peso(NHide, NOut, true);
    }

    public RedeMLP(String Nomearq) {
        String linha = null;
        this.Dir = path + "\\tempredes\\";
        try {
            SetMelhorRede(Nomearq);
            FileReader reader = new FileReader(new File(Nomearq));
            BufferedReader leitor = new BufferedReader(reader);
            StringTokenizer tok;
            linha = leitor.readLine();//linha de cabeçalho
            linha = leitor.readLine();
            tok = new StringTokenizer(linha, ",");

            C1 = new Neuron[Integer.valueOf(tok.nextToken())];//entrada
            System.out.println("C1 tam= " + C1.length);
            C2 = new Neuron[Integer.valueOf(tok.nextToken())];//Hide
            C3 = new Neuron[Integer.valueOf(tok.nextToken())];//Saída
            for (int x = 0; x < C1.length; x++) {
                C1[x] = new Neuron();
            }
            for (int x = 0; x < C2.length; x++) {
                C2[x] = new Neuron();
            }
            for (int x = 0; x < C3.length; x++) {
                C3[x] = new Neuron();
            }
            P1 = new Peso(C1.length, C2.length, false);
            P2 = new Peso(C2.length, C3.length, false);

            linha = leitor.readLine();//Comentários

            for (int lin = 0; lin < C1.length; lin++) {
                linha = leitor.readLine();
                tok = new StringTokenizer(linha, ",");
                for (int col = 0; col < C2.length; col++) {
                    //Train[idlinha][col]= Double.valueOf(tok.nextToken()).doubleValue();
                    P1.SetPeso(lin, col, Double.valueOf(tok.nextToken()).doubleValue());
                }

            }

            linha = leitor.readLine();//Comentários
            for (int lin = 0; lin < C2.length; lin++) {
                linha = leitor.readLine();
                tok = new StringTokenizer(linha, ",");
                for (int col = 0; col < C3.length; col++) {
                    //Train[idlinha][col]= Double.valueOf(tok.nextToken()).doubleValue();
                    P2.SetPeso(lin, col, Double.valueOf(tok.nextToken()).doubleValue());
                }

            }

            leitor.close();
            reader.close();


        } catch (IOException x) {
        }
    }

    public void CarregaArqTreino(double[][] Nometreino) {

        // Train = new double[Integer.valueOf(tok.nextToken())][Integer.valueOf(tok.nextToken())];
        Train = Nometreino;
        NumPadroes = Train.length;

    }

    public void CarregaArqTreinoOLD(String Nometreino) {

        //double [][] entrada;
        String linha = null;
        try {
            FileReader reader = new FileReader(new File(Nometreino));
            BufferedReader leitor = new BufferedReader(reader);
            StringTokenizer tok;
            linha = leitor.readLine();
            tok = new StringTokenizer(linha, " ");

            Train = new double[Integer.valueOf(tok.nextToken())][Integer.valueOf(tok.nextToken())];
            //System.out.println(entrada.length+" "+entrada[0].length);
            NumPadroes = Train.length;

            int idlinha = 0;
            while ((linha = leitor.readLine()) != null) {
                tok = new StringTokenizer(linha, "\t");
                for (int col = 0; col < Train[0].length; col++) {
                    Train[idlinha][col] = Double.valueOf(tok.nextToken()).doubleValue();

                }

                //System.out.println("linha "+idlinha);
                idlinha++;

            }

            leitor.close();
            reader.close();

        } catch (IOException x) {
        }

    }

    public void CarregaArqCV(double[][] NomeCV) {

        //CV = new double[Integer.valueOf(tok.nextToken())][Integer.valueOf(tok.nextToken())];
        CV = NomeCV;

    }

    public void CarregaArqCVOLD(String NomeCV) {

        //double [][] entrada;
        String linha = null;
        try {
            FileReader reader = new FileReader(new File(NomeCV));
            BufferedReader leitor = new BufferedReader(reader);
            StringTokenizer tok;
            linha = leitor.readLine();
            tok = new StringTokenizer(linha, " ");

            CV = new double[Integer.valueOf(tok.nextToken())][Integer.valueOf(tok.nextToken())];
            System.out.println(CV.length + " " + CV[0].length);

            int idlinha = 0;
            while ((linha = leitor.readLine()) != null) {
                tok = new StringTokenizer(linha, "\t");
                for (int col = 0; col < CV[0].length; col++) {
                    //System.out.println(col);
                    CV[idlinha][col] = Double.valueOf(tok.nextToken()).doubleValue();

                }

                //System.out.println("linha "+idlinha);
                idlinha++;

            }

            leitor.close();
            reader.close();

        } catch (IOException x) {
        }

    }

    public void CarregaRecall(double[][] recall) {
        Train = recall;

    }

    public void SalvaRede(String Nomerede) {

        try {
            FileWriter writer = new FileWriter(new File(Nomerede));
            PrintWriter saida = new PrintWriter(writer);
            String linhasaida = "";
            saida.println("# Pesos da Rede Treinada " + "TxH=  " + CoefiAprendH + " TxOut=" + CoefiAprendO);
            saida.println(C1.length + "," + C2.length + "," + C3.length);
            //pesos da camada hide e saída
            saida.println(P1.GetNumLinha() + "," + P1.GetNumColuna());
            for (int x = 0; x < P1.GetNumLinha(); x++) {

                for (int y = 0; y < P1.GetNumColuna(); y++) {

                    linhasaida = linhasaida + P1.GetPeso(x, y) + ",";

                }
                saida.println(linhasaida);
                linhasaida = "";
            }
            //pesos da camada hide e saída
            saida.println(P2.GetNumLinha() + "," + P2.GetNumColuna());
            linhasaida = "";
            for (int x = 0; x < P2.GetNumLinha(); x++) {

                for (int y = 0; y < P2.GetNumColuna(); y++) {

                    linhasaida = linhasaida + P2.GetPeso(x, y) + ",";

                }
                saida.println(linhasaida);
                linhasaida = "";

            }


            saida.close();
            writer.close();
        } catch (IOException x) {
        }

    }

    public void SetMelhorRede(String Nomearq) {
        MelhorRede = Nomearq;
    }

    public String GetMelhorRede() {
        return (MelhorRede);
    }

    public void Treinamento() {
        double ErroMSE = 0, ErroMSEmenor = 100.0;
        int idout = 0, epocamenor = 0;
        String NomeRede = null;
        double[] VetErroMSE = new double[NumEpocas];
        for (int epocas = 0; epocas < NumEpocas; epocas++) {

            for (int cin = 0; cin < C2.length; cin++) {
                C2[cin].ResetErros();
            }
            for (int cin = 0; cin < C3.length; cin++) {
                C3[cin].ResetErros();
            }

            for (int padrao = 0; padrao < NumPadroes; padrao++) {
                //selecionar um padrao aleatoriamente
                for (int cin = 0; cin < C1.length; cin++) {
                    C1[cin].CalAtivacaoEntrada(Train[padrao][cin]);
                }

                P1.CalcAtivacaoHide(C1, C2);
                P2.CalcAtivacaoOut(C2, C3);
                idout = C1.length;
                for (int cout = 0; cout < C3.length; cout++) {
                    C3[cout].CalErroSaidaACC(Train[padrao][idout]);
                    //ErroMSE += C3[cout].GetErrosse();
                    idout++;
                }

                P2.CalcBackPropagationOut(C2, C3, CoefiAprendO);
                P1.CalcBackPropagationHide(C1, C2, CoefiAprendH);

            }//fecha ciclo de um padrao


            ErroMSE = 0;
            for (int pe = 0; pe < C3.length; pe++) {
                ErroMSE += C3[pe].GetErroMSE();
            //System.out.println("ErroMSE= "+ErroMSE);
            }


            //System.out.println("Época = "+epocas+" ErroMSE = "+ErroMSE/2);
            ErroMSE = ErroMSE / NumPadroes;
            //System.out.println("Época = "+epocas+" ErroMSEtr = "+ErroMSE);
            ErroMSE = CalcnetCV();
            //System.out.println("Época = "+epocas+" ErroMSECV = "+ErroMSE);
            VetErroMSE[epocas] = ErroMSE;
            if (ErroMSEmenor > ErroMSE) {
                ErroMSEmenor = ErroMSE;
                epocamenor = epocas;
                //System.out.println("Época = "+epocas+" ErroMSECV = "+ErroMSE);

                NomeRede = Dir + "MelhorRede_" + C2.length + "_" + CoefiAprendH + "_" + CoefiAprendO + ".txt";
//                System.out.println("NomeRede: "+NomeRede);
                SalvaRede(NomeRede);
                SetMelhorRede(NomeRede);
            //System.out.println("Melhor rede = "+NomeRede);

            }
        // else { if(ErroMSEmenor*1.4<ErroMSE) {epocas=NumEpocas;} }


        }//fecha épocas

    }

    public void Calcnet() {
        int idout = 0;
        double ErroMSE = 0;

        for (int cin = 0; cin < C3.length; cin++) {
            C3[cin].ResetErros();
        }

        for (int pad = 0; pad < Train.length; pad++) {
            for (int cin = 0; cin < C1.length; cin++) {
                C1[cin].CalAtivacaoEntrada(Train[pad][cin]);
            }
            P1.CalcAtivacaoHide(C1, C2);
            P2.CalcAtivacaoOut(C2, C3);
            idout = C1.length;
            for (int cout = 0; cout < C3.length; cout++) {
                C3[cout].CalErroSaidaACC(Train[pad][idout]);
                //System.out.println("In0= "+Train[pad][0]+" In1= "+Train[pad][1]+"Desejada "+Train[pad][idout]+"obtida= "+C3[cout].GetSaida());
                //System.out.println("Padrão= "+pad+" Desejada "+Train[pad][idout]+"obtida= "+C3[cout].GetSaida());
                idout++;
            }

        }//fecha ciclo de um padrão

        ErroMSE = 0;
        for (int pe = 0; pe < C3.length; pe++) {
            ErroMSE += C3[pe].GetErroMSE();
        //System.out.println("ErroMSE= "+ErroMSE);
        }
        //ErroMSE = ErroMSE/NumPadroes;
        //ErroMSE = ErroMSE/Train.length;
        ErroMSE = ErroMSE / (Train.length * C3.length);
        TreinamentoRNA.consoleTreinamento.append("\nResultado da melhor RNA = " + ErroMSE);
        System.out.println(" ErroMSEavg = " + ErroMSE);

    }

    public double CalcnetCV() {
        int idout = 0;
        double ErroMSE = 0;

        for (int cin = 0; cin < C3.length; cin++) {
            C3[cin].ResetErros();
        }

        for (int pad = 0; pad < CV.length; pad++) {
            for (int cin = 0; cin < C1.length; cin++) {
                C1[cin].CalAtivacaoEntrada(CV[pad][cin]);
            }
            P1.CalcAtivacaoHide(C1, C2);
            P2.CalcAtivacaoOut(C2, C3);
            idout = C1.length;
            for (int cout = 0; cout < C3.length; cout++) {
                C3[cout].CalErroSaidaACC(CV[pad][idout]);
                //System.out.println("Padrão= "+pad+" Desejada "+CV[pad][idout]+"obtida= "+C3[cout].GetSaida());
                idout++;
            }

        }//fecha ciclo de um padrão

        ErroMSE = 0;
        for (int pe = 0; pe < C3.length; pe++) {
            ErroMSE += C3[pe].GetErroMSE();
        //System.out.println("ErroMSE= "+ErroMSE);
        }
        //ErroMSE = ErroMSE/NumPadroes;
        ErroMSE = ErroMSE / CV.length;
        //System.out.println(" ErroMSEavg = "+ErroMSE);
        return (ErroMSE);

    }

    public double[] Previsao(double[] dados) {
        double[] saida = new double[C3.length];
        if (dados.length != C1.length) {
            System.out.println("Incompatibilidade entre RNA e Dados de Entrada!");
            System.exit(0);
        }
        for (int cin = 0; cin < C3.length; cin++) {
            C3[cin].ResetErros();
        }
        for (int cin = 0; cin < C1.length; cin++) {
            C1[cin].CalAtivacaoEntrada(dados[cin]);
        }
        P1.CalcAtivacaoHide(C1, C2);
        P2.CalcAtivacaoOut(C2, C3);
        for (int cout = 0; cout < C3.length; cout++) {
            saida[cout] = C3[cout].GetSaida();
        }

        return (saida);

    }

    public double CalcnetCVimp() {
        int idout = 0;
        double ErroMSE = 0, ErroPercent = 0, ErroAcc = 0;

        for (int cin = 0; cin < C3.length; cin++) {
            C3[cin].ResetErros();
        }
        try {
            //String NomeArq = "tempConsumo/"+GetMelhorRede()+"_RMS.txt";
            String NomeArq = GetMelhorRede() + "_RMS.txt";
            System.out.println(NomeArq);
            FileWriter writer = new FileWriter(new File(NomeArq));
            PrintWriter saida = new PrintWriter(writer);
            String linhasaida = "";
            saida.println("# Erro MSE do conjunto de Teste");


            for (int pad = 0; pad < CV.length; pad++) {
                for (int cin = 0; cin < C1.length; cin++) {
                    C1[cin].CalAtivacaoEntrada(CV[pad][cin]);
                }
                P1.CalcAtivacaoHide(C1, C2);
                P2.CalcAtivacaoOut(C2, C3);
                idout = C1.length;
                ErroAcc = 0;
                for (int cout = 0; cout < C3.length; cout++) {
                    C3[cout].CalErroSaidaACC(CV[pad][idout]);
                    linhasaida = "Padrão=, " + pad + " ,Desejada, " + CV[pad][idout] + ", obtida=, " + C3[cout].GetSaida();

                    //System.out.println(linhasaida);
                    saida.println(linhasaida);
                    ErroAcc += Math.abs(CV[pad][idout] - C3[cout].GetSaida()) / CV[pad][idout];
                    idout++;
                }
                ErroPercent += ErroAcc / C3.length;

            }//fecha ciclo de um padrão
            ErroPercent = (ErroPercent / CV.length) * 100;
            ErroMSE = 0;
            for (int pe = 0; pe < C3.length; pe++) {
                ErroMSE += C3[pe].GetErroMSE();
            //System.out.println("ErroMSE= "+ErroMSE);
            }
            //ErroMSE = ErroMSE/NumPadroes;         
            ErroMSE = ErroMSE / CV.length;
            //System.out.println(" ErroMSEavg = "+ErroMSE);
            linhasaida = " Erro Percentual Normalizado= " + ErroPercent + " %";
            saida.println(linhasaida);
            System.out.println(linhasaida);
            //System.out.println(" Erro Percentual Normalizado= "+ErroPercent+" %");
            saida.close();
            writer.close();
        } catch (IOException x) {
        }

        //return(ErroMSE);
        return (ErroPercent);

    }

    public double[] CalcnetRecall() {

        int idout = 0;
        double[] recall = new double[Train.length];
        for (int pad = 0; pad < Train.length; pad++) {
            for (int cin = 0; cin < C1.length; cin++) {
                C1[cin].CalAtivacaoEntrada(Train[pad][cin]);
            }

            P1.CalcAtivacaoHide(C1, C2);
            P2.CalcAtivacaoOut(C2, C3);
            idout = C1.length;
            /*
            for(int cout=0; cout<C3.length;cout++){

            System.out.println("Padrão= "+pad+" Desejada "+Train[pad][idout]+"obtida= "+C3[cout].GetSaida());
            idout++;
            }
             */

            recall[pad] = C3[0].GetSaida();

        }
        return (recall);
    }
}
