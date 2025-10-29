package es.deusto.eleutradia.domain;

public class RegionGeografica {
	private final int codigo;
	private String nombre;
	
	public RegionGeografica(int codigo, String nombre) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getCodigo() {
		return codigo;
	}
	
	@Override
	public String toString() {
		return "RegionGeografica [codigo=" + codigo + ", nombre=" + nombre + "]";
	}	
	
}
