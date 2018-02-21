/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author fernando
 */
public class VentanaPrincipal extends JFrame implements ActionListener,ComponentListener{
    //***********CONTROL TABS***************//
    JPanel panelTabControl = new JPanel();
    JTabbedPane controlTab1 = new JTabbedPane();
    
   
    Template temp1 = new Template();
    public VentanaPrincipal() {
        super("WEB - USAC [201213562]");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setResizable(true);
        
        //label = new JLabel();
        //getContentPane().add(label);
        
        creandoControlTab();
        agregarNuevaPagina("nuevo");
        //agregarNuevaPagina("nuevo2");
        //getContentPane().add(temp1);   
        setVisible(true);
        getContentPane().addComponentListener((ComponentListener) this);
        /*
        //add(temp1);
        setResizable(true);
        getContentPane().add(temp1);   
        setVisible(true);   
        //pack();   
        */
        
        
        
        
        
    }
    
    public void creandoControlTab(){
        controlTab1.setPreferredSize(this.getPreferredSize().getSize());
        controlTab1.setBackground(Color.gray);
        getContentPane().add(controlTab1);
        //controlTab1.setTabPlacement(JTabbedPane.BOTTOM);
    }
    
    
    public void agregarNuevaPagina(String tabname){
        Template pagina = new Template();
        //pagina.populateContentPane(this.getContentPane());
        
        //pagina.setBounds(10, 10, 800, 300);
        controlTab1.add(pagina, tabname);
        controlTab1.setSelectedComponent(pagina);
    }
    
    public static void main(String[] args) {
        new VentanaPrincipal();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

    @Override
    public void componentResized(ComponentEvent e) {
        int height = this.getHeight();
        int width = this.getWidth();
        if (controlTab1.getComponentCount() > 0) {
                Template t=(Template)controlTab1.getComponent(controlTab1.getSelectedIndex());
                
                
                JPanel bar=(JPanel)t.getComponent(0);
                bar.setPreferredSize(new Dimension(width, bar.getHeight()));
                
                
                JPanel pp=(JPanel)t.getComponent(1);
                pp.setBackground(Color.yellow);
                pp.setPreferredSize(new Dimension(width, height-50));
                
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {
        
    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

  
}
