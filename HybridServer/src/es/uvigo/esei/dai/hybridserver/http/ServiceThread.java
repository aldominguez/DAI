package es.uvigo.esei.dai.hybridserver.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Properties;



public class ServiceThread implements Runnable {
	private Socket socket;

	public ServiceThread(Socket socket, Properties pag) {
		this.socket = socket;
	}

	public ServiceThread(Socket socket, Map<String, String> pages) {
		this.socket = socket;
		// this.md = new MemoryDao(pages);
	}

	public ServiceThread(Socket clientSocket) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		try (Socket socket = this.socket) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			try {
				HTTPRequest request = new HTTPRequest(reader);
				HTTPResponse response = new HTTPResponse();
				response.setVersion(request.getHttpVersion());
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
