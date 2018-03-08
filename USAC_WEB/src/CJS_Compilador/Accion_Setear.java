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
        if(raiz.hijos.size()==2 && raiz.hijos.get(0).nombre.equals("id_cmp")){//I_D:w PtoSetElemento:h   |   I_D:w PtoObservador:h
            Nodo id  = raiz.hijos.get(0);
            Nodo pto = raiz.hijos.get(1);
            opL= new OperacionesARL(global, tabla);
            ResultadoG componente = opL.ejecutar(id);
            
            System.out.println("quiero setear lo que hay en var:"+id.valor);
            System.out.println(componente.valor);
            if(componente!=null){
                System.out.println("COMPONENTE NO ES NULL");
                switch(pto.nombre){
                case "pto_elemento":
                    setElemento(pto.hijos.get(0), pto.hijos.get(1), (Componente)componente.valor, componente.tipo,((Componente)componente.valor).id);
                    break;
                case "pto_observador":
                    break;    
                }
            }
            
        }else if(raiz.hijos.size()==2){//   Accion_Obtener:x PtoSetElemento:h    |   Accion_Obtener:x PtoObservador:h
            Nodo Accion_Obtener = raiz.hijos.get(0);
            Nodo pto = raiz.hijos.get(1);
            Nodo pto_Obtener = Accion_Obtener.hijos.get(0);
            Nodo id = pto_Obtener.hijos.get(0);
            if(id.nombre.equals("string")){
                id.nombre="id_componente";
                opL= new OperacionesARL(global, tabla);
                ResultadoG componente = opL.ejecutar(id);
                if(componente!=null){
                    switch(pto.nombre){
                        case "pto_elemento":
                            setElemento(pto.hijos.get(0), pto.hijos.get(1), (Componente)componente.valor, componente.tipo,((Componente)componente.valor).id);
                            break;
                        case "pto_observador":
                            
                            break;    
                    }
                }
            }
        }
        return metodoActual;
    }
    
    public boolean setElemento(Nodo nodo1, Nodo nodo2,Componente comp, String tipo_comp, String ID){
        opL= new OperacionesARL(global, tabla);
        ResultadoG    pro = opL.ejecutar(nodo1);
        ResultadoG    val = opL.ejecutar(nodo2);
    
        
        if(pro!=null && val!=null){
            
            if(pro.tipo.equals("string") && (val.tipo.equals("string") || val.tipo.equals("number"))){
                String propiedad= (String)pro.valor;
                propiedad=propiedad.trim();
                Hashtable<String,Propiedad> propiedades=null;
                Double num=null;
                
                TerminarSetear(tipo_comp, propiedad, pro, val, comp, nodo1, nodo2);
                
                for (Componente componente :  Template.cmps_repetidos.get(ID.trim()).getLista()) {
                    TerminarSetear(componente.tipo, propiedad, pro, val, componente, nodo1, nodo2);
                }
                
            }else{
                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, "Ambos Valores string,string deben ser String en SETELEMENTO");
            }
        }
        
        
        
        
        return false;
    }
    
    
    public void TerminarSetear(String tipo_comp, String propiedad,ResultadoG pro, ResultadoG val,Componente comp,Nodo nodo1, Nodo nodo2){
    
        Hashtable<String,Propiedad> propiedades=null;
        Double num=null;
        switch (tipo_comp) {
            case "panel":
                PanelGenerico nuevopanel = (PanelGenerico) comp.objeto;
                propiedades = nuevopanel.propiedades;

                switch (propiedad) {
                    case "id":
                        break;
                    case "grupo":
                        break;
                    case "alto":

                        if (val.tipo.equals("string")) {
                            try {
                                num = Double.valueOf(((String) val.valor).trim());
                                nuevopanel.propiedades.get("alto").valor = String.valueOf(num.intValue());
                                nuevopanel.setAlto();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alto necesita un numero");
                            }
                        } else if (val.tipo.equals("number")) {
                            try {
                                num = (Double) val.valor;
                                nuevopanel.propiedades.get("alto").valor = String.valueOf(num.intValue());
                                nuevopanel.setAlto();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alto necesita un numero");
                            }
                        }
                        break;
                    case "ancho":
                        if (val.tipo.equals("string")) {
                            try {
                                num = Double.valueOf(((String) val.valor).trim());
                                nuevopanel.propiedades.get("ancho").valor = String.valueOf(num.intValue());
                                nuevopanel.setAncho();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ancho necesita un numero");
                            }
                        } else if (val.tipo.equals("number")) {
                            try {
                                num = (Double) val.valor;
                                nuevopanel.propiedades.get("ancho").valor = String.valueOf(num.intValue());
                                nuevopanel.setAncho();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ancho necesita un numero");
                            }
                        }
                    case "ruta":
                        if (val.tipo.equals("string")) {
                            nuevopanel.propiedades.get("ruta").valor = (String) val.valor;
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ruta necesita una CADENA");
                        }
                        break;
                    case "click":
                        if (val.tipo.equals("string")) {
                            nuevopanel.propiedades.get("click").valor = (String) val.valor;
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Click necesita una CADENA");
                        }
                        break;
                    case "alineado":
                        if (val.tipo.equals("string")) {
                            nuevopanel.propiedades.get("alineado").valor = (String) val.valor;
                            boolean paso = nuevopanel.setAlineado();
                            if (!paso) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alineado, valor " + (String) val.valor + " invalida");
                            }
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alineado necesita una CADENA");
                        }
                        break;
                    case "texto":
                        if (val.tipo.equals("string") || val.tipo.equals("number")) {
                            try {
                                nuevopanel.propiedades.get("$text").valor = String.valueOf(val.valor);
                                nuevopanel.setTexto();
                            } catch (Exception e) {
                            }
                        }
                        break;
                    case "fondo":
                        if (val.tipo.equals("string")) {
                            nuevopanel.propiedades.get("fondo").valor = (String) val.valor;
                            boolean paso = nuevopanel.setFondo();
                            if (!paso) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Fondo, Color " + (String) val.valor + " invalido");
                            }
                        }
                        break;
                    default:
                        Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad: " + propiedad + " es invalida");
                }
                break;
            case "texto":
                TextoGenerico texxto = (TextoGenerico) comp.objeto;
                propiedades = texxto.propiedades;
                switch (propiedad) {
                    case "id":
                        break;
                    case "grupo":
                        break;
                    case "alto":
                        if (val.tipo.equals("string")) {
                            try {
                                num = Double.valueOf(((String) val.valor).trim());
                                texxto.propiedades.get("alto").valor = String.valueOf(num.intValue());
                                texxto.setAlto();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alto necesita un numero");
                            }
                        } else if (val.tipo.equals("number")) {
                            try {
                                num = (Double) val.valor;
                                texxto.propiedades.get("alto").valor = String.valueOf(num.intValue());
                                texxto.setAlto();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alto necesita un numero");
                            }
                        }
                        break;
                    case "ancho":
                        if (val.tipo.equals("string")) {
                            try {
                                num = Double.valueOf(((String) val.valor).trim());
                                texxto.propiedades.get("ancho").valor = String.valueOf(num.intValue());
                                texxto.setAncho();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ancho necesita un numero");
                            }
                        } else if (val.tipo.equals("number")) {
                            try {
                                num = (Double) val.valor;
                                texxto.propiedades.get("ancho").valor = String.valueOf(num.intValue());
                                texxto.setAncho();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ancho necesita un numero");
                            }
                        }
                    case "ruta":
                        if (val.tipo.equals("string")) {
                            texxto.propiedades.get("ruta").valor = (String) val.valor;
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ruta necesita una CADENA");
                        }
                        break;
                    case "click":
                        System.out.println(" me van a violar");
                        if (val.tipo.equals("string")) {
                            texxto.propiedades.get("click").valor = (String) val.valor;
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Click necesita una CADENA");
                        }
                        break;
                    case "alineado":
                        if (val.tipo.equals("string")) {
                            texxto.propiedades.get("alineado").valor = (String) val.valor;
                            boolean paso = texxto.setAlineado();
                            if (!paso) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alineado, valor " + (String) val.valor + " invalida");
                            }
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alineado necesita una CADENA");
                        }
                        break;
                    case "texto":
                        if (val.tipo.equals("string") || val.tipo.equals("number")) {
                            try {
                                texxto.propiedades.get("$text").valor = String.valueOf(val.valor);
                                texxto.setTexto();
                            } catch (Exception e) {
                            }
                        }
                        break;
                    case "fondo":
                        if (val.tipo.equals("string")) {
                            texxto.propiedades.get("fondo").valor = (String) val.valor;
                            boolean paso = texxto.setFondo();
                            if (!paso) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Fondo, Color " + (String) val.valor + " invalido");
                            }
                        }
                        break;
                    default:
                        Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad: " + propiedad + " es invalida");
                }
                break;
            case "boton":
                BotonGenerico btn = (BotonGenerico) comp.objeto;
                propiedades = btn.propiedades;
                switch (propiedad) {
                    case "id":
                        break;
                    case "grupo":
                        break;
                    case "alto":
                        if (val.tipo.equals("string")) {
                            try {
                                num = Double.valueOf(((String) val.valor).trim());
                                btn.propiedades.get("alto").valor = String.valueOf(num.intValue());
                                btn.setAlto();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alto necesita un numero");
                            }
                        } else if (val.tipo.equals("number")) {
                            try {
                                num = (Double) val.valor;
                                btn.propiedades.get("alto").valor = String.valueOf(num.intValue());
                                btn.setAlto();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alto necesita un numero");
                            }
                        }
                        break;
                    case "ancho":
                        if (val.tipo.equals("string")) {
                            try {
                                num = Double.valueOf(((String) val.valor).trim());
                                btn.propiedades.get("ancho").valor = String.valueOf(num.intValue());
                                btn.setAncho();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ancho necesita un numero");
                            }
                        } else if (val.tipo.equals("number")) {
                            try {
                                num = (Double) val.valor;
                                btn.propiedades.get("ancho").valor = String.valueOf(num.intValue());
                                btn.setAncho();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ancho necesita un numero");
                            }
                        }
                    case "ruta":
                        if (val.tipo.equals("string")) {
                            btn.propiedades.get("ruta").valor = (String) val.valor;
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ruta necesita una CADENA");
                        }
                        break;
                    case "click":
                        if (val.tipo.equals("string")) {
                            //btn.setClick();
                            btn.propiedades.get("click").valor = (String) val.valor;
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Click necesita una CADENA");
                        }
                        break;
                    case "alineado":
                        if (val.tipo.equals("string")) {
                            btn.propiedades.get("alineado").valor = (String) val.valor;
                            boolean paso = btn.setAlineado();
                            if (!paso) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alineado, valor " + (String) val.valor + " invalida");
                            }
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alineado necesita una CADENA");
                        }
                        break;
                    case "texto":
                        if (val.tipo.equals("string") || val.tipo.equals("number")) {
                            try {
                                btn.propiedades.get("$text").valor = String.valueOf(val.valor);
                                btn.setTexto();
                            } catch (Exception e) {
                            }
                        }
                        break;
                    case "fondo":
                        if (val.tipo.equals("string")) {
                            btn.propiedades.get("fondo").valor = ((String) val.valor).trim();
                            boolean paso = btn.setFondo();
                            if (!paso) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Fondo, Color " + (String) val.valor + " invalido");
                            }
                        }
                        break;
                    default:
                        Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad: " + propiedad + " es invalida");
                }
                break;
            case "cajatexto":
                CajaTextoGenerica ctxt = (CajaTextoGenerica) comp.objeto;
                propiedades = ctxt.propiedades;
                switch (propiedad) {
                    case "id":
                        break;
                    case "grupo":
                        break;
                    case "alto":
                        if (val.tipo.equals("string")) {
                            try {
                                num = Double.valueOf(((String) val.valor).trim());
                                ctxt.propiedades.get("alto").valor = String.valueOf(num.intValue());
                                ctxt.setAlto();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alto necesita un numero");
                            }
                        } else if (val.tipo.equals("number")) {
                            try {
                                num = (Double) val.valor;
                                ctxt.propiedades.get("alto").valor = String.valueOf(num.intValue());
                                ctxt.setAlto();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alto necesita un numero");
                            }
                        }
                        break;
                    case "ancho":
                        if (val.tipo.equals("string")) {
                            try {
                                num = Double.valueOf(((String) val.valor).trim());
                                ctxt.propiedades.get("ancho").valor = String.valueOf(num.intValue());
                                ctxt.setAncho();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ancho necesita un numero");
                            }
                        } else if (val.tipo.equals("number")) {
                            try {
                                num = (Double) val.valor;
                                ctxt.propiedades.get("ancho").valor = String.valueOf(num.intValue());
                                ctxt.setAncho();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ancho necesita un numero");
                            }
                        }
                    case "ruta":
                        if (val.tipo.equals("string")) {
                            ctxt.propiedades.get("ruta").valor = (String) val.valor;
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ruta necesita una CADENA");
                        }
                        break;
                    case "click":
                        if (val.tipo.equals("string")) {
                            ctxt.propiedades.get("click").valor = (String) val.valor;
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Click necesita una CADENA");
                        }
                        break;
                    case "alineado":
                        if (val.tipo.equals("string")) {
                            ctxt.propiedades.get("alineado").valor = (String) val.valor;
                            boolean paso = ctxt.setAlineado();
                            if (!paso) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alineado, valor " + (String) val.valor + " invalida");
                            }
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alineado necesita una CADENA");
                        }
                        break;
                    case "texto":
                        if (val.tipo.equals("string") || val.tipo.equals("number")) {
                            try {
                                ctxt.propiedades.get("$text").valor = String.valueOf(val.valor);
                                ctxt.setTexto();
                            } catch (Exception e) {
                            }
                        }
                        break;
                    case "fondo":
                        if (val.tipo.equals("string")) {
                            ctxt.propiedades.get("fondo").valor = (String) val.valor;
                            boolean paso = ctxt.setFondo();
                            if (!paso) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Fondo, Color " + (String) val.valor + " invalido");
                            }
                        }
                        break;
                    default:
                        Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad: " + propiedad + " es invalida");
                }
                break;
            case "areatexto":
                //VER BIEN
                AreaTextoGenerica txt_a = (AreaTextoGenerica) comp.objeto;
                propiedades = txt_a.propiedades;
                switch (propiedad) {
                    case "id":
                        break;
                    case "grupo":
                        break;
                    case "alto":
                        if (val.tipo.equals("string")) {
                            try {
                                num = Double.valueOf(((String) val.valor).trim());
                                txt_a.propiedades.get("alto").valor = String.valueOf(num.intValue());
                                txt_a.setAlto();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alto necesita un numero");
                            }
                        } else if (val.tipo.equals("number")) {
                            try {
                                num = (Double) val.valor;
                                txt_a.propiedades.get("alto").valor = String.valueOf(num.intValue());
                                txt_a.setAlto();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alto necesita un numero");
                            }
                        }
                        break;
                    case "ancho":
                        if (val.tipo.equals("string")) {
                            try {
                                num = Double.valueOf(((String) val.valor).trim());
                                txt_a.propiedades.get("ancho").valor = String.valueOf(num.intValue());
                                txt_a.setAncho();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ancho necesita un numero");
                            }
                        } else if (val.tipo.equals("number")) {
                            try {
                                num = (Double) val.valor;
                                txt_a.propiedades.get("ancho").valor = String.valueOf(num.intValue());
                                txt_a.setAncho();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ancho necesita un numero");
                            }
                        }
                    case "ruta":
                        if (val.tipo.equals("string")) {
                            txt_a.propiedades.get("ruta").valor = (String) val.valor;
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ruta necesita una CADENA");
                        }
                        break;
                    case "click":
                        if (val.tipo.equals("string")) {
                            txt_a.propiedades.get("click").valor = (String) val.valor;
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Click necesita una CADENA");
                        }
                        break;
                    case "alineado":
                        if (val.tipo.equals("string")) {
                            txt_a.propiedades.get("alineado").valor = (String) val.valor;
                            boolean paso = txt_a.setAlineado();
                            if (!paso) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alineado, valor " + (String) val.valor + " invalida");
                            }
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alineado necesita una CADENA");
                        }
                        break;
                    case "texto":
                        if (val.tipo.equals("string") || val.tipo.equals("number")) {
                            try {
                                txt_a.propiedades.get("$text").valor = String.valueOf(val.valor);
                                txt_a.setTexto();
                            } catch (Exception e) {
                            }
                        }
                        break;
                    case "fondo":
                        if (val.tipo.equals("string")) {
                            txt_a.propiedades.get("fondo").valor = (String) val.valor;
                            boolean paso = txt_a.setFondo();
                            if (!paso) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Fondo, Color " + (String) val.valor + " invalido");
                            }
                        }
                        break;
                    default:
                        Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad: " + propiedad + " es invalida");
                }
                break;
            case "spinner":
                SpinnerGenerico sp = (SpinnerGenerico) comp.objeto;
                propiedades = sp.propiedades;
                switch (propiedad) {
                    case "id":
                        break;
                    case "grupo":
                        break;
                    case "alto":
                        if (val.tipo.equals("string")) {
                            try {
                                num = Double.valueOf(((String) val.valor).trim());
                                sp.propiedades.get("alto").valor = String.valueOf(num.intValue());
                                sp.setAlto();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alto necesita un numero");
                            }
                        } else if (val.tipo.equals("number")) {
                            try {
                                num = (Double) val.valor;
                                sp.propiedades.get("alto").valor = String.valueOf(num.intValue());
                                sp.setAlto();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alto necesita un numero");
                            }
                        }
                        break;
                    case "ancho":
                        if (val.tipo.equals("string")) {
                            try {
                                num = Double.valueOf(((String) val.valor).trim());
                                sp.propiedades.get("ancho").valor = String.valueOf(num.intValue());
                                sp.setAncho();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ancho necesita un numero");
                            }
                        } else if (val.tipo.equals("number")) {
                            try {
                                num = (Double) val.valor;
                                sp.propiedades.get("ancho").valor = String.valueOf(num.intValue());
                                sp.setAncho();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ancho necesita un numero");
                            }
                        }
                    case "ruta":
                        if (val.tipo.equals("string")) {
                            sp.propiedades.get("ruta").valor = (String) val.valor;
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ruta necesita una CADENA");
                        }
                        break;
                    case "click":
                        if (val.tipo.equals("string")) {
                            sp.propiedades.get("click").valor = (String) val.valor;
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Click necesita una CADENA");
                        }
                        break;
                    case "alineado":
                        if (val.tipo.equals("string")) {
                            sp.propiedades.get("alineado").valor = (String) val.valor;
                            boolean paso = sp.setAlineado();
                            if (!paso) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alineado, valor " + (String) val.valor + " invalida");
                            }
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alineado necesita una CADENA");
                        }
                        break;
                    case "texto":
                        if (val.tipo.equals("string") || val.tipo.equals("number")) {
                            try {
                                sp.propiedades.get("$text").valor = String.valueOf(val.valor);
                                sp.setTexto();
                            } catch (Exception e) {
                            }
                        }
                        break;
                    case "fondo":
                        if (val.tipo.equals("string")) {
                            sp.propiedades.get("fondo").valor = (String) val.valor;
                            boolean paso = sp.setFondo();
                            if (!paso) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Fondo, Color " + (String) val.valor + " invalido");
                            }
                        }
                        break;
                    default:
                        Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad: " + propiedad + " es invalida");
                }
                break;
            case "enlace":
                EnlaceGenerico enlac = (EnlaceGenerico) comp.objeto;
                propiedades = enlac.propiedades;
                switch (propiedad) {
                    case "id":
                        break;
                    case "grupo":
                        break;
                    case "alto":
                        if (val.tipo.equals("string")) {
                            try {
                                num = Double.valueOf(((String) val.valor).trim());
                                enlac.propiedades.get("alto").valor = String.valueOf(num.intValue());
                                enlac.setAlto();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alto necesita un numero");
                            }
                        } else if (val.tipo.equals("number")) {
                            try {
                                num = (Double) val.valor;
                                enlac.propiedades.get("alto").valor = String.valueOf(num.intValue());
                                enlac.setAlto();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alto necesita un numero");
                            }
                        }
                        break;
                    case "ancho":
                        if (val.tipo.equals("string")) {
                            try {
                                num = Double.valueOf(((String) val.valor).trim());
                                enlac.propiedades.get("ancho").valor = String.valueOf(num.intValue());
                                enlac.setAncho();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ancho necesita un numero");
                            }
                        } else if (val.tipo.equals("number")) {
                            try {
                                num = (Double) val.valor;
                                enlac.propiedades.get("ancho").valor = String.valueOf(num.intValue());
                                enlac.setAncho();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ancho necesita un numero");
                            }
                        }
                    case "ruta":
                        if (val.tipo.equals("string")) {
                            enlac.propiedades.get("ruta").valor = (String) val.valor;
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ruta necesita una CADENA");
                        }
                        break;
                    case "click":
                        if (val.tipo.equals("string")) {
                            enlac.propiedades.get("click").valor = (String) val.valor;
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Click necesita una CADENA");
                        }
                        break;
                    case "alineado":
                        if (val.tipo.equals("string")) {
                            enlac.propiedades.get("alineado").valor = (String) val.valor;
                            boolean paso = enlac.setAlineado();
                            if (!paso) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alineado, valor " + (String) val.valor + " invalida");
                            }
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alineado necesita una CADENA");
                        }
                        break;
                    case "texto":
                        if (val.tipo.equals("string") || val.tipo.equals("number")) {
                            try {
                                enlac.propiedades.get("$text").valor = String.valueOf(val.valor);
                                enlac.setTexto();
                            } catch (Exception e) {
                            }
                        }
                        break;
                    case "fondo":
                        if (val.tipo.equals("string")) {
                            enlac.propiedades.get("fondo").valor = (String) val.valor;
                            boolean paso = enlac.setFondo();
                            if (!paso) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Fondo, Color " + (String) val.valor + " invalido");
                            }
                        }
                        break;
                    default:
                        Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad: " + propiedad + " es invalida");
                }
                break;
            case "tabla":
                TablaGenerica2 tabla1 = (TablaGenerica2) comp.objeto;
                propiedades = tabla1.propiedadesTabla;
                switch (propiedad) {
                    case "id":
                        break;
                    case "grupo":
                        break;
                    case "alto":
                        if (val.tipo.equals("string")) {
                            try {
                                num = Double.valueOf(((String) val.valor).trim());
                                tabla1.propiedadesTabla.get("alto").valor = String.valueOf(num.intValue());
                                tabla1.setAlto();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alto necesita un numero");
                            }
                        } else if (val.tipo.equals("number")) {
                            try {
                                num = (Double) val.valor;
                                tabla1.propiedadesTabla.get("alto").valor = String.valueOf(num.intValue());
                                tabla1.setAlto();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alto necesita un numero");
                            }
                        }
                        break;
                    case "ancho":
                        if (val.tipo.equals("string")) {
                            try {
                                num = Double.valueOf(((String) val.valor).trim());
                                tabla1.propiedadesTabla.get("ancho").valor = String.valueOf(num.intValue());
                                tabla1.setAncho();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ancho necesita un numero");
                            }
                        } else if (val.tipo.equals("number")) {
                            try {
                                num = (Double) val.valor;
                                tabla1.propiedadesTabla.get("ancho").valor = String.valueOf(num.intValue());
                                tabla1.setAncho();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ancho necesita un numero");
                            }
                        }
                    case "ruta":
                        if (val.tipo.equals("string")) {
                            tabla1.propiedadesTabla.get("ruta").valor = (String) val.valor;
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ruta necesita una CADENA");
                        }
                        break;
                    case "click":
                        if (val.tipo.equals("string")) {
                            tabla1.propiedadesTabla.get("click").valor = (String) val.valor;
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Click necesita una CADENA");
                        }
                        break;
                    case "alineado":
                        if (val.tipo.equals("string")) {
                            tabla1.propiedadesTabla.get("alineado").valor = (String) val.valor;
                            boolean paso = tabla1.setAlineado();
                            if (!paso) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alineado, valor " + (String) val.valor + " invalida");
                            }
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alineado necesita una CADENA");
                        }
                        break;
                    case "texto":
                        Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Texto no APLICA");
                        break;
                    case "fondo":
                        if (val.tipo.equals("string")) {
                            tabla1.propiedadesTabla.get("fondo").valor = (String) val.valor;
                            boolean paso = tabla1.setFondo();
                            if (!paso) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Fondo, Color " + (String) val.valor + " invalido");
                            }
                        }
                        break;
                    default:
                        Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad: " + propiedad + " es invalida");
                }
                break;
            case "imagen":
                ImagenGenerica img = (ImagenGenerica) comp.objeto;
                propiedades = img.propiedades;
                switch (propiedad) {
                    case "id":
                        break;
                    case "grupo":
                        break;
                    case "alto":
                        if (val.tipo.equals("string")) {
                            try {
                                num = Double.valueOf(((String) val.valor).trim());
                                img.propiedades.get("alto").valor = String.valueOf(num.intValue());
                                img.setAlto();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alto necesita un numero");
                            }
                        } else if (val.tipo.equals("number")) {
                            try {
                                num = (Double) val.valor;
                                img.propiedades.get("alto").valor = String.valueOf(num.intValue());
                                img.setAlto();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alto necesita un numero");
                            }
                        }
                        break;
                    case "ancho":
                        if (val.tipo.equals("string")) {
                            try {
                                num = Double.valueOf(((String) val.valor).trim());
                                img.propiedades.get("ancho").valor = String.valueOf(num.intValue());
                                img.setAncho();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ancho necesita un numero");
                            }
                        } else if (val.tipo.equals("number")) {
                            try {
                                num = (Double) val.valor;
                                img.propiedades.get("ancho").valor = String.valueOf(num.intValue());
                                img.setAncho();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ancho necesita un numero");
                            }
                        }
                    case "ruta":
                        if (val.tipo.equals("string")) {
                            img.propiedades.get("ruta").valor = (String) val.valor;
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ruta necesita una CADENA");
                        }
                        break;
                    case "click":
                        if (val.tipo.equals("string")) {
                            img.propiedades.get("click").valor = (String) val.valor;
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Click necesita una CADENA");
                        }
                        break;
                    case "alineado":
                        if (val.tipo.equals("string")) {
                            img.propiedades.get("alineado").valor = (String) val.valor;
                            boolean paso = img.setAlineado();
                            if (!paso) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alineado, valor " + (String) val.valor + " invalida");
                            }
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alineado necesita una CADENA");
                        }
                        break;
                    case "texto":
                        if (val.tipo.equals("string") || val.tipo.equals("number")) {
                            try {
                                img.propiedades.get("$text").valor = String.valueOf(val.valor);
                                img.setTexto();
                            } catch (Exception e) {
                            }
                        }
                        break;
                    case "fondo":
                        if (val.tipo.equals("string")) {
                            img.propiedades.get("fondo").valor = (String) val.valor;
                            boolean paso = img.setFondo();
                            if (!paso) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Fondo, Color " + (String) val.valor + " invalido");
                            }
                        }
                        break;
                    default:
                        Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad: " + propiedad + " es invalida");
                }
                break;
            case "cajaopciones":
                CajaOpcionesGenerica combo = (CajaOpcionesGenerica) comp.objeto;
                propiedades = combo.propiedades;
                switch (propiedad) {
                    case "id":
                        break;
                    case "grupo":
                        break;
                    case "alto":
                        if (val.tipo.equals("string")) {
                            try {
                                num = Double.valueOf(((String) val.valor).trim());
                                combo.propiedades.get("alto").valor = String.valueOf(num.intValue());
                                combo.setAlto();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alto necesita un numero");
                            }
                        } else if (val.tipo.equals("number")) {
                            try {
                                num = (Double) val.valor;
                                combo.propiedades.get("alto").valor = String.valueOf(num.intValue());
                                combo.setAlto();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alto necesita un numero");
                            }
                        }
                        break;
                    case "ancho":
                        if (val.tipo.equals("string")) {
                            try {
                                num = Double.valueOf(((String) val.valor).trim());
                                combo.propiedades.get("ancho").valor = String.valueOf(num.intValue());
                                combo.setAncho();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ancho necesita un numero");
                            }
                        } else if (val.tipo.equals("number")) {
                            try {
                                num = (Double) val.valor;
                                combo.propiedades.get("ancho").valor = String.valueOf(num.intValue());
                                combo.setAncho();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ancho necesita un numero");
                            }
                        }
                    case "ruta":
                        if (val.tipo.equals("string")) {
                            combo.propiedades.get("ruta").valor = (String) val.valor;
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ruta necesita una CADENA");
                        }
                        break;
                    case "click":
                        if (val.tipo.equals("string")) {
                            combo.propiedades.get("click").valor = (String) val.valor;
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Click necesita una CADENA");
                        }
                        break;
                    case "alineado":
                        if (val.tipo.equals("string")) {
                            combo.propiedades.get("alineado").valor = (String) val.valor;
                            boolean paso = combo.setAlineado();
                            if (!paso) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alineado, valor " + (String) val.valor + " invalida");
                            }
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alineado necesita una CADENA");
                        }
                        break;
                    case "texto":
                        if (val.tipo.equals("string") || val.tipo.equals("number")) {
                            try {
                                combo.propiedades.get("$text").valor = String.valueOf(val.valor);
                                combo.setTexto();
                            } catch (Exception e) {
                            }
                        }
                        break;
                    case "fondo":
                        if (val.tipo.equals("string")) {
                            combo.propiedades.get("fondo").valor = (String) val.valor;
                            boolean paso = combo.setFondo();
                            if (!paso) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Fondo, Color " + (String) val.valor + " invalido");
                            }
                        }
                        break;
                    default:
                        Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad: " + propiedad + " es invalida");
                }
                break;
            case "opcion":
                OpcionGenerica opcion = (OpcionGenerica) comp.objeto;
                propiedades = opcion.propiedades;
                switch (propiedad) {
                    case "id":
                        break;
                    case "grupo":
                        break;
                    case "alto":
                        if (val.tipo.equals("string")) {
                            try {
                                num = Double.valueOf(((String) val.valor).trim());
                                opcion.propiedades.get("alto").valor = String.valueOf(num.intValue());
                                opcion.setAlto();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alto necesita un numero");
                            }
                        } else if (val.tipo.equals("number")) {
                            try {
                                num = (Double) val.valor;
                                opcion.propiedades.get("alto").valor = String.valueOf(num.intValue());
                                opcion.setAlto();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alto necesita un numero");
                            }
                        }
                        break;
                    case "ancho":
                        if (val.tipo.equals("string")) {
                            try {
                                num = Double.valueOf(((String) val.valor).trim());
                                opcion.propiedades.get("ancho").valor = String.valueOf(num.intValue());
                                opcion.setAncho();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ancho necesita un numero");
                            }
                        } else if (val.tipo.equals("number")) {
                            try {
                                num = (Double) val.valor;
                                opcion.propiedades.get("ancho").valor = String.valueOf(num.intValue());
                                opcion.setAncho();
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ancho necesita un numero");
                            }
                        }
                    case "ruta":
                        if (val.tipo.equals("string")) {
                            opcion.propiedades.get("ruta").valor = (String) val.valor;
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Ruta necesita una CADENA");
                        }
                        break;
                    case "click":
                        if (val.tipo.equals("string")) {
                            opcion.propiedades.get("click").valor = (String) val.valor;
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Click necesita una CADENA");
                        }
                        break;
                    case "alineado":
                        if (val.tipo.equals("string")) {
                            opcion.propiedades.get("alineado").valor = (String) val.valor;
                            boolean paso = opcion.setAlineado();
                            if (!paso) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alineado, valor " + (String) val.valor + " invalida");
                            }
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Alineado necesita una CADENA");
                        }
                        break;
                    case "texto":
                        if (val.tipo.equals("string") || val.tipo.equals("number")) {
                            try {
                                opcion.propiedades.get("$text").valor = String.valueOf(val.valor);
                                opcion.setTexto();
                            } catch (Exception e) {
                            }
                        }
                        break;
                    case "fondo":
                        if (val.tipo.equals("string")) {
                            opcion.propiedades.get("fondo").valor = (String) val.valor;
                            boolean paso = opcion.setFondo();
                            if (!paso) {
                                Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad Fondo, Color " + (String) val.valor + " invalido");
                            }
                        }
                        break;
                    default:
                        Template.reporteError_CJS.agregar("Error Semantico", nodo1.linea, nodo1.columna, tipo_comp + " Propiedad: " + propiedad + " es invalida");
                }
                break;
        }
    }

    
    public void setObservador(Nodo evento, Nodo funcion,Componente comp, String tipo_comp){
        opL= new OperacionesARL(global, tabla);
        ResultadoG event = opL.ejecutar(evento);
        if(event!=null){
            if(event.tipo.equals("string")){
                String evt_string = (String)event.valor;
                evt_string=evt_string.toLowerCase().trim();
                switch(evt_string){
                    case "listo":
                        break;
                    case "modificado":
                        break;    
                    case "clic":
                    case "click":
                    case "cliqueado":
                        switch(tipo_comp){
                            case "boton":
                                
                                break;
                            case "enlace":
                                break;
                            case "imagen":
                                break;  
                            case "cajaopciones":
                                break;  
                            default:
                                break;
                        }
                        break;      
                }
            }
        }
    }
}
