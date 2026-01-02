package es.deusto.eleutradia.domain;

import java.util.ArrayList;
import java.util.List;

public class Curso {
	private final int id;
	private String nombre;
	private List<Modulo> modulos;
	private NivelConocimiento nivelRecomendado;
	
	public Curso(int id, String nombre, NivelConocimiento nivelRecomendado) {
		if (id < 0) throw new IllegalArgumentException("El ID no puede ser negativo");
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("El curso debe tener un nombre");
        
		this.id = id;
		this.nombre = nombre;
		this.modulos = new ArrayList<>();
		this.nivelRecomendado = nivelRecomendado;
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
		return String.format("/images/cursos/curso%d.png", id);
	}
	
	public static Curso parseCSV(String linea) {
		if (linea == null || linea.isBlank()) return null;
		
		String[] campos = linea.split(";");
		
		String nombre = campos[0];
		int nivelIndex = Integer.parseInt(campos[1]);
		NivelConocimiento nivelRecomendado = NivelConocimiento.values()[nivelIndex];
		
		return new Curso(0, nombre, nivelRecomendado);
	}

	@Override
	public String toString() {
		return "Curso [id=" + id + ", nombre=" + nombre + ", modulos=" + modulos
				+ ", nivelRecomendado=" + nivelRecomendado + "]";
	}
	
	//IAG (Claude)
	//SIN MODIFICAR
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Curso curso = (Curso) obj;
	    return id == curso.id; // Comparar por ID
	}

	@Override
	public int hashCode() {
	    return Integer.hashCode(id);
	}
	//END IAG
	
}
