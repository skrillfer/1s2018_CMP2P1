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
    protected TablaSimboloG tabla;
    protected TablaSimboloG global;
    
    public OperacionesARL(TablaSimboloG global, TablaSimboloG local) {
        this.tabla = local;
        this.global = global;
    }

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
                ResultadoG r_r = operacionesAritmeticas(r1, r2, "MAS");
                System.out.println("valor: "+r_r.valor+ "\nTipo: "+r_r.tipo);
                break;    
            case "number":
                result=new ResultadoG("number", Double.parseDouble(nodo.valor));
                break;
            case "string":
                result = new ResultadoG("string", nodo.valor + "");
                break;
            case "date":
                result=new ResultadoG("date", nodo.valor + "");
                break;
            case "datetime":
                result=new ResultadoG("datetime", nodo.valor +"");
                break;
            case "boolean":
                if(nodo.valor.equalsIgnoreCase("true")){
                    result=new ResultadoG("boolean", true);
                }else{
                    result=new ResultadoG("boolean", false);
                }
                break;
        }
        return result;
    }
    public ResultadoG operacionesAritmeticas(ResultadoG r1, ResultadoG r2, String op){
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
                        case "date":
                        case "datetime":
                            System.out.println("error");
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
                        case "date":
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
                            valor = (String)r1.valor + (String)r2.valor;
                            result = new ResultadoG("string", valor);
                            break;
                        case "date":
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
                            valor = (String)r1.valor + (String)r2.valor;
                            result = new ResultadoG("string", valor);
                            break;
                        case "date":
                        case "datetime":
                            System.out.println("error");
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
                        case "date":
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
                            valor = (Double)r1.valor - getBoolValor(r2.valor);
                            result = new ResultadoG("number", valor );
                            break;
                        case "number":
                            valor = (Double)r1.valor - (Double)r2.valor;
                            result = new ResultadoG("number", valor );
                            break;
                        case "string":
                            System.out.println("error");
                            break;
                        case "date":
                        case "datetime":
                            System.out.println("error");
                            break;
                    }
                    break;
                case "string":
                    switch (r2.tipo) {
                        case "boolean":
                        case "number":
                        case "string":
                        case "date":
                        case "datetime":
                            System.out.println("error");
                            break;
                    }
                    break;
                case "date":
                    switch (r2.tipo) {
                        case "boolean":
                        case "number":
                        case "string":
                        case "date":
                        case "datetime":
                            System.out.println("error");
                            break;
                    }
                    break;
                case "datetime":
                    switch (r2.tipo) {
                        case "boolean":
                        case "number":
                        case "string":
                        case "date":
                        case "datetime":
                            System.out.println("error");
                            break;
                    }
                    break;
                case "boolean":
                    switch (r2.tipo) {
                        case "number":
                            valor = getBoolValor(r1.valor) - (Double) r2.valor;
                            result = new ResultadoG("number", valor);
                            break;
                        case "string":
                        case "boolean":
                        case "date":
                        case "datetime":
                            System.out.println("error");
                            break;
                    }
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
                        case "string":
                        case "date":
                        case "datetime":
                            System.out.println("error");
                            break;
                    }
                    break;
                case "string":
                    switch (r2.tipo) {
                        case "boolean":
                        case "number":
                        case "string":
                        case "date":
                        case "datetime":
                            System.out.println("error");
                            break;
                    }
                    break;
                case "date":
                    switch (r2.tipo) {
                        case "boolean":
                        case "number":
                        case "string":
                        case "date":
                        case "datetime":
                            System.out.println("error");
                            break;
                    }
                    break;
                case "datetime":
                    switch (r2.tipo) {
                        case "boolean":
                        case "number":
                        case "string":
                        case "date":
                        case "datetime":
                            System.out.println("error");
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
                        case "string":
                        case "date":
                        case "datetime":
                            System.out.println("error");
                            break;
                    }
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
                                System.out.println("error no se puede dividir un numero entre 0");
                                return new ResultadoG("-1",valor);
                            }
                            
                            result = new ResultadoG("number", valor );
                            break;
                        case "number":
                            try {
                                valor = (Double)r1.valor / (Double)r2.valor;
                            } catch (Exception e) {
                                System.out.println("error no se puede dividir un numero entre 0");
                                return new ResultadoG("-1",valor);
                            }
                            
                            result = new ResultadoG("number", valor );
                            break;
                        case "string":
                        case "date":
                        case "datetime":
                            System.out.println("error");
                            break;
                    }
                    break;
                case "string":
                    switch (r2.tipo) {
                        case "boolean":
                        case "number":
                        case "string":
                        case "date":
                        case "datetime":
                            System.out.println("error");
                            break;
                    }
                    break;
                case "date":
                    switch (r2.tipo) {
                        case "boolean":
                        case "number":
                        case "string":
                        case "date":
                        case "datetime":
                            System.out.println("error");
                            break;
                    }
                    break;
                case "datetime":
                    switch (r2.tipo) {
                        case "boolean":
                        case "number":
                        case "string":
                        case "date":
                        case "datetime":
                            System.out.println("error");
                            break;
                    }
                    break;
                case "boolean":
                    switch (r2.tipo) {
                        case "number":
                            try {
                                valor = getBoolValor(r1.valor) / (Double) r2.valor;
                            } catch (Exception e) {
                                System.out.println("error no se puede dividir un numero entre 0");
                                return new ResultadoG("-1",valor);
                            }
                            result = new ResultadoG("number", valor);
                            break;
                        case "string":
                        case "boolean":
                        case "date":
                        case "datetime":
                            System.out.println("error");
                            break;
                    }
                    break;
            }
        }else if (op.equals("POT")){
            switch (r1.tipo) {
                case "number":
                    switch (r2.tipo) {
                        case "boolean":
                            valor = Math.pow((Double)r1.valor,(getBoolValor(r2.valor)));
                            result = new ResultadoG("number", valor );
                            break;
                        case "number":
                            valor = Math.pow((Double)r1.valor , (Double)r2.valor);
                            result = new ResultadoG("number", valor );
                            break;
                        case "string":
                        case "date":
                        case "datetime":
                            System.out.println("error");
                            break;
                    }
                    break;
                case "string":
                    switch (r2.tipo) {
                        case "boolean":
                        case "number":
                        case "string":
                        case "date":
                        case "datetime":
                            System.out.println("error");
                            break;
                    }
                    break;
                case "date":
                    switch (r2.tipo) {
                        case "boolean":
                        case "number":
                        case "string":
                        case "date":
                        case "datetime":
                            System.out.println("error");
                            break;
                    }
                    break;
                case "datetime":
                    switch (r2.tipo) {
                        case "boolean":
                        case "number":
                        case "string":
                        case "date":
                        case "datetime":
                            System.out.println("error");
                            break;
                    }
                    break;
                case "boolean":
                    switch (r2.tipo) {
                        case "number":
                            valor = Math.pow(getBoolValor(r1.valor) , (Double) r2.valor);
                            result = new ResultadoG("number", valor);
                            break;
                        case "string":
                        case "boolean":
                        case "date":
                        case "datetime":
                            System.out.println("error");
                            break;
                    }
                    break;
            }
        }
        return result;
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
