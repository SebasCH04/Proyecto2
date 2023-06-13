public class POO extends Hamburguesa {
        
        //Se utiliza polimorfismo para crear esta hamburguesa POO
        public POO() {
            super("POO", 3000);
        }

        @Override
        public void calcularPrecio() {
            System.out.println("El precio de la hamburguesa es: " + precio);
        }
    }