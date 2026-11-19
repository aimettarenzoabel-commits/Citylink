package algoritmos;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

import modelo.Pedido;

public class IntercalacionPedidos {

    public static List<Pedido> intercalar(
            List<Pedido> lista1,
            List<Pedido> lista2,
            Comparator<Pedido> criterio) {

        if (lista1 == null || lista2 == null || criterio == null) {
            throw new IllegalArgumentException(
                "Las listas y el criterio no pueden ser nulos"
            );
        }

        List<Pedido> resultado = new ArrayList<>();

        int i = 0;
        int j = 0;

        while (i < lista1.size() && j < lista2.size()) {

            if (criterio.compare(lista1.get(i), lista2.get(j)) <= 0) {
                resultado.add(lista1.get(i));
                i++;
            } else {
                resultado.add(lista2.get(j));
                j++;
            }
        }

        while (i < lista1.size()) {
            resultado.add(lista1.get(i));
            i++;
        }

        while (j < lista2.size()) {
            resultado.add(lista2.get(j));
            j++;
        }

        return resultado;
    }
}
