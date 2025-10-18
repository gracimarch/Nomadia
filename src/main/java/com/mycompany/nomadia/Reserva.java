package com.mycompany.nomadia;

import java.util.Date;

public class Reserva {
    private int propiedadId;
    private int inquilinoId;
    private Date fechaInicio;
    private Date fechaFin;
    private double precioFinal;
    private int cantidadPersonas;
    private boolean pagado;

    public Reserva(int propiedadId, int inquilinoId, Date fechaInicio, Date fechaFin, double precioFinal, int cantidadPersonas, boolean pagado) {
        this.propiedadId = propiedadId;
        this.inquilinoId = inquilinoId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.precioFinal = precioFinal;
        this.cantidadPersonas = cantidadPersonas;
        this.pagado = pagado;
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
    
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
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
    
    
}
