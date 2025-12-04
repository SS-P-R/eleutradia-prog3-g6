package es.deusto.eleutradia.domain;

import java.util.ArrayList;
import java.util.List;

public class Curso {
	private final int id;
	private String nombre;
	private List<Modulo> modulos;
	private NivelConocimiento nivelRecomendado;
	private String rutaImagen;
	
	public Curso(int id, String nombre, List<Modulo> modulos, NivelConocimiento nivelRecomendado, String rutaImagen) {
		if (id < 0) throw new IllegalArgumentException("El ID no puede ser negativo");
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("El curso debe tener un nombre");
        
		this.id = id;
		this.nombre = nombre;
		if (modulos != null) {
			this.modulos = new ArrayList<>(modulos);
		} else {
			this.modulos = new ArrayList<>();
		}
		this.nivelRecomendado = nivelRecomendado;
		this.rutaImagen = rutaImagen;
	}
	
	public int getId() {
        return id;
    }

    public String getCodigo() {
        return String.format("C%03d", id);
    }

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Modulo> getModulos() {
		return modulos;
	}

	public void setModulos(List<Modulo> modulos) {
		this.modulos = modulos;
	}
	
	public NivelConocimiento getNivelRecomendado() {
		return nivelRecomendado;
	}

	public void setNivelRecomendado(NivelConocimiento nivelRecomendado) {
		this.nivelRecomendado = nivelRecomendado;
	}

	public String getRutaImagen() {
		return rutaImagen;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}

	@Override
	public String toString() {
		return "Curso [ID=" + id + ", nombre=" + nombre + ", modulos=" + modulos + "]";
	}
	
}
