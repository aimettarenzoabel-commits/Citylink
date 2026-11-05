package app;

import java.time.LocalDateTime;
import java.util.Arrays;

import excepciones.AlmacenamientoLlenoException;
import excepciones.PedidoInvalidoException;
import modelo.Citylink;
import modelo.Cliente;
import modelo.Moto;
import modelo.Pedido;
import modelo.PedidoNormal;
import modelo.Repartidor;

/**
 * Verifica las correcciones de integridad aplicadas al modelo de CityLink.
 */
public class PruebaCorreccionesModelo {

    public static void main(String[] args) throws Exception {
        probarCodigoDuplicadoComoPedidoInvalido();
        probarAsignacionNoPublica();
        probarProteccionDeConfiguracionDelVehiculo();
        probarProteccionDelVehiculoDelRepartidorRegistrado();
        probarCapacidadMaximaDePedidosPorRepartidor();
    }

    private static void probarCodigoDuplicadoComoPedidoInvalido()
            throws Exception {

        System.out.println("\n===== CODIGO DUPLICADO DE PEDIDO =====");

        Citylink sistema = new Citylink();
        Cliente cliente = new Cliente(
            "Mateo", "2664000000", 1, "Centro"
        );
        sistema.agregarCliente(cliente);

        Pedido primero = new PedidoNormal(
            10, cliente, "Centro", 2, 5, LocalDateTime.now()
        );
        Pedido duplicado = new PedidoNormal(
            10, cliente, "Norte", 3, 7, LocalDateTime.now()
        );

        sistema.agregarPedido(primero);

        try {
            sistema.agregarPedido(duplicado);
            System.out.println("ERROR: se aceptó un código duplicado.");
        } catch (PedidoInvalidoException e) {
            System.out.println(
                "Código duplicado rechazado con PedidoInvalidoException: "
                + e.getMessage()
            );
        }

        System.out.println(
            "Cantidad de pedidos conservada: "
            + (sistema.getCantidadPedidos() == 1)
        );
    }

    private static void probarAsignacionNoPublica() {
        System.out.println("\n===== ENCAPSULAMIENTO DE LA ASIGNACION =====");

        boolean asignacionPublica = Arrays.stream(Pedido.class.getMethods())
            .anyMatch(metodo -> metodo.getName().equals("asignarVehiculo"));

        System.out.println(
            "Pedido no expone asignarVehiculo como método público: "
            + !asignacionPublica
        );
    }

    private static void probarProteccionDeConfiguracionDelVehiculo() throws Exception {
        System.out.println("\n===== PROTECCION DE VEHICULO ASIGNADO =====");

        Moto moto = new Moto("MOTO100");
        Repartidor repartidor = new Repartidor("Juan", "R100", moto);
        Cliente cliente = new Cliente(
            "Ana", "2664111111", 100, "Norte"
        );
        Pedido pedido = new PedidoNormal(
            100, cliente, "Centro", 5, 10, LocalDateTime.now()
        );

        try {
            repartidor.asignarPedido(pedido);
            System.out.println("Asignación inicial: correcta.");
        } catch (Exception e) {
            System.out.println("ERROR inesperado al asignar: " + e.getMessage());
            return;
        }

        comprobarCambioVehiculo("patente", () -> moto.setPatente("OTRA100"));
        comprobarCambioVehiculo("capacidad", () -> moto.setCapacidadkg(100));
        comprobarCambioVehiculo("velocidad", () -> moto.setVelocidadkm(80));
        comprobarCambioVehiculo("costo por km", () -> moto.setCostoPorKm(500));

        System.out.println("Patente conservada: " + moto.getPatente());
        System.out.println("Capacidad conservada: " + moto.getCapacidadkg());
        System.out.println("Velocidad conservada: " + moto.getVelocidadkm());
        System.out.println("Costo por km conservado: " + moto.getCostoPorKm());
    }

    private static void comprobarCambioVehiculo(String dato, Runnable cambio) {
        try {
            cambio.run();
            System.out.println("ERROR: se pudo modificar " + dato + ".");
        } catch (IllegalStateException e) {
            System.out.println(
                "Cambio de " + dato + " rechazado: " + e.getMessage()
            );
        }
    }

    private static void probarProteccionDelVehiculoDelRepartidorRegistrado()
            throws AlmacenamientoLlenoException {

        System.out.println("\n===== VEHICULO DE REPARTIDOR REGISTRADO =====");

        Citylink sistema = new Citylink();
        Repartidor repartidor = new Repartidor(
            "Pedro", "R200", new Moto("MOTO200")
        );
        sistema.agregarRepartidor(repartidor);

        try {
            repartidor.setVehiculo(new Moto("MOTO201"));
            System.out.println("ERROR: se permitió cambiar el vehículo registrado.");
        } catch (IllegalStateException e) {
            System.out.println(
                "Cambio de vehículo registrado rechazado: " + e.getMessage()
            );
        }

        System.out.println(
            "El repartidor conserva su vehículo: "
            + repartidor.getVehiculo().getPatente().equals("MOTO200")
        );
    }

    private static void probarCapacidadMaximaDePedidosPorRepartidor()
            throws Exception {

        System.out.println("\n===== CAPACIDAD DE PEDIDOS DEL REPARTIDOR =====");

        Cliente cliente = new Cliente(
            "Cliente prueba", "2664222222", 300, "Sur"
        );
        Repartidor repartidor = new Repartidor(
            "Luis", "R300", new Moto("MOTO300")
        );

        for (int i = 1; i <= 100; i++) {
            Pedido pedido = new PedidoNormal(
                i, cliente, "Destino " + i, 1, 1, LocalDateTime.now()
            );
            repartidor.asignarPedido(pedido);
        }

        System.out.println(
            "Pedidos asignados al alcanzar 100: "
            + repartidor.getCantidadPedidos()
        );

        Pedido pedido101 = new PedidoNormal(
            101, cliente, "Destino 101", 1, 1, LocalDateTime.now()
        );

        try {
            repartidor.asignarPedido(pedido101);
            System.out.println("ERROR: se permitió asignar el pedido 101.");
        } catch (IllegalStateException e) {
            System.out.println(
                "Pedido 101 rechazado: " + e.getMessage()
            );
        }

        System.out.println(
            "Cantidad final conservada en 100: "
            + (repartidor.getCantidadPedidos() == 100)
        );
    }
}
