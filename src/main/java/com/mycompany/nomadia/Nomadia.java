package com.mycompany.nomadia;

import java.sql.*;

public class Nomadia {

    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:nomadia.db");
            System.out.println("Conectado a la base de datos");

            /* Activar claves foráneas */
            try (Statement st = conn.createStatement()) {
                st.execute("PRAGMA foreign_keys = ON");
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }

            // Crear instancias de los gestores
            GestorUsuario gestorUsuario = new GestorUsuario();
            GestorPropiedad gestorPropiedad = new GestorPropiedad();
            GestorResenias gestorResenias = new GestorResenias();
            GestorReserva gestorReserva = new GestorReserva();

            // Agregar un usuario
            Usuario usuario = new Inquilino("Mr. Fernandez", "mrfernandez@gmail.com", "123456789");
            gestorUsuario.agregarUsuario(conn, usuario);

            // Agregar una propiedad
            Casa casa = new Casa(usuario.getId(), "Mr. Fernandez 123", 100.0, 4, 2, 4, true, true, true, true);
            gestorPropiedad.agregarCasa(conn, casa);

            // Agregar una reserva
            Reserva reserva = new Reserva(1, usuario.getId(), Date.valueOf("2025-10-15"), Date.valueOf("2025-10-20"), 500.0, 4, true);
            gestorReserva.agregarReserva(conn, reserva);

            // Agregar una reseña
            Resenia resenia = new Resenia(1, usuario.getId(), "Excelente propiedad, muy cómoda.", 5);
            gestorResenias.agregarResenia(conn, resenia);

            // Mostrar todos los datos
            System.out.println("\nUsuarios:");
            gestorUsuario.mostrarUsuarios(conn);

            System.out.println("\nPropiedades:");
            gestorPropiedad.mostrarPropiedades(conn);

            System.out.println("\nReservas:");
            gestorReserva.mostrarReservas(conn);

            System.out.println("\nReseñas:");
            gestorResenias.mostrarResenias(conn);

            // Eliminar los datos creados
            System.out.println("\nEliminando datos de prueba...");
            gestorResenias.eliminarResenia(conn, 1);
            gestorReserva.eliminarReserva(conn, 1);
            gestorPropiedad.eliminarPropiedad(conn, 1);
            gestorUsuario.eliminarUsuario(conn, usuario.getId());

            System.out.println("Datos de prueba eliminados.");

        } catch (SQLException e) {
            System.out.println("Error al conectarse a la base de datos: " + e.getMessage());
        }
    }
}