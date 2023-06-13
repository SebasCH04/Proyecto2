public class Megaburger extends Hamburguesa {
    public Megaburger() {
        super("Megaburger", 2500);
    }

    @Override
    public void calcularPrecio() {
        System.out.println("El precio de la hamburguesa es: " + precio);
    }
}