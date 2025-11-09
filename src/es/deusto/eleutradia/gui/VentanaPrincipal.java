package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.deusto.eleutradia.domain.Particular;
import es.deusto.eleutradia.domain.Usuario;

public class VentanaPrincipal extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	        
    private CardLayout layout;
    private JPanel contenedor;
    
    private JButton botonInicio, botonExplorar, botonPortfolio, botonAprender, botonPerfil;
    
    private final String[] nombresPaneles = {"Inicio", "Explorar", "Portfolio", "Aprender", "Perfil"};
    private int indicePanelActual = 0;
    
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
    
    // Estilos
    private static final Color MY_AZUL = new Color(0, 100, 255);   // Azul
	
	public VentanaPrincipal(Usuario usuario) {
		super("EleuTradia: Inicio");
		this.usuario = usuario;
		this.configurarVentana();
		this.inicializarPaneles();
		this.construirPanelNavegacion();
		this.addKeyListener(navegacionKeyListener);
		this.setFocusable(true);
		this.requestFocusInWindow();
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
        PanelExplorar panelExplorar = new PanelExplorar(usuario);
        PanelPortfolio panelPortfolio = new PanelPortfolio(usuario);
        JPanel panelAprender;
        if (usuario instanceof Particular) {
        	Particular pUsuario = (Particular) usuario;
        	panelAprender = new PanelAprender(pUsuario);
        } else {
        	panelAprender = new JPanel();
        	panelAprender.add(new JLabel("El módulo 'Aprender' solo está disponible para cuentas de particulares."), JLabel.CENTER);
        	JButton botonCrearParticular = new JButton("¡Quiero acceder a esta función!");
        	botonCrearParticular.setBackground(Color.WHITE);
        	botonCrearParticular.setForeground(MY_AZUL);
        	botonCrearParticular.setBorderPainted(false);
        	botonCrearParticular.setFocusPainted(false);
        	panelAprender.add(botonCrearParticular);
        	botonCrearParticular.addActionListener(e -> {
    			new VentanaInicial().setVisible(true);
    			dispose();
        	});
        	
        }

        JPanel panelPerfil = new PanelPerfil(usuario);
        
        contenedor.add(panelInicio, "Inicio");
        contenedor.add(panelExplorar, "Explorar");
        contenedor.add(panelPortfolio, "Portfolio");
        contenedor.add(panelAprender,"Aprender");
        contenedor.add(panelPerfil,"Perfil");
        
        layout.show(contenedor, "Inicio");
	}
	
	private void construirPanelNavegacion() {
		JPanel panelNavegacion = new JPanel();
		panelNavegacion.setLayout(new BoxLayout(panelNavegacion, BoxLayout.Y_AXIS));
		panelNavegacion.setBackground(Color.WHITE);
		panelNavegacion.setBorder(BorderFactory.createTitledBorder(
			    BorderFactory.createLineBorder(Color.BLACK, 4), "Menú", javax.swing.border.TitledBorder.CENTER,
			    javax.swing.border.TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14), Color.BLACK));
		panelNavegacion.add(Box.createVerticalGlue());
		
		agregarBotonNavegacion(panelNavegacion, "Inicio", "Inicio",
	            ICONO_INICIO_NEGRO, ICONO_INICIO_AZUL);
		
	    agregarBotonNavegacion(panelNavegacion, "Explorar", "Explorar",
	            ICONO_EXPLORAR_NEGRO, ICONO_EXPLORAR_AZUL);
		
	    agregarBotonNavegacion(panelNavegacion, "Portfolio", "Portfolio",
	            ICONO_PORTFOLIO_NEGRO, ICONO_PORTFOLIO_AZUL);

	    agregarBotonNavegacion(panelNavegacion, "Aprender", "Aprender",
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
        
        boton.setForeground(Color.BLACK);
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
	        mostrarPanel(nombrePanel);
	        boton.setIcon(cargarIcono(iconoActivo));
	        boton.setText("");
	        VentanaPrincipal.this.requestFocusInWindow();
	    });

	    //IAG (ChatGPT)
	    //SIN MODIFICAR
	    // Guarda la referencia para resetBotones()
	    switch (nombrePanel) {
	        case "Inicio" -> botonInicio = boton;
	        case "Explorar" -> botonExplorar = boton;
	        case "Portfolio" -> botonPortfolio = boton;
	        case "Aprender" -> botonAprender = boton;
	        case "Perfil" -> botonPerfil = boton;
	    }
	    //END IAG
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
		
	    for (int i = 0; i < nombresPaneles.length; i++) {
	        if (nombresPaneles[i].equals(nombre)) {
	            indicePanelActual = i;
	            break;
	        }
	    }
		
		switch (nombre) {
		case "Inicio":
			botonInicio.setIcon(cargarIcono(ICONO_INICIO_AZUL));
			botonInicio.setText("");
			break;
		case "Explorar":
			botonExplorar.setIcon(cargarIcono(ICONO_EXPLORAR_AZUL));
			botonExplorar.setText("");
			break;
		case "Portfolio":
			botonPortfolio.setIcon(cargarIcono(ICONO_PORTFOLIO_AZUL));
			botonPortfolio.setText("");
			break;
		case "Aprender":
			botonAprender.setIcon(cargarIcono(ICONO_APRENDER_AZUL));
			botonAprender.setText("");
			break;
		case "Perfil":
			botonPerfil.setIcon(cargarIcono(ICONO_PERFIL_AZUL));
			botonPerfil.setText("");
			break;
			
		default:
			throw new IllegalArgumentException("No existe la pestaña: " + nombre);
		}
	}
	
	KeyListener navegacionKeyListener = new KeyListener() {
		
		@Override
		public void keyTyped(KeyEvent e) { }
		
		@Override
		public void keyReleased(KeyEvent e) { }
		
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.isControlDown() && e.getKeyCode()==KeyEvent.VK_UP) {
				int nuevoIndice = indicePanelActual - 1;
				if (nuevoIndice < 0) {
					nuevoIndice = nombresPaneles.length - 1;
				}
				indicePanelActual = nuevoIndice;
				mostrarPanel(nombresPaneles[indicePanelActual]);
			} else if (e.isControlDown() && e.getKeyCode()==KeyEvent.VK_DOWN) {
				int nuevoIndice = indicePanelActual + 1;
				if (nuevoIndice >= nombresPaneles.length) {
					nuevoIndice = 0;
				}
				indicePanelActual = nuevoIndice;
				mostrarPanel(nombresPaneles[indicePanelActual]);
			}
		}
	};
}