package br.ufpa.lprad.predict.redebayesiana.analisetemporal;
import br.ufpa.lprad.predict.redebayesiana.aprendizado.K2;
import br.ufpa.lprad.predict.redebayesiana.aprendizado.Vertice;
import java.util.Vector;
/**
 *
 * @author J. Gabriel Lima
 */
public class AnaliseTemporalMarkoviana {
    private Vertice vertice,                       // vertice estudado
                    paiTemporario,                       // vertice pai temporario criado
                    paiInferenciado;               // vertice pai inferenciado
    private String estado;                         // estado do pai inferenciado
    private double matrizTransicaoMarkoviana[][];  // matriz de transicao markoviana
    private int passo;                             // passo q sera elevado a matriz
    private Vector CombEstadosPais;                // Vector com as combinacoes de estados dos pais do vertice estudado e suas probabilidades sem o pai temporario criado

    public AnaliseTemporalMarkoviana(){

    }

    public AnaliseTemporalMarkoviana(Vertice vertice){
        this.vertice = vertice;
        paiTemporario = (Vertice)vertice.getPais().lastElement();
    }

    public AnaliseTemporalMarkoviana(Vertice vertice, Vector CombEstadosPais){
        this.vertice = vertice;
        this.CombEstadosPais = CombEstadosPais;

        paiTemporario = (Vertice)vertice.getPais().lastElement();
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Vertice getPaiInferenciado() {
        return paiInferenciado;
    }

    public void setPaiInferenciado(Vertice paiInferenciado) {
        this.paiInferenciado = paiInferenciado;
    }

    public Vertice getPaiTemp() {
        return paiTemporario;
    }

    public void setPaiTemp(Vertice paiTemporario) {
        this.paiTemporario = paiTemporario;
    }

    public int getPasso() {
        return passo;
    }

    public void setPasso(int passo) {
        this.passo = passo;
    }

    public Vertice getVertice() {
        return vertice;
    }

    public void setVertice(Vertice vertice) {
        this.vertice = vertice;
    }

    public Vector getCombEstadosPais(){
        return CombEstadosPais;
    }

    public void setCombEstadosPais(Vector CombEstadosPais){
        this.CombEstadosPais = CombEstadosPais;
    }

    //faz a Analise Temporal Markoviana para o esse tempo
    public double[] fazAnaliseTemporal(){
        double probs[] = new double[vertice.getNEstados()];
        matrizTransicaoMarkoviana = new double[paiTemporario.getNEstados()][vertice.getNEstados()];

        Vector combinacoes[] = retornaCombinacoes();

        //so pra ficar como na formula...
        int m = vertice.getNEstados();
        int n = combinacoes.length;

        //faz matriz de transicao markoviana
        for(int x=0; x<paiTemporario.getNEstados(); x++){
            //calcula constante normalizadora para o estado x
            double constanteNormalizadora = 0;
            for(int j=0; j<m; j++){
                for(int k=0; k<n; k++){
                    Vector combinacao = (Vector)combinacoes[k].clone();
                    double probPA = getProbabilidadeCombinacao(combinacao);
                    String estadoPaiTemp = paiTemporario.getEstados()[x];
                    combinacao.add(estadoPaiTemp);
                   int indice = K2.getRedeBayesiana().getIndexOfVerticeByNome(vertice.getNome());
                    constanteNormalizadora += vertice.getTabProbCond(vertice.getIndiceCombinacao(combinacao), j) * probPA;
                }
            }

            for(int y=0; y<vertice.getNEstados(); y++){
                double numerador = 0;
                for(int i=0; i<n; i++){
                    Vector combinacao = (Vector)combinacoes[i].clone();
                    double probPA = getProbabilidadeCombinacao(combinacao);
                    String estadoPaiTemp = paiTemporario.getEstados()[x];
                    combinacao.add(estadoPaiTemp);
                    numerador += vertice.getTabProbCond(vertice.getIndiceCombinacao(combinacao), y) * probPA;
                }
                if(constanteNormalizadora != 0)
                    matrizTransicaoMarkoviana[x][y] = numerador/constanteNormalizadora;
                else
                    matrizTransicaoMarkoviana[x][y] = 0;
            }
        }
        matrizPotencia();
        probs = novasProbabilidades();
        probs = arrumaProbabilidades(probs);
        return probs;
    }
    //retorna as probabilidades de entrada arrumadas
    private double[] arrumaProbabilidades(double probs[]){
        double total = 0;
        for(int i=0; i<probs.length; i++)
            total+=probs[i];

        for(int i=0; i<probs.length; i++){
            if(probs[i] != 0)
                probs[i] = probs[i]/total;
            else
                probs[i] = 0;
        }

        //checando...
//        total = 0;
//        for(int i=0; i<probs.length; i++){
//            System.out.println("probs["+i+"]: "+probs[i]);
//            total+=probs[i];
//        }
//        System.out.println("total = "+total);
//
//        System.out.println("-----------------------------------------------------------------------------");

        return probs;
    }

    //retorna as novas probabilidades a partir da matriz de transicao (tese de doutorado do Adamo)
    private double[] novasProbabilidades(){
        double probs[] = new double[vertice.getNEstados()];

        int n = vertice.getNEstados();

        for(int x=0; x<vertice.getNEstados(); x++){
            for(int i=0; i<n; i++){
                probs[x] += matrizTransicaoMarkoviana[i][x] * paiTemporario.getProbabilidade(i);
            }
        }

        return probs;
    }

    //calcula a matriz de transicao no passo n(matriz de transicao elevada a n)
    private void matrizPotencia(){
     //   System.out.println("***************** no passo n ****************************");

        double matriz[][] = this.matrizTransicaoMarkoviana;

        for(int y=1; y<passo; y++){
            double result[][] = new double[paiTemporario.getNEstados()][vertice.getNEstados()];
            for(int i=0; i<paiTemporario.getNEstados(); i++){
                for(int j=0; j<vertice.getNEstados(); j++){
                    for(int m=0; m<matriz.length; m++){
                        result[i][j] += matriz[i][m] * matrizTransicaoMarkoviana[m][j];
                    }
                }
            }
            matriz = result;
        }


        matrizTransicaoMarkoviana = matriz;
    }

    //retorna a probabilidade Pa(sendo a probabilidade dessa combinacao de estados dos pais)
    private double getProbabilidadeCombinacao(Vector combinacao) {
        double prob = 1;
        for(int i=0; i<CombEstadosPais.size(); i++){
            boolean ehEssa = true;
            for(int j=0; j<combinacao.size(); j++){
                if( !(combinacao.elementAt(j).toString().equalsIgnoreCase( ((CombinacaoEstadosPais)CombEstadosPais.elementAt(i)).getEstados()[j] )) ){
                    ehEssa = false;
                    break;
                }
            }
            if(ehEssa)
                prob = ((CombinacaoEstadosPais)CombEstadosPais.elementAt(i)).getProbabilidadeDaCombinacao();
        }

        return prob;
    }

    //retorna as combinacoes possiveis entre os estados dos pais do vertice sem o pai temporario
    private Vector[] retornaCombinacoes(){
        Vector vetor = (Vector)vertice.getPais().clone();

        //remove o ultimo pai que eh o pai temporario criado
        vetor.remove(vetor.size()-1);

        //descobre o total das combinacoes
        int tamanho = 1;
        for(int i=0; i<vetor.size(); i++){
            if(paiInferenciado != null){
                if( !(paiInferenciado.getNome().equalsIgnoreCase( ((Vertice)vetor.elementAt(i)).getNome() ) ) )
                    tamanho *= ((Vertice)vetor.elementAt(i)).getNEstados();
            }
            else
                tamanho *= ((Vertice)vetor.elementAt(i)).getNEstados();
        }

//        System.out.println("tamanho = "+tamanho);

        Vector comb[] = new Vector[tamanho];
        for(int i=0; i<comb.length; i++)
            comb[i] = new Vector();

        //faz todas as combinacoes possiveis
        int r=tamanho;
        for(int i=0; i<vetor.size(); i++){
            if(paiInferenciado != null){
                if( (paiInferenciado.getNome().equalsIgnoreCase( ((Vertice)vetor.elementAt(i)).getNome() ) ) ){
                    for(int j=0; j<comb.length; j++)
                        comb[j].add(estado);
                }
                else{
                    r/=((Vertice)vetor.elementAt(i)).getNEstados();
                    int cont=1;
                    int indice = 0;
                    for(int j=0; j<comb.length; j++,cont++){
                        comb[j].add(((Vertice)vetor.elementAt(i)).getEstados()[indice]);
                        if(cont==r){
                            indice++;
                            cont=0;
                            if(indice==((Vertice)vetor.elementAt(i)).getNEstados())
                                indice = 0;
                        }
                    }
                }
            }
            else{
                r/=((Vertice)vetor.elementAt(i)).getNEstados();
                int cont=1;
                int indice = 0;
                for(int j=0; j<comb.length; j++,cont++){
                    comb[j].add(((Vertice)vetor.elementAt(i)).getEstados()[indice]);
                    if(cont==r){
                        indice++;
                        cont=0;
                        if(indice==((Vertice)vetor.elementAt(i)).getNEstados())
                            indice = 0;
                    }
                }
            }
        }

        return comb;
    }
    public static void main(String args[]){
    double probEstudo[] = {0.133, 0.534, 0.333};
    double probNota[] = {0.210, 0.467, 0.323};
    double probNotaAnt[] = {0.333, 0.333, 0.333};

    double probCond[][] = {{0.934, 0.033, 0.033},
                           {0.333, 0.333, 0.333},
                           {0.333, 0.333, 0.333},
                           {0.491, 0.491, 0.018},
                           {0.033, 0.934, 0.033},
                           {0.018, 0.491, 0.491},
                           {0.333, 0.333, 0.333},
                           {0.018, 0.491, 0.491},
                           {0.033, 0.033, 0.934}};

        Vertice estudo = new Vertice();
        Vertice nota = new Vertice();
        Vertice nota_ant = new Vertice();

        String estudo_estados[] = {"Muito", "Médio", "Pouco"};
        String nota_estados[] = {"Excelente", "Bom", "Regular"};


        estudo.setNome("Estudo");
        estudo.setEstados(estudo_estados);
        estudo.setNEstados(estudo_estados.length);
        estudo.setFilhos(nota);
        estudo.setNFilhos(1);
        estudo.setProbabilidade(probEstudo);


        nota_ant.setNome("Nota-1");
        nota_ant.setEstados(nota_estados);
        nota_ant.setNEstados(nota_estados.length);
        nota_ant.setFilhos(nota);
        nota_ant.setNFilhos(1);
        nota_ant.setProbabilidade(probNotaAnt);


        nota.setNome("Nota");
        nota.setEstados(nota_estados);
        nota.setNEstados(nota_estados.length);
        nota.setPais(estudo);
        nota.setPais(nota_ant);
        nota.setNPais(2);
        nota.setProbabilidade(probNota);
        nota.setTabProbCond(probCond);

        Vector probCombPais = new Vector();
        for(int i=0; i<estudo.getNEstados(); i++){
            String estados[] = new String[1];
            estados[0] = estudo.getEstados()[i];
            CombinacaoEstadosPais nova = new CombinacaoEstadosPais();
            nova.setEstados(estados);
            nova.setProbabilidadeDaCombinacao(estudo.getProbabilidade(i));
            probCombPais.add(nova);
        }

        AnaliseTemporalMarkoviana q = new AnaliseTemporalMarkoviana();
        q.setVertice(nota);
        q.setPasso(3);
        q.setPaiTemp(nota_ant);
        q.setCombEstadosPais(probCombPais);
        double p[] = q.fazAnaliseTemporal();
        double total = 0;
        System.out.println("Probabilidades:");
        for(int i=0; i<p.length; i++){
            System.out.print(p[i]+"  ");
            total+=p[i];
        }
        System.out.println("\ntotal = "+total);

        System.out.println("\n");

        q.setPaiInferenciado(estudo);
        q.setEstado("Médio");
        p = q.fazAnaliseTemporal();
        total = 0;
        System.out.println("Probabilidades:");
        for(int i=0; i<p.length; i++){
            System.out.print(p[i]+"  ");
            total+=p[i];
        }
        System.out.println("\ntotal = "+total);

        System.out.println("");
    }
}