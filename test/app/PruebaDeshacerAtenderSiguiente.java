package app;

import java.time.LocalDateTime;

import modelo.Citylink;
import modelo.Cliente;
import modelo.Pedido;
import modelo.PedidoUrgente;
import modelo.Repartidor;
import modelo.Moto;

public class PruebaDeshacerAtenderSiguiente {

    public static void main(String[] args) throws Exception {

        Citylink sistema = new Citylink();

        Cliente cliente = new Cliente(
            "Mateo", "2664000000", 1, "Centro"
        );

        sistema.agregarCliente(cliente);

        Repartidor repartidor = new Repartidor(
            "Juan", "R001", new Moto("M001")
        );

        sistema.agregarRepartidor(repartidor);

        PedidoUrgente urgente = new PedidoUrgente(
            1, cliente, "Destino urgente",
            2.0, 3.0, LocalDateTime.now()
        );

        sistema.agregarPedido(urgente);

        System.out.println("=== PRUEBA DESHACER ATENCION ===");

        Pedido atendido = sistema.atenderSiguiente();

        System.out.println(
            "Estado después de atender: "
            + atendido.obtenerEstadoActual()
        );

        sistema.deshacer();

        System.out.println(
            "Estado después del primer deshacer: "
            + atendido.obtenerEstadoActual()
        );

        sistema.deshacer();

        System.out.println(
            "Estado después del segundo deshacer: "
            + atendido.obtenerEstadoActual()
        );

        Pedido nuevamenteAtendido = sistema.atenderSiguiente();

        System.out.println(
            "Pedido vuelto a atender: "
            + nuevamenteAtendido.getCodigo()
        );
    }
}