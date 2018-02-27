/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author fernando
 */
public class ButtonColumnContainer implements TableCellRenderer {
    Map<RowColumn, ButtonColumn> mapIntToColumn = new HashMap<>();

    public ButtonColumn createButtonColumn(JTable table, int column, int row) {

        RowColumn rc = new RowColumn(column, row);
        ButtonColumn buttonColumn = new ButtonColumn(column, row);
        mapIntToColumn.put(rc, buttonColumn);

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(column).setCellRenderer(this);
        // columnModel.getColumn(column).setCellEditor(this);

        return buttonColumn;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean hasFocus,
            int row, int column) {
        ButtonColumn buttonColumn = getButtonColumn(column, row);

        if (buttonColumn != null) {
            JButton renderButton = buttonColumn.getRenderButton();
            if (hasFocus) {
                renderButton.setForeground(table.getForeground());
                renderButton.setBackground(UIManager.getColor("Button.background"));
            } else if (selected) {
                renderButton.setForeground(table.getSelectionForeground());
                renderButton.setBackground(table.getSelectionBackground());
            } else {
                renderButton.setForeground(table.getForeground());
                renderButton.setBackground(UIManager.getColor("Button.background"));
            }

            renderButton.setText((value == null) ? "" : value.toString());
            return renderButton;
        } else {
            return null;
        }
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean selected, int row,
            int column) {
        ButtonColumn buttonColumn = getButtonColumn(column, row);

        if (buttonColumn != null) {
            JButton editButton = buttonColumn.getEditButton();
            String text = (value == null) ? "" : value.toString();
            editButton.setText(text);
            return editButton;
        } else {
            return null;
        }

    }

    public ButtonColumn getButtonColumn(int column, int row) {
        RowColumn rowColumn = new RowColumn(column, row);
        return mapIntToColumn.get(rowColumn);
    }

}
