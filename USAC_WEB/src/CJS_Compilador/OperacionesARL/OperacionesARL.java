/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CJS_Compilador.OperacionesARL;
import Estructuras.*;
import CJS_Compilador.*;
import Interfaz.Componente;
import Interfaz.Template;
import java.util.ArrayList;
import javax.swing.JComponent;
/**
 *
 * @author fernando
 */
public class OperacionesARL {
    protected TablaSimboloG tabla;
    protected TablaSimboloG global;
    
    private int linea1=0;
    private int linea2=0;
    
    private int columna1=0;
    private int columna2=0;
    
    public OperacionesARL(TablaSimboloG global, TablaSimboloG local) {
        this.tabla = local;
        this.global = global;
    }

    public ResultadoG ejecutar(Nodo nodo){
        ResultadoG result=null;
        switch(nodo.nombre){
            /***********          EXPRESIONES LOGICAS                    ******/    
            case "AND":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2= nodo.hijos.get(1).linea;        columna2=nodo.hijos.get(0).columna;
                
                ResultadoG r_and1 = ejecutar(nodo.hijos.get(0));
                ResultadoG r_and2 = ejecutar(nodo.hijos.get(1));
                result = operacionesLogicas(r_and1, r_and2, "AND");
                imprimirResultado(result);
                break;
            case "OR":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2= nodo.hijos.get(1).linea;        columna2=nodo.hijos.get(0).columna;
                
                ResultadoG r_or1 = ejecutar(nodo.hijos.get(0));
                ResultadoG r_or2 = ejecutar(nodo.hijos.get(1));
                result = operacionesLogicas(r_or1, r_or2, "OR");
                imprimirResultado(result);
                break;
            case "NOT":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2=0;        columna2=0;
                
                ResultadoG r_not1 = ejecutar(nodo.hijos.get(0));
                result = operacionesLogicas(r_not1, null, "NOT");
                imprimirResultado(result);
                break;
            /***********          EXPRESIONES RELACIONALES               ******/
            case "MENQ":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2= nodo.hijos.get(1).linea;        columna2=nodo.hijos.get(0).columna;
                
                ResultadoG r_mm1 = ejecutar(nodo.hijos.get(0));
                ResultadoG r_mm2 = ejecutar(nodo.hijos.get(1));
                result = operacionesRelacionales(r_mm1, r_mm2, "MENQ");
                imprimirResultado(result);
                break;
            case "MENIQ":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2= nodo.hijos.get(1).linea;        columna2=nodo.hijos.get(0).columna;
                
                ResultadoG r_mmi1 = ejecutar(nodo.hijos.get(0));
                ResultadoG r_mmi2 = ejecutar(nodo.hijos.get(1));
                result = operacionesRelacionales(r_mmi1, r_mmi2, "MENIQ");
                imprimirResultado(result);
                break;
            case "MAYQ":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2= nodo.hijos.get(1).linea;        columna2=nodo.hijos.get(0).columna;
                
                ResultadoG r_my1 = ejecutar(nodo.hijos.get(0));
                ResultadoG r_my2 = ejecutar(nodo.hijos.get(1));
                result = operacionesRelacionales(r_my1, r_my2, "MAYQ");
                imprimirResultado(result);
                break;
            case "MAYIQ":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2= nodo.hijos.get(1).linea;        columna2=nodo.hijos.get(0).columna;
                
                ResultadoG r_myi1 = ejecutar(nodo.hijos.get(0));
                ResultadoG r_myi2 = ejecutar(nodo.hijos.get(1));
                result = operacionesRelacionales(r_myi1, r_myi2, "MAYIQ");
                imprimirResultado(result);
                break;
            case "IG_IG":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2= nodo.hijos.get(1).linea;        columna2=nodo.hijos.get(0).columna;
                
                ResultadoG r_ig1 = ejecutar(nodo.hijos.get(0));
                ResultadoG r_ig2 = ejecutar(nodo.hijos.get(1));
                result = operacionesRelacionales(r_ig1, r_ig2, "IG_IG");
                imprimirResultado(result);
                break;
            case "DIF":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2= nodo.hijos.get(1).linea;        columna2=nodo.hijos.get(0).columna;
                
                ResultadoG r_df1 = ejecutar(nodo.hijos.get(0));
                ResultadoG r_df2 = ejecutar(nodo.hijos.get(1));
                result = operacionesRelacionales(r_df1, r_df2, "DIF");
                imprimirResultado(result);
                break;
            /***********          EXPRESIONES ARITMETICAS                ******/        
            case "ADD":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2=0;        columna2=0;
                ResultadoG r_add1 = ejecutar(nodo.hijos.get(0));
                result = operacionesSimplificadas(r_add1, "ADD");
                //imprimirResultado(result);
                break;
            case "SUB":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2=0;        columna2=0;
                ResultadoG r_sub1 = ejecutar(nodo.hijos.get(0));
                System.out.println("voy a DECREMENTAR:"+r_sub1.valor);
                result = operacionesSimplificadas(r_sub1, "SUB");
                //System.out.println("===>"+result.valor);
                //imprimirResultado(result.valor);
                break;
            case "POT":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2= nodo.hijos.get(1).linea;        columna2=nodo.hijos.get(0).columna;
                ResultadoG r_pt1 = ejecutar(nodo.hijos.get(0));
                ResultadoG r_pt2 = ejecutar(nodo.hijos.get(1));
                result = operacionesAritmeticas(r_pt1, r_pt2, "POT");
                //imprimirResultado(result);
                break;
            case "DIV":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2= nodo.hijos.get(1).linea;        columna2=nodo.hijos.get(0).columna;
                ResultadoG r_d1 = ejecutar(nodo.hijos.get(0));
                ResultadoG r_d2 = ejecutar(nodo.hijos.get(1));
                result = operacionesAritmeticas(r_d1, r_d2, "DIV");
                //imprimirResultado(result);
                break;
            case "POR":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2= nodo.hijos.get(1).linea;        columna2=nodo.hijos.get(0).columna;
                ResultadoG r_p1 = ejecutar(nodo.hijos.get(0));
                ResultadoG r_p2 = ejecutar(nodo.hijos.get(1));
                result = operacionesAritmeticas(r_p1, r_p2, "POR");
               // imprimirResultado(result);
                break;
            case "MENOS":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2= nodo.hijos.get(1).linea;        columna2=nodo.hijos.get(0).columna;
                ResultadoG r_m1 = ejecutar(nodo.hijos.get(0));
                ResultadoG r_m2 = ejecutar(nodo.hijos.get(1));
                result = operacionesAritmeticas(r_m1, r_m2, "MENOS");
                //imprimirResultado(result);
                break; 
            case "MAS":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2= nodo.hijos.get(1).linea;        columna2=nodo.hijos.get(0).columna;
                ResultadoG r1 = ejecutar(nodo.hijos.get(0));
                ResultadoG r2 = ejecutar(nodo.hijos.get(1));
                result = operacionesAritmeticas(r1, r2, "MAS");
                //imprimirResultado(result);
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
                linea1=nodo.hijos.get(0).linea;
                columna1=nodo.hijos.get(0).columna;
                
                if(res.valor!=null){
                    
                    if(res.simbolo.esArreglo){
                        Double i1= ((Arreglo)res.simbolo.valor).funcion_Conteo();

                        result=new ResultadoG("number", i1);
                    }else{
                        Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "Funcion Conteo solo aplica con Vectores");
                        //System.out.println("funcion conteo solo aplica con Vectores");
                    }
                }else{
                    Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No existe el Simbolo: "+nodo.hijos.get(0).valor);
                }
               
                break;
            case "funcion_aTexto":
                ResultadoG res1=acceso(nodo.hijos.get(0));
                linea1=nodo.hijos.get(0).linea;
                columna1=nodo.hijos.get(0).columna;
                
                if(res1.valor!=null){
                    if(res1.simbolo.esArreglo){
                        String i1= ((Arreglo)res1.simbolo.valor).funcion_aTexto();
                        result=new ResultadoG("string", i1);
                    }else{
                        Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "Funcion aTexto solo aplica con Vectores");
                    }

                }else{
                    Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No existe el Simbolo: "+nodo.hijos.get(0).valor);
                }
                //acceso(nodo.hijos.get(0));
                break;
            case "accesoAr":
                ResultadoG res01=acceso(nodo);
                linea1=nodo.linea;
                columna1=nodo.columna;
                if(res01!=null){
                    result=res01;
                }
                break;
            case "id":
                ResultadoG res0=acceso(nodo);
                linea1=nodo.linea;
                columna1=nodo.columna;
                //System.out.println("id:"+res0.tipo);
                if(res0!=null){
                    result=res0;
                    if(result.tipo.equals("0")){
                        Template.reporteError_CJS.agregar("Error Semantico",nodo.linea, nodo.columna,"La variable " + nodo.valor + " no ha sido inicialiada");
                        //result=null;
                    }
                }else{
                    //Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No existe el Simbolo: "+nodo.valor);
                }
                
                break;
            case "llamadaFuncion":
                ResultadoG callR = acceso(nodo);
                if(callR!=null){
                    result = callR;
                }
                break;
            case "unitario":
                ResultadoG resu=ejecutar(nodo.hijos.get(0));
                if(resu!=null){
                    try {
                        if(resu.tipo.equals("number")){
                            resu.valor = (Double)resu.valor *-1.0;
                            result = resu;
                        }else{
                            Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "Unitario no aplica a tipo: "+resu.tipo);
                        }
                    } catch (Exception e) {}
                }
                break;
        }
        return result;
    }
    
    public void imprimirResultado(ResultadoG res){
        //System.out.println("____________________________________________________");
        //System.out.println("Resultado: " + res.valor + "\n Tipo: "+res.tipo);
        //System.out.println("____________________________________________________");
        
    }
    public ResultadoG operacionesLogicas(ResultadoG r1, ResultadoG r2, String op){
        ResultadoG result = null;
        Object valor = new Object();
        
        if(op.equals("NOT")){
            if(r1==null){
                return result;
            }
        }else{
            if(verNulabilidad(r1, r2)){
                return result;
            }
        }
        
        if(op.equals("AND")){
            if(r1.tipo.equals(r2.tipo) && r1.tipo.equals("boolean")){
                valor =  getBoolValor(r1.valor) + getBoolValor(r2.valor);
                result = new ResultadoG("boolean", false);
                if((int)valor==2){
                    result = new ResultadoG("boolean", true);
                }
            }else{
                Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No aplica AND entre "+r1.tipo+"-"+r2.tipo);
                //return new ResultadoG("-1", valor);
            }
        }else if(op.equals("OR")){
            if(r1.tipo.equals(r2.tipo) && r1.tipo.equals("boolean")){
                valor =  getBoolValor(r1.valor) + getBoolValor(r2.valor);
                result = new ResultadoG("boolean", false);
                if((int)valor==1 || (int)valor==2){
                    result = new ResultadoG("boolean", true);
                }
            }else{
               Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No aplica OR entre "+r1.tipo+"-"+r2.tipo);
            }
        }else if(op.equals("NOT")){
            if(r1.tipo.equals("boolean")){
                valor =  getBoolValor(r1.valor);
                result = new ResultadoG("boolean", true);
                if((int)valor==1 ){
                    result = new ResultadoG("boolean", false);
                }
            }else{
                Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica NOT a "+r1.tipo);
            }
        }
        return result;
    }
    
    
    public ResultadoG operacionesRelacionales(ResultadoG r1, ResultadoG r2, String op){
        if(verNulabilidad(r1, r2)){
            return null;
        }
        ResultadoG result = null;
        if(op.equals("MENQ")){
            switch(r1.tipo){
                case "number":
                    switch(r2.tipo){
                        case "number":
                            result = new ResultadoG("boolean", false);
                            if((double)r1.valor < (double)r2.valor){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
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
                        case "date":
                        case "datetime":
                            result = new ResultadoG("boolean", false);
                            if( ((String)r1.valor).compareTo(((String)r2.valor).trim()) < 0 ){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                        case "number":
                            result = new ResultadoG("boolean", false);
                            int tam= ((String)r1.valor).length();
                            Double num =  (Double)r2.valor;
                            if(tam<num){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
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
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;    
                    }
                    break;
                case "date":
                case "datetime":
                    switch(r2.tipo){
                        case "date":
                        case "datetime":    
                            result = new ResultadoG("boolean", false);
                            if( (((String)r1.valor).trim()).compareTo(((String)r2.valor).trim()) < 0 ){
                                result = new ResultadoG("boolean", true);
                            }
                            break;    
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                default:
                    Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                    break;
            }
        }else if(op.equals("MENIQ")){
            switch(r1.tipo){
                case "number":
                    switch(r2.tipo){
                        case "number":
                            result = new ResultadoG("boolean", false);
                            if((double)r1.valor <= (double)r2.valor){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR_IGUAL QUE entre "+r1.tipo+"-"+r2.tipo);
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
                        case "date":
                        case "datetime":
                            result = new ResultadoG("boolean", false);
                            if( ((String)r1.valor).compareTo(((String)r2.valor).trim()) <= 0 ){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                        case "number":
                            result = new ResultadoG("boolean", false);
                            int tam= ((String)r1.valor).length();
                            Double num =  (Double)r2.valor;
                            if(tam<=num){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR_IGUAL QUE entre "+r1.tipo+"-"+r2.tipo);
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
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR_IGUAL QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;   
                    }
                    break;
                case "date":
                case "datetime":
                    switch(r2.tipo){
                        case "date":
                        case "datetime":    
                            result = new ResultadoG("boolean", false);
                            if( (((String)r1.valor).trim()).compareTo(((String)r2.valor).trim()) <= 0 ){
                                result = new ResultadoG("boolean", true);
                            }
                            break;    
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR_IGUAL QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                default:
                    Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No aplica MENOR_IGUAL QUE entre "+r1.tipo+"-"+r2.tipo);
                    break;    
            }
        }else if(op.equals("MAYQ")){
            switch(r1.tipo){
                case "number":
                    switch(r2.tipo){
                        case "number":
                            result = new ResultadoG("boolean", false);
                            if((double)r1.valor > (double)r2.valor){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MAYOR QUE entre "+r1.tipo+"-"+r2.tipo);
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
                        case "date":
                        case "datetime":
                            result = new ResultadoG("boolean", false);
                            if( ((String)r1.valor).compareTo(((String)r2.valor).trim()) > 0 ){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                        case "number":
                            result = new ResultadoG("boolean", false);
                            int tam= ((String)r1.valor).length();
                            Double num =  (Double)r2.valor;
                            if(tam>num){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MAYOR QUE entre "+r1.tipo+"-"+r2.tipo);
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
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MAYOR QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;    
                    }
                    break;
                case "date":
                case "datetime":
                    switch(r2.tipo){
                        case "date":
                        case "datetime":    
                            result = new ResultadoG("boolean", false);
                            if( (((String)r1.valor).trim()).compareTo(((String)r2.valor).trim()) > 0 ){
                                result = new ResultadoG("boolean", true);
                            }
                            break;    
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MAYOR QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                default:
                    Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No aplica MAYOR QUE entre "+r1.tipo+"-"+r2.tipo);
                    break;      
            }
        }else if(op.equals("MAYIQ")){
            switch(r1.tipo){
                case "number":
                    switch(r2.tipo){
                        case "number":
                            result = new ResultadoG("boolean", false);
                            if((double)r1.valor >= (double)r2.valor){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MAYOR_IGUAL QUE entre "+r1.tipo+"-"+r2.tipo);
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
                        case "date":
                        case "datetime":
                            result = new ResultadoG("boolean", false);
                            if( ((String)r1.valor).compareTo(((String)r2.valor).trim()) >= 0 ){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                        case "number":
                            result = new ResultadoG("boolean", false);
                            int tam= ((String)r1.valor).length();
                            Double num =  (Double)r2.valor;
                            if(tam>=num){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MAYOR_IGUAL QUE entre "+r1.tipo+"-"+r2.tipo);
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
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MAYOR_IGUAL QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;    
                    }
                    break;
                case "date":
                case "datetime":
                    switch(r2.tipo){
                        case "date":
                        case "datetime":    
                            result = new ResultadoG("boolean", false);
                            if( (((String)r1.valor).trim()).compareTo(((String)r2.valor).trim()) >= 0 ){
                                result = new ResultadoG("boolean", true);
                            }
                            break;    
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MAYOR_IGUAL QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                default:
                    Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No aplica MAYOR_IGUAL QUE entre "+r1.tipo+"-"+r2.tipo);
                    break;     
            }
        }else if(op.equals("IG_IG")){
            switch(r1.tipo){
                case "number":
                    switch(r2.tipo){
                        case "number":
                            result = new ResultadoG("boolean", false);
                            //System.out.println("[cIG] Valor de r1:"+(Double)r1.valor);
                            //System.out.println("[cIG] Valor de r2:"+(Double)r2.valor);
                            if((double)r1.valor == (double)r2.valor){
                                //System.out.println("son iguales :)");
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica IGUALIGUAL QUE entre "+r1.tipo+"-"+r2.tipo);
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
                        case "date":
                        case "datetime":
                            result = new ResultadoG("boolean", false);
                            if( ((String)r1.valor).compareTo(((String)r2.valor).trim()) == 0 ){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                        case "number":
                            result = new ResultadoG("boolean", false);
                            int tam= ((String)r1.valor).length();
                            Double num =  (Double)r2.valor;
                            if(tam==num){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica IGUALIGUAL QUE entre "+r1.tipo+"-"+r2.tipo);
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
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica IGUALIGUAL QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;        
                    }
                    break;
                case "date":
                case "datetime":
                    switch(r2.tipo){
                        case "date":
                        case "datetime":    
                            result = new ResultadoG("boolean", false);
                            if( (((String)r1.valor).trim()).compareTo(((String)r2.valor).trim()) == 0 ){
                                result = new ResultadoG("boolean", true);
                            }
                            break;    
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica IGUALIGUAL QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                default:
                    Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No aplica IGUALIGUAL QUE entre "+r1.tipo+"-"+r2.tipo);
                    break;     
            }
        }else if(op.equals("DIF")){
            switch(r1.tipo){
                case "number":
                    switch(r2.tipo){
                        case "number":
                            result = new ResultadoG("boolean", false);
                            if((double)r1.valor != (double)r2.valor){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica DIFERENTE QUE entre "+r1.tipo+"-"+r2.tipo);
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
                        case "date":
                        case "datetime":
                            result = new ResultadoG("boolean", false);
                            if( ((String)r1.valor).compareTo(((String)r2.valor).trim()) != 0 ){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                        case "number":
                            result = new ResultadoG("boolean", false);
                            int tam= ((String)r1.valor).length();
                            Double num =  (Double)r2.valor;
                            if(tam!=num){
                                result = new ResultadoG("boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica DIFERENTE QUE entre "+r1.tipo+"-"+r2.tipo);
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
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica DIFERENTE QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;      
                    }
                    break;
                case "date":
                case "datetime":
                    switch(r2.tipo){
                        case "date":
                        case "datetime":    
                            result = new ResultadoG("boolean", false);
                            if( (((String)r1.valor).trim()).compareTo(((String)r2.valor).trim()) != 0 ){
                                result = new ResultadoG("boolean", true);
                            }
                            break;    
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica DIFERENTE QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                default:
                    Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No aplica DIFERENTE QUE entre "+r1.tipo+"-"+r2.tipo);
                    break;       
            }
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
                        case "date":
                        case "datetime":
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Suma entre "+r1.tipo+"-"+r2.tipo);
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
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Suma entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                case "date":
                    switch (r2.tipo) {
                        case "string":
                            valor = (String)r1.valor + (String)r2.valor;
                            result = new ResultadoG("string", valor);
                            break;
                        case "boolean":    
                        case "number":    
                        case "date":
                        case "datetime":
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Suma entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                case "datetime":
                    switch (r2.tipo) {
                        case "string":
                            valor = (String)r1.valor + (String)r2.valor;
                            result = new ResultadoG("string", valor);
                            break;
                        case "boolean":
                        case "number":    
                        case "date":
                        case "datetime":
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Suma entre "+r1.tipo+"-"+r2.tipo);
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
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Suma entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                default:
                    Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No aplica Suma entre "+r1.tipo+"-"+r2.tipo);    
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
                        case "date":
                        case "datetime":
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Resta entre "+r1.tipo+"-"+r2.tipo);
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
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Resta entre "+r1.tipo+"-"+r2.tipo);
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
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Resta entre "+r1.tipo+"-"+r2.tipo);
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
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Resta entre "+r1.tipo+"-"+r2.tipo);
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
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Resta entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                default:
                    Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No aplica Resta entre "+r1.tipo+"-"+r2.tipo);    
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
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Multiplicacion entre "+r1.tipo+"-"+r2.tipo);
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
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Multiplicacion entre "+r1.tipo+"-"+r2.tipo);
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
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Multiplicacion entre "+r1.tipo+"-"+r2.tipo);
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
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Multiplicacion entre "+r1.tipo+"-"+r2.tipo);
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
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Multiplicacion entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                default:
                    Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No aplica Multiplicacion entre "+r1.tipo+"-"+r2.tipo);
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
                                Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "Division entre 0"+r1.valor+"-"+r2.valor);
                                //return new ResultadoG("-1",valor);
                            }
                            
                            result = new ResultadoG("number", valor );
                            break;
                        case "number":
                            try {
                                valor = (Double)r1.valor / (Double)r2.valor;
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "Division entre 0"+r1.valor+"-"+r2.valor);
                                //eturn new ResultadoG("-1",valor);
                            }
                            
                            result = new ResultadoG("number", valor );
                            break;
                        case "string":
                        case "date":
                        case "datetime":
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Division entre "+r1.tipo+"-"+r2.tipo);
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
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Division entre "+r1.tipo+"-"+r2.tipo);
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
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Division entre "+r1.tipo+"-"+r2.tipo);
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
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Division entre "+r1.tipo+"-"+r2.tipo);
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
                                //return new ResultadoG("-1",valor);
                            }
                            result = new ResultadoG("number", valor);
                            break;
                        case "string":
                        case "boolean":
                        case "date":
                        case "datetime":
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Division entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                default:
                    Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No aplica Divison entre "+r1.tipo+"-"+r2.tipo);
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
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Potencia entre "+r1.tipo+"-"+r2.tipo);
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
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Potencia entre "+r1.tipo+"-"+r2.tipo);
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
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Potencia entre "+r1.tipo+"-"+r2.tipo);
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
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Potencia entre "+r1.tipo+"-"+r2.tipo);
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
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Potencia entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                default:
                    Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No aplica Potencia entre "+r1.tipo+"-"+r2.tipo);
                    break;    
            }
        }
        return result;
    }
    
    public ResultadoG operacionesSimplificadas(ResultadoG r1, String op){
        if(r1==null){
            return null;
        }
        ResultadoG resultado=null;
        Object valor = new Object();
        if(op.equals("ADD")){
            if(r1.tipo.equals("number")){
                valor = (Double)r1.valor + 1;
                resultado = new ResultadoG("number", valor);
            }else{
                Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No aplica Incremento a "+r1.tipo);  
            }
        }else if(op.equals("SUB")){
            if(r1.tipo.equals("number")){
                valor = (Double)r1.valor - 1;
                resultado = new ResultadoG("number", valor);
            }else{
                Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No aplica Decremento a "+r1.tipo);
            }
        }
        return resultado;
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
        int nivel = 0;
        String nombre;
        SimboloG simbolo;
        switch(raiz.nombre){
            case "accesoAr":
                    aux.tabla = tabla;
                    tabla = tablaAux;
                    retorno = accesoAr(raiz, nivel, aux);
                    break;
            case "id_componente":
                nombre = raiz.valor;
                if(Template.lista_componentes.containsKey(nombre)){
                    Componente componente=Template.lista_componentes.get(nombre);
                    retorno.valor = componente;
                    retorno.tipo  = componente.tipo;
                    retorno.simbolo = null;
                }else {
                    //retorno.tipo = "-1";
                    //retorno.valor = null;
                    Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " no existe en la lista de componentes CHTML");
                    return null;
                }
                break;
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
                            retorno.tipo = "Arreglo";
                            retorno.simbolo = simbolo;
                        }
                    }else{
                        retorno.tipo = "";
                        retorno.valor = null;
                        Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " no ha sido inicializada");
                        return retorno;
                    }
                }else {
                    //retorno.tipo = "-1";
                    //retorno.valor = null;
                    Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " no existe en el ambito donde fue invocada");
                    return null;
                }
                break;
                
            case "llamadaFuncion":
                LlamadaMetodo llamada = new LlamadaMetodo(aux, nivel);
                Metodo metodo = llamada.ejecutar(raiz);
                if (metodo != null) {
                    if (metodo.retorno != null) {
                            retorno = metodo.retorno;
                            metodo.estadoRetorno = false;
                    }
                } else {
                    retorno.tipo = "-1";
                    retorno.valor = null;
                }
                break;
        }
        tabla = tablaAux;
        return retorno;
    }
    
    
    private ResultadoG accesoAr(Nodo raiz, int nivel, Clase aux) {
        SimboloG simbolo;
        simbolo = aux.tabla.getSimbolo((String) raiz.valor, aux);
        if(simbolo!=null){
            if (simbolo.inicializado) {
                if(simbolo.esArreglo){
                    Arreglo arreglo = (Arreglo) simbolo.valor;
                    ArrayList<Integer> indices = new ArrayList<>();
                    OperacionesARL opL = new OperacionesARL(global, tabla);
                    ResultadoG indice = opL.ejecutar(raiz.hijos.get(0));
                    if (indice != null) {
                        if (indice.tipo.equals("number")) {
                            Double iii = (Double) indice.valor;
                            indices.add(iii.intValue());
                        }
                    }
                    Object valor = arreglo.getValor(indices);
                    if (valor != null) {
                        
                        ResultadoG ree = (ResultadoG)valor;
                        return ree;
                    } else {
                        Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "No se pudo acceder al indice del arreglo: "+simbolo.nombre+" Indice fuera del limite");
                    }
                }else {
                    Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + raiz.valor + " no es arreglo");
                    return null;
                }
            }else{
                Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + raiz.valor + " no ha sido inicializada");
                return null;
            }
        }
        return null;
    }
    
    public boolean verNulabilidad(ResultadoG r1, ResultadoG r2){
        if(r1 != null && r2!=null){
            return false;
        }else{
            return true;
        }
    }
}
