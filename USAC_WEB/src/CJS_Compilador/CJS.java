/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CJS_Compilador;

import Estructuras.*;
import java.util.Stack;

/**
 *
 * @author fernando
 */

public class CJS extends Compilador{
    
    
    public void ejecucionCJS(Nodo raiz,String metodoInicio){
        //aqui habra una lista de archivos cjs que pertenecen a el html
        //como hay una lista de archivos cjs debe haber una lista de CLASES (JAVASCRIPT)
        //##############Se crear una nueva CLASE
        raiz.valor="clase1";
        Clase n_clase = new Clase(raiz);
        
        claseActual=n_clase;
        global=n_clase.global;
        tabla = n_clase.tabla;
        //n_clase.ejecutar(); //se ejecutan las declaraciones globales 
        
        pilaNivelCiclo = new Stack<>();
        pilaClases = new Stack<>();
        pilaMetodos = new Stack<>();
        pilaTablas = new Stack<>();
        
        metodoActual = getInicio(metodoInicio);
        global = claseActual.global;
        tabla = claseActual.tabla;
        if(metodoActual!=null){
//            ejecutarSentencias(metodoActual.sentencias);// se ejecutan las sentencias del metodo que inicia la compilacion
        }
        
        
        System.out.println("");
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
