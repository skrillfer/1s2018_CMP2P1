/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Generadores;

import java.io.File;

/**
 *
 * @author fernando
 */
public class CupCJS {
    public static void main(String[] args) {
        String opciones[] = new String[5];
        opciones[0]="-destdir";
        opciones[1]="src"
                +File.separator+
                "Analizadores"+
                File.separator+
                "LenguajeCJS";
        opciones[2]="-parser";
        opciones[3]="AS_CJS";
        opciones[4]="src"
                +File.separator+
                "Analizadores"
                +File.separator+
                "LenguajeCJS"+
                File.separator+
                "S_CJS.cup";
        try {
            java_cup.Main.main(opciones);
            }
        catch (Exception e) {
            System.out.print(e.getMessage().toString());
            }
    }
}
