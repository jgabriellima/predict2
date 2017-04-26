/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.redebayesiana.algoritmogenetico;

import br.ufpa.lprad.predict.actions.Actions_Rede;
import br.ufpa.lprad.predict.gui.window.correlacao.FrameAG;
import br.ufpa.lprad.predict.gui.window.PainelInferencia;
import br.ufpa.lprad.predict.gui.window.correlacao.backstage.BackAG;
import br.ufpa.lprad.predict.redebayesiana.aprendizado.K2;
import br.ufpa.lprad.predict.redebayesiana.aprendizado.RedeBayesiana;
import br.ufpa.lprad.predict.redebayesiana.aprendizado.Vertice;
import br.ufpa.lprad.predict.redebayesiana.grafo.Tela;
import br.ufpa.lprad.predict.redebayesiana.inferencias.Dados;
import br.ufpa.lprad.predict.redebayesiana.inferencias.Itens;
import br.ufpa.lprad.predict.redebayesiana.inferencias.MetaDados;
import br.ufpa.lprad.predict.redebayesiana.inferencias.ProbabilidadeNo;
import java.beans.PropertyVetoException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabriel
 */
public class AG extends Thread {

    public Vector<Individuo> pop;
    public Vector<Individuo> maisAptos;
    public RedeBayesiana rb;
    public final int tamanhoPopulacao = 20;
    public int tamCromossomo;
    public double[][] tab_prob;
    public int ciclo = 500;
    public double probCruzamento = 0.7;
    public double probMutacao = 0.01;
    public ProbabilidadeNo pb;
    public int indice_atributo_estudado;
    public int numeroDeInferenciadas = 4;
    public int faixa;
    public int nEstados;
    public String nom_faixa;
    DecimalFormat df = df = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
    public static String relatorio = "";

    public AG() {
        this.rb = K2.getRedeBayesiana();
        this.tamCromossomo = rb.getNVertices();
        pop = new Vector<Individuo>();
        maisAptos = new Vector<Individuo>();

    }

    public AG(int indice, int faixa, String nom_faixa) {

        this.rb = K2.getRedeBayesiana();
        this.tamCromossomo = rb.getNVertices();

        pop = new Vector<Individuo>();
        maisAptos = new Vector<Individuo>();
        indice_atributo_estudado = indice;
        this.faixa = faixa;
        this.nom_faixa = nom_faixa;

        df.applyPattern("#.##");

    }

    @Override
    public void run() {
        iniciaCiclo();
    }

    public void criaPopulacaoInicial() {

        Individuo ind;
        Cromossomo crom;

        for (int i = 0; i < tamanhoPopulacao; i++) {
            ind = new Individuo();
            crom = new Cromossomo(tamCromossomo);

            for (int j = 0; j < tamCromossomo; j++) {
                if (j != indice_atributo_estudado) {

                    nEstados = getEstados(j);

                    int v = ((int) (Math.random() * nEstados));
                    crom.add(v);
                } else {
                    crom.add(-1);
                }

            }

//            System.out.println("COMOSSOMO " + i + " = " + crom.genes);

            ind.setCromossomo(crom);
            ind.setTamanho(crom.getGenes().size());
            pop.add(ind);

        }
//        System.out.println("\n\n");
    }

    public void fusion_AG_RB() {


        MetaDados meta = Itens.meta;
        Dados dad = Itens.dado;

        for (int k = 0; k < pop.size(); k++) {

            Individuo ind = pop.get(k);
            Cromossomo crom = ind.getCromossomo();

            int[] atrinf = getAtribInfo(crom);// */{1, 2, 3, 4, -1};
            int[] atrfxinf = getAtribFx(crom);//{0, 2, 4, 1, -1};

//            System.out.println("Indivíduo: " + k + " : " + crom.genes);
            try {
                pb = new ProbabilidadeNo(atrinf, atrfxinf, meta, dad, tamCromossomo - 1);
            } catch (Exception e) {
                pb.P[indice_atributo_estudado][faixa] = 0.01;
            }
            pop.get(k).setAptidao(pb.P[indice_atributo_estudado][faixa]);
        }
    }

    public void avaliaPopulacao(Vector<Individuo> populacao) {
        double maior = 0;

        maisAptos.removeAllElements();
        int indice = 0;
        int indice2 = 0;
        for (int i = 0; i < populacao.size(); i++) {
            if (populacao.get(i).getAptidao() > maior) {
                maior = populacao.get(i).getAptidao();
                indice = i;
            }
        }

        maisAptos.add(populacao.get(indice));

        maior = 0;
        for (int i = 0; i < populacao.size(); i++) {
            if (populacao.get(i).getAptidao() > maior && i != indice) {

                if (populacao.get(i).getAptidao() > maior) {
                    indice2 = i;
                    maior = populacao.get(i).getAptidao();
                }
            }
        }
        maisAptos.add(populacao.get(indice2));

    }

    private void GeraRelatorio(Individuo maisApto) {
        /**
         * 
         */
        Vertice v = K2.getRedeBayesiana().getVerticeByNome(Tela.selecionados.get(0).getNome());
        int indice = K2.getRedeBayesiana().getIndiceByVertice(v);
        Vector<Vertice> atributos = new Vector<Vertice>();
        for (int i = 0; i < rb.getVertices().size(); i++) {
            atributos.add((Vertice) rb.getVertices().get(i));
        }
        /**
         *
         */
        Cromossomo crom = maisApto.getCromossomo();
        relatorio = "";
        relatorio += "<br><br>Para a vari&aacute;vel <strong>" + corrigeCofificacao(K2.getRedeBayesiana().getVerticeByIndex(indice_atributo_estudado).getNome()) + "</strong> ,encontrar-se entre " + formataFaixa(nom_faixa) + "<br><br>";// + ", estar com probabilidade de " + df.format(maisApto.getAptidao() * 100) + " %  :\n\n";
        relatorio += "O cen&aacute;rio estudado seria:<br><br>";
        for (int i = 0; i < atributos.size()/* crom.getGenes().size()*/; i++) {
            if (i != indice_atributo_estudado) {
                indice = K2.getRedeBayesiana().getIndiceByVertice(atributos.get(i));
                String[] str = BackAG.obtemFaixasDoAtributo(indice);
                relatorio += " o Atributo: " + corrigeCofificacao(atributos.get(i).getNome()) + " dever&aacute; estar na faixa : " + str[crom.getGenes().get(i)] + "<br><br>";
            }
        }
        for (int i = 0; i < Itens.botoes.length; i++) {
            if (crom.getGenes().get(i) != -1) {
                Itens.botoes[i].botInfer[crom.getGenes().get(i)].fazInf();
                Itens.botoes[i].setJPBar(i, crom.getGenes().get(i));

            }
            if (!Itens.botoes[i].status) {
                Itens.botoes[i].click();
                PainelInferencia.itens.atualizaItens();
            }
        }
        try {
            Actions_Rede.FAT.setClosed(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(AG.class.getName()).log(Level.SEVERE, null, ex);
        }
        Actions_Rede.mostrarRelatorioAg(null,null);

//        System.out.println("" + relatorio);

    }

    private static String corrigeCofificacao(String nome) {

        String aux = "";
        for (int i = 0; i < nome.length(); i++) {
            if (nome.charAt(i) == 'á') {
                aux += "&aacute;";
            } else if (nome.charAt(i) == 'ã') {
                aux += "&atilde;";
            }else if (nome.charAt(i) == 'Ã') {
                aux += "&Atilde;";
            }else if (nome.charAt(i) == 'Á') {
                aux += "&Aacute;";
            } else if (nome.charAt(i) == 'ç') {
                aux += "&ccedil;";
            }else if (nome.charAt(i) == 'Ç') {
                aux += "&Ccedil;";
            } else if (nome.charAt(i) == 'ú') {
                aux += "&uacute;";
            }else if (nome.charAt(i) == 'Ú') {
                aux += "&Uacute;";
            } else if(nome.charAt(i)=='é'){
                aux+="&eacute;";
            }else if(nome.charAt(i)=='É'){
                aux+="&Eacute;";
            }else{
                aux += nome.charAt(i);
            }
        }
        return aux;

    }

    private void aplicaOperadoresGeneticos() {

        Vector<Individuo> newPop = new Vector<Individuo>();


        pop = cruzamento(pop);

        for (Individuo ind : maisAptos) {
            if (ind.getCromossomo().genes.size() < tamCromossomo) {
                ind.getCromossomo().genes.add(indice_atributo_estudado, -1);
            }
            pop.add(ind);
        }

        for (int i = 0; i < pop.size(); i++) {
            if (!maisAptos.contains(pop.get(i))) {
                if (Math.abs(((int) Math.random()) % 1001) <= (probMutacao * 1000)) {
                    mutacao(pop.get(i));
                }
            }
        }


//        for (int i = 0; i < pop.size(); i++) {
//            System.out.println("New pop: " + i + " = " + pop.get(i).getCromossomo().getGenes());
//        }
//



    }

    public void mutacao(Individuo ind) {
        int indice;
        int valor;
        do {
            indice = (int) ((Math.random() * tamCromossomo));
//            System.out.println("MUTA       - "+indice);

        } while (indice == indice_atributo_estudado);
        valor = (int) ((Math.random() * tamCromossomo));

        ind.getCromossomo().getGenes().remove(indice);
        ind.getCromossomo().getGenes().add(indice, valor);

//        System.out.println("MUTACAO - INDIVIDUO: " + ind.getCromossomo().getGenes());
    }

    private Vector<Individuo> cruzamento(Vector<Individuo> populacao) {

        Vector<Individuo> newPop = new Vector<Individuo>();

        for (int k = 0; k < (populacao.size() - maisAptos.size()) / 2; k++) {
//            System.out.println("                          populacao.size(): " + populacao.size());

            Individuo individuo1 = populacao.get(((int) (Math.random() * tamanhoPopulacao)));
            Individuo individuo2 = populacao.get(((int) (Math.random() * tamanhoPopulacao)));

            Cromossomo crm1 = getCrom(individuo1);
            Cromossomo crm2 = getCrom(individuo2);

//            System.out.println("Individuo: 1 " + crm1.genes);
//            System.out.println("Individuo: 2 " + crm2.genes);

            Cromossomo crom1 = new Cromossomo();
            Cromossomo crom2 = new Cromossomo();

            int limite = (int) crm1.getGenes().size() / 2;

//            System.out.println("limite: " + limite);
            int aux = 0;
            for (int i = 0; i < limite; i++) {

                int valor = crm1.getGenes().get(aux);

                crom1.genes.add(valor);
                aux++;
            }

            for (int i = aux; i < crm2.getGenes().size(); i++) {

                int valor = crm2.getGenes().get(i);

                crom1.genes.add(valor);
            }

            crom1.genes.add(indice_atributo_estudado, -1);

            Individuo ind = new Individuo();
            ind.setCromossomo(crom1);
//            System.out.println("CRUZAMENTO : " + k + " = " + ind.getCromossomo().getGenes());
            newPop.add(ind);
            /*-------------------------------------------------------*/
            aux = 0;
            for (int i = 0; i < limite; i++) {

                int valor = crm2.getGenes().get(aux);

                crom2.genes.add(valor);
                aux++;
            }

            for (int i = aux; i < crm2.getGenes().size(); i++) {

                int valor = crm1.getGenes().get(i);

                crom2.genes.add(valor);
            }


            crom2.genes.add(indice_atributo_estudado, -1);



            Individuo ind2 = new Individuo();
            ind2.setCromossomo(crom2);
//            System.out.println("CRUZAMENTO : " + k + " = " + ind2.getCromossomo().getGenes());

            newPop.add(ind2);
        }

        return newPop;

    }

    private Individuo ecolheMaisApto() {

        double maior = 0;
        Individuo ind = null;
        for (int i = 0; i < maisAptos.size(); i++) {
            if (maisAptos.get(i).getAptidao() > maior) {
                ind = maisAptos.get(i);
            }
        }

        return ind;

    }

    private String formataFaixa(String nom_faixa) {

        String[] aux = nom_faixa.split("-");
        return aux[0] + " e " + aux[1];

    }

    private int[] getAtribFx(Cromossomo crom) {
        int[] atribFx = new int[crom.genes.size()];
        int cont = 0;
        int aux;
        for (int i = 0; i < crom.genes.size(); i++) {
            if (crom.genes.get(i) != -1) {
                atribFx[cont] = crom.genes.get(i);
                cont++;
            }
        }
        atribFx[atribFx.length - 1] = -1;

        return atribFx;
    }

    private int[] getAtribInfo(Cromossomo crom) {
        int[] atribInfo = new int[crom.genes.size()];

        int cont = 0;
        int aux = 0;
        for (int i = 0; i < crom.genes.size(); i++) {
            if (crom.genes.get(i) != -1) {
                atribInfo[cont] = i;
                cont++;
            }
        }
        atribInfo[atribInfo.length - 1] = -1;

        return atribInfo;

    }

    private Cromossomo getCrom(Individuo individuo1) {

        Cromossomo c = new Cromossomo();

        Vector<Integer> v = individuo1.getCromossomo().getGenes();
        for (int i = 0; i < v.size(); i++) {
            if (v.get(i) != -1) {
                c.genes.add(v.get(i));
            }
        }
        individuo1.setCromossomo(c);

        return individuo1.getCromossomo();
    }

    private int getEstados(int j) {
        return ((Vertice) rb.getVertices().get(j)).getNEstados();
    }

    private void iniciaCiclo() {

        criaPopulacaoInicial();
//        System.out.println("INICIA CICLO");
        for (int i = 0; i < 100; i++) {
            FrameAG.status.setText(i + " cliclos " + " -> Avaliando...");
//            System.out.println("Inicia Fusao - CLICO: " + i);
            fusion_AG_RB();
//            System.out.println("Avalia POP: - CICLO: " + i);
            avaliaPopulacao(pop);
//            System.out.println("Operadores  - CICLO: " + i);
            aplicaOperadoresGeneticos();
            System.gc();
        }


        Individuo maisApto = ecolheMaisApto();

//        System.out.println("Mais Apto: "+maisApto.getCromossomo().getGenes());
        GeraRelatorio(maisApto);

    }
}
