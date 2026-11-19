package algoritmos;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;

import modelo.Cliente;
import modelo.Pedido;

public class BusquedaPedidos {

    // BÚSQUEDA LINEAL POR CÓDIGO
    public static Pedido buscarPorCodigoLineal(
            List<Pedido> pedidos, int codigo) {

        if (pedidos == null) {
            throw new IllegalArgumentException(
                "La lista de pedidos no puede ser nula"
            );
        }

        for (Pedido pedido : pedidos) {
            if (pedido.getCodigo() == codigo) {
                return pedido;
            }
        }

        return null;
    }

    // BÚSQUEDA LINEAL DE CLIENTES POR NOMBRE
    public static Cliente buscarClientePorNombre(
            List<Cliente> clientes, String nombre) {

        if (clientes == null) {
            throw new IllegalArgumentException(
                "La lista de clientes no puede ser nula"
            );
        }

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                "El nombre no puede estar vacío"
            );
        }

        String nombreBuscado = normalizar(nombre);

        for (Cliente cliente : clientes) {
            if (cliente != null && cliente.getNombre() != null) {
                String nombreCliente = normalizar(
                    cliente.getNombre()
                );

                if (nombreCliente.equals(nombreBuscado)) {
                    return cliente;
                }
            }
        }

        return null;
    }

    // NORMALIZAR NOMBRES
    private static String normalizar(String texto) {

        String sinAcentos = Normalizer.normalize(
            texto.trim().toLowerCase(Locale.ROOT),
            Normalizer.Form.NFD
        );

        return sinAcentos.replaceAll("\\p{M}", "");
    }

    // BÚSQUEDA BINARIA POR CÓDIGO
    public static Pedido buscarPorCodigoBinaria(
            List<Pedido> pedidos, int codigo) {

        if (pedidos == null) {
            throw new IllegalArgumentException(
                "La lista de pedidos no puede ser nula"
            );
        }

        int inicio = 0;
        int fin = pedidos.size() - 1;

        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            int codigoMedio = pedidos.get(medio).getCodigo();

            if (codigoMedio == codigo) {
                return pedidos.get(medio);
            }

            if (codigo < codigoMedio) {
                fin = medio - 1;
            } else {
                inicio = medio + 1;
            }
        }

        return null;
    }
}