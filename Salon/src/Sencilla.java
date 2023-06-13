public class Sencilla extends Hamburguesa {
    public Sencilla() {
        super("HamburguesaSencilla", 1500);
    }

    @Override
    public void calcularPrecio() {
        System.out.println("El precio de la hamburguesa es: " + precio);
    }
}