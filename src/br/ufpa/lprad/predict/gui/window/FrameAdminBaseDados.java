/*
 * FrameAdminBaseDados.java
 *
 * Created on 20 de Janeiro de 2009, 18:00
 */

package br.ufpa.lprad.predict.gui.window;

import br.ufpa.lprad.predict.util.ListaArquivos;
import br.ufpa.lprad.predict.exception.PredictException;
import br.ufpa.lprad.predict.propriedades.PredictPropriedades;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;



/**
 *
 * @author  Gabriel
 */
public class FrameAdminBaseDados extends javax.swing.JInternalFrame {

    public int contador=0; // contador das linhas da tabela
    public Vector lista_arquivos = new Vector();
    public ListaArquivos list;
    public DateFormat formatData;
    public File[] lista;
    
    public FrameAdminBaseDados() {
        initComponents();
        btn_Adicionar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btn_action(e);
            }
        });
        le_pasta();
    }

    public void le_pasta()
    {
        try {
            contador = 0;
            File dir = new File(PredictPropriedades.getCaminhoPrincipal() + "\\arquivos\\estadual\\");
            lista = dir.listFiles(new FileFilter() {

                public boolean accept(File pathname) {
                    if (pathname.getAbsolutePath().endsWith(".xls")) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            //lista_arquivos = new ListaArquivos[lista.length];
            formatData = new SimpleDateFormat("dd/MM/yyyy");
            adicionaLista(lista);
            cria_tabela(lista_arquivos);
        } catch (PredictException ex) {
            Logger.getLogger(FrameAdminBaseDados.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    private void adicionaLista(File[] lista) {
        
        lista_arquivos.removeAllElements();
       for(int i=0; i<lista.length; i++)
        {
            String nome = lista[i].getName();
            String data = formatData.format(new Date(lista[i].lastModified()));
           // long tam = lista[i].getUsableSpace()/(1024 * 1024);
            list = new ListaArquivos(nome,data);//,tam);
            lista_arquivos.add(list);
        }
        
    }
    
    private void cria_tabela(Vector lista_arquivos) {
        
        
        tabela_arquivos.updateUI();
        
        setTabelaModel(lista_arquivos.size()+2);
        
        for(int i=0; i<lista_arquivos.size(); i++)
        {
            tabela_arquivos.setValueAt(((ListaArquivos)lista_arquivos.elementAt(i)).getNome(), contador, 0);
            tabela_arquivos.setValueAt(((ListaArquivos)lista_arquivos.elementAt(i)).getData_modificacao(), contador, 1);
          //  tabela_arquivos.setValueAt(((ListaArquivos)lista_arquivos.elementAt(i)).getTam(), contador, 2);
            
            contador++;
        }
        
    }
   
    public void setTabelaModel(int row)
    {
        String[] cabecalho = {"Nome","Data de Modificação"};//,"Tamanho"};
        tabela_arquivos.setModel(new DefaultTableModel(cabecalho, row));
        
        
    }

    public File[] getLista() {
        return lista;
    }

    public void setLista(File[] lista) {
        this.lista = lista;
    }
    



    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela_arquivos = new javax.swing.JTable();
        btn_atualizar = new javax.swing.JButton();
        btn_remover = new javax.swing.JButton();
        btn_fechar = new javax.swing.JButton();
        btn_Adicionar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("Atualizar Base de Dados");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/predict.gif"))); // NOI18N
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        setVisible(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Arquivos"));

        tabela_arquivos.setFont(new java.awt.Font("Arial", 0, 14));
        tabela_arquivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tabela_arquivos);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_atualizar.setText("Atualizar");
        btn_atualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_atualizarActionPerformed(evt);
            }
        });

        btn_remover.setText("Remover");
        btn_remover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_removerActionPerformed(evt);
            }
        });

        btn_fechar.setText("Fechar");
        btn_fechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_fecharActionPerformed(evt);
            }
        });

        btn_Adicionar.setText("Adicionar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_remover, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_Adicionar, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_atualizar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                    .addComponent(btn_fechar))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(btn_Adicionar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_remover)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_atualizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_fechar))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void btn_atualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_atualizarActionPerformed

    
    le_pasta();
}//GEN-LAST:event_btn_atualizarActionPerformed

private void btn_removerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_removerActionPerformed

int result = JOptionPane.showOptionDialog(this, "Deseja realmente remover essa base de dados?","Atenção", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Sim","Não"}, null);
if(result==0)  
for(int i=0; i<lista.length; i++)
    {
        if(((ListaArquivos)getLista_arquivos().get(tabela_arquivos.getSelectedRow())).getNome().equals(lista[i].getName()))
        {
          
          lista[i].delete();
          getLista_arquivos().remove(tabela_arquivos.getSelectedRow());
          contador--;
          cria_tabela(lista_arquivos);
          le_pasta();
          tabela_arquivos.updateUI();
        }
    }
    
    
    
}//GEN-LAST:event_btn_removerActionPerformed

private void btn_fecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_fecharActionPerformed
        try {

            setClosed(true);
        } catch (PropertyVetoException ex) {//GEN-LAST:event_btn_fecharActionPerformed
            Logger.getLogger(FrameAdminBaseDados.class.getName()).log(Level.SEVERE, null, ex);
        }
}

public void btn_action(ActionEvent evt){
        try {
            JFileChooser escolher_arquivo = new JFileChooser(PredictPropriedades.getCaminhoPrincipal());
            escolher_arquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
            escolher_arquivo.setFileFilter(new javax.swing.filechooser.FileFilter() {

                public boolean accept(File pathname) {
                    /* if(pathname.getAbsolutePath().endsWith(".xls"))
                    {
                    return true;
                    }else
                    return false;
                     */
                    return true;
                }

                public String getDescription() {
                    return "Arquivo Excel (.xls)";
                }
            });
            int result = escolher_arquivo.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    if (escolher_arquivo.getSelectedFile().getAbsolutePath().endsWith(".xls")) {
                        copia(escolher_arquivo.getSelectedFile());
                    } else {
                        JOptionPane.showMessageDialog(null, "Formato de arquivo inválido.\nO Predict trabalha com a base de dados no formato '.xls' (Microsoft Exel).\nPor favor, escolha um arquivo válido", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro ao gravar o arquivo na pasta raiz.\nVerifique as permissões de escrita ou contate o administrador.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
            le_pasta();
        } catch (PredictException ex) {
            Logger.getLogger(FrameAdminBaseDados.class.getName()).log(Level.SEVERE, null, ex);
        }
}

   public void copia(File fonte)/*, File destino)*/ throws IOException {
        OutputStream out = null;
        try {
            InputStream in = new FileInputStream(fonte);
            out = new FileOutputStream(PredictPropriedades.getCaminhoPrincipal() + "\\arquivos\\estadual\\" + fonte.getName());
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (PredictException ex) {
            Logger.getLogger(FrameAdminBaseDados.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(FrameAdminBaseDados.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    
    public Vector getLista_arquivos() {
        return lista_arquivos;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Adicionar;
    private javax.swing.JButton btn_atualizar;
    private javax.swing.JButton btn_fechar;
    private javax.swing.JButton btn_remover;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabela_arquivos;
    // End of variables declaration//GEN-END:variables

}
