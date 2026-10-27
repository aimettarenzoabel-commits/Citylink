package modelo;
public class PedidoUrgente {
    private String descripcion;
    private double peso;
    private boolean esUrgente;

    public PedidoUrgente(String descripcion, double peso, boolean esUrgente) {
        this.descripcion = descripcion;
        this.peso = peso;
        this.esUrgente = esUrgente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPeso() {
        return peso;
    }

    public boolean isEsUrgente() {
        return esUrgente;
    }
}
