/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.util.io;

import br.ufpa.lprad.predict.propriedades.PredictPropriedades;
import br.ufpa.lprad.predict.util.BigDecimalUtility;
import java.io.File;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author J. Gabriel Lima
 */
public class ExportarXLS {

    private static NumberFormat nf;
    static Locale locale;
    private static String path;

    public static void exportarPlanilhaXLS(String[] cabecalho, String[][] matriz, String tipoConsumo) {

        locale = new Locale("pt", "BR");
        Locale.setDefault(locale);
        nf = NumberFormat.getNumberInstance(locale);

        try {
            WritableWorkbook workbook = null;
            WritableSheet s = null;
            //define o formato dos nomes dos atributos da tabela
            WritableFont wfColunas = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            WritableCellFormat cfColunas = new WritableCellFormat(wfColunas);
            cfColunas.setWrap(false);
            cfColunas.setAlignment(jxl.format.Alignment.CENTRE);
            cfColunas.setBackground(jxl.format.Colour.YELLOW);
            cfColunas.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
            //define o formato dos dados que preenchem a tabela
            WritableFont wfDados = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
            WritableCellFormat cfDados = new WritableCellFormat(wfDados);
            cfDados.setWrap(false);
            cfDados.setAlignment(jxl.format.Alignment.JUSTIFY);
            cfDados.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);

            path = PredictPropriedades.getCaminhoArquivosExportados() + "\\" +/*tipoConsumo.replace("-", "").replace(" ", "").replace("\\", "_").trim() + "_" + */ tipoConsumo + "_" + getDataHora() + ".xls";

            if(tipoConsumo.isEmpty()){
                tipoConsumo="Predict II";
            }
            workbook = Workbook.createWorkbook(new File(path));
            s = workbook.createSheet(tipoConsumo, 0);
            preencheColunasXLS(cabecalho, s, cfColunas, tipoConsumo);

//            for(int i=0; i<matriz.length; i++)
//            {
//                for(int j=0; j<matriz[i].length; j++)
//                {
//                    System.out.print(""+matriz[i][j]+"\t");
//                }
//                System.out.println("");
//            }

            Label l = null;
            jxl.write.Number n;
            for (int i = 0; i < matriz.length; i++) {
                s.addCell(new Label(0, i + 2, matriz[i][0], cfDados));
            }

            for (int i = 0; i < matriz.length; i++) {
                for (int j = 1; j < matriz[0].length; j++) {
                    try {
//                        n = new jxl.write.Number(j, i + 2, nf.parse(matriz[i][j]).doubleValue());
//                        n = new jxl.write.Number(j, i + 2, BigDecimalUtility.setFormattedCurrency(matriz[i][j].replace(",", ".")).doubleValue());
                        String num = matriz[i][j];
//                        System.out.println("NUM: "+num);
                        l = new Label(j, i + 2, num);
                        s.addCell(l);
                    } catch (Exception ex) {
//                        ex.printStackTrace();
                        try {
                            l = new Label(j, i + 2, matriz[i][j].replace(".", ""), cfDados);
                        } catch (Exception e) {
//                            e.printStackTrace();
                            l = new Label(j, i + 2, "", cfDados);
                        }
                        s.addCell(l);
                    }
                }
            }

            workbook.write();
            workbook.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getPathXLS() {
        return path;
    }

    // metodo que preenche os rótulos das colunas de acordo com o tipo de dados
    private static void preencheColunasXLS(String[] colunas, WritableSheet s, WritableCellFormat cf, String nomeXLS) {
        Label l = null;

        try {
            //mescla a primeira linha da planilha que possui o tipo de dado da planilha
            s.mergeCells(0, 0, (colunas.length - 1), 0);
            l = new Label(0, 0, nomeXLS, cf);

            s.addCell(l);
            for (int i = 0; i < colunas.length; i++) {
                l = new Label(i, 1, colunas[i], cf);
                s.addCell(l);
            }
        } catch (WriteException writeEx) {
            writeEx.printStackTrace();
        }
    }

    private static String getDataHora() {
        Date data = new Date();
        SimpleDateFormat fData = new SimpleDateFormat("dd_MM_yyyy");
        SimpleDateFormat fHora = new SimpleDateFormat("HH_mm_ss");
        String s = fData.format(data) + "_" + fHora.format(data);
        return s;
    }
}
