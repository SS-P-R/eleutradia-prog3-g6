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
    
    private ImageIcon iconoInicio;
    private ImageIcon iconoExplorar;
    private ImageIcon iconoPortfolio;
    private ImageIcon iconoAprender;
    private ImageIcon iconoPerfil;
    
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
		this.cargarIconos();
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
        PanelInicio panelInicio = new PanelInicio(usuario);
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
		JPanel panelNavegacion = new JPanel(new GridLayout(5, 1));
		panelNavegacion.setLayout(new BoxLayout(panelNavegacion, BoxLayout.Y_AXIS));
		panelNavegacion.setBackground(Color.WHITE);
		panelNavegacion.setAlignmentY(CENTER_ALIGNMENT);
		panelNavegacion.add(Box.createVerticalGlue());
		
		botonInicio = crearBotonNavegacion("", iconoInicio);
		panelNavegacion.add(botonInicio);
		panelNavegacion.add(Box.createVerticalStrut(30));
		botonInicio.addActionListener(e-> {
			layout.show(contenedor, "Inicio");
			resetBotones();
            botonInicio.setIcon(cargarIcono(inicioAzul)); 
            botonInicio.setText("");
		});
		
		botonExplorar = crearBotonNavegacion("Explorar", iconoExplorar);
		panelNavegacion.add(botonExplorar);
		panelNavegacion.add(Box.createVerticalStrut(30));
		botonExplorar.addActionListener(e->{
			layout.show(contenedor, "Explorador");
			resetBotones();
            botonExplorar.setIcon(cargarIcono(explorarAzul)); 
            botonExplorar.setText("");
		});
		
		botonPortfolio = crearBotonNavegacion("Portfolio", iconoPortfolio);
		panelNavegacion.add(botonPortfolio);
		panelNavegacion.add(Box.createVerticalStrut(30));
		botonPortfolio.addActionListener(e->{
			layout.show(contenedor, "Portfolio");
			resetBotones();
            botonPortfolio.setIcon(cargarIcono(portfolioAzul)); 
            botonPortfolio.setText("");
		});
		
		
		botonAprender = crearBotonNavegacion("Aprender", iconoAprender);
		panelNavegacion.add(botonAprender);
		panelNavegacion.add(Box.createVerticalStrut(30));
		botonAprender.addActionListener(e->{
			layout.show(contenedor, "Aprendizaje");
			resetBotones();
            botonAprender.setIcon(cargarIcono(aprenderAzul)); 
            botonAprender.setText("");
		});
		
		botonPerfil = crearBotonNavegacion("Mi perfil", iconoPerfil);
		panelNavegacion.add(botonPerfil);
		panelNavegacion.add(Box.createVerticalStrut(30));
		botonPerfil.addActionListener(e->{
			layout.show(contenedor, "Perfil");
			resetBotones();
            botonPerfil.setIcon(cargarIcono(perfilAzul)); 
            botonPerfil.setText("");
		});
		
		panelNavegacion.add(Box.createVerticalGlue());
		
		this.add(panelNavegacion, BorderLayout.WEST);
		
	}

	private void cargarIconos() {
		iconoInicio = cargarIcono("/imagenes/casaAzul.png");
        iconoExplorar = cargarIcono("/imagenes/busquedaNegro.png"); // Asumiendo nombres
        iconoPortfolio = cargarIcono("/imagenes/portfolioNegro.png");
        iconoAprender = cargarIcono("/imagenes/aprendizajeNegro.png");        
        iconoPerfil = cargarIcono("/imagenes/perfilNegro.png");
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
        
        if (icono != null) {
            boton.setIcon(icono);
        }
        
        boton.setForeground(Color.GRAY);
        boton.setVerticalTextPosition(SwingConstants.BOTTOM); 
        boton.setHorizontalTextPosition(SwingConstants.CENTER);
        
        boton.setVerticalAlignment(SwingConstants.CENTER);
        boton.setHorizontalAlignment(SwingConstants.CENTER);
        
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setFocusPainted(false);
        boton.setOpaque(false);
        
        return boton;
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
}