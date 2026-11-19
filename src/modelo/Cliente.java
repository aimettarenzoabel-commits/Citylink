package modelo;

/**
 * Representa a una persona que solicita servicios de envío en CityLink.
 */
public class Cliente {

    // ATRIBUTOS
    private String nombre;
    private String zona;
    private String telefono;
    private int codigo;
    private boolean registradoEnCitylink;

    // CONSTRUCTOR VACIO
    /**
     * Construye un cliente sin datos inicializados.
     */
    public Cliente() {
        this.registradoEnCitylink = false;
    }

    // CONSTRUCTOR COMPLETO
    /**
     * Construye un cliente sin datos inicializados.
     * @param nombre parámetro de la operación
     * @param telefono parámetro de la operación
     * @param codigo parámetro de la operación
     * @param zona parámetro de la operación
     */
    public Cliente(String nombre, String telefono, int codigo, String zona) {
        this.nombre = validarTexto(nombre, "El nombre no puede estar vacío");
        this.telefono = validarTexto(telefono, "El teléfono no puede estar vacío");
        this.codigo = validarCodigo(codigo);
        this.zona = validarTexto(zona, "La zona no puede estar vacía");
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
     * Devuelve el teléfono del cliente.
     * @return resultado de la operación
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Devuelve el código identificador.
     * @return resultado de la operación
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Devuelve la zona asociada al cliente.
     * @return resultado de la operación
     */
    public String getZona() {
        return zona;
    }

    // VALIDACIONES PRIVADAS COMPARTIDAS POR CONSTRUCTOR Y SETTERS
    private static String validarTexto(String valor, String mensajeError) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(mensajeError);
        }
        return valor;
    }

    private static int validarCodigo(int codigo) {
        if (codigo <= 0) {
            throw new IllegalArgumentException(
                "El código debe ser mayor a cero"
            );
        }
        return codigo;
    }

    // SETTERS CON VALIDACION
    /**
     * Valida y establece el nombre del objeto.
     * @param nombre parámetro de la operación
     */
    public void setNombre(String nombre) {
        this.nombre = validarTexto(
            nombre,
            "El nombre no puede estar vacío"
        );
    }

    /**
     * Valida y establece el teléfono del cliente.
     * @param telefono parámetro de la operación
     */
    public void setTelefono(String telefono) {
        this.telefono = validarTexto(
            telefono,
            "El teléfono no puede estar vacío"
        );
    }

    /**
     * Valida y establece el código identificador.
     * @param codigo parámetro de la operación
     */
    public void setCodigo(int codigo) {
        int codigoValidado = validarCodigo(codigo);

        if (registradoEnCitylink) {
            throw new IllegalStateException(
                "No se puede modificar el código de un cliente ya registrado en Citylink"
            );
        }

        this.codigo = codigoValidado;
    }

    /**
     * Valida y establece la zona del cliente.
     * @param zona parámetro de la operación
     */
    public void setZona(String zona) {
        this.zona = validarTexto(
            zona,
            "La zona no puede estar vacía"
        );
    }

    // METODOS DE USO INTERNO DEL GESTOR (MISMO PAQUETE)
    boolean estaRegistradoEnCitylink() {
        return registradoEnCitylink;
    }

    void marcarComoRegistradoEnCitylink() {
        if (registradoEnCitylink) {
            throw new IllegalStateException(
                "El cliente ya está registrado en un sistema Citylink"
            );
        }
        registradoEnCitylink = true;
    }

    void desmarcarComoRegistradoEnCitylink() {
        registradoEnCitylink = false;
    }

    // TOSTRING
    /**
     * Devuelve una representación textual de los datos del objeto.
     * @return resultado de la operación
     */
    @Override
    public String toString() {
        return "Cliente: " + nombre
            + "\nCódigo: " + codigo
            + "\nTeléfono: " + telefono
            + "\nZona: " + zona;
    }
}
