package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import es.deusto.eleutradia.domain.Particular;
import es.deusto.eleutradia.domain.Usuario;

public class PanelPerfil extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	
	private JButton botonCerrarSesion;
	private JButton botonPrivacidad;
	private JButton botonEvaluacion;
	private JButton botonApariencia;
	private JButton botonInfoET;
	private JButton botonEditarDomicilio;
	private JButton botonEditarTelefono;
	private JButton botonEditarPassword;
	
	// Estilos
    private static final Color MY_AZUL = new Color(0, 100, 255);   // Azul
    private static final Color MY_GRIS = new Color(100, 100, 100); // Gris
    private static final Color MY_ROJO = new Color(220, 53, 69);   // Rojo
	private static final Font FONT_TITULO = new Font("Segoe UI", Font.BOLD, 20);
    private static final Font FONT_SUBTITULO = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONT_NORMAL = new Font("Segoe UI", Font.BOLD, 12);
    private static final Font FONT_CAMPO = new Font("Segoe UI", Font.PLAIN, 14);
	
	public PanelPerfil(Usuario usuario) {
		this.usuario = usuario;
		
		this.setLayout(new BorderLayout(10, 10));
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel panelSesion = construirPanelSesion();
		JPanel panelPerfil = construirPanelPerfil();
		JPanel panelOpciones = construirPanelOpciones();
		
		this.add(panelSesion, BorderLayout.NORTH);
		this.add(panelPerfil, BorderLayout.CENTER);
		this.add(panelOpciones, BorderLayout.EAST);
	}
	
	private JPanel construirPanelSesion() {
		JPanel mainPanel = new JPanel(new FlowLayout());
		
		JLabel labelTitulo = new JLabel("Mi perfil", JLabel.CENTER);
		
		
		botonCerrarSesion = new JButton("Cerrar sesión");
        botonCerrarSesion.setBackground(MY_ROJO);
        botonCerrarSesion.setForeground(Color.WHITE);
        botonCerrarSesion.setFocusPainted(false);
        botonCerrarSesion.setFont(FONT_SUBTITULO);
		
        return mainPanel;
	}

	private JPanel construirPanelPerfil() {
		JPanel mainPanel = new JPanel();
		
		return mainPanel;
	}
	
	private JPanel construirPanelOpciones() {
		JPanel mainPanel = new JPanel();
		
		return mainPanel;
	}

}
