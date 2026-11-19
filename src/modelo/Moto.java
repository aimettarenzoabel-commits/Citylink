package modelo;

/**
 * Vehículo motorizado de dos ruedas con tiempo adicional de estacionamiento.
 */
public class Moto extends Vehiculo {

    // CONSTRUCTOR
    /**
     * Construye una moto con sus valores de capacidad, velocidad y costo por kilómetro.
     * @param patente parámetro de la operación
     */
    public Moto(String patente) {
        // 30 kg, 40 km/h, $120 por km[cite: 2]
        super(patente, 30, 40, 120); 
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

        // Tiempo base + 5 minutos extra por estacionamiento[cite: 2]
        return ((km / getVelocidadkm()) * 60) + 5;
    }
}