/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Estructuras.Propiedad;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author fernando
 */
public  class ImagenGenerica extends JLabel implements MouseListener{
    Hashtable<String,Propiedad> propiedades;
    int alto=0;
    int ancho=0;
    String click="";
    public ImagenGenerica(Hashtable<String, Propiedad> propiedades) {
        this.propiedades = propiedades;
        setPropiedades();
    }
    
    public void setPropiedades(){
        setId();
        setClick();
        setAlto();
        setAncho();
        setTexto();
        setFondo();
        renderizarImagen();
        
        
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(click);
                // aqui se busca el motodo al cual hace click
            }
        });
        
        
    }
    
    public void renderizarImagen(){
        ImageIcon img=null;
        String ruta=getRutaTexto(propiedades.get("$text").valor.trim());
        File f = new File(ruta);
        if(ruta.equals("") || !f.exists() || f.isDirectory()){
            ruta=propiedades.get("ruta").valor.trim();
            f = new File(ruta);
        }
        if(ruta.equals("") || !f.exists() || f.isDirectory()){
            ruta="src/Recursos/image_notfound.png";
        }
        if(alto>0 && ancho>0){
            img = new ImageIcon(new ImageIcon(ruta).getImage().getScaledInstance(ancho,alto, Image.SCALE_DEFAULT));
        }else{
            img = new ImageIcon(propiedades.get("ruta").valor.trim());
        }
        setIcon(img);
    }
    
    public void setAlto(){
        try {
            alto=Integer.valueOf(propiedades.get("alto").valor);
        } catch (Exception e) {}
    }
    
    public void setAncho(){
        try {
            ancho=Integer.valueOf(propiedades.get("ancho").valor);
        } catch (Exception e) {}
    }
    
    public void setClick(){
        try {
            click= propiedades.get("click").valor.trim();
        } catch (Exception e) {}
        
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
    
    public String getRutaTexto(String ruta){
        ruta=ruta.trim();
        try {
            ruta=ruta.substring(1,ruta.length()-1);
        } catch (Exception e) {
        }
        System.out.println(ruta);
        return ruta;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}