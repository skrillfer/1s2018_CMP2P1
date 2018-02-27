/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * @author fernando
 */
public class NodoDOM {

    public String nombre;
    public String valor;
    public int linea;
    public int columna;
    public int index;
    public ArrayList<NodoDOM> hijos;
    //-------------Lista de propiedades de este nodo DOM
    public Hashtable<String,Propiedad> propiedades;

    public NodoDOM(String nombre, String valor, int linea, int columna, int index) {
        this.nombre = nombre;
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
        this.index = index;
        this.hijos = new ArrayList<>();
        this.propiedades = new Hashtable<>();
        
        llenarHash();
        //este valor debe ir en blanco pero cuestiones de practicidad...
        //propiedades.add(new Propiedad("$text","btn1"));


    }

    public NodoDOM() {

    }

    public void add(NodoDOM h) {
        this.hijos.add(h);
    }

    
    public void addP(String nombre, String valor) {
        this.propiedades.put(nombre,new Propiedad(nombre, valor));
    }

    //este metodo obtiene el valor de la propiedad
    public String getVP(String nombrePropiedad) {
        if(propiedades.containsKey(nombrePropiedad.toLowerCase())){
            return propiedades.get(nombrePropiedad.toLowerCase()).valor;
        }
        return "";
    }

    //este metodo setea el valor de la propiedad, primero se busca y luego se cambia
    public void setVP(String nombrePropiedad, String valorPropiedad) {
        if(propiedades.containsKey(nombrePropiedad.toLowerCase())){
            propiedades.get(nombrePropiedad.toLowerCase()).valor=valorPropiedad;
        }
    }

    public void llenarHash(){
        //generales
        addP("$text", "");
        addP("id", "");
        addP("grupo", "");
        addP("alto", "");
        addP("ancho", "");
        addP("alineado", "");
        //particulares
        addP("fondo", "");
        addP("ruta", "");
        addP("click", "");
        addP("valor", "");//aplica en la etiqueta OPTION
    }
}
