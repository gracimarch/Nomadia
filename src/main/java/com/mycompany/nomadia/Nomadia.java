package com.mycompany.nomadia;

import java.sql.*;
import java.util.Scanner;

public class Nomadia {

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
                System.out.print("Seleccione una opción: ");
                opc1 = read.nextInt();

                switch (opc1) {

                    case 1:
                        do {
                            System.out.println("\n¿Qué desea hacer?");
                            System.out.println("1. Agregar Usuario");
                            System.out.println("2. Actualizar Usuario");
                            System.out.println("3. Eliminar Usuario");
                            System.out.println("4. Mostrar Usuarios");
                            System.out.println("5. Volver");
                            System.out.print("Seleccione una opción: ");
                            opc2 = read.nextInt();

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
                                        System.out.print("Seleccione una opción: ");
                                        opc3 = read.nextInt();

                                        switch (opc3) {
                                            case 1:
                                                System.out.println("Ingrese el nombre y apellido");
                                                nombre = read.nextLine();
                                                System.out.println("Ingrese el email");
                                                email = read.nextLine();
                                                System.out.println("Ingrese el teléfono");
                                                telefono = read.nextLine();

                                                Inquilino i = new Inquilino(nombre, email, telefono);
                                                gestorUsuario.agregarUsuario(conn, i);
                                                break;

                                            case 2:
                                                System.out.println("Ingrese el nombre y apellido");
                                                nombre = read.nextLine();
                                                System.out.println("Ingrese el email");
                                                email = read.nextLine();
                                                System.out.println("Ingrese el teléfono");
                                                telefono = read.nextLine();

                                                Anfitrion a = new Anfitrion(nombre, email, telefono);
                                                gestorUsuario.agregarUsuario(conn, a);
                                                break;

                                            case 3:
                                                System.out.println("Volviendo a gestionar usuarios...");
                                                break;

                                            default:
                                                System.out.println("Opción no válida. Intente de nuevo.");
                                        }
                                    } while (opc3 != 3);
                                    break;

                                case 2:
                                    do {
                                        int id;
                                        System.out.println("\nIngrese el ID del usuario que desea modificar");
                                        id = read.nextInt();

                                        System.out.println("\n¿Qué desea actualizar?");
                                        System.out.println("1. Actualizar email");
                                        System.out.println("2. Actualizar teléfono");
                                        System.out.println("3. Ascender Inquilino a Inquilino Premium");
                                        System.out.println("4. Volver");
                                        System.out.print("Seleccione una opción: ");
                                        opc3 = read.nextInt();

                                        switch (opc3) {
                                            case 1:
                                                System.out.println("Ingrese el email actualizado");
                                                gestorUsuario.actualizarEmail(conn, id, read.nextLine());
                                                break;

                                            case 2:
                                                System.out.println("Ingrese el teléfono actualizado");
                                                gestorUsuario.actualizarTelefono(conn, id, read.nextLine());
                                                break;

                                            case 3:
                                                gestorUsuario.actualizarTipoUsuario(conn, id, "InquilinoPremium");
                                                break;

                                            case 4:
                                                System.out.println("Volviendo a gestionar usuarios...");
                                                break;

                                            default:
                                                System.out.println("Opción no válida. Intente de nuevo.");
                                        }
                                    } while (opc3 != 4);
                                    break;

                                case 3:
                                    System.out.println("\nIngrese el ID del usuario que desea eliminar");
                                    gestorUsuario.eliminarUsuario(conn, read.nextInt());
                                    break;

                                case 4:
                                    switch (opc2) {
                                        case 1:
                                            do {
                                                System.out.println("\n¿Qué desea mostrar?");
                                                System.out.println("1. Mostrar Usuarios");
                                                System.out.println("2. Mostrar Inquilinos");
                                                System.out.println("3. Mostrar Anfitriones");
                                                System.out.println("4. Mostrar por ID");
                                                System.out.println("5. Volver");
                                                System.out.print("Seleccione una opción: ");
                                                opc3 = read.nextInt();

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
                                                        System.out.print("Ingrese el ID del usuario que desea mostrar: ");
                                                        gestorUsuario.mostrarUsuarioPorId(conn, read.nextInt());
                                                        break;

                                                    case 5:
                                                        System.out.println("Volviendo a gestionar usuarios...");
                                                        break;

                                                    default:
                                                        System.out.println("Opción no válida. Intente de nuevo.");
                                                }
                                            } while (opc3 != 5);
                                            break;
                                    }
                                case 5:
                                    System.out.println("Volviendo a menú principal...");
                                    break;

                                default:
                                    System.out.println("Opción no válida. Intente de nuevo.");
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
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            } while (opc1 != 5);

        } catch (SQLException e) {
            System.out.println("Error al conectarse a la base de datos: " + e.getMessage());
        }
    }
}
