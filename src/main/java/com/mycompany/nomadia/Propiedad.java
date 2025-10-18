package com.mycompany.nomadia;

import java.util.ArrayList;

public abstract class Propiedad {
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
