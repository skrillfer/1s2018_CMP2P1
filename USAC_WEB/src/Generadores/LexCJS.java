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
public class LexCJS {
    public static void main(String[] args) {
        JFlex.Main.generate(new File(
                "src"+File.separator+"Analizadores"+File.separator+"LenguajeCJS"
                + File.separator + "Lex_CJS.flex"));
    }
}
