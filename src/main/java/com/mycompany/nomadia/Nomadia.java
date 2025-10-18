package com.mycompany.nomadia;

import java.sql.*;
import java.util.Scanner;

public class Nomadia {

    private static int leerInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número.");
            }
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

                                    System.out.println("\nDatos actuales del usuario:");
                                    gestorUsuario.mostrarUsuarioPorId(conn, id);

                                    do {
                                        System.out.println("\n¿Qué desea actualizar ?");
                                        System.out.println("1. Actualizar email");
                                        System.out.println("2. Actualizar teléfono");
                                        System.out.println("3. Ascender Inquilino a Inquilino Premium");
                                        System.out.println("4. Volver");
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
                                                gestorUsuario.actualizarTipoUsuario(conn, id, "InquilinoPremium");
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
                        // Lógica para gestionar propiedades
                        break;
                    case 3:
                        // Lógica para gestionar reservas
                        break;
                    case 4:
                        // Lógica para gestionar reseñas
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