package es.deusto.eleutradia.domain;

public enum TipoProducto {
	
	// TipoProducto(Nombre del tipo,
	//				Clase de activo subyacente,
	//				Riesgo en escala 1-7,
	//				importe mínimo en USD para contratarlo)
	
	// --- Activos de RF ---
	DEPOSITO("Depósitos", ClaseActivo.RENTA_FIJA, 1, 10000.0),
	BONO("Bonos", ClaseActivo.RENTA_FIJA, 2, 1000.0),
	LETRA_TESORO("Letras del tesoro", ClaseActivo.RENTA_FIJA, 1, 100.0),
	PLAN_PENSIONES_RF("Planes de Pensiones: RF", ClaseActivo.RENTA_FIJA, 3, 100.0),
	ETF_RF("ETFs: RF", ClaseActivo.RENTA_FIJA, 3, 1000.0),
	
	// --- Activos de RV ---
	ACCION("Acciones", ClaseActivo.RENTA_VARIABLE, 5, 10.0),
	FONDO_INVERSION("Fondos de inversión", ClaseActivo.RENTA_VARIABLE, 4, 1.0),
	PLAN_PENSIONES_RV("Planes de Pensiones: RV", ClaseActivo.RENTA_VARIABLE, 4, 100.0),
	ETF_RV("ETFs: RV", ClaseActivo.RENTA_VARIABLE, 4, 10.0),
	
	// --- Activos alternativos ---
	CRIPTOMONEDA("Criptomonedas", ClaseActivo.ALTERNATIVO, 7, 10.0),
	COMMODITY("Materias primas", ClaseActivo.ALTERNATIVO, 6, 100.0),
	PRIVATE_EQUITY("Capital inversión", ClaseActivo.ALTERNATIVO, 6, 10000.0),
	CROWDFUND_INM("Inmuebles", ClaseActivo.ALTERNATIVO, 6, 1000.0);
	
	private final String nombre;
	private final ClaseActivo claseActivo;
	private final int riesgo;
	private final double importeMin;
	
	private TipoProducto(String nombre, ClaseActivo claseActivo, int riesgo, double importeMin) {
		this.nombre = nombre;
		this.claseActivo = claseActivo;
		this.riesgo = riesgo;
		this.importeMin = importeMin;
	}

	public String getNombre() {
		return nombre;
	}

	public ClaseActivo getClaseActivo() {
		return claseActivo;
	}
	
	public int getRiesgo() {
		return riesgo;
	}
	
	public String getStringRiesgo() {
		return riesgo + "/7";
	}
	
	public double getImporteMin() {
		return importeMin;
	}
	
}
