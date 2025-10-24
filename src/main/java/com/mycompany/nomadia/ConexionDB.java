package com.mycompany.nomadia;

import java.sql.*;

public class ConexionDB {
    public static Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:nomadia.db");
            System.out.println("Conectado a la base de datos");

            /* Activar claves foráneas */
            try (Statement st = conn.createStatement()) {
                st.execute("PRAGMA foreign_keys = ON");
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
            return conn;
            
        } catch (SQLException a) {
            System.out.println(a);
            System.out.println("No se pudo conectar a la base de datos.");
            return null;
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
            System.out.println("No se pudo conectar a la base de datos.");
            return null;
        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("No se pudo conectar a la base de datos.");
            return null;
        }
    }
}