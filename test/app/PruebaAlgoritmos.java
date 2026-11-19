package app;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import algoritmos.BusquedaPedidos;
import algoritmos.OrdenamientoPedidos;
import algoritmos.CriteriosPedido;
import algoritmos.IntercalacionPedidos;
import modelo.Cliente;
import modelo.Pedido;
import modelo.PedidoNormal;
import modelo.PedidoUrgente;
import modelo.PedidoFragil;

public class PruebaAlgoritmos {

    public static void main(String[] args) throws Exception {

        Cliente cliente = new Cliente(
            "Mateo", "2664000000", 1, "Centro"
        );

        Pedido p1 = new PedidoNormal(
            3, cliente, "Centro", 8, 8,
            LocalDateTime.of(2026, 1, 3, 10, 0)
        );

        Pedido p2 = new PedidoUrgente(
            1, cliente, "Norte", 5, 4,
            LocalDateTime.of(2026, 1, 1, 10, 0)
        );

        Pedido p3 = new PedidoFragil(
            2, cliente, "Sur", 3, 6,
            LocalDateTime.of(2026, 1, 2, 10, 0)
        );

        List<Pedido> pedidos = new ArrayList<>();

        pedidos.add(p1);
        pedidos.add(p2);
        pedidos.add(p3);

        System.out.println("===== BUSQUEDAS =====");

        System.out.println(
            "Busqueda lineal: "
            + (BusquedaPedidos.buscarPorCodigoLineal(
                pedidos, 1) == p2)
        );

        System.out.println(
            "Busqueda por cliente: "
            + (BusquedaPedidos.buscarClientePorNombre(
                List.of(cliente), "mateo") == cliente)
        );

        System.out.println("\n===== ORDENAMIENTOS =====");

        OrdenamientoPedidos.ordenarBurbuja(
            pedidos, CriteriosPedido.porDistancia()
        );

        System.out.println(
            "Burbuja por distancia: "
            + pedidos.get(0).getKm() + ", "
            + pedidos.get(1).getKm() + ", "
            + pedidos.get(2).getKm()
        );

        OrdenamientoPedidos.ordenarQuickSort(
            pedidos, CriteriosPedido.porFecha()
        );

        System.out.println(
            "QuickSort por fecha: "
            + pedidos.get(0).getCodigo() + ", "
            + pedidos.get(1).getCodigo() + ", "
            + pedidos.get(2).getCodigo()
        );

        OrdenamientoPedidos.ordenarPorCodigoQuickSort(pedidos);

        System.out.println(
            "Busqueda binaria: "
            + (BusquedaPedidos.buscarPorCodigoBinaria(
                pedidos, 2) == p3)
        );

        System.out.println("\n===== INTERCALACION =====");

        List<Pedido> turno1 = new ArrayList<>();
        turno1.add(p2);
        turno1.add(p3);

        List<Pedido> turno2 = new ArrayList<>();
        turno2.add(p1);

        List<Pedido> resultado = IntercalacionPedidos.intercalar(
            turno1, turno2, CriteriosPedido.porDistancia()
        );

        System.out.println("Pedidos intercalados:");

        for (Pedido pedido : resultado) {
            System.out.println(
                "Código: " + pedido.getCodigo()
                + " | Distancia: " + pedido.getKm()
            );
        }
    }
}