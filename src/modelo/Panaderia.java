package modelo;

/** Se vende por porción: una porción de pandequeso trae varias unidades. */
public class Panaderia extends ProductoMenu {
    private final int unidades;
    private final boolean recienHorneado;

    public Panaderia(String codigo, String nombre, double precioBase, int stock,
                     int unidades, boolean recienHorneado) {
        super(codigo, nombre, precioBase, stock);
        this.unidades = unidades;
        this.recienHorneado = recienHorneado;
    }

    public int getUnidades() { return unidades; }

    /** Precio de la porción: el precio base es por unidad. */
    @Override
    public double calcularPrecio() {
        return getPrecioBase() * unidades;
    }

    /** Lo recién horneado tarda 12 minutos; lo de vitrina, 3. */
    @Override
    public int minutosPreparacion() {
        return recienHorneado ? 12 : 3;
    }

    @Override
    public String getCategoria() {
        return "Panadería";
    }

    @Override
    public boolean esVegetariano() {
        return true;
    }
}
