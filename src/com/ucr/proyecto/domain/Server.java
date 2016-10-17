/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ucr.proyecto.domain;

import com.ucr.proyecto.data.ConexionSQL;
import com.ucr.proyecto.gui.TablaTransacciones;
import com.ucr.proyecto.util.Constantes;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends Thread {

    private ServerSocket serverSocket;
    private Socket socket;
    private final int PUERTO;
    private Transaccion transaccion;
    private Semaphore funcionarios;
    private String funcion_Cliente;
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
            Constantes.listaTransacciones = conexion.getTransacciones();
            do {
                socket = serverSocket.accept();
                entrada = new ObjectInputStream(socket.getInputStream());
                salida = new ObjectOutputStream(socket.getOutputStream());

                funcion_Cliente = entrada.readUTF();  // 1] recibe la funcion que el usuario le comunico
                switch (funcion_Cliente) {
                    case Constantes.WAIT_CLIENT:
                        break;
                    case Constantes.VERIFICACION_DE_DATOS:
                        transaccion = (Transaccion) entrada.readObject(); // 2] recibe los datos del usuario                      
                        salida.writeObject(conexion.verificarEmpleado(transaccion.getEmpleado()));
                        salida.writeObject(conexion.getEmpleadoActual());
                        salida.writeObject(getEmpleados());
                        break;
                    case Constantes.VERIFICACION_DE_DATOS_CONSOLA:
                        transaccion = (Transaccion) entrada.readObject(); // 2] recibe los datos del usuario                      
                        salida.writeObject(conexion.verificarEmpleado(transaccion.getEmpleado()));
                        salida.writeObject(conexion.getEmpleadoActual());
                        salida.writeObject(getEmpleados());
                        break;
                    case Constantes.ENVIAR_TRANSACCION_ACREDITAR://recibe la transaccion y envia el string si la transaccion se pudo realizar
                        transaccion = (Transaccion) entrada.readObject(); //recibe la transaccion del usuario                      
                        salida.writeObject(atiendeCliente(transaccion));//usa el atiendeCliente con la transaccion que recibe y retorna si se pudo o no realizar la transacion
                        agregarTransaccion(transaccion);
                        break;
                     case Constantes.ENVIAR_TRANSACCION_DEBITAR://recibe la transaccion y envia el string si la transaccion se pudo realizar
                        transaccion = (Transaccion) entrada.readObject(); //recibe la transaccion del usuario                        
                        salida.writeObject(atiendeCliente(transaccion));//usa el atiendeCliente con la transaccion que recibe y retorna si se pudo o no realizar la transacion
                        agregarTransaccion(transaccion);
                        break;
                     case Constantes.ENVIAR_TRANSACCION_ACREDITAR_OTRA_CUENTA://recibe la transaccion y envia el string si la transaccion se pudo realizar
                        transaccion = (Transaccion) entrada.readObject(); //recibe la transaccion del usuario                     
                        salida.writeObject(atiendeCliente(transaccion));//usa el atiendeCliente con la transaccion que recibe y retorna si se pudo o no realizar la transacion
                        agregarTransaccion(transaccion);
                        break;
                }
                entrada.close();
                salida.close();
            } while (true);

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ArrayList<Empleado> getEmpleados() {
        ArrayList<Empleado> aux = new ArrayList();
        for (int i = 1; i < 21; i++) {
            aux.add(conexion.obtenerEmpleado(i));
        }
        return aux;
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
    
    public void agregarTransaccion(Transaccion transaccion){
        System.out.println(transaccion.toString());
        Constantes.listaTransacciones.add(transaccion);
    }
}
