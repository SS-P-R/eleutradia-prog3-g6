package es.deusto.eleutradia.domain;

public class Posicion {
	private ProductoFinanciero producto;
	private double cantidadTotal;
	private double precioMedioCompra;
	
	public Posicion(ProductoFinanciero producto, double cantidadTotal, double precioMedioCompra) {
		this.producto = producto;
		this.cantidadTotal = cantidadTotal;
		this.precioMedioCompra = precioMedioCompra;
	}
	
	public ProductoFinanciero getProducto() {
		return this.producto;
	}
	
	public double getCantidadTotal() {
		return this.cantidadTotal;
	}
	
	public double getPrecioMedioCompra() {
		return this.precioMedioCompra;
	}
	
	public double getValorTotal() {
		return cantidadTotal * producto.getValorUnitario();
	}
	
	public double getGanancia() {
		return (producto.getValorUnitario() - precioMedioCompra) * cantidadTotal;
	}
	
	public double getPorcentajeGanancia() {
		if(precioMedioCompra == 0) return 0;
		return ((producto.getValorUnitario() - precioMedioCompra)/precioMedioCompra)*100;
	}
	
	@Override
	public String toString() {
	    return "Posicion [producto=" + producto.getNombre() 
	            + ", cantidad=" + cantidadTotal 
	            + ", precioMedio=" + precioMedioCompra 
	            + ", valorTotal=" + getValorTotal() + "]";
	}
}
