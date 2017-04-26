/*
 * RNA.java
 *
 * Created on September 27, 2006, 8:59 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */
package br.ufpa.lprad.predict.previsao.rna;

import br.ufpa.lprad.predict.gui.window.previsao.TreinamentoRNA;
import java.awt.Desktop;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.util.ArrayList;

/**
 *
 * @author FADESP
 */
public class RNA {

    String NomeArquivo;
    String Dir = "tempResult/", Dirdados = "Dados/Execucao/";
    int AnoInicial, AnoLimiar, AnoFinal, MesFinal;
    int NumEntradas = 12;
    int NumSaidas = 12;
    int LagMeses = 12;
    public static double FatorNormalizacao;
    double[][] matriz, treino, teste;
    public static String path;

    /** Creates a new instance of RNA */
    public RNA(String path, String NomeConsumo, int anoInicial, int anoLimiar, int anoFinal, int mesFinal) {
        NomeArquivo = NomeConsumo;
        AnoInicial = anoInicial;
        AnoLimiar = anoLimiar;
        AnoFinal = anoFinal;
        MesFinal = mesFinal;
        FatorNormalizacao = 0;
        RNA.path = path;
        this.Dir = path + "\\tempResult\\";
        this.Dirdados = path + "\\Execucao\\";

        matriz = new double[12][((AnoFinal - AnoInicial) + 1)];
        //matriz = new double[12][((AnoFinal - AnoInicial) + 1)];
        //System.out.println("Matriz Tam Anos = "+matriz[0].length);
        try {

            FileReader reader = new FileReader(new File(NomeArquivo));
            BufferedReader leitor = new BufferedReader(reader);
            String linha = null;
            StringTokenizer tok;
            int idlinha = 0;
            while ((linha = leitor.readLine()) != null) {
                tok = new StringTokenizer(linha, "\t");
//                System.out.println("matriz[0].length: " + matriz[0].length);
//                System.out.println("tok.countTokens(): " + tok.countTokens());
                for (int col = 0; col < matriz[0].length; col++) {
                    String value = tok.nextToken();
                    matriz[idlinha][col] = Double.valueOf(value).doubleValue();
                    if (matriz[idlinha][col] > FatorNormalizacao) {
                        FatorNormalizacao = matriz[idlinha][col];
                    }
                }
                idlinha++;
            }


            FatorNormalizacao *= 1.3;
            leitor.close();
            reader.close();
            //GravaFator(FatorNormalizacao);
            int[] DConf = {AnoInicial, AnoLimiar, AnoFinal, MesFinal, NumEntradas, NumSaidas, LagMeses};
            GravaConfiguracao(DConf, FatorNormalizacao);


        //System.out.println("FatorNormalizacao = "+FatorNormalizacao);

        } catch (Exception x) {
            x.printStackTrace();
        }



    }

    public RNA(String path) {
        RNA.path = path;
        this.Dir = path + "\\tempResult\\";
        this.Dirdados = path + "\\Execucao\\";
        ArqConfiguracao();
    /*
    System.out.println("AnoInicial = "+AnoInicial);
    System.out.println("AnoLimiar = "+AnoLimiar);
    System.out.println("AnoFinal = "+AnoFinal);
    System.out.println("MesFInal = "+MesFinal);
    System.out.println("NumEntradas = "+NumEntradas);
    System.out.println("NumSaidas = "+NumSaidas);
    System.out.println("LagMeses = "+LagMeses);
    System.out.println("FatorNormalização = "+FatorNormalizacao);
     */
    }

    public RNA() {
        ArqConfiguracao();
    /*
    System.out.println("AnoInicial = "+AnoInicial);
    System.out.println("AnoLimiar = "+AnoLimiar);
    System.out.println("AnoFinal = "+AnoFinal);
    System.out.println("MesFInal = "+MesFinal);
    System.out.println("NumEntradas = "+NumEntradas);
    System.out.println("NumSaidas = "+NumSaidas);
    System.out.println("LagMeses = "+LagMeses);
    System.out.println("FatorNormalização = "+FatorNormalizacao);
     */
    }

    public void Treinamento() {
        String melhorRede, melhorRedeGeral = null;
        double ErroAtual, melhorErro = Double.MAX_VALUE;
//        for (int neuroH = 10; neuroH < 13; neuroH++) {
        for (int neuroH = 10; neuroH < 30; neuroH++) {
//            for (double txh = 0.8; txh < 1.0; txh = txh + 0.1) {
            for (double txh = 0.1; txh < 1.0; txh = txh + 0.1) {
//                for (double txout = 0.08; txout < 0.1; txout = txout + 0.01) {
                for (double txout = 0.01; txout < 0.1; txout = txout + 0.01) {

                    RedeMLP MLP1 = new RedeMLP(path, NumEntradas, neuroH, NumSaidas, 1200, txh, txout);

//                    System.out.println("treino.length: " + treino.length);
//                    System.out.println("teste.length: " + teste.length);
                    MLP1.CarregaArqTreino(treino);

                    MLP1.CarregaArqCV(teste);
                    MLP1.Calcnet();
                    MLP1.Treinamento();
                    MLP1.Calcnet();
                    melhorRede = MLP1.GetMelhorRede();
                    MLP1 = new RedeMLP(melhorRede);
//                    System.out.println("Melhor Rede " + melhorRede);
                    //MLP1.CarregaArqCV("dadosnovos/T1/TesteDEZ.txt");
                    MLP1.CarregaArqCV(teste);
                    ErroAtual = MLP1.CalcnetCVimp();
                    if (ErroAtual < melhorErro) {
                        melhorRedeGeral = melhorRede;
                        melhorErro = ErroAtual;
                    }
                // System.out.println("ErrosPercenteCV = "+ErroAtual);


                }//for txout
            }//for txh
        }//for neuroH
        String MelhorRede = null;
        MelhorRede = renameRede(melhorRedeGeral, "REDE");
        //teste
        //RedeMLP MLPresult = new RedeMLP("tempResult/"+melhorRedeGeral);
        RedeMLP MLPresult = new RedeMLP(MelhorRede);
        //MLPresult.CarregaArqCV("dadosnovos/T1/TesteDEZ.txt");
        MLPresult.CarregaArqCV(teste);
        TreinamentoRNA.consoleTreinamento.append("\nResultadoMSECV = " + MLPresult.CalcnetCVimp());
        System.out.println("ErrosMSECV = " + MLPresult.CalcnetCVimp());

        //teste

        TreinamentoRNA.consoleTreinamento.append("\nMelhor Erro Percente CV = " + melhorErro);
        System.out.println("Melhor Erro Percente CV = " + melhorErro);
        TreinamentoRNA.consoleTreinamento.append("\nMelhor Rede = " + melhorRedeGeral);
        System.out.println("Melhor Rede = " + melhorRedeGeral);


    }

    public String renameRede(String arqrede, String Mes_arq) {
        String arqredeSaida = null;
        try {

            FileReader reader = new FileReader(new File(arqrede));
            BufferedReader leitor = new BufferedReader(reader);
            //arqredeSaida= "tempResult/"+arqrede+Mes_arq+".txt";
            arqredeSaida = Dir + Mes_arq + ".txt";
            FileWriter writer = new FileWriter(new File(arqredeSaida));
            PrintWriter saida = new PrintWriter(writer);
            String linhasaida = null;

            while ((linhasaida = leitor.readLine()) != null) {

                saida.println(linhasaida);
            }

            leitor.close();
            reader.close();
            saida.close();
            writer.close();
        } catch (IOException x) {
        }
        return (arqredeSaida);
    }

    public void Normalizacao() {

        double[][] tr = new double[(AnoLimiar - AnoInicial)][(NumEntradas + NumSaidas)];
        int limiar = AnoLimiar - AnoInicial;

        for (int ano = 0; ano < limiar; ano++) {

            for (int m = 0; m < LagMeses; m++) {

                tr[ano][m] = matriz[m][ano] / FatorNormalizacao;
                tr[ano][m + LagMeses] = matriz[m][ano + 1] / FatorNormalizacao;
            }
        }

        GravaN(tr, "Treino.txt");
        treino = tr;


        double[][] ts = new double[(AnoFinal - AnoLimiar)][(NumEntradas + NumSaidas)];

        int limiar2 = (AnoFinal - AnoLimiar) + limiar;
        //System.out.println("Limiar = "+limiar);
        //System.out.println("Limiar2 = "+limiar2);
        int col = 0;
        for (int ano = limiar; ano < limiar2; ano++) {

            for (int m = 0; m < LagMeses; m++) {

                ts[col][m] = matriz[m][ano] / FatorNormalizacao;
                ts[col][m + LagMeses] = matriz[m][ano + 1] / FatorNormalizacao;

            }

            col++;
        }
        GravaN(ts, "Teste.txt");
        teste = ts;

    }

    public void GravaN(double[][] dados, String NomeArq) {


        try {


            FileWriter writer = new FileWriter(new File(Dirdados + NomeArq));
            PrintWriter saida = new PrintWriter(writer);
            String Saida = "";

            //System.out.println("Dados linha = "+dados.length);
            //System.out.println("Dados coluna = "+dados[0].length);

            for (int lin = 0; lin < dados.length; lin++) {

                for (int col = 0; col < dados[0].length; col++) {

                    Saida += (dados[lin][col] * FatorNormalizacao) + "\t";
                // Saida=dados.length+" "+(dados[0].length+2);
                // saida.println(Saida);

                }
                // System.out.println("Saida = "+Saida);
                saida.println(Saida);
                Saida = "";
            }

            saida.close();
            writer.close();
        } catch (IOException x) {
        }


    }

    public void GravaN2(double[][] dados, String NomeArq) {


        try {


            FileWriter writer = new FileWriter(new File(Dirdados + NomeArq));
            PrintWriter saida = new PrintWriter(writer);
            String Saida = "";

//            System.out.println("Dados linha = " + dados.length);
//            System.out.println("Dados coluna = " + dados[0].length);

            for (int lin = 0; lin < dados.length; lin++) {

                for (int col = 0; col < dados[0].length; col++) {

                    Saida += (dados[lin][col]) + "\t";
                // Saida=dados.length+" "+(dados[0].length+2);
                // saida.println(Saida);

                }
                // System.out.println("Saida = "+Saida);
                saida.println(Saida);
                Saida = "";
            }

            saida.close();
            writer.close();
        } catch (IOException x) {
        }


    }

    public void GravaConfiguracao(int[] Conf, double ft) {


        // String NomeArquivo;String Dir="tempResult/",Dirdados="Dados/Execucao/";

        String[] DefConf = {"AnoInicial", "AnoLimiar", "AnoFinal", "MesFinal", "NumEntradas", "NumSaidas", "LagMeses"};

        try {


            FileWriter writer = new FileWriter(new File(Dirdados + "RNAConfiguracao.txt"));
            PrintWriter saida = new PrintWriter(writer);


            for (int id = 0; id < Conf.length; id++) {


                saida.println(DefConf[id] + "=" + "\t" + Conf[id] + "\t");

            }

            saida.println("FatorNormalização=" + "\t" + (ft) + "\t");

            saida.close();
            writer.close();
        } catch (IOException x) {
        }


    }

    public void GravaFator(double ft) {


        try {


            FileWriter writer = new FileWriter(new File(Dirdados + "FatorNormalizacao.txt"));
            PrintWriter saida = new PrintWriter(writer);
            //String Saida="";

            //Saida=(ft)+"\t";

            //saida.println(Saida);
            saida.println((ft) + "\t");
            saida.close();
            writer.close();
        } catch (IOException x) {
        }


    }

    /*
    public double PrevisaoPadrao(double[] entrada) {

    LerTeste();
    RedeMLP Rede;

    Rede = new RedeMLP(Dir+"REDE.txt");
    double previsao;

    for(int col=0;col<entrada.length;col++){
    entrada[col]=entrada[col]/FatorNormalizacao;
    }
    previsao=Rede.Previsao(entrada);

    System.out.println("Previsão = "+previsao);



    return(previsao);
    }

     */
    public double[][] PrevisaoTeste() {

        LerTeste();
        RedeMLP Rede;
        int tamPadroes = teste.length;
        int tamEntradas = teste[0].length - LagMeses;

        Rede = new RedeMLP(Dir + "REDE.txt");
        double[][] previsao = new double[tamPadroes][NumSaidas];
        double[] entrada = new double[tamEntradas];
        String linha = null;

        try {
            File f = new File(Dir + "ResultadoTeste.txt");

            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            System.out.println("tamPadroes: " + tamPadroes);
            for (int ano = 0; ano < tamPadroes; ano++) {

                linha = "Ano = " + ano + "\n";
                for (int col = 0; col < tamEntradas; col++) {
                    entrada[col] = teste[ano][col] / FatorNormalizacao;
                }
                previsao[ano] = Rede.Previsao(entrada);
                for (int cols = 0; cols < NumSaidas; cols++) {
                    linha += "Previsao =," + previsao[ano][cols] * FatorNormalizacao + ", real=, " + teste[ano][cols + LagMeses] + "\n";
                }
                bw.append(linha);
                bw.newLine();
                linha = "";
            }

            bw.close();
            Desktop.getDesktop().open(f);
        } catch (IOException x) {
        }
        return (previsao);
    }

//    public double[][] PrevisaoTeste(int ano_inicial, int ano_final) {
    public List<double[]> pevisaoTeste(int ano_inicial, int ano_final) {
        LerTeste();
        RedeMLP Rede;
        int tamPadroes = teste.length;
        int tamEntradas = teste[0].length - LagMeses;
        int anoFinalBase = anoFinalBase();
        /**
         * Result
         */
        List<double[]> previsaoList = new ArrayList<double[]>();
        /**
         * 
         */
//        System.out.println("Ano Base: " + anoFinalBase + " - Ano Inicial: " + ano_inicial);
        int intervalo = ano_inicial - anoFinalBase;
        int tamPrevisao = (ano_final - ano_inicial) + 1;
        /**
         *
         */
        Rede = new RedeMLP(Dir + "REDE.txt");
        double[][] previsao = new double[tamPadroes][NumSaidas];
        double[] entrada = new double[tamEntradas];
        String linha = null;
//        System.out.println("entrada.length: " + entrada.length);
        /**
         * 
         */
        try {
            File f = new File(Dir + "ResultadoTeste.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
//            System.out.println("tamPadroes: " + tamPadroes);
            for (int ano = 0; ano < tamPadroes; ano++) {

                linha = "Ano = " + ano + "\n";
                for (int col = 0; col < tamEntradas; col++) {
                    entrada[col] = teste[ano][col] / FatorNormalizacao;
                }
                previsao[ano] = Rede.Previsao(entrada);
                for (int cols = 0; cols < NumSaidas; cols++) {
                    linha += "Previsao =," + previsao[ano][cols] * FatorNormalizacao + ", real=, " + teste[ano][cols + LagMeses] + "\n";
                }
                bw.append(linha);
                bw.newLine();
                linha = "";
            }
            /**
             * Loop para percorrer atÈ o ano inicial da previs„o
             */
            double[] prev = previsao[0];
            for (int i = 0; i < intervalo; i++) {
                prev = Rede.Previsao(prev);
            }
            /**
             * Loop que realiza a previs„o propriamente dita
             */
            previsao[0] = prev;
            for (int i = 0; i < tamPrevisao; i++) {
                prev = Rede.Previsao(prev);
                previsaoList.add(prev);
            }


            bw.close();
//            Desktop.getDesktop().open(f);
        } catch (IOException x) {
            x.printStackTrace();
        }
//        return (previsao);
        return previsaoList;
    }

    public int anoFinalBase() {
        int result = 2006;

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(Dirdados + "RNAConfiguracao.txt")));
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split("\t");
                if (dados[0].equalsIgnoreCase("AnoFinal=")) {
                    result = Integer.valueOf(dados[1]);
                    break;
                }
            }

        } catch (Exception e) {
        }
        return result;
    }

    /*
    public double[] PrevisaoTesteInicio() {

    LerTeste();
    RedeMLP Rede; int tamPadroes=teste.length; int tamEntradas=teste[0].length-1;

    Rede = new RedeMLP(Dir+"REDE.txt");
    double[] previsao = new double[tamPadroes];
    double[] entrada = new double[tamEntradas];

    try{


    FileWriter writer = new FileWriter(new File(Dir+"Resultado.txt"));
    PrintWriter saida = new PrintWriter(writer);

    for(int col=0;col<tamEntradas;col++){
    entrada[col]=teste[0][col]/FatorNormalizacao;
    }

    for(int mes=0;mes<tamPadroes;mes++){


    previsao[mes]=Rede.Previsao(entrada);

    entrada[0]=entrada[1];
    entrada[1]=entrada[2];
    entrada[2]=entrada[3];
    entrada[3]=entrada[4];
    entrada[4]=entrada[5];
    entrada[5]=entrada[6];
    entrada[6]=entrada[7];
    entrada[7]=entrada[8];
    entrada[8]=entrada[9];
    entrada[9]=entrada[10];
    entrada[10]=entrada[11];
    entrada[11]=previsao[mes];



    //System.out.println( mes+" = "+(previsao[mes]+" Real = "+teste[mes][tamEntradas]/FatorNormalizacao));
    saida.println(mes+" = "+previsao[mes]+" Real = "+teste[mes][tamEntradas]/FatorNormalizacao);
    }


    saida.close();   writer.close();
    }catch (IOException x){}


    return(previsao);

    }



    public double[] PrevisaoTesteInicio(int ano) {

    LerTeste();
    RedeMLP Rede; int tamPadroes=(teste.length); int tamEntradas=teste[0].length-1;
    int tp=tamPadroes*ano;

    Rede = new RedeMLP(Dir+"REDE.txt");
    double[] previsao = new double[tp];
    double[] entrada = new double[tamEntradas];

    try{


    FileWriter writer = new FileWriter(new File(Dir+"Resultado.txt"));
    PrintWriter saida = new PrintWriter(writer);

    for(int col=0;col<tamEntradas;col++){
    entrada[col]=teste[0][col]/FatorNormalizacao;
    }

    for(int mes=0;mes<tp;mes++){


    previsao[mes]=Rede.Previsao(entrada);

    entrada[0]=entrada[1];
    entrada[1]=entrada[2];
    entrada[2]=entrada[3];
    entrada[3]=entrada[4];
    entrada[4]=entrada[5];
    entrada[5]=entrada[6];
    entrada[6]=entrada[7];
    entrada[7]=entrada[8];
    entrada[8]=entrada[9];
    entrada[9]=entrada[10];
    entrada[10]=entrada[11];
    entrada[11]=previsao[mes];



    //System.out.println( mes+" = "+(previsao[mes]+" Real = "+teste[mes][tamEntradas]/FatorNormalizacao));

    if(mes<tamPadroes)
    saida.println(mes+" = "+previsao[mes]+" Real = "+teste[mes][tamEntradas]/FatorNormalizacao);
    else saida.println(mes+" = "+previsao[mes]);





    }


    saida.close();   writer.close();
    }catch (IOException x){}


    return(previsao);

    }


    public double[] PrevisaoTesteFim(int ano) {

    LerTeste();
    RedeMLP Rede; int tamPadroes=(teste.length); int tamEntradas=teste[0].length-1;
    int tp=tamPadroes*ano;

    Rede = new RedeMLP(Dir+"REDE.txt");
    double[] previsao = new double[tp];
    double[] entrada = new double[tamEntradas];

    try{


    FileWriter writer = new FileWriter(new File(Dir+"Resultado.txt"));
    PrintWriter saida = new PrintWriter(writer);

    for(int col=0;col<tamEntradas;col++){
    entrada[col]=teste[tamPadroes-1][col]/FatorNormalizacao;
    }

    for(int mes=0;mes<tp;mes++){


    previsao[mes]=Rede.Previsao(entrada);

    entrada[0]=entrada[1];
    entrada[1]=entrada[2];
    entrada[2]=entrada[3];
    entrada[3]=entrada[4];
    entrada[4]=entrada[5];
    entrada[5]=entrada[6];
    entrada[6]=entrada[7];
    entrada[7]=entrada[8];
    entrada[8]=entrada[9];
    entrada[9]=entrada[10];
    entrada[10]=entrada[11];
    entrada[11]=previsao[mes];



    //System.out.println( mes+" = "+(previsao[mes]+" Real = "+teste[mes][tamEntradas]/FatorNormalizacao));

    if(mes<tamPadroes)
    saida.println(mes+" = "+previsao[mes]+" Real = "+teste[mes][tamEntradas]/FatorNormalizacao);
    else saida.println(mes+" = "+previsao[mes]);





    }


    saida.close();   writer.close();
    }catch (IOException x){}


    return(previsao);

    }
     */
    public void ArqConfiguracao() {
        String linha = null;

        try {

            FileReader reader = new FileReader(new File(Dirdados + "RNAConfiguracao.txt"));
            BufferedReader leitor = new BufferedReader(reader);
            StringTokenizer tok;

            //leitor.readLine();
            linha = leitor.readLine();
            tok = new StringTokenizer(linha, "\t");
            tok.nextToken();
            AnoInicial = Integer.valueOf(tok.nextToken());

            linha = leitor.readLine();
            tok = new StringTokenizer(linha, "\t");
            tok.nextToken();
            AnoLimiar = Integer.valueOf(tok.nextToken());

            linha = leitor.readLine();
            tok = new StringTokenizer(linha, "\t");
            tok.nextToken();
            AnoFinal = Integer.valueOf(tok.nextToken());

            linha = leitor.readLine();
            tok = new StringTokenizer(linha, "\t");
            tok.nextToken();
            MesFinal = Integer.valueOf(tok.nextToken());

            linha = leitor.readLine();
            tok = new StringTokenizer(linha, "\t");
            tok.nextToken();
            NumEntradas = Integer.valueOf(tok.nextToken());

            linha = leitor.readLine();
            tok = new StringTokenizer(linha, "\t");
            tok.nextToken();
            NumSaidas = Integer.valueOf(tok.nextToken());

            linha = leitor.readLine();
            tok = new StringTokenizer(linha, "\t");
            tok.nextToken();
            LagMeses = Integer.valueOf(tok.nextToken());

            linha = leitor.readLine();
            tok = new StringTokenizer(linha, "\t");
            tok.nextToken();
            FatorNormalizacao = Double.valueOf(tok.nextToken()).doubleValue();


            leitor.close();
            reader.close();

        } catch (IOException x) {
        }


    }

    public double[] Arq(String NomeArq, int Col) {
        String linha = null;
        double[] Dados = new double[Col];
        try {
            //System.out.println("Arquivo ="+NomeArq);
            FileReader reader = new FileReader(new File(NomeArq));
            BufferedReader leitor = new BufferedReader(reader);
            StringTokenizer tok;
            //int idlinha=0;
            leitor.readLine();
            linha = leitor.readLine();//System.out.println("Arquivo ="+NomeArq+" "+linha);
            tok = new StringTokenizer(linha, "\t");
            for (int col = 0; col < Dados.length; col++) {
                Dados[col] = Double.valueOf(tok.nextToken()).doubleValue();
            }

            leitor.close();
            reader.close();

        } catch (IOException x) {
        }

        return (Dados);
    }

    public double[] Arq2(String NomeArq, int Col) {
        String linha = null;
        double[] Dados = new double[Col];
        try {
            //System.out.println("Arquivo ="+NomeArq);
            FileReader reader = new FileReader(new File(NomeArq));
            BufferedReader leitor = new BufferedReader(reader);
            StringTokenizer tok;
            //int idlinha=0;
            leitor.readLine();
            leitor.readLine();
            linha = leitor.readLine();//System.out.println("Arquivo ="+NomeArq+" "+linha);
            tok = new StringTokenizer(linha, "\t");
            for (int col = 0; col < Dados.length; col++) {
                Dados[col] = Double.valueOf(tok.nextToken()).doubleValue();
            }

            leitor.close();
            reader.close();

        } catch (IOException x) {
        }

        return (Dados);
    }

    public double ArqD(String NomeArq, int Col) {
        String linha = null;
        double Dados = 0;
        try {
            //System.out.println("Arquivo ="+NomeArq);
            FileReader reader = new FileReader(new File(NomeArq));
            BufferedReader leitor = new BufferedReader(reader);
            StringTokenizer tok;
            //int idlinha=0;
            leitor.readLine();
            linha = leitor.readLine();//System.out.println("Arquivo ="+NomeArq+" "+linha);
            tok = new StringTokenizer(linha, "\t");
            for (int col = 0; col < Col; col++) {
                //Dados[col]= Double.valueOf(tok.nextToken()).doubleValue(); 
                Double.valueOf(tok.nextToken()).doubleValue();
            }
            Dados = Double.valueOf(tok.nextToken()).doubleValue();
            leitor.close();
            reader.close();

        } catch (IOException x) {
        }

        return (Dados);
    }

    public void LerTeste() {
        String linha = null;

        teste = new double[(AnoFinal - AnoLimiar)][(NumEntradas + NumSaidas)];

        try {

            FileReader reader = new FileReader(new File(Dirdados + "Teste.txt"));
            BufferedReader leitor = new BufferedReader(reader);
            StringTokenizer tok;

            for (int lin = 0; lin < teste.length; lin++) {
                linha = leitor.readLine();
                tok = new StringTokenizer(linha, "\t");
                for (int col = 0; col < teste[0].length; col++) {
                    teste[lin][col] = Double.valueOf(tok.nextToken()).doubleValue();

                }

            }

            leitor.close();
            reader.close();

        } catch (IOException x) {
        }


    }
}
