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
public class Si extends Compilador{

    @Override
    public Metodo ejecutar(Nodo raiz) {
        Nodo exp = raiz.hijos.get(0); // se toma el nodo EXPRESION
        Nodo sentenciasSi = raiz.hijos.get(1);// nodo de las sentencias SI
        Nodo sentenciasSino = raiz.hijos.get(2);// nodo de las sentencias SINO
        
        // se ejecuta el nodo EXPRESION
        opL = new OperacionesARL(global, tabla);
        ResultadoG condicion = opL.ejecutar(exp);
        
        /*
            tabla que es ACTUAL
            tablaTemp contiene los elementos de tabla ACTUAL, los puede modificar y todo
            pero los nuevos valores que se agreguen en ese ambito no van a pertencer al ambito anterior
            solo se quedan en el ambito temporal y hacia dentro pero no hacia afuera
        */
         //cambio de ambito
        TablaSimboloG tablaTemp = new TablaSimboloG();// tabla TEMPORAL
        tablaTemp.cambiarAmbito(tabla);// se copia la tabla ACTUAL en tabla TEMPORAL
        pilaTablas.push(tabla);       // se apila la tabla ACTUAL
        tabla = tablaTemp;            // tabla TEMPORAL es la nueva tabla
        
        
        if (condicion.tipo.equalsIgnoreCase("boolean")) {
            if ((Boolean) condicion.valor) {
                metodoActual = ejecutarSentencias(sentenciasSi);

                if (metodoActual.estadoRetorno) {
                    tabla = pilaTablas.pop();
                    return metodoActual;
                }

            } else {
                metodoActual = ejecutarSentencias(sentenciasSino);
                
                if (metodoActual.estadoRetorno) {
                    tabla = pilaTablas.pop();
                    return metodoActual;
                }
            }
        }
        //regreso al ambito 
        tabla = pilaTablas.pop();
        return metodoActual;
    }
    
    
}
