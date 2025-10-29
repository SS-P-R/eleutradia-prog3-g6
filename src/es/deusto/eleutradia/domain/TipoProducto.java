package es.deusto.eleutradia.domain;

public enum TipoProducto {
	
	// --- Activos de RF ---
	BONO(ClaseActivo.RENTA_FIJA),
	LETRA_TESORO(ClaseActivo.RENTA_FIJA),
	DEPOSITO(ClaseActivo.RENTA_FIJA),
	ETF_RF(ClaseActivo.RENTA_FIJA),
	
	// --- Activos de RV ---
	ACCION(ClaseActivo.RENTA_VARIABLE),
	FONDO_INVERSION(ClaseActivo.RENTA_VARIABLE),
	PLAN_PENSIONES(ClaseActivo.RENTA_VARIABLE),
	ETF_RV(ClaseActivo.RENTA_VARIABLE),
	
	// --- Activos alternativos ---
	CRIPTOMONEDA(ClaseActivo.ALTERNATIVO),
	COMMODITY(ClaseActivo.ALTERNATIVO),
	PRIVATE_EQUITY(ClaseActivo.ALTERNATIVO),
	VENTURE_CAPITAL(ClaseActivo.ALTERNATIVO),
	CROWDFUND_INM(ClaseActivo.ALTERNATIVO);
	
	private final ClaseActivo claseActivo;
	
	private TipoProducto(ClaseActivo claseActivo) {
		this.claseActivo = claseActivo;
	}
}
