/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ucr.proyecto.util;

import com.ucr.proyecto.domain.Empleado;
import com.ucr.proyecto.domain.Transaccion;
import java.util.ArrayList;
import java.util.List;

public class Constantes {

    public static String DB_USER = "sqlserver";
    public static String USER_PASS = "saucr.12";
    public static String DB_URL = "jdbc:sqlserver://163.178.107.130;databaseName=Proyecto_II_Operativos_B32144_B56535_B44540_B56246";
    public static String SQL_SERVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static Empleado empleadoNulo = new Empleado("No suministrado", "null", 0, "00000000", "No suministrado", 0);
    
    public static final String ENVIAR_TRANSACCION = "ENVIAR";
    public static final String ENVIAR_TRANSACCION_ACREDITAR= "Enviar_Acreditar";
    public static final String ENVIAR_TRANSACCION_DEBITAR= "Enviar_Debitar";
    public static final String ENVIAR_TRANSACCION_ACREDITAR_OTRA_CUENTA= "Enviar_Acreditar_Otra_Cuenta";
    public static final String ACTUALIZACION_DE_ESTADOO = "NUEVO_ESTADO";
    public static final String VERIFICACION_DE_DATOS = "VERIFICAR_DATOS";
    public static final String VERIFICACION_DE_DATOS_CONSOLA = "VERIFICAR_DATOS_CONSOLA";
    public static final String WAIT_CLIENT = "EN_ESPERA";

    public static List<Transaccion> listaTransacciones = new ArrayList<>();
    public static int MONTO_AHORRO=1000;    
    
    public static final String ACTUALIZAR_LISTA = "ACTUALIZAR_LISTA";
}
