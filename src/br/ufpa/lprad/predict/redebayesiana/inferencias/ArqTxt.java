package br.ufpa.lprad.predict.redebayesiana.inferencias;
import java.io.*;
import java.util.*;

public class ArqTxt {

    private File arqTxt;
    private StringTokenizer st;
    private FileInputStream input;
    private DataInputStream in;
    private int n_linhas = 0;
    private int n_colunas = 0;

    //método construtor da classe arquivo
    public ArqTxt(File ark) throws Exception{
        this.arqTxt = ark;
        input = new FileInputStream(arqTxt);
        in = new DataInputStream(input);
        contador();
    }
    //conta o n° linhas do arquivo
    public void contador() throws Exception {
        String linha = "", delim = "\t";

        //conta o n° d ecolunas
        linha = proxLinha();
        //st = new StringTokenizer(linha, delim);
        n_colunas = linha.split("\t").length;//(st.countTokens());

        // conta o n° de Linhas
        n_linhas = 0;
        while((linha= proxLinha()) != null){
            n_linhas++;
        }
        in.close();
        input = new FileInputStream(arqTxt);
        in = new DataInputStream(input);
    }
    //retorna o valor de linhas do meu arquivo
    public int linhas(){
        return n_linhas;
    }
     //retorna o numero de colunas do meu arquivo
    public int colunas(){
        return n_colunas;
    }
    //retorna a proxima linha que deve ser lida
    public String proxLinha() {
        try {
            return in.readLine();
        } catch (Exception e) {
            return null;
        }
    }

    //escreve linha a linha da minha matriz
    public void descarregaArquivo(MetaDados meta){
        for(int i = 0; i<n_linhas;i++){
            String linha = proxLinha();

            meta.escreveLinha(linha);
        }
    }
}
