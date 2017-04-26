package br.ufpa.lprad.predict.previsao.neural.grafico;

/**
 *
 * @author gabriel
 */
public class ModeloGraficoRNA {

    private String periodo;
    private String valor;
    private String ano;

    public ModeloGraficoRNA(String periodo, String valor, String ano) {
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
