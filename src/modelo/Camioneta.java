package modelo;

public class Camioneta extends Vehiculo {

    // CONSTRUCTOR
    public Camioneta(String patente) {
        // 500 kg, 30 km/h, $300 por km[cite: 2]
        super(patente, 500, 30, 300);
    }

    // MÉTODO ABSTRACTO HEREDADO
    @Override
    public double calcularTiempoEntrega(double km) {

        if (km <= 0) {
            throw new IllegalArgumentException(
                "La distancia debe ser positiva"
            );
        }

        // Tiempo base + 10 minutos extra por carga y descarga[cite: 2]
        return ((km / getVelocidadkm()) * 60) + 10;
    }
}
