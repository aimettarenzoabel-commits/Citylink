package modelo;

import java.time.LocalDateTime;

import excepciones.CapacidadExcedidaException;
import excepciones.PedidoInvalidoException;
import excepciones.EstadoInvalidoException;

/**
 * Clase abstracta que representa un envío y define sus datos y reglas de estado.
 */
public abstract class Pedido implements Rastreable, Facturable {

    // ATRIBUTOS
    private int codigo;
    private Cliente cliente;
    private String destino;
    private double kg;
    private double km;
    private LocalDateTime fechaHora;
    private String estado;
    private Vehiculo vehiculoAsignado;
    private boolean registradoEnCitylink;

    // CONSTRUCTOR COMPLETO
    /**
     * Ejecuta la operación Pedido del sistema CityLink.
     * @param codigo parámetro de la operación
     * @param cliente parámetro de la operación
     * @param destino parámetro de la operación
     * @param kg parámetro de la operación
     * @param km parámetro de la operación
     * @param fechaHora parámetro de la operación
     * @throws PedidoInvalidoException cuando no se puede completar la operación
     */
    public Pedido(int codigo, Cliente cliente, String destino,
                  double kg, double km, LocalDateTime fechaHora)
                  throws PedidoInvalidoException {

        this.codigo = validarCodigo(codigo);
        this.cliente = validarCliente(cliente);
        this.destino = validarDestino(destino);
        this.kg = validarPeso(kg);
        this.km = validarDistancia(km);
        this.fechaHora = validarFechaHora(fechaHora);
        this.estado = "PENDIENTE";
        this.registradoEnCitylink = false;
    }

    // GETTERS
    /**
     * Devuelve el código identificador.
     * @return resultado de la operación
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Devuelve el valor de cliente.
     * @return resultado de la operación
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Devuelve el destino del pedido.
     * @return resultado de la operación
     */
    public String getDestino() {
        return destino;
    }

    /**
     * Devuelve el peso del pedido en kilogramos.
     * @return resultado de la operación
     */
    public double getKg() {
        return kg;
    }

    /**
     * Devuelve la distancia del pedido en kilómetros.
     * @return resultado de la operación
     */
    public double getKm() {
        return km;
    }

    /**
     * Devuelve la fecha y hora de ingreso del pedido.
     * @return resultado de la operación
     */
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    /**
     * Devuelve el estado actual del pedido.
     * @return resultado de la operación
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Devuelve el valor de vehiculoasignado.
     * @return resultado de la operación
     */
    public Vehiculo getVehiculoAsignado() {
        return vehiculoAsignado;
    }

    // VALIDACIONES PRIVADAS COMPARTIDAS POR CONSTRUCTOR Y SETTERS
    private static int validarCodigo(int codigo) throws PedidoInvalidoException {
        if (codigo <= 0) {
            throw new PedidoInvalidoException(
                "El código debe ser positivo"
            );
        }
        return codigo;
    }

    private static Cliente validarCliente(Cliente cliente) throws PedidoInvalidoException {
        if (cliente == null) {
            throw new PedidoInvalidoException(
                "El cliente no puede ser nulo"
            );
        }
        return cliente;
    }

    private static String validarDestino(String destino) throws PedidoInvalidoException {
        if (destino == null || destino.isBlank()) {
            throw new PedidoInvalidoException(
                "El destino no puede estar vacío"
            );
        }
        return destino;
    }

    private static double validarPeso(double kg) throws PedidoInvalidoException {
        if (!Double.isFinite(kg) || kg <= 0) {
            throw new PedidoInvalidoException(
                "El peso debe ser positivo y finito"
            );
        }
        return kg;
    }

    private static double validarDistancia(double km) throws PedidoInvalidoException {
        if (!Double.isFinite(km) || km <= 0) {
            throw new PedidoInvalidoException(
                "La distancia debe ser positiva y finita"
            );
        }
        return km;
    }

    private static LocalDateTime validarFechaHora(LocalDateTime fechaHora)
            throws PedidoInvalidoException {
        if (fechaHora == null) {
            throw new PedidoInvalidoException(
                "La fecha y hora no pueden ser nulas"
            );
        }
        return fechaHora;
    }

    // IMPIDE CAMBIOS CUANDO EL PEDIDO YA FUE ASIGNADO O DEJO DE ESTAR PENDIENTE
    private void validarDatosModificables() {
        if (vehiculoAsignado != null || !estado.equals("PENDIENTE")) {
            throw new IllegalStateException(
                "No se pueden modificar los datos de un pedido después de asignarlo o cuando ya no está PENDIENTE"
            );
        }
    }

    // IMPIDE CAMBIAR LA IDENTIDAD O EL CLIENTE UNA VEZ REGISTRADO EN CITYLINK
    private void validarIdentidadModificable() {
        if (registradoEnCitylink) {
            throw new IllegalStateException(
                "No se puede modificar el código ni el cliente de un pedido ya registrado en Citylink"
            );
        }
    }

    // SETTERS CON VALIDACION
    /**
     * Valida y establece el código identificador.
     * @param codigo parámetro de la operación
     * @throws PedidoInvalidoException cuando no se puede completar la operación
     */
    public void setCodigo(int codigo) throws PedidoInvalidoException {
        validarDatosModificables();
        validarIdentidadModificable();
        this.codigo = validarCodigo(codigo);
    }

    /**
     * Valida y actualiza el valor de cliente.
     * @param cliente parámetro de la operación
     * @throws PedidoInvalidoException cuando no se puede completar la operación
     */
    public void setCliente(Cliente cliente) throws PedidoInvalidoException {
        validarDatosModificables();
        validarIdentidadModificable();
        this.cliente = validarCliente(cliente);
    }

    /**
     * Valida y establece el destino del pedido.
     * @param destino parámetro de la operación
     * @throws PedidoInvalidoException cuando no se puede completar la operación
     */
    public void setDestino(String destino) throws PedidoInvalidoException {
        validarDatosModificables();
        this.destino = validarDestino(destino);
    }

    /**
     * Valida y establece el peso del pedido.
     * @param kg parámetro de la operación
     * @throws PedidoInvalidoException cuando no se puede completar la operación
     */
    public void setKg(double kg) throws PedidoInvalidoException {
        validarDatosModificables();
        this.kg = validarPeso(kg);
    }

    /**
     * Valida y establece la distancia del pedido.
     * @param km parámetro de la operación
     * @throws PedidoInvalidoException cuando no se puede completar la operación
     */
    public void setKm(double km) throws PedidoInvalidoException {
        validarDatosModificables();
        this.km = validarDistancia(km);
    }

    /**
     * Valida y establece la fecha y hora de ingreso.
     * @param fechaHora parámetro de la operación
     * @throws PedidoInvalidoException cuando no se puede completar la operación
     */
    public void setFechaHora(LocalDateTime fechaHora) throws PedidoInvalidoException {
        validarDatosModificables();
        this.fechaHora = validarFechaHora(fechaHora);
    }

    // METODOS DE USO INTERNO DEL GESTOR (MISMO PAQUETE)
    boolean estaRegistradoEnCitylink() {
        return registradoEnCitylink;
    }

    void marcarComoRegistradoEnCitylink() {
        if (registradoEnCitylink) {
            throw new IllegalStateException(
                "El pedido ya está registrado en un sistema Citylink"
            );
        }
        registradoEnCitylink = true;
    }

    void desmarcarComoRegistradoEnCitylink() {
        registradoEnCitylink = false;
    }

    // CAMBIAR ESTADO
    /**
     * Cambia el estado si la transición solicitada está permitida.
     * @param nuevoEstado parámetro de la operación
     */
    public void cambiarEstado(String nuevoEstado) {
        if (nuevoEstado == null || nuevoEstado.isBlank()) {
            throw new EstadoInvalidoException(
                "El nuevo estado no puede ser nulo ni vacío"
            );
        }

        if (nuevoEstado.equals("EN_CAMINO") && vehiculoAsignado == null) {
            throw new EstadoInvalidoException(
                "No se puede iniciar el envío sin un vehículo asignado"
            );
        }

        boolean cambioPermitido = false;

        if (estado.equals("PENDIENTE")) {
            if (nuevoEstado.equals("EN_CAMINO") ||
                nuevoEstado.equals("CANCELADO")) {
                cambioPermitido = true;
            }
        } else if (estado.equals("EN_CAMINO")) {
            if (nuevoEstado.equals("ENTREGADO")) {
                cambioPermitido = true;
            }
        }

        if (!cambioPermitido) {
            throw new EstadoInvalidoException(
                "No se puede cambiar de " + estado +
                " a " + nuevoEstado
            );
        }

        this.estado = nuevoEstado;
    }

    // METODO DE LA INTERFAZ RASTREABLE
    /**
     * Devuelve el estado actual de la entidad.
     * @return resultado de la operación
     */
    @Override
    public String obtenerEstadoActual() {
        return estado;
    }

    // METODO ABSTRACTO
    /**
     * Indica si el pedido requiere manipulación como paquete frágil.
     * Las subclases redefinen este método cuando corresponde.
     * @return true si el pedido es frágil; false en caso contrario
     */
    public boolean esFragil() {
        return false;
    }

    /**
     * Calcula el costo según el tipo de pedido y el vehículo recibido.
     * @param vehiculo vehículo que realizará el envío
     * @return costo total del envío
     */
    public abstract double calcularCosto(Vehiculo vehiculo);

    // METODO DE LA INTERFAZ FACTURABLE
    /**
     * Calcula el importe o comisión definido por el contrato Facturable.
     * @return resultado de la operación
     */
    @Override
    public double calcularImporte() {
        if (vehiculoAsignado == null) {
            throw new IllegalStateException(
                "No se puede calcular el importe: el pedido no tiene un vehículo asignado"
            );
        }

        return calcularCosto(vehiculoAsignado);
    }

    // TOSTRING
    /**
     * Devuelve una representación textual de los datos del objeto.
     * @return resultado de la operación
     */
    @Override
    public String toString() {
        return "Pedido: " + codigo
            + "\nCliente: " + cliente
            + "\nDestino: " + destino
            + "\nPeso: " + kg + " kg"
            + "\nDistancia: " + km + " km"
            + "\nFecha y hora de ingreso: " + fechaHora
            + "\nEstado: " + estado;
    }

    // ASIGNAR VEHICULO
    // Operación interna del paquete modelo: la asignación debe realizarse
    // a través de Repartidor para mantener sincronizados ambos objetos.
    void asignarVehiculo(Vehiculo vehiculo)
            throws CapacidadExcedidaException {
        if (vehiculo == null) {
            throw new IllegalArgumentException(
                "El vehículo no puede ser nulo"
            );
        }

        if (vehiculoAsignado != null) {
            throw new IllegalStateException(
                "El pedido ya tiene un vehículo asignado"
            );
        }

        if (!estado.equals("PENDIENTE")) {
            throw new IllegalStateException(
                "Solo se pueden asignar pedidos que estén PENDIENTES"
            );
        }

        if (!vehiculo.puedeTransportar(this)) {
            throw new CapacidadExcedidaException(
                "El vehículo no puede transportar este pedido"
            );
        }

        // Se bloquean los cambios de configuración del vehículo desde que
        // participa en al menos una asignación.
        vehiculo.registrarPedidoAsignado();
        this.vehiculoAsignado = vehiculo;
    }
}
