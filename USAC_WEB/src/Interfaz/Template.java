/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

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
import java.util.StringTokenizer;

/**
 *
 * @author fernando
 */
public class Template extends JPanel implements ActionListener{
    //**************************************************************************
    static Hashtable<String,Componente> lista_componentes= new Hashtable<>();
    static Hashtable<String,Lista> lista_grupos= new Hashtable<>();
    
    static ArrayList<Erro_r> lista_errores = new ArrayList<>();
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
        panel_P.setName("$cuerpo");
        lista_componentes.put("$cuerpo",new Componente("$cuerpo","panel",panel_P,null));
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
           NodoDOM dom=new USAC_WEB().CompilarCHTML(leerArchivo("/home/fernando/NetBeansProjects/1s2018_CMP2P1/USAC_WEB/last.txt"));
           if(dom!=null){
               GENERADOR_VISTA(dom,panel_P);
               ejecutarArchivosCcss();
           }
           
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
                    System.out.println("entre a cuerpo");
                    setPropiedadesCuerpo(hijo.propiedades);
                    GENERADOR_VISTA(hijo,panel_P);
                    break;
                case "panel":
                    JPanel nuevopanel = new PanelGenerico(hijo.propiedades);
                    addComponente(nuevopanel.getName(),"panel",nuevopanel,panel_P,"panel",panel_P.getName());
                    panel_P.add(nuevopanel);
                    updateUI();
                    GENERADOR_VISTA(hijo,nuevopanel);
                    //panel_P=pila_panels.pop();
                    break;
                case "texto":
                    JLabel texxto = new TextoGenerico(hijo.propiedades);
                    addComponente(texxto.getName(),"texto",texxto,panel_P,"panel",panel_P.getName());
                    panel_P.add(texxto);
                    updateUI();
                    break;
                case "boton":                    
                    JButton btn=new BotonGenerico(hijo.propiedades);
                    addComponente(btn.getName(),"boton",btn,panel_P,"panel",panel_P.getName());
                    panel_P.add(btn);                    
                    updateUI();
                    break;
                case "caja_texto":
                    JTextField txt=new CajaTextoGenerica(hijo.propiedades);
                    addComponente(txt.getName(),"cajatexto",txt,panel_P,"panel",panel_P.getName());
                    panel_P.add(txt); 
                    updateUI();
                    break;
                case "texto_a":
                    //VER BIEN
                    JTextPane txt_a=new AreaTextoGenerica(hijo.propiedades);
                    JScrollPane scroll = new JScrollPane(txt_a,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    scroll.setName("texto_a-"+txt_a.getName());
                    addComponente(txt_a.getName(),"areatexto",txt_a,panel_P,"panel",panel_P.getName());
                    panel_P.add(scroll);  
                    updateUI();
                    break;
                case "spinner":
                    JSpinner sp= new SpinnerGenerico(hijo.propiedades);
                    addComponente(sp.getName(),"spinner",sp,panel_P,"panel",panel_P.getName());
                    panel_P.add(sp);
                    updateUI();
                    break;
                case "enlace":
                     JLabel enlac = new EnlaceGenerico(hijo.propiedades);
                     addComponente(enlac.getName(),"enlace",enlac,panel_P,"panel",panel_P.getName());
                     panel_P.add(enlac);
                     updateUI();
                     break;
                case "tabla":
                     JPanel tabla = new TablaGenerica2(hijo.propiedades, hijo);
                     addComponente(tabla.getName(),"tabla",tabla,panel_P,"panel",panel_P.getName());
                     panel_P.add(tabla);
                     updateUI();
                     break;
                case "imagen":
                    JLabel img = new ImagenGenerica(hijo.propiedades);
                    addComponente(img.getName(),"imagen",img,panel_P,"panel",panel_P.getName());
                    panel_P.add(img);
                    updateUI();
                    break;
                case "caja":
                    JComboBox combo = new CajaOpcionesGenerica(hijo.propiedades, hijo.hijos,meta_colores);
                    addComponente(combo.getName(),"cajaopciones",combo,panel_P,"panel",panel_P.getName());
                    panel_P.add(combo);
                    updateUI();
                    break;
            }
        }
    }
    
    public void addComponente(String id, String tipo,Object objeto, Object padre, String tipopadre, String idpadre){
        System.out.println("CCCC   >   " + id);
        
            //se obtiene el componente padre
        Componente cpadre = null;
        //se vefirica que exista el padre
        if (lista_componentes.containsKey(idpadre)) {
            cpadre = lista_componentes.get(idpadre);
        }
        //si el padre no es nulo
        if (cpadre != null) {
            //se crea el componente hijo
            if (!lista_componentes.containsKey(id.trim())) {
                Componente chijo = new Componente(id, tipo, objeto, cpadre);
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
                }

                if (lista_grupos.containsKey(grupo)) {
                    System.out.println("se agrego a GRUPO: [" + grupo + "] a [" + id+"]");
                    lista_grupos.get(grupo).getLista().add(chijo);
                }

            }

        }
    }
    
    public String getProp_Componente(String tipo, Object objeto, String prop){
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
    
    
    public void ejecutarArchivosCcss() throws FileNotFoundException{
        Enumeration<String> llaves = lista_ccss.keys();
        while (llaves.hasMoreElements()) {
            String llave = llaves.nextElement();
            String vruta = lista_ccss.get(llave).trim();
            
            //*** para cada una de las rutas guardadas, vento y compilo
           NodoCSS dom=new USAC_WEB().compilarCCSS(leerArchivo(vruta));
           
           if(dom!=null){
               for (NodoCSS bloque : dom.hijos) {
                   for (NodoCSS nodo : bloque.hijos) {
                       switch (nodo.nombre) {
                           case "grupo":
                               aplicarCcss(nodo);
                               break;
                           case "identificador":
                               aplicarCcss(nodo);
                               break;
                       }
                   }
               }
                
           }
        }
    }
    
    public void aplicarCcss(NodoCSS raiz){
        
        OperacionesARL opl = new OperacionesARL();
        for (NodoCSS nodo : raiz.hijos) {
            switch(nodo.nombre){
                case "alineado":
                    if(raiz.nombre.equals("identificador")){
                        Componente cmp = lista_componentes.get(raiz.valor.trim());
                        ResultadoG res = opl.ejecutar(nodo.hijos.get(0));
                        boolean do_alineado = false;
                        switch (res.tipo) {
                            case "izquierda":
                            case "derecha":
                            case "justificado":
                            case "centrado":
                                do_alineado = true;
                            default:
                                addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "alineado solo acepta: izquierda,derecha,centrado,justificado", "Lenguaje CCSS");

                        }
                        switch (cmp.tipo) {
                            case "boton":
                                BotonGenerico btn = (BotonGenerico) cmp.objeto;
                                if(do_alineado){
                                    btn.propiedades.get("alineado").valor = res.tipo;
                                    btn.setAlineado();
                                    System.out.println("se alineo a "+res.tipo);
                                }
                                break;
                            case "enlace":
                                EnlaceGenerico enl = (EnlaceGenerico) cmp.objeto;
                                if(do_alineado){
                                    enl.propiedades.get("alineado").valor = res.tipo;
                                    //enl.setAlineado();
                                }
                        }
                    }
                    break;
                case "texto":
                    if(raiz.nombre.equals("identificador")){
                        Componente cmp = lista_componentes.get(raiz.valor.trim());
                        ResultadoG res = opl.ejecutar(nodo.hijos.get(0));
                        boolean do_texto = false;
                        switch (res.tipo) {
                            case "string":
                                do_texto = true;
                            default:
                                addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "alineado solo acepta: izquierda,derecha,centrado,justificado", "Lenguaje CCSS");

                        }
                        switch (cmp.tipo) {
                            case "boton":
                                BotonGenerico btn = (BotonGenerico) cmp.objeto;
                                if(do_texto){
                                    btn.propiedades.get("$text").valor = (String)res.valor;
                                    btn.setTexto();
                                }
                                break;
                        }
                    }
                    break;    
                case "formato":
                    if(raiz.nombre.equals("identificador")){
                        Componente cmp = lista_componentes.get(raiz.valor.trim());
                        NodoCSS valores= nodo.hijos.get(0);
                        for (NodoCSS valor : valores.hijos) {
                            ResultadoG res = opl.ejecutar(valor);
                            switch (res.tipo) {
                                case "capital-t":
                                    switch (cmp.tipo) {
                                        case "boton":
                                            BotonGenerico btn = (BotonGenerico) cmp.objeto;
                                            try {
                                                String tp=toCapital_t(btn.propiedades.get("$text").valor);
                                                if(!tp.equals("")){
                                                    btn.propiedades.get("$text").valor=tp;
                                                }
                                            } catch (Exception e) {}
                                            btn.setTexto();
                                            break;
                                    }
                                    break;
                                case "mayuscula":
                                    switch (cmp.tipo) {
                                        case "boton":
                                            BotonGenerico btn = (BotonGenerico) cmp.objeto;
                                            try {
                                                btn.propiedades.get("$text").valor=btn.propiedades.get("$text").valor.toUpperCase();
                                            } catch (Exception e) {}
                                            btn.setTexto();
                                            System.out.println("se seeo mayusual");
                                            break;
                                    }
                                    break;
                                case "minuscula":
                                    switch (cmp.tipo) {
                                        case "boton":
                                            BotonGenerico btn = (BotonGenerico) cmp.objeto;
                                            try {
                                                btn.propiedades.get("$text").valor=btn.propiedades.get("$text").valor.toLowerCase();
                                            } catch (Exception e) {}
                                            btn.setTexto();
                                            break;
                                    }
                                    break;
                                case "cursiva":
                                    switch (cmp.tipo) {
                                        case "boton":
                                            BotonGenerico btn = (BotonGenerico) cmp.objeto;
                                            Font ft =null;
                                            if(btn.getFont().isBold()){
                                                ft=new Font(btn.getFont().getName(),Font.ITALIC+Font.BOLD,btn.getFont().getSize());
                                            }else{
                                                ft=new Font(btn.getFont().getName(),Font.ITALIC,btn.getFont().getSize());
                                            }
                                            try {
                                                btn.setFont(ft);
                                            } catch (Exception e) {}
                                            btn.setTexto();
                                            break;
                                    }
                                    break;
                                case "negrilla":
                                    switch (cmp.tipo) {
                                        case "boton":
                                            BotonGenerico btn = (BotonGenerico) cmp.objeto;
                                            Font ft =null;
                                            if(btn.getFont().isItalic()){
                                                ft=new Font(btn.getFont().getName(),Font.ITALIC+Font.BOLD,btn.getFont().getSize());
                                            }else{
                                                ft=new Font(btn.getFont().getName(),Font.BOLD,btn.getFont().getSize());
                                            }
                                            try {
                                                btn.setFont(ft);
                                            } catch (Exception e) {}
                                            btn.setTexto();
                                            break;
                                    }
                                    break;
                                default:
                                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "alineado solo acepta: izquierda,derecha,centrado,justificado", "Lenguaje CCSS");

                            }   
                        }
                    }
                    
                    break;   
                case "letra":
                    if(raiz.nombre.equals("identificador")){
                        Componente cmp = lista_componentes.get(raiz.valor.trim());
                        ResultadoG res = opl.ejecutar(nodo.hijos.get(0));
                        boolean do_letra = false;
                        switch (res.tipo) {
                            case "string":
                                do_letra = true;
                            default:
                                addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "alineado solo acepta: izquierda,derecha,centrado,justificado", "Lenguaje CCSS");
                        }
                        switch (cmp.tipo) {
                            case "boton":
                                BotonGenerico btn = (BotonGenerico) cmp.objeto;
                                if(do_letra){
                                    String valor = (String)res.valor;
                                    valor= valor.trim();
                                    Font ft = null;
                                    try {
                                        ft= new Font(valor,btn.getFont().getStyle(),btn.getFont().getSize());
                                        btn.setFont(ft);
                                        System.out.println("se aplico fuente: ["+valor+"]");
                                    } catch (Exception e) { System.out.println("error al aplicar fuente");}
                                    btn.setTexto();
                                }
                                break;
                        }
                    }
                    break; 
                case "tamtex":
                    setTamtex(raiz, nodo, opl);
                    break;    
                case "fondoelemento":
                    setFondoElemento(raiz, nodo, opl);
                    break;    
                case "autoredimension":
                    // aqui no se que putas hacer
                    break;    
                case "visible":
                    setVisible(raiz, nodo, opl);
                    break;        
                case "borde":
                    break;
                case "opaque":
                    break;
                case "colortext":
                    break;
            }
        }
    }
    
    
    public void setTamtex(NodoCSS raiz, NodoCSS nodo, OperacionesARL opl){
        if (raiz.nombre.equals("identificador")) {
            Componente cmp = lista_componentes.get(raiz.valor.trim());
            ResultadoG res = opl.ejecutar(nodo.hijos.get(0));
            boolean do_tamtext = false;
            switch (res.tipo) {
                case "number":
                    do_tamtext = true;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "alineado solo acepta: izquierda,derecha,centrado,justificado", "Lenguaje CCSS");
            }
            switch (cmp.tipo) {
                case "boton":
                    BotonGenerico btn = (BotonGenerico) cmp.objeto;
                    if (do_tamtext) {
                        Double valor = (Double) res.valor;
                        long value = Math.round(valor);
                        Integer tam = (int)(long)value;
                        
                        Font ft = null;
                        try {
                            ft = new Font(btn.getFont().getName(), btn.getFont().getStyle(), tam);
                            btn.setFont(ft);
                            System.out.println("se aplico tam: [" + tam + "]");
                        } catch (Exception e) {
                            System.out.println("error al aplico tamtext");
                        }
                        btn.setTexto();
                    }
                    break;
            }
        }
    }
    
    
    public void setFondoElemento(NodoCSS raiz, NodoCSS nodo, OperacionesARL opl){
        if (raiz.nombre.equals("identificador")) {
            Componente cmp = lista_componentes.get(raiz.valor.trim());
            ResultadoG res = opl.ejecutar(nodo.hijos.get(0));
            boolean do_fondo = false;
            switch (res.tipo) {
                case "string":
                    do_fondo = true;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "alineado solo acepta: izquierda,derecha,centrado,justificado", "Lenguaje CCSS");
            }
            switch (cmp.tipo) {
                case "boton":
                    BotonGenerico btn = (BotonGenerico) cmp.objeto;
                    if (do_fondo) {
                        String valor = (String) res.valor;
                        valor = valor.trim();
                        Color color = meta_colores.obtenerColor(valor);
                        try {
                            if(color!=null){
                                btn.setBackground(color);
                            }else{
                                addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna,(String) nodo.hijos.get(0).valor , "el color es invalido", "LenguajeCCSS");
                            }
                            System.out.println("se aplico fondocolor: [" + valor + "]");
                        } catch (Exception e) {
                            System.out.println("error al aplicar fuente");
                        }
                        btn.setTexto();
                    }
                    break;
            }
        }
    }
    
    
    public void setVisible(NodoCSS raiz, NodoCSS nodo, OperacionesARL opl){
        if (raiz.nombre.equals("identificador")) {
            Componente cmp = lista_componentes.get(raiz.valor.trim());
            ResultadoG res = opl.ejecutar(nodo.hijos.get(0));
            boolean do_fondo = false;
            switch (res.tipo) {
                case "boolean":
                    do_fondo = true;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "alineado solo acepta: izquierda,derecha,centrado,justificado", "Lenguaje CCSS");
            }
            switch (cmp.tipo) {
                case "boton":
                    BotonGenerico btn = (BotonGenerico) cmp.objeto;
                    if (do_fondo) {
                        boolean valor = (boolean) res.valor;
                        
                        try {
                            btn.setVisible(valor);
                            btn.setVisible(valor);
                            System.out.println("se aplico visibilidad: [" + valor + "]");
                        } catch (Exception e) {
                            System.out.println("error al aplicar fuente");
                        }
                        btn.setTexto();
                    }
                    break;
            }
        }
    }
    
    public void setBorde(NodoCSS raiz, NodoCSS nodo, OperacionesARL opl){
        if (raiz.nombre.equals("identificador")) {
            Componente cmp = lista_componentes.get(raiz.valor.trim());
            ResultadoG res1 = opl.ejecutar(nodo.hijos.get(0));
            ResultadoG res2 = opl.ejecutar(nodo.hijos.get(1));
            ResultadoG res3 = opl.ejecutar(nodo.hijos.get(2));
            
            boolean do_borde = false;
            
            if(res1.tipo.equals("number") && res2.tipo.equals("string") && res1.tipo.equals("boolean")){
                do_borde=true;
            }else{
                addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "borde debe ser [number,string,boolean] error en los tipos", "Lenguaje CCSS");
            }
            
            switch (cmp.tipo) {
                case "boton":
                    BotonGenerico btn = (BotonGenerico) cmp.objeto;
                    if (do_borde) {
                        Double r1=(double)res1.valor;
                        String r2=(String)res2.valor;
                        boolean r3 = (boolean)res3.valor;
                        
                        try {
                            //btn.setVisible(valor);
                            //btn.setVisible(valor);
                            //System.out.println("se aplico visibilidad: [" + valor + "]");
                        } catch (Exception e) {
                            System.out.println("error al aplicar fuente");
                        }
                        btn.setTexto();
                    }
                    break;
            }
        }
    }
    
    
    
    public String toCapital_t(String txt){
        String salida="";
        txt=txt.trim();
        String delim = " \n\r\t";
        StringTokenizer st = new StringTokenizer(txt, delim);
        while (st.hasMoreTokens()) {
            String tx=st.nextToken();
            tx=tx.substring(0, 1).toUpperCase() + tx.substring(1).toLowerCase();
            salida+=" "+tx;
            //System.out.println(st.nextToken());
        }
        
        return salida;
    }
    
    public void addError( int fila,int columna,String valor, String Detalle, String pertenece){
        lista_errores.add(new Erro_r(fila, columna, valor, Detalle,pertenece));
    }
}


class Componente {
    public String id;
    public String tipo; // boton,imagen,enlace etc.
    Object objeto ;
    Componente padre;
    public Componente(String id, String tipo, Object objeto,Componente padre) {
        this.id = id;
        this.tipo = tipo;
        this.objeto = objeto;
        this.padre=padre;
    }

    public Componente() {
        this.id="";
        this.tipo="";
        this.objeto=null;
        this.padre=null;
    }
    

}
