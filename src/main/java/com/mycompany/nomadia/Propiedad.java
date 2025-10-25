package com.mycompany.nomadia;

import java.util.Scanner;
import java.util.ArrayList;

public abstract class Propiedad {
    private int anfitrionId;
    private String tipo;
    private String ubicacion;
    private double precioNoche;
    private int habitaciones;
    private int banios;
    private int maxPersonas;
    private boolean parking;
    private boolean petFriendly;
    private ArrayList<Resenia> calificaciones;

    public Propiedad(int anfitrionId, String tipo, String ubicacion, double precioNoche, int habitaciones, int banios,
            int maxPersonas, boolean parking, boolean petFriendly) {
        this.anfitrionId = anfitrionId;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.precioNoche = precioNoche;
        this.habitaciones = habitaciones;
        this.banios = banios;
        this.maxPersonas = maxPersonas;
        this.parking = parking;
        this.petFriendly = petFriendly;
        calificaciones = new ArrayList<>();
    }

    public int getAnfitrionId() {
        return anfitrionId;
    }

    public void setAnfitrionId(int anfitrionId) {
        this.anfitrionId = anfitrionId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public double getPrecioNoche() {
        return precioNoche;
    }

    public void setPrecioNoche(double precioNoche) {
        this.precioNoche = precioNoche;
    }

    public int getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(int habitaciones) {
        this.habitaciones = habitaciones;
    }

    public int getBanios() {
        return banios;
    }

    public void setBanios(int banios) {
        this.banios = banios;
    }

    public int getMaxPersonas() {
        return maxPersonas;
    }

    public void setMaxPersonas(int maxPersonas) {
        this.maxPersonas = maxPersonas;
    }

    public boolean isParking() {
        return parking;
    }

    public void setParking(boolean parking) {
        this.parking = parking;
    }

    public boolean isPetFriendly() {
        return petFriendly;
    }

    public void setPetFriendly(boolean petFriendly) {
        this.petFriendly = petFriendly;
    }

    public ArrayList<Resenia> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(ArrayList<Resenia> calificaciones) {
        this.calificaciones = calificaciones;
    }

    public static void agregarPropiedad(Scanner read, GestorPropiedad gp, GestorUsuario gu, String tipoPropiedad) {
        System.out.println("\n== Agregar Propiedad ==");
        int anfitrionId;
        boolean esAnfitrion;
        do {
            anfitrionId = Leer.leerInt(read, "Anfitrión ID: ");
            esAnfitrion = gu.esTipoUsuario(anfitrionId,"Anfitrion");
            if (!esAnfitrion)
                System.out.println("El ID no es de un anfitrión");
        } while (!esAnfitrion);
        System.out.print("Ubicación: ");
        String ubicacion = read.nextLine();
        double precio = Leer.leerDouble(read, "Precio por noche: ");
        int habitaciones = Leer.leerInt(read, "Habitaciones (número): ");
        int banios = Leer.leerInt(read, "Baños (número): ");
        int maxPersonas = Leer.leerInt(read, "Máx. personas: ");
        boolean parking = Leer.leerBoolean(read, "Parking disponible?");
        boolean petFriendly = Leer.leerBoolean(read, "Pet friendly?");

        if(tipoPropiedad.equals("Casa")) {
            boolean parrilla = Leer.leerBoolean(read, "Posee parrilla?");
            boolean patio = Leer.leerBoolean(read, "Posee patio?");
            Casa casa = new Casa(anfitrionId, ubicacion, precio, habitaciones, banios,
                    maxPersonas, parking, petFriendly, parrilla, patio);
            gp.agregarCasa(casa);
            return;
        } else if(tipoPropiedad.equals("Departamento")) {
            int piso = Leer.leerInt(read, "Piso: ");
            boolean balcon = Leer.leerBoolean(read, "Posee balcón?");
            boolean zonaComun = Leer.leerBoolean(read, "Posee zona común?");
            Departamento depto = new Departamento(anfitrionId, "Departamento",
                ubicacion, precio, habitaciones, banios, maxPersonas, parking,
                petFriendly, piso, balcon, zonaComun);
            gp.agregarDepartamento(depto);
            return;
        } else {
            System.out.print("Check-In: ");
            String checkIn = read.nextLine();
            System.out.print("Check-Out: ");
            String checkOut = read.nextLine();
            int estrellas = Leer.leerInt(read, "Cantidad de estrellas: ");
            boolean piscina = Leer.leerBoolean(read, "Posee piscina? ");
            Hotel hotel = new Hotel(anfitrionId, ubicacion, precio,
                habitaciones, banios, maxPersonas, parking, petFriendly,
                checkIn, checkOut, estrellas, piscina);
            gp.agregarHotel(hotel);
            return;
        }
    }

    public static void eliminarPropiedad(Scanner read, GestorPropiedad gp) {
        int id = Leer.leerInt(read, "\nIngrese el ID de la Propiedad que desea eliminar: ");
        if (!gp.existePropiedad(id)) {
            System.out.println("No se encontró la propiedad con el ID proporcionado.");
        } else {
            gp.eliminarPropiedad(id);
        }
    }
    
    public static void actualizarPropiedad(Scanner read, GestorPropiedad gp, int id, String tipoDato, String dato) {
        if (tipoDato.equals("double")) {
            double nuevoPrecio = Leer.leerDouble(read, "Ingrese nuevo valor: ");
            gp.actualizarPropiedad(id, nuevoPrecio);
        } else if (tipoDato.equals("boolean")) {
            boolean nuevoValor = Leer.leerBoolean(read, "Ingrese el nuevo valor (s/n): ");
            gp.actualizarPropiedad(id, dato, nuevoValor);
        } else if (tipoDato.equals("int")) {
            int nuevoValor = Leer.leerEstrellas(read, "Ingrese el nuevo valor ");
            gp.actualizarPropiedad(id, nuevoValor);
        } else {
            System.out.print("Ingrese el nuevo dato: ");
            String nuevoValor = read.nextLine();
            gp.actualizarPropiedad(id, dato, nuevoValor);
        }
    }
}
