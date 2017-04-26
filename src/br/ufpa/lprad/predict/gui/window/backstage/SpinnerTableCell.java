/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.gui.window.backstage;

import br.ufpa.lprad.predict.correlacao.discretizacao.Discretizacao;
import br.ufpa.lprad.predict.gui.window.correlacao.FrameDiscretizacao;
import java.awt.AWTException;
import java.awt.Component;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author gabriel
 */
public class SpinnerTableCell extends AbstractCellEditor implements TableCellEditor {

    JSpinner spinner;
    int selectedRow;
    int selectedColumn;
    JTable currentTable;
    Robot robot;

    public SpinnerTableCell() {
        SpinnerModel model = new SpinnerNumberModel(1, 1, 1000000000, 1);
        spinner = new JSpinner(model);

        try {
            robot = new Robot();
        } catch (AWTException ex) {
            ex.printStackTrace();
        }
    }

    public Object getCellEditorValue() {
        return spinner.getValue();
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

        spinner.setValue(value);

        currentTable = table;
        selectedRow = row;
        selectedColumn = column;
        return spinner;
    }
}
