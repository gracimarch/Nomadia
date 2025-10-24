package com.mycompany.nomadia;

import java.util.Scanner;

public class PagoTarjeta implements Pago {
    @Override
    public void procesarPago(double monto) {
        Scanner scPago = new Scanner(System.in);
        int cuotas = 0;

        while (true) {
            System.out.print("Ingrese cantidad de cuotas (3 o 6): ");
            String line = scPago.nextLine().trim();
            try {
                cuotas = Integer.parseInt(line);
                if (cuotas == 3 || cuotas == 6) break;
                System.out.println("Cuotas inválidas. Sólo se permiten 3 o 6.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número (3 o 6).");
            }
        }

        double valorCuota = monto / cuotas;
        System.out.println("Procesando pago con tarjeta por: $" + String.format("%.2f", monto));
        System.out.println("Plan de pago: " + cuotas + " cuota(s) de $" + String.format("%.2f", valorCuota));

        System.out.println("Pago con tarjeta aprobado en " + cuotas + " cuota(s).");
    }
}