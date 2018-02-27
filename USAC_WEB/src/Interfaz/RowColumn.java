/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

/**
 *
 * @author fernando
 */
public class RowColumn {
    final int row;
    final int column;

    RowColumn(int theColumn, int theRow) {
        this.column = theColumn;
        this.row = theRow;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + column;
        result = prime * result + row;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RowColumn other = (RowColumn) obj;
        if (column != other.column)
            return false;
        if (row != other.row)
            return false;
        return true;
    }


}

