package es.deusto.eleutradia.domain;

public class Leccion implements Comparable<Leccion> {
	private final int id;
	private String titulo;
	private int posicion;

	public Leccion(int id, String titulo, int posicion) {
		if (id < 0) throw new IllegalArgumentException("El ID no puede ser negativo");
        if (titulo == null || titulo.isBlank()) throw new IllegalArgumentException("La lección debe tener un título");
		this.id = id;
		this.titulo = titulo;
		this.posicion = posicion;
	}

	public int getId() {
		return id;
	}

	public String getCodigo() {
		return String.format("L%03d", id);
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	@Override
	public int compareTo(Leccion o) {
		return Integer.compare(this.posicion, o.posicion);
	}
	
	@Override
	public String toString() {
		return "Leccion [ID=" + id + ", titulo=" + titulo + ", posicion=" + posicion + "]";
	}
	
}
