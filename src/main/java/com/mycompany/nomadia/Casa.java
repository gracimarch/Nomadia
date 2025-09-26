/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nomadia;

import java.util.UUID;

/**
 *
 * @author graci
 */
public class Casa extends Propiedad {
    private boolean parrilla;
    private boolean patio;

    public Casa(boolean parrilla, boolean patio, UUID id, String ubicacion, double precioNoche, int habitaciones, int banios, int maxPersonas, boolean parking, boolean petFriendly) {
        super(id, ubicacion, precioNoche, habitaciones, banios, maxPersonas, parking, petFriendly);
        this.parrilla = parrilla;
        this.patio = patio;
    }

    public boolean isParrilla() {
        return parrilla;
    }

    public void setParrilla(boolean parrilla) {
        this.parrilla = parrilla;
    }

    public boolean isPatio() {
        return patio;
    }

    public void setPatio(boolean patio) {
        this.patio = patio;
    }
    
    
}
