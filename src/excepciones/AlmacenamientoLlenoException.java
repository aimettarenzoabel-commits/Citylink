package excepciones;

/**
 * Excepción checked que indica que un arreglo del sistema alcanzó su capacidad máxima.
 */
public class AlmacenamientoLlenoException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Ejecuta la operación AlmacenamientoLlenoException del sistema CityLink.
     * @param mensaje parámetro de la operación
     */
    public AlmacenamientoLlenoException(String mensaje) {
        super(mensaje);
    }
}
