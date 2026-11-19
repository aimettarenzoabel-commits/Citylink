package app;

import java.time.LocalDateTime;

import excepciones.AlmacenamientoLlenoException;
import excepciones.CapacidadExcedidaException;
import excepciones.ClienteNoEncontradoException;
import excepciones.PedidoInvalidoException;
import modelo.Bicicleta;
import modelo.Citylink;
import modelo.Cliente;
import modelo.Pedido;
import modelo.PedidoNormal;
import modelo.Repartidor;

public class PruebaDeshacer {

    public static void main(String[] args)
            throws AlmacenamientoLlenoException,
            CapacidadExcedidaException,
            ClienteNoEncontradoException,
            PedidoInvalidoException {

        Citylink sistema = new Citylink();

        Cliente cliente = new Cliente(
            "Mateo", "2664000000", 1, "Centro"
        );

        sistema.agregarCliente(cliente);

        Bicicleta bicicleta = new Bicicleta("BICI01");

        Repartidor repartidor = new Repartidor(
            "Juan", "R001", bicicleta
        );

        sistema.agregarRepartidor(repartidor);


        // PRUEBA 1: DESHACER ALTA
        Pedido pedido = new PedidoNormal(
            1, cliente, "Centro", 5, 10, LocalDateTime.now()
        );

        sistema.agregarPedido(pedido);
        sistema.deshacer();

        System.out.println(
            "Deshacer alta: " +
            sistema.getPedidos().isEmpty()
        );

        // PRUEBA 2: DESHACER ASIGNACIÓN
        Pedido pedido2 = new PedidoNormal(
            2, cliente, "Norte", 5, 10, LocalDateTime.now()
        );

        sistema.agregarPedido(pedido2);
        sistema.asignarPedido(2, "R001");
        sistema.deshacer();

        System.out.println(
            "Deshacer asignación: " +
            (pedido2.getVehiculoAsignado() == null
                && pedido2.obtenerEstadoActual().equals("PENDIENTE"))
        );

        // PRUEBA 3: DESHACER CANCELACIÓN
        sistema.cambiarEstadoPedido(2, "CANCELADO");
        sistema.deshacer();

        System.out.println(
            "Deshacer cancelación: " +
            (pedido2.obtenerEstadoActual().equals("PENDIENTE")
                && sistema.getColaPedidos().contains(pedido2))
        );

        // PRUEBA 4: DESHACER ENTREGA
        sistema.asignarPedido(2, "R001");
        sistema.cambiarEstadoPedido(2, "EN_CAMINO");
        sistema.registrarEntrega(2, "R001");
        sistema.deshacer();

        System.out.println(
            "Deshacer entrega: " +
            (pedido2.obtenerEstadoActual().equals("EN_CAMINO")
                && repartidor.getPedidos().contains(pedido2))
        );

        System.out.println(
            "Comisión después de deshacer entrega: $" +
            repartidor.calcularImporte()
        );
    }
}