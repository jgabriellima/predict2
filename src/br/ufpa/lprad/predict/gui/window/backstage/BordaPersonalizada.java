package br.ufpa.lprad.predict.gui.window.backstage;


import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.UIManager;
import javax.swing.border.Border;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gabriel
 */
public class BordaPersonalizada implements Border {

      private String title;

      private int titleHeight;

      private Insets insets = new Insets(titleHeight, 0, 0, 0);

      private Color primaryColor;

      private Color secondaryColor;

      private Color fontColor;

      private Color shadowColor;

      private int indent = 5;

      private Font titleFont;

      /**
       * Constructtor that assumes a title height.
       *
       * @param title - string to display
       * @param primaryColor - first color of gradient
       * @param secondaryColor - second color of gradient (lower)
       * @param fontColor - color for the font
       */
      public BordaPersonalizada(String title, Color primaryColor,
            Color secondaryColor, Color fontColor) {
         this(title, primaryColor, secondaryColor, fontColor, 30);
      }

      /**
       * Full option constructor
       *
       * @param title - string to display
       * @param primaryColor - first color of gradient
       * @param secondaryColor - second color of gradient (lower)
       * @param fontColor - color for the font
       * @param titleHeight - height of the title bar
       */
      public BordaPersonalizada(String title, Color primaryColor,
            Color secondaryColor, Color fontColor, int titleHeight) {
         this.title = title;
         this.titleHeight = titleHeight;
         this.insets = new Insets(titleHeight, 2, 2, 2);
         this.primaryColor = primaryColor;
         this.shadowColor = primaryColor.darker();
         this.secondaryColor = secondaryColor;
         this.fontColor = fontColor;
         this.titleFont = UIManager.getFont("TitledBorder.font").deriveFont(Font.BOLD);
      }

      /**
       * Creates a GradientTitleBorder with default values.
       * @param title
       */
      public BordaPersonalizada(String title) {
         this(title, Color.BLACK, Color.GRAY, Color.WHITE, 30);
      }

      /**
       *
       */
      public Insets getBorderInsets(Component c) {
         return insets;
      }

      /**
       *
       */
      public boolean isBorderOpaque() {
         return false;
      }

      /**
       * paint the border, being carefull to stay inside the insets.
       */
      public void paintBorder(Component c, Graphics g, int x, int y,
            int width, int height) {

         Graphics2D g2d = (Graphics2D) g;
         g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
               RenderingHints.VALUE_ANTIALIAS_ON);

         GradientPaint gp = new GradientPaint(x, y, primaryColor, x, y
               + titleHeight, secondaryColor);
         g2d.setPaint(gp);
         g2d.fillRect(x, y, width, titleHeight);

         g2d.setColor(shadowColor);
         g2d.drawRect(x, y - 1, width - 1, titleHeight);

         g2d.setFont(titleFont);

         g2d.setColor(shadowColor);
         int titleOffset = (titleHeight / 2) + (c.getFont().getSize() / 2)
               - 1;

         g2d.drawString(title, x + insets.left + indent + 1, y + titleOffset
               + 1);

         g2d.setColor(fontColor);
         g2d.drawString(title, x + insets.left + indent, y + titleOffset);

         g2d.setColor(shadowColor);
         g2d.drawRect(x, y - 1, width - 1, height);
      }
   }


