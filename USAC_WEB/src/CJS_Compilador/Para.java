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
public class Para extends Compilador {

    @Override
    public Metodo ejecutar(Nodo raiz) {
        Nodo declaracion = raiz.hijos.get(0);
        Nodo expCondicion = raiz.hijos.get(1);
        Nodo operador = raiz.hijos.get(2);
        Nodo sentencias = raiz.hijos.get(3);

        
        Nodo expA = declaracion.hijos.get(1);

        //********** declarar la variable
        Nodo declaraVar = new Nodo("declara_var_L", "", declaracion.linea, declaracion.columna, 0);
        declaracion.hijos.get(0).nombre="nombre";
        declaraVar.add(declaracion.hijos.get(0));
        Nodo h1 = new Nodo("asigna_var", "", expA.linea, expA.columna, 0);
        h1.add(expA);
        declaraVar.add(h1);

        TablaSimboloG tablaTempPrincipal = new TablaSimboloG();
        tablaTempPrincipal.cambiarAmbito(tabla);
        pilaTablas.push(tabla);
        tabla = tablaTempPrincipal;

        Declaracion declara = new Declaracion(declaraVar, global, tabla,miTemplate);
        
        SimboloG simbolo = tabla.getSimbolo(declaracion.hijos.get(0).valor, CJS.claseActual);
        opL = new OperacionesARL(global, tabla,miTemplate);
        ResultadoG condicion = opL.ejecutar(expCondicion);
        
        if(simbolo!=null){
            if (condicion.tipo.equals("boolean")) {

                while ((Boolean) condicion.valor) {

                    TablaSimboloG tablaTemp = new TablaSimboloG();
                    tablaTemp.cambiarAmbito(tabla);
                    pilaTablas.push(tabla);
                    tabla = tablaTemp;

                    metodoActual = ejecutarSentencias(sentencias);

                    tabla = pilaTablas.pop();
                    //**************************************************************
                    //**************************************************************

                    opL = new OperacionesARL(global, tabla,miTemplate);
                    if (operador.valor.equals("add") || operador.valor.equals("sub")) {
                        Nodo n_ADD = new Nodo(operador.valor.toUpperCase(), "", operador.linea, operador.columna, 0);
                        declaracion.hijos.get(0).nombre = "id";
                        n_ADD.add(declaracion.hijos.get(0));

                        ResultadoG rsg = opL.ejecutar(n_ADD);
                        if (rsg != null) {
                            simbolo.valor=rsg.valor;
                        }
                    }

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

                    condicion = opL.ejecutar(expCondicion);
                    if (condicion.tipo.equals("number")) {
                        if ((int) condicion.valor == 1) {
                            condicion = new ResultadoG("boolean", true);
                        } else if ((int) condicion.valor == 0) {
                            condicion = new ResultadoG("boolean", false);
                        }
                    }
                }
            }
        }
            
        
        tabla = pilaTablas.pop();
        return metodoActual;
    }

}
