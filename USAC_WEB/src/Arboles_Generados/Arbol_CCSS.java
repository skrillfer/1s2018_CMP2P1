/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arboles_Generados;

import Estructuras.NodoCSS;
import Estructuras.NodoDOM;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 *
 * @author fernando
 */
public class Arbol_CCSS {
    public void generacion_arbolCCSS(NodoCSS raiz) {
        try {
            String contenidoDot = textoDot(raiz);
            escribir("Imagenes/arbol_ccss.dot", contenidoDot);

            String fileInputPath = "Imagenes/arbol_ccss.dot";
            String fileOutputPath = "Imagenes/CCSS_ARBOL.jpg";


            
            String comando = "dot -Tjpg " + fileInputPath + " -o " + fileOutputPath;
            ejecutarEnTerminal(comando);
        } catch (Exception e) {
            System.out.println("error:" + e.getMessage());
        }
    }

    public void ejecutarEnTerminal(String comando) {
        try {
            String command;
            command = comando;

            final Process process = Runtime.getRuntime().exec(command);
            new Thread() {
                public void run() {
                    try {
                        InputStream is = process.getInputStream();
                        byte[] buffer = new byte[1024];
                        for (int count = 0; (count = is.read(buffer)) >= 0;) {
                            System.out.write(buffer, 0, count);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            new Thread() {
                public void run() {
                    try {
                        InputStream is = process.getErrorStream();
                        byte[] buffer = new byte[1024];
                        for (int count = 0; (count = is.read(buffer)) >= 0;) {
                            System.err.write(buffer, 0, count);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();

            int returnCode = process.waitFor();
            System.out.println("Return code = " + returnCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void escribir(String direccion, String texto) {
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

    public String textoDot(NodoCSS raiz) {
        if (raiz == null) {
            return null;
        }
        StringBuilder str = new StringBuilder();
        str.append("digraph G {" + "\n");
        str.append("node[shape=Mrecord]" + "\n");
        str.append(raiz.index + "[label=\"" + raiz.nombre + " | " + raiz.valor + "\",style=bold]" + "\n");

        recorridoAST(raiz, str);
        str.append("\n}");
        return str.toString();
    }

    public void recorridoAST(NodoCSS nodo, StringBuilder str) {
        if (nodo == null) {
            return;
        }

        for (int x = 0; x < nodo.hijos.size(); x++) {
            str.append(nodo.hijos.get(x).index + "[label=\"" + nodo.hijos.get(x).nombre + " | " + nodo.hijos.get(x).valor + "\",style=bold]" + "\n");
            str.append("{" + nodo.index + "}->{" + nodo.hijos.get(x).index + "}\n");
            recorridoAST(nodo.hijos.get(x), str);
        }
    }
}
