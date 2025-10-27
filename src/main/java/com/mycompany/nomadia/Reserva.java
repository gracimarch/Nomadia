package com.mycompany.nomadia;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Reserva {
    private int propiedadId;
    private int inquilinoId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double precioFinal;
    private int cantidadPersonas;
    private boolean pagado;

    public Reserva(int propiedadId, int inquilinoId, LocalDate fechaInicio, LocalDate fechaFin,
            double precioFinal, int cantidadPersonas, boolean pagado) {

        this.propiedadId = propiedadId;
        this.inquilinoId = inquilinoId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cantidadPersonas = cantidadPersonas;
        this.pagado = pagado;
        this.precioFinal = precioFinal;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
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

    public double getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(double precioFinal) {
        this.precioFinal = precioFinal;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public static long getDuracionReserva(LocalDate fechaInicio, LocalDate fechaFin) {
        return ChronoUnit.DAYS.between(fechaInicio, fechaFin);
    }

    public static void crearReserva(Scanner read, GestorReserva gr, GestorPropiedad gp, GestorUsuario gu) {
        System.out.println("\n== Agregar Reserva ==");

        int propiedadId = Leer.leerInt(read, "Propiedad ID: ");
        if (!gp.existePropiedad(propiedadId)) {
            System.out.println("No se encontró la propiedad con el ID proporcionado. Volviendo al menú de reservas.");
            return;
        }

        double precioNoche = gp.obtenerPrecioNoche(propiedadId);
        if (precioNoche <= 0) {
            System.out.println("Error: No se pudo obtener el precio por noche o es inválido.");
            return;
        }

        int inquilinoId = Leer.leerInt(read, "Inquilino ID: ");
        if (!gu.existeUsuario(inquilinoId)) {
            System.out.println("No se encontró el usuario con el ID proporcionado. Volviendo al menú de reservas.");
            return;
        }
        if (gu.esTipoUsuario(inquilinoId, "Anfitrion")) {
            System.out.println("El ID del Usuario no es inquilino");
            return;
        }

        LocalDate fechaInicio = Leer.leerFecha(read, "Fecha inicio (YYYY-MM-DD): ");
        LocalDate fechaFin = esFechaValida(read, fechaInicio);

        double precioFinal = calcularPrecio(precioNoche, fechaInicio, fechaFin);

        int cantidadPersonas = leerCantidadPersonasValida(read, gp, propiedadId);
        boolean pagado = false;

        Reserva r = new Reserva(propiedadId, inquilinoId, fechaInicio, fechaFin,
                precioFinal, cantidadPersonas, pagado);

        gr.agregarReserva(r);
    }

    public static void actualizarReserva(Scanner read, GestorReserva gr, GestorPropiedad gp, int id, int propiedadId, String dato) {
        if (dato.equals("fecha")) {
            LocalDate nuevaFechaInicio = Leer.leerFecha(read, "Nueva fecha inicio (YYYY-MM-DD): ");
            LocalDate nuevaFechaFin = Reserva.esFechaValida(read, nuevaFechaInicio);

            double precioNoche = gp.obtenerPrecioNoche(propiedadId);
            if (precioNoche <= 0) {
                System.out.println("Error: No se pudo obtener el precio por noche o es inválido. No se actualizó la reserva.");
                return;
            }

            double precioFinal = Reserva.calcularPrecio(precioNoche, nuevaFechaInicio, nuevaFechaFin);

            gr.actualizarReserva(id, nuevaFechaInicio, nuevaFechaFin, precioFinal);
        } else {
            int nuevaCant = Reserva.leerCantidadPersonasValida(read, gp, propiedadId);

            gr.actualizarReserva(id, nuevaCant);
        }
    }

    public static LocalDate esFechaValida(Scanner read, LocalDate fechaInicio) {
        LocalDate fechaFin = null;

        while (fechaFin == null) {
            fechaFin = Leer.leerFecha(read, "Fecha fin (YYYY-MM-DD): ");

            if (fechaFin != null && (fechaFin.isBefore(fechaInicio) || fechaFin.equals(fechaInicio))) {
                System.out.println("La fecha de fin debe ser posterior a la fecha de inicio.");
                fechaFin = null;
            }
        }
        return fechaFin;
    }

    public static double calcularPrecio(double precioNoche, LocalDate fechaInicio, LocalDate fechaFin) {
        long noches = getDuracionReserva(fechaInicio, fechaFin);
        double precioFinal = precioNoche * noches;
        System.out.println("Precio final calculado: $" + String.format("%.2f", precioFinal));
        return precioFinal;
    }

    public static int leerCantidadPersonasValida(Scanner sc, GestorPropiedad gp, int idPropiedad) {
        int capacidadMaxima = gp.obtenerMaxPersonas(idPropiedad);
        while (true) {
            int cantPersonas = Leer.leerIntPositivo(sc, "Cantidad de personas: ");

            if (cantPersonas <= capacidadMaxima) {
                return cantPersonas;
            } else {
                System.out.println(
                        "Error: La cantidad de personas excede la capacidad máxima (" + capacidadMaxima + ").");
            }
        }
    }

    public static void eliminarReserva(Scanner read, GestorReserva gr) {
        int id = Leer.leerInt(read, "\nIngrese el ID de la reserva que desea eliminar: ");
        if (!gr.existeReserva(id)) {
            System.out.println("No se encontró la reserva con el ID proporcionado.");
        } else {
            gr.eliminarReserva(id);
        }
    }
}