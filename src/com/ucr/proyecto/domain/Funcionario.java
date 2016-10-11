/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ucr.proyecto.domain;

import com.ucr.proyecto.data.ConexionSQL;
import java.util.concurrent.Semaphore;

class Funcionario extends Thread {

    private final Semaphore SEMAFORO;
    private final ConexionSQL CONEXION;

    public Funcionario() {
        this.SEMAFORO = new Semaphore(1);
        this.CONEXION = new ConexionSQL();
    }

    @Override
    public void run() {
        
    }

    public void ejecutarTransaccion(Transaccion transaccion) {
        
    }

}
