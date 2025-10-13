package com.mycompany.nomadia;

import java.util.ArrayList;

public class Inquilino extends Usuario {
    private ArrayList<Reserva> reservas;
    
    public Inquilino(String nombre, String email, String telefono) {
        super(nombre, email, telefono, "Inquilino");
        reservas = new ArrayList<>();
    }
    
}
