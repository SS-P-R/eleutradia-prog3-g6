package es.deusto.eleutradia.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PerfilFinanciero {
	private static int contador = 1;
	private final int id;
	private Usuario usuario;
	private PerfilRiesgo perfilRiesgo;
	private int horizonte; // Tiempo previsto de manteción del capital
	private NivelConocimiento nivel;
	private List<TipoProducto> tiposProducto;
	
	public PerfilFinanciero(Usuario usuario, PerfilRiesgo perfilRiesgo, int horizonte,
							NivelConocimiento nivel, List<TipoProducto> tiposProducto) {
        this.id = contador++; // Incremento automático
        this.usuario = usuario;
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
		this.id = contador++;
		this.usuario = null;
		this.perfilRiesgo = PerfilRiesgo.CONSERVADOR;
		this.horizonte = 0;
		this.nivel = NivelConocimiento.PRINCIPIANTE;
		this.tiposProducto = new ArrayList<>();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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

	public int getId() {
		return id;
	}

	public double calcularPatrimonioLiquido() {
		Usuario usuario = this.getUsuario();
	    if (usuario == null) return 0.0;
	    double total = 0.0;
	    for (Cartera cartera : usuario.getCarteras()) {
	        total += cartera.getSaldo();
	    }
	    return total;
	}
	
	public double calcularPatrimonioInvertido() {
		Usuario usuario = this.getUsuario();
	    if (usuario == null) return 0.0;
	    double total = 0.0;
	    for (Cartera cartera : usuario.getCarteras()) {
	        total += cartera.calcularValorInversiones();
	    }
	    return total;
	}
	
	public double calcularPatrimonioTotal() {
		Usuario usuario = this.getUsuario();
	    if (usuario == null) return 0.0;
	    double total = 0.0;
	    for (Cartera cartera : usuario.getCarteras()) {
	        total += cartera.calcularPatrimonio();
	    }
	    return total;
	}
	
	@Override
	public String toString() {
		return "PerfilFinanciero [id=" + id + ", usuario" + usuario + ", perfilRiesgo=" + perfilRiesgo +
				", horizonte=" + horizonte + ", nivel=" + nivel + ", tiposProducto=" + tiposProducto + "]";
	}
	
}
