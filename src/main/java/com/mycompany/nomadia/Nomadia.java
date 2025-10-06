package com.mycompany.nomadia;

import java.sql.*;

public class Nomadia {

    public static void main(String[] args) {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:agenda.sqlite");
            System.out.println("Conectado a la base de datos");
            
            /*Se hace PRAGMA foreign_keys = ON para que funcione correctamente el ON DELETE CASCADE*/
            try (Statement st = conn.createStatement()) {
                st.execute("PRAGMA foreign_keys = ON");
            } catch (SQLException e){
                System.out.println("Error: " + e.getMessage());
            }
            
        } catch (SQLException e) {
            System.out.println("Error al conectarse a la base de datos: " + e.getMessage());
        }
    }
}
