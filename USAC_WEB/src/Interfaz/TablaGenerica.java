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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import  java.util.Random;
import static java.lang.StrictMath.random;
import java.util.ArrayList;
import java.util.Hashtable;
import javafx.scene.control.CheckBox;
import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author fernando
 */
public class TablaGenerica extends JTable {
    //RECORDAR que propiedades puede tener TABLA,FILA y COLUMNA
    ArrayList<Coornenada> listabotones= new ArrayList<>();
    ArrayList<Coornenada> listaimagenes= new ArrayList<>();
    
    boolean yapaso=false;
    NodoDOM raizT;
    Object columnNames[];
    Object rowData[][];
    Hashtable<String, Propiedad> propiedadesTabla;


    
    public TablaGenerica(Hashtable<String, Propiedad> propiedadesTabla, NodoDOM raizT) {

        this.raizT = raizT;
        this.propiedadesTabla = propiedadesTabla;

        //setPropiedadesTabla();
        setDimension();
        //setDatos();

        final JButton jButton = new JButton();
        final JTextField jtextfield = new JTextField();
        setDefaultRenderer(String.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component tableCellRendererComponent = super.getTableCellRendererComponent(table, value, isSelected,
                        hasFocus, row, column);
                String cont = (String) value;
                //System.out.println(cont);
                //String contL[] = cont.split("_");
                
                
                /*String cmp="";
                if(contL.length>1){
                    cmp=contL[contL.length-1];
                    System.out.println("->"+cmp+"<-");
                }
                System.out.println("["+(String)value+"]");*/
                
                if(esBoton(row, column)){
                    jButton.setAction(createSameActionForEditorAndRenderer(table, value));
                    return jButton;
                }else if(esImagen(row, column)){
                    return tableCellRendererComponent;
                }else{
                    jtextfield.setText(String.valueOf(value));
                    return jtextfield;
                }
                /*
                if ("txt".equals(cmp)) {
                    System.out.println("es txt");
                    // default renderer for empty cells
                    
                    return tableCellRendererComponent;
                } else if ("btn".equals(cmp)) {
                    System.out.println("es btn");
                    jButton.setAction(createSameActionForEditorAndRenderer(table, value));
                    return jButton;
                } else {
                    return tableCellRendererComponent;
                }*/
                //return tableCellRendererComponent;

            }
        });
        
        JTable table= this;
        //setDefaultRenderer(Object.class, new MyRenderer());
        /*setDefaultEditor(String.class, new DefaultCellEditor(new JCheckBox()) { // JCheckBox is closest to a button...

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                    int column) {
                Component tableCellEditorComponent = super.getTableCellEditorComponent(table, value, isSelected, row,
                        column);
                if(esBoton(row, column)){
                    jButton.setAction(createSameActionForEditorAndRenderer(table, value));
                    return jButton;
                }else{
                    return jtextfield;
                }
                //
                //return jButton;
            }

        });*/
        //setDatos();
    }

    
/*
    
    @Override
    public TableCellEditor getCellEditor(int row, int column) {
        
        if(esBoton(row, column)){
            Component cmp=getDefaultRenderer(String.class).getTableCellRendererComponent(this, getValueAt(row, column),true,true,row,column);
            JButton btn =(JButton)cmp;
            System.out.println(cmp.getClass().getSimpleName()); 
            
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("holaaaaaaaaaaaaaaaa");
                }
            });
            //btn.setAction(createSameActionForEditorAndRenderer(this,getValueAt(row, column)));
            
            JCheckBox checkBox = new JCheckBox();
            DefaultCellEditor defaultCellEditor=new DefaultCellEditor(checkBox);
            setDefaultEditor(String.class, defaultCellEditor);
            return defaultCellEditor;
            /*JCheckBox checkBox = new JCheckBox();
             checkBox.setSelected(yapaso);
             JButton btn = new JButton();
             DefaultCellEditor defaultCellEditor=new DefaultCellEditor(checkBox);
             setDefaultEditor(String.class, defaultCellEditor);
             //Object tableCellEditorComponent = super.getDefaultEditor(String.class).getCellEditorValue();//getTableCellEditorComponent(this, column, true, row, column);
             
             btn.setAction(createSameActionForEditorAndRenderer(this,getValueAt(row, column)));
             
            return defaultCellEditor;*/
        /*}
        System.out.println("ROW:"+row+"COLUMN:"+column);
        return super.getCellEditor(row, column); //To change body of generated methods, choose Tools | Templates.
    }

    */


  

    
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

            //DefaultTableModel model = new DefaultTableModel(tam_filas, tam_colMax);
            //setModel(model);
            final Random random = new Random();
            DefaultTableModel tableModel = new DefaultTableModel(tam_filas, tam_colMax) {
                @Override
                public Class<?> getColumnClass(int arg0) {
                    // provide the default renderer and editor of String for empty cells
                    return String.class;
                }

                @Override
                public boolean isCellEditable(int row, int column) {
                    // do not request the editor for empty cells
                    /*if(row==2 && column==0){
                        return !"".equals(getValueAt(row, column));
                    }else{
                        return !"".equals(getValueAt(row, column));
                    }*/
                    return true;
                    
                }

                @Override
                public Object getValueAt(int row, int column) {
                    // some random table content
                    if (null == super.getValueAt(row, column)) {
                        /*int nextInt = random.nextInt(10);
                        if (nextInt > 5) {
                            super.setValueAt(String.format("cell %dx%d", row, column), row, column);
                        } else {
                            super.setValueAt("", row, column);
                        }*/
                        
                        for (int f = 0; f < raizT.hijos.size(); f++) {
                            NodoDOM fill = raizT.hijos.get(f);
                            for (int c = 0; c < fill.hijos.size(); c++) {
                                NodoDOM coll = fill.hijos.get(c);
                                super.setValueAt(getValElemento(coll,f,c), f, c);
                            }
                        }
                        yapaso=true;
                        super.setValueAt("", row, column);
                    }
                    return super.getValueAt(row, column);
                }

                @Override
                public void setValueAt(Object arg0, int arg1, int arg2) {
                    //fireTableCellUpdated(arg1, arg2);
                    //System.out.println("-=-=- " + arg0);
                    super.setValueAt(arg0, arg2, arg2);
                     //fireTableCellUpdated(arg1, arg2);
                    //System.out.println(arg0);
                    try {
                        //getModel().setValueAt(arg0, arg1, arg2);
                    } catch (Exception e) {
                    }
                    // prevent update to NULL
                }

            };
            setModel(tableModel);

            //System.out.println("numeroFilas: " + tam_filas + " ColsMax:" + tam_colMax);
        }
    }

    public void setPropiedadesTabla() {
        //******************************************************************Seteando ALTO
        try {
            setPreferredSize(new Dimension(getPreferredSize().width, Integer.valueOf(propiedadesTabla.get("alto").valor)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //******************************************************************Seteando ANCHO
        try {
            setPreferredSize(new Dimension(Integer.valueOf(propiedadesTabla.get("ancho").valor), getPreferredSize().height));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void setDatos() {
        for (int f = 0; f < raizT.hijos.size(); f++) {
            NodoDOM fill = raizT.hijos.get(f);
            for (int c = 0; c < fill.hijos.size(); c++) {
                NodoDOM coll = fill.hijos.get(c);
                //getModel().setValueAt(getValElemento(coll), f, c);
            }
        }

        //getModel().setValueAt(new JTextField("si"), 1, 3);
        //System.out.println(getCellRenderer(1, 3));
    }

    public String getValElemento(NodoDOM col,int fila,int columna) {
        String elemento = "";
        NodoDOM hijo = col.hijos.get(0);
        if (hijo.nombre.equals("boton")) {
            if(!yapaso){
                listabotones.add(new Coornenada(fila, columna));
            }
            elemento = hijo.propiedades.get("$text").valor;
        } else if (hijo.nombre.equals("imagen")) {
            elemento = hijo.propiedades.get("$text").valor;
        } else if (hijo.nombre.equals("texto")) {
            elemento = col.propiedades.get("$text").valor;
        }
        return elemento;
    }
    
    public boolean esBoton(int fila, int columna){
        boolean es=false;
        for (Coornenada boton : listabotones) {
            if(boton.fila==fila && boton.columna==columna){
                es=true;
                break;
            }
        }
        return es;
    }
    
    public boolean esImagen(int fila, int columna){
        boolean es=false;
        for (Coornenada imagen : listaimagenes) {
            if(imagen.fila==fila && imagen.columna==columna){
                es=true;
                break;
            }
        }
        return es;
    }
    
    public boolean esTexto(){
        boolean es=false;
        return es;
    }

    private static AbstractAction createSameActionForEditorAndRenderer(JTable table, Object value) {
        return new AbstractAction((String) value) {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                SwingUtilities.invokeLater(() -> {
                    System.out.println("<->"+arg0);
                    JOptionPane.showMessageDialog(table, String.format("clicked on %s", value));
                });
                table.getCellEditor().stopCellEditing();
                table.repaint();
            }
        };
    }
    
    
}

class MyRenderer implements TableCellRenderer {
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
      boolean hasFocus, int row, int column) {
    JTextField editor = new JTextField();
    if (value != null)
      editor.setText(value.toString());
    editor.setBackground((row % 2 == 0) ? Color.white : Color.cyan);
    return editor;
  }
}

class Coornenada{
    public int fila;
    public int columna;

    public Coornenada(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public Coornenada() {
    }
    
}