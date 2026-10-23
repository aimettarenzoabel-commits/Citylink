package modelo;

public class Cliente {

    // ATRIBUTOS
    private String nombre;
    private String zona;
    private String telefono;
    private int codigo;

    // CONSTRUCTOR VACÍO
    public Cliente() {
    }

    // CONSTRUCTOR COMPLETO
    public Cliente(String nombre, String telefono,int codigo, String zona) {
        setNombre(nombre);
        setTelefono(telefono);
        setCodigo(codigo);
        setZona(zona);
    }

    // GETTERS
    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getZona() {
        return zona;
    }

    // SETTERS CON VALIDACIÓN
    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                "El nombre no puede estar vacío"
            );
        }

        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        if (telefono == null || telefono.isBlank()) {
            throw new IllegalArgumentException(
                "El teléfono no puede estar vacío"
            );
        }

        this.telefono = telefono;
    }

    public void setCodigo(int codigo) {
        if (codigo <= 0) {
            throw new IllegalArgumentException(
                "El código debe ser mayor a cero"
            );
        }

        this.codigo = codigo;
    }

    public void setZona(String zona) {
        if (zona == null || zona.isBlank()) {
            throw new IllegalArgumentException(
                "La zona no puede estar vacía"
            );
        }

        this.zona = zona;
    }

    // TOSTRING
    @Override
    public String toString() {
        return 
        "Cliente: " + nombre +
        "\nCódigo: " + codigo +
        "\nTeléfono: " + telefono +
        "\nZona: " + zona;
    }
}
