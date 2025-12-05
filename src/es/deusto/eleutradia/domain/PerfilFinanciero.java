package es.deusto.eleutradia.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PerfilFinanciero {
	private int horizonte; // Tiempo en años previsto de mantención del capital
	private PerfilRiesgo perfilRiesgo;
	private NivelConocimiento nivel;
	private Set<TipoProducto> tiposProducto = new HashSet<>();
	
	public PerfilFinanciero(int horizonte, PerfilRiesgo perfilRiesgo,
							NivelConocimiento nivel, List<TipoProducto> tiposProducto) {
        this.perfilRiesgo = perfilRiesgo;
        this.horizonte = horizonte;
        this.nivel = nivel;
        this.tiposProducto = new HashSet<>(tiposProducto);
    }

	public PerfilFinanciero() {
		this.perfilRiesgo = PerfilRiesgo.CONSERVADOR;
		this.horizonte = 0;
		this.nivel = NivelConocimiento.PRINCIPIANTE;
	}
	
	public int getHorizonte() {
		return horizonte;
	}

	public void setHorizonte(int horizonte) {
		if (horizonte < 0) {
            throw new IllegalArgumentException("El horizonte temporal no puede ser negativo");
        }
		this.horizonte = horizonte;
	}

	public PerfilRiesgo getPerfilRiesgo() {
		return perfilRiesgo;
	}

	public void setPerfilRiesgo(PerfilRiesgo perfilRiesgo) {

		this.perfilRiesgo = perfilRiesgo;
	}

	public NivelConocimiento getNivel() {
		return nivel;
	}

	public void setNivel(NivelConocimiento nivel) {
		this.nivel = nivel;
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
				+ ", nivel=" + nivel + ", tiposProducto=" + tiposProducto + "]";
	}
	
}
