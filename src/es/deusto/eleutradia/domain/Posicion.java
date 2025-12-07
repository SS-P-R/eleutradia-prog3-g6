package es.deusto.eleutradia.domain;

public class Posicion {
	private final int id;
	private ProductoFinanciero prodFinanciero;
	private double cantidadTotal;
	private double precioMedio;
	
	public Posicion(ProductoFinanciero producto, double cantidadTotal, double precioMedio) {
		this.id = 0;
		this.prodFinanciero = producto;
		this.cantidadTotal = cantidadTotal;
		this.precioMedio = precioMedio;
	}
	
	public Posicion(int id, ProductoFinanciero prodFinanciero, double cantidadTotal, double precioMedio) {
		if (id < 0) throw new IllegalArgumentException("El ID no puede ser negativo");
		if (prodFinanciero == null) throw new IllegalArgumentException("Producto Financiero obligatorio");
		if (cantidadTotal < 0) throw new IllegalArgumentException("La cantidad no puede ser negativa");
		if (precioMedio < 0) throw new IllegalArgumentException("El precio medio no puede ser negativo");
		this.id = id;
		this.prodFinanciero = prodFinanciero;
		this.cantidadTotal = cantidadTotal;
		this.precioMedio = precioMedio;
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
	
	public double getValorTotal() {
		return cantidadTotal * prodFinanciero.getValorUnitario();
	}
	
	public double getGanancia() {
		return (prodFinanciero.getValorUnitario() - precioMedio) * cantidadTotal;
	}
	
	public double getPorcentajeGanancia() {
		if(precioMedio == 0) return 0;
		return ((prodFinanciero.getValorUnitario() - precioMedio)/precioMedio)*100;
	}
	
	@Override
	public String toString() {
	    return "Posicion [ID=" + id + ", producto=" + prodFinanciero.getId() 
	            + ", cantidad=" + cantidadTotal 
	            + ", precioMedio=" + precioMedio 
	            + ", valorTotal=" + getValorTotal() + "]";
	}
}
