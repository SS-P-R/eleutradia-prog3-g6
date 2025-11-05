package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.deusto.eleutradia.domain.Usuario;

public class VentanaPrincipal extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	        
    private CardLayout layout;
    private JPanel contenedor;
    
    private JButton botonInicio, botonExplorar, botonPortfolio, botonAprender, botonPerfil;
    
    // Rutas de los iconos de pestaña
    private static final String ICONO_INICIO_NEGRO = "/imagenes/inicioNegro.png";
    private static final String ICONO_INICIO_AZUL = "/imagenes/inicioAzul.png";
    private static final String ICONO_EXPLORAR_NEGRO = "/imagenes/explorarNegro.png";
    private static final String ICONO_EXPLORAR_AZUL = "/imagenes/explorarAzul.png";
    private static final String ICONO_PORTFOLIO_NEGRO = "/imagenes/portfolioNegro.png";
    private static final String ICONO_PORTFOLIO_AZUL = "/imagenes/portfolioAzul.png";
    private static final String ICONO_APRENDER_NEGRO = "/imagenes/aprenderNegro.png";  
    private static final String ICONO_APRENDER_AZUL = "/imagenes/aprenderAzul.png";    
    private static final String ICONO_PERFIL_NEGRO = "/imagenes/perfilNegro.png";    
    private static final String ICONO_PERFIL_AZUL = "/imagenes/perfilAzul.png";
	
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
	            ICONO_INICIO_NEGRO, ICONO_INICIO_AZUL);
		
	    agregarBotonNavegacion(panelNavegacion, "Explorador", "Explorar",
	            ICONO_EXPLORAR_NEGRO, ICONO_EXPLORAR_AZUL);
		
	    agregarBotonNavegacion(panelNavegacion, "Portfolio", "Portfolio",
	            ICONO_PORTFOLIO_NEGRO, ICONO_PORTFOLIO_AZUL);

	    agregarBotonNavegacion(panelNavegacion, "Aprendizaje", "Aprender",
	            ICONO_APRENDER_NEGRO, ICONO_APRENDER_AZUL);

	    agregarBotonNavegacion(panelNavegacion, "Perfil", "Perfil",
	            ICONO_PERFIL_NEGRO, ICONO_PERFIL_AZUL);
		
		panelNavegacion.add(Box.createVerticalGlue());
		this.add(panelNavegacion, BorderLayout.WEST);
		
		botonInicio.setIcon(cargarIcono(ICONO_INICIO_AZUL));
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
		resetBoton(botonInicio, ICONO_INICIO_NEGRO, "Inicio");

		resetBoton(botonExplorar, ICONO_EXPLORAR_NEGRO, "Explorar");

		resetBoton(botonPortfolio, ICONO_PORTFOLIO_NEGRO, "Portfolio");

		resetBoton(botonAprender, ICONO_APRENDER_NEGRO, "Aprender");

		resetBoton(botonPerfil, ICONO_PERFIL_NEGRO, "Perfil");

    }
	
	private void resetBoton(JButton boton, String iconoRuta, String texto) {
	    boton.setIcon(cargarIcono(iconoRuta));
	    boton.setText(texto);
	}
	
	public void mostrarPanel(String nombre) {
		layout.show(contenedor, nombre);
		resetBotones();
		
		switch (nombre) {
		case "Inicio":
			botonInicio.setIcon(cargarIcono(ICONO_INICIO_AZUL));
			botonInicio.setText("Inicio");
			break;
		case "Explorar":
			botonExplorar.setIcon(cargarIcono(ICONO_EXPLORAR_AZUL));
			botonExplorar.setText("Explorar");
			break;
		case "Portfolio":
			botonPortfolio.setIcon(cargarIcono(ICONO_PORTFOLIO_AZUL));
			botonPortfolio.setText("Portfolio");
			break;
		case "Aprendizaje":
			botonAprender.setIcon(cargarIcono(ICONO_APRENDER_AZUL));
			botonAprender.setText("Aprendizaje");
			break;
		case "Perfil":
			botonPerfil.setIcon(cargarIcono(ICONO_PERFIL_AZUL));
			botonPerfil.setText("Perfil");
			break;
			
		default:
			throw new IllegalArgumentException("No existe la pestaña: " + nombre);
		}
	}
}