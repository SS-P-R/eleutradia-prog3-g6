package es.deusto.eleutradia.domain;

import java.util.List;

public class PerfilFinanciero {
	private static int contador = 1;
	private final int id;
	private final Usuario usuario;
	private double patrimonio;
	private double riesgo;
	private int horizonte;
	private NivelConocimiento nivel;
	private List<TipoProducto> tiposProducto;
	
	public PerfilFinanciero(int id, Usuario usuario, double patrimonio, double riesgo, int horizonte,
							NivelConocimiento nivel, List<TipoProducto> tiposProducto) {
        if (usuario == null) {
            throw new IllegalArgumentException("Debe asociarse a un usuario");
        }
        this.id = contador++; // Incremento automático
        this.usuario = usuario;
        usuario.setPerfilFinanciero(this); // Asignación bidireccional
        this.patrimonio = patrimonio;
        this.riesgo = riesgo;
        this.horizonte = horizonte;
        this.nivel = nivel;
        this.tiposProducto = tiposProducto;
    }

	public double getPatrimonio() {
		return patrimonio;
	}

	public void setPatrimonio(double patrimonio) {
		this.patrimonio = patrimonio;
	}

	public double getRiesgo() {
		return riesgo;
	}

	public void setRiesgo(double riesgo) {
		this.riesgo = riesgo;
	}

	public int getHorizonte() {
		return horizonte;
	}

	public void setHorizonte(int horizonte) {
		this.horizonte = horizonte;
	}

	public NivelConocimiento getNivel() {
		return nivel;
	}

	public void setNivel(NivelConocimiento nivel) {
		this.nivel = nivel;
	}

	public List<TipoProducto> getTiposProducto() {
		return tiposProducto;
	}

	public void setTiposProducto(List<TipoProducto> tiposProducto) {
		this.tiposProducto = tiposProducto;
	}

	public int getId() {
		return id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	@Override
	public String toString() {
		return "PerfilFinanciero [id=" + id + ", usuario=" + usuario + ", patrimonio=" + patrimonio + ", riesgo="
				+ riesgo + ", horizonte=" + horizonte + ", nivel=" + nivel + ", tiposProducto=" + tiposProducto + "]";
	}
	
}
