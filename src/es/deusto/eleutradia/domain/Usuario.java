package es.deusto.eleutradia.domain;

import java.util.ArrayList;

public abstract class Usuario {
    private String email;
    private String telefono;
    private String direccion;
    private Pais domicilioFiscal;
    
    // Relaciones
    private PerfilFinanciero perfilFinanciero;
    private ArrayList<CarteraVirtual> carterasVirtuales;
    
	public Usuario(String email, String telefono, String direccion, Pais domicilioFiscal) {
		super();
		this.email = email;
		this.telefono = telefono;
		this.direccion = direccion;
		this.domicilioFiscal = domicilioFiscal;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Pais getDomicilioFiscal() {
		return domicilioFiscal;
	}

	public void setDomicilioFiscal(Pais domicilioFiscal) {
		this.domicilioFiscal = domicilioFiscal;
	}

	public PerfilFinanciero getPerfilFinanciero() {
		return perfilFinanciero;
	}

	public void setPerfilFinanciero(PerfilFinanciero perfilFinanciero) {
		this.perfilFinanciero = perfilFinanciero;
	}

	public ArrayList<CarteraVirtual> getCarterasVirtuales() {
		return carterasVirtuales;
	}

	public void setCarterasVirtuales(ArrayList<CarteraVirtual> carterasVirtuales) {
		this.carterasVirtuales = carterasVirtuales;
	}

	@Override
	public String toString() {
		return "Usuario [email=" + email + ", telefono=" + telefono + ", direccion=" + direccion + ", domicilioFiscal="
				+ domicilioFiscal + ", perfilFinanciero=" + perfilFinanciero + ", carterasVirtuales="
				+ carterasVirtuales + "]";
	}
    
}