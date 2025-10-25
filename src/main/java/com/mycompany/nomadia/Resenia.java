package com.mycompany.nomadia;

import java.util.Scanner;

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
    
    public static void crearResenia(Scanner read, GestorPropiedad gp, GestorUsuario gu, GestorResenia gr){
        System.out.println("\n== Agregar Reseña ==");
        int propiedadId = Leer.leerInt(read, "Propiedad ID: ");
        if (!gp.existePropiedad(propiedadId)) {
            System.out.println(
                    "No se encontró la propiedad con el ID proporcionado. Volviendo al menú de reseñas.");
            return;
        }

        int inquilinoId = Leer.leerInt(read, "Inquilino ID: ");
        if (!gu.existeUsuario(inquilinoId)) {
            System.out.println(
                    "No se encontró el usuario con el ID proporcionado. Volviendo al menú de reseñas.");
            return;
        }
        if (gu.esTipoUsuario(inquilinoId, "Anfitrion")) {
            System.out.println("El id del Usuario no es inquilino");
            return;
        }

        System.out.print("Comentario: ");
        String comentario = read.nextLine();
        int puntaje = Leer.leerEstrellas(read, "Puntaje ");

        Resenia resenia = new Resenia(propiedadId, inquilinoId, comentario, puntaje);
        gr.agregarResenia(resenia);
        return;
    }

    public static void eliminarResenia(Scanner read, GestorResenia gr) {
        int id = Leer.leerInt(read, "\nIngrese el ID de la reseña que desea eliminar: ");
        if (!gr.existeResenia(id)) {
            System.out.println("No se encontró la reseña con el ID proporcionado.");
        } else {
            gr.eliminarResenia(id);
        }
    }

}
