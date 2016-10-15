/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ucr.proyecto.domain;

import com.ucr.proyecto.data.ConexionSQL;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends Thread {

    private ServerSocket serverSocket;
    private Socket socket;
    private final int PUERTO;
    private Transaccion transaccion;
    private Semaphore funcionarios;
    
    private ConexionSQL conexion;
    
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;

    public Server(int puerto) {
        super("Server");
        this.PUERTO = puerto;
        this.funcionarios = new Semaphore(3);
        conexion = new ConexionSQL();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(this.PUERTO);
            System.out.println("Iniciado");
            do {
                socket = serverSocket.accept();
                entrada = new ObjectInputStream(socket.getInputStream());
                salida = new ObjectOutputStream(socket.getOutputStream());
                
                salida.writeObject("Conectado"); //Comunica al cliente que la conexion y los flujos de datos se establecieron correctamente
                
                transaccion = (Transaccion) entrada.readObject();
                atiendeCliente(transaccion);
                
                System.out.println(transaccion.getEmpleado());
                System.out.println(transaccion.getFuncion());
                System.out.println(transaccion.getCantidad());
                System.out.println(transaccion.getEmpleadoDestino());
                
                entrada.close();
                salida.close();
            } while (true);

        } catch (IOException | ClassNotFoundException | InterruptedException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String atiendeCliente(Transaccion t) throws InterruptedException {
        /*AHORRO AUTOMATICO case "ahorroautomatico"*/
        funcionarios.acquire();
        String funcion = t.getFuncion();
        String resultado = "";
        switch (funcion) {
            case "debitar"://El cliente saca de su cuenta
                resultado = conexion.debitar(t);
            case "acreditar"://El cliente mete en su cuenta, SUPUESTO SEMANTICO: EN EFECTIVO O CHEQUES
                resultado = conexion.acreditar(t);
                break;
            case "acreditarotracuenta":
                resultado = conexion.acreditarOtraCuenta(t);
                break;
            default:
        }
        funcionarios.release();
        return resultado;
    }
}
