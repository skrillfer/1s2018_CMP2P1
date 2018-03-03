/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Estructuras.NodoDOM;
import Estructuras.Propiedad;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Hashtable;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author fernando
 */
public class TablaGenerica2 extends JPanel {
    int filas =0;
    int columnas=0;
    NodoDOM raizT;

    Hashtable<String, Propiedad> propiedadesTabla;

    public TablaGenerica2(Hashtable<String, Propiedad> propiedadesTabla, NodoDOM raizT) {
        this.propiedadesTabla=this.propiedadesTabla;
        this.raizT=raizT;
        setDimension();
        if(filas>0 && columnas>0){
            setLayout(new GridLayout(filas, columnas));
        }
        setName("");
        setDatos();
        setPreferredSize(new Dimension(300, 300));
    }
    
    
    public void setDimension() {
        if (raizT != null) {
            int tam_filas = raizT.hijos.size();
            int tam_colMax = 0;
            for (int i = 0; i < tam_filas; i++) {
                int tmpTam = raizT.hijos.get(i).hijos.size();
                if (tmpTam > tam_colMax) {
                    tam_colMax = tmpTam;
                }
            }
            filas=tam_filas;
            columnas=tam_colMax;
            //System.out.println("numeroFilas: " + tam_filas + " ColsMax:" + tam_colMax);
        }
    }

    public void setDatos() {
        for (int f = 0; f < raizT.hijos.size(); f++) {
            NodoDOM fill = raizT.hijos.get(f);
            for (int c = 0; c < columnas; c++) {
                if(c<fill.hijos.size()){
                    NodoDOM coll = fill.hijos.get(c);
                    NodoDOM hijo = coll.hijos.get(0);
                    if(hijo.nombre.equals("boton")){
                       add(new BotonGenerico(hijo.propiedades));
                    }else if(hijo.nombre.equals("imagen")){
                       add(new ImagenGenerica(hijo.propiedades));
                    }else if(hijo.nombre.equals("texto")){
                       add(new JTextField());
                    }
                }else{
                    add( new JTextField());
                }
            }
        }
    }
    
    public String getValElemento(NodoDOM col) {
        String elemento = "";
        NodoDOM hijo = col.hijos.get(0);
        if (hijo.nombre.equals("boton")) {
            
            elemento = hijo.propiedades.get("$text").valor;
        } else if (hijo.nombre.equals("imagen")) {
            elemento = hijo.propiedades.get("$text").valor;
        } else if (hijo.nombre.equals("texto")) {
            elemento = col.propiedades.get("$text").valor;
        }
        return elemento;
    }
}
