/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Estructuras.NodoDOM;
import Estructuras.Propiedad;
import Interfaz.OpcionesWeb.VentanaOpciones;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import usac_web.USAC_WEB;

/**
 *
 * @author fernando
 */
public class Template extends JPanel implements ActionListener{
    //______________________________ PILA DE PAGINAS____________________________
    ArrayList<JPanel> listaPaginas = new ArrayList<>();
    //__________________________________________________________________________
    
    //************ MENSAJE PAGINA NO ENCONTRADA ********************************
    JLabel error404 = new JLabel("Error Pagina no Encontrada");
    //************ PANEL PADRE**************************************************
    JPanel panel_P = new JPanel();

    //************ Atributos de la Barra Principal *****************************
    JPanel barraPrincipal = new JPanel();
    JButton atras = new JButton("");
    JButton adelante = new JButton("");
    JButton reload = new JButton("");

    JButton historial = new JButton("");
    JButton opciones = new JButton("Opciones");
    JButton plus = new JButton("Fav");//add a favoritos
    JButton favoritos = new JButton("");//Favoritos
    JTextField campoURL = new JTextField(40);
    //--------------------------------------------------------------------------
    //************ Atributos de la Barra Favoritos *****************************

    //**************************************************************************
    public Template() {
        crearBarraPrincipal();

        campoURL.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    
                    if(!campoURL.getText().isEmpty()){
                        try {
                            buscarPagina(campoURL.getText());
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(Template.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    //System.out.println();
                }
            }
        });
    }

    public void crearBarraPrincipal() {
        try {
            campoURL.setToolTipText("example: C:/carpeta1/pagina.chtml");
            reload.setOpaque(false);
            reload.setContentAreaFilled(false);
            reload.setBorderPainted(false);
            reload.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Recursos/reload.png"))));

            adelante.setOpaque(false);
            adelante.setContentAreaFilled(false);
            adelante.setBorderPainted(false);
            adelante.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Recursos/adelante.png"))));

            atras.setOpaque(false);
            atras.setContentAreaFilled(false);
            atras.setBorderPainted(false);
            atras.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Recursos/atras.png"))));

            plus.setOpaque(false);
            plus.setContentAreaFilled(false);
            plus.setBorderPainted(false);
            plus.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Recursos/plus.png"))));

            historial.setOpaque(false);
            historial.setContentAreaFilled(false);
            historial.setBorderPainted(false);
            historial.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Recursos/historial.png"))));

            favoritos.setOpaque(false);
            favoritos.setContentAreaFilled(false);
            favoritos.setBorderPainted(false);
            favoritos.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Recursos/favoritos.png"))));

        } catch (Exception ex) {
            System.out.println(ex);
        }
        /*Forma 1*/
        //campoURL.setPreferredSize(new Dimension(500, 25));
        barraPrincipal.add(atras);
        barraPrincipal.add(adelante);
        barraPrincipal.add(reload);
        barraPrincipal.add(campoURL);
        barraPrincipal.add(historial);
        opciones.addActionListener(this);
        barraPrincipal.add(opciones);
        barraPrincipal.add(plus);
        barraPrincipal.add(favoritos);
        //add(barraPrincipal);
        /*Forma 2*/
 /*
        add(atras);
        add(adelante);
        add(reload);
        add(campoURL);
        add(historial);
        add(opciones);
        add(plus);
        add(favoritos);
         */
        panel_P.setPreferredSize(new Dimension(1000, 600));
        //panel_P.setPreferredSize(this.getPreferredSize().getSize());

        panel_P.setBackground(Color.red);
        add(barraPrincipal);
        add(panel_P);
        //panel_P.setMaximumSize(new Dimension(this.getParent().getWidth(), 0));
    }

    // este metodo busca la pagina ingresada en el campo de texto
    public void buscarPagina(String path) throws FileNotFoundException {
        panel_P.removeAll();
        
        File f = new File(path);
        //if (f.exists() && !f.isDirectory()) {
            //1. compilar el archivo CHTML
           NodoDOM dom=new USAC_WEB().CompilarCHTML(leerArchivo(path));
           GENERADOR_VISTA(dom);
            System.out.println("");
        /*} else {
            System.out.println("no existe");
            panel_P.removeAll();
            
            setearFont(error404);
            panel_P.add(error404);
            //panel_P.repaint();
            updateUI();
        }*/
    }

    public void setearFont(JLabel label) {
        label.setFont(new Font("Trebuchet MS", Font.BOLD, 50));
        label.setForeground(Color.GRAY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object boton = e.getSource();
        if(boton==opciones){
            new VentanaOpciones();
        }
    }

    public String leerArchivo(String path){
         try {
            File file = new File("/home/fernando/A_Entradas/ventas.chtml");
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            String str = new String(data, "UTF-8");
            return str;
        } catch (Exception e) {
            return "";
        }
    }
    
    public void GENERADOR_VISTA(NodoDOM raiz){
        for (NodoDOM hijo : raiz.hijos) {
            switch(hijo.nombre){
                case "encabezado":
                    break;
                case "titulo":
                    break;
                case "cuerpo":
                    System.out.println("entre a cuerpo");
                    GENERADOR_VISTA(hijo);
                    break;
                case "boton":
                    System.out.println("entre a boton");
                    //hijo.propiedades.add(new Propiedad("$text", "boton1"));
                    
                    JButton btn=new BotonGenerico(hijo.propiedades);
                    panel_P.add(btn);
                    
                    updateUI();
                    
                    System.out.println(btn.getPreferredSize());
                    break;
                case "texto":
                    break;
                    
                    
            }
        }
    }
}
