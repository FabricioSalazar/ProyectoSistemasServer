/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ucr.proyecto.domain;

import com.ucr.proyecto.data.ConexionSQL;
import com.ucr.proyecto.util.Constantes;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Machito
 */
public class AhorroAutomatico extends Thread {
    
    private ConexionSQL conexion;

    public AhorroAutomatico(ConexionSQL conexion) {
        this.conexion = conexion;
    }

    @Override
    public synchronized void run() {
        while (true) {            
            try {
                conexion.ahorroAutomatico(Constantes.MONTO_AHORRO);//hace la transaccion de ahorro automatico
                Thread.sleep(180000);
            } catch (InterruptedException ex) {
                Logger.getLogger(AhorroAutomatico.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
}
