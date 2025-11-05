package es.deusto.eleutradia.domain;

import java.util.ArrayList;
import java.util.List;

public class Gestora {
	private String nombreComercial;
	private String nombreCompleto;
	private String direccion;
	private Pais sede;
	private List<ProductoFinanciero> ofertaProductos;
	
	public Gestora() {
		this.nombreComercial = "";
		this.nombreCompleto = "";
		this.direccion = "";
		this.sede = null;
		this.ofertaProductos = new ArrayList<ProductoFinanciero>();
	}

	public Gestora(String nombreComercial, String nombre, String direccion, Pais sede, List<ProductoFinanciero> ofertaProductos) {
		this.nombreComercial = nombreComercial;
		this.nombreCompleto = nombre;
		this.direccion = direccion;
		this.sede = sede;
		this.ofertaProductos = ofertaProductos;
	}

	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
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
		return "Gestora [nombre=" + nombreCompleto + ", direccion=" + direccion + ", sede=" + sede + ", ofertaProductos="
				+ ofertaProductos + "]";
	}
	
}
