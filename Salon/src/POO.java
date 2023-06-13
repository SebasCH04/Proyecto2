public class POO extends Hamburguesa {
        public POO() {
            super("POO", 3000);
        }

        @Override
        public void calcularPrecio() {
            System.out.println("El precio de la hamburguesa es: " + precio);
        }
    }