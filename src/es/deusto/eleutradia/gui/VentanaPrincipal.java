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

import static es.deusto.eleutradia.gui.style.UITema.*;

public class VentanaPrincipal extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	        
    private CardLayout layout;
    private JPanel contenedor;
    private JPanel panelMenu;
    private JButton botonInicio, botonExplorar, botonPortfolio, botonAprender, botonPerfil;
    
    private final String[] nombresPaneles = {"Inicio", "Explorar", "Portfolio", "Aprender", "Perfil"};
    private int indicePanelActual = 0;
    
    // Nombres de los iconos de menú
    private static final String INICIO = "inicio";
    private static final String EXPLORAR = "explorar";
    private static final String PORTFOLIO = "portfolio";
    private static final String APRENDER = "aprender";
    private static final String PERFIL = "perfil";
    
    // Paneles
    private PanelInicio panelInicio;
    private PanelAprender panelAprender;
    private PanelExplorar panelExplorar;
    private PanelPerfil panelPerfil;
    private PanelPortfolio panelPortfolio;
    	
	public VentanaPrincipal(Usuario usuario) {
		super("EleuTradia: Inicio");
		this.usuario = usuario;
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
		this.setSize(800, 600);
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
		panelMenu = new JPanel();
		panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
		panelMenu.setBackground(MAIN_FONDO);
		panelMenu.setBorder(BorderFactory.createTitledBorder(
			    BorderFactory.createLineBorder(Color.BLACK, 4), "Menú", TitledBorder.CENTER,
			    TitledBorder.BOTTOM, new Font("Segoe UI", Font.BOLD, 14), Color.BLACK));
	    panelMenu.setPreferredSize(new Dimension(105, 600));
		
		panelMenu.add(Box.createVerticalGlue());
		
		agregarBotonMenu(panelMenu, "Inicio", "Inicio", INICIO);
	    agregarBotonMenu(panelMenu, "Explorar", "Explorar", EXPLORAR);
	    agregarBotonMenu(panelMenu, "Portfolio", "Portfolio", PORTFOLIO);
	    agregarBotonMenu(panelMenu, "Aprender", "Aprender", APRENDER);
	    agregarBotonMenu(panelMenu, "Perfil", "Perfil", PERFIL);
		
		panelMenu.add(Box.createVerticalGlue());
		
		this.add(panelMenu, BorderLayout.WEST);
		
		botonInicio.setIcon(cargarIcono(INICIO, true));
		botonInicio.setText("");
	}

	private ImageIcon cargarIcono(String nombreIcono, boolean seleccionado) {
        try {
        	String colorIcono = "";
        	if (seleccionado) colorIcono = "Azul";
        	else colorIcono = "Negro";
        	String rutaIcono = "/images/menu/" + nombreIcono + colorIcono + ".png";
            return new ImageIcon(getClass().getResource(rutaIcono));
        } catch (Exception e) {
            System.err.println("Error al cargar icono: " + nombreIcono);
            return null; // el botón se muestra sin icono si falla
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
	
	private void agregarBotonMenu(JPanel panel, String nombrePanel,
			String texto, String nombreIcono) {
		
	    ImageIcon icono = cargarIcono(nombreIcono, false);
	    JButton boton = crearBotonNavegacion(texto, icono);
	    panel.add(boton);
	    panel.add(Box.createVerticalStrut(30));

	    boton.addActionListener(e -> {
	        mostrarPanel(nombrePanel);
	        boton.setIcon(cargarIcono(nombreIcono, true));
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
		resetBoton(botonInicio, INICIO, "Inicio");

		resetBoton(botonExplorar, EXPLORAR, "Explorar");

		resetBoton(botonPortfolio, PORTFOLIO, "Portfolio");

		resetBoton(botonAprender, APRENDER, "Aprender");

		resetBoton(botonPerfil, PERFIL, "Perfil");

    }
	
	private void resetBoton(JButton boton, String nombreIcono, String texto) {
	    boton.setIcon(cargarIcono(nombreIcono, false));
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
			botonInicio.setIcon(cargarIcono(INICIO, true));
			botonInicio.setText("");
			break;
		case "Explorar":
			botonExplorar.setIcon(cargarIcono(EXPLORAR, true));
			botonExplorar.setText("");
			break;
		case "Portfolio":
			botonPortfolio.setIcon(cargarIcono(PORTFOLIO, true));
			botonPortfolio.setText("");
			break;
		case "Aprender":
			botonAprender.setIcon(cargarIcono(APRENDER, true));
			botonAprender.setText("");
			break;
		case "Perfil":
			botonPerfil.setIcon(cargarIcono(PERFIL, true));
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