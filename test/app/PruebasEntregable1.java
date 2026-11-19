package app;

import java.time.LocalDateTime;

import excepciones.CapacidadExcedidaException;
import excepciones.ClienteNoEncontradoException;
import excepciones.PedidoInvalidoException;
import excepciones.EstadoInvalidoException;
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
 * Pruebas de aceptación para los requisitos principales del Entregable 1.
 * Cada fallo detiene la ejecución con AssertionError.
 */
public final class PruebasEntregable1 {

    private static int comprobaciones;

    private PruebasEntregable1() {
    }

    public static void main(String[] args) throws Exception {
        probarDatosIniciales();
        probarReglasDeNegocio();
        probarPolimorfismo();
        probarSobrecarga();
        probarExcepcionesChecked();
        probarIntegridadYEliminaciones();
        probarComision();
        System.out.println("\nTODAS LAS PRUEBAS DE ACEPTACION PASARON: " + comprobaciones);
    }

    private static void probarDatosIniciales() throws Exception {
        Citylink sistema = new Citylink();
        DatosIniciales.cargar(sistema);
       
        comprobar(sistema.getCantidadRepartidores() >= 3, "Carga al menos tres repartidores");
        comprobar(sistema.getCantidadPedidos() >= 8, "Carga al menos ocho pedidos");
        comprobar(contieneTipo(sistema.getPedidos(), PedidoNormal.class), "Incluye pedidos normales");
        comprobar(contieneTipo(sistema.getPedidos(), PedidoUrgente.class), "Incluye pedidos urgentes");
        comprobar(contieneTipo(sistema.getPedidos(), PedidoFragil.class), "Incluye pedidos frágiles");
    }

    private static boolean contieneTipo(
        java.util.ArrayList<Pedido> pedidos, Class<?> tipo) {

        return pedidos.stream().anyMatch(tipo::isInstance);
    }

    private static void probarReglasDeNegocio() throws Exception {
        Citylink sistema = new Citylink();
        Cliente cliente = new Cliente("Prueba", "2664000000", 1, "Centro");
        sistema.agregarCliente(cliente);
        Repartidor bici = new Repartidor("Ciclista", "B01", new Bicicleta("BIC001"));
        sistema.agregarRepartidor(bici);
        Pedido fragil = new PedidoFragil(1, cliente, "Destino", 2, 4, LocalDateTime.now());
        sistema.agregarPedido(fragil);
        esperar(CapacidadExcedidaException.class, () -> sistema.asignarPedido(1, "B01"));
        comprobar(fragil.getVehiculoAsignado() == null, "Pedido frágil no queda asignado a bicicleta");

        Pedido sobrepeso = new PedidoNormal(2, cliente, "Destino", 11, 4, LocalDateTime.now());
        sistema.agregarPedido(sobrepeso);
        esperar(CapacidadExcedidaException.class, () -> sistema.asignarPedido(2, "B01"));
        comprobar(sobrepeso.getVehiculoAsignado() == null, "Pedido con sobrepeso no queda asignado");

        Pedido normal = new PedidoNormal(3, cliente, "Destino", 2, 4, LocalDateTime.now());
        sistema.agregarPedido(normal);
        sistema.asignarPedido(3, "B01");
        sistema.cambiarEstadoPedido(3, "EN_CAMINO");
        esperar(EstadoInvalidoException.class,
            () -> sistema.cambiarEstadoPedido(3, "ENTREGADO"));
        sistema.registrarEntrega(3, "B01");
        comprobar(normal.getEstado().equals("ENTREGADO"), "Flujo de estados permitido");
        esperar(EstadoInvalidoException.class, () -> sistema.cambiarEstadoPedido(3, "CANCELADO"));

        Pedido cancelable = new PedidoNormal(
            4, cliente, "Destino cancelable", 1, 2, LocalDateTime.now()
        );
        sistema.agregarPedido(cancelable);
        sistema.cambiarEstadoPedido(4, "CANCELADO");
        comprobar(cancelable.getEstado().equals("CANCELADO"),
            "Cancelación permitida desde PENDIENTE");
    }

    private static void probarPolimorfismo() {
        Cliente cliente = new Cliente("Poli", "2664111111", 9, "Norte");
        Pedido[] pedidos = {
            nuevoPedidoNormal(10, cliente),
            nuevoPedidoUrgente(11, cliente),
            nuevoPedidoFragil(12, cliente)
        };
        comprobar(pedidos[0].calcularCosto(new Moto("M001")) == 1480.0,
            "Polimorfismo de PedidoNormal");
        comprobar(pedidos[1].calcularCosto(new Moto("M002")) == 2220.0,
            "Polimorfismo de PedidoUrgente");
        comprobar(pedidos[2].calcularCosto(new Moto("M003")) == 2280.0,
            "Polimorfismo de PedidoFragil");

        Vehiculo[] vehiculos = {new Bicicleta("B002"), new Moto("M004"), new Camioneta("C001")};
        comprobar(vehiculos[0].calcularTiempoEntrega(10) == 40.0, "Tiempo de bicicleta");
        comprobar(vehiculos[1].calcularTiempoEntrega(10) == 20.0, "Tiempo de moto");
        comprobar(vehiculos[2].calcularTiempoEntrega(10) == 30.0, "Tiempo de camioneta");
    }

    private static Pedido nuevoPedidoNormal(int codigo, Cliente cliente) {
        try { return new PedidoNormal(codigo, cliente, "Destino", 2, 4, LocalDateTime.now()); }
        catch (PedidoInvalidoException e) { throw new AssertionError(e); }
    }

    private static Pedido nuevoPedidoUrgente(int codigo, Cliente cliente) {
        try { return new PedidoUrgente(codigo, cliente, "Destino", 2, 4, LocalDateTime.now()); }
        catch (PedidoInvalidoException e) { throw new AssertionError(e); }
    }

    private static Pedido nuevoPedidoFragil(int codigo, Cliente cliente) {
        try { return new PedidoFragil(codigo, cliente, "Destino", 2, 4, LocalDateTime.now()); }
        catch (PedidoInvalidoException e) { throw new AssertionError(e); }
    }

    private static void probarSobrecarga() throws Exception {
        Citylink sistema = new Citylink();
        Cliente cliente = new Cliente("Nombre", "2664222222", 20, "Sur");
        sistema.agregarCliente(cliente);
        comprobar(sistema.buscarCliente(20) == cliente, "buscarCliente(int)");
        comprobar(sistema.buscarCliente("nombre") == cliente, "buscarCliente(String)");

        Repartidor repartidor = new Repartidor("Rep", "R20", new Moto("M020"));
        sistema.agregarRepartidor(repartidor);
        Pedido pedido = new PedidoNormal(20, cliente, "Destino", 3, 4, LocalDateTime.now());
        sistema.agregarPedido(pedido);
        repartidor.asignarPedido(pedido, repartidor.getVehiculo());
        comprobar(pedido.getVehiculoAsignado() == repartidor.getVehiculo(), "asignarPedido(Pedido, Vehiculo)");
    }

    private static void probarExcepcionesChecked() throws Exception {
        Citylink sistema = new Citylink();
        sistema.agregarCliente(new Cliente("C1", "T1", 1, "Zona"));
        esperar(ClienteNoEncontradoException.class, () -> sistema.buscarCliente(999));

        Cliente cliente = sistema.buscarCliente(1);
        Pedido duplicadoA = new PedidoNormal(500, cliente, "Destino", 1, 1, LocalDateTime.now());
        Pedido duplicadoB = new PedidoNormal(500, cliente, "Destino", 1, 1, LocalDateTime.now());
        sistema.agregarPedido(duplicadoA);
        esperar(PedidoInvalidoException.class, () -> sistema.agregarPedido(duplicadoB));
    }

    private static void probarIntegridadYEliminaciones() throws Exception {
        Citylink sistema = new Citylink();
        Cliente a = new Cliente("A", "1", 1, "Centro");
        Cliente b = new Cliente("B", "2", 2, "Norte");
        sistema.agregarCliente(a);
        sistema.agregarCliente(b);
        sistema.eliminarCliente(1);
        comprobar(sistema.getCantidadClientes() == 1
        && sistema.getClientes().get(2) == b,
        "Eliminación de cliente conserva el registro restante en el TreeMap");
    }

    private static void probarComision() throws Exception {
        Citylink sistema = new Citylink();
        Cliente cliente = new Cliente("Comision", "2664333333", 3, "Este");
        sistema.agregarCliente(cliente);
        Repartidor repartidor = new Repartidor("Rep", "R03", new Moto("M030"));
        sistema.agregarRepartidor(repartidor);
        Pedido pedido = new PedidoNormal(30, cliente, "Destino", 1, 4, LocalDateTime.now());
        sistema.agregarPedido(pedido);
        sistema.asignarPedido(30, "R03");
        sistema.cambiarEstadoPedido(30, "EN_CAMINO");
        sistema.registrarEntrega(30, "R03");
        comprobar(Math.abs(sistema.calcularComisionRepartidor("R03") - 148.0) < 0.0001,
            "Comisión del 10 por ciento");
    }

    private static void comprobar(boolean condicion, String descripcion) {
        if (!condicion) {
            throw new AssertionError("FALLÓ: " + descripcion);
        }
        comprobaciones++;
        System.out.println("OK: " + descripcion);
    }

    private static void esperar(Class<? extends Throwable> tipo, Accion accion) {
        try {
            accion.ejecutar();
        } catch (Throwable error) {
            if (tipo.isInstance(error)) {
                comprobaciones++;
                System.out.println("OK: excepción " + tipo.getSimpleName());
                return;
            }
            throw new AssertionError("Se esperaba " + tipo.getSimpleName()
                + " pero ocurrió " + error.getClass().getSimpleName(), error);
        }
        throw new AssertionError("Se esperaba la excepción " + tipo.getSimpleName());
    }

    @FunctionalInterface
    private interface Accion {
        void ejecutar() throws Exception;
    }
}
