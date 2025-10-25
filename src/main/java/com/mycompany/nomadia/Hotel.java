package com.mycompany.nomadia;

public class Hotel extends Propiedad {
    private String checkIn;
    private String checkOut;
    private int estrellas;
    private boolean piscina;

    public Hotel(int anfitrionId, String ubicacion, double precioNoche, int habitaciones, int banios, int maxPersonas, boolean parking, boolean petFriendly, String checkIn, String checkOut, int estrellas, boolean piscina) {
        super(anfitrionId, "Hotel", ubicacion, precioNoche, habitaciones, banios, maxPersonas, parking, petFriendly);
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.estrellas = estrellas;
        this.piscina = piscina;
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

    public int getEstrellas() {
        return estrellas;
    }
    
    public void setEstrellas(int estrellas) {
        this.estrellas = estrellas;
    }
    
    public boolean isPiscina() {
        return piscina;
    }

    public void setPiscina(boolean piscina) {
        this.piscina = piscina;
    }
    
}
