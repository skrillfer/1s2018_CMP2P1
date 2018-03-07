/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * @author fernando
 */
public class Lista_Cargadas {
    String ultima_agregada="";
    ArrayList<String> listaPerfecta = new ArrayList<>();
    
    int index=-1;
    int pt=-1;
    
    public void add(String rutaNueva){
        if(!ultima_agregada.equals(rutaNueva)){
            listaPerfecta.add(rutaNueva);
            index=(listaPerfecta.size()-1);
            ultima_agregada=rutaNueva;
        }
    }
    
    public int getIndex(){
        return this.index;
    }
    
    public String getPagina(int index){
        if(index>-1 && index<=(this.listaPerfecta.size()-1)){
            return this.listaPerfecta.get(index);
        }else{
            return "";
        }
    }
    
    public int size(){
        return this.listaPerfecta.size();
    }
    
    public int getPt(){
        return this.pt;
    }
    
    public void setPt(int pt){
        this.pt=pt;
    }
    
}
