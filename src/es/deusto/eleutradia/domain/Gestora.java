package es.deusto.eleutradia.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Gestora {
	private final int id;
	private String nombreComercial;
	private String nombreCompleto;
	private String direccion;
	private Pais sede;
	private List<ProductoFinanciero> ofertaProductos;
	
	public Gestora(int id, String nombreComercial, String nombre, String direccion, Pais sede, List<ProductoFinanciero> ofertaProductos) {
		this.id = id;
		this.nombreComercial = nombreComercial;
		this.nombreCompleto = nombre;
		this.direccion = direccion;
		this.sede = sede;
		this.ofertaProductos = new ArrayList<>(ofertaProductos);
	}
	
	public int getId() {
        return id;
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
		return Collections.unmodifiableList(ofertaProductos);
	}

	public void setOfertaProductos(List<ProductoFinanciero> ofertaProductos) {
        this.ofertaProductos = new ArrayList<>(ofertaProductos);
    }

	@Override
	public String toString() {
		return "Gestora [ID=" + id + ", nombreComercial=" + nombreComercial + ", nombreCompleto=" + nombreCompleto +
				", direccion=" + direccion + ", sede=" + sede + ", ofertaProductos=" + ofertaProductos + "]";
	}
	
}
