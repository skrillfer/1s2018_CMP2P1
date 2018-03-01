/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import java.util.ArrayList;

/**
 *
 * @author fernando
 */
public class Lista {
    private ArrayList<Componente> lista = new ArrayList<>();
    
    public void add(Componente dato){
        this.lista.add(dato);
    }
    
    public int size(){
        return this.lista.size();
    }
    
    public ArrayList<Componente> getLista(){
        return this.lista;
    }
}
