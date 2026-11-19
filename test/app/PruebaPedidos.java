package app;

import excepciones.CapacidadExcedidaException;
import excepciones.PedidoInvalidoException;
import java.time.LocalDateTime;
import modelo.Bicicleta;
import modelo.Cliente;
import modelo.Moto;
import modelo.Pedido;
import modelo.PedidoFragil;
import modelo.PedidoNormal;
import modelo.PedidoUrgente;
import modelo.Repartidor;

public class PruebaPedidos {

    public static void main(String[] args)
            throws PedidoInvalidoException, CapacidadExcedidaException {

        Cliente cliente = new Cliente(
            "Mateo", "2664000000", 1, "Centro"
        );

        // Creamos los vehículos
        Bicicleta bicicleta = new Bicicleta("ABC123");
        Moto moto = new Moto("DEF456");

        // Creamos los tres tipos de pedidos
        PedidoNormal normal = new PedidoNormal(
            1, cliente, "Calle 1", 8, 10, LocalDateTime.now()
        );

        PedidoUrgente urgente = new PedidoUrgente(
            2, cliente, "Calle 2", 8, 10, LocalDateTime.now()
        );

        PedidoFragil fragil = new PedidoFragil(
            3, cliente, "Calle 3", 8, 10, LocalDateTime.now()
        );

        // Los vehículos se asignan a través de repartidores para mantener
        // sincronizados el pedido y el historial del repartidor.
        Repartidor repartidorBicicleta = new Repartidor(
            "Repartidor bicicleta", "RB001", bicicleta
        );
        Repartidor repartidorMoto = new Repartidor(
            "Repartidor moto", "RM001", moto
        );

        repartidorBicicleta.asignarPedido(normal);
        repartidorMoto.asignarPedido(urgente);

        // Intentamos asignar un pedido frágil a una bicicleta.
        try {
            repartidorBicicleta.asignarPedido(fragil);
        } catch (CapacidadExcedidaException e) {
            System.out.println(
                "Asignación rechazada: " + e.getMessage()
            );
        }

        // Lo asignamos a una moto.
        repartidorMoto.asignarPedido(fragil);

        // Un único arreglo contiene los tres tipos de pedidos
        Pedido[] pedidos = {normal, urgente, fragil};

        System.out.println("\n========== LISTADO DE PEDIDOS ==========");

        for (Pedido pedido : pedidos) {
            System.out.println("\nCódigo: " + pedido.getCodigo());
            System.out.println(
                "Tipo: " + pedido.getClass().getSimpleName()
            );
            System.out.println("Importe: $" + pedido.calcularImporte());
        }
    }
}
