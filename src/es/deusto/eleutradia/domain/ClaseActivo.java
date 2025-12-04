package es.deusto.eleutradia.domain;

public enum ClaseActivo {
	RF("Renta Fija"),
	RV("Renta Variable"),
	ALT("Alternativos");
	
	private final String nombre;
	
	private ClaseActivo(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
	
}
