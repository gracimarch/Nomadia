package com.mycompany.nomadia;

public class InquilinoPremium extends Inquilino {
    private double descuento;
    
    public InquilinoPremium(String nombre, String email, String telefono, double descuento) {
        super(nombre, email, telefono);
        setTipo("InquilinoPremium");
        this.descuento = descuento;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
}
