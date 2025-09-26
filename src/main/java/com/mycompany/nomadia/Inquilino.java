/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nomadia;

import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author graci
 */
public class Inquilino extends Usuario {
    private ArrayList<Reserva> reservas;
    
    public Inquilino(UUID id, String nombre, String email, String telefono) {
        super(id, nombre, email, telefono);
        reservas = new ArrayList<>();
    }
    
}
