/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import CJS_Compilador.CJS;
import CJS_Compilador.Clase;
import CJS_Compilador.TablaSimboloG;
import Estructuras.Nodo;
import Estructuras.NodoDOM;
import Estructuras.Observador;
import Estructuras.Propiedad;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author fernando
 */
public class TablaGenerica2 extends JPanel {
        public Observador click=null;
    public Observador modificado=null;
    public Observador listo=null;
    public  CJS principal_cjs=null;
    int filas =0;
    int columnas=0;
    NodoDOM raizT;

    public Hashtable<String, Propiedad> propiedadesTabla;
    public Template template1;
    public TablaGenerica2(Hashtable<String, Propiedad> propiedadesTabla, NodoDOM raizT,CJS principal_cjs,Template template1) {
        this.template1=template1;
        this.principal_cjs=principal_cjs;
        this.propiedadesTabla=propiedadesTabla;
        this.raizT=raizT;
        setDimension();
        
        
        System.out.println("FONDO TABLA"+propiedadesTabla.get("fondo").valor);
        try {
            if(filas>0 && columnas>0){
                setLayout(new GridLayout(filas, columnas));
            }
        } catch (Exception e) {
        }
        
        setPropiedades();
        
        try {
            setDatos();
        } catch (Exception e) {}
        
        
        try {
            if(propiedadesTabla.get("alto").valor.equals("") && propiedadesTabla.get("alto").valor.equals("") ){
                setPreferredSize(new Dimension(400, 400));
                //setBackground(Color.blue);
            }
        } catch (Exception e) {
        }
        
        
    }
    
    public void setPropiedades(){
        setBorder(new EtchedBorder(EtchedBorder.RAISED));
        setId();
        setGrupo();
        setAlineado();
        setTexto();
        setFondo();
        setAlto();
        setAncho();
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            
                try {
                    String metodo = propiedadesTabla.get("click").valor;
                    if(!metodo.equals("")){
                        metodo = metodo.replace("(", "").replace(")", "");
                        principal_cjs.ejecutarMetodo(metodo, 0, 0,template1);
                    }
                } catch (Exception ex) {
                }

                try {
                    lanzarClick();
                } catch (Exception ex) {
                }
            }
            
        });
    }
    
    
    
    public void setObservador(TablaSimboloG global,TablaSimboloG tabla, ArrayList<Nodo> sentencias, String tipo, Clase claseActual){
        switch(tipo){
            case "click":
                click = new Observador(global, tabla, sentencias,claseActual);
                break;
            case "modificado":        
                modificado = new Observador(global, tabla, sentencias,claseActual);
                break;
            case "listo":
                listo = new Observador(global, tabla, sentencias,claseActual);
                break;
        }
    }
    
    
    
    public void lanzarClick(){
        if(click!=null){
            principal_cjs.ejecutarMETODO1(click.global, click.tabla,click.sentencias, click.claseActual,template1);
        }
    }
    
    public void lanzarEditado(){
        if(modificado!=null){
            principal_cjs.ejecutarMETODO1(modificado.global, modificado.tabla,modificado.sentencias, modificado.claseActual,template1);
        }
    }
    
    public void lanzarFinalizado(){
        if(listo!=null){
            principal_cjs.ejecutarMETODO1(listo.global, listo.tabla,listo.sentencias, listo.claseActual,template1);
        }
    }
    
    
    
    public boolean setId(){
        try {
                setName(propiedadesTabla.get("id").valor.trim());
                updateUI();
                return true;
            }catch(Exception ex){return false;}    
    }
    
    public void setGrupo() {
        try {
            propiedadesTabla.get("grupo").valor = propiedadesTabla.get("grupo").valor.trim();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        updateUI();
    }

    public void setTexto(){
        try {
                setToolTipText(propiedadesTabla.get("$text").valor.trim());
            }catch(Exception ex){}
        updateUI();
    }
    
    public boolean setAncho(){
         try {
                setPreferredSize(new Dimension(Integer.valueOf(propiedadesTabla.get("ancho").valor.trim()), getPreferredSize().height));
                updateUI();
                return true;
            } catch (Exception e) {System.out.println(e.getMessage()); return false;}
        
    }
    
    public boolean setFondo(){
        try {
            String fondo = propiedadesTabla.get("fondo").valor.trim();
            Color color=template1.meta_colores.obtenerColor(fondo);
            
            if(color!=null){
                setBackground(color);
                updateUI();
                return true;
            }
            updateUI();
            return false;
        } catch (Exception e) { return false;}
        
    }
    
    public boolean setAlineado(){
        boolean flag=true;
        try {
            String alineado = propiedadesTabla.get("alineado").valor.trim();
            switch(alineado){
                case "izquierda":
                    setLayout(new FlowLayout(FlowLayout.LEFT));
                    //setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                    break;
                case "derecha":
                    setLayout(new FlowLayout(FlowLayout.RIGHT));
                    //setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    break;
                case "centrado":
                    setLayout(new FlowLayout(FlowLayout.CENTER));
                    break;
                case "justificado":
                    setLayout(new FlowLayout(FlowLayout.LEADING));
                    break; 
                default:
                    flag=false;
                    
            }
        } catch (Exception e) {flag=false;}
        updateUI();
        return flag;
    }
    
    public boolean setAlto(){
        try {
                setPreferredSize(new Dimension(getPreferredSize().width, Integer.valueOf(propiedadesTabla.get("alto").valor.trim())));
                updateUI();
                return true;
            } catch (Exception e) {System.out.println(e.getMessage()); return false;}
        
    }
    public void setDimension() {
        if (raizT != null) {
            int tam_filas = raizT.hijos.size();
            int tam_colMax = 0;
            for (int i = 0; i < tam_filas; i++) {
                int tmpTam = raizT.hijos.get(i).hijos.size();
                
                if (tmpTam > tam_colMax) {
                    tam_colMax = tmpTam;
                }
            }
            filas=tam_filas;
            columnas=tam_colMax;
            //System.out.println("numeroFilas: " + tam_filas + " ColsMax:" + tam_colMax);
        }
    }

    public void setDatos() {
        for (int f = 0; f < raizT.hijos.size(); f++) {
            NodoDOM fill = raizT.hijos.get(f);
            
            
            for (int c = 0; c < columnas; c++) {
                PanelGenerico panel1 = new PanelGenerico(fill.propiedades,principal_cjs,template1);
                panel1.setVisible(false);
                if(c<fill.hijos.size()){
                    NodoDOM coll = fill.hijos.get(c);
                    NodoDOM hijo = coll.hijos.get(0);
                    
                    PanelGenerico panel = new PanelGenerico(coll.propiedades,principal_cjs,template1);
                    panel.setVisible(false);
                    add(panel);
                    if(hijo.nombre.equals("boton")){
                        BotonGenerico btng = new BotonGenerico(hijo.propiedades,principal_cjs,template1);
                        try {
                            template1.addComponente(btng.getName(), "boton", btng, this, "tabla", getName());
                        } catch (Exception e) {}
                       panel.add(btng);
                    }else if(hijo.nombre.equals("imagen")){
                        
                       ImagenGenerica imagen=new ImagenGenerica(hijo.propiedades,principal_cjs,template1);
                       imagen.setVisible(false);
                        try {
                            template1.addComponente(imagen.getName(), "imagen", imagen, this, "tabla", getName());
                        } catch (Exception e) {}
                       panel.add(imagen);
                    }else if(hijo.nombre.equals("texto")){
                        //System.out.println("=>=>"+coll.propiedades.get("$text").valor);
                       AreaTextoGenerica area=new AreaTextoGenerica(coll.propiedades,panel.getPreferredSize(),principal_cjs,template1);
                       
                       if(area.propiedades.get("alto").valor.trim().equals("") || area.propiedades.get("alto").valor.trim().equals("")){
                           area.setPreferredSize(new Dimension(90, 120));
                       }
                       
                       area.setVisible(false);
                       area.setOpaque(true);
                       area.setEditable(false);
                       area.setBorder(new EtchedBorder(EtchedBorder.RAISED));
                       try {
                            template1.addComponente(area.getName(), "areatexto", area, this, "tabla", getName());
                            
                        } catch (Exception e) {}
                       panel.add(area);
                    }
                    //panel.setBackground(Color.ORANGE);
                    //add(panel);
                }else{
                    add(panel1);
                     
                     AreaTextoGenerica area=new AreaTextoGenerica(fill.propiedades,panel1.getPreferredSize(),principal_cjs,template1);
                     if(area.propiedades.get("alto").valor.trim().equals("") || area.propiedades.get("alto").valor.trim().equals("")){
                           area.setPreferredSize(new Dimension(90, 120));
                     }
                       
                    area.setVisible(false);
                    area.setOpaque(true);
                    area.setEditable(false);
                    area.setBorder(new EtchedBorder(EtchedBorder.RAISED));
                    try {
                         template1.addComponente(area.getName(), "areatexto", area, this, "tabla", getName());
                     } catch (Exception e) {}
                    panel1.add(area);
                    //panel1.setBackground(Color.orange);
                    panel1.updateUI();
                    //add(panel1);
                }
            }
        }
    }
    
    public String getValElemento(NodoDOM col) {
        String elemento = "";
        NodoDOM hijo = col.hijos.get(0);
        if (hijo.nombre.equals("boton")) {
            
            elemento = hijo.propiedades.get("$text").valor;
        } else if (hijo.nombre.equals("imagen")) {
            elemento = hijo.propiedades.get("$text").valor;
        } else if (hijo.nombre.equals("texto")) {
            elemento = col.propiedades.get("$text").valor;
        }
        return elemento;
    }
}
