package modelo;

public abstract class Vehiculo{

    //ATRIBUTOS
    private String patente;
    private double capacidadkg;
    private double velocidadkm;
    private double costoPorKm;

    // CONSTRUCTOR VACÍO
    public Vehiculo() {
    }

    // CONSTRUCTOR COMPLETO
    public Vehiculo(String patente, double capacidadkg, double velocidadkm, double costoPorKm) {
        setPatente(patente);
        setCapacidadkg(capacidadkg);
        setVelocidadkm(velocidadkm);
        setCostoPorKm(costoPorKm);
    }

    // GETTERS
    public String getPatente() {
        return patente;
    }

    public double getCapacidadkg() {
        return capacidadkg;
    }

    public double getVelocidadkm() {
        return velocidadkm;
    }

    public double getCostoPorKm() {
        return costoPorKm;
    }

    // SETTERS CON VALIDACIÓN
    public void setPatente(String patente) {
        if (patente == null || patente.isBlank()) {
            throw new IllegalArgumentException("La patente no puede estar vacía");
        }
        this.patente = patente;
    }

    public void setCapacidadkg(double capacidadkg) {
        if (capacidadkg <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser positiva");
        }
        this.capacidadkg = capacidadkg;
    }

    public void setVelocidadkm(double velocidadkm) {
        if (velocidadkm <= 0) {
            throw new IllegalArgumentException("La velocidad debe ser positiva");
        }
        this.velocidadkm = velocidadkm;
    }

    public void setCostoPorKm(double costoPorKm) {
        if (costoPorKm <= 0) {
            throw new IllegalArgumentException("El costo por km no puede ser negativo");
        }
        this.costoPorKm = costoPorKm;
    }

    //MÉTODO ABSTRACTO
    //Cada vehículo tendrá una forma distinta de calcular su tiempo de entrega según su velocidad y los minutos adicionales
    public abstract double calcularTiempoEntrega(double km); 

    //MÉTODO CONCRETO
    //Este método comprueba la capacidad por peso.
    public boolean puedeTransportar(Pedido pedido) {

        if (pedido == null) {
            throw new IllegalArgumentException(
                "El pedido no puede ser nulo"
            );
        }

        return pedido.getKg() <= capacidadkg; 
    }

    //TOSTRING
    @Override
    public String toString() {
        return 
        "Patente: " + patente +
        "\nCapacidad: " + capacidadkg + " kg" +
        "\nVelocidad: " + velocidadkm + " km/h" +
        "\nCosto por km: $" + costoPorKm;
    }
}