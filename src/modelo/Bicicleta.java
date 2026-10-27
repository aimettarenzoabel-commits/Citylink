package modelo;

public class Bicicleta extends Vehiculo {

    // CONSTRUCTOR
    public Bicicleta(String patente) {
        super(patente, 10, 15, 50);
    }

    // MÉTODO ABSTRACTO HEREDADO
    @Override
    public double calcularTiempoEntrega(double km) {

        if (km <= 0) {
            throw new IllegalArgumentException(
                "La distancia debe ser positiva"
            );
        }

        return (km / getVelocidadkm()) * 60;
    }

    // MÉTODO PUEDE TRANSPORTAR

    @Override
    public boolean puedeTransportar(Pedido pedido) {

        if (pedido == null) {
            throw new IllegalArgumentException(
                "El pedido no puede ser nulo"
            );
        }

        // La bicicleta no transporta pedidos frágiles
        if (pedido instanceof PedidoFragil) { //Comprueba si el objeto recibido es de tipo PedidoFragil. Si lo es, devuelve true y nuestro método rechaza el transporte mediante return false.
            return false;
        }

        // Verifica si el peso supera la capacidad
        return super.puedeTransportar(pedido); //En lugar de volver a escribir la validación del peso, llamamos al método que ya creamos en la clase padre Vehiculo.
    }
}