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
public class InquilinoPremium extends Inquilino {
    private double descuento;

    public InquilinoPremium(UUID id, String nombre, String email, String telefono, double descuento) {
        super(id, nombre, email, telefono);
        this.descuento = descuento;
    }
}
