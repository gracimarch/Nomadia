package com.mycompany.nomadia;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class PagoTarjeta implements Pago {
    
    @Override
    public void procesarPago(double monto, Scanner read) { 
        int cuotas = 0;

        while (true) {
            cuotas = Leer.leerInt(read, "Ingrese cantidad de cuotas (3 o 6): ");
            
            if (cuotas == 3 || cuotas == 6) break;
            System.out.println("Cuotas inválidas. Sólo se permiten 3 o 6.");
        }

        double montoConInteres = monto + (0.1 * monto);
        double valorCuota = montoConInteres / cuotas;
        System.out.println("Procesando pago con tarjeta por: $" + String.format("%.2f", montoConInteres));
        System.out.println("Plan de pago: " + cuotas + " cuotas de $" + String.format("%.2f", valorCuota));

        try {
            TimeUnit.MILLISECONDS.sleep(1500);

        } catch (InterruptedException e) {
            System.out.println("El proceso de pago fue interrumpido.");
            Thread.currentThread().interrupt();
        }

        System.out.println("Pago con tarjeta aprobado en " + cuotas + " cuotas.");
    }
}