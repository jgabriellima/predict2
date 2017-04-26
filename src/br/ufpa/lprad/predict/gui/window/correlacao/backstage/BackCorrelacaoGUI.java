/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.gui.window.correlacao.backstage;

import br.ufpa.lprad.predict.actions.Action;
import br.ufpa.lprad.predict.correlacao.io.AbreXMLCorrelacao;
import br.ufpa.lprad.predict.correlacao.CorrelacaoBean;
import br.ufpa.lprad.predict.correlacao.io.GeraXMLCorrelacao;
import br.ufpa.lprad.predict.propriedades.PredictPropriedades;
import br.ufpa.lprad.predict.redebayesiana.aprendizado.K2;
import br.ufpa.lprad.predict.redebayesiana.aprendizado.RedeBayesiana;
import br.ufpa.lprad.predict.redebayesiana.io.AbrirXML;
import br.ufpa.lprad.predict.redebayesiana.io.GravaXML;
import br.ufpa.lprad.predict.redebayesiana.io.VerificaBaseDados;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author J. Gabriel Lima
 */
public class BackCorrelacaoGUI {

    public static Vector<CorrelacaoBean> correlacoes = new Vector<CorrelacaoBean>();
    public static Vector<CorrelacaoBean> correlacoesPadrao = new Vector<CorrelacaoBean>();
    public static int nCorrelacoes;
    public static int nCorrelacaoPadrao;
    public static String nomeArquivoImportado;
    public static File arquivoImportado;
    public static boolean importado;
    public static RedeBayesiana rb;
    private static File f;
    public static K2 k2;

    public static void copiaArquivoImportado(File arquivo) {
        nomeArquivoImportado = arquivo.getName();

        try {
            FileChannel oriChannel = new FileInputStream(arquivo).getChannel();
            FileChannel destChannel = new FileOutputStream(PredictPropriedades.getCaminhoCorrelacao()).getChannel();
            destChannel.transferFrom(oriChannel, 0, oriChannel.size());
            oriChannel.close();
            destChannel.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        importado = true;
    }

    public static boolean isImportado() {
        return importado;
    }

    public static boolean contem(String text) {

        AbreXMLCorrelacao.abrirXmlCorrelacao();
        for (int i = 0; i < correlacoes.size(); i++) {
            if (correlacoes.get(i).getNome().equalsIgnoreCase(text)) {
                return true;
            }
        }
        return false;
    }

    public static int getNCorrelacaoPadrao() {
        return nCorrelacaoPadrao;
    }

    public static String[] retiraPeriodo(String[] cabecalhoPlanilhaConsumo) {
        String[] str;
        Vector<String> vetor = new Vector<String>();

        for (int i = 1; i < cabecalhoPlanilhaConsumo.length; i++) {
            vetor.add(cabecalhoPlanilhaConsumo[i]);
        }

        str = new String[vetor.size()];

        for (int i = 0; i < str.length; i++) {
            str[i] = vetor.get(i);
        }

        return str;
    }

    public static void salvarCorrelacao(String text, Vector list_atributos, Vector<String> tipoCosumo) {

        String[] listaConsumo = getListaConsumo(list_atributos, tipoCosumo);

        CorrelacaoBean cb = new CorrelacaoBean(text, listaConsumo);

        AbreXMLCorrelacao.abrirXmlCorrelacao();
        correlacoes.add(cb);
        GeraXMLCorrelacao.gerarXmlCorrelacao();

    }

    public static void excluirCorrelacao(String nome) {
        AbreXMLCorrelacao.abrirXmlCorrelacao();
        for (int i = 0; i < correlacoes.size(); i++) {
            if (correlacoes.get(i).getNome().equalsIgnoreCase(nome)) {
                correlacoes.remove(i);
                break;
            }
        }
        GeraXMLCorrelacao.gerarXmlCorrelacao();
    }

    public static String[] converteObjectString(Object[] objs) {

        String[] str = new String[objs.length];
        for (int i = 0; i < str.length; i++) {
            str[i] = String.valueOf(objs[i]);
        }
        return str;
    }

    public static String[] getCorrelacoesArquivo() {

        AbreXMLCorrelacao.abrirXmlCorrelacao();

        String[] result = new String[correlacoes.size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = correlacoes.get(i).getNome();
        }

        return result;

    }

    public static Object[] getAtributosCorrelacao(String nome) {
        CorrelacaoBean cb = null;
        Vector<String> atributos = new Vector<String>();
        try {
            cb = getCorrelacao(nome);

            for (int i = 0; i < cb.getAtrib().length; i++) {
                atributos.add(cb.getAtrib()[i].split("-")[0]);
            }



        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return atributos.toArray();
    }

    public static Vector<String> getTiposAtributosCorrelacao(String nome) {

        AbreXMLCorrelacao.abrirXmlCorrelacao();
        CorrelacaoBean cb = null;
        try {
            cb = getCorrelacao(nome);
        } catch (Exception ex) {
            Logger.getLogger(BackCorrelacaoGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        Vector<String> vetor = new Vector<String>();

        for (int i = 0; i < cb.getAtrib().length; i++) {
            vetor.add(cb.getAtrib()[i].split("-")[1]);
        }

        return vetor;

    }

    public static void setNCorrelacaoPadrao(int nCorrelacaoPadrao) {
        BackCorrelacaoGUI.nCorrelacaoPadrao = nCorrelacaoPadrao;
    }

    public static Vector getCorrelacoesPadrao() {
        return correlacoesPadrao;
    }

    public static void setCorrelacoesPadrao(Vector correlacoesPadrao) {
        BackCorrelacaoGUI.correlacoesPadrao = correlacoesPadrao;
    }

    public static Vector getCorrelacoes() {
        return correlacoes;
    }

    public static void setCorrelacoes(Vector correlacoes) {
        BackCorrelacaoGUI.correlacoes = correlacoes;
    }

    public static int getNCorrelacoes() {
        return nCorrelacoes;
    }

    public static void setNCorrelacoes(int nCorrelacoes) {
        BackCorrelacaoGUI.nCorrelacoes = nCorrelacoes;
    }

    public static String[] retornaNomesCorrelacao() throws Exception {
        try {

            AbreXMLCorrelacao.abrirXmlCorrelacao();

            String[] result = new String[getCorrelacoes().size()];

            for (int i = 0; i < getCorrelacoes().size(); i++) {
                result[i] = ((CorrelacaoBean) getCorrelacoes().get(i)).getNome();
            }
            return result;
        } catch (Exception e) {
            throw new Exception("Erro ao povoar os itens de correlação");
        }
    }

    public static CorrelacaoBean getCorrelacao(String nome) throws Exception {
        try {
            AbreXMLCorrelacao.abrirXmlCorrelacao();
            CorrelacaoBean cb = null;
            for (Object obj : getCorrelacoes()) {
                cb = (CorrelacaoBean) obj;
                if (cb.getNome().equalsIgnoreCase(nome)) {
                    break;
                }
            }
            return cb;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception();
        }
    }

    public static int getIdCorrelacao(String nome) {

        int indice = 0;
        for (int i = 0; i < getCorrelacoes().size(); i++) {
            if (((CorrelacaoBean) getCorrelacoes().get(i)).getNome().equalsIgnoreCase(nome)) {
                indice = i;
                break;
            }

        }
        return indice;
    }

    private static String[] getListaConsumo(Vector list, Vector<String> tipoCosumo) {
        String[] valores = new String[list.size()];

        for (int i = 0; i < valores.length; i++) {

            valores[i] = String.valueOf(list.get(i)) + "-" + tipoCosumo.get(i);
//            System.out.println("valores[" + i + "]: " + valores[i]);
        }
        return valores;
    }

    public static void go(final String s) {
//        new Thread() {

//            @Override
//            public void run() {
                VerificaBaseDados VBD = new VerificaBaseDados();
                f = new File(PredictPropriedades.getCaminhoDiretorioXML() + "\\" + s.replace("ó", "o").replace("ô", "o").replace("-", "") + ".xml");
                if (!VBD.foiModificado) {
//                if (true) {
                    int escolha = JOptionPane.showOptionDialog(null, "A base de dados foi modificada. Deseja Atualizar a estrutura da Rede Bayesiana?", "Atenção", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]{"Sim", "Não"}, null);
                    switch (escolha) {
                        case 0:
                             {
                                JOptionPane.showMessageDialog(null, "Essa operação pode durar alguns minutos", "", JOptionPane.INFORMATION_MESSAGE);
                                refazAprendizagem(s);
                            }
                            break;
                        case 1:
                            utilizaArquivo(s);
                            break;
                    }
                } else {
                    utilizaArquivo(s);
                }
                Action.AbrirRedeBayesiana();
//            }
//        }.start();
    }

    // quando a base de dados não foi modificada
    public static void utilizaArquivo(String s) {
        if (f.exists()) {
            new AbrirXML(f);
            rb = AbrirXML.getRedeBaysiana();
        } else {
            new K2(PredictPropriedades.getCaminhoCorrelacao());
            BackCorrelacaoGUI.rb = K2.getRedeBayesiana();
            new GravaXML(s, K2.getRedeBayesiana());
        }
    }

    public static void refazAprendizagem(String s) {
        new K2(PredictPropriedades.getCaminhoCorrelacao());
        BackCorrelacaoGUI.rb = K2.getRedeBayesiana();
        new GravaXML(s, BackCorrelacaoGUI.rb);

    }
}
