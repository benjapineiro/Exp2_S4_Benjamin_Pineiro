
package exp3_s8_benjamin_pineiro;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Exp3_S8_Benjamin_Pineiro {

    static int totalAsientos = 100;
    static int maxClientes = 200;

    static Cliente[] clientes = new Cliente[maxClientes];
    static Asiento[] asientos = new Asiento[totalAsientos];
    static List<Evento> listaEventos = new ArrayList<>();
    static List<Promocion> listaPromociones = new ArrayList<>();

    static int totalClientes = 0;
    static int siguienteIdCliente = 1;
    static int siguienteIdVenta = 1;
    static int siguienteIdEvento = 1;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Bienvenidos al sitio web de Teatro Moro");

        for (int i = 0; i < totalAsientos; i++) {
            asientos[i] = new Asiento(i + 1, (i < 15) ? "VIP" : (i < 50) ? "Preferencial" : "General");
        }

        Evento evento = new Evento(siguienteIdEvento++, "Concierto Teatro Moro", "Fecha 1");
        listaEventos.add(evento);

        listaPromociones.add(new Promocion("Descuento estudiante", 0.10));
        listaPromociones.add(new Promocion("Descuento tercera edad", 0.15));

        boolean continuar = true;
        while (continuar) {
            System.out.println("\nMenú principal:");
            System.out.println("1) Registrar cliente");
            System.out.println("2) Comprar entrada");
            System.out.println("3) Reservar asiento");

            if (!evento.listaVentas.isEmpty()) {
                System.out.println("4) Eliminar venta");
                System.out.println("5) Listar eventos");
                System.out.println("6) Salir");
            } else {
                System.out.println("4) Listar eventos");
                System.out.println("5) Salir");
            }

            System.out.print("Seleccione una opción: ");
            String opcionMenu = sc.nextLine();

            if (opcionMenu.equals("1")) {
                registrarCliente(sc);
            } else if (opcionMenu.equals("2")) {
                comprarEntrada(sc, evento);
            } else if (opcionMenu.equals("3")) {
                reservarAsiento(sc);
            } else if (opcionMenu.equals("4")) {
                if (!evento.listaVentas.isEmpty()) {
                    eliminarVenta(sc, evento);
                } else {
                    listarEventos();
                }
            } else if (opcionMenu.equals("5")) {
                if (!evento.listaVentas.isEmpty()) {
                    listarEventos();
                } else {
                    System.out.println("Gracias por visitar el sistema del Teatro Moro. ¡Hasta pronto!");
                    continuar = false;
                }
            } else if (opcionMenu.equals("6") && !evento.listaVentas.isEmpty()) {
                System.out.println("Gracias por visitar el sistema del Teatro Moro. ¡Hasta pronto!");
                continuar = false;
            } else {
                System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
        sc.close();
    }

    static void registrarCliente(Scanner sc) {
        if (totalClientes >= maxClientes) {
            System.out.println("No es posible registrar más clientes.");
            return;
        }
        System.out.print("Nombre del cliente: ");
        String nombreCliente = sc.nextLine();
        System.out.print("Edad del cliente: ");
        int edadCliente = Integer.parseInt(sc.nextLine());
        System.out.print("¿Es estudiante? (si/no): ");
        boolean esEstudiante = sc.nextLine().equalsIgnoreCase("si");

        clientes[totalClientes] = new Cliente(siguienteIdCliente++, nombreCliente, edadCliente, esEstudiante);
        totalClientes++;
        System.out.println("Cliente registrado exitosamente.");
    }

    static void comprarEntrada(Scanner sc, Evento evento) {
        System.out.print("Ingrese ID de cliente: ");
        int idCliente = Integer.parseInt(sc.nextLine());
        Cliente cliente = buscarCliente(idCliente);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        mostrarAsientosDisponibles();
        System.out.print("Seleccione ID de asiento: ");
        int idAsientoSeleccionado = Integer.parseInt(sc.nextLine());

        if (idAsientoSeleccionado < 1 || idAsientoSeleccionado > totalAsientos) {
            System.out.println("Número de asiento inválido.");
            return;
        }

        Asiento asientoSeleccionado = asientos[idAsientoSeleccionado - 1];
        if (asientoSeleccionado.vendido) {
            System.out.println("El asiento que ha seleccionado no está disponible.");
            return;
        }

        double precioBase = obtenerPrecio(asientoSeleccionado.tipo);
        double descuento = calcularDescuento(cliente, precioBase);
        double precioFinal = precioBase - descuento;

        System.out.println("Precio final: " + precioFinal);
        System.out.print("¿Confirmar compra? (si/no): ");
        if (sc.nextLine().equalsIgnoreCase("si")) {
            asientoSeleccionado.vendido = true;
            Venta nuevaVenta = new Venta(siguienteIdVenta++, idCliente, idAsientoSeleccionado, precioFinal);
            evento.listaVentas.add(nuevaVenta);
            System.out.println("Venta realizada con éxito. ID Venta: " + nuevaVenta.idVenta);
        } else {
            System.out.println("Compra cancelada.");
        }
    }

    static void reservarAsiento(Scanner sc) {
        mostrarAsientosDisponibles();
        System.out.print("Seleccione ID de asiento para reservar: ");
        int idAsientoSeleccionado = Integer.parseInt(sc.nextLine());

        if (idAsientoSeleccionado < 1 || idAsientoSeleccionado > totalAsientos) {
            System.out.println("Número de asiento inválido.");
            return;
        }

        Asiento asientoSeleccionado = asientos[idAsientoSeleccionado - 1];
        if (asientoSeleccionado.reservado || asientoSeleccionado.vendido) {
            System.out.println("El asiento que ha seleccionado no está disponible.");
            return;
        }

        asientoSeleccionado.reservado = true;
        System.out.println("Su asiento " + idAsientoSeleccionado + " ha sido reservado.");
    }

    static void eliminarVenta(Scanner sc, Evento evento) {
        System.out.print("Ingrese ID de venta a eliminar: ");
        int idVentaEliminar = Integer.parseInt(sc.nextLine());

        Venta ventaAEliminar = null;
        for (Venta venta : evento.listaVentas) {
            if (venta.idVenta == idVentaEliminar) {
                ventaAEliminar = venta;
                break;
            }
        }

        if (ventaAEliminar == null) {
            System.out.println("Venta no encontrada.");
            return;
        }

        Asiento asiento = asientos[ventaAEliminar.idAsiento - 1];
        asiento.vendido = false;

        evento.listaVentas.remove(ventaAEliminar);
        System.out.println("Venta eliminada exitosamente.");
    }

    static void listarEventos() {
        for (Evento evento : listaEventos) {
            System.out.println("Evento ID: " + evento.idEvento + " - " + evento.nombreEvento + " - " + evento.fechaEvento);
            System.out.println("Ventas realizadas: " + evento.listaVentas.size());
        }
    }

    static Cliente buscarCliente(int idCliente) {
        for (int i = 0; i < totalClientes; i++) {
            if (clientes[i].idCliente == idCliente) {
                return clientes[i];
            }
        }
        return null;
    }

    static void mostrarAsientosDisponibles() {
        System.out.println("\nSecciones de asientos disponibles:");
        System.out.println("Asientos 1 a 15 - VIP");
        System.out.println("Asientos 16 a 50 - Preferencial");
        System.out.println("Asientos 51 a 100 - General");
    }

    static double obtenerPrecio(String tipo) {
        switch (tipo) {
            case "VIP":
                return 20000;
            case "Preferencial":
                return 17000;
            case "General":
                return 15000;
            default:
                return 0;
        }
    }

    static double calcularDescuento(Cliente cliente, double precioBase) {
        double descuento = 0.0;
        if (cliente.esEstudiante) {
            descuento += precioBase * 0.10;
        }
        if (cliente.edad >= 60) {
            descuento += precioBase * 0.15;
        }
        return descuento;
    }
}

class Cliente {
    int idCliente;
    String nombre;
    int edad;
    boolean esEstudiante;

    Cliente(int idCliente, String nombre, int edad, boolean esEstudiante) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.edad = edad;
        this.esEstudiante = esEstudiante;
    }
}

class Asiento {
    int idAsiento;
    String tipo;
    boolean vendido;
    boolean reservado;

    Asiento(int idAsiento, String tipo) {
        this.idAsiento = idAsiento;
        this.tipo = tipo;
        this.vendido = false;
        this.reservado = false;
    }
}

class Venta {
    int idVenta;
    int idCliente;
    int idAsiento;
    double precioFinal;

    Venta(int idVenta, int idCliente, int idAsiento, double precioFinal) {
        this.idVenta = idVenta;
        this.idCliente = idCliente;
        this.idAsiento = idAsiento;
        this.precioFinal = precioFinal;
    }
}

class Evento {
    int idEvento;
    String nombreEvento;
    String fechaEvento;
    List<Venta> listaVentas;

    Evento(int idEvento, String nombreEvento, String fechaEvento) {
        this.idEvento = idEvento;
        this.nombreEvento = nombreEvento;
        this.fechaEvento = fechaEvento;
        this.listaVentas = new ArrayList<>();
    }
}

class Promocion {
    String descripcion;
    double descuento;

    Promocion(String descripcion, double descuento) {
        this.descripcion = descripcion;
        this.descuento = descuento;
    }
}
