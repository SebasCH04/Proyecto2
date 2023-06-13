import java.util.ArrayList;
import java.util.List;

public class Hamburguesa {
    private String nombre;
    private List<String> ingredientes;
    public int precio;

    //Se utiliza polimorfismo para crear los 3 tipos de hamburguesas POO, Megaburger y Sencilla
    public Hamburguesa(String nombre, int precio) {
        this.nombre = nombre;
        this.ingredientes = new ArrayList<>();
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public List<String> getIngredientes() {
        return ingredientes;
    }

    public int getPrecio() {
        return precio;
    }

    public void agregarIngredientes(String ingrediente) {
        ingredientes.add(ingrediente);
        precio += 500;
    }

    public void calcularPrecio() {
    }
}