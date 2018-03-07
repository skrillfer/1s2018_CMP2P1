/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import javax.swing.JOptionPane;

/**
 *
 * @author fernando
 */
public class AlertaGenerica extends JOptionPane{

    
    public AlertaGenerica(String informacion) {
        showMessageDialog(this, informacion,"Mensaje",JOptionPane.INFORMATION_MESSAGE);
    }
    
}
