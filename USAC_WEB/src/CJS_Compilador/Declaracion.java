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

    //* cuando DECLARO PARAMETROS USO ESTE CONSTRUCTOR
    public Declaracion(Nodo raiz, ResultadoG resultado, TablaSimboloG global, TablaSimboloG tabla) {
        this.raiz = raiz;
        this.global = global;
        this.tabla = tabla;
        declararParametro(resultado);
    }
    
    //* cuando hago una declaracion simple USO este CONSTRUCTOR
    public Declaracion(Nodo raiz, TablaSimboloG global, TablaSimboloG tabla) {
        this.raiz = raiz;
        this.global = global;//tabla de variables locales
        this.tabla = tabla;//tabla de variables globales
        opL = new OperacionesARL(global, tabla);
        declarar();
    }
    
    private SimboloG declararParametro(ResultadoG resultado) {

        switch (raiz.nombre) {
            case "parametro":
                return parametro(resultado);
            case "parametroAr":
                //return parametroAr(resultado);
        }

        return null;
    }
    
    private SimboloG parametro(ResultadoG resultado) {
        String nombre = raiz.hijos.get(0).valor;
        
        if (resultado != null) {
            String tipo = resultado.tipo;
            SimboloG simbolo = new SimboloG(tipo, nombre, resultado.valor);
            simbolo.inicializado = true;
            if (!tabla.setSimbolo(simbolo)) {
                //Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe");
            }
        } else {

        }
        return null;
    }
    
    public Object declarar() {
        switch (raiz.nombre) {
            //********************  AMBITO GLOBAL ******************************
            case "declara_var":
                declara_var();
                break;
            case "declara_vecF1":
                declara_vecF1();
                break;
            case "declara_vecF2":
                declara_vecF2();
                break;    
            case "asigna_vecGlbF1":
                asigna_vecGlbF1();
                break;
            case "asigna_vecGlbF2":
                asigna_vecGlbF2();
                break;
            case "asignacionGlb":
                asignacionGlb();
                break;
            //********************   AMBITO LOCAL ******************************    
            case "declara_var_L":  
                declara_var_L();
                break;
            case "declara_vecF1_L":
                declara_vecF1_L();
                break;
            case "declara_vecF2_L":
                declara_vecF2_L();
                break;
            case "asigna_vecLocalF1":
                asignacionLocal();
                break;
            case "asigna_vecLocalF2":

                break;
            case "asignacionLocal":
                asignacionLocal();
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
        
        if (!global.setSimbolo(simbolo)) {
            //Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe");
        }
    }
    
    public void asigna_vecGlbF1(){
        String nombre = raiz.hijos.get(0).valor;
        String tipo="";
        
        SimboloG sim = global.getSimbolo(nombre, CJS.claseActual);
        if(sim.esArreglo){
            
            Arreglo arreglo = (Arreglo)sim.valor;
            Arreglo arr1 = new Arreglo(raiz, global, tabla, arreglo.dimensiones);
            //************como es de una sola dimension
            // si la cantidad de datos es menor o igual al tamanio unidimensional
            if(arr1.getDatos().size()<=arreglo.getDatos().size()){

                for (int i = 0; i < arr1.getDatos().size(); i++) {
                    arreglo.getDatos().set(i, arr1.getDatos().get(i));
                }
                
            }else{
                System.out.println("cantidad de valores a agregar es mayor a el tamano del vector");
            }
            
            
            System.out.println("");
        }
    }
    
    public void asigna_vecGlbF2(){
        String nombre = raiz.hijos.get(0).valor;
        String tipo="";
        
        SimboloG sim = global.getSimbolo(nombre, CJS.claseActual);// SE OBTIENE EL ARRGELO COMO TAL
       
        ResultadoG valor =opL.ejecutar(raiz.hijos.get(2));// se obitene el nodo que contiene EXPR  se ejecuta
        
        if(sim.esArreglo){
            Arreglo arreglo = (Arreglo)sim.valor;
            
            if(valor!=null){
                arreglo.setValor(raiz.hijos.get(1).hijos.get(0),valor);// envio el (Nodo)indice y el (ResultadoG)valor
            }
            
            
        }
    }
    
    public void asignacionGlb(){
        String nombre = raiz.hijos.get(0).valor;
        SimboloG sim = global.getSimbolo(nombre, CJS.claseActual);
        ResultadoG resultado = opL.ejecutar(raiz.hijos.get(1));// se obtiene el valor a asignar
        if(sim != null){
            //* por lo del tipo de la variable DUDA1
            sim.inicializado=true;
            if(sim.esArreglo == false && resultado!=null){
                sim.tipo=resultado.tipo;
                sim.valor=resultado.valor;
            }
        }else{
            System.out.println("variable no existe");
        }
    }
   
    
    /***********************    AMBITO LOCAL                    ***************/
      public void asignacionLocal(){
        String nombre = raiz.hijos.get(0).valor;
        SimboloG sim = tabla.getSimbolo(nombre, CJS.claseActual);
        ResultadoG resultado = opL.ejecutar(raiz.hijos.get(1));// se obtiene el valor a asignar
        if(sim != null){
            //* por lo del tipo de la variable DUDA1
            sim.inicializado=true;
            if(sim.esArreglo == false && resultado!=null){
                sim.tipo=resultado.tipo;
                sim.valor=resultado.valor;
            }
        }else{
            System.out.println("variable Local no existe");
        }
    }
    public void declara_var_L(){
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
                if (!tabla.setSimbolo(simbolo)) {
                    System.out.println("Agregado el simbolo correctamente");
                    //Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe");
                }
            }
        }else{
            SimboloG simbolo = new SimboloG(tipo, nombre, "", null);
            if (!tabla.setSimbolo(simbolo)) {
                //Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe");
            }
        }
    }
    
    public void declara_vecF1_L(){
        String nombre = raiz.hijos.get(0).valor;
        String tipo="";
        Arreglo arreglo = new Arreglo(raiz, global, tabla);
        if (arreglo.estado) {
            SimboloG simbolo = new SimboloG(tipo, nombre, "", arreglo);
            simbolo.inicializado = true;
            if (!tabla.setSimbolo(simbolo)) {
                //Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe");
            }
        }
    }
    
    
    public void declara_vecF2_L(){
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
