/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CJS_Compilador;

/**
 *
 * @author fernando
 */
public class SimboloG {
    public String nombre;
    public String tipo;
    public String visibilidad;
    public Object valor;
    public boolean inicializado = false;
    public boolean esArreglo = false;

    public String ambito;
    public String rol;

    public SimboloG(String tipo, String nombre, String visibilidad, Object valor) {
        if (valor != null) {
            if (valor.getClass().getSimpleName().equalsIgnoreCase("arreglo")) {
                esArreglo = true;
            }
        }
        this.nombre = nombre;
        this.tipo = tipo;
        this.visibilidad = visibilidad;
        this.valor = valor;
    }

    public SimboloG(String tipo, String nombre, Object valor) {
        if (valor != null) {
            if (valor.getClass().getSimpleName().equalsIgnoreCase("arreglo")) {
                esArreglo = true;
            }
        }
        this.tipo = tipo;
        this.nombre = nombre;
        this.valor = valor;
    }
}
