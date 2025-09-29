package com.mycompany.nomadia;

import java.sql.Connection;     /*Representa la conexión a la base de datos*/
import java.sql.DriverManager;  /*Gestiona los drivers JDBC y abre la conexión*/
import java.sql.Statement;      /*Permite ejecutar sentencias SQL (CREATE, INSERT, UPDATE, DELETE)*/
import java.sql.ResultSet;      /*Contiene los resultados de una consulta SELECT*/
import java.sql.SQLException;

public class Nomadia {

    public static Connection connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:nomadia.db";
            conn = DriverManager.getConnection(url);
            System.out.println("Conectado con exito");
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos" + e.getMessage());
        }
        return conn;
    }

    public static void main(String[] args) {
        connect();
    }
}
