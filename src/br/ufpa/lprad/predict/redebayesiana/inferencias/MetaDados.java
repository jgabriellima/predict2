package br.ufpa.lprad.predict.redebayesiana.inferencias;
//
//import br.ufpa.lprad.predict.redebayesiana.aprendizado.K2;

import br.ufpa.lprad.predict.gui.window.correlacao.backstage.BackDiscretizacaoGUI;
import br.ufpa.lprad.predict.redebayesiana.aprendizado.K2;
import br.ufpa.lprad.predict.redebayesiana.aprendizado.Vertice;
import java.awt.Container;
import java.io.Serializable;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JInternalFrame;

public class MetaDados implements Serializable {

    // Atributos da Rede
    private int qntdAtributos, // Numero de atributos
             numInfer;
    private String[] atributos;     // Denominacao dos atributos

    // Faixas dos Atributos da Rede
    private int[] qtdFaixas, // qtdFaixas[x] --> Numero de faixas do atributo x
             clicados,  atrInf,  atrValInf;
    private int[] vetorFaixa;       //vetor que define que atributos sao discretos ou nao!
    private int nFaixas = 5;          //numeros de faixas para valores discretos;
    private String[][] faixas;      // faixas[x][y] --> Nome da faixa y do atributo x

    // Hierarquia da Rede
    private int[] qtdPais, // qtdPais[x] --> Numero de pais do filho x
             atrInfer,  fxInfer;
    private int[][] pais;           // pais[x][y] --> Quem eh o pai numero y do atributo x
    // Tabela de Registros Completa, para normalizacao
    private Dados originais;
    private Discretizar disc;
    public static double[][] valorProb;

    // Tabela auxiliar para definicao das estruturas
    private String[][] matriz;
    private int count = 0,  teste,  nVx,  auxIni = -1;//-666;
    private String[][] TabBAK;
    private Container ent;

    /** Creates a new instance of MetaDados */
    public MetaDados(ArqTxt arquivo, Container entrada) {

        if (ent instanceof JInternalFrame) {
            ent = (JInternalFrame) entrada;
        }

        // Atributos auxiliares que determinam a extensao dos outros
        int entradas = arquivo.linhas();
        qntdAtributos = arquivo.colunas();

        //System.out.println(" qntdAtributos: "+ qntdAtributos);

        clicados = new int[qntdAtributos];
        atrInf = new int[qntdAtributos];
        atrValInf = new int[qntdAtributos];

        for (int i = 0; i < qntdAtributos; i++) {
            clicados[i] = -1;
            atrInf[i] = -1;
            atrValInf[i] = -1;
        }

//        System.out.println("K2.getRedeBayesiana().getVertices().size(): "+K2.getRedeBayesiana().getVertices().size());
        // Define MetaDados
        atributos = new String[K2.getRedeBayesiana().getVertices().size()];          // instancia e inicializa a matriz atributo;
        faixas = new String[K2.getRedeBayesiana().getVertices().size()][entradas];   // instancia a matriz faixas;
        qtdFaixas = new int[entradas];                  // instancia o vetor qtdFaixas;
        qtdPais = new int[K2.getRedeBayesiana().getVertices().size()];
        pais = new int[K2.getRedeBayesiana().getVertices().size()][K2.getRedeBayesiana().getVertices().size()];
        vetorFaixa = new int[K2.getRedeBayesiana().getVertices().size()];            // instancia o vetor vetorFaixas;
        valorProb = new double[K2.getRedeBayesiana().getVertices().size()][entradas];
        // Define os valores de Inferencia
        fxInfer = new int[K2.getRedeBayesiana().getVertices().size()];
        atrInfer = new int[K2.getRedeBayesiana().getVertices().size()];

        paiFilho();

        // Define o conjunto total de Dados
        originais = new Dados(entradas, qntdAtributos, this);

        TabBAK = new String[qntdAtributos][entradas];
        backupTab();
        disc = new Discretizar(originais, this);

        // Normalizacao dos Dados
        matriz = new String[qntdAtributos][entradas];
        arquivo.descarregaArquivo(this);
        normalizaDados();

        /*for(int j=0; j<qntdAtributos; j++){
        for(int i=1; i<originais.getEntradas();i++){
        ////System.out.println("original["+j+"]"+"["+i+"]="+ originais.getDado(j,i-1));
        }
        }*/
        // Auto-explicativo
        defineFaixasAtributo();

        for (int i = 0; i < qntdAtributos; i++) {
            fxInfer[i] = auxIni;
            atrInfer[i] = auxIni;
        }


    }

    /**************************************************************/
    /***********metodo que le linha a linha do arquivo************/
    /************************************************************/
    public void escreveLinha(String linha) {
        StringTokenizer st;
        String delim = "\t";

        st = new StringTokenizer(linha, delim);
        ////System.out.println(linha);
//        st.nextToken();
        // String[] te = linha.split("\t");
        for (int j = 0; j < qntdAtributos; j++) {
            try {

                matriz[j][count] = st.nextToken();
            } catch (Exception e) {
                //   e.printStackTrace();
            }
        // System.out.println("matriz["+j+"]["+count+"] : "+matriz[j][count]);
        }
        count++;
    }

    /***********************************************************/
    /***** Metodo que divide o meu arquivo(tabela) em duas *****/
    /*********** matrizes: registros e atributos ***************/
    /***********************************************************/
    /**registros são os valores da tabela atributos são os nomes das colunas da tabela */
    public void normalizaDados() {

        //define a matriz de atributos
        for (int j = 0; j < qntdAtributos; j++) {
            atributos[j] = matriz[j][0];
        }

        //define a matriz de registros
        for (int j = 0; j < qntdAtributos; j++) {
            for (int i = 1; i < originais.getEntradas(); i++) {
                originais.setDado(j, i - 1, matriz[j][i]);
            }
        }
    }


    //verifica se o registro faz parte da faixa desejada
    public boolean igualaFaixa(int atrib, int lin, int fx) {
        String auxDado = originais.getDado(atrib, lin);
        boolean aux;

        if (vetorFaixa[atrib] == 0) {
            if (getFaixa(atrib, fx).equals(auxDado)) {
                ////System.out.println("registro"+originais.getDado(atrib,lin));
                ////System.out.println("faixa"+ getFaixa(atrib,fx));
                aux = true;
            ////System.out.println("entroui");
            } else {
                aux = false;
            }
        } else {
            ////System.out.println("hahhahhahaa");
            if (((Double.parseDouble(auxDado)) >= (disc.getValInf(atrib, fx))) &&
                    ((Double.parseDouble(auxDado)) < (disc.getValSup(atrib, fx))) ||
                    ((Double.parseDouble(auxDado)) > (disc.getValInf(atrib, fx))) &&
                    ((Double.parseDouble(auxDado)) <= (disc.getValSup(atrib, fx)))) {
                aux = true;
            } else {
                aux = false;
            }
        }
        return aux;
    }

    /**************************************************************************/
    /**** Metodo que define os registros diferentes para cada atributo e ******/
    /************* armazena-os em faixas[][], para cada atributo **************/
    /**************************************************************************/
    /** f: array auxiliar para a definicao de faixas dos atributos;
     *  qtdFaixas: qtde de faixas de um atributo;
     *  faixas: tipos de registros que possuem cada atributo**/
    public void defineFaixasAtributo() {
        // System.out.println("FOI POR AQUI.... DEFINE FX ATRIB META");
        String f[] = new String[originais.getEntradas()];
        double d;

        for (int j = 0; j < qntdAtributos; j++) {
            int count = 1;
            f[0] = originais.getDado(j, 0);
            faixas[j][0] = f[0];

            for (int i = 0; i < originais.getEntradas() - 1; i++) {
                for (int k = 0; k < count; k++) {
                    if (originais.getDado(j, i).equals(f[k])) {
                        break;
                    }

                    if (k == (count - 1)) {
                        f[count] = originais.getDado(j, i);
                        faixas[j][count] = f[count];
                        count++;
                    }
                }
            }
            qtdFaixas[j] = count;

            if (qtdFaixas[j] > 10) { //mudar para um valor maior,de acordo cm o n de faixas da base real

                try {
                    d = Double.parseDouble(originais.getDado(j, 0));
                    disc.Converte(j);
                    disc.normalizaFaixa(j);

                    for (int i = 0; i < originais.getEntradas() - 1; i++) {
                        faixas[j][i] = null;
                    }

                    count = 1;
                    f[0] = originais.getDado(j, 0);
                    faixas[j][0] = f[0];

                    for (int i = 0; i < originais.getEntradas() - 1; i++) {
                        for (int k = 0; k < count; k++) {
                            if (originais.getDado(j, i).equals(f[k])) {
                                break;
                            }

                            if (k == (count - 1)) {
                                f[count] = originais.getDado(j, i);
                                faixas[j][count] = f[count];
                                count++;
                            }
                        }
                    }
                    //valorProb = new double[qntdAtributos][nFaixas];
                    vetorFaixa[j] = 1;
                    qtdFaixas[j] = BackDiscretizacaoGUI.atribDisc[j].getNFaixas();// nFaixas; //--> num de faixas para valores dicretos; nesse caso:Nfaixas=5
                    contaQtdeValoresDisc(count, j);

                } catch (Exception e) {
                    vetorFaixa[j] = 0;
                    contaQtdeAtributo(qtdFaixas[j], j);
                //valorProb = new double[qntdAtributos][qtdFaixas[j]];
                //System.out.println("Erro: " + e.getMessage());
                }
            } else {
                vetorFaixa[j] = 0;
                contaQtdeAtributo(count, j);

            }
        }
        disc.retornaFx();
    }

    /***********************************************************************/
    /**** Metodo que calcula a quantidade de registros de determinada *****/
    /**************** faixa de atributos (P=p/t) *************************/
    /********************************************************************/
    /**
     *  n_faixas_atributo[colunaFaixa][j]:qtde de cada registro por atributo;
     *  colunaFaixa: é a coluna do atributo q está sendo trabalhado;
     **/
    public void contaQtdeAtributo(int count, int colunaFaixa) {
        String aux = "";
        int cont;

        //calcula a qtde de cada registro para cada atributo
        for (int j = 0; j < count; j++) {
            cont = 0;
            aux = faixas[colunaFaixa][j];
            //System.out.println("col = "+colunaFaixa);
            for (int i = 0; i < (originais.getEntradas() - 1); i++) {
                if (aux.equals(originais.getDado(colunaFaixa, i))) {
                    cont++;  //conta o numero de registros diferentes para cada atributos
                }
            }
            originais.setNumRegFxAtrib(colunaFaixa, j, cont);
        ////System.out.println("col = "+j+ " numfx = "+originais.getNumRegFxAtrib(j,count));
        ////System.out.println("faixa"+j+"nRegistros"+cont);
        }
    ////System.out.println("col"+colunaFaixa+ "num "+originais.getNumRegFxAtrib(colunaFaixa,count));

    }

    //conta o numero de registros tem em cada faixa discreta
    //nFaixas-->numero de faixas do atributo discreto
    //colunaFaixa-->representa o atributo q se esta sendo trabalhando
    public void contaQtdeValoresDisc(int nFaixasReg, int colunaFaixa) {
        String aux = "";
        int cont;
        //calcula a qtde de cada registro para cada atributo
        for (int j = 0; j < nFaixas; j++) {
            cont = 0;
            for (int i = 0; i < (originais.getEntradas() - 1); i++) {
                if (igualaFaixa(colunaFaixa, i, j)) {
                    cont++;  //conta o numero de registros diferentes para cada atributos
                }
            }
            originais.setNumRegFxAtrib(colunaFaixa, j, cont);
        }
    }

    /************************************************************************************/
    /** Metodo que define o no de faixas do atributo pai do atributo filho(variavel z) **/
    /************ que esta sendo trabalhado na classe ProbabilidadeNo *******************/
    /************************************************************************************/
    public int nFaixasPais(int z) {
        int sizeCond = 1;
        for (int k = 0; k < qtdPais[z]; k++) {
            sizeCond = sizeCond * qtdFaixas[pais[z][k]];
        }
        return sizeCond;
    }

    //backup da tabela original
    public void backupTab() {
        for (int j = 0; j < qntdAtributos; j++) {
            for (int i = 0; i < originais.getEntradas(); i++) {
                TabBAK[j][i] = originais.getDado(j, i);
            }
        }
    }

    /**
     * incrementa o numero de inferencias da rede
     */
    public void setNumInfer() {
        numInfer++;
    }

    /**
     * redefine o vetor de atributos inferenciados
     */
    public void defineInfer(int atrib, int fx) {
        for (int i = 0; i < qntdAtributos; i++) {
            //se o atributo jah estah na lista dos inferenciados, deve-se decrementar o numero de inferencias
            //e atualizar o vetor de atributos e faixas inferenciados
            if (atrInfer[i] == atrib) {
                decNumInfer();

                for (int j = i; j < qntdAtributos; j++) {
                    //se o atributo nao estiver na ultima posicao do vetor de atributos
                    if (j < (qntdAtributos - 1)) {
                        //se as ultimas posicoes do vetor estiverem livres (=-666)
                        if (atrInfer[j + 1] == auxIni) {
                            fxInfer[j] = fx;
                            atrInfer[j] = atrib;
                            break;
                        //se as ultimas posicoes do vetor estiverem ocupadas com valor de atributo
                        } else {
                            atrInfer[j] = atrInfer[j + 1];
                            atrInfer[j + 1] = atrib;
                            fxInfer[j] = fxInfer[j + 1];
                            fxInfer[j + 1] = fx;
                        }
                    //se o atributo estiver na ultima posicao do vetor de atributos
                    } else {
                        atrInfer[j] = atrib;
                        fxInfer[j] = fx;
                    }
                }
                break;
            }
        }
        atrInfer[numInfer - 1] = atrib;
        fxInfer[numInfer - 1] = fx;



//        for(int i=0;i<qntdAtributos;i++){
//            System.out.println("atrInfer["+i+"] ="+atrInfer[i]);
//        }
//
//        for(int i=0;i<qntdAtributos;i++){
//            System.out.println("fxInfer["+i+"] ="+fxInfer[i]);
//        }
    }

    /**
     * decrementa o numero de atributos inferenciados
     */
    public void decNumInfer() {
        numInfer--;
    }

    /**
     * atualiza o vetor de inferencias de acordo cm o desfazer das inferencias
     */
    public void desfazInfer(int atr) {
        for (int i = 0; i < qntdAtributos; i++) {
            //se o atributo inferenciado corresponder ao atrInfer[i]
            if (atrInfer[i] == atr) {
                //se o numero de inferencias for maior que um
                if (numInfer > 0) {
                    decNumInfer();
                } else {
                    break;
                }
                ////System.out.println("foi decrementado o numInfer para "+numInfer);
                for (int j = i; j < qntdAtributos; j++) {
                    //se o atributo estiver na ultima posicao do vetor de atributos
                    if (j < (qntdAtributos - 1)) {
                        //se o atributo for o ultimo inferenciado no vetor de inferencias
                        if (atrInfer[j + 1] == auxIni) {
                            atrInfer[j] = auxIni;
                            fxInfer[j] = auxIni;
                            break;
                        } else {
                            atrInfer[j] = atrInfer[j + 1];
                            fxInfer[j] = fxInfer[j + 1];
                            atrInfer[j + 1] = auxIni;
                            fxInfer[j + 1] = auxIni;
                        }
                    } else {
                        atrInfer[j] = auxIni;
                        fxInfer[j] = auxIni;
                    }
                }
                break;
            }
        }
    // System.out.println("o atributo q foi retirado da inferencia eh "+ atr);

    /*for(int i=0;i<qntdAtributos;i++){
    System.out.println("atrInfer["+i+"] ="+atrInfer[i]);
    }*/

//        for(int i=0;i<qntdAtributos;i++){
//            System.out.println("fxInfer["+i+"] ="+fxInfer[i]);
//        }
    }

    /*retorna a quantidade de atributos presentes na base de dados*/
    public int getQntdAtrib() {
        return qntdAtributos;
    }

    /*retorna a quantidade de pais que o atributo possui*/
    public int getQntdPais(int x) {
        return qtdPais[x];
    }

    /*retorna os pais do atributo*/
    public int getPais(int x, int y) {
        return pais[x][y];
    }

    /*retorna o numero de faixas que o atributo possui*/
    public int getQntdFaixas(int i) {
        return qtdFaixas[i];
    }

    /*retorna as faixas dos atributos*/
    public String getFaixa(int x, int y) {
        return faixas[x][y];
    }

    /*retorna a referencia para a classe Dados*/
    public Dados getOriginais() {
        return originais;
    }

    /*retorna um determinado atributo*/
    public String getAtributo(int i) {
        return atributos[i];
    }

    /*retorna os pais do atributo*/
    public int[][] meDaTeusPais() {
        return pais;
    }

    public int[] meDaQntdPais() {
        return qtdPais;
    }

    public int[] meDaQntFaixas() {
        return qtdFaixas;
    }

    public int ehDiscreto(int atrib) {
        return vetorFaixa[atrib];
    }

    /*retorna a faixa inferior do atributo*/
    public Discretizar getDiscretizar() {
        return disc;
    }

    public JInternalFrame getEntrada() {
        return (JInternalFrame) ent;
    }

    // Metodo temporario!!! Devera ser substituido por uma definicao pelo usuario
    // Define quem eh pai e filho de quem
    public void paiFilho() {

        Vector qtdPaisVect = K2.getRedeBayesiana().getVertices();

        for (int i = 0; i < qtdPaisVect.size(); i++) {
            if (((Vertice) qtdPaisVect.get(i)).getPais().size() > 0) {
                qtdPais[i] = ((Vertice) qtdPaisVect.get(i)).getPais().size();
            }
        }

        int indice = 0;
        for (int i = 0; i < qtdPaisVect.size(); i++) {
            if (((Vertice) qtdPaisVect.get(i)).getPais().size() > 0) {
                Vector aux_pais = ((Vertice) qtdPaisVect.get(i)).getPais();
                for (int j = 0; j < aux_pais.size(); j++) {
                    p:
                    for (int k = 0; k < qtdPaisVect.size(); k++) {
                        if (((Vertice) qtdPaisVect.get(k)).getNome().equalsIgnoreCase(((Vertice) aux_pais.get(j)).getNome())) {
                            indice = k;
                            break p;
                        }
                    }
                    pais[i][j] = indice;
                }
            }
        }

    }

    public void setProb(int i, int j, double p) {
        valorProb[i][j] = p;
//        System.out.println("valorProb111["+i+"]["+j+"]="+valorProb[i][j]);
    }

    public double getProb(int i, int j) {

        return valorProb[i][j];


    }

//    public int setInfer(int atrib, int fx){
//        if (clicados[atrib]==-1){
//            clicados[atrib]=1;
//            numInfer++;
//        }
//        atrInf[atrib]=atrib;
//        atrValInf[atrib]=fx;
//        return numInfer;
//    }
    public int[] getAtrInfer() {
        return atrInfer;
    }

    public int[] getFxInfer() {
        return fxInfer;
    }

    public int getNumInfer() {
        return numInfer;
    }

    public int[] getAtrValInf() {
        return atrValInf;
    }
}

