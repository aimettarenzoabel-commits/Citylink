package algoritmos;

import java.util.List;
import java.util.Comparator;

import modelo.Pedido;

public class OrdenamientoPedidos {

    // ORDENAMIENTO BURBUJA POR CÓDIGO
    public static void ordenarPorCodigoBurbuja(
            List<Pedido> pedidos) {

        if (pedidos == null) {
            throw new IllegalArgumentException(
                "La lista de pedidos no puede ser nula"
            );
        }

        for (int i = 0; i < pedidos.size() - 1; i++) {

            for (int j = 0; j < pedidos.size() - 1 - i; j++) {

                if (pedidos.get(j).getCodigo()
                        > pedidos.get(j + 1).getCodigo()) {

                    Pedido auxiliar = pedidos.get(j);

                    pedidos.set(j, pedidos.get(j + 1));

                    pedidos.set(j + 1, auxiliar);
                }
            }
        }
    }

     // ORDENAMIENTO BURBUJA SEGÚN UN CRITERIO
    public static void ordenarBurbuja(
            List<Pedido> pedidos, Comparator<Pedido> criterio) {

        if (pedidos == null || criterio == null) {
            throw new IllegalArgumentException(
                "La lista y el criterio no pueden ser nulos"
            );
        }

        for (int i = 0; i < pedidos.size() - 1; i++) {

            for (int j = 0; j < pedidos.size() - 1 - i; j++) {

                if (criterio.compare(
                        pedidos.get(j), pedidos.get(j + 1)) > 0) {

                    Pedido auxiliar = pedidos.get(j);
                    pedidos.set(j, pedidos.get(j + 1));
                    pedidos.set(j + 1, auxiliar);
                }
            }
        }
    }

    // ORDENAMIENTO QUICK SORT POR CÓDIGO
    public static void ordenarPorCodigoQuickSort(
            List<Pedido> pedidos) {

        if (pedidos == null) {
            throw new IllegalArgumentException(
                "La lista de pedidos no puede ser nula"
            );
        }

        quickSort(pedidos, 0, pedidos.size() - 1);
    }

     public static void ordenarQuickSort(
            List<Pedido> pedidos, Comparator<Pedido> criterio) {

        if (pedidos == null || criterio == null) {
            throw new IllegalArgumentException(
                "La lista y el criterio no pueden ser nulos"
            );
        }

        quickSortPorCriterio(
            pedidos, 0, pedidos.size() - 1, criterio
        );
    }

    private static void quickSortPorCriterio(
            List<Pedido> pedidos, int inicio, int fin,
            Comparator<Pedido> criterio) {

        if (inicio < fin) {
            int pivote = particionarPorCriterio(
                pedidos, inicio, fin, criterio
            );

            quickSortPorCriterio(
                pedidos, inicio, pivote - 1, criterio
            );

            quickSortPorCriterio(
                pedidos, pivote + 1, fin, criterio
            );
        }
    }

    private static int particionarPorCriterio(
            List<Pedido> pedidos, int inicio, int fin,
            Comparator<Pedido> criterio) {

        Pedido pivote = pedidos.get(fin);
        int i = inicio - 1;

        for (int j = inicio; j < fin; j++) {
            if (criterio.compare(pedidos.get(j), pivote) <= 0) {
                i++;

                Pedido auxiliar = pedidos.get(i);
                pedidos.set(i, pedidos.get(j));
                pedidos.set(j, auxiliar);
            }
        }

        Pedido auxiliar = pedidos.get(i + 1);
        pedidos.set(i + 1, pedidos.get(fin));
        pedidos.set(fin, auxiliar);

        return i + 1;
    }

    private static void quickSort(
            List<Pedido> pedidos, int inicio, int fin) {

        if (inicio < fin) {

            int pivote = particionar(pedidos, inicio, fin);

            quickSort(pedidos, inicio, pivote - 1);
            quickSort(pedidos, pivote + 1, fin);
        }
    }

    private static int particionar(
            List<Pedido> pedidos, int inicio, int fin) {

        int pivote = pedidos.get(fin).getCodigo();
        int i = inicio - 1;

        for (int j = inicio; j < fin; j++) {

            if (pedidos.get(j).getCodigo() <= pivote) {

                i++;

                Pedido auxiliar = pedidos.get(i);
                pedidos.set(i, pedidos.get(j));
                pedidos.set(j, auxiliar);
            }
        }

        Pedido auxiliar = pedidos.get(i + 1);
        pedidos.set(i + 1, pedidos.get(fin));
        pedidos.set(fin, auxiliar);

        return i + 1;
    }

}