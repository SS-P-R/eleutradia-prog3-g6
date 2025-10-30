package es.deusto.eleutradia.domain;

import java.util.ArrayList;
import java.util.List;

public class Cartera {
    private Usuario propietario;
    private double saldo;
    private PerfilRiesgo perfilRiesgo;
    private Divisa divisa;
    private List<Operacion> operaciones;
    
	public Cartera() {
		this.propietario = null;
		this.saldo = 0.0;
		this.perfilRiesgo = PerfilRiesgo.CONSERVADOR;
		this.divisa = Divisa.USD;
		this.operaciones = new ArrayList<Operacion>();
	}

	public Cartera(Usuario propietario, double saldo, PerfilRiesgo perfilRiesgo, Divisa divisa, List<Operacion> operaciones) {
		this.propietario = propietario;
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

	@Override
	public String toString() {
		String propietarioId;

	    if (propietario instanceof Particular) {
	        propietarioId = ((Particular) propietario).getDni();
	    } else if (propietario instanceof Empresa) {
	        propietarioId = ((Empresa) propietario).getIdEmpresa();
	    } else {
	        propietarioId = "Desconocido";
	    }
	    
	    return "Cartera [propietario=" + propietarioId
	            + ", saldo=" + saldo
	            + ", perfilRiesgo=" + perfilRiesgo
	            + ", divisa=" + divisa
	            + ", operaciones=" + operaciones.size() + "]";
	}
    
}