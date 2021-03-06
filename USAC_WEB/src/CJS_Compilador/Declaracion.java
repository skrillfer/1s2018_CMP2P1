/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CJS_Compilador;

import CJS_Compilador.OperacionesARL.OperacionesARL;
import Estructuras.Nodo;
import Interfaz.Template;

/**
 *
 * @author fernando
 */
public class Declaracion extends Compilador{
    TablaSimboloG tabla;
    TablaSimboloG global;

    //* cuando DECLARO PARAMETROS USO ESTE CONSTRUCTOR
    public Declaracion(Nodo raiz, ResultadoG resultado, TablaSimboloG global, TablaSimboloG tabla,Template template1) {
        this.miTemplate=template1;
        this.raiz = raiz;
        this.global = global;
        this.tabla = tabla;
        declararParametro(resultado);
    }
    
    //* cuando hago una declaracion simple USO este CONSTRUCTOR
    public Declaracion(Nodo raiz, TablaSimboloG global, TablaSimboloG tabla,Template template1) {
        this.miTemplate=template1;
        this.raiz = raiz;
        this.global = global;//tabla de variables locales
        this.tabla = tabla;//tabla de variables globales
        opL = new OperacionesARL(global, tabla,miTemplate);
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
        //System.out.println(raiz.nombre);
        switch (raiz.nombre) {
            //********************  AMBITO GLOBAL ******************************
            case "declara_var":
                try {
                    declara_var();
                } catch (Exception e) {
                }
                
                break;
            case "declara_vecF1":
                try {
                    declara_vecF1();
                } catch (Exception e) {
                }
                
                break;
            case "declara_vecF2":
                try {
                    declara_vecF2();
                } catch (Exception e) {
                }
                break;    
            case "asigna_vecGlbF1":
                try {
                    asigna_vecGlbF1();
                } catch (Exception e) {
                }
                break;
            case "asigna_vecGlbF2":
                try {
                    asigna_vecGlbF2();
                } catch (Exception e) {
                }
                break;
            case "asignacionGlb":
                try {
                    asignacionGlb();
                } catch (Exception e) {
                }
                break;
            //********************   AMBITO LOCAL ******************************    
            case "declara_var_L":  
                try {
                    declara_var_L();
                } catch (Exception e) {
                }
                break;
            case "declara_vecF1_L":
                try {
                    declara_vecF1_L();
                } catch (Exception e) {
                }
                break;
            case "declara_vecF2_L":
                try {
                    declara_vecF2_L();
                } catch (Exception e) {
                }
                break;
            case "asigna_vecLocalF1":
                try {
                    asigna_vecLocalF1();
                } catch (Exception e) {
                }
                break;
            case "asigna_vecLocalF2":
                try {
                    asigna_vecLocalF2();
                } catch (Exception e) {
                }
                break;
            case "asignacionLocal":
                try {
                    asignacionLocal();
                } catch (Exception e) {
                }
                break;
            default:
                System.out.println(raiz.nombre);
                break;
        }

        return null;
    }
    
    
    public void declara_var(){
        String tipo = "";//el tipo de la  variable depende del valor que tenga
        String nombre= raiz.hijos.get(0).valor;//se obtiene el nombre de la variable a declarar
        
        
        if(raiz.hijos.get(1).hijos.size()>0){
            Nodo exp = raiz.hijos.get(1).hijos.get(0);//se obtiene el nodo de la expresion
            //----------ejecuto la parte de la expresion
            System.out.println("ASIGNO a " + nombre);
            ResultadoG resultado = opL.ejecutar(exp);
            
            if(resultado!=null ){
                // la variable toma el tipo del valor que le es asignado
                tipo=resultado.tipo;
                SimboloG simbolo = new SimboloG(tipo, nombre, "", resultado.valor);
                simbolo.inicializado = true;
                if (!global.setSimbolo(simbolo)) {
                    Template.reporteError_CJS.agregar("Error Semantico",raiz.linea, raiz.columna,"La variable " + nombre + " ya existe");
                }
            }else{
                SimboloG simbolo = new SimboloG("", nombre, "", null);
                if (!global.setSimbolo(simbolo)) {
                    Template.reporteError_CJS.agregar("Error Semantico",raiz.linea, raiz.columna,"La variable " + nombre + " ya existe");
                }/*else{
                    System.out.println("se agrego correctamente  "+simbolo.nombre);
                }*/
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
        Arreglo arreglo = new Arreglo(raiz, global, tabla,miTemplate);
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
        Arreglo arreglo = new Arreglo(raiz,tabla,global,0,miTemplate);
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
            Arreglo arr1 = new Arreglo(raiz, global, tabla, arreglo.dimensiones,miTemplate,0);
            //************como es de una sola dimension
            // si la cantidad de datos es menor o igual al tamanio unidimensional
            if(arr1.getDatos().size()<=arreglo.getDatos().size()){
                try {
                    
                    for (int x = 0; x < arr1.getDatos().size(); x++) {
                        arreglo.getDatos().set(x, arr1.getDatos().get(x));
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                
            }else{
                
            }
            
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
            if(resultado!=null){
                if(!resultado.tipo.equals("")){
                    sim.inicializado=true;
                    if(resultado.simbolo!=null){
                        if(resultado.simbolo.esArreglo==false){
                            sim.tipo=resultado.tipo;
                            sim.valor=resultado.valor;
                        }else{
                            sim.esArreglo=true;
                            sim.tipo=resultado.tipo;
                            sim.valor=resultado.valor;
                        }
                    }else {
                        sim.tipo=resultado.tipo;
                        sim.valor=resultado.valor;
                    }
                }
            }
        }else{
           Template.reporteError_CJS.agregar("Semantico", raiz.hijos.get(0).linea, raiz.hijos.get(0).columna, "La variable " + nombre + " no existe en el ambito donde fue invocada");
        }
    }
   
    
    /***********************    AMBITO LOCAL                    ***************/
      public void asignacionLocal(){
        String nombre = raiz.hijos.get(0).valor;
        SimboloG sim = tabla.getSimbolo(nombre, CJS.claseActual);
        ResultadoG resultado = opL.ejecutar(raiz.hijos.get(1));// se obtiene el valor a asignar
        if(sim != null){
            //System.out.println("asignare:"+nombre);

            if(resultado!=null){                
                //System.out.println("        asignare:"+nombre+" Tipo:"+resultado.tipo + " Esarreglo:"+resultado.valor);
                if(!resultado.tipo.equals("")){
                    System.out.println("\n\n\n\n\nASIGNO a "+sim.nombre + "  -> " + resultado.tipo);
                    sim.inicializado=true;
                    if(resultado.simbolo!=null){
                        if(resultado.simbolo.esArreglo==false){
                            sim.tipo=resultado.tipo;
                            sim.valor=resultado.valor;
                        }else{
                            sim.esArreglo=true;
                            sim.tipo=resultado.tipo;
                            sim.valor=resultado.valor;
                        }
                    }else {
                        sim.tipo=resultado.tipo;
                        sim.valor=resultado.valor;
                    }
                }
            }
            
        }else{
            Template.reporteError_CJS.agregar("Semantico", raiz.hijos.get(0).linea, raiz.hijos.get(0).columna, "La variable " + nombre + " no existe en el ambito donde fue invocada");
        }
    }
      
    public void declara_var_L(){
        String tipo = "";//el tipo de la  variable depende del valor que tenga
        String nombre= raiz.hijos.get(0).valor;//se obtiene el nombre de la variable a declarar
        
        if(raiz.hijos.get(1).hijos.size()>0){// si tiene asignacion
            Nodo exp = raiz.hijos.get(1).hijos.get(0);//se obtiene el nodo de la expresion
            //----------ejecuto la parte de la expresion
            ResultadoG resultado = opL.ejecutar(exp);
            System.out.println("declarare:"+nombre);
            if(resultado!=null ){
                // la variable toma el tipo del valor que le es asignado
                tipo=resultado.tipo;
                SimboloG simbolo = new SimboloG(tipo, nombre, "", resultado.valor);
                simbolo.inicializado = true;

                if (!tabla.setSimbolo(simbolo)) {
                    Template.reporteError_CJS.agregar("Error Semantico",raiz.linea, raiz.columna,"La variable " + nombre + " ya existe");
                }else{
                    System.out.println("se agrego correctamente  "+simbolo.nombre+"---"+simbolo.tipo);
                }
                
            }else{
                SimboloG simbolo = new SimboloG("", nombre, "", null);
                if (!tabla.setSimbolo(simbolo)) {
                    Template.reporteError_CJS.agregar("Error Semantico",raiz.linea, raiz.columna,"La variable " + nombre + " ya existe");
                }else{
                    System.out.println("se agrego correctamente  "+simbolo.nombre);
                }
            }
        }else{// no tiene asignacion
            SimboloG simbolo = new SimboloG(tipo, nombre, "", null);
            if (!tabla.setSimbolo(simbolo)) {
                Template.reporteError_CJS.agregar("Error Semantico",raiz.linea, raiz.columna,"La variable " + nombre + " ya existe");
            }/*else{
                System.out.println("se agrego correctamente  "+simbolo.nombre);
            }*/
        }
    }
    
    public void declara_vecF1_L(){
        String nombre = raiz.hijos.get(0).valor;
        String tipo="";
        Arreglo arreglo = new Arreglo(raiz, global, tabla,miTemplate);
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
        Arreglo arreglo = new Arreglo(raiz,tabla,global,0,miTemplate);
        SimboloG simbolo = new SimboloG(tipo,nombre,arreglo);
        simbolo.inicializado = true;
        
        if (!tabla.setSimbolo(simbolo)) {
            //Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe");
        }
    }
    
    public void asigna_vecLocalF1(){
        String nombre = raiz.hijos.get(0).valor;
        String tipo="";
        
        SimboloG sim = tabla.getSimbolo(nombre, CJS.claseActual);
        if(sim.esArreglo){
            
            Arreglo arreglo = (Arreglo)sim.valor;
            Arreglo arr1 = new Arreglo(raiz, global, tabla, arreglo.dimensiones,miTemplate,0);
            //************como es de una sola dimension
            // si la cantidad de datos es menor o igual al tamanio unidimensional
            if(arr1.getDatos().size()<=arreglo.getDatos().size()){
                try {
                    
                    for (int x = 0; x < arr1.getDatos().size(); x++) {
                        arreglo.getDatos().set(x, arr1.getDatos().get(x));
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                
            }else{
                
            }
            
        }
    }
    
    
    
    public void asigna_vecLocalF2(){
        String nombre = raiz.hijos.get(0).valor;
        String tipo="";
        
        SimboloG sim = tabla.getSimbolo(nombre, CJS.claseActual);// SE OBTIENE EL ARRGELO COMO TAL
       
        ResultadoG valor =opL.ejecutar(raiz.hijos.get(2));// se obitene el nodo que contiene EXPR  se ejecuta
        
        if(sim.esArreglo){
            Arreglo arreglo = (Arreglo)sim.valor;
            
            if(valor!=null){
                arreglo.setValor(raiz.hijos.get(1).hijos.get(0),valor);// envio el (Nodo)indice y el (ResultadoG)valor
            }
        }
    }
    
    
    @Override
    public Metodo ejecutar(Nodo raiz) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
