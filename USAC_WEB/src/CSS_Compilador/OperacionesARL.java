/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CSS_Compilador;
import CJS_Compilador.ResultadoG;
import Estructuras.NodoCSS;
/**
 *
 * @author fernando
 */
public class OperacionesARL {
    
    public ResultadoG ejecutar(NodoCSS nodo){
        ResultadoG result= null;
        switch(nodo.nombre){
            //-- operaciones aritmeticas 
            case "MAS":
                
                break;
            case "MENOS":
                break;
            case "POR":
                break;
            case "DIV":
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
    
    
    public void operacionesAritmeticas(){
    
    }
    
}
