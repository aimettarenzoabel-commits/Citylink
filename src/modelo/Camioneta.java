package modelo;

/**
 * Vehículo de gran capacidad con tiempo adicional de carga y descarga.
 */
public class Camioneta extends Vehiculo {

    // CONSTRUCTOR
    /**
     * Construye una camioneta con sus valores de capacidad, velocidad y costo por kilómetro.
     * @param patente parámetro de la operación
     */
    public Camioneta(String patente) {
        // 500 kg, 30 km/h, $300 por km[cite: 2]
        super(patente, 500, 30, 300);
    }

    // MÉTODO ABSTRACTO HEREDADO
    /**
     * Calcula el tiempo estimado para recorrer la distancia recibida, incluidos los minutos extra.
     * @param km parámetro de la operación
     * @return resultado de la operación
     */
    @Override
    public double calcularTiempoEntrega(double km) {

        if (!Double.isFinite(km) || km <= 0) {
            throw new IllegalArgumentException(
                "La distancia debe ser positiva y finita"
            );
        }

        // Tiempo base + 10 minutos extra por carga y descarga[cite: 2]
        return ((km / getVelocidadkm()) * 60) + 10;
    }
}
