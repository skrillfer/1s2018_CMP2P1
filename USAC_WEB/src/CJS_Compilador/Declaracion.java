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
        opL = new OperacionesARL(global, tabla);
        declarar();
    }
    
    public Object declarar() {
        switch (raiz.nombre) {
            
            case "declara_var":
                declara_var();
                break;
            case "declara_vecF1":
                declara_vecF1();
                break;
            case "declara_vecF2":
                declara_vecF2();
                break;
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
    
    
    public void declara_var(){
        String tipo = "";//el tipo de la  variable depende del valor que tenga
        String nombre= raiz.hijos.get(0).valor;//se obtiene el nombre de la variable a declarar
        
        
        if(raiz.hijos.get(1).hijos.size()>0){
            Nodo exp = raiz.hijos.get(1).hijos.get(0);//se obtiene el nodo de la expresion
            //----------ejecuto la parte de la expresion
            ResultadoG resultado = opL.ejecutar(exp);
            if(resultado!=null ){
                // la variable toma el tipo del valor que le es asignado, de ahi en adelante no cambia
                tipo=resultado.tipo;
                SimboloG simbolo = new SimboloG(tipo, nombre, "", resultado.valor);
                simbolo.inicializado = true;
                if (!global.setSimbolo(simbolo)) {
                    System.out.println("Agregado el simbolo correctamente");
                    //Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe");
                }
            }
        }else{
            SimboloG simbolo = new SimboloG(tipo, nombre, "", null);
            if (!global.setSimbolo(simbolo)) {
                //Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe");
            }
        }
    }
    
    // el vector se crea con valores declarados
    public void declara_vecF1(){
        String nombre = raiz.hijos.get(0).valor;
        String tipo="";
        Arreglo arreglo = new Arreglo(raiz, global, tabla);
        if (arreglo.estado) {
            SimboloG simbolo = new SimboloG(tipo, nombre, "", arreglo);
            simbolo.inicializado = true;
            if (!global.setSimbolo(simbolo)) {
                //Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe");
            }
        }
    }
    // el vector se crea con una dimension establecida
    public void declara_vecF2(){
        String nombre = raiz.hijos.get(0).valor;
        String tipo = "";
        Arreglo arreglo = new Arreglo(raiz,tabla,global,0);
        SimboloG simbolo = new SimboloG(tipo,nombre,arreglo);
        simbolo.inicializado = true;
        
        if (!tabla.setSimbolo(simbolo)) {
            //Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe");
        }
    }
    
    @Override
    public Metodo ejecutar(Nodo raiz) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
