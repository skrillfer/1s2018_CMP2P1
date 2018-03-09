/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import CJS_Compilador.Clase;
import CJS_Compilador.TablaSimboloG;
import Estructuras.Nodo;
import Estructuras.Observador;
import Estructuras.Propiedad;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;



/**
 *
 * @author fernando
 */
public class AreaTextoGenerica extends JTextPane{
    public Observador click=null;
    public Observador modificado=null;
    public Observador listo=null;
    
    
    public boolean mayuscula=false;
    public boolean minuscula=false;
    public boolean capital_t=false; 
    
    private char LastkeyChar=0;
    
    public Hashtable<String,Propiedad> propiedades;
    Dimension tamPadre=null;

    public AreaTextoGenerica(Hashtable<String,Propiedad> propiedades,  Dimension tamPadre) {
        this.tamPadre=tamPadre;
        this.propiedades = propiedades;
        setPropiedades();
    }
    
    public void setPropiedades(){
        setOpaque(false);
        setEditable(true);
        setId();
        setGrupo();
        setAlineado();
        setTexto();
        setFondo();
        
        if (!propiedades.get("alto").valor.trim().equals("") || !propiedades.get("ancho").valor.trim().equals("")){
            if(!propiedades.get("alto").valor.trim().equals("")){
                setAlto();
            }else{
                setAncho();
            }
        }else{
            setPreferredSize(new Dimension(70, 90));  
        }
        
        
        
        /*if(getPreferredSize().height==21 || getPreferredSize().width==6){
            setPreferredSize(new Dimension(150, 150));
        }*/
        /*  
        try {
            if (getPreferredSize().width > tamPadre.width) {
                System.out.println(tamPadre.width);
                setPreferredSize(new Dimension(tamPadre.width - 15, getPreferredSize().height));
            }
        } catch (Exception e) {
        }*/
        
        /*
        try {
            if (propiedades.get("alto").valor.trim().equals("")) {
                String tamtex = propiedades.get("$text").valor;
                int lineas = tamtex.length() / (tamPadre.width / 2);
                if (lineas * 90 > getPreferredSize().height) {
                    setPreferredSize(new Dimension(getPreferredSize().width, lineas * 90));
                }
            }
        } catch (Exception e) {
        }*/
        
      

    

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
        
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    String metodo = propiedades.get("click").valor;
                    metodo = metodo.replace("(", "").replace(")", "");
                    Template.principal_cjs.ejecutarMetodo(metodo, 0, 0);
                } catch (Exception ex) {
                }

                try {
                    lanzarClick();
                } catch (Exception ex) {
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
            Template.principal_cjs.ejecutarMETODO1(click.global, click.tabla,click.sentencias, click.claseActual);
        }
    }
    
    public void lanzarEditado(){
        if(modificado!=null){
            Template.principal_cjs.ejecutarMETODO1(modificado.global, modificado.tabla,modificado.sentencias, modificado.claseActual);
        }
    }
    
    public void lanzarFinalizado(){
        if(listo!=null){
            Template.principal_cjs.ejecutarMETODO1(listo.global, listo.tabla,listo.sentencias, listo.claseActual);
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
            String alineado = propiedades.get("alineado").valor.trim();
            
            StyleContext context = new StyleContext();
            StyledDocument document = new DefaultStyledDocument(context);
            Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
            
            switch(alineado){
                case "izquierda":
                    StyleConstants.setAlignment(style, StyleConstants.ALIGN_LEFT);
                    document.insertString(document.getLength(), propiedades.get("$text").valor, style);
                    setDocument(document);
                    break;
                case "derecha":
                    StyleConstants.setAlignment(style, StyleConstants.ALIGN_RIGHT);
                    document.insertString(document.getLength(), propiedades.get("$text").valor, style);
                    setDocument(document);
                    break;
                case "centrado":
                    StyleConstants.setAlignment(style, StyleConstants.ALIGN_CENTER);
                    document.insertString(document.getLength(), propiedades.get("$text").valor, style);
                    setDocument(document);
                    break;
                case "justificado":
                    setPreferredSize(new Dimension(getPreferredSize().width,getPreferredSize().height));
                    StyleConstants.setAlignment(style, StyleConstants.ALIGN_JUSTIFIED);
                    document.insertString(document.getLength(), propiedades.get("$text").valor, style);
                    setDocument(document);    
                    break;
                default:
                    flag=false;
                    break;
            }
        } catch (Exception e) {flag=false;}
        updateUI();
        return flag;
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
                            txt=Template.toCapital_t(txt);
                        }
                } catch (Exception e) {}
                setText(txt);
            } catch (Exception e) {}
         updateUI();
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
}
