import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class hiloCliente extends Thread {
    private Socket socket_cliente;

    public hiloCliente(Socket socket_cliente) {
        this.socket_cliente = socket_cliente;
    }

    @Override
    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket_cliente.getInputStream()));
            PrintWriter salida = new PrintWriter(socket_cliente.getOutputStream(), true);
            BufferedReader lectorServidor = new BufferedReader(new InputStreamReader(System.in));

            String datos_recibidos;
            while ((datos_recibidos = entrada.readLine()) != null) {
                System.out.println("Mensaje de " + socket_cliente.getInetAddress().getHostAddress() + ": " + datos_recibidos);

                if (datos_recibidos.equals("Adios")) {
                    // Enviar confirmación de cierre al cliente
                    salida.println("Conexion cerrada por el servidor");
                    break; // Salir del bucle al recibir "Adios"
                }

                // Lectura de entrada del servidor para enviar al cliente
                System.out.print("Yo: ");
                String mensajeServidor = lectorServidor.readLine();
                salida.println(mensajeServidor);
            }

            // Cerrar la conexión con el cliente
            socket_cliente.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                // Cerrar la conexión con el cliente
                socket_cliente.close();
                System.out.println("Conexión cerrada desde el cliente");
                System.out.println("---------------------------------");
                System.out.println("Esperando Conexiones...");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
