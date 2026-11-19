package algoritmos;

import java.util.Comparator;

import modelo.Pedido;

public class CriteriosPedido {

    // Ordenar por distancia (de menor a mayor)
    public static Comparator<Pedido> porDistancia() {
        return Comparator.comparingDouble(Pedido::getKm);
    }

    // Ordenar por fecha (de más antiguo a más reciente)
    public static Comparator<Pedido> porFecha() {
        return Comparator.comparing(Pedido::getFechaHora);
    }

    // Ordenar por costo (de menor a mayor)
    // Requiere que todos los pedidos tengan vehículo asignado.
    public static Comparator<Pedido> porCosto() {
        return Comparator.comparingDouble(Pedido::calcularImporte);
    }
}