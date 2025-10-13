package com.mycompany.nomadia;

import java.util.ArrayList;

public class Hotel extends Propiedad {
    private String checkIn;
    private String checkOut;
    private ArrayList<String> servicios;

    public Hotel(int anfitrionId, String tipo, String ubicacion, double precioNoche, int habitaciones, int banios, int maxPersonas, boolean parking, boolean petFriendly, String checkIn, String checkOut, ArrayList<String> servicios) {
        super(anfitrionId, "Hotel", ubicacion, precioNoche, habitaciones, banios, maxPersonas, parking, petFriendly);
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.servicios = servicios;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public ArrayList<String> getServicios() {
        return servicios;
    }

    public void setServicios(ArrayList<String> servicios) {
        this.servicios = servicios;
    }
    
    
}
