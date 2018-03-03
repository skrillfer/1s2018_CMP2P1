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
import java.util.Hashtable;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 *
 * @author fernando
 */
public class VentanaPrincipal extends JFrame implements ActionListener,ComponentListener{
    //***********CONTROL TABS***************//
    JPanel panelTabControl = new JPanel();
    static public JTabbedPane controlTab1 = new JTabbedPane();
    
   
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
        
        //JPanel container = new JPanel();
        //container.add(panel1);
        //container.add(Panel2);
        
        
        getContentPane().add(controlTab1);
        //controlTab1.setTabPlacement(JTabbedPane.BOTTOM);
    }
    
    
    public void agregarNuevaPagina(String tabname){
        Template pagina = new Template();
        //pagina.populateContentPane(this.getContentPane());
        
        //pagina.setBounds(10, 10, 800, 300);
        
        //JScrollPane jsp = new JScrollPane(pagina);
        //frame.add(jsp);
        
        controlTab1.add(tabname, pagina);
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
                
                
                //JPanel bar=(JPanel)t.getComponent(0);
                //bar.setPreferredSize(new Dimension(width, bar.getHeight()));
                
                
                JScrollPane scroll = (JScrollPane)t.getComponent(1);
                System.out.println(scroll.getViewport().getComponents().length);
                
                JPanel pp=(JPanel)scroll.getViewport().getComponents()[0];
                
                //scroll.getViewport().removeAll();
                Double ttt=width*0.75;
                pp.setPreferredSize(new Dimension(ttt.intValue(), pp.getPreferredSize().height));
                scroll.setPreferredSize(new Dimension(width, height));
                //scroll.getViewport().add(pp);
                scroll.updateUI();
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
