package es.deusto.eleutradia.domain;

import java.time.LocalDate;

public class Particular extends Usuario {
	private final String dni;
    private String nombre;
    private LocalDate fechaNacimiento;
    private Pais paisResidencia;
    
	public Particular(String dni, String nombre, LocalDate fechaNacimiento, Pais paisResidencia,
					  String email, String telefono, String direccion, Pais domicilioFiscal) {
		super(email, telefono, direccion, domicilioFiscal);
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
