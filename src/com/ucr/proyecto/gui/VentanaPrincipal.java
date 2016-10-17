package com.ucr.proyecto.gui;

import com.ucr.proyecto.util.Constantes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.Timer;

/**
 *
 * @author Nelson
 */
public class VentanaPrincipal extends JFrame {

    private JDesktopPane desktopPane;
    private TablaTransacciones tablaTransacciones;
    private Timer timer;

    public VentanaPrincipal() {
        iniciar();
    }

    private void iniciar() {
        this.setVisible(true);
        this.setSize(800, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        //empleado, double cantidad, String funcion, empleadoDestino, String detalle, String fecha
        //usuario, contrasena, saldo, numCuenta, nombre, codEmpleado

        tablaTransacciones = new TablaTransacciones(Constantes.listaTransacciones);

        timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tablaTransacciones.setListaTransacciones(Constantes.listaTransacciones);
                tablaTransacciones.llenarTabla();
                timer.restart();
            }
        });
        timer.start();

        desktopPane = new JDesktopPane();
        desktopPane.setBounds(0, 0, 800, 600);

        this.desktopPane.add(this.tablaTransacciones);

        this.add(this.desktopPane);
    }
}
