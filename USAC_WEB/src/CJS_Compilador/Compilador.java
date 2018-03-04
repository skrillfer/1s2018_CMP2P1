/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CJS_Compilador;
import Estructuras.*;
import java.util.Stack;
import CJS_Compilador.OperacionesARL.OperacionesARL;
/**
 *
 * @author fernando
 */
public abstract class Compilador {
    //--------------------------------------------------------------------------
    
    public static Clase claseActual;
    public static Stack<Clase> pilaClases;
    public static Stack<Metodo> pilaMetodos;
    public static Metodo metodoActual;
    public static Stack<TablaSimboloG> pilaTablas;
    //--------------------------------------------------------------------------
    
    protected Nodo raiz;
    public static int nivelCiclo = 0;
    public static Stack<Integer> pilaNivelCiclo;
    protected OperacionesARL opL;
    public static TablaSimboloG tabla;//
    public static TablaSimboloG global;//esta es la tabla que se usa a nivel global para todas las clases del compilador
    
    
    public abstract Metodo ejecutar(Nodo raiz);
    
    public Metodo ejecutarSentencias(Nodo Sentencias){
        for (Nodo sentencia : Sentencias.hijos) {
            switch(sentencia.nombre){
                case "declara_var_L":
                case "declara_vecF1_L":
                case "declara_vecF2_L":
                case "asignacionLocal":
                case "asigna_vecLocalF2":    
                    new Declaracion(sentencia, global, tabla);
                    break;
                case "llamadaFuncion":
                    opL = new OperacionesARL(global, tabla);
                    opL.acceso(sentencia);
                    break;    
                case "retornar":
                    Retornar retorno = new Retornar();
                    //System.out.println("[ignore]estoy entrando aretornars");
                    metodoActual = retorno.ejecutar(sentencia);
                    //System.out.println(metodoActual.retorno.valor);
                    return metodoActual;
                case "si":
                    Si si = new Si();
                    metodoActual = si.ejecutar(sentencia);
                    if (metodoActual.estadoRetorno) {
                        return metodoActual;
                    }
                    if (metodoActual.estadoTerminar) {
                        //metodoActual.estadoTerminar=false;
                        return metodoActual;
                    }

                    if (metodoActual.estadoContinuar) {
                        return metodoActual;
                    }
                    break;
                case "selecciona":
                    break;
                case "mientras":
                    Mientras mientras = new Mientras();
                    nivelCiclo++;
                    metodoActual = mientras.ejecutar(sentencia);
                    if (metodoActual.estadoRetorno) {
                        nivelCiclo--;
                        return metodoActual;
                    }
                    nivelCiclo--;
                    break;
                case "imprimir":
                    opL = new OperacionesARL(global,tabla);
                    ResultadoG rs = opL.ejecutar(sentencia.hijos.get(0));
                    try {
                        System.out.println(rs.valor);
                    } catch (Exception e) {
                    }
                    
                    break;
            }
        }
        return metodoActual;
    }
}
