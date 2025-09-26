package com.mycompany.nomadia;

import java.util.UUID;

public class InquilinoPremium extends Inquilino {
    private double descuento;

    public InquilinoPremium(UUID id, String nombre, String email, String telefono, double descuento) {
        super(id, nombre, email, telefono);
        this.descuento = descuento;
    }
}
