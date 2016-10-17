package com.ucr.proyecto.main;

import com.ucr.proyecto.domain.Server;
import com.ucr.proyecto.gui.VentanaPrincipal;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Server(5700).start();
        new VentanaPrincipal();
    }
}
