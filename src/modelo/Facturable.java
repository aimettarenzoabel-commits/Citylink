package modelo;

/**
 * Contrato para los objetos que pueden calcular un importe monetario.
 */
public interface Facturable {

    /**
     * Calcula el importe asociado al objeto.
     * @return importe calculado
     */
    double calcularImporte();
}
