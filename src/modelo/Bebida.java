package modelo;

public class Bebida extends ProductoMenu {
    public static final double RECARGO_TAMANO_GRANDE = 2500;
    public static final int MILILITROS_TAMANO_GRANDE = 500;

    private final int mililitros;
    private final boolean caliente;

    public Bebida(String codigo, String nombre, double precioBase, int stock,
                  int mililitros, boolean caliente) {
        super(codigo, nombre, precioBase, stock);
        this.mililitros = mililitros;
        this.caliente = caliente;
    }

    public int getMililitros() { return mililitros; }
    public boolean isCaliente() { return caliente; }

    /** Las bebidas de 500 ml o más son tamaño grande y suben $2.500. */
    @Override
    public double calcularPrecio() {
        if (mililitros > MILILITROS_TAMANO_GRANDE) {
            return super.calcularPrecio() + RECARGO_TAMANO_GRANDE;
        }
        return super.calcularPrecio();
    }

    /** Las calientes salen en 4 minutos; las frías, en 6. */
    @Override
    public int minutosPreparacion() {
        return caliente ? 4 : 6;
    }

    @Override
    public String getCategoria() {
        return "Bebida";
    }

    @Override
    public boolean esVegetariano() {
        return true;
    }
}
