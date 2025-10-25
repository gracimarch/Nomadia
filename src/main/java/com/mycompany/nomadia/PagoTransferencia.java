package com.mycompany.nomadia;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class PagoTransferencia implements Pago {

    @Override
    public void procesarPago(double monto, Scanner read) { 
        System.out.println("Procesando transferencia: $" + monto);
        
        try {
            TimeUnit.MILLISECONDS.sleep(1500);

        } catch (InterruptedException e) {
            System.out.println("El proceso de pago fue interrumpido.");
            Thread.currentThread().interrupt();
        }
        
        System.out.println("Pago con transferencia recibido.");
    }
}