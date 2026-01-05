package es.deusto.eleutradia.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Operacion {
	private final int id;
	private ProductoFinanciero prodFinanciero;
	private double cantidad;
	private double precioUnitario;
	private LocalDate fechaOp;
	private boolean tipoOp; // true = compra, false = venta

	public Operacion(ProductoFinanciero prodFinanciero, double cantidad, double precioUnitario, LocalDate fechaOp, boolean tipoOp) {
		this.id = 0;
		this.prodFinanciero = prodFinanciero;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
		this.fechaOp = fechaOp;
		this.tipoOp = tipoOp;
	}
	
	public Operacion(int id, ProductoFinanciero prodFinanciero, double cantidad, double precioUnitario, LocalDate fechaOp, boolean tipoOp) {
        if (id < 0) throw new IllegalArgumentException("El ID no puede ser negativo");
        this.id = id;
        this.prodFinanciero = prodFinanciero;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.fechaOp = fechaOp;
        this.tipoOp = tipoOp;
    }

	public int getId() {
		return id;
	}

	public ProductoFinanciero getProdFinanciero() {
		return prodFinanciero;
	}

	public void setProdFinanciero(ProductoFinanciero prodFinanciero) {
		this.prodFinanciero = prodFinanciero;
	}
	
	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	
	public double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public LocalDate getFechaOp() {
		return fechaOp;
	}

	public void setFechaOp(LocalDate fechaOp) {
		this.fechaOp = fechaOp;
	}

	public boolean getTipoOp() {
		return tipoOp;
	}

	public void setTipoOp(boolean tipoOp) {
		this.tipoOp = tipoOp;
	}
	
	/**
	 * Calcula el coste/ingreso total de esta operación en la divisa del producto
	 */
	public double getImporteTotal() {
		return cantidad * precioUnitario;
	}

	/**
	 * Calcula el importe total convertido a la divisa especificada
	 */
	public double getImporteTotalEnDivisa(Divisa divisaObjetivo) {
		BigDecimal importeEnDivisaProducto = BigDecimal.valueOf(getImporteTotal());
		BigDecimal importeConvertido = prodFinanciero.getDivisa()
			.convertirA(importeEnDivisaProducto, divisaObjetivo);
		return importeConvertido.doubleValue();
	}

	@Override
	public String toString() {
		return "Operacion [ID=" + id + ", prodFinanciero=" + prodFinanciero.getNombre() 
				+ ", cantidad=" + cantidad + ", precioUnitario=" + precioUnitario
				+ ", fechaOp=" + fechaOp + ", tipoOp=" + (tipoOp ? "COMPRA" : "VENTA") + "]";
	}
}
