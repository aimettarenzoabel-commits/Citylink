package modelo;

import java.util.NoSuchElementException;

import excepciones.AlmacenamientoLlenoException;
import excepciones.CapacidadExcedidaException;
import excepciones.ClienteNoEncontradoException;
import excepciones.PedidoInvalidoException;

/**
 * Gestiona los registros, búsquedas, asignaciones y consultas del sistema mediante arreglos.
 */
public class Citylink {

    // CAPACIDADES MAXIMAS DEL SISTEMA
    private static final int MAX_CLIENTES = 50;
    private static final int MAX_PEDIDOS = 100;
    private static final int MAX_REPARTIDORES = 10;

    // ARREGLOS PRINCIPALES
    private Cliente[] clientes;
    private Pedido[] pedidos;
    private Repartidor[] repartidores;

    // CONTADORES
    private int cantidadClientes;
    private int cantidadPedidos;
    private int cantidadRepartidores;

    // CONSTRUCTOR
    /**
     * Construye un gestor con arreglos vacíos y sus capacidades máximas.
     */
    public Citylink() {
        this.clientes = new Cliente[MAX_CLIENTES];
        this.pedidos = new Pedido[MAX_PEDIDOS];
        this.repartidores = new Repartidor[MAX_REPARTIDORES];
        this.cantidadClientes = 0;
        this.cantidadPedidos = 0;
        this.cantidadRepartidores = 0;
    }

    // ==================== CLIENTES ====================

    /**
     * Valida y registra un cliente si hay espacio y su código no está repetido.
     * @param cliente parámetro de la operación
     * @throws AlmacenamientoLlenoException cuando no se puede completar la operación
     */
    public void agregarCliente(Cliente cliente)
            throws AlmacenamientoLlenoException {

        validarClienteCompleto(cliente);

        if (cliente.estaRegistradoEnCitylink()) {
            throw new IllegalStateException(
                "El cliente ya está registrado en un sistema Citylink"
            );
        }

        for (int i = 0; i < cantidadClientes; i++) {
            if (clientes[i].getCodigo() == cliente.getCodigo()) {
                throw new IllegalArgumentException(
                    "Ya existe un cliente con ese código"
                );
            }
        }

        if (cantidadClientes >= clientes.length) {
            throw new AlmacenamientoLlenoException(
                "No hay espacio para registrar más clientes"
            );
        }

        cliente.marcarComoRegistradoEnCitylink();
        clientes[cantidadClientes] = cliente;
        cantidadClientes++;
    }

    /**
     * Busca un cliente por código o por nombre, según la sobrecarga utilizada.
     * @param codigo parámetro de la operación
     * @return resultado de la operación
     * @throws ClienteNoEncontradoException cuando no se puede completar la operación
     */
    public Cliente buscarCliente(int codigo)
            throws ClienteNoEncontradoException {

        if (codigo <= 0) {
            throw new IllegalArgumentException(
                "El código del cliente debe ser positivo"
            );
        }

        for (int i = 0; i < cantidadClientes; i++) {
            if (clientes[i].getCodigo() == codigo) {
                return clientes[i];
            }
        }

        throw new ClienteNoEncontradoException(
            "No se encontró un cliente con el código " + codigo
        );
    }

    /**
     * Busca un cliente por código o por nombre, según la sobrecarga utilizada.
     * @param nombre parámetro de la operación
     * @return resultado de la operación
     * @throws ClienteNoEncontradoException cuando no se puede completar la operación
     */
    public Cliente buscarCliente(String nombre)
            throws ClienteNoEncontradoException {

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                "El nombre del cliente no puede estar vacío"
            );
        }

        String nombreBuscado = nombre.trim();

        for (int i = 0; i < cantidadClientes; i++) {
            if (clientes[i].getNombre().equalsIgnoreCase(nombreBuscado)) {
                return clientes[i];
            }
        }

        throw new ClienteNoEncontradoException(
            "No se encontró un cliente con el nombre " + nombreBuscado
        );
    }

    private void validarClienteCompleto(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException(
                "El cliente no puede ser nulo"
            );
        }

        if (cliente.getCodigo() <= 0 ||
            cliente.getNombre() == null || cliente.getNombre().isBlank() ||
            cliente.getTelefono() == null || cliente.getTelefono().isBlank() ||
            cliente.getZona() == null || cliente.getZona().isBlank()) {
            throw new IllegalArgumentException(
                "No se puede registrar un cliente con datos incompletos"
            );
        }
    }

    // ==================== REPARTIDORES ====================

    /**
     * Valida y registra un repartidor si hay espacio y su legajo no está repetido.
     * @param repartidor parámetro de la operación
     * @throws AlmacenamientoLlenoException cuando no se puede completar la operación
     */
    public void agregarRepartidor(Repartidor repartidor)
            throws AlmacenamientoLlenoException {

        validarRepartidorCompleto(repartidor);

        if (repartidor.estaRegistradoEnCitylink()) {
            throw new IllegalStateException(
                "El repartidor ya está registrado en un sistema Citylink"
            );
        }

        String legajoNuevo = repartidor.getLegajo().trim();

        for (int i = 0; i < cantidadRepartidores; i++) {
            if (repartidores[i].getLegajo().trim()
                    .equalsIgnoreCase(legajoNuevo)) {
                throw new IllegalArgumentException(
                    "Ya existe un repartidor con ese legajo"
                );
            }
        }

        if (cantidadRepartidores >= repartidores.length) {
            throw new AlmacenamientoLlenoException(
                "No hay espacio para registrar más repartidores"
            );
        }

        repartidor.marcarComoRegistradoEnCitylink();
        repartidores[cantidadRepartidores] = repartidor;
        cantidadRepartidores++;
    }

    /**
     * Busca un repartidor por su legajo.
     * @param legajo parámetro de la operación
     * @return resultado de la operación
     */
    public Repartidor buscarRepartidor(String legajo) {
        if (legajo == null || legajo.isBlank()) {
            throw new IllegalArgumentException(
                "El legajo del repartidor no puede estar vacío"
            );
        }

        String legajoBuscado = legajo.trim();

        for (int i = 0; i < cantidadRepartidores; i++) {
            if (repartidores[i].getLegajo().trim()
                    .equalsIgnoreCase(legajoBuscado)) {
                return repartidores[i];
            }
        }

        throw new NoSuchElementException(
            "No se encontró un repartidor con el legajo " + legajoBuscado
        );
    }

    private void validarRepartidorCompleto(Repartidor repartidor) {
        if (repartidor == null) {
            throw new IllegalArgumentException(
                "El repartidor no puede ser nulo"
            );
        }

        if (repartidor.getNombre() == null || repartidor.getNombre().isBlank() ||
            repartidor.getLegajo() == null || repartidor.getLegajo().isBlank() ||
            repartidor.getVehiculo() == null ||
            repartidor.getVehiculo().getPatente() == null ||
            repartidor.getVehiculo().getPatente().isBlank()) {
            throw new IllegalArgumentException(
                "No se puede registrar un repartidor con datos incompletos"
            );
        }
    }

    // ==================== PEDIDOS ====================

    /**
     * Valida y registra un pedido asociado a un cliente existente.
     * @param pedido parámetro de la operación
     * @throws AlmacenamientoLlenoException cuando no se puede completar la operación
     * @throws ClienteNoEncontradoException cuando no se puede completar la operación
     * @throws PedidoInvalidoException cuando no se puede completar la operación
     */
    public void agregarPedido(Pedido pedido)
            throws AlmacenamientoLlenoException,
                   ClienteNoEncontradoException,
                   PedidoInvalidoException {

        if (pedido == null) {
            throw new IllegalArgumentException(
                "El pedido no puede ser nulo"
            );
        }

        if (pedido.estaRegistradoEnCitylink()) {
            throw new IllegalStateException(
                "El pedido ya está registrado en un sistema Citylink"
            );
        }

        if (pedido.getVehiculoAsignado() != null ||
            !pedido.obtenerEstadoActual().equals("PENDIENTE")) {
            throw new IllegalStateException(
                "Solo se pueden registrar pedidos nuevos, pendientes y sin vehículo asignado"
            );
        }

        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidos[i].getCodigo() == pedido.getCodigo()) {
                throw new PedidoInvalidoException(
                    "Ya existe un pedido con ese código"
                );
            }
        }

        // El cliente debe estar registrado en ESTE Citylink y ser la misma instancia.
        Cliente clienteRegistrado = buscarCliente(pedido.getCliente().getCodigo());
        if (clienteRegistrado != pedido.getCliente()) {
            throw new IllegalArgumentException(
                "El pedido debe estar asociado al objeto Cliente registrado en este Citylink"
            );
        }

        if (cantidadPedidos >= pedidos.length) {
            throw new AlmacenamientoLlenoException(
                "No hay espacio para registrar más pedidos"
            );
        }

        pedido.marcarComoRegistradoEnCitylink();
        pedidos[cantidadPedidos] = pedido;
        cantidadPedidos++;
    }

    /**
     * Busca un pedido por su código.
     * @param codigo parámetro de la operación
     * @return resultado de la operación
     */
    public Pedido buscarPedido(int codigo) {
        if (codigo <= 0) {
            throw new IllegalArgumentException(
                "El código del pedido debe ser positivo"
            );
        }

        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidos[i].getCodigo() == codigo) {
                return pedidos[i];
            }
        }

        throw new NoSuchElementException(
            "No se encontró un pedido con el código " + codigo
        );
    }

    // ==================== OPERACIONES ENTRE ENTIDADES ====================

    /**
     * Asigna el pedido si pertenece al contexto esperado y el vehículo puede transportarlo.
     * @param codigoPedido parámetro de la operación
     * @param legajoRepartidor parámetro de la operación
     * @throws CapacidadExcedidaException cuando no se puede completar la operación
     */
    public void asignarPedido(int codigoPedido, String legajoRepartidor)
            throws CapacidadExcedidaException {

        Pedido pedido = buscarPedido(codigoPedido);
        Repartidor repartidor = buscarRepartidor(legajoRepartidor);
        repartidor.asignarPedido(pedido);
    }

    /**
     * Solicita el cambio de estado de un pedido aplicando las reglas de transición.
     * @param codigoPedido parámetro de la operación
     * @param nuevoEstado parámetro de la operación
     */
    public void cambiarEstadoPedido(int codigoPedido, String nuevoEstado) {
        if ("ENTREGADO".equals(nuevoEstado)) {
            throw new excepciones.EstadoInvalidoException(
                "La entrega debe registrarse indicando el repartidor responsable"
            );
        }
        Pedido pedido = buscarPedido(codigoPedido);
        pedido.cambiarEstado(nuevoEstado);
    }

    /**
     * Registra como entregado un pedido que pertenece al repartidor.
     * @param codigoPedido parámetro de la operación
     * @param legajoRepartidor parámetro de la operación
     */
    public void registrarEntrega(int codigoPedido, String legajoRepartidor) {
        Pedido pedido = buscarPedido(codigoPedido);
        Repartidor repartidor = buscarRepartidor(legajoRepartidor);
        repartidor.registrarEntrega(pedido);
    }

    // ==================== ELIMINACION DE ENTIDADES ====================

    /**
     * Elimina un cliente si no tiene pedidos asociados.
     * Los elementos siguientes se desplazan para mantener el arreglo compacto.
     *
     * @param codigo código del cliente que se desea eliminar
     * @throws ClienteNoEncontradoException si el cliente no existe
     * @throws IllegalStateException si el cliente todavía tiene pedidos asociados
     */
    public void eliminarCliente(int codigo)
            throws ClienteNoEncontradoException {

        Cliente cliente = buscarCliente(codigo);

        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidos[i].getCliente() == cliente) {
                throw new IllegalStateException(
                    "No se puede eliminar el cliente porque tiene pedidos registrados"
                );
            }
        }

        int indice = -1;
        for (int i = 0; i < cantidadClientes; i++) {
            if (clientes[i] == cliente) {
                indice = i;
                break;
            }
        }

        if (indice == -1) {
            // Defensa adicional: buscarCliente ya garantiza que debería existir.
            throw new IllegalStateException(
                "No se pudo localizar la posición interna del cliente"
            );
        }

        for (int i = indice; i < cantidadClientes - 1; i++) {
            clientes[i] = clientes[i + 1];
        }

        clientes[cantidadClientes - 1] = null;
        cantidadClientes--;
        cliente.desmarcarComoRegistradoEnCitylink();
    }

    /**
     * Elimina un pedido solamente si todavía no fue asignado a un vehículo.
     * Los elementos siguientes se desplazan para mantener el arreglo compacto.
     *
     * @param codigo código del pedido que se desea eliminar
     * @throws NoSuchElementException si el pedido no existe
     * @throws IllegalStateException si el pedido ya fue asignado o salió a reparto
     */
    public void eliminarPedido(int codigo) {

        Pedido pedido = buscarPedido(codigo);

        if (pedido.getVehiculoAsignado() != null ||
            (!pedido.obtenerEstadoActual().equals("PENDIENTE") &&
             !pedido.obtenerEstadoActual().equals("CANCELADO"))) {
            throw new IllegalStateException(
                "No se puede eliminar un pedido asignado o que ya inició su proceso de entrega"
            );
        }

        int indice = -1;
        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidos[i] == pedido) {
                indice = i;
                break;
            }
        }

        if (indice == -1) {
            throw new IllegalStateException(
                "No se pudo localizar la posición interna del pedido"
            );
        }

        for (int i = indice; i < cantidadPedidos - 1; i++) {
            pedidos[i] = pedidos[i + 1];
        }

        pedidos[cantidadPedidos - 1] = null;
        cantidadPedidos--;
        pedido.desmarcarComoRegistradoEnCitylink();
    }

    /**
     * Elimina un repartidor solamente si no tiene pedidos a su cargo.
     * Los elementos siguientes se desplazan para mantener el arreglo compacto.
     *
     * @param legajo legajo del repartidor que se desea eliminar
     * @throws NoSuchElementException si el repartidor no existe
     * @throws IllegalStateException si todavía tiene pedidos asociados
     */
    public void eliminarRepartidor(String legajo) {

        Repartidor repartidor = buscarRepartidor(legajo);

        if (repartidor.getCantidadPedidos() > 0) {
            throw new IllegalStateException(
                "No se puede eliminar un repartidor que conserva pedidos en su historial"
            );
        }

        int indice = -1;
        for (int i = 0; i < cantidadRepartidores; i++) {
            if (repartidores[i] == repartidor) {
                indice = i;
                break;
            }
        }

        if (indice == -1) {
            throw new IllegalStateException(
                "No se pudo localizar la posición interna del repartidor"
            );
        }

        for (int i = indice; i < cantidadRepartidores - 1; i++) {
            repartidores[i] = repartidores[i + 1];
        }

        repartidores[cantidadRepartidores - 1] = null;
        cantidadRepartidores--;
        repartidor.desmarcarComoRegistradoEnCitylink();
    }

    // ==================== CONSULTAS Y CALCULOS ====================

    /**
     * Devuelve el estado actual de un pedido.
     *
     * @param codigo código del pedido
     * @return estado actual del pedido
     */
    public String consultarEstadoPedido(int codigo) {
        Pedido pedido = buscarPedido(codigo);
        return pedido.obtenerEstadoActual();
    }

    /**
     * Devuelve el estado agregado de un repartidor según sus pedidos.
     *
     * @param legajo legajo del repartidor
     * @return estado actual informado por Rastreable
     */
    public String consultarEstadoRepartidor(String legajo) {
        Repartidor repartidor = buscarRepartidor(legajo);
        return repartidor.obtenerEstadoActual();
    }

    /**
     * Calcula el costo de un pedido usando el tipo concreto de Pedido y su
     * vehículo asignado. La llamada a calcularCosto se resuelve por polimorfismo.
     *
     * @param codigo código del pedido
     * @return costo del envío
     * @throws IllegalStateException si el pedido todavía no tiene vehículo
     */
    public double calcularCostoPedido(int codigo) {
        Pedido pedido = buscarPedido(codigo);
        Vehiculo vehiculo = pedido.getVehiculoAsignado();

        if (vehiculo == null) {
            throw new IllegalStateException(
                "No se puede calcular el costo: el pedido no tiene vehículo asignado"
            );
        }

        return pedido.calcularCosto(vehiculo);
    }

    /**
     * Calcula el tiempo estimado usando el tipo concreto de Vehiculo.
     * La llamada a calcularTiempoEntrega se resuelve por polimorfismo.
     *
     * @param codigo código del pedido
     * @return tiempo estimado en minutos
     * @throws IllegalStateException si el pedido todavía no tiene vehículo
     */
    public double calcularTiempoEstimadoPedido(int codigo) {
        Pedido pedido = buscarPedido(codigo);
        Vehiculo vehiculo = pedido.getVehiculoAsignado();

        if (vehiculo == null) {
            throw new IllegalStateException(
                "No se puede calcular el tiempo: el pedido no tiene vehículo asignado"
            );
        }

        return vehiculo.calcularTiempoEntrega(pedido.getKm());
    }

    /**
     * Suma los importes de los pedidos que tienen un vehículo asignado.
     * Los pedidos sin asignación se excluyen porque todavía no se puede
     * determinar su tarifa por kilómetro. Incluye los pedidos asignados
     * aunque luego hayan sido cancelados: devuelve el importe cotizado.
     *
     * @return importe total cotizado de los pedidos asignados
     */
    public double calcularImporteTotalPedidos() {
        double total = 0;

        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidos[i].getVehiculoAsignado() != null) {
                total += pedidos[i].calcularImporte();
            }
        }

        return total;
    }

    /**
     * Cuenta pedidos que todavía no tienen vehículo asignado.
     *
     * @return cantidad de pedidos sin asignación
     */
    public int contarPedidosSinAsignar() {
        int cantidad = 0;

        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidos[i].getVehiculoAsignado() == null) {
                cantidad++;
            }
        }

        return cantidad;
    }

    /**
     * Calcula la comisión de un repartidor. Repartidor aplica Facturable y
     * contabiliza exclusivamente los pedidos entregados.
     *
     * @param legajo legajo del repartidor
     * @return comisión acumulada
     */
    public double calcularComisionRepartidor(String legajo) {
        Repartidor repartidor = buscarRepartidor(legajo);
        return repartidor.calcularImporte();
    }

    /**
     * Suma las comisiones de todos los repartidores registrados.
     *
     * @return suma de comisiones
     */
    public double calcularComisionesTotales() {
        double total = 0;

        for (int i = 0; i < cantidadRepartidores; i++) {
            total += repartidores[i].calcularImporte();
        }

        return total;
    }

    // ==================== COPIAS DEFENSIVAS DE ARREGLOS ====================

    /**
     * Devuelve una copia del arreglo de clientes registrados.
     * @return resultado de la operación
     */
    public Cliente[] getClientes() {
        Cliente[] copia = new Cliente[cantidadClientes];
        for (int i = 0; i < cantidadClientes; i++) {
            copia[i] = clientes[i];
        }
        return copia;
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

    /**
     * Devuelve una copia del arreglo de repartidores registrados.
     * @return resultado de la operación
     */
    public Repartidor[] getRepartidores() {
        Repartidor[] copia = new Repartidor[cantidadRepartidores];
        for (int i = 0; i < cantidadRepartidores; i++) {
            copia[i] = repartidores[i];
        }
        return copia;
    }

    // ==================== CONTADORES ====================

    /**
     * Devuelve la cantidad de clientes registrados.
     * @return resultado de la operación
     */
    public int getCantidadClientes() {
        return cantidadClientes;
    }

    /**
     * Devuelve la cantidad de pedidos asignados al repartidor.
     * @return resultado de la operación
     */
    public int getCantidadPedidos() {
        return cantidadPedidos;
    }

    /**
     * Devuelve la cantidad de repartidores registrados.
     * @return resultado de la operación
     */
    public int getCantidadRepartidores() {
        return cantidadRepartidores;
    }
}
