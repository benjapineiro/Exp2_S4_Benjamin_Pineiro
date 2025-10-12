
package eft_s9_benjamin_pineiro;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class eft_s9_benjamin_pineiro {

    static final int TOTAL_ASIENTOS = 100;
    static Asiento[] asientos = new Asiento[TOTAL_ASIENTOS];
    static List<Venta> ventas = new ArrayList<>();
    static int siguienteIdVenta = 1;

    public static void main(String[] args) {
        inicializarAsientos();
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        System.out.println("Bienvenidos al sitio web de Teatro Moro");

        while (!salir) {
            System.out.println("\nMenú principal:");
            System.out.println("1) Comprar entrada");
            System.out.println("2) Reservar asiento");
            System.out.println("3) Imprimir boleta (por ID de venta)");
            System.out.println("4) Buscar venta (por ID)");
            System.out.println("5) Eliminar venta (por ID)");
            System.out.println("6) Listar ventas");
            System.out.println("7) Mostrar asientos disponibles por sección");
            System.out.println("8) Salir");
            System.out.print("Seleccione una opción: ");
            String opcion = sc.nextLine().trim();

            switch (opcion) {
                case "1":
                    comprarEntrada(sc);
                    break;
                case "2":
                    reservarAsiento(sc);
                    break;
                case "3":
                    imprimirBoleta(sc);
                    break;
                case "4":
                    buscarVenta(sc);
                    break;
                case "5":
                    eliminarVenta(sc);
                    break;
                case "6":
                    listarVentas();
                    break;
                case "7":
                    mostrarSecciones();
                    break;
                case "8":
                    System.out.println("Gracias por usar el sistema del Teatro Moro. ¡Hasta pronto!");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }

        sc.close();
    }

    static void inicializarAsientos() {
        for (int i = 1; i <= TOTAL_ASIENTOS; i++) {
            String seccion;
            if (i <= 15) seccion = "VIP";
            else if (i <= 30) seccion = "Palco";
            else if (i <= 55) seccion = "Platea baja";
            else if (i <= 80) seccion = "Platea alta";
            else seccion = "Galería";
            asientos[i - 1] = new Asiento(i, seccion);
        }
    }

    static void mostrarSecciones() {
        System.out.println("\nSecciones y rangos:");
        System.out.println("Asientos 1 a 15   - VIP");
        System.out.println("Asientos 16 a 30  - Palco");
        System.out.println("Asientos 31 a 55  - Platea baja");
        System.out.println("Asientos 56 a 80  - Platea alta");
        System.out.println("Asientos 81 a 100 - Galería");
    }

    static void comprarEntrada(Scanner sc) {
        System.out.println("\n--- Compra de entrada ---");
        Cliente cliente = leerDatosCliente(sc);

        mostrarSecciones();
        int idAsiento = leerEntero(sc, "Ingrese número de asiento que desea comprar: ");
        if (!validarRangoAsiento(idAsiento)) {
            System.out.println("Número de asiento inválido.");
            return;
        }

        Asiento asiento = asientos[idAsiento - 1];
        if (asiento.vendido) {
            System.out.println("El asiento que ha seleccionado no está disponible.");
            return;
        }
        if (asiento.reservado) {
            System.out.println("El asiento que ha seleccionado está reservado. Si pertenece a usted, convierta la reserva en compra o elija otro asiento.");
            return;
        }

        double precioBase = obtenerPrecioPorSeccion(asiento.seccion);
        double descuento = determinarDescuento(cliente, precioBase);
        double totalPagar = aplicarRedondeo(precioBase - descuento);

        System.out.println("Precio base: $" + (int)precioBase);
        System.out.println("Descuento aplicado: $" + (int)descuento);
        System.out.println("Total a pagar: $" + (int)totalPagar);

        String confirmar = leerLinea(sc, "¿Confirmar compra? (si/no): ");
        if (confirmar.equalsIgnoreCase("si")) {
            asiento.vendido = true;
            Venta venta = new Venta(siguienteIdVenta++, cliente, asiento, totalPagar);
            ventas.add(venta);
            System.out.println("Compra exitosa. ID de venta: " + venta.idVenta);
            imprimirResumenBoleta(venta);
        } else {
            System.out.println("Compra cancelada.");
        }
    }

    static void reservarAsiento(Scanner sc) {
        System.out.println("\n--- Reserva de asiento ---");
        mostrarSecciones();
        int idAsiento = leerEntero(sc, "Ingrese número de asiento que desea reservar: ");
        if (!validarRangoAsiento(idAsiento)) {
            System.out.println("Número de asiento inválido.");
            return;
        }

        Asiento asiento = asientos[idAsiento - 1];
        if (asiento.vendido) {
            System.out.println("El asiento que ha seleccionado no está disponible.");
            return;
        }
        if (asiento.reservado) {
            System.out.println("Ese asiento está reservado.");
            return;
        }

        String confirmar = leerLinea(sc, "¿Desea confirmar la reserva del asiento " + idAsiento + "? (si/no): ");
        if (confirmar.equalsIgnoreCase("si")) {
            asiento.reservado = true;
            System.out.println("Su asiento " + idAsiento + " ha sido reservado. Gracias por visitar el sitio web.");
        } else {
            System.out.println("Reserva cancelada.");
        }
    }

    static void imprimirBoleta(Scanner sc) {
        System.out.println("\n--- Imprimir boleta ---");
        int idVenta = leerEntero(sc, "Ingrese ID de venta: ");
        Venta venta = buscarVentaPorId(idVenta);
        if (venta == null) {
            System.out.println("Venta no encontrada.");
            return;
        }
        imprimirResumenBoleta(venta);
    }

    static void buscarVenta(Scanner sc) {
        System.out.println("\n--- Buscar venta por ID ---");
        int idVenta = leerEntero(sc, "Ingrese ID de venta: ");
        Venta venta = buscarVentaPorId(idVenta);
        if (venta == null) {
            System.out.println("Venta no encontrada.");
            return;
        }
        System.out.println("Venta encontrada:");
        imprimirResumenBoleta(venta);
    }

    static void eliminarVenta(Scanner sc) {
        System.out.println("\n--- Eliminar venta ---");
        int idVenta = leerEntero(sc, "Ingrese ID de venta a eliminar: ");
        Venta venta = buscarVentaPorId(idVenta);
        if (venta == null) {
            System.out.println("Venta no encontrada.");
            return;
        }
        venta.asiento.vendido = false;
        ventas.remove(venta);
        System.out.println("Venta eliminada correctamente.");
    }

    static void listarVentas() {
        System.out.println("\n--- Ventas registradas ---");
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }
        for (Venta v : ventas) {
            System.out.println("ID Venta: " + v.idVenta + " | Asiento: " + v.asiento.numero + " (" + v.asiento.seccion + ") | Cliente: " + v.cliente.nombre + " | Total: $" + (int)v.totalPagado);
        }
    }

    static Cliente leerDatosCliente(Scanner sc) {
        String nombre = leerLinea(sc, "Nombre del cliente: ");
        int edad = leerEntero(sc, "Edad del cliente: ");
        String genero = leerLinea(sc, "Género (M para mujer / H para hombre / O otro): ");
        String esEstudianteStr = leerLinea(sc, "¿Es estudiante? (si/no): ");
        boolean esEstudiante = esEstudianteStr.equalsIgnoreCase("si");
        boolean esMujer = genero.equalsIgnoreCase("M");
        Cliente c = new Cliente(nombre, edad, esMujer, esEstudiante);
        return c;
    }

    static double determinarDescuento(Cliente c, double precioBase) {
        double descuento = 0.0;
        if (c.edad <= 12) descuento += precioBase * 0.05;
        if (c.esMujer) descuento += precioBase * 0.07;
        if (c.esEstudiante) descuento += precioBase * 0.25;
        if (c.edad >= 60) descuento += precioBase * 0.30;
        return descuento;
    }

    static double obtenerPrecioPorSeccion(String seccion) {
        switch (seccion) {
            case "VIP": return 22000;
            case "Palco": return 20000;
            case "Platea baja": return 17000;
            case "Platea alta": return 15000;
            case "Galería": return 10000;
            default: return 0;
        }
    }

    static Venta buscarVentaPorId(int idVenta) {
        for (Venta v : ventas) {
            if (v.idVenta == idVenta) return v;
        }
        return null;
    }

    static boolean validarRangoAsiento(int id) {
        return id >= 1 && id <= TOTAL_ASIENTOS;
    }

    static void imprimirResumenBoleta(Venta v) {
        System.out.println("\n--- BOLETA ---");
        System.out.println("ID Venta: " + v.idVenta);
        System.out.println("Cliente: " + v.cliente.nombre);
        System.out.println("Edad: " + v.cliente.edad + (v.cliente.esEstudiante ? " (Estudiante)" : ""));
        System.out.println("Género: " + (v.cliente.esMujer ? "Femenino" : "Otro"));
        System.out.println("Asiento: " + v.asiento.numero + " (" + v.asiento.seccion + ")");
        System.out.println("Precio base: $" + (int)v.precioBase);
        System.out.println("Descuento aplicado: $" + (int)(v.precioBase - v.totalPagado));
        System.out.println("Total pagado: $" + (int)v.totalPagado);
        System.out.println("Gracias por su compra, disfrute la función.\n");
    }

    static int leerEntero(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = sc.nextLine().trim();
            try {
                int val = Integer.parseInt(linea);
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número entero.");
            }
        }
    }

    static String leerLinea(Scanner sc, String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    static double aplicarRedondeo(double valor) {
        return Math.round(valor);
    }

    static class Cliente {
        String nombre;
        int edad;
        boolean esMujer;
        boolean esEstudiante;

        Cliente(String nombre, int edad, boolean esMujer, boolean esEstudiante) {
            this.nombre = nombre;
            this.edad = edad;
            this.esMujer = esMujer;
            this.esEstudiante = esEstudiante;
        }
    }

    static class Asiento {
        int numero;
        String seccion;
        boolean vendido;
        boolean reservado;

        Asiento(int numero, String seccion) {
            this.numero = numero;
            this.seccion = seccion;
            this.vendido = false;
            this.reservado = false;
        }
    }

    static class Venta {
        int idVenta;
        Cliente cliente;
        Asiento asiento;
        double precioBase;
        double totalPagado;

        Venta(int idVenta, Cliente cliente, Asiento asiento, double totalPagado) {
            this.idVenta = idVenta;
            this.cliente = cliente;
            this.asiento = asiento;
            this.precioBase = obtenerPrecioPorSeccion(asiento.seccion);
            this.totalPagado = totalPagado;
        }
    }
}
