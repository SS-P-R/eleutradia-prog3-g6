package es.deusto.eleutradia.domain;

public class Pais {
	private final int codigo;
	private String nombre;
	private RegionGeografica region;
	
	public Pais(int codigo, String nombre, RegionGeografica region) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.region = region;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public RegionGeografica getRegion() {
		return region;
	}

	public void setRegion(RegionGeografica region) {
		this.region = region;
	}

	public int getCodigo() {
		return codigo;
	}

	@Override
	public String toString() {
		return "Pais [codigo=" + codigo + ", nombre=" + nombre + "]";
	}
	
}
