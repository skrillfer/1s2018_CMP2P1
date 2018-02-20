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
public class Selecciona extends Compilador{

    @Override
    public Metodo ejecutar(Nodo raiz) {
        Nodo acceso = raiz.hijos.get(0);// Nodo EXP 
        Nodo casos = raiz.hijos.get(1); // Lista de Casos
        boolean estado = true;
        
        Nodo defecto=null;
        if (casos.hijos.size() > 0) {
            for (int i = 0; i < casos.hijos.size(); i++) {
                if(casos.hijos.get(i).nombre.equals("caso")){
                    Nodo caso = casos.hijos.get(i); // Nodo CASO
                    Nodo comp = caso.hijos.get(0); // Nodo EXPR
                    Nodo sentencias = caso.hijos.get(1);// Sentencias del CASO

                    Nodo exp = new Nodo("IG_IG","", comp.linea, comp.columna,0);
                    exp.add(acceso);
                    exp.add(comp);

                    opL = new OperacionesARL(global, tabla);
                    ResultadoG condicion = opL.ejecutar(exp);

                    if (condicion.tipo.equals("boolean")) {
                        if ((Boolean) condicion.valor) {
                            //cambio de ambito
                            TablaSimboloG tablaTemp = new TablaSimboloG();
                            tablaTemp.cambiarAmbito(tabla);
                            pilaTablas.push(tabla);
                            tabla = tablaTemp;
                            
                            metodoActual = ejecutarSentencias(sentencias);

                            if (metodoActual.estadoRetorno) {
                                tabla = pilaTablas.pop();
                                return metodoActual;
                            }

                            if (metodoActual.estadoTerminar) {
                                estado = false;
                                tabla = pilaTablas.pop();
                                metodoActual.estadoTerminar = false;
                                break;
                            }
                            if (metodoActual.estadoContinuar) {
                                estado = false;
                                tabla = pilaTablas.pop();
                                //metodoActual.estadoContinuar=false;
                                break;
                            }
                            tabla = pilaTablas.pop();
                        }
                    }
                }else{
                    defecto = casos.hijos.get(i);
                }
                
            }
            
            //defecto
            if (estado && defecto != null) {
                
                
                Nodo sentencias = defecto.hijos.get(0);

                estado = false;
                TablaSimboloG tablaTemp = new TablaSimboloG();
                tablaTemp.cambiarAmbito(tabla);
                pilaTablas.push(tabla);
                tabla = tablaTemp;
                metodoActual = ejecutarSentencias(sentencias);

                if (metodoActual.estadoRetorno) {
                    tabla = pilaTablas.pop();
                    return metodoActual;
                }

                if (metodoActual.estadoTerminar) {
                    tabla = pilaTablas.pop();
                    metodoActual.estadoTerminar = false;
                }
                if (metodoActual.estadoContinuar) {
                    estado = false;
                    tabla = pilaTablas.pop();
                    //metodoActual.estadoContinuar=false;
                } else {
                    tabla = pilaTablas.pop();
                }
            }

        }
        return metodoActual;
    }
    
}
