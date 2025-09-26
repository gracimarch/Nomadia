package com.mycompany.nomadia;

import java.util.ArrayList;
import java.util.UUID;

public class Inquilino extends Usuario {
    private ArrayList<Reserva> reservas;
    
    public Inquilino(UUID id, String nombre, String email, String telefono) {
        super(id, nombre, email, telefono);
        reservas = new ArrayList<>();
    }
    
}
