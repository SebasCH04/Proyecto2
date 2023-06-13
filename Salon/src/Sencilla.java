public class Sencilla extends Hamburguesa {
    
    //Se utiliza polimorfismo para crear esta hamburguesa Sencilla
    public Sencilla() {
        super("Hamburguesa Sencilla", 1500);
    }

    @Override
    public void calcularPrecio() {
        System.out.println("El precio de la hamburguesa es: " + precio);
    }
}