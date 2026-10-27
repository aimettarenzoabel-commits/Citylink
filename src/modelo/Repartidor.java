package modelo;

public class Repartidor{

    //ATRIBUTOS
    private String nombre;
    private String legajo;
    private Vehiculo vehiculo;
    private Pedido[] pedidos;
    private int cantidadPedidos;
    // CONSTRUCTOR VACÍO
    public Repartidor() {
        this.pedidos = new Pedido[20];
        this.cantidadPedidos = 0;
    }

    // CONSTRUCTOR COMPLETO
    public Repartidor(String nombre, String legajo, Vehiculo vehiculo) {
        setNombre(nombre);
        setLegajo(legajo);
        setVehiculo(vehiculo);

        this.pedidos = new Pedido[20];
        this.cantidadPedidos = 0;
    }

    // GETTERS
    public String getNombre() {
        return nombre;
    }

    public String getLegajo() {
        return legajo;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public Pedido[] getPedidos() {
        return pedidos;
    }

    // SETTERS CON VALIDACIÓN
    public void setNombre(String nombre){
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre;
    }

    public void setLegajo(String legajo){
        if (legajo == null || legajo.isBlank()) {
            throw new IllegalArgumentException("El legajo no puede estar vacío");
        }
        this.legajo = legajo;
    }
   

    public void setVehiculo(Vehiculo vehiculo){
        if (vehiculo == null) {
            throw new IllegalArgumentException("El vehículo no puede ser nulo");
        }
        this.vehiculo = vehiculo;
    }
    
    //ASIGNAR PEDIDO
    public void asignarPedido(Pedido pedido) {

        if (pedido == null) {
            throw new IllegalArgumentException(
                "El pedido no puede ser nulo"
            );
        }

        if (cantidadPedidos >= pedidos.length) {
            throw new IllegalStateException(
                "El repartidor no puede recibir más pedidos"
            );
        }

        pedidos[cantidadPedidos] = pedido;
        cantidadPedidos++;
    }
}