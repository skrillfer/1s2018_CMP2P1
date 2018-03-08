/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Estructuras.NodoDOM;
import Estructuras.Propiedad;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextField;
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
    int filas =0;
    int columnas=0;
    NodoDOM raizT;

    public Hashtable<String, Propiedad> propiedadesTabla;

    public TablaGenerica2(Hashtable<String, Propiedad> propiedadesTabla, NodoDOM raizT) {
        this.propiedadesTabla=this.propiedadesTabla;
        this.raizT=raizT;
        setDimension();
        
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
                setPreferredSize(new Dimension(300, 300));
                setBackground(Color.blue);
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
            Color color=Template.meta_colores.obtenerColor(fondo);
            
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
                PanelGenerico panel1 = new PanelGenerico(fill.propiedades);
                if(c<fill.hijos.size()){
                    NodoDOM coll = fill.hijos.get(c);
                    NodoDOM hijo = coll.hijos.get(0);
                    
                    PanelGenerico panel = new PanelGenerico(coll.propiedades);                    
                    add(panel);
                    if(hijo.nombre.equals("boton")){
                        
                       panel.add(new BotonGenerico(hijo.propiedades));
                    }else if(hijo.nombre.equals("imagen")){
                        
                       ImagenGenerica imagen=new ImagenGenerica(hijo.propiedades);
                       
                        try {
                            Template.addComponente(imagen.getName(), "imagen", imagen, this, "tabla", getName());
                        } catch (Exception e) {}
                       panel.add(imagen);
                    }else if(hijo.nombre.equals("texto")){
                        //System.out.println("=>=>"+coll.propiedades.get("$text").valor);
                       AreaTextoGenerica area=new AreaTextoGenerica(coll.propiedades,panel.getPreferredSize());
                       area.setOpaque(true);
                       //area.setEditable(false);
                       area.setBorder(new EtchedBorder(EtchedBorder.RAISED));
                       try {
                            Template.addComponente(area.getName(), "areatexto", area, this, "tabla", getName());
                        } catch (Exception e) {}
                       panel.add(area);
                    }
                    panel.setBackground(Color.ORANGE);
                    //add(panel);
                }else{
                    add(panel1);
                    AreaTextoGenerica area=new AreaTextoGenerica(fill.propiedades,panel1.getPreferredSize());
                    area.setOpaque(true);
                    //area.setEditable(false);
                    area.setBorder(new EtchedBorder(EtchedBorder.RAISED));
                    try {
                         Template.addComponente(area.getName(), "areatexto", area, this, "tabla", getName());
                     } catch (Exception e) {}
                    panel1.add(area);
                    panel1.setBackground(Color.orange);
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
