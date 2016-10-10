/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ucr.proyecto.domain;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends Thread {

    private ServerSocket serverSocket;
    private Socket socket;
    private final int PUERTO;
    private Transaccion transaccion;
    private Funcionario funcionarios;
    
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;

    public Server(int puerto) {
        super("Server");
        this.PUERTO = puerto;

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
                
                System.out.println(transaccion.getEmpleado());
                System.out.println(transaccion.getFuncion());
                System.out.println(transaccion.getCantidad());
                entrada.close();
                salida.close();
            } while (true);

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
