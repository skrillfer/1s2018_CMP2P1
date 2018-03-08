/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Estructuras.*;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author fernando
 */
public class PanelGenerico extends JPanel{
    public Hashtable<String,Propiedad> propiedades;

    public PanelGenerico(Hashtable<String,Propiedad> propiedades) {
        this.propiedades=propiedades;
        setPropiedades();
    }
    
    
    public void setPropiedades(){
        
        setBorder(new EtchedBorder(EtchedBorder.RAISED));
        setId();
        setGrupo();
        setAlineado();
        setTexto();
        setFondo();
        setAlto();
        setAncho();
        
    }
    
    
    
    public boolean setId(){
        try {
                setName(propiedades.get("id").valor.trim());
                updateUI();
                return true;
            }catch(Exception ex){return false;}
        
    }
    
    public void setGrupo() {
        try {
            propiedades.get("grupo").valor = propiedades.get("grupo").valor.trim();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        updateUI();
    }

    public void setTexto(){
        try {
                setToolTipText(propiedades.get("$text").valor.trim());
            }catch(Exception ex){}
        updateUI();
    }
    
    public boolean setAncho(){
         try {
                setPreferredSize(new Dimension(Integer.valueOf(propiedades.get("ancho").valor.trim()), getPreferredSize().height));
                updateUI();
                return true;
            } catch (Exception e) {System.out.println(e.getMessage()); return false;}
        
    }
    
    public boolean setAlto(){
        try {
                setPreferredSize(new Dimension(getPreferredSize().width, Integer.valueOf(propiedades.get("alto").valor.trim())));
                updateUI();
                return true;
            } catch (Exception e) {System.out.println(e.getMessage()); return false;}
        
    }
    
    public boolean setFondo(){
        try {
            String fondo = propiedades.get("fondo").valor.trim();
            Color color=Template.meta_colores.obtenerColor(fondo);
            
            if(color!=null){
                setBackground(color);
                updateUI();
                return true;
            }
            updateUI();
            return false;
        } catch (Exception e) { return false;}
        
    }
    
    public boolean setAlineado(){
        boolean flag=true;
        try {
            String alineado = propiedades.get("alineado").valor.trim();
            switch(alineado){
                case "izquierda":
                    setLayout(new FlowLayout(FlowLayout.LEFT));
                    //setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                    break;
                case "derecha":
                    setLayout(new FlowLayout(FlowLayout.RIGHT));
                    //setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    break;
                case "centrado":
                    setLayout(new FlowLayout(FlowLayout.CENTER));
                    break;
                case "justificado":
                    setLayout(new FlowLayout(FlowLayout.LEADING));
                    break; 
                default:
                    flag=false;
                    
            }
        } catch (Exception e) {flag=false;}
        updateUI();
        return flag;
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
}
