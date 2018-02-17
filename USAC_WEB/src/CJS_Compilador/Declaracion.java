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
public class Declaracion extends Compilador{
    TablaSimboloG tabla;
    TablaSimboloG global;

    public Declaracion(Nodo raiz, TablaSimboloG global, TablaSimboloG tabla) {
        this.raiz = raiz;
        this.global = global;//tabla de variables locales
        this.tabla = tabla;//tabla de variables globales
        //opL = new OperacionesARL(global, tabla);
        declarar();
    }
    
    public Object declarar() {
        switch (raiz.nombre) {
            /*case "atributoVarDA"://atributo tipo entero,cadena... declarado y asignado
                return atributoVarDA();
            case "atributoVarD"://atributo tipo entero,cadena... declarado
                return atributoVarD();
            case "atributoAlsDI"://atributo tipo Als declarado e instanciado
                return atributoAlsDI();
            case "atributoAlsD"://atributo tipo Als declarado
                return atributoAlsD();
            case "atributoVarArDA"://atributo arreglo entero,cadena.. declarado y asignado
                return atributoVarArDA();
            case "atributoVarArD"://atributo arreglo entero,cadena.. declarado
                return atributoVarArD();
            case "varLocalD"://variable local tipo,entero,cadena... declarada
                return varLocalD();
            case "varLocalDA"://variable local tipo,entero,cadena... declarada y asignada
                return varLocalDA();
            case "varLocalAlsD"://variable local Als... declarada
                return varLocalAlsD();
            case "varLocalAlsDI"://variable local Als... declarada e instanciada
                return varLocalAlsDI();
            case "varLocalArD"://variable arreglo local tipo cadena,numero... declarada
                return varLocalArD();
            case "varLocalArDA"://variable arreglo local tipo cadena,numero... declarada y asignada
                return varLocalArDA();

            //direccionamientos
            case "atributoVarArDD"://atributo arreglo direccionado
                return atributoVarArDD();
            case "atributoAlsDD"://atributo als direccionado
                return atributoAlsDD();
            case "varLocalAlsDD"://variable local Als... direccionada
                return varLocalAlsDD();
            case "varLocalArDD"://variable arreglo local tipo cadena,numero... direccionada
                return varLocalArDD();*/
        }

        return null;
    }
    
    @Override
    public Metodo ejecutar(Nodo raiz) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
