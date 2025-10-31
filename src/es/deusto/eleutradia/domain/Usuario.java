package es.deusto.eleutradia.domain;

import java.util.ArrayList;
import java.util.List;

public abstract class Usuario {
    private String email;
    private String password;
    private String telefono;
    private String direccion;
    private Pais domicilioFiscal;
    private PerfilFinanciero perfilFinanciero;
    private List<Cartera> carteras;
    
	public Usuario() {
		this.email = "";
		this.password = "";
		this.telefono = "";
		this.direccion = "";
		this.domicilioFiscal = new Pais();
		this.perfilFinanciero = new PerfilFinanciero();
		this.carteras = new ArrayList<Cartera>();
	}

	public Usuario(String email, String password, String telefono, String direccion,
			Pais domicilioFiscal, PerfilFinanciero perfilFinanciero, ArrayList<Cartera> carteras) {
		this.email = email;
		this.password = password;
		this.telefono = telefono;
		this.direccion = direccion;
		this.domicilioFiscal = domicilioFiscal;
		this.perfilFinanciero = perfilFinanciero;
		if (carteras != null) {
			this.carteras = new ArrayList<>(carteras);
		} else {
			this.carteras = new ArrayList<>();
		}
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

	public void setCarteras(ArrayList<Cartera> carteras) {
		this.carteras = carteras;
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
		return "Usuario [email=" + email + ", password=" + password + ", telefono=" + telefono + ", direccion=" + direccion
				+ ", domicilioFiscal=" + domicilioFiscal + ", perfilFinanciero=" + perfilFinanciero + ", carteras=" + carteras + "]";
	}
    
}
