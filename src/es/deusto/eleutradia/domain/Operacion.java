package es.deusto.eleutradia.domain;

import java.time.LocalDate;

public class Operacion {
	private ProductoFinanciero prodFinanciero;
	private double cantidad;
	private LocalDate fechaOp;
	private boolean tipoOp; // true = Compra, false = Venta
	
	public Operacion() {
		this.cantidad = 0.0;
		this.prodFinanciero = null;
		this.fechaOp = LocalDate.now();
		this.tipoOp = true;
	}

	public Operacion(ProductoFinanciero prodFinanciero, double cantidad, LocalDate fechaOp, boolean tipoOp) {
		this.cantidad = cantidad;
		this.prodFinanciero = prodFinanciero;
		this.fechaOp = fechaOp;
		this.tipoOp = tipoOp;
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

	@Override
	public String toString() {
		return "Operacion [prodFinanciero=" + prodFinanciero + ", cantidad=" + cantidad
				+ ", fechaOp=" + fechaOp + ", tipoOp=" + (tipoOp ? "COMPRA" : "VENTA") + "]";
	}
}
