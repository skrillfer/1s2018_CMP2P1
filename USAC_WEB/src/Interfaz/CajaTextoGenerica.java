/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Estructuras.Propiedad;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Hashtable;
import javax.swing.JTextField;

/**
 *
 * @author fernando
 */
public class CajaTextoGenerica extends JTextField {
    public boolean mayuscula=false;
    public boolean minuscula=false;
    public boolean capital_t=false; 
    Dimension tamPadre=null;
    private char LastkeyChar=0;
    public Hashtable<String, Propiedad> propiedades;

    public CajaTextoGenerica(Hashtable<String, Propiedad> propiedades) {
        this.propiedades = propiedades;
        setPropiedades();
    }

    public void setPropiedades() {
        
        setEditable(true);
        
        setId();
        setGrupo();
        setTexto();
        setFondo();
        setAlineado();
        setAlto();
        setAncho();

        /*
        try {
            String txt = propiedades.get("$text").valor;
            txt = txt.replaceAll("\r", " ").replaceAll("\n", " ").replace("\t", " ");
            setText(txt);
        } catch (Exception e) {}
        */
        if(propiedades.get("alto").valor.trim().equals("") && propiedades.get("ancho").valor.trim().equals("")){
            if(getPreferredSize().height<=19 || getPreferredSize().width<=4){
                setPreferredSize(new Dimension(150, 30));
            }
        }
        
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                
                char keyChar = e.getKeyChar();
                capital_t=true;
                if(minuscula){
                    e.setKeyChar(Character.toLowerCase(keyChar));
                }else if(mayuscula){
                    e.setKeyChar(Character.toUpperCase(keyChar));
                }else if(capital_t){
                    if(LastkeyChar=='\n' || LastkeyChar==' '||  LastkeyChar=='\t' || getText().length()==1){
                        e.setKeyChar(Character.toUpperCase(keyChar));
                    }
                }
                LastkeyChar=keyChar;
            }
        });
    }
    
    
    public void setId(){
        try {
                setName(propiedades.get("id").valor.trim());
            }catch(Exception ex){}
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
                txt = txt.replaceAll("\r", " ").replaceAll("\n", " ").replace("\t", " ");
                try {
                        if(minuscula){
                            txt=txt.toLowerCase();
                        }else if(mayuscula){
                            txt=txt.toUpperCase();
                        }else if(capital_t){
                            txt=Template.toCapital_t(txt);
                        }
                } catch (Exception e) {}
                setText(txt);
            }catch(Exception ex){}
        updateUI();
    }
    
     public void setAncho(){
         try {
                setPreferredSize(new Dimension(Integer.valueOf(propiedades.get("ancho").valor.trim()), getPreferredSize().height));
            } catch (Exception e) {setPreferredSize(new Dimension(getPreferredSize().width,getPreferredSize().height));}
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
            Color color=Template.meta_colores.obtenerColor(fondo);
            if(color!=null){
                //setForeground(color);
                setBackground(color);
            }else{flag=false;}
        } catch (Exception e) {flag=false;}
        updateUI();
        return flag;
    }
    
    public boolean setAlineado(){
        boolean flag=true;
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
                    case "justificado":
                        setHorizontalAlignment(JTextField.LEADING);
                        break;    
                    default:
                        flag=false;
                        break;
                }
        }catch(Exception e){ flag=false;}
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
