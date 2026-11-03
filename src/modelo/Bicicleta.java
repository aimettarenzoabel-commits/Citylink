package modelo;

/**
 * Vehículo liviano que no puede transportar pedidos frágiles.
 */
public class Bicicleta extends Vehiculo {

    // CONSTRUCTOR
    /**
     * Construye una bicicleta con sus valores de capacidad, velocidad y costo por kilómetro.
     * @param patente parámetro de la operación
     */
    public Bicicleta(String patente) {
        super(patente, 10, 15, 50);
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

        return (km / getVelocidadkm()) * 60;
    }

    // MÉTODO PUEDE TRANSPORTAR
    /**
     * Indica si el vehículo puede transportar el pedido según sus restricciones.
     * @param pedido parámetro de la operación
     * @return resultado de la operación
     */

    @Override
    public boolean puedeTransportar(Pedido pedido) {

        if (pedido == null) {
            throw new IllegalArgumentException(
                "El pedido no puede ser nulo"
            );
        }

        // La bicicleta no transporta pedidos frágiles
        if (pedido.esFragil()) { //Comprueba si el objeto recibido es de tipo PedidoFragil. Si lo es, devuelve true y nuestro método rechaza el transporte mediante return false.
            return false;
        }

        // Verifica si el peso supera la capacidad
        return super.puedeTransportar(pedido); //En lugar de volver a escribir la validación del peso, llamamos al método que ya creamos en la clase padre Vehiculo.
    }
}