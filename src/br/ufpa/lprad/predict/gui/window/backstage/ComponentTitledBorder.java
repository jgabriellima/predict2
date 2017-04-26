/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpa.lprad.predict.gui.window.backstage;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;

/**
 *
 * @author Microsoft
 */
public class ComponentTitledBorder implements Border, MouseListener, SwingConstants{
       int offset = 5;

       Component comp;
       JComponent container;
       Rectangle rect; 
       Border border;

       public ComponentTitledBorder(Component comp, JComponent container, Border border){
           this.comp = comp;
           this.container = container;
           this.border = border;
           container.addMouseListener(this);
       }

       public boolean isBorderOpaque(){
           return true;
       }

       public void paintBorder(Component c, Graphics g, int x, int y, int width, int height){
           Insets borderInsets = border.getBorderInsets(c);
           Insets insets = getBorderInsets(c);
           int temp = (insets.top-borderInsets.top)/2;
           border.paintBorder(c, g, x, y+temp, width, height-temp);
           Dimension size = comp.getPreferredSize();
           rect = new Rectangle(offset, 0, size.width, size.height);
           SwingUtilities.paintComponent(g, comp, (Container)c, rect);
       }

       public Insets getBorderInsets(Component c){
           Dimension size = comp.getPreferredSize();
           Insets insets = border.getBorderInsets(c);
           insets.top = Math.max(insets.top, size.height);
           return insets;
       }

       private void dispatchEvent(MouseEvent me){
           if(rect!=null && rect.contains(me.getX(), me.getY())){
               Point pt = me.getPoint();
               pt.translate(-offset, 0);
               comp.setBounds(rect);
               comp.dispatchEvent(new MouseEvent(comp, me.getID()
                       , me.getWhen(), me.getModifiers()
                       , pt.x, pt.y, me.getClickCount()
                       , me.isPopupTrigger(), me.getButton()));
               if(!comp.isValid())
                   container.repaint();
           }
       }

       public void mouseClicked(MouseEvent me){
           dispatchEvent(me);
       }

       public void mouseEntered(MouseEvent me){
           dispatchEvent(me);
       }

       public void mouseExited(MouseEvent me){
          dispatchEvent(me);
       }

       public void mousePressed(MouseEvent me){
           dispatchEvent(me);
       }

       public void mouseReleased(MouseEvent me){
           dispatchEvent(me);
       }

        public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }
        final JPanel proxyPanel = new JPanel();
        proxyPanel.add(new JLabel("Proxy Host: "));
        proxyPanel.add(new JTextField("proxy.xyz.com"));
        proxyPanel.add(new JLabel("  Proxy Port"));
        proxyPanel.add(new JTextField("8080"));
        final JCheckBox checkBox = new JCheckBox("Use Proxy", true);
        checkBox.setFocusPainted(false);
        ComponentTitledBorder componentBorder =
                new ComponentTitledBorder(checkBox, proxyPanel
                , BorderFactory.createEtchedBorder());
        checkBox.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                boolean enable = checkBox.isSelected();
                Component comp[] = proxyPanel.getComponents();
                for(int i = 0; i<comp.length; i++){
                    comp[i].setEnabled(enable);
                }
            }
        });
        proxyPanel.setBorder(componentBorder);
        JFrame frame = new JFrame("ComponentTitledBorder - santhosh@in.fiorano.com");
        Container contents = frame.getContentPane();
        contents.setLayout(new FlowLayout());
        contents.add(proxyPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    } 
   }
