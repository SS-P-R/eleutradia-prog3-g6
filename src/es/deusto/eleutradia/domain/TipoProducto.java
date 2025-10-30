package es.deusto.eleutradia.domain;

public enum TipoProducto {
	
	// TipoProducto(Clase de activo subyacente,
	//				Riesgo en escala 1-7,
	//				importe mínimo en USD para contratarlo)
	
	// --- Activos de RF ---
	BONO(ClaseActivo.RENTA_FIJA, 2, 1000.0),
	LETRA_TESORO(ClaseActivo.RENTA_FIJA, 1, 100.0),
	DEPOSITO(ClaseActivo.RENTA_FIJA, 1, 10000.0),
	ETF_RF(ClaseActivo.RENTA_FIJA, 3, 1000.0),
	
	// --- Activos de RV ---
	ACCION(ClaseActivo.RENTA_VARIABLE, 5, 10.0),
	FONDO_INVERSION(ClaseActivo.RENTA_VARIABLE, 4, 1.0),
	PLAN_PENSIONES(ClaseActivo.RENTA_VARIABLE, 4, 100.0),
	ETF_RV(ClaseActivo.RENTA_VARIABLE, 4, 10.0),
	
	// --- Activos alternativos ---
	CRIPTOMONEDA(ClaseActivo.ALTERNATIVO, 7, 10.0),
	COMMODITY(ClaseActivo.ALTERNATIVO, 6, 100.0),
	PRIVATE_EQUITY(ClaseActivo.ALTERNATIVO, 6, 10000.0),
	VENTURE_CAPITAL(ClaseActivo.ALTERNATIVO, 7, 10000.0),
	CROWDFUND_INM(ClaseActivo.ALTERNATIVO, 6, 1000.0);
	
	private final ClaseActivo claseActivo;
	private final int riesgo;
	private final double importeMin;
	
	private TipoProducto(ClaseActivo claseActivo, int riesgo, double importeMin) {
		this.claseActivo = claseActivo;
		this.riesgo = riesgo;
		this.importeMin = importeMin;
	}

	public ClaseActivo getClaseActivo() {
		return claseActivo;
	}
	
	public int getRiesgo() {
		return riesgo;
	}
	
	public double getImporteMin() {
		return importeMin;
	}
	
}
