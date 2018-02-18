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
    
    public void ejecutarSentencias(Nodo Sentencias){
        for (Nodo sentencia : Sentencias.hijos) {
            switch(sentencia.nombre){
                case "declara_var":
                    
            }
        }
    }
}
