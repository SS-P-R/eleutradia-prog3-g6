package es.deusto.eleutradia.domain;

import java.util.ArrayList;
import java.util.List;

public class Cartera {
    private String nombre;
    private double saldo;
    private PerfilRiesgo perfilRiesgo;
    private Divisa divisa;
    private List<Operacion> operaciones;
    
	public Cartera() {
		this.nombre = "";
		this.saldo = 0.0;
		this.perfilRiesgo = PerfilRiesgo.CONSERVADOR;
		this.divisa = Divisa.USD;
		this.operaciones = new ArrayList<Operacion>();
	}

	public Cartera(String nombre, double saldo, PerfilRiesgo perfilRiesgo, Divisa divisa, List<Operacion> operaciones) {
		this.nombre = nombre;
		this.saldo = saldo;
		this.perfilRiesgo = perfilRiesgo;
		this.divisa = divisa;
		this.operaciones = operaciones;
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

	@Override
	public String toString() {
		return "Cartera [nombre=" + nombre + ", saldo=" + saldo + ", perfilRiesgo=" + perfilRiesgo + ", divisa="
				+ divisa + ", operaciones=" + operaciones + "]";
	}
    
}