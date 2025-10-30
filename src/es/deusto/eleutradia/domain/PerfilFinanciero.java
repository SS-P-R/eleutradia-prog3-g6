package es.deusto.eleutradia.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PerfilFinanciero {
	private static int contador = 1;
	private final int id;
	private double patrimonio;
	private int horizonte;
	private PerfilRiesgo perfilRiesgo;
	private NivelConocimiento nivel;
	private List<TipoProducto> tiposProducto;
	
	public PerfilFinanciero(double patrimonio, PerfilRiesgo perfilRiesgo, int horizonte,
							NivelConocimiento nivel, List<TipoProducto> tiposProducto) {
        this.id = contador++; // Incremento automático
        this.patrimonio = patrimonio;
        this.perfilRiesgo = perfilRiesgo;
        this.horizonte = horizonte;
        this.nivel = nivel;
        this.tiposProducto = tiposProducto;
    }

	public PerfilFinanciero() {
		this.id = contador++;
		this.tiposProducto = new ArrayList<>();
	}

	public double getPatrimonio() {
		return patrimonio;
	}

	public void setPatrimonio(double patrimonio) {
		if (patrimonio < 0) {
            throw new IllegalArgumentException("El patrimonio no puede ser negativo");
        }
		this.patrimonio = patrimonio;
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

	public void setTiposProducto(List<TipoProducto> tiposProducto) {
		this.tiposProducto = tiposProducto;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "PerfilFinanciero [id=" + id + ", patrimonio=" + patrimonio + ", perfilRiesgo=" + perfilRiesgo +
				", horizonte=" + horizonte + ", nivel=" + nivel + ", tiposProducto=" + tiposProducto + "]";
	}
	
}
