package es.deusto.eleutradia.domain;

public class Empresa extends Usuario {
	private String nif;

	public Empresa(String nif) {
		super("", "", "", "", "", null, null);
		if (nif == null || nif.isBlank()) throw new IllegalArgumentException("El NIF no puede estar vacío");
		this.nif = nif;
	}

	public Empresa(String nif, String nombre, String email, String password, String telefono, String direccion,
				Pais domicilioFiscal, PerfilFinanciero perfilFinanciero) {
		super(nombre, email, password, telefono, direccion, domicilioFiscal, perfilFinanciero);
		if (nif == null || nif.isBlank()) throw new IllegalArgumentException("El NIF no puede estar vacío");
		this.nif = nif;
	}

	
	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	@Override
	public String toString() {
		return "Empresa [NIF=" + nif + ", nombre="+ getNombre() + "]";
	}
	
}
