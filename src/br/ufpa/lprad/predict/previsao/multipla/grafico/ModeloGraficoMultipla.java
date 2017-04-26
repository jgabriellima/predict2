package br.ufpa.lprad.predict.previsao.multipla.grafico;

/**
 *
 * @author gabriel
 */
public class ModeloGraficoMultipla {

    private String periodo;
    private String valor;
    private String ano;

    public ModeloGraficoMultipla(String periodo, String valor, String ano) {
        this.periodo = periodo;
        this.valor = valor;
        this.ano = ano;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
