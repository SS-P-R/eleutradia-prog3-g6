package es.deusto.eleutradia.domain;

public enum PlazoRentabilidad {
	YTD("a fecha actual"), // Year To Date
	UN_ANO("a un año"),
	TRES_ANOS("a tres años"),
	CINCO_ANOS("a cinco años"),
	MAX("histórica");
	
	private final String definicion;
	
	private PlazoRentabilidad(String definicion) {
		this.definicion = definicion;
	}

	public String getDefinicion() {
		return definicion;
	}
	
}
