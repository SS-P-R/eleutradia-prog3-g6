package es.deusto.eleutradia.domain;

import java.math.BigDecimal;

public class Posicion {
	private final int id;
	private ProductoFinanciero prodFinanciero;
	private double cantidadTotal;
	private double precioMedio;
	private Divisa divisaReferencia;
	
	public Posicion(ProductoFinanciero producto, double cantidadTotal, double precioMedio, Divisa divisaReferencia) {
		this.id = 0;
		this.prodFinanciero = producto;
		this.cantidadTotal = cantidadTotal;
		this.precioMedio = precioMedio;
		this.divisaReferencia = divisaReferencia;
	}
	
	public Posicion(int id, ProductoFinanciero prodFinanciero, double cantidadTotal, double precioMedio, Divisa divisaReferencia) {
		if (id < 0) throw new IllegalArgumentException("El ID no puede ser negativo");
		if (prodFinanciero == null) throw new IllegalArgumentException("Producto Financiero obligatorio");
		if (cantidadTotal < 0) throw new IllegalArgumentException("La cantidad no puede ser negativa");
		if (precioMedio < 0) throw new IllegalArgumentException("El precio medio no puede ser negativo");
		this.id = id;
		this.prodFinanciero = prodFinanciero;
		this.cantidadTotal = cantidadTotal;
		this.precioMedio = precioMedio;
		this.divisaReferencia = divisaReferencia;
	}
	
	public int getId() {
		return id;
	}

	public ProductoFinanciero getProducto() {
		return this.prodFinanciero;
	}
	
	public double getCantidadTotal() {
		return this.cantidadTotal;
	}
	
	public double getPrecioMedioCompra() {
		return this.precioMedio;
	}
	
	public Divisa getDivisaReferencia() {
		return this.divisaReferencia;
	}
	
	/**
	 * Calcula el precio unitario actual del producto en la divisa de referencia
	 */
	public double getPrecioActualEnDivisaReferencia() {
		BigDecimal precioEnDivisaProducto = BigDecimal.valueOf(prodFinanciero.getValorUnitario());
		BigDecimal precioConvertido = prodFinanciero.getDivisa()
			.convertirA(precioEnDivisaProducto, divisaReferencia);
		return precioConvertido.doubleValue();
	}
	
	/**
	 * Calcula el valor total de la posición en la divisa de referencia
	 */
	public double getValorTotal() {
		return cantidadTotal * getPrecioActualEnDivisaReferencia();
	}
	
	/**
	 * Calcula el valor total en cierta divisa
	 */
	public double getValorTotalEnDivisa(Divisa divisaObjetivo) {
		// Primero obtenemos el valor en la divisa del producto
		double valorEnDivisaProducto = cantidadTotal * prodFinanciero.getValorUnitario();
		
		// Luego lo convertimos a la divisa objetivo
		BigDecimal valorBD = BigDecimal.valueOf(valorEnDivisaProducto);
		BigDecimal valorConvertido = prodFinanciero.getDivisa()
			.convertirA(valorBD, divisaObjetivo);
		
		return valorConvertido.doubleValue();
	}
	
	/**
	 * Calcula la ganancia en la divisa de referencia
	 */
	public double getGanancia() {
		double precioActual = getPrecioActualEnDivisaReferencia();
		return (precioActual - precioMedio) * cantidadTotal;
	}
	
	/**
	 * Calcula el porcentaje de ganancia
	 */
	public double getPorcentajeGanancia() {
		if (precioMedio == 0) return 0;
		double precioActual = getPrecioActualEnDivisaReferencia();
		return ((precioActual - precioMedio) / precioMedio) * 100;
	}
	
	/**
	 * Calcula el coste total de la inversión (cantidad * precio medio)
	 */
	public double getCosteTotal() {
		return cantidadTotal * precioMedio;
	}
	
	@Override
	public String toString() {
		return "Posicion [ID=" + id + ", producto=" + prodFinanciero.getNombre()
				+ ", cantidad=" + cantidadTotal
				+ ", precioMedio=" + precioMedio + " " + divisaReferencia
				+ ", valorTotal=" + getValorTotal() + " " + divisaReferencia + "]";
	}
}
