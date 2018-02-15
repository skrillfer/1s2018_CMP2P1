/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;

import java.util.ArrayList;

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
    public ArrayList<Propiedad> propiedades;

    public NodoDOM(String nombre, String valor, int linea, int columna, int index) {
        this.nombre = nombre;
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
        this.index = index;
        this.hijos = new ArrayList<>();
        this.propiedades = new ArrayList<>();
    }

    public NodoDOM() {

    }

    public void add(NodoDOM h) {
        this.hijos.add(h);
    }

    
    public void addP(String nombre, String valor) {
        this.propiedades.add(new Propiedad(nombre, valor));
    }

    //este metodo obtiene el valor de la propiedad
    public String getVP(String nombrePropiedad) {
        for (int i = 0; i < this.propiedades.size(); i++) {
            if (this.propiedades.get(i).nombre.equals(nombrePropiedad)) {
                return this.propiedades.get(i).valor;
            }
        }
        return "";
    }

    //este metodo setea el valor de la propiedad, primero se busca y luego se cambia
    public void setVP(String nombrePropiedad, String valorPropiedad) {
        for (int i = 0; i < this.propiedades.size(); i++) {
            if (this.propiedades.get(i).nombre.equals(nombrePropiedad)) {
                this.propiedades.set(i, new Propiedad(nombrePropiedad, valorPropiedad));
                break;
            }
        }
    }

}
