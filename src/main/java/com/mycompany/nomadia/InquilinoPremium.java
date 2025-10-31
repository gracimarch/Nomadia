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

    @Override
    public double obtenerDescuento() {
        System.out.println("El inquilino premium tiene un descuento del " + (DESCUENTO * 100) + "% en reservas.");
        return DESCUENTO;
    }
}
