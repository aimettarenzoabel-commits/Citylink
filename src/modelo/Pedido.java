package modelo;

public class Pedido{

    // ATRIBUTOS
    private int codigo;
    private String cliente;
    private String destino;
    private float kg;
    private float km;
    private int fechayhoradeentrega;
    private String estadoytipo;

    // CONSTRUCTOR VACÍO
    public Pedido() {
    }
    
    // CONSTRUCTOR COMPLETO
    public Pedido(int codigo, String cliente, String destino, float kg, float km, int fechayhoradeentrega, String estadoytipo) {
        setCodigo(codigo);
        setCliente(cliente);
        setDestino(destino);
        setKg(kg);
        setKm(km);
        setFechayHoradeEntrega(fechayhoradeentrega);
        setEstadoyTipo(estadoytipo);
    }
}