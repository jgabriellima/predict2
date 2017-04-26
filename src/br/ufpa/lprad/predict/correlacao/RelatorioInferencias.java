/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.correlacao;

import br.ufpa.lprad.predict.gui.window.Principal;
import br.ufpa.lprad.predict.propriedades.PredictPropriedades;
import br.ufpa.lprad.predict.redebayesiana.inferencias.Itens;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author gabriel
 */
public class RelatorioInferencias {

    private List lista;
    private RelatorioInferenciaBean rib;
    private String titulo;

    private String relatorio;
    private String complemento = "</span></td>" +
            " <td colspan=\"3\"><img alt=\"\" src=\"RelatorioInferencias.html_files/px\" style=\"width: 76px; height: 568px;\"/></td>" +
            "</tr>" +
            "<tr valign=\"top\">" +
            "  <td colspan=\"10\"><img alt=\"\" src=\"RelatorioInferencias.html_files/px\" style=\"width: 595px; height: 136px;\"/></td>" +
            "</tr>" +
            "<tr>"+
            "</tr>" +
            "</table></td><td width=\"50%\">" +
            "</td></tr>" +
            "</table>" +
            "</body>" +
            "</html>\"";
    private String textoInfer;
    public RelatorioInferencias(String txt, String titulo) {
        this.textoInfer = txt;
        this.titulo = titulo;
        montaRelatorio();
        criaRelatorio();

    }

    public void criaRelatorio() {

        BufferedWriter bw = null;
        try {
            File f = new File(PredictPropriedades.getCaminhoRelatorioInferencias());

            f.createNewFile();

            bw = new BufferedWriter(new FileWriter(f));
            bw.append(relatorio);
            bw.append(textoInfer);
            bw.append(complemento);

            bw.close();


        } catch (IOException ex) {
            JOptionPane.showMessageDialog(Principal.getDesktopPane(), "Ocorreu um erro ao gerar o relatório de projeção.\n Se o problema persistir, contate o administrador.", "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(Principal.getDesktopPane(), "Ocorreu um erro ao gerar o relatório de projeção.\n Se o problema persistir, contate o administrador.", "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }

    }

    private void montaRelatorio() {
       relatorio = "<html>" +
            "<head>" +
            "<title></title>" +
            "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>" +
            " <style type=\"text/css\">" +
            "  a {text-decoration: none}" +
            "</style>" +
            "</head>" +
            "<body text=\"#000000\" link=\"#000000\" alink=\"#000000\" vlink=\"#000000\">" +
            "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">" +
            "<tr><td width=\"50%\">&nbsp;</td><td align=\"center\">" +
            "<a name=\"JR_PAGE_ANCHOR_0_1\"/>" +
            "<table style=\"width: 595px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"white\">" +
            "<tr>" + " <td><img alt=\"\" src=\"RelatorioInferencias.html_files/px\" style=\"width: 49px; height: 1px;\"/></td>" + " <td><img alt=\"\" src=\"RelatorioInferencias.html_files/px\" style=\"width: 14px; height: 1px;\"/></td>" + "<td><img alt=\"\" src=\"RelatorioInferencias.html_files/px\" style=\"width: 53px; height: 1px;\"/></td>" + "<td><img alt=\"\" src=\"RelatorioInferencias.html_files/px\" style=\"width: 62px; height: 1px;\"/></td>" + "<td><img alt=\"\" src=\"RelatorioInferencias.html_files/px\" style=\"width: 244px; height: 1px;\"/></td>" + "<td><img alt=\"\" src=\"RelatorioInferencias.html_files/px\" style=\"width: 63px; height: 1px;\"/></td>" + "<td><img alt=\"\" src=\"RelatorioInferencias.html_files/px\" style=\"width: 34px; height: 1px;\"/></td>" + "<td><img alt=\"\" src=\"RelatorioInferencias.html_files/px\" style=\"width: 14px; height: 1px;\"/></td>" + "<td><img alt=\"\" src=\"RelatorioInferencias.html_files/px\" style=\"width: 17px; height: 1px;\"/></td>" + "<td><img alt=\"\" src=\"RelatorioInferencias.html_files/px\" style=\"width: 45px; height: 1px;\"/></td>" + "</tr>" +
            "<tr valign=\"top\">" +
            "<td colspan=\"10\"><img alt=\"\" src=\"RelatorioInferencias.html_files/px\" style=\"width: 595px; height: 42px;\"/></td>" +
            "</tr>" +
            "<tr valign=\"top\">" + " <td colspan=\"2\"><img alt=\"\" src=\"RelatorioInferencias.html_files/px\" style=\"width: 63px; height: 16px;\"/></td>" + " <td colspan=\"6\" style=\"text-align: center;\"><span style=\"font-family: Arial; font-size: 12.0px; font-weight: bold;\">Predict - Ferramenta de Suporte &agrave; Decis&atilde;o para Predi&ccedil;&atilde;o de Cargas de Sistemas El&eacute;tricos </span></td>" + " <td colspan=\"2\"><img alt=\"\" src=\"RelatorioInferencias.html_files/px\" style=\"width: 62px; height: 16px;\"/></td>" + "</tr>" + "<tr valign=\"top\">" +
            " <td colspan=\"10\"><img alt=\"\" src=\"RelatorioInferencias.html_files/px\" style=\"width: 595px; height: 9px;\"/></td>" +
            "</tr>" +
            "<tr valign=\"top\">" +
            "  <td><img alt=\"\" src=\"RelatorioInferencias.html_files/px\" style=\"width: 49px; height: 7px;\"/></td>" +
            "  <td colspan=\"2\" rowspan=\"3\"><img src=\"RelatorioInferencias.html_files/logo_celpa.gif\" style=\"width: 67px; height: 57px\" alt=\"\"/></td>" +
            "  <td colspan=\"3\"><img alt=\"\" src=\"RelatorioInferencias.html_files/px\" style=\"width: 369px; height: 7px;\"/></td>" +
            "  <td colspan=\"3\" rowspan=\"3\"><img src=\"RelatorioInferencias.html_files/logo_celpa.gif\" style=\"width: 65px; height: 57px\" alt=\"\"/></td>" +
            "  <td><img alt=\"\" src=\"RelatorioInferencias.html_files/px\" style=\"width: 45px; height: 7px;\"/></td>" +
            "</tr>" +
            "<tr valign=\"top\">" +
            "  <td><img alt=\"\" src=\"RelatorioInferencias.html_files/px\" style=\"width: 49px; height: 16px;\"/></td>" +
            "  <td><img alt=\"\" src=\"RelatorioInferencias.html_files/px\" style=\"width: 62px; height: 16px;\"/></td>" +
            "  <td style=\"text-align: center;\"><span style=\"font-family: Arial; font-size: 12.0px; font-weight: bold;text-align: center;\">"+titulo+"</span></td>" +
            "  <td><img alt=\"\" src=\"RelatorioInferencias.html_files/px\" style=\"width: 63px; height: 16px;\"/></td>" +
            " <td><img alt=\"\" src=\"RelatorioInferencias.html_files/px\" style=\"width: 45px; height: 16px;\"/></td>" +
            "</tr>" +
            "<tr valign=\"top\">" +
            "  <td><img alt=\"\" src=\"RelatorioInferencias.html_files/px\" style=\"width: 49px; height: 34px;\"/></td>" +
            "  <td colspan=\"3\"><img alt=\"\" src=\"RelatorioInferencias.html_files/px\" style=\"width: 369px; height: 34px;\"/></td>" +
            "  <td><img alt=\"\" src=\"RelatorioInferencias.html_files/px\" style=\"width: 45px; height: 34px;\"/></td>" +
            "</tr>" +
            "<tr valign=\"top\">" +
            "  <td colspan=\"10\"><img alt=\"\" src=\"RelatorioInferencias.html_files/px\" style=\"width: 595px; height: 14px;\"/></td>" +
            "</tr>" +
            "<tr valign=\"top\">" +
            "  <td colspan=\"2\"><img alt=\"\" src=\"RelatorioInferencias.html_files/px\" style=\"width: 63px; height: 568px;\"/></td>" +
            " <td colspan=\"5\"><span style=\"font-family: sansserif; font-size: 10.0px;\">"
            ;
    }
}
