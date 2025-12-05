package es.deusto.eleutradia.domain;

import java.util.ArrayList;

public class Empresa extends Usuario {
	private String nif;
	
	public Empresa() {
		this.nif = "";
	}

	public Empresa(String nif) {
		this.nif = nif;
	}

	public Empresa(String nif, String nombre, String email, String password, String telefono, String direccion,
				Pais domicilioFiscal, PerfilFinanciero perfilFinanciero) {
		super(nombre, email, password, telefono, direccion, domicilioFiscal, perfilFinanciero);
		this.nif = nif;
	}

	
	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	@Override
	public String toString() {
		return "Empresa [NIF=" + nif + ", nombre="+ getNombre() + "]";
	}
	
}
