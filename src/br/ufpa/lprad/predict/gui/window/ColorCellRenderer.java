/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.gui.window;

/**
 *
 * @author Gabriel
 */
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Guilherme Keller
 */
public class ColorCellRenderer extends DefaultTableCellRenderer {

    public static final int R = 206;
    public static final int G = 255;
    public static final int B = 221;
    private static final long serialVersionUID = 433648737270300478L;
    private static final Log log = LogFactory.getLog(ColorCellRenderer.class);
    private Color color;
    private int row = -1;
    private int[] rows;

    public ColorCellRenderer() {
        super();
        log.info("ColorCellRenderer::constructor");
    }

    public ColorCellRenderer(Color color) {
        super();
        this.color = color;
    }

    public ColorCellRenderer(Color color, int row) {
        super();
        this.color = color;
        this.row = row;
    }

    public ColorCellRenderer(Color color, int[] row) {
        super();
        this.color = color;
        this.rows = row;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {

        if (row % 2 != 0) {
            this.setBackground(new Color(240,250,255));//255,217,222));//186,205,235));//new Color(R, G, B));
//            this.setBackground(new Color(255,217,222));//186,205,235));//new Color(R, G, B));
//            this.setBackground(new Color(186,205,235));//new Color(R, G, B));
//            this.setBackground(new Color(R, G, B));
        } else {
            this.setBackground(Color.WHITE);
        }

        if (this.color != null && this.rows != null) {
            for (int i = 0; i < this.rows.length; i++) {
                int rowToPaint = this.rows[i];
                if (rowToPaint == row) {
                    this.setBackground(this.color);
                }
            }
        } else if (this.color != null) {
            this.setBackground(this.color);
        }

        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

    }

    public void validate() {
    }

    public void revalidate() {
    }
}
