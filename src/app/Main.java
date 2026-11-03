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
 * Punto de entrada del sistema Citylink.
 */
public class Main {

    private static final Scanner TECLADO = new Scanner(System.in);

    private Main() {
    }

    /**
     * Inicia el gestor, carga los datos de ejemplo y ejecuta el menú.
     * @param args parámetro de la operación
     */
    public static void main(String[] args) {
        Citylink citylink = new Citylink();

        try {
            DatosIniciales.cargar(citylink);
            System.out.println("Datos iniciales cargados correctamente.");
            ejecutarMenu(citylink);
        } catch (ClienteNoEncontradoException | PedidoInvalidoException
                | AlmacenamientoLlenoException e) {
            System.out.println("No se pudieron cargar los datos iniciales: " + e.getMessage());
        } finally {
            TECLADO.close();
            System.out.println("Sistema Citylink finalizado.");
        }
    }

    private static void ejecutarMenu(Citylink citylink) {
        boolean continuar = true;

        while (continuar) {
            mostrarMenu();
            int opcion = leerEntero("Elegí una opción: ");

            try {
                switch (opcion) {
                    case 1:
                        registrarCliente(citylink);
                        break;
                    case 2:
                        registrarRepartidor(citylink);
                        break;
                    case 3:
                        registrarPedido(citylink);
                        break;
                    case 4:
                        asignarPedido(citylink);
                        break;
                    case 5:
                        cambiarEstado(citylink);
                        break;
                    case 6:
                        buscarCliente(citylink);
                        break;
                    case 7:
                        listarPedidos(citylink);
                        break;
                    case 8:
                        consultarEstado(citylink);
                        break;
                    case 9:
                        consultarImportes(citylink);
                        break;
                    case 10:
                        continuar = false;
                        break;
                    default:
                        System.out.println("Opción inexistente. Elegí un número del 1 al 10.");
                }
            } catch (ClienteNoEncontradoException | PedidoInvalidoException
                    | CapacidadExcedidaException e) {
                System.out.println("No se pudo completar la operación: " + e.getMessage());
            } catch (AlmacenamientoLlenoException e) {
                System.out.println("No hay espacio disponible: " + e.getMessage());
            } catch (RuntimeException e) {
                System.out.println("Operación rechazada: " + e.getMessage());
            } finally {
                if (continuar) {
                    System.out.println("\n--- Operación finalizada ---");
                }
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n========== CITYLINK ==========");
        System.out.println("1. Registrar cliente");
        System.out.println("2. Registrar repartidor");
        System.out.println("3. Registrar pedido");
        System.out.println("4. Asignar pedido");
        System.out.println("5. Cambiar estado de pedido");
        System.out.println("6. Buscar cliente");
        System.out.println("7. Listar pedidos con costo y tiempo");
        System.out.println("8. Consultar estado de pedido o repartidor");
        System.out.println("9. Consultar importes y comisiones");
        System.out.println("10. Salir");
    }

    private static void registrarCliente(Citylink citylink) throws AlmacenamientoLlenoException {
        System.out.println("\n--- Nuevo cliente ---");
        String nombre = leerTexto("Nombre: ");
        String telefono = leerTexto("Teléfono: ");
        int codigo = leerEntero("Código: ");
        String zona = leerTexto("Zona: ");

        citylink.agregarCliente(new Cliente(nombre, telefono, codigo, zona));
        System.out.println("Cliente registrado correctamente.");
    }

    private static void registrarRepartidor(Citylink citylink) throws AlmacenamientoLlenoException {
        System.out.println("\n--- Nuevo repartidor ---");
        String nombre = leerTexto("Nombre: ");
        String legajo = leerTexto("Legajo: ");
        System.out.println("Vehículo: 1. Bicicleta | 2. Moto | 3. Camioneta");
        int tipo = leerEntero("Tipo: ");
        String patente = leerTexto("Patente: ");

        Vehiculo vehiculo;
        switch (tipo) {
            case 1:
                vehiculo = new Bicicleta(patente);
                break;
            case 2:
                vehiculo = new Moto(patente);
                break;
            case 3:
                vehiculo = new Camioneta(patente);
                break;
            default:
                throw new IllegalArgumentException("Tipo de vehículo inexistente.");
        }

        citylink.agregarRepartidor(new Repartidor(nombre, legajo, vehiculo));
        System.out.println("Repartidor registrado correctamente.");
    }

    private static void registrarPedido(Citylink citylink)
            throws ClienteNoEncontradoException, PedidoInvalidoException, AlmacenamientoLlenoException {
        System.out.println("\n--- Nuevo pedido ---");
        int codigo = leerEntero("Código del pedido: ");
        int codigoCliente = leerEntero("Código del cliente: ");
        Cliente cliente = citylink.buscarCliente(codigoCliente);
        String destino = leerTexto("Destino: ");
        double kg = leerDecimal("Peso en kg: ");
        double km = leerDecimal("Distancia en km: ");

        System.out.println("Tipo: 1. Normal | 2. Urgente | 3. Frágil");
        int tipo = leerEntero("Tipo de pedido: ");
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
                throw new IllegalArgumentException("Tipo de pedido inexistente.");
        }

        citylink.agregarPedido(pedido);
        System.out.println("Pedido registrado correctamente.");
    }

    private static void asignarPedido(Citylink citylink)
            throws CapacidadExcedidaException {
        int codigo = leerEntero("Código del pedido: ");
        String legajo = leerTexto("Legajo del repartidor: ");
        citylink.asignarPedido(codigo, legajo);
        System.out.println("Pedido asignado correctamente.");
    }

    private static void cambiarEstado(Citylink citylink) {
        int codigo = leerEntero("Código del pedido: ");
        System.out.println("Estado nuevo: 1. EN_CAMINO | 2. CANCELADO | 3. ENTREGADO");
        int opcion = leerEntero("Elegí: ");

        if (opcion == 1) {
            citylink.cambiarEstadoPedido(codigo, "EN_CAMINO");
        } else if (opcion == 2) {
            citylink.cambiarEstadoPedido(codigo, "CANCELADO");
        } else if (opcion == 3) {
            String legajo = leerTexto("Legajo del repartidor responsable: ");
            citylink.registrarEntrega(codigo, legajo);
        } else {
            throw new IllegalArgumentException("Estado inexistente.");
        }
        System.out.println("Estado actualizado correctamente.");
    }

    private static void buscarCliente(Citylink citylink)
            throws ClienteNoEncontradoException {
        System.out.println("Buscar por: 1. Código | 2. Nombre");
        int opcion = leerEntero("Elegí: ");
        Cliente cliente;

        if (opcion == 1) {
            cliente = citylink.buscarCliente(leerEntero("Código: "));
        } else if (opcion == 2) {
            cliente = citylink.buscarCliente(leerTexto("Nombre: "));
        } else {
            throw new IllegalArgumentException("Criterio de búsqueda inexistente.");
        }
        System.out.println(cliente);
    }

    private static void listarPedidos(Citylink citylink) {
        Pedido[] pedidos = citylink.getPedidos();
        if (pedidos.length == 0) {
            System.out.println("No hay pedidos registrados.");
            return;
        }

        for (Pedido pedido : pedidos) {
            System.out.println("\n" + pedido);
            if (pedido.getVehiculoAsignado() == null) {
                System.out.println("Costo y tiempo: pendientes de asignación.");
            } else {
                System.out.printf("Costo: $%.2f%n", citylink.calcularCostoPedido(pedido.getCodigo()));
                System.out.printf("Tiempo estimado: %.2f minutos%n",
                        citylink.calcularTiempoEstimadoPedido(pedido.getCodigo()));
            }
        }
    }

    private static void consultarEstado(Citylink citylink) {
        System.out.println("Consultar: 1. Pedido | 2. Repartidor");
        int opcion = leerEntero("Elegí: ");
        if (opcion == 1) {
            int codigo = leerEntero("Código del pedido: ");
            System.out.println("Estado: " + citylink.consultarEstadoPedido(codigo));
        } else if (opcion == 2) {
            String legajo = leerTexto("Legajo del repartidor: ");
            System.out.println("Estado: " + citylink.consultarEstadoRepartidor(legajo));
        } else {
            throw new IllegalArgumentException("Opción inexistente.");
        }
    }

    private static void consultarImportes(Citylink citylink) {
        System.out.printf("Importe total cotizado de pedidos asignados: $%.2f%n",
                citylink.calcularImporteTotalPedidos());
        System.out.printf("Comisiones totales: $%.2f%n",
                citylink.calcularComisionesTotales());

        for (Repartidor repartidor : citylink.getRepartidores()) {
            System.out.printf("Comisión de %s (%s): $%.2f%n",
                    repartidor.getNombre(), repartidor.getLegajo(),
                    citylink.calcularComisionRepartidor(repartidor.getLegajo()));
        }
        System.out.println("Pedidos sin asignar: " + citylink.contarPedidosSinAsignar());
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        String valor = TECLADO.nextLine().trim();
        if (valor.isEmpty()) {
            throw new IllegalArgumentException("El valor no puede estar vacío.");
        }
        return valor;
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = TECLADO.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Ingresá un número entero válido.");
            }
        }
    }

    private static double leerDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = TECLADO.nextLine().trim().replace(',', '.');
            try {
                double valor = Double.parseDouble(entrada);
                if (Double.isFinite(valor) && valor > 0) {
                    return valor;
                }
                System.out.println("Ingresá un número positivo y finito.");
            } catch (NumberFormatException e) {
                System.out.println("Ingresá un número válido. Podés usar coma o punto decimal.");
            }
        }
    }
}
