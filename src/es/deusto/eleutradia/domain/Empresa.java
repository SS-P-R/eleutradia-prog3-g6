package es.deusto.eleutradia.domain;

import java.util.ArrayList;

public class Empresa extends Usuario {
	private final String idEmpresa;
	private String nombre;
	private String NIF;
	
	public Empresa() {
		this.idEmpresa = "";
		this.nombre = "";
	}

	public Empresa(String idEmpresa, String nombre, String NIF) {
		this.idEmpresa = idEmpresa;
		this.nombre = nombre;
		this.NIF = NIF;
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
	
	public String getNIF() {
		return NIF;
	}

	public void setCIF(String nif) {
		NIF = nif;
	}

	public String getIdEmpresa() {
		return idEmpresa;
	}

	@Override
	public String toString() {
		return "Empresa [idEmpresa=" + idEmpresa + ", nombre=" + nombre + "]";
	}
	
}
