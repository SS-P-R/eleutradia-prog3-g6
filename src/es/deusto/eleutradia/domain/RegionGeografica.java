package es.deusto.eleutradia.domain;

public enum RegionGeografica {
	AMERICA_NORTE("América del Norte"),
    AMERICA_SUR("América del Sur"),
    EUROPA_OCCIDENTAL("Europa Occidental"),
    EUROPA_ORIENTAL("Europa Oriental"),
    ASIA_PACIFICO("Asia-Pacífico"),
    MEDIO_ORIENTE("Medio Oriente"),
    AFRICA("África"),
	MUNDO("Mundo"); // Para productos que cotizan globalmente

    private final String nombre;

    private RegionGeografica(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
