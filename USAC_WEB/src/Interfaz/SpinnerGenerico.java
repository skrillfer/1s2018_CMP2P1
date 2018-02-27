/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Estructuras.Propiedad;
import java.awt.Dimension;
import java.util.Hashtable;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 *
 * @author fernando
 */
public class SpinnerGenerico extends JSpinner{
    Hashtable<String,Propiedad> propiedades;

    public SpinnerGenerico(Hashtable<String, Propiedad> propiedades) {
        this.propiedades = propiedades;
        setPropiedades();
    }
    
    public void setPropiedades(){
        //******************************************************************Seteando ID
            try {
                setName(propiedades.get("id").valor);
            } catch (Exception e) {System.out.println(e.getMessage());}
            //**Seteando GRUPO
            //******************************************************************Seteando $text
            try {
                if(!propiedades.get("$text").valor.trim().isEmpty()){
                    int inicio = Integer.valueOf(propiedades.get("$text").valor.trim());
                    setValue(inicio);
                }
            } catch (Exception e) {}
            try {
                //setText(propiedades.get("$text").valor+"1;");
            } catch (Exception e) {System.out.println(e.getMessage());}
            //******************************************************************Seteando ALTO
            try {
                setPreferredSize(new Dimension(getPreferredSize().width, Integer.valueOf(propiedades.get("alto").valor)));
            } catch (Exception e) {System.out.println(e.getMessage());}
            //******************************************************************Seteando ANCHO
            try {
                setPreferredSize(new Dimension(Integer.valueOf(propiedades.get("ancho").valor), getPreferredSize().height));
            } catch (Exception e) {System.out.println(e.getMessage());}
            try{
                String alineado = propiedades.get("alineado").valor;
                SimpleAttributeSet attribs = new SimpleAttributeSet();
                
                JComponent editor = this.getEditor();
                JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor)editor;
                switch(alineado.toLowerCase()){
                    case "centrado":
                        spinnerEditor.getTextField().setHorizontalAlignment(JTextField.CENTER);
                        break;
                    case "derecha":
                        spinnerEditor.getTextField().setHorizontalAlignment(JTextField.RIGHT);
                        break;
                    case "izquierda":
                        spinnerEditor.getTextField().setHorizontalAlignment(JTextField.LEFT);
                        break;
                }
            }catch(Exception e){}
    }
    
}
