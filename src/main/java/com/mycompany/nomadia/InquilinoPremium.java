package com.mycompany.nomadia;

public class InquilinoPremium extends Inquilino {
    private static final double DESCUENTO = 0.2;
    
    public InquilinoPremium(String nombre, String email, String telefono) {
        super(nombre, email, telefono);
        setTipo("InquilinoPremium");
    }

    public double getDescuento() {
        return DESCUENTO;
    }
}
