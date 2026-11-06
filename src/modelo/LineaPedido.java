package modelo;

import java.util.ArrayList;

/** Un renglón de la cuenta: un producto, cuántos se piden y sus adiciones. */
public class LineaPedido implements Cobrable {
    private final ProductoMenu producto;
    private final int cantidad;
    private final ArrayList<Adicion> adiciones;

    public LineaPedido(ProductoMenu producto, int cantidad) {
        if (producto == null) {
            throw new IllegalArgumentException("La línea necesita un producto");
        }
        this.producto = producto;
        this.cantidad = cantidad;
        this.adiciones = new ArrayList<>();
    }

    public ProductoMenu getProducto() { return producto; }
    public int getCantidad() { return cantidad; }

    public void agregarAdicion(Adicion adicion) {
        if (adicion != null) {
            adiciones.add(adicion);
        }
    }

    /** CADA unidad lleva sus adiciones: (precio del producto + adiciones) × cantidad. */
    @Override
    public double calcularPrecio() {
        double sumaAdiciones = 0;
        for (Adicion a : adiciones) {
            sumaAdiciones += a.calcularPrecio();
        }
        return (producto.calcularPrecio() + sumaAdiciones) * cantidad;
    }

    /** Dónde se prepara: los platos en Cocina, las bebidas en Barra y la panadería en Horno. */
    public String obtenerEstacion() {
        if (producto instanceof PlatoFuerte) {
            return "Cocina";
        } else if (producto instanceof Bebida) {
            return "Barra";
        } else if (producto instanceof Panaderia) {
            return "Horno";
        }
        return "Sin estación";
    }

    public String obtenerComanda() {
        String texto = cantidad + " x " + producto.getNombre();
        for (Adicion a : adiciones) {
            texto += " + " + a.getNombre();
        }
        return texto + " -> " + obtenerEstacion();
    }
}
