/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CJS_Compilador;

import CJS_Compilador.OperacionesARL.OperacionesARL;
import Estructuras.Nodo;
import Estructuras.Propiedad;
import Interfaz.AreaTextoGenerica;
import Interfaz.BotonGenerico;
import Interfaz.CajaOpcionesGenerica;
import Interfaz.CajaTextoGenerica;
import Interfaz.Componente;
import Interfaz.EnlaceGenerico;
import Interfaz.ImagenGenerica;
import Interfaz.OpcionGenerica;
import Interfaz.PanelGenerico;
import Interfaz.SpinnerGenerico;
import Interfaz.TablaGenerica2;
import Interfaz.Template;
import static Interfaz.Template.addComponente;
import static Interfaz.Template.meta_colores;
import Interfaz.TextoGenerico;
import java.util.Hashtable;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTextPane;

/**
 *
 * @author fernando
 */
public class Accion_Setear extends Compilador{

    
  
    
    @Override
    public Metodo ejecutar(Nodo raiz) {
        if(raiz.hijos.size()==2){//I_D:w PtoSetElemento:h   |   I_D:w PtoObservador:h
            Nodo id  = raiz.hijos.get(0);
            Nodo pto = raiz.hijos.get(1);
            opL= new OperacionesARL(global, tabla);
            ResultadoG componente = opL.ejecutar(id);
            if(componente!=null){
                
                switch(pto.nombre){
                case "pto_elemento":
                    setElemento(pto.hijos.get(0), pto.hijos.get(1), (Componente)componente.valor, componente.tipo);
                    break;
                case "pto_observador":
                    break;    
                }
            }
            
        }else{
        
        }
        return metodoActual;
    }
    
    public boolean setElemento(Nodo nodo1, Nodo nodo2,Componente comp, String tipo_comp){
        opL= new OperacionesARL(global, tabla);
        ResultadoG pro = opL.ejecutar(nodo1);
        ResultadoG val = opL.ejecutar(nodo2);
        if(pro!=null && val!=null){
            if(pro.tipo.equals("string") && (val.tipo.equals("string") || val.tipo.equals("number"))){
                String propiedad= (String)pro.valor;
                propiedad=propiedad.trim();
                Hashtable<String,Propiedad> propiedades=null;
                Double num=null;
                switch(tipo_comp){
                    case "panel":
                        PanelGenerico nuevopanel = (PanelGenerico)comp.objeto;
                        propiedades=nuevopanel.propiedades;
                        
                        switch(propiedad){
                            case "id":
                                break;
                            case "grupo":
                                break;
                            case "alto":
                                
                                if(val.tipo.equals("string")){
                                    try {
                                        num= Double.valueOf(((String)val.valor).trim());
                                        nuevopanel.propiedades.get("alto").valor=String.valueOf(num.intValue());
                                        nuevopanel.setAlto();
                                    } catch (Exception e) {
                                        Template.reporteError_CJS.agregar("Error Semantico",nodo1.linea, nodo1.columna, tipo_comp+" Propiedad Alto necesita un numero");
                                    }
                                }else if(val.tipo.equals("number")){
                                    try {
                                        num= (Double)val.valor;
                                        nuevopanel.propiedades.get("alto").valor=String.valueOf(num.intValue());
                                        nuevopanel.setAlto();
                                    } catch (Exception e) {
                                        Template.reporteError_CJS.agregar("Error Semantico",nodo1.linea, nodo1.columna, tipo_comp+" Propiedad Alto necesita un numero");
                                    }
                                }
                                break;
                            case "ancho":
                                if(val.tipo.equals("string")){
                                    try {
                                        num= Double.valueOf(((String)val.valor).trim());
                                        nuevopanel.propiedades.get("ancho").valor=String.valueOf(num.intValue());
                                        nuevopanel.setAncho();
                                    } catch (Exception e) {
                                        Template.reporteError_CJS.agregar("Error Semantico",nodo1.linea, nodo1.columna, tipo_comp+" Propiedad Ancho necesita un numero");
                                    }
                                }else if(val.tipo.equals("number")){
                                    try {
                                        num= (Double)val.valor;
                                        nuevopanel.propiedades.get("ancho").valor=String.valueOf(num.intValue());
                                        nuevopanel.setAncho();
                                    } catch (Exception e) {
                                        Template.reporteError_CJS.agregar("Error Semantico",nodo1.linea, nodo1.columna, tipo_comp+" Propiedad Ancho necesita un numero");
                                    }
                                }
                            case "ruta":
                                if(val.tipo.equals("string")){                                    
                                        nuevopanel.propiedades.get("ruta").valor=(String)val.valor;
                                }else{
                                    Template.reporteError_CJS.agregar("Error Semantico",nodo1.linea, nodo1.columna, tipo_comp+" Propiedad Ruta necesita una CADENA");
                                }
                                break;
                            case "click":
                                if(val.tipo.equals("string")){                                    
                                        nuevopanel.propiedades.get("click").valor=(String)val.valor;
                                }else{
                                    Template.reporteError_CJS.agregar("Error Semantico",nodo1.linea, nodo1.columna, tipo_comp+" Propiedad Click necesita una CADENA");
                                }
                                break;
                            case "alineado":
                                if(val.tipo.equals("string")){
                                        nuevopanel.propiedades.get("alineado").valor=(String)val.valor;
                                        boolean paso=nuevopanel.setAlineado();
                                        if(!paso){
                                            Template.reporteError_CJS.agregar("Error Semantico",nodo1.linea, nodo1.columna, tipo_comp+" Propiedad Alineado, valor "+(String)val.valor+" invalida");
                                        }
                                }else{
                                    Template.reporteError_CJS.agregar("Error Semantico",nodo1.linea, nodo1.columna, tipo_comp+" Propiedad Alineado necesita una CADENA");
                                }
                                break;
                            case "texto":
                                if(val.tipo.equals("string") || val.tipo.equals("number")){
                                    try {
                                        nuevopanel.propiedades.get("$text").valor=String.valueOf(val.valor);
                                        nuevopanel.setTexto();
                                    } catch (Exception e) {
                                    }
                                }
                                break; 
                            case "fondo":
                                if(val.tipo.equals("string")){
                                    nuevopanel.propiedades.get("fondo").valor=(String)val.valor;
                                    boolean paso=nuevopanel.setFondo();
                                    if(!paso){
                                        Template.reporteError_CJS.agregar("Error Semantico",nodo1.linea, nodo1.columna, tipo_comp+" Propiedad Fondo, Color "+(String)val.valor+" invalido");
                                    }
                                }
                                break; 
                            default:
                                Template.reporteError_CJS.agregar("Error Semantico",nodo1.linea, nodo1.columna, tipo_comp + " Propiedad: "+propiedad +" es invalida");
                        }
                        
                        
                        
                        break;
                    case "texto":
                        TextoGenerico texxto =(TextoGenerico)comp.objeto;
                        propiedades=texxto.propiedades;
                         switch(propiedad){
                            case "id":
                                break;
                            case "grupo":
                                break;
                            case "alto":
                                if(val.tipo.equals("string")){
                                    try {
                                        num= Double.valueOf(((String)val.valor).trim());
                                        texxto.propiedades.get("alto").valor=String.valueOf(num.intValue());
                                        texxto.setAlto();
                                    } catch (Exception e) {
                                        Template.reporteError_CJS.agregar("Error Semantico",nodo1.linea, nodo1.columna, tipo_comp+" Propiedad Alto necesita un numero");
                                    }
                                }else if(val.tipo.equals("number")){
                                    try {
                                        num= (Double)val.valor;
                                        texxto.propiedades.get("alto").valor=String.valueOf(num.intValue());
                                        texxto.setAlto();
                                    } catch (Exception e) {
                                        Template.reporteError_CJS.agregar("Error Semantico",nodo1.linea, nodo1.columna, tipo_comp+" Propiedad Alto necesita un numero");
                                    }
                                }
                                break;
                            case "ancho":
                                if(val.tipo.equals("string")){
                                    try {
                                        num= Double.valueOf(((String)val.valor).trim());
                                        texxto.propiedades.get("ancho").valor=String.valueOf(num.intValue());
                                        texxto.setAncho();
                                    } catch (Exception e) {
                                        Template.reporteError_CJS.agregar("Error Semantico",nodo1.linea, nodo1.columna, tipo_comp+" Propiedad Ancho necesita un numero");
                                    }
                                }else if(val.tipo.equals("number")){
                                    try {
                                        num= (Double)val.valor;
                                        texxto.propiedades.get("ancho").valor=String.valueOf(num.intValue());
                                        texxto.setAncho();
                                    } catch (Exception e) {
                                        Template.reporteError_CJS.agregar("Error Semantico",nodo1.linea, nodo1.columna, tipo_comp+" Propiedad Ancho necesita un numero");
                                    }
                                }
                            case "ruta":
                                if(val.tipo.equals("string")){                                    
                                        texxto.propiedades.get("ruta").valor=(String)val.valor;
                                }else{
                                    Template.reporteError_CJS.agregar("Error Semantico",nodo1.linea, nodo1.columna, tipo_comp+" Propiedad Ruta necesita una CADENA");
                                }
                                break;
                            case "click":
                                if(val.tipo.equals("string")){                                    
                                        texxto.propiedades.get("click").valor=(String)val.valor;
                                }else{
                                    Template.reporteError_CJS.agregar("Error Semantico",nodo1.linea, nodo1.columna, tipo_comp+" Propiedad Click necesita una CADENA");
                                }
                                break;
                            case "alineado":
                                if(val.tipo.equals("string")){
                                        texxto.propiedades.get("alineado").valor=(String)val.valor;
                                        boolean paso=texxto.setAlineado();
                                        if(!paso){
                                            Template.reporteError_CJS.agregar("Error Semantico",nodo1.linea, nodo1.columna, tipo_comp+" Propiedad Alineado, valor "+(String)val.valor+" invalida");
                                        }
                                }else{
                                    Template.reporteError_CJS.agregar("Error Semantico",nodo1.linea, nodo1.columna, tipo_comp+" Propiedad Alineado necesita una CADENA");
                                }
                                break;
                            case "texto":
                                if(val.tipo.equals("string") || val.tipo.equals("number")){
                                    try {
                                        nuevopanel.propiedades.get("$text").valor=String.valueOf(val.valor);
                                        nuevopanel.setTexto();
                                    } catch (Exception e) {
                                    }
                                }
                                break; 
                            case "fondo":
                                if(val.tipo.equals("string")){
                                    nuevopanel.propiedades.get("fondo").valor=(String)val.valor;
                                    boolean paso=nuevopanel.setFondo();
                                    if(!paso){
                                        Template.reporteError_CJS.agregar("Error Semantico",nodo1.linea, nodo1.columna, tipo_comp+" Propiedad Fondo, Color "+(String)val.valor+" invalido");
                                    }
                                }
                                break; 
                            default:
                                Template.reporteError_CJS.agregar("Error Semantico",nodo1.linea, nodo1.columna, tipo_comp + " Propiedad: "+propiedad +" es invalida");
                        }
                        
                        break;
                    case "boton":
                        BotonGenerico btn = (BotonGenerico)comp.objeto;
                        propiedades=btn.propiedades;
                        break;
                    case "caja_texto":
                        CajaTextoGenerica txt = (CajaTextoGenerica)comp.objeto;
                        propiedades=txt.propiedades;
                        break;
                    case "texto_a":
                        //VER BIEN
                        AreaTextoGenerica txt_a = (AreaTextoGenerica)comp.objeto;
                        propiedades=txt_a.propiedades;
                        break;
                    case "spinner":
                        SpinnerGenerico sp =(SpinnerGenerico)comp.objeto;
                        propiedades=sp.propiedades;
                        break;
                    case "enlace":
                        EnlaceGenerico enlac = (EnlaceGenerico)comp.objeto;
                        propiedades=enlac.propiedades;
                        break;
                    case "tabla":
                        TablaGenerica2 tabla1 = (TablaGenerica2)comp.objeto;
                        propiedades=tabla1.propiedadesTabla;
                        break;
                    case "imagen":
                        ImagenGenerica img = (ImagenGenerica)comp.objeto;
                        propiedades=img.propiedades;
                        break;
                    case "caja":
                        CajaOpcionesGenerica combo = (CajaOpcionesGenerica)comp.objeto;
                        propiedades=combo.propiedades;
                        break;
                    case "opcion":
                        OpcionGenerica opcion = (OpcionGenerica)comp.objeto;
                        propiedades=opcion.propiedades;
                        break;    
                }
            }else{
                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, "Ambos Valores string,string deben ser String en SETELEMENTO");
            }
        }
        return false;
    }
    
    
    public void SetearElemento(){
    
    }
}
