package es.deusto.eleutradia.domain;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class ProductoFinanciero {
	private final int codigo;
	private String nombre;
	private YearMonth plazo; // En caso de que lo haya (bonos)
	private Map<PlazoRentabilidad, BigDecimal> rentabilidades;
	private double valorUnitario;
	private double valorActual;
	private TipoProducto tipoProducto;
	private RegionGeografica regionGeografica;
	private PeriodicidadPago perPago;
	private Divisa divisa;
	
	public ProductoFinanciero() {
		this.codigo = 0;
	    this.nombre = "";
	    this.plazo = null;
	    this.rentabilidades = new EnumMap<>(PlazoRentabilidad.class);
	    this.valorUnitario = 0.0;
	    this.valorActual = 0.0;
	    this.tipoProducto = TipoProducto.ACCION;
	    this.regionGeografica = RegionGeografica.MUNDO;
	    this.perPago = PeriodicidadPago.MENSUAL;
	    this.divisa = Divisa.USD;
	}

	public ProductoFinanciero(int codigo, String nombre, YearMonth plazo, Map<PlazoRentabilidad, BigDecimal> rentabilidades,
			double valorUnitario, double valorActual, TipoProducto tipoProducto,
			RegionGeografica regionGeografica, PeriodicidadPago perPago, Divisa divisa) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.plazo = plazo;
		this.rentabilidades = Collections.unmodifiableMap(new EnumMap<>(rentabilidades));
		this.valorUnitario = valorUnitario;
		this.valorActual = valorActual;
		this.tipoProducto = tipoProducto;
		this.regionGeografica = regionGeografica;
		this.perPago = perPago;
		this.divisa = divisa;
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
		this.rentabilidades = rentabilidades;
	}

	public double getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	
	public double getValorActual() {
		return valorActual;
	}

	public void setValorActual(double valorActual) {
		this.valorActual = valorActual;
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

	public int getCodigo() {
		return codigo;
	}

	@Override
	public String toString() {
		return "ProductoFinanciero [codigo=" + codigo + ", nombre=" + nombre + ", plazo=" + plazo + ", rentabilidades="
			+ rentabilidades + ", valorUnitario=" + valorUnitario + ", valorActual=" + valorActual
			+ ", tipoProducto=" + tipoProducto + ", regionGeografica=" + regionGeografica
			+ ", perPago=" + perPago + ", divisa=" + divisa + "]";
	}
	
}
