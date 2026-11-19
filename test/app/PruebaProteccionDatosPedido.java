package app;

import java.time.LocalDateTime;
import modelo.Cliente;
import modelo.Moto;
import modelo.Pedido;
import modelo.PedidoNormal;
import modelo.Repartidor;

public class PruebaProteccionDatosPedido {

    @FunctionalInterface
    private interface ModificacionPedido {
        void ejecutar() throws Exception;
    }

    public static void main(String[] args) throws Exception {

        Cliente cliente = new Cliente(
            "Mateo", "2664000000", 1, "Centro"
        );

        Cliente otroCliente = new Cliente(
            "Juan", "2664111111", 2, "Norte"
        );

        Moto moto = new Moto("ABC123");
        Repartidor repartidor = new Repartidor("Juan", "R001", moto);

        Pedido pedido = new PedidoNormal(
            10, cliente, "Centro", 5, 12, LocalDateTime.now()
        );

        // Antes de asignarlo, los setters permiten corregir datos válidos.
        pedido.setCodigo(11);
        pedido.setDestino("Centro actualizado");
        pedido.setKg(8);
        pedido.setKm(15);
        pedido.setFechaHora(LocalDateTime.now().plusMinutes(1));

        System.out.println("Modificaciones antes de asignar: permitidas.");

        // Al asignarlo, los datos logísticos quedan protegidos.
        repartidor.asignarPedido(pedido);
        System.out.println("Asignación del pedido: correcta.");

        // Intentos de modificación posteriores a la asignación.
        comprobarBloqueo("Cambiar código", () -> pedido.setCodigo(99));
        comprobarBloqueo("Cambiar cliente", () -> pedido.setCliente(otroCliente));
        comprobarBloqueo("Cambiar destino", () -> pedido.setDestino("Sur"));
        comprobarBloqueo("Cambiar peso", () -> pedido.setKg(40));
        comprobarBloqueo("Cambiar distancia", () -> pedido.setKm(50));
        comprobarBloqueo(
            "Cambiar fecha y hora",
            () -> pedido.setFechaHora(LocalDateTime.now().plusDays(1))
        );

        // Se comprueba que los datos originales se conservaron.
        System.out.println("\n========== DATOS CONSERVADOS ==========");
        System.out.println("Código: " + pedido.getCodigo());
        System.out.println("Cliente: " + pedido.getCliente().getNombre());
        System.out.println("Destino: " + pedido.getDestino());
        System.out.println("Peso: " + pedido.getKg() + " kg");
        System.out.println("Distancia: " + pedido.getKm() + " km");
        System.out.println("Estado: " + pedido.obtenerEstadoActual());
        System.out.println("Vehículo: " + pedido.getVehiculoAsignado().getPatente());
    }

    private static void comprobarBloqueo(
            String descripcion, ModificacionPedido modificacion) {

        try {
            modificacion.ejecutar();
            System.out.println("ERROR: se permitió " + descripcion + ".");
        } catch (IllegalStateException e) {
            System.out.println(
                descripcion + " rechazada: " + e.getMessage()
            );
        } catch (Exception e) {
            System.out.println(
                "ERROR inesperado al " + descripcion + ": " + e.getMessage()
            );
        }
    }
}
