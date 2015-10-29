package es.uvigo.esei.dai.hybridserver;

<<<<<<< HEAD
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
=======

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
>>>>>>> a5c98cbff981c0f3615307ff7147a2852e96d25a
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import es.uvigo.esei.dai.hybridserver.http.ServiceThread;




public class HybridServer {
	private static final int SERVICE_PORT = 8888;
	private Thread serverThread;
	private boolean stop;
	private int numClientes;
	private Map<String, String> pages;
	private Properties prop;
//	DATOS DE LA BASE DE DATOS
//	db.url=jdbc:mysql://localhost:3306/hstestdb
//	db.user=hsdb
//	db.password=hsdbpass
	

	public HybridServer() {
		
		this(Collections.<String, String> emptyMap()); //Primera Semana
		System.out.println("hola");
	}
	
	public HybridServer(Map<String, String> pages) { //Segunda Semana
		this.pages = pages; 
		this.numClientes = Integer.parseInt(pages.get("numClients"));
		System.out.println(numClientes);
	}

	public HybridServer(Properties properties) {
		this.numClientes = Integer.parseInt(properties.getProperty("numClients"));
		this.prop = properties;
		
	}

	public int getPort() {
		return SERVICE_PORT;
	}

	public Map<String, String> getMap() {
		return this.pages;
	}

	public void start() {
		this.serverThread = new Thread() {
			@Override
			public void run() {
				try (final ServerSocket serverSocket = new ServerSocket(SERVICE_PORT)) { //Crear socket del servidor
					ExecutorService threadPool = Executors.newFixedThreadPool(numClientes); //Crear un pool de hilos
					while (true) {
							Socket socket = serverSocket.accept();
							if (stop) break; // Si stop para hilo.
							if (prop != null) { // Si no para crea el servicio
								threadPool.execute(new ServiceThread(socket, prop)); //Primera forma de crear un servicio (externa: pasando socket y propiedades)
							} else {
								threadPool.execute(new ServiceThread(socket, pages)); //Segunda forma de crear un servicio (memoria: pasando socket y mapa de paginas)
							}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};

		this.stop = false;
		this.serverThread.start();
	}

	public void stop() {
		this.stop = true;
		
		try (Socket socket = new Socket("localhost", SERVICE_PORT)) {
			// Esta conexión se hace, simplemente, para "despertar" el hilo servidor
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		try {
			this.serverThread.join();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		
		this.serverThread = null;
	}

}
