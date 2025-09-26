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
public class Resenia {
    private UUID id;
    private String comentario;
    private int puntaje;

    public Resenia(UUID id, String comentario, int puntaje) {
        this.id = id;
        this.comentario = comentario;
        this.puntaje = puntaje;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
    
    
}
