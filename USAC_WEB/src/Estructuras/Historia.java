/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;

import java.util.Calendar;

/**
 *
 * @author fernando
 */
public class Historia {
    public String fecha="";
    public String hora="";
    public String ruta="";

    public Historia(String ruta) {
        this.ruta=ruta;
        Calendar cal = Calendar.getInstance();
        hora=String.valueOf(cal.get(Calendar.HOUR_OF_DAY))+":"+String.valueOf(cal.get(Calendar.MINUTE))+":"+String.valueOf(cal.get(Calendar.SECOND));
        fecha=String.valueOf(cal.get(Calendar.DAY_OF_MONTH))+"/"+String.valueOf(cal.get(Calendar.MONDAY))+"/"+String.valueOf(cal.get(Calendar.YEAR));
    }
    
}
