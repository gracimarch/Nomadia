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
public class Departamento extends Propiedad {
    private int piso;
    private boolean balcon;
    private boolean zonaComun;

    public Departamento(int piso, boolean balcon, boolean zonaComun, UUID id, String ubicacion, double precioNoche, int habitaciones, int banios, int maxPersonas, boolean parking, boolean petFriendly) {
        super(id, ubicacion, precioNoche, habitaciones, banios, maxPersonas, parking, petFriendly);
        this.piso = piso;
        this.balcon = balcon;
        this.zonaComun = zonaComun;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public boolean isBalcon() {
        return balcon;
    }

    public void setBalcon(boolean balcon) {
        this.balcon = balcon;
    }

    public boolean isZonaComun() {
        return zonaComun;
    }

    public void setZonaComun(boolean zonaComun) {
        this.zonaComun = zonaComun;
    }
    
    
}
