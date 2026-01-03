package es.deusto.eleutradia.gui;

import java.awt.Color;

import static es.deusto.eleutradia.gui.style.UITema.*;

public class GestorTema {
	private static GestorTema gestorTema;
	private boolean temaOscuro;
            
    private GestorTema() {
    	this.temaOscuro = false;
    }
    
    public static GestorTema getInstancia() {
    	if (gestorTema == null) {
    		gestorTema = new GestorTema();
    	}
    	return gestorTema;
    }
    
    public boolean getTema() {
    	return this.temaOscuro;
    }
    
    public void cambiarTema() {
    	this.temaOscuro = !this.temaOscuro;
    }
    
    public void hacerTemaOscuro() {
    	this.temaOscuro = true;
    }
    
    public Color getColorTexto() {
        return temaOscuro ? MAIN_FONDO : MAIN_FONDO_OSCURO;
    }
    
    public Color getColorFondo() {
        return temaOscuro ? MAIN_FONDO_OSCURO : MAIN_FONDO;
    }
    
    public Color getColorPanel() {
        return temaOscuro ? MAIN_PANEL_OSCURO : MAIN_PANEL;
    }
    
    public Color getColorBorde() {
        return temaOscuro ? MAIN_BORDE_OSCURO : MAIN_BORDE;
    }
    
}
