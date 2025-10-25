package com.mycompany.nomadia;

import java.sql.*;
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

    public static void eliminarUsuario(Scanner read, GestorUsuario gu) {
        System.out.println("\nIngrese el ID del usuario a eliminar:");
        int idEliminar = Integer.parseInt(read.nextLine());
        gu.eliminarUsuario(idEliminar);
    }

    public static void actualizarUsuario(Scanner read, GestorUsuario gu, String dato) {
        System.out.println("\nIngrese el ID del usuario a actualizar:");
        int idActualizar = Integer.parseInt(read.nextLine());
        System.out.println("Ingrese el nuevo "+ dato + ":");
        String nuevoEmail = read.nextLine();
        gu.actualizarUsuario(idActualizar, dato, nuevoEmail);
    }
}