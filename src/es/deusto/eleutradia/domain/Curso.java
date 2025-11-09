package es.deusto.eleutradia.domain;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Curso {
	private final String codigo;
	private String nombre;
	private List<Modulo> modulos;
	private NivelConocimiento nivelRecomendado;
	private String rutaImagen;
	
	public Curso(int codigo, String nombre, List<Modulo> modulos, NivelConocimiento nivelRecomendado, String rutaImagen) {
		if (codigo < 0) throw new IllegalArgumentException("El código no puede ser negativo");
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("El curso debe tener un nombre");
		this.codigo = "C" + codigo;
		this.nombre = nombre;
		if (modulos != null) {
			this.modulos = new ArrayList<>(modulos);
		} else {
			this.modulos = new ArrayList<>();
		}
		this.nivelRecomendado = nivelRecomendado;
		this.rutaImagen = rutaImagen;
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

	public String getCodigo() {
		return codigo;
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

	@Override
	public String toString() {
		return "Curso [codigo=" + codigo + ", nombre=" + nombre + ", modulos=" + modulos + "]";
	}
	
}
