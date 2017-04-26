package br.ufpa.lprad.predict.redebayesiana.inferencias;

import br.ufpa.lprad.predict.exception.PredictException;
import br.ufpa.lprad.predict.redebayesiana.grafo.Objetos;
import br.ufpa.lprad.predict.redebayesiana.grafo.Tela;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;



public class Entrada extends JFrame {
    
    // Referencias Gerais do Programa
    private Processamento proc = new Processamento(this);
    private MetaDados meta;
    private Objetos objetos = new Objetos();
    private Itens itens;
    private JPanel panelInformacoes;
    
    // painel com o scrollbar
    private JPanel panelScroll;
    private JScrollPane scroll;
   
    // Variaveis de manipulacao de arquivos
    public boolean arquivoAberto = false;
    
    // Definicao de componentes graficos
    private javax.swing.JButton BotAbrir;
    private javax.swing.JButton BotFechar;
    private javax.swing.JPanel painel;
    
    //Variaveis do Menu
    private javax.swing.JMenuItem itemNova;
    private javax.swing.JMenuItem itemAbrir;
    private javax.swing.JMenuItem itemExpandir;
    private javax.swing.JMenuItem itemFechar;
    private javax.swing.JMenuItem itemIniciar;
    private javax.swing.JMenuItem itemRetrair;
    private javax.swing.JMenuItem itemSalvar;
    private javax.swing.JMenuItem itemSelecionar;
    private javax.swing.JMenu menuArquivo;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuRede;
    private javax.swing.JMenu menuAjuda;
    private javax.swing.JMenuItem itemSobre;
    
    
    /** Creates new form Principal */
    public Entrada() {
        try {
            //initComponents();
            arquivoAberto = proc.abreArquivo(true);
            addWindowListener(new WindowAdapter() {

                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
            if (arquivoAberto) {
                meta = new MetaDados(proc.getArqTxt(), this);
                objetos = new Objetos(meta);
                proc.runScullyRun(meta);
            }
            iniciaTela();
        } catch (PredictException ex) {
            Logger.getLogger(Entrada.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void initComponents() {
        
        painel = new javax.swing.JPanel();
        
        // ####### Menu ###########################
        
        menuBar = new javax.swing.JMenuBar();
        menuArquivo = new javax.swing.JMenu();
        itemNova = new javax.swing.JMenuItem();
        itemAbrir = new javax.swing.JMenuItem();
        itemSalvar = new javax.swing.JMenuItem();
        itemFechar = new javax.swing.JMenuItem();
        menuRede = new javax.swing.JMenu();
        itemIniciar = new javax.swing.JMenuItem();
        itemExpandir = new javax.swing.JMenuItem();
        itemRetrair = new javax.swing.JMenuItem();
        itemSelecionar = new javax.swing.JMenuItem();
        menuAjuda = new javax.swing.JMenu();
        itemSobre = new javax.swing.JMenuItem();
        scroll = new JScrollPane();
        
        
        menuArquivo.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        menuArquivo.setLabel("Arquivo");
        
        itemNova.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        itemNova.setText("Nova");
        menuArquivo.add(itemNova);
        
        itemAbrir.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        itemAbrir.setText("Abrir");
        menuArquivo.add(itemAbrir);
        
        itemSalvar.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        itemSalvar.setText("Salvar");
        menuArquivo.add(itemSalvar);
        
        itemFechar.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        itemFechar.setText("Fechar");
        menuArquivo.add(itemFechar);
        
        menuBar.add(menuArquivo);
        
        menuRede.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        menuRede.setLabel("Rede");
        itemIniciar.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        itemIniciar.setText("Inicializar");
        menuRede.add(itemIniciar);
        
        itemExpandir.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        itemExpandir.setText("Expandir");
        menuRede.add(itemExpandir);
        
        itemRetrair.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        itemRetrair.setText("Retrair");
        menuRede.add(itemRetrair);
        
        itemSelecionar.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        itemSelecionar.setText("Selecionar Tudo");
        menuRede.add(itemSelecionar);
        
        menuBar.add(menuRede);
        
        menuAjuda.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        menuAjuda.setLabel("Ajuda");
        itemSobre.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        itemSobre.setText("Sobre");
        menuAjuda.add(itemSobre);
        
        menuBar.add(menuAjuda);
        
        setJMenuBar(menuBar);
        
        
        setTitle("Sistema Rede Celpa");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });
        
        menuBar.setSize(new Dimension(1020, 710));
        getContentPane().add(painel, java.awt.BorderLayout.CENTER);
        painel.setSize(new Dimension(1020,710));
        
        itemNova.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemNovaActionPerformed(evt);
            }
        });
        
        itemAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAbrirActionPerformed(evt);
            }
        });
        
        itemFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemFecharActionPerformed(evt);
            }
        });
        
        itemSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSalvarActionPerformed(evt);
            }
        });
        
        
        itemExpandir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemExpandirActionPerformed(evt);
            }
        });
        
        itemRetrair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemRetrairActionPerformed(evt);
            }
        });
        
        itemSelecionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSelecionarActionPerformed(evt);
            }
        });
        
        itemSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSobreActionPerformed(evt);
            }
        });
        
        setSize(new Dimension(1020,710));
    }
    
    private void itemFecharActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }
    
    private void itemNovaActionPerformed(java.awt.event.ActionEvent evt) {
        if(arquivoAberto){
            
            JPanel panelGrafico = new javax.swing.JPanel();
            panelGrafico.setLayout(new java.awt.BorderLayout());
            panelGrafico.setBackground(java.awt.Color.white);
            panelGrafico.setBorder(new javax.swing.border.TitledBorder(null, "Rede Bayesiana", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("MS Sans Serif", 1, 11)));            
            
            getContentPane().add(panelGrafico); 
            setSize(new Dimension(1020, 710));         
        }        
    }
    
    private void itemSalvarActionPerformed(java.awt.event.ActionEvent evt) {
        if(arquivoAberto){
            proc.salvaArquivo();
        }        
    }
    
    private void itemAbrirActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            arquivoAberto = proc.abreArquivo(true);
            if (arquivoAberto) {
                meta = new MetaDados(proc.getArqTxt(), this);
                objetos = new Objetos(meta);
                proc.runScullyRun(meta);
            }
            iniciaTela();
            painel.hide();
        } catch (PredictException ex) {
            Logger.getLogger(Entrada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void itemIniciarActionPerformed(java.awt.event.ActionEvent evt) {
        
    }
    
    private void itemExpandirActionPerformed(java.awt.event.ActionEvent evt) {
        
    }
    
    private void itemRetrairActionPerformed(java.awt.event.ActionEvent evt ) {
        
    }
    
    private void itemSelecionarActionPerformed(java.awt.event.ActionEvent evt ) {
        
    }
    
    private void itemSobreActionPerformed(java.awt.event.ActionEvent evt) {
        JPanel p1 = new JPanel();
        JLabel label1;
        JLabel label2;
        JLabel label3;
        
        label1 = new JLabel("[Predict - Ferramenta de Suporte à Decisão para Predição de Cargas de Sistemas Elétricos]");
        label2 = new JLabel("[Centrais Elétricas do Pará - CELPA ]");
        label3 = new JLabel("[2007]");
        p1.add(label1, BorderLayout.NORTH);
        p1.add(label2, BorderLayout.CENTER);
        p1.add(label3, BorderLayout.SOUTH);
        
        JDialog f1 = new JDialog(this,"Sobre",true);
        f1.setLayout(new BorderLayout());
        f1.add(p1);
        f1.pack();
        f1.setSize(385, 125);
        f1.show();
    }
    
    public void iniciaTela() {
        
        panelInformacoes = new javax.swing.JPanel();
        panelInformacoes.setLayout(null);        
        panelInformacoes.setBorder(new javax.swing.border.TitledBorder(null, "Inferência Bayesiana", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("MS Sans Serif", 1, 11)));
        panelInformacoes.setPreferredSize(new java.awt.Dimension(300, 200));         
        
        JPanel panelGrafico = new javax.swing.JPanel();
        
        panelGrafico.setLayout(new java.awt.BorderLayout());
        panelGrafico.setBackground(java.awt.Color.white);
        panelGrafico.setBorder(new javax.swing.border.TitledBorder(null, "Rede Bayesiana", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("MS Sans Serif", 1, 11)));
        
        // Adiciona os Nos e Arcos na tela
        panelGrafico.add(new Tela(objetos));
        
        
        // Adiciona os itens na tela
        //itens = new Itens(meta, meta.getOriginais(), panelInformacoes, this);      
        itens = new Itens(meta, meta.getOriginais(), panelInformacoes, panelGrafico);      
        
        JScrollPane scroll = new JScrollPane(Itens.getPanelInfo());       
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);        
        
        Container containerG = getContentPane();
        containerG.add(menuBar, BorderLayout.PAGE_START);
        containerG.add(scroll, BorderLayout.WEST);
        
        containerG.add(panelGrafico, BorderLayout.CENTER);
        
        JFrame f = new JFrame("Visualizando a Rede Bayesiana");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });

        f.add(containerG); 
        f.pack();
        setSize(new Dimension(1020,710));
        show();        
    }
    
//    public static void main(String args[]) {
//        new Entrada().show();
//    }
    
    private void exitForm(WindowEvent evt) {
        System.exit(0);
    }
    
    public JPanel PanelInf(){
        return panelInformacoes;
    }
}