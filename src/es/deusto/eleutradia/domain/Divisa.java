package es.deusto.eleutradia.domain;
import java.math.BigDecimal;

public enum Divisa {
	
	// Divisa(Nombre de la moneda, Tipo de cambio relativo al USD)
    EUR("Euro", new BigDecimal("0.86")),
    USD("Dólar estadounidense", BigDecimal.ONE),
    GBP("Libra esterlina", new BigDecimal("0.76")),
    CHF("Franco suizo", new BigDecimal("0.80")),
    JPY("Yen japonés", new BigDecimal("153.77")),
    CAD("Dólar canadiense", new BigDecimal("1.39")),
    MXN("Peso mexicano", new BigDecimal("18.49")),
    AUD("Dólar australiano", new BigDecimal("1.52")),
    SEK("Corona sueca", new BigDecimal("9.40")),
    NOK("Corona noruega", new BigDecimal("10.03")),
    DKK("Corona danesa", new BigDecimal("6.42")),
    HKD("Dólar de Hong Kong", new BigDecimal("7.77")),
    SGD("Dólar de Singapur", new BigDecimal("1.30"));
	
	private final String nombre;
	private final BigDecimal tasaCambioUSD;
	
	Divisa(String nombre, BigDecimal tasaCambioUSD) {
		this.nombre = nombre;
		this.tasaCambioUSD = tasaCambioUSD;
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
