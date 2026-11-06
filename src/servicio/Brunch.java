package servicio;

import java.util.ArrayList;
import modelo.LineaPedido;
import modelo.Mesa;
import modelo.Pedido;
import modelo.ProductoMenu;

public class Brunch {
    private final String nombre;
    private final ArrayList<ProductoMenu> menu;
    private final ArrayList<Mesa> mesas;
    private final ArrayList<Pedido> pedidos;

    public Brunch(String nombre) {
        this.nombre = nombre;
        this.menu = new ArrayList<>();
        this.mesas = new ArrayList<>();
        this.pedidos = new ArrayList<>();
    }

    public String getNombre() { return nombre; }
    public int getCantidadPedidos() { return pedidos.size(); }

    public ArrayList<ProductoMenu> getMenu() {
        return menu;
    }

    public void agregarAlMenu(ProductoMenu producto) {
        if (producto != null) {
            menu.add(producto);
        }
    }

    public void agregarMesa(Mesa mesa) {
        if (mesa != null) {
            mesas.add(mesa);
        }
    }

    /** Busca por código sin importar mayúsculas o minúsculas: "pan-001" encuentra a "PAN-001". */
    public ProductoMenu buscarProducto(String codigo) {
        for (ProductoMenu p : menu) {
            if (p.getCodigo().equalsIgnoreCase (codigo)) {
                return p;
            }
        }
        return null;
    }

    /** Retorna la mesa con ese número o null si no existe. */
    public Mesa buscarMesa(int numero) {
        for (Mesa m : mesas) {
            if (m.getNumero() == numero) {
                return m;
            }
        }
        return null;
    }

    /** Abre la cuenta SOLO si la mesa existe y puede recibir al grupo. Ocupa la mesa. */
    public Pedido abrirPedido(int numeroMesa, int personas, boolean aceptaPropina) {
        Mesa mesa = buscarMesa(numeroMesa);
        if (mesa == null) {
            System.out.println("La mesa " + numeroMesa + " no existe.");
            return null;
        }
        if (!mesa.puedeRecibir(personas)) {
            System.out.println("La mesa " + numeroMesa + " no puede recibir a " + personas + " personas.");
            return null;
        }
        mesa.ocupar();
        String codigo = String.format("PED-%03d", pedidos.size() + 1);
        Pedido pedido = new Pedido(codigo, mesa, personas, aceptaPropina);
        pedidos.add(pedido);
        return pedido;
    }

    private boolean v(ProductoMenu p, int c) {
        boolean r;
        if (p != null && p.puedeVender(c) == true) {
            r = true;
        } else {
            r = false;
        }
        return r;
    }

    /** Agrega el producto SOLO si se puede vender, y lo descuenta del stock. */
    public LineaPedido agregarAlPedido(Pedido pedido, String codigoProducto, int cantidad) {
        ProductoMenu producto = buscarProducto(codigoProducto);
        if (!v(producto, cantidad)) {
            System.out.println("No se puede vender " + cantidad + " de " + codigoProducto + ".");
            return null;
        }
        producto.vender(cantidad);
        LineaPedido linea = new LineaPedido(producto, cantidad);
        pedido.agregarLinea(linea);
        return linea;
    }

    /** Cobra el pedido y deja la mesa libre para el siguiente grupo. */
    public boolean cerrarPedido(Pedido pedido) {
        if (pedido == null || pedido.isPagado()) {
            pedido.getMesa();
            return false;
        }
        pedido.marcarPagado();
        pedido.getMesa().liberar();
        return true;
    }

    /** Suma lo que pagaron los pedidos YA cerrados. Una cuenta abierta todavía no es venta. */
    public double calcularVentasDelDia() {
        double total = 0;
        for (Pedido p : pedidos) {
            if (p.isPagado()) {
                total += p.calcularPrecio();
            }
        }
        return total;
    }
}
