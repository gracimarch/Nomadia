package com.mycompany.nomadia;

import java.sql.*;

public class GestorReserva {

    public void agregarReserva(Connection conn, Reserva reserva) {
        String sql = "INSERT INTO Reservas (id, propiedadId, inquilinoId, fechaInicio, fechaFin, precioFinal, cantidadPersonas, pagado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setInt(1, reserva.getId());
            sentencia.setInt(2, reserva.getPropiedadId());
            sentencia.setInt(3, reserva.getInquilinoId());
            sentencia.setDate(4, new java.sql.Date(reserva.getFechaInicio().getTime()));
            sentencia.setDate(5, new java.sql.Date(reserva.getFechaFin().getTime()));
            sentencia.setDouble(6, reserva.getPrecioFinal());
            sentencia.setInt(7, reserva.getCantidadPersonas());
            sentencia.setBoolean(8, reserva.isPagado());
            sentencia.executeUpdate();
            System.out.println("Reserva agregada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al agregar reserva: " + e.getMessage());
        }
    }

    public void actualizarReserva(Connection conn, Reserva reserva) {
        String sql = "UPDATE Reservas SET fechaInicio = ?, fechaFin = ?, precioFinal = ?, cantidadPersonas = ?, pagado = ? WHERE id = ?";
        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setDate(1, new java.sql.Date(reserva.getFechaInicio().getTime()));
            sentencia.setDate(2, new java.sql.Date(reserva.getFechaFin().getTime()));
            sentencia.setDouble(3, reserva.getPrecioFinal());
            sentencia.setInt(4, reserva.getCantidadPersonas());
            sentencia.setBoolean(5, reserva.isPagado());
            sentencia.setInt(6, reserva.getId());
            sentencia.executeUpdate();
            System.out.println("Reserva actualizada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar reserva: " + e.getMessage());
        }
    }

    public void eliminarReserva(Connection conn, int id) {
        String sql = "DELETE FROM Reservas WHERE id = ?";
        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setInt(1, id);
            int filasAfectadas = sentencia.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Reserva eliminada correctamente.");
            } else {
                System.out.println("No se encontró la reserva con el ID proporcionado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar reserva: " + e.getMessage());
        }
    }

    public void marcarReservaComoPagada(Connection conn, int id) {
        String sql = "UPDATE Reservas SET pagado = 1 WHERE id = ?";
        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setInt(1, id);
            int filasAfectadas = sentencia.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Reserva marcada como pagada.");
            } else {
                System.out.println("No se encontró la reserva con el ID proporcionado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al marcar reserva como pagada: " + e.getMessage());
        }
    }

    public void mostrarReservas(Connection conn) {
        String sql = "SELECT * FROM Reservas";
        try (Statement sentencia = conn.createStatement();
            ResultSet rs = sentencia.executeQuery(sql)) {
            while (rs.next()) {
                imprimirDatosReserva(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al mostrar reservas: " + e.getMessage());
        }
    }

    public void mostrarReservasPorId(Connection conn, int id) {
        String sql = "SELECT * FROM Reservas WHERE id = ?";
        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setInt(1, id);
            try (ResultSet rs = sentencia.executeQuery()) {
                while (rs.next()) {
                    imprimirDatosReserva(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al mostrar reserva por ID: " + e.getMessage());
        }
    }

    public void mostrarReservasPorInquilino(Connection conn, int inquilinoId) {
        String sql = "SELECT * FROM Reservas WHERE inquilinoId = ?";
        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setInt(1, inquilinoId);
            try (ResultSet rs = sentencia.executeQuery()) {
                while (rs.next()) {
                    imprimirDatosReserva(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al mostrar reservas por inquilino: " + e.getMessage());
        }
    }

    public void imprimirDatosReserva(ResultSet rs) {
    try {
        System.out.println("=========== Reserva " + rs.getInt("id") + " ===========");
        System.out.println("Propiedad ID: " + rs.getInt("propiedadId"));
        System.out.println("Inquilino ID: " + rs.getInt("inquilinoId"));
        System.out.println("Fecha Inicio: " + rs.getDate("fechaInicio"));
        System.out.println("Fecha Fin: " + rs.getDate("fechaFin"));
        System.out.println("Precio Final: $" + rs.getDouble("precioFinal"));
        System.out.println("Cantidad de Personas: " + rs.getInt("cantidadPersonas"));
        System.out.println("Pagado: " + (rs.getBoolean("pagado") ? "Sí" : "No"));
        System.out.println("=================================\n");
    } catch (SQLException e) {
        System.out.println("Error al imprimir datos de la reserva: " + e.getMessage());
    }
}
}