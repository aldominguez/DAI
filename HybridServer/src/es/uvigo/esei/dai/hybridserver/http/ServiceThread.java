package es.uvigo.esei.dai.hybridserver.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Properties;

//import es.uvigo.esei.dai.hybridserver.MainRunnable;



public class ServiceThread implements Runnable {
	private Socket socket;

	public ServiceThread(Socket socket, Properties pag) {
		this.socket = socket;
	}

	public ServiceThread(Socket socket, Map<String, String> pages) {
		this.socket = socket;
	}

	public ServiceThread(Socket clientSocket) {
	}

	@Override
	public void run() {

		try (Socket socket = this.socket) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			try {
				HTTPRequest request = new HTTPRequest(reader); // Recibo Request del HybridServer y almaceno.
				DAO MemoryDAO = new HTMLDao();  // Instancio la clase que accede a la base de datos.
				Controller control = new Controller(MemoryDAO); // Creo una instancia que le paso un MemoryDAO (objeto que puede acceder a la base de datos) 
				HTTPResponse response = control.getDatos(request);
				response.print(new PrintWriter(socket.getOutputStream()));
				

			} catch (HTTPParseException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}
