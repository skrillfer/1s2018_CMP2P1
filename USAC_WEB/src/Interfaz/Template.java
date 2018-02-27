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
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.text.DefaultCaret;
import usac_web.USAC_WEB;

/**
 *
 * @author fernando
 */
public class Template extends JPanel implements ActionListener{
    //**************************************************************************
    //**************************************************************************
    static Hashtable<String,String> lista_cjs= new Hashtable<>(); 
    static Hashtable<String,String> lista_ccss= new Hashtable<>(); 
    //**************************************************************************
    static public Colores meta_colores = new Colores();
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
                            try {
                                buscarPagina(campoURL.getText());
                            } catch (URISyntaxException ex) {
                                Logger.getLogger(Template.class.getName()).log(Level.SEVERE, null, ex);
                            }
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
        panel_P.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        panel_P.setPreferredSize(new Dimension(1000, 600));
        //panel_P.setPreferredSize(this.getPreferredSize().getSize());

        //panel_P.setBackground(Color.red);
        add(barraPrincipal);
        add(panel_P);
        //panel_P.setMaximumSize(new Dimension(this.getParent().getWidth(), 0));
    }

    // este metodo busca la pagina ingresada en el campo de texto
    public void buscarPagina(String path) throws FileNotFoundException, URISyntaxException {
        panel_P.removeAll();
        
        File f = new File(path);
        //if (f.exists() && !f.isDirectory()) {
            //1. compilar el archivo CHTML
           NodoDOM dom=new USAC_WEB().CompilarCHTML(leerArchivo(path));
           if(dom!=null){
               GENERADOR_VISTA(dom,panel_P);
           }
           
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
            File file = new File("/home/fernando/NetBeansProjects/1s2018_CMP2P1/USAC_WEB/last.txt");
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
    
    public void GENERADOR_VISTA(NodoDOM raiz,JPanel panel_P) throws URISyntaxException{
         
        for (NodoDOM hijo : raiz.hijos) {
            switch(hijo.nombre){
                case "encabezado":
                    GENERADOR_VISTA(hijo,panel_P);
                    break;
                case "titulo":
                    setTitulo(hijo.propiedades.get("$text").valor.trim());
                    break;
                case "cjs":
                    addCjs(hijo.propiedades.get("ruta").valor.trim());
                    break;
                case "ccss":
                    addCcss(hijo.propiedades.get("ruta").valor.trim());
                    break;
                case "cuerpo":
                    System.out.println("entre a cuerpo");
                    setPropiedadesCuerpo(hijo.propiedades);
                    GENERADOR_VISTA(hijo,panel_P);
                    break;
                case "panel":
                    
                    //pila_panels.push(panel_P);
                    JPanel nuevopanel = new PanelGenerico(hijo.propiedades);
                    panel_P.add(nuevopanel);
                    updateUI();
                    GENERADOR_VISTA(hijo,nuevopanel);
                    //panel_P=pila_panels.pop();
                    break;
                case "texto":
                    JLabel texxto = new TextoGenerico(hijo.propiedades);
                    panel_P.add(texxto);
                    updateUI();
                    break;
                case "boton":                    
                    JButton btn=new BotonGenerico(hijo.propiedades);
                    panel_P.add(btn);                    
                    updateUI();
                    
                    //System.out.println(btn.getPreferredSize());
                    break;
                case "caja_texto":
                    JTextField txt=new CajaTextoGenerica(hijo.propiedades);
                    panel_P.add(txt);                    
                    updateUI();
                    break;
                case "texto_a":
                    //VER BIEN
                    JTextPane txt_a=new AreaTextoGenerica(hijo.propiedades);
                    JScrollPane scroll = new JScrollPane(txt_a,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    scroll.setName("texto_a-"+txt_a.getName());
                    panel_P.add(scroll);  
                    updateUI();
                    break;
                case "spinner":
                    JSpinner sp= new SpinnerGenerico(hijo.propiedades);
                    panel_P.add(sp);
                    updateUI();
                    break;
                case "enlace":
                     JLabel enlac = new EnlaceGenerico(hijo.propiedades);
                     panel_P.add(enlac);
                     updateUI();
                     break;
                case "tabla":
                     JPanel tabla = new TablaGenerica2(hijo.propiedades, hijo);
                     panel_P.add(tabla);
                     updateUI();
                     break;
                case "imagen":
                    JLabel img = new ImagenGenerica(hijo.propiedades);
                    panel_P.add(img);
                    updateUI();
                    break;
                case "caja":
                    JComboBox combo = new CajaOpcionesGenerica(hijo.propiedades, hijo.hijos,meta_colores);
                    panel_P.add(combo);
                    updateUI();
                    break;
            }
        }
    }
    
    public void setPropiedadesCuerpo(Hashtable<String,Propiedad> propiedades){
        try {
            String fondo = propiedades.get("fondo").valor.trim();
            Color color= meta_colores.obtenerColor(fondo);
            if(color!=null){
                panel_P.setBackground(color);
            }
            updateUI();
        } catch (Exception e) {}
        
        try {
            panel_P.setPreferredSize(new Dimension(getPreferredSize().width, Integer.valueOf(propiedades.get("alto").valor)));
        } catch (Exception e) {System.out.println(e.getMessage());}
        //******************************************************************Seteando ANCHO
        try {
            panel_P.setPreferredSize(new Dimension(Integer.valueOf(propiedades.get("ancho").valor), getPreferredSize().height));
        } catch (Exception e) {System.out.println(e.getMessage());}
        updateUI();

    }
    
    public void setTitulo(String nombre){
        try {
            if(VentanaPrincipal.controlTab1.getComponentCount()>0){
               VentanaPrincipal.controlTab1.setTitleAt(VentanaPrincipal.controlTab1.getSelectedIndex(),nombre);
            }
        } catch (Exception e) {}
    }
    
    
    public void addCjs(String ruta){
        try {
            if(!lista_cjs.containsKey(ruta)){
                lista_cjs.put(ruta, ruta);
                System.out.println("se agrego cjss:"+ruta);
            }else{
                System.out.println("ya existe cjs:"+ruta);
            }
        } catch (Exception e) {}
    }
    
    public void addCcss(String ruta){
        try {
            if(!lista_ccss.containsKey(ruta)){
                lista_ccss.put(ruta, ruta);
                System.out.println("se agrego ccss:"+ruta);
            }else{
                System.out.println("ya existe ccss:"+ruta);
            }
        } catch (Exception e) {}
    }
}
