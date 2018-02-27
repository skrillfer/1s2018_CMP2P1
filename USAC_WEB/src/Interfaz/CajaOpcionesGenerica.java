/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Estructuras.NodoDOM;
import Estructuras.Propiedad;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

/**
 *
 * @author fernando
 */
public class CajaOpcionesGenerica extends JComboBox {
    ArrayList<opcion> lista_opciones = new ArrayList<>();
    ArrayList<Color> lista_colores = new ArrayList<>();
    Colores meta_colores;
    Hashtable<String, Propiedad> propiedades;
    ArrayList<NodoDOM> opciones;

    public CajaOpcionesGenerica(Hashtable<String, Propiedad> propiedades, ArrayList<NodoDOM> opciones,Colores meta_colores) {
        this.meta_colores=meta_colores;
        this.propiedades = propiedades;
        this.opciones = opciones;
        addItem("-------");
        lista_opciones.add(new opcion("-------", "-------"));
        lista_colores.add(Color.BLACK);
        setDatos();
        setPropiedades();

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> combo = (JComboBox<String>) e.getSource();
                String selectedBook = (String) combo.getSelectedItem();

                System.out.println(lista_opciones.get(combo.getSelectedIndex()).valor);
                
                /*if (selectedBook.equals("Effective Java")) {
                    System.out.println("Good choice!");
                } else if (selectedBook.equals("Head First Java")) {
                    System.out.println("Nice pick, too!");
                }*/
            }
        });
    }

    public void setPropiedades() {
        setColorCaja();
        ComboBoxRenderer renderer = new ComboBoxRenderer(this);

        renderer.setColors(lista_colores);
        renderer.setStrings(lista_opciones);
        System.out.println(lista_colores.size()+","+lista_opciones.size());
        setRenderer(renderer);
    }

    public void setDatos() {
        for (NodoDOM opcion : opciones) {
            String texto = opcion.propiedades.get("$text").valor.trim();
            String valor = opcion.propiedades.get("valor").valor.trim();
            String fondo = opcion.propiedades.get("fondo").valor.trim();
            if(valor.equals("")){
                valor=texto;
            }
            lista_opciones.add(new opcion(texto, valor));
            Color color=meta_colores.obtenerColor(fondo);
            if (color==null) {
                System.out.println("era null");
                color= Color.BLACK;
            }
            lista_colores.add(color);
            
            addItem(lista_opciones.get(lista_opciones.size()-1).texto);
        }
    }
    
    
    public void setColorCaja(){
        try {
            String fondo = propiedades.get("fondo").valor;
            Color color=meta_colores.obtenerColor(fondo);
            if (color!=null) {
                setBackground(color);
                
            }
        } catch (Exception e) {}
        
    }
    
    public void setColorItem(){
        
    }

}

class opcion{
    public String texto;
    public String valor;

    public opcion(String texto, String valor) {
        this.texto = texto;
        this.valor = valor;
    }
}

class ComboBoxRenderer extends JPanel implements ListCellRenderer
{

    private static final long serialVersionUID = -1L;
    private ArrayList<Color> colors;
    private ArrayList<opcion> strings;

    JPanel textPanel;
    JLabel text;

    public ComboBoxRenderer(JComboBox combo) {

        textPanel = new JPanel();
        textPanel.add(this);
        text = new JLabel();
        text.setOpaque(true);
        text.setFont(combo.getFont());
        textPanel.add(text);
    }

    public void setColors(ArrayList<Color> col)
    {
        colors = col;
    }

    public void setStrings(ArrayList<opcion> str)
    {
        strings = str;
    }

    public ArrayList<Color> getColors()
    {
        return colors;
    }

    public ArrayList<opcion> getStrings()
    {
        return strings;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {

        if (isSelected)
        {
            setBackground(list.getSelectionBackground());
        }
        else
        {
            setBackground(Color.WHITE);
        }

        if (colors.size() != strings.size())
        {
            System.out.println("colors.length does not equal strings.length");
            return this;
        }
        else if (colors == null)
        {
            System.out.println("use setColors first.");
            return this;
        }
        else if (strings == null)
        {
            System.out.println("use setStrings first.");
            return this;
        }

        text.setBackground(getBackground());

        text.setText(value.toString());
        if (index>-1) {
            text.setForeground(colors.get(index));
        }
        return text;
    }
}