package com.ucr.proyecto.gui;

import com.ucr.proyecto.util.Constantes;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Nelson
 */
public class VentanaPrincipal extends JFrame{

    private JDesktopPane desktopPane;
    private TablaTransacciones tablaTransacciones;
    private Timer timer;
    private JLabel color1, color2, color3, color4;
    private JPanel panelColor1, panelColor2, panelColor3, panelColor4, borde;
    private JCheckBox checkBox;

    public VentanaPrincipal() {
        iniciar();
    }

    private void iniciar() {
        repaint();
        this.setVisible(true);
        this.setSize(950, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        color1 = new JLabel("Acreditar");
        color2 = new JLabel("Debitar");
        color3 = new JLabel("Ahorro automático");
        color4 = new JLabel("Acreditar otra cuenta");
        
        panelColor1 = new JPanel();
        panelColor1.setBackground(Color.green);
        panelColor1.setSize(20, 20); 
        panelColor2 = new JPanel();
        panelColor2.setBackground(Color.red);
        panelColor2.setSize(20, 20); 
        panelColor3 = new JPanel();
        panelColor3.setBackground(Color.orange);
        panelColor3.setSize(20, 20); 
        panelColor4 = new JPanel();
        panelColor4.setBackground(Color.white);
        borde = new JPanel();
        borde.setBackground(Color.black);
        borde.setSize(20, 20);
        panelColor4.setSize(18, 18); 
        
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

        this.desktopPane = new JDesktopPane();
        this.desktopPane.setBounds(0, 0, 800, 600);
        
        this.desktopPane.add(this.color1).setBounds(800, 80, 200, 20); 
        this.desktopPane.add(this.panelColor1).setLocation(770, 80);
        this.desktopPane.add(this.color2).setBounds(800, 110, 200, 20); 
        this.desktopPane.add(this.panelColor2).setLocation(770, 110);
        this.desktopPane.add(this.color3).setBounds(800, 140, 200, 20); 
        this.desktopPane.add(this.panelColor3).setLocation(770, 140);
        this.desktopPane.add(this.color4).setBounds(800, 170, 200, 20); 
        this.desktopPane.add(this.panelColor4).setLocation(771, 171);
        this.desktopPane.add(this.borde).setLocation(770, 170);
        this.desktopPane.add(this.checkBox).setBounds(780, 500, 200, 20);
        
        this.desktopPane.add(this.tablaTransacciones);

        this.add(this.desktopPane);
        repaint();
    }
}