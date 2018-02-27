/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Estructuras.Propiedad;
import java.awt.Dimension;
import java.util.Hashtable;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;



/**
 *
 * @author fernando
 */
public class AreaTextoGenerica extends JTextPane{
    Hashtable<String,Propiedad> propiedades;

    public AreaTextoGenerica(Hashtable<String,Propiedad> propiedades) {
        this.propiedades = propiedades;
        setPropiedades();
    }
    
    public void setPropiedades(){
        if(propiedades!=null){
            //******************************************************************Seteando ID
            try {
                setName(propiedades.get("id").valor);
            } catch (Exception e) {System.out.println(e.getMessage());}
            //**Seteando GRUPO
            //******************************************************************Seteando $text
            try {
                String txt = propiedades.get("$text").valor;
                setText(txt);
            } catch (Exception e) {}
            
            //******************************************************************Seteando ALTO
            try {
                setPreferredSize(new Dimension(getPreferredSize().width, Integer.valueOf(propiedades.get("alto").valor)));
            } catch (Exception e) {System.out.println(e.getMessage());}
            //******************************************************************Seteando ANCHO
            try {
                setPreferredSize(new Dimension(Integer.valueOf(propiedades.get("ancho").valor), getPreferredSize().height));
            } catch (Exception e) {System.out.println(e.getMessage());}
            

            if(getPreferredSize().height==21 || getPreferredSize().width==6){
                setPreferredSize(new Dimension(150, 150));
            }
            try{
                String alineado = propiedades.get("alineado").valor;
                SimpleAttributeSet attribs = new SimpleAttributeSet();
                
                
                switch(alineado.toLowerCase()){
                    case "centrado":
                        StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_CENTER);
                        setParagraphAttributes(attribs, true);
                        //this.setHorizontalAlignment(JTextField.CENTER);
                        break;
                    case "derecha":
                        StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_RIGHT);
                        setParagraphAttributes(attribs, true);
                        //setHorizontalAlignment(JTextField.RIGHT);
                        break;
                    case "izquierda":
                        StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_LEFT);
                        setParagraphAttributes(attribs, true);
                        //setHorizontalAlignment(JTextField.LEFT);
                        break;
                }
            }catch(Exception e){}
        }
    }
    
}
