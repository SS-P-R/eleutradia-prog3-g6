package es.deusto.eleutradia.domain;

import java.util.ArrayList;
import java.util.List;

public class Gestora {
	private String nombre;
	private String direccion;
	private Pais sede;
	private List<ProductoFinanciero> ofertaProductos;
	
	public Gestora() {
		this.nombre = "";
		this.direccion = "";
		this.sede = null;
		this.ofertaProductos = new ArrayList<ProductoFinanciero>();
	}

	public Gestora(String nombre, String direccion, Pais sede, List<ProductoFinanciero> ofertaProductos) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.sede = sede;
		this.ofertaProductos = ofertaProductos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Pais getSede() {
		return sede;
	}

	public void setSede(Pais sede) {
		this.sede = sede;
	}

	public List<ProductoFinanciero> getOfertaProductos() {
		return ofertaProductos;
	}

	public void setOfertaProductos(List<ProductoFinanciero> ofertaProductos) {
		this.ofertaProductos = ofertaProductos;
	}

	@Override
	public String toString() {
		return "Gestora [nombre=" + nombre + ", direccion=" + direccion + ", sede=" + sede + ", ofertaProductos="
				+ ofertaProductos + "]";
	}
	
}
