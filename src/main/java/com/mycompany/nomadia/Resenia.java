package com.mycompany.nomadia;

public class Resenia {
    private int propiedadId;
    private int inquilinoId;
    private String comentario;
    private int puntaje;
    
    public Resenia(int propiedadId, int inquilinoId, String comentario, int puntaje) {
        this.propiedadId = propiedadId;
        this.inquilinoId = inquilinoId;
        this.comentario = comentario;
        this.puntaje = puntaje;
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
