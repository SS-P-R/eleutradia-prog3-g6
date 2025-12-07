package es.deusto.eleutradia.domain;

import java.util.ArrayList;
import java.util.List;

public class Curso {
	private final int id;
	private String nombre;
	private List<Modulo> modulos;
	private NivelConocimiento nivelRecomendado;
	private String rutaImagen;
	
	public Curso(String nombre, List<Modulo> modulos, NivelConocimiento nivelRecomendado, String rutaImagen) {
		if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("El curso debe tener un nombre");

        this.id = 0;
        this.nombre = nombre;
        this.modulos = (modulos != null) ? new ArrayList<>(modulos) : new ArrayList<>();
        this.nivelRecomendado = nivelRecomendado;
        this.rutaImagen = rutaImagen;
    }
	
	public Curso(int id, String nombre, List<Modulo> modulos, NivelConocimiento nivelRecomendado, String rutaImagen) {
		if (id < 0) throw new IllegalArgumentException("El ID no puede ser negativo");
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("El curso debe tener un nombre");
        
		this.id = id;
		this.nombre = nombre;
		this.modulos = (modulos != null) ? new ArrayList<>(modulos) : new ArrayList<>();
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
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El curso debe tener un nombre");
        }
        this.nombre = nombre;
    }

	public List<Modulo> getModulos() {
		return List.copyOf(modulos);
	}

	public void addModulo(Modulo modulo) {
        if (modulo != null) {
            modulos.add(modulo);
        }
    }
	
	public void removeModulo(Modulo modulo) {
        modulos.remove(modulo);
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
		return "Curso [id=" + id + ", nombre=" + nombre + ", modulos=" + modulos + ", nivelRecomendado="
				+ nivelRecomendado + ", rutaImagen=" + rutaImagen + "]";
	}
	
}
