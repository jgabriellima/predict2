/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpa.lprad.predict.util.io;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author J. Gabriel Lima
 */
public class AbrirArquivo {
    private static File arquivo;    
    private static String caminhoArquivo;
    private static JFileChooser escolherArquivo;

    public static void abrirArquivo()
    {
        try{
            escolherArquivo = new JFileChooser(System.getProperty("user.home"));

            escolherArquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
//            escolherArquivo.setFileFilter(new FileFilter() {
//
//                @Override
//                public boolean accept(File f) {
//                    if(f.getAbsolutePath().endsWith(".xls")||f.getAbsolutePath().endsWith(".xlsx"))
//                        return true;
//                    else
//                        return false;
//                }
//
//                @Override
//                public String getDescription() {
//                    return "Arquivo Excel - *.xsl / *.xlsx";
//                }
//            });

            int resultado = escolherArquivo.showOpenDialog(null);
            if(resultado==JFileChooser.APPROVE_OPTION)
            {
                setArquivo(escolherArquivo.getSelectedFile());
                setCaminhoArquivo(getArquivo().getAbsolutePath());
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

     public static void abrirCaminhoArquivo()
    {
        try{
            escolherArquivo = new JFileChooser(System.getProperty("user.home"));

            escolherArquivo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            
            int resultado = escolherArquivo.showOpenDialog(null);
            if(resultado==JFileChooser.APPROVE_OPTION)
            {
                setArquivo(escolherArquivo.getSelectedFile());
                setCaminhoArquivo(getArquivo().getAbsolutePath());
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static File getArquivo() {
        return arquivo;
    }

    public static void setArquivo(File arquivo) {
        AbrirArquivo.arquivo = arquivo;
    }

    public static String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public static void setCaminhoArquivo(String caminhoArquivo) {
        AbrirArquivo.caminhoArquivo = caminhoArquivo;
    }

    public static JFileChooser getEscolherArquivo() {
        return escolherArquivo;
    }

    public static void setEscolherArquivo(JFileChooser escolherArquivo) {
        AbrirArquivo.escolherArquivo = escolherArquivo;
    }


}
