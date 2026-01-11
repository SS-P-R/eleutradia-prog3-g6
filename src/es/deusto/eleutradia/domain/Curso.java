package es.deusto.eleutradia.domain;

import java.util.ArrayList;
import java.util.List;

public class Curso {
	private final int id;
	private String nombre;
	private List<Modulo> modulos;
	private NivelConocimiento nivelRecomendado;
	private List<Curso> requisitos;
	
	public Curso(int id, String nombre, NivelConocimiento nivelRecomendado) {
		if (id < 0) throw new IllegalArgumentException("El ID no puede ser negativo");
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("El curso debe tener un nombre");
        
		this.id = id;
		this.nombre = nombre;
		this.modulos = new ArrayList<>();
		this.nivelRecomendado = nivelRecomendado;
		this.requisitos = new ArrayList<>();
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
	
	public List<Curso> getRequistos(){
		return List.copyOf(requisitos);
	}
	
	public void addRequisito (Curso curso) {
		if(curso != null && !requisitos.contains(curso)) {
			requisitos.add(curso);
		}
	}
	
	public static Curso parseCSV(String linea) {
		if (linea == null || linea.isBlank()) return null;
		
		String[] campos = linea.split(";");
		
		String nombre = campos[0];
		int nivelIndex = Integer.parseInt(campos[1]);
		NivelConocimiento nivelRecomendado = NivelConocimiento.values()[nivelIndex];
		
		return new Curso(0, nombre, nivelRecomendado);
	}
	
	
	//Métodos recursivos
	public boolean cursoListo(List<Curso> cursosCompletados) {
		//Caso base
		if(requisitos.isEmpty()) {
			return true;
		}
		
		//Caso recursivo
		for (Curso r: requisitos) {
			if(!cursosCompletados.contains(r)) {
				if(!r.cursoListo(cursosCompletados)) {
					return false;
				}
			}
		}
		return true;
	}

	//Encontrar todos los cursos que son requisitos
	public List<Curso> getAllRequisitos(){
		List<Curso> todos = new ArrayList<>();
		getAllRequisitosRecursivo(todos);
		return todos;
	}
	
	private void getAllRequisitosRecursivo(List<Curso> todos) {
		for (Curso r : requisitos) {
			if(!todos.contains(r)) {
				todos.add(r);
				getAllRequisitosRecursivo(todos);
			}
		}
	}
	
	//Generar planes de estudio para los usuarios
	public List<Curso> planDeEstudio(){
		List<Curso> plan = new ArrayList<>();
		planDeEstudioRecursivo(plan);
		plan.add(this);
		return plan;
	}
	
	private void planDeEstudioRecursivo(List<Curso> plan) {
		for(Curso r : requisitos) {
			if(!plan.contains(r)) {
				r.planDeEstudioRecursivo(plan);
				plan.add(r);
			}
		}
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
