package es.deusto.eleutradia.domain;

import java.time.LocalDate;

public class Operacion {
	private final int id;
	private ProductoFinanciero prodFinanciero;
	private double cantidad;
	private LocalDate fechaOp;
	private boolean tipoOp; // true = compra, false = venta

	public Operacion(ProductoFinanciero prodFinanciero, double cantidad, LocalDate fechaOp, boolean tipoOp) {
		this.id = 0;
		this.cantidad = cantidad;
		this.prodFinanciero = prodFinanciero;
		this.fechaOp = fechaOp;
		this.tipoOp = tipoOp;
	}
	
	public Operacion(int id, ProductoFinanciero prodFinanciero, double cantidad, LocalDate fechaOp, boolean tipoOp) {
        if (id < 0) throw new IllegalArgumentException("El ID no puede ser negativo");
        this.id = id;
        this.prodFinanciero = prodFinanciero;
        this.cantidad = cantidad;
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
		return "Operacion [ID=" + id + ", prodFinanciero=" + prodFinanciero + ", cantidad=" + cantidad
				+ ", fechaOp=" + fechaOp + ", tipoOp=" + (tipoOp ? "COMPRA" : "VENTA") + "]";
	}
}
