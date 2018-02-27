/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Estructuras.Propiedad;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author fernando
 */
public class EnlaceGenerico extends JLabel{
    Hashtable<String,Propiedad> propiedades;
  
    public EnlaceGenerico(Hashtable<String,Propiedad> propiedades) throws URISyntaxException {
        this.propiedades=propiedades;
        setPropiedades();
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // aqui deberia redireccionar a la nueva pagina
                System.out.println("click en enlace");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                if(!propiedades.get("ruta").valor.trim().isEmpty()){
                    setToolTipText(propiedades.get("ruta").valor.trim());
                }
            }
        });
        
        
        
    }
    
    public void setPropiedades(){
        try {
            setName(propiedades.get("id").valor);
        } catch (Exception e) {System.out.println(e.getMessage());}
        
        try {
            
            String txt="enlace";
            if(!propiedades.get("$text").valor.trim().isEmpty()){
                txt=propiedades.get("$text").valor;
            }

            setText("<html><a href=\"http://www.google.com/\">"+txt+"</a></html>");

        } catch (Exception e) {System.out.println(e.getMessage());}
        //******************************************************************Seteando ALTO
        try {
            setPreferredSize(new Dimension(getPreferredSize().width, Integer.valueOf(propiedades.get("alto").valor)));
        } catch (Exception e) {System.out.println(e.getMessage());}
        //******************************************************************Seteando ANCHO
        try {
            setPreferredSize(new Dimension(Integer.valueOf(propiedades.get("ancho").valor), getPreferredSize().height));
        } catch (Exception e) {System.out.println(e.getMessage());}
        
        System.out.println("="+getPreferredSize());

        if(getPreferredSize().height==10 || getPreferredSize().width==34){
            setPreferredSize(new Dimension(85, 35));
        }
    }
    
}
