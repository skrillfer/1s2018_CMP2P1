/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz.OpcionesWeb;

import Interfaz.Template;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author fernando
 */
public class VentanaOpciones extends JFrame {

    //***********CONTROL TABS***************//
    JPanel panelTabControl = new JPanel();
    JTabbedPane controlTab1 = new JTabbedPane();

    public VentanaOpciones() {
        super(" Opciones");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(1000, 300);
        setLocationRelativeTo(null);
        setResizable(true);

        creandoControlTab();
        setVisible(true);
    }

    public void creandoControlTab() {
        controlTab1.setPreferredSize(this.getPreferredSize().getSize());
        controlTab1.setBackground(Color.gray);
        getContentPane().add(controlTab1);
        controlTab1.setTabPlacement(JTabbedPane.LEFT);

        // 1.area del codigo chtml
        controlTab1.add(crearTextArea(1), "Ver Codigo CHTML");
        // 2.area del codigo cjs
        controlTab1.add(crearTextArea(2), "Ver Codigo CJS");
        // 3.area del codigo css
        controlTab1.add(crearTextArea(3), "Ver Codigo CCSS");
        // 4.area de la consola salida
        controlTab1.add(crearTextArea(4), "Consola de salida");
        // 5.area de la consola de errores

        controlTab1.setSelectedIndex(0);

        //pagina.populateContentPane(this.getContentPane());
        //controlTab1.add(pagina, tabname);
    }

    public JScrollPane crearTextArea(int num) {

        JTextArea cod_area = new JTextArea();
        JScrollPane scroll = new JScrollPane(cod_area,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        cod_area.setEditable(false);

        DefaultCaret caret = (DefaultCaret) cod_area.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);

        try {
            File file = new File("/home/fernando/A_Entradas/ventas.chtml");
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            String str = new String(data, "UTF-8");
            cod_area.setText(str);
        } catch (Exception e) {
        }

        if (num == 1) {
            cod_area.setFont(new Font("Arial", Font.BOLD, 12));
            //cod_area.setBackground(Color.BLACK);
            //cod_area.setForeground(Color.LIGHT_GRAY);
            cod_area.setCaretColor(Color.darkGray);

        } else if (num == 2) {
            cod_area.setFont(new Font("Arial", Font.PLAIN, 12));
            cod_area.setForeground(Color.BLUE);
        } else if (num == 3) {
            cod_area.setFont(new Font("Proggy", Font.PLAIN, 12));
        } else if (num == 4) {
            //JScrollPane scroll = new JScrollPane (txtarea);
            //panel2.add(scroll); //Object of Jpanel
            cod_area.setFont(new Font("Monospaced", Font.PLAIN, 15));
            cod_area.setBackground(Color.BLACK);
            cod_area.setForeground(Color.LIGHT_GRAY);
            cod_area.setCaretColor(Color.WHITE);
        }
        
        return scroll;
    }

    public static void main(String[] args) {
        new VentanaOpciones();
    }
}
