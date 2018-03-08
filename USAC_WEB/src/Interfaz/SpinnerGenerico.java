/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Estructuras.Propiedad;
import java.awt.Color;
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
    public Hashtable<String,Propiedad> propiedades;

    public SpinnerGenerico(Hashtable<String, Propiedad> propiedades) {
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
            Color color=Template.meta_colores.obtenerColor(fondo);
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
