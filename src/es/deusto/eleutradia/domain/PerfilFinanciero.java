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
	
	public PerfilFinanciero(Usuario usuario, double patrimonio, double riesgo, int horizonte,
							NivelConocimiento nivel, List<TipoProducto> tiposProducto) {
        if (usuario == null) {
            throw new IllegalArgumentException("Debe asociarse a un usuario");
        }
        this.id = contador++; // incremento automático
        this.usuario = usuario;
        usuario.setPerfilFinanciero(this); // asignación bidireccional
        this.patrimonio = patrimonio;
        this.riesgo = riesgo;
        this.horizonte = horizonte;
        this.nivel = nivel;
        this.tiposProducto = tiposProducto;
    }
}
