package app;

import excepciones.CapacidadExcedidaException;
import excepciones.EstadoInvalidoException;
import excepciones.PedidoInvalidoException;
import java.time.LocalDateTime;
import modelo.Bicicleta;
import modelo.Cliente;
import modelo.Moto;
import modelo.Pedido;
import modelo.PedidoFragil;
import modelo.PedidoNormal;
import modelo.Repartidor;

public class PruebaRepartidor {

    public static void main(String[] args)
            throws PedidoInvalidoException, CapacidadExcedidaException {

        Cliente cliente = new Cliente(
            "Mateo", "2664000000", 1, "Centro"
        );

        Moto moto = new Moto("ABC123");
        Bicicleta bicicleta = new Bicicleta("DEF456");

        Repartidor repartidor = new Repartidor("Juan", "R001", moto);

        Pedido normal = new PedidoNormal(
            1, cliente, "Centro", 8, 10, LocalDateTime.now()
        );

        Pedido fragil = new PedidoFragil(
            2, cliente, "Norte", 5, 15, LocalDateTime.now()
        );

        // PRUEBA 1: asignación normal
        repartidor.asignarPedido(normal);
        System.out.println("Pedido normal asignado correctamente.");

        // PRUEBA 2: asignación duplicada
        try {
            repartidor.asignarPedido(normal);
        } catch (IllegalArgumentException e) {
            System.out.println("Duplicado rechazado: " + e.getMessage());
        }

        // PRUEBA 3: vehículo incorrecto
        try {
            repartidor.asignarPedido(fragil, bicicleta);
        } catch (IllegalArgumentException e) {
            System.out.println("Vehículo incorrecto: " + e.getMessage());
        }

        // PRUEBA 4: asignación válida de pedido frágil
        repartidor.asignarPedido(fragil, moto);
        System.out.println("Pedido frágil asignado correctamente.");

        // PRUEBA 5: entrega válida
        normal.cambiarEstado("EN_CAMINO");
        repartidor.registrarEntrega(normal);

        // PRUEBA 6: intentar entregar un pedido ajeno
        Pedido otroPedido = new PedidoNormal(
            3, cliente, "Sur", 5, 8, LocalDateTime.now()
        );

        try {
            repartidor.registrarEntrega(otroPedido);
        } catch (IllegalArgumentException e) {
            System.out.println("Pedido ajeno rechazado: " + e.getMessage());
        }

        // PRUEBA 7: intentar entregar un pedido pendiente.
        // EstadoInvalidoException es unchecked, por eso no se propaga con throws.
        try {
            repartidor.registrarEntrega(fragil);
        } catch (EstadoInvalidoException e) {
            System.out.println("Entrega inválida rechazada: " + e.getMessage());
        }

        // RESULTADOS FINALES
        System.out.println(
            "\nEstado del pedido normal: " + normal.obtenerEstadoActual()
        );
        System.out.println(
            "Estado del pedido frágil: " + fragil.obtenerEstadoActual()
        );
        System.out.println(
            "Cantidad de pedidos: " + repartidor.getCantidadPedidos()
        );
        System.out.println(
            "Estado del repartidor: " + repartidor.obtenerEstadoActual()
        );
        System.out.println("Comisión: $" + repartidor.calcularImporte());
    }
}
