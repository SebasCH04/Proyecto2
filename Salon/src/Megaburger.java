public class Megaburger extends Hamburguesa {
    
    //Se utiliza polimorfismo para crear esta hamburguesa Megaburger
    public Megaburger() {
        super("Megaburger", 2500);
    }

    @Override
    public void calcularPrecio() {
        System.out.println("El precio de la hamburguesa es: " + precio);
    }
}