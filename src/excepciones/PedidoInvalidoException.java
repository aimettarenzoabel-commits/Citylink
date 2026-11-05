package excepciones;

/**
 * Excepción checked que indica que los datos de un pedido no cumplen las reglas de validación.
 */
public class PedidoInvalidoException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Ejecuta la operación PedidoInvalidoException del sistema CityLink.
     * @param mensaje parámetro de la operación
     */
    public PedidoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
