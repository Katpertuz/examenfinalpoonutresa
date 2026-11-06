package modelo;

public class PlatoFuerte extends ProductoMenu {
    public static final double RECARGO_PROTEINA_EXTRA = 6000;

    private final boolean vegetariano;
    private final boolean conProteinaExtra;

    public PlatoFuerte(String codigo, String nombre, double precioBase, int stock,
                       boolean vegetariano, boolean conProteinaExtra) {
        super(codigo, nombre, precioBase,stock);
        this.vegetariano = vegetariano;
        this.conProteinaExtra = conProteinaExtra;
    }

    public boolean isConProteinaExtra() { return conProteinaExtra; }

    /** La proteína extra (chicharrón, carne en polvo) sube el precio en $6.000. */
    @Override
    public double calcularPrecio() {
        if (conProteinaExtra) {
            return super.calcularPrecio() + RECARGO_PROTEINA_EXTRA;
        }
        return super.calcularPrecio();
    }

    /** Un plato tarda 20 minutos; con proteína extra, 25. */
    @Override
    public int minutosPreparacion() {
        return conProteinaExtra ? 25 : 20;
    }

    @Override
    public String getCategoria() {
        return "Plato fuerte";
    }

    @Override
    public boolean esVegetariano() {
        return vegetariano;
    }
}
