package modelo;

/**
 * Clase abstracta que define las características y operaciones comunes de un vehículo.
 */
public abstract class Vehiculo {

    // ATRIBUTOS
    private String patente;
    private double capacidadkg;
    private double velocidadkm;
    private double costoPorKm;
    private boolean tienePedidosAsignados;

    // CONSTRUCTOR VACIO
    /**
     * Construye un vehículo sin datos inicializados.
     */
    public Vehiculo() {
        this.tienePedidosAsignados = false;
    }

    // CONSTRUCTOR COMPLETO
    /**
     * Construye un vehículo sin datos inicializados.
     * @param patente parámetro de la operación
     * @param capacidadkg parámetro de la operación
     * @param velocidadkm parámetro de la operación
     * @param costoPorKm parámetro de la operación
     */
    public Vehiculo(String patente, double capacidadkg, double velocidadkm, double costoPorKm) {
        // Se validan los datos sin invocar setters sobrescribibles durante
        // la construccion del objeto.
        this.patente = validarPatente(patente);
        this.capacidadkg = validarValorPositivoFinito(
            capacidadkg,
            "La capacidad debe ser positiva y finita"
        );
        this.velocidadkm = validarValorPositivoFinito(
            velocidadkm,
            "La velocidad debe ser positiva y finita"
        );
        this.costoPorKm = validarValorPositivoFinito(
            costoPorKm,
            "El costo por km debe ser positivo y finito"
        );
        this.tienePedidosAsignados = false;
    }

    // GETTERS
    /**
     * Devuelve la patente del vehículo.
     * @return resultado de la operación
     */
    public String getPatente() {
        return patente;
    }

    /**
     * Devuelve la capacidad del vehículo en kilogramos.
     * @return resultado de la operación
     */
    public double getCapacidadkg() {
        return capacidadkg;
    }

    /**
     * Devuelve la velocidad promedio del vehículo en kilómetros por hora.
     * @return resultado de la operación
     */
    public double getVelocidadkm() {
        return velocidadkm;
    }

    /**
     * Devuelve el costo por kilómetro del vehículo.
     * @return resultado de la operación
     */
    public double getCostoPorKm() {
        return costoPorKm;
    }

    // VALIDACIONES PRIVADAS COMPARTIDAS POR CONSTRUCTOR Y SETTERS
    private static String validarPatente(String patente) {
        if (patente == null || patente.isBlank()) {
            throw new IllegalArgumentException(
                "La patente no puede estar vacía"
            );
        }
        return patente;
    }

    private static double validarValorPositivoFinito(double valor, String mensajeError) {
        if (!Double.isFinite(valor) || valor <= 0) {
            throw new IllegalArgumentException(mensajeError);
        }
        return valor;
    }

    // IMPIDE ALTERAR LA CONFIGURACION DESPUES DE LA PRIMERA ASIGNACION
    private void validarConfiguracionModificable() {
        if (tienePedidosAsignados) {
            throw new IllegalStateException(
                "No se puede modificar la configuración de un vehículo que ya tiene pedidos asignados"
            );
        }
    }

    // SETTERS CON VALIDACION
    /**
     * Valida y establece la patente del vehículo.
     * @param patente parámetro de la operación
     */
    public void setPatente(String patente) {
        validarConfiguracionModificable();
        this.patente = validarPatente(patente);
    }

    /**
     * Valida y establece la capacidad del vehículo.
     * @param capacidadkg parámetro de la operación
     */
    public void setCapacidadkg(double capacidadkg) {
        validarConfiguracionModificable();
        this.capacidadkg = validarValorPositivoFinito(
            capacidadkg,
            "La capacidad debe ser positiva y finita"
        );
    }

    /**
     * Valida y establece la velocidad promedio.
     * @param velocidadkm parámetro de la operación
     */
    public void setVelocidadkm(double velocidadkm) {
        validarConfiguracionModificable();
        this.velocidadkm = validarValorPositivoFinito(
            velocidadkm,
            "La velocidad debe ser positiva y finita"
        );
    }

    /**
     * Valida y establece el costo por kilómetro.
     * @param costoPorKm parámetro de la operación
     */
    public void setCostoPorKm(double costoPorKm) {
        validarConfiguracionModificable();
        this.costoPorKm = validarValorPositivoFinito(
            costoPorKm,
            "El costo por km debe ser positivo y finito"
        );
    }

    // METODO INTERNO DEL PAQUETE MODELO, INVOCADO POR Pedido
    void registrarPedidoAsignado() {
        tienePedidosAsignados = true;
    }

    // METODO ABSTRACTO
    // Cada vehiculo calcula el tiempo de entrega segun su velocidad y tiempo extra.
    /**
     * Calcula el tiempo estimado para recorrer la distancia recibida, incluidos los minutos extra.
     * @param km parámetro de la operación
     * @return resultado de la operación
     */
    public abstract double calcularTiempoEntrega(double km);

    // METODO CONCRETO
    // Comprueba si el vehiculo puede transportar el peso del pedido.
    /**
     * Indica si el vehículo puede transportar el pedido según sus restricciones.
     * @param pedido parámetro de la operación
     * @return resultado de la operación
     */
    public boolean puedeTransportar(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException(
                "El pedido no puede ser nulo"
            );
        }

        return pedido.getKg() <= capacidadkg;
    }

    // TOSTRING
    /**
     * Devuelve una representación textual de los datos del objeto.
     * @return resultado de la operación
     */
    @Override
    public String toString() {
        return "Patente: " + patente
            + "\nCapacidad: " + capacidadkg + " kg"
            + "\nVelocidad: " + velocidadkm + " km/h"
            + "\nCosto por km: $" + costoPorKm;
    }
}
