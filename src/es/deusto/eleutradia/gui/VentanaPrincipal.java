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
import javax.swing.border.TitledBorder;

import es.deusto.eleutradia.domain.Particular;
import es.deusto.eleutradia.domain.Usuario;

import static es.deusto.eleutradia.gui.style.UITema.AZUL_CLARO;

public class VentanaPrincipal extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	private GestorTema gestorTema;
	        
    private CardLayout layout;
    private JPanel contenedor;
    
    private JButton botonInicio, botonExplorar, botonPortfolio, botonAprender, botonPerfil;
    
    private final String[] nombresPaneles = {"Inicio", "Explorar", "Portfolio", "Aprender", "Perfil"};
    private int indicePanelActual = 0;
    
    // Rutas de los iconos de pestaña
    private static final String ICONO_INICIO_NEGRO = "/images/menu/inicioNegro.png";
    private static final String ICONO_INICIO_AZUL = "/images/menu/inicioAzul.png";
    private static final String ICONO_EXPLORAR_NEGRO = "/images/menu/explorarNegro.png";
    private static final String ICONO_EXPLORAR_AZUL = "/images/menu/explorarAzul.png";
    private static final String ICONO_PORTFOLIO_NEGRO = "/images/menu/portfolioNegro.png";
    private static final String ICONO_PORTFOLIO_AZUL = "/images/menu/portfolioAzul.png";
    private static final String ICONO_APRENDER_NEGRO = "/images/menu/aprenderNegro.png";  
    private static final String ICONO_APRENDER_AZUL = "/images/menu/aprenderAzul.png";    
    private static final String ICONO_PERFIL_NEGRO = "/images/menu/perfilNegro.png";    
    private static final String ICONO_PERFIL_AZUL = "/images/menu/perfilAzul.png";
    
    //Paneles
    private PanelInicio panelInicio;
    private PanelAprender panelAprender;
    private PanelExplorar panelExplorar;
    private PanelPerfil panelPerfil;
    private PanelPortfolio panelPortfolio;
    	
	public VentanaPrincipal(Usuario usuario) {
		super("EleuTradia: Inicio");
		this.usuario = usuario;
		this.gestorTema = GestorTema.getInstancia();
		this.configurarVentana();
		this.inicializarPaneles();
		this.construirPanelMenu();
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
		this.panelInicio = new PanelInicio(usuario, this);
		this.panelExplorar = new PanelExplorar(usuario);
		this.panelPortfolio = new PanelPortfolio(usuario);
		this.panelPerfil = new PanelPerfil(usuario, this);
        if (usuario instanceof Particular) {
        	Particular pUsuario = (Particular) usuario;
        	this.panelAprender = new PanelAprender(pUsuario);
        } else {
        	this.panelAprender = (PanelAprender) new JPanel();
        	this.panelAprender.add(new JLabel("El módulo 'Aprender' solo está disponible para cuentas de particulares."), JLabel.CENTER);
        	JButton botonCrearParticular = new JButton("¡Quiero acceder a esta función!");
        	botonCrearParticular.setBackground(Color.WHITE);
        	botonCrearParticular.setForeground(AZUL_CLARO);
        	botonCrearParticular.setBorderPainted(false);
        	botonCrearParticular.setFocusPainted(false);
        	panelAprender.add(botonCrearParticular);
        	botonCrearParticular.addActionListener(e -> {
    			new VentanaInicial().setVisible(true);
    			dispose();
        	});
        	
        }
        
        contenedor.add(this.panelInicio, "Inicio");
        contenedor.add(this.panelExplorar, "Explorar");
        contenedor.add(this.panelPortfolio, "Portfolio");
        contenedor.add(this.panelAprender,"Aprender");
        contenedor.add(this.panelPerfil,"Perfil");
        layout.show(contenedor, "Inicio");
	}
	
	private void construirPanelMenu() {
		JPanel panelMenu = new JPanel();
		panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
		panelMenu.setBackground(Color.WHITE);
		panelMenu.setBorder(BorderFactory.createTitledBorder(
			    BorderFactory.createLineBorder(Color.BLACK, 4), "Menú", TitledBorder.CENTER,
			    TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14), Color.BLACK));
	    panelMenu.setPreferredSize(new Dimension(110, 600));
		
		panelMenu.add(Box.createVerticalGlue());
		
		agregarBotonMenu(panelMenu, "Inicio", "Inicio",
	            ICONO_INICIO_NEGRO, ICONO_INICIO_AZUL);
		
	    agregarBotonMenu(panelMenu, "Explorar", "Explorar",
	            ICONO_EXPLORAR_NEGRO, ICONO_EXPLORAR_AZUL);
		
	    agregarBotonMenu(panelMenu, "Portfolio", "Portfolio",
	            ICONO_PORTFOLIO_NEGRO, ICONO_PORTFOLIO_AZUL);

	    agregarBotonMenu(panelMenu, "Aprender", "Aprender",
	            ICONO_APRENDER_NEGRO, ICONO_APRENDER_AZUL);

	    agregarBotonMenu(panelMenu, "Perfil", "Perfil",
	            ICONO_PERFIL_NEGRO, ICONO_PERFIL_AZUL);
		
		panelMenu.add(Box.createVerticalGlue());
		this.add(panelMenu, BorderLayout.WEST);
		
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
	
	public void actualizarTema() {
		if (panelPerfil!=null) {
			panelPortfolio.refrescarColores();
		}
	    if (panelPerfil != null) {
	        panelPerfil.refrescarDatos();
	    }
	    revalidate();
	    repaint();
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
	
	private void agregarBotonMenu(JPanel panel, String nombrePanel,
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