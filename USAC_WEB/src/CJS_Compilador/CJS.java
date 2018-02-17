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

public class CJS {
    
    
    public void ejecucionCJS(Nodo raiz){
        //aqui habra una lista de archivos cjs que pertenecen a el html
        //como hay una lista de archivos cjs debe haber una lista de CLASES (JAVASCRIPT)
        //##############Se crear una nueva CLASE
        Clase n_clase = new Clase(raiz);
        n_clase.ejecutar();
        
        System.out.println("sss");
        
    }
}
