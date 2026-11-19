package app;

import java.time.LocalDateTime;

import modelo.Citylink;
import modelo.Cliente;
import modelo.Pedido;
import modelo.PedidoNormal;
import modelo.PedidoUrgente;
import modelo.Repartidor;
import modelo.Moto;

public class PruebaAtenderSiguiente {

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

        PedidoNormal normal = new PedidoNormal(
            1, cliente, "Destino normal",
            2.0, 5.0, LocalDateTime.now()
        );

        PedidoUrgente urgente = new PedidoUrgente(
            2, cliente, "Destino urgente",
            2.0, 3.0, LocalDateTime.now()
        );

        sistema.agregarPedido(normal);
        sistema.agregarPedido(urgente);

        System.out.println("=== PRUEBA ATENDER SIGUIENTE ===");

        Pedido primero = sistema.atenderSiguiente();

        System.out.println("Primer pedido: " + primero.getCodigo());
        System.out.println("Estado: " + primero.obtenerEstadoActual());

        Pedido segundo = sistema.atenderSiguiente();

        System.out.println("Segundo pedido: " + segundo.getCodigo());
        System.out.println("Estado: " + segundo.obtenerEstadoActual());

        System.out.println("Pedidos atendidos correctamente.");
    }
}
