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
            /***********          EXPRESIONES LOGICAS                    ******/    
            case "AND":
                ResultadoG r_and1 = ejecutar(nodo.hijos.get(0));
                ResultadoG r_and2 = ejecutar(nodo.hijos.get(1));
                result = operacionesAritmeticas(r_and1, r_and2, "AND");
                imprimirResultado(result);
                break;
            case "OR":
                ResultadoG r_or1 = ejecutar(nodo.hijos.get(0));
                ResultadoG r_or2 = ejecutar(nodo.hijos.get(1));
                result = operacionesAritmeticas(r_or1, r_or2, "OR");
                imprimirResultado(result);
                break;
            case "NOT":
                ResultadoG r_not1 = ejecutar(nodo.hijos.get(0));
                result = operacionesAritmeticas(r_not1, null, "NOT");
                imprimirResultado(result);
                break;
            /***********          EXPRESIONES RELACIONALES               ******/
            case "MENQ":
                ResultadoG r_mm1 = ejecutar(nodo.hijos.get(0));
                ResultadoG r_mm2 = ejecutar(nodo.hijos.get(1));
                result = operacionesAritmeticas(r_mm1, r_mm2, "MENQ");
                imprimirResultado(result);
                break;
            case "MENIQ":
                ResultadoG r_mmi1 = ejecutar(nodo.hijos.get(0));
                ResultadoG r_mmi2 = ejecutar(nodo.hijos.get(1));
                result = operacionesAritmeticas(r_mmi1, r_mmi2, "MENIQ");
                imprimirResultado(result);
                break;
            case "MAYQ":
                ResultadoG r_my1 = ejecutar(nodo.hijos.get(0));
                ResultadoG r_my2 = ejecutar(nodo.hijos.get(1));
                result = operacionesAritmeticas(r_my1, r_my2, "MAYIQ");
                imprimirResultado(result);
                break;
            case "MAYIQ":
                ResultadoG r_myi1 = ejecutar(nodo.hijos.get(0));
                ResultadoG r_myi2 = ejecutar(nodo.hijos.get(1));
                result = operacionesAritmeticas(r_myi1, r_myi2, "MAYIQ");
                imprimirResultado(result);
                break;
            case "IG_IG":
                ResultadoG r_ig1 = ejecutar(nodo.hijos.get(0));
                ResultadoG r_ig2 = ejecutar(nodo.hijos.get(1));
                result = operacionesAritmeticas(r_ig1, r_ig2, "IG_IG");
                imprimirResultado(result);
                break;
            case "DIF":
                ResultadoG r_df1 = ejecutar(nodo.hijos.get(0));
                ResultadoG r_df2 = ejecutar(nodo.hijos.get(1));
                result = operacionesAritmeticas(r_df1, r_df2, "DIF");
                imprimirResultado(result);
                break;
            /***********          EXPRESIONES ARITMETICAS                ******/        
            case "ADD":
                break;
            case "SUB":
                break;
            case "POT":
                ResultadoG r_pt1 = ejecutar(nodo.hijos.get(0));
                ResultadoG r_pt2 = ejecutar(nodo.hijos.get(1));
                result = operacionesAritmeticas(r_pt1, r_pt2, "POT");
                imprimirResultado(result);
                break;
            case "DIV":
                ResultadoG r_d1 = ejecutar(nodo.hijos.get(0));
                ResultadoG r_d2 = ejecutar(nodo.hijos.get(1));
                result = operacionesAritmeticas(r_d1, r_d2, "DIV");
                imprimirResultado(result);
                break;
            case "POR":
                ResultadoG r_p1 = ejecutar(nodo.hijos.get(0));
                ResultadoG r_p2 = ejecutar(nodo.hijos.get(1));
                result = operacionesAritmeticas(r_p1, r_p2, "POR");
                imprimirResultado(result);
                break;
            case "MENOS":
                ResultadoG r_m1 = ejecutar(nodo.hijos.get(0));
                ResultadoG r_m2 = ejecutar(nodo.hijos.get(1));
                result = operacionesAritmeticas(r_m1, r_m2, "MENOS");
                imprimirResultado(result);
                break; 
            case "MAS":
                ResultadoG r1 = ejecutar(nodo.hijos.get(0));
                ResultadoG r2 = ejecutar(nodo.hijos.get(1));
                result = operacionesAritmeticas(r1, r2, "MAS");
                imprimirResultado(result);
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
            case "funcion_Conteo":
                ResultadoG res=acceso(nodo.hijos.get(0));
                if(res.simbolo.esArreglo){
                    Double i1= ((Arreglo)res.simbolo.valor).funcion_Conteo();
                    
                    result=new ResultadoG("number", i1);
                }else{
                    System.out.println("funcion conteo solo aplica con Vectores");
                }
                break;
            case "funcion_aTexto":
                ResultadoG res1=acceso(nodo.hijos.get(0));
                if(res1.simbolo.esArreglo){
                    String i1= ((Arreglo)res1.simbolo.valor).funcion_aTexto();
                    result=new ResultadoG("string", i1);
                }else{
                    System.out.println("funcion aTexto solo aplica con Vectores");
                }
                acceso(nodo.hijos.get(0));
                break;
            case "id":
                ResultadoG res0=acceso(nodo);
                if(res0!=null){
                    result=res0;
                }else{
                    System.out.println("no existe la variable");
                }
                
                break;
        }
        return result;
    }
    
    public void imprimirResultado(ResultadoG res){
        System.out.println("____________________________________________________");
        System.out.println("Resultado: " + res.valor + "\n Tipo: "+res.tipo);
        
    }
    public ResultadoG operacionesLogicas(ResultadoG r1, ResultadoG r2, String op){
        ResultadoG result = null;
        Object valor = new Object();
        if(op.equals("AND")){
            if(r1.tipo.equals(r2.tipo) && r1.tipo.equals("boolean")){
                valor =  getBoolValor(r1.valor) + getBoolValor(r2.valor);
                result = new ResultadoG("boolean", false);
                if((int)valor==2){
                    result = new ResultadoG("boolean", true);
                }
            }else{
                return new ResultadoG("-1", valor);
            }
        }else if(op.equals("OR")){
            if(r1.tipo.equals(r2.tipo) && r1.tipo.equals("boolean")){
                valor =  getBoolValor(r1.valor) + getBoolValor(r2.valor);
                result = new ResultadoG("boolean", false);
                if((int)valor==1 || (int)valor==2){
                    result = new ResultadoG("boolean", true);
                }
            }else{
                return new ResultadoG("-1", valor);
            }
        }else if(op.equals("NOT")){
            if(r1.tipo.equals(r2.tipo) && r1.tipo.equals("boolean")){
                valor =  getBoolValor(r1.valor);
                result = new ResultadoG("boolean", true);
                if((int)valor==1 ){
                    result = new ResultadoG("boolean", false);
                }
            }else{
                return new ResultadoG("-1", valor);
            }
        }
        return result;
    }
    
    
    public ResultadoG operacionesRelacionales(ResultadoG r1, ResultadoG r2, String op){
        ResultadoG result = null;
        if(op.equals("MENQ")){
            switch(r1.tipo){
                case "number":
                    switch(r2.tipo){
                        case "number":
                            result = new ResultadoG("boolean", false);
                            if((Double)r1.valor < (Double)r2.valor){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                    }
                    break;
                case "string":
                    switch(r2.tipo){
                        case "string":
                            result = new ResultadoG("boolean", false);
                            if( ((String)r1.valor).compareTo((String)r2.valor) < 0 ){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                    }
                    break;
                case "boolean":
                    switch(r2.tipo){
                        case "boolean":
                            result = new ResultadoG("boolean", false);
                            if(  getBoolValor(r1.valor) < getBoolValor(r2.valor) ){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                    }
                    break;
            }
        }else if(op.equals("MENIQ")){
            switch(r1.tipo){
                case "number":
                    switch(r2.tipo){
                        case "number":
                            result = new ResultadoG("boolean", false);
                            if((Double)r1.valor <= (Double)r2.valor){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                    }
                    break;
                case "string":
                    switch(r2.tipo){
                        case "string":
                            result = new ResultadoG("boolean", false);
                            if( ((String)r1.valor).compareTo((String)r2.valor) <= 0 ){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                    }
                    break;
                case "boolean":
                    switch(r2.tipo){
                        case "boolean":
                            result = new ResultadoG("boolean", false);
                            if(  getBoolValor(r1.valor) <= getBoolValor(r2.valor) ){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                    }
                    break;
            }
        }else if(op.equals("MAYQ")){
            switch(r1.tipo){
                case "number":
                    switch(r2.tipo){
                        case "number":
                            result = new ResultadoG("boolean", false);
                            if((Double)r1.valor > (Double)r2.valor){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                    }
                    break;
                case "string":
                    switch(r2.tipo){
                        case "string":
                            result = new ResultadoG("boolean", false);
                            if( ((String)r1.valor).compareTo((String)r2.valor) > 0 ){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                    }
                    break;
                case "boolean":
                    switch(r2.tipo){
                        case "boolean":
                            result = new ResultadoG("boolean", false);
                            if(  getBoolValor(r1.valor) > getBoolValor(r2.valor) ){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                    }
                    break;
            }
        }else if(op.equals("MAYIQ")){
            switch(r1.tipo){
                case "number":
                    switch(r2.tipo){
                        case "number":
                            result = new ResultadoG("boolean", false);
                            if((Double)r1.valor >= (Double)r2.valor){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                    }
                    break;
                case "string":
                    switch(r2.tipo){
                        case "string":
                            result = new ResultadoG("boolean", false);
                            if( ((String)r1.valor).compareTo((String)r2.valor) >= 0 ){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                    }
                    break;
                case "boolean":
                    switch(r2.tipo){
                        case "boolean":
                            result = new ResultadoG("boolean", false);
                            if(  getBoolValor(r1.valor) >= getBoolValor(r2.valor) ){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                    }
                    break;
            }
        }else if(op.equals("IG_IG")){
            switch(r1.tipo){
                case "number":
                    switch(r2.tipo){
                        case "number":
                            result = new ResultadoG("boolean", false);
                            if((Double)r1.valor == (Double)r2.valor){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                    }
                    break;
                case "string":
                    switch(r2.tipo){
                        case "string":
                            result = new ResultadoG("boolean", false);
                            if( ((String)r1.valor).compareTo((String)r2.valor) == 0 ){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                    }
                    break;
                case "boolean":
                    switch(r2.tipo){
                        case "boolean":
                            result = new ResultadoG("boolean", false);
                            if(  getBoolValor(r1.valor) == getBoolValor(r2.valor) ){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                    }
                    break;
            }
        }else if(op.equals("DIF")){
            switch(r1.tipo){
                case "number":
                    switch(r2.tipo){
                        case "number":
                            result = new ResultadoG("boolean", false);
                            if((Double)r1.valor != (Double)r2.valor){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                    }
                    break;
                case "string":
                    switch(r2.tipo){
                        case "string":
                            result = new ResultadoG("boolean", false);
                            if( ((String)r1.valor).compareTo((String)r2.valor) != 0 ){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                    }
                    break;
                case "boolean":
                    switch(r2.tipo){
                        case "boolean":
                            result = new ResultadoG("boolean", false);
                            if(  getBoolValor(r1.valor) != getBoolValor(r2.valor) ){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                    }
                    break;
            }
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
    
    
    public ResultadoG acceso(Nodo raiz){
        Clase aux = CJS.claseActual;
        TablaSimboloG tablaAux = tabla;
        ResultadoG retorno = new ResultadoG("-1", null);
        //----------------------------------------------------------------------
        String nombre;
        SimboloG simbolo;
        switch(raiz.nombre){
            case "id":
                nombre = raiz.valor;
                simbolo = tabla.getSimbolo(nombre, aux);
                if(simbolo != null){
                    if(simbolo.inicializado){
                        switch(simbolo.tipo){
                            case "boolean":
                            case "number":
                            case "string":
                            case "date":
                            case "datetime":
                                retorno.valor = simbolo.valor;
                                retorno.tipo  = simbolo.tipo;
                                retorno.simbolo = simbolo;
                                break;
                        }
                        if(simbolo.esArreglo){
                            retorno.valor = simbolo.valor;
                            retorno.tipo  = simbolo.tipo;
                            retorno.simbolo = simbolo;
                        }
                    }else{
                        retorno.tipo = "-1";
                        retorno.valor = null;
                        //Inicio.reporteError.agregar("Semantico", acceso.linea, acceso.columna, "La variable " + nombre + " no ha sido inicializada");
                        return retorno;
                    }
                }else {
                    retorno.tipo = "-1";
                    retorno.valor = null;
                    //Inicio.reporteError.agregar("Semantico", acceso.linea, acceso.columna, "La variable " + nombre + " no existe en el ambito donde fue invocada");
                    return retorno;
                }
                break;
        }
        return retorno;
    }
}
