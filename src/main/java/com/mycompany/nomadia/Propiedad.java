package com.mycompany.nomadia;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class Propiedad {
    private static final Set<Integer> idsExistentes = new HashSet<>();
    private int id;
    private int anfitrionId;
    private String tipo;
    private String ubicacion;
    private double precioNoche;
    private int habitaciones;
    private int banios;
    private int maxPersonas;
    private boolean parking;
    private boolean petFriendly;
    private ArrayList<Resenia> calificaciones;

    public Propiedad(int anfitrionId, String tipo, String ubicacion, double precioNoche, int habitaciones, int banios, int maxPersonas, boolean parking, boolean petFriendly) {
        setId(generarIdUnico());
        this.anfitrionId = anfitrionId;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.precioNoche = precioNoche;
        this.habitaciones = habitaciones;
        this.banios = banios;
        this.maxPersonas = maxPersonas;
        this.parking = parking;
        this.petFriendly = petFriendly;
        calificaciones = new ArrayList<>();
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
    
    public int getAnfitrionId() {
        return anfitrionId;
    }

    public void setAnfitrionId(int anfitrionId) {
        this.anfitrionId = anfitrionId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public double getPrecioNoche() {
        return precioNoche;
    }

    public void setPrecioNoche(double precioNoche) {
        this.precioNoche = precioNoche;
    }

    public int getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(int habitaciones) {
        this.habitaciones = habitaciones;
    }

    public int getBanios() {
        return banios;
    }

    public void setBanios(int banios) {
        this.banios = banios;
    }

    public int getMaxPersonas() {
        return maxPersonas;
    }

    public void setMaxPersonas(int maxPersonas) {
        this.maxPersonas = maxPersonas;
    }

    public boolean isParking() {
        return parking;
    }

    public void setParking(boolean parking) {
        this.parking = parking;
    }

    public boolean isPetFriendly() {
        return petFriendly;
    }

    public void setPetFriendly(boolean petFriendly) {
        this.petFriendly = petFriendly;
    }

    public ArrayList<Resenia> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(ArrayList<Resenia> calificaciones) {
        this.calificaciones = calificaciones;
    }    
    
}
