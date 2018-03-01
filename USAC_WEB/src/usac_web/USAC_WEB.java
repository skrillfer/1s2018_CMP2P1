/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usac_web;

import Analizadores.LenguajeCCSS.AL_CCSS;
import Analizadores.LenguajeCCSS.AS_CCSS;
import Analizadores.LenguajeCHTML.AL_HTML;
import Analizadores.LenguajeCHTML.AS_HTML;
import Analizadores.LenguajeCJS.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import Estructuras.*;
import Arboles_Generados.*;
import CJS_Compilador.CJS;
/**
 *
 * @author fernando
 */
public class USAC_WEB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        new USAC_WEB().CompilarCJS();
    }
    
    
    public NodoCSS compilarCCSS(String texto) throws FileNotFoundException{
        NodoCSS retorno=null;
        
        escribir("tmpccss.txt",texto);
        AL_CCSS lex = new AL_CCSS(new FileReader("tmpccss.txt"));//se le pasa al analizador lexico lo que se escribio
        AS_CCSS parser = new AS_CCSS(lex);

        try {
            parser.parse();
            NodoCSS raiz = parser.getRoot();
            
            Arbol_CCSS gen_arbol = new Arbol_CCSS();
            if(raiz!=null){
                gen_arbol.generacion_arbolCCSS(raiz);
            }
            retorno=raiz;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return retorno;
    }
    
    public void CompilarCJS() throws FileNotFoundException{
        //escribir("programa.txt","100");
        AL_CJS lex = new AL_CJS(new FileReader("programa.txt"));//se le pasa al analizador lexico lo que se escribio
        AS_CJS parser = new AS_CJS(lex);
        
       
        try {
            parser.parse();
            Nodo raiz = parser.getRoot();
            Arbol_CJS genTcjs = new Arbol_CJS();
            genTcjs.generacion_arbolCJS(raiz);
            
            CJS cj_s= new CJS();
            cj_s.ejecucionCJS(raiz, "metodo1");
            
            //---------------------------Se le pasa la raiz a la clase CJS
            
            //        Arma arma = new Arma(null, Destruir, null)
        } catch (Exception e) {
            // System.out.println(e.getMessage());
        }
    }
    
    public NodoDOM CompilarCHTML(String texto) throws FileNotFoundException{
        NodoDOM retorno=null;
        
        escribir("tmpchtml.txt",texto);
        AL_HTML lex = new AL_HTML(new FileReader("tmpchtml.txt"));//se le pasa al analizador lexico lo que se escribio
        AS_HTML parser = new AS_HTML(lex);

        try {
            parser.parse();
            NodoDOM raiz = parser.getRoot();
            Arbol_DOM gen_arbol = new Arbol_DOM();
            if(raiz!=null){
                System.out.println("***");
                gen_arbol.generacion_arbolCJS(raiz);
            }
            
            retorno=raiz;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return retorno;
    }
    public void escribir(String direccion,String texto) {
        //metodo que guarda lo que esta escrito en un archivo de texto
        try {
            FileWriter writer = new FileWriter(direccion);
            PrintWriter print = new PrintWriter(writer);
            print.print(texto);
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
