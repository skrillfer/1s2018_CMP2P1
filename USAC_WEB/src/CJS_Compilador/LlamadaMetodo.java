/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CJS_Compilador;

import CJS_Compilador.OperacionesARL.OperacionesARL;
import Estructuras.Nodo;
import java.util.ArrayList;

/**
 *
 * @author fernando
 */
public class LlamadaMetodo extends Compilador{
    private Clase actual;
    private int nivel = 0;

    public LlamadaMetodo(Clase actual) {
        this.actual = actual;
    }

    public LlamadaMetodo(Clase actual, int nivel) {
        this.actual = actual;
        this.nivel = nivel;
    }
    
    @Override
    public Metodo ejecutar(Nodo raiz) {
        String nombre = raiz.hijos.get(0).valor;
        
        
        ArrayList<ResultadoG> parametros = getParametros(raiz);
        String id = getId(nombre, parametros);
        Metodo metodoTemp = getMetodo(id);
        if (metodoTemp != null) {
            pilaNivelCiclo.push(nivelCiclo);
            nivelCiclo = 0;
            //actual.tabla=new TablaSimboloG();
            pilaTablas.push(tabla);
            TablaSimboloG tablaTemp = new TablaSimboloG();
            tabla = tablaTemp;
            
            CJS.metodoActual = metodoTemp;
            
            for (int i = 0; i < metodoTemp.parametros.size(); i++) {
                Nodo parametro = metodoTemp.parametros.get(i);
                ResultadoG valor = parametros.get(i);
                
                new Declaracion(parametro, valor, actual.global, tabla);
            }
            pilaMetodos.push(metodoActual);
            metodoActual = metodoTemp;

            global = actual.global;
            //pilaClases.push(claseActual);
            //claseActual = actual;
            
            metodoTemp = ejecutarSentencias(metodoActual.sentencias);
            metodoTemp.estadoRetorno = false;
            metodoTemp.estadoContinuar = false;
            metodoTemp.estadoTerminar = false;
            metodoActual = pilaMetodos.pop();
            //claseActual = pilaClases.pop();
            //global = claseActual.global;
            tabla = pilaTablas.pop();
            nivelCiclo = pilaNivelCiclo.pop();
            
        } else {
            //Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "El metodo " + nombre + " no existe en el ambito donde fue invocado");
        }
        return metodoTemp;
    }
    
    private String getId(String nombre, ArrayList<ResultadoG> parametros) {
        for (ResultadoG resultado : parametros) {
            if (resultado.valor != null) {
                if (resultado.valor.getClass().getSimpleName().equalsIgnoreCase("arreglo")) {
                    Arreglo arr = (Arreglo) resultado.valor;
                    nombre += resultado.tipo + arr.dimensiones.size();
                } else {
                    nombre += "_1p";
                }
            }
        }
        return nombre;
    }
    
    private ArrayList<ResultadoG> getParametros(Nodo raiz) {
        ArrayList<ResultadoG> parametros = new ArrayList<>();
        Nodo nodoParametros = raiz.hijos.get(1);
        for (Nodo hijo : nodoParametros.hijos) {
            opL = new OperacionesARL(global, tabla);
            ResultadoG resultado = opL.ejecutar(hijo);
            parametros.add(resultado);
        }
        return parametros;
    }
    
    private Metodo getMetodo(String id) {
        Metodo metodo = buscarMetodo(id, actual);
        
        if (metodo != null) {
            return metodo;
        }
        return metodo;
    }

    private Metodo buscarMetodo(String id, Clase actual) {
        for (Metodo metodo : actual.metodos) {
            if (metodo.id.equalsIgnoreCase(id)) {
                return metodo;
            }
        }
        return null;
    }
}
