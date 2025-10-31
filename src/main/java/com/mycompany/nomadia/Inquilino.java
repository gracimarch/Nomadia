package com.mycompany.nomadia;

public class Inquilino extends Usuario {
    
    public Inquilino(String nombre, String email, String telefono) {
        super(nombre, email, telefono, "Inquilino");
    }

    public double obtenerDescuento(){
        System.out.println("El inquilino no tiene descuentos");
        return 0.0;
    }
    
}
