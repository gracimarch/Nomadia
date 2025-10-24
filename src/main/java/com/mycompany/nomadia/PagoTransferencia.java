package com.mycompany.nomadia;

public class PagoTransferencia implements Pago {

    @Override
    public void procesarPago(double monto) {
        System.out.println("Procesando transferencia bancaria: $" + monto);
        
        System.out.println("Transferencia confirmada.");
    }
}