package app;

import excepciones.AlmacenamientoLlenoException;
import excepciones.ClienteNoEncontradoException;
import modelo.Citylink;
import modelo.Cliente;

public class PruebaCitylink {

    public static void main(String[] args)
            throws AlmacenamientoLlenoException, ClienteNoEncontradoException {

        Citylink sistema = new Citylink();

        Cliente cliente1 = new Cliente(
            "Mateo", "2664000000", 1, "Centro"
        );

        Cliente cliente2 = new Cliente(
            "Juan", "2664111111", 2, "Norte"
        );

        // PRUEBA 1: registrar clientes
        sistema.agregarCliente(cliente1);
        sistema.agregarCliente(cliente2);

        System.out.println(
            "Clientes registrados: " + sistema.getCantidadClientes()
        );

        // PRUEBA 2: intentar registrar un código duplicado
        Cliente duplicado = new Cliente(
            "Pedro", "2664222222", 1, "Sur"
        );

        try {
            sistema.agregarCliente(duplicado);
        } catch (IllegalArgumentException e) {
            System.out.println("Duplicado rechazado: " + e.getMessage());
        }

        // PRUEBA 3: intentar registrar un cliente nulo
        try {
            sistema.agregarCliente(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Nulo rechazado: " + e.getMessage());
        }

        // PRUEBA 4: buscar un cliente por código
        Cliente encontradoPorCodigo = sistema.buscarCliente(1);
        System.out.println(
            "Encontrado por código: " + encontradoPorCodigo.getNombre()
        );

        // PRUEBA 5: buscar un cliente por nombre, sin distinguir mayúsculas
        Cliente encontradoPorNombre = sistema.buscarCliente("juan");
        System.out.println(
            "Encontrado por nombre: " + encontradoPorNombre.getNombre()
        );

        // PRUEBA 6: buscar un cliente inexistente
        try {
            sistema.buscarCliente(99);
        } catch (ClienteNoEncontradoException e) {
            System.out.println("Búsqueda inexistente: " + e.getMessage());
        }

        // PRUEBA 7: completar la capacidad máxima de 50 clientes
        for (int codigo = 3; codigo <= 50; codigo++) {
            Cliente cliente = new Cliente(
                "Cliente " + codigo,
                "2664000000",
                codigo,
                "Centro"
            );
            sistema.agregarCliente(cliente);
        }

        System.out.println(
            "Clientes al alcanzar la capacidad: " + sistema.getCantidadClientes()
        );

        // PRUEBA 8: intentar superar la capacidad máxima
        try {
            Cliente clienteExtra = new Cliente(
                "Cliente extra", "2664999999", 51, "Sur"
            );
            sistema.agregarCliente(clienteExtra);
        } catch (AlmacenamientoLlenoException e) {
            System.out.println("Capacidad máxima controlada: " + e.getMessage());
        }

        System.out.println(
            "Cantidad final: " + sistema.getCantidadClientes()
        );
    }
}
