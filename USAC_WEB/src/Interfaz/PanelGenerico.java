/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Estructuras.*;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author fernando
 */
public class PanelGenerico extends JPanel{
    Hashtable<String,Propiedad> propiedades;

    public PanelGenerico(Hashtable<String,Propiedad> propiedades) {
        this.propiedades=propiedades;
        setPropiedades();
    }
    
    
    public void setPropiedades(){
        
        setBorder(new EtchedBorder(EtchedBorder.RAISED));
        setId();
        setTexto();
        setAncho();
        setAlto();
        setFondo();
        setAlineado();
    }
    
    
    
    public void setId(){
        try {
                setName(propiedades.get("id").valor.trim());
            }catch(Exception ex){}
        updateUI();
    }
    
    public void setTexto(){
        try {
                setToolTipText(propiedades.get("$text").valor.trim());
            }catch(Exception ex){}
        updateUI();
    }
    
    public void setAncho(){
         try {
                setPreferredSize(new Dimension(Integer.valueOf(propiedades.get("ancho").valor.trim()), getPreferredSize().height));
            } catch (Exception e) {System.out.println(e.getMessage());}
        updateUI();
    }
    public void setAlto(){
        try {
                setPreferredSize(new Dimension(getPreferredSize().width, Integer.valueOf(propiedades.get("alto").valor.trim())));
            } catch (Exception e) {System.out.println(e.getMessage());}
        updateUI();
    }
    
    public void setFondo(){
        try {
            String fondo = propiedades.get("fondo").valor.trim();
            Color color=Template.meta_colores.obtenerColor(fondo);
            if(color!=null){
                setBackground(color);
            }
        } catch (Exception e) {}
        updateUI();
    }
    
    public void setAlineado(){
        try {
            String alineado = propiedades.get("alineado").valor.trim();
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
            }
        } catch (Exception e) {}
        updateUI();
    }
}
