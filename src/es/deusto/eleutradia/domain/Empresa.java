package es.deusto.eleutradia.domain;

import java.util.ArrayList;

public class Empresa extends Usuario {
	private final String idEmpresa;
	private String nombre;
	
	public Empresa() {
		this.idEmpresa = "";
		this.nombre = "";
	}

	public Empresa(String idEmpresa, String nombre) {
		this.idEmpresa = idEmpresa;
		this.nombre = nombre;
	}

	public Empresa(String idEmpresa, String nombre, String email, String password, String telefono, String direccion,
				Pais domicilioFiscal, PerfilFinanciero perfilFinanciero, ArrayList<Cartera> carteras) {
		super(email, password, telefono, direccion, domicilioFiscal, perfilFinanciero, carteras);
		this.idEmpresa = idEmpresa;
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIdEmpresa() {
		return idEmpresa;
	}

	@Override
	public String toString() {
		return "Empresa [idEmpresa=" + idEmpresa + ", nombre=" + nombre + "]";
	}
	
}
