package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import es.deusto.eleutradia.domain.Usuario;

public class VentanaPrincipal extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	        
    private CardLayout layout;
    private JPanel contenedor;
    
    private JButton botonInicio, botonExplorar, botonPortfolio, botonAprender, botonPerfil;
    
    // Imágenes
    private String inicioNegro = "/imagenes/casaNegro.png";
    private String explorarNegro = "/imagenes/busquedaNegro.png";
    private String portfolioNegro = "/imagenes/portfolioNegro.png";
    private String aprenderNegro = "/imagenes/aprendizajeNegro.png";        
    private String perfilNegro = "/imagenes/perfilNegro.png";
    private String inicioAzul = "/imagenes/casaAzul.png";
    private String explorarAzul = "/imagenes/busquedaAzul.png";
    private String portfolioAzul = "/imagenes/portfolioAzul.png";
    private String aprenderAzul = "/imagenes/aprendizajeAzul.png";        
    private String perfilAzul = "/imagenes/perfilAzul.png";
	
	public VentanaPrincipal(Usuario usuario) {
		super("EleuTradia: Inicio");
		this.usuario = usuario;
		this.configurarVentana();
		this.inicializarPaneles();
		this.construirPanelNavegacion();
		this.setVisible(true);
	}
	
	private void configurarVentana() {
		this.setLayout(new BorderLayout());
		this.setSize(800,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setMinimumSize(new Dimension(800, 600));
		
		layout = new CardLayout();
		contenedor = new JPanel(layout);
		this.add(contenedor);
	}
	
	private void inicializarPaneles() {		
		// Ejemplo de las 5 ventanas
        PanelInicio panelInicio = new PanelInicio(usuario, this);
        PanelExplorar panelExplorar = new PanelExplorar();
        JPanel panelPortfolio = new JPanel();
        panelPortfolio.add(new JLabel("Aquí irá el Módulo de Portfolio"));
        JPanel panelAprender = new JPanel();
        panelAprender.add(new JLabel("Aquí irá el Módulo de Aprender"));
        JPanel panelPerfil = new PanelPerfil();
        
        contenedor.add(panelInicio, "Inicio");
        contenedor.add(panelExplorar, "Explorador");
        contenedor.add(panelPortfolio, "Portfolio");
        contenedor.add(panelAprender,"Aprendizaje");
        contenedor.add(panelPerfil,"Perfil");
        
        layout.show(contenedor, "Inicio");
	}
	
	private void construirPanelNavegacion() {
		JPanel panelNavegacion = new JPanel();
		panelNavegacion.setLayout(new BoxLayout(panelNavegacion, BoxLayout.Y_AXIS));
		panelNavegacion.setBackground(Color.WHITE);
		panelNavegacion.add(Box.createVerticalGlue());
		
		agregarBotonNavegacion(panelNavegacion, "Inicio", "Inicio",
	            inicioNegro, inicioAzul);
		
	    agregarBotonNavegacion(panelNavegacion, "Explorador", "Explorar",
	            explorarNegro, explorarAzul);
		
	    agregarBotonNavegacion(panelNavegacion, "Portfolio", "Portfolio",
	            portfolioNegro, portfolioAzul);

	    agregarBotonNavegacion(panelNavegacion, "Aprendizaje", "Aprender",
	            aprenderNegro, aprenderAzul);

	    agregarBotonNavegacion(panelNavegacion, "Perfil", "Perfil",
	            perfilNegro, perfilAzul);
		
		panelNavegacion.add(Box.createVerticalGlue());
		this.add(panelNavegacion, BorderLayout.WEST);
		
		botonInicio.setIcon(cargarIcono("/imagenes/casaAzul.png"));
		botonInicio.setText("");
	}

	private ImageIcon cargarIcono(String ruta) {
        try {
            return new ImageIcon(getClass().getResource(ruta));
        } catch (Exception e) {
            System.err.println("Error al cargar icono: " + ruta);
            return null; // El botón se mostrará sin icono si falla
        }
	}

	private JButton crearBotonNavegacion(String texto, ImageIcon icono) {
        JButton boton = new JButton(texto);
        if (icono != null) boton.setIcon(icono);
        
        boton.setForeground(Color.GRAY);
        boton.setVerticalTextPosition(JButton.BOTTOM); 
        boton.setHorizontalTextPosition(JButton.CENTER);
        boton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setFocusPainted(false);
        boton.setOpaque(false);
        
        return boton;
    }
	
	private void agregarBotonNavegacion(JPanel panel, String nombrePanel,
			String texto, String iconoNormal, String iconoActivo) {
		
	    ImageIcon icono = cargarIcono(iconoNormal);
	    JButton boton = crearBotonNavegacion(texto, icono);
	    panel.add(boton);
	    panel.add(Box.createVerticalStrut(30));

	    boton.addActionListener(e -> {
	        layout.show(contenedor, nombrePanel);
	        resetBotones();
	        boton.setIcon(cargarIcono(iconoActivo));
	        boton.setText("");
	    });

	    // IA (ChatGPT)
	    // SIN MODIFICAR
	    // Guarda la referencia para resetBotones()
	    switch (nombrePanel) {
	        case "Inicio" -> botonInicio = boton;
	        case "Explorador" -> botonExplorar = boton;
	        case "Portfolio" -> botonPortfolio = boton;
	        case "Aprendizaje" -> botonAprender = boton;
	        case "Perfil" -> botonPerfil = boton;
	    }
	}

	private void resetBotones() {
		botonInicio.setIcon(cargarIcono("/imagenes/casaNegro.png"));
		botonInicio.setText("Inicio");

		botonExplorar.setIcon(cargarIcono("/imagenes/busquedaNegro.png"));
		botonExplorar.setText("Explorar");

	    botonPortfolio.setIcon(cargarIcono("/imagenes/portfolioNegro.png"));
		botonPortfolio.setText("Portfolio");

		botonAprender.setIcon(cargarIcono("/imagenes/aprendizajeNegro.png"));
		botonAprender.setText("Aprender");

		botonPerfil.setIcon(cargarIcono("/imagenes/perfilNegro.png"));
		botonPerfil.setText("Perfil");

    }
	
	public void mostrarPanel(String nombre) {
		layout.show(contenedor, nombre);
		resetBotones();
		
		switch (nombre) {
		case "Inicio":
			botonInicio.setIcon(cargarIcono(inicioAzul));
			botonInicio.setText("Inicio");
			break;
		case "Explorar":
			botonExplorar.setIcon(cargarIcono(explorarAzul));
			botonExplorar.setText("Explorar");
			break;
		case "Portfolio":
			botonPortfolio.setIcon(cargarIcono(portfolioAzul));
			botonPortfolio.setText("Portfolio");
			break;
		case "Aprendizaje":
			botonAprender.setIcon(cargarIcono(aprenderAzul));
			botonAprender.setText("Aprendizaje");
			break;

		case "Perfil":
			botonPerfil.setIcon(cargarIcono(perfilAzul));
			botonPerfil.setText("Perfil");
			break;
			
		default:
			throw new IllegalArgumentException("No existe la pestaña: " + nombre);
		}
	}

	private void resetBoton(JButton boton, String iconoRuta, String texto) {
	    boton.setIcon(cargarIcono(iconoRuta));
	    boton.setText(texto);
	}
}