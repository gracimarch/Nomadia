package com.mycompany.nomadia;

import java.util.Scanner;

public abstract class Usuario {
    private String nombre;
    private String email;
    private String telefono;
    private String tipo;

    public Usuario(String nombre, String email, String telefono, String tipo) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public static void crearUsuario(Scanner read, GestorUsuario gu, String tipo) {
        System.out.println("\nIngrese el nombre y apellido");
        String nombre = read.nextLine();
        System.out.println("Ingrese el email");
        String email = read.nextLine();
        System.out.println("Ingrese el teléfono");
        String telefono = read.nextLine();

        if (tipo.equals( "Inquilino")) {
            Inquilino i = new Inquilino(nombre, email, telefono);
            gu.agregarUsuario(i);
        } else {
            Anfitrion a = new Anfitrion(nombre, email, telefono);
            gu.agregarUsuario(a);
        }
    }

    public static void eliminarUsuario(Scanner read, GestorUsuario gu, GestorReserva gr) { // <-- Añadido GestorReserva
        int id = Leer.leerInt(read, "\nIngrese el ID del usuario que desea eliminar: ");

        // 1. Verificar si el usuario existe
        if (!gu.existeUsuario(id)) {
            System.err.println("⚠️ No se encontró ningún usuario con el ID " + id + ".");
            return;
        }

        try {
            if (gu.esTipoUsuario(id, "Anfitrion")) {
                if (gr.anfitrionTieneReservasActivas(id)) {
                    throw new AnfitrionConReservasActivasException(
                        "No se puede eliminar el Anfitrión ID " + id + ". Una o más de sus propiedades tienen reservas activas."
                    );
                }
                System.out.println("Aviso: El Anfitrión tiene propiedades, pero ninguna con reservas activas. Procediendo...");
            }
            gu.eliminarUsuario(id);

        } catch (AnfitrionConReservasActivasException e) {
            System.err.println("Error: " + e.getMessage());
            System.out.println("(Debe eliminar las reservas de las propiedades de este anfitrión).");
        }
    }

    public static void actualizarUsuario(Scanner read, GestorUsuario gu, String dato) {
        System.out.println("\nIngrese el ID del usuario a actualizar:");
        int idActualizar = Integer.parseInt(read.nextLine());
        System.out.println("Ingrese el nuevo "+ dato + ":");
        String nuevo = read.nextLine();
        gu.actualizarUsuario(idActualizar, dato, nuevo);
    }
}