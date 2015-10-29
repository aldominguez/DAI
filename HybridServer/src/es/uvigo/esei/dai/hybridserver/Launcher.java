package es.uvigo.esei.dai.hybridserver;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//import java.net.Socket;
//import java.util.Properties;
//
//import es.uvigo.esei.dai.hybridserver.http.ServiceThread;

public class Launcher {

	public static void main(String[] args) throws IOException {
		
		Properties props = new Properties();
		InputStream inputStream =new FileInputStream("config.conf");
		props.load(inputStream);
		inputStream.close();

		HybridServer server = new HybridServer(props);
		server.start();
		
		System.out.println("Funcionando...");

	}
}
