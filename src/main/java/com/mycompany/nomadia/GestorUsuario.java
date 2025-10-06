/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nomadia;

import java.sql.*;

/**
 *
 * @author graci
 */
public class GestorUsuario {
    
    public void mostrarPropiedades(Connection conn) {
        String sql = "SELECT * FROM Propiedades";

        try (Statement sentencia = conn.createStatement(); ResultSet rs = sentencia.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("========== Propiedad ==========");
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Tipo: " + rs.getString("tipo"));
                System.out.println("Ubicación: " + rs.getString("ubicacion"));
                System.out.println("Precio/Noche: $" + rs.getDouble("precioNoche"));
                System.out.println("Anfitrión ID: " + rs.getInt("anfitrionId"));
                System.out.println("Máx. Personas: " + rs.getInt("maxPersonas"));

                // Campos opcionales
                int habitaciones = rs.getInt("habitaciones");
                if (!rs.wasNull()) System.out.println("Habitaciones: " + habitaciones);

                int banios = rs.getInt("banios");
                if (!rs.wasNull()) System.out.println("Baños: " + banios);

                int piso = rs.getInt("piso");
                if (!rs.wasNull()) System.out.println("Piso: " + piso);

                String checkIn = rs.getString("checkIn");
                if (checkIn != null) System.out.println("Check-In: " + checkIn);

                String checkOut = rs.getString("checkOut");
                if (checkOut != null) System.out.println("Check-Out: " + checkOut);

                String servicios = rs.getString("servicios");
                if (servicios != null) System.out.println("Servicios: " + servicios);

                if (rs.getBoolean("parking")) System.out.println("Parking disponible");
                if (rs.getBoolean("petFriendly")) System.out.println("Pet Friendly");
                if (rs.getBoolean("parrilla")) System.out.println("Parrilla");
                if (rs.getBoolean("patio")) System.out.println("Patio");
                if (rs.getBoolean("balcon")) System.out.println("Balcón");
                if (rs.getBoolean("zonaComun")) System.out.println("Zona Común");

                System.out.println("===============================\n");
            }

        } catch (SQLException e) {
            System.out.println("Error al mostrar propiedades: " + e.getMessage());
        }
    }


}
