package modelo;
import java.time.LocalDateTime;
public class PedidoFragil extends Pedido{
    //CONSTRUCTOR
    public PedidoFragil(int codigo, Cliente cliente, String destino, double kg, double km, LocalDateTime fechaHora) {
        super(codigo, cliente, destino, kg, km, fechaHora); //En lugar de repetir todos los atributos, getters y setters dentro de PedidoFragil, llamamos al constructor de Pedido.
    }
    //CALCULAR COSTO
    @Override
    public double calcularCosto(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new IllegalArgumentException("El vehículo no puede ser nulo");
        }

        if (!vehiculo.puedeTransportar(this)) {
            throw new IllegalArgumentException("El vehículo no puede transportar este pedido");
        }

        double costoBase = 1000;
        double costoDistancia = getKm() * vehiculo.getCostoPorKm();
        double recargoFragil = 800;

        return costoBase + costoDistancia + recargoFragil;
    }
}