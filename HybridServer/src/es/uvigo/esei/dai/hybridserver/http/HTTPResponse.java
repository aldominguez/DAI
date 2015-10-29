package es.uvigo.esei.dai.hybridserver.http;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HTTPResponse {

<<<<<<< HEAD
	private HTTPResponseStatus status;
	private String version;
	private String contenido;
	private Map<String, String> parametros = new LinkedHashMap<String, String>();

	public HTTPResponse() {
	}

	public HTTPResponseStatus getStatus() {
		return status;
	}

	public void setStatus(HTTPResponseStatus status) {
		this.status = status;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getContent() {
		return contenido;
	}

	public void setContent(String content) {
		this.contenido = content;
	}

	public Map<String, String> getParameters() {
		return parametros;
	}

	public String putParameter(String name, String value) {
		parametros.put(name, value);
		return parametros.get(name);
	}

	public boolean containsParameter(String name) {
		return this.parametros.containsKey(name);
	}

	public String removeParameter(String name) {
		return this.parametros.remove(name);
	}

	public void clearParameters() {
		this.parametros.clear();
	}

	public List<String> listParameters() {
		List<String> tr = new ArrayList<String>();

		for (Map.Entry<String, String> elemento : this.parametros.entrySet()) {
			tr.add(elemento.getKey());
		}

		return tr;
	}

	public void print(Writer writer) throws IOException {
		writer.write(this.version + " " + this.status.getCode() + " " + this.status.getStatus() + "\r\n");
		for (Map.Entry<String, String> elemento : this.parametros.entrySet()) {
			writer.write(elemento.getKey() + ": " + elemento.getValue() + "\r\n\r\n");
		}

		if (contenido != null) {
			writer.write("Content-Length: " + contenido.length() + "\r\n");
			writer.write("\r\n");
			writer.write(contenido);
		} else {
			writer.write("\r\n");
		}
		writer.close();
	}

	@Override
	public String toString() {
		final StringWriter writer = new StringWriter();

		try {
			this.print(writer);

		} catch (IOException e) {
		}

		return writer.toString();
	}
=======
    private HTTPResponseStatus status;
    private String version;
    private String contenido;
    private Map<String, String> parametros = new LinkedHashMap<String, String>();

    public HTTPResponse() {
    }

    public HTTPResponseStatus getStatus() {
        // TODO Auto-generated method stub
        return status;
    }

    public void setStatus(HTTPResponseStatus status) {
        this.status = status;
    }

    public String getVersion() {
        // TODO Auto-generated method stub
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getContent() {
        // TODO Auto-generated method stub
        return contenido;
    }

    public void setContent(String content) {
        this.contenido = content;
    }

    public Map<String, String> getParameters() {
        // TODO Auto-generated method stub
        return parametros;
    }

    public String putParameter(String name, String value) {
        parametros.put(name, value);
        return parametros.get(name);
    }

    public boolean containsParameter(String name) {
        // TODO Auto-generated method stub
        return this.parametros.containsKey(name);
    }

    public String removeParameter(String name) {
        // TODO Auto-generated method stub
        return this.parametros.remove(name);
    }

    public void clearParameters() {
        this.parametros.clear();
    }

    public List<String> listParameters() {
        // TODO Auto-generated method stub
        List<String> tr = new ArrayList<String>();

        for (Map.Entry<String, String> elemento : this.parametros.entrySet()) {
            tr.add(elemento.getKey());
        }

        return tr;
    }

    public void print(Writer writer) throws IOException {

        System.out.println(parametros.size());
        writer.write(this.version + " " + this.status.getCode() + " " + this.status.getStatus() + "\r\n");
        for (Map.Entry<String, String> elemento : this.parametros.entrySet()) {
            writer.write(elemento.getKey() + ": " + elemento.getValue() + "\r\n\r\n");
            System.out.println(elemento.getKey());
        }
        
        
        if (contenido != null){
            writer.write("Content-Length: " + contenido.length() + "\r\n");
            writer.write("\r\n");
            writer.write(contenido);
        }else{
            writer.write("\r\n");
        }
        writer.close();
    }

    @Override
    public String toString() {
        final StringWriter writer = new StringWriter();

        try {
            this.print(writer);

        } catch (IOException e) {
        }

        return writer.toString();
    }
>>>>>>> a5c98cbff981c0f3615307ff7147a2852e96d25a
}


