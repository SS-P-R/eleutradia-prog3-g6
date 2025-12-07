package es.deusto.eleutradia.domain;

public class Pais {
	private final int id;
	private String nombre;
	private RegionGeografica region;
	
	public Pais(int id, String nombre, RegionGeografica region) {
        if (id < 0) throw new IllegalArgumentException("ID no puede ser negativo");
        this.id = id;
        this.nombre = nombre;
        this.region = region;
    }

	public int getId() {
		return id;
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
	    RegionGeografica region = RegionGeografica.valueOf(campos[1]);

	    return new Pais(-1, nombre, region);
	}

	@Override
	public String toString() {
		return "Pais [ID=" + id + ", nombre=" + nombre + ", region=" + region + "]";
	}
	
}
