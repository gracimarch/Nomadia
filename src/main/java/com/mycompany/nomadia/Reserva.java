package com.mycompany.nomadia;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Reserva {
    private static final Set<Integer> idsExistentes = new HashSet<>();
    private int id;
    private int propiedadId;
    private int inquilinoId;
    private Date fechaInicio;
    private Date fechaFin;
    private double precioFinal;
    private int cantidadPersonas;
    private boolean pagado;

    public Reserva(int propiedadId, int inquilinoId, Date fechaInicio, Date fechaFin, double precioFinal, int cantidadPersonas, boolean pagado) {
        setId(generarIdUnico());
        this.propiedadId = propiedadId;
        this.inquilinoId = inquilinoId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.precioFinal = precioFinal;
        this.cantidadPersonas = cantidadPersonas;
        this.pagado = pagado;
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
