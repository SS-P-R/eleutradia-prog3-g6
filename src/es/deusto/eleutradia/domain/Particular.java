package es.deusto.eleutradia.domain;

import java.util.List;

import java.time.LocalDate;
import java.util.ArrayList;

public class Particular extends Usuario {
	private String dni;
    private LocalDate fechaNacimiento;
    private Pais paisResidencia;
    private List<Curso> cursos;

	public Particular(String dni, String nombre, LocalDate fechaNacimiento, Pais paisResidencia) {
		super("", "", "", "", "", null, null);
		if (dni == null || dni.isBlank()) throw new IllegalArgumentException("El DNI no puede estar vacío");
		this.dni = dni;
		this.fechaNacimiento = fechaNacimiento;
		this.paisResidencia = paisResidencia;
		this.cursos = new ArrayList<>();
	}

	public Particular(String dni, String nombre, LocalDate fechaNacimiento, Pais paisResidencia,
			String email, String password, String telefono, String direccion, Pais domicilioFiscal,
			PerfilFinanciero perfilFinanciero) {
		super(nombre, email, password, telefono, direccion, domicilioFiscal, perfilFinanciero);
		if (dni == null || dni.isBlank()) throw new IllegalArgumentException("El DNI no puede estar vacío");
		this.dni = dni;
		this.fechaNacimiento = fechaNacimiento;
		this.paisResidencia = paisResidencia;
		this.cursos = new ArrayList<>();
	}

	public Pais getPaisResidencia() {
		return paisResidencia;
	}

	public void setPaisResidencia(Pais paisResidencia) {
		this.paisResidencia = paisResidencia;
	}

	public List<Curso> getCursos() {
		return List.copyOf(cursos);
	}
	
	public void addCurso(Curso curso) {
		cursos.add(curso);
	}
	
	public void removeCurso(Curso curso) {
		cursos.remove(curso);
	}

	public String getDni() {
		return dni;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	@Override
	public String toString() {
		return "Particular [dni=" + dni + ", nombre=" + getNombre() + ", fechaNacimiento=" + fechaNacimiento
				+ ", paisResidencia=" + paisResidencia + ", cursos=" + cursos + "]";
	}
    
}
