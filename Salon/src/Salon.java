import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Salon extends JFrame implements Runnable {

    private List<Mesa> mesas;
    private Mesa mesaSeleccionada;

    private JComboBox<String> mesaComboBox;
    private JButton ocuparMesaButton;
    private JButton seguirOrdenButton;
    private JButton agregarButton;
    private JButton calcularButton;
    private JButton desocuparButton;

    public Salon() {
        super("Restaurante de Sebas & Angelo");

        //Inicializa el hilo
        Thread hilo = new Thread(this);
        hilo.start();

        //Inicializa las mesas
        mesas = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Mesa mesa = new Mesa(i);
            mesas.add(mesa);
        }

        //Configura la ventana principal
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new FlowLayout());

        //Crea los componentes
        mesaComboBox = new JComboBox<>();
        for (int i = 1; i <= mesas.size(); i++) {
            mesaComboBox.addItem("Mesa " + i);
        }

        ocuparMesaButton = new JButton("Ocupar Mesa");
        seguirOrdenButton = new JButton("Seguir con Orden");
        agregarButton = new JButton("Agregar Hamburguesa");
        calcularButton = new JButton("Calcular Precio y Pagar");
        desocuparButton = new JButton("Desocupar Mesa");

        //Agrega los componentes a la ventana
        add(mesaComboBox);
        add(ocuparMesaButton);
        add(seguirOrdenButton);
        add(agregarButton);
        add(calcularButton);
        add(desocuparButton);

        //Asigna algunos controladores de eventos a los botones
        ocuparMesaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ocuparMesa();
            }
        });

        seguirOrdenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                seguirOrden();
            }
        });

        agregarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarHamburguesa();
            }
        });

        calcularButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calcularPrecioYPagar();
            }
        });

        desocuparButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                desocuparMesa();
            }
        });
    }

    private void ocuparMesa() {
        int numeroMesa = mesaComboBox.getSelectedIndex();

        if (numeroMesa >= 0 && numeroMesa < mesas.size()) {
            Mesa mesa = mesas.get(numeroMesa);

            if (!mesa.isOcupada()) {
                mesa.ocuparMesa();
                mesa.setOrden(new Orden(mesa.getNumeroMesa())); //Pasa el numero de mesa
                mesaSeleccionada = mesa;

                JOptionPane.showMessageDialog(Salon.this, "La mesa " + mesa.getNumeroMesa() + " ha sido ocupada.");
                JOptionPane.showMessageDialog(Salon.this, "Puede comenzar a hacer pedidos para esta mesa.");
            } else {
                JOptionPane.showMessageDialog(Salon.this, "La mesa " + mesa.getNumeroMesa() + " ya está ocupada. Seleccione otra mesa.");
            }
        } else {
            JOptionPane.showMessageDialog(Salon.this, "Número de mesa inválido. Seleccione una mesa válida.");
        }
    }


    private void seguirOrden() {
        int numeroMesa = mesaComboBox.getSelectedIndex();

        if (numeroMesa >= 0 && numeroMesa < mesas.size()) {
            Mesa mesa = mesas.get(numeroMesa);

            if (mesa.isOcupada()) {
                mesaSeleccionada = mesa;

                JOptionPane.showMessageDialog(Salon.this, "La mesa " + mesa.getNumeroMesa() + " está ocupada.");
                JOptionPane.showMessageDialog(Salon.this, "Puede continuar la orden para esta mesa.");
            } else {
                JOptionPane.showMessageDialog(Salon.this, "La mesa " + mesa.getNumeroMesa() + " no está ocupada. Seleccione otra mesa.");
            }
        } else {
            JOptionPane.showMessageDialog(Salon.this, "Número de mesa inválido. Seleccione una mesa válida.");
        }
    }

    private void agregarHamburguesa() {
        if (mesaSeleccionada != null && mesaSeleccionada.isOcupada()) {
            int opcion = JOptionPane.showOptionDialog(Salon.this, "Seleccione una hamburguesa:",
                    "Agregar Hamburguesa", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                    new String[]{"POO (3000\u20A1): Pollo, Carne, Jamón, Queso, Cebolla y Salsas", "Megaburger (2500\u20A1): Carne, Jamón, Queso, y Salsas", "Sencilla (1500\u20A1): Carne"}, null);

            if (opcion >= 0 && opcion <= 2) {
                Hamburguesa hamburguesa;
                switch (opcion) {
                    case 0:
                        hamburguesa = new POO();
                        break;
                    case 1:
                        hamburguesa = new Megaburger();
                        break;
                    case 2:
                        hamburguesa = new Sencilla();
                        break;
                    default:
                        hamburguesa = null;
                        break;
                }

                if (hamburguesa != null) {
                    agregarIngredientes(hamburguesa);
                    mesaSeleccionada.getOrden().agregarHamburguesa(hamburguesa);
                    try{
                        Socket socketParaCocina = new Socket("127.0.0.1",1111);
                        DataOutputStream enviarOrden = new DataOutputStream(socketParaCocina.getOutputStream());
                        
                        enviarOrden.writeUTF("Orden de la mesa numero " + mesaSeleccionada.getNumeroMesa() + ": " + hamburguesa.getNombre());

                        socketParaCocina.close();
                        
                    }catch(Exception e){
                        System.out.println(e);
                    }
                    JOptionPane.showMessageDialog(Salon.this, "La hamburguesa fue agregada exitosamente a la orden.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(Salon.this, "No se ha seleccionado una mesa ocupada. Seleccione una mesa ocupada para continuar.");
        }
    }

    private void agregarIngredientes(Hamburguesa hamburguesa) {
        int opcion;
        String ingrediente;

        do {
            opcion = JOptionPane.showOptionDialog(Salon.this, "Seleccione una opción:",
                    "Agregar Ingredientes", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                    new String[]{"Agregar ingrediente", "Finalizar"}, null);

            switch (opcion) {
                case 0:
                    ingrediente = JOptionPane.showInputDialog(Salon.this, "Ingrese el nombre del ingrediente que desea agregar (500\u20A1 c/u):");

                    if (ingrediente != null && !ingrediente.isEmpty()) {
                        hamburguesa.agregarIngredientes(ingrediente);
                    }
                    break;
                case 1:
                    JOptionPane.showMessageDialog(Salon.this, "Finalizando...");
                    break;
                default:
                    JOptionPane.showMessageDialog(Salon.this, "Opción inválida. Intente nuevamente.");
                    break;
            }
        } while (opcion != 1);
    }

    private void calcularPrecioYPagar() {
        int total = 0;
        if (mesaSeleccionada != null && mesaSeleccionada.isOcupada()) {
            total = mesaSeleccionada.calcularPrecioTotal();
            mesaSeleccionada.desocuparMesa(); //Desocupa la mesa al pagar la cuenta
            mesaSeleccionada = null; //Reinicia la mesa seleccionada
            JOptionPane.showMessageDialog(Salon.this, "El precio total de la orden es de: " + total + "\u20A1");
            JOptionPane.showMessageDialog(Salon.this, "La cuenta fue pagada y la mesa fue desocupada.");
        } else {
            JOptionPane.showMessageDialog(Salon.this, "No se ha seleccionado una mesa ocupada. Seleccione una mesa ocupada para continuar.");
        }
    }

    private void desocuparMesa() {
        if (mesaSeleccionada != null && mesaSeleccionada.isOcupada()) {
            mesaSeleccionada.desocuparMesa();
            JOptionPane.showMessageDialog(Salon.this, "Mesa desocupada.");

            //Reinicia la mesa seleccionada solo si es igual a la mesa que se desocupo
            if (mesaSeleccionada.equals(mesaComboBox.getSelectedItem())) {
                mesaSeleccionada = null;
            }
        } else {
            JOptionPane.showMessageDialog(Salon.this, "No se ha seleccionado una mesa ocupada. Seleccione una mesa ocupada para desocupar.");
        }
    }

    @Override
    public void run() {
        try{

            ServerSocket servidorSalon = new ServerSocket(9999);
            
            while(true) {
                Socket socketSalon = servidorSalon.accept();
                DataInputStream notificacionesRecibidas = new DataInputStream(socketSalon.getInputStream());

                String notificaciones = notificacionesRecibidas.readUTF();
                JOptionPane.showMessageDialog(Salon.this, notificaciones);
            }

        }catch (Exception e){
            System.out.println(e);
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Salon frame = new Salon();
                frame.setVisible(true);
            }
        });
    }
}