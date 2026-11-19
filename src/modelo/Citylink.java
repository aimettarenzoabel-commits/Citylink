package modelo;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.TreeMap;

import excepciones.AlmacenamientoLlenoException;
import excepciones.CapacidadExcedidaException;
import excepciones.ClienteNoEncontradoException;
import excepciones.PedidoInvalidoException;

/**
 * Gestiona los registros, búsquedas, asignaciones y consultas del sistema
 * mediante estructuras dinámicas de Java Collections.
 */
public class Citylink {

    private static class Accion {

        private String tipo;
        private Pedido pedido;
        private Repartidor repartidor;
        private String estadoAnterior;

        public Accion(String tipo, Pedido pedido,Repartidor repartidor, String estadoAnterior) {

        this.tipo = tipo;
        this.pedido = pedido;
        this.repartidor = repartidor;
        this.estadoAnterior = estadoAnterior;
        }
    }

    //Estructura dinamicas principales
    private TreeMap<Integer, Cliente> clientes;
    private ArrayList<Pedido> pedidos;
    private ArrayList<Repartidor> repartidores;

    // Estructuras para nuevas funcionalidades
        private ArrayDeque<Accion> historialAcciones;
        private LinkedList<Pedido> colaUrgentes;
        private LinkedList<Pedido> colaNormales;

    // CONSTRUCTOR
    /**
    * Construye un gestor con sus estructuras dinámicas vacías.
    */
    public Citylink() {
        this.clientes = new TreeMap<>();
        this.pedidos = new ArrayList<>();
        this.repartidores = new ArrayList<>();
        this.historialAcciones = new ArrayDeque<>();
        this.colaUrgentes = new LinkedList<>();
        this.colaNormales = new LinkedList<>();
    }

    // ==================== CLIENTES ====================

    
    /**
    * Registra un nuevo cliente en el sistema.
    *
    * @param cliente cliente que se desea registrar
    * @throws IllegalArgumentException si el cliente es nulo o inválido
    * @throws IllegalStateException si el cliente ya está registrado
    */
    public void agregarCliente(Cliente cliente) {

        validarClienteCompleto(cliente);

        if (cliente.estaRegistradoEnCitylink()) {
            throw new IllegalStateException(
                "El cliente ya está registrado en un sistema Citylink"
            );
        }

        if (clientes.containsKey(cliente.getCodigo())) {
            throw new IllegalArgumentException(
                "Ya existe un cliente con ese código"
            );
        }

        cliente.marcarComoRegistradoEnCitylink();
        clientes.put(cliente.getCodigo(), cliente);

    }

    /**
    * Busca un cliente mediante su código.
    *
    * @param codigo código del cliente que se desea buscar
    * @return cliente encontrado
    * @throws ClienteNoEncontradoException si no existe un cliente con ese código
    */

    public Cliente buscarCliente(int codigo)
            throws ClienteNoEncontradoException {

        if (codigo <= 0) {
            throw new IllegalArgumentException(
                "El código del cliente debe ser positivo"
            );
        }

        Cliente cliente = clientes.get(codigo);

        if (cliente == null) {
            throw new ClienteNoEncontradoException(
                "No se encontró un cliente con el código " + codigo
            );
        }

        return cliente;
    }

    /**
    * Busca un cliente mediante su nombre.
    *
    * @param nombre nombre del cliente que se desea buscar
    * @return cliente encontrado
    * @throws ClienteNoEncontradoException si no existe un cliente con ese nombre
    */
    public Cliente buscarCliente(String nombre)
            throws ClienteNoEncontradoException {

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                "El nombre del cliente no puede estar vacío"
            );
        }

        for (Cliente cliente : clientes.values()) {
            if (cliente.getNombre().equalsIgnoreCase(nombre.trim())) {
                return cliente;
            }
        }

        throw new ClienteNoEncontradoException(
            "No se encontró un cliente con el nombre " + nombre
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
    * Registra un nuevo pedido en el sistema.
    *
    * @param pedido pedido que se desea registrar
    * @throws IllegalArgumentException si el pedido es nulo o inválido
    * @throws IllegalStateException si el pedido ya está registrado
    * @throws ClienteNoEncontradoException si el cliente asociado no está registrado
    */
    
    public void agregarRepartidor(Repartidor repartidor){

        validarRepartidorCompleto(repartidor);

        if (repartidor.estaRegistradoEnCitylink()) {
            throw new IllegalStateException(
                "El repartidor ya está registrado en un sistema Citylink"
            );
        }

        String legajoBuscado = repartidor.getLegajo().trim();

        for (Repartidor r : repartidores) {
            if (r.getLegajo().trim().equalsIgnoreCase(legajoBuscado)) {
                throw new IllegalArgumentException(
                    "Ya existe un repartidor con ese legajo"
                );
            }
        }

        repartidor.marcarComoRegistradoEnCitylink();
        repartidores.add(repartidor);
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

        for (Repartidor repartidor : repartidores) {
            if (repartidor.getLegajo().trim()
                .equalsIgnoreCase(legajoBuscado)) {
                return repartidor;
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
        throws ClienteNoEncontradoException,
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

        for (Pedido p : pedidos) {
            if (p.getCodigo() == pedido.getCodigo()) {
                throw new PedidoInvalidoException(
                    "Ya existe un pedido con ese código"
                );
            }
        }

        Cliente clienteRegistrado = buscarCliente(pedido.getCliente().getCodigo());

        if (clienteRegistrado != pedido.getCliente()) {
            throw new IllegalArgumentException(
                "El pedido debe estar asociado al objeto Cliente registrado en este Citylink"
            );
        }

        pedido.marcarComoRegistradoEnCitylink();
        pedidos.add(pedido);

        if (pedido instanceof PedidoUrgente) {
         colaUrgentes.offer(pedido);
        } else {
            colaNormales.offer(pedido);
        }

        historialAcciones.push(
            new Accion("ALTA", pedido, null, null)
        );
    }

    /**
     * Devuelve una copia de la cola de pedidos pendientes, 
     * respetando el orden de prioridad.
     * 
     * @return copia de la cola de pedidos
     */
    public LinkedList<Pedido> getColaPedidos() {
        LinkedList<Pedido> colaCompleta = new LinkedList<>(colaUrgentes);
        colaCompleta.addAll(colaNormales);

        return colaCompleta;
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

        for (Pedido pedido : pedidos) {
            if (pedido.getCodigo() == codigo) {
                return pedido;
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

        // Retirar el pedido de la cola una vez asignado
        if (pedido instanceof PedidoUrgente) {
            colaUrgentes.remove(pedido);
        } else {
            colaNormales.remove(pedido);
        }

        historialAcciones.push(
            new Accion("ASIGNACION", pedido, repartidor, null)
        );
    }
    
    /**
     * Atiende el siguiente pedido pendiente, priorizando los urgentes.
     * Busca un repartidor compatible, asigna el pedido y lo pone en camino.
     *
     * @return pedido que fue atendido
     * @throws CapacidadExcedidaException si falla la asignación
     * @throws NoSuchElementException si no hay pedidos pendientes
     * @throws IllegalStateException si no hay repartidores compatibles
     */
    public Pedido atenderSiguiente()
            throws CapacidadExcedidaException {

        Pedido pedido;

        if (!colaUrgentes.isEmpty()) {
            pedido = colaUrgentes.peekFirst();
        } else if (!colaNormales.isEmpty()) {
            pedido = colaNormales.peekFirst();
        } else {
            throw new NoSuchElementException(
                "No hay pedidos pendientes para atender"
            );
        }

        Repartidor repartidorDisponible = null;

        for (Repartidor repartidor : repartidores) {
            if (repartidor.getCantidadPedidos() < 100
                    && repartidor.getVehiculo().puedeTransportar(pedido)) {
                repartidorDisponible = repartidor;
                break;
            }
        }

        if (repartidorDisponible == null) {
            throw new IllegalStateException(
                "No hay repartidores disponibles para transportar este pedido"
            );
        }

        asignarPedido(
            pedido.getCodigo(),
            repartidorDisponible.getLegajo()
        );

        cambiarEstadoPedido(
            pedido.getCodigo(),
            "EN_CAMINO"
        );

        return pedido;
    }
    public void cambiarEstadoPedido(int codigoPedido, String nuevoEstado) {
        if ("ENTREGADO".equals(nuevoEstado)) {
            throw new excepciones.EstadoInvalidoException(
                "La entrega debe registrarse indicando el repartidor responsable"
            );
        }

        Pedido pedido = buscarPedido(codigoPedido);
        String estadoAnterior = pedido.obtenerEstadoActual();
        pedido.cambiarEstado(nuevoEstado);

        if ("CANCELADO".equals(nuevoEstado)) {
            colaUrgentes.remove(pedido);
            colaNormales.remove(pedido);
        }

        historialAcciones.push(
            new Accion("CAMBIO_ESTADO", pedido, null, estadoAnterior)
        );
    }

    /**
     * Registra como entregado un pedido que pertenece al repartidor.
     * @param codigoPedido parámetro de la operación
     * @param legajoRepartidor parámetro de la operación
     */
    public void registrarEntrega(int codigoPedido, String legajoRepartidor) {
       
        Pedido pedido = buscarPedido(codigoPedido);
        Repartidor repartidor = buscarRepartidor(legajoRepartidor);

        if (!"EN_CAMINO".equals(pedido.obtenerEstadoActual())) {
            throw new IllegalStateException(
                "Solo se pueden entregar pedidos que estén en camino"
            );
        }

        String estadoAnterior = pedido.obtenerEstadoActual();

        repartidor.registrarEntrega(pedido);

        historialAcciones.push(
            new Accion("CAMBIO_ESTADO", pedido, repartidor, estadoAnterior)
        );
    }

    // ==================== ELIMINACION DE ENTIDADES ====================

    /**
    * Elimina un cliente del sistema si no tiene pedidos asociados.
    *
    * @param codigo código del cliente que se desea eliminar
    * @throws ClienteNoEncontradoException si el cliente no existe
    * @throws IllegalStateException si el cliente tiene pedidos asociados
    */
    public void eliminarCliente(int codigo)
    throws ClienteNoEncontradoException {

        Cliente cliente = buscarCliente(codigo);

        // Verificamos que el cliente no tenga pedidos asociados.
        for (Pedido pedido : pedidos) {
            if (pedido.getCliente() == cliente) {
                throw new IllegalStateException(
                    "No se puede eliminar el cliente porque tiene pedidos registrados"
                );
            }
        }

        // Eliminamos al cliente del TreeMap.
        clientes.remove(codigo);

        // Marcamos que el cliente ya no pertenece al sistema.
        cliente.desmarcarComoRegistradoEnCitylink();
    }

    /**
    * Elimina un pedido del sistema si cumple con las condiciones necesarias.
    *
    * @param codigo código del pedido que se desea eliminar
    * @throws NoSuchElementException si el pedido no existe
    * @throws IllegalStateException si el pedido no puede eliminarse por su estado o asignación
    */
    public void eliminarPedido(int codigo) {

        Pedido pedido = buscarPedido(codigo);

        if (pedido.getVehiculoAsignado() != null ||
            "EN_CAMINO".equals(pedido.obtenerEstadoActual()) ||
        "ENTREGADO".equals(pedido.obtenerEstadoActual())) {

        throw new IllegalStateException(
            "No se puede eliminar un pedido asignado o que ya inició su proceso de entrega"
        );
        }

        // Eliminamos el pedido de su cola correspondiente.
        if (pedido instanceof PedidoUrgente) {
            colaUrgentes.remove(pedido);
        } else {
            colaNormales.remove(pedido);
        }

        // Eliminamos el pedido del ArrayList.
        pedidos.remove(pedido);

        // Marcamos que el pedido ya no pertenece al sistema.
        pedido.desmarcarComoRegistradoEnCitylink();
    }

    /**
    * Elimina un repartidor del sistema si no tiene pedidos asignados.
    *
    * @param legajo legajo del repartidor que se desea eliminar
    * @throws NoSuchElementException si el repartidor no existe
    * @throws IllegalStateException si el repartidor tiene pedidos asignados
    */
    public void eliminarRepartidor(String legajo) {

        Repartidor repartidor = buscarRepartidor(legajo);

        if (repartidor.getCantidadPedidos() > 0) {
            throw new IllegalStateException(
                "No se puede eliminar un repartidor que conserva pedidos en su historial"
            );
        }

        // Eliminamos el repartidor del ArrayList.
        repartidores.remove(repartidor);

        // Marcamos que ya no pertenece al sistema.
        repartidor.desmarcarComoRegistradoEnCitylink();
    }

    public void deshacer() {

        if (historialAcciones.isEmpty()) {
            throw new NoSuchElementException(
                "No hay acciones para deshacer"
            );
        }

        Accion accion = historialAcciones.peek();

        switch (accion.tipo) {

            case "ALTA":
                Pedido pedidoAlta = accion.pedido;

                if (pedidoAlta.obtenerEstadoActual().equals("PENDIENTE")
                        && pedidoAlta.getVehiculoAsignado() == null) {

                    if (pedidoAlta instanceof PedidoUrgente) {
                        colaUrgentes.remove(pedidoAlta);
                    } else {
                        colaNormales.remove(pedidoAlta);
                    }

                    pedidos.remove(pedidoAlta);
                    pedidoAlta.desmarcarComoRegistradoEnCitylink();

                } else {
                    throw new IllegalStateException(
                        "No se puede deshacer el alta: el pedido ya fue procesado"
                    );
                }
            break;

           case "ASIGNACION":
                Pedido pedidoAsignado = accion.pedido;
                Repartidor repartidorAsignado = accion.repartidor;

                if (!"PENDIENTE".equals(pedidoAsignado.obtenerEstadoActual())) {
                    throw new IllegalStateException(
                        "No se puede deshacer la asignación: el pedido ya fue procesado"
                    );
                }
            

                repartidorAsignado.desasignarPedido(pedidoAsignado);
                pedidoAsignado.desasignarVehiculo();

                if (pedidoAsignado instanceof PedidoUrgente) {
                    colaUrgentes.addFirst(pedidoAsignado);
                } else {
                    colaNormales.addFirst(pedidoAsignado);
                }

            break;

            case "CAMBIO_ESTADO":
                Pedido pedidoModificado = accion.pedido;

                String estadoActual = pedidoModificado.obtenerEstadoActual();

                // Deshacer una entrega.
                if ("ENTREGADO".equals(estadoActual)
                    && "EN_CAMINO".equals(accion.estadoAnterior)) {

                    if (accion.repartidor == null) {
                        throw new IllegalStateException(
                            "No se encontró el repartidor responsable"
                        );
                    }

                    accion.repartidor.restaurarPedidoEntregado(pedidoModificado);
                }

                // Restaurar el estado anterior.
                pedidoModificado.restaurarEstado(accion.estadoAnterior);

                // Deshacer una cancelación.
                if ("CANCELADO".equals(estadoActual)
                    && "PENDIENTE".equals(accion.estadoAnterior)) {

                    if (pedidoModificado instanceof PedidoUrgente) {
                        colaUrgentes.addFirst(pedidoModificado);
                    } else {
                        colaNormales.addFirst(pedidoModificado);
                    }
                }
            
            break;
        }

        historialAcciones.pop();
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

        for (Pedido pedido : pedidos) {
            if (pedido.getVehiculoAsignado() != null) {
                total += pedido.calcularImporte();
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

        for (Pedido pedido : pedidos) {
            if (pedido.getVehiculoAsignado() == null) {
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

        for (Repartidor repartidor : repartidores) {
            total += repartidor.calcularImporte();
        }

        return total;
    }

    // ==================== COPIAS DEFENSIVAS DE COLECCIONES ====================
    /**
    * Devuelve una copia del mapa de clientes registrados.
    * @return copia del TreeMap de clientes
    */
    public TreeMap<Integer, Cliente> getClientes() {
        return new TreeMap<>(clientes);
    }

    /**
    * Devuelve una copia de la lista de pedidos registrados.
    * @return copia del ArrayList de pedidos
    */
    public ArrayList<Pedido> getPedidos() {
        return new ArrayList<>(pedidos);
    }

    /**
    * Devuelve una copia de la lista de repartidores registrados.
    * @return copia del ArrayList de repartidores
    */
    public ArrayList<Repartidor> getRepartidores() {
        return new ArrayList<>(repartidores);
    }
    // ==================== CONTADORES ====================

    /**
    * Devuelve la cantidad de clientes registrados.
    * @return cantidad de clientes
    */
    public int getCantidadClientes() {
        return clientes.size();
    }

    /**
    * Devuelve la cantidad de pedidos registrados.
    * @return cantidad de pedidos
    */
    public int getCantidadPedidos() {
        return pedidos.size();
    }

    /**
    * Devuelve la cantidad de repartidores registrados.
    * @return cantidad de repartidores
    */
    public int getCantidadRepartidores() {
        return repartidores.size();
    }

}
