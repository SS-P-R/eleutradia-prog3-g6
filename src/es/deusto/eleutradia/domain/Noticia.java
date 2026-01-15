package es.deusto.eleutradia.domain;

public class Noticia {
    String titular;
    String contenido;

    public Noticia(String titular, String contenido) {
        this.titular = titular;
        this.contenido = contenido;
    }

	public String getTitular() {
		return titular;
	}

	public String getContenido() {
		return contenido;
	}

}
