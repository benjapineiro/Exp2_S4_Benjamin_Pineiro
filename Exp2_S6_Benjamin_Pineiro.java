
package exp2_s6_benjamin_pineiro;

import java.util.Scanner;

public class Exp2_S6_Benjamin_Pineiro {

    static final int capacidadSala = 50;
    static String[] estadoAsientos = new String[capacidadSala];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < capacidadSala; i++) {
            estadoAsientos[i] = "LIBRE";
        }

        System.out.println("  Bienvenidos al sitio web de Teatro Moro");

        boolean continuar = true;
        while (continuar) {
            System.out.println("\nMenú principal:");
            System.out.println("1. Comprar entrada");
            System.out.println("2. Reservar asiento");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            String opcion = sc.nextLine().trim();

            switch (opcion) {
                case "1":
                    comprarEntrada(sc);
                    continuar = false;
                    break;
                case "2":
                    reservarAsiento(sc);
                    continuar = false;
                    break;
                case "3":
                    System.out.println("Gracias por visitar el sitio web de Teatro Moro. ¡Hasta pronto!");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }

        sc.close();
    }

    static void comprarEntrada(Scanner sc) {
        int sector = elegirSector(sc);
        if (sector == 0) {
            System.out.println("Gracias por visitar el sitio web de Teatro Moro. ¡Hasta pronto!");
            return;
        }

        mostrarAsientosDisponibles(sector);
        System.out.print("Ingrese el número de asiento que desea comprar: ");
        int asiento = sc.nextInt();
        sc.nextLine();

        if (!esAsientoValidoEnSector(asiento, sector)) {
            System.out.println("Número de asiento inválido para este sector.");
            System.out.println("Gracias por visitar el sitio web de Teatro Moro. ¡Hasta pronto!");
            return;
        }

        if (!estadoAsientos[asiento - 1].equals("LIBRE")) {
            System.out.println("El asiento ya está reservado o vendido.");
            System.out.println("Gracias por visitar el sitio web de Teatro Moro. ¡Hasta pronto!");
            return;
        }

        double precio = obtenerPrecio(asiento);
        System.out.println("\nResumen de su elección:");
        System.out.println("Sector: " + nombreSector(sector));
        System.out.println("Asiento: " + asiento);
        System.out.println("Precio: $" + (int) precio);

        System.out.print("¿Desea confirmar la compra? (si/no): ");
        String confirmar = sc.nextLine().trim().toLowerCase();

        if (confirmar.equals("si")) {
            estadoAsientos[asiento - 1] = "VENDIDO";
            System.out.println("Compra confirmada del asiento " + asiento + ".");
            System.out.print("¿Desea imprimir boleta? (si/no): ");
            String boleta = sc.nextLine().trim().toLowerCase();
            if (boleta.equals("si")) {
                System.out.println("Imprimiendo boleta...");
            }
            System.out.println("Gracias por visitar el sitio web de Teatro Moro. ¡Hasta pronto!");
        } else {
            System.out.println("Compra cancelada.");
            System.out.println("Gracias por visitar el sitio web de Teatro Moro. ¡Hasta pronto!");
        }
    }

    static void reservarAsiento(Scanner sc) {
        int sector = elegirSector(sc);
        if (sector == 0) {
            System.out.println("Gracias por visitar el sitio web de Teatro Moro. ¡Hasta pronto!");
            return;
        }

        mostrarAsientosDisponibles(sector);
        System.out.print("Ingrese el número de asiento que desea reservar: ");
        int asiento = sc.nextInt();
        sc.nextLine();

        if (!esAsientoValidoEnSector(asiento, sector)) {
            System.out.println("Número de asiento inválido para este sector.");
            System.out.println("Gracias por visitar el sitio web de Teatro Moro. ¡Hasta pronto!");
            return;
        }

        if (!estadoAsientos[asiento - 1].equals("LIBRE")) {
            System.out.println("El asiento ya está reservado o vendido.");
            System.out.println("Gracias por visitar el sitio web de Teatro Moro. ¡Hasta pronto!");
            return;
        }

        estadoAsientos[asiento - 1] = "RESERVADO";
        System.out.println("Su asiento " + asiento + " ha sido reservado.");

        System.out.println("\n¿Qué desea hacer ahora?");
        System.out.println("1. Modificar la reserva");
        System.out.println("2. Salir");
        String opcion = sc.nextLine().trim();

        switch (opcion) {
            case "1":
                modificarReserva(sc);
                break;
            case "2":
                System.out.println("Gracias por visitar el sitio web de Teatro Moro. ¡Hasta pronto!");
                break;
            default:
                System.out.println("Opción inválida.");
                System.out.println("Gracias por visitar el sitio web de Teatro Moro. ¡Hasta pronto!");
        }
    }

    static void modificarReserva(Scanner sc) {
        System.out.print("\nIngrese el número de asiento reservado que desea cambiar: ");
        int asientoAnterior = sc.nextInt();
        sc.nextLine();

        if (asientoAnterior < 1 || asientoAnterior > capacidadSala) {
            System.out.println("Número de asiento inválido.");
            System.out.println("Gracias por visitar el sitio web de Teatro Moro. ¡Hasta pronto!");
            return;
        }

        if (!estadoAsientos[asientoAnterior - 1].equals("RESERVADO")) {
            System.out.println("Ese asiento no está reservado.");
            System.out.print("¿Desea reservarlo ahora? (si/no): ");
            String respuesta = sc.nextLine().trim().toLowerCase();
            if (respuesta.equals("si")) {
                estadoAsientos[asientoAnterior - 1] = "RESERVADO";
                System.out.println("Su asiento " + asientoAnterior + " ha sido reservado.");
                System.out.println("Gracias por visitar el sitio web de Teatro Moro. ¡Hasta pronto!");
            } else {
                System.out.print("¿Desea seguir en la página? (si/no): ");
                String seguir = sc.nextLine().trim().toLowerCase();
                if (seguir.equals("si")) {
                    main(new String[0]);
                } else {
                    System.out.println("Gracias por visitar el sitio web de Teatro Moro. ¡Hasta pronto!");
                }
            }
            return;
        }

        System.out.print("Ingrese el nuevo número de asiento: ");
        int asientoNuevo = sc.nextInt();
        sc.nextLine();

        if (asientoNuevo < 1 || asientoNuevo > capacidadSala || !estadoAsientos[asientoNuevo - 1].equals("LIBRE")) {
            System.out.println("El nuevo asiento no está disponible.");
            System.out.println("Gracias por visitar el sitio web de Teatro Moro. ¡Hasta pronto!");
            return;
        }

        estadoAsientos[asientoAnterior - 1] = "LIBRE";
        estadoAsientos[asientoNuevo - 1] = "RESERVADO";
        System.out.println("Su reserva ha sido cambiada al asiento " + asientoNuevo + ".");
        System.out.println("Gracias por visitar el sitio web de Teatro Moro. ¡Hasta pronto!");
    }

    static int elegirSector(Scanner sc) {
        System.out.println("\nSectores disponibles:");
        System.out.println("1. VIP (asientos 1 a 15) $20.000");
        System.out.println("2. Medio (asientos 16 a 35) $17.000");
        System.out.println("3. General (asientos 36 a 50) $15.000");
        System.out.println("0. Cancelar");
        System.out.print("Seleccione el sector: ");
        int sector = sc.nextInt();
        sc.nextLine();
        return sector;
    }

    static void mostrarAsientosDisponibles(int sector) {
        int inicio = 0;
        int fin = 0;
        if (sector == 1) { inicio = 1; fin = 15; }
        if (sector == 2) { inicio = 16; fin = 35; }
        if (sector == 3) { inicio = 36; fin = 50; }

        System.out.print("Asientos disponibles en este sector: ");
        for (int i = inicio; i <= fin; i++) {
            if (estadoAsientos[i - 1].equals("LIBRE")) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }

    static boolean esAsientoValidoEnSector(int asiento, int sector) {
        if (sector == 1 && asiento >= 1 && asiento <= 15) return true;
        if (sector == 2 && asiento >= 16 && asiento <= 35) return true;
        if (sector == 3 && asiento >= 36 && asiento <= 50) return true;
        return false;
    }

    static double obtenerPrecio(int asiento) {
        if (asiento >= 1 && asiento <= 15) {
            return 20000;
        } else if (asiento >= 16 && asiento <= 35) {
            return 17000;
        } else {
            return 15000;
        }
    }

    static String nombreSector(int sector) {
        if (sector == 1) return "VIP";
        if (sector == 2) return "Medio";
        return "General";
    }
}
