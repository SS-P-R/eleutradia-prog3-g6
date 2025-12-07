package es.deusto.eleutradia.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PerfilFinanciero {
	private final int id;
	private int horizonte; // Tiempo en años previsto de mantención del capital
	private PerfilRiesgo perfilRiesgo;
	private NivelConocimiento nivelConocimiento;
	private Set<TipoProducto> tiposProducto = new HashSet<>();
	
	public PerfilFinanciero(int horizonte, PerfilRiesgo perfilRiesgo,
			NivelConocimiento nivel, List<TipoProducto> tiposProducto) {
		if (horizonte < 0) throw new IllegalArgumentException("El horizonte temporal no puede ser negativo");
		if (perfilRiesgo == null) throw new IllegalArgumentException("Perfil de riesgo obligatorio");
		if (nivel == null) throw new IllegalArgumentException("Nivel de conocimiento obligatorio");
		this.id = 0;
        this.perfilRiesgo = perfilRiesgo;
        this.horizonte = horizonte;
        this.nivelConocimiento = nivel;
        if (tiposProducto != null) {
        	this.tiposProducto = new HashSet<>(tiposProducto);
        }
    }

	public PerfilFinanciero() {
		this.id = 0;
		this.perfilRiesgo = PerfilRiesgo.CONSERVADOR;
		this.horizonte = 0;
		this.nivelConocimiento = NivelConocimiento.PRINCIPIANTE;
	}
	
	public int getId() {
		return id;
	}

	public int getHorizonte() {
		return horizonte;
	}

	public void setHorizonte(int horizonte) {
		if (horizonte < 0) throw new IllegalArgumentException("El horizonte temporal no puede ser negativo");
		this.horizonte = horizonte;
	}

	public PerfilRiesgo getPerfilRiesgo() {
		return perfilRiesgo;
	}

	public void setPerfilRiesgo(PerfilRiesgo perfilRiesgo) {

		this.perfilRiesgo = perfilRiesgo;
	}

	public NivelConocimiento getNivelConocimiento() {
		return nivelConocimiento;
	}

	public void setNivelConocimiento(NivelConocimiento nivel) {
		this.nivelConocimiento = nivel;
	}

	public Set<TipoProducto> getTiposProducto() {
		return tiposProducto;
	}

	public void addTipoProducto(TipoProducto tipo) {
	    tiposProducto.add(tipo);
	}

	public void removeTipoProducto(TipoProducto tipo) {
	    tiposProducto.remove(tipo);
	}
	
	@Override
	public String toString() {
		return "PerfilFinanciero [horizonte=" + horizonte + ", perfilRiesgo=" + perfilRiesgo
				+ ", nivel=" + nivelConocimiento + ", tiposProducto=" + tiposProducto + "]";
	}
	
}
