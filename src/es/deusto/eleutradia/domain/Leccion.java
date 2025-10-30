package es.deusto.eleutradia.domain;

public class Leccion implements Comparable<Leccion> {
	private final String codigo;
	private String titulo;
	private int posicion;

	public Leccion(int codigo, String titulo, int posicion) {
		if (codigo < 0) throw new IllegalArgumentException("El código no puede ser negativo");
        if (titulo == null || titulo.isBlank()) throw new IllegalArgumentException("La lección debe tener un título");
		this.codigo = "L" + codigo;
		this.titulo = titulo;
		this.posicion = posicion;
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

	public String getCodigo() {
		return codigo;
	}

	@Override
	public int compareTo(Leccion o) {
		return Integer.compare(this.posicion, o.posicion);
	}
	
	@Override
	public String toString() {
		return "Leccion [codigo=" + codigo + ", titulo=" + titulo + ", posicion=" + posicion + "]";
	}
	
}
