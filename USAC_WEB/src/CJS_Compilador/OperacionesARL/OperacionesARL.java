/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CJS_Compilador.OperacionesARL;
import Estructuras.*;
import CJS_Compilador.*;
/**
 *
 * @author fernando
 */
public class OperacionesARL {
    
    public ResultadoG ejecutar(Nodo nodo){
        ResultadoG result=null;
        switch(nodo.nombre){
            case "AND":
                break;
            case "OR":
                break;
            case "NOT":
                break;
            case "MENQ":
                break;
            case "MENIQ":
                break;
            case "MAYQ":
                break;
            case "MAYIQ":
                break;
            case "IG_IG":
                break;
            case "DIF":
                break;
            case "ADD":
                break;
            case "SUB":
                break;
            case "POT":
                break;
            case "DIV":
                break;
            case "POR":
                break;
            case "MENOS":
                ResultadoG r_1 = ejecutar(nodo.hijos.get(0));
                ResultadoG r_2 = ejecutar(nodo.hijos.get(1));
                
                break; 
            case "MAS":
                ResultadoG r1 = ejecutar(nodo.hijos.get(0));
                ResultadoG r2 = ejecutar(nodo.hijos.get(1));
                break;    
            case "number":
                result=new ResultadoG("number", nodo.valor);
                break;
            case "string":
                result=new ResultadoG("string", nodo.valor);
                break;
            case "date":
                result=new ResultadoG("date", nodo.valor);
                break;
            case "datetime":
                result=new ResultadoG("datetime", nodo.valor);
                break;
            case "boolean":
                result=new ResultadoG("boolean", nodo.valor);
                break;
        }
        return result;
    }
    public ResultadoG operacionesAritmeticas(ResultadoG r1, ResultadoG r2, String op){
        ResultadoG result = null;
        if(op.equals("MAS")){
            switch (r1.tipo) {
                case "number":
                    switch (r2.tipo) {
                        case "boolean":
                            result = new ResultadoG("number", ( (double)r1.valor) + ((double) r2.valor) );
                            break;
                        case "number":
                            result = new ResultadoG("number", ( (double)r1.valor) + ((double) r2.valor) );
                            break;
                        case "string":
                            result = new ResultadoG("string", ( (String)r1.valor) + ((String) r2.valor) );
                            break;
                        case "date":
                            System.out.println("error");
                            break;
                        case "datetime":
                            System.out.println("error");
                            break;
                    }
                    break;
                case "string":
                    switch (r2.tipo) {
                        case "boolean":
                            result = new ResultadoG("string", ( (String)r1.valor) + ((String) r2.valor) );
                            break;
                        case "number":
                            result = new ResultadoG("string", ( (String)r1.valor) + ((String) r2.valor) );
                            break;
                        case "string":
                            result = new ResultadoG("string", ( (String)r1.valor) + ((String) r2.valor) );
                            break;
                        case "date":
                            System.out.println("error");
                            break;
                        case "datetime":
                            System.out.println("error");
                            break;
                    }
                    break;
                case "date":
                    switch (r2.tipo) {
                        case "boolean":
                            System.out.println("error");
                            break;
                        case "number":
                            System.out.println("error");
                            break;
                        case "string":
                            result = new ResultadoG("string", ( (String)r1.valor) + ((String) r2.valor) );
                            break;
                        case "date":
                            System.out.println("error");
                            break;
                        case "datetime":
                            System.out.println("error");
                            break;
                    }
                    break;
                case "datetime":
                    switch (r2.tipo) {
                        case "boolean":
                            System.out.println("error");
                            break;
                        case "number":
                            System.out.println("error");
                            break;
                        case "string":
                            result = new ResultadoG("string", ( (String)r1.valor) + ((String) r2.valor) );
                            break;
                        case "date":
                            System.out.println("error");
                            break;
                        case "datetime":
                            System.out.println("error");
                            break;
                    }
                    break;
                case "boolean":
                    switch (r2.tipo) {
                        case "boolean":
                            result = new ResultadoG("boolean", (boolean) (( (boolean)r1.valor) || ((boolean) r2.valor)) );
                            break;
                        case "number":
                            result = new ResultadoG("number", ( (double)r1.valor) + ((double) r2.valor) );
                            break;
                        case "string":
                            result = new ResultadoG("string", ( (String)r1.valor) + ((String) r2.valor) );
                            break;
                        case "date":
                            System.out.println("error");
                            break;
                        case "datetime":
                            System.out.println("error");
                            break;
                    }
                    break;
            }
        }else if(op.equals("MENOS")){
            switch (r1.tipo) {
                case "number":
                    switch (r2.tipo) {
                        case "boolean":
                            result = new ResultadoG("number", ( (double)r1.valor) + ((double) r2.valor) );
                            break;
                        case "number":
                            result = new ResultadoG("number", ( (double)r1.valor) + ((double) r2.valor) );
                            break;
                        case "string":
                            result = new ResultadoG("string", ( (String)r1.valor) + ((String) r2.valor) );
                            break;
                        case "date":
                            System.out.println("error");
                            break;
                        case "datetime":
                            System.out.println("error");
                            break;
                    }
                    break;
                case "string":
                    switch (r2.tipo) {
                        case "boolean":
                            result = new ResultadoG("string", ( (String)r1.valor) + ((String) r2.valor) );
                            break;
                        case "number":
                            result = new ResultadoG("string", ( (String)r1.valor) + ((String) r2.valor) );
                            break;
                        case "string":
                            result = new ResultadoG("string", ( (String)r1.valor) + ((String) r2.valor) );
                            break;
                        case "date":
                            System.out.println("error");
                            break;
                        case "datetime":
                            System.out.println("error");
                            break;
                    }
                    break;
                case "date":
                    switch (r2.tipo) {
                        case "boolean":
                            System.out.println("error");
                            break;
                        case "number":
                            System.out.println("error");
                            break;
                        case "string":
                            result = new ResultadoG("string", ( (String)r1.valor) + ((String) r2.valor) );
                            break;
                        case "date":
                            System.out.println("error");
                            break;
                        case "datetime":
                            System.out.println("error");
                            break;
                    }
                    break;
                case "datetime":
                    switch (r2.tipo) {
                        case "boolean":
                            System.out.println("error");
                            break;
                        case "number":
                            System.out.println("error");
                            break;
                        case "string":
                            result = new ResultadoG("string", ( (String)r1.valor) + ((String) r2.valor) );
                            break;
                        case "date":
                            System.out.println("error");
                            break;
                        case "datetime":
                            System.out.println("error");
                            break;
                    }
                    break;
                case "boolean":
                    switch (r2.tipo) {
                        case "boolean":
                            result = new ResultadoG("boolean", (boolean) (( (boolean)r1.valor) || ((boolean) r2.valor)) );
                            break;
                        case "number":
                            result = new ResultadoG("number", ( (double)r1.valor) + ((double) r2.valor) );
                            break;
                        case "string":
                            result = new ResultadoG("string", ( (String)r1.valor) + ((String) r2.valor) );
                            break;
                        case "date":
                            System.out.println("error");
                            break;
                        case "datetime":
                            System.out.println("error");
                            break;
                    }
                    break;
            }
        }
        return result;
    }
    
}
