package es.deusto.eleutradia.domain;

import java.awt.Color;

public enum TipoProducto {
	
	// TipoProducto(Nombre del tipo,
	//				Clase de activo subyacente,
	//				Riesgo en escala 1-7,
	//				importe mínimo en USD para contratarlo)
	
	// --- Activos de RF ---
	DEPOSITO("Depósitos", ClaseActivo.RF, 1, 10000.0),
	BONO("Bonos", ClaseActivo.RF, 2, 1000.0),
	LETRA_TESORO("Letras del tesoro", ClaseActivo.RF, 1, 100.0),
	PP_RF("P. de Pensiones a RF", ClaseActivo.RF, 3, 100.0),
	ETF_RF("ETFs de RF", ClaseActivo.RF, 3, 1000.0),
	
	// --- Activos de RV ---
	ACCION("Acciones", ClaseActivo.RV, 5, 10.0),
	FONDO_INVERSION("Fondos de inversión", ClaseActivo.RV, 4, 1.0),
	PP_RV("P. de Pensiones a RV", ClaseActivo.RV, 4, 100.0),
	ETF_RV("ETFs de RV", ClaseActivo.RV, 4, 10.0),
	
	// --- Activos alternativos ---
	CRIPTOMONEDA("Criptomonedas", ClaseActivo.ALT, 7, 10.0),
	COMMODITY("Materias primas", ClaseActivo.ALT, 6, 100.0),
	PRIVATE_EQUITY("Capital inversión", ClaseActivo.ALT, 6, 10000.0),
	CROWDFUND_INM("Inmuebles", ClaseActivo.ALT, 6, 1000.0);
	
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
	
	//IAG (Claude)
	//SIN MODIFICAR: Devuelve un color para cada nivel de riesgo (1-7)
	public static Color getColorRiesgo(int riesgo) {
        switch (riesgo) {
            case 1: return new Color(0, 160, 60);    // Verde intenso
            case 2: return new Color(80, 190, 80);   // Verde claro
            case 3: return new Color(190, 200, 70);  // Amarillo verdoso
            case 4: return new Color(255, 200, 0);   // Amarillo
            case 5: return new Color(255, 150, 0);   // Naranja
            case 6: return new Color(255, 90, 0);    // Naranja oscuro
            case 7: return new Color(200, 40, 40);   // Rojo
            default: return Color.GRAY;
        }
    }
	//END IAG
	
}
