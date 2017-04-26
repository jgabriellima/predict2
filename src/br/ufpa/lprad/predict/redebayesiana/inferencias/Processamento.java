
package br.ufpa.lprad.predict.redebayesiana.inferencias;

import br.ufpa.lprad.predict.exception.PredictException;
import br.ufpa.lprad.predict.propriedades.PredictPropriedades;
import java.awt.Container;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;



public class Processamento implements Serializable{
    
    ArqTxt arquivo; // = new ArqTxt(arq);   //trata o arquivo a ser lido
    Container origem;
    MetaDados meta;
    int nVx;
    int[] clicados;
    
    /** Creates a new instance of Processamento */
    public Processamento(Container jf) {
        
     //   System.out.println("Classe Processamento");
        
        if (jf instanceof JDialog)
            origem = (JDialog)jf;
        else if (jf instanceof JFrame)
            origem = (JFrame)jf;
        else if (jf instanceof JInternalFrame)
            origem = (JInternalFrame)jf;
        
        
        
    }
    
    // Nao entendeu Liviane? Eh uma quote classica do Arquivo X... Qnd a coisa
    // ficava feia o Mulder sempre gritava pra Scully (parceira dele): "Run Scully, Run!!"
    public void runScullyRun(MetaDados md) {
        System.out.println("Processamento - runScully()");
        meta = md;
        
        // Verifica duracao do calculo da bayesiana
        long inicio, fim;
        inicio = System.currentTimeMillis();
        ProbabilidadeNo probGeral = new ProbabilidadeNo(meta, meta.getOriginais());
        
        
        System.out.println("Tempo");
        fim = System.currentTimeMillis();
        System.out.println("Tempo decorrido: "+(((double) fim - (double) inicio)/1000)+ " segundos");
        
        //probGeral.printResults();
        
    }
    
    public ArqTxt getArqTxt() {
     //   System.out.println("Processamento - getArqTxt()");
        return arquivo;
    }
    
    public boolean abreArquivo(boolean priVez) throws PredictException{
     //   System.out.println("Processamento - abreArquivo()");
        File arq;          //instancia o caminho do arquivo a ser lido
        int resultado;
        
        /*JFileChooser escolharquivo = new JFileChooser();
        escolharquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
        resultado = escolharquivo.showOpenDialog(origem);
        if (resultado == JFileChooser.CANCEL_OPTION)
            return false;
         */
        //arq = escolharquivo.getSelectedFile();
        arq = new File(PredictPropriedades.getCaminhoCorrelacao());
        
        if (arq == null || arq.getName().equals("")) {
            JOptionPane.showMessageDialog(
                    origem,
                    "Nome de Arquivo invalido",
                    "Nome de Arquivo invalido",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            // abre o arquivo
            try {
                arquivo = new ArqTxt(arq);   //trata o arquivo a ser lido
                return true;
                
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(origem, "Arquivo nao existe", "Arquivo Invalido", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(origem, "Erro Desconhecido", "Erro Desconhecido", JOptionPane.ERROR_MESSAGE);
            }
        }
        return false;
    }
    
    public boolean salvaArquivo() {
       // System.out.println("Processamento - salvarArquivo()");
        File arq;   //instancia o caminho do arquivo a ser lido
        int resultado;
        
        JFileChooser escolharquivo = new JFileChooser();
        escolharquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
        resultado = escolharquivo.showSaveDialog(origem);
        if (resultado == JFileChooser.CANCEL_OPTION)
            return false;
        arq = escolharquivo.getSelectedFile();
        
        if (arq.getName()==null || arq.getName().equals("")) {
            JOptionPane.showMessageDialog(
                    origem,
                    "Nome de Arquivo invalido",
                    "Nome de Arquivo invalido",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                
                Object output = new ObjectOutputStream(new FileOutputStream(arq));
                output = new ArqTxt(arq);   //trata o arquivo a ser lido
                return true;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(origem, "Arquivo nao existe", "Arquivo Invalido", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(origem, "Erro Desconhecido", "Erro Desconhecido", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        return false;
    }
    
    
    
    
}