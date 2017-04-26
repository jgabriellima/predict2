/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.propriedades;

import br.ufpa.lprad.predict.exception.PredictException;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Arilene
 */
public class PredictPropriedades {

    private static String caminhoPlanilhaConsumo;
    private static String caminhoPlanilhaSocioEconomico;
    private static String caminhoPlanilhaClimaticos;
    private static String caminhoPrincipal;
    private static String caminhoArquivosExportados;
    private static String caminhoRelatorioDadosConsumo;
    private static String caminhoRelatorioDadosSocioEconomico;
    private static String caminhoRelatorioDadosClimaticos;
    private static String caminhoArquivoEntradaNeural;
    private static String caminhoTempRedes;
    private static String caminhoTempResult;
    private static String caminhoArquivoCorrelacao;
    private static String CorrelacaoXML;
    private static String CorrelacaoXMLPadrao;
    private static String nomeConcessionaria;
    private static String caminhoDiretorioXML;
    private static String nomeLookAndFeel;
    private static String classeLookAndFeel;
    private static String caminhoArquivoDatasConsumo;
    private static String caminhoRelatorioPrevisao;
    private static String caminhoRelatorioPrevisaoTOTAL;
    private static String caminhoRelatorioInferencias;
    private static String caminhoCorrelacao;
    private static String caminhoRNAExecucao;
    private static String redeTreinada;
    private static File arquivoConfig;
    private static Properties properties;
    private static String root = "C:\\";

    public static File getArquivoConfig() {
        return arquivoConfig;
    }

    public static void setArquivoConfig(File arquivoConfig) {
        PredictPropriedades.arquivoConfig = arquivoConfig;
    }

    public static String getRoot() {
        return root;
    }

    public static void setRoot(String root) {
        PredictPropriedades.root = root;
    }

    

    public static String getCaminhoRNAExecucao() {
        return caminhoRNAExecucao;
    }

    public static void setCaminhoRNAExecucao(String caminhoRNAExecucao) {
        PredictPropriedades.caminhoRNAExecucao = caminhoRNAExecucao;
    }

    public static String getCaminhoArquivoEntradaNeural() {
        return caminhoArquivoEntradaNeural;
    }

    public static void setCaminhoArquivoEntradaNeural(String caminhoArquivoEntradaNeural) {
        PredictPropriedades.caminhoArquivoEntradaNeural = caminhoArquivoEntradaNeural;
    }

    public static String getCaminhoTempRedes() {
        return caminhoTempRedes;
    }

    public static void setCaminhoTempRedes(String caminhoTempRedes) {
        PredictPropriedades.caminhoTempRedes = caminhoTempRedes;
    }

    public static String getCaminhoTempResult() {
        return caminhoTempResult;
    }

    public static void setCaminhoTempResult(String caminhoTempResult) {
        PredictPropriedades.caminhoTempResult = caminhoTempResult;
    }

    public static String getCaminhoCorrelacao() {
        return caminhoCorrelacao;
    }

    public static void setCaminhoCorrelacao(String caminhoCorrelacao) {
        PredictPropriedades.caminhoCorrelacao = caminhoCorrelacao;
    }

    public PredictPropriedades() {
    }

    public static String getRedeTreinada() {
        return redeTreinada;
    }

    public static void setRedeTreinada(String redeTreinada) {
        PredictPropriedades.redeTreinada = redeTreinada;
    }

    public static String getCaminhoRelatorioInferencias() {
        return caminhoRelatorioInferencias;
    }

    public static void setCaminhoRelatorioInferencias(String caminhoRelatorioInferencias) {
        PredictPropriedades.caminhoRelatorioInferencias = caminhoRelatorioInferencias;
    }

    public static String getCaminhoRelatorioPrevisaoTOTAL() {
        return caminhoRelatorioPrevisaoTOTAL;
    }

    public static void setCaminhoRelatorioPrevisaoTOTAL(String caminhoRelatorioPrevisaoTOTAL) {
        PredictPropriedades.caminhoRelatorioPrevisaoTOTAL = caminhoRelatorioPrevisaoTOTAL;
    }

    public static String getCaminhoRelatorioPrevisao() {
        return caminhoRelatorioPrevisao;
    }

    public static void setCaminhoRelatorioPrevisao(String caminhoRelatorioPrevisao) {
        PredictPropriedades.caminhoRelatorioPrevisao = caminhoRelatorioPrevisao;
    }

    public static String getCaminhoArquivoCorrelacao() {
        return caminhoArquivoCorrelacao;
    }

    public static void setCaminhoArquivoCorrelacao(String caminhoArquivoCorrelacao) {
        PredictPropriedades.caminhoArquivoCorrelacao = caminhoArquivoCorrelacao;
    }

    public static String getNomeConcessionaria() {
        return nomeConcessionaria;
    }

    public static void setNomeConcessionaria(String nomeConcessionaria) {
        PredictPropriedades.nomeConcessionaria = nomeConcessionaria;
    }

    public static String getCaminhoDiretorioXML() {
        return caminhoDiretorioXML;
    }

    public static void setCaminhoDiretorioXML(String caminhoDiretorioXML) {
        PredictPropriedades.caminhoDiretorioXML = caminhoDiretorioXML;
    }

    public static String getCaminhoArquivoDatasConsumo() {
        return caminhoArquivoDatasConsumo;
    }

    public static void setCaminhoArquivoDatasConsumo(String caminhoArquivoDatasConsumo) {
        PredictPropriedades.caminhoArquivoDatasConsumo = caminhoArquivoDatasConsumo;
    }

    public static String getCorrelacaoXML() {
        return CorrelacaoXML;
    }

    public static void setCorrelacaoXML(String CorrelacaoXML) {
        PredictPropriedades.CorrelacaoXML = CorrelacaoXML;
    }

    public static String getCorrelacaoXMLPadrao() {
        return CorrelacaoXMLPadrao;
    }

    public static void setCorrelacaoXMLPadrao(String CorrelacaoXMLPadrao) {
        PredictPropriedades.CorrelacaoXMLPadrao = CorrelacaoXMLPadrao;
    }

    public static void leProperties() {
        try {
            properties = new Properties();

//            arquivoConfig = new File(System.getenv("ProgramFiles") + "\\" + getNomeConcessionaria() + "Predict\\config\\config.properties");
            arquivoConfig = new File(getRoot() +"Predict\\"+ getNomeConcessionaria() +"\\config\\config.properties");
            if (arquivoConfig.exists()) {

                System.out.println("Arq 1");
                properties.load(new FileInputStream(arquivoConfig));


                setCaminhoPlanilhaClimaticos(properties.getProperty("caminhoPlanilhaClimaticos"));
                setCaminhoPlanilhaConsumo(properties.getProperty("caminhoPlanilhaConsumo"));
                setCaminhoPlanilhaSocioEconomico(properties.getProperty("caminhoPlanilhaSocioEconomico"));
                setCaminhoPrincipal(properties.getProperty("caminhoPrincipal"));
                setNomeLookAndFeel(properties.getProperty("nomeLookAndFeel"));
                setCaminhoArquivosExportados(properties.getProperty("caminhoArquivosExportados"));
                setClasseLookAndFeel(properties.getProperty("classeLookAndFeel"));
                setCaminhoRelatorioDadosClimaticos(properties.getProperty("caminhoRelatorioDadosClimaticos"));
                setCaminhoRelatorioDadosConsumo(properties.getProperty("caminhoRelatorioDadosConsumo"));
                setCaminhoRelatorioDadosSocioEconomico(properties.getProperty("caminhoRelatorioDadosSocioEconomico"));
                setCaminhoArquivoCorrelacao(properties.getProperty("caminhoArquivoCorrelacao"));
                setNomeConcessionaria(properties.getProperty("nomeConcessionaria"));
                setCaminhoDiretorioXML(properties.getProperty("caminhoDiretorioXML"));
                setCaminhoArquivoDatasConsumo(properties.getProperty("caminhoArquivoDatasConsumo"));
                setCorrelacaoXML(properties.getProperty("CorrelacaoXML"));
                setCorrelacaoXMLPadrao(properties.getProperty("CorrelacaoXMLPadrao"));
                setCaminhoRelatorioPrevisao(properties.getProperty("caminhoRelatorioPrevisao"));
                setCaminhoRelatorioPrevisaoTOTAL(properties.getProperty("caminhoRelatorioPrevisaoTOTAL"));
                setCaminhoRelatorioInferencias(properties.getProperty("caminhoRelatorioInferencias"));
                setCaminhoArquivoEntradaNeural(properties.getProperty("caminhoArquivoEntradaNeural"));
                setCaminhoTempRedes(properties.getProperty("caminhoTempRedes"));
                setCaminhoTempResult(properties.getProperty("caminhoTempResult"));
                setRedeTreinada(properties.getProperty("redeTreinada"));
                setCaminhoRNAExecucao(properties.getProperty("caminhoRNAExecucao"));
            } else {
                System.out.println("Arq 2");
                setCaminhoPadrao();
                gravaProperties();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void setCaminhoPadrao() {
        try {
            setNomeConcessionaria(/*"ENERSUL");//*/"Celpa");
            setCaminhoPrincipal(getRoot() +"Predict\\"+ getNomeConcessionaria() +"\\");
            setCaminhoArquivoCorrelacao(getCaminhoPrincipal() + "arquivos\\correlações");
            setCaminhoCorrelacao(getCaminhoPrincipal() + "arquivos\\correlações\\ArquivoCorrelacao.txt");
            setCaminhoArquivoDatasConsumo(getCaminhoPrincipal() + "arquivos\\estadual\\DadosConsumo");
            setCaminhoArquivosExportados(getCaminhoPrincipal() + "arquivos\\exportados");
            setCaminhoDiretorioXML(getCaminhoPrincipal() + "arquivos\\XML");
            setCaminhoPlanilhaClimaticos(getCaminhoPrincipal() + "arquivos\\municipios_clima\\Dados_Climaticos.xls");
            setCaminhoPlanilhaConsumo(getCaminhoPrincipal() + "arquivos\\estadual\\Dados_Consumo.xls");
//            setCaminhoPlanilhaSocioEconomico(getCaminhoPrincipal() + "arquivos\\estadual\\Dados_Sócio-Econômico.xls");
            setCaminhoPlanilhaSocioEconomico(getCaminhoPrincipal() + "arquivos\\estadual\\Dados_Sócio-Econômico-Celpa.xls");
            setCaminhoRelatorioDadosClimaticos(getCaminhoPrincipal() + "layouts\\DadosClimaticos.jrxml");
            setCaminhoRelatorioDadosConsumo(getCaminhoPrincipal() + "layouts\\DadosConsumos.jrxml");
            setCaminhoRelatorioPrevisao(getCaminhoPrincipal() + "\\layouts\\RelHistorico.jrxml");
            setCaminhoRelatorioPrevisaoTOTAL(getCaminhoPrincipal() + "\\layouts\\RelHistoricoTotal.jrxml");
            setCaminhoRelatorioDadosSocioEconomico(getCaminhoPlanilhaSocioEconomico() + "layouts\\DadosSocioEconomicos.jrxml");
            setCorrelacaoXML(getCaminhoArquivoCorrelacao() + "\\Correlacao.xml");
            setCorrelacaoXMLPadrao(getCaminhoArquivoCorrelacao() + "\\CorrelacaoPadrao.xml");
            setCaminhoRelatorioInferencias(getCaminhoPrincipal() + "html\\RelatorioInferencias.html");
            setCaminhoArquivoEntradaNeural(getCaminhoPrincipal() + "arquivos\\Previsao\\Dados\\");
            setCaminhoTempRedes(getCaminhoPrincipal() + "arquivos\\Previsao\\tempredes\\");
            setCaminhoTempResult(getCaminhoPrincipal() + "arquivos\\Previsao\\tempResult\\");
            setCaminhoRNAExecucao(getCaminhoPrincipal() + "arquivos\\Previsao\\Dados\\Execucao\\");
            setRedeTreinada("true");
            gravaProperties();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void gravaProperties() {
        try {

            properties = new Properties();

            properties.setProperty("caminhoPlanilhaConsumo", getCaminhoPlanilhaConsumo());
            properties.setProperty("caminhoPlanilhaSocioEconomico", getCaminhoPlanilhaSocioEconomico());
            properties.setProperty("caminhoPlanilhaClimaticos", getCaminhoPlanilhaClimaticos());
            properties.setProperty("caminhoPrincipal", getCaminhoPrincipal());
            properties.setProperty("caminhoArquivosExportados", getCaminhoArquivosExportados());
            properties.setProperty("caminhoRelatorioDadosClimaticos", getCaminhoRelatorioDadosClimaticos());
            properties.setProperty("caminhoRelatorioDadosConsumo", getCaminhoRelatorioDadosConsumo());
            properties.setProperty("caminhoRelatorioDadosSocioEconomico", getCaminhoRelatorioDadosSocioEconomico());
            properties.setProperty("caminhoArquivoCorrelacao", getCaminhoArquivoCorrelacao());
            properties.setProperty("nomeConcessionaria", getNomeConcessionaria());
            properties.setProperty("caminhoDiretorioXML", getCaminhoDiretorioXML());
            properties.setProperty("caminhoArquivoDatasConsumo", getCaminhoArquivoDatasConsumo());
            properties.setProperty("CorrelacaoXML", getCorrelacaoXML());
            properties.setProperty("caminhoRelatorioPrevisao", getCaminhoRelatorioPrevisao());
            properties.setProperty("caminhoRelatorioPrevisaoTOTAL", getCaminhoRelatorioPrevisaoTOTAL());
            properties.setProperty("CorrelacaoXMLPadrao", getCorrelacaoXMLPadrao());
            properties.setProperty("CorrelacaoXMLPadrao", getCorrelacaoXMLPadrao());
            properties.setProperty("caminhoRelatorioInferencias", getCaminhoRelatorioInferencias());
            properties.setProperty("redeTreinada", getRedeTreinada());
            properties.setProperty("caminhoArquivoEntradaNeural", getCaminhoArquivoEntradaNeural());
            File f = new File(getRoot() +"Predict\\"+ getNomeConcessionaria() + "\\config\\config.properties");
//
//            f.delete();
//            f.createNewFile();

            properties.store((new BufferedOutputStream(new FileOutputStream(f))), "Arquivos de Configuração do Predict v2.0");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static String getnomeLookAndFeel() {
        return nomeLookAndFeel;
    }

    public static String getClasseLookAndFeel() {
        return classeLookAndFeel;
    }

    public static void setClasseLookAndFeel(String classeLookAndFeel) {
        PredictPropriedades.classeLookAndFeel = classeLookAndFeel;
    }

    public static void setNomeLookAndFeel(String nomeLookAndFeel) {
        PredictPropriedades.nomeLookAndFeel = nomeLookAndFeel;
    }

    public static Properties getProperties() {
        return properties;
    }

    public static void setProperties(Properties properties) {
        PredictPropriedades.properties = properties;
    }

    public static String getCaminhoPlanilhaConsumo() throws PredictException {

        return caminhoPlanilhaConsumo;
    }

    public static String getCaminhoPlanilhaSocioEconomico() throws PredictException {
        return caminhoPlanilhaSocioEconomico;
    }

    public static String getCaminhoPlanilhaClimaticos() throws PredictException {
        return caminhoPlanilhaClimaticos;
    }

    public static String getCaminhoPrincipal() throws PredictException {
        return caminhoPrincipal;
    }

    public static void setCaminhoPlanilhaClimaticos(String caminhoPlanilhaClimaticos) {
        PredictPropriedades.caminhoPlanilhaClimaticos = caminhoPlanilhaClimaticos;
    }

    public static void setCaminhoPlanilhaConsumo(String caminhoPlanilhaConsumo) {
        PredictPropriedades.caminhoPlanilhaConsumo = caminhoPlanilhaConsumo;
    }

    public static void setCaminhoPlanilhaSocioEconomico(String caminhoPlanilhaSocioEconomico) {
        PredictPropriedades.caminhoPlanilhaSocioEconomico = caminhoPlanilhaSocioEconomico;
    }

    public static void setCaminhoPrincipal(String caminhoPrincipal) {
        PredictPropriedades.caminhoPrincipal = caminhoPrincipal;
    }

    public static String getCaminhoRelatorioDadosClimaticos() {
        return caminhoRelatorioDadosClimaticos;
    }

    public static void setCaminhoRelatorioDadosClimaticos(String caminhoRelatorioDadosClimaticos) {
        PredictPropriedades.caminhoRelatorioDadosClimaticos = caminhoRelatorioDadosClimaticos;
    }

    public static String getCaminhoRelatorioDadosConsumo() {
        return caminhoRelatorioDadosConsumo;
    }

    public static void setCaminhoRelatorioDadosConsumo(String caminhoRelatorioDadosConsumo) {
        PredictPropriedades.caminhoRelatorioDadosConsumo = caminhoRelatorioDadosConsumo;
    }

    public static String getCaminhoRelatorioDadosSocioEconomico() {
        return caminhoRelatorioDadosSocioEconomico;
    }

    public static void setCaminhoRelatorioDadosSocioEconomico(String caminhoRelatorioDadosSocioEconomico) {
        PredictPropriedades.caminhoRelatorioDadosSocioEconomico = caminhoRelatorioDadosSocioEconomico;
    }

    public static String getCaminhoArquivosExportados() {
        return caminhoArquivosExportados;
    }

    public static void setCaminhoArquivosExportados(String caminhoArquivosExportados) {
        PredictPropriedades.caminhoArquivosExportados = caminhoArquivosExportados;
    }

    public static void setLookAndFeel() {

        try {
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {

        setCaminhoPadrao();

    }
}
