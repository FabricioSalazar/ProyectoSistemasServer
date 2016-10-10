/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ucr.proyecto.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class Empleado implements Serializable {

    private String usuario;
    private String contrasena;
    private double saldo;
    private String numCuenta;
    private String nombre;
    private ArrayList movimientos;

    public Empleado(String usuario, String contrasena, double saldo, String numCuenta, String nombre) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.saldo = saldo;
        this.numCuenta = numCuenta;
        this.nombre = nombre;
    }

    // @param Parametro extra: Movimientos de cuenta
    public Empleado(String usuario, String contrasena, int saldo, String numCuenta, String nombre, ArrayList movimientos) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.saldo = saldo;
        this.numCuenta = numCuenta;
        this.nombre = nombre;
        this.movimientos = movimientos;
    }
    
    //****getter & setter
    
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(String numCuenta) {
        this.numCuenta = numCuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(ArrayList movimientos) {
        this.movimientos = movimientos;
    }

    @Override
    public String toString() {
        return "Empleado{" + "usuario=" + usuario + ", contrasena=" + contrasena + ", saldo=" + saldo + ", numCuenta=" + numCuenta + ", nombre=" + nombre + '}';
    }
    
    
    
}
