package es.deusto.eleutradia.domain;

import java.util.ArrayList;
import java.util.List;

public class Modulo implements Comparable<Modulo> {
	private final String codigo;
	private String nombre;
	private int posicion;
	private List<Leccion> lecciones;
	
	public Modulo(int codigo, String nombre, int posicion, List<Leccion> lecciones) {
		if (codigo < 0) throw new IllegalArgumentException("El código no puede ser negativo");
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("El módulo debe tener un nombre");
		this.codigo = "M" + codigo;
		this.nombre = nombre;
		this.posicion = posicion;
		if (lecciones != null) {
			this.lecciones = new ArrayList<>(lecciones);
		} else {
			this.lecciones = new ArrayList<>();
		}
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	public List<Leccion> getLecciones() {
		return lecciones;
	}

	public void setLecciones(List<Leccion> lecciones) {
		this.lecciones = lecciones;
	}

	public String getCodigo() {
		return codigo;
	}
	
	@Override
	public int compareTo(Modulo o) {
		return Integer.compare(this.posicion, o.posicion);
	}

	@Override
	public String toString() {
		return "Modulo [codigo=" + codigo + ", nombre=" + nombre
				+ ", posicion=" + posicion + ", lecciones=" + lecciones + "]";
	}
	
}
