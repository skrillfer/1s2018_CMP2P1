/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CJS_Compilador;
import Estructuras.*;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author fernando
 */
public class Clase {
    public Stack<TablaSimboloG> pilaTablas;
         //--------------------------------------
    public String archivo;
    public TablaSimboloG global;
    public TablaSimboloG tabla;
    public String nombre;
    public String visibilidad;
    public Clase herencia;
    public String nombreHereda;
    public ArrayList<Metodo> metodos;
    public ArrayList<Nodo> atributos;
    
    
     public Clase() {
        global = new TablaSimboloG();   //La tabla de simbolos global
        tabla = new TablaSimboloG();    
        atributos = new ArrayList<>();
        metodos = new ArrayList<>();
        pilaTablas = new Stack<>();
    }
     
    public Clase(Nodo raiz) {

        global = new TablaSimboloG();
        tabla = new TablaSimboloG();
        atributos = new ArrayList<>();
        metodos = new ArrayList<>();
        pilaTablas = new Stack<>();
     
        //----------------------------------------------------------------------
        //this.nombre = raiz.valor;
        //this.visibilidad = raiz.hijos.get(0).valor;
        this.metodos = getMetodos(raiz);
        this.atributos = getAtributos(raiz);
        //----------------------------------------------------------------------
    } 

     private ArrayList<Metodo> getMetodos(Nodo raiz) {
        ArrayList<Metodo> metodos = new ArrayList<>();//una lista de METODOS
        for (Nodo hijo : raiz.hijos) {
            if (hijo.nombre.equalsIgnoreCase("funcion")) {
                Metodo metodo = new Metodo(hijo);
                if (!existeMetodo(metodo.id, hijo)) {
                    metodos.add(metodo);
                    SimboloG simbolo = new SimboloG("funcion", metodo.nombre, "metodo");
                    simbolo.rol = "Metodo";
                    simbolo.ambito = nombre;
                    //Graphik.reporteSimbolos.add(simbolo);
                }
            }
        }
        return metodos;
    }

    private Boolean existeMetodo(String id, Nodo raiz) {
        for (Metodo metodo : metodos) {
            if (metodo.id.equalsIgnoreCase(id)) {
                //Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "El metodo " + metodo.nombre + " ya existe en la clase " + nombre);
                return true;
            }
        }
        return false;
    }

    private ArrayList<Nodo> getAtributos(Nodo raiz) {
        ArrayList<Nodo> atributos = new ArrayList<>();
        for (Nodo hijo : raiz.hijos) {
            if ( hijo.nombre.equalsIgnoreCase("declara_var") || hijo.nombre.equalsIgnoreCase("declara_vecF1") || hijo.nombre.equalsIgnoreCase("declara_vecF2")) {
                atributos.add(hijo);
            }
        }
        return atributos;
    }

    public void ejecutar() {
        
        for (Nodo atributo : atributos) {
            new Declaracion(atributo, global, tabla);
        }
        
    }
}
