package modelo;
public class PedidoNormal {
    private String descripcion;
    private double peso;

    public PedidoNormal(String descripcion, double peso) {
        this.descripcion = descripcion;
        this.peso = peso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPeso() {
        return peso;
    }
}