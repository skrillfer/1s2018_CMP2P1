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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

/**
 *
 * @author fernando
 */
public class CajaOpcionesGenerica extends JComboBox {
    public boolean mayuscula=false;
    public boolean minuscula=false;
    public boolean capital_t=false; 
    
    ArrayList<opcion> lista_opciones = new ArrayList<>();
    ArrayList<Color> lista_colores = new ArrayList<>();
    ArrayList<OpcionGenerica> lista_generica = new ArrayList<>();
    
    Colores meta_colores;
    Hashtable<String, Propiedad> propiedades;
    ArrayList<NodoDOM> opciones;

    public CajaOpcionesGenerica(Hashtable<String, Propiedad> propiedades, ArrayList<NodoDOM> opciones,Colores meta_colores) {
        this.meta_colores=meta_colores;
        this.propiedades = propiedades;
        this.opciones = opciones;
        //addItem("-------");
        //lista_opciones.add(new opcion("-------", "-------",null));
        //lista_colores.add(Color.BLACK);
        
        setPropiedades();

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> combo = (JComboBox<String>) e.getSource();
                String selectedBook = (String) combo.getSelectedItem();
                System.out.println("fue seleccionado:"+combo.getSelectedIndex());
                //System.out.println(lista_opciones.get(combo.getSelectedIndex()).valor);
                
                /*if (selectedBook.equals("Effective Java")) {
                    System.out.println("Good choice!");
                } else if (selectedBook.equals("Head First Java")) {
                    System.out.println("Nice pick, too!");
                }*/
            }
        });
    }

    public void setPropiedades() {
        
        setId();
        setGrupo();
        setAlineado();
        setTexto();
        setFondo();
        setAlto();
        setAncho();
        setDatos();
        
        ComboBoxRenderer renderer = new ComboBoxRenderer(this,mayuscula,minuscula,capital_t);
        renderer.setColors(lista_colores);
        renderer.setStrings(lista_opciones);
        renderer.setGenerica(lista_generica);
        setRenderer(renderer);
        
    }
    
    
    public void setId() {
        try {
            setName(propiedades.get("id").valor.trim());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        updateUI();
    }

    public void setGrupo() {
        try {
            propiedades.get("grupo").valor = propiedades.get("grupo").valor.trim();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        updateUI();
    }
    
    public void setAlineado(){
        try {
            String alineado = propiedades.get("alineado").valor.trim();
            switch(alineado){
                case "izquierda":
                    setLayout(new FlowLayout(FlowLayout.LEFT));
                    //setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                    break;
                case "derecha":
                    setLayout(new FlowLayout(FlowLayout.RIGHT));
                    //setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    break;
                case "centrado":
                    setLayout(new FlowLayout(FlowLayout.CENTER));
                    break;
                case "justificado":
                    setLayout(new FlowLayout(FlowLayout.LEADING));
                    break;    
            }
        } catch (Exception e) {}
        updateUI();
    }
    
    public void setTexto(){
         
    }
    
    public void setAlto(){
        try {
                setPreferredSize(new Dimension(getPreferredSize().width, Integer.valueOf(propiedades.get("alto").valor.trim())));
            } catch (Exception e) {System.out.println(e.getMessage());}
        updateUI();
    }
    
    public void setAncho(){
        try {
                setPreferredSize(new Dimension(Integer.valueOf(propiedades.get("ancho").valor.trim()), getPreferredSize().height));
            } catch (Exception e) {System.out.println(e.getMessage());}
        updateUI();
    }
    
     public void setFondo(){
        try {
            String fondo = propiedades.get("fondo").valor;
            Color color=meta_colores.obtenerColor(fondo);
            if (color!=null) {
                setBackground(color);
                
            }
        } catch (Exception e) {}
    }
    public void setDatos() {
        for (NodoDOM opcion : opciones) {
            String texto = opcion.propiedades.get("$text").valor.trim();
            String valor = opcion.propiedades.get("valor").valor.trim();
            String fondo = opcion.propiedades.get("fondo").valor.trim();
            
            if(valor.equals("")){
                valor=texto;
            }
            OpcionGenerica opciong = new OpcionGenerica(opcion.propiedades);
            
            lista_opciones.add(new opcion(texto, valor));
            lista_generica.add(opciong);
            
            
            Template.addComponente(opciong.getName(),"opcion",opciong,this,"cajaopciones",getName());
            
            Color color=meta_colores.obtenerColor(fondo);
            if (color==null) {
                System.out.println("era null");
                color= Color.lightGray;
            }
            
            lista_colores.add(color);
            
            addItem(lista_opciones.get(lista_opciones.size()-1).texto);
        }
    }
    
    public void setMayu_Minu_Capital(int num){
        switch (num) {
            case 1:
                // es para minusculas
                minuscula=true;
                mayuscula=false;
                capital_t=false;
                break;
            case 2:
                // es para mayusculas
                minuscula=false;
                mayuscula=true;
                capital_t=false;
                break;
            case 3:
                // es para capital-t
                minuscula=false;
                mayuscula=false;
                capital_t=true;
                break;
            default:
                break;
        }
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

    private boolean mayuscula=false;
    private boolean minuscula=false;
    private boolean capital_t=false;
    
    private static final long serialVersionUID = -1L;
    private ArrayList<Color> colors;
    private ArrayList<opcion> strings;
    private ArrayList<OpcionGenerica> generica;

    JPanel textPanel;
    JLabel text;
    JComboBox combo;
    public ComboBoxRenderer(JComboBox combo,boolean mayuscula,boolean minuscula, boolean capital_t) {
        System.out.println("siempre paso aqui");
        this.mayuscula=mayuscula;
        this.minuscula=minuscula;
        this.capital_t=capital_t;
        this.combo=combo;
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

    public void setGenerica(ArrayList<OpcionGenerica> generica){
        this.generica=generica;
    }
    
    public void setStrings(ArrayList<opcion> str){
        strings = str;
    }

    public ArrayList<Color> getColors(){
        return colors;
    }

    public ArrayList<OpcionGenerica> getGenerica(){
        return this.generica;
    } 
    
    public ArrayList<opcion> getStrings(){
        return strings;
    }
    

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {

        
        if (isSelected){
            setBackground(list.getSelectionBackground());
            //setBackground(Color.green);
        }else{
            setBackground(Color.WHITE);
            //setBackground(Color.green);
            /*if(index==-1){
                setBackground(Color.WHITE);
            }else{
                try {
                    JLabel tmp = (JLabel) generica.get(index);
                    
                    text.setBackground(Color.PINK);
                    text.updateUI();
                    //System.out.println("!!!! obtuve"+colors.get(index));
                } catch (Exception e) {
                    System.out.println("no lo pude obtener");
                }
            }*/
            
        }

        if (colors.size() != strings.size()){
            System.out.println("colors.length does not equal strings.length");
            return this;
        }else if (colors == null){
            System.out.println("use setColors first.");
            return this;
        }else if (strings == null){
            System.out.println("use setStrings first.");
            return this;
        }
        
        //
        text.setText(value.toString());
        if (index>-1) {
            
            OpcionGenerica opcion = generica.get(index);
            text.setOpaque(opcion.isOpaque());
            text.setBackground(opcion.getBackground());
            text.setForeground(opcion.getForeground());
            text.setFont(opcion.getFont());
            String txt=opcion.getText();
            try {
                
                if (opcion.minuscula) {
                    txt = txt.toLowerCase();
                } else if (opcion.mayuscula) {
                    txt = txt.toUpperCase();
                } else if (opcion.capital_t) {
                    txt = Template.toCapital_t(txt);
                }
            } catch (Exception e) {}
            
            try{
                String alineado = opcion.propiedades.get("alineado").valor;
                switch(alineado.toLowerCase()){
                    case "centrado":
                        text.setHorizontalAlignment(JTextField.CENTER);
                        break;
                    case "derecha":
                        text.setHorizontalAlignment(JTextField.RIGHT);
                        break;
                    case "izquierda":
                        text.setHorizontalAlignment(JTextField.LEFT);
                        break;
                }
            }catch(Exception e){}
            
            text.setText(txt);
        }else{
            String txt=value.toString();
            try {
                
                if (minuscula) {
                    txt = txt.toLowerCase();
                } else if (mayuscula) {
                    txt = txt.toUpperCase();
                } else if (capital_t) {
                    txt = Template.toCapital_t(txt);
                }
            } catch (Exception e) {}
            text.setText(txt);
            
        }
        
        return text;
    }
    
     
}