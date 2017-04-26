package br.ufpa.lprad.predict.redebayesiana.grafo;

import br.ufpa.lprad.predict.actions.Actions_Rede;
import br.ufpa.lprad.predict.redebayesiana.inferencias.BotAtrib;
import br.ufpa.lprad.predict.redebayesiana.inferencias.Entrada;
import br.ufpa.lprad.predict.redebayesiana.inferencias.Itens;
import br.ufpa.lprad.predict.redebayesiana.inferencias.MetaDados;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;
import java.util.Vector;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class Tela extends JPanel implements MouseListener, MouseMotionListener, Serializable {

    // DEFINICAO DE CONSTANTES
    final double ALT_RET = 50;             // Largura dos Nos
    final double LARG_RET = 100;           // Altura dos Nos
    final double ARC_W = 50;               // Largura do arco dos Nos
    final double ARC_H = 50;               // Altura do arco dos Nos

    // Variaveis da Tela
    No foco;                               // Objeto clicado
    Objetos objetos = new Objetos(this);   // Referencias de objetos
    Itens itens;
    MetaDados meta;
    BotAtrib[] botoes;
    Entrada entrada;                    // Ligacao com classe principal do codigo
    double last_x, last_y;              // Registra as coordenadas da ultima posicao onde o mouse foi clicado
    boolean firstTime = true;           // Marca se essa e a primeira interacao do programa
    Rectangle area;                     // Area de trabalho do programa
    boolean pressOut = false;           // True qnd mouse for pressionado/arrastado/solto fora do No
    boolean botClicado = true;
    public static Vector<No> selecionados;
    public static JPopupMenu menupopup;

    public static Vector getSelecionado() {
        return selecionados;
    }

    public void criaMenupopup() {
        JMenuItem AnaliseTemporal = new JMenuItem("Análise Temporal Markoviana");
        AnaliseTemporal.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Actions_Rede.action_analise_temp(e, null);
            }
        });
        JMenuItem ag = new JMenuItem("Análise de Cenários");
        ag.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Actions_Rede.action_algoritmo_genetico(e, null);
            }
        });
        menupopup = new JPopupMenu();
//        menupopup.add(AnaliseTemporal);
        menupopup.add(ag);

    }

    public Tela(Objetos obj) {
        setBackground(Color.white);
        addMouseMotionListener(this);
        addMouseListener(this);

        this.objetos = obj;
        selecionados = new Vector();
    }

    void adicionaFoco(MouseEvent e) {


        foco = objetos.objetoClicado(e);
        if (!selecionados.contains(foco)) {
            selecionados.removeAllElements();
            selecionados.add(foco);
        }
    }
    // Metodo para lidar com os Botoes de Atributos e as Faixas

    public void mouseClicked(MouseEvent e) {
        criaMenupopup();
        int Y_INIT_BOTAO = 17;
        adicionaFoco(e);
        if (e.isMetaDown()) {
            try {
                if (!foco.contains(e.getX(), e.getY())) {
                    JOptionPane.showMessageDialog(null, "Para mais opções click ou selecione um nó");
                    selecionados.removeAllElements();
                } else {
                    adicionaFoco(e);
                    this.setComponentPopupMenu(menupopup);
                    menupopup.show(true);
                }
            } catch (NullPointerException ex) {
                System.err.println("" + ex.getMessage());
                JOptionPane.showMessageDialog(null, "Para usar as opções selecione um nó e click no botão direito do mouse");
            }
        }
        repaint();
        if (meta != null) {
            for (int i = 0; i < meta.getQntdAtrib(); i++) {
                botoes[i].atualiza(Y_INIT_BOTAO);
            }
        }
    }

    // Handles the event of the user pressing down the mouse button.
    public void mousePressed(MouseEvent e) {
        // Checks whether or not the cursor is inside of the rectangle while the user is pressing the mouse.
        foco = objetos.objetoClicado(e);
        if (foco != null) {
            last_x = foco.x - e.getX();
            last_y = foco.y - e.getY();
            updateLocation(e);
        } else {
            pressOut = true;
        }
    }

    // Handles the event of a user dragging the mouse while holding down the mouse button.
    public void mouseDragged(MouseEvent e) {
        if (!pressOut) {
            updateLocation(e);
        }

    }

    // Handles the event of a user releasing the mouse button.
    public void mouseReleased(MouseEvent e) {

        // Checks whether or not the cursor is inside of the rectangle when the user releases the mouse button.
        foco = objetos.objetoClicado(e);

        if (foco != null) {
            updateLocation(e);
        } else {
            pressOut = false;
        }

        foco = null;
    }

    // Updates the coordinates representing the location of the current rectangle.
    public void updateLocation(MouseEvent e) {
        foco.setLocation(last_x + e.getX(), last_y + e.getY());
        // checkRect();
        repaint();
    }

    public void paint(Graphics g) {
        update(g);
    }

    public void update(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        RenderingHints hints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        hints.add(new RenderingHints(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY));
        g2.setRenderingHints(hints);
        Dimension dim = getSize();
        int w = (int) dim.getWidth();
        int h = (int) dim.getHeight();
        g2.setStroke(new BasicStroke(8.0f));

        // Na primeira atualizacao da tela, posiciona os Nos
        if (firstTime) {
            area = new Rectangle(dim);
            firstTime = false;
        }

        // Limpa a tela com um 'No branco'
        g2.setPaint(Color.white);
        g2.fillRect(0, 0, w, h);

        objetos.desenhaObjetos(g2);

    }

    /**
     * Checks if the rectangle is contained within the applet window.  If the rectangle
     * is not contained withing the applet window, it is redrawn so that it is adjacent
     * to the edge of the window and just inside the window.
     */
    boolean checkRect() {

        if (area == null) {
            return false;
        }
        int new_x = (int) foco.x;
        int new_y = (int) foco.y;
        //String focos="";
        // Verifica se 'rect' esta dentro de 'area'

        if (area.contains(foco.x, foco.y, LARG_RET, ALT_RET)) {
            return true;
        }


        if (foco.x + LARG_RET > area.getWidth()) {
            new_x = (int) (area.getWidth() - (LARG_RET + 130));
        }


        if (foco.x < 0) {
            new_x = -1;
        }
        if ((foco.y + ALT_RET) > area.getHeight()) {
            new_y = (int) (area.getHeight() - (ALT_RET - 1));
        }
        if (foco.y < 0) {
            new_y = -1;
        }

        foco.setLocation(new_x, new_y);
        return false;
    }

    // Definicao de metodos exigida por MouseListener e MouseMotionListerner
    public void mouseMoved(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }
}
