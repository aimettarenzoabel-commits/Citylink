package modelo;

import java.time.LocalDateTime;
import excepciones.PedidoInvalidoException;

/**
 * Pedido cuyo costo incorpora un adicional fijo por manipulación especial.
 */
public class PedidoFragil extends Pedido{
    //CONSTRUCTOR
    /**
     * Construye un pedido frágil con sus datos de ingreso.
     * @param codigo parámetro de la operación
     * @param cliente parámetro de la operación
     * @param destino parámetro de la operación
     * @param kg parámetro de la operación
     * @param km parámetro de la operación
     * @param fechaHora parámetro de la operación
     * @throws PedidoInvalidoException cuando no se puede completar la operación
     */
    public PedidoFragil(int codigo, Cliente cliente, String destino, double kg, double km, LocalDateTime fechaHora)
    throws PedidoInvalidoException {
        super(codigo, cliente, destino, kg, km, fechaHora); //En lugar de repetir todos los atributos, getters y setters dentro de PedidoFragil, llamamos al constructor de Pedido.
    }
    /**
     * Indica que este pedido requiere manipulación especial.
     * @return true porque el pedido es frágil
     */
    @Override
    public boolean esFragil() {
        return true;
    }

    //CALCULAR COSTO
    /**
     * Calcula el costo según el tipo de pedido y el vehículo recibido.
     * @param vehiculo parámetro de la operación
     * @return resultado de la operación
     */
    @Override
    public double calcularCosto(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new IllegalArgumentException("El vehículo no puede ser nulo");
        }

        if (!vehiculo.puedeTransportar(this)) {
            throw new IllegalArgumentException("El vehículo no puede transportar este pedido");
        }

        double costoBase = 1000;
        double costoDistancia = getKm() * vehiculo.getCostoPorKm();
        double recargoFragil = 800;

        return costoBase + costoDistancia + recargoFragil;
    }
}
