/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author fernando
 */
public class ConnTableModel extends AbstractTableModel{
    Object[][] data;

    public ConnTableModel(Object[][] data){
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return data[0].length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) { 
        if(columnIndex == 2 || columnIndex == 3 || columnIndex == 4) {
            return true;
        } else {
            return false;
        }
    }   
}
