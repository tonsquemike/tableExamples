/*
 * To change this license COLUMNAS, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablasejemplo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;


 public class TableAddTest extends JPanel {

    private static final int N_FILAS = 8;//número de filas
    private static String[] COLUMNAS = {"cuenta", "nombre", "apm", "app", "nada"};//cabecera de la tabla,, se crean cuatro columnas
    
    private DefaultTableModel tableModel = new DefaultTableModel(null, COLUMNAS) {
    
        @Override
        public Class<?> getColumnClass(int col) {
            return getValueAt(0, col).getClass();
        }
    };
    
    private JTable jTable1 = new JTable(tableModel){
    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
        Component comp = super.prepareRenderer(renderer, row, col);
        Object value = getModel().getValueAt(row, col);
        try{
        char c = (char)value;
        if (getSelectedRow() == row) {
            if (c==('A')) {
                comp.setBackground(Color.red);
            } else if (c==('B')) {
                comp.setBackground(Color.green);
            } else {
                comp.setBackground(Color.white);
            }
        } else {
            comp.setBackground(Color.BLUE);
        }
        }catch(Exception e){}
        return comp;
    }
    };
    
    
    private JScrollPane scrollPane = new JScrollPane(jTable1);
    private JScrollBar vScroll = scrollPane.getVerticalScrollBar();
    private int fila;
    private boolean isAutoScroll;

    public TableAddTest() {
        this.setLayout(new BorderLayout());
        Dimension dim = new Dimension(500, N_FILAS * jTable1.getRowHeight());
        jTable1.setPreferredScrollableViewportSize(dim);//asigna tamaño de toda la tabla
        
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(10);//camiba tamanio de columna 0
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(10); //cambia tamanio de columna 1
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(10); //cambia tamanio de columna 2
        for (int i = 0; i < N_FILAS; i++) {
            addRow();
        }
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        vScroll.addAdjustmentListener(new AdjustmentListener() 
        {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                isAutoScroll = !e.getValueIsAdjusting();
            }
        });
        
        this.add(scrollPane, BorderLayout.CENTER);
        JPanel panel = new JPanel();
        panel.add(new JButton(new AbstractAction("Agrega fila") {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRow();
            }
        }));
        
        this.add(panel, BorderLayout.SOUTH);
    }

    private void addRow() {
        char c = (char) ('A' + fila++ % 26);
        tableModel.addRow(new Object[]
        {
                Character.valueOf(c),                    //valor columna 1
                String.valueOf(c) + String.valueOf(fila),//valor columna 2
                Integer.valueOf(fila),                   //valor columna 3
                Boolean.valueOf(fila % 2 == 0),          //valor columna 4
                "agrega tus variables"                   //valor columna 5
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame f = new JFrame();
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                TableAddTest nlt = new TableAddTest();
                f.add(nlt);// puede agregarser a un jPanel existente
                f.pack();
                f.setLocationRelativeTo(null); //coloca ventana en el centro
                f.setVisible(true);
            }
        });
    }
}