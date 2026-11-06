import java.util.ArrayList;
import modelo.Adicion;
import modelo.Cobrable;
import modelo.Identificable;
import modelo.LineaPedido;
import modelo.Mesa;
import modelo.Panaderia;
import modelo.Pedido;
import modelo.PlatoFuerte;
import modelo.ProductoMenu;
import servicio.Brunch;
import modelo.Bebida;
public class Main {
    public static void main(String[] args) {
        System.out.println("=== 1. Carta del domingo ===");
        Brunch brunch = new Brunch("El Brunch POO");
        //                                   código     nombre  precio stock vegetariano proteínaExtra
        brunch.agregarAlMenu(new PlatoFuerte("PLA-001", "Calentado paisa", 26000, 8, false, false));
        brunch.agregarAlMenu(new PlatoFuerte("PLA-002", "Calentado con chicharrón", 26000, 6, false, true));
        brunch.agregarAlMenu(new PlatoFuerte("PLA-003", "Huevos pericos con arepa", 18000, 10, true, false));
        brunch.agregarAlMenu(new PlatoFuerte("PLA-004", "Arepa de chócolo con quesito", 16000, 5, true, false));
        //                              código     nombre  precio stock mililitros caliente
        brunch.agregarAlMenu(new Bebida("BEB-001", "Tinto de Jardín", 5000, 30, 250, true));
        brunch.agregarAlMenu(new Bebida("BEB-002", "Chocolate en leche", 7000, 20, 300, true));
        brunch.agregarAlMenu(new Bebida("BEB-003", "Jugo de mandarina", 9000, 15, 500, false));
        brunch.agregarAlMenu(new Bebida("BEB-004", "Limonada de coco", 10000, 15, 350, false));
        //                                 código     nombre  precio stock unidades recienHorneado
        brunch.agregarAlMenu(new Panaderia("PAN-001", "Pandequeso", 3500, 12, 2, true));
        brunch.agregarAlMenu(new Panaderia("PAN-002", "Buñuelo", 3000, 5, 3, false));
        for (ProductoMenu p : brunch.getMenu()) {
            System.out.println("  - " + p.obtenerFicha());
        }

        System.out.println("\n=== 2. Búsquedas y disponibilidad ===");
        ProductoMenu buscado = brunch.buscarProducto("pan-001");
        System.out.println("Buscar pan-001: " + (buscado != null ? buscado.getNombre() : "no encontrado"));
        System.out.println("Buscar XXX-999: " + (brunch.buscarProducto("XXX-999") != null ? "encontrado" : "no encontrado"));
        ProductoMenu arepa = brunch.buscarProducto("PLA-004");
        System.out.println("¿Se puede vender 1 arepa? " + arepa.puedeVender(1));
        arepa.setDisponible(false);
        System.out.println("Se acabó el chócolo. ¿Se puede vender 1 arepa? " + arepa.puedeVender(1));

        System.out.println("\n=== 3. Mesas ===");
        brunch.agregarMesa(new Mesa(1, 2)); 
        brunch.agregarMesa(new Mesa(2, 4));
        brunch.agregarMesa(new Mesa(3, 6));
        Mesa mesaTres = brunch.buscarMesa(3);
        System.out.println("¿La mesa 3 recibe a 6 personas? " + mesaTres.puedeRecibir(6));
        System.out.println("¿La mesa 3 recibe a 7 personas? " + mesaTres.puedeRecibir(7));

        System.out.println("\n=== 4. Pedidos ===");
        Adicion aguacate = new Adicion("Aguacate", 4000);
        Adicion quesito = new Adicion("Quesito", 3500);

        Pedido familia = brunch.abrirPedido(3, 4, true);
        LineaPedido calentados = brunch.agregarAlPedido(familia, "PLA-002", 2);
        calentados.agregarAdicion(aguacate);
        calentados.agregarAdicion(quesito);
        brunch.agregarAlPedido(familia, "PLA-003", 2);
        brunch.agregarAlPedido(familia, "BEB-002", 4);
        brunch.agregarAlPedido(familia, "BEB-003", 3);
        brunch.agregarAlPedido(familia, "PAN-001", 1);

        Pedido amigos = brunch.abrirPedido(2, 4, false);
        brunch.agregarAlPedido(amigos, "PLA-003", 2);
        brunch.agregarAlPedido(amigos, "PLA-001", 2);
        brunch.agregarAlPedido(amigos, "BEB-001", 4);

        Pedido pareja = brunch.abrirPedido(1, 2, true);
        brunch.agregarAlPedido(pareja, "PLA-003", 2);
        brunch.agregarAlPedido(pareja, "BEB-004", 2);

        LineaPedido sinChocolo = brunch.agregarAlPedido(pareja, "PLA-004", 1);
        System.out.println("¿Se vendió la arepa no disponible? " + (sinChocolo != null));
        LineaPedido sinBunuelos = brunch.agregarAlPedido(pareja, "PAN-002", 6);
        System.out.println("¿Se vendieron 6 buñuelos? " + (sinBunuelos != null));
        LineaPedido conBunuelos = brunch.agregarAlPedido(pareja, "PAN-002", 2);
        System.out.println("¿Se vendieron 2 buñuelos? " + (conBunuelos != null));
        Pedido sinMesa = brunch.abrirPedido(9, 2, true);
        System.out.println("¿Se abrió el pedido de la mesa 9? " + (sinMesa != null));
        Pedido ocupada = brunch.abrirPedido(3, 2, true);
        System.out.println("¿Se abrió otro pedido en la mesa 3? " + (ocupada != null));

        System.out.println("Comanda de la familia:");
        for (LineaPedido linea : familia.getLineas()) {
            System.out.println("  - " + linea.obtenerComanda());
        }
        System.out.println("Quedan de chocolate: " + brunch.buscarProducto("BEB-002").getStock());
        System.out.println("Quedan de buñuelo: " + brunch.buscarProducto("PAN-002").getStock());
        System.out.println("Pedido de amigos, ¿vegetariano? " + amigos.esVegetariano());
        System.out.println("Pedido de pareja, ¿vegetariano? " + pareja.esVegetariano());

        System.out.println("\n=== 5. Cuentas ===");
        System.out.println(familia.obtenerFicha());
        System.out.println(familia.obtenerCuenta());
        System.out.println(amigos.obtenerFicha());
        System.out.println(amigos.obtenerCuenta());
        System.out.println(pareja.obtenerFicha());
        System.out.println(pareja.obtenerCuenta());

        System.out.println("\n=== 6. Cierre del día ===");
        System.out.println("¿Se cerró la familia? " + brunch.cerrarPedido(familia));
        System.out.println("¿Se cerró dos veces? " + brunch.cerrarPedido(familia));
        System.out.println("¿Se cerró la pareja? " + brunch.cerrarPedido(pareja));
        System.out.println("¿La mesa 3 quedó libre? " + !mesaTres.isOcupada());
        Pedido siguiente = brunch.abrirPedido(3, 5, true);
        System.out.println("¿Se abrió el siguiente grupo en la mesa 3? " + (siguiente != null));
        System.out.println("Pedidos abiertos en el día: " + brunch.getCantidadPedidos());
        System.out.printf("Ventas del día: $%.0f%n", brunch.calcularVentasDelDia());

        System.out.println("\n=== 7. Polimorfismo ===");
        Identificable conCodigo = familia;
        System.out.println("Como Identificable: " + conCodigo.getCodigo());
        ArrayList<Cobrable> cosasConPrecio = new ArrayList<>();
        cosasConPrecio.add(brunch.buscarProducto("PAN-001"));
        cosasConPrecio.add(quesito);
        cosasConPrecio.add(calentados);
        cosasConPrecio.add(pareja);
        for (Cobrable c : cosasConPrecio) {
            System.out.printf("%s vale $%.0f%n", c.getClass().getSimpleName(), c.calcularPrecio());
        }
    }
}
