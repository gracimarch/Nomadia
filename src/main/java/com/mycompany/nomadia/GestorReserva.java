package com.mycompany.nomadia;

import java.sql.*;
import java.time.LocalDate;

public class GestorReserva {
    private Connection conn;

    public GestorReserva(Connection conn) {
        this.conn = conn;
    }

    public void agregarReserva(Reserva reserva) {
        String sql = "INSERT INTO Reservas (propiedadId, inquilinoId, fechaInicio, fechaFin, precioFinal, cantidadPersonas, pagado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setInt(1, reserva.getPropiedadId());
            sentencia.setInt(2, reserva.getInquilinoId());
            sentencia.setObject(3, reserva.getFechaInicio());
            sentencia.setObject(4, reserva.getFechaFin());
            sentencia.setDouble(5, reserva.getPrecioFinal());
            sentencia.setInt(6, reserva.getCantidadPersonas());
            sentencia.setBoolean(7, reserva.isPagado());
            sentencia.executeUpdate();
            System.out.println("Reserva agregada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al agregar reserva: " + e.getMessage());
        }
    }

    public void eliminarReserva(int id) {
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

    public void actualizarReserva(int id, LocalDate fechaInicio, LocalDate fechaFin, double precioFinal) {
        String sql = "UPDATE Reservas SET fechaInicio = ?, fechaFin = ?, precioFinal = ? WHERE id = ?";
        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setObject(1, fechaInicio);
            sentencia.setObject(2, fechaFin);
            sentencia.setDouble(3, precioFinal);
            sentencia.setInt(4, id);
            sentencia.executeUpdate();
            System.out.println("Reserva actualizada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar reserva: " + e.getMessage());
        }
    }

    public void actualizarReserva(int id, int cantidadPersonas) {
        String sql = "UPDATE Reservas SET cantidadPersonas = ? WHERE id = ?";
        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setInt(1, cantidadPersonas);
            sentencia.setInt(2, id);
            sentencia.executeUpdate();
            System.out.println("Reserva actualizada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar reserva: " + e.getMessage());
        }
    }

    public void marcarReservaComoPagada(int id) {
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

    public void mostrarReservas() {
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

    public void mostrarReservasPorId(int id) {
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

    public void mostrarReservasPorInquilino(int inquilinoId) {
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
            System.out.println("Fecha Inicio: " + rs.getObject("fechaInicio"));
            System.out.println("Fecha Fin: " + rs.getObject("fechaFin"));
            System.out.println("Precio Final: $" + rs.getDouble("precioFinal"));
            System.out.println("Cantidad de Personas: " + rs.getInt("cantidadPersonas"));
            System.out.println("Pagado: " + (rs.getBoolean("pagado") ? "Sí" : "No"));
            System.out.println("=================================\n");
        } catch (SQLException e) {
            System.out.println("Error al imprimir datos de la reserva: " + e.getMessage());
        }
    }

    public Reserva buscarReserva(int id) {
        String sql = "SELECT * FROM Reservas WHERE id = ?";
        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setInt(1, id);
            
            try (ResultSet rs = sentencia.executeQuery()) {
                if (rs.next()) {
                    int propiedadId = rs.getInt("propiedadId");
                    int inquilinoId = rs.getInt("inquilinoId");
                    LocalDate fechaInicio = rs.getObject("fechaInicio", LocalDate.class); 
                    LocalDate fechaFin = rs.getObject("fechaFin", LocalDate.class);
                    double precioFinal = rs.getDouble("precioFinal");
                    int cantidadPersonas = rs.getInt("cantidadPersonas");
                    boolean pagado = rs.getBoolean("pagado");

                    return new Reserva(propiedadId, inquilinoId, fechaInicio, fechaFin, precioFinal, cantidadPersonas, pagado);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar reserva por ID: " + e.getMessage());
        }
        return null; 
    }

    public int obtenerPropiedadId(int reservaId) {
        String sql = "SELECT propiedadId FROM Reservas WHERE id = ?";
        int propiedadId = 0;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, reservaId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    propiedadId = rs.getInt("propiedadId");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener datos de la reserva: " + e.getMessage());
        }
        return propiedadId;
    }

    public boolean existeReserva(int id) {
        String sql = "SELECT 1 FROM Reservas WHERE id = ?";
        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setInt(1, id);
            try (ResultSet rs = sentencia.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar existencia de la reserva: " + e.getMessage());
            return false;
        }
    }
}