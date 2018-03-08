/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Generadores;

import Analizadores.LenguajeCHTML.AL_HTML;
import Analizadores.LenguajeCHTML.AS_HTML;

import Analizadores.LenguajeCCSS.AL_CCSS;
import Analizadores.LenguajeCCSS.AS_CCSS;
import Arboles_Generados.Arbol_CCSS;

import Arboles_Generados.Arbol_DOM;
import Estructuras.NodoCSS;
import Estructuras.NodoDOM;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 *
 * @author fernando
 */
public class Principal {

    public static void main(String[] args) throws FileNotFoundException {
        /*JFrame frame = new JFrame("Testing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Object[][] licData = {{"License 1", "0.0.0.0", "connect", "disconnect", ""},{"License 2", "123.123.123", "", "", ""},{"License 3", "42.23.4", "connect", "disconnect", "delete"}};

    ConnTableModel licConnModel = new ConnTableModel(licData);

    //this.setLayout(new MigLayout("", "[grow]", "[][grow][][][][][][][grow][][][][][]"));
    //this.setSize(new Dimension(500, 300));
    //JLabel lblLicenses = new JLabel("Licenses");
    //this.add(lblLicenses, "cell 0 0,growx");
    
        JTable tab= new JTable(licConnModel);
        ButtonColumnContainer container = new ButtonColumnContainer();
        container.createButtonColumn(tab, 1, 0);
        container.createButtonColumn(tab, 2, 0);
        container.createButtonColumn(tab, 3, 0);
        
        frame.add(tab);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        */
        //new Principal().CompilarCHTML("");
        
        
        //new Principal().CompilarCCSS("");
        String texto = "hola mundo";
        new usac_web.USAC_WEB().CompilarCJS("");
        
        /*String dato ="_hola";
        String d[]=dato.split("_");
        System.out.println(d[1]);*/
    }

    public NodoCSS CompilarCCSS(String texto) throws FileNotFoundException{
        NodoCSS retorno = null;
        AL_CCSS lex = new AL_CCSS(new FileReader("estilos.txt"));
        AS_CCSS parser  = new AS_CCSS(lex);
        
        try {
            parser.parse();
            NodoCSS raiz = parser.getRoot();
            Arbol_CCSS gen_arbol = new Arbol_CCSS();
            if (raiz != null) {
                gen_arbol.generacion_arbolCCSS(raiz);
            }else{
                System.out.println("raiz e nula");
            }
            retorno = raiz;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return retorno;
    }
    
    public NodoDOM CompilarCHTML(String texto) throws FileNotFoundException {
        NodoDOM retorno = null;

        //escribir("tmpchtml.txt",texto);
        AL_HTML lex = new AL_HTML(new FileReader("last.txt"));//se le pasa al analizador lexico lo que se escribio
        AS_HTML parser = new AS_HTML(lex);

        try {
            parser.parse();
            NodoDOM raiz = parser.getRoot();
            Arbol_DOM gen_arbol = new Arbol_DOM();
            if (raiz != null) {
                gen_arbol.generacion_arbolCJS(raiz);
            }else{
                System.out.println("raiz e nula");
            }
            retorno = raiz;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return retorno;
    }

}
