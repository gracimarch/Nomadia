package com.mycompany.nomadia;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reserva {
    private int propiedadId;
    private int inquilinoId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double precioFinal;
    private int cantidadPersonas;
    private boolean pagado;

    public Reserva(int propiedadId, int inquilinoId, LocalDate fechaInicio, LocalDate fechaFin, 
                   double precioNoche, int cantidadPersonas, boolean pagado) {
        
        this.propiedadId = propiedadId;
        this.inquilinoId = inquilinoId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cantidadPersonas = cantidadPersonas;
        this.pagado = pagado;
        this.calcularPrecio(precioNoche); 
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
    
    public double calcularPrecio(double precioNoche) { 
    
        long noches = ChronoUnit.DAYS.between(this.fechaInicio, this.fechaFin);
        this.precioFinal = precioNoche * noches; 
        System.out.println("Precio final calculado: $" + String.format("%.2f", this.precioFinal));
        
        return this.precioFinal;
    }
}