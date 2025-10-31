package es.deusto.eleutradia.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PerfilFinanciero {
	private PerfilRiesgo perfilRiesgo;
	private int horizonte; // Tiempo previsto de manteción del capital
	private NivelConocimiento nivel;
	private List<TipoProducto> tiposProducto;
	
	public PerfilFinanciero(PerfilRiesgo perfilRiesgo, int horizonte,
							NivelConocimiento nivel, List<TipoProducto> tiposProducto) {
        this.perfilRiesgo = perfilRiesgo;
        this.horizonte = horizonte;
        this.nivel = nivel;
        if (tiposProducto != null) {
        	this.tiposProducto = new ArrayList<>(tiposProducto);
        } else {
        	this.tiposProducto = new ArrayList<>();
        }
    }

	public PerfilFinanciero() {
		this.perfilRiesgo = PerfilRiesgo.CONSERVADOR;
		this.horizonte = 0;
		this.nivel = NivelConocimiento.PRINCIPIANTE;
		this.tiposProducto = new ArrayList<>();
	}

	public PerfilRiesgo getPerfilRiesgo() {
		return perfilRiesgo;
	}

	public void setPerfilRiesgo(PerfilRiesgo perfilRiesgo) {

		this.perfilRiesgo = perfilRiesgo;
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

	public NivelConocimiento getNivel() {
		return nivel;
	}

	public void setNivel(NivelConocimiento nivel) {
		this.nivel = nivel;
	}

	public List<TipoProducto> getTiposProducto() {
		return Collections.unmodifiableList(tiposProducto);
	}

	public void agregarTipoProducto(TipoProducto tipo) {
	    if (tipo != null && !tiposProducto.contains(tipo)) {
	        tiposProducto.add(tipo);
	    }
	}

	public void eliminarTipoProducto(TipoProducto tipo) {
	    tiposProducto.remove(tipo);
	}
	
	@Override
	public String toString() {
		return "PerfilFinanciero [perfilRiesgo=" + perfilRiesgo + ", horizonte=" + horizonte
				+ ", nivel=" + nivel + ", tiposProducto=" + tiposProducto + "]";
	}
	
}
