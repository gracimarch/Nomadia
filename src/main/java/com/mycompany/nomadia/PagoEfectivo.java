package com.mycompany.nomadia;

public class PagoEfectivo implements Pago {

    @Override
    public void procesarPago(double monto) {
        System.out.println("Procesando pago en efectivo: $" + monto);
        
        System.out.println("Pago en efectivo recibido.");
    }
}