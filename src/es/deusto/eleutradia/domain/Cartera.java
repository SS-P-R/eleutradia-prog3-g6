package es.deusto.eleutradia.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cartera {
    private Usuario propietario;
    private String nombre;
    private double saldo;
    private PerfilRiesgo perfilRiesgo;
    private Divisa divisa;
    private List<Operacion> operaciones;
    
	public Cartera() {
		this.propietario = null;
		this.nombre = "";
		this.saldo = 0.0;
		this.perfilRiesgo = PerfilRiesgo.CONSERVADOR;
		this.divisa = Divisa.USD;
		this.operaciones = new ArrayList<Operacion>();
	}

	public Cartera(Usuario propietario, String nombre, double saldo, PerfilRiesgo perfilRiesgo,
			Divisa divisa, List<Operacion> operaciones) {
		this.propietario = propietario;
		this.nombre = nombre;
		this.saldo = saldo;
		this.perfilRiesgo = perfilRiesgo;
		this.divisa = divisa;
		this.operaciones = operaciones;
	}

	public Usuario getPropietario() {
		return propietario;
	}

	public void setPropietario(Usuario propietario) {
		this.propietario = propietario;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		if (saldo < 0) {
            throw new IllegalArgumentException("El saldo no puede ser negativo");
        }
		this.saldo = saldo;
	}

	public PerfilRiesgo getPerfilRiesgo() {
		return perfilRiesgo;
	}

	public void setPerfilRiesgo(PerfilRiesgo perfilRiesgo) {
		this.perfilRiesgo = perfilRiesgo;
	}

	public Divisa getDivisa() {
		return divisa;
	}

	public void setDivisa(Divisa divisa) {
		this.divisa = divisa;
	}

	public List<Operacion> getOperaciones() {
		return operaciones;
	}

	public void setOperaciones(List<Operacion> operaciones) {
		this.operaciones = operaciones;
	}
	
	public double calcularValorInversiones() {
		double total = 0.0;
		for (Operacion operacion : operaciones) {
			total += operacion.getProdFinanciero().getValorUnitario() * operacion.getCantidad();
		}
		return total;
	}
	
	public double calcularPatrimonio() {
	    return saldo + calcularValorInversiones();
	}
	
	public boolean anadirProducto(ProductoFinanciero producto, double cantidad) {
	    if (producto == null || cantidad <= 0) return false;
	    for (Operacion op : operaciones) {
	    	ProductoFinanciero p = op.getProdFinanciero();
	        if (p.getNombre().equalsIgnoreCase(producto.getNombre())) {
	            System.out.println("El producto ya está en la cartera.");
	            return false;
	        }
	    }
		this.operaciones.add(new Operacion(producto, cantidad, LocalDate.now(), true));
		return true;
	}

	@Override
	public String toString() {
		String propietarioId;

	    if (propietario instanceof Particular) {
	        propietarioId = ((Particular) propietario).getDni();
	    } else if (propietario instanceof Empresa) {
	        propietarioId = ((Empresa) propietario).getNif();
	    } else {
	        propietarioId = "Desconocido";
	    }
	    
	    return "Cartera [propietario=" + propietarioId
	    		+ ", nombre=" + nombre
	            + ", saldo=" + saldo
	            + ", perfilRiesgo=" + perfilRiesgo
	            + ", divisa=" + divisa
	            + ", operaciones=" + operaciones.size() + "]";
	}
    
}