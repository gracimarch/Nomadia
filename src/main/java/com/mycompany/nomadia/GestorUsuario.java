package com.mycompany.nomadia;

import java.sql.*;

public class GestorUsuario {
    private Connection conn;

    public GestorUsuario() {
        this.conn = conn;
    }
    
    public void agregarUsuario(Usuario usuario) {
        String sql = "INSERT INTO Usuarios(nombre, email, telefono, tipo) VALUES(?, ?, ?, ?)";

        try (PreparedStatement sentencia = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            sentencia.setString(1, usuario.getNombre());
            sentencia.setString(2, usuario.getEmail());
            sentencia.setString(3, usuario.getTelefono());
            sentencia.setString(4, usuario.getTipo());

            int filas = sentencia.executeUpdate();

            if (filas > 0) {
                try (ResultSet ids = sentencia.getGeneratedKeys()) {
                    int idGenerado = ids.getInt(1);
                    System.out.println("Usuario agregado correctamente. ID: " + idGenerado);
                }
            } else {
                System.out.println("No se pudo agregar el usuario.");
            }
        } catch (SQLException e) {
            System.out.println("Error al agregar usuario: " + e.getMessage());
        }
    }

    public void eliminarUsuario(int id) {
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

    public void actualizarTipoUsuario(int id, String nuevoTipo) {
        String sql = "UPDATE Usuarios SET tipo = ?, descuento = ? WHERE id = ?";

        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setString(1, nuevoTipo);

            if ("InquilinoPremium".equals(nuevoTipo)) {
                sentencia.setDouble(2, 0.2);
            } else {
                sentencia.setNull(2, Types.DOUBLE);
            }

            sentencia.setInt(3, id);

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

    public void actualizarUsuario(int id, String dato, String nuevo) {
        String sql = "UPDATE Usuarios SET ? = ? WHERE id = ?";

        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setString(1, dato);
            sentencia.setString(2, nuevo);
            sentencia.setInt(3, id);

            int registrosModificados = sentencia.executeUpdate();

            if (registrosModificados == 1) {
                System.out.println("Dato actualizado correctamente");
            } else {
                System.out.println("No se encontró el usuario con el ID proporcionado");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar email: " + e.getMessage());
        }
    }

    public void mostrarUsuarioPorId(int id) {
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

    public void mostrarUsuariosPorTipo(String tipo) {
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
    
    public void mostrarUsuarios() {
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
            System.out.println("\n=========== Usuario " + rs.getInt("id") + " ===========");
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

    public boolean existeUsuario(int id) {
        String sql = "SELECT 1 FROM Usuarios WHERE id = ?";
        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setInt(1, id);
            try (ResultSet rs = sentencia.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar existencia del usuario: " + e.getMessage());
            return false;
        }
    }

    public boolean esTipoUsuario(int id, String tipo) {
        String sql = "SELECT 1 FROM Usuarios WHERE id = ? AND tipo = ?";
        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setInt(1, id);
            sentencia.setString(2, tipo);
            try (ResultSet rs = sentencia.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar tipo de usuario: " + e.getMessage());
            return false;
        }
    }

    public String obtenerTipoUsuario(int inquilinoId) {
        String sql = "SELECT tipo FROM Usuarios WHERE id = ?";
        String tipo = "";

        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setInt(1, inquilinoId);
            ResultSet rs = sentencia.executeQuery();

            if (rs.next()) {
                tipo = rs.getString("tipo");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener tipo: " + e.getMessage());
        }
        return tipo;
    }

}
