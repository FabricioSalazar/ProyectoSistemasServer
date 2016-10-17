package com.ucr.proyecto.data;

import com.ucr.proyecto.domain.Empleado;
import com.ucr.proyecto.domain.Transaccion;
import com.ucr.proyecto.util.Constantes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ConexionSQL {

    private final Connection conexion;
    private Statement statement = null;
    private Empleado empActual;

    public ConexionSQL() {

        this.conexion = getConexion();

    }// constructor

    // @return retorna un objeto de tipo conecction con  el cual se realiza cualquier funcion sobre la DB
    private Connection getConexion() {
        try {
            Class.forName(Constantes.SQL_SERVER).newInstance();
            return (DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.USER_PASS));

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ConexionSQL.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

    //Acreditar la cuenta del empleado que pasa como parametro
    public String acreditar(Transaccion acreditar) {
//        Empleado empleadoOrigen=obtenerEmpleado(0);
//        Transaccion acreditar=new Transaccion(empleadoOrigen, monto, "acreditar", empleado,detalle);
        String respuesta;
        String funcion = "INSERT INTO TRANSACCION(CodEmpleado,numCuentaOrigen,funcion,cantidad,codEmpleadoDestino,numCuentaDestino,detalle) VALUES(";
        funcion += acreditar.getEmpleado().getCodEmpleado() + ",'" + acreditar.getEmpleado().getNumCuenta() + "','" + acreditar.getFuncion() + "'," + acreditar.getCantidad() + "," + acreditar.getEmpleadoDestino().getCodEmpleado() + ",'" + acreditar.getEmpleadoDestino().getNumCuenta() + "','" + acreditar.getDetalle() + "')";

        try {
            statement = conexion.createStatement();

            statement.executeUpdate(funcion);
            respuesta = "Transaccion realizada con exito";
        } catch (SQLException ex) {
            respuesta = "Error de conexion";
        }
        return respuesta;
    }

    //Debita la cuenta del empleado que pasa como parametro
    public String debitar(Transaccion debitar) {
//        Empleado empleadoDestino=obtenerEmpleado(0);
//        Transaccion debitar=new Transaccion(empleado, monto, "debitar", empleadoDestino,detalle);
        String respuesta;

        if (debitar.getEmpleado().getSaldo() >= debitar.getCantidad()) {
            String funcion = "INSERT INTO TRANSACCION(CodEmpleado,numCuentaOrigen,funcion,cantidad,codEmpleadoDestino,numCuentaDestino,detalle) VALUES(";

            funcion += debitar.getEmpleado().getCodEmpleado() + ",'" + debitar.getEmpleado().getNumCuenta() + "','" + debitar.getFuncion() + "'," + debitar.getCantidad() + "," + debitar.getEmpleadoDestino().getCodEmpleado() + ",'" + debitar.getEmpleadoDestino().getNumCuenta() + "','" + debitar.getDetalle() + "')";

            try {
                statement = conexion.createStatement();

                statement.executeUpdate(funcion);
                respuesta = "Transaccion realizada con exito";
            } catch (SQLException ex) {
                respuesta = "Error de conexion";
            }
        } else {
            respuesta = "El saldo en la cuenta es menor que la cantidad a retirar";
        }
        return respuesta;
    }

    //Genera el ahorro automatico
    public void ahorroAutomatico(int monto) {
        Empleado empleado = obtenerEmpleado(0);
        Transaccion ahorro = new Transaccion(empleado, monto, "ahorroautomatico", empleado, "Ahorro Automatico");

        String funcion = "INSERT INTO TRANSACCION(CodEmpleado,numCuentaOrigen,funcion,cantidad,codEmpleadoDestino,numCuentaDestino,detalle) VALUES(";

        funcion += ahorro.getEmpleado().getCodEmpleado() + ",'" + ahorro.getEmpleado().getNumCuenta() + "','" + ahorro.getFuncion() + "'," + ahorro.getCantidad() + "," + ahorro.getEmpleadoDestino().getCodEmpleado() + ",'" + ahorro.getEmpleadoDestino().getNumCuenta() + "','" + ahorro.getDetalle() + "')";

        try {
            statement = conexion.createStatement();

            statement.executeUpdate(funcion);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Debita el dinero de la cuenta del empleado de origen para acreditarlo al empleado de destino
    public String acreditarOtraCuenta(Transaccion acreditarOtraCuenta) {
        //Transaccion acreditarOtraCuenta=new Transaccion(empleadoOrigen, monto, "acreditarotracuenta", empleadoDestino,detalle);
        String respuesta;
        if (acreditarOtraCuenta.getEmpleado().getSaldo() >= acreditarOtraCuenta.getCantidad()) {
            String funcion = "INSERT INTO TRANSACCION(CodEmpleado,numCuentaOrigen,funcion,cantidad,codEmpleadoDestino,numCuentaDestino,detalle) VALUES(";
            funcion += acreditarOtraCuenta.getEmpleado().getCodEmpleado() + ",'" + acreditarOtraCuenta.getEmpleado().getNumCuenta() + "','" + acreditarOtraCuenta.getFuncion() + "'," + acreditarOtraCuenta.getCantidad() + "," + acreditarOtraCuenta.getEmpleadoDestino().getCodEmpleado() + ",'" + acreditarOtraCuenta.getEmpleadoDestino().getNumCuenta() + "','" + acreditarOtraCuenta.getDetalle() + "')";

            try {
                statement = conexion.createStatement();

                statement.executeUpdate(funcion);
                respuesta = "Transaccion realizada con exito";
            } catch (SQLException ex) {
                respuesta = "Error de conexion";
            }
        } else {
            respuesta = "El saldo en la cuenta es menor que la cantidad a traspasar";
        }
        return respuesta;
    }

    public Empleado obtener(Empleado estadoAntiguo) {
        Empleado empleado = obtenerEmpleado(estadoAntiguo.getCodEmpleado());

        if (empleado != null) {
            return empleado;
        } else {
            return null;
        }
    }

    //Obtiene la informacion de los empleados segun el codigo
    public Empleado obtenerEmpleado(int codigo) {
        String funcion = "SELECT * FROM EMPLEADOS WHERE codEmpleado=" + codigo;
        String funcion2 = "SELECT numCuenta,saldo FROM CUENTA WHERE codEmpleado=" + codigo;

        Empleado emp = null;
        String usuario = "", contraseña = "", nombre = "", numCuenta = "";
        float saldo = 0;
        int codEmpleado = 0;

        try {
            statement = conexion.createStatement();

            ResultSet rs = statement.executeQuery(funcion);

            Statement statement2 = null;
            statement2 = conexion.createStatement();
            ResultSet rs1 = statement2.executeQuery(funcion2);

            while (rs.next()) {
                codEmpleado = rs.getInt(1);
                nombre = rs.getString(2);
                usuario = rs.getString(3);
                contraseña = rs.getString(4);
            }

            while (rs1.next()) {
                numCuenta = rs1.getString(1);
                saldo = rs1.getFloat(2);
            }

            emp = new Empleado(usuario, contraseña, saldo, numCuenta, nombre, codEmpleado);

        } catch (SQLException ex) {
            Logger.getLogger(ConexionSQL.class.getName()).log(Level.SEVERE, null, ex);
        }

        return emp;
    }

    public Empleado obtenerConCuentaEmpleado(String numCuenta) {

        Empleado emp = null;
        String usuario = "", contraseña = "", nombre = "", numCuenta1 = "";
        float saldo = 0;
        int codEmpleado = 0;

        String funcion2 = "SELECT codEmpleado,numCuenta,saldo FROM CUENTA WHERE numCuenta='" + numCuenta + "'";

        try {
            statement = conexion.createStatement();

            ResultSet rs = statement.executeQuery(funcion2);

            while (rs.next()) {
                codEmpleado = rs.getInt(1);
                numCuenta1 = rs.getString(2);
                saldo = rs.getFloat(3);
            }

            String funcion = "SELECT nombre,usuario,contraseña FROM EMPLEADOS WHERE codEmpleado=" + codEmpleado;
            Statement statement2 = null;
            statement2 = conexion.createStatement();
            ResultSet rs1 = statement2.executeQuery(funcion);

            while (rs1.next()) {
                nombre = rs1.getString(1);
                usuario = rs1.getString(2);
                contraseña = rs1.getString(3);
            }
            emp = new Empleado(usuario, contraseña, saldo, numCuenta, nombre, codEmpleado);

        } catch (SQLException ex) {
            Logger.getLogger(ConexionSQL.class.getName()).log(Level.SEVERE, null, ex);
        }

        return emp;
    }

    public Empleado obtenerEmpleadoConElUsuario(String usuario) {

        Empleado emp = null;
        String usuario1 = "", contraseña = "", nombre = "", numCuenta = "";
        float saldo = 0;
        int codEmpleado = 0;

        String funcion = "SELECT * FROM EMPLEADOS WHERE usuario='" + usuario + "'";

        try {
            statement = conexion.createStatement();

            ResultSet rs = statement.executeQuery(funcion);

            while (rs.next()) {
                codEmpleado = rs.getInt(1);
                nombre = rs.getString(2);
                usuario = rs.getString(3);
                contraseña = rs.getString(4);
            }

            String funcion2 = "SELECT numCuenta,saldo FROM CUENTA WHERE codEmpleado=" + codEmpleado;
            Statement statement2 = null;
            statement2 = conexion.createStatement();
            ResultSet rs1 = statement2.executeQuery(funcion2);

            while (rs1.next()) {
                numCuenta = rs1.getString(1);
                saldo = rs1.getFloat(2);
            }

            emp = new Empleado(usuario, contraseña, saldo, numCuenta, nombre, codEmpleado);

        } catch (SQLException ex) {
            Logger.getLogger(ConexionSQL.class.getName()).log(Level.SEVERE, null, ex);
        }

        return emp;
    }

    public Empleado obtenerEmpleado(String user) {
        String funcion = "SELECT * FROM EMPLEADOS WHERE usuario='" + user + "'";
        Empleado emp = null;
        String usuario = "", contraseña = "", nombre = "", numCuenta = "";
        int codEmpleado = 0;
        float saldo = 0;
        try {
            statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery(funcion);
            while (rs.next()) {
                codEmpleado = rs.getInt(1);
                nombre = rs.getString(2);
                usuario = rs.getString(3);
                contraseña = rs.getString(4);
            }

            String funcion2 = "SELECT numCuenta,saldo FROM CUENTA WHERE codEmpleado=" + codEmpleado;
            Statement statement2 = conexion.createStatement();
            ResultSet rs1 = statement2.executeQuery(funcion2);

            while (rs1.next()) {
                numCuenta = rs1.getString(1);
                saldo = rs1.getFloat(2);
            }

            emp = new Empleado(usuario, contraseña, saldo, numCuenta, nombre, codEmpleado);

        } catch (SQLException ex) {
            Logger.getLogger(ConexionSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return emp;
    }

    /*Verifica si el usuario y contrasena del empleado que pasa como parametro coinciden
     @param recibe un empleadocon los datos ingresados porelusuario para iniciar secion 
    @return retorna si ese cliente existe en la base de datos y autoriza elinicio de sesion
     */
    public boolean verificarEmpleado(Empleado datos) {
        Empleado aux = obtenerEmpleado(datos.getUsuario());
        empActual = aux;
        if (aux != null) {
            return aux.getUsuario().equalsIgnoreCase(datos.getUsuario()) && aux.getContrasena().equals(datos.getContrasena());
        } else {
            return false;
        }

    }

    public Empleado getEmpleadoActual() {
        return empActual;
    }

}
