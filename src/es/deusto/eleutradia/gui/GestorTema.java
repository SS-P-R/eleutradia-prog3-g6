package es.deusto.eleutradia.gui;

import java.awt.Color;

import static es.deusto.eleutradia.gui.style.UITema.*;

public class GestorTema {
	private static GestorTema instancia;
	private boolean temaOscuro;
	
	//Tema claro
    public static final Color TEXTO_PRINCIPAL_CLARO = new Color(33, 37, 41);
    public static final Color TEXTO_SECUNDARIO = new Color(108, 117, 125);   
    
    //Tema ocuro
    public static final Color FONDO_OSCURO = new Color(33, 37, 41);
    public static final Color CARD_OSCURO = new Color(52, 58, 64);
    public static final Color BORDE_OSCURO = new Color(73, 80, 87);
    public static final Color TEXTO_PRINCIPAL_OSCURO = new Color(248, 249, 250);
    
    // Colores comunes (sin cambio entre temas)
    public static final Color ACENTO = new Color(0, 123, 255);
    public static final Color EXITO = new Color(40, 167, 69);
    public static final Color PELIGRO = new Color(220, 53, 69);
    public static final Color ADVERTENCIA = new Color(255, 193, 7);
    public static final Color GANANCIA = new Color(0, 153, 76);
    public static final Color PERDIDA = new Color(220, 53, 69);
    
    private GestorTema() {
    	this.temaOscuro=false;
    }
    
    public static GestorTema getInstancia() {
    	if (instancia == null) {
    		instancia = new GestorTema();
    	}
    	return instancia;
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
    
    public Color getColorFondo() {
        return temaOscuro ? FONDO_OSCURO : MAIN_FONDO;
    }
    
    public Color getColorVentana() {
        return temaOscuro ? CARD_OSCURO : Color.WHITE;
    }
    
    public Color getColorBorde() {
        return temaOscuro ? BORDE_OSCURO : MAIN_BORDE;
    }
    
    public Color getColorTextoPrincipal() {
        return temaOscuro ? TEXTO_PRINCIPAL_OSCURO : TEXTO_PRINCIPAL_CLARO;
    }
    
    public Color getColorTextoSecundario() {
        return TEXTO_SECUNDARIO;
    }
    
    public Color getColorAcento() {
        return ACENTO;
    }
    
    public Color getColorExito() {
        return EXITO;
    }
    
    public Color getColorPeligro() {
        return PELIGRO;
    }
    
    public Color getColorGanancia() {
        return GANANCIA;
    }
    
    public Color getColorPerdida() {
        return PERDIDA;
    }
    
}
