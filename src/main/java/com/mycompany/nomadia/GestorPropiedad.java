package com.mycompany.nomadia;

import java.sql.*;

public class GestorPropiedad {
    private Connection conn;

    public GestorPropiedad(Connection conn) {
        this.conn = conn;
    }

    public void agregarCasa(Casa propiedad) {
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
            sentencia.setInt(6, propiedad.getHabitaciones());
            sentencia.setInt(7, propiedad.getBanios());
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

    public void agregarDepartamento(Departamento propiedad) {
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
            sentencia.setInt(6, propiedad.getHabitaciones());
            sentencia.setInt(7, propiedad.getBanios());
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

    public void agregarHotel(Hotel propiedad) {
        String sql = """
                INSERT INTO Propiedades (tipo, ubicacion, precioNoche, anfitrionId, maxPersonas, habitaciones, banios,
                                        parking, petFriendly, checkIn, checkOut, estrellas, piscina)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setString(1, propiedad.getTipo());
            sentencia.setString(2, propiedad.getUbicacion());
            sentencia.setDouble(3, propiedad.getPrecioNoche());
            sentencia.setInt(4, propiedad.getAnfitrionId());
            sentencia.setInt(5, propiedad.getMaxPersonas());
            sentencia.setInt(6, propiedad.getHabitaciones());
            sentencia.setInt(7, propiedad.getBanios());
            sentencia.setBoolean(8, propiedad.isParking());
            sentencia.setBoolean(9, propiedad.isPetFriendly());
            sentencia.setString(10, propiedad.getCheckIn());
            sentencia.setString(11, propiedad.getCheckOut());
            sentencia.setInt(12, propiedad.getEstrellas());
            sentencia.setBoolean(13, propiedad.isPiscina());

            int filas = sentencia.executeUpdate();
            System.out.println(filas == 1 ? "Propiedad agregada correctamente" : "Error al agregar propiedad");
        } catch (SQLException e) {
            System.out.println("Error al insertar propiedad: " + e.getMessage());
        }
    }

    public void eliminarPropiedad(int id) throws AnfitrionConReservasActivasException {
        String sql = "DELETE FROM Propiedades WHERE id = ?";

        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setInt(1, id);
            int registrosModificados = sentencia.executeUpdate();

            if (registrosModificados == 1) {
                System.out.println("Propiedad eliminada correctamente");
            }
        } catch (SQLException e) {
            if (e.getMessage().toLowerCase().contains("foreign key constraint failed")) {
                // 3. Lanza tu excepción de negocio específica
                throw new AnfitrionConReservasActivasException(
                    "No se puede eliminar la propiedad ID " + id + ". Tiene reservas activas asociadas."
                );
            }
            System.err.println("Error al eliminar propiedad: " + e.getMessage());
        }
    }

    public void actualizarPropiedad(int id, double nuevoPrecio) {
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

    public void actualizarPropiedad(int id, String dato, Boolean nuevo) {
        String sql = "UPDATE Propiedades SET ? = ? WHERE id = ?";

        try (PreparedStatement psUpdate = conn.prepareStatement(sql)) {
            psUpdate.setString(1, dato);
            psUpdate.setBoolean(2, nuevo);
            psUpdate.setInt(3, id);
            int filas = psUpdate.executeUpdate();
            if (filas == 1) {
                System.out.println("Dato actualizado correctamente");
            } else {
                System.out.println("No se pudo actualizar la propiedad.");
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar propiedad: " + e.getMessage());
        }
    }

    public void actualizarPropiedad(int id, int nuevo) {
        String sql = "UPDATE Propiedades SET estrellas = ? WHERE id = ?";

        try (PreparedStatement psUpdate = conn.prepareStatement(sql)) {
            psUpdate.setInt(1, nuevo);
            psUpdate.setInt(2, id);
            int filas = psUpdate.executeUpdate();
            if (filas == 1) {
                System.out.println("Cantidad de estrellas actualizado correctamente");
            } else {
                System.out.println("No se pudo actualizar la propiedad.");
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar propiedad: " + e.getMessage());
        }
    }

    public void actualizarPropiedad(int id, String dato, String nuevo) {
        String sql = "UPDATE Propiedades SET ? = ? WHERE id = ?";

        try (PreparedStatement psUpdate = conn.prepareStatement(sql)) {
            psUpdate.setString(1, dato);
            psUpdate.setString(2, nuevo);
            psUpdate.setInt(3, id);
            int filas = psUpdate.executeUpdate();
            if (filas == 1) {
                System.out.println("Dato actualizado correctamente");
            } else {
                System.out.println("No se pudo actualizar la propiedad.");
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar propiedad: " + e.getMessage());
        }
    }

    public void mostrarPropiedades() {
        String sql = "SELECT * FROM Propiedades";

        try (Statement sentencia = conn.createStatement(); ResultSet rs = sentencia.executeQuery(sql)) {

            while (rs.next()) {
                imprimirDatos(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error al mostrar propiedades: " + e.getMessage());
        }
    }

    public void mostrarPropiedadPorId(int id) {
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

    public void mostrarPropiedadesPorAnfitrion(int anfitrionId) {
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

    public void mostrarPropiedadesPorTipo(String tipo) {
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

    public void mostrarPropiedadesPorUbicacion(String ubicacion) {
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

    public void mostrarPropiedadesPorPrecioMaximo(double precioMaximo) {
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

    public void mostrarPropiedadesPorCapacidad(int capacidad) {
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

    public void mostrarPropiedadesPorBoolean(String dato) {
        String sql = "SELECT * FROM Propiedades WHERE " + dato + "= 1";

        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            ResultSet rs = sentencia.executeQuery();

            boolean existe = false;
            while (rs.next()) {
                existe = true;
                imprimirDatos(rs);
            }
            if (!existe) {
                System.out.println("No se encontraron propiedades con lo requerido");
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar propiedades: " + e.getMessage());
        }
    }

    public void imprimirDatos(ResultSet rs) {
        try {
            GestorResenia gestorResenia = new GestorResenia(conn);
            int idPropiedad = rs.getInt("id");
            
            System.out.println("\n========== Propiedad " + idPropiedad + " ==========");
            System.out.println("Tipo: " + rs.getString("tipo"));
            System.out.println("Ubicación: " + rs.getString("ubicacion"));
            System.out.println("Precio/Noche: $" + rs.getDouble("precioNoche"));
            System.out.println("Anfitrión ID: " + rs.getInt("anfitrionId"));
            System.out.println("Máx. Personas: " + rs.getInt("maxPersonas"));

            /* Campos opcionales */
            int habitaciones = rs.getInt("habitaciones");
            if (!rs.wasNull())
                System.out.println("Habitaciones: " + habitaciones);

            int banios = rs.getInt("banios");
            if (!rs.wasNull())
                System.out.println("Baños: " + banios);

            int piso = rs.getInt("piso");
            if (!rs.wasNull())
                System.out.println("Piso: " + piso);

            String checkIn = rs.getString("checkIn");
            if (checkIn != null)
                System.out.println("Check-In: " + checkIn);

            String checkOut = rs.getString("checkOut");
            if (checkOut != null)
                System.out.println("Check-Out: " + checkOut);

            int estrellas = rs.getInt("estrellas");
            if (!rs.wasNull())
                System.out.println("Estrellas: " + estrellas);

            if (rs.getBoolean("parking"))
                System.out.println("Parking disponible");
            if (rs.getBoolean("petFriendly"))
                System.out.println("Pet Friendly");
            if (rs.getBoolean("parrilla"))
                System.out.println("Parrilla");
            if (rs.getBoolean("patio"))
                System.out.println("Patio");
            if (rs.getBoolean("balcon"))
                System.out.println("Balcón");
            if (rs.getBoolean("zonaComun"))
                System.out.println("Zona Común");
            if (rs.getBoolean("piscina"))
                System.out.println("Piscina");

            System.out.println("Calificación promedio: " + gestorResenia.calcularPuntajePromedio(idPropiedad));
            System.out.println("=================================");
        } catch (SQLException e) {
            System.out.println("Error al imprimir datos de la propiedad: " + e.getMessage());
        }
    }

    public boolean existePropiedad(int id) {
        String sql = "SELECT 1 FROM Propiedades WHERE id = ?";
        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setInt(1, id);
            try (ResultSet rs = sentencia.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar existencia de la propiedad: " + e.getMessage());
            return false;
        }
    }

    public boolean esTipoPropiedad(int id, String tipo) {
        String sql = "SELECT 1 FROM Propiedades WHERE id = ? AND tipo = ?";
        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setInt(1, id);
            sentencia.setString(2, tipo);
            try (ResultSet rs = sentencia.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar tipo de propiedad: " + e.getMessage());
            return false;
        }
    }

    public double obtenerPrecioNoche(int propiedadId) {
        String sql = "SELECT precioNoche FROM Propiedades WHERE id = ?";
        double precio = 0.0;

        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setInt(1, propiedadId);
            ResultSet rs = sentencia.executeQuery();

            if (rs.next()) {
                precio = rs.getDouble("precioNoche");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener precio por noche: " + e.getMessage());
        }
        return precio;
    }

    public int obtenerMaxPersonas(int propiedadId) {
        String sql = "SELECT maxPersonas FROM Propiedades WHERE id = ?";
        int maxPersonas = 0;

        try (PreparedStatement sentencia = conn.prepareStatement(sql)) {
            sentencia.setInt(1, propiedadId);
            ResultSet rs = sentencia.executeQuery();

            if (rs.next()) {
                maxPersonas = rs.getInt("maxPersonas");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener capacidad máxima: " + e.getMessage());
        }
        return maxPersonas;
    }

}
