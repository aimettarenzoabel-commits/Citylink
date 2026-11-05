package modelo;

import excepciones.CapacidadExcedidaException;

/**
 * Representa a un trabajador, su vehículo y los pedidos que tiene a cargo.
 */
public class Repartidor implements Rastreable, Facturable {

    // Citylink admite como máximo 100 pedidos; un repartidor puede tener
    // asignados hasta todos los pedidos del sistema.
    private static final int MAX_PEDIDOS = 100;

    // ATRIBUTOS
    private String nombre;
    private String legajo;
    private Vehiculo vehiculo;
    private Pedido[] pedidos;
    private int cantidadPedidos;
    private boolean registradoEnCitylink;

    // CONSTRUCTOR VACIO
    /**
     * Construye un repartidor sin pedidos asignados.
     */
    public Repartidor() {
        this.pedidos = new Pedido[MAX_PEDIDOS];
        this.cantidadPedidos = 0;
        this.registradoEnCitylink = false;
    }

    // CONSTRUCTOR COMPLETO
    /**
     * Construye un repartidor sin pedidos asignados.
     * @param nombre parámetro de la operación
     * @param legajo parámetro de la operación
     * @param vehiculo parámetro de la operación
     */
    public Repartidor(String nombre, String legajo, Vehiculo vehiculo) {
        this.nombre = validarNombre(nombre);
        this.legajo = validarLegajo(legajo);
        this.vehiculo = validarVehiculo(vehiculo);
        this.pedidos = new Pedido[MAX_PEDIDOS];
        this.cantidadPedidos = 0;
        this.registradoEnCitylink = false;
    }

    // GETTERS
    /**
     * Devuelve el nombre del objeto.
     * @return resultado de la operación
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve el legajo identificador del repartidor.
     * @return resultado de la operación
     */
    public String getLegajo() {
        return legajo;
    }

    /**
     * Devuelve el vehículo asignado al repartidor.
     * @return resultado de la operación
     */
    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    /**
     * Devuelve una copia de los pedidos asignados al repartidor.
     * @return resultado de la operación
     */
    public Pedido[] getPedidos() {
        Pedido[] copia = new Pedido[cantidadPedidos];

        for (int i = 0; i < cantidadPedidos; i++) {
            copia[i] = pedidos[i];
        }

        return copia;
    }

    // VALIDACIONES PRIVADAS COMPARTIDAS POR CONSTRUCTOR Y SETTERS
    private static String validarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                "El nombre no puede estar vacío"
            );
        }
        return nombre;
    }

    private static String validarLegajo(String legajo) {
        if (legajo == null || legajo.isBlank()) {
            throw new IllegalArgumentException(
                "El legajo no puede estar vacío"
            );
        }
        return legajo.trim();
    }

    private static Vehiculo validarVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new IllegalArgumentException(
                "El vehículo no puede ser nulo"
            );
        }
        return vehiculo;
    }

    // SETTERS CON VALIDACION
    /**
     * Valida y establece el nombre del objeto.
     * @param nombre parámetro de la operación
     */
    public void setNombre(String nombre) {
        this.nombre = validarNombre(nombre);
    }

    /**
     * Valida y establece el legajo del repartidor.
     * @param legajo parámetro de la operación
     */
    public void setLegajo(String legajo) {
        String legajoValidado = validarLegajo(legajo);

        if (registradoEnCitylink) {
            throw new IllegalStateException(
                "No se puede modificar el legajo de un repartidor ya registrado en Citylink"
            );
        }

        this.legajo = legajoValidado;
    }

    /**
     * Valida y establece el vehículo del repartidor.
     * @param vehiculo parámetro de la operación
     */
    public void setVehiculo(Vehiculo vehiculo) {
        Vehiculo vehiculoValidado = validarVehiculo(vehiculo);

        // Un repartidor conserva su vehículo una vez que tiene pedidos asignados.
        if (this.vehiculo != vehiculoValidado &&
            (cantidadPedidos > 0 || registradoEnCitylink)) {
            throw new IllegalStateException(
                "No se puede cambiar el vehículo de un repartidor registrado o que ya tiene pedidos asignados"
            );
        }

        this.vehiculo = vehiculoValidado;
    }

    // METODOS DE USO INTERNO DEL GESTOR (MISMO PAQUETE)
    boolean estaRegistradoEnCitylink() {
        return registradoEnCitylink;
    }

    void marcarComoRegistradoEnCitylink() {
        if (registradoEnCitylink) {
            throw new IllegalStateException(
                "El repartidor ya está registrado en un sistema Citylink"
            );
        }
        registradoEnCitylink = true;
    }

    void desmarcarComoRegistradoEnCitylink() {
        registradoEnCitylink = false;
    }

    // SOBRECARGA 1: UTILIZA EL VEHICULO DEL REPARTIDOR
    /**
     * Asigna el pedido si pertenece al contexto esperado y el vehículo puede transportarlo.
     * @param pedido parámetro de la operación
     * @throws CapacidadExcedidaException cuando no se puede completar la operación
     */
    public void asignarPedido(Pedido pedido)
            throws CapacidadExcedidaException {
        asignarPedido(pedido, this.vehiculo);
    }

    // SOBRECARGA 2: RECIBE UN VEHICULO EXPLICITAMENTE
    /**
     * Asigna el pedido si pertenece al contexto esperado y el vehículo puede transportarlo.
     * @param pedido parámetro de la operación
     * @param vehiculo parámetro de la operación
     * @throws CapacidadExcedidaException cuando no se puede completar la operación
     */
    public void asignarPedido(Pedido pedido, Vehiculo vehiculo)
            throws CapacidadExcedidaException {
        if (pedido == null) {
            throw new IllegalArgumentException(
                "El pedido no puede ser nulo"
            );
        }

        if (vehiculo == null) {
            throw new IllegalArgumentException(
                "El vehículo no puede ser nulo"
            );
        }

        if (vehiculo != this.vehiculo) {
            throw new IllegalArgumentException(
                "El vehículo no pertenece a este repartidor"
            );
        }

        if (cantidadPedidos >= pedidos.length) {
            throw new IllegalStateException(
                "El repartidor alcanzó el máximo de " + MAX_PEDIDOS + " pedidos asignados"
            );
        }

        if (pedido.getVehiculoAsignado() != null) {
            throw new IllegalArgumentException(
                "El pedido ya tiene un vehículo asignado"
            );
        }

        pedido.asignarVehiculo(vehiculo);
        pedidos[cantidadPedidos] = pedido;
        cantidadPedidos++;
    }

    /**
     * Devuelve la cantidad de pedidos asignados al repartidor.
     * @return resultado de la operación
     */
    public int getCantidadPedidos() {
        return cantidadPedidos;
    }
    /**
     * Devuelve el estado actual de la entidad.
     * @return resultado de la operación
     */

    @Override
    public String obtenerEstadoActual() {
        if (cantidadPedidos == 0) {
            return "SIN_PEDIDOS";
        }

        boolean tienePendientes = false;

        for (int i = 0; i < cantidadPedidos; i++) {
            String estado = pedidos[i].obtenerEstadoActual();

            if (estado.equals("EN_CAMINO")) {
                return "EN_CAMINO";
            }

            if (estado.equals("PENDIENTE")) {
                tienePendientes = true;
            }
        }

        if (tienePendientes) {
            return "PENDIENTE";
        }

        return "SIN_PEDIDOS_PENDIENTES";
    }
    /**
     * Calcula el importe o comisión definido por el contrato Facturable.
     * @return resultado de la operación
     */

    @Override
    public double calcularImporte() {
        double totalEntregado = 0;

        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidos[i].obtenerEstadoActual().equals("ENTREGADO")) {
                totalEntregado += pedidos[i].calcularImporte();
            }
        }

        return totalEntregado * 0.10;
    }

    /**
     * Registra como entregado un pedido que pertenece al repartidor.
     * @param pedido parámetro de la operación
     */
    public void registrarEntrega(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException(
                "El pedido no puede ser nulo."
            );
        }

        boolean pertenece = false;

        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidos[i] == pedido) {
                pertenece = true;
                break;
            }
        }

        if (!pertenece) {
            throw new IllegalArgumentException(
                "El pedido no pertenece a este repartidor."
            );
        }

        pedido.cambiarEstado("ENTREGADO");
    }
}
