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
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.font.TextAttribute;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author fernando
 */
public class EnlaceGenerico extends JLabel{
        public  CJS principal_cjs=null;
    public Observador click=null;
    public Observador modificado=null;
    public Observador listo=null;
    
    
    
    public boolean mayuscula=false;
    public boolean minuscula=false;
    public boolean capital_t=false; 
    public Hashtable<String,Propiedad> propiedades;
    public Template template1;
    public EnlaceGenerico(Hashtable<String,Propiedad> propiedades,CJS principal_cjs,Template template1) throws URISyntaxException {
        this.template1=template1;
        this.principal_cjs=principal_cjs;
                
        this.propiedades=propiedades;
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
        
        if(getPreferredSize().height==10 || getPreferredSize().width==34){
            setPreferredSize(new Dimension(85, 35));
        }
        
        setClick();
        setRuta();
        Font ft = getFont();
        
        Map atributes = ft.getAttributes();
        atributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        setFont(ft.deriveFont(atributes));
        setForeground(Color.BLUE);
        
        
        addMouseListener(new MouseAdapter() {
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
                
                String rrrta=propiedades.get("ruta").valor.trim();
                if(!rrrta.equals("")){
                    try {
                        //VentanaPrincipal.agregarNuevaPagina("nuevo_"+VentanaPrincipal.controlTab1.getComponentCount());
                        template1.buscarPagina(rrrta);
                        //VentanaPrincipal.lanzarPagina(rrrta);
                    } catch (Exception ex) {
                    }
                }
                
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                if(!propiedades.get("ruta").valor.trim().isEmpty()){
                    setToolTipText(propiedades.get("ruta").valor.trim());
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
            
            String txt="enlace";
            if(!propiedades.get("$text").valor.trim().isEmpty()){
                txt=propiedades.get("$text").valor;
            }else{
                propiedades.get("$text").valor="enlace";
            }
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
            //setText("<html><a href=\"http://www.google.com/\">"+txt+"</a></html>");

        } catch (Exception e) {System.out.println(e.getMessage());}
        updateUI();
    }
    
    
    public boolean setAlineado(){
        boolean flag=true;
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
                        flag=false;
                        //error
                        
                }
            }catch(Exception e){flag=false;}
        updateUI();
        return flag;
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
    
    public void setClick(){
        
    }

    public void setRuta(){
        
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
}
