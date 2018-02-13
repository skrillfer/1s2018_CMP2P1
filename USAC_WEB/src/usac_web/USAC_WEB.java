/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usac_web;

import Analizadores.AL_HTML;
import Analizadores.AS_HTML;
import Analizadores.LenguajeCJS.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import Estructuras.*;
import Arboles_Generados.*;
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
    
    public void CompilarCJS() throws FileNotFoundException{
        //escribir("programa.txt","100");
        AL_CJS lex = new AL_CJS(new FileReader("programa.txt"));//se le pasa al analizador lexico lo que se escribio
        AS_CJS parser = new AS_CJS(lex);
        
       
        try {
            parser.parse();
            Nodo raiz = parser.getRoot();
            Arbol_CJS genTcjs = new Arbol_CJS();
            genTcjs.generacion_arbolCJS(raiz);
        
            //        Arma arma = new Arma(null, Destruir, null)
        } catch (Exception e) {
            // System.out.println(e.getMessage());
        }
    }
    
    public void CompilarCHTML() throws FileNotFoundException{
        //escribir("programa.txt","100");
        AL_HTML lex = new AL_HTML(new FileReader("programa.txt"));//se le pasa al analizador lexico lo que se escribio
        AS_HTML parser = new AS_HTML(lex);

        try {
            parser.parse();
            //        Arma arma = new Arma(null, Destruir, null)
        } catch (Exception e) {
            // System.out.println(e.getMessage());
        }
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
