package es.uvigo.esei.dai.hybridserver.http;

public class Controller {
	private DAO MemoryDAO;
	private HTTPResponse response;
	
	public Controller (DAO MemoryDAO){
		this.MemoryDAO = MemoryDAO;
		this.response = new HTTPResponse();
	}
	
	public HTTPResponse getDatos (HTTPRequest request) throws Exception {
		String UUID;
		
		if (request.getMethod() == HTTPRequestMethod.GET){ // Vemos si quiere GET, POST, DELETE,...
			UUID = request.getHeaderParameters().get("uuid"); // Guardo el UUID ya que va a estar en la base de datos.
			response.setVersion(request.getHttpVersion()); // Lo mismo para versión
			
			if (UUID != null) {
				String contenido = MemoryDAO.getPage(UUID);
				if (contenido == null) {
					response.setContent("<html><h1>Petición incorrecta. No existe UUID en la base de datos.</h1></html>"); // Usamos el MemoryDAO para recuperar el contenido de la base de datos en función de la UUID y se lo pasamos al HTTPResponse (lo rellenamos para cada Thread)
					response.setStatus(HTTPResponseStatus.S400);
				}else{
					response.setContent(contenido); // Usamos el MemoryDAO para recuperar el contenido de la base de datos en función de la UUID y se lo pasamos al HTTPResponse (lo rellenamos para cada Thread)
					response.setStatus(HTTPResponseStatus.S200);
				}
			}else{
				response.setContent("<html><h1>Petición incorrecta. No existe UUID.</h1></html>"); // Usamos el MemoryDAO para recuperar el contenido de la base de datos en función de la UUID y se lo pasamos al HTTPResponse (lo rellenamos para cada Thread)
				response.setStatus(HTTPResponseStatus.S404);
			}
		}
		return response;
	}
}
