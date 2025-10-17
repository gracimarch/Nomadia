package com.mycompany.nomadia;

import java.sql.*;

public class GestorUsuario {
    
    public void agregarUsuario(Connection conn, Usuario usuario) {
        String sql = "INSERT INTO Usuarios(id, nombre, email, telefono, tipo) VALUES(?, ?, ?, ?, ?)";

        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setInt(1, usuario.getId());
            sentencia.setString(2, usuario.getNombre());
            sentencia.setString(3, usuario.getEmail());
            sentencia.setString(4, usuario.getTelefono());
            sentencia.setString(5, usuario.getTipo());

            sentencia.executeUpdate();
            System.out.println("Usuario agregado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al agregar usuario: " + e.getMessage());
        }
    }

    public void eliminarUsuario(Connection conn, int id) {
        String sql = "DELETE FROM Usuarios WHERE id = ?";

        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setInt(1, id);
            int registrosModificados = sentencia.executeUpdate();

            if (registrosModificados == 1) {
                System.out.println("Usuario eliminado correctamente");
            } else {
                System.out.println("No se encontró el usuario con el ID proporcionado");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
        }
    }

    public void actualizarTipoUsuario(Connection conn, int id, String nuevoTipo) {
        String sql = "UPDATE Usuarios SET tipo = ? WHERE id = ?";

        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setString(1, nuevoTipo);
            sentencia.setInt(2, id);

            int registrosModificados = sentencia.executeUpdate();

            if (registrosModificados == 1) {
                System.out.println("Tipo de usuario actualizado correctamente");
            } else {
                System.out.println("No se encontró el usuario con el ID proporcionado");
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar tipo de usuario: " + e.getMessage());
        }
    }

    public void actualizarEmail(Connection conn, int id, String nuevoEmail) {
        String sql = "UPDATE Usuarios SET email = ? WHERE id = ?";

        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setString(1, nuevoEmail);
            sentencia.setInt(2, id);

            int registrosModificados = sentencia.executeUpdate();

            if (registrosModificados == 1) {
                System.out.println("Email actualizado correctamente");
            } else {
                System.out.println("No se encontró el usuario con el ID proporcionado");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar email: " + e.getMessage());
        }
    }

    public void actualizarTelefono(Connection conn, int id, String nuevoTelefono) {
        String sql = "UPDATE Usuarios SET telefono = ? WHERE id = ?";

        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setString(1, nuevoTelefono);
            sentencia.setInt(2, id);

            int registrosModificados = sentencia.executeUpdate();

            if (registrosModificados == 1) {
                System.out.println("Teléfono actualizado correctamente");
            } else {
                System.out.println("No se encontró el usuario con el ID proporcionado");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar teléfono: " + e.getMessage());
        }
    }

    public void mostrarUsuarioPorId(Connection conn, int id) {
        String sql = "SELECT * FROM Usuarios WHERE id = ?";

        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setInt(1, id);
            ResultSet rs = sentencia.executeQuery();

            if (rs.next()) {
                imprimirDatos(rs);
            } else {
                System.out.println("No se encontró el usuario con el ID proporcionado");
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar usuario: " + e.getMessage());
        }
    }

    public void mostrarUsuariosPorTipo(Connection conn, String tipo) {
        String sql = "SELECT * FROM Usuarios WHERE tipo = ?";

        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setString(1, tipo);
            ResultSet rs = sentencia.executeQuery();

            while (rs.next()) {
                imprimirDatos(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al mostrar usuarios por tipo: " + e.getMessage());
        }
    }
    
    public void mostrarUsuarios(Connection conn) {
        String sql = "SELECT * FROM Usuarios";

        try (Statement sentencia = conn.createStatement();
            ResultSet rs = sentencia.executeQuery(sql)) {

            while (rs.next()) {
                imprimirDatos(rs);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al mostrar usuarios: " + e.getMessage());
        }
    }

    public void imprimirDatos(ResultSet rs) {
        try {
            System.out.println("=========== Usuario " + rs.getInt("id") + " ===========");
            System.out.println("Nombre: " + rs.getString("nombre"));
            System.out.println("Email: " + rs.getString("email"));
            System.out.println("Teléfono: " + rs.getString("telefono"));
            System.out.println("Tipo: " + rs.getString("tipo"));

            if ("InquilinoPremium".equals(rs.getString("tipo"))) {
                System.out.println("Descuento: " + rs.getDouble("descuento"));
            }

            System.out.println("=================================");
        } catch (SQLException e) {
            System.out.println("Error al imprimir datos del usuario: " + e.getMessage());
        }
    }

}
