package app;

import java.time.LocalDateTime;

import excepciones.AlmacenamientoLlenoException;
import excepciones.ClienteNoEncontradoException;
import excepciones.PedidoInvalidoException;
import modelo.Bicicleta;
import modelo.Camioneta;
import modelo.Citylink;
import modelo.Cliente;
import modelo.Moto;
import modelo.PedidoFragil;
import modelo.PedidoNormal;
import modelo.PedidoUrgente;
import modelo.Repartidor;

/**
 * Carga datos de ejemplo para iniciar el sistema.
 */
public final class DatosIniciales {

    private DatosIniciales() {
    }

    /**
     * Registra cinco clientes, tres repartidores y ocho pedidos de ejemplo.
     *
     * @param citylink gestor donde se cargarán los datos iniciales
     * @throws ClienteNoEncontradoException si una búsqueda de cliente falla
     * @throws PedidoInvalidoException si algún pedido de ejemplo no es válido
     * @throws AlmacenamientoLlenoException si algún arreglo no tiene espacio
     */
    public static void cargar(Citylink citylink)
            throws ClienteNoEncontradoException, PedidoInvalidoException, AlmacenamientoLlenoException {
        citylink.agregarCliente(new Cliente("Ana Gómez", "2657000001", 1, "Centro"));
        citylink.agregarCliente(new Cliente("Bruno Díaz", "2657000002", 2, "Estación"));
        citylink.agregarCliente(new Cliente("Carla López", "2657000003", 3, "La Ribera"));
        citylink.agregarCliente(new Cliente("Diego Ruiz", "2657000004", 4, "Banderitas"));
        citylink.agregarCliente(new Cliente("Eva Torres", "2657000005", 5, "Centro"));

        citylink.agregarRepartidor(new Repartidor("Lucas Pérez", "R001", new Bicicleta("A001AAA")));
        citylink.agregarRepartidor(new Repartidor("Sofía Martín", "R002", new Moto("A002BBB")));
        citylink.agregarRepartidor(new Repartidor("Tomás Silva", "R003", new Camioneta("A003CCC")));

        LocalDateTime ahora = LocalDateTime.now();
        citylink.agregarPedido(new PedidoNormal(101, citylink.buscarCliente(1), "Av. Mitre 120", 2, 4, ahora));
        citylink.agregarPedido(new PedidoUrgente(102, citylink.buscarCliente(2), "Belgrano 450", 5, 8, ahora));
        citylink.agregarPedido(new PedidoFragil(103, citylink.buscarCliente(3), "Pedernera 800", 3, 6, ahora));
        citylink.agregarPedido(new PedidoNormal(104, citylink.buscarCliente(4), "Junín 250", 7, 10, ahora));
        citylink.agregarPedido(new PedidoUrgente(105, citylink.buscarCliente(5), "Pringles 90", 1, 3, ahora));
        citylink.agregarPedido(new PedidoFragil(106, citylink.buscarCliente(1), "Lavalle 670", 12, 5, ahora));
        citylink.agregarPedido(new PedidoNormal(107, citylink.buscarCliente(2), "Tucumán 310", 18, 12, ahora));
        citylink.agregarPedido(new PedidoNormal(108, citylink.buscarCliente(3), "España 540", 4, 2, ahora));
    }
}
