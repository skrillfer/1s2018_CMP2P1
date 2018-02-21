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
import javax.swing.JButton;

/**
 *
 * @author fernando
 */
public class BotonGenerico extends JButton {

    ArrayList<Propiedad> propiedades;
    
    public BotonGenerico(ArrayList<Propiedad> propiedades) {
        this.propiedades = propiedades;
        
        setPropiedades();
    }
    
    public void setPropiedades() {
        for (Propiedad propiedad : propiedades) {
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
                //*********** todos los componentes denben tener esto *********/   
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
        }
    }
    
}
