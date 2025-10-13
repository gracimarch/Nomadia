package com.mycompany.nomadia;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public abstract class Usuario {
    private static final Set<Integer> idsExistentes = new HashSet<>();
    private int id;
    private String nombre;
    private String email;
    private String telefono;
    private String tipo;

    public Usuario(String nombre, String email, String telefono, String tipo) {
        setId(generarIdUnico());
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.tipo = tipo;
    }

    private int generarIdUnico() {
        int nuevoId = 1;
        while (idsExistentes.contains(nuevoId)) {
            nuevoId++;
        }
        return nuevoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int nuevoId) {
        boolean idAsignado = false;
        while (!idAsignado) {
            try {
                if (!idsExistentes.contains(nuevoId)) {
                    idsExistentes.remove(this.id);
                    this.id = nuevoId;
                    idsExistentes.add(nuevoId);
                    idAsignado = true;
                } else {
                    throw new IllegalArgumentException("El ID ya existe en memoria.");
                }
            } catch (Exception e) {
                nuevoId = generarIdUnico();
            }
        }
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
}