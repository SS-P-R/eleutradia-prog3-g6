package es.deusto.eleutradia.domain;

public enum PeriodicidadPago {
	SIN_PAGO(0),
	DIARIA(1),
    SEMANAL(7),
    QUINCENAL(15),
    MENSUAL(30),
    TRIMESTRAL(90),
    SEMESTRAL(180),
    ANUAL(365);

    private final int dias;

    private PeriodicidadPago(int dias) {
        this.dias = dias;
    }

    public int getDias() {
        return dias;
    }
}
