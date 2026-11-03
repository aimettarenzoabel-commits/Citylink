package modelo;

/**
 * Contrato para las entidades cuyo estado actual puede consultarse.
 */
public interface Rastreable {

    /**
     * Devuelve el estado actual de la entidad.
     * @return estado actual
     */
    String obtenerEstadoActual();
}
