package es.deusto.eleutradia.domain;

import java.util.ArrayList;
import java.util.List;

public class Modulo implements Comparable<Modulo> {
	private final int id;
	private String nombre;
	private int posicion;
	private List<Leccion> lecciones;
	
	public Modulo(String nombre, int posicion, List<Leccion> lecciones) {
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("El módulo debe tener un nombre");
		
        this.id = 0;
		this.nombre = nombre;
		this.posicion = posicion;
		this.lecciones = (lecciones != null) ? new ArrayList<>(lecciones) : new ArrayList<>();
	}
	
	public Modulo(int id, String nombre, int posicion, List<Leccion> lecciones) {
		if (id < 0) throw new IllegalArgumentException("El ID no puede ser negativo");
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("El módulo debe tener un nombre");
		this.id = id;
		this.nombre = nombre;
		this.posicion = posicion;
		this.lecciones = (lecciones != null) ? new ArrayList<>(lecciones) : new ArrayList<>();
	}
	
	public int getId() {
		return id;
	}

	public String getCodigo() {
		return String.format("M%03d", id);
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
		return List.copyOf(lecciones);
	}

	public void addLeccion(Leccion leccion) {
        if (leccion != null) {
            lecciones.add(leccion);
        }
    }

    public void removeLeccion(Leccion leccion) {
        lecciones.remove(leccion);
    }
	
	@Override
	public int compareTo(Modulo o) {
		return Integer.compare(this.posicion, o.posicion);
	}

	@Override
	public String toString() {
		return "Modulo [ID=" + id + ", nombre=" + nombre
				+ ", posicion=" + posicion + ", lecciones=" + lecciones + "]";
	}
	
}
