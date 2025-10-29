package es.deusto.eleutradia.domain;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.ArrayList;

public class Usuario {
	
	// --- Atributos ---
	private String nombre, DNI, telefono, direccion, email, password;
	private LocalDate fechaNacimiento;
	private NivelConocimiento nivelConocimiento;
	private ArrayList<TipoProducto> listaInteres;
	private PerfilFinanciero perfilFinanciero;
    private ArrayList<CarteraVirtual> carterasVirtuales;
	
	// --- Constructores ---
	public Usuario() {
		super();
	}
	
	public Usuario(int codigo, String nombre, String email) {
		super();
		this.nombre = nombre;
		this.email = email;
	}
	
	// --- Getters y Settters ---
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public NivelConocimiento getNivelConocimiento() {
		return nivelConocimiento;
	}

	public void setNivelConocimiento(NivelConocimiento nivelConocimiento) {
		this.nivelConocimiento = nivelConocimiento;
	}

	public ArrayList<TipoProducto> getListaInteres() {
		return listaInteres;
	}

	public void setListaInteres(ArrayList<TipoProducto> listaInteres) {
		this.listaInteres = listaInteres;
	}	
	

}
