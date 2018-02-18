/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CJS_Compilador;

import java.util.Hashtable;

/**
 *
 * @author fernando
 */
public class TablaSimboloG {

    public Hashtable<String, SimboloG> tabla;

    public TablaSimboloG() {
        tabla = new Hashtable<>();
    }

    //verifica si existe el simbolo en la tabla de simbolos
    public Boolean existe(String nombre) {
        nombre = nombre.toLowerCase();
        return tabla.containsKey(nombre);
    }
    
    public boolean setSimbolo(SimboloG simbolo) {
        simbolo.nombre = simbolo.nombre.toLowerCase();
        if (!existe(simbolo.nombre)) {
            tabla.put(simbolo.nombre, simbolo);
            if (CJS.metodoActual != null) {
                simbolo.ambito = CJS.claseActual.nombre + "_" + CJS.metodoActual.nombre;
            } else {
                simbolo.ambito = CJS.claseActual.nombre;
            }
            simbolo.rol = "Variable";
            //Graphik.reporteSimbolos.add(simbolo);
            System.out.println("AMBITO-> "+simbolo.ambito);

            return true;//se agrego correctamente
        } else {
            return false;//no se agrego a la global
        }
    }
}
