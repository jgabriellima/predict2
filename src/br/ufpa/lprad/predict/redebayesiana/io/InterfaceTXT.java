package br.ufpa.lprad.predict.redebayesiana.io;

import br.ufpa.lprad.predict.redebayesiana.aprendizado.RedeBayesiana;
import br.ufpa.lprad.predict.redebayesiana.aprendizado.Vertice;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;




/**
 *
 * @author Gabriel
 */
public class InterfaceTXT 
{
    private Hashtable resultado;
    private double tempo;
    private String fileName;
    private RedeBayesiana rb;
    private File file;
    String[] cabecalhos;
     String[] dados;
    
     
    public InterfaceTXT(File file)
    {
        this.file = file;
    }
    
    public void escrever() throws IOException 
    {
        PrintWriter out = new PrintWriter(new FileWriter(this.fileName));
                        
        out.println("Tempo de Execução do Algoritmo:  "+this.tempo+" segundos.");                
        int tamanho = rb.getNVertices();
        for (int i=0;i<tamanho;i++)
        {            
            Vertice va = rb.getVerticeByIndex(i);            
            out.println(va.getNome());                        
            int nEstados = va.getNEstados();
            String[] estados = va.getEstados();                        
            for (int j=0;j<nEstados;j++)
            {                
                String key = va.getNome()+"="+estados[j];
                String valor = (String)resultado.get(key);                
                JLabel labelValor = new JLabel(valor);
                out.println("     "+estados[j]+"  =  "+valor);
                          
            } 
        }
        out.close();
    }
    
    public JTable dataToTable()
    {
        JTable tabela = new JTable();
        tabela.setAutoResizeMode(3);
        DefaultTableModel modeloTabela = new DefaultTableModel();
        tabela.setModel(modeloTabela);
        
        try
        {
            FileReader reader = new FileReader(this.file);    
            
            //System.out.println("metodo dataToTable: "+file.getAbsolutePath());
            
            BufferedReader leitor = new BufferedReader(reader);
            String cabecalho = leitor.readLine();
           cabecalhos = cabecalho.split("\t");                                                
            
            
            modeloTabela.setColumnIdentifiers(cabecalhos);          
            String linha = null;            
          //  System.out.println("linhas: "+leitor.readLine());
            while( (linha = leitor.readLine()) !=  (null) )
            {                
                dados = linha.split("\t");
                modeloTabela.addRow(dados);
            }
            reader.close();
        }
        
        catch (Exception e)
        {
            System.out.println(e);
        }
      //  for(int i=0; i<dados.length; i++)
           // System.out.println("dad: "+dados[i]);
        
        
        return tabela;
    }    
}
    
