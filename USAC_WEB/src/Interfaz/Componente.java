/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

/**
 *
 * @author fernando
 */

public class Componente {
    public String id;
    public String tipo; // boton,imagen,enlace etc.
    public Object objeto ;
    public Componente padre;
    public Componente(String id, String tipo, Object objeto,Componente padre) {
        this.id = id;
        this.tipo = tipo;
        this.objeto = objeto;
        this.padre=padre;
    }

    public Componente() {
        this.id="";
        this.tipo="";
        this.objeto=null;
        this.padre=null;
    }
}
