/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import CJS_Compilador.CJS;
import CJS_Compilador.ResultadoG;
import Estructuras.NodoCSS;
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
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
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
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;
import usac_web.USAC_WEB;
import CSS_Compilador.*;
import Errores.Erro_r;
import Errores.ReporteError;
import Estructuras.Historia;
import Estructuras.Lista_Cargadas;
import Estructuras.Nodo;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Map;
import java.util.StringTokenizer;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.PanelUI;

/**
 *
 * @author fernando
 */
public class Template extends JPanel implements ActionListener{
    //***************** lista de CJS clases*************************************/
    int yahay=0;
    public  CJS principal_cjs = new CJS();
    //******************* consola de salida ***/////////////////
    public  String CONSOLA="";
    
    
    //----------------------------------------------------//
    public static ReporteError reporteError_CJS = new ReporteError(); // este REPORTE es para CJS
    
    //**************************************************************************
    //lista de COMPONENTES REPETIDOS
    public  Hashtable<String,Lista> cmps_repetidos = new Hashtable<>();
    
    //componentes HTML
    public  Hashtable<String,Componente> lista_componentes= new Hashtable<>();
    
    //lista de GRUPOS un grupo tiene una lista de componentes
    public  Hashtable<String,Lista> lista_grupos= new Hashtable<>();
    
    //lista de estilos ya sea por id o por grupo
    public  Hashtable<String,ArrayList<NodoCSS>> lista_estilos_id = new Hashtable<>();
    public  Hashtable<String,ArrayList<NodoCSS>> lista_estilos_grupo = new Hashtable<>();
    
    public static Template instance;
    
     ArrayList<Erro_r> lista_errores = new ArrayList<>();
    //**************************************************************************
    Hashtable<String,String> lista_cjs= new Hashtable<>(); 
     Hashtable<String,String> lista_ccss= new Hashtable<>(); 
    //**************************************************************************
     public Colores meta_colores = new Colores();
    //______________________________ PILA DE PAGINAS____________________________
    ArrayList<JPanel> listaPaginas = new ArrayList<>();
    //__________________________________________________________________________
     Lista_Cargadas lista_cargadas = new Lista_Cargadas();
    //************ MENSAJE PAGINA NO ENCONTRADA ********************************
    JLabel error404 = new JLabel("Error Pagina no Encontrada");
    //************ PANEL PADRE**************************************************
    JPanel panel_P = new JPanel();
    //final JScrollPane scroll = new JScrollPane();
    //************ Atributos de la Barra Principal *****************************
    JPanel barraPrincipal = new JPanel();
    
    JButton agregarTab = new JButton("");
    JButton quitarTab = new JButton("");
    
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
        panel_P.setName("$cuerpo");
        lista_componentes.put("$cuerpo",new Componente("$cuerpo","panel",panel_P,null));
        campoURL.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    cargar_Recargar();
                }
            }
        });
       
    }
    
    
    public static Template getInstance(){
        
        if(instance==null)
            instance = new Template();
        return instance;
    }

    public void crearBarraPrincipal() {
        try {
            campoURL.setToolTipText("example: C:/carpeta1/pagina.chtml");
            reload.setOpaque(false);
            reload.setContentAreaFilled(false);
            reload.setBorderPainted(false);
            reload.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Recursos/reload.png"))));

            
            adelante.setOpaque(false);
            adelante.setEnabled(false);
            adelante.setContentAreaFilled(false);
            adelante.setBorderPainted(false);
            adelante.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Recursos/adelante.png"))));

            atras.setOpaque(false);
            atras.setEnabled(false);
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
            
            agregarTab.setOpaque(false);
            agregarTab.setContentAreaFilled(false);
            agregarTab.setBorderPainted(false);
            agregarTab.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Recursos/add.png"))));

            quitarTab.setOpaque(false);
            quitarTab.setContentAreaFilled(false);
            quitarTab.setBorderPainted(false);
            quitarTab.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Recursos/quit.png"))));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        /*Forma 1*/
        //barraPrincipal.setLayout(new GridLayout(1, 8));
        atras.addActionListener(this);
        barraPrincipal.add(atras);
        
        adelante.addActionListener(this);
        barraPrincipal.add(adelante);
        
        reload.addActionListener(this);
        barraPrincipal.add(reload);
        
        barraPrincipal.add(campoURL);
        
        agregarTab.addActionListener(this);
        barraPrincipal.add(agregarTab);
        
        quitarTab.addActionListener(this);
        barraPrincipal.add(quitarTab);
        
        historial.addActionListener(this);
        barraPrincipal.add(historial);
        
        opciones.addActionListener(this);
        barraPrincipal.add(opciones);
        
        plus.addActionListener(this);
        barraPrincipal.add(plus);
        
        favoritos.addActionListener(this);
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
        //scroll.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        panel_P.setPreferredSize(new Dimension(1400, 1700));
        //panel_P.setLayout(new BoxLayout(panel_P, BoxLayout.Y_AXIS));
        //panel_P.setPreferredSize(this.getPreferredSize().getSize());
        //panel_P.setBackground(Color.red);
        add(barraPrincipal);
        
        JScrollPane jsp = new JScrollPane(panel_P);
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        jsp.setPreferredSize(new Dimension(1350, 550));
        
        add(jsp);
        
    }

    // este metodo busca la pagina ingresada en el campo de texto
    public void buscarPagina(String path) throws FileNotFoundException, URISyntaxException {
        campoURL.setText(path);
        panel_P.removeAll();
        panel_P.setBackground(new Color(238, 238, 238));
        updateUI();
        File f = new File(path);
        if (f.exists() && !f.isDirectory()) {
            //1. compilar el archivo CHTML
           NodoDOM dom=new USAC_WEB().CompilarCHTML(leerArchivo(path));
           if(dom!=null){
               try {
                    VentanaPrincipal.lista_historial.add(new Historia(path));
                    boolean si=lista_cargadas.add(path);
                    
                    CONSOLA="";
                    principal_cjs = new CJS();
                    cmps_repetidos= new Hashtable<>();
                    lista_componentes=new Hashtable<>();
                    lista_grupos= new Hashtable<>();
                    lista_estilos_id = new Hashtable<>();
                    lista_estilos_grupo = new Hashtable<>();
                    lista_errores =  new ArrayList<>();
                    lista_cjs = new Hashtable<>();
                    lista_ccss =  new Hashtable<>();
                    panel_P.removeAll();

                    
                   
                    if(lista_cargadas.size()>1 && lista_cargadas.getPt()==-1){
                        atras.setEnabled(true);
                    }
                    
                    
                    GENERADOR_VISTA(dom,panel_P);
                    //imprimirComponentes(panel_P);
               } catch (Exception e) {
                   reporteError_CJS.agregar("Error Ejecucion", dom.linea, dom.columna,"Generando Vista-> "+ e.getMessage(), path);
               }
               
               try {
                   ejecutarArchivosCcss();
               } catch (Exception e) {
                   reporteError_CJS.agregar("Error Ejecucion", dom.linea, dom.columna,"Ejecutando CCSS ->"+ e.getMessage(), path);
               }
               
               try {
                   ejecutarArchivosCjs();
               } catch (Exception e) {
                   reporteError_CJS.agregar("Error Ejecucion", dom.linea, dom.columna,"Ejecutando CJS ->"+ e.getMessage(), path);
               }
               imprimirComponentes(panel_P);
           }
           
        } else {
            System.out.println("no existe");
            CONSOLA="";
            principal_cjs = new CJS();
            cmps_repetidos= new Hashtable<>();
            lista_componentes=new Hashtable<>();
            lista_grupos= new Hashtable<>();
            lista_estilos_id = new Hashtable<>();
            lista_estilos_grupo = new Hashtable<>();
            lista_errores =  new ArrayList<>();
            lista_cjs = new Hashtable<>();
            lista_ccss =  new Hashtable<>();
            panel_P.removeAll();

            
            setearFont(error404);
            panel_P.add(error404);
            //panel_P.repaint();
            updateUI();
        }
    }
    
    

    public void setearFont(JLabel label) {
        label.setFont(new Font("Trebuchet MS", Font.BOLD, 50));
        label.setForeground(Color.GRAY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object boton = e.getSource();
        if(boton==agregarTab){
            try {
                VentanaPrincipal.agregarNuevaPagina("nuevo_"+VentanaPrincipal.controlTab1.getComponentCount());
            } catch (Exception ex) {}
            
        }else if(boton==quitarTab){
            try {
                VentanaPrincipal.quitarPaginaActual();
            } catch (Exception ex) {}
         
        }
        if(boton==opciones){
            String data=lista_cargadas.getPagina(lista_cargadas.getIndex());
            System.out.println("===>"+data);
            new VentanaOpciones(data,obtenerListaArchivosCJS(),obtenerListaArchivosCCSS(),CONSOLA,reporteError_CJS.generarHtml("Errores ", "CJS y CSS"));
        }else if(boton==reload){
            cargar_Recargar();
        }else if(boton==historial){
            presentarHistorial();
        }else if(boton==adelante){
            if(lista_cargadas.size()>1){
                
                String link="";
                int index=-1;
                
                index=lista_cargadas.getPt()+1;
                
                link=lista_cargadas.getPagina(index);
                if(!link.equals("")){
                    System.out.println("adel==>"+index);
                    lista_cargadas.setPt(index);
                    campoURL.setText(link);
                    updateUI();
                    cargar_Recargar();
                    atras.setEnabled(true);
                    if(index==(lista_cargadas.size()-1 )){
                        adelante.setEnabled(false);
                        updateUI();
                    }
                }
                
            }
        }else if(boton==atras){
            if(lista_cargadas.size()>1){
                int pt = lista_cargadas.getPt();
                String link="";
                int index=-1;
                if(pt==-1){
                    index=lista_cargadas.getIndex()-1;
                    link=lista_cargadas.getPagina(index);
                    if(!link.equals("")){
                        adelante.setEnabled(true);
                        lista_cargadas.setPt(index);
                        campoURL.setText(link);
                        updateUI();
                        cargar_Recargar();
                        
                        if(index==0 ){
                            atras.setEnabled(false);
                            updateUI();
                        }
                    }
                }else{
                    index=pt-1;
                    link=lista_cargadas.getPagina(index);
                    if(!link.equals("")){
                        adelante.setEnabled(true);
                        lista_cargadas.setPt(index);
                        campoURL.setText(link);
                        cargar_Recargar();
                        if(index==0 ){
                            atras.setEnabled(false);
                        }
                    }
                }
                //String link= lista_cargadas.getPagina(WIDTH);
            }
        }else if(boton==plus){
            int index = lista_cargadas.getIndex();
            String link = lista_cargadas.getPagina(index);
            if(!link.equals("")){
                AlertaGenerica alert= new AlertaGenerica("Se ha agregado Exitosamente a Favoritos:\n" +link);
                VentanaPrincipal.lista_favoritos.add(link);
            }
            
        }else if(boton==favoritos){
            presentarFavoritos();
        }
    }

    public void imprimirComponentes(JPanel panel_P){
        for (Component component : panel_P.getComponents()) {
            String nombre_clase=component.getClass().getSimpleName();
            nombre_clase=nombre_clase.toLowerCase();
            switch(nombre_clase){
                case "panelgenerico":
                    ((PanelGenerico)component).setVisible(true);
                    imprimirComponentes((PanelGenerico)component);
                    ((PanelGenerico)component).lanzarFinalizado();
                    break;
                case "textogenerico":
                    ((TextoGenerico)component).setVisible(true);
                    ((TextoGenerico)component).lanzarFinalizado();
                    break;
                case "jscrollpane":
                    ((JScrollPane)component).setVisible(true);
                    try {
                        Component areatexto=((TextoGenerico)component).getComponent(0);
                        ((AreaTextoGenerica)areatexto).lanzarEditado();
                    } catch (Exception e) {
                    }
                    
                    
                    break;
                case "tablagenerica2":
                    ((TablaGenerica2)component).setVisible(true);
                    imprimirComponentes((TablaGenerica2)component);
                    ((TablaGenerica2)component).lanzarFinalizado();
                    break;
                case "spinnergenerico":
                    ((SpinnerGenerico)component).setVisible(true);
                    ((SpinnerGenerico)component).lanzarFinalizado();
                    break;
                case "opciongenerica":
                    ((OpcionGenerica)component).setVisible(true);
                    ((OpcionGenerica)component).lanzarFinalizado();
                    break;
                case "imagengenerica":
                    ((ImagenGenerica)component).setVisible(true);
                    ((ImagenGenerica)component).lanzarFinalizado();
                    break;
                case "enlacegenerico":
                    ((EnlaceGenerico)component).setVisible(true);
                    ((EnlaceGenerico)component).lanzarFinalizado();
                    break;    
                case "cajatextogenerica":
                    ((CajaTextoGenerica)component).setVisible(true);
                    ((CajaTextoGenerica)component).lanzarFinalizado();
                    break;     
                case "cajaopcionesgenerica":
                    ((CajaOpcionesGenerica)component).setVisible(true);
                    ((CajaOpcionesGenerica)component).lanzarFinalizado();
                    break;         
                case "botongenerico":
                    ((BotonGenerico)component).setVisible(true);
                    ((BotonGenerico)component).lanzarFinalizado();
                    break;             
                case "areatextogenerica":
                    ((AreaTextoGenerica)component).setVisible(true);
                    ((AreaTextoGenerica)component).lanzarFinalizado();
                    break;                 
            }
        }
    }
    public String leerArchivo(String path){
         try {
            File file = new File(path);
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
                    //JPanel regenera= new JPanel();
                    //regenera.setBackground(Color.green);
                    //regenera.setName("");
                    
                    
                    //this.panel_P.add(regenera);
                    GENERADOR_VISTA(hijo,panel_P);
                    //this.panel_P.add(regenera);
                    System.out.println("entre a cuerpo");
                    //setPropiedadesCuerpo(hijo.propiedades);
                    
                    break;
                case "panel":
                    
                    PanelGenerico nuevopanel = new PanelGenerico(hijo.propiedades,principal_cjs,this);
                    nuevopanel.setVisible(false);
                    nuevopanel.setLayout(new BoxLayout(nuevopanel, BoxLayout.Y_AXIS));
                    try {
                        addComponente(nuevopanel.getName(),"panel",nuevopanel,panel_P,"panel",panel_P.getName());
                    } catch (Exception e) {}
                    panel_P.add(nuevopanel);
                    GENERADOR_VISTA(hijo,nuevopanel);
                    updateUI();
                    
                    //panel_P=pila_panels.pop();
                    break;
                case "texto":
                    TextoGenerico texxto = new TextoGenerico(hijo.propiedades,panel_P.getPreferredSize(),principal_cjs,this);
                    texxto.setVisible(false);
                    try {
                        addComponente(texxto.getName(),"texto",texxto,panel_P,"panel",panel_P.getName());
                    } catch (Exception e) {}
                    panel_P.add(texxto);
                    updateUI();
                    break;
                case "boton":                    
                    BotonGenerico btn=new BotonGenerico(hijo.propiedades,principal_cjs,this);
                    btn.setVisible(false);
                    try {
                        addComponente(btn.getName(),"boton",btn,panel_P,"panel",panel_P.getName());
                    } catch (Exception e) {}
                    panel_P.add(btn);                    
                    updateUI();
                    break;
                case "caja_texto":
                    CajaTextoGenerica txt=new CajaTextoGenerica(hijo.propiedades,principal_cjs,this);
                    txt.setVisible(false);
                    try {
                        addComponente(txt.getName(),"cajatexto",txt,panel_P,"panel",panel_P.getName());
                    } catch (Exception e) {}
                    panel_P.add(txt); 
                    updateUI();
                    break;
                case "texto_a":
                    //VER BIEN
                    AreaTextoGenerica txt_a=new AreaTextoGenerica(hijo.propiedades,panel_P.getPreferredSize(),principal_cjs,this);
                    
                    JScrollPane scroll = new JScrollPane(txt_a,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    scroll.setName("texto_a-"+txt_a.getName());
                    scroll.setVisible(false);
                    try {
                        addComponente(txt_a.getName(),"areatexto",txt_a,panel_P,"panel",panel_P.getName());
                    } catch (Exception e) {}
                    panel_P.add(scroll);  
                    updateUI();
                    break;
                case "spinner":
                    SpinnerGenerico sp= new SpinnerGenerico(hijo.propiedades,principal_cjs,this);
                    sp.setVisible(false);
                    try {
                        addComponente(sp.getName(),"spinner",sp,panel_P,"panel",panel_P.getName());
                    } catch (Exception e) {}
                    panel_P.add(sp);
                    updateUI();
                    break;
                case "enlace":
                     EnlaceGenerico enlac = new EnlaceGenerico(hijo.propiedades,principal_cjs,this);
                     enlac.setVisible(false);
                     try {
                        addComponente(enlac.getName(),"enlace",enlac,panel_P,"panel",panel_P.getName());
                     } catch (Exception e) {}
                     
                     panel_P.add(enlac);
                     updateUI();
                     break;
                case "tabla":
                    
                     TablaGenerica2 tabla = new TablaGenerica2(hijo.propiedades, hijo,principal_cjs,this);
                     tabla.setVisible(false);
                     try {
                         addComponente(tabla.getName(),"tabla",tabla,panel_P,"panel",panel_P.getName());
                     } catch (Exception e) {}
                     
                     panel_P.add(tabla);
                     updateUI();
                     break;
                case "imagen":
                    ImagenGenerica img = new ImagenGenerica(hijo.propiedades,principal_cjs,this);
                    img.setVisible(false);
                    try {
                        addComponente(img.getName(),"imagen",img,panel_P,"panel",panel_P.getName());
                    } catch (Exception e) {}
                    
                    panel_P.add(img);
                    updateUI();
                    break;
                case "caja":
                    CajaOpcionesGenerica combo = new CajaOpcionesGenerica(hijo.propiedades, hijo.hijos,meta_colores,principal_cjs,this);
                    combo.setVisible(false);
                    try {
                        addComponente(combo.getName(),"cajaopciones",combo,panel_P,"panel",panel_P.getName());
                    } catch (Exception e) {}
                    
                    combo.agregarOpciones();
                    panel_P.add(combo);
                    updateUI();
                    break;
            }
        }
    }
    
    public   void addComponente(String id, String tipo,Object objeto, Object padre, String tipopadre, String idpadre){
        System.out.println("CCCC   >   " + id);
        
            //se obtiene el componente padre
        Componente cpadre = null;
        //se vefirica que exista el padre
        if (lista_componentes.containsKey(idpadre)) {
            cpadre = lista_componentes.get(idpadre);
        }
        
        //si el padre no es nulo
        if (cpadre != null) {
            Componente chijo = new Componente(id, tipo, objeto, cpadre);
            //se crea el componente hijo
            if (!lista_componentes.containsKey(id.trim())) {
                
                //se agrega el nuevo componente a la lista de componentes
                if(!id.trim().equals("")){
                    lista_componentes.put(id, chijo);
                    System.out.println("se agrego a lstComponentes a: [" + id+"] y padre ["+cpadre.id+"]");
                }
                

                // ahora se obtiene el grupo del chijo
                String grupo = getProp_Componente(tipo, objeto, "grupo");
                // si no existe el grupo entonces se agrega
                if (!lista_grupos.containsKey(grupo) && !grupo.equals("")) {
                    Lista lt = new Lista();
                    lista_grupos.put(grupo, lt);
                    System.out.println("AGREGE GRUPO  " + grupo);
                }

                if (lista_grupos.containsKey(grupo)) {
                    System.out.println("se agrego a GRUPO: [" + grupo + "] a [" + id+"]");
                    lista_grupos.get(grupo).getLista().add(chijo);
                    
                }
                cmps_repetidos.put(id, new Lista());
            }else{
                try {
                     // ahora se obtiene el grupo del chijo
                String grupo = getProp_Componente(tipo, objeto, "grupo");
                // si no existe el grupo entonces se agrega
                if (!lista_grupos.containsKey(grupo) && !grupo.equals("")) {
                    Lista lt = new Lista();
                    lista_grupos.put(grupo, lt);
                    System.out.println("AGREGE GRUPO  " + grupo);
                }

                if (lista_grupos.containsKey(grupo)) {
                    System.out.println("se agrego a GRUPO: [" + grupo + "] a [" + id+"]");
                    lista_grupos.get(grupo).getLista().add(chijo);
                    
                }
                
                cmps_repetidos.get(id).getLista().add(chijo);
                } catch (Exception e) {
                    System.out.println("error al agregar REPETIDO:"+e.getMessage());
                }
            }

        }else{
            if(cpadre==null){
                Componente chijo = new Componente(id, tipo, objeto, null);
                if (!lista_componentes.containsKey(id.trim())) {
                    
                    //se agrega el nuevo componente a la lista de componentes
                    if (!id.trim().equals("")) {
                        lista_componentes.put(id, chijo);
                        System.out.println("se agrego a lstComponentes a: [" + id + "] y padre [" + "" + "]");
                    }

                    // ahora se obtiene el grupo del chijo
                    String grupo = getProp_Componente(tipo, objeto, "grupo");
                    // si no existe el grupo entonces se agrega
                    if (!lista_grupos.containsKey(grupo) && !grupo.equals("")) {
                        Lista lt = new Lista();
                        lista_grupos.put(grupo, lt);
                        System.out.println("AGREGE GRUPO  " + grupo);
                    }

                    if (lista_grupos.containsKey(grupo)) {
                        System.out.println("se agrego a GRUPO: [" + grupo + "] a [" + id + "]");
                        lista_grupos.get(grupo).getLista().add(chijo);
                    }
                    cmps_repetidos.put(id, new Lista());
                }else{
                    try {
                        // ahora se obtiene el grupo del chijo
                        String grupo = getProp_Componente(tipo, objeto, "grupo");
                        // si no existe el grupo entonces se agrega
                        if (!lista_grupos.containsKey(grupo) && !grupo.equals("")) {
                            Lista lt = new Lista();
                            lista_grupos.put(grupo, lt);
                            System.out.println("AGREGE GRUPO  " + grupo);
                        }

                        if (lista_grupos.containsKey(grupo)) {
                            System.out.println("se agrego a GRUPO: [" + grupo + "] a [" + id+"]");
                            lista_grupos.get(grupo).getLista().add(chijo);

                        }
                        cmps_repetidos.get(id).getLista().add(chijo);
                    } catch (Exception e) {
                        System.out.println("error al agregar REPETIDO:"+e.getMessage());
                    }
                }
            }
            //System.out.println("el padre de "+id +" es null");
        }
    }
    
    public static String getProp_Componente(String tipo, Object objeto, String prop){
        prop=prop.trim();
                
        String propiedad="";
        BotonGenerico btn;
        EnlaceGenerico enl;
        ImagenGenerica img;
        PanelGenerico pnl;
        TextoGenerico txt;
        SpinnerGenerico spin;
        CajaTextoGenerica ctxt;
        AreaTextoGenerica atxt;
        CajaOpcionesGenerica cop;
        TablaGenerica2 tbl;
        OpcionGenerica opc;
        switch(tipo){
            case "boton":
                try {
                    btn = (BotonGenerico)objeto;
                    propiedad=btn.propiedades.get(prop.toLowerCase()).valor.trim();
                } catch (Exception e) {System.out.println(e.getMessage());}
                break;
            case "enlace":
                try {
                    enl = (EnlaceGenerico)objeto;
                    propiedad=enl.propiedades.get(prop.toLowerCase()).valor.trim();
                } catch (Exception e) {}
                break;
            case "imagen":
                try {
                    img = (ImagenGenerica)objeto;
                    propiedad=img.propiedades.get(prop.toLowerCase()).valor.trim();
                } catch (Exception e) {}
                break;
            case "panel":
                try {
                    pnl = (PanelGenerico)objeto;
                    propiedad=pnl.propiedades.get(prop.toLowerCase()).valor.trim();
                } catch (Exception e) {}
                break;
            case "texto":
                try {
                    txt = (TextoGenerico)objeto;
                    propiedad=txt.propiedades.get(prop.toLowerCase()).valor.trim();
                } catch (Exception e) {}
                break;
            case "spinner":
                try {
                    spin = (SpinnerGenerico)objeto;
                    propiedad=spin.propiedades.get(prop.toLowerCase()).valor.trim();
                } catch (Exception e) {}
                break;
            case "cajatexto":
                try {
                    ctxt = (CajaTextoGenerica)objeto;
                    propiedad=ctxt.propiedades.get(prop.toLowerCase()).valor.trim();
                } catch (Exception e) {}
                break;    
            case "areatexto":
                try {
                    atxt = (AreaTextoGenerica)objeto;
                    propiedad=atxt.propiedades.get(prop.toLowerCase()).valor.trim();
                } catch (Exception e) {}
                break;    
            case "cajaopciones":
                try {
                    cop = (CajaOpcionesGenerica)objeto;
                    propiedad=cop.propiedades.get(prop.toLowerCase()).valor.trim();
                } catch (Exception e) {}
                break;  
            case "opcion":
                try {
                    opc = (OpcionGenerica)objeto;
                    propiedad=opc.propiedades.get(prop.toLowerCase()).valor.trim();
                } catch (Exception e) {
                }
                break;
            case "tabla":
                try {
                    tbl = (TablaGenerica2)objeto;
                    propiedad=tbl.propiedadesTabla.get(prop.toLowerCase()).valor.trim();
                } catch (Exception e) {}
                break;      
        }
        return propiedad;
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
                //System.out.println("se agrego cjss:"+ruta);
            }else{
                //System.out.println("ya existe cjs:"+ruta);
            }
        } catch (Exception e) {}
    }
    
    public void addCcss(String ruta){
        try {
            if(!lista_ccss.containsKey(ruta)){
                lista_ccss.put(ruta, ruta);
                //System.out.println("se agrego ccss:"+ruta);
            }else{
                //System.out.println("ya existe ccss:"+ruta);
            }
        } catch (Exception e) {}
    }
    
    public ArrayList<String> obtenerListaArchivosCCSS(){
        ArrayList<String> lista = new ArrayList<>();
        Enumeration<String> llaves = lista_ccss.keys();
        while (llaves.hasMoreElements()) {
            String llave = llaves.nextElement();
            String vruta = lista_ccss.get(llave).trim();
            lista.add(vruta);
        }
        return lista;
    }
    public ArrayList<String> obtenerListaArchivosCJS(){
        ArrayList<String> lista = new ArrayList<>();
        Enumeration<String> llaves = lista_cjs.keys();
        while (llaves.hasMoreElements()) {
            String llave = llaves.nextElement();
            String vruta = lista_cjs.get(llave).trim();
            lista.add(vruta);
        }
        return lista;
    }
    public void ejecutarArchivosCcss() throws FileNotFoundException{
        Enumeration<String> llaves = lista_ccss.keys();
        while (llaves.hasMoreElements()) {
            String llave = llaves.nextElement();
            String vruta = lista_ccss.get(llave).trim();
            
            //*** para cada una de las rutas guardadas, vento y compilo
            NodoCSS dom=null;
            try {
                dom=new USAC_WEB().compilarCCSS(leerArchivo(vruta));
            } catch (Exception e) {
            }
           
           
           if(dom!=null){
               for (NodoCSS bloque : dom.hijos) {
                   for (NodoCSS nodo : bloque.hijos) {
                       
                       switch (nodo.nombre) {
                           case "grupo":
                               System.out.println("holaaa vot a aplicar grupo CCCSS  ->"+nodo.valor);
                               ArrayList<Componente> listaCOMP ;
                               
                               if(lista_grupos.containsKey(nodo.valor.trim())){
                                   System.out.println("existe :"+ nodo.valor);
                                   listaCOMP = lista_grupos.get(nodo.valor.trim()).getLista();
                                   System.out.println("TAM lista GRUPO"+ listaCOMP.size());
                                   for (Componente componente : listaCOMP) {
                                       System.out.println("si hay GRUPO:"+nodo.valor);
                                       NodoCSS nnn = new NodoCSS("identificador","", nodo.linea,nodo.columna, 1000);
                                       nnn.hijos=nodo.hijos;
                                       
                                       aplicarCcss(nnn,componente);
                                   }
                               }
                               
                               if(!lista_estilos_grupo.containsKey(nodo.valor.trim())){
                                   ArrayList<NodoCSS> lista = new ArrayList<>();
                                   lista.add(nodo);
                                   lista_estilos_grupo.put(nodo.valor.trim(),lista);
                                   System.out.println("HE AGREGADO EL GRUPO:"+nodo.valor);
                               }else{
                                   lista_estilos_grupo.get(nodo.valor.trim()).add(nodo);
                               }
                               
                               break;
                           case "identificador":
                               System.out.println("voy a aplicar");
                               aplicarCcss(nodo,null);
                               if(!lista_estilos_id.containsKey(nodo.valor.trim())){
                                   ArrayList<NodoCSS> lista = new ArrayList<>();
                                   lista.add(nodo);
                                   lista_estilos_id.put(nodo.valor.trim(),lista);
                               }else{
                                   lista_estilos_id.get(nodo.valor.trim()).add(nodo);
                               }
                               break;
                       }
                   }
               }
                
           }
        }
    }
    
   
    
    public void ejecutarArchivosCjs() throws FileNotFoundException{
        ArrayList<String> listacjs = obtenerListaArchivosCJS();
        if(listacjs!=null){
            for (String ruta : listacjs) {
                try {
                   Nodo root=new USAC_WEB().CompilarCJS(leerArchivo(ruta));     
                   principal_cjs.ejecucionCJS(root, "", ruta,this);
                } catch (Exception e) {
                }
                
            }
        }
    }
    public void aplicarCcss(NodoCSS raiz, Componente componente){
        
        OperacionesARL opl = new OperacionesARL(this);
        for (NodoCSS nodo : raiz.hijos) {
            switch(nodo.nombre){
                case "alineado":
                    setAlineado(raiz, nodo, opl,componente);
                    break;
                case "texto":
                    setTexto(raiz, nodo, opl,componente);
                    break;    
                case "formato":
                    if(raiz.nombre.equals("identificador")){
                        Componente cmp = lista_componentes.get(raiz.valor.trim());
                        if (cmp==null){
                            if (componente!=null){
                                cmp=componente;
                            }else{
                                break;
                            }
                        }
                        try {
                            lanzarEditado(cmp);
                        } catch (Exception e) {}    
                        NodoCSS valores= nodo.hijos.get(0);
                        for (NodoCSS valor : valores.hijos) {
                            ResultadoG res = opl.ejecutar(valor);
                            if(res==null)
                                res= new ResultadoG("ninguno", "");
                            
                            switch (res.tipo) {
                                case "capital-t":
                                    switch (cmp.tipo) {
                                        case "boton":
                                            BotonGenerico btn = (BotonGenerico) cmp.objeto;
                                            btn.setMayu_Minu_Capital(3);
                                            btn.setTexto();
                                            updateUI();
                                            break;
                                        case "enlace":
                                            EnlaceGenerico enl = (EnlaceGenerico) cmp.objeto;
                                            enl.setMayu_Minu_Capital(3);
                                            enl.setTexto();
                                            updateUI();
                                            break;   
                                        case "texto":
                                            TextoGenerico txt = (TextoGenerico) cmp.objeto;
                                            txt.setMayu_Minu_Capital(3);
                                            txt.setTexto();
                                            updateUI();
                                            break;       
                                        case "areatexto":
                                            AreaTextoGenerica atxt = (AreaTextoGenerica) cmp.objeto;
                                            atxt.setMayu_Minu_Capital(3);
                                            atxt.setTexto();
                                            updateUI();
                                            break;  
                                        case "cajatexto":    
                                            CajaTextoGenerica ctxt = (CajaTextoGenerica) cmp.objeto;
                                            ctxt.setMayu_Minu_Capital(3);
                                            ctxt.setTexto();
                                            updateUI();
                                            break;
                                        case "opcion":    
                                            OpcionGenerica opc = (OpcionGenerica) cmp.objeto;
                                            opc.setMayu_Minu_Capital(3);
                                            opc.setTexto();
                                            updateUI();
                                            break;
                                        case "cajaopciones":    
                                            CajaOpcionesGenerica c_opc = (CajaOpcionesGenerica) cmp.objeto;
                                            c_opc.setMayu_Minu_Capital(3);
                                            c_opc.setTexto();
                                            updateUI();
                                            break;    
                                            
                                    }
                                    break;
                                case "mayuscula":
                                    switch (cmp.tipo) {
                                        case "boton":
                                            BotonGenerico btn = (BotonGenerico) cmp.objeto;
                                            btn.setMayu_Minu_Capital(2);
                                            btn.setTexto();
                                            break;
                                        case "enlace":
                                            EnlaceGenerico enl = (EnlaceGenerico) cmp.objeto;
                                            enl.setMayu_Minu_Capital(2);
                                            enl.setTexto();
                                            updateUI();
                                        case "texto":
                                            TextoGenerico txt = (TextoGenerico) cmp.objeto;
                                            txt.setMayu_Minu_Capital(2);
                                            txt.setTexto();
                                            updateUI();    
                                            break; 
                                        case "areatexto":
                                            AreaTextoGenerica atxt = (AreaTextoGenerica) cmp.objeto;
                                            atxt.setMayu_Minu_Capital(2);
                                            atxt.setTexto();
                                            updateUI();    
                                            break;     
                                        case "cajatexto":    
                                            CajaTextoGenerica ctxt = (CajaTextoGenerica) cmp.objeto;
                                            ctxt.setMayu_Minu_Capital(2);
                                            ctxt.setTexto();
                                            updateUI();    
                                            break;  
                                        case "opcion":
                                            OpcionGenerica opc = (OpcionGenerica) cmp.objeto;
                                            opc.setMayu_Minu_Capital(2);
                                            opc.setTexto();
                                            updateUI();
                                            break;
                                        case "cajaopciones":
                                            CajaOpcionesGenerica c_opc = (CajaOpcionesGenerica) cmp.objeto;
                                            c_opc.setMayu_Minu_Capital(2);
                                            c_opc.setTexto();
                                            updateUI();
                                            break;   
                                    }
                                    break;
                                case "minuscula":
                                    switch (cmp.tipo) {
                                        case "boton":
                                            BotonGenerico btn = (BotonGenerico) cmp.objeto;
                                            btn.setMayu_Minu_Capital(1);
                                            btn.setTexto();
                                            break;
                                        case "enlace":
                                            EnlaceGenerico enl = (EnlaceGenerico) cmp.objeto;
                                            enl.setMayu_Minu_Capital(1);
                                            enl.setTexto();
                                            break; 
                                        case "texto":
                                            TextoGenerico txt = (TextoGenerico) cmp.objeto;
                                            txt.setMayu_Minu_Capital(1);
                                            txt.setTexto();
                                            break;    
                                        case "areatexto":
                                            AreaTextoGenerica atxt = (AreaTextoGenerica) cmp.objeto;
                                            atxt.setMayu_Minu_Capital(1);
                                            atxt.setTexto();
                                            break; 
                                        case "cajatexto":      
                                            CajaTextoGenerica ctxt = (CajaTextoGenerica) cmp.objeto;
                                            ctxt.setMayu_Minu_Capital(1);
                                            ctxt.setTexto();
                                            break; 
                                        case "opcion":
                                            OpcionGenerica opc = (OpcionGenerica) cmp.objeto;
                                            opc.setMayu_Minu_Capital(1);
                                            opc.setTexto();
                                            updateUI();
                                            break;
                                        case "cajaopciones":
                                            CajaOpcionesGenerica c_opc = (CajaOpcionesGenerica) cmp.objeto;
                                            c_opc.setMayu_Minu_Capital(1);
                                            c_opc.setTexto();
                                            updateUI();
                                            break;         
                                    }
                                    break;
                                case "cursiva":
                                    switch (cmp.tipo) {
                                        case "boton":
                                        case "enlace":
                                        case "texto": 
                                        case "areatexto":
                                        case "cajatexto":
                                        case "opcion":
                                        case "cajaopciones":    
                                            JComponent btn = (JComponent) cmp.objeto;
                                            Font ft =null;
                                            if(btn.getFont().isBold()){
                                                ft=new Font(btn.getFont().getName(),Font.ITALIC+Font.BOLD,btn.getFont().getSize());
                                            }else{
                                                ft=new Font(btn.getFont().getName(),Font.ITALIC,btn.getFont().getSize());
                                            }
                                            try {
                                                 Map atributes = ft.getAttributes();
                                                if (cmp.tipo.equals("enlace")) {
                                                    atributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                                                    
                                                }//btn.setFont(ft);
                                                btn.setFont(ft.deriveFont(atributes));
                                            } catch (Exception e) {}
                                            updateUI();
                                            break;
                                    }
                                    break;
                                case "negrilla":
                                    switch (cmp.tipo) {
                                        case "boton":
                                        case "enlace":  
                                        case "texto":    
                                        case "areatexto":   
                                        case "cajatexto":   
                                        case "opcion":
                                        case "cajaopciones":    
                                            JComponent btn = (JComponent) cmp.objeto;
                                            Font ft =null;
                                            if(btn.getFont().isItalic()){
                                                ft=new Font(btn.getFont().getName(),Font.ITALIC+Font.BOLD,btn.getFont().getSize());
                                            }else{
                                                ft=new Font(btn.getFont().getName(),Font.BOLD,btn.getFont().getSize());
                                            }
                                            try {
                                                Map atributes = ft.getAttributes();
                                                if (cmp.tipo.equals("enlace")) {
                                                    atributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                                                }//btn.setFont(ft);
                                                btn.setFont(ft.deriveFont(atributes));
                                                
                                            } catch (Exception e) {}
                                            updateUI();
                                            break;
                                    }
                                    break;
                                default:
                                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, alineado solo acepta: izquierda,derecha,centrado,justificado", "Lenguaje CCSS");

                            }   
                        }
                    }
                    
                    break;   
                case "letra":
                    setLetra(raiz, nodo, opl,componente);
                    break; 
                case "tamtex":
                    setTamtex(raiz, nodo, opl,componente);
                    break;    
                case "fondoelemento":
                    setFondoElemento(raiz, nodo, opl,componente);
                    break;    
                case "autoredimension":
                    // aqui no se que putas hacer
                    break;    
                case "visible":
                    setVisible(raiz, nodo, opl,componente);
                    break;        
                case "borde":
                    setBorde(raiz, nodo, opl,componente);
                    break;
                case "opaque":
                    setOpaque(raiz, nodo, opl,componente);
                    break;
                case "colortext":
                    setColortext(raiz, nodo, opl,componente);
                    break;
            }
        }
    }
    
    public void setAlineado(NodoCSS raiz, NodoCSS nodo, OperacionesARL opl, Componente porGrupo){
        if (raiz.nombre.equals("identificador")) {
            Componente cmp = lista_componentes.get(raiz.valor.trim());
            
            if (cmp == null) {
                if(porGrupo!=null){
                    cmp=porGrupo;
                }else{
                    cmp = new Componente("null", "null", new Object(), null);
                }
            }
            try {
                            lanzarEditado(cmp);
                        } catch (Exception e) {} 
            ResultadoG res = opl.ejecutar(nodo.hijos.get(0));
            if (res == null) {
                res = new ResultadoG("null", null);
            }
            boolean do_alineado = false;
            switch (res.tipo) {
                case "izquierda":
                case "derecha":
                case "justificado":
                case "centrado":    
                    do_alineado = true;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, alineado solo acepta: izquierda,derecha,centrado,justificado", "Lenguaje CCSS");

            }
            switch (cmp.tipo) {
                case "boton":
                    BotonGenerico btn = (BotonGenerico) cmp.objeto;
                    if (do_alineado) {
                        btn.propiedades.get("alineado").valor = res.tipo;
                        btn.setAlineado();
                        System.out.println("se alineo a " + res.tipo);
                    }
                    break;
                case "enlace":
                    EnlaceGenerico enl = (EnlaceGenerico) cmp.objeto;
                    if (do_alineado) {
                        enl.propiedades.get("alineado").valor = res.tipo;
                        enl.setAlineado();
                    }
                    break;
                case "imagen":
                    ImagenGenerica img = (ImagenGenerica) cmp.objeto;
                    if (do_alineado) {
                        img.propiedades.get("alineado").valor = res.tipo;
                        img.setAlineado();
                    }
                    break;
                case "texto":
                    TextoGenerico txt = (TextoGenerico) cmp.objeto;
                    if (do_alineado) {
                        txt.propiedades.get("alineado").valor = res.tipo;
                        txt.setAlineado();
                    }
                    break;    
                case "areatexto":    
                    AreaTextoGenerica atxt = (AreaTextoGenerica) cmp.objeto;
                    if (do_alineado) {
                        atxt.propiedades.get("alineado").valor = res.tipo;
                        atxt.setAlineado();
                    }
                    break;    
                case "cajatexto":    
                    CajaTextoGenerica ctxt = (CajaTextoGenerica) cmp.objeto;
                    if (do_alineado) {
                        ctxt.propiedades.get("alineado").valor = res.tipo;
                        ctxt.setAlineado();
                    }
                    break;    
                case "panel":      
                    PanelGenerico panel = (PanelGenerico) cmp.objeto;
                    if (do_alineado) {
                        panel.propiedades.get("alineado").valor = res.tipo;
                        panel.setAlineado();
                    }
                    break; 
                case "opcion":      
                    OpcionGenerica opc = (OpcionGenerica) cmp.objeto;
                    if (do_alineado) {
                        opc.propiedades.get("alineado").valor = res.tipo;
                        opc.setAlineado();
                    }
                    break;    
                case "cajaopciones":      
                    CajaOpcionesGenerica c_opc = (CajaOpcionesGenerica) cmp.objeto;
                    if (do_alineado) {
                        c_opc.propiedades.get("alineado").valor = res.tipo;
                        c_opc.setAlineado();
                    }
                    break;       
                case "spinner":      
                    SpinnerGenerico spiin = (SpinnerGenerico) cmp.objeto;
                    if (do_alineado) {
                        spiin.propiedades.get("alineado").valor = res.tipo;
                        spiin.setAlineado();
                    }
                    break;      
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, alineado solo No aplica a "+cmp.tipo, "Lenguaje CCSS");
                    break;
            }
        }
    }
    
    public void setTexto(NodoCSS raiz, NodoCSS nodo, OperacionesARL opl, Componente porGrupo){
        if (raiz.nombre.equals("identificador")) {
            Componente cmp = lista_componentes.get(raiz.valor.trim());
            if (cmp == null){
                if(porGrupo!=null){
                    cmp=porGrupo;
                }else{
                    cmp = new Componente("null", "null", new Object(),null);
                }
            }
            try {
                            lanzarEditado(cmp);
                        } catch (Exception e) {} 

            ResultadoG res = opl.ejecutar(nodo.hijos.get(0));
            if (res == null)
                res = new ResultadoG("ninguno", null);
            
            boolean do_texto = false;
            switch (res.tipo) {
                case "string":
                    do_texto = true;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, texto solo acepta STRING", "Lenguaje CCSS");

            }
            switch (cmp.tipo) {
                case "boton":
                    BotonGenerico btn = (BotonGenerico) cmp.objeto;
                    if (do_texto) {
                        btn.propiedades.get("$text").valor = (String) res.valor;
                        btn.setTexto();
                    }
                    break;
                case "enlace":
                    EnlaceGenerico enl = (EnlaceGenerico) cmp.objeto;
                    if (do_texto) {
                        enl.propiedades.get("$text").valor = (String) res.valor;
                        enl.setTexto();
                    }
                    break;
                case "texto":
                    TextoGenerico txt = (TextoGenerico) cmp.objeto;
                    if (do_texto) {
                        txt.propiedades.get("$text").valor = (String) res.valor;
                        txt.setTexto();
                    }
                    break;
                case "areatexto": 
                    AreaTextoGenerica atxt = (AreaTextoGenerica) cmp.objeto;
                    if (do_texto) {
                        atxt.propiedades.get("$text").valor = (String) res.valor;
                        atxt.setTexto();
                    }
                    break;
                case "cajatexto":     
                    CajaTextoGenerica ctxt = (CajaTextoGenerica) cmp.objeto;
                    if (do_texto) {
                        ctxt.propiedades.get("$text").valor = (String) res.valor;
                        ctxt.setTexto();
                    }
                    break;
                case "opcion":     
                    OpcionGenerica opc = (OpcionGenerica) cmp.objeto;
                    if (do_texto) {
                        opc.propiedades.get("$text").valor = (String) res.valor;
                        opc.setTexto();
                    }
                    break;
                case "cajaopciones":     
                    CajaOpcionesGenerica c_opc = (CajaOpcionesGenerica) cmp.objeto;
                    if (do_texto) {
                        c_opc.propiedades.get("$text").valor = (String) res.valor;
                        c_opc.setTexto();
                    }
                    break;    
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, Texto  No aplica a "+cmp.tipo, "Lenguaje CCSS");
                    break;
            }
        }
    }
    
    
    public void setLetra(NodoCSS raiz, NodoCSS nodo, OperacionesARL opl, Componente porGrupo){
        if (raiz.nombre.equals("identificador")) {
            Componente cmp = lista_componentes.get(raiz.valor.trim());
            if (cmp == null){
                if(porGrupo!=null){
                    cmp=porGrupo;
                }else{
                    cmp = new Componente("null", "null", new Object(), null);
                }
            } 
                
            try {
                            lanzarEditado(cmp);
                        } catch (Exception e) {} 
            ResultadoG res = opl.ejecutar(nodo.hijos.get(0));
            if (res == null)
                res = new ResultadoG("ninguno", null);
            
            boolean do_letra = false;
            switch (res.tipo) {
                case "string":
                    do_letra = true;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, Letra solo acepta: STRING ", "Lenguaje CCSS");
            }
            switch (cmp.tipo) {
                case "boton":
                case "enlace":  
                case "texto":   
                case "areatexto":   
                case "cajatexto":
                case "opcion":
                case "cajaopciones":    
                    JComponent btn = (JComponent) cmp.objeto;
                    if (do_letra) {
                        String valor = (String) res.valor;
                        valor = valor.trim();
                        Font ft = null;
                        try {
                            ft = new Font(valor, btn.getFont().getStyle(), btn.getFont().getSize());
                            Map atributes = ft.getAttributes();
                            if (cmp.tipo.equals("enlace")){
                                atributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                            }//btn.setFont(ft);
                            btn.setFont(ft.deriveFont(atributes));

                            
                            System.out.println("se aplico fuente: [" + valor + "]");
                        } catch (Exception e) {
                            System.out.println("error al aplicar fuente");
                        }
                        updateUI();
                    }
                    break;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, Texto  No aplica a "+cmp.tipo, "Lenguaje CCSS");
                    break;
            }
        }
    }
    
  
    
    
    public  void setTamtex(NodoCSS raiz, NodoCSS nodo, OperacionesARL opl, Componente porGrupo){
        if (raiz.nombre.equals("identificador")) {
            Componente cmp = lista_componentes.get(raiz.valor.trim());
            if (cmp==null){
                if(porGrupo!=null){
                    cmp=porGrupo;
                }else{
                    cmp = new Componente("", "",new Object(), null);
                }
            }
            try {
                            lanzarEditado(cmp);
                        } catch (Exception e) {}     
            
            ResultadoG res = opl.ejecutar(nodo.hijos.get(0));
            if (res==null)
                res = new ResultadoG("ninguno",null);
            boolean do_tamtext = false;
            switch (res.tipo) {
                case "number":
                    do_tamtext = true;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico,Tamtex solo acepta: NUMBER", "Lenguaje CCSS");
            }
            switch (cmp.tipo) {
                case "boton":
                case "enlace": 
                case "texto":
                case "areatexto":
                case "cajatexto":
                case "cajaopciones":    
                    JComponent btn = (JComponent) cmp.objeto;
                    if (do_tamtext) {
                        Double valor = (Double) res.valor;
                        long value = Math.round(valor);
                        Integer tam = (int)(long)value;
                        
                        Font ft = null;
                        try {
                            ft = new Font(btn.getFont().getName(), btn.getFont().getStyle(), tam);
                            Map atributes = ft.getAttributes();
                            if (cmp.tipo.equals("enlace")){
                                atributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                            }//btn.setFont(ft);
                            btn.setFont(ft.deriveFont(atributes));
                            
                            System.out.println("se aplico tam: [" + tam + "]");
                        } catch (Exception e) {
                            System.out.println("error al aplico tamtext");
                        }
                        updateUI();
                        //getInstance().updateUI();
                    }
                    break;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, TamTex  No aplica a "+cmp.tipo, "Lenguaje CCSS");
                    break;
            }
        }
    }
    
    
    public void setFondoElemento(NodoCSS raiz, NodoCSS nodo, OperacionesARL opl,Componente porGrupo){
        if (raiz.nombre.equals("identificador")) {
            Componente cmp = lista_componentes.get(raiz.valor.trim());
            if (cmp==null){
                if(porGrupo!=null){
                    cmp=porGrupo;
                }else{
                    cmp = new Componente("", "",new Object(), null);
                }
            }
            try {
                            lanzarEditado(cmp);
                        } catch (Exception e) {}     
            ResultadoG res = opl.ejecutar(nodo.hijos.get(0));
            if (res==null)
                res = new ResultadoG("ninguno",null);
            
            boolean do_fondo = false;
            switch (res.tipo) {
                case "string":
                    do_fondo = true;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico,fondoElemento solo acepta: STRING", "Lenguaje CCSS");
            }
            switch (cmp.tipo) {
                case "boton":
                case "enlace":
                case "texto": 
                case "areatexto": 
                case "cajatexto":
                case "panel":
                case "opcion":    
                    JComponent btn = (JComponent) cmp.objeto;
                    if (cmp.tipo.equals("enlace"))
                            btn.setOpaque(true);
                    if (do_fondo) {
                        String valor = (String) res.valor;
                        valor = valor.trim();
                        Color color = meta_colores.obtenerColor(valor);
                        try {
                            if(color!=null){
                                System.out.println("se aplico fondocolor: [" + valor + "] a "+ cmp.id);
                                btn.setBackground(color);
                            }else{
                                addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna,(String) nodo.hijos.get(0).valor , "el color es invalido", "LenguajeCCSS");
                            }
                            
                        } catch (Exception e) {
                            System.out.println("error al aplicar fuente");
                        }
                        updateUI();
                    }
                    break;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, FondoElemento  No aplica a "+cmp.tipo, "Lenguaje CCSS");
                    break;
            }
        }
    }
    
    
    public void setVisible(NodoCSS raiz, NodoCSS nodo, OperacionesARL opl, Componente porGrupo){
        if (raiz.nombre.equals("identificador")) {
            Componente cmp = lista_componentes.get(raiz.valor.trim());
            if (cmp==null){
                if(porGrupo!=null){
                    cmp=porGrupo;
                }else{
                    cmp = new Componente("", "",new Object(), null);
                }
            }
            try {
                            lanzarEditado(cmp);
                        } catch (Exception e) {} 
            ResultadoG res = opl.ejecutar(nodo.hijos.get(0));
            if (res==null)
                res = new ResultadoG("ninguno",null);
            
            boolean do_fondo = false;
            switch (res.tipo) {
                case "boolean":
                    do_fondo = true;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, Visible solo acepta: boolean", "Lenguaje CCSS");
            }
            switch (cmp.tipo) {
                case "boton":
                case "enlace":
                case "imagen":    
                case "texto":   
                case "areatexto": 
                case "cajatexto":
                case "cajaopciones": 
                case "spinner":     
                    JComponent btn = (JComponent) cmp.objeto;
                    if (do_fondo) {
                        boolean valor = (boolean) res.valor;
                        
                        try {
                            btn.setVisible(valor);
                            btn.setVisible(valor);
                            System.out.println("se aplico visibilidad: [" + valor + "]");
                        } catch (Exception e) {
                            System.out.println("error al aplicar fuente");
                        }
                    }
                    updateUI();
                    break;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, Visible  No aplica a "+cmp.tipo, "Lenguaje CCSS");
                    break;
            }
        }
    }
    
    public void setBorde(NodoCSS raiz, NodoCSS nodo, OperacionesARL opl,Componente porGrupo){
        if (raiz.nombre.equals("identificador")) {
            Componente cmp = lista_componentes.get(raiz.valor.trim());
            
            if (cmp==null){
                if(porGrupo!=null){
                    cmp=porGrupo;
                }else{
                    cmp = new Componente("", "",new Object(), null);
                }
                //cmp = new Componente("", "",new Object(), null);
            }
                
            try {
                lanzarEditado(cmp);
            } catch (Exception e) {} 
            ResultadoG res1 = opl.ejecutar(nodo.hijos.get(0));
            ResultadoG res2 = opl.ejecutar(nodo.hijos.get(1));
            ResultadoG res3 = opl.ejecutar(nodo.hijos.get(2));
            if (res1==null)
                res1 = new ResultadoG("ninguno",null);
            if (res2==null)
                res2 = new ResultadoG("ninguno",null);
            if (res3==null)
                res3 = new ResultadoG("ninguno",null);
            
            boolean do_borde = false;
            System.out.println(res1.tipo +":"+res2.tipo+":"+res3.tipo);
            if(res1.tipo.equals("number") && res2.tipo.equals("string") && res3.tipo.equals("boolean")){
                do_borde=true;
                System.out.println("si cumple");
            }else{
                addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Eror Semantico, Borde debe ser [number,string,boolean] error en los tipos", "Lenguaje CCSS");
            }
            
            switch (cmp.tipo) {
                case "boton":
                case "enlace":    
                case "texto":
                case "areatexto":    
                case "cajatexto":     
                case "borde":    
                case "cajaopciones":        
                    JComponent btn = (JComponent) cmp.objeto;
                    if (do_borde) {
                        Double r1=(double)res1.valor;
                        String r2=(String)res2.valor;
                        boolean r3 = (boolean)res3.valor;
                        System.out.println("ANTES se aplica borde redondeado");
                        Color color=meta_colores.obtenerColor(r2);
                        try {
                            if(r3){
                                System.out.println("se aplica borde redondeado");
                                if(color!=null){
                                    //AbstractBorder brdr = new TextBubbleBorder(Color.BLACK,r1.intValue(),btn.getPreferredSize().height/10,0);
                                    LineBorder brdr = new LineBorder(color, r1.intValue(),true);
                                    btn.setBorder(brdr);
                                }else{
                                    addError(nodo.hijos.get(1).linea, nodo.hijos.get(1).columna, nodo.hijos.get(1).valor, "el color es invalido, no existe", "Lenguaje CCSS");
                                }
                            }else{
                                if(color!=null){
                                    btn.setBorder(BorderFactory.createMatteBorder(r1.intValue(), r1.intValue(), r1.intValue(), r1.intValue(), color));
                                }else{
                                    addError(nodo.hijos.get(1).linea, nodo.hijos.get(1).columna, nodo.hijos.get(1).valor, "el color es invalido, no existe", "Lenguaje CCSS");
                                }
                                
                            }
                            
                        } catch (Exception e) {
                            System.out.println("error al aplicar fuente");
                        }
                        updateUI();
                    }
                    break;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, Borde  No aplica a "+cmp.tipo, "Lenguaje CCSS");
                    break;
            }
        }
    }
    
    public void setOpaque(NodoCSS raiz, NodoCSS nodo, OperacionesARL opl, Componente porGrupo){
        if (raiz.nombre.equals("identificador")) {
            Componente cmp = lista_componentes.get(raiz.valor.trim());
            if (cmp==null){
                if(porGrupo!=null){
                    cmp=porGrupo;
                }else{
                    cmp = new Componente("", "",new Object(), null);
                }
            }
            try {
                            lanzarEditado(cmp);
                        } catch (Exception e) {} 
            ResultadoG res = opl.ejecutar(nodo.hijos.get(0));
            if (res==null)
                res= new ResultadoG("ninguno", null);
            boolean do_fondo = false;
            switch (res.tipo) {
                case "boolean":
                    do_fondo = true;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, Opaque solo acepta: true o false", "Lenguaje CCSS");
            }
            switch (cmp.tipo) {
                case "boton":
                case "enlace":    
                case "texto":      
                case "areatexto":
                case "cajatexto":
                case "panel":
                case "opcion":
                case "cajaopciones":  
                case "spinner":      
                    JComponent btn = (JComponent) cmp.objeto;
                    if (do_fondo) {
                        boolean valor = (boolean) res.valor;
                        
                        try {
                            btn.setOpaque(valor);
                            System.out.println("se aplico opaque: [" + valor + "]");
                        } catch (Exception e) {
                            System.out.println("error al aplicar opaque");
                        }
                        updateUI();
                    }
                    break;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, Opaque  No aplica a "+cmp.tipo, "Lenguaje CCSS");
                    break;
            }
        }
    }
    
    
    public void setColortext(NodoCSS raiz, NodoCSS nodo, OperacionesARL opl, Componente porGrupo){
        if (raiz.nombre.equals("identificador")) {
            Componente cmp = lista_componentes.get(raiz.valor.trim());
            if (cmp==null){
                if(porGrupo!=null){
                    cmp=porGrupo;
                }else{
                    cmp = new Componente("", "",new Object(), null);
                }
            }
            try {
                lanzarEditado(cmp);
            } catch (Exception e) {} 

            ResultadoG res = opl.ejecutar(nodo.hijos.get(0));
            if (res==null)
                res= new ResultadoG("ninguno", null);
            
            boolean do_colortext = false;
            switch (res.tipo) {
                case "string":
                    do_colortext = true;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, color text solo acepta: string", "Lenguaje CCSS");
            }
            switch (cmp.tipo) {
                case "boton":
                case "enlace":    
                case "texto":  
                case "areatexto":  
                case "cajatexto":     
                    JComponent btn = (JComponent) cmp.objeto;
                    if (do_colortext) {
                        String valor = (String) res.valor;
                        valor=valor.trim();
                        Color color=meta_colores.obtenerColor(valor);

                        try {
                            if(color!=null){
                                btn.setForeground(color);
                            }else{
                                addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, COLORTEXT, color Invalido->: "+valor, "Lenguaje CCSS");
                            }
                            System.out.println("se aplico colortext: [" + valor + "]");
                        } catch (Exception e) {
                            System.out.println("error al aplicar colortext");
                        }
                        updateUI();
                    }
                    break;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, ColorText  No aplica a "+cmp.tipo, "Lenguaje CCSS");
                    break;    
            }
        }
    }
    
    
    public static String toCapital_t(String txt){
        String salida="";
        txt=txt.trim();
        String delim = " ";
        StringTokenizer st = new StringTokenizer(txt, delim);
        while (st.hasMoreTokens()) {
            String tx=st.nextToken();
            tx=tx.substring(0, 1).toUpperCase() + tx.substring(1).toLowerCase();
            salida+=" "+tx;
            //System.out.println(st.nextToken());
        }
        delim = "\n";
        st = new StringTokenizer(salida, delim);
        
        while (st.hasMoreTokens()) {
            String tx=st.nextToken();
            tx=tx.substring(0, 1).toUpperCase() + tx.substring(1).toLowerCase();
            salida+="\n"+tx;
            //System.out.println(st.nextToken());
        }
        return salida;
    }
    
    public  void addError( int fila,int columna,String valor, String Detalle, String pertenece){
        lista_errores.add(new Erro_r(fila, columna, valor, Detalle,pertenece));
    }

    public void presentarHistorial(){
        ArrayList<Historia> ls=VentanaPrincipal.lista_historial;
        JPanel panell= new JPanel();
        panell.setLayout(new BoxLayout(panell, BoxLayout.PAGE_AXIS));
        JScrollPane scroll = new JScrollPane(panell);
        
        for (Historia hi :  ls) {
            JPanel historia = new JPanel();
            historia.setLayout(new GridLayout(1, 2));
            historia.setBackground(Color.ORANGE);
            historia.setPreferredSize(new Dimension(900, 30));
            
            JLabel link = new JLabel(hi.ruta);
            link.setHorizontalAlignment(SwingConstants.CENTER);
            link.setForeground(Color.BLUE);
            link.setToolTipText("Click goto:"+hi.ruta);

            link.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    campoURL.setText(link.getText().trim());
                    cargar_Recargar();
                }
                
            });
            
            JLabel fecha_hora = new JLabel(hi.fecha+"    "+hi.hora);
            fecha_hora.setForeground(Color.black);
            fecha_hora.setHorizontalAlignment(SwingConstants.CENTER);
            historia.add(link);
            historia.add(fecha_hora);
            
            panell.add(historia);
        }
        JFrame ventana = new JFrame("Historial");
        ventana.add(scroll);
        //ventana.setBounds(WIDTH, WIDTH, WIDTH, HEIGHT);
        ventana.setSize(1000, 300);
        ventana.setLocationRelativeTo(this);

        ventana.setVisible(true);
    }

    public void presentarFavoritos(){
        ArrayList<String> ls=VentanaPrincipal.lista_favoritos;
        JPanel panell= new JPanel();
        panell.setLayout(new BoxLayout(panell, BoxLayout.PAGE_AXIS));
        JScrollPane scroll = new JScrollPane(panell);
        
        for (String l : ls) {
            JPanel historia = new JPanel();
            historia.setLayout(new GridLayout(1, 2));
            historia.setBackground(Color.ORANGE);
            historia.setPreferredSize(new Dimension(900, 30));
            
            JLabel link = new JLabel(l);
            link.setHorizontalAlignment(SwingConstants.CENTER);
            link.setForeground(Color.BLUE);
            link.setToolTipText("Click goto:"+l);

            link.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    campoURL.setText(link.getText().trim());
                    cargar_Recargar();
                }
                
            });
            historia.add(link);
            panell.add(historia);
        }
        
        JFrame ventana = new JFrame("FAVORITOS");
        ventana.add(scroll);
        //ventana.setBounds(WIDTH, WIDTH, WIDTH, HEIGHT);
        ventana.setSize(1000, 300);
        ventana.setLocationRelativeTo(this);

        ventana.setVisible(true);
    }
    
    
    public void cargar_Recargar(){
        if (!campoURL.getText().isEmpty()) {
                
                try {
                    try {
                         buscarPagina(campoURL.getText().trim());
                    } catch (URISyntaxException ex) {
                        Logger.getLogger(Template.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Template.class.getName()).log(Level.SEVERE, null, ex);
                }
        } else {
            new AlertaGenerica("campo Url Vacio");
        }
    }
    
    
    public void lanzarEditado(Componente compo){
        if(compo!=null){
            switch(compo.tipo){
                case "boton":
                    ((BotonGenerico)compo.objeto).lanzarEditado();
                    break;
                case "opcion":
                    ((OpcionGenerica)compo.objeto).lanzarEditado();
                    break;  
                case "cajatexto":
                    ((CajaTextoGenerica)compo.objeto).lanzarEditado();
                    break; 
                case "cajaopciones":
                    ((CajaOpcionesGenerica)compo.objeto).lanzarEditado();
                    break; 
                case "areatexto":
                    ((AreaTextoGenerica)compo.objeto).lanzarEditado();
                    break;    
                case "texto":
                    ((TextoGenerico)compo.objeto).lanzarEditado();
                    break; 
                case "spinner":
                    ((SpinnerGenerico)compo.objeto).lanzarEditado();
                    break;   
                case "tabla":
                    ((TablaGenerica2)compo.objeto).lanzarEditado();
                    break;   
                case "enlace":
                    ((EnlaceGenerico)compo.objeto).lanzarEditado();
                    break;  
                case "imagen":
                    ((ImagenGenerica)compo.objeto).lanzarEditado();
                    break;    
                case "panel":
                    ((PanelGenerico)compo.objeto).lanzarEditado();
                    break;      
            }
        }
    }
}


