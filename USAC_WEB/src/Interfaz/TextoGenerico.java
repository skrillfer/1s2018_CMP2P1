/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Estructuras.Propiedad;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Hashtable;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author fernando
 */
public class TextoGenerico extends JLabel{
    Hashtable <String,Propiedad> propiedades;

    public TextoGenerico(Hashtable<String, Propiedad> propiedades) {
        super("", SwingConstants.CENTER);
        this.propiedades = propiedades;
        setPropiedades();
    }
    public void setPropiedades(){
        setId();

        setTexto();
        setFondo();
        setAlineado();
        setBorder(new EtchedBorder(EtchedBorder.RAISED));
    }
    
    public void setId(){
        try {
                setName(propiedades.get("id").valor.trim());
            }catch(Exception ex){}
        updateUI();
    }
    
    public void setTexto(){
        try {
                String texto="<html><p>"+propiedades.get("$text").valor.replaceAll("\n", "<br/>")+"</p></html>";
                setText(texto);
                //setHorizontalAlignment(HEIGHT);
            }catch(Exception ex){}
        updateUI();
    }
    
    public void setAncho(){
         try {
                setPreferredSize(new Dimension(Integer.valueOf(propiedades.get("ancho").valor.trim()), getPreferredSize().height));
            } catch (Exception e) {setPreferredSize(new Dimension(getPreferredSize().width,getPreferredSize().height));}
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
                setForeground(color);
                
                //setBackground(color);
            }
        } catch (Exception e) {}
        updateUI();
    }
    
    public void setAlineado(){
        try {
            String alineado = propiedades.get("alineado").valor.trim();
            switch(alineado){
                case "izquierda":
                    setHorizontalAlignment(SwingConstants.LEFT);
                    break;
                case "derecha":
                    setHorizontalAlignment(SwingConstants.RIGHT);
                    break;
                case "centrado":
                    System.out.println("centrado");
                    setHorizontalAlignment(SwingConstants.CENTER);
                    break;
            }
        } catch (Exception e) {}
        updateUI();
    }
    
}
