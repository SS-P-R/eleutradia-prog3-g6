package es.deusto.eleutradia.domain;

import java.util.ArrayList;
import java.util.List;

public abstract class Usuario {
    private String nombre;
	private String email;
    private String password;
    private String telefono;
    private String direccion;
    private Pais domicilioFiscal;
    private PerfilFinanciero perfilFinanciero;
    private List<Cartera> carteras = new ArrayList<>();

	public Usuario(String nombre, String email, String password, String telefono, String direccion,
			Pais domicilioFiscal, PerfilFinanciero perfilFinanciero) {
		this.nombre= nombre;
		this.email = email;
		this.password = password;
		this.telefono = telefono;
		this.direccion = direccion;
		this.domicilioFiscal = domicilioFiscal;
		this.perfilFinanciero = perfilFinanciero;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public List<Cartera> getCarteras() {
		return carteras;
	}
	
	public void addCartera(Cartera cartera) {
	    if (!carteras.contains(cartera)) {
	        carteras.add(cartera);
	    }
	}

	public void removeCartera(Cartera cartera) {
	    carteras.remove(cartera);
	}

	public double calcularPatrimonioLiquido() {
	    double total = 0.0;
	    for (Cartera cartera : this.getCarteras()) {
	        total += cartera.getSaldo();
	    }
	    return total;
	}
	
	public double calcularPatrimonioInvertido() {
	    double total = 0.0;
	    for (Cartera cartera : this.getCarteras()) {
	        total += cartera.calcularValorInversiones();
	    }
	    return total;
	}
	
	public double calcularPatrimonioTotal() {
	    double total = 0.0;
	    for (Cartera cartera : this.getCarteras()) {
	        total += cartera.calcularPatrimonio();
	    }
	    return total;
	}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", email=" + email + ", password=" + password + ", telefono=" + telefono + ", direccion=" + direccion
				+ ", domicilioFiscal=" + domicilioFiscal + ", perfilFinanciero=" + perfilFinanciero + ", carteras=" + carteras + "]";
	}
    
}
