package es.uvigo.esei.dai.hybridserver.http;

/*
 * ResourceChain -> con barra
 * 
 * */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

public class HTTPRequest {
	private HTTPRequestMethod metodo;
	private String version;
	private String resourceName;
	private String resourceChain;
	private String[] resourcePath;
	private Map<String, String> headerParameters;
	private Map<String, String> resourceParameters;

	public HTTPRequest(Reader reader) throws IOException, HTTPParseException {

		resourceParameters = new HashMap<String, String>();
		headerParameters = new HashMap<String, String>();

		BufferedReader br = new BufferedReader(reader);
		try {
			String linea;
			while ((linea = br.readLine()) != null) {

				// System.out.println(linea);
				String[] space = linea.split(" "); // Inicializar y divido
													// petición
													// por espacios.

				if (space[0].equals("GET")) {
					metodo = HTTPRequestMethod.GET;
				} else if (space[0].equals("POST")) {
					metodo = HTTPRequestMethod.POST;
				} else if (space[0].equals("PUT")) {
					metodo = HTTPRequestMethod.PUT;
				} else if (space[0].equals("DELETE")) {
					metodo = HTTPRequestMethod.DELETE;
				} else if (space[0].equals("TRACE")) {
					metodo = HTTPRequestMethod.TRACE;
				} else if (space[0].equals("HEAD")) {
					metodo = HTTPRequestMethod.HEAD;
				} else if (space[0].equals("CONNECT")) {
					metodo = HTTPRequestMethod.CONNECT;
				} else if (space[0].equals("OPTIONS")) {
					metodo = HTTPRequestMethod.OPTIONS;
				}

				version = space[2];

				if (space[1].contains("?")) {
					String[] name = space[1].split("\\?"); // dividimos por
															// interrogante
					resourceChain = space[1]; // /hello/hola.txt?province=ourense
					resourceName = name[0].substring(1); // hello/hola.txt
					// aux2[1] split por & e tes o getResourceParameters
					// almacendos.
					// facer split por = e xa o tes

					resourcePath = resourceName.split("/");
					String[] ampers = name[1].split("&");

					String[] aux;
					for (int i = 0; i < ampers.length; i++) {
						aux = ampers[i].split("[=]+");
						resourceParameters.put(aux[0], aux[1]);
					}

				} else {

					resourceChain = space[1];
					String name = space[1].substring(1);
					resourceName = name;

					resourcePath = name.split("/");

				}

				if(linea.contains("Host")){
					
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public HTTPRequestMethod getMethod() {
		return metodo;
	}

	public String getResourceChain() {
		return resourceChain;
	}

	public String[] getResourcePath() {
		return resourcePath;
	}

	public String getResourceName() {
		// TODO Auto-generated method stub
		return resourceName;
	}

	public Map<String, String> getResourceParameters() {
		// TODO Auto-generated method stub
		return resourceParameters;
	}

	public String getHttpVersion() {
		return version;
	}

	public Map<String, String> getHeaderParameters() {
		// TODO Auto-generated method stub
		return headerParameters;
	}

	public String getContent() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getContentLength() {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(this.getMethod().name()).append(' ').append(this.getResourceChain())
				.append(' ').append(this.getHttpVersion()).append("\r\n");

		for (Map.Entry<String, String> param : this.getHeaderParameters().entrySet()) {
			sb.append(param.getKey()).append(": ").append(param.getValue()).append("\r\n");
		}

		if (this.getContentLength() > 0) {
			sb.append("\r\n").append(this.getContent());
		}

		return sb.toString();
	}

	public static void main(String[] args) throws IOException {
		Map<String, String> parametro = new HashMap<String, String>();

		String pruebax = "GET /hello/world.html?country=Spain&province=Ourense&city=Ourense HTTP/1.1\r\n"
				+ "jidweijdweowedoiedwiewd";

		Reader r = new StringReader(pruebax);
		BufferedReader br = new BufferedReader(r);

		if (pruebax.contains("?")) {

			String[] prueba2;
			String[] pruebaz;
			pruebaz = pruebax.split(" ");
			prueba2 = pruebaz[1].split("\\?");
			// resourceChain = prueba2[0];
			// String resourceName2 = prueba2[0];
			// aux2[1] split por & e tes o getResourceParameters almacendos.
			// facer split por = e xa o tes

			// String ver[];
			// ver = resourceName2.split("/");

			// System.out.println(ver[1]);

			String[] ampers = prueba2[1].split("&");

			String[] aux;
			for (int i = 0; i < ampers.length; i++) {
				aux = ampers[i].split("[=]+");
				parametro.put(aux[0], aux[1]);
			}
		}

		Iterator it = parametro.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			System.out.println(e.getKey() + " " + e.getValue());
		}
	}
}
