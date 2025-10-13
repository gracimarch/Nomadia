package com.mycompany.nomadia;

public class Casa extends Propiedad {
    private boolean parrilla;
    private boolean patio;

    public Casa(int anfitrionId, String ubicacion, double precioNoche, int habitaciones, int banios, int maxPersonas, boolean parking, boolean petFriendly, boolean parrilla, boolean patio) {
        super(anfitrionId, "Casa", ubicacion, precioNoche, habitaciones, banios, maxPersonas, parking, petFriendly);
        this.parrilla = parrilla;
        this.patio = patio;
    }

    public boolean isParrilla() {
        return parrilla;
    }

    public void setParrilla(boolean parrilla) {
        this.parrilla = parrilla;
    }

    public boolean isPatio() {
        return patio;
    }

    public void setPatio(boolean patio) {
        this.patio = patio;
    }
    
    
}
