/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CJS_Compilador;

import CJS_Compilador.OperacionesARL.OperacionesARL;
import Estructuras.Nodo;

/**
 *
 * @author fernando
 */
public class Mientras extends Compilador{

    @Override
    public Metodo ejecutar(Nodo raiz) {
        Nodo exp = raiz.hijos.get(0);// nodo EXPRESION
        Nodo sentencias = raiz.hijos.get(1);// nodo SENTENCIAS

        //se ejecuta EXPR y se obtiene el resultado
        opL = new OperacionesARL(global, tabla);
        ResultadoG condicion = opL.ejecutar(exp);
        
        if (condicion.tipo.equalsIgnoreCase("boolean")) {
            while ((Boolean) condicion.valor) {
                
                //se cambia el ambito
                TablaSimboloG tablaTemp = new TablaSimboloG();
                tablaTemp.cambiarAmbito(tabla);
                pilaTablas.push(tabla);
                tabla = tablaTemp;
                
                metodoActual = ejecutarSentencias(sentencias);
                
                //regresamos al ambito
                tabla = pilaTablas.pop();
                opL = new OperacionesARL(global, tabla);
                condicion = opL.ejecutar(exp);

                if (metodoActual.estadoRetorno) {
                    break;
                }
                if (metodoActual.estadoTerminar) {
                    metodoActual.estadoTerminar = false;
                    break;
                }

                if (metodoActual.estadoContinuar) {
                    metodoActual.estadoContinuar = false;
                }

            }
        } else {
            //Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "El tipo de dato de la condicion no es Bool");
        }
        return metodoActual;
    }
}
