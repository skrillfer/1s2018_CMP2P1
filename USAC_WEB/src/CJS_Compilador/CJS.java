/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CJS_Compilador;

import Estructuras.*;
import Interfaz.Template;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author fernando
 */

public class CJS extends Compilador{
    ArrayList<Clase> lista_Clases = new ArrayList<>();
    
    public void ejecucionCJS(Nodo raiz,String metodoInicio,String archivo){
        //aqui habra una lista de archivos cjs que pertenecen a el html
        //como hay una lista de archivos cjs debe haber una lista de CLASES (JAVASCRIPT)
        //##############Se crear una nueva CLASE
        raiz.valor=archivo;
        Clase n_clase = new Clase(raiz);
        n_clase.archivo=archivo;
       
        
        claseActual=n_clase;
        global=n_clase.global;
        tabla = n_clase.tabla;
        
        TablaSimboloG tmpGlobal = n_clase.global;
        TablaSimboloG tmpLocal = n_clase.tabla;
        
        //n_clase.ejecutar(); //se ejecutan las declaraciones globales 
        pilaNivelCiclo = new Stack<>();
        pilaClases = new Stack<>();
        pilaMetodos = new Stack<>();
        pilaTablas = new Stack<>();
        
        for (Nodo atributo : n_clase.atributos) {
            if (atributo.nombre.equalsIgnoreCase("declara_var") || atributo.nombre.equalsIgnoreCase("declara_vecF1")
                    || atributo.nombre.equalsIgnoreCase("declara_vecF2") || atributo.nombre.equalsIgnoreCase("asigna_vecGlbF1")
                    || atributo.nombre.equalsIgnoreCase("asigna_vecGlbF2") || atributo.nombre.equalsIgnoreCase("asignacionGlb")) {
                new Declaracion(atributo, global, tabla);
            } else {
                Nodo padre = new Nodo("Sentencias", "", 0, 0, 898);
                padre.hijos.add(atributo);
                ejecutarSentencias(padre);
            }
        }
        
        
        
        metodoActual = getInicio(metodoInicio);
        global = claseActual.global;
        tabla = claseActual.tabla;
        
        /*if(metodoActual!=null){
            ejecutarSentencias(metodoActual.sentencias);// se ejecutan las sentencias del metodo que inicia la compilacion
        }*/
        
        // despues de ejecutar se guarda los valores iniciales que tenia
        n_clase.global=tmpGlobal;
        n_clase.tabla=tmpLocal;
        lista_Clases.add(n_clase);
        
    }
    
    public void ejecutarMETODO1(TablaSimboloG global1,TablaSimboloG local,ArrayList<Nodo> Sentencias,Clase claseActual1){
        claseActual=claseActual1;
        global=global1;
        if(local==null){
            tabla = new TablaSimboloG();
        }else{
            tabla =local;
        }
        
        pilaNivelCiclo = new Stack<>();
        pilaClases = new Stack<>();
        pilaMetodos = new Stack<>();
        pilaTablas = new Stack<>();
        
        if (Sentencias != null) {
            Nodo nnnn= new Nodo("sentencias", "", 0, 0, 11222);
            nnnn.hijos=Sentencias;
            ejecutarSentencias(nnnn);// se ejecutan las sentencias del metodo que inicia la compilacion   
        }
    }
    
    public void ejecutarMetodo(String nombre, int linea, int columna){
        for (Clase clase : lista_Clases) {
            //se busca el metodo
            claseActual=clase;
            clase.tabla = new TablaSimboloG();    
            global=clase.global;
            tabla =clase.tabla;
            
            
            pilaNivelCiclo = new Stack<>();
            pilaClases = new Stack<>();
            pilaMetodos = new Stack<>();
            pilaTablas = new Stack<>();
        
            metodoActual=getInicio(nombre);
            global = claseActual.global;
            tabla = claseActual.tabla;
            
            if(metodoActual!=null){
                ejecutarSentencias(metodoActual.sentencias);// se ejecutan las sentencias del metodo que inicia la compilacion
                break;
            }else{
                Template.reporteError_CJS.agregar("Error Ejecucion", linea, columna, "El metodo a ejecutar -> "+nombre+ " no existe");
            }
        }
    }
    
    public Metodo getInicio(String nombre){
        for (Metodo metodo : claseActual.metodos) {
            if (metodo.nombre.equalsIgnoreCase(nombre)) {
                return metodo;
            }
        }
        return null;
    }
     

    @Override
    public Metodo ejecutar(Nodo raiz) {
        return null;
    }
}
