package app;

import java.time.LocalDateTime;

import excepciones.AlmacenamientoLlenoException;
import excepciones.CapacidadExcedidaException;
import excepciones.ClienteNoEncontradoException;
import excepciones.PedidoInvalidoException;
import modelo.Bicicleta;
import modelo.Camioneta;
import modelo.Citylink;
import modelo.Cliente;
import modelo.Moto;
import modelo.Pedido;
import modelo.PedidoFragil;
import modelo.PedidoNormal;
import modelo.PedidoUrgente;
import modelo.Repartidor;
import modelo.Vehiculo;

/**
 * Prueba los listados, cálculos polimórficos, estados e importes del gestor.
 */
public class PruebaConsultasCitylink {

    public static void main(String[] args)
            throws PedidoInvalidoException,
                   AlmacenamientoLlenoException,
                   ClienteNoEncontradoException,
                   CapacidadExcedidaException {

        Citylink sistema = new Citylink();

        Cliente cliente = new Cliente(
            "Mateo", "2664000000", 1, "Centro"
        );
        sistema.agregarCliente(cliente);

        Repartidor repartidorMoto = new Repartidor(
            "Juan", "R001", new Moto("MOTO01")
        );
        Repartidor repartidorCamioneta = new Repartidor(
            "Pedro", "R002", new Camioneta("CAMI01")
        );
        Repartidor repartidorBicicleta = new Repartidor(
            "Luis", "R003", new Bicicleta("BICI01")
        );

        sistema.agregarRepartidor(repartidorMoto);
        sistema.agregarRepartidor(repartidorCamioneta);
        sistema.agregarRepartidor(repartidorBicicleta);

        Pedido normal = new PedidoNormal(
            1, cliente, "Microcentro", 8, 10, LocalDateTime.now()
        );
        Pedido urgente = new PedidoUrgente(
            2, cliente, "Terminal", 10, 10, LocalDateTime.now()
        );
        Pedido fragil = new PedidoFragil(
            3, cliente, "Hospital", 5, 15, LocalDateTime.now()
        );
        Pedido sinAsignar = new PedidoNormal(
            4, cliente, "Barrio Sur", 3, 5, LocalDateTime.now()
        );

        sistema.agregarPedido(normal);
        sistema.agregarPedido(urgente);
        sistema.agregarPedido(fragil);
        sistema.agregarPedido(sinAsignar);

        sistema.asignarPedido(1, "R001");
        sistema.asignarPedido(2, "R002");
        sistema.asignarPedido(3, "R001");

        sistema.cambiarEstadoPedido(1, "EN_CAMINO");
        sistema.registrarEntrega(1, "R001");
        sistema.cambiarEstadoPedido(2, "EN_CAMINO");
        sistema.registrarEntrega(2, "R002");

        System.out.println("===== LISTADO POLIMORFICO DE PEDIDOS =====");
        for (Pedido pedido : sistema.getPedidos()) {
            System.out.println("\nCódigo: " + pedido.getCodigo());
            System.out.println("Tipo: " + pedido.getClass().getSimpleName());
            System.out.println("Estado: " + pedido.obtenerEstadoActual());

            Vehiculo vehiculo = pedido.getVehiculoAsignado();
            if (vehiculo == null) {
                System.out.println("Vehículo: SIN ASIGNAR");
                System.out.println("Costo: pendiente de asignación");
                System.out.println("Tiempo: pendiente de asignación");
            } else {
                System.out.println("Vehículo: " + vehiculo.getPatente());
                System.out.println(
                    "Costo: $" + sistema.calcularCostoPedido(pedido.getCodigo())
                );
                System.out.println(
                    "Tiempo estimado: "
                    + sistema.calcularTiempoEstimadoPedido(pedido.getCodigo())
                    + " minutos"
                );
            }
        }

        System.out.println("\n===== IMPORTES Y COMISIONES =====");
        System.out.println(
            "Importe total de pedidos asignados: $"
            + sistema.calcularImporteTotalPedidos()
        );
        System.out.println(
            "Pedidos sin asignar: " + sistema.contarPedidosSinAsignar()
        );

        for (Repartidor repartidor : sistema.getRepartidores()) {
            System.out.println(
                "Comisión de " + repartidor.getNombre() + ": $"
                + sistema.calcularComisionRepartidor(repartidor.getLegajo())
            );
            System.out.println(
                "Estado de " + repartidor.getNombre() + ": "
                + sistema.consultarEstadoRepartidor(repartidor.getLegajo())
            );
        }

        System.out.println(
            "Comisiones totales: $" + sistema.calcularComisionesTotales()
        );
        System.out.println(
            "Estado del pedido 3: " + sistema.consultarEstadoPedido(3)
        );

        System.out.println("\n===== VALIDACIONES DE CONSULTA =====");
        try {
            sistema.calcularCostoPedido(4);
        } catch (IllegalStateException e) {
            System.out.println("Costo sin vehículo rechazado: " + e.getMessage());
        }

        try {
            sistema.calcularTiempoEstimadoPedido(4);
        } catch (IllegalStateException e) {
            System.out.println("Tiempo sin vehículo rechazado: " + e.getMessage());
        }

        System.out.println("\n===== POLIMORFISMO CON ARREGLO DE VEHICULOS =====");
        Vehiculo[] vehiculos = {
            new Bicicleta("BICI03"),
            new Moto("MOTO03"),
            new Camioneta("CAMI03")
        };

        for (Vehiculo vehiculo : vehiculos) {
            System.out.println(
                vehiculo.getClass().getSimpleName()
                + " - tiempo para 10 km: "
                + vehiculo.calcularTiempoEntrega(10)
                + " minutos"
            );
        }

        System.out.println("\n===== VALIDACIÓN DE DISTANCIA =====");
        validarDistanciaNoFinita("Bicicleta", new Bicicleta("BICI02"));
        validarDistanciaNoFinita("Moto", new Moto("MOTO02"));
        validarDistanciaNoFinita("Camioneta", new Camioneta("CAMI02"));
    }

    private static void validarDistanciaNoFinita(String tipo, Vehiculo vehiculo) {
        try {
            vehiculo.calcularTiempoEntrega(Double.NaN);
            System.out.println("ERROR: " + tipo + " aceptó NaN");
        } catch (IllegalArgumentException e) {
            System.out.println(tipo + " rechazó NaN correctamente");
        }
    }
}
