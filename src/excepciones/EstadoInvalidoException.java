package excepciones;

/**
 * Excepción unchecked que indica que se intentó realizar una transición de estado no permitida.
 */
public class EstadoInvalidoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Ejecuta la operación EstadoInvalidoException del sistema CityLink.
     * @param mensaje parámetro de la operación
     */
    public EstadoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
