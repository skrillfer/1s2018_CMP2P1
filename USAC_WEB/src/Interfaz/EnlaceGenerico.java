/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

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
    public boolean mayuscula=false;
    public boolean minuscula=false;
    public boolean capital_t=false; 
    public Hashtable<String,Propiedad> propiedades;
  
    public EnlaceGenerico(Hashtable<String,Propiedad> propiedades) throws URISyntaxException {
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
                    txt=Template.toCapital_t(txt);
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
            Color color=Template.meta_colores.obtenerColor(fondo);
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
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // aqui deberia redireccionar a la nueva pagina
                System.out.println("click en enlace");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                if(!propiedades.get("ruta").valor.trim().isEmpty()){
                    setToolTipText(propiedades.get("ruta").valor.trim());
                }
            }
        });
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
