/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Estructuras.Propiedad;
import java.awt.Dimension;
import java.util.Hashtable;
import javax.swing.JTextField;

/**
 *
 * @author fernando
 */
public class CajaTextoGenerica extends JTextField {

    Hashtable<String, Propiedad> propiedades;

    public CajaTextoGenerica(Hashtable<String, Propiedad> propiedades) {
        this.propiedades = propiedades;
        setPropiedades();
    }

    public void setPropiedades() {
        if (propiedades != null) {
            //******************************************************************Seteando ID
            try {
                setName(propiedades.get("id").valor);
            } catch (Exception e) {System.out.println(e.getMessage());}
            //**Seteando GRUPO
            //******************************************************************Seteando $text
            try {
                String txt = propiedades.get("$text").valor;
                txt = txt.replaceAll("\r", " ").replaceAll("\n", " ").replace("\t", " ");
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
            

            if(getPreferredSize().height==19 || getPreferredSize().width==4){
                    setPreferredSize(new Dimension(150, 30));
            }
            
            try{
                String alineado = propiedades.get("alineado").valor;
                switch(alineado.toLowerCase()){
                    case "centrado":
                        this.setHorizontalAlignment(JTextField.CENTER);
                        break;
                    case "derecha":
                        setHorizontalAlignment(JTextField.RIGHT);
                        break;
                    case "izquierda":
                        setHorizontalAlignment(JTextField.LEFT);
                        break;
                }
            }catch(Exception e){}
        }

    }

}
