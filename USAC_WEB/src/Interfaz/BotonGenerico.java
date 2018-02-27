/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Estructuras.Propiedad;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 *
 * @author fernando
 */
public class BotonGenerico extends JButton {

    Hashtable<String, Propiedad> propiedades;

    public BotonGenerico(Hashtable<String, Propiedad> propiedades) {
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
            //**Seteando ALINEADO
            //******************************************************************Seteando $text
            try {
                setText(propiedades.get("$text").valor);
            } catch (Exception e) {System.out.println(e.getMessage());}
            
            
            //******************************************************************Seteando ALTO
            try {
                setPreferredSize(new Dimension(getPreferredSize().width, Integer.valueOf(propiedades.get("alto").valor)));
            } catch (Exception e) {System.out.println(e.getMessage());}
            //******************************************************************Seteando ANCHO
            try {
                setPreferredSize(new Dimension(Integer.valueOf(propiedades.get("ancho").valor), getPreferredSize().height));
            } catch (Exception e) {System.out.println(e.getMessage());}
            
            if(getPreferredSize().height==10 || getPreferredSize().width==34){
                setPreferredSize(new Dimension(85, 35));
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
  
            
            if(!propiedades.get("click").valor.isEmpty()){
                this.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("me presionaron click :D");
                        }
                    });
            }
            if(!propiedades.get("ruta").valor.isEmpty()){
                this.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("me presionaron ruta :D");
                        }
                    });
            }
            
            
        }
        /*Enumeration<String> ex = propiedades.keys();
        String clave;
        Propiedad propiedad;
        while (ex.hasMoreElements()) {
            clave = ex.nextElement();
            propiedad = propiedades.get(clave);
            
            switch (propiedad.nombre.toLowerCase()) {
                case "click":
                    this.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //buscar el metodo al que hace click en todos los CJS
                            //despues de encontrarlo ejecuto ese metodo
                        }
                    });
                    break;
                case "ruta":
                    //esta propiedad redirecciona la pagina a otra
                    //venir y buscar el archivo y guardar el nodo raiz que tengo actualmente para hacer el atras
                    this.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //buscar el metodo al que hace click en todos los CJS
                            //despues de encontrarlo ejecuto ese metodo
                        }
                    });
                    break;

                case "$text":
                    setText(propiedad.valor);
                    break;
                case "id":
                    setName(propiedad.valor);
                    break;
                case "grupo":
                    //----------------
                    break;
                case "alto":
                    //System.out.println(Integer.parseInt(propiedad.valor));
                    try {
                        //if(getWidth()==0){
                        setPreferredSize(new Dimension(getPreferredSize().width, Integer.valueOf(propiedad.valor)));
                        //}else{
                        //setPreferredSize(new Dimension(getWidth(), Integer.valueOf(propiedad.valor)));
                        //}
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    //
                    break;
                case "ancho":
                    try {
                        //if(getHeight()==0){
                        setPreferredSize(new Dimension(Integer.valueOf(propiedad.valor), getPreferredSize().height));
                        //}else{
                        //setPreferredSize(new Dimension(Integer.valueOf(propiedad.valor), getSize().height));
                        //}

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case "alineado":
                    //---------------
                    break;
                case "ccss":
                    //---------------
                    break;
            }
        }*/
    }

}
