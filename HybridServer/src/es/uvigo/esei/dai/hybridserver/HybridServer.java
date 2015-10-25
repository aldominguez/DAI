package es.uvigo.esei.dai.hybridserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import es.uvigo.esei.dai.hybridserver.http.ServiceThread;



public class HybridServer {
	private int SERVICE_PORT = 8888;
	private Thread serverThread;
	private int numClientes = 50;
	private Map<String, String> pages;


	public HybridServer() {
		this(Collections.<String, String> emptyMap());

	}
	


	public HybridServer(Map<String, String> pages) {
		// Constructor necesario para los tests de la segunda semana
		this.pages = pages;
	}

	public HybridServer(Properties properties) {
		// Constructor necesario para los tests de la tercera semana

	}

	public int getPort() {
		// TODO Auto-generated method stub
		return -1;
	}

	public Map<String, String> getMap() {
		return this.pages;
	}

	public void start() {
		this.serverThread = new Thread() {
			@Override
			public void run() {
				try (ServerSocket serverSocket = new ServerSocket(SERVICE_PORT)) {
					ExecutorService threadPool = Executors.newFixedThreadPool(numClientes);

					while (true) {
						Socket clientSocket = serverSocket.accept();
						threadPool.execute(new ServiceThread(clientSocket));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};

		this.serverThread.start();
	}

	public void stop() {		
	}
	
	
	public static void main(String[] args) throws IOException {
	}
}
