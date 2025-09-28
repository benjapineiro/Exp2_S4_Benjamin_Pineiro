
package exp3_s7_benjamin_pineiro;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Exp3_S7_Benjamin_Pineiro {

    static class Entrada {
        String sector;
        int asiento;
        boolean estudiante;
        int edad;
        double precioFinal;

        Entrada(String sector, int asiento, boolean estudiante, int edad, double precioFinal) {
            this.sector = sector;
            this.asiento = asiento;
            this.estudiante = estudiante;
            this.edad = edad;
            this.precioFinal = precioFinal;
        }
    }

    static List<Entrada> ventas = new ArrayList<>();
    static List<Integer> asientosVIP = new ArrayList<>();
    static List<Integer> asientosPreferencial = new ArrayList<>();
    static List<Integer> asientosGeneral = new ArrayList<>();

    public static void main(String[] args) {
        inicializarAsientos();
        Scanner sc = new Scanner(System.in);

        System.out.println("Bienvenidos al sitio web de Teatro Moro");

        boolean continuar = true;
        while (continuar) {
            System.out.println("\nSeleccione una opción:");
            System.out.println("1. Comprar entrada");
            System.out.println("2. Reservar asiento");
            System.out.println("3. Salir");
            System.out.print("Opción: ");
            String opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    comprarEntrada(sc);
                    break;
                case "2":
                    reservarAsiento(sc);
                    break;
                case "3":
                    System.out.println("Gracias por visitar el sitio web del Teatro Moro. ¡Hasta pronto!");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
        sc.close();
    }

    static void inicializarAsientos() {
        for (int i = 1; i <= 15; i++) {
            asientosVIP.add(i);
        }
        for (int i = 16; i <= 50; i++) {
            asientosPreferencial.add(i);
        }
        for (int i = 51; i <= 100; i++) {
            asientosGeneral.add(i);
        }
    }

    static void comprarEntrada(Scanner sc) {
        System.out.println("\nSeleccione sector:");
        System.out.println("1. VIP ($20000)");
        System.out.println("2. Preferencial ($17000)");
        System.out.println("3. General ($15000)");
        System.out.print("Sector: ");
        String sectorOpcion = sc.nextLine();

        String sector = "";
        double precioBase = 0;
        List<Integer> listaAsientos = null;

        switch (sectorOpcion) {
            case "1":
                sector = "VIP";
                precioBase = 20000;
                listaAsientos = asientosVIP;
                break;
            case "2":
                sector = "Preferencial";
                precioBase = 17000;
                listaAsientos = asientosPreferencial;
                break;
            case "3":
                sector = "General";
                precioBase = 15000;
                listaAsientos = asientosGeneral;
                break;
            default:
                System.out.println("Opción de sector inválida.");
                return;
        }

        if (listaAsientos.isEmpty()) {
            System.out.println("No quedan asientos disponibles en este sector.");
            return;
        }

        System.out.println("Asientos disponibles en " + sector + ": " + listaAsientos);
        System.out.print("Elija un asiento: ");
        int asiento = Integer.parseInt(sc.nextLine());
        if (!listaAsientos.contains(asiento)) {
            System.out.println("Asiento no disponible.");
            return;
        }

        System.out.print("¿Es estudiante? (si/no): ");
        boolean esEstudiante = sc.nextLine().equalsIgnoreCase("si");

        System.out.print("Ingrese su edad: ");
        int edad = Integer.parseInt(sc.nextLine());
        boolean esTerceraEdad = edad >= 60;

        double descuento = 0;
        if (esEstudiante) descuento += 0.10;
        if (esTerceraEdad) descuento += 0.15;

        double precioFinal = precioBase - (precioBase * descuento);

        System.out.println("\nResumen de su selección:");
        System.out.println("Sector: " + sector);
        System.out.println("Asiento: " + asiento);
        System.out.println("Precio final: $" + (int) precioFinal);
        System.out.print("¿Desea comprar este asiento? (si/no): ");
        if (sc.nextLine().equalsIgnoreCase("si")) {
            listaAsientos.remove(Integer.valueOf(asiento));
            ventas.add(new Entrada(sector, asiento, esEstudiante, edad, precioFinal));
            System.out.print("¿Desea imprimir boleta? (si/no): ");
            if (sc.nextLine().equalsIgnoreCase("si")) {
                System.out.println("Imprimiendo boleta...");
                System.out.println("Gracias por su compra en el Teatro Moro. ¡Disfrute el espectáculo!");
            } else {
                System.out.println("Gracias por su compra en el Teatro Moro. ¡Disfrute el espectáculo!");
            }
        } else {
            System.out.println("Compra cancelada. Gracias por visitar el Teatro Moro.");
        }
    }

    static void reservarAsiento(Scanner sc) {
        System.out.println("\nSeleccione sector para reservar:");
        System.out.println("1. VIP ($20000)");
        System.out.println("2. Preferencial ($17000)");
        System.out.println("3. General ($15000)");
        System.out.print("Sector: ");
        String sectorOpcion = sc.nextLine();

        String sector = "";
        List<Integer> listaAsientos = null;

        switch (sectorOpcion) {
            case "1":
                sector = "VIP";
                listaAsientos = asientosVIP;
                break;
            case "2":
                sector = "Preferencial";
                listaAsientos = asientosPreferencial;
                break;
            case "3":
                sector = "General";
                listaAsientos = asientosGeneral;
                break;
            default:
                System.out.println("Opción de sector inválida.");
                return;
        }

        if (listaAsientos.isEmpty()) {
            System.out.println("No quedan asientos disponibles en este sector.");
            return;
        }

        System.out.println("Asientos disponibles en " + sector + ": " + listaAsientos);
        System.out.print("Elija un asiento para reservar: ");
        int asiento = Integer.parseInt(sc.nextLine());
        if (!listaAsientos.contains(asiento)) {
            System.out.println("Ese asiento no está disponible para reservar.");
            return;
        }

        listaAsientos.remove(Integer.valueOf(asiento));
        System.out.println("Su asiento " + asiento + " ha sido reservado.");
        System.out.println("Gracias por visitar el sitio web del Teatro Moro.");
    }
}
