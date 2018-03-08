/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CJS_Compilador;

import Estructuras.Nodo;
import java.util.ArrayList;

/**
 *
 * @author fernando
 */
public class Archivo {
    public String ruta;
    public ArrayList<Metodo> metodos;
    public ArrayList<Nodo> atributos;
    
    public String archivo;
    public TablaSimboloG global;
    public TablaSimboloG tabla;

    public Archivo(String ruta, ArrayList<Metodo> metodos, ArrayList<Nodo> atributos, String archivo, TablaSimboloG global, TablaSimboloG tabla) {
        this.ruta = ruta;
        this.metodos = metodos;
        this.atributos = atributos;
        this.archivo = archivo;
        this.global = global;
        this.tabla = tabla;
    }
    
    
}
