package app;

import java.time.LocalDateTime;
import java.util.Scanner;

import excepciones.AlmacenamientoLlenoException;
import excepciones.CapacidadExcedidaException;
import excepciones.ClienteNoEncontradoException;
import excepciones.PedidoInvalidoException;
import modelo.Bicicleta;
import modelo.Camioneta;
import modelo.Citylink;
import modelo.Cliente;
import modelo.Moto;
import modelo.Pedido;
import modelo.PedidoFragil;
import modelo.PedidoNormal;
import modelo.PedidoUrgente;
import modelo.Repartidor;
import modelo.Vehiculo;

/**
 * Punto de entrada y presentacion en consola del sistema Citylink.
 * La clase organiza la navegacion y la interaccion con el usuario.
 */
public class Main {

    private static final Scanner TECLADO = new Scanner(System.in);
    private static final String VERDE = "\u001B[32m";
    private static final String RESET = "\u001B[0m";
    private static final String SEPARADOR =
            "============================================================";
    private static final String SUBSEPARADOR =
            "------------------------------------------------------------";

    private Main() {
    }

    public static void main(String[] args) {
        Citylink citylink = new Citylink();

        try {
            DatosIniciales.cargar(citylink);
            ejecutarMenuPrincipal(citylink);
        } catch (ClienteNoEncontradoException | PedidoInvalidoException
                | AlmacenamientoLlenoException e) {
            limpiarPantalla();
            mostrarEncabezado("ERROR DE INICIALIZACION");
            System.out.println("No se pudieron cargar los datos iniciales: " + e.getMessage());
            esperarContinuar();
        } finally {
            limpiarPantalla();
            mostrarEncabezado("SISTEMA FINALIZADO");
            System.out.println("Gracias por utilizar Citylink. Vuelve pronto :)");
            TECLADO.close();
        }
    }

    private static void ejecutarMenuPrincipal(Citylink citylink) {
        boolean continuar = true;

        while (continuar) {
            limpiarPantalla();
            mostrarEncabezado("CITYLINK");
            mostrarResumen(citylink);
            System.out.println(SEPARADOR);
            centrar("MENU PRINCIPAL");
            System.out.println(SEPARADOR);
            System.out.println();
            System.out.println("  [1] Gestión de clientes");
            System.out.println("  [2] Gestión de repartidores");
            System.out.println("  [3] Gestión de pedidos");
            System.out.println("  [4] Asignaciones");
            System.out.println("  [5] Consultas y reportes");
            System.out.println();
            System.out.println("  [0] Salir");
            System.out.println(SEPARADOR);

            int opcion = leerOpcion("Elegí una opción: ", 0, 5);
            switch (opcion) {
                case 1:
                    ejecutarMenuClientes(citylink);
                    break;
                case 2:
                    ejecutarMenuRepartidores(citylink);
                    break;
                case 3:
                    ejecutarMenuPedidos(citylink);
                    break;
                case 4:
                    ejecutarMenuAsignaciones(citylink);
                    break;
                case 5:
                    ejecutarMenuConsultas(citylink);
                    break;
                case 0:
                    continuar = false;
                    break;
                default:
                    break;
            }
        }
    }

    private static void ejecutarMenuClientes(Citylink citylink) {
        boolean volver = false;
        while (!volver) {
            limpiarPantalla();
            mostrarEncabezado("GESTIÓN DE CLIENTES");
            System.out.println();
            System.out.println("  [1] Registrar cliente.");
            System.out.println("  [2] Buscar cliente.");
            System.out.println("  [0] Volver al menu principal.");
            System.out.println();
            System.out.println(SEPARADOR);

            int opcion = leerOpcion("Elegí una opción: ", 0, 2);
            switch (opcion) {
                case 1:
                    ejecutarOperacion(() -> registrarCliente(citylink));
                    break;
                case 2:
                    ejecutarOperacion(() -> buscarCliente(citylink));
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    break;
            }
        }
    }

    private static void ejecutarMenuRepartidores(Citylink citylink) {
        boolean volver = false;
        while (!volver) {
            limpiarPantalla();
            mostrarEncabezado("GESTIÓN DE REPARTIDORES");
            System.out.println();
            System.out.println("  [1] Registrar repartidor.");
            System.out.println("  [2] Buscar repartidor.");
            System.out.println("  [0] Volver al menu principal.");
            System.out.println();
            System.out.println(SEPARADOR);

            int opcion = leerOpcion("Elegí una opción: ", 0, 2);
            switch (opcion) {
                case 1:
                    ejecutarOperacion(() -> registrarRepartidor(citylink));
                    break;
                case 2:
                    ejecutarOperacion(() -> buscarRepartidor(citylink));
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    break;
            }
        }
    }

    private static void ejecutarMenuPedidos(Citylink citylink) {
        boolean volver = false;
        while (!volver) {
            limpiarPantalla();
            mostrarEncabezado("GESTIÓN DE PEDIDOS");
            System.out.println();
            System.out.println("  [1] Registrar pedido.");
            System.out.println("  [2] Listar pedidos con costo y tiempo.");
            System.out.println("  [0] Volver al menu principal.");
            System.out.println();
            System.out.println(SEPARADOR);

            int opcion = leerOpcion("Elegí una opción: ", 0, 2);
            switch (opcion) {
                case 1:
                    ejecutarOperacion(() -> registrarPedido(citylink));
                    break;
                case 2:
                    ejecutarOperacion(() -> listarPedidos(citylink));
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    break;
            }
        }
    }

    private static void ejecutarMenuAsignaciones(Citylink citylink) {
        boolean volver = false;
        while (!volver) {
            limpiarPantalla();
            mostrarEncabezado("ASIGNACIONES");
            System.out.println();
            System.out.println("  [1] Asignar pedido a repartidor.");
            System.out.println("  [2] Cambiar estado de pedido.");
            System.out.println("  [0] Volver al menu principal.");
            System.out.println();
            System.out.println(SEPARADOR);

            int opcion = leerOpcion("Elegí una opción: ", 0, 2);
            switch (opcion) {
                case 1:
                    ejecutarOperacion(() -> asignarPedido(citylink));
                    break;
                case 2:
                    ejecutarOperacion(() -> cambiarEstado(citylink));
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    break;
            }
        }
    }

    private static void ejecutarMenuConsultas(Citylink citylink) {
        boolean volver = false;
        while (!volver) {
            limpiarPantalla();
            mostrarEncabezado("CONSULTAS Y REPORTES");
            System.out.println();
            System.out.println("  [1] Consultar estado de pedido o repartidor.");
            System.out.println("  [2] Consultar importes y comisiones.");
            System.out.println("  [0] Volver al menu principal.");
            System.out.println();
            System.out.println(SEPARADOR);

            int opcion = leerOpcion("Elegí una opción: ", 0, 2);
            switch (opcion) {
                case 1:
                    ejecutarOperacion(() -> consultarEstado(citylink));
                    break;
                case 2:
                    ejecutarOperacion(() -> consultarImportes(citylink));
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    break;
            }
        }
    }

    @FunctionalInterface
    private interface Operacion {
        void ejecutar() throws Exception;
    }

    private static void ejecutarOperacion(Operacion operacion) {
        limpiarPantalla();
        try {
            operacion.ejecutar();
        } catch (ClienteNoEncontradoException | PedidoInvalidoException
                | CapacidadExcedidaException e) {
            mostrarRechazo(e.getMessage());
        } catch (AlmacenamientoLlenoException e) {
            mostrarRechazo(e.getMessage());
        } catch (EntradaInvalidaException e) {
            // El mensaje y la pausa ya se mostraron al detectar la entrada invalida.
            return;
        } catch (RuntimeException e) {
            mostrarRechazo(e.getMessage());
        } catch (Exception e) {
            mostrarRechazo(e.getMessage());
        }
        esperarContinuar();
    }

    private static void registrarCliente(Citylink citylink)
            throws AlmacenamientoLlenoException {
        mostrarEncabezado("REGISTRAR CLIENTE");
        String nombre = leerTexto("Nombre: ");
        String telefono = leerTexto("Teléfono: ");
        int codigo = leerEntero("Código: ");
        String zona = leerTexto("Zona: ");

        citylink.agregarCliente(new Cliente(nombre, telefono, codigo, zona));
        mostrarExito("CLIENTE REGISTRADO CON ÉXITO");
    }

    private static void buscarCliente(Citylink citylink)
            throws ClienteNoEncontradoException {
        mostrarEncabezado("BUSCAR CLIENTE");
        System.out.println("Criterio de búsqueda:");
        System.out.println("  [1] Por código");
        System.out.println("  [2] Por nombre");
        System.out.println(SEPARADOR);
        int criterio = leerOpcion("Elegí una opción: ", 1, 2);

        Cliente encontrado;
        if (criterio == 1) {
            int codigo = leerEntero("Código: ");
            encontrado = citylink.buscarCliente(codigo);
        } else {
            String nombre = leerTexto("Nombre: ");
            encontrado = null;
            String consulta = normalizar(nombre);
            for (Cliente cliente : citylink.getClientes()) {
                if (normalizar(cliente.getNombre()).contains(consulta)) {
                    encontrado = cliente;
                    break;
                }
            }
            if (encontrado == null) {
                throw new ClienteNoEncontradoException(
                        "No se encontro un cliente cuyo nombre contenga: " + nombre);
            }
        }

        System.out.println(SEPARADOR);
        centrar("RESULTADO");
        System.out.println(SEPARADOR);
        imprimirCliente(encontrado);
        System.out.println(SEPARADOR);
    }

    private static void registrarRepartidor(Citylink citylink)
            throws AlmacenamientoLlenoException {
        mostrarEncabezado("REGISTRAR REPARTIDOR");
        String nombre = leerTexto("Nombre: ");
        String legajo = leerTexto("Legajo: ");

        System.out.println(SEPARADOR);
        System.out.println("Tipo de vehículo:");
        System.out.println();
        System.out.println("  [1] Bicicleta");
        System.out.println("  [2] Moto");
        System.out.println("  [3] Camioneta");
        System.out.println(SEPARADOR);
        int tipo = leerOpcion("Seleccioná el tipo: ", 1, 3);
        String patente = leerTexto("Patente: ");

        Vehiculo vehículo;
        switch (tipo) {
            case 1:
                vehículo = new Bicicleta(patente);
                break;
            case 2:
                vehículo = new Moto(patente);
                break;
            case 3:
                vehículo = new Camioneta(patente);
                break;
            default:
                throw new IllegalArgumentException("Tipo de vehículo invalido.");
        }

        citylink.agregarRepartidor(new Repartidor(nombre, legajo, vehículo));
        mostrarExito("REPARTIDOR REGISTRADO CON ÉXITO");
    }

    private static void buscarRepartidor(Citylink citylink) {
        mostrarEncabezado("BUSCAR REPARTIDOR");
        System.out.println("Criterio de búsqueda:");
        System.out.println("  [1] Por legajo.");
        System.out.println("  [2] Por nombre.");
        System.out.println(SEPARADOR);
        int criterio = leerOpcion("Elegí una opción: ", 1, 2);

        Repartidor encontrado = null;
        if (criterio == 1) {
            String legajo = leerTexto("Legajo: ");
            for (Repartidor repartidor : citylink.getRepartidores()) {
                if (repartidor.getLegajo().equalsIgnoreCase(legajo)) {
                    encontrado = repartidor;
                    break;
                }
            }
            if (encontrado == null) {
                mostrarErrorEntrada();
                throw new EntradaInvalidaException();
            }
        } else {
            String nombre = leerTexto("Nombre: ");
            String consulta = normalizar(nombre);
            for (Repartidor repartidor : citylink.getRepartidores()) {
                if (normalizar(repartidor.getNombre()).contains(consulta)) {
                    encontrado = repartidor;
                    break;
                }
            }
            if (encontrado == null) {
                throw new IllegalArgumentException(
                        "No se encontro un repartidor cuyo nombre contenga: " + nombre);
            }
        }

        System.out.println(SEPARADOR);
        centrar("RESULTADO");
        System.out.println(SEPARADOR);
        imprimirRepartidor(encontrado);
        System.out.println(SEPARADOR);
    }

    private static void registrarPedido(Citylink citylink)
            throws ClienteNoEncontradoException, PedidoInvalidoException,
            AlmacenamientoLlenoException {
        mostrarEncabezado("REGISTRAR PEDIDO");
        int codigo = leerEntero("Código del pedido: ");
        int codigoCliente = leerEntero("Código del cliente: ");
        Cliente cliente = citylink.buscarCliente(codigoCliente);
        String destino = leerTexto("Destino: ");
        double kg = leerDecimal("Peso en kg: ");
        double km = leerDecimal("Distancia en km: ");

        System.out.println(SEPARADOR);
        System.out.println("Tipo de pedido:");
        System.out.println();
        System.out.println("  [1] Normal");
        System.out.println("  [2] Urgente");
        System.out.println("  [3] Frágil");
        System.out.println(SEPARADOR);
        int tipo = leerOpcion("Seleccioná el tipo: ", 1, 3);

        LocalDateTime fecha = LocalDateTime.now();
        Pedido pedido;
        switch (tipo) {
            case 1:
                pedido = new PedidoNormal(codigo, cliente, destino, kg, km, fecha);
                break;
            case 2:
                pedido = new PedidoUrgente(codigo, cliente, destino, kg, km, fecha);
                break;
            case 3:
                pedido = new PedidoFragil(codigo, cliente, destino, kg, km, fecha);
                break;
            default:
                throw new IllegalArgumentException("Tipo de pedido invalido.");
        }

        citylink.agregarPedido(pedido);
        mostrarExito("PEDIDO REGISTRADO CON ÉXITO");
    }

    private static void listarPedidos(Citylink citylink) {
        mostrarEncabezado("LISTADO DE PEDIDOS");
        Pedido[] pedidos = citylink.getPedidos();
        if (pedidos.length == 0) {
            System.out.println("No hay pedidos registrados.");
            return;
        }

        for (int i = 0; i < pedidos.length; i++) {
            Pedido pedido = pedidos[i];
            System.out.println();
            System.out.println("PEDIDO " + pedido.getCodigo());
            System.out.println("Pedido: " + pedido.getCodigo());
            imprimirCliente(pedido.getCliente());
            System.out.println("Destino: " + pedido.getDestino());
            System.out.println("Peso: " + pedido.getKg() + " kg");
            System.out.println("Distancia: " + pedido.getKm() + " km");
            System.out.println("Fecha y hora de ingreso: " + pedido.getFechaHora());
            System.out.println("Estado: " + pedido.getEstado());

            if (pedido.getVehiculoAsignado() == null) {
                System.out.println("Costo y tiempo: pendientes de asignación.");
            } else {
                System.out.printf("Costo: $%.2f%n",
                        citylink.calcularCostoPedido(pedido.getCodigo()));
                System.out.printf("Tiempo estimado: %.2f minutos%n",
                        citylink.calcularTiempoEstimadoPedido(pedido.getCodigo()));
            }

            if (i < pedidos.length - 1) {
                System.out.println(SUBSEPARADOR);
            }
        }
        System.out.println(SEPARADOR);
    }

    private static void asignarPedido(Citylink citylink)
            throws CapacidadExcedidaException {
        mostrarEncabezado("ASIGNAR PEDIDO");
        int codigo = leerEntero("Código del pedido: ");
        String legajo = leerTexto("Legajo del repartidor: ");
        citylink.asignarPedido(codigo, legajo);
        mostrarExito("PEDIDO ASIGNADO CON ÉXITO");
    }

    private static void cambiarEstado(Citylink citylink) {
        mostrarEncabezado("CAMBIAR ESTADO DEL PEDIDO");
        int codigo = leerEntero("Código del pedido: ");
        System.out.println(SEPARADOR);
        System.out.println("Nuevo estado:");
        System.out.println();
        System.out.println("  [1] EN_CAMINO");
        System.out.println("  [2] CANCELADO");
        System.out.println("  [3] ENTREGADO");
        System.out.println(SEPARADOR);
        int opcion = leerOpcion("Seleccioná el estado: ", 1, 3);

        if (opcion == 1) {
            citylink.cambiarEstadoPedido(codigo, "EN_CAMINO");
        } else if (opcion == 2) {
            citylink.cambiarEstadoPedido(codigo, "CANCELADO");
        } else {
            String legajo = leerTexto("Legajo del repartidor responsable: ");
            citylink.registrarEntrega(codigo, legajo);
        }
        mostrarExito("ESTADO ACTUALIZADO CON ÉXITO");
    }

    private static void consultarEstado(Citylink citylink) {
        mostrarEncabezado("CONSULTAR ESTADO");
        System.out.println("Consultar:");
        System.out.println("  [1] Pedido");
        System.out.println("  [2] Repartidor");
        System.out.println(SEPARADOR);
        int opcion = leerOpcion("Seleccioná una opción: ", 1, 2);

        if (opcion == 1) {
            int codigo = leerEntero("Código del pedido: ");
            System.out.println(SEPARADOR);
            System.out.println("Estado: " + citylink.consultarEstadoPedido(codigo));
        } else {
            String legajo = leerTexto("Legajo del repartidor: ");
            System.out.println(SEPARADOR);
            System.out.println("Estado: " + citylink.consultarEstadoRepartidor(legajo));
        }
        System.out.println(SEPARADOR);
    }

    private static void consultarImportes(Citylink citylink) {
        mostrarEncabezado("IMPORTES Y COMISIONES");
        System.out.println();
        System.out.println("Importe total cotizado de pedidos asignados:");
        System.out.printf("$%.2f%n", citylink.calcularImporteTotalPedidos());
        System.out.println();
        System.out.println("Comisiones totales:");
        System.out.printf("$%.2f%n", citylink.calcularComisionesTotales());
        System.out.println(SEPARADOR);
        centrar("COMISION POR REPARTIDOR");
        System.out.println(SEPARADOR);

        for (Repartidor repartidor : citylink.getRepartidores()) {
            System.out.printf("%s (%s): $%.2f%n",
                    repartidor.getNombre(), repartidor.getLegajo(),
                    citylink.calcularComisionRepartidor(repartidor.getLegajo()));
        }
        System.out.println(SEPARADOR);
        System.out.println("Pedidos sin asignar: " + citylink.contarPedidosSinAsignar());
    }

    private static void mostrarResumen(Citylink citylink) {
        System.out.println("RESUMEN DEL SISTEMA");
        System.out.printf("Clientes: %d     Pedidos: %d     Repartidores: %d%n",
                citylink.getCantidadClientes(),
                citylink.getCantidadPedidos(),
                citylink.getCantidadRepartidores());
    }

    private static void mostrarEncabezado(String titulo) {
        System.out.println(SEPARADOR);
        centrar(titulo);
        System.out.println(SEPARADOR);
    }

    private static void centrar(String texto) {
        int espacios = Math.max(0, (SEPARADOR.length() - texto.length()) / 2);
        System.out.println(" ".repeat(espacios) + texto);
    }

    private static void imprimirCliente(Cliente cliente) {
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Código: " + cliente.getCodigo());
        System.out.println("Teléfono: " + cliente.getTelefono());
        System.out.println("Zona: " + cliente.getZona());
    }

    private static void imprimirRepartidor(Repartidor repartidor) {
        Vehiculo vehículo = repartidor.getVehiculo();
        System.out.println("Repartidor: " + repartidor.getNombre());
        System.out.println("Legajo: " + repartidor.getLegajo());
        System.out.println("Vehículo: " + nombreVehiculo(vehículo));
        System.out.println("Patente: " + vehículo.getPatente());
    }

    private static String nombreVehiculo(Vehiculo vehículo) {
        return vehículo.getClass().getSimpleName();
    }

    private static String normalizar(String texto) {
        return texto.toLowerCase(java.util.Locale.ROOT).trim();
    }

    private static void mostrarExito(String mensaje) {
        System.out.println(SEPARADOR);
        System.out.println("[" + mensaje + "]");
    }

    private static void mostrarRechazo(String mensaje) {
        System.out.println(SEPARADOR);
        System.out.println("[OPERACIÓN RECHAZADA: "
                + (mensaje == null ? "No se pudo completar la operacion." : mensaje) + "]");
    }

    private static int leerOpcion(String mensaje, int minimo, int maximo) {
        while (true) {
            String entrada = leerTextoConPrompt(mensaje);
            try {
                int opcion = Integer.parseInt(entrada);
                if (opcion >= minimo && opcion <= maximo) {
                    return opcion;
                }
            } catch (NumberFormatException e) {
                // Se informa debajo y se vuelve a solicitar la opcion.
            }
            mostrarErrorEntrada();
        }
    }

    private static int leerEntero(String mensaje) {
        String entrada = leerTextoConPrompt(mensaje);
        try {
            return Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            mostrarErrorEntrada();
            throw new EntradaInvalidaException();
        }
    }

    private static double leerDecimal(String mensaje) {
        String entrada = leerTextoConPrompt(mensaje).replace(',', '.');
        try {
            double valor = Double.parseDouble(entrada);
            if (!Double.isFinite(valor) || valor <= 0) {
                throw new NumberFormatException();
            }
            return valor;
        } catch (NumberFormatException e) {
            mostrarErrorEntrada();
            throw new EntradaInvalidaException();
        }
    }

    private static String leerTexto(String mensaje) {
        String valor = leerTextoConPrompt(mensaje);
        if (valor.isEmpty()) {
            mostrarErrorEntrada();
            throw new EntradaInvalidaException();
        }
        return valor;
    }

    private static String leerTextoConPrompt(String mensaje) {
        System.out.print(mensaje);
        if (!TECLADO.hasNextLine()) {
            throw new EntradaInvalidaException();
        }
        return TECLADO.nextLine().trim();
    }

    private static void mostrarErrorEntrada() {
        System.out.println(SEPARADOR);
        System.out.println("Opción inválida. Intente de nuevo.");
        esperarContinuar();
    }

    private static void esperarContinuar() {
        System.out.println();
        System.out.print("Presiona ENTER para continuar...");
        if (TECLADO.hasNextLine()) {
            TECLADO.nextLine();
        }
    }

    private static void limpiarPantalla() {
        String sistema = System.getProperty("os.name").toLowerCase();
        if (sistema.contains("win")) {
            try {
                new ProcessBuilder("cmd", "/c", "cls")
                        .inheritIO()
                        .start()
                        .waitFor();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("\n\n\n\n\n\n");
            } catch (java.io.IOException e) {
                System.out.println("\n\n\n\n\n\n");
            }
        } else {
            System.out.print("\u001B[H\u001B[2J");
            System.out.flush();
        }
    }

    private static final class EntradaInvalidaException extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }
}
