package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import es.deusto.eleutradia.domain.Particular;
import es.deusto.eleutradia.domain.Usuario;
import es.deusto.eleutradia.domain.Empresa;

public class VentanaPrincipal extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private Usuario usuarioLogueado;
	
	private JButton botonDashboard, botonBusqueda, botonPortfolio, botonAprendizaje, botonPerfil;
	private JLabel espacioPrincipal, espacio1, espacio2, espacio3, espacio4;
	
	private JPanel panelDashboard;
    private JPanel panelBusqueda;
    private JPanel panelPortfolio;
    private JPanel panelAprendizaje;
    private JPanel panelPerfil;
    
    private JPanel panelContenido;
    
    private ImageIcon iconoDashboard, iconoDashboardActivo;
    private ImageIcon iconoBusqueda, iconoBusquedaActivo;
    private ImageIcon iconoPortfolio, iconoPortfolioActivo;
    private ImageIcon iconoAprendizaje, iconoAprendizajeActivo;
    private ImageIcon iconoPerfil, iconoPerfilActivo;
	
	public VentanaPrincipal(Usuario usuario) {
		
		this.usuarioLogueado = usuario;
		
		cargarIconos();
		
		if (usuario instanceof Particular) {
			this.setTitle("Plataforma de Inversion - Bienvenido/a " + (Particular)(usuarioLogueado));
		} else {
			this.setTitle("Plataforma de Inversion - Bienvenido/a " + (Empresa)(usuarioLogueado));
		}
		this.setSize(800,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(800, 600));
		
		this.setLayout(new BorderLayout());
		
		JPanel panelNavegacion = new JPanel();
		panelNavegacion.setLayout(new BoxLayout(panelNavegacion, BoxLayout.Y_AXIS));
		panelNavegacion.setBackground(Color.WHITE);
		
		espacioPrincipal = new JLabel();
		espacioPrincipal.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
		panelNavegacion.add(espacioPrincipal);
		
		botonDashboard = crearBotonNavegacion("", iconoDashboardActivo);
		panelNavegacion.add(botonDashboard);
		
		espacio1 = new JLabel();
		espacio1.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
		panelNavegacion.add(espacio1);
		
		botonBusqueda = crearBotonNavegacion("Búsqueda", iconoBusqueda);
		panelNavegacion.add(botonBusqueda);
		
		espacio2 = new JLabel();
		espacio2.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
		panelNavegacion.add(espacio2);
		
		botonPortfolio = crearBotonNavegacion("Portfolio", iconoPortfolio);
		panelNavegacion.add(botonPortfolio);
		
		espacio3 = new JLabel();
		espacio3.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
		panelNavegacion.add(espacio3);
		
		botonAprendizaje = crearBotonNavegacion("Aprendizaje", iconoAprendizaje);
		panelNavegacion.add(botonAprendizaje);
		
		espacio4 = new JLabel();
		espacio4.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
		panelNavegacion.add(espacio4);
		
		botonPerfil = crearBotonNavegacion("Mi perfil", iconoPerfil);
		panelNavegacion.add(botonPerfil);
		
		this.add(panelNavegacion, BorderLayout.WEST);
		
		// Ejemplo de las 5 ventanas
        panelDashboard = new JPanel();
        panelDashboard.add(new JLabel("Aquí irá el Dashboard")); 

        panelBusqueda = new PanelBusqueda();

        panelPortfolio = new JPanel();
        panelPortfolio.add(new JLabel("Aquí irá el Módulo de Portfolio"));

        panelAprendizaje = new JPanel();
        panelAprendizaje.add(new JLabel("Aquí irá el Módulo de Aprendizaje"));
        
        panelPerfil = new JPanel();
        panelPerfil.add(new JLabel("Aquí irá el Módulo de Perfil"));

        // panel donde irian todo el contenido de las ventanas, ahora el texto de ejmplo
        panelContenido = new JPanel(new BorderLayout());
        
        // mostrar el dashboard pordefecto
        panelContenido.add(panelDashboard, BorderLayout.CENTER); 
        add(panelContenido, BorderLayout.CENTER);
		
		ActionListener listenerNavegacion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String nombreBoton = e.getActionCommand();
                
                panelContenido.removeAll();
                
                botonDashboard.setIcon(iconoDashboard);
                botonDashboard.setText("Dashboard");
                
                botonBusqueda.setIcon(iconoBusqueda);
                botonBusqueda.setText("Búsqueda");

                botonPortfolio.setIcon(iconoPortfolio);
                botonPortfolio.setText("Portfolio");

                botonAprendizaje.setIcon(iconoAprendizaje);
                botonAprendizaje.setText("Aprendizaje");

                botonPerfil.setIcon(iconoPerfil);
                botonPerfil.setText("Mi perfil");

                if (nombreBoton.equals("Dashboard")) {
            		panelContenido.add(panelDashboard, BorderLayout.CENTER);
                    botonDashboard.setIcon(iconoDashboardActivo); 
                    botonDashboard.setText("");
            	} else if (nombreBoton.equals("Búsqueda")) {
            		panelContenido.add(panelBusqueda, BorderLayout.CENTER);
                    botonBusqueda.setIcon(iconoBusquedaActivo); 
                    botonBusqueda.setText("");
            	} else if (nombreBoton.equals("Portfolio")) {
            		panelContenido.add(panelPortfolio, BorderLayout.CENTER);
                    botonPortfolio.setIcon(iconoPortfolioActivo); 
                    botonPortfolio.setText("");
            	} else if (nombreBoton.equals("Aprendizaje")) {
            		panelContenido.add(panelAprendizaje, BorderLayout.CENTER);
                    botonAprendizaje.setIcon(iconoAprendizajeActivo); 
                    botonAprendizaje.setText("");
            	} else if (nombreBoton.equals("Mi perfil")) {
            		panelContenido.add(panelPerfil, BorderLayout.CENTER);
                    botonPerfil.setIcon(iconoPerfilActivo); 
                    botonPerfil.setText("");
            	}
                
                // esto hay q hacer para actualizar el panel con el contenido de la nueva ventana
                panelContenido.revalidate();
                panelContenido.repaint();
            }

        };
		
        botonDashboard.addActionListener(listenerNavegacion);
        botonBusqueda.addActionListener(listenerNavegacion);
        botonPortfolio.addActionListener(listenerNavegacion);
        botonAprendizaje.addActionListener(listenerNavegacion);
        botonPerfil.addActionListener(listenerNavegacion);
        
		this.setVisible(true);
	}
	
	private void cargarIconos() {

		iconoDashboard = cargarIcono("/imagenes/casaNegro.png");
        iconoDashboardActivo = cargarIcono("/imagenes/casaAzul.png");
        
        iconoBusqueda = cargarIcono("/imagenes/busquedaNegro.png"); // Asumiendo nombres
        iconoBusquedaActivo = cargarIcono("/imagenes/busquedaAzul.png");
        
        iconoPortfolio = cargarIcono("/imagenes/portfolioNegro.png");
        iconoPortfolioActivo = cargarIcono("/imagenes/portfolioAzul.png");
        
        iconoAprendizaje = cargarIcono("/imagenes/aprendizajeNegro.png");
        iconoAprendizajeActivo = cargarIcono("/imagenes/aprendizajeAzul.png");
        
        iconoPerfil = cargarIcono("/imagenes/perfilNegro.png");
        iconoPerfilActivo = cargarIcono("/imagenes/perfilAzul.png");		
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
	
}