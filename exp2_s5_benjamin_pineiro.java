
package exp2_s5_benjamin_pineiro;

import java.util.Scanner;

public class exp2_s5_benjamin_pineiro {

    static final int MAX_ENTRADAS = 500;
    static final int CAPACIDAD_SALA = 200;

    static int[] numerosEntradas = new int[MAX_ENTRADAS];
    static String[] ubicacionesEntradas = new String[MAX_ENTRADAS];
    static int[] edadesEntradas = new int[MAX_ENTRADAS];
    static boolean[] esEstudianteEntradas = new boolean[MAX_ENTRADAS];
    static double[] preciosFinalesEntradas = new double[MAX_ENTRADAS];

    static int cantidadEntradasVendidas = 0;
    static double ingresosTotales = 0.0;
    static int siguienteNumeroEntrada = 1;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("==========================================");
        System.out.println("Bienvenidos al sitio web de Teatro Moro");
        System.out.println("==========================================");

        boolean continuar = true;
        while (continuar) {
            System.out.println("\n¿Qué acción desea ejecutar?");
            System.out.println("1. Comprar entrada");
            System.out.println("2. Ver promociones");
            System.out.println("3. Buscar entrada (por número, ubicación o tipo)");
            System.out.println("4. Eliminar entrada (por número)");
            System.out.println("5. Ver estadísticas");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            String opcion = sc.nextLine().trim();

            switch (opcion) {
                case "1":
                    comprarEntrada(sc);
                    pausa(sc);
                    break;
                case "2":
                    mostrarPromociones();
                    pausa(sc);
                    break;
                case "3":
                    buscarEntrada(sc);
                    pausa(sc);
                    break;
                case "4":
                    eliminarEntrada(sc);
                    pausa(sc);
                    break;
                case "5":
                    mostrarEstadisticas();
                    pausa(sc);
                    break;
                case "6":
                    System.out.println("Gracias por visitar Teatro Moro. ¡Hasta pronto!");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
                    pausa(sc);
            }
        }

        sc.close();
    }

    static void comprarEntrada(Scanner sc) {
        if (cantidadEntradasVendidas >= CAPACIDAD_SALA) {
            System.out.println("No quedan entradas disponibles (capacidad alcanzada).");
            return;
        }

        System.out.println("\nUbicaciones disponibles:");
        System.out.println("A - VIP ($35000)");
        System.out.println("B - Platea baja ($25000)");
        System.out.println("C - Platea alta ($15000)");
        System.out.println("D - Palcos ($11000)");
        String entradaUbicacion = leerLinea(sc, "Seleccione la ubicación (A/B/C/D o nombre): ");

        String ubicacionFinal = interpretarUbicacion(entradaUbicacion);
        if (ubicacionFinal == null) {
            System.out.println("Ubicación inválida. Operación cancelada.");
            return;
        }

        double precioBase = precioPorUbicacion(ubicacionFinal);

        int edadCliente = leerEnteroValido(sc, "Ingrese su edad: ");

        boolean esTerceraEdad = edadCliente >= 60;

        String respuestaEstudiante = leerLinea(sc, "¿Es estudiante? (si/no): ").toLowerCase();
        boolean esEstudiante = respuestaEstudiante.equals("si");

        double descuento = 0.0;
        if (esEstudiante) descuento += precioBase * 0.10;   // 10% descuento
        if (esTerceraEdad) descuento += precioBase * 0.15;  // 15% descuento
        double precioFinal = precioBase - descuento;

        numerosEntradas[cantidadEntradasVendidas] = siguienteNumeroEntrada++;
        ubicacionesEntradas[cantidadEntradasVendidas] = ubicacionFinal;
        edadesEntradas[cantidadEntradasVendidas] = edadCliente;
        esEstudianteEntradas[cantidadEntradasVendidas] = esEstudiante;
        preciosFinalesEntradas[cantidadEntradasVendidas] = precioFinal;

        cantidadEntradasVendidas++;
        ingresosTotales += precioFinal;

        System.out.println("\n--- Resumen de la compra ---");
        System.out.println("Número de entrada: " + numerosEntradas[cantidadEntradasVendidas - 1]);
        System.out.println("Ubicación: " + ubicacionFinal);
        System.out.println("Edad: " + edadCliente);
        System.out.println("Estudiante: " + (esEstudiante ? "Sí" : "No"));
        System.out.println("Precio base: $" + (int) precioBase);
        System.out.println("Descuento aplicado: $" + (int) descuento);
        System.out.println("Total a pagar: $" + (int) precioFinal);
    }

    static void mostrarPromociones() {
        System.out.println("\n--- Promociones disponibles ---");
        System.out.println("- 10% de descuento para estudiantes.");
        System.out.println("- 15% de descuento para personas de la tercera edad (60+).");
        System.out.println("- Si alguien cumple ambas condiciones, ambos descuentos se acumulan.");
    }

    static void buscarEntrada(Scanner sc) {
        if (cantidadEntradasVendidas == 0) {
            System.out.println("No hay entradas vendidas aún.");
            return;
        }

        System.out.println("\nBuscar por:");
        System.out.println("1. Número de entrada");
        System.out.println("2. Ubicación");
        System.out.println("3. Tipo (estudiante / tercera edad)");
        String opcionBusqueda = leerLinea(sc, "Seleccione (1/2/3): ");

        if (opcionBusqueda.equals("1")) {
            int numeroBuscado = leerEnteroValido(sc, "Ingrese número de entrada a buscar: ");
            boolean encontrado = false;
            for (int i = 0; i < cantidadEntradasVendidas; i++) {
                if (numerosEntradas[i] == numeroBuscado) {
                    imprimirEntrada(i);
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) System.out.println("No se encontró la entrada con número " + numeroBuscado + ".");
        } else if (opcionBusqueda.equals("2")) {
            String ubicacionBuscada = leerLinea(sc, "Ingrese la ubicación a buscar (VIP, Platea baja, Platea alta, Palcos): ");
            String ubicacionNormalizada = interpretarUbicacion(ubicacionBuscada);
            if (ubicacionNormalizada == null) {
                System.out.println("Ubicación inválida para búsqueda.");
                return;
            }
            boolean encontrado = false;
            for (int i = 0; i < cantidadEntradasVendidas; i++) {
                if (ubicacionesEntradas[i].equalsIgnoreCase(ubicacionNormalizada)) {
                    imprimirEntrada(i);
                    encontrado = true;
                }
            }
            if (!encontrado) System.out.println("No se encontraron entradas para la ubicación: " + ubicacionNormalizada);
        } else if (opcionBusqueda.equals("3")) {
            String tipo = leerLinea(sc, "Buscar por 'estudiante' o 'tercera' (tercera edad): ").toLowerCase();
            boolean encontrado = false;
            if (tipo.equals("estudiante")) {
                for (int i = 0; i < cantidadEntradasVendidas; i++) {
                    if (esEstudianteEntradas[i]) {
                        imprimirEntrada(i);
                        encontrado = true;
                    }
                }
                if (!encontrado) System.out.println("No se encontraron entradas vendidas a estudiantes.");
            } else if (tipo.startsWith("tercera")) {
                for (int i = 0; i < cantidadEntradasVendidas; i++) {
                    if (edadesEntradas[i] >= 60) {
                        imprimirEntrada(i);
                        encontrado = true;
                    }
                }
                if (!encontrado) System.out.println("No se encontraron entradas vendidas a tercera edad.");
            } else {
                System.out.println("Tipo inválido para búsqueda.");
            }
        } else {
            System.out.println("Opción inválida de búsqueda.");
        }
    }

    static void eliminarEntrada(Scanner sc) {
        if (cantidadEntradasVendidas == 0) {
            System.out.println("No hay entradas para eliminar.");
            return;
        }

        int numeroEliminar = leerEnteroValido(sc, "Ingrese el número de la entrada a eliminar: ");
        int indice = -1;
        for (int i = 0; i < cantidadEntradasVendidas; i++) {
            if (numerosEntradas[i] == numeroEliminar) {
                indice = i;
                break;
            }
        }
        if (indice == -1) {
            System.out.println("No se encontró la entrada con número " + numeroEliminar + ".");
            return;
        }

        System.out.println("Entrada encontrada:");
        imprimirEntrada(indice);

        String confirmar = leerLinea(sc, "¿Confirma eliminación? (si/no): ").toLowerCase();
        if (!confirmar.equals("si")) {
            System.out.println("Eliminación cancelada.");
            return;
        }

        ingresosTotales -= preciosFinalesEntradas[indice];
        for (int i = indice; i < cantidadEntradasVendidas - 1; i++) {
            numerosEntradas[i] = numerosEntradas[i + 1];
            ubicacionesEntradas[i] = ubicacionesEntradas[i + 1];
            edadesEntradas[i] = edadesEntradas[i + 1];
            esEstudianteEntradas[i] = esEstudianteEntradas[i + 1];
            preciosFinalesEntradas[i] = preciosFinalesEntradas[i + 1];
        }
        cantidadEntradasVendidas--;
        System.out.println("Entrada número " + numeroEliminar + " eliminada correctamente.");
    }

    static void mostrarEstadisticas() {
        System.out.println("\n--- Estadísticas ---");
        System.out.println("Entradas vendidas: " + cantidadEntradasVendidas);
        System.out.println("Total ingresos: $" + (int) ingresosTotales);
        System.out.println("Próximo número de entrada disponible: " + siguienteNumeroEntrada);
    }

    static String interpretarUbicacion(String s) {
        if (s == null) return null;
        String t = s.trim().toLowerCase();
        switch (t) {
            case "a": case "vip": return "VIP";
            case "b": case "platea": case "platea baja": return "Platea baja";
            case "c": case "platea alta": return "Platea alta";
            case "d": case "palcos": return "Palcos";
            default: return null;
        }
    }

    static double precioPorUbicacion(String ubicacion) {
        switch (ubicacion) {
            case "VIP": return 35000;
            case "Platea baja": return 25000;
            case "Platea alta": return 15000;
            case "Palcos": return 11000;
            default: return 0;
        }
    }

    static int leerEnteroValido(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = sc.nextLine().trim();
            try {
                int val = Integer.parseInt(linea);
                if (val < 0) {
                    System.out.println("Ingrese un número no negativo.");
                    continue;
                }
                return val;
            } catch (NumberFormatException ex) {
                System.out.println("Entrada inválida. Ingrese un número entero.");
            }
        }
    }

    static String leerLinea(Scanner sc, String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    static void imprimirEntrada(int i) {
        System.out.println("\n--- Entrada ---");
        System.out.println("Número: " + numerosEntradas[i]);
        System.out.println("Ubicación: " + ubicacionesEntradas[i]);
        System.out.println("Edad: " + edadesEntradas[i]);
        System.out.println("Estudiante: " + (esEstudianteEntradas[i] ? "Sí" : "No"));
        System.out.println("Precio final: $" + (int) preciosFinalesEntradas[i]);
    }

    static void pausa(Scanner sc) {
        System.out.println("\nPresione ENTER para volver al menú principal");
        sc.nextLine();
    }
}
