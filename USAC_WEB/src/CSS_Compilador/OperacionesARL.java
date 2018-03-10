/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CSS_Compilador;
import CJS_Compilador.ResultadoG;
import Estructuras.Nodo;
import Estructuras.NodoCSS;
import Interfaz.Template;
/**
 *
 * @author fernando
 */
public class OperacionesARL {
    int fila1=0;
    int columna1=0;
    
    int fila2=0;
    int columna2=0;
    public Template template1;

    public OperacionesARL(Template template1) {
        this.template1=template1;
    }
    
    
    public ResultadoG ejecutar(NodoCSS nodo){
        ResultadoG result= null;
        switch(nodo.nombre){
            //-- operaciones aritmeticas 
            case "MAS":
                fila1=nodo.hijos.get(0).linea;  columna1=nodo.hijos.get(0).columna; fila2=nodo.hijos.get(1).linea;  columna2=nodo.hijos.get(1).columna;
                try {
                    ResultadoG rr = operacionesAritmeticas(ejecutar(nodo.hijos.get(0)),ejecutar(nodo.hijos.get(1)), "MAS");
                    result=rr;
                } catch (Exception e) {}
                break;
            case "MENOS":
                fila1=nodo.hijos.get(0).linea;  columna1=nodo.hijos.get(0).columna; fila2=nodo.hijos.get(1).linea;  columna2=nodo.hijos.get(1).columna;
                try {
                    ResultadoG rr = operacionesAritmeticas(ejecutar(nodo.hijos.get(0)),ejecutar(nodo.hijos.get(1)), "MENOS");
                    result=rr;
                } catch (Exception e) {}
                break;
            case "POR":
                fila1=nodo.hijos.get(0).linea;  columna1=nodo.hijos.get(0).columna; fila2=nodo.hijos.get(1).linea;  columna2=nodo.hijos.get(1).columna;
                try {
                    ResultadoG rr = operacionesAritmeticas(ejecutar(nodo.hijos.get(0)),ejecutar(nodo.hijos.get(1)), "POR");
                    result=rr;
                } catch (Exception e) {}
                break;
            case "DIV":
                fila1=nodo.hijos.get(0).linea;  columna1=nodo.hijos.get(0).columna; fila2=nodo.hijos.get(1).linea;  columna2=nodo.hijos.get(1).columna;
                try {
                    ResultadoG rr = operacionesAritmeticas(ejecutar(nodo.hijos.get(0)),ejecutar(nodo.hijos.get(1)), "DIV");
                    result=rr;
                } catch (Exception e) {}
                break;
            //-- VALORES DE ALINEADO 
            case "izquierda":
                result = new ResultadoG("izquierda", nodo);
                break;
            case "derecha":
                result = new ResultadoG("derecha", nodo);
                break;
            case "centrado":
                result = new ResultadoG("centrado", nodo);
                break;    
            case "justificado":
                result = new ResultadoG("justificado", nodo);
                break;   
            //-- FUENTE
            case "mayuscula":
                result = new ResultadoG("mayuscula", nodo);
                break;       
            case "minuscula":
                result = new ResultadoG("minuscula", nodo);
                break;    
            case "capital-t":
                result = new ResultadoG("capital-t", nodo);
                break;        
            case "negrilla":
                result = new ResultadoG("negrilla", nodo);
                break;        
            case "cursiva":
                result = new ResultadoG("cursiva", nodo);
                break;        
            //-- valores de de datos
            case "string_literal":
                result = new ResultadoG("string", nodo.valor);
                break;                
            case "number":
                Double db = Double.parseDouble((String) nodo.valor);
                result = new ResultadoG("number", db);
                break;
            case "boolean":
                String dd=(String)nodo.valor;
                if(dd.equals("true")){
                    result = new ResultadoG("boolean",true);
                }else{
                    result = new ResultadoG("boolean",false);
                }
                
                break;    
        }
        return result;
    }
    
    
    public ResultadoG operacionesAritmeticas(ResultadoG r1, ResultadoG r2, String op){
        if(verNulabilidad(r1, r2)){
            return null;
        }
        
        ResultadoG result = null;
        Object valor = new Object();
        if(op.equals("MAS")){
            switch (r1.tipo) {
                case "number":
                    switch (r2.tipo) {
                        case "boolean":
                            valor = (Double)r1.valor + getBoolValor(r2.valor);
                            result = new ResultadoG("number", valor );
                            break;
                        case "number":
                            valor = (Double)r1.valor + (Double)r2.valor;
                            result = new ResultadoG("number", valor );
                            break;
                        case "string":
                            valor = (Double)r1.valor + (String)r2.valor;
                            result = new ResultadoG("string", valor );
                            break;
                        default:
                            template1.addError(fila2, columna2, r2.tipo, "Error Semantico, el tipo "+r2.tipo+" no es aplicaba en suma", "LenguajeCCSS");
                            break;
                    }
                    break;
                case "string":
                    switch (r2.tipo) {
                        case "boolean":
                            valor = (String)r1.valor + (r2.valor+"");
                            result = new ResultadoG("string", valor );
                            break;
                        case "number":
                            valor = (String)r1.valor + (Double)r2.valor;
                            result = new ResultadoG("string", valor);
                            break;
                        case "string":
                            valor = (String)r1.valor + (String)r2.valor;
                            result = new ResultadoG("string", valor);
                            break;
                        default:
                            template1.addError(fila2, columna2, r2.tipo, "Error Semantico, el tipo "+r2.tipo+" no es aplicaba en suma", "LenguajeCCSS");
                            break;
                    }
                    break;
                case "boolean":
                    switch (r2.tipo) {
                        case "boolean":
                            valor=getBoolValor(r1.valor) + getBoolValor(r2.valor);
                            if((int)valor==2 || (int)valor ==1){
                                result = new ResultadoG("boolean", true);
                            }else{
                                result = new ResultadoG("boolean", false);
                            }
                            break;
                        case "number":
                            valor = getBoolValor(r1.valor) + (Double) r2.valor;
                            result = new ResultadoG("number", valor);
                            break;
                        case "string":
                            valor = (r1.valor+"") + (String)r2.valor;
                            result = new ResultadoG("string", valor );
                            break;
                        default:
                            template1.addError(fila2, columna2, r2.tipo, "Error Semantico, el tipo "+r2.tipo+" no es aplicaba en suma", "LenguajeCCSS");
                            break;
                    }
                    break;
                default:
                    template1.addError(fila1, columna1, r1.tipo, "Error Semantico, el tipo "+r1.tipo+" no es aplicaba en suma", "LenguajeCCSS");
                    break;
            }
        }else if(op.equals("MENOS")){
            switch (r1.tipo) {
                case "number":
                    switch (r2.tipo) {
                        case "boolean":
                            valor = (Double)r1.valor - getBoolValor(r2.valor);
                            result = new ResultadoG("number", valor );
                            break;
                        case "number":
                            valor = (Double)r1.valor - (Double)r2.valor;
                            result = new ResultadoG("number", valor );
                            break;
                        default:
                            template1.addError(fila2, columna2, r2.tipo, "Error Semantico, el tipo "+r2.tipo+" no es aplicaba en resta", "LenguajeCCSS");
                            break;
                    }
                    break;
                default:    
                    template1.addError(fila1, columna1, r1.tipo, "Error Semantico, el tipo "+r1.tipo+" no es aplicaba en resta", "LenguajeCCSS");
                    break;
            }
        }else if (op.equals("POR")){
            switch (r1.tipo) {
                case "number":
                    switch (r2.tipo) {
                        case "boolean":
                            valor = (Double)r1.valor * getBoolValor(r2.valor);
                            result = new ResultadoG("number", valor );
                            break;
                        case "number":
                            valor = (Double)r1.valor * (Double)r2.valor;
                            result = new ResultadoG("number", valor );
                            break;
                        default:
                            template1.addError(fila2, columna2, r2.tipo, "Error Semantico, el tipo "+r2.tipo+" no es aplicaba en multiplicacion", "LenguajeCCSS");
                            break;
                    }
                    break;
                case "boolean":
                    switch (r2.tipo) {
                        case "boolean":
                            valor=getBoolValor(r1.valor) + getBoolValor(r2.valor);
                            if((int)valor==2 ){
                                result = new ResultadoG("boolean", true);
                            }else{
                                result = new ResultadoG("boolean", false);
                            }
                            break;
                        case "number":
                            valor = getBoolValor(r1.valor) * (Double) r2.valor;
                            result = new ResultadoG("number", valor);
                            break;
                        default:
                            template1.addError(fila2, columna2, r2.tipo, "Error Semantico, el tipo "+r2.tipo+" no es aplicaba en multiplicacion", "LenguajeCCSS");
                            break;
                    }  
                    break;
                default:
                    template1.addError(fila1, columna1, r1.tipo, "Error Semantico, el tipo "+r1.tipo+" no es aplicaba en multiplicacion", "LenguajeCCSS");
                    break;
                    
            }
        }else if (op.equals("DIV")){
            switch (r1.tipo) {
                case "number":
                    switch (r2.tipo) {
                        case "boolean":
                            try {
                                valor = (Double)r1.valor / getBoolValor(r2.valor);
                            } catch (Exception e) {
                                template1.addError(fila2, columna2, r2.tipo, "Error Semantico, division entre 0", "LenguajeCCSS");
                                return new ResultadoG("-1",valor);
                            }
                            
                            result = new ResultadoG("number", valor );
                            break;
                        case "number":
                            try {
                                valor = (Double)r1.valor / (Double)r2.valor;
                            } catch (Exception e) {
                                template1.addError(fila2, columna2, r2.tipo, "Error Semantico, division entre 0", "LenguajeCCSS");
                                return new ResultadoG("-1",valor);
                            }
                            
                            result = new ResultadoG("number", valor );
                            break;
                        default:
                            template1.addError(fila2, columna2, r2.tipo, "Error Semantico, el tipo "+r2.tipo+" no es aplicaba en division", "LenguajeCCSS");
                            break;
                    }
                    break;
                case "boolean":
                    switch (r2.tipo) {
                        case "number":
                            try {
                                valor = getBoolValor(r1.valor) / (Double) r2.valor;
                            } catch (Exception e) {
                                template1.addError(fila2, columna2, r2.tipo, "Error Semantico, division entre 0", "LenguajeCCSS");
                                return new ResultadoG("-1",valor);
                            }
                            result = new ResultadoG("number", valor);
                            break;
                        default:
                            template1.addError(fila2, columna2, r2.tipo, "Error Semantico, el tipo "+r2.tipo+" no es aplicaba en division", "LenguajeCCSS");
                            break;
                    }
                    break;
                default:
                    template1.addError(fila1, columna1, r1.tipo, "Error Semantico, el tipo "+r1.tipo+" no es aplicaba en division", "LenguajeCCSS");
                    break;
                
            }
        }
        
        return result;
    }
    
    
    public boolean verNulabilidad(ResultadoG r1, ResultadoG r2){
        if(r1 != null || r2!=null){
            if(r1.valor!=null || r2.valor!=null){
                return false;
            }
            return false;
        }else{
            return true;
        }
    }
    
    public int getBoolValor(Object objeto) {
        Boolean valor = (Boolean) objeto;
        if (valor) {
            return 1;
        } else {
            return 0;
        }
    }
    
    public int getStringValor(Object objeto, Nodo raiz) {
        String valor = (String) objeto;
        try {
            return Integer.parseInt(valor);
        } catch (Exception e) {
            //Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se pudo castear la cadena a tipo Bool");
            return -1;
        }
    }
    
}
