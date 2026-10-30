package modelo;

public class Moto extends Vehiculo {

    // CONSTRUCTOR
    public Moto(String patente) {
        // 30 kg, 40 km/h, $120 por km[cite: 2]
        super(patente, 30, 40, 120); 
    }

    // MÉTODO ABSTRACTO HEREDADO
    @Override
    public double calcularTiempoEntrega(double km) {

        if (km <= 0) {
            throw new IllegalArgumentException(
                "La distancia debe ser positiva"
            );
        }

        // Tiempo base + 5 minutos extra por estacionamiento[cite: 2]
        return ((km / getVelocidadkm()) * 60) + 5;
    }
}