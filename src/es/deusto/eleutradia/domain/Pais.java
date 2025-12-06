package es.deusto.eleutradia.domain;

public class Pais {
	private String nombre;
	private RegionGeografica region;
	
	public Pais() {
		this.nombre = "";
		this.region = null;
	}

	public Pais(String nombre, RegionGeografica region) {
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
	
	public static Pais parseCSV(String linea) {
	    if (linea == null || linea.isBlank()) return null;

	    String[] campos = linea.split(";");

	    String nombre = campos[0];
	    int regionNum = Integer.parseInt(campos[1]);

	    RegionGeografica region = RegionGeografica.values()[regionNum - 1];

	    return new Pais(nombre, region);
	}

	@Override
	public String toString() {
		return "Pais [nombre=" + nombre + ", region=" + region + "]";
	}
	
}
