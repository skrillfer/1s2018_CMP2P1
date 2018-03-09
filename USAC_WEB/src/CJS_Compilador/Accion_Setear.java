/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CJS_Compilador;

import CJS_Compilador.OperacionesARL.OperacionesARL;
import Estructuras.Nodo;
import Estructuras.NodoCSS;
import Estructuras.Propiedad;
import Interfaz.AreaTextoGenerica;
import Interfaz.BotonGenerico;
import Interfaz.CajaOpcionesGenerica;
import Interfaz.CajaTextoGenerica;
import Interfaz.Componente;
import Interfaz.EnlaceGenerico;
import Interfaz.ImagenGenerica;
import Interfaz.Lista;
import Interfaz.OpcionGenerica;
import Interfaz.PanelGenerico;
import Interfaz.SpinnerGenerico;
import Interfaz.TablaGenerica2;
import Interfaz.Template;
import static Interfaz.Template.addComponente;
import static Interfaz.Template.addError;
import static Interfaz.Template.lista_componentes;
import static Interfaz.Template.meta_colores;
import Interfaz.TextoGenerico;
import java.awt.Color;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;

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
            
            if(componente!=null){
                System.out.println(((Componente)componente.valor).id);
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
                
                int stop=0;
                
                try {
                    stop=Template.cmps_repetidos.get(ID.trim()).getLista().size();
                } catch (Exception e) {
                }
                
                
                for (int i = 0; i < stop; i++) {
                    Componente componente=Template.cmps_repetidos.get(ID.trim()).getLista().get(i);
                    TerminarSetear(componente.tipo, propiedad, pro, val, componente, nodo1, nodo2);
                    try {
                        stop=Template.cmps_repetidos.get(ID.trim()).getLista().size();
                    } catch (Exception e) {
                    }
                    
                    System.out.println(" --- STOP:"+stop);
                }
                System.out.println("*******************");
                
                /*
                for (Componente componente :  Template.cmps_repetidos.get(ID.trim()).getLista()) {
                    TerminarSetear(componente.tipo, propiedad, pro, val, componente, nodo1, nodo2);
                }*/
                
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
                System.out.println(" boton ::::: "+btn.getName() + " ::::: "+propiedad);
                propiedades = btn.propiedades;
                switch (propiedad) {
                    case "id":
                        //1. Ver si existe ese ID en la tabla HASH, ya que es el primero
                        //System.out.println("Viendo si eXISTE:"+(String)val.valor);
                        
                        if(val.tipo.equals("string")){
                            String value=((String)val.valor).trim();
                            String actual=btn.propiedades.get("id").valor.trim();
                            
                            if(Template.lista_componentes.containsKey(actual)){
                                System.out.println("2. Existe ID");
                                //2. Si existe entonces, voy a traer repetidos con ese ID
                                
                                Componente inicial = Template.lista_componentes.get(actual);//inicial
                                
                                btn.propiedades.get("id").valor=value;
                                btn.setName(value);
                                
                                inicial.id=value;
                                
                                Template.lista_componentes.put(value, inicial);
                                
                                Template.lista_componentes.remove(actual);
                                
                                
                                Lista lt=Template.cmps_repetidos.get(actual);//repetidos
                                lt.cambiarId(value);
                                
                                Template.cmps_repetidos.put(value, lt);
                                Template.cmps_repetidos.remove(actual);
                                
                                ArrayList<NodoCSS> lista_estilos =null;
                                if(Template.lista_estilos_id.containsKey(value)){
                                    lista_estilos= Template.lista_estilos_id.get(value);
                                }
                                
                                
                                //3. Cambio el ID para cada componente
                                //4. Aplico CCSS para cada Componente 
                                
                                if(lista_estilos!=null){
                                    System.out.println(",.,.,.,,.,.,.,.,.,.,.,...,.,.,.,.,.,.,.,.,.,");
                                    
                                    //System.out.println("TAM LISTA ESTILOS:"+lista_estilos.size());
                                    for (NodoCSS estilo : lista_estilos) {
                                        System.out.println("APLICO ESTILO: "+estilo.valor);
                                        System.out.println("1Aestilos size:"+estilo.hijos.size());
                                        aplicarCcss(estilo, inicial,false);
                                        System.out.println("2Destilos size:"+estilo.hijos.size());
                                    }
                                    
                                    System.out.println("LISTA REPETIDA:"+lt.size());
                                    for (Componente componente : lt.getLista()) {
                                        System.out.println("COMPONENTE:"+componente.id);
                                        String jjeje=((JComponent)componente.objeto).getName();
                                        System.out.println(" $# --> "+jjeje);
                                        //5. recorrer la lista CCSS para ese ID
                                        //System.out.println("TAM LISTA ESTILOS:"+lista_estilos.size());
                                        
                                        for (int i = 0; i < lista_estilos.size(); i++) {
                                            NodoCSS estilo1 = lista_estilos.get(i);
                                            System.out.println("APLICO ESTILO: "+estilo1.valor);
                                            System.out.println("1Aestilos size:"+estilo1.hijos.size());
                                            aplicarCcss(estilo1, componente,true);
                                            System.out.println("2Destilos size:"+estilo1.hijos.size());
                                        }
                                        
                                    }
                                    System.out.println("TERMINEEEEEEEEEEEEEEEEEEEEEE");
                                }
                                
                            }
                        }
                        break;
                    case "grupo":
                        if (val.tipo.equals("string")) {
                            String value=((String)val.valor).trim();
                            String actual=btn.propiedades.get("grupo").valor.trim();
                            System.out.println(" VOY A SETEAR GRUPO");
                            if(Template.lista_grupos.containsKey(actual)){
                                System.out.println("EXISTE EL GRUPO:" + actual);
                                Lista lt=Template.lista_grupos.get(actual);// componentes que pertences a un grupo
                                
                                if(!Template.lista_grupos.containsKey(value)){
                                    Template.lista_grupos.put(value,new Lista());
                                }
                                Template.lista_grupos.get(value).getLista().add(comp);
                                
                                try {
                                    lt.removeListaGrupo(comp);
                                    System.out.println("HE REMOVIDO: "+comp.id);
                                } catch (Exception e) {}
                                
                                ArrayList<NodoCSS> lista_estilos =null;
                                if(Template.lista_estilos_grupo.containsKey(value)){
                                    System.out.println(" LISTA DE GRUPOS CONTIENE A:"+value);
                                    lista_estilos= Template.lista_estilos_grupo.get(value);
                                }
                                //System.out.println(lista_estilos.size());
                                if(lista_estilos!=null){
                                    //System.out.println(" cantidadEstilos de  "+value + " es " +lista_estilos.size());
                                    for (int i = 0; i < lista_estilos.size(); i++) {
                                        NodoCSS nod = lista_estilos.get(i);
                                        NodoCSS nnn = new NodoCSS("identificador","", nod.linea,nod.columna, 1000);
                                        nnn.hijos=nod.hijos;
                                       
                                        aplicarCcss(nnn, comp, true);
                                    }
                                }
                            }
                        }
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
                            System.out.println(" fondo === " +paso);
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
    
    
    
    public void aplicarCcss(NodoCSS raiz, Componente componente, boolean pasada){
        
        CSS_Compilador.OperacionesARL opl = new CSS_Compilador.OperacionesARL();
        for (NodoCSS nodo : raiz.hijos) {
            System.out.println(" <#####> "+ nodo.nombre);
            switch(nodo.nombre){
                case "alineado":
                    setAlineado(raiz, nodo, opl,componente,pasada);
                    break;
                case "texto":
                    System.out.println("Aplicare TEXTO");
                    setTexto(raiz, nodo, opl,componente,pasada);
                    break;    
                case "formato":
                    if(raiz.nombre.equals("identificador")){
                        //Componente cmp = lista_componentes.get(raiz.valor.trim());
                        Componente cmp = null;
                        if (!pasada) {
                            cmp = lista_componentes.get(raiz.valor.trim());
                        }
                        if (cmp==null){
                            if (componente!=null){
                                cmp=componente;
                            }else{
                                break;
                            }
                        }
                           
                        NodoCSS valores= nodo.hijos.get(0);
                        for (NodoCSS valor : valores.hijos) {
                            ResultadoG res = opl.ejecutar(valor);
                            if(res==null)
                                res= new ResultadoG("ninguno", "");
                            
                            switch (res.tipo) {
                                case "capital-t":
                                    switch (cmp.tipo) {
                                        case "boton":
                                            BotonGenerico btn = (BotonGenerico) cmp.objeto;
                                            btn.setMayu_Minu_Capital(3);
                                            btn.setTexto();
                                            
                                            break;
                                        case "enlace":
                                            EnlaceGenerico enl = (EnlaceGenerico) cmp.objeto;
                                            enl.setMayu_Minu_Capital(3);
                                            enl.setTexto();
                                            
                                            break;   
                                        case "texto":
                                            TextoGenerico txt = (TextoGenerico) cmp.objeto;
                                            txt.setMayu_Minu_Capital(3);
                                            txt.setTexto();
                                            
                                            break;       
                                        case "areatexto":
                                            AreaTextoGenerica atxt = (AreaTextoGenerica) cmp.objeto;
                                            atxt.setMayu_Minu_Capital(3);
                                            atxt.setTexto();
                                            
                                            break;  
                                        case "cajatexto":    
                                            CajaTextoGenerica ctxt = (CajaTextoGenerica) cmp.objeto;
                                            ctxt.setMayu_Minu_Capital(3);
                                            ctxt.setTexto();
                                            
                                            break;
                                        case "opcion":    
                                            OpcionGenerica opc = (OpcionGenerica) cmp.objeto;
                                            opc.setMayu_Minu_Capital(3);
                                            opc.setTexto();
                                            
                                            break;
                                        case "cajaopciones":    
                                            CajaOpcionesGenerica c_opc = (CajaOpcionesGenerica) cmp.objeto;
                                            c_opc.setMayu_Minu_Capital(3);
                                            c_opc.setTexto();
                                            
                                            break;    
                                            
                                    }
                                    break;
                                case "mayuscula":
                                    switch (cmp.tipo) {
                                        case "boton":
                                            BotonGenerico btn = (BotonGenerico) cmp.objeto;
                                            btn.setMayu_Minu_Capital(2);
                                            btn.setTexto();
                                            break;
                                        case "enlace":
                                            EnlaceGenerico enl = (EnlaceGenerico) cmp.objeto;
                                            enl.setMayu_Minu_Capital(2);
                                            enl.setTexto();
                                            
                                        case "texto":
                                            TextoGenerico txt = (TextoGenerico) cmp.objeto;
                                            txt.setMayu_Minu_Capital(2);
                                            txt.setTexto();
                                                
                                            break; 
                                        case "areatexto":
                                            AreaTextoGenerica atxt = (AreaTextoGenerica) cmp.objeto;
                                            atxt.setMayu_Minu_Capital(2);
                                            atxt.setTexto();
                                                
                                            break;     
                                        case "cajatexto":    
                                            CajaTextoGenerica ctxt = (CajaTextoGenerica) cmp.objeto;
                                            ctxt.setMayu_Minu_Capital(2);
                                            ctxt.setTexto();
                                                
                                            break;  
                                        case "opcion":
                                            OpcionGenerica opc = (OpcionGenerica) cmp.objeto;
                                            opc.setMayu_Minu_Capital(2);
                                            opc.setTexto();
                                            
                                            break;
                                        case "cajaopciones":
                                            CajaOpcionesGenerica c_opc = (CajaOpcionesGenerica) cmp.objeto;
                                            c_opc.setMayu_Minu_Capital(2);
                                            c_opc.setTexto();
                                            
                                            break;   
                                    }
                                    break;
                                case "minuscula":
                                    switch (cmp.tipo) {
                                        case "boton":
                                            BotonGenerico btn = (BotonGenerico) cmp.objeto;
                                            btn.setMayu_Minu_Capital(1);
                                            btn.setTexto();
                                            break;
                                        case "enlace":
                                            EnlaceGenerico enl = (EnlaceGenerico) cmp.objeto;
                                            enl.setMayu_Minu_Capital(1);
                                            enl.setTexto();
                                            break; 
                                        case "texto":
                                            TextoGenerico txt = (TextoGenerico) cmp.objeto;
                                            txt.setMayu_Minu_Capital(1);
                                            txt.setTexto();
                                            break;    
                                        case "areatexto":
                                            AreaTextoGenerica atxt = (AreaTextoGenerica) cmp.objeto;
                                            atxt.setMayu_Minu_Capital(1);
                                            atxt.setTexto();
                                            break; 
                                        case "cajatexto":      
                                            CajaTextoGenerica ctxt = (CajaTextoGenerica) cmp.objeto;
                                            ctxt.setMayu_Minu_Capital(1);
                                            ctxt.setTexto();
                                            break; 
                                        case "opcion":
                                            OpcionGenerica opc = (OpcionGenerica) cmp.objeto;
                                            opc.setMayu_Minu_Capital(1);
                                            opc.setTexto();
                                            
                                            break;
                                        case "cajaopciones":
                                            CajaOpcionesGenerica c_opc = (CajaOpcionesGenerica) cmp.objeto;
                                            c_opc.setMayu_Minu_Capital(1);
                                            c_opc.setTexto();
                                            
                                            break;         
                                    }
                                    break;
                                case "cursiva":
                                    switch (cmp.tipo) {
                                        case "boton":
                                        case "enlace":
                                        case "texto": 
                                        case "areatexto":
                                        case "cajatexto":
                                        case "opcion":
                                        case "cajaopciones":    
                                            JComponent btn = (JComponent) cmp.objeto;
                                            Font ft =null;
                                            if(btn.getFont().isBold()){
                                                ft=new Font(btn.getFont().getName(),Font.ITALIC+Font.BOLD,btn.getFont().getSize());
                                            }else{
                                                ft=new Font(btn.getFont().getName(),Font.ITALIC,btn.getFont().getSize());
                                            }
                                            try {
                                                 Map atributes = ft.getAttributes();
                                                if (cmp.tipo.equals("enlace")) {
                                                    atributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                                                    
                                                }//btn.setFont(ft);
                                                btn.setFont(ft.deriveFont(atributes));
                                            } catch (Exception e) {}
                                            
                                            break;
                                    }
                                    break;
                                case "negrilla":
                                    switch (cmp.tipo) {
                                        case "boton":
                                        case "enlace":  
                                        case "texto":    
                                        case "areatexto":   
                                        case "cajatexto":   
                                        case "opcion":
                                        case "cajaopciones":    
                                            JComponent btn = (JComponent) cmp.objeto;
                                            Font ft =null;
                                            if(btn.getFont().isItalic()){
                                                ft=new Font(btn.getFont().getName(),Font.ITALIC+Font.BOLD,btn.getFont().getSize());
                                            }else{
                                                ft=new Font(btn.getFont().getName(),Font.BOLD,btn.getFont().getSize());
                                            }
                                            try {
                                                Map atributes = ft.getAttributes();
                                                if (cmp.tipo.equals("enlace")) {
                                                    atributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                                                }//btn.setFont(ft);
                                                btn.setFont(ft.deriveFont(atributes));
                                                
                                            } catch (Exception e) {}
                                            
                                            break;
                                    }
                                    break;
                                default:
                                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, alineado solo acepta: izquierda,derecha,centrado,justificado", "Lenguaje CCSS");

                            }   
                        }
                    }
                    
                    break;   
                case "letra":
                    setLetra(raiz, nodo, opl,componente,pasada);
                    break; 
                case "tamtex":
                    setTamtex(raiz, nodo, opl,componente,pasada);
                    break;    
                case "fondoelemento":
                    setFondoElemento(raiz, nodo, opl,componente,pasada);
                    break;    
                case "autoredimension":
                    // aqui no se que putas hacer
                    break;    
                case "visible":
                    setVisible(raiz, nodo, opl,componente,pasada);
                    break;        
                case "borde":
                    setBorde(raiz, nodo, opl,componente,pasada);
                    break;
                case "opaque":
                    setOpaque(raiz, nodo, opl,componente,pasada);
                    break;
                case "colortext":
                    setColortext(raiz, nodo, opl,componente,pasada);
                    break;
            }
        }
    }
    
    public void setAlineado(NodoCSS raiz, NodoCSS nodo, CSS_Compilador.OperacionesARL opl, Componente porGrupo,boolean pasada){
        if (raiz.nombre.equals("identificador")) {
            Componente cmp=null;
            if(!pasada){
                cmp = lista_componentes.get(raiz.valor.trim());
            }
            
            if (cmp == null) {
                if(porGrupo!=null){
                    cmp=porGrupo;
                }else{
                    cmp = new Componente("null", "null", new Object(), null);
                }
            }
            
            ResultadoG res = opl.ejecutar(nodo.hijos.get(0));
            if (res == null) {
                res = new ResultadoG("null", null);
            }
            boolean do_alineado = false;
            switch (res.tipo) {
                case "izquierda":
                case "derecha":
                case "justificado":
                case "centrado":    
                    do_alineado = true;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, alineado solo acepta: izquierda,derecha,centrado,justificado", "Lenguaje CCSS");

            }
            switch (cmp.tipo) {
                case "boton":
                    BotonGenerico btn = (BotonGenerico) cmp.objeto;
                    if (do_alineado) {
                        btn.propiedades.get("alineado").valor = res.tipo;
                        btn.setAlineado();
                        System.out.println("se alineo a " + res.tipo);
                    }
                    break;
                case "enlace":
                    EnlaceGenerico enl = (EnlaceGenerico) cmp.objeto;
                    if (do_alineado) {
                        enl.propiedades.get("alineado").valor = res.tipo;
                        enl.setAlineado();
                    }
                    break;
                case "imagen":
                    ImagenGenerica img = (ImagenGenerica) cmp.objeto;
                    if (do_alineado) {
                        img.propiedades.get("alineado").valor = res.tipo;
                        img.setAlineado();
                    }
                    break;
                case "texto":
                    TextoGenerico txt = (TextoGenerico) cmp.objeto;
                    if (do_alineado) {
                        txt.propiedades.get("alineado").valor = res.tipo;
                        txt.setAlineado();
                    }
                    break;    
                case "areatexto":    
                    AreaTextoGenerica atxt = (AreaTextoGenerica) cmp.objeto;
                    if (do_alineado) {
                        atxt.propiedades.get("alineado").valor = res.tipo;
                        atxt.setAlineado();
                    }
                    break;    
                case "cajatexto":    
                    CajaTextoGenerica ctxt = (CajaTextoGenerica) cmp.objeto;
                    if (do_alineado) {
                        ctxt.propiedades.get("alineado").valor = res.tipo;
                        ctxt.setAlineado();
                    }
                    break;    
                case "panel":      
                    PanelGenerico panel = (PanelGenerico) cmp.objeto;
                    if (do_alineado) {
                        panel.propiedades.get("alineado").valor = res.tipo;
                        panel.setAlineado();
                    }
                    break; 
                case "opcion":      
                    OpcionGenerica opc = (OpcionGenerica) cmp.objeto;
                    if (do_alineado) {
                        opc.propiedades.get("alineado").valor = res.tipo;
                        opc.setAlineado();
                    }
                    break;    
                case "cajaopciones":      
                    CajaOpcionesGenerica c_opc = (CajaOpcionesGenerica) cmp.objeto;
                    if (do_alineado) {
                        c_opc.propiedades.get("alineado").valor = res.tipo;
                        c_opc.setAlineado();
                    }
                    break;       
                case "spinner":      
                    SpinnerGenerico spiin = (SpinnerGenerico) cmp.objeto;
                    if (do_alineado) {
                        spiin.propiedades.get("alineado").valor = res.tipo;
                        spiin.setAlineado();
                    }
                    break;      
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, alineado solo No aplica a "+cmp.tipo, "Lenguaje CCSS");
                    break;
            }
        }
    }
    
    public void setTexto(NodoCSS raiz, NodoCSS nodo, CSS_Compilador.OperacionesARL opl, Componente porGrupo,boolean pasada){
        if (raiz.nombre.equals("identificador")) {
            Componente cmp=null;
            
            if(!pasada){
                cmp = lista_componentes.get(raiz.valor.trim());
            }
            
            //System.out.println("SIE ESTOY PUTO"+cmp.id);
            
            if (cmp == null){
                if(porGrupo!=null){
                    cmp=porGrupo;
                }else{
                    cmp = new Componente("null", "null", new Object(),null);
                }
            }
            

            ResultadoG res = opl.ejecutar(nodo.hijos.get(0));
            if (res == null)
                res = new ResultadoG("ninguno", null);
            
            boolean do_texto = false;
            switch (res.tipo) {
                case "string":
                    do_texto = true;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, texto solo acepta STRING", "Lenguaje CCSS");

            }
            switch (cmp.tipo) {
                case "boton":
                    BotonGenerico btn = (BotonGenerico) cmp.objeto;
                    if (do_texto) {
                        btn.propiedades.get("$text").valor = (String) res.valor;
                        btn.setTexto();
                    }
                    break;
                case "enlace":
                    EnlaceGenerico enl = (EnlaceGenerico) cmp.objeto;
                    if (do_texto) {
                        enl.propiedades.get("$text").valor = (String) res.valor;
                        enl.setTexto();
                    }
                    break;
                case "texto":
                    TextoGenerico txt = (TextoGenerico) cmp.objeto;
                    if (do_texto) {
                        txt.propiedades.get("$text").valor = (String) res.valor;
                        txt.setTexto();
                    }
                    break;
                case "areatexto": 
                    AreaTextoGenerica atxt = (AreaTextoGenerica) cmp.objeto;
                    if (do_texto) {
                        atxt.propiedades.get("$text").valor = (String) res.valor;
                        atxt.setTexto();
                    }
                    break;
                case "cajatexto":     
                    CajaTextoGenerica ctxt = (CajaTextoGenerica) cmp.objeto;
                    if (do_texto) {
                        ctxt.propiedades.get("$text").valor = (String) res.valor;
                        ctxt.setTexto();
                    }
                    break;
                case "opcion":     
                    OpcionGenerica opc = (OpcionGenerica) cmp.objeto;
                    if (do_texto) {
                        opc.propiedades.get("$text").valor = (String) res.valor;
                        opc.setTexto();
                    }
                    break;
                case "cajaopciones":     
                    CajaOpcionesGenerica c_opc = (CajaOpcionesGenerica) cmp.objeto;
                    if (do_texto) {
                        c_opc.propiedades.get("$text").valor = (String) res.valor;
                        c_opc.setTexto();
                    }
                    break;    
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, Texto  No aplica a "+cmp.tipo, "Lenguaje CCSS");
                    break;
            }
        }
    }
    
    
    public void setLetra(NodoCSS raiz, NodoCSS nodo, CSS_Compilador.OperacionesARL opl, Componente porGrupo,boolean pasada){
        if (raiz.nombre.equals("identificador")) {
            Componente cmp = null;
            if(!pasada){
                cmp = lista_componentes.get(raiz.valor.trim());
            }
            
            
            if (cmp == null){
                if(porGrupo!=null){
                    cmp=porGrupo;
                }else{
                    cmp = new Componente("null", "null", new Object(), null);
                }
            } 
                
            
            ResultadoG res = opl.ejecutar(nodo.hijos.get(0));
            if (res == null)
                res = new ResultadoG("ninguno", null);
            
            boolean do_letra = false;
            switch (res.tipo) {
                case "string":
                    do_letra = true;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, Letra solo acepta: STRING ", "Lenguaje CCSS");
            }
            switch (cmp.tipo) {
                case "boton":
                case "enlace":  
                case "texto":   
                case "areatexto":   
                case "cajatexto":
                case "opcion":
                case "cajaopciones":    
                    JComponent btn = (JComponent) cmp.objeto;
                    if (do_letra) {
                        String valor = (String) res.valor;
                        valor = valor.trim();
                        Font ft = null;
                        try {
                            ft = new Font(valor, btn.getFont().getStyle(), btn.getFont().getSize());
                            Map atributes = ft.getAttributes();
                            if (cmp.tipo.equals("enlace")){
                                atributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                            }//btn.setFont(ft);
                            btn.setFont(ft.deriveFont(atributes));

                            
                            System.out.println("se aplico fuente: [" + valor + "]");
                        } catch (Exception e) {
                            System.out.println("error al aplicar fuente");
                        }
                        
                    }
                    break;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, Texto  No aplica a "+cmp.tipo, "Lenguaje CCSS");
                    break;
            }
        }
    }
    
    
    public void setTamtex(NodoCSS raiz, NodoCSS nodo, CSS_Compilador.OperacionesARL opl, Componente porGrupo,boolean pasada){
        if (raiz.nombre.equals("identificador")) {
            Componente cmp = null;
            if(!pasada){
                cmp = lista_componentes.get(raiz.valor.trim());
            }
            
            //System.out.println("APLICARE TAMTEX a --> "+ cmp.id);
            if (cmp==null){
                if(porGrupo!=null){
                    //System.out.println("es POR GRUPO");
                    cmp=porGrupo;
                }else{
                    cmp = new Componente("", "",new Object(), null);
                }
            }
                
            
            ResultadoG res = opl.ejecutar(nodo.hijos.get(0));
            if (res==null)
                res = new ResultadoG("ninguno",null);
            boolean do_tamtext = false;
            switch (res.tipo) {
                case "number":
                    do_tamtext = true;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico,Tamtex solo acepta: NUMBER", "Lenguaje CCSS");
            }
            switch (cmp.tipo) {
                case "boton":
                case "enlace": 
                case "texto":
                case "areatexto":
                case "cajatexto":
                case "cajaopciones":    
                    JComponent btn = (JComponent) cmp.objeto;
                    if (do_tamtext) {
                        Double valor = (Double) res.valor;
                        long value = Math.round(valor);
                        Integer tam = (int)(long)value;
                        
                        Font ft = null;
                        try {
                            ft = new Font(btn.getFont().getName(), btn.getFont().getStyle(), tam);
                            Map atributes = ft.getAttributes();
                            if (cmp.tipo.equals("enlace")){
                                atributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                            }//btn.setFont(ft);
                            btn.setFont(ft.deriveFont(atributes));
                            
                            System.out.println("se aplico tam: [" + tam + "]");
                        } catch (Exception e) {
                            System.out.println("error al aplico tamtext");
                        }
                        
                    }
                    break;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, TamTex  No aplica a "+cmp.tipo, "Lenguaje CCSS");
                    break;
            }
        }
    }
    
    
    public void setFondoElemento(NodoCSS raiz, NodoCSS nodo, CSS_Compilador.OperacionesARL opl,Componente porGrupo,boolean pasada){
        if (raiz.nombre.equals("identificador")) {
            Componente cmp = null;
            if(!pasada){
                cmp = lista_componentes.get(raiz.valor.trim());
            }
            
            if (cmp==null){
                if(porGrupo!=null){
                    cmp=porGrupo;
                }else{
                    cmp = new Componente("", "",new Object(), null);
                }
            }
                
            ResultadoG res = opl.ejecutar(nodo.hijos.get(0));
            if (res==null)
                res = new ResultadoG("ninguno",null);
            
            boolean do_fondo = false;
            switch (res.tipo) {
                case "string":
                    do_fondo = true;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico,fondoElemento solo acepta: STRING", "Lenguaje CCSS");
            }
            switch (cmp.tipo) {
                case "boton":
                case "enlace":
                case "texto": 
                case "areatexto": 
                case "cajatexto":
                case "panel":
                case "opcion":    
                    JComponent btn = (JComponent) cmp.objeto;
                    if (cmp.tipo.equals("enlace"))
                            btn.setOpaque(true);
                    if (do_fondo) {
                        String valor = (String) res.valor;
                        valor = valor.trim();
                        Color color = meta_colores.obtenerColor(valor);
                        try {
                            if(color!=null){
                                System.out.println("se aplico fondocolor: [" + valor + "] a "+ cmp.id);
                                btn.setBackground(color);
                            }else{
                                addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna,(String) nodo.hijos.get(0).valor , "el color es invalido", "LenguajeCCSS");
                            }
                            
                        } catch (Exception e) {
                            System.out.println("error al aplicar fuente");
                        }
                        
                    }
                    break;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, FondoElemento  No aplica a "+cmp.tipo, "Lenguaje CCSS");
                    break;
            }
        }
    }
    
    
    public void setVisible(NodoCSS raiz, NodoCSS nodo, CSS_Compilador.OperacionesARL opl, Componente porGrupo,boolean pasada){
        if (raiz.nombre.equals("identificador")) {
            Componente cmp = null;
            if(!pasada){
                cmp = lista_componentes.get(raiz.valor.trim());
            }
            if (cmp==null){
                if(porGrupo!=null){
                    cmp=porGrupo;
                }else{
                    cmp = new Componente("", "",new Object(), null);
                }
            }
            
            ResultadoG res = opl.ejecutar(nodo.hijos.get(0));
            if (res==null)
                res = new ResultadoG("ninguno",null);
            
            boolean do_fondo = false;
            switch (res.tipo) {
                case "boolean":
                    do_fondo = true;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, Visible solo acepta: boolean", "Lenguaje CCSS");
            }
            switch (cmp.tipo) {
                case "boton":
                case "enlace":
                case "imagen":    
                case "texto":   
                case "areatexto": 
                case "cajatexto":
                case "cajaopciones": 
                case "spinner":     
                    JComponent btn = (JComponent) cmp.objeto;
                    if (do_fondo) {
                        boolean valor = (boolean) res.valor;
                        
                        try {
                            btn.setVisible(valor);
                            btn.setVisible(valor);
                            System.out.println("se aplico visibilidad: [" + valor + "]");
                        } catch (Exception e) {
                            System.out.println("error al aplicar fuente");
                        }
                    }
                    
                    break;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, Visible  No aplica a "+cmp.tipo, "Lenguaje CCSS");
                    break;
            }
        }
    }
    
    public void setBorde(NodoCSS raiz, NodoCSS nodo, CSS_Compilador.OperacionesARL opl,Componente porGrupo, boolean pasada){
        if (raiz.nombre.equals("identificador")) {
            Componente cmp = null;
            if(!pasada){
                cmp = lista_componentes.get(raiz.valor.trim());
            }
            
            if (cmp==null){
                if(porGrupo!=null){
                    cmp=porGrupo;
                }else{
                    cmp = new Componente("", "",new Object(), null);
                }
            }
                
            
            ResultadoG res1 = opl.ejecutar(nodo.hijos.get(0));
            ResultadoG res2 = opl.ejecutar(nodo.hijos.get(1));
            ResultadoG res3 = opl.ejecutar(nodo.hijos.get(2));
            if (res1==null)
                res1 = new ResultadoG("ninguno",null);
            if (res2==null)
                res2 = new ResultadoG("ninguno",null);
            if (res3==null)
                res3 = new ResultadoG("ninguno",null);
            
            boolean do_borde = false;
            System.out.println(res1.tipo +":"+res2.tipo+":"+res3.tipo);
            if(res1.tipo.equals("number") && res2.tipo.equals("string") && res3.tipo.equals("boolean")){
                do_borde=true;
                System.out.println("si cumple");
            }else{
                addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Eror Semantico, Borde debe ser [number,string,boolean] error en los tipos", "Lenguaje CCSS");
            }
            
            switch (cmp.tipo) {
                case "boton":
                case "enlace":    
                case "texto":
                case "areatexto":    
                case "cajatexto":     
                case "borde":    
                case "cajaopciones":        
                    JComponent btn = (JComponent) cmp.objeto;
                    if (do_borde) {
                        Double r1=(double)res1.valor;
                        String r2=(String)res2.valor;
                        boolean r3 = (boolean)res3.valor;
                        System.out.println("ANTES se aplica borde redondeado");
                        Color color=meta_colores.obtenerColor(r2);
                        try {
                            if(r3){
                                System.out.println("se aplica borde redondeado");
                                if(color!=null){
                                    //AbstractBorder brdr = new TextBubbleBorder(Color.BLACK,r1.intValue(),btn.getPreferredSize().height/10,0);
                                    LineBorder brdr = new LineBorder(color, r1.intValue(),true);
                                    btn.setBorder(brdr);
                                }else{
                                    addError(nodo.hijos.get(1).linea, nodo.hijos.get(1).columna, nodo.hijos.get(1).valor, "el color es invalido, no existe", "Lenguaje CCSS");
                                }
                            }else{
                                if(color!=null){
                                    btn.setBorder(BorderFactory.createMatteBorder(r1.intValue(), r1.intValue(), r1.intValue(), r1.intValue(), color));
                                }else{
                                    addError(nodo.hijos.get(1).linea, nodo.hijos.get(1).columna, nodo.hijos.get(1).valor, "el color es invalido, no existe", "Lenguaje CCSS");
                                }
                                
                            }
                            
                        } catch (Exception e) {
                            System.out.println("error al aplicar fuente");
                        }
                        
                    }
                    break;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, Borde  No aplica a "+cmp.tipo, "Lenguaje CCSS");
                    break;
            }
        }
    }
    
    public void setOpaque(NodoCSS raiz, NodoCSS nodo, CSS_Compilador.OperacionesARL opl, Componente porGrupo,boolean pasada){
        if (raiz.nombre.equals("identificador")) {
            Componente cmp = null;
            if(!pasada){
                cmp = lista_componentes.get(raiz.valor.trim());
            }
            if (cmp==null){
                if(porGrupo!=null){
                    cmp=porGrupo;
                }else{
                    cmp = new Componente("", "",new Object(), null);
                }
            }
            
            ResultadoG res = opl.ejecutar(nodo.hijos.get(0));
            if (res==null)
                res= new ResultadoG("ninguno", null);
            boolean do_fondo = false;
            switch (res.tipo) {
                case "boolean":
                    do_fondo = true;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, Opaque solo acepta: true o false", "Lenguaje CCSS");
            }
            switch (cmp.tipo) {
                case "boton":
                case "enlace":    
                case "texto":      
                case "areatexto":
                case "cajatexto":
                case "panel":
                case "opcion":
                case "cajaopciones":  
                case "spinner":      
                    JComponent btn = (JComponent) cmp.objeto;
                    if (do_fondo) {
                        boolean valor = (boolean) res.valor;
                        
                        try {
                            btn.setOpaque(valor);
                            System.out.println("se aplico opaque: [" + valor + "]");
                        } catch (Exception e) {
                            System.out.println("error al aplicar opaque");
                        }
                        
                    }
                    break;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, Opaque  No aplica a "+cmp.tipo, "Lenguaje CCSS");
                    break;
            }
        }
    }
    
    
    public void setColortext(NodoCSS raiz, NodoCSS nodo, CSS_Compilador.OperacionesARL opl, Componente porGrupo,boolean pasada){
        if (raiz.nombre.equals("identificador")) {
            Componente cmp = null;
            if(!pasada){
                cmp = lista_componentes.get(raiz.valor.trim());
            }
            if (cmp==null){
                if(porGrupo!=null){
                    cmp=porGrupo;
                }else{
                    cmp = new Componente("", "",new Object(), null);
                }
            }
            
            ResultadoG res = opl.ejecutar(nodo.hijos.get(0));
            if (res==null)
                res= new ResultadoG("ninguno", null);
            
            boolean do_colortext = false;
            switch (res.tipo) {
                case "string":
                    do_colortext = true;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, color text solo acepta: string", "Lenguaje CCSS");
            }
            switch (cmp.tipo) {
                case "boton":
                case "enlace":    
                case "texto":  
                case "areatexto":  
                case "cajatexto":     
                    JComponent btn = (JComponent) cmp.objeto;
                    if (do_colortext) {
                        String valor = (String) res.valor;
                        valor=valor.trim();
                        Color color=meta_colores.obtenerColor(valor);

                        try {
                            if(color!=null){
                                btn.setForeground(color);
                            }else{
                                addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, COLORTEXT, color Invalido->: "+valor, "Lenguaje CCSS");
                            }
                            System.out.println("se aplico colortext: [" + valor + "]");
                        } catch (Exception e) {
                            System.out.println("error al aplicar colortext");
                        }
                        
                    }
                    break;
                default:
                    addError(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, nodo.hijos.get(0).nombre, "Error Semantico, ColorText  No aplica a "+cmp.tipo, "Lenguaje CCSS");
                    break;    
            }
        }
    }
    
}
