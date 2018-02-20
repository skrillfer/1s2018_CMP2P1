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
public class Retornar extends Compilador{

    @Override
    public Metodo ejecutar(Nodo raiz) {
        if (raiz.hijos.size() > 0) {
            opL = new OperacionesARL(global, tabla);
            ResultadoG retorno = opL.ejecutar(raiz.hijos.get(0));
            metodoActual.retorno = retorno;
            metodoActual.estadoRetorno = true;
        } else {
            metodoActual.retorno = null;
            metodoActual.estadoRetorno = true;
        }
        return metodoActual;
    }
    
}
