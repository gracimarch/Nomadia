package com.mycompany.nomadia;

import java.sql.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Nomadia {

    private static int leerInt(Scanner sc, String texto) {
        while (true) {
            System.out.print(texto);
            String line = sc.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número.");
            }
        }
    }

    private static double leerDouble(Scanner sc, String texto) {
        while (true) {
            System.out.print(texto);
            String line = sc.nextLine();
            try {
                return Double.parseDouble(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número (decimal permitido).");
            }
        }
    }

    private static boolean leerBoolean(Scanner sc, String texto) {
        while (true) {
            System.out.print(texto + " (s/n): ");
            String line = sc.nextLine().trim().toLowerCase();
            if (line.equals("s") || line.equals("si"))
                return true;
            if (line.equals("n") || line.equals("no"))
                return false;
            System.out.println("Entrada inválida. Responda 's' o 'n'.");
        }
    }

    private static LocalDate leerFecha(Scanner read, String prompt) {
        LocalDate fecha = null;
        while (fecha == null) {
            System.out.print(prompt);
            String line = read.nextLine().trim();
            try {
                fecha = LocalDate.parse(line);
            } catch (Exception e) {
                System.out.println("Formato inválido. Use YYYY-MM-DD.");
            }
        }
        return fecha;
    }

    public static void main(String[] args) {
        Connection conn = ConexionDB.getConnection();

        if (conn == null) {
            System.out.println("No se pudo conectar a la base de datos. Saliendo.");
            return;
        }

        GestorUsuario gestorUsuario = new GestorUsuario(conn);
        GestorPropiedad gestorPropiedad = new GestorPropiedad(conn);
        GestorResenia gestorResenia = new GestorResenia(conn);
        GestorReserva gestorReserva = new GestorReserva(conn);

        Scanner read = new Scanner(System.in);
        int opc1 = 0;
        int opc2 = 0;
        int opc3 = 0;
        int opc4 = 0;

        do {
            System.out.println("\n===== Menú Nomadia =====");
            System.out.println("1. Gestionar Usuarios");
            System.out.println("2. Gestionar Propiedades");
            System.out.println("3. Gestionar Reservas");
            System.out.println("4. Gestionar Reseñas");
            System.out.println("5. Salir");
            opc1 = leerInt(read, "Seleccione una opción: ");

            switch (opc1) {

                case 1:
                    do {
                        System.out.println("\n¿Qué desea hacer?");
                        System.out.println("1. Agregar Usuario");
                        System.out.println("2. Actualizar Usuario");
                        System.out.println("3. Eliminar Usuario");
                        System.out.println("4. Mostrar Usuarios");
                        System.out.println("5. Volver");
                        opc2 = leerInt(read, "Seleccione una opción: ");

                        switch (opc2) {
                            case 1:
                                do {
                                    System.out.println("\n¿Qué desea agregar?");
                                    System.out.println("1. Agregar Inquilino");
                                    System.out.println("2. Agregar Anfitrión");
                                    System.out.println("3. Volver");
                                    opc3 = leerInt(read, "Seleccione una opción: ");

                                    switch (opc3) {
                                        case 1:
                                            Usuario.crearUsuario(read, gestorUsuario, "Inquilino");
                                            break;

                                        case 2:
                                            Usuario.crearUsuario(read, gestorUsuario, "Anfitrion");
                                            break;

                                        case 3:
                                            System.out.println("\nVolviendo a gestionar usuarios...");
                                            break;

                                        default:
                                            System.out.println("\nOpción no válida. Intente de nuevo.");
                                    }
                                } while (opc3 != 3);
                                break;

                            case 2:
                                int id = leerInt(read, "\nIngrese el ID del usuario que desea modificar: ");

                                if (!gestorUsuario.existeUsuario(id)) {
                                    System.out.println("No se encontró el usuario con el ID proporcionado. Volviendo al menú de usuarios.");
                                    break;
                                }

                                System.out.println("\nDatos actuales del usuario:");
                                gestorUsuario.mostrarUsuarioPorId(id);

                                String tipoUsuario = gestorUsuario.obtenerTipoUsuario(id);

                                do {
                                    String opc3TextoEspecial = "3. (Sin acciones de tipo disponibles)";
                                    String accionEspecial = "ninguna";

                                    if ("Inquilino".equals(tipoUsuario)) {
                                        opc3TextoEspecial = "3. Ascender Inquilino a Inquilino Premium";
                                        accionEspecial = "ascender";
                                    } else if ("InquilinoPremium".equals(tipoUsuario)) {
                                        opc3TextoEspecial = "3. Descender Inquilino Premium a Inquilino";
                                        accionEspecial = "descender";
                                    }

                                    System.out.println("\n¿Qué desea actualizar ?");
                                    System.out.println("1. Actualizar email");
                                    System.out.println("2. Actualizar teléfono");
                                    System.out.println(opc3TextoEspecial);
                                    System.out.println("4. Volver");
                                    
                                    opc3 = leerInt(read, "Seleccione una opción: ");

                                    switch (opc3) {
                                        case 1:
                                            Usuario.actualizarUsuario(read, gestorUsuario, "email");
                                            break;

                                        case 2:
                                            Usuario.actualizarUsuario(read, gestorUsuario, "telefono");
                                            break;

                                        case 3:
                                            if ("ascender".equals(accionEspecial)) {
                                                gestorUsuario.actualizarTipoUsuario(id, "InquilinoPremium");
                                                tipoUsuario = "InquilinoPremium";
                                                System.out.println("\nUsuario ascendido a Premium.");
                                                
                                            } else if ("descender".equals(accionEspecial)) {
                                                gestorUsuario.actualizarTipoUsuario(id, "Inquilino");
                                                tipoUsuario = "Inquilino";
                                                System.out.println("\nUsuario descendido a Inquilino.");
                                                
                                            } else {
                                                System.out.println("\nOpción no válida para este tipo de usuario.");
                                            }
                                            break;

                                        case 4:
                                            System.out.println("\nVolviendo a gestionar usuarios...");
                                            break;

                                        default:
                                            System.out.println("\nOpción no válida. Intente de nuevo.");
                                    }

                                } while (opc3 != 4);
                                break;

                            case 3:
                                int idEliminar = leerInt(read, "\nIngrese el ID del usuario que desea eliminar: ");
                                gestorUsuario.eliminarUsuario(idEliminar);
                                break;

                            case 4:
                                do {
                                    System.out.println("\n¿Qué desea mostrar?");
                                    System.out.println("1. Mostrar Usuarios");
                                    System.out.println("2. Mostrar Inquilinos");
                                    System.out.println("3. Mostrar Anfitriones");
                                    System.out.println("4. Mostrar por ID");
                                    System.out.println("5. Volver");
                                    opc3 = leerInt(read, "Seleccione una opción: ");

                                    switch (opc3) {
                                        case 1:
                                            gestorUsuario.mostrarUsuarios();
                                            break;

                                        case 2:
                                            gestorUsuario.mostrarUsuariosPorTipo("Inquilino");
                                            break;

                                        case 3:
                                            gestorUsuario.mostrarUsuariosPorTipo("Anfitrion");
                                            break;

                                        case 4:
                                            int idMostrar = leerInt(read, "\nIngrese el ID del usuario que desea mostrar: ");
                                            gestorUsuario.mostrarUsuarioPorId(idMostrar);
                                            break;

                                        case 5:
                                            System.out.println("\nVolviendo a gestionar usuarios...");
                                            break;

                                        default:
                                            System.out.println("\nOpción no válida. Intente de nuevo.");
                                    }
                                } while (opc3 != 5);
                                break;

                            case 5:
                                System.out.println("\nVolviendo a menú principal...");
                                break;

                            default:
                                System.out.println("\nOpción no válida. Intente de nuevo.");
                        }
                    } while (opc2 != 5);

                    break;
                case 2:
                    do {
                        System.out.println("\n¿Qué desea hacer?");
                        System.out.println("1. Agregar Propiedad");
                        System.out.println("2. Actualizar Propiedad");
                        System.out.println("3. Eliminar Propiedad");
                        System.out.println("4. Mostrar Propiedad");
                        System.out.println("5. Volver");
                        opc2 = leerInt(read, "Seleccione una opción: ");

                        switch (opc2) {
                            case 1:
                                do {
                                    System.out.println("\n¿Qué tipo de propiedad desea agregar?");
                                    System.out.println("1. Casa");
                                    System.out.println("2. Departamento");
                                    System.out.println("3. Hotel");
                                    System.out.println("4. Volver");
                                    opc3 = leerInt(read, "Seleccione una opción: ");

                                    switch (opc3) {
                                        case 1: {
                                            System.out.println("\n== Agregar Casa ==");
                                            int anfitrionId;
                                            boolean esAnfitrion;
                                            do {
                                                anfitrionId = leerInt(read, "Anfitrión ID: ");
                                                esAnfitrion = gestorUsuario.esTipoUsuario(anfitrionId,
                                                        "Anfitrion");
                                                if (!esAnfitrion)
                                                    System.out.println("El ID no es de un anfitrión");
                                            } while (!esAnfitrion);
                                            System.out.print("Ubicación: ");
                                            String ubicacion = read.nextLine();
                                            double precio = leerDouble(read, "Precio por noche: ");
                                            int habitaciones = leerInt(read, "Habitaciones (número): ");
                                            int banios = leerInt(read, "Baños (número): ");
                                            int maxPersonas = leerInt(read, "Máx. personas: ");
                                            boolean parking = leerBoolean(read, "Parking disponible?");
                                            boolean petFriendly = leerBoolean(read, "Pet friendly?");
                                            boolean parrilla = leerBoolean(read, "Posee parrilla?");
                                            boolean patio = leerBoolean(read, "Posee patio?");

                                            Casa casa = new Casa(anfitrionId, ubicacion, precio, habitaciones, banios,
                                                    maxPersonas, parking, petFriendly, parrilla, patio);
                                            gestorPropiedad.agregarCasa(casa);
                                            break;
                                        }
                                        case 2: {
                                            System.out.println("\n== Agregar Departamento ==");
                                            int anfitrionId;
                                            boolean esAnfitrion;
                                            do {
                                                anfitrionId = leerInt(read, "Anfitrión ID: ");
                                                esAnfitrion = gestorUsuario.esTipoUsuario(anfitrionId,
                                                        "Anfitrion");
                                                if (!esAnfitrion)
                                                    System.out.println("El ID no es de un anfitrión");
                                            } while (!esAnfitrion);
                                            System.out.print("Ubicación: ");
                                            String ubicacion = read.nextLine();
                                            double precio = leerDouble(read, "Precio por noche: ");
                                            int habitaciones = leerInt(read, "Habitaciones (número): ");
                                            int banios = leerInt(read, "Baños (número): ");
                                            int maxPersonas = leerInt(read, "Máx. personas: ");
                                            boolean parking = leerBoolean(read, "Parking disponible?");
                                            boolean petFriendly = leerBoolean(read, "Pet friendly?");
                                            int piso = leerInt(read, "Piso: ");
                                            boolean balcon = leerBoolean(read, "Posee balcón?");
                                            boolean zonaComun = leerBoolean(read, "Posee zona común?");

                                            Departamento depto = new Departamento(anfitrionId, "Departamento",
                                                    ubicacion, precio, habitaciones, banios, maxPersonas, parking,
                                                    petFriendly, piso, balcon, zonaComun);
                                            gestorPropiedad.agregarDepartamento(depto);
                                            break;
                                        }
                                        case 3: {
                                            System.out.println("\n== Agregar Hotel ==");
                                            int anfitrionId;
                                            boolean esAnfitrion;
                                            do {
                                                anfitrionId = leerInt(read, "Anfitrión ID: ");
                                                esAnfitrion = gestorUsuario.esTipoUsuario(anfitrionId,
                                                        "Anfitrion");
                                                if (!esAnfitrion)
                                                    System.out.println("El ID no es de un anfitrión");
                                            } while (!esAnfitrion);
                                            System.out.print("Ubicación: ");
                                            String ubicacion = read.nextLine();
                                            double precio = leerDouble(read, "Precio por noche: ");
                                            int habitaciones = leerInt(read, "Habitaciones (número): ");
                                            int banios = leerInt(read, "Baños (número): ");
                                            int maxPersonas = leerInt(read, "Máx. personas: ");
                                            boolean parking = leerBoolean(read, "Parking disponible?");
                                            boolean petFriendly = leerBoolean(read, "Pet friendly?");
                                            System.out.print("Check-In (texto): ");
                                            String checkIn = read.nextLine();
                                            System.out.print("Check-Out (texto): ");
                                            String checkOut = read.nextLine();
                                            java.util.ArrayList<String> servicios = new java.util.ArrayList<>();

                                            Hotel hotel = new Hotel(anfitrionId, "Hotel", ubicacion, precio,
                                                    habitaciones, banios, maxPersonas, parking, petFriendly, checkIn,
                                                    checkOut, servicios);
                                            gestorPropiedad.agregarHotel(hotel);
                                            break;
                                        }
                                        case 4:
                                            System.out.println("\nVolviendo a gestionar propiedades...");
                                            break;
                                        default:
                                            System.out.println("Opción no válida. Intente de nuevo.");
                                    }
                                } while (opc3 != 4);
                                break;

                            case 2:
                                int idActualizar = leerInt(read,
                                        "\nIngrese el ID de la propiedad que desea modificar: ");
                                gestorPropiedad.mostrarPropiedadPorId(idActualizar);
                                do {
                                    System.out.println("\n¿Qué desea actualizar?");
                                    System.out.println("1. Precio por noche");
                                    System.out.println("2. Parking");
                                    System.out.println("3. PetFriendly");
                                    System.out.println("4. Parrilla");
                                    System.out.println("5. Patio");
                                    System.out.println("6. Balcón");
                                    System.out.println("7. Zona común");
                                    System.out.println("8. Volver");
                                    opc3 = leerInt(read, "Seleccione una opción: ");

                                    switch (opc3) {
                                        case 1:
                                            double nuevoPrecio = leerDouble(read, "Ingrese nuevo precio por noche: ");
                                            gestorPropiedad.actualizarPropiedad(idActualizar, nuevoPrecio);
                                            break;
                                        case 2:
                                            gestorPropiedad.actualizarPropiedad(idActualizar, "parking");
                                            break;
                                        case 3:
                                            gestorPropiedad.actualizarPropiedad(idActualizar, "petFriendly");
                                            break;
                                        case 4:
                                            gestorPropiedad.actualizarPropiedad(idActualizar, "parrilla");
                                            break;
                                        case 5:
                                            gestorPropiedad.actualizarPropiedad(idActualizar, "patio");
                                            break;
                                        case 6:
                                            gestorPropiedad.actualizarPropiedad(idActualizar, "balcon");
                                            break;
                                        case 7:
                                            gestorPropiedad.actualizarPropiedad(idActualizar, "zonaComun");
                                            break;
                                        case 8:
                                            System.out.println("\nVolviendo a gestionar propiedades...");
                                            break;
                                        default:
                                            System.out.println("Opción no válida. Intente de nuevo.");
                                    }
                                } while (opc3 != 8);
                                break;

                            case 3:
                                int idEliminar = leerInt(read, "\nIngrese el ID de la propiedad que desea eliminar: ");
                                gestorPropiedad.eliminarPropiedad(idEliminar);
                                break;

                            case 4:
                                do {
                                    System.out.println("\n¿Qué desea mostrar?");
                                    System.out.println("1. Mostrar Todas");
                                    System.out.println("2. Por anfitrión");
                                    System.out.println("3. Por tipo (Casa/Departamento/Hotel)");
                                    System.out.println("4. Por ID");
                                    System.out.println("5. Por ubicación");
                                    System.out.println("6. Por precio máximo");
                                    System.out.println("7. Por capacidad mínima");
                                    System.out.println("8. Por un adicional");
                                    System.out.println("9. Volver");
                                    opc3 = leerInt(read, "Seleccione una opción: ");

                                    switch (opc3) {
                                        case 1:
                                            gestorPropiedad.mostrarPropiedades();
                                            break;
                                        case 2: {
                                            int anfitrionId = leerInt(read, "Ingrese anfitrión ID: ");
                                            gestorPropiedad.mostrarPropiedadesPorAnfitrion(anfitrionId);
                                            break;
                                        }
                                        case 3: {
                                            System.out.print("¿Qué tipo desea mostrar?");
                                            System.out.println("1. Casa");
                                            System.out.println("2. Departamento");
                                            System.out.println("3. Hotel");
                                            System.out.println("4. Volver");
                                            opc4 = leerInt(read, "Seleccione una opción: ");
                                            String tipo = null;

                                            switch (opc4) {
                                                case 1:
                                                    tipo = "Casa";
                                                    break;
                                                case 2:
                                                    tipo = "Departamento";
                                                    break;
                                                case 3:
                                                    tipo = "Hotel";
                                                    break;
                                                case 4:
                                                    System.out.println("\nVolviendo a mostrar propiedades...");
                                                    break;
                                                default:
                                                    System.out.println("Opción no válida");
                                                    break;
                                            }
                                            if (tipo != null)
                                                gestorPropiedad.mostrarPropiedadesPorTipo(tipo);
                                            break;
                                        }
                                        case 4: {
                                            int idMostrar = leerInt(read, "Ingrese ID de la propiedad: ");
                                            gestorPropiedad.mostrarPropiedadPorId(idMostrar);
                                            break;
                                        }
                                        case 5: {
                                            System.out.print("Ingrese ubicación (texto o parte): ");
                                            String ubic = read.nextLine();
                                            gestorPropiedad.mostrarPropiedadesPorUbicacion(ubic);
                                            break;
                                        }
                                        case 6: {
                                            double precioMax = leerDouble(read, "Ingrese precio máximo: ");
                                            gestorPropiedad.mostrarPropiedadesPorPrecioMaximo(precioMax);
                                            break;
                                        }
                                        case 7: {
                                            int capacidad = leerInt(read, "Ingrese capacidad mínima (personas): ");
                                            gestorPropiedad.mostrarPropiedadesPorCapacidad(capacidad);
                                            break;
                                        }
                                        case 8: {
                                            System.out.println("Seleccione el adicional por el que desea filtrar:");
                                            System.out.println("1. Parking");
                                            System.out.println("2. Apto Mascotas (petFriendly)");
                                            System.out.println("3. Parrilla");
                                            System.out.println("4. Patio");
                                            System.out.println("5. Balcón");
                                            System.out.println("6. Zona Común");
                                            System.out.println("7. Volver");

                                            opc4 = leerInt(read, "Seleccione una opción: ");
                                            String campo = null;

                                            switch (opc4) {
                                                case 1:
                                                    campo = "parking";
                                                    break;
                                                case 2:
                                                    campo = "petFriendly";
                                                    break;
                                                case 3:
                                                    campo = "parrilla";
                                                    break;
                                                case 4:
                                                    campo = "patio";
                                                    break;
                                                case 5:
                                                    campo = "balcon";
                                                    break;
                                                case 6:
                                                    campo = "zonaComun";
                                                    break;
                                                case 7:
                                                    System.out.println("\nVolviendo a mostrar propiedades...");
                                                    break;
                                                default:
                                                    System.out.println("Opción no válida. Intente de nuevo.");
                                                    break;
                                            }
                                            if (campo != null)
                                                gestorPropiedad.mostrarPropiedadesPorBoolean(campo);
                                            break;
                                        }
                                        case 9:
                                            System.out.println("\nVolviendo a gestionar propiedades...");
                                            break;
                                        default:
                                            System.out.println("Opción no válida. Intente de nuevo.");
                                    }

                                } while (opc3 != 9);
                                break;

                            case 5:
                                System.out.println("\nVolviendo a menú principal...");
                                break;

                            default:
                                System.out.println("\nOpción no válida. Intente de nuevo.");
                        }

                    } while (opc2 != 5);
                    break;
                case 3:
                    do {
                        System.out.println("\n¿Qué desea hacer?");
                        System.out.println("1. Agregar Reserva");
                        System.out.println("2. Actualizar Reserva");
                        System.out.println("3. Marcar Reserva como pagada");
                        System.out.println("4. Eliminar Reserva");
                        System.out.println("5. Mostrar Reservas");
                        System.out.println("6. Volver");
                        opc2 = leerInt(read, "Seleccione una opción: ");

                        switch (opc2) {
                            case 1: {
                                System.out.println("\n== Agregar Reserva ==");

                                int propiedadId = leerInt(read, "Propiedad ID: ");
                                if (!gestorPropiedad.existePropiedad(propiedadId)) {
                                    System.out.println("No se encontró la propiedad con el ID proporcionado. Volviendo al menú de reservas.");
                                    break;
                                }

                                double precioNoche = gestorPropiedad.obtenerPrecioNoche(propiedadId);
                                if (precioNoche <= 0) {
                                    System.out.println("Error: No se pudo obtener el precio por noche o es inválido.");
                                    break;
                                }

                                int inquilinoId = leerInt(read, "Inquilino ID: ");
                                if (!gestorUsuario.existeUsuario(inquilinoId)) {
                                    System.out.println("No se encontró el usuario con el ID proporcionado. Volviendo al menú de reservas.");
                                    break;
                                }
                                if (gestorUsuario.esTipoUsuario(inquilinoId, "Anfitrion")) {
                                    System.out.println("El id del Usuario no es inquilino");
                                    break;
                                }

                                LocalDate fechaInicio = leerFecha(read, "Fecha inicio (YYYY-MM-DD): ");

                                LocalDate fechaFin = null;

                                while (fechaFin == null) {
                                    fechaFin = leerFecha(read, "Fecha fin (YYYY-MM-DD): ");
                                    
                                    if (fechaFin != null && (fechaFin.isBefore(fechaInicio) || fechaFin.equals(fechaInicio))) {
                                        System.out.println("La fecha de fin debe ser posterior a la fecha de inicio.");
                                        fechaFin = null;
                                    }
                                }

                                int cantidadPersonas = leerInt(read, "Cantidad de personas: ");
                                boolean pagado = false;

                                Reserva nueva = new Reserva(propiedadId, inquilinoId, fechaInicio, fechaFin, 
                                                            precioNoche, cantidadPersonas, pagado);
                                
                                gestorReserva.agregarReserva(nueva);
                                break;
                            }

                            case 2: {
                                int idActualizar = leerInt(read, "\nIngrese el ID de la reserva que desea modificar: ");
                                if (!gestorReserva.existeReserva(idActualizar)) {
                                    System.out.println(
                                            "No se encontró la reserva con el ID proporcionado. Volviendo al menú de reservas.");
                                    break;
                                }
                                System.out.println("Datos actuales:");
                                gestorReserva.mostrarReservasPorId(idActualizar);

                                int opcionModificar = 0;
                                do {
                                    System.out.println("\n¿Qué desea modificar?");
                                    System.out.println("1. Fechas (inicio y/o fin)");
                                    System.out.println("2. Cantidad de personas");
                                    System.out.println("3. Volver");
                                    opcionModificar = leerInt(read, "Seleccione una opción: ");
                                    /*
                                    switch (opcionModificar) {
                                        case 1: {
                                            // Obtener propiedadId y cantidad actual desde la BD
                                            int propiedadId = -1;
                                            int cantidadActual = 1;
                                            String sql = "SELECT propiedadId, cantidadPersonas FROM Reservas WHERE id = ?";
                                            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                                                ps.setInt(1, idActualizar);
                                                try (ResultSet rs = ps.executeQuery()) {
                                                    if (rs.next()) {
                                                        propiedadId = rs.getInt("propiedadId");
                                                        cantidadActual = rs.getInt("cantidadPersonas");
                                                    }
                                                }
                                            } catch (SQLException e) {
                                                System.out.println("Error al obtener datos de la reserva: " + e.getMessage());
                                                break;
                                            }

                                            java.sql.Date nuevaFechaInicio = null;
                                            while (nuevaFechaInicio == null) {
                                                System.out.print("Nueva fecha inicio (YYYY-MM-DD): ");
                                                String line = read.nextLine().trim();
                                                try {
                                                    nuevaFechaInicio = java.sql.Date
                                                            .valueOf(java.time.LocalDate.parse(line));
                                                } catch (Exception e) {
                                                    System.out.println("Formato inválido. Use YYYY-MM-DD.");
                                                }
                                            }

                                            java.sql.Date nuevaFechaFin = null;
                                            while (nuevaFechaFin == null) {
                                                System.out.print("Nueva fecha fin (YYYY-MM-DD): ");
                                                String line = read.nextLine().trim();
                                                try {
                                                    nuevaFechaFin = java.sql.Date
                                                            .valueOf(java.time.LocalDate.parse(line));
                                                    if (nuevaFechaFin.before(nuevaFechaInicio)
                                                            || nuevaFechaFin.equals(nuevaFechaInicio)) {
                                                        System.out.println(
                                                                "La fecha de fin debe ser posterior a la fecha de inicio.");
                                                        nuevaFechaFin = null;
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println("Formato inválido. Use YYYY-MM-DD.");
                                                }
                                            }

                                            double precioNoche = gestorPropiedad.obtenerPrecioNoche(propiedadId);
                                            if (precioNoche <= 0) {
                                                System.out.println(
                                                        "Error: No se pudo obtener el precio por noche o es inválido. No se actualizó la reserva.");
                                                break;
                                            }

                                            java.time.LocalDate inicio = nuevaFechaInicio.toLocalDate();
                                            java.time.LocalDate fin = nuevaFechaFin.toLocalDate();
                                            long noches = java.time.temporal.ChronoUnit.DAYS.between(inicio, fin);
                                            double precioFinal = precioNoche * noches;

                                            Reserva actualizadaFechas = new Reserva(0, 0,
                                                    new java.util.Date(nuevaFechaInicio.getTime()),
                                                    new java.util.Date(nuevaFechaFin.getTime()), precioFinal,
                                                    cantidadActual, false);
                                            gestorReserva.actualizarReserva(actualizadaFechas, idActualizar);
                                            break;
                                        }

                                        case 2: {
                                            // Obtener fechas y propiedadId actuales desde la BD para recalcular precio
                                            int propiedadId = -1;
                                            java.sql.Date fechaInicioAct = null;
                                            java.sql.Date fechaFinAct = null;
                                            String sql = "SELECT propiedadId, fechaInicio, fechaFin FROM Reservas WHERE id = ?";
                                            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                                                ps.setInt(1, idActualizar);
                                                try (ResultSet rs = ps.executeQuery()) {
                                                    if (rs.next()) {
                                                        propiedadId = rs.getInt("propiedadId");
                                                        fechaInicioAct = rs.getDate("fechaInicio");
                                                        fechaFinAct = rs.getDate("fechaFin");
                                                    }
                                                }
                                            } catch (SQLException e) {
                                                System.out.println(
                                                        "Error al obtener datos de la reserva: " + e.getMessage());
                                                break;
                                            }

                                            int nuevaCant = leerInt(read, "Nueva cantidad de personas: ");

                                            double precioNoche = gestorPropiedad.obtenerPrecioNoche(propiedadId);
                                            if (precioNoche <= 0 || fechaInicioAct == null || fechaFinAct == null) {
                                                System.out.println(
                                                        "Error: No se pudo obtener datos necesarios para calcular el precio. No se actualizó la reserva.");
                                                break;
                                            }

                                            java.time.LocalDate inicio = fechaInicioAct.toLocalDate();
                                            java.time.LocalDate fin = fechaFinAct.toLocalDate();
                                            long noches = java.time.temporal.ChronoUnit.DAYS.between(inicio, fin);
                                            double precioFinal = precioNoche * noches;

                                            Reserva actualizadaCant = new Reserva(0, 0,
                                                    new java.util.Date(fechaInicioAct.getTime()),
                                                    new java.util.Date(fechaFinAct.getTime()), precioFinal, nuevaCant,
                                                    false);
                                            gestorReserva.actualizarReserva(actualizadaCant, idActualizar);
                                            break;
                                        }
                                        case 3:
                                            System.out.println("Volviendo...");
                                            break;

                                        default:
                                            System.out.println("Opción no válida. Intente de nuevo.");
                                    }*/

                                } while (opcionModificar != 4);
                                break;
                            }

                            case 3: {
                                int idPagar = leerInt(read, "\nIngrese el ID de la reserva a marcar como pagada: ");
                                gestorReserva.marcarReservaComoPagada(idPagar);
                                break;
                            }

                            case 4: {
                                int idEliminar = leerInt(read, "\nIngrese el ID de la reserva que desea eliminar: ");
                                gestorReserva.eliminarReserva(idEliminar);
                                break;
                            }

                            case 5: {
                                do {
                                    System.out.println("\n¿Qué desea mostrar?");
                                    System.out.println("1. Mostrar todas");
                                    System.out.println("2. Mostrar por ID");
                                    System.out.println("3. Mostrar por Inquilino");
                                    System.out.println("4. Volver");
                                    opc3 = leerInt(read, "Seleccione una opción: ");

                                    switch (opc3) {
                                        case 1:
                                            gestorReserva.mostrarReservas();
                                            break;
                                        case 2: {
                                            int idMostrar = leerInt(read, "Ingrese ID de la reserva: ");
                                            if (!gestorPropiedad.existePropiedad(idMostrar)) {
                                                System.out.println(
                                                        "No se encontró la reserva con el ID proporcionado. Volviendo...");
                                                break;
                                            }
                                            gestorReserva.mostrarReservasPorId(idMostrar);
                                            break;
                                        }
                                        case 3: {
                                            int inqId = leerInt(read, "Ingrese ID del inquilino: ");
                                            if (!gestorUsuario.existeUsuario(inqId)) {
                                                System.out.println(
                                                        "No se encontró el usuario con el ID proporcionado. Volviendo al menú de reservas.");
                                                break;
                                            }
                                            if (gestorUsuario.esTipoUsuario(inqId, "Anfitrion")) {
                                                System.out.println("El id del Usuario no es inquilino");
                                                break;
                                            }
                                            gestorReserva.mostrarReservasPorInquilino(inqId);
                                            break;
                                        }
                                        case 4:
                                            System.out.println("\nVolviendo al menú de Reservas...");
                                            break;
                                        default:
                                            System.out.println("\nOpción no válida. Intente de nuevo.");
                                            break;
                                    }
                                } while (opc3 != 4);
                                break;
                            }

                            case 6:
                                System.out.println("\nVolviendo a menú principal...");
                                break;

                            default:
                                System.out.println("\nOpción no válida. Intente de nuevo.");
                        }
                    } while (opc2 != 6);
                    break;
                case 4:
                    do {
                        System.out.println("\n¿Qué desea hacer con Reseñas?");
                        System.out.println("1. Agregar Reseña");
                        System.out.println("2. Actualizar Reseña");
                        System.out.println("3. Eliminar Reseña");
                        System.out.println("4. Mostrar Reseñas");
                        System.out.println("5. Volver");
                        opc2 = leerInt(read, "Seleccione una opción: ");

                        switch (opc2) {
                            case 1: {
                                System.out.println("\n== Agregar Reseña ==");
                                int propiedadId = leerInt(read, "Propiedad ID: ");
                                if (!gestorPropiedad.existePropiedad(propiedadId)) {
                                    System.out.println(
                                            "No se encontró la propiedad con el ID proporcionado. Volviendo al menú de reseñas.");
                                    break;
                                }

                                int inquilinoId = leerInt(read, "Inquilino ID: ");
                                if (!gestorUsuario.existeUsuario(inquilinoId)) {
                                    System.out.println(
                                            "No se encontró el usuario con el ID proporcionado. Volviendo al menú de reseñas.");
                                    break;
                                }
                                if (gestorUsuario.esTipoUsuario(inquilinoId, "Anfitrion")) {
                                    System.out.println("El id del Usuario no es inquilino");
                                    break;
                                }

                                System.out.print("Comentario: ");
                                String comentario = read.nextLine();
                                int puntaje = leerInt(read, "Puntaje (enteros): ");

                                Resenia resenia = new Resenia(propiedadId, inquilinoId, comentario, puntaje);
                                gestorResenia.agregarResenia(resenia);
                                break;
                            }

                            case 2: {
                                int idActualizar = leerInt(read, "\nIngrese el ID de la reseña que desea modificar: ");
                                if (!gestorResenia.existeResenia(idActualizar)) {
                                    System.out.println(
                                            "No se encontró la reseña con el ID proporcionado. Volviendo al menú de reseñas.");
                                    break;
                                }

                                System.out.println("Ingrese los nuevos datos:");
                                System.out.print("Nuevo comentario: ");
                                String comentarioNuevo = read.nextLine();
                                int puntajeNuevo = leerInt(read, "Nuevo puntaje (enteros): ");

                                Resenia actualizada = new Resenia(0, 0, comentarioNuevo, puntajeNuevo);
                                gestorResenia.actualizarResenia(actualizada, idActualizar);
                                break;
                            }

                            case 3: {
                                int idEliminar = leerInt(read, "\nIngrese el ID de la reseña que desea eliminar: ");
                                gestorResenia.eliminarResenia(idEliminar);
                                break;
                            }

                            case 4: {
                                do {
                                    System.out.println("\n¿Qué desea mostrar?");
                                    System.out.println("1. Mostrar todas");
                                    System.out.println("2. Mostrar por Propiedad");
                                    System.out.println("3. Mostrar por Inquilino");
                                    System.out.println("4. Volver");
                                    opc3 = leerInt(read, "Seleccione una opción: ");

                                    switch (opc3) {
                                        case 1:
                                            gestorResenia.mostrarResenias();
                                            break;
                                        case 2: {
                                            int propiedadId = leerInt(read, "Ingrese ID de la propiedad: ");
                                            if (gestorPropiedad.existePropiedad(propiedadId)) {
                                                gestorResenia.mostrarReseniasPorPropiedad(propiedadId);
                                            } else {
                                                System.out
                                                        .println("No se encontró la propiedad con el ID proporcionado");
                                            }
                                            break;
                                        }
                                        case 3: {
                                            int inquilinoId = leerInt(read, "Ingrese ID del inquilino: ");
                                            gestorResenia.mostrarReseniasPorInquilino(inquilinoId);
                                            if (gestorUsuario.existeUsuario(inquilinoId)) {
                                                gestorResenia.mostrarReseniasPorInquilino(inquilinoId);
                                            } else {
                                                System.out.println("No se encontró el usuario con el ID proporcionado");
                                            }
                                            break;
                                        }
                                        case 4:
                                            System.out.println("Volviendo al menú de Reseñas...");
                                            break;
                                        default:
                                            System.out.println("Opción no válida. Intente de nuevo.");
                                    }
                                } while (opc3 != 4);
                                break;
                            }

                            case 5:
                                System.out.println("\nVolviendo a menú principal...");
                                break;

                            default:
                                System.out.println("\nOpción no válida. Intente de nuevo.");
                        }
                    } while (opc2 != 5);
                    break;
                case 5:
                    System.out.println("\nSaliendo del programa...");
                    break;
                default:
                    System.out.println("\nOpción no válida. Intente de nuevo.");
            }
        } while (opc1 != 5);
    }
}