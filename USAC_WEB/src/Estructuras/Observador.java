/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;

import CJS_Compilador.Clase;
import CJS_Compilador.TablaSimboloG;
import java.util.ArrayList;

/**
 *
 * @author fernando
 */
public class Observador {
    public TablaSimboloG global=null;
    public TablaSimboloG tabla=null;
    public ArrayList<Nodo> sentencias=null;
    public Clase claseActual=null;

    public Observador(TablaSimboloG global, TablaSimboloG tabla, ArrayList<Nodo> sentencias,Clase claseActual) {
        this.global = global;
        this.tabla = tabla;
        this.sentencias = sentencias;
        this.claseActual=claseActual;
    }

    public Observador() {
    }
    
     
}
