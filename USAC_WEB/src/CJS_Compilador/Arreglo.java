/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CJS_Compilador;

import java.util.ArrayList;
import CJS_Compilador.OperacionesARL.*;
import Estructuras.Nodo;
import Interfaz.Template;
/**
 *
 * @author fernando
 */
public class Arreglo {
    public ArrayList<Integer> dimensiones;
    private ArrayList<Object> datos;
    private TablaSimboloG global;
    private TablaSimboloG tabla;
    public boolean estado = true;
    private  OperacionesARL opL;
    public Template miTemplate;
    
    public Arreglo() {
        dimensiones = new ArrayList<>();
        datos = new ArrayList<>();
    }
    
    //****asignando valores a un vector EXISTENTE
    public Arreglo(Nodo raiz, TablaSimboloG global, TablaSimboloG tabla, ArrayList<Integer> dimensiones,Template template1) {
        this.miTemplate=template1;
        this.dimensiones = dimensiones;
        datos = new ArrayList<>();
        this.global = global;
        this.tabla = tabla;
        opL = new OperacionesARL(global, tabla,miTemplate);
        guardarValores2(raiz);
    }
    //*****creando VECTOR con una lista de VALORES
    public Arreglo(Nodo raiz, TablaSimboloG global, TablaSimboloG tabla,Template template1) {
        this.miTemplate=template1;
        dimensiones = new ArrayList<>();
        datos = new ArrayList<>();
        this.global = global;
        this.tabla = tabla;
        opL = new OperacionesARL(global, tabla,miTemplate);
        guardarValores(raiz);
    }

    //*****creando un VECTOR sin valores pero con TAM establecido
    public Arreglo(Nodo raiz, TablaSimboloG global, TablaSimboloG tabla, int n,Template template1) {
        this.miTemplate=template1;
        dimensiones = new ArrayList<>();
        datos = new ArrayList<>();
        this.global = global;
        this.tabla = tabla;
        opL = new OperacionesARL(global, tabla,miTemplate);
        guardarDimensiones(raiz);
        for (int i = 0; i < dimensiones.size(); i++) {
            int tam = dimensiones.get(i);
            for (int j = 0; j < tam; j++) {
                datos.add(null);
            }
        }
    }
    
    public void setDatos(ArrayList<Object> datos){
        this.datos=datos;
    }
    public ArrayList<Object> getDatos() {
        return datos;
    }

    
    private void guardarDimensiones(Nodo raiz) {
        ArrayList<Nodo> dim = raiz.hijos.get(1).hijos;
        int total = 1;
        for (Nodo hijo : dim) {
            ResultadoG dimension = opL.ejecutar(hijo);
            
            if (dimension.tipo.equalsIgnoreCase("number")) {
                total = total* ((Double) dimension.valor).intValue();
                dimensiones.add( ((Double) dimension.valor).intValue());
            } else {
                //Inicio.reporteError.agregar("Semantico", hijo.linea, hijo.columna, "Solo se permiten valores enteros para los indices de un arreglo");
                estado = false;
            }
        }
    }
    
    public void guardarValores(Nodo raiz) {
        ArrayList<Nodo> dim = null;
        ArrayList<Nodo> val = null;
        String tipo = "";
        switch (raiz.hijos.size()) {
            
            case 2:
                val = raiz.hijos.get(1).hijos;//modifique para la nueva version
                break;
            default:
                break;
        }
        
        int total = 0;        
        for (Nodo hijo : val) {
            ResultadoG resultado = opL.ejecutar(hijo);
            if (resultado != null) {
                total++;
                datos.add(resultado);
            }
        }
        if(dimensiones.isEmpty()){
            dimensiones.add(total);
        }
        
    }
    
    public void guardarValores2(Nodo raiz){
        ArrayList<Nodo> val = raiz.hijos.get(1).hijos;// (LISTA_NODO)valores        
        if(val.size()<=dimensiones.get(0)){
            for (int i = 0; i < val.size(); i++) {
                Nodo hijo = val.get(i);
                ResultadoG resultado = opL.ejecutar(hijo);
                if (resultado != null) {
                    datos.set(i,resultado);
                }
            }
        }
    }
    
    public boolean setValor(Nodo indice, ResultadoG dato) {
        ResultadoG res = opL.ejecutar(indice);
        if(res.tipo.equals("number")){
            int posicion = ((Double)res.valor).intValue();
            
            if (posicion <= (datos.size()-1) && posicion >= 0) {
                datos.set(posicion, dato);
                return true;
            }
        }
        return false;
    }
    
    //obtene la cantidad de DATOS (no null) que tiene el arreglo
    public Double funcion_Conteo(){
        Double tam=0.0;
        for (int i = 0; i < datos.size(); i++) {
            if(datos.get(i)!=null){
                tam++;
            }
        }
        return tam;
    }
    //todo el contenido del vector es convertido a STRING
    public String funcion_aTexto(){
        String data="";
        for (int i = 0; i < datos.size(); i++) {
            Object dato1 = datos.get(i);
            ResultadoG  dato = (ResultadoG)dato1;
            if(dato!=null){
                String tipo = dato.tipo;
                //System.out.println("tipo del dato===>"+tipo);
                if(tipo.equals("number")){
                    data += String.valueOf(((Double)dato.valor));
                }else if(tipo.equals("boolean")){
                    data += Boolean.toString((boolean)dato.valor);
                }else if(tipo.equals("string")){
                    data += (String)dato.valor;
                }else if(tipo.equals("date") || tipo.equals("datetime")){
                    data += (String)dato.valor;
                }
            }
        }
        return data;
    }
    
    public Object getValor(ArrayList<Integer> indices) {
        int indice = indices.get(0);
        if ((indice + 1) <= datos.size() && indice >= 0) {
            return datos.get(indice);
        } else {
            //indice incorrecto
            return null;
        }
    }
}
