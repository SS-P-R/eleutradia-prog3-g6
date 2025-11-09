package es.deusto.eleutradia.domain;
import java.math.BigDecimal;

public enum Divisa {
	
	// Divisa(Nombre de la moneda, Tipo de cambio relativo al USD)
    EUR("Euro", new BigDecimal("0.86"), "€"),
    USD("Dólar estadounidense", BigDecimal.ONE, "$"),
    GBP("Libra esterlina", new BigDecimal("0.76"), "£"),
    CHF("Franco suizo", new BigDecimal("0.80"), "Fr."),
    JPY("Yen japonés", new BigDecimal("153.77"), "¥"),
    HKD("Dólar de Hong Kong", new BigDecimal("7.77"), "HK$"),
    CNY("Yuan chino", new BigDecimal("7.11"), "¥");
	
	private final String nombre;
	private final BigDecimal tasaCambioUSD;
	private final String simbolo;
	
	Divisa(String nombre, BigDecimal tasaCambioUSD, String simbolo) {
		this.nombre = nombre;
		this.tasaCambioUSD = tasaCambioUSD;
		this.simbolo = simbolo;
	}
	
	public String getSimbolo() {
		return simbolo;
	}
	
	public String getNombre() {
        return nombre;
    }

    public BigDecimal getTasaCambioUSD() {
        return tasaCambioUSD;
    }
    
    // Convierte una cantidad de cierta divisa a USD
    public BigDecimal convertirAUSD(BigDecimal cantidad) {
        return cantidad.divide(tasaCambioUSD);
    }

    // Convierte una cantidad de USD a cierta divisa
    public BigDecimal convertirDesdeUSD(BigDecimal cantidadEnUSD) {
        return cantidadEnUSD.multiply(tasaCambioUSD);
    }

    // Convierte una cantidad de cierta divisa a otra
    public BigDecimal convertirA(BigDecimal cantidad, Divisa divisaObjetivo) {
        if (cantidad == null) {
            throw new IllegalArgumentException("La cantidad no puede ser nula.");
        }
        // Si no se especifica divisaObjetivo, se devuelve la cantidad original
        if (divisaObjetivo == null) {
            return cantidad;
        }
        BigDecimal enUSD = convertirAUSD(cantidad);
        return divisaObjetivo.convertirDesdeUSD(enUSD);
    }
    
}
