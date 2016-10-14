package com.ucr.proyecto.data;

import com.ucr.proyecto.domain.Empleado;
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

    
    public void acreditar(int monto, Empleado empleado) {

    }

    public void debitar(int monto, Empleado emleado) {

    }

    public Empleado obtener(Empleado estadoAntiguo) {
        return null;
    }

    public Empleado verificarEmpleado(Empleado datos) {

        String funcion = "SELECT * FROM EMPLEADO WHERE";

        return null;
    }
    
//    public void insertarEncuesta(Encuesta encuesta) throws SQLException {
//
//        statement = conexion.createStatement();
//
//        String values = "INSERT INTO ENCUESTA VALUES(";
//
//        Iterator<Pregunta> itr = encuesta.preguntas.iterator();
//        final byte fin = (byte) (encuesta.preguntas.size() - 1);
//        byte cont = 0;
//        while (itr.hasNext()) {
//            if (fin == cont++) {
//                values += "'" + itr.next().getRespuesta() + "'";
//            } else {
//                values += "'" + itr.next().getRespuesta() + "'" + ",";
//            }
//        }
//        values += ")";
//        System.out.println(values);
//
//        statement.executeUpdate(values);
//    }// insertar encuesta
}
