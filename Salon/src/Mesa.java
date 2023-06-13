public class Mesa {
    private int numeroMesa;
    private boolean ocupada;
    private Orden orden;

    public Mesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
        this.ocupada = false;
        this.orden = new Orden(numeroMesa);
    }

    public void ocuparMesa() {
        this.ocupada = true;
    }

    public void desocuparMesa() {
        this.ocupada = false;
        this.orden = null;
    }

    public int calcularPrecioTotal() {
        if (orden != null) {
            return orden.calcularPrecioTotal();
        } else {
            System.out.println("No hay una orden asociada a la mesa " + numeroMesa + ".");
        }
        return 0;
    }

    //Getters y setters

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }
}
