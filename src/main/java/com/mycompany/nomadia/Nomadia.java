package com.mycompany.nomadia;

import java.sql.*;
import java.util.Scanner;

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
            if (line.equals("s") || line.equals("si")) return true;
            if (line.equals("n") || line.equals("no")) return false;
            System.out.println("Entrada inválida. Responda 's' o 'n'.");
        }
    }

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

            GestorUsuario gestorUsuario = new GestorUsuario();
            GestorPropiedad gestorPropiedad = new GestorPropiedad();
            GestorResenias gestorResenias = new GestorResenias();
            GestorReserva gestorReserva = new GestorReserva();

            Scanner read = new Scanner(System.in);
            int opc1 = 0;
            int opc2 = 0;
            int opc3 = 0;

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
                                        String nombre;
                                        String email;
                                        String telefono;

                                        System.out.println("\n¿Qué desea agregar?");
                                        System.out.println("1. Agregar Inquilino");
                                        System.out.println("2. Agregar Anfitrión");
                                        System.out.println("3. Volver");
                                        opc3 = leerInt(read, "Seleccione una opción: ");

                                        switch (opc3) {
                                            case 1:
                                                System.out.println("\nIngrese el nombre y apellido");
                                                nombre = read.nextLine();
                                                System.out.println("Ingrese el email");
                                                email = read.nextLine();
                                                System.out.println("Ingrese el teléfono");
                                                telefono = read.nextLine();

                                                Inquilino i = new Inquilino(nombre, email, telefono);
                                                gestorUsuario.agregarUsuario(conn, i);
                                                break;

                                            case 2:
                                                System.out.println("\nIngrese el nombre y apellido");
                                                nombre = read.nextLine();
                                                System.out.println("Ingrese el email");
                                                email = read.nextLine();
                                                System.out.println("Ingrese el teléfono");
                                                telefono = read.nextLine();

                                                Anfitrion a = new Anfitrion(nombre, email, telefono);
                                                gestorUsuario.agregarUsuario(conn, a);
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

                                    if (!gestorUsuario.existeUsuario(conn, id)) {
                                        System.out.println("No se encontró el usuario con el ID proporcionado. Volviendo al menú de usuarios.");
                                        break;
                                    }

                                    System.out.println("\nDatos actuales del usuario:");
                                    gestorUsuario.mostrarUsuarioPorId(conn, id);

                                    do {
                                        System.out.println("\n¿Qué desea actualizar ?");
                                        System.out.println("1. Actualizar email");
                                        System.out.println("2. Actualizar teléfono");
                                        
                                        if (gestorUsuario.esTipoUsuario(conn, id, "Inquilino")) {
                                            System.out.println("3. Ascender Inquilino a Inquilino Premium");
                                            System.out.println("4. Opción no disponible para este tipo de usuario");

                                        } else if (gestorUsuario.esTipoUsuario(conn, id, "InquilinoPremium")) {
                                            System.out.println("3. Opción no disponible para este tipo de usuario");
                                            System.out.println("4. Descender Inquilino Premium a Inquilino");

                                        } else {
                                            System.out.println("3. Opción no disponible para este tipo de usuario");
                                            System.out.println("4. Opción no disponible para este tipo de usuario");
                                        }
                                        System.out.println("5. Volver");
                                        opc3 = leerInt(read, "Seleccione una opción: ");

                                        switch (opc3) {
                                            case 1:
                                                System.out.println("\nIngrese el email actualizado");
                                                String emailActualizado = read.nextLine();
                                                gestorUsuario.actualizarEmail(conn, id, emailActualizado);
                                                break;

                                            case 2:
                                                System.out.println("\nIngrese el teléfono actualizado");
                                                String telefonoActualizado = read.nextLine();
                                                gestorUsuario.actualizarTelefono(conn, id, telefonoActualizado);
                                                break;

                                            case 3:
                                                if (gestorUsuario.esTipoUsuario(conn, id, "Inquilino")) {
                                                    gestorUsuario.actualizarTipoUsuario(conn, id, "InquilinoPremium");
                                                } else {
                                                    System.out.println("El usuario no es un Inquilino. No se puede ascender.");
                                                }
                                                break;

                                            case 4:
                                                if (gestorUsuario.esTipoUsuario(conn, id, "InquilinoPremium")) {
                                                    gestorUsuario.actualizarTipoUsuario(conn, id, "Inquilino");
                                                } else {
                                                    System.out.println("El usuario no es un Inquilino Premium. No se puede descender.");
                                                }
                                                break;

                                            case 5:
                                                System.out.println("\nVolviendo a gestionar usuarios...");
                                                break;

                                            default:
                                                System.out.println("\nOpción no válida. Intente de nuevo.");
                                        }
                                    } while (opc3 != 5);
                                    break;

                                case 3:
                                    int idEliminar = leerInt(read, "\nIngrese el ID del usuario que desea eliminar: ");
                                    gestorUsuario.eliminarUsuario(conn, idEliminar);
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
                                                gestorUsuario.mostrarUsuarios(conn);
                                                break;

                                            case 2:
                                                gestorUsuario.mostrarUsuariosPorTipo(conn, "Inquilino");
                                                break;

                                            case 3:
                                                gestorUsuario.mostrarUsuariosPorTipo(conn, "Anfitrion");
                                                break;

                                            case 4:
                                                int idMostrar = leerInt(read, "\nIngrese el ID del usuario que desea mostrar: ");
                                                gestorUsuario.mostrarUsuarioPorId(conn, idMostrar);
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
                                                int anfitrionId = leerInt(read, "Anfitrión ID: ");
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

                                                Casa casa = new Casa(anfitrionId, ubicacion, precio, habitaciones, banios, maxPersonas, parking, petFriendly, parrilla, patio);
                                                gestorPropiedad.agregarCasa(conn, casa);
                                                break;
                                            }
                                            case 2: {
                                                System.out.println("\n== Agregar Departamento ==");
                                                int anfitrionId = leerInt(read, "Anfitrión ID: ");
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

                                                Departamento depto = new Departamento(anfitrionId, "Departamento", ubicacion, precio, habitaciones, banios, maxPersonas, parking, petFriendly, piso, balcon, zonaComun);
                                                gestorPropiedad.agregarDepartamento(conn, depto);
                                                break;
                                            }
                                            case 3: {
                                                System.out.println("\n== Agregar Hotel ==");
                                                int anfitrionId = leerInt(read, "Anfitrión ID: ");
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
                                                java.util.ArrayList<String> servicios = new java.util.ArrayList<>(); // manejar servicios por fuera si hace falta

                                                Hotel hotel = new Hotel(anfitrionId, "Hotel", ubicacion, precio, habitaciones, banios, maxPersonas, parking, petFriendly, checkIn, checkOut, servicios);
                                                gestorPropiedad.agregarHotel(conn, hotel);
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
                                    int idActualizar = leerInt(read, "\nIngrese el ID de la propiedad que desea modificar: ");
                                    // verificar existencia mostrando datos
                                    gestorPropiedad.mostrarPropiedadPorId(conn, idActualizar);
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
                                                gestorPropiedad.actualizarPropiedad(conn, idActualizar, nuevoPrecio);
                                                break;
                                            case 2:
                                                gestorPropiedad.actualizarPropiedad(conn, idActualizar, "parking");
                                                break;
                                            case 3:
                                                gestorPropiedad.actualizarPropiedad(conn, idActualizar, "petFriendly");
                                                break;
                                            case 4:
                                                gestorPropiedad.actualizarPropiedad(conn, idActualizar, "parrilla");
                                                break;
                                            case 5:
                                                gestorPropiedad.actualizarPropiedad(conn, idActualizar, "patio");
                                                break;
                                            case 6:
                                                gestorPropiedad.actualizarPropiedad(conn, idActualizar, "balcon");
                                                break;
                                            case 7:
                                                gestorPropiedad.actualizarPropiedad(conn, idActualizar, "zonaComun");
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
                                    gestorPropiedad.eliminarPropiedad(conn, idEliminar);
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
                                        System.out.println("8. Mostrar con pet friendly / otro boolean");
                                        System.out.println("9. Volver");
                                        opc3 = leerInt(read, "Seleccione una opción: ");

                                        switch (opc3) {
                                            case 1:
                                                gestorPropiedad.mostrarPropiedades(conn);
                                                break;
                                            case 2: {
                                                int anfitrionId = leerInt(read, "Ingrese anfitrión ID: ");
                                                gestorPropiedad.mostrarPropiedadesPorAnfitrion(conn, anfitrionId);
                                                break;
                                            }
                                            case 3: {
                                                System.out.print("Ingrese tipo (Casa/Departamento/Hotel): ");
                                                String tipo = read.nextLine();
                                                gestorPropiedad.mostrarPropiedadesPorTipo(conn, tipo);
                                                break;
                                            }
                                            case 4: {
                                                int idMostrar = leerInt(read, "Ingrese ID de la propiedad: ");
                                                gestorPropiedad.mostrarPropiedadPorId(conn, idMostrar);
                                                break;
                                            }
                                            case 5: {
                                                System.out.print("Ingrese ubicación (texto o parte): ");
                                                String ubic = read.nextLine();
                                                gestorPropiedad.mostrarPropiedadesPorUbicacion(conn, ubic);
                                                break;
                                            }
                                            case 6: {
                                                double precioMax = leerDouble(read, "Ingrese precio máximo: ");
                                                gestorPropiedad.mostrarPropiedadesPorPrecioMaximo(conn, precioMax);
                                                break;
                                            }
                                            case 7: {
                                                int capacidad = leerInt(read, "Ingrese capacidad mínima (personas): ");
                                                gestorPropiedad.mostrarPropiedadesPorCapacidad(conn, capacidad);
                                                break;
                                            }
                                            case 8: {
                                                System.out.print("Ingrese nombre del campo boolean a filtrar (ej. petFriendly, parking, balcon, zonaComun, etc.): ");
                                                String campo = read.nextLine();
                                                gestorPropiedad.mostrarPropiedadesPetFriendly(conn, campo);
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
                                    int inquilinoId = leerInt(read, "Inquilino ID: ");

                                    java.sql.Date fechaInicio = null;
                                    while (fechaInicio == null) {
                                        System.out.print("Fecha inicio (YYYY-MM-DD): ");
                                        String line = read.nextLine().trim();
                                        try {
                                            fechaInicio = java.sql.Date.valueOf(java.time.LocalDate.parse(line));
                                        } catch (Exception e) {
                                            System.out.println("Formato inválido. Use YYYY-MM-DD.");
                                        }
                                    }

                                    java.sql.Date fechaFin = null;
                                    while (fechaFin == null) {
                                        System.out.print("Fecha fin (YYYY-MM-DD): ");
                                        String line = read.nextLine().trim();
                                        try {
                                            fechaFin = java.sql.Date.valueOf(java.time.LocalDate.parse(line));
                                        } catch (Exception e) {
                                            System.out.println("Formato inválido. Use YYYY-MM-DD.");
                                        }
                                    }

                                    double precioFinal = leerDouble(read, "Precio final: ");
                                    int cantidadPersonas = leerInt(read, "Cantidad de personas: ");
                                    boolean pagado = false; // por defecto al crear

                                    Reserva nueva = new Reserva(propiedadId, inquilinoId, new java.util.Date(fechaInicio.getTime()), new java.util.Date(fechaFin.getTime()), precioFinal, cantidadPersonas, pagado);
                                    gestorReserva.agregarReserva(conn, nueva);
                                    break;
                                }

                                case 2: {
                                    int idActualizar = leerInt(read, "\nIngrese el ID de la reserva que desea modificar: ");
                                    System.out.println("Datos actuales:");
                                    gestorReserva.mostrarReservasPorId(conn, idActualizar);

                                    java.sql.Date fechaInicio = null;
                                    while (fechaInicio == null) {
                                        System.out.print("Nueva fecha inicio (YYYY-MM-DD): ");
                                        String line = read.nextLine().trim();
                                        try {
                                            fechaInicio = java.sql.Date.valueOf(java.time.LocalDate.parse(line));
                                        } catch (Exception e) {
                                            System.out.println("Formato inválido. Use YYYY-MM-DD.");
                                        }
                                    }

                                    java.sql.Date fechaFin = null;
                                    while (fechaFin == null) {
                                        System.out.print("Nueva fecha fin (YYYY-MM-DD): ");
                                        String line = read.nextLine().trim();
                                        try {
                                            fechaFin = java.sql.Date.valueOf(java.time.LocalDate.parse(line));
                                        } catch (Exception e) {
                                            System.out.println("Formato inválido. Use YYYY-MM-DD.");
                                        }
                                    }

                                    double nuevoPrecio = leerDouble(read, "Nuevo precio final: ");
                                    int nuevaCant = leerInt(read, "Nueva cantidad de personas: ");

                                    Reserva actualizada = new Reserva(0, 0, new java.util.Date(fechaInicio.getTime()), new java.util.Date(fechaFin.getTime()), nuevoPrecio, nuevaCant, false);
                                    gestorReserva.actualizarReserva(conn, actualizada, idActualizar);
                                    break;
                                }

                                case 3: {
                                    int idPagar = leerInt(read, "\nIngrese el ID de la reserva a marcar como pagada: ");
                                    gestorReserva.marcarReservaComoPagada(conn, idPagar);
                                    break;
                                }

                                case 4: {
                                    int idEliminar = leerInt(read, "\nIngrese el ID de la reserva que desea eliminar: ");
                                    gestorReserva.eliminarReserva(conn, idEliminar);
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
                                                gestorReserva.mostrarReservas(conn);
                                                break;
                                            case 2: {
                                                int idMostrar = leerInt(read, "Ingrese ID de la reserva: ");
                                                gestorReserva.mostrarReservasPorId(conn, idMostrar);
                                                break;
                                            }
                                            case 3: {
                                                int inqId = leerInt(read, "Ingrese ID del inquilino: ");
                                                gestorReserva.mostrarReservasPorInquilino(conn, inqId);
                                                break;
                                            }
                                            case 4:
                                                break;
                                            default:
                                                System.out.println("Opción no válida. Intente de nuevo.");
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
                                    int inquilinoId = leerInt(read, "Inquilino ID: ");
                                    System.out.print("Comentario: ");
                                    String comentario = read.nextLine();
                                    int puntaje = leerInt(read, "Puntaje (enteros): ");

                                    Resenia nueva = new Resenia(propiedadId, inquilinoId, comentario, puntaje);
                                    gestorResenias.agregarResenia(conn, nueva);
                                    break;
                                }

                                case 2: {
                                    int idActualizar = leerInt(read, "\nIngrese el ID de la reseña que desea modificar: ");
                                    System.out.println("Ingrese los nuevos datos:");
                                    System.out.print("Nuevo comentario: ");
                                    String comentarioNuevo = read.nextLine();
                                    int puntajeNuevo = leerInt(read, "Nuevo puntaje (enteros): ");

                                    Resenia actualizada = new Resenia(0, 0, comentarioNuevo, puntajeNuevo);
                                    gestorResenias.actualizarResenia(conn, actualizada, idActualizar);
                                    break;
                                }

                                case 3: {
                                    int idEliminar = leerInt(read, "\nIngrese el ID de la reseña que desea eliminar: ");
                                    gestorResenias.eliminarResenia(conn, idEliminar);
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
                                                gestorResenias.mostrarResenias(conn);
                                                break;
                                            case 2: {
                                                int propiedadId = leerInt(read, "Ingrese ID de la propiedad: ");
                                                gestorResenias.mostrarReseniasPorPropiedad(conn, propiedadId);
                                                break;
                                            }
                                            case 3: {
                                                int inquilinoId = leerInt(read, "Ingrese ID del inquilino: ");
                                                gestorResenias.mostrarReseniasPorInquilino(conn, inquilinoId);
                                                break;
                                            }
                                            case 4:
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

        } catch (SQLException e) {
            System.out.println("Error al conectarse a la base de datos: " + e.getMessage());
        }
    }
}