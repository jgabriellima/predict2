package br.ufpa.lprad.predict.leitura;

import br.ufpa.lprad.predict.exception.PredictException;
import br.ufpa.lprad.predict.gui.window.previsao.backstage.BackPrevisaoGUI;
import br.ufpa.lprad.predict.leitura.interfaces.InterfaceLeituraClimaticos;
import br.ufpa.lprad.predict.leitura.interfaces.InterfaceLeituraConsumo;
import br.ufpa.lprad.predict.leitura.interfaces.InterfaceLeituraPrevisao;
import br.ufpa.lprad.predict.leitura.interfaces.InterfacerLeituraSocioEconomico;
import br.ufpa.lprad.predict.previsao.bean.PrevisaoBean;
import br.ufpa.lprad.predict.propriedades.PredictPropriedades;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * @author J. Gabriel Lima
 */
public class ControleLeitura implements InterfaceLeituraPrevisao, InterfaceLeituraClimaticos,
        InterfacerLeituraSocioEconomico, InterfaceLeituraConsumo {

    private Workbook planhilhaConsumo;
    private Workbook planhilhaSocioEconomico;
    private Workbook planhilhaClimaticos;
    private Sheet aba;
    private Sheet[] abas;
    private static NumberFormat nf;
    static Locale locale;
    private String msgNaoAferido = "não aferido";

    public ControleLeitura() throws IOException, BiffException, PredictException {
        try {
//            System.out.println("# " + PredictPropriedades.getCaminhoPlanilhaConsumo());
            PredictPropriedades.leProperties();


            planhilhaConsumo = Workbook.getWorkbook(new File(PredictPropriedades.getCaminhoPlanilhaConsumo()));
            planhilhaSocioEconomico = Workbook.getWorkbook(new File(PredictPropriedades.getCaminhoPlanilhaSocioEconomico()));
            planhilhaClimaticos = Workbook.getWorkbook(new File(PredictPropriedades.getCaminhoPlanilhaClimaticos()));



            locale = new Locale("pt", "BR");
            Locale.setDefault(locale);
            nf = NumberFormat.getNumberInstance(locale);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PredictException("Erro ao inicializar as planilhas: " + ex.getMessage());
        }
    }

    public String[] getTiposDeConsumo() throws PredictException {

        String[] nomes = planhilhaConsumo.getSheetNames();

//        System.out.println("TIPO de Consumo");
//        for (int i = 0; i < nomes.length; i++) {
//            System.out.print(nomes[i] + "\t");
//        }

        return nomes;
    }

    public String[] getRestricao(String tipoDeConsumo) throws PredictException {
        return null;
    }

    public String[][] getPlanilhaClimaticos() throws PredictException {
        try {

            String[][] matrizResultado;         //matriz que será retornada
            int linhas = 0;                     // contador para linhas
            int colunas = 0;                    // contador para colunas


            aba = getPlanhilhaClimaticos().getSheet(0); // obtem a aba selecionada
            Cell[] anos = aba.getColumn(0);                    // obtem a primeira coluna - anos

            for (Cell a : anos) {                              // conta as linhas da planilha
                if (!a.getContents().trim().equalsIgnoreCase("")) {
                    linhas++;
                }
            }

            Cell[] atributos = aba.getRow(1);

            for (Cell a : atributos) {                          // conta as colunas da planilha
                if (!a.getContents().trim().equalsIgnoreCase("")) {
                    colunas++;
                }
            }

            matrizResultado = new String[linhas][colunas];      // instancia a matriz com o tamanho adequado

            for (int i = 2; i < matrizResultado.length; i++) //loop para povoar a matriz de dados
            {
                Cell[] linha = aba.getRow(i);
                for (int j = 0; j < matrizResultado[0].length; j++) {
                    try {
                        if (!linha[j].getContents().trim().equalsIgnoreCase("")) {
                            matrizResultado[i - 2][j] = nf.format(Math.abs(Double.valueOf(linha[j].getContents().replace(",", ".")).doubleValue()));
                        } else {
                            matrizResultado[i - 2][j] = msgNaoAferido;
                        }
                    } catch (NumberFormatException ex) {
                        matrizResultado[i - 2][j] = linha[j].getContents();
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }
            }

//               System.out.println("PLANILHA CLIMÁTICOS");
//             for (int i = 0; i < matrizResultado.length; i++) {
//                for (int j = 0; j < matrizResultado[0].length; j++) {
//                    System.out.print(matrizResultado[i][j] + "\t");
//                }
//                System.out.println("");
//            }
            return matrizResultado;

        } catch (Exception e) {
            throw new PredictException("Erro em obter  planilha de climaticos!" + e.getMessage());
        }
    }

    public String[] getCabecalhoPlanilhaClimaticos() throws PredictException {

        aba = planhilhaClimaticos.getSheet(0);

        Cell[] c = aba.getRow(1);

        String[] retorno = new String[c.length];

//        System.out.println("Cabecalho planilha climáticos...");
        for (int i = 0; i < c.length; i++) {
//            System.out.println(c[i].getContents() + "\t");
            retorno[i] = c[i].getContents();
        }


        return retorno;
    }

    public String[][] getPlanilhaSocioEconomico() throws PredictException {
        try {

            String[][] matrizResultado;         //matriz que será retornada
            int linhas = 0;                     // contador para linhas
            int colunas = 0;                    // contador para colunas


            aba = getPlanhilhaSocioEconomico().getSheet(0); // obtem a aba selecionada
            Cell[] anos = aba.getColumn(0);                    // obtem a primeira coluna - anos

            for (Cell a : anos) {                              // conta as linhas da planilha
                if (!a.getContents().trim().equalsIgnoreCase("")) {
                    linhas++;
                }
            }

            Cell[] atributos = aba.getRow(1);

            for (Cell a : atributos) {                          // conta as colunas da planilha
                if (!a.getContents().trim().equalsIgnoreCase("")) {
                    colunas++;
                }
            }

            matrizResultado = new String[linhas][colunas];      // instancia a matriz com o tamanho adequado

            for (int i = 2; i < matrizResultado.length; i++) //loop para povoar a matriz de dados
            {
                Cell[] linha = aba.getRow(i);
                for (int j = 0; j < matrizResultado[0].length; j++) {
                    try {
                        if (!linha[j].getContents().trim().equalsIgnoreCase("")) {
                            matrizResultado[i - 2][j] = nf.format(Math.abs(Double.valueOf(linha[j].getContents().replace(",", ".")).doubleValue()));
                        } else {
                            matrizResultado[i - 2][j] = msgNaoAferido;
                        }
                    } catch (NumberFormatException ex) {
                        matrizResultado[i - 2][j] = linha[j].getContents();
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }
            }

//            System.out.println("PLANILHA SÓCIO-ECONÔMICO");
//             for (int i = 0; i < matrizResultado.length; i++) {
//                for (int j = 0; j < matrizResultado[0].length; j++) {
//                    System.out.print(matrizResultado[i][j] + "\t");
//                }
//                System.out.println("");
//            }

            return matrizResultado;

        } catch (Exception e) {
            throw new PredictException("Erro em obter o cabecalho da planilha de Sócio-Econômico!" + e.getMessage());
        }
    }

    public String[] getCabecalhoPlanilhaSocioEconomico() throws PredictException {
        aba = planhilhaSocioEconomico.getSheet(0);

        Cell[] c = aba.getRow(1);

        String[] retorno = new String[c.length];

//        System.out.println("Cabecalho Planilha Socio Economico");
        for (int i = 0; i < c.length; i++) {
//            System.out.println(c[i].getContents() + "\t");
            retorno[i] = c[i].getContents();
        }


        return retorno;
    }

    public String[][] getPlanilhaConsumo(String tipoConsumo) throws PredictException {

        try {

            String[][] matrizResultado;         //matriz que será retornada
            int linhas = 0;                     // contador para linhas
            int colunas = 0;                    // contador para colunas


            aba = getPlanhilhaConsumo().getSheet(tipoConsumo); // obtem a aba selecionada
            Cell[] anos = aba.getColumn(0);                    // obtem a primeira coluna - anos

            for (Cell a : anos) {                              // conta as linhas da planilha
                if (!a.getContents().trim().equalsIgnoreCase("")) {
                    linhas++;
                }
            }

            Cell[] atributos = aba.getRow(1);

            for (Cell a : atributos) {                          // conta as colunas da planilha
                if (!a.getContents().trim().equalsIgnoreCase("")) {
                    colunas++;
                }
            }

//            System.out.println("COLUNAS: " + colunas);
            matrizResultado = new String[linhas][colunas];      // instancia a matriz com o tamanho adequado

            for (int i = 2; i < matrizResultado.length; i++) //loop para povoar a matriz de dados
            {
                Cell[] linha = aba.getRow(i);
                for (int j = 0; j < matrizResultado[0].length; j++) {
                    try {
                        if (!linha[j].getContents().trim().equalsIgnoreCase("")) {
                            matrizResultado[i - 2][j] = linha[j].getContents();
//                            matrizResultado[i - 2][j] = nf.format(Math.abs(Double.valueOf(linha[j].getContents().replace(",", ".")).doubleValue()));
//                            matrizResultado[i - 2][j] = matrizResultado[i - 2][j].replace(",", ".");
                        } else {
                            matrizResultado[i - 2][j] = msgNaoAferido;
                        }
                    } catch (NumberFormatException ex) {
                        matrizResultado[i - 2][j] = linha[j].getContents();
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }
            }

//            System.out.println("PLANILHA CONSUMO");
//            for (int i = 0; i < matrizResultado.length; i++) {
//                for (int j = 0; j < matrizResultado[0].length; j++) {
//                    System.out.print(matrizResultado[i][j] + "\t");
//                }
//                System.out.println("");
//            }

            return matrizResultado;

        } catch (Exception e) {
            e.printStackTrace();
            throw new PredictException("Erro em obter a planilha de consumo!" + e.getMessage());

        }

    }

    public String[][] getPlanilhaConsumo(int tipoConsumo) throws PredictException {

        try {

            String[][] matrizResultado;         //matriz que será retornada
            int linhas = 0;                     // contador para linhas
            int colunas = 1;                    // contador para colunas


            aba = getPlanhilhaConsumo().getSheet(tipoConsumo); // obtem a aba selecionada
            Cell[] anos = aba.getColumn(0);                    // obtem a primeira coluna - anos

            for (Cell a : anos) {                              // conta as linhas da planilha
                if (!a.getContents().trim().equalsIgnoreCase("")) {
                    linhas++;
                }
            }

            Cell[] atributos = aba.getRow(1);

            for (Cell a : atributos) {                          // conta as colunas da planilha
                if (!a.getContents().trim().equalsIgnoreCase("")) {
                    colunas++;
                }
            }

            //  System.out.println("COLUNAS: " + colunas);
            matrizResultado = new String[linhas][colunas - 1];      // instancia a matriz com o tamanho adequado

            for (int i = 2; i < matrizResultado.length; i++) //loop para povoar a matriz de dados
            {
                Cell[] linha = aba.getRow(i);
                for (int j = 0; j < matrizResultado[0].length; j++) {
                    try {
                        if (!linha[j].getContents().trim().equalsIgnoreCase("")) {
                            matrizResultado[i - 2][j] = nf.format(Math.abs(Double.valueOf(linha[j].getContents().replace(",", ".")).doubleValue()));
                        } else {
                            matrizResultado[i - 2][j] = msgNaoAferido;
                        }
                    } catch (NumberFormatException ex) {
                        matrizResultado[i - 2][j] = linha[j].getContents();
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }
            }

//               System.out.println("PLANILHA SÓCIO-CONSUMO");
//             for (int i = 0; i < matrizResultado.length; i++) {
//                for (int j = 0; j < matrizResultado[0].length; j++) {
//                    System.out.print(matrizResultado[i][j] + "\t");
//                }
//                System.out.println("");
//            }

            return matrizResultado;

        } catch (Exception e) {
            e.printStackTrace();
            throw new PredictException("Erro em obter a planilha de consumo!" + e.getMessage());

        }

    }

    public String[] getTipoConsumo() throws PredictException {


        return planhilhaConsumo.getSheetNames();

    }

    public String[] getCabecalhoPlanilhaConsumo(String tipoConsumo) throws PredictException {
        try {

//            System.out.println("TIPO CONSUMO: "+tipoConsumo);
            String[] resultado;                                 // vetor de resultado
            Vector auxResultado = new Vector();                 // vetor auxiliar
            aba = getPlanhilhaConsumo().getSheet(tipoConsumo);  //obtem a aba que deseja trabalhar

            Cell[] linhaCabecalho = aba.getRow(1);              // obtem a linha dos atributos

            int colunas = 0;
            for (Cell a : linhaCabecalho) {                          // conta as colunas da planilha
                if (!a.getContents().trim().equalsIgnoreCase("")) {
                    colunas++;
                }
            }
            resultado = new String[colunas];


//            System.out.println("Cabecalho Planilha Consumo");
            for (int i = 0; i < resultado.length; i++) {
                resultado[i] = String.valueOf(linhaCabecalho[i].getContents());
//                System.out.println("resultado[" + i + "]: " + resultado[i] + "\t");
            }

            return resultado;

        } catch (Exception e) {
            e.printStackTrace();
            throw new PredictException("Erro em obter o cabecalho da planilha de consumo!" + e.getMessage());
        }

    }

    public String[] getNomeDoConsumo(String tipoDeConsumo) throws PredictException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String[] getAnos(String tipoDeConsumo) throws PredictException {

//        System.out.println("TIPO CONSUMO: " + tipoDeConsumo);

        try {
            Vector vetorAnos = new Vector();
            String[] anos;

            aba = getPlanhilhaConsumo().getSheet(tipoDeConsumo);
            Cell[] coluna_anos = aba.getColumn(0);
            for (int i = 2; i < coluna_anos.length; i++) {
                try {

                    String[] aux = coluna_anos[i].getContents().split("/");

                    if (!vetorAnos.contains(aux[1])) {
                        vetorAnos.add(aux[1]);
                    }
                } catch (Exception e) {
                }
            }
            anos = new String[vetorAnos.size()];

            for (int i = 0; i < vetorAnos.size(); i++) {
                anos[i] = String.valueOf(vetorAnos.get(i));
            }

            return anos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new PredictException("Erro ao ler os anos do consumo " + tipoDeConsumo);
        }
    }

    public Workbook getPlanhilhaClimaticos() {
        return planhilhaClimaticos;
    }

    public void setPlanhilhaClimaticos(Workbook planhilhaClimaticos) {
        this.planhilhaClimaticos = planhilhaClimaticos;
    }

    public Workbook getPlanhilhaConsumo() {
        return planhilhaConsumo;
    }

    public void setPlanhilhaConsumo(Workbook planhilhaConsumo) {
        this.planhilhaConsumo = planhilhaConsumo;
    }

    public Workbook getPlanhilhaSocioEconomico() {
        return planhilhaSocioEconomico;
    }

    public void setPlanhilhaSocioEconomico(Workbook planhilhaSocioEconomico) {
        this.planhilhaSocioEconomico = planhilhaSocioEconomico;
    }

//    public static void main(String[] args) throws IOException, BiffException, PredictException, IOException {
//
//        PredictPropriedades.leProperties();
//
//        PrevisaoBean.setNomeConsumo("RESIDENCIAL (MWh)");
//        InterfaceLeituraConsumo ic = new ControleLeitura();
//        InterfaceLeituraClimaticos iclim = new ControleLeitura();
//        InterfacerLeituraSocioEconomico ilse = new ControleLeitura();
//
//        ic.getPlanilhaConsumo("Interligado CATIVO");
//
//
//        for (String c : ic.getCabecalhoPlanilhaConsumo("Interligado CATIVO")) {
//            System.out.println("_" + c);
//        }
//
//        ic.getTipoConsumo();
//        iclim.getCabecalhoPlanilhaClimaticos();
//        iclim.getPlanilhaClimaticos();
//        ilse.getPlanilhaSocioEconomico();
//        ilse.getCabecalhoPlanilhaSocioEconomico();
//
//    }
    public double[][] getDadosParaPrevisao(String anoInicial, String anoFinal, String mesInicial, String mesFinal, String tipoConsumo, String nomeConsumo) throws PredictException {
        String[][] resultado = null;
        double[][] resultadoFinal = null;
        try {

            String inicio = AuxiliarLeitura.formataMes(mesInicial).concat("/").concat(anoInicial);
            String fim = AuxiliarLeitura.formataMes(mesFinal).concat("/").concat(anoFinal);

            aba = planhilhaConsumo.getSheet(tipoConsumo);

            Cell[] colunaConsumo = aba.getColumn(AuxiliarLeitura.getColunaConsumo(nomeConsumo, aba));
            Cell[] colunaPeriodo = aba.getColumn(0);

            int linhaInicial = AuxiliarLeitura.getLinha(inicio, 0, aba);

            int linhaFinal = AuxiliarLeitura.getLinha(fim, 0, aba);

            resultado = new String[(linhaFinal - linhaInicial) + 1][2];

            BackPrevisaoGUI.setAnosDiferentes((linhaFinal - linhaInicial));

            for (int i = linhaInicial; i <= linhaFinal; i++) {
                resultado[i - linhaInicial][0] = colunaPeriodo[i].getContents();
                resultado[i - linhaInicial][1] = colunaConsumo[i].getContents();
            }

            resultadoFinal = new double[12][BackPrevisaoGUI.qntsAnosDesejaPrever() + 1];

            int linAux = 0;
            loop:
            for (int i = 0; i < resultadoFinal[0].length; i++) {
                for (int j = 0; j < resultado.length; j++) {
                    try {
                        if (!resultado[linAux][0].equalsIgnoreCase("TOTAL")) {
                            resultadoFinal[j][i] = Double.valueOf(resultado[linAux][1].replace(",", ".")).doubleValue();
                            linAux++;
                        } else {
                            linAux++;
                            break;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        break loop;
                    }
                }
            }
//            for (int i = 0; i < resultado.length; i++) {
//                for (int j = 0; j < resultado[0].length; j++) {
//                    System.out.print(resultado[i][j] + "\t");
//                }
//                System.out.println("");
//            }
//            System.out.println("------------------------------------------------\n");
//            for (int i = 0; i < resultadoFinal.length; i++) {
//                for (int j = 0; j < resultadoFinal[0].length; j++) {
//                    System.out.print(resultadoFinal[i][j] + "\t");
//                }
//                System.out.println("");
//            }

        //  System.out.println("OK!");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultadoFinal;
    }

    public double[][] getDadosAnosSelecionados(String[] anosSelecionados, String tipoConsumo, String nomeConsumo) throws PredictException {
        try {

            double[][] resultado = new double[12][anosSelecionados.length];

            aba = planhilhaConsumo.getSheet(tipoConsumo);
            Cell[] dados = aba.getColumn(AuxiliarLeitura.getColunaConsumo(nomeConsumo, aba));

            for (int i = 0; i < anosSelecionados.length; i++) {
                int linhaInicial = AuxiliarLeitura.getIndiceAnoLeitura(aba.getColumn(0), anosSelecionados[i]);
//                System.out.println(""+linhaInicial);
                loop:
                for (int j = 0; j < resultado.length;) {

                    if (!dados[linhaInicial].getContents().isEmpty() && !dados[linhaInicial].getContents().equalsIgnoreCase("TOTAL") || !dados[linhaInicial].getContents().trim().equalsIgnoreCase("")) {

                        resultado[j][i] = Double.valueOf(dados[linhaInicial].getContents().replace(",", ".")).doubleValue();
                        linhaInicial++;
                        j++;

                    } else {
                        break loop;
                    }


                }
            }

//            for (int i = 0; i < resultado.length; i++) {
//                for (int j = 0; j < resultado[0].length; j++) {
//                    System.out.print(resultado[i][j] + "\t");
//                }
//                System.out.println("");
//            }

            // System.out.println("OK2");

            return resultado;


        } catch (Exception e) {
            e.printStackTrace();
            throw new PredictException();
        }
    }

    public String[][] getLerTodoHistorico(String tipoConsumo) throws PredictException {


        String[][] resultado = null;
        String[][] resultadoFinal = null;
        try {
            aba = planhilhaConsumo.getSheet(tipoConsumo);

            Cell[] colunaConsumo = aba.getColumn(AuxiliarLeitura.getColunaConsumo(PrevisaoBean.getNomeConsumo(), aba));
            Cell[] colunaPeriodo = aba.getColumn(0);

            int linhas = 0;
            try {
                while (colunaPeriodo[linhas] != null) {
                    linhas++;
                }
            } catch (Exception e) {
            }


            resultado = new String[linhas][2];



            for (int i = 2; i < resultado.length; i++) {
                resultado[i - 2][0] = colunaPeriodo[i].getContents();
                resultado[i - 2][1] = colunaConsumo[i].getContents();
            }

            resultadoFinal = new String[12][(resultado.length / 12)];

            //System.out.println("inhas: " + linhas);
            int linAux = 0;
            loop:
            for (int i = 0; i < resultadoFinal[0].length; i++) {
                for (int j = 0; j < resultado.length; j++) {
                    try {
                        try {

//                        System.out.println("resultado["+linAux+"][0]: "+resultado[linAux][0]);
                            if (!resultado[linAux][0].equalsIgnoreCase("TOTAL")) {
                                resultadoFinal[j][i] = resultado[linAux][1];
                                linAux++;
                            } else {
                                linAux++;
                                break;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            break loop;
                        }
                    } catch (Exception e) {
                    }
                }
            }
//             for (int i = 0; i < resultado.length; i++) {
//                for (int j = 0; j < resultado[0].length; j++) {
//                    System.out.print(resultado[i][j] + "\t");
//                }
//                System.out.println("");
//            }

//            for (int i = 0; i < resultadoFinal.length; i++) {
//                for (int j = 0; j < resultadoFinal[0].length; j++) {
//                    System.out.print(resultadoFinal[i][j] + "\t");
//                }
//                System.out.println("");
//            }

        //System.out.println("OK!");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultadoFinal;
    }

    public String[] getTotalAnosHistorico(String tipoConsumo) throws PredictException {

        String[] resultadoFinal = null;
        try {
            aba = planhilhaConsumo.getSheet(tipoConsumo);

            Cell[] colunaConsumo = aba.getColumn(AuxiliarLeitura.getColunaConsumo(PrevisaoBean.getNomeConsumo(), aba));
            Cell[] colunaPeriodo = aba.getColumn(0);

            Vector v = new Vector();

            for (int i = 0; i < colunaPeriodo.length; i++) {
                if (colunaPeriodo[i].getContents().equalsIgnoreCase("TOTAL")) {
                    v.add(colunaConsumo[i].getContents());
                }
            }

            resultadoFinal = new String[v.size()];
            for (int i = 0; i < v.size(); i++) {
                resultadoFinal[i] = String.valueOf(v.get(i));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultadoFinal;
    }

    public String getNomeDaPlanilha(String tipoConsumo) {

        aba = planhilhaConsumo.getSheet(tipoConsumo);
        Cell[] c = aba.getRow(0);

        return c[0].getContents();
    }

    public String getTopoPlanilhaClimaticos() {
        aba = planhilhaClimaticos.getSheet(0);
        Cell[] c = aba.getRow(0);

        return c[0].getContents();
    }

    public String getTopoSE() {
        aba = planhilhaSocioEconomico.getSheet(0);
        Cell[] c = aba.getRow(0);

        return c[0].getContents();
    }

    public String[] consumoEspecifico(String tipoConsumo, String nomeConsumo, String anoInicial) throws PredictException {
        try {
            String retorno[] = null;

            aba = planhilhaConsumo.getSheet(tipoConsumo);
            String cabecalho[] = getCabecalhoPlanilhaConsumo(tipoConsumo);

            int indice = 0;
            for (int i = 0; i < cabecalho.length; i++) {
                if (cabecalho[i].equalsIgnoreCase(nomeConsumo)) {
                    indice = i;
                    break;
                }
            }

            Cell[] periodo = aba.getColumn(0);

            Cell[] c = aba.getColumn(indice);
            int inicio = 0;
            /* encontra inicio da leitura */
            for (int i = 2; i < periodo.length; i++) {
                if (!periodo[i].getContents().equalsIgnoreCase("TOTAL")) {
                    if (periodo[i].getContents().split("/")[1].equalsIgnoreCase(anoInicial)) {
                        inicio = i;
                        break;
                    }
                }
            }

            retorno = new String[c.length];
            int aux = 0;
            for (int i = inicio; i < retorno.length; i++) {
                if (!periodo[i].getContents().equalsIgnoreCase("TOTAL")) {
                    retorno[aux] = c[i].getContents();
                    aux++;
                }
            }

//            System.out.println("CONSUMO ESPECIFICO");
//            for (String d : retorno) {
//                System.out.println("" + d);
//            }

            return retorno;
        } catch (Exception e) {
            e.printStackTrace();
            throw new PredictException("Erro 'consumoEspecifico' ");
        }
    }

    public String[] consumoEspecifico(String tipoConsumo, String nomeConsumo) throws PredictException {
        try {
            String retorno[] = null;

            aba = planhilhaConsumo.getSheet(tipoConsumo);

//            System.out.println("TIPO CONS: " + tipoConsumo);
            String cabecalho[] = getCabecalhoPlanilhaConsumo(tipoConsumo);


//            System.out.println("NOME DO CONS: " + nomeConsumo);
            int indice = 0;
            for (int i = 0; i < cabecalho.length; i++) {
                if (cabecalho[i].equalsIgnoreCase(nomeConsumo)) {
                    indice = i;
                    break;
                }
            }


            Cell[] c = aba.getColumn(indice);

            retorno = new String[c.length];
            for (int i = 2; i < retorno.length; i++) {
                retorno[i - 2] = c[i].getContents();
            }

//            System.out.println("CONSUMO ESPECIFICO");
//            for (String d : retorno) {
//                System.out.println("" + d);
//            }

            return retorno;
        } catch (Exception e) {
            throw new PredictException("Erro 'consumoEspecifico' ");
        }
    }

    private String[][] corrigeMatriz(String[][] matrizResultado) {

        String[][] matriz;
        Vector<Integer> valores = new Vector<Integer>();
        int cont = 0;

        for (int j = 0; j < matrizResultado[0].length; j++) {
            for (int i = 0; i < matrizResultado.length; i++) {
                try {
                    if (!matrizResultado[i][j].equalsIgnoreCase("null") || !matrizResultado[i][j].equalsIgnoreCase("não aferido")) {
                        cont++;
                    }
                } catch (Exception e) {
                }
            }
            valores.add(cont);
            cont = 0;
        }

        matriz = new String[pegaMenor(valores)][matrizResultado[0].length];


        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                matriz[i][j] = matrizResultado[i][j];
            }
        }
        return matriz;
    }

    private int pegaMenor(Vector<Integer> valores) {
        int menor = 100000;

        for (int b : valores) {
            if (menor > b) {
                menor = b;
            }
        }

        return menor;
    }

    private boolean temVirgula(double val) {
        boolean b = false;

        String contents = String.valueOf(val);
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) == ',') {
                b = true;
                break;
            }
        }

        return b;

    }

    @Override
    public String[][] getPlanilhaConsumoCorrec(String tipoConsumo) {
        String[][] matrizResultado = null;         //matriz que será retornada
        try {

            int linhas = 0;                     // contador para linhas
            int colunas = 1;                    // contador para colunas


            aba = getPlanhilhaConsumo().getSheet(tipoConsumo); // obtem a aba selecionada
            Cell[] anos = aba.getColumn(0);                    // obtem a primeira coluna - anos

            for (Cell a : anos) {                              // conta as linhas da planilha
                if (!a.getContents().trim().equalsIgnoreCase("")) {
                    linhas++;
                }
            }

            Cell[] atributos = aba.getRow(1);

            for (Cell a : atributos) {                          // conta as colunas da planilha
                if (!a.getContents().trim().equalsIgnoreCase("")) {
                    colunas++;
                }
            }

            //  System.out.println("COLUNAS: " + colunas);
            matrizResultado = new String[linhas][colunas - 1];      // instancia a matriz com o tamanho adequado

            int aux = 0;
            boolean teste = false;
            for (int i = 2; i < matrizResultado.length; i++) //loop para povoar a matriz de dados
            {
                Cell[] linha = aba.getRow(i);
                for (int j = 0; j < matrizResultado[0].length; j++) {
                    if (!linha[0].getContents().equalsIgnoreCase("TOTAL")) {

                        if (!linha[j].getContents().trim().equalsIgnoreCase("")) {
                            matrizResultado[aux][j] = linha[j].getContents();
//                            matrizResultado[i - 2][j] = nf.format(Math.abs(Double.valueOf(linha[j].getContents().replace(",", ".")).doubleValue()));
                        } else {
                            matrizResultado[aux][j] = msgNaoAferido;
                        }
                        teste = true;
                    }
                }
                if (teste) {
                    aux++;
                }
                teste = false;

            }

            matrizResultado = corrigeMatriz(matrizResultado);

//            System.out.println("PLANILHA CONSUMO CORREC");
//            for (int i = 0; i < matrizResultado.length; i++) {
//                for (int j = 0; j < matrizResultado[0].length; j++) {
//                    System.out.print(matrizResultado[i][j] + "\t");
//                }
//                System.out.println("");
//            }


        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro de Leitura: getPlanilhaConsumoCorrec()");

        }
        return matrizResultado;

    }

    @Override
    public String[] getEspecifico(String nome) {
        String[] valores = null;
        try {
            String[] cabSE = getCabecalhoPlanilhaSocioEconomico();
            int indice = 0;
            for (int i = 0; i < cabSE.length; i++) {
                if (cabSE[i].equalsIgnoreCase(nome)) {
                    indice = i;
                    break;
                }
            }

            String[][] dados = getPlanilhaSocioEconomico();
            valores = new String[dados.length];

            for (int i = 0; i < valores.length; i++) {
                valores[i] = dados[i][indice];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valores;
    }

    public String[] getEspecificoClimaticos(String nome) {
        String[] valores = null;
        try {
            String[] cabCL = getCabecalhoPlanilhaClimaticos();
            int indice = 0;
            for (int i = 0; i < cabCL.length; i++) {
                if (cabCL[i].equalsIgnoreCase(nome)) {
                    indice = i;
                    break;
                }
            }
            String[][] dados = getPlanilhaClimaticos();
            valores = new String[dados.length];

            for (int i = 0; i < valores.length; i++) {
                valores[i] = dados[i][indice];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valores;
    }

//    public String[] getAnoInicialFinal(String tipoConsumo, String nomeConsumo) {
//        try {
//            String[] anoInicialFinal = new String[2];
//
//            aba = planhilhaConsumo.getSheet(tipoConsumo);
//            String cabecalho[] = getCabecalhoPlanilhaConsumo(tipoConsumo);
//            int indice = 0;
//            for (int i = 0; i < cabecalho.length; i++) {
//                if (cabecalho[i].trim().equalsIgnoreCase(nomeConsumo.trim())) {
//                    indice = i;
//                    break;
//                }
//            }
//             List<String> anos = new ArrayList<String>
//
//
//            return anoInicialFinal;
//        } catch (Exception e) {
//        }
//        return null;
//    }

    public String[][] consumoEspecifico(String tipoConsumo, String nomeConsumo, String anoInicial, String anoFinal) {
        try {
            String retorno[][] = null;

            aba = planhilhaConsumo.getSheet(tipoConsumo);
            String cabecalho[] = getCabecalhoPlanilhaConsumo(tipoConsumo);

            int indice = 0;
            for (int i = 0; i < cabecalho.length; i++) {
//                System.out.println("cabecalho["+i+"]: "+cabecalho[i]+" - nomeConsumo: "+nomeConsumo);
                if (cabecalho[i].trim().equalsIgnoreCase(nomeConsumo.trim())) {
                    indice = i;
                    break;
                }
            }

            Cell[] periodo = aba.getColumn(0);

            Cell[] c = aba.getColumn(indice);
            int inicio = 0;
            int finale = 0;
            /* encontra inicio da leitura */
            for (int i = 2; i < periodo.length; i++) {
                if (!periodo[i].getContents().equalsIgnoreCase("TOTAL")) {
                    if (periodo[i].getContents().split("/")[1].equalsIgnoreCase(anoInicial)) {
                        inicio = i;
                        break;
                    }
                }
            }
            for (int i = 2; i < periodo.length; i++) {
                if (!periodo[i].getContents().equalsIgnoreCase("TOTAL")) {
                    if (periodo[i].getContents().split("/")[1].equalsIgnoreCase(anoFinal)) {
                        finale = i;
//                        break;
                    }
                }
            }

            if (finale == 0) {
                finale = c.length;
            }

            retorno = new String[c.length][2];
            int aux = 0;
            for (int i = inicio; i <= /*retorno.length*/ finale; i++) {
                try {
                    if (!periodo[i].getContents().equalsIgnoreCase("TOTAL")) {
                        retorno[aux][0] = periodo[i].getContents();
                        retorno[aux][1] = c[i].getContents();
                        aux++;
                    }
                } catch (Exception e) {
                }
            }
            int linValidas = 0;

            for (int i = 0; i < retorno.length; i++) {
                if (retorno[i][0] != null) {
                    if (!retorno[i][0].isEmpty()) {
                        linValidas++;
                    }
                }
            }

            String auxRetorno[][] = new String[linValidas][2];
            for (int i = 0; i < auxRetorno.length; i++) {
                auxRetorno[i][0] = retorno[i][0];
                auxRetorno[i][1] = retorno[i][1];
            }


            return auxRetorno;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String[] PeriodoEspecifico(String tipoConsumo, String nomeConsumo, String anoInicial, String anoFinal) {
        try {
            String retorno[] = null;

            aba = planhilhaConsumo.getSheet(tipoConsumo);
            String cabecalho[] = getCabecalhoPlanilhaConsumo(tipoConsumo);

            int indice = 0;
            for (int i = 0; i < cabecalho.length; i++) {
                if (cabecalho[i].equalsIgnoreCase(nomeConsumo)) {
                    indice = i;
                    break;
                }
            }

            Cell[] periodo = aba.getColumn(0);

            Cell[] c = aba.getColumn(indice);
            int inicio = 0;
            int finale = 0;
            /* encontra inicio da leitura */
            for (int i = 2; i < periodo.length; i++) {
                if (!periodo[i].getContents().equalsIgnoreCase("TOTAL")) {
                    if (periodo[i].getContents().split("/")[1].equalsIgnoreCase(anoInicial)) {
                        inicio = i;

                    }
                    if (periodo[i].getContents().split("/")[1].equalsIgnoreCase(anoFinal)) {
                        finale = i;
                        break;
                    }
                }
            }

            if (finale == 0) {
                finale = c.length;
            }

            retorno = new String[c.length];
            int aux = 0;
            for (int i = inicio; i < /*retorno.length*/ finale; i++) {
                if (!periodo[i].getContents().equalsIgnoreCase("TOTAL")) {
                    retorno[aux] = periodo[i].getContents();
//                    System.out.println("retorno[" + aux + "]: " + retorno[aux]);
                    aux++;
                }
            }

            return retorno;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        PredictPropriedades.leProperties();
        try {
            String[][] dados = new ControleLeitura().consumoEspecifico("Interligado CATIVO", "RESIDENCIAL (MWh)", "1994", "2008");

            for (int i = 0; i < dados.length; i++) {
                System.out.println(dados[i][0] + " - " + dados[i][1]);
            }

            List<String> ano = new ArrayList<String>();
            for (int i = 0; i < dados.length; i++) {
                if (dados[i][0] != null) {
                    if (!ano.contains(dados[i][0].split("/")[1])) {
                        ano.add(dados[i][0].split("/")[1]);
                    }
                }
            }
            /**
             *
             */
            String dFinal[][] = new String[12][ano.size()];
            int cont = 0;
            for (int i = 0; i < ano.size(); i++) {
                for (int j = 0; j < dados.length; j++) {
                    if (dados[j][0].split("/")[1].equalsIgnoreCase(ano.get(i))) {
                        dFinal[cont][i] = dados[j][1];
                        cont++;
                    }
                }
                cont = 0;
            }

            for (int i = 0; i < dFinal.length; i++) {
                for (int j = 0; j < dFinal[i].length; j++) {
                    System.out.print(dFinal[i][j] + "\t");
                }
                System.out.println("");
            }




        } catch (IOException ex) {
            Logger.getLogger(ControleLeitura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(ControleLeitura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PredictException ex) {
            Logger.getLogger(ControleLeitura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
