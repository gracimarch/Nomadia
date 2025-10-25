package com.mycompany.nomadia;

import java.sql.*;

public class GestorResenias {
    private Connection conn;

    public GestorResenias() {
        this.conn = conn;
    }

    public void agregarResenia(Resenia resenia) {
        String sql = "INSERT INTO Resenias (propiedadId, inquilinoId, comentario, puntaje) VALUES (?, ?, ?, ?)";
        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setInt(1, resenia.getPropiedadId());
            sentencia.setInt(2, resenia.getInquilinoId());
            sentencia.setString(3, resenia.getComentario());
            sentencia.setInt(4, resenia.getPuntaje());
            sentencia.executeUpdate();
            System.out.println("Reseña agregada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al agregar reseña: " + e.getMessage());
        }
    }

    public void actualizarResenia(Resenia resenia, int id) {
        String sql = "UPDATE Resenias SET comentario = ?, puntaje = ? WHERE id = ?";
        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setString(1, resenia.getComentario());
            sentencia.setInt(2, resenia.getPuntaje());
            sentencia.setInt(3, id);
            sentencia.executeUpdate();
            System.out.println("Reseña actualizada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar reseña: " + e.getMessage());
        }
    }

    public void eliminarResenia(int id) {
        String sql = "DELETE FROM Resenias WHERE id = ?";
        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setInt(1, id);
            sentencia.executeUpdate();
            System.out.println("Reseña eliminada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar reseña: " + e.getMessage());
        }
    }

    public void mostrarResenias() {
        String sql = "SELECT * FROM Resenias";
        try (Statement sentencia = conn.createStatement();
             ResultSet rs = sentencia.executeQuery(sql)) {
            while (rs.next()) {
                imprimirDatosResenia(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al mostrar reseñas: " + e.getMessage());
        }
    }

    public void mostrarReseniasPorPropiedad(int propiedadId) {
        String sql = "SELECT * FROM Resenias WHERE propiedadId = ?";
        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setInt(1, propiedadId);
            try (ResultSet rs = sentencia.executeQuery()) {
                while (rs.next()) {
                    imprimirDatosResenia(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al mostrar reseñas por propiedad: " + e.getMessage());
        }
    }

    public void mostrarReseniasPorInquilino(int inquilinoId) {
        String sql = "SELECT * FROM Resenias WHERE inquilinoId = ?";
        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setInt(1, inquilinoId);
            try (ResultSet rs = sentencia.executeQuery()) {
                while (rs.next()) {
                    imprimirDatosResenia(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al mostrar reseñas por inquilino: " + e.getMessage());
        }
    }

    public void imprimirDatosResenia(ResultSet rs) {
        try {
            System.out.println("=========== Reseña " + rs.getInt("id") + " ===========");
            System.out.println("Propiedad ID: " + rs.getInt("propiedadId"));
            System.out.println("Inquilino ID: " + rs.getInt("inquilinoId"));
            System.out.println("Comentario: " + rs.getString("comentario"));
            System.out.println("Puntaje: " + rs.getInt("puntaje"));
            System.out.println("=================================\n");
        } catch (SQLException e) {
            System.out.println("Error al imprimir datos de la reseña: " + e.getMessage());
        }
    }
    
    public boolean existeResenia(int id) {
        String sql = "SELECT 1 FROM Resenias WHERE id = ?";
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