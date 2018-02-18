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

public class CJS extends Compilador{
    
    
    public void ejecucionCJS(Nodo raiz){
        //aqui habra una lista de archivos cjs que pertenecen a el html
        //como hay una lista de archivos cjs debe haber una lista de CLASES (JAVASCRIPT)
        //##############Se crear una nueva CLASE
        raiz.valor="clase1";
        Clase n_clase = new Clase(raiz);
        
        claseActual=n_clase;
        n_clase.ejecutar();
        
        System.out.println("");
        
    }

    @Override
    public Metodo ejecutar(Nodo raiz) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
