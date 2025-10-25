package com.mycompany.nomadia;

import java.time.LocalDate;
import java.util.Scanner;

public class Leer {
    public static int leerInt(Scanner sc, String texto) {
        while (true) {
            System.out.print(texto);
            String line = sc.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número.");
            }
        }
    }

    public static double leerDouble(Scanner sc, String texto) {
        while (true) {
            System.out.print(texto);
            String line = sc.nextLine();
            try {
                return Double.parseDouble(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número (decimal permitido).");
            }
        }
    }

    public static boolean leerBoolean(Scanner sc, String texto) {
        while (true) {
            System.out.print(texto + " (s/n): ");
            String line = sc.nextLine().trim().toLowerCase();
            if (line.equals("s") || line.equals("si"))
                return true;
            if (line.equals("n") || line.equals("no"))
                return false;
            System.out.println("Entrada inválida. Responda 's' o 'n'.");
        }
    }

    public static LocalDate leerFecha(Scanner read, String texto) {
        LocalDate fecha = null;
        while (fecha == null) {
            System.out.print(texto);
            String line = read.nextLine().trim();
            try {
                fecha = LocalDate.parse(line);
            } catch (Exception e) {
                System.out.println("Formato inválido. Use YYYY-MM-DD.");
            }
        }
        return fecha;
    }

    public static int leerEstrellas(Scanner sc, String texto) {
        while (true) {
            System.out.print(texto + "(1-5): ");
            String line = sc.nextLine();
            try {
                int estrellas = Integer.parseInt(line.trim());

                if (estrellas >= 1 && estrellas <= 5) {
                    return estrellas;
                } else {
                    System.out.println("Puntuación inválida. Ingrese un número entre 1 y 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número (del 1 al 5).");
            }
        }
    }
}
