package app;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import excepciones.AlmacenamientoLlenoException;
import excepciones.ClienteNoEncontradoException;
import excepciones.PedidoInvalidoException;
import excepciones.EstadoInvalidoException;
import modelo.Bicicleta;
import modelo.Citylink;
import modelo.Cliente;
import modelo.Moto;
import modelo.Pedido;
import modelo.PedidoNormal;
import modelo.Repartidor;

/**
 * Pruebas de eliminación, desplazamiento de arreglos y protección de relaciones
 * entre las entidades administradas por Citylink.
 */
public class PruebaEliminacionesCitylink {

    public static void main(String[] args) throws Exception {
        probarEliminacionYDesplazamientoDeClientes();
        probarEliminacionYDesplazamientoDePedidos();
        probarEliminacionYDesplazamientoDeRepartidores();
        probarIntegridadDeEstados();
    }

    private static void probarEliminacionYDesplazamientoDeClientes()
            throws AlmacenamientoLlenoException, ClienteNoEncontradoException,
                   PedidoInvalidoException {

        System.out.println("\n===== ELIMINACION DE CLIENTES =====");

        Citylink sistema = new Citylink();
        Cliente cliente1 = new Cliente("Ana", "111", 1, "Centro");
        Cliente cliente2 = new Cliente("Bruno", "222", 2, "Norte");
        Cliente cliente3 = new Cliente("Carla", "333", 3, "Sur");

        sistema.agregarCliente(cliente1);
        sistema.agregarCliente(cliente2);
        sistema.agregarCliente(cliente3);

        sistema.eliminarCliente(1);
        Cliente[] clientes = sistema.getClientes();

        System.out.println("Cantidad después de eliminar el primero: " +
            sistema.getCantidadClientes());
        System.out.println("Desplazamiento de clientes correcto: " +
            (clientes.length == 2 && clientes[0] == cliente2 && clientes[1] == cliente3));

        Pedido pedidoDeCliente2 = new PedidoNormal(
            10, cliente2, "Norte", 2, 4, LocalDateTime.now()
        );
        sistema.agregarPedido(pedidoDeCliente2);

        try {
            sistema.eliminarCliente(2);
        } catch (IllegalStateException e) {
            System.out.println("Cliente con pedidos protegido: " + e.getMessage());
        }

        sistema.eliminarPedido(10);
        sistema.eliminarCliente(2);
        System.out.println("Cliente liberado después de eliminar su pedido: " +
            (sistema.getCantidadClientes() == 1 && sistema.buscarCliente(3) == cliente3));

        // El cliente eliminado deja de estar bloqueado y puede registrarse en otro sistema.
        Citylink otroSistema = new Citylink();
        otroSistema.agregarCliente(cliente2);
        System.out.println("Cliente eliminado puede registrarse nuevamente: " +
            (otroSistema.buscarCliente(2) == cliente2));
    }

    private static void probarEliminacionYDesplazamientoDePedidos()
            throws Exception {

        System.out.println("\n===== ELIMINACION DE PEDIDOS =====");

        Citylink sistema = new Citylink();
        Cliente cliente = new Cliente("Diego", "444", 4, "Oeste");
        sistema.agregarCliente(cliente);

        Pedido pedido1 = new PedidoNormal(
            20, cliente, "Destino 20", 2, 5, LocalDateTime.now()
        );
        Pedido pedido2 = new PedidoNormal(
            30, cliente, "Destino 30", 3, 6, LocalDateTime.now()
        );
        sistema.agregarPedido(pedido1);
        sistema.agregarPedido(pedido2);

        sistema.eliminarPedido(20);
        Pedido[] pedidos = sistema.getPedidos();
        System.out.println("Cantidad después de eliminar el primero: " +
            sistema.getCantidadPedidos());
        System.out.println("Desplazamiento de pedidos correcto: " +
            (pedidos.length == 1 && pedidos[0] == pedido2));

        // Un pedido eliminado deja de estar registrado y puede corregirse y reutilizarse.
        pedido1.setCodigo(21);
        sistema.agregarPedido(pedido1);
        System.out.println("Pedido eliminado puede registrarse nuevamente: " +
            (sistema.buscarPedido(21) == pedido1));

        Repartidor repartidor = new Repartidor(
            "Elena", "R020", new Moto("MOT020")
        );
        sistema.agregarRepartidor(repartidor);
        sistema.asignarPedido(30, "R020");

        try {
            sistema.eliminarPedido(30);
        } catch (IllegalStateException e) {
            System.out.println("Pedido asignado protegido: " + e.getMessage());
        }

        Pedido cancelado = new PedidoNormal(
            40, cliente, "Destino 40", 1, 2, LocalDateTime.now()
        );
        sistema.agregarPedido(cancelado);
        cancelado.cambiarEstado("CANCELADO");
        sistema.eliminarPedido(40);
        System.out.println("Pedido cancelado y no asignado eliminado: " +
            (sistema.getCantidadPedidos() == 2));

        try {
            sistema.eliminarPedido(999);
        } catch (NoSuchElementException e) {
            System.out.println("Pedido inexistente controlado: " + e.getMessage());
        }
    }

    private static void probarEliminacionYDesplazamientoDeRepartidores()
            throws Exception {

        System.out.println("\n===== ELIMINACION DE REPARTIDORES =====");

        Citylink sistema = new Citylink();
        Repartidor repartidor1 = new Repartidor(
            "Fabio", "R030", new Bicicleta("BIC030")
        );
        Repartidor repartidor2 = new Repartidor(
            "Gabriela", "R031", new Moto("MOT031")
        );

        Cliente cliente = new Cliente("Irene", "666", 6, "Centro");
        sistema.agregarCliente(cliente);
        sistema.agregarRepartidor(repartidor1);
        sistema.agregarRepartidor(repartidor2);

        Pedido pedidoAsignado = new PedidoNormal(
            60, cliente, "Destino 60", 2, 4, LocalDateTime.now()
        );
        sistema.agregarPedido(pedidoAsignado);
        sistema.asignarPedido(60, "R031");

        sistema.eliminarRepartidor("R030");

        Repartidor[] repartidores = sistema.getRepartidores();
        System.out.println("Cantidad después de eliminar el primero: " +
            sistema.getCantidadRepartidores());
        System.out.println("Desplazamiento de repartidores correcto: " +
            (repartidores.length == 1 && repartidores[0] == repartidor2));

        Citylink otroSistema = new Citylink();
        otroSistema.agregarRepartidor(repartidor1);
        System.out.println("Repartidor eliminado puede registrarse nuevamente: " +
            (otroSistema.buscarRepartidor("R030") == repartidor1));

        try {
            sistema.eliminarRepartidor("R031");
        } catch (IllegalStateException e) {
            System.out.println("Repartidor con pedidos protegido: " + e.getMessage());
        }

        try {
            sistema.eliminarRepartidor("R999");
        } catch (NoSuchElementException e) {
            System.out.println("Repartidor inexistente controlado: " + e.getMessage());
        }
    }

    private static void probarIntegridadDeEstados() throws Exception {

        System.out.println("\n===== INTEGRIDAD DE ESTADOS =====");

        Citylink sistema = new Citylink();
        Cliente cliente = new Cliente("Hugo", "555", 5, "Este");
        sistema.agregarCliente(cliente);

        Pedido pedido = new PedidoNormal(
            50, cliente, "Destino 50", 1, 3, LocalDateTime.now()
        );
        sistema.agregarPedido(pedido);

        try {
            sistema.cambiarEstadoPedido(50, "EN_CAMINO");
        } catch (EstadoInvalidoException e) {
            System.out.println("Pedido sin vehículo no puede salir: " + e.getMessage());
        }

        System.out.println("El pedido conserva estado PENDIENTE: " +
            pedido.getEstado().equals("PENDIENTE"));
    }
}
