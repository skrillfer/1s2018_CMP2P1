/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CJS_Compilador;
import Estructuras.*;
import java.util.ArrayList;
/**
 *
 * @author fernando
 */
public class Metodo {
    public String nombre;
    public String id;
    public String tipo;
    public String visibilidad;
    public Nodo sentencias;
    public ArrayList<Nodo> parametros;
    public ResultadoG retorno;

    public boolean estadoRetorno = false;
    public boolean estadoTerminar = false;
    public boolean estadoContinuar = false;
    
     public Metodo() {

    }

    public Metodo(Nodo raiz) {
        if (raiz.hijos.size() == 1) {
            //si es metodo inicio o datos
            this.tipo = "vacio";
            this.nombre = raiz.valor;
            this.sentencias = raiz.hijos.get(0);
            this.id = nombre;
            this.visibilidad = "privado";
            this.parametros = new ArrayList();
        } else {
            //si es un metodo o funcion 
            this.tipo = raiz.hijos.get(0).valor;
            this.nombre = raiz.valor;
            this.visibilidad = raiz.hijos.get(1).valor;
            this.parametros = raiz.hijos.get(2).hijos;
            this.sentencias = raiz.hijos.get(3);
            this.id = getId();
        }
    }
    
     private String getId() {
        String id = nombre;
        for (Nodo parametro : parametros) {
            if (parametro.nombre.equals("parametro")) {
                id += parametro.hijos.get(0).valor;
            } else {
                //si el parametro es un vector
                id += parametro.hijos.get(0).valor + parametro.hijos.get(1).hijos.size();
            }
        }
        return id;
    }
}
