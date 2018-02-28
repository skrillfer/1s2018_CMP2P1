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
public class NodoCSS {
     public String nombre;
    public String valor;
    public int linea;
    public int columna;
    public int index;
    public ArrayList<NodoCSS> hijos;

    public NodoCSS(String nombre, String valor, int linea, int columna, int index) {
        this.nombre = nombre;
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
        this.index = index;
        this.hijos = new ArrayList<>();
    }

    public NodoCSS() {
        
    }
    
    
    public void add(NodoCSS h){
        this.hijos.add(h);
    }
}
