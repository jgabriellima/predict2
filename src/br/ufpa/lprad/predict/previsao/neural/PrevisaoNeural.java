package br.ufpa.lprad.predict.previsao.neural;

import br.ufpa.lprad.predict.actions.Action;
import br.ufpa.lprad.predict.gui.window.backstage.GerenciadorDesktop;
import br.ufpa.lprad.predict.gui.window.previsao.TreinamentoRNA;
import br.ufpa.lprad.predict.leitura.ControleLeitura;
import br.ufpa.lprad.predict.previsao.bean.PrevisaoBean;
import br.ufpa.lprad.predict.previsao.rna.RNA;
import br.ufpa.lprad.predict.propriedades.PredictPropriedades;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author J. Gabriel Lima
 */
public class PrevisaoNeural {

    private boolean isTreinada;
    private String nomeConsumo;
    private String tipoConsumo;
    private ControleLeitura cons;
    private String path;
    private static List<double[]> resultado;
    private String naoExisteMsg = "não aferido!";
    private static String[][] relatorioSimples;
    private String InexistenteMsg = "inexistente";
    private List<String> erros;
    NumberFormat nf;
    private static DecimalFormat df;
    public static String erroGeral;
    public static Vector<String> periodoDados;
    public static Vector<String> historicoDados;
    public static Vector<String> previstoDados;
    public static TreinamentoRNA treinamentoRNA;

    public PrevisaoNeural(String tipoConsumo, String nomeConsumo) {
        try {
            cons = new ControleLeitura();
        } catch (Exception e) {
        }
        nf = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
        df = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
        df.applyPattern("#.##");
        /**
         * Verifica se a rede já está treinada
         */
        //mensagem
        String msg = "Você precisará realizar o treinamento da Rede Neural Artificial para o consumo " + nomeConsumo + " - " + tipoConsumo + ".\n" +
                "Esse processo poderá se estender por vários minutos. \nDeseja iniciar o processo de treinamento?";

        this.nomeConsumo = nomeConsumo;
        this.tipoConsumo = tipoConsumo;
        this.isTreinada = isTreinada();
        if (!isTreinada) {
            int result = JOptionPane.showOptionDialog(null, msg, "Aviso", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]{"Sim", "Não"}, null);
            if (result == 0) {
                treinamentoRNA = new TreinamentoRNA();
                Action.actionIniciarTreinamento(treinamentoRNA);
                getTreinamentoRNA();
            }
        } else {
            roda();
        }

    }

    private double[] converteLista(List<double[]> lista) {

        double[] result = new double[lista.size() * lista.get(0).length];
        int cont = 0;
        for (int i = 0; i < lista.size(); i++) {
            for (int j = 0; j < lista.get(i).length; j++) {
                result[cont] = lista.get(i)[j] * RNA.FatorNormalizacao;
                cont++;
            }
        }
        return result;
    }

    private void getTreinamentoRNA() {
        /**
         * Verifica os anos
         */
        String[] anos;
        try {
            anos = new ControleLeitura().getAnos(tipoConsumo);
        } catch (Exception e) {
            anos = new String[3];
            anos[0] = "1991";
            anos[1] = "2005";
            anos[2] = "2006";
        }
        int ano_inicial = Integer.valueOf(anos[0]);
        int ano_final = Integer.valueOf(anos[anos.length - 3]);
        int ano_teste = ano_final + 1;

        /**
         * Gera base para treinamento
         */
        String s = PredictPropriedades.getCaminhoArquivoEntradaNeural() + tipoConsumo;
        boolean gravado = IONeural.gravaArquivo(s, this.tipoConsumo, this.nomeConsumo, IONeural.getConsumo(this.tipoConsumo, this.nomeConsumo), ano_teste);

        if (!gravado) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao gerar a base de treinamento.\n Tente novamente. Se o problema persistir, contate o suporte do sistema.");
        } else {
            RNA rede = new RNA(PredictPropriedades.getCaminhoArquivoEntradaNeural() + this.tipoConsumo.concat("\\" + nomeConsumo), PredictPropriedades.getCaminhoArquivoEntradaNeural() + this.tipoConsumo.trim().concat("\\" + this.nomeConsumo.concat("\\".concat(this.nomeConsumo.concat(".txt")))), ano_inicial, ano_final, ano_teste, 12);
            rede.Normalizacao();
            rede.Treinamento();
            GerenciadorDesktop.remove(treinamentoRNA);
        }

    }

    public void roda() {

        RNA rede = new RNA(PredictPropriedades.getCaminhoArquivoEntradaNeural() + this.tipoConsumo.concat("\\" + nomeConsumo));
        int ano_inicial = PrevisaoBean.getAnoIni();
        int ano_fim = PrevisaoBean.getAnoFim();
        /**
         *
         */
        List<double[]> lista = rede.pevisaoTeste(ano_inicial, ano_fim);
        setResultado(lista);
        double[] previsao = converteLista(lista);
        List<String> periodo = getPeriodoPrevisao(ano_fim, ano_inicial);
        /**
         *  Gera relatorio
         */
        try {
            String[][] dados = new ControleLeitura().consumoEspecifico(tipoConsumo, nomeConsumo, String.valueOf(ano_inicial), String.valueOf(ano_fim));
//            converteListVector(periodo, previsao, dados);
            calculaErro(previsao, dados);
            calculaErroMedioPercentual(dados, previsao);
            geraRelatorioSimples(periodo, dados, previsao);
//            System.out.println("Periodo\tHistorico\tPrevisao\tErro");
//            for (int i = 0; i < relatorioSimples.length; i++) {
//                System.out.println(relatorioSimples[i][0] + "\t" + relatorioSimples[i][1] + "\t" + relatorioSimples[i][2] + "\t" + relatorioSimples[i][3]);
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("Ano Inicial: "+ano_inicial+"\tAno Final: "+ano_fim);

    }

    public void geraRelatorioSimples(List<String> periodo, String[][] historico, double[] previsao) {
        relatorioSimples = new String[(previsao.length) + ((int) previsao.length / 12)][4];
        /**
         *
         */
        Vector<Double> totalHistorico = new Vector<Double>();
        Vector<String> totalPrevisto = new Vector<String>();
        /**
         *
         */
        int index = 0;
        int index_ = 0;
        for (int i = 0; i < relatorioSimples.length; i++) {
            try {
                if (i > 0) {
                    if (periodo.get(index - 1).split("/")[0].equalsIgnoreCase("12")) {
                        relatorioSimples[index_][0] = "TOTAL";
                        relatorioSimples[index_][1] = nf.format(Double.valueOf(somaH(totalHistorico)));
                        relatorioSimples[index_][2] = nf.format(Double.valueOf(somaP(totalPrevisto)));
                        relatorioSimples[index_][3] = calcErro(relatorioSimples[index_][1], relatorioSimples[index_][2]);
                        totalHistorico.clear();
                        totalPrevisto.clear();
                        index_++;
                    }
                }
                relatorioSimples[index_][0] = periodo.get(index);
                try {
                    relatorioSimples[index_][1] = nf.format(Double.valueOf(historico[index][1].replace(",", ".")));//*/;
                    totalHistorico.add(Double.valueOf(historico[index][1].replace(",", ".")));
                } catch (Exception e) {
                    relatorioSimples[index_][1] = naoExisteMsg;
                }
                relatorioSimples[index_][2] = nf.format(previsao[index]);
                totalPrevisto.add(relatorioSimples[index_][2]);
                relatorioSimples[index_][3] = erros.get(index);
                index_++;
                index++;
            } catch (Exception e) {
            }
        }
    }

    public static void converteListVector(List<String> periodo, double[] previsao, String[][] historico) {

        /**
         * Inicializa colecao
         */
        periodoDados = new Vector<String>();
        previstoDados = new Vector<String>();
        historicoDados = new Vector<String>();

        /**
         * Converte período
         */
        for (int i = 0; i < periodo.size(); i++) {
            periodoDados.add(periodo.get(i));
        }
        /**
         * Converte Previsao
         */
        for (int i = 0; i < previsao.length; i++) {
            previstoDados.add(String.valueOf(previsao[i]));
        }
        /**
         * Converte Historico
         */
        for (int i = 0; i < historico.length; i++) {
            previstoDados.add(historico[i][1]);
        }
    }

    private void calculaErro(double[] previsao, String[][] historico) {

        erros = new Vector<String>();

        for (int i = 0; i < previsao.length; i++) {
            try {
                double h = Double.valueOf(historico[i][1].replace(".", "").replace(",", "."));
                double p = previsao[i];

                double err = ((h - p) / p) * 100;

                erros.add(df.format(err));
            } catch (Exception e) {
                erros.add(InexistenteMsg);
            }

        }
    }

    public String calcErro(String real, String previsto) {
        double r = Double.valueOf(real.replace(".", "").replace(",", ".")).doubleValue();
        double p = Double.valueOf(previsto.replace(".", "").replace(",", ".")).doubleValue();
        double media;
        media = ((r - p) / p) * 100;
        String result = nf.format(media);
        if (media == -100) {
            result = InexistenteMsg;
        }
        return result;
    }

    /*
     * Calcula o erro médio percentual
     *
     */
    public void calculaErroMedioPercentual(String[][] historico, double[] previsto) {


        double hist = 0, prev = 0;
        int cont = 0;
        for (int i = 0; i < historico.length; i++) {
            try {
                hist += Double.valueOf(historico[i][1].replace(",", "."));
                cont++;
            } catch (Exception e) {
                hist += 0;
            }
        }

        for (int i = 0; i < cont; i++) {
            try {
                prev += previsto[i];
            } catch (Exception e) {
                prev += 0;
            }
        }

        if (hist != 0 && prev != 0) {
            double result = (Math.abs(hist - prev) / prev) * 100;
            erroGeral = df.format(result);
        } else {
            erroGeral = InexistenteMsg;
        }

    }

    private String somaP(Vector<String> totalPrevisto) {

        double soma = 0;
        for (String s : totalPrevisto) {
            try {
                soma += Double.valueOf(s.replace(".", "").replace(",", ".")).doubleValue();
            } catch (Exception e) {
                e.printStackTrace();
                soma += 0;
            }
        }

        return String.valueOf(soma);

    }

    private String somaH(Vector<Double> totalHistorico) {

        double soma = 0;
        for (Double d : totalHistorico) {
            try {
                soma += d.doubleValue();
            } catch (Exception e) {
                e.printStackTrace();
                soma += 0;
            }
        }
        return String.valueOf(soma);
    }

    public List<String> getPeriodoPrevisao(int ano_final, int ano_inicial) {
        List<String> periodo = new ArrayList<String>();
        for (int i = ano_inicial; i <= ano_final; i++) {
            for (int j = 1; j <= 12; j++) {
                periodo.add(j + "/" + i);
            }
        }
        return periodo;
    }

    private boolean isTreinada() {
        String path_ = PredictPropriedades.getCaminhoArquivoEntradaNeural() + tipoConsumo.concat("\\" + nomeConsumo.concat("\\" + nomeConsumo.concat(".txt")));
        File f = new File(path_);
        if (f.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public static String[][] getRelatorioSimples() {
        return relatorioSimples;
    }

    public static void setRelatorioSimples(String[][] relatorioSimples) {
        PrevisaoNeural.relatorioSimples = relatorioSimples;
    }

    public static List<double[]> getResultado() {
        return resultado;
    }

    public static void setResultado(List<double[]> resultado) {
        PrevisaoNeural.resultado = resultado;
    }

    public static void main(String[] args) {
        PredictPropriedades.leProperties();
        new PrevisaoNeural("Interligado CATIVO", "RESIDENCIAL (MWh)");
    }
}