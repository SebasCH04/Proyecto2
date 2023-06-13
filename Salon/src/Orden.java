import java.util.ArrayList;
import java.util.List;

public class Orden {
    private int numMesa;
    private List<Hamburguesa> hamburguesasPedidas;
    private boolean estado;
    
    //Se crea una orden que recibe el numero de mesa de la que se ordeno
    public Orden(int numMesa) {
        this.numMesa = numMesa;
        this.hamburguesasPedidas = new ArrayList<>();
        this.estado = false; //False para Pendiente, true para Lista
    }

    public void agregarHamburguesa(Hamburguesa hamburguesas) {
        this.hamburguesasPedidas.add(hamburguesas);
    }

    public void actualizarEstado(boolean estado) {
        this.estado = true; //Se actaliza para saber que ya esta lista
    }

    //Formula para calcular el precio total de la orden 
    public int calcularPrecioTotal() {
        int precioTotal = 0;

        for (Hamburguesa hamburguesa : hamburguesasPedidas) {
            precioTotal += hamburguesa.getPrecio();
        }

        return precioTotal;
    }

    //Getters y setters

    public int getNumMesa() {
        return numMesa;
    }

    public void setNumMesa(int numMesa) {
        this.numMesa = numMesa;
    }

    public List<Hamburguesa> getHamburguesasPedidas() {
        return hamburguesasPedidas;
    }

    public boolean getEstado() {
        return estado;
    }
}
