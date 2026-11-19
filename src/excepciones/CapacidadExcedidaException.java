package excepciones;

/**
 * Excepción checked que indica que un vehículo no puede transportar un pedido.
 */
public class CapacidadExcedidaException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Ejecuta la operación CapacidadExcedidaException del sistema CityLink.
     * @param mensaje parámetro de la operación
     */
    public CapacidadExcedidaException(String mensaje) {
        super(mensaje);
    }
}
