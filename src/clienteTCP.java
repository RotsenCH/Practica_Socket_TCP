import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class clienteTCP {
    public static void main(String[] args) {
        try {
            Socket socket_cliente = new Socket("localhost", 1234);

            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket_cliente.getInputStream()));
            PrintWriter salida = new PrintWriter(socket_cliente.getOutputStream(), true);
            BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));

            String mensaje;
            while (true) {
                System.out.print("Yo: ");
                mensaje = lector.readLine();
                salida.println(mensaje);

                // Verificar si se envía "Adios" para cerrar la conexión
                if (mensaje.equals("Adios")) {
                    break; // Salir del bucle
                }

                // Leer datos recibidos del servidor
                String datos_recibidos = entrada.readLine();
                System.out.println("Servidor: " + datos_recibidos);
            }

            // Cerrar la conexión con el servidor
            socket_cliente.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
