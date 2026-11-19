package app;

import java.time.LocalDateTime;
import modelo.Bicicleta;
import modelo.Cliente;
import modelo.Moto;
import modelo.Pedido;
import modelo.PedidoNormal;
import modelo.Repartidor;

public class PruebaIntegridadAsignaciones {

    public static void main(String[] args) throws Exception {

        Cliente cliente = new Cliente(
            "Mateo", "2664000000", 1, "Centro"
        );

        Moto moto = new Moto("ABC123");
        Bicicleta bicicleta = new Bicicleta("DEF456");

        Repartidor repartidor = new Repartidor(
            "Juan", "R001", moto
        );

        Pedido pedido = new PedidoNormal(
            1, cliente, "Centro", 8, 10, LocalDateTime.now()
        );

        // PRUEBA 1: asignación inicial válida.
        repartidor.asignarPedido(pedido);
        System.out.println("Asignación inicial: correcta.");

        // PRUEBA 2: impedir reasignar directamente el pedido.
        Repartidor otroRepartidor = new Repartidor(
            "Pedro", "R002", bicicleta
        );

        try {
            otroRepartidor.asignarPedido(pedido);
            System.out.println("ERROR: se permitió reasignar el pedido.");
        } catch (IllegalArgumentException e) {
            System.out.println("Reasignación del pedido rechazada: " + e.getMessage());
        }

        // PRUEBA 3: impedir cambiar el vehículo del repartidor con pedidos.
        try {
            repartidor.setVehiculo(bicicleta);
            System.out.println("ERROR: se permitió cambiar el vehículo del repartidor.");
        } catch (IllegalStateException e) {
            System.out.println("Cambio de vehículo rechazado: " + e.getMessage());
        }

        // PRUEBA 4: comprobar que el vínculo original sigue intacto.
        System.out.println(
            "El pedido conserva la moto: " +
            (pedido.getVehiculoAsignado() == moto)
        );
        System.out.println(
            "El repartidor conserva la moto: " +
            (repartidor.getVehiculo() == moto)
        );

        // PRUEBA 5: impedir asignar un pedido cancelado.
        Pedido cancelado = new PedidoNormal(
            2, cliente, "Norte", 4, 6, LocalDateTime.now()
        );
        cancelado.cambiarEstado("CANCELADO");

        try {
            repartidor.asignarPedido(cancelado);
            System.out.println("ERROR: se permitió asignar un pedido cancelado.");
        } catch (IllegalStateException e) {
            System.out.println("Pedido cancelado rechazado: " + e.getMessage());
        }

        System.out.println(
            "Cantidad de pedidos del repartidor: " +
            repartidor.getCantidadPedidos()
        );
    }
}
