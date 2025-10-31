package es.deusto.eleutradia.domain;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

public class Particular extends Usuario {
	private final String dni;
    private String nombre;
    private LocalDate fechaNacimiento;
    private Pais paisResidencia;
    private List<Curso> cursos;
    
	public Particular() {
		this.dni = "";
		this.nombre = "";
		this.fechaNacimiento = null;
		this.paisResidencia = null;
		this.cursos = new ArrayList<Curso>();
	}

	public Particular(String dni, String nombre, LocalDate fechaNacimiento, Pais paisResidencia, List<Curso> cursos) {
		this.dni = dni;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.paisResidencia = paisResidencia;
		if (cursos != null) {
			this.cursos = new ArrayList<>(cursos);
		} else {
			this.cursos = new ArrayList<>();
		}
	}

	public Particular(String dni, String nombre, LocalDate fechaNacimiento, Pais paisResidencia,
			String email, String password, String telefono, String direccion, Pais domicilioFiscal,
			PerfilFinanciero perfilFinanciero, ArrayList<Cartera> carteras, ArrayList<Curso> cursos) {
		super(email, password, telefono, direccion, domicilioFiscal, perfilFinanciero, carteras);
		this.dni = dni;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.paisResidencia = paisResidencia;
		if (cursos != null) {
			this.cursos = new ArrayList<>(cursos);
		} else {
			this.cursos = new ArrayList<>();
		}
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

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
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
				+ ", paisResidencia=" + paisResidencia + ", cursos=" + cursos + "]";
	}
    
}
