package es.deusto.eleutradia.gui.style;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UITema {
	
	private static final Color MAIN_FONDO = new Color(250, 250, 250);
    private static final Color MAIN_BORDER = new Color(220, 220, 230);
    private static final Color GRIS_SCROLLBAR = new Color(180, 180, 180);
    
    private static final Color AZUL_CLARO = new Color(0, 120, 255);
    private static final Color AZUL_OSCURO = new Color(10, 60, 170);
    private static final Color GRIS_CLARO = new Color(120, 120, 120);
    private static final Color GRIS_OSCURO = new Color(70, 70, 70);
    private static final Color VERDE_CLARO = new Color(40, 170, 70);
    private static final Color VERDE_OSCURO = new Color(25, 120, 50);
    private static final Color ROSA_CLARO = new Color(220, 90, 130);
    private static final Color ROSA_OSCURO = new Color(180, 50, 100);
    private static final Color NARANJA_CLARO = new Color(255, 140, 0);
    
    private static final Font TITULO_GRANDE = new Font("Segoe UI", Font.BOLD, 20);
    private static final Font TITULO_MEDIO = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font SUBTITULO_GRANDE = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font SUBTITULO_MEDIO = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font CUERPO_GRANDE = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font CUERPO_MEDIO = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font CUERPO_PEQUENO = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font CUERPO_CURSIVA = new Font("Segoe UI", Font.ITALIC, 14);

    MouseAdapter myAdapterAzul = new MouseAdapter() {
    	@Override
		public void mouseEntered(MouseEvent e) {e.getComponent().setBackground(AZUL_OSCURO);}
		@Override
		public void mouseExited(MouseEvent e) {e.getComponent().setBackground(AZUL_CLARO);}
		@Override
		public void mousePressed(MouseEvent e) {e.getComponent().setBackground(AZUL_OSCURO);}
		@Override
		public void mouseReleased(MouseEvent e) {e.getComponent().setBackground(AZUL_CLARO);}
    };
    
    MouseAdapter myAdapterGris = new MouseAdapter() {
    	@Override
		public void mouseEntered(MouseEvent e) {e.getComponent().setBackground(GRIS_OSCURO);}
		@Override
		public void mouseExited(MouseEvent e) {e.getComponent().setBackground(GRIS_CLARO);}
		@Override
		public void mousePressed(MouseEvent e) {e.getComponent().setBackground(GRIS_OSCURO);}
		@Override
		public void mouseReleased(MouseEvent e) {e.getComponent().setBackground(GRIS_CLARO);}
    };
    
    MouseAdapter myAdapterVerde = new MouseAdapter() {
    	@Override
		public void mouseEntered(MouseEvent e) {e.getComponent().setBackground(VERDE_OSCURO);}
		@Override
		public void mouseExited(MouseEvent e) {e.getComponent().setBackground(VERDE_CLARO);}
		@Override
		public void mousePressed(MouseEvent e) {e.getComponent().setBackground(VERDE_OSCURO);}
		@Override
		public void mouseReleased(MouseEvent e) {e.getComponent().setBackground(VERDE_CLARO);}
    };
    
    MouseAdapter myAdapterRosa = new MouseAdapter() {
    	@Override
		public void mouseEntered(MouseEvent e) {e.getComponent().setBackground(ROSA_OSCURO);}
		@Override
		public void mouseExited(MouseEvent e) {e.getComponent().setBackground(ROSA_CLARO);}
		@Override
		public void mousePressed(MouseEvent e) {e.getComponent().setBackground(ROSA_OSCURO);}
		@Override
		public void mouseReleased(MouseEvent e) {e.getComponent().setBackground(ROSA_CLARO);}
    };
}
