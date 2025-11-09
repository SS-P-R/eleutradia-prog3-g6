package es.deusto.eleutradia.domain;

public enum ClaseActivo {
	RENTA_FIJA("Renta Fija"),
	RENTA_VARIABLE("Renta Variable"),
	ALTERNATIVO("Alternativos");
	
	private final String nombre;
	
	private ClaseActivo(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
	
}
