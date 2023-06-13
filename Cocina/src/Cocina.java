import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Cocina extends JFrame implements Runnable {

    private JTextArea ordenesPendientesList;
    private JButton ordenListaButton;

    public Cocina() {
        super("Cocina de Sebas & Angelo");
        
        //Inicializa el hilo
        Thread hilo = new Thread(this);
        hilo.start();

        //Ventana principal
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        //Crea los componentes
        ordenesPendientesList = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(ordenesPendientesList);
        ordenListaButton = new JButton("Orden Lista");

        //Agrega los componentes
        add(scrollPane, BorderLayout.CENTER);
        add(ordenListaButton, BorderLayout.SOUTH);

        //Asigna un boton de "Orden Lista"
        ordenListaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                marcarOrdenLista();
            }
        });
    }

    private void marcarOrdenLista() {
        
        try{
            Socket socketParaSalon = new Socket("127.0.0.1",9999);
            DataOutputStream enviarNotificacion = new DataOutputStream(socketParaSalon.getOutputStream());
                        
            enviarNotificacion.writeUTF("La orden ya está lista.");

            socketParaSalon.close();
                        
        }catch(Exception e){
            System.out.println(e);
        }

        ordenesPendientesList.setText(null);
        JOptionPane.showMessageDialog(Cocina.this, "La orden se marcó como lista.");
    }

    @Override
    public void run() {
        try{

            ServerSocket servidorCocina = new ServerSocket(1111);
            
            while(true) {
                Socket socketCocina = servidorCocina.accept();
                DataInputStream ordenesRecibidas = new DataInputStream(socketCocina.getInputStream());

                String ordenes = ordenesRecibidas.readUTF();
                ordenesPendientesList.append("\n" + ordenes);
            }

        }catch (Exception e){
            System.out.println(e);
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Cocina frame = new Cocina();
                frame.setVisible(true);
            }
        });
    }
}