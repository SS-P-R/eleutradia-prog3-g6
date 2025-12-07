package es.deusto.eleutradia.domain;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class ProductoFinanciero {
	private final int id;
	private String nombre;
	private YearMonth plazo; // En caso de que lo haya (bonos y depósitos)
	private Map<PlazoRentabilidad, BigDecimal> rentabilidades;
	private double valorUnitario;
	private TipoProducto tipoProducto;
	private RegionGeografica regionGeografica;
	private PeriodicidadPago perPago;
	private Divisa divisa;
	private Gestora gestora;

	public ProductoFinanciero(int id, String nombre, YearMonth plazo, Map<PlazoRentabilidad, BigDecimal> rentabilidades,
			double valorUnitario, TipoProducto tipoProducto, RegionGeografica regionGeografica,
			PeriodicidadPago perPago, Divisa divisa, Gestora gestora) {
		this.id = id;
		this.nombre = nombre;
		this.plazo = plazo;
		//IAG (ChatGPT)
		//SIN MODIFICAR
		this.rentabilidades = Collections.unmodifiableMap(new EnumMap<>(rentabilidades));
		//END IAG
		this.valorUnitario = valorUnitario;
		this.tipoProducto = tipoProducto;
		this.regionGeografica = regionGeografica;
		this.perPago = perPago;
		this.divisa = divisa;
		this.gestora = gestora;
	}
	
	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public YearMonth getPlazo() {
		return plazo;
	}

	public void setPlazo(YearMonth plazo) {
		this.plazo = plazo;
	}

	public Map<PlazoRentabilidad, BigDecimal> getRentabilidades() {
		return rentabilidades;
	}

	public void setRentabilidades(Map<PlazoRentabilidad, BigDecimal> rentabilidades) {
	    this.rentabilidades = Collections.unmodifiableMap(new EnumMap<>(rentabilidades));
	}

	public double getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public TipoProducto getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(TipoProducto tipoProducto) {
		this.tipoProducto = tipoProducto;
	}

	public RegionGeografica getRegionGeografica() {
		return regionGeografica;
	}

	public void setRegionGeografica(RegionGeografica regionGeografica) {
		this.regionGeografica = regionGeografica;
	}

	public PeriodicidadPago getPerPago() {
		return perPago;
	}

	public void setPerPago(PeriodicidadPago perPago) {
		this.perPago = perPago;
	}

	public Divisa getDivisa() {
		return divisa;
	}

	public void setDivisa(Divisa divisa) {
		this.divisa = divisa;
	}
	
	public Gestora getGestora() {
		return gestora;
	}

	public void setGestora(Gestora gestora) {
		this.gestora = gestora;
	}

	@Override
	public String toString() {
		return "ProductoFinanciero [ID=" + id + ", nombre=" + nombre + ", plazo=" + plazo
				+ ", rentabilidades=" + rentabilidades + ", valorUnitario=" + valorUnitario
				+ ", tipoProducto=" + tipoProducto + ", regionGeografica=" + regionGeografica
				+ ", perPago=" + perPago + ", divisa=" + divisa + ", gestora=" + gestora + "]";
	}
	
}
