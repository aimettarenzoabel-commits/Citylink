package modelo;

import java.time.LocalDateTime;

public abstract class Pedido implements Rasteable, Facturable {

    // ATRIBUTOS
    private int codigo;
    private Cliente cliente;
    private String destino;
    private double kg;
    private double km;
    private LocalDateTime fechaHora;
    private String estado;

    // CONSTRUCTOR VACÍO
    public Pedido() {
        this.estado = "PENDIENTE";
        this.fechaHora = LocalDateTime.now();
    }

    // CONSTRUCTOR COMPLETO
    public Pedido(int codigo, Cliente cliente, String destino,
                  double kg, double km, LocalDateTime fechaHora) {

        setCodigo(codigo);
        setCliente(cliente);
        setDestino(destino);
        setKg(kg);
        setKm(km);
        setFechaHora(fechaHora);

        this.estado = "PENDIENTE";
    }

    // GETTERS
    public int getCodigo() {
        return codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getDestino() {
        return destino;
    }

    public double getKg() {
        return kg;
    }

    public double getKm() {
        return km;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getEstado() {
        return estado;
    }

    // SETTERS CON VALIDACIÓN
    public void setCodigo(int codigo) {
        if (codigo <= 0) {
            throw new IllegalArgumentException(
                "El código debe ser positivo"
            );
        }

        this.codigo = codigo;
    }

    public void setCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException(
                "El cliente no puede ser nulo"
            );
        }

        this.cliente = cliente;
    }

    public void setDestino(String destino) {
        if (destino == null || destino.isBlank()) {
            throw new IllegalArgumentException(
                "El destino no puede estar vacío"
            );
        }

        this.destino = destino;
    }

    public void setKg(double kg) {
        if (!Double.isFinite(kg) || kg <= 0) {
            throw new IllegalArgumentException(
                "El peso debe ser positivo"
            );
        }

        this.kg = kg;
    }

    public void setKm(double km) {
        if (!Double.isFinite(km) || km <= 0) {
            throw new IllegalArgumentException(
                "La distancia debe ser positiva"
            );
        }

        this.km = km;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        if (fechaHora == null) {
            throw new IllegalArgumentException(
                "La fecha y hora no pueden ser nulas"
            );
        }

        this.fechaHora = fechaHora;
    }

    // CAMBIAR ESTADO
    public void cambiarEstado(String nuevoEstado) {

        if (nuevoEstado == null) {
            throw new IllegalArgumentException(
                "El nuevo estado no puede ser nulo"
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
            throw new IllegalStateException(
                "No se puede cambiar de " + estado +
                " a " + nuevoEstado
            );
        }

        this.estado = nuevoEstado;
    }

    // MÉTODO DE LA INTERFAZ RASTEABLE
    @Override
    public String obtenerEstadoActual() {
        return estado;
    }

    // MÉTODO ABSTRACTO
    public abstract double calcularCosto(Vehiculo vehiculo);

    // MÉTODO DE LA INTERFAZ FACTURABLE
    @Override
    public double calcularImporte() {
        throw new UnsupportedOperationException(
            "El importe requiere conocer el vehículo asignado"
        );
    }

    // TOSTRING
    @Override
    public String toString() {
        return "Pedido: " + codigo +
               "\nCliente: " + cliente +
               "\nDestino: " + destino +
               "\nPeso: " + kg + " kg" +
               "\nDistancia: " + km + " km" +
               "\nFecha y hora de ingreso: " + fechaHora +
               "\nEstado: " + estado;
    }
}