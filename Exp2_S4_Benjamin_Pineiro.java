package Exp2_S4_Benjamin_Pineiro;

import java.util.Scanner;

public class Exp2_S4_Benjamin_Pineiro {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        boolean continuar = true; 
        String opcionMenu, ubicacion;
        int edad;
        double precioBase, precioFinal, descuento;
        boolean esEstudiante;

        System.out.println("Bienvenidos al sitio web de Teatro Moro");

        do {
            System.out.println("\n¿Qué acción desea ejecutar?");
            System.out.println("1. Comprar entrada");
            System.out.println("2. Salir");
            opcionMenu = sc.nextLine();

            if (opcionMenu.equals("1")) {
                System.out.println("\nPlano del Teatro:");
                System.out.println("[ Zona A = VIP ]");
                System.out.println("[ Zona B = Platea baja ]");
                System.out.println("[ Zona C = Platea alta ]");
                System.out.println("[ Zona D = Palcos ]");

                System.out.println("\nSeleccione la ubicación del asiento (A, B, C, D): ");
                ubicacion = sc.nextLine().toUpperCase();

                switch (ubicacion) {
                    case "A": precioBase = 35000; break; 
                    case "B": precioBase = 25000; break; 
                    case "C": precioBase = 15000; break; 
                    case "D": precioBase = 11000; break; 
                    default:
                        System.out.println("Ubicación inválida.");
                        continue; 
                }

                System.out.println("Ingrese su edad:");
                while (!sc.hasNextInt()) {
                    System.out.println("Edad inválida. Ingrese un número:");
                    sc.next();
                }
                edad = sc.nextInt();
                sc.nextLine(); 

                System.out.println("¿Es estudiante? (si/no)");
                String respuesta = sc.nextLine().toLowerCase();
                esEstudiante = respuesta.equals("si");

                descuento = 0;
                if (esEstudiante) {
                    descuento += precioBase * 0.10; 
                }
                if (edad >= 60) {
                    descuento += precioBase * 0.15; 
                }

                precioFinal = precioBase - descuento;

                System.out.println("\nResumen de la compra:");
                System.out.println("Ubicación: " + ubicacion);
                System.out.println("Precio base: $" + (int) precioBase);
                System.out.println("Descuento aplicado: $" + (int) descuento);
                System.out.println("Total a pagar: $" + (int) precioFinal);
                System.out.println("Gracias por su compra, disfrute la función.");

                System.out.println("\n¿Desea realizar otra compra? (si/no)");
                String resp = sc.nextLine().toLowerCase();
                continuar = resp.equals("si");

            } else if (opcionMenu.equals("2")) {
                continuar = false; 
            } else {
                System.out.println("Opción inválida. Intente nuevamente.");
            }

        } while (continuar);

        System.out.println("Gracias por visitar Teatro Moro. ¡Hasta pronto!");
        sc.close();
    }
}


    


