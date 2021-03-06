/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import CJS_Compilador.CJS;
import CJS_Compilador.Clase;
import CJS_Compilador.TablaSimboloG;
import Estructuras.Nodo;
import Estructuras.Observador;
import Estructuras.Propiedad;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
    public  CJS principal_cjs;
    public Observador click=null;
    public Observador modificado=null;
    public Observador listo=null;
    
    public Nodo metodo = null; 
    public boolean mayuscula=false;
    public boolean minuscula=false;
    public boolean capital_t=false; 
    
    public Hashtable<String, Propiedad> propiedades;
    public Template template1;
    public BotonGenerico(Hashtable<String, Propiedad> propiedades,CJS principal_cjs,Template template1) {
        this.template1=template1;
        this.principal_cjs=principal_cjs;
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
        
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String metodo = propiedades.get("click").valor;
                   if(!metodo.equals("")){
                        metodo = metodo.replace("(", "").replace(")", "");
                        principal_cjs.ejecutarMetodo(metodo, 0, 0,template1);
                    }
                } catch (Exception ex) {
                }

                try {
                    lanzarClick();
                } catch (Exception ex) {
                }

                String rrrta=propiedades.get("ruta").valor.trim();
                if(!rrrta.equals("")){
                    try {
                        template1.buscarPagina(rrrta);
                    } catch (Exception ex) {
                    }
                }
                
            }
        });
        
    }
    
    
    public void setObservador(TablaSimboloG global,TablaSimboloG tabla, ArrayList<Nodo> sentencias, String tipo, Clase claseActual){
        switch(tipo){
            case "click":
                click = new Observador(global, tabla, sentencias,claseActual);
                break;
            case "modificado":        
                modificado = new Observador(global, tabla, sentencias,claseActual);
                break;
            case "listo":
                listo = new Observador(global, tabla, sentencias,claseActual);
                break;
        }
    }
    
    
    
    public void lanzarClick(){
        if(click!=null){
            principal_cjs.ejecutarMETODO1(click.global, click.tabla,click.sentencias, click.claseActual,template1);
        }
    }
    
    public void lanzarEditado(){
        if(modificado!=null){
            principal_cjs.ejecutarMETODO1(modificado.global, modificado.tabla,modificado.sentencias, modificado.claseActual,template1);
        }
    }
    
    public void lanzarFinalizado(){
        if(listo!=null){
            principal_cjs.ejecutarMETODO1(listo.global, listo.tabla,listo.sentencias, listo.claseActual,template1);
        }
    }
    
    
    public void cambiarGrupo(String grupo){
        grupo=grupo.trim();
        if(!grupo.equals("")){
            // si existe el grupo que quiero setear a este
            // sino existe entonces agrego este grupo
            if(!template1.lista_grupos.containsKey(grupo)){
                Lista lt = new Lista();
                template1.lista_grupos.put(grupo,lt);
            }
            // vere si el id existe en el grupo que tiene este mismo OBJETO
            String a_grupo=propiedades.get("grupo").valor.trim();
            if(!a_grupo.equals("")){ // si no es vacio el grupo de este componente
                // tengo que buscar el componente en la lista de grupos
                Lista lt= template1.lista_grupos.get(a_grupo);
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
        id=id.trim();
        if(!id.equals("")){
            setName(id);
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
                String txt=propiedades.get("$text").valor;
                try {
                    if(minuscula){
                        txt=txt.toLowerCase();
                    }else if(mayuscula){
                        txt=txt.toUpperCase();
                    }else if(capital_t){
                        txt=template1.toCapital_t(txt);
                    }
                } catch (Exception e) {}
                
                setText(txt);
                
            } catch (Exception e) {System.out.println(e.getMessage());}
            
        updateUI();
    }
    
    public void setAncho(){
        System.out.println("hare ancho en oboton");
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
    
    public boolean setFondo(){
        boolean flag=true;
        try {
            String fondo = propiedades.get("fondo").valor.trim();
            Color color=template1.meta_colores.obtenerColor(fondo);
            if(color!=null){
                setBackground(color);
                updateUI();
            }else{flag=false;} 
        } catch (Exception e) {flag=false;}
        updateUI();
        return flag;
    }
    
    public boolean setAlineado(){
        boolean flag=true;
       try{
            String alineado = propiedades.get("alineado").valor.trim();
           switch (alineado.toLowerCase()) {

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
                   flag=false;
                   break;
               //error

           }
        }catch(Exception e){flag=false;}
        updateUI();
        return flag;
    }
    
    
    public void setMayu_Minu_Capital(int num){
        switch (num) {
            case 1:
                // es para minusculas
                minuscula=true;
                mayuscula=false;
                capital_t=false;
                break;
            case 2:
                // es para mayusculas
                minuscula=false;
                mayuscula=true;
                capital_t=false;
                break;
            case 3:
                // es para capital-t
                minuscula=false;
                mayuscula=false;
                capital_t=true;
                break;
            default:
                break;
        }
    }
    /*
    @Override
    protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension arcs = new Dimension(cornerRadius, cornerRadius);
            int width = getWidth();
            int height = getHeight();
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color backgroundColor=null;

        backgroundColor=getBackground();
            //Draws the rounded panel with borders.
            if (backgroundColor != null) {
                graphics.setColor(backgroundColor);
            } else {
                graphics.setColor(getBackground());
            }
            graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height); //paint background
            graphics.setColor(getForeground());
            graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height); //paint border
        }
    */
}
