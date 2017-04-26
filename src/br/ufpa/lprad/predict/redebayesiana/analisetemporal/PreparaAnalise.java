/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpa.lprad.predict.redebayesiana.analisetemporal;

import br.ufpa.lprad.predict.propriedades.PredictPropriedades;
import br.ufpa.lprad.predict.redebayesiana.aprendizado.Vertice;
import br.ufpa.lprad.predict.redebayesiana.io.InterfaceTXT;
import br.ufpa.lprad.predict.util.BigDecimalUtility;
import java.io.File;
import java.math.BigDecimal;
import java.util.Vector;
import javax.swing.JTable;

/**
 *
 * @author J. Gabriel Lima
 */

/*
 * classe que:
 *  1 - pega os dados do vertice e do seus pais para analise
 *  2 - discretiza
 *  3 - elimina primeiro e ultimo registro
 *  4 - gera as probabilidades das combinacoes dos estados dos pais do vertice
 *  5 - cria pai temporario
 *  6 - calcula as probabilidades a priori do pai temporario
 *  7 - calcula as probabilidades condicionais do vertice com o pai temporario
 */
public class PreparaAnalise {
    private Vertice vertice,             //vertice estudado
                    paiTemp;             //vertice que sera criado na classe (vertice estudando no tempo anterior)
    private Vector CombEstadosPais;      //Vector com as combinacoes de estados dos pais do vertice estudado e suas probabilidades sem o pai temporario criado
    private String dados[][];            //matriz com os dados do vertice e de seus pais
    private String dadosDiscretos[][];   //matriz com dados discretizados
    
    public PreparaAnalise(Vertice vertice){
        this.vertice = vertice;
        
//        System.out.println("\n************** Class PreparaAnalise ***************");
//        System.out.println("vertice : "+vertice.getNome());
//        System.out.println("numero de Pais: "+vertice.getNPais());
//        System.out.println("Pais: ");
//        for(int i=0; i<vertice.getNPais(); i++)
//            System.out.println(((Vertice)vertice.getPais().elementAt(i)).getNome());
        
        pegaDados();
        discretiza();
        elimina();
        
        fazCombEstadosPais();
        
//        System.out.println("Class PreparaAnalise");
//        System.out.println("vertice : "+vertice.getNome()+"   num estados = "+vertice.getNEstados());
//        System.out.println("Pais: ");
//        for(int i=0; i<vertice.getNPais(); i++)
//            System.out.println(((Vertice)vertice.getPais().elementAt(i)).getNome()+"   num estados = "+((Vertice)vertice.getPais().elementAt(i)).getNEstados());
        
        criaPaiTemporario();
        
//        fazAnalise();

    }
    
    private void pegaDados(){
            InterfaceTXT inter = new InterfaceTXT(new File(PredictPropriedades.getCaminhoCorrelacao()));
            JTable tabela=inter.dataToTable();
            
            dados = new String[vertice.getNPais()+2][tabela.getRowCount()+1];
            dadosDiscretos = new String[tabela.getRowCount()+1][vertice.getNPais()+2];
            
            //preenche a primira linha com os dados do vertice
            //e a ultima com os dados para o pai temporario
            for(int j=0; j<tabela.getColumnCount(); j++){
                if( tabela.getColumnName(j).contains(vertice.getNome()) ){
                    for(int i=0; i<tabela.getRowCount(); i++){
                        dados[0][i] = tabela.getValueAt(i, j).toString();
                        dados[vertice.getNPais()+1][i+1] = tabela.getValueAt(i, j).toString();
                    }
                    break;
                }
            }
            
            //preenche as linhas restantes com os dados dos pais do vertice
            for(int k=0; k<vertice.getNPais(); k++){
                for(int j=0; j<tabela.getColumnCount(); j++){
                    if( ((Vertice)vertice.getPais().elementAt(k)).getNome().equalsIgnoreCase(tabela.getColumnName(j)) ){
                        for(int i=0; i<tabela.getRowCount(); i++)
                            dados[k+1][i] = tabela.getValueAt(i, j).toString();
                        break;
                    }
                }
            }
            
            //verifica se preencheu a tabela
            //se nao preencheu tenta outra busca
            for(int i=0; i<(dados.length-1); i++){
                if(dados[i][0] == null){
                    String nome[];
                    if(i == 0)
                        nome = vertice.getNome().split("_");
                    else
                        nome = ((Vertice)vertice.getPais().elementAt(i-1)).getNome().split("_");
                    boolean achou = false;
                    for(int j=0; j<nome.length; j++){
                        for(int k=0; k<tabela.getColumnCount(); k++){
                            if( tabela.getColumnName(k).contains(nome[j]) ){
                                achou = true;
                                for(int l=0; l<tabela.getRowCount(); l++)
                                    dados[i][l] = tabela.getValueAt(l, k).toString();
                            }
                            if(achou)
                                break;
                        }
                        if(achou)
                            break;
                    }
                }
            }
            
//            System.out.println("dados[][]:");
//            for(int i=0; i<dados.length; i++){
//                for(int j=0; j<dados[i].length; j++)
//                    System.out.print(dados[i][j]+"   ");
//                System.out.println("");
//            }
            
    }
    
    //discretiza os dados armazenando-os em dadosDiscretos
    private void discretiza(){
        for(int i=0; i<dados.length; i++){
            System.out.println("dados["+i+"]: "+dados[i]);
            double menor = descobreMenor(dados[i]);
            double maior = descobreMaior(dados[i]);
            
            double diferenca = (maior-menor)/5;
            
//            System.out.println("diferenca = "+diferenca);
            
            //gera valores inferiores e superiores das faixas de cada atributo
            double faixasInferiores[] = new double[5];
            double faixasSuperiores[] = new double[5];
            
            faixasInferiores[0] = menor;
            faixasSuperiores[0] = menor + diferenca;
            for(int k=1; k<5; k++){
                faixasInferiores[k] = faixasSuperiores[k-1];
                if(k < 4)
                    faixasSuperiores[k] = faixasInferiores[k] + diferenca;
                else
                   faixasSuperiores[k] = maior; 
            }
            
//            System.out.println("Faixas: ");
//            for(int k=0; k<faixasInferiores.length; k++)
//                System.out.println("faixasInferiores["+k+"]: "+faixasInferiores[k]+"   faixasSuperiores["+k+"]: "+faixasSuperiores[k]);
            
            //descobre a faixa de cada valor
            for(int j=0; j<dados[i].length; j++){
                double valor;
                try{
//                    valor = Double.parseDouble(dados[i][j]);
                    valor = BigDecimalUtility.setFormattedCurrency(dados[i][j]).doubleValue();
                }
                catch(NullPointerException e){
                    valor = -1;
                }
                for(int k=0; k<faixasInferiores.length; k++){
                    if(k<faixasInferiores.length-1){
                        if( valor >= faixasInferiores[k] && valor < faixasSuperiores[k] )
                            dadosDiscretos[j][i] = Integer.toString(k);
                    }
                    else{
                        if( valor >= faixasInferiores[k] && valor <= faixasSuperiores[k] )
                            dadosDiscretos[j][i] = Integer.toString(k);
                    }
                }
            }
        }
        
//        System.out.println("dadosDiscretos[][]:");
//        for(int i=0; i<dadosDiscretos.length; i++){
//            for(int j=0; j<dadosDiscretos[i].length; j++)
//                System.out.print(dadosDiscretos[i][j]+"   ");
//            System.out.println("");
//        }
        
    }
    
    private double descobreMenor(String dados[]){
        double menor;
        try{
//            menor = Double.parseDouble(dados[0].replace(",", "."));
            menor = BigDecimalUtility.setFormattedCurrency(dados[0]).doubleValue();
        }
        catch(NullPointerException e){
//            menor = Double.parseDouble(dados[1]);
            menor = BigDecimalUtility.setFormattedCurrency(dados[1]).doubleValue();
        }
        
        for(int i=1; i<dados.length; i++){
            double valor;
            try{
//                valor = Double.parseDouble(dados[i].replace(",", "."));
                valor = BigDecimalUtility.setFormattedCurrency(dados[i]).doubleValue();
            }
            catch(NullPointerException e){
                break;
            }
            if(menor > valor)
                menor = valor;
        }
        
//        System.out.println("menor = "+menor);
        return menor;
    }
    
    private double descobreMaior(String dados[]){
        double maior;
        try{
            maior = BigDecimalUtility.setFormattedCurrency(dados[0]).doubleValue();//);//dados[0].replace(",", "."));
        }
        catch(NullPointerException e){
//            maior = Double.parseDouble(dados[1].replace(",", "."));
            maior = BigDecimalUtility.setFormattedCurrency(dados[1]).doubleValue();//replace(",", "."));
        }
        
        for(int i=1; i<dados.length; i++){
            double valor;
            try{
//                valor = Double.parseDouble(dados[i]);
                valor = BigDecimalUtility.setFormattedCurrency(dados[i]).doubleValue();
            }
            catch(NullPointerException e){
                break;
            }
            if(maior < valor)
                maior = valor;
        }
        
//        System.out.println("maior = "+maior);
        return maior;
    }
    
    //elimina o primeiro e o ultimo registro de dadosDiscretos
    private void elimina(){
        String matriz[][] = new String[dadosDiscretos.length-2][dadosDiscretos[0].length];
        
        for(int i=1; i<dadosDiscretos.length-1; i++){
            for(int j=0; j<dadosDiscretos[i].length; j++)
                matriz[i-1][j] = dadosDiscretos[i][j]; 
        }
        
        dadosDiscretos = matriz;
        
//        System.out.println("depois de eliminar primeiro e ultimo registro");
//        System.out.println("dadosDiscretos[][]:");
//        for(int i=0; i<dadosDiscretos.length; i++){
//            for(int j=0; j<dadosDiscretos[i].length; j++)
//                System.out.print(dadosDiscretos[i][j]+"   ");
//            System.out.println("");
//        }
        
    }
    
    //calcula as probabilidades ce cada combinacao de estados dos pais do vertice semo pai temporario
    private void fazCombEstadosPais(){
        vertice.setNEstados(5);
        String estados[] = {"0","1","2","3","4"};
        vertice.setEstados(estados);
        
        for(int i=0; i<vertice.getNPais(); i++){
            ((Vertice)vertice.getPais().elementAt(i)).setNEstados(5);
            ((Vertice)vertice.getPais().elementAt(i)).setEstados(estados);
        }
        estados = null;
        
//        System.out.println("vertice : "+vertice.getNome()+"   num estados = "+vertice.getNEstados());
//        System.out.println("Pais: ");
//        for(int i=0; i<vertice.getNPais(); i++)
//            System.out.println(((Vertice)vertice.getPais().elementAt(i)).getNome()+"   num estados = "+((Vertice)vertice.getPais().elementAt(i)).getNEstados());
        
        Vector comb[] = vertice.getCombinacoes();
        
//        System.out.println("fazCombEstadosPais()");
//        for(int i=0; i<comb.length; i++){
//            System.out.print("comb["+i+"]: ");
//            for(int j=0; j<comb[i].size(); j++)
//                System.out.print(comb[i].elementAt(j)+"   ");
//            System.out.println("");
//        }
         
        //Welcome To The Jungle
        CombEstadosPais = new Vector();
        estados = new String[vertice.getNPais()];
        for(int i=0; i<comb.length; i++){
            for(int j=0; j<comb[i].size(); j++)
                estados[j] = comb[i].elementAt(j).toString();
            
            CombinacaoEstadosPais nova = new CombinacaoEstadosPais(estados);
            double cont = 0;
            for(int k=0; k<dadosDiscretos.length; k++){
                boolean ehEssa = true;
                for(int j=0; j<nova.getEstados().length; j++){
                    if( !(nova.getEstados()[j].equalsIgnoreCase(dadosDiscretos[k][j+1])) ){
                        ehEssa = false;
                        break;
                    }
                }
                if(ehEssa)
                    cont++;
            }
            double prob = cont/dadosDiscretos.length;
            nova.setProbabilidadeDaCombinacao(prob);
            CombEstadosPais.add(nova);
        }
        
//        double total = 0;
//        System.out.println("CombEstadosPais");
//        for(int i=0; i<CombEstadosPais.size(); i++){
//            System.out.print("CombEstadosPais["+i+"]: ");
//            for(int j=0; j<((CombinacaoEstadosPais)CombEstadosPais.elementAt(i)).getEstados().length; j++ )
//                System.out.print(((CombinacaoEstadosPais)CombEstadosPais.elementAt(i)).getEstados()[j]+"   ");
//            System.out.print("prob = "+((CombinacaoEstadosPais)CombEstadosPais.elementAt(i)).getProbabilidadeDaCombinacao());
//            total += ((CombinacaoEstadosPais)CombEstadosPais.elementAt(i)).getProbabilidadeDaCombinacao();
//            System.out.println("");
//        }
//        System.out.println("total = "+total);
        
    }
    
    //cria pai temporario
    private void criaPaiTemporario(){
        paiTemp = new Vertice();
        paiTemp.setNome(vertice.getNome()+"-1");
        paiTemp.setNEstados(vertice.getNEstados());
        paiTemp.setEstados(vertice.getEstados());
        paiTemp.setFilhos(vertice);
        paiTemp.setNFilhos(1);
        
        calculaProbabilidades();
        
        vertice.setPais(paiTemp);
        vertice.setNPais(vertice.getNPais()+1);
        
        calculaNovasProbCond();
    }
    
    //calcula probabilidades a priori do vertice criado
    private void calculaProbabilidades(){
//        System.out.println("calculaProbabilidades()");
//        System.out.println("indice = "+(vertice.getNPais()+1));
        
        double probs[] = new double[paiTemp.getNEstados()];
        for(int i=0; i<paiTemp.getNEstados(); i++){
            double cont = 0;
            for(int j=0; j<dadosDiscretos.length; j++){
                if(paiTemp.getEstados()[i].equalsIgnoreCase(dadosDiscretos[j][vertice.getNPais()+1]))
                    cont++;
            }
            probs[i] = cont/dadosDiscretos.length;
        }
        
//        double total = 0;
//        System.out.println("probs[]");
//        for(int i=0; i<probs.length; i++){
//            System.out.println("probs["+i+"]: "+probs[i]);
//            total+=probs[i];
//        }
//        System.out.println("total = "+total);
        
        paiTemp.setProbabilidade(probs);
    }
    
    //calcula as probabilidades condicinais do vertice com pai temporario
    private void calculaNovasProbCond(){
//        System.out.println("calculaNovasProbCond()");
        
        Vector comb[] = vertice.getCombinacoes();
        
//        for(int i=0; i<comb.length; i++){
//            System.out.print("comb["+i+"]: ");
//            for(int j=0; j<comb[i].size(); j++)
//                System.out.print(comb[i].elementAt(j)+"   ");
//            System.out.println("");
//        }
        
        double tabProbcond[][] = new double[comb.length][vertice.getNEstados()];
        
        //PQP!!!
        //montando a tabela...
        for(int i=0; i<vertice.getNEstados(); i++){
            String estados[] = new String[comb[0].size()+1];
            estados[0] = vertice.getEstados()[i];
            for(int j=0; j<comb.length; j++){
                for(int k=0; k<comb[j].size(); k++)
                    estados[k+1] = comb[j].elementAt(k).toString();
                
                double cont = 0;
                for(int k=0; k<dadosDiscretos.length; k++){
                    boolean ehEssa = true;
                    
                    for(int l=0; l<dadosDiscretos[k].length; l++){
                        if( !(estados[l].equalsIgnoreCase(dadosDiscretos[k][l])) ){
                            ehEssa = false;
                            break;
                        }
                    }
                    if(ehEssa)
                        cont++;
                }
                
                tabProbcond[j][i] = cont/dadosDiscretos.length;
            }
        }
        
        vertice.setTabProbCond(tabProbcond);
        
//        //PQP2!!!
//        //ate pra verificar eh essa po#@$%$ eh FDP!!!
//        System.out.println("tabProbcond[][]");
//        double totalColunas[] = new double[vertice.getNEstados()];
//        double totalDoTotalLinhas = 0;
//        for(int i=0; i<totalColunas.length; i++)
//            totalColunas[i] = 0;
//
//        for(int i=0; i<tabProbcond.length; i++){
//            double totalLinha = 0;
//            for(int j=0; j<tabProbcond[i].length; j++){
//                System.out.print(tabProbcond[i][j]+"   ");
//                totalLinha += tabProbcond[i][j];
//                totalColunas[j] += tabProbcond[i][j];
//            }
//            totalDoTotalLinhas += totalLinha;
//            System.out.println("total = "+totalLinha);
//        }
//        System.out.println("totalDoTotalLinhas = "+totalDoTotalLinhas);
//
//        double totalDoTotalColunas = 0;
//        System.out.println("total colunas:");
//        for(int i=0; i<totalColunas.length; i++){
//            System.out.print(totalColunas[i]+"   ");
//            totalDoTotalColunas += totalColunas[i];
//        }
//        System.out.println("");
//        System.out.println("totalDoTotalColunas = "+totalDoTotalColunas);
        
    }
    
    //faz Analise Temporal Markoviana de acordo dados selecionados(tempo, pai inferenciado, estado do pai)
    public double[] fazAnalise(int passo, Vertice paiInferenciado, String estado){
        AnaliseTemporalMarkoviana markov = new AnaliseTemporalMarkoviana();
        markov.setVertice(vertice);
        markov.setPaiTemp(paiTemp);
        markov.setPasso(passo);
        markov.setCombEstadosPais(CombEstadosPais);
        
        markov.setPaiInferenciado(paiInferenciado);
        markov.setEstado(estado);
        
        double probs[] = markov.fazAnaliseTemporal();

        vertice.tirar();
        vertice.setNPais(vertice.getNPais()-1);
        
        return probs;
    }

}
