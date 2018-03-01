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
            setId();
            setGrupo();
            setAlineado();
            setTexto();
            setFondo();
            setAlto();
            setAncho();
            
            if(getPreferredSize().height==10 || getPreferredSize().width==34){
                setPreferredSize(new Dimension(85, 35));
            }
            setClick();
            setRuta();
            
            
            
            
            
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
    
    public void cambiarGrupo(String grupo){
        grupo=grupo.trim();
        if(!grupo.equals("")){
            // si existe el grupo que quiero setear a este
            // sino existe entonces agrego este grupo
            if(!Template.lista_grupos.containsKey(grupo)){
                Lista lt = new Lista();
                Template.lista_grupos.put(grupo,lt);
            }
            // vere si el id existe en el grupo que tiene este mismo OBJETO
            String a_grupo=propiedades.get("grupo").valor.trim();
            if(!a_grupo.equals("")){ // si no es vacio el grupo de este componente
                // tengo que buscar el componente en la lista de grupos
                Lista lt= Template.lista_grupos.get(a_grupo);
                int index=0;
                
                Componente cmp=null;
                for (Componente componente : lt.getLista()) {
                    if(componente.id.equals(getName())){
                        cmp=lt.getLista().get(index);
                        lt.getLista().remove(index);
                    }
                    index++;
                }
                
                if(cmp!=null)// quiere decir que si pertenecia a un grupo 
                    lt.add(cmp);
                else
                    lt.add(cmp);// entonces agrego el elemetno al grupo..
            }
            
        }
        // cuando cambio de grupo deberia sacar el elemento de la lista ddel GRUPO
    }
    
    public void cambiarId(String id){
        // cuando cambia el id debe de sacarse de la hash de lista de componentens y
        // verificar si el nuevo id se puede cambiar
        id=id.trim();
        if(!id.equals("")){
            if(!Template.lista_componentes.containsKey(id)){
                // se saca el anterior y se mete el nuevo
                Componente cmp = Template.lista_componentes.get(getName());
                
                if(cmp!=null){
                    Template.lista_componentes.remove(id);
                    Template.lista_componentes.put(id, cmp);
                    setName(id);
                    propiedades.get("id").valor=getName();
                            
                }
            }
        }
    }
    
    
    public void setRuta(){
        if (!propiedades.get("ruta").valor.trim().isEmpty()) {
            this.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("me presionaron ruta :D");
                }
            });
        }
    }

    public void setClick(){
        if(!propiedades.get("click").valor.trim().isEmpty()){
                this.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("me presionaron click :D");
                        }
                    });
        }
    }
    public void setId(){
        try {
                setName(propiedades.get("id").valor.trim());
            } catch (Exception e) {System.out.println(e.getMessage());}
        updateUI();
    }
    
    public void setGrupo(){
        try {
                propiedades.get("grupo").valor=propiedades.get("grupo").valor.trim();
            } catch (Exception e) {System.out.println(e.getMessage());}
        updateUI();
    }
    
    public void setTexto(){
         try {
                setText(propiedades.get("$text").valor);
            } catch (Exception e) {System.out.println(e.getMessage());}
            
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
       try{
                String alineado = propiedades.get("alineado").valor.trim();
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
                    case "justificado":
                        //no aplica
                        break;
                    default:
                        //error
                        
                }
            }catch(Exception e){}
        updateUI();
    }
    
    
    
}
