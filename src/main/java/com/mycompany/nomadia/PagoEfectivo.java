package com.mycompany.nomadia;

import java.util.Scanner;

public class PagoEfectivo implements Pago {

    @Override
    public void procesarPago(double monto, Scanner read) { 
        System.out.println("Procesando pago en efectivo: $" + monto);
        System.out.println("Pago en efectivo recibido.");
    }
}