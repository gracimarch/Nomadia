package com.mycompany.nomadia;

import java.util.HashSet;
import java.util.Set;

public class Resenia {
    private static final Set<Integer> idsExistentes = new HashSet<>();
    private int id;
    private int propiedadId;
    private int inquilinoId;
    private String comentario;
    private int puntaje;
    
    public Resenia(int propiedadId, int inquilinoId, String comentario, int puntaje) {
        setId(generarIdUnico());
        this.propiedadId = propiedadId;
        this.inquilinoId = inquilinoId;
        this.comentario = comentario;
        this.puntaje = puntaje;
    }

    private int generarIdUnico() {
        int nuevoId = 1;
        while (idsExistentes.contains(nuevoId)) {
            nuevoId++;
        }
        return nuevoId;
    }

    public int getId() {
        return id;
    }
    
    public void setId(int nuevoId) {
        boolean idAsignado = false;
        while (!idAsignado) {
            try {
                if (!idsExistentes.contains(nuevoId)) {
                    idsExistentes.remove(this.id);
                    this.id = nuevoId;
                    idsExistentes.add(nuevoId);
                    idAsignado = true;
                } else {
                    throw new IllegalArgumentException("El ID ya existe en memoria.");
                }
            } catch (Exception e) {
                nuevoId = generarIdUnico();
            }
        }
    }

    public int getPropiedadId() {
        return propiedadId;
    }

    public void setPropiedadId(int propiedadId) {
        this.propiedadId = propiedadId;
    }

    public int getInquilinoId() {
        return inquilinoId;
    }

    public void setInquilinoId(int inquilinoId) {
        this.inquilinoId = inquilinoId;
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
