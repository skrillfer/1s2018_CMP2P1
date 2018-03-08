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
        
        n_clase.ejecutar(); //se ejecutan las declaraciones globales 
        
        pilaNivelCiclo = new Stack<>();
        pilaClases = new Stack<>();
        pilaMetodos = new Stack<>();
        pilaTablas = new Stack<>();
        
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
    
    public void ejecutarMetodo(String nombre, int linea, int columna){
        for (Clase clase : lista_Clases) {
            //se busca el metodo
            claseActual=clase;
            
            clase.global = new TablaSimboloG();   //La tabla de simbolos global
            clase.tabla = new TablaSimboloG();    
            clase.pilaTablas=pilaTablas = new Stack<>();
            
            global=clase.global;
            tabla =clase.tabla;
            clase.ejecutar(); //se ejecutan las declaraciones globales
            
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
