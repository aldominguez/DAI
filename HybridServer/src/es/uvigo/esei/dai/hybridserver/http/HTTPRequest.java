package es.uvigo.esei.dai.hybridserver.http;

import static es.uvigo.esei.dai.hybridserver.http.HTTPHeaders.CONTENT_LENGTH;
import static es.uvigo.esei.dai.hybridserver.http.HTTPHeaders.CONTENT_TYPE;
import static org.junit.Assert.assertEquals;

/*
 * ResourceChain -> con barra
 * 
 * */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URLDecoder;
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
	private String content;
	private int contentLength;

	public HTTPRequest(Reader reader) throws IOException, HTTPParseException {

		resourceParameters = new HashMap<String, String>();
		headerParameters = new HashMap<String, String>();
		contentLength = 0;
		

		BufferedReader br = new BufferedReader(reader);
		try {
			String linea;

			linea = br.readLine();
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
				String[] aux;
				String[] ampers;
				if (name[1].contains("&")) {
					ampers = name[1].split("&");

					for (int i = 0; i < ampers.length; i++) {
						aux = ampers[i].split("[=]+");
						resourceParameters.put(aux[0], aux[1]);
					}
				} else {
					aux = name[1].split("[=]+");
					resourceParameters.put(aux[0], aux[1]);

				}

			} else {

				resourceChain = space[1];
				String name = space[1].substring(1);
				resourceName = name;

				resourcePath = name.split("/");

			}

			while ((linea = br.readLine()) != null && !(linea.isEmpty())) {
				

				if (linea.contains("Content-Length:")) {
					space = linea.split(": ");
					contentLength = Integer.parseInt(space[1]);
					headerParameters.put(space[0], space[1]);
				} else {
					space = linea.split(":");
					headerParameters.put(space[0], space[1].replace(" ", ""));
				}

				if (contentLength != 0) {
					char[] buff = new char[contentLength];
					br.read(buff);
					String c = new String(buff);
					content = c;
				}

				String type = headerParameters.get("Content-Type");

				if (type != null && type.startsWith("application/x-www-form-urlencoded")) {
					content = URLDecoder.decode(content, "UTF-8");
				}

				String aux3 = content.replaceAll("&", ", ");

				String[] auxCont = aux3.split(", ");
				for (int i = 0; i < auxCont.length; i++) {
					String[] auxBucl = auxCont[i].split("[=]+");
					resourceParameters.put(auxBucl[0], auxBucl[1]);
				}

			}

		} catch (Exception e) {
			System.err.println("Error parsing.");
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
		return content;
	}

	public int getContentLength() {
		// TODO Auto-generated method stub
		return contentLength;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(this.getMethod().name()).append(' ').append(this.getResourceChain())
				.append(' ').append(this.getHttpVersion()).append("\r\n");

		for (Map.Entry<String, String> param : this.getHeaderParameters().entrySet()) {
			sb.append(param.getKey()).append(": ").append(param.getValue()).append("\r\n");
		}

		if (this.getContentLength() > 0) {
			sb.append("\n").append(this.getContent());
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