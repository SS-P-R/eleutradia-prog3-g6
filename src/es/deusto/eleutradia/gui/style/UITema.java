package es.deusto.eleutradia.gui.style;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

public class UITema {
	
	public static final Color MAIN_FONDO = new Color(250, 250, 250);
	public static final Color MAIN_BORDE = new Color(220, 220, 230);
	public static final Color GRIS_SCROLLBAR = new Color(180, 180, 180);
    
	public static final Color GRIS_SUAVE = new Color(220, 220, 220);
	public static final Color GRIS_CLARO = new Color(170, 170, 170);
	public static final Color GRIS_MEDIO = new Color(120, 120, 120);
	public static final Color GRIS_OSCURO = new Color(70, 70, 70);
	public static final Color AZUL_CLARO = new Color(0, 120, 255);
	public static final Color AZUL_OSCURO = new Color(10, 60, 170);
	public static final Color VERDE_CLARO = new Color(40, 170, 70);
	public static final Color VERDE_OSCURO = new Color(25, 120, 50);
	public static final Color ROSA_CLARO = new Color(220, 90, 130);
	public static final Color ROSA_OSCURO = new Color(180, 50, 100);
	public static final Color ROJO_CLARO = new Color(220, 50, 50);
	public static final Color NARANJA_CLARO = new Color(255, 140, 0);
    
	public static final Font TITULO_GRANDE = new Font("Segoe UI", Font.BOLD, 20);
	public static final Font TITULO_MEDIO = new Font("Segoe UI", Font.BOLD, 18);
	public static final Font SUBTITULO_GRANDE = new Font("Segoe UI", Font.BOLD, 16);
	public static final Font SUBTITULO_MEDIO = new Font("Segoe UI", Font.BOLD, 14);
	public static final Font SUBTITULO_PEQUENO = new Font("Segoe UI", Font.BOLD, 12);
	public static final Font CUERPO_GRANDE = new Font("Segoe UI", Font.PLAIN, 14);
	public static final Font CUERPO_MEDIO = new Font("Segoe UI", Font.PLAIN, 13);
	public static final Font CUERPO_PEQUENO = new Font("Segoe UI", Font.PLAIN, 12);
	public static final Font CUERPO_CURSIVA = new Font("Segoe UI", Font.ITALIC, 14);

    public static MouseAdapter myAdapterAzul = new MouseAdapter() {
    	@Override
		public void mouseEntered(MouseEvent e) {e.getComponent().setBackground(AZUL_OSCURO);}
		@Override
		public void mouseExited(MouseEvent e) {e.getComponent().setBackground(AZUL_CLARO);}
		@Override
		public void mousePressed(MouseEvent e) {e.getComponent().setBackground(AZUL_OSCURO);}
		@Override
		public void mouseReleased(MouseEvent e) {e.getComponent().setBackground(AZUL_CLARO);}
    };
    
    public static MouseAdapter myAdapterGris = new MouseAdapter() {
    	@Override
		public void mouseEntered(MouseEvent e) {e.getComponent().setBackground(GRIS_OSCURO);}
		@Override
		public void mouseExited(MouseEvent e) {e.getComponent().setBackground(GRIS_MEDIO);}
		@Override
		public void mousePressed(MouseEvent e) {e.getComponent().setBackground(GRIS_OSCURO);}
		@Override
		public void mouseReleased(MouseEvent e) {e.getComponent().setBackground(GRIS_MEDIO);}
    };
    
    public static MouseAdapter myAdapterVerde = new MouseAdapter() {
    	@Override
		public void mouseEntered(MouseEvent e) {e.getComponent().setBackground(VERDE_OSCURO);}
		@Override
		public void mouseExited(MouseEvent e) {e.getComponent().setBackground(VERDE_CLARO);}
		@Override
		public void mousePressed(MouseEvent e) {e.getComponent().setBackground(VERDE_OSCURO);}
		@Override
		public void mouseReleased(MouseEvent e) {e.getComponent().setBackground(VERDE_CLARO);}
    };
    
    public static MouseAdapter myAdapterRosa = new MouseAdapter() {
    	@Override
		public void mouseEntered(MouseEvent e) {e.getComponent().setBackground(ROSA_OSCURO);}
		@Override
		public void mouseExited(MouseEvent e) {e.getComponent().setBackground(ROSA_CLARO);}
		@Override
		public void mousePressed(MouseEvent e) {e.getComponent().setBackground(ROSA_OSCURO);}
		@Override
		public void mouseReleased(MouseEvent e) {e.getComponent().setBackground(ROSA_CLARO);}
    };
    
    public static MouseAdapter myAdapterRegistro = new MouseAdapter() {
    	@Override
		public void mouseEntered(MouseEvent e) {e.getComponent().setForeground(AZUL_OSCURO);}
		@Override
		public void mouseExited(MouseEvent e) {e.getComponent().setForeground(AZUL_CLARO);}
		@Override
		public void mousePressed(MouseEvent e) {e.getComponent().setForeground(AZUL_OSCURO);}
		@Override
		public void mouseReleased(MouseEvent e) {e.getComponent().setForeground(AZUL_CLARO);}
    };
    
	public static ImageIcon cargarIconoEscalado(String ruta, int anchoMax, int altoMax) {
        if (ruta == null || UITema.class.getResource(ruta) == null) return null;
        ImageIcon icono = new ImageIcon(UITema.class.getResource(ruta));
        Image img = icono.getImage();
        int anchoOriginal = img.getWidth(null);
        int altoOriginal = img.getHeight(null);
        double ratio = Math.min((double)anchoMax/anchoOriginal, (double)altoMax/altoOriginal);
        int anchoNuevo = (int)(anchoOriginal * ratio);
        int altoNuevo = (int)(altoOriginal * ratio);
        return new ImageIcon(img.getScaledInstance(anchoNuevo, altoNuevo, Image.SCALE_SMOOTH));
    }
}
