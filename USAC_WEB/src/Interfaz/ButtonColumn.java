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
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;




public class ButtonColumn extends AbstractCellEditor implements ActionListener {

    final JButton editButton;
    final JButton renderButton;
    String text;
    int showRow;

    public ButtonColumn(int column, int showRow) {
        super();
        this.showRow = showRow;
        renderButton = new JButton();

        editButton = new JButton();
        editButton.setFocusPainted(false);
        editButton.addActionListener(this);

    }

    @Override
    public Object getCellEditorValue() {
        return text;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fireEditingStopped();
        System.out.println(e.getSource().toString());
        if (text.equals("2013")) {
            System.out.println("conn");
        } else if (text.equals("disconnect")) {
            System.out.println("disc");
        }
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getRenderButton() {
        return renderButton;
    }



}