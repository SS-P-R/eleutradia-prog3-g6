package es.deusto.eleutradia.domain;

public enum RegionGeografica {
	AMERICA_NORTE(1, "América del Norte"),
    AMERICA_SUR(2, "América del Sur"),
    EUROPA_OCCIDENTAL(3, "Europa Occidental"),
    EUROPA_ORIENTAL(4, "Europa Oriental"),
    ASIA_PACIFICO(5, "Asia-Pacífico"),
    MEDIO_ORIENTE(6, "Medio Oriente"),
    AFRICA(7, "África"),
	MUNDO(8, "Mundo"); // Para productos que cotizan globalmente

    private final int id;
    private final String nombre;

    private RegionGeografica(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
