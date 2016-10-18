package com.ucr.proyecto.gui;

import com.ucr.proyecto.domain.Transaccion;
import com.ucr.proyecto.util.Constantes;
import java.awt.Color;
import java.awt.Component;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Nelson
 */
public class TablaTransacciones extends JPanel {

    private List<Transaccion> listaTransacciones;
    private String[] encabezado = {"Fecha", "Función", "Detalle", "Cuenta origen", "Cuenta destino", "Cantidad"};
    private String [][] data = {{"Cargando datos", "", "", "", "", ""}};
    private DefaultTableModel modeloTabla;
    private JPanel panel;
    private JTable tabla;

    public TablaTransacciones(List<Transaccion> listaTransacciones) {
        this.listaTransacciones = listaTransacciones;
        iniciar();
    }

    private void iniciar() {
        this.setSize(770, 550);
        this.setLayout(null);
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setLayout(null);

        tabla = new JTable(data, encabezado) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component comp = super.prepareRenderer(renderer, row, col);
                Object value = getModel().getValueAt(row, 1);
                if(value.equals("debitar")) {
                    comp.setBackground(Color.red);
                } else if (value.equals("acreditar")){
                    comp.setBackground(Color.green);
                } else if (value.equals("ahorroautomatico")){
                    comp.setBackground(Color.orange);
                } else {
                    comp.setBackground(Color.WHITE);
                }
                return comp;
            }
        };
        tabla.setModel(modeloTabla);
        tabla.setAutoCreateRowSorter(true);

        tabla.setFillsViewportHeight(true);
        tabla.setGridColor(Color.black);
        tabla.setCellSelectionEnabled(true);

        JScrollPane scrollPane = new JScrollPane(tabla);
        panel.add(scrollPane).setBounds(20, 40, 720, 500);
        this.add(panel).setBounds(0, 0, 770, 550);
        llenarTabla();
    }

    @Override
    public void updateUI() {
        super.updateUI();
    }

    public void llenarTabla() {   
       updateUI();
        
        if (!listaTransacciones.isEmpty()) {
            data = new String[listaTransacciones.size()][6];
            //{"Fecha", "Función", "Detalle", "Cuenta origen", "Cuenta destino", "Cantidad"}

            for (int i = 0; i < listaTransacciones.size(); i++) {
                data[i][0] = listaTransacciones.get(i).getFecha();
                data[i][1] = listaTransacciones.get(i).getFuncion();
                data[i][2] = listaTransacciones.get(i).getDetalle();
                data[i][3] = listaTransacciones.get(i).getEmpleado().getNumCuenta();
                data[i][4] = listaTransacciones.get(i).getEmpleadoDestino().getNumCuenta();
                data[i][5] = listaTransacciones.get(i).getCantidad() + "";
            }
        }
        modeloTabla.setDataVector(data, encabezado);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }

    public List<Transaccion> getListaTransacciones() {
        return listaTransacciones;
    }

    public void setListaTransacciones(List<Transaccion> listaTransacciones) {
        this.listaTransacciones = listaTransacciones;
    }
}

