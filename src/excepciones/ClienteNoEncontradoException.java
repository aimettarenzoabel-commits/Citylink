package excepciones;

/**
 * Excepción checked que indica que no se encontró el cliente solicitado.
 */
public class ClienteNoEncontradoException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Ejecuta la operación ClienteNoEncontradoException del sistema CityLink.
     * @param mensaje parámetro de la operación
     */
    public ClienteNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
