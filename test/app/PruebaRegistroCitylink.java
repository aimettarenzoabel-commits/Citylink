package app;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import excepciones.AlmacenamientoLlenoException;
import excepciones.CapacidadExcedidaException;
import excepciones.ClienteNoEncontradoException;
import excepciones.PedidoInvalidoException;
import modelo.Bicicleta;
import modelo.Citylink;
import modelo.Cliente;
import modelo.Moto;
import modelo.Pedido;
import modelo.PedidoNormal;
import modelo.Repartidor;

public class PruebaRegistroCitylink {

    public static void main(String[] args) throws Exception {
        probarRegistroYBusquedaDeRepartidores();
        probarRegistroYBusquedaDePedidos();
        probarAsignacionDesdeCitylink();
        probarCapacidadDeRepartidores();
        probarCapacidadDePedidos();
    }

    private static void probarRegistroYBusquedaDeRepartidores()
            throws AlmacenamientoLlenoException {

        System.out.println("\n===== REGISTRO Y BUSQUEDA DE REPARTIDORES =====");

        Citylink sistema = new Citylink();
        Repartidor repartidor = new Repartidor(
            "Juan", "R001", new Moto("ABC123")
        );

        sistema.agregarRepartidor(repartidor);
        System.out.println("Registro inicial: correcto.");

        Repartidor encontrado = sistema.buscarRepartidor(" r001 ");
        System.out.println(
            "Búsqueda sin distinguir mayúsculas ni espacios: " +
            (encontrado == repartidor)
        );

        try {
            repartidor.setLegajo("R999");
        } catch (IllegalStateException e) {
            System.out.println("Cambio de legajo rechazado: " + e.getMessage());
        }

        try {
            sistema.agregarRepartidor(
                new Repartidor("Pedro", "r001", new Bicicleta("DEF456"))
            );
        } catch (IllegalArgumentException e) {
            System.out.println("Legajo duplicado rechazado: " + e.getMessage());
        }

        try {
            sistema.buscarRepartidor("R999");
        } catch (NoSuchElementException e) {
            System.out.println("Búsqueda inexistente controlada: " + e.getMessage());
        }

        Repartidor[] copia = sistema.getRepartidores();
        copia[0] = null;
        System.out.println(
            "La copia no altera el arreglo interno: " +
            (sistema.buscarRepartidor("R001") == repartidor)
        );

        try {
            sistema.agregarRepartidor(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Repartidor nulo rechazado: " + e.getMessage());
        }

        try {
            sistema.agregarRepartidor(new Repartidor());
        } catch (IllegalArgumentException e) {
            System.out.println("Repartidor incompleto rechazado: " + e.getMessage());
        }

        try {
            new Citylink().agregarRepartidor(repartidor);
        } catch (IllegalStateException | AlmacenamientoLlenoException e) {
            System.out.println("Reutilización en otro sistema rechazada: " + e.getMessage());
        }

        System.out.println(
            "Cantidad de repartidores: " + sistema.getCantidadRepartidores()
        );
    }

    private static void probarRegistroYBusquedaDePedidos() throws Exception {

        System.out.println("\n===== REGISTRO Y BUSQUEDA DE PEDIDOS =====");

        Citylink sistema = new Citylink();
        Cliente cliente = new Cliente(
            "Mateo", "2664000000", 1, "Centro"
        );
        sistema.agregarCliente(cliente);

        Pedido pedido = new PedidoNormal(
            100, cliente, "Centro", 8, 10, LocalDateTime.now()
        );
        sistema.agregarPedido(pedido);

        System.out.println("Registro inicial del pedido: correcto.");
        System.out.println(
            "Búsqueda por código: " + (sistema.buscarPedido(100) == pedido)
        );

        try {
            pedido.setCodigo(200);
        } catch (IllegalStateException e) {
            System.out.println("Cambio de código rechazado: " + e.getMessage());
        }

        try {
            pedido.setCliente(new Cliente(
                "Otro", "2664111111", 2, "Norte"
            ));
        } catch (IllegalStateException e) {
            System.out.println("Cambio de cliente rechazado: " + e.getMessage());
        }

        try {
            sistema.agregarPedido(new PedidoNormal(
                100, cliente, "Sur", 5, 5, LocalDateTime.now()
            ));
        } catch (PedidoInvalidoException e) {
            System.out.println("Código de pedido duplicado rechazado: " + e.getMessage());
        }

        try {
            sistema.buscarPedido(999);
        } catch (NoSuchElementException e) {
            System.out.println("Pedido inexistente controlado: " + e.getMessage());
        }

        try {
            Pedido pedidoConClienteAjeno = new PedidoNormal(
                101,
                new Cliente("Ajeno", "2664222222", 1, "Sur"),
                "Sur", 5, 5, LocalDateTime.now()
            );
            sistema.agregarPedido(pedidoConClienteAjeno);
        } catch (IllegalArgumentException | ClienteNoEncontradoException e) {
            System.out.println("Cliente ajeno rechazado: " + e.getMessage());
        }

        try {
            cliente.setCodigo(50);
        } catch (IllegalStateException e) {
            System.out.println("Cambio de código de cliente rechazado: " + e.getMessage());
        }

        try {
            sistema.agregarCliente(new Cliente());
        } catch (IllegalArgumentException | AlmacenamientoLlenoException e) {
            System.out.println("Cliente incompleto rechazado: " + e.getMessage());
        }

        try {
            new Citylink().agregarCliente(cliente);
        } catch (IllegalStateException | AlmacenamientoLlenoException e) {
            System.out.println("Cliente ya registrado en otro sistema: " + e.getMessage());
        }

        System.out.println(
            "Cantidad de pedidos: " + sistema.getCantidadPedidos()
        );
    }

    private static void probarAsignacionDesdeCitylink() throws Exception {

        System.out.println("\n===== ASIGNACION DESDE CITYLINK =====");

        Citylink sistema = new Citylink();
        Cliente cliente = new Cliente(
            "Mateo", "2664000000", 1, "Centro"
        );
        Repartidor repartidor = new Repartidor(
            "Juan", "R001", new Moto("ABC123")
        );
        Pedido pedido = new PedidoNormal(
            1, cliente, "Centro", 8, 10, LocalDateTime.now()
        );

        sistema.agregarCliente(cliente);
        sistema.agregarRepartidor(repartidor);
        sistema.agregarPedido(pedido);
        sistema.asignarPedido(1, "r001");

        System.out.println(
            "Pedido asignado al repartidor correcto: " +
            (pedido.getVehiculoAsignado() == repartidor.getVehiculo())
        );

        sistema.cambiarEstadoPedido(1, "EN_CAMINO");
        sistema.registrarEntrega(1, "R001");

        System.out.println("Estado final del pedido: " + pedido.getEstado());
        System.out.println("Comisión del repartidor: $" + repartidor.calcularImporte());

        Pedido pedidoPesado = new PedidoNormal(
            2, cliente, "Norte", 35, 5, LocalDateTime.now()
        );
        sistema.agregarPedido(pedidoPesado);

        try {
            sistema.asignarPedido(2, "R001");
        } catch (CapacidadExcedidaException e) {
            System.out.println("Capacidad excedida controlada: " + e.getMessage());
        }
    }

    private static void probarCapacidadDeRepartidores()
            throws AlmacenamientoLlenoException {

        System.out.println("\n===== CAPACIDAD DE REPARTIDORES =====");

        Citylink sistema = new Citylink();

        for (int i = 1; i <= 10; i++) {
            sistema.agregarRepartidor(new Repartidor(
                "Repartidor " + i,
                "R" + String.format("%03d", i),
                new Moto("MOT" + String.format("%03d", i))
            ));
        }

        System.out.println(
            "Repartidores registrados: " + sistema.getCantidadRepartidores()
        );

        try {
            sistema.agregarRepartidor(new Repartidor(
                "Repartidor extra", "R011", new Moto("MOT011")
            ));
        } catch (AlmacenamientoLlenoException e) {
            System.out.println("Límite de 10 respetado: " + e.getMessage());
        }
    }

    private static void probarCapacidadDePedidos()
            throws Exception {

        System.out.println("\n===== CAPACIDAD DE PEDIDOS =====");

        Citylink sistema = new Citylink();
        Cliente cliente = new Cliente(
            "Cliente prueba", "2664000000", 1, "Centro"
        );
        sistema.agregarCliente(cliente);

        for (int i = 1; i <= 100; i++) {
            sistema.agregarPedido(new PedidoNormal(
                i, cliente, "Destino " + i, 1, 1, LocalDateTime.now()
            ));
        }

        System.out.println(
            "Pedidos registrados: " + sistema.getCantidadPedidos()
        );

        try {
            sistema.agregarPedido(new PedidoNormal(
                101, cliente, "Destino extra", 1, 1, LocalDateTime.now()
            ));
        } catch (AlmacenamientoLlenoException e) {
            System.out.println("Límite de 100 respetado: " + e.getMessage());
        }
    }
}
