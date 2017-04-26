/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.leitura;

import br.ufpa.lprad.predict.bean.Atributos;
import br.ufpa.lprad.predict.bean.DadosClimaticos;
import br.ufpa.lprad.predict.bean.DadosConsumoCELPA_TOTAL;
import br.ufpa.lprad.predict.bean.DadosSocioEconomicos;
import br.ufpa.lprad.predict.exception.PredictException;
import br.ufpa.lprad.predict.gui.window.MostrarDados;
import br.ufpa.lprad.predict.gui.window.RelatorioViewer;
import br.ufpa.lprad.predict.propriedades.PredictPropriedades;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Pedro Vitor
 */
public class Relatorio {

    private String matriz[][];   //matriz de dados(os elementos da primeira coluna eh o periodo e o restante as outras colunas)
    private String cabecalho[];  //array com cabecalhos do arquivo
    private String tipoConsumo;  //tipo de consumo

    public Relatorio(String matriz[][], String cabecalho[], String topo, String tipoConsumo) {
        this.matriz = matriz;
        this.cabecalho = cabecalho;
        this.tipoConsumo = tipoConsumo;
        if (tipoConsumo.equalsIgnoreCase("")) {         //climaticos ou socio economicos
            if (topo.contains("Dados Climáticos")) {
                relatorioClimaticos();
                
            } else {
                relatorioSocioEconomicos();

            }
        } else {      //celpa total ou outros
            if (tipoConsumo.equalsIgnoreCase("Sistema CELPA Total")) {
                relatorioCelpaTotal();
                
            } else {
                relatorioConsumos();
                
            }
        }
    }

    // retorna um String com as 3 primeiras inicias de cada palavra da String passada como argumento separadas por '_'
    public String getIniciais(String frase) {
        String aux = "";
        int numPalavras = 0;
        for (int i = 0; i < frase.length() && frase.charAt(i) != '('; i++) {
            if (frase.charAt(i) == ' ') {
                numPalavras++;
                
            }
        }

        if (numPalavras == 1) {
            aux += frase.charAt(0);
            aux += frase.charAt(1);
            aux += frase.charAt(2);
            return aux;
        }

        for (int i = 0, cont = 0, feitas = 0; i < frase.length() && frase.charAt(i) != '('; i++) {
            if (cont < 3) {
                aux += frase.charAt(i);
                cont++;
                if (cont == 3) {
                    feitas++;
                    if (feitas < numPalavras) {
                        aux += "_";
                        
                    } else {
                        return aux;
                        
                    }
                }
            }
            if (frase.charAt(i) == ' ') {
                cont = 0;
                
            }
        }
        return aux;
    }

    // gera relatorio dos Dados Socio Economicos
    public void relatorioSocioEconomicos() {
//        System.out.println("relatorioSocioEconomico()");
        List linhaTab = new LinkedList();

        DadosSocioEconomicos d = null;
        for (int i = 0; i < matriz.length; i++) {
            if (matriz[i][0] != null) {
                d = new DadosSocioEconomicos();
                d.setPeriodo(matriz[i][0]);
                d.setTotalReceita(matriz[i][1]);
                d.setIndustriaTrans(matriz[i][2]);
                d.setAgropecuaria(matriz[i][3]);
                d.setRealDolar(matriz[i][4]);
                d.setConcessionaria(PredictPropriedades.getNomeConcessionaria());
                linhaTab.add(d);
            }
        }

        try {
//            criaRelatorio(linhaTab, "C:\\Users\\Pedro Vitor\\Documents\\Predict olds\\Modificados\\Layouts\\DadosSocioEconomicos.jrxml");
            criaRelatorio(linhaTab, PredictPropriedades.getCaminhoPrincipal() + "\\layouts\\DadosSocioEconomicos.jrxml");
        } catch (PredictException ex) {
            ex.printStackTrace();
        }
    }

    // gera o relatorio dos Dados Climaticos
    public void relatorioClimaticos() {
//        System.out.println("relatorioClimaticos()");
        List linhaTab = new LinkedList();

        DadosClimaticos d = null;
        for (int i = 0; i < matriz.length; i++) {
            if (matriz[i][0] != null) {
                d = new DadosClimaticos();
                d.setPeriodo(matriz[i][0]);
                d.setIndpluvio(matriz[i][1]);
                d.setTempmax(matriz[i][2]);
                d.setTempmin(matriz[i][3]);
                d.setUmidadear(matriz[i][4]);
                d.setConcessionaria(PredictPropriedades.getNomeConcessionaria());
                linhaTab.add(d);
            }
        }

        try {
//            criaRelatorio(linhaTab, "C:\\Users\\Pedro Vitor\\Documents\\Predict olds\\Modificados\\Layouts\\DadosClimaticos.jrxml");
            criaRelatorio(linhaTab, PredictPropriedades.getCaminhoPrincipal() + "\\layouts\\DadosClimaticos.jrxml");
        } catch (PredictException ex) {
            ex.printStackTrace();
        }

    }

    // cara...eu devia estar bebado quando fiz isso...ta doido mas funciona direitinho!
    // gera os relatorios dos Dados Consumo(menos o CELPA TOTAL)
    public void relatorioConsumos() {
//        System.out.println("relatorioConsumos()");
        List linhaTab = new LinkedList();

        Atributos d = null;
        for (int i = 0; i < matriz.length; i++) {
            if (matriz[i][0] != null) {
                d = new Atributos();
                d.setPeriodo(matriz[i][0]);
                d.setAtrb1(matriz[i][1]);
                d.setAtrb2(matriz[i][2]);
                d.setAtrb3(matriz[i][3]);
                d.setAtrb4(matriz[i][4]);
                d.setAtrb5(matriz[i][5]);
                d.setAtrb6(matriz[i][6]);
                d.setAtrb7(matriz[i][7]);
                d.setAtrb8(matriz[i][8]);
                d.setAtrb9(matriz[i][9]);
                d.setAtrb10(matriz[i][10]);
                d.setAtrb11(matriz[i][11]);
                d.setNome1(" - " + cabecalho[1]);
                d.setNome2(" - " + cabecalho[2]);
                d.setNome3(" - " + cabecalho[3]);
                d.setNome4(" - " + cabecalho[4]);
                d.setNome5(" - " + cabecalho[5]);
                d.setNome6(" - " + cabecalho[6]);
                d.setNome7(" - " + cabecalho[7]);
                d.setNome8(" - " + cabecalho[8]);
                d.setNome9(" - " + cabecalho[9]);
                d.setNome10(" - " + cabecalho[10]);
                d.setNome11(" - " + cabecalho[11]);
                d.setLabel1(getIniciais(cabecalho[1]));
                d.setLabel2(getIniciais(cabecalho[2]));
                d.setLabel3(getIniciais(cabecalho[3]));
                d.setLabel4(getIniciais(cabecalho[4]));
                d.setLabel5(getIniciais(cabecalho[5]));
                d.setLabel6(getIniciais(cabecalho[6]));
                d.setLabel7(getIniciais(cabecalho[7]));
                d.setLabel8(getIniciais(cabecalho[8]));
                d.setLabel9(getIniciais(cabecalho[9]));
                d.setLabel10(getIniciais(cabecalho[10]));
                d.setLabel11(getIniciais(cabecalho[11]));
                d.setTpConsumo(tipoConsumo);
                d.setConcessionaria(PredictPropriedades.getNomeConcessionaria());
                linhaTab.add(d);
            }
        }

        try {
//            criaRelatorio(linhaTab, "C:\\Users\\Pedro Vitor\\Documents\\Predict olds\\Modificados\\Layouts\\DadosConsumos.jrxml");
            criaRelatorio(linhaTab, PredictPropriedades.getCaminhoPrincipal() + "\\layouts\\DadosConsumos.jrxml");
        } catch (PredictException ex) {
            ex.printStackTrace();
        }

    }

    // gera os relatorios dos Dados Consumo(CELPA TOTAL)
    public void relatorioCelpaTotal() {
//        System.out.println("relatorioCelpaTotal()");
        List linhaTab = new LinkedList();

        DadosConsumoCELPA_TOTAL d = null;
        for (int i = 0; i < matriz.length; i++) {
            if (matriz[i][0] != null) {
                d = new DadosConsumoCELPA_TOTAL();
                d.setPeriodo(matriz[i][0]);
                d.setResidencial(matriz[i][1]);
                d.setIndustrialFio(matriz[i][2]);
                d.setIndustrialCativo(matriz[i][3]);
                d.setComercialFio(matriz[i][4]);
                d.setComercialCativo(matriz[i][5]);
                d.setRural(matriz[i][6]);
                d.setPoderPublico(matriz[i][7]);
                d.setIluminacao(matriz[i][8]);
                d.setServico(matriz[i][9]);
                d.setConsumoProprio(matriz[i][10]);
                d.setConsFatTotalFio(matriz[i][11]);
                d.setConsFatTotalCativo(matriz[i][12]);
                d.setEnerReqTotalCativo(matriz[i][13]);
                d.setEnerReqTotalFio(matriz[i][14]);
                d.setDemReqTotalFio(matriz[i][15]);
                d.setDemReqTotalCativo(matriz[i][16]);
                d.setConcessionaria(PredictPropriedades.getNomeConcessionaria());
                linhaTab.add(d);
            }
        }

        try {
//            criaRelatorio(linhaTab, "C:\\Users\\Pedro Vitor\\Documents\\Predict olds\\Modificados\\Layouts\\DadosConsumosCelpaTotal.jrxml");
            criaRelatorio(linhaTab, PredictPropriedades.getCaminhoPrincipal() + "\\layouts\\DadosConsumoCelpaTotal.jrxml");
        } catch (PredictException ex) {
            ex.printStackTrace();
        }

    }

    // exibe a visualização do relatorio
    public void criaRelatorio(List linhaTab, String path_layout) {
        Map parametros = new HashMap();
        MostrarDados.imprimir.setEnabled(true);

        try {
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(linhaTab);

            JasperReport jr = JasperCompileManager.compileReport(path_layout);

            JasperPrint jp = JasperFillManager.fillReport(jr, parametros, ds);

            RelatorioViewer viewer = new RelatorioViewer(jp, false);
            viewer.viewReport(jp, false);
        } catch (JRException ex) {
            System.out.println("Não foi possível criar um relatório de impressão");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Ocorreu um erro geral do sistema. Contate o administrador");
            ex.printStackTrace();
        }
    }
}
