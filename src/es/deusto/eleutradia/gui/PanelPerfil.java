package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.util.concurrent.Flow;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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
	
	private JButton botonPrivacidad;
	private JButton botonEvaluacion;
	private JButton botonApariencia;
	private JButton botonInfoET;
	private JButton botonEditarDomicilio;
	private JButton botonEditarTelefono;
	private JButton botonEditarPassword;
	
	// Ruta de icono de configuración
	private static final String ICONO_CONFIGURACION = "/imagenes/configuracion.png";
	
	// Estilos
    private static final Color MY_AZUL = new Color(0, 100, 255);   // Azul
    private static final Color MY_GRIS = new Color(200, 200, 200); // Gris
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
	}
	
	private JPanel construirPanelSesion() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(getBackground());
		
		JLabel labelTitulo = new JLabel("Mi perfil", JLabel.LEFT);
		labelTitulo.setFont(FONT_TITULO);
		labelTitulo.setForeground(Color.BLACK);
		mainPanel.add(labelTitulo, BorderLayout.WEST);
		
		JPanel panelBotones = new JPanel(new FlowLayout(20));
		panelBotones.setBackground(getBackground());
		
		JButton botonEditar = new JButton("Editar perfil");
		botonEditar.setPreferredSize(new Dimension(120, 35));
		botonEditar.setFont(FONT_SUBTITULO);
		botonEditar.setBackground(new Color(40, 167, 69));
		botonEditar.setForeground(Color.WHITE);
		botonEditar.setFocusPainted(false);
		panelBotones.add(botonEditar);
		
		ImageIcon config = new ImageIcon(getClass().getResource(ICONO_CONFIGURACION));
		ImageIcon configEscalada = new ImageIcon(config.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
		JButton botonConfiguracion = new JButton(configEscalada);
		botonConfiguracion.setBackground(MY_GRIS);
		botonConfiguracion.setBorder(BorderFactory.createBevelBorder(1, MY_GRIS, MY_AZUL));
		botonConfiguracion.setFocusPainted(false);
        panelBotones.add(botonConfiguracion);
        
        mainPanel.add(panelBotones, BorderLayout.EAST);
		
        return mainPanel;
	}

	private JPanel construirPanelPerfil() {
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(getBackground());
		
		return mainPanel;
	}
	
	private JPanel construirPanelOpciones() {
		JPanel mainPanel = new JPanel();
		
		JButton botonCerrarSesion = new JButton("Cerrar sesión");
        botonCerrarSesion.setFont(FONT_SUBTITULO);
        botonCerrarSesion.setBackground(MY_ROJO);
        botonCerrarSesion.setForeground(Color.WHITE);
        botonCerrarSesion.setFocusPainted(false);
		
		return mainPanel;
	}

}
