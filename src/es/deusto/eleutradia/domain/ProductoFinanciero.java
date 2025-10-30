package es.deusto.eleutradia.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

public class ProductoFinanciero {
	private final int codigo;
	private String nombre;
	private YearMonth plazo;
	private Map<String, BigDecimal> rentabilidades;
	private BigDecimal valorActual;
	private LocalDate fechaLanzamiento;
	private TipoProducto tipoProducto;
	private RegionGeografica regionGeografica;
	private PeriodicidadPago perPago;
	private Divisa divisa;
	
	public ProductoFinanciero(int codigo, String nombre, YearMonth plazo, Map<String, BigDecimal> rentabilidades,
			BigDecimal valorActual, LocalDate fechaLanzamiento, TipoProducto tipoProducto,
			RegionGeografica regionGeografica, PeriodicidadPago perPago, Divisa divisa) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.plazo = plazo;
		this.rentabilidades = rentabilidades;
		this.valorActual = valorActual;
		this.fechaLanzamiento = fechaLanzamiento;
		this.tipoProducto = tipoProducto;
		this.regionGeografica = regionGeografica;
		this.perPago = perPago;
		this.divisa = divisa;
	}

	
	
	
}
