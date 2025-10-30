package es.deusto.eleutradia.domain;

import java.time.LocalDate;
import java.util.ArrayList;

public class Particular extends Usuario {
	private final String dni;
    private String nombre;
    private LocalDate fechaNacimiento;
    private Pais paisResidencia;
    
	public Particular() {
		this.dni = "";
		this.nombre = "";
		this.fechaNacimiento = null;
		this.paisResidencia = null;
	}

	public Particular(String dni, String nombre, LocalDate fechaNacimiento, Pais paisResidencia) {
		this.dni = dni;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.paisResidencia = paisResidencia;
	}

	public Particular(String dni, String nombre, LocalDate fechaNacimiento, Pais paisResidencia,
			String email, String telefono, String direccion, Pais domicilioFiscal,
			PerfilFinanciero perfilFinanciero, ArrayList<Cartera> carteras) {
		super(email, telefono, direccion, domicilioFiscal, perfilFinanciero, carteras);
		this.dni = dni;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.paisResidencia = paisResidencia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Pais getPaisResidencia() {
		return paisResidencia;
	}

	public void setPaisResidencia(Pais paisResidencia) {
		this.paisResidencia = paisResidencia;
	}

	public String getDni() {
		return dni;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	@Override
	public String toString() {
		return "Particular [dni=" + dni + ", nombre=" + nombre + ", fechaNacimiento=" + fechaNacimiento
				+ ", paisResidencia=" + paisResidencia + "]";
	}
    
}
