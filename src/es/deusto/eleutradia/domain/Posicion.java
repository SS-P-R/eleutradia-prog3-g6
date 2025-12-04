package es.deusto.eleutradia.domain;

public class Posicion {
	private ProductoFinanciero prodFinanciero;
	private double cantidadTotal;
	private double precioMedio;
	
	public Posicion(ProductoFinanciero producto, double cantidadTotal, double precioMedioCompra) {
		this.prodFinanciero = producto;
		this.cantidadTotal = cantidadTotal;
		this.precioMedio = precioMedioCompra;
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
	    return "Posicion [producto=" + prodFinanciero.getNombre() 
	            + ", cantidad=" + cantidadTotal 
	            + ", precioMedio=" + precioMedio 
	            + ", valorTotal=" + getValorTotal() + "]";
	}
}
