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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
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
    public Hashtable<String,Propiedad> propiedades;
public  CJS principal_cjs=null;
    public Observador click=null;
    public Observador modificado=null;
    public Observador listo=null;
    public Template template1;
    public SpinnerGenerico(Hashtable<String, Propiedad> propiedades,CJS principal_cjs,Template template1) {
        this.template1=template1;
        this.principal_cjs=principal_cjs;
                
        this.propiedades = propiedades;
        setPropiedades();
        
        
    }
    
    public void setPropiedades(){
        
        setId();
        setGrupo();
        setAlineado();
        setTexto();
        setFondo();
        setAlto();
        setAncho();
        
        if(propiedades.get("alto").valor.trim().equals("") && propiedades.get("ancho").valor.trim().equals("")){
            if (getPreferredSize().width == 28 && getPreferredSize().height == 20) {
                setPreferredSize(new Dimension(45, 20));
            }
        }
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
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
            }
                
        });
              
    }
    
    public void setObservador(TablaSimboloG global, TablaSimboloG tabla, ArrayList<Nodo> sentencias, String tipo, Clase claseActual) {
        switch (tipo) {
            case "click":
                click = new Observador(global, tabla, sentencias, claseActual);
                break;
            case "modificado":
                modificado = new Observador(global, tabla, sentencias, claseActual);
                break;
            case "listo":
                listo = new Observador(global, tabla, sentencias, claseActual);
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
        // cuando cambia el id debe de sacarse de la hash de lista de componentens y
        // verificar si el nuevo id se puede cambiar
        id=id.trim();
        if(!id.equals("")){
            if(!template1.lista_componentes.containsKey(id)){
                // se saca el anterior y se mete el nuevo
                Componente cmp = template1.lista_componentes.get(getName());
                
                if(cmp!=null){
                    template1.lista_componentes.remove(id);
                    template1.lista_componentes.put(id, cmp);
                    setName(id);
                    propiedades.get("id").valor=getName();
                            
                }
            }
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
    
    public boolean setAlineado(){
        boolean flag=true;
        try {
            String alineado = propiedades.get("alineado").valor;
            SimpleAttributeSet attribs = new SimpleAttributeSet();

            JComponent editor = this.getEditor();
            JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor) editor;
            switch (alineado.toLowerCase()) {
                case "centrado":
                    spinnerEditor.getTextField().setHorizontalAlignment(JTextField.CENTER);
                    break;
                case "derecha":
                    spinnerEditor.getTextField().setHorizontalAlignment(JTextField.RIGHT);
                    break;
                case "izquierda":
                    spinnerEditor.getTextField().setHorizontalAlignment(JTextField.LEFT);
                    break;
                case "justificado":
                    spinnerEditor.getTextField().setHorizontalAlignment(JTextField.LEADING);
                    break;    
                default:
                    flag=false;
                    break;
            }
        } catch (Exception e) {
            flag=false;
        }
        updateUI();
        return flag;
    }
    
    public void setTexto(){
          //******************************************************************Seteando $text
            try {
                if(!propiedades.get("$text").valor.trim().isEmpty()){
                    int inicio = Integer.valueOf(propiedades.get("$text").valor.trim());
                    setValue(inicio);
                }
            } catch (Exception e) {}
        
    }
    
    public boolean setFondo(){
        boolean flag=true;
        try {
            String fondo = propiedades.get("fondo").valor.trim();
            Color color=template1.meta_colores.obtenerColor(fondo);
            if(color!=null){
                setBackground(color);
            }else{flag=false;}
        } catch (Exception e) {flag=false;}
        updateUI();
        return flag;
    }
    
    public void setAlto(){
        try {
                setPreferredSize(new Dimension(getPreferredSize().width, Integer.valueOf(propiedades.get("alto").valor.trim())));
            } catch (Exception e) {System.out.println(e.getMessage());}
        updateUI();
    }
    
    public void setAncho(){
        try {
                setPreferredSize(new Dimension(Integer.valueOf(propiedades.get("ancho").valor.trim()), getPreferredSize().height));
            } catch (Exception e) {System.out.println(e.getMessage());}
        updateUI();
    }
}
