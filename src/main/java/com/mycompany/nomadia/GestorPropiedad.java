package com.mycompany.nomadia;

import java.sql.*;

public class GestorPropiedad {

    public void agregarCasa(Connection conn, Casa propiedad) {
        String sql = """
            INSERT INTO Propiedades (tipo, ubicacion, precioNoche, anfitrionId, maxPersonas, habitaciones, banios,
                                    parking, petFriendly, parrilla, patio)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setString(1, propiedad.getTipo());
            sentencia.setString(2, propiedad.getUbicacion());
            sentencia.setDouble(3, propiedad.getPrecioNoche());
            sentencia.setInt(4, propiedad.getAnfitrionId());
            sentencia.setInt(5, propiedad.getMaxPersonas());
            sentencia.setObject(6, propiedad.getHabitaciones());
            sentencia.setObject(7, propiedad.getBanios());
            sentencia.setBoolean(8, propiedad.isParking());
            sentencia.setBoolean(9, propiedad.isPetFriendly());
            sentencia.setBoolean(10, propiedad.isParrilla());
            sentencia.setBoolean(11, propiedad.isPatio());

            int filas = sentencia.executeUpdate();
            System.out.println(filas == 1 ? "Propiedad agregada correctamente" : "Error al agregar propiedad");
        } catch (SQLException e) {
            System.out.println("Error al insertar propiedad: " + e.getMessage());
        }
    }

    public void agregarDepartamento(Connection conn, Departamento propiedad) {
        String sql = """
            INSERT INTO Propiedades (tipo, ubicacion, precioNoche, anfitrionId, maxPersonas, habitaciones, banios,
                                    parking, petFriendly, piso, balcon, zonaComun)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
        
        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setString(1, propiedad.getTipo());
            sentencia.setString(2, propiedad.getUbicacion());
            sentencia.setDouble(3, propiedad.getPrecioNoche());
            sentencia.setInt(4, propiedad.getAnfitrionId());
            sentencia.setInt(5, propiedad.getMaxPersonas());
            sentencia.setObject(6, propiedad.getHabitaciones());
            sentencia.setObject(7, propiedad.getBanios());
            sentencia.setBoolean(8, propiedad.isParking());
            sentencia.setBoolean(9, propiedad.isPetFriendly());
            sentencia.setInt(10, propiedad.getPiso());
            sentencia.setBoolean(11, propiedad.isBalcon());
            sentencia.setBoolean(11, propiedad.isZonaComun());

            int filas = sentencia.executeUpdate();
            System.out.println(filas == 1 ? "Propiedad agregada correctamente" : "Error al agregar propiedad");
        } catch (SQLException e) {
            System.out.println("Error al insertar propiedad: " + e.getMessage());
        }
    }  

    public void agregarHotel(Connection conn, Hotel propiedad) {
        String sql = """
            INSERT INTO Propiedades (tipo, ubicacion, precioNoche, anfitrionId, maxPersonas, habitaciones, banios,
                                    parking, petFriendly, checkIn, checkOut, servicios)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setString(1, propiedad.getTipo());
            sentencia.setString(2, propiedad.getUbicacion());
            sentencia.setDouble(3, propiedad.getPrecioNoche());
            sentencia.setInt(4, propiedad.getAnfitrionId());
            sentencia.setInt(5, propiedad.getMaxPersonas());
            sentencia.setObject(6, propiedad.getHabitaciones());
            sentencia.setObject(7, propiedad.getBanios());
            sentencia.setBoolean(8, propiedad.isParking());
            sentencia.setBoolean(9, propiedad.isPetFriendly());
            sentencia.setString(10, propiedad.getCheckIn());
            sentencia.setString(11, propiedad.getCheckOut());

            int filas = sentencia.executeUpdate();
            System.out.println(filas == 1 ? "Propiedad agregada correctamente" : "Error al agregar propiedad");
        } catch (SQLException e) {
            System.out.println("Error al insertar propiedad: " + e.getMessage());
        }
    }

    public void mostrarPropiedades(Connection conn) {
        String sql = "SELECT * FROM Propiedades";

        try (Statement sentencia = conn.createStatement(); ResultSet rs = sentencia.executeQuery(sql)) {

            while (rs.next()) {
                imprimirDatos(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error al mostrar propiedades: " + e.getMessage());
        }
    }

    public void eliminarPropiedad(Connection conn, int id) {
        String sql = "DELETE FROM Propiedades WHERE id = ?";

        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setInt(1, id);
            int registrosModificados = sentencia.executeUpdate();

            if (registrosModificados == 1) {
                System.out.println("Propiedad eliminada correctamente");
            } else {
                System.out.println("No se encontró la propiedad con el ID proporcionado");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar propiedad: " + e.getMessage());
        }
    }

    public void actualizarPrecioNoche(Connection conn, int id, double nuevoPrecio) {
        String sql = "UPDATE Propiedades SET precioNoche = ? WHERE id = ?";

        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setDouble(1, nuevoPrecio);
            sentencia.setInt(2, id);

            int registrosModificados = sentencia.executeUpdate();

            if (registrosModificados == 1) {
                System.out.println("Precio por noche actualizado correctamente");
            } else {
                System.out.println("No se encontró la propiedad con el ID proporcionado");
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar precio por noche: " + e.getMessage());
        }
    }

    public void mostrarPropiedadPorId(Connection conn, int id) {
        String sql = "SELECT * FROM Propiedades WHERE id = ?";

        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setInt(1, id);
            ResultSet rs = sentencia.executeQuery();

            if (rs.next()) {
                imprimirDatos(rs);
            } else {
                System.out.println("No se encontró la propiedad con el ID proporcionado");
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar propiedad: " + e.getMessage());
        }
    }

    public void mostrarPropiedadesPorAnfitrion(Connection conn, int anfitrionId) {
        String sql = "SELECT * FROM Propiedades WHERE anfitrionId = ?";

        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setInt(1, anfitrionId);
            ResultSet rs = sentencia.executeQuery();

            boolean existe = false;
            while (rs.next()) {
                existe = true;
                imprimirDatos(rs);
            }
            if (!existe) {
                System.out.println("No se encontraron propiedades para el anfitrión con el ID proporcionado");
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar propiedades: " + e.getMessage());   
        }
    }

    public void mostrarPropiedadesPorTipo(Connection conn, String tipo) {
        String sql = "SELECT * FROM Propiedades WHERE tipo = ?";

        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setString(1, tipo);
            ResultSet rs = sentencia.executeQuery();

            boolean existe = false;
            while (rs.next()) {
                existe = true;
                imprimirDatos(rs);
            }
            if (!existe) {
                System.out.println("No se encontraron propiedades del tipo proporcionado");
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar propiedades: " + e.getMessage());   
        }
    }

    public void mostrarPropiedadesPorUbicacion(Connection conn, String ubicacion) {
        String sql = "SELECT * FROM Propiedades WHERE ubicacion LIKE ?";

        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setString(1, "%" + ubicacion + "%");
            ResultSet rs = sentencia.executeQuery();

            boolean existe = false;
            while (rs.next()) {
                existe = true;
                imprimirDatos(rs);
                }
            if (!existe) {
                System.out.println("No se encontraron propiedades en la ubicación proporcionada");
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar propiedades: " + e.getMessage());   
        }
    }

    public void mostrarPropiedadesPorPrecioMaximo(Connection conn, double precioMaximo) {
        String sql = "SELECT * FROM Propiedades WHERE precioNoche <= ?";

        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setDouble(1, precioMaximo);
            ResultSet rs = sentencia.executeQuery();

            boolean existe = false;
            while (rs.next()) {
                existe = true;
                imprimirDatos(rs);
                }
                if (!existe) {
                System.out.println("No se encontraron propiedades con un precio menor al proporcionado");
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar propiedades: " + e.getMessage());   
        }
    }

    public void mostrarPropiedadesPorCapacidad(Connection conn, int capacidad) {
        String sql = "SELECT * FROM Propiedades WHERE maxPersonas >= ?";

        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setInt(1, capacidad);
            ResultSet rs = sentencia.executeQuery();

            boolean existe = false;
            while (rs.next()) {
                existe = true;
                imprimirDatos(rs);
            }
            if (!existe) {
                System.out.println("No se encontraron propiedades con la capacidad mínima requerida");
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar propiedades: " + e.getMessage());   
        }
    }

    public void mostrarPropiedadesConParking(Connection conn) {
        String sql = "SELECT * FROM Propiedades WHERE parking = 1";

        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            ResultSet rs = sentencia.executeQuery();

            boolean existe = false;
            while (rs.next()) {
                existe = true;
                imprimirDatos(rs);
            }
            if (!existe) {
                System.out.println("No se encontraron propiedades con parking disponible");
                }
        } catch (SQLException e) {
            System.out.println("Error al buscar propiedades: " + e.getMessage());   
        }
    }

    public void mostrarPropiedadesPetFriendly(Connection conn) {
        String sql = "SELECT * FROM Propiedades WHERE petFriendly = 1";

        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            ResultSet rs = sentencia.executeQuery();

            boolean existe = false;
            while (rs.next()) {
                existe = true;
                imprimirDatos(rs);
            }
            if (!existe) {
                System.out.println("No se encontraron propiedades pet friendly");
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar propiedades: " + e.getMessage());   
        }
    }

    public void imprimirDatos (ResultSet rs) {
        try {
            System.out.println("========== Propiedad " + rs.getInt("id") + " ==========");
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
            System.out.println("=================================\n");
        } catch (SQLException e) {
            System.out.println("Error al imprimir datos de la propiedad: " + e.getMessage());
        }
    }

}
