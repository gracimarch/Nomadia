/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nomadia;

import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author graci
 */
public abstract class Propiedad {
    private UUID id;
    private String ubicacion;
    private double precioNoche;
    private int habitaciones;
    private int banios;
    private int maxPersonas;
    private boolean parking;
    private boolean petFriendly;
    private ArrayList<Resenia> calificaciones;

    public Propiedad(UUID id, String ubicacion, double precioNoche, int habitaciones, int banios, int maxPersonas, boolean parking, boolean petFriendly) {
        this.id = id;
        this.ubicacion = ubicacion;
        this.precioNoche = precioNoche;
        this.habitaciones = habitaciones;
        this.banios = banios;
        this.maxPersonas = maxPersonas;
        this.parking = parking;
        this.petFriendly = petFriendly;
        calificaciones = new ArrayList<>();
    }
    
    
}
