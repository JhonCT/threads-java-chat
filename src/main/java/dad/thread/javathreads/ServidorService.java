package dad.thread.javathreads;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import org.springframework.stereotype.Service;

@Service
public class ServidorService implements ServidorDao{
	private Socket socket;
    private ServerSocket serverSocket;
    private DataInputStream bufferDeEntrada = null;
    private DataOutputStream bufferDeSalida = null;
    Scanner escaner = new Scanner(System.in);
    final String COMANDO_TERMINACION = "salir()";
    
	@Override
	public void levantarConexion() {
		try {
            serverSocket = new ServerSocket(1050);
            System.out.println("Esperando conexión entrante en el puerto " + String.valueOf(1050) + "...");
            socket = serverSocket.accept();
            System.out.println("Conexión establecida con: " + socket.getInetAddress().getHostName() + "\n\n\n");                                  
        } catch (Exception e) {
            System.out.println("Error en levantarConexion(): " + e.getMessage());
            System.exit(0);
        }		
	}

	@Override
	public void flujos() {
		try {
            bufferDeEntrada = new DataInputStream(socket.getInputStream());
            bufferDeSalida = new DataOutputStream(socket.getOutputStream());
            bufferDeSalida.flush();
        } catch (IOException e) {
            System.out.println("Error en la apertura de flujos");
        }
	}

	@Override
	public void recibirDatos() {
		String st = "";
        try {
            do {  
                st = (String) bufferDeEntrada.readUTF();
                System.out.println("\n[Cliente] => " + st);
                System.out.print("\n[Usted] => ");
            } while (!st.equals(COMANDO_TERMINACION));
        } catch (IOException e) {
            cerrarConexion();
        }
		
	}

	@Override
	public void enviar(String s) {
		try {
            bufferDeSalida.writeUTF(s);
            bufferDeSalida.flush();
        } catch (IOException e) {
            System.out.println("Error en enviar(): " + e.getMessage());
        }		
	}

	@Override
	public void escribirDatos(String mensaje) {
		enviar(mensaje);		
	}

	@Override
	public void cerrarConexion() {
		 try {
	            bufferDeEntrada.close();
	            bufferDeSalida.close();
	            socket.close();
	        } catch (IOException e) {
	          System.out.println("Excepción en cerrarConexion(): " + e.getMessage());
	        } finally {
	            System.out.println("Conversación finalizada....");
	            System.exit(0);

	        }
		
	}

	@Override
	public void ejecutarConexion() {
		Thread hilo = new Thread(new Runnable() {
            
            public void run() {
                while (true) {
                    try {
                        levantarConexion();
                        flujos();
                        recibirDatos();
                    } finally {
                        cerrarConexion();
                    }
                }
            }
        });
        hilo.start();
	}
}