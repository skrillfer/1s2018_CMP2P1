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
    
    
    public boolean removeListaGrupo(Componente o){
        try {
            this.lista.remove(o);
            return true;
        } catch (Exception e) {
            return false;
        }
        
    }
    public boolean cambiarGrupo(String grupo){
        if (grupo.equals(""))
            return false;
        
        for (Componente componente : lista) {
            System.out.println("CAMBIANDO GRUPO A:"+grupo);
            //componente.=name;
            switch(componente.tipo){
                case "opcion":
                    ((OpcionGenerica)componente.objeto).propiedades.get("grupo").valor=grupo;
                    
                    break;
                case "imagen":
                    ((ImagenGenerica)componente.objeto).propiedades.get("grupo").valor=grupo;
                    
                    break;
                case "boton":
                    ((BotonGenerico)componente.objeto).propiedades.get("grupo").valor=grupo;
                    
                    break;
                case "enlace":
                    ((EnlaceGenerico)componente.objeto).propiedades.get("grupo").valor=grupo;
                    
                    break;
                case "panel":
                    ((PanelGenerico)componente.objeto).propiedades.get("grupo").valor=grupo;
                    
                    break;
                case "spinner":    
                    ((SpinnerGenerico)componente.objeto).propiedades.get("grupo").valor=grupo;
                    
                    break;
                case "tabla":
                    ((TablaGenerica2)componente.objeto).propiedadesTabla.get("grupo").valor=grupo;
                    
                    break;
                case "caja":
                    ((CajaTextoGenerica)componente.objeto).propiedades.get("grupo").valor=grupo;
                    
                    break;
                case "cajaopciones":
                    ((CajaOpcionesGenerica)componente.objeto).propiedades.get("grupo").valor=grupo;
                    
                    break;
                case "cajatexto":    
                    ((CajaTextoGenerica)componente.objeto).propiedades.get("grupo").valor=grupo;
                    
                    break;
                case "texto":
                    ((TextoGenerico)componente.objeto).propiedades.get("grupo").valor=grupo;
                    
                    break;
                case "areatexto": 
                    ((AreaTextoGenerica)componente.objeto).propiedades.get("grupo").valor=grupo;
                    
                    break;
                        
            }
        }
        return true;
    }
    
    public boolean cambiarId(String name){
        if (name.equals(""))
            return false;
            
        for (Componente componente : lista) {
            System.out.println("CAMBIANDO A:"+name);
            componente.id=name;
            switch(componente.tipo){
                case "opcion":
                    ((OpcionGenerica)componente.objeto).propiedades.get("id").valor=name;
                    ((OpcionGenerica)componente.objeto).setName(name);
                    break;
                case "imagen":
                    ((ImagenGenerica)componente.objeto).propiedades.get("id").valor=name;
                    ((ImagenGenerica)componente.objeto).setName(name);
                    break;
                case "boton":
                    ((BotonGenerico)componente.objeto).propiedades.get("id").valor=name;
                    ((BotonGenerico)componente.objeto).setName(name);
                    break;
                case "enlace":
                    ((EnlaceGenerico)componente.objeto).propiedades.get("id").valor=name;
                    ((EnlaceGenerico)componente.objeto).setName(name);
                    break;
                case "panel":
                    ((PanelGenerico)componente.objeto).propiedades.get("id").valor=name;
                    ((PanelGenerico)componente.objeto).setName(name);
                    break;
                case "spinner":    
                    ((SpinnerGenerico)componente.objeto).propiedades.get("id").valor=name;
                    ((SpinnerGenerico)componente.objeto).setName(name);
                    break;
                case "tabla":
                    ((TablaGenerica2)componente.objeto).propiedadesTabla.get("id").valor=name;
                    ((TablaGenerica2)componente.objeto).setName(name);
                    break;
                case "caja":
                    ((CajaTextoGenerica)componente.objeto).propiedades.get("id").valor=name;
                    ((CajaTextoGenerica)componente.objeto).setName(name);
                    break;
                case "cajaopciones":
                    ((CajaOpcionesGenerica)componente.objeto).propiedades.get("id").valor=name;
                    ((CajaOpcionesGenerica)componente.objeto).setName(name);
                    break;
                case "cajatexto":    
                    ((CajaTextoGenerica)componente.objeto).propiedades.get("id").valor=name;
                    ((CajaTextoGenerica)componente.objeto).setName(name);
                    break;
                case "texto":
                    ((TextoGenerico)componente.objeto).propiedades.get("id").valor=name;
                    ((TextoGenerico)componente.objeto).setName(name);
                    break;
                case "areatexto": 
                    ((AreaTextoGenerica)componente.objeto).propiedades.get("id").valor=name;
                    ((AreaTextoGenerica)componente.objeto).setName(name);
                    break;
                        
            }
        }
        return true;
    }
}
