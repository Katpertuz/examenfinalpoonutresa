package modelo;

/** Lo que se le suma a un producto: aguacate, quesito... No está en la carta. */
public class Adicion implements Cobrable {
    private final String nombre;
    private final double precio;

    public Adicion(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() { return nombre; }

    @Override
    public double calcularPrecio() {
        return precio;
    }
}
