/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CJS_Compilador;
import Estructuras.*;
/**
 *
 * @author fernando
 */
public class Compilador {
    public void ejecutarSentencias(Nodo Sentencias){
        for (Nodo sentencia : Sentencias.hijos) {
            switch(sentencia.nombre){
                case "declara_var":
                    
            }
        }
    }
}
