/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CJS_Compilador;

import java.util.ArrayList;
import CJS_Compilador.OperacionesARL.*;
import Estructuras.Nodo;
/**
 *
 * @author fernando
 */
public class Arreglo {
    public ArrayList<Integer> dimensiones;
    private ArrayList<Object> datos;
    private TablaSimboloG global;
    private TablaSimboloG tabla;
    public boolean estado = true;
    private  OperacionesARL opL;
    
    
    public Arreglo() {
        dimensiones = new ArrayList<>();
        datos = new ArrayList<>();
    }
    
    public Arreglo(Nodo raiz, TablaSimboloG global, TablaSimboloG tabla) {
        dimensiones = new ArrayList<>();
        datos = new ArrayList<>();
        this.global = global;
        this.tabla = tabla;
        opL = new OperacionesARL(global, tabla);
        guardarValores(raiz);
    }

    public Arreglo(Nodo raiz, TablaSimboloG global, TablaSimboloG tabla, int n) {
        dimensiones = new ArrayList<>();
        datos = new ArrayList<>();
        this.global = global;
        this.tabla = tabla;
        opL = new OperacionesARL(global, tabla);
        guardarDimensiones(raiz);
        for (int i = 0; i < dimensiones.size(); i++) {
            int tam = dimensiones.get(i);
            for (int j = 0; j < tam; j++) {
                datos.add(null);
            }
        }
    }
    
    public ArrayList<Object> getDatos() {
        return datos;
    }

    
    private void guardarDimensiones(Nodo raiz) {
        ArrayList<Nodo> dim = raiz.hijos.get(1).hijos;
        int total = 1;
        for (Nodo hijo : dim) {
            ResultadoG dimension = opL.ejecutar(hijo);
            
            if (dimension.tipo.equalsIgnoreCase("number")) {
                total = total* ((Double) dimension.valor).intValue();
                dimensiones.add( ((Double) dimension.valor).intValue());
            } else {
                //Inicio.reporteError.agregar("Semantico", hijo.linea, hijo.columna, "Solo se permiten valores enteros para los indices de un arreglo");
                estado = false;
            }
        }
    }
    
    private void guardarValores(Nodo raiz) {
        ArrayList<Nodo> dim = null;
        ArrayList<Nodo> val = null;
        String tipo = "";
        switch (raiz.hijos.size()) {
            
            case 2:
                val = raiz.hijos.get(1).hijos;//modifique para la nueva version
                break;
            default:
                break;
        }
        
        int total = 1;        
        for (Nodo hijo : val) {
            ResultadoG resultado = opL.ejecutar(hijo);
            if (resultado != null) {
                total++;
                datos.add(resultado.valor);
            }
        }
        dimensiones.add(total);
    }
}
