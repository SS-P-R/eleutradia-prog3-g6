package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
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
	
	private JLabel labelBienvenida;
	private JButton botonDashboard, botonBusqueda, botonPortfolio, botonAprendizaje, botonPerfil;
	private Usuario usuarioLogueado;
	
	private JPanel panelDashboard;
    private JPanel panelBusqueda;
    private JPanel panelPortfolio;
    private JPanel panelAprendizaje;
    private JPanel panelPerfil;
    
    private JPanel panelContenido; 
	
	public VentanaPrincipal(Usuario usuario) {
		
		this.usuarioLogueado = usuario;
		
		if (usuario instanceof Particular) {
			this.setTitle("Plataforma de Inversion - Bienvenido/a " + (Particular)(usuarioLogueado));
		} else {
			this.setTitle("Plataforma de Inversion - Bienvenido/a " + (Empresa)(usuarioLogueado));
		}
		this.setSize(800,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		this.setLayout(new BorderLayout());
		
		JPanel panelNavegacion = new JPanel();
		panelNavegacion.setLayout(new BoxLayout(panelNavegacion, BoxLayout.Y_AXIS));
		
		JLabel espacio = new JLabel();
		espacio.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
		botonDashboard = crearBotonNavegacion("Dashboard", "/imagenes/casaNegro.png");
		botonBusqueda = crearBotonNavegacion("Búsqueda", "/imagenes/busquedaNegro.png");
		botonPortfolio = crearBotonNavegacion("Portfolio", "/imagenes/carteraNegro.png");
		botonAprendizaje = crearBotonNavegacion("Aprendizaje", "/imagenes/librosNegro.png");
		botonPerfil = crearBotonNavegacion("Mi perfil", "/imagenes/perfilNegro.png");
		
		panelNavegacion.add(espacio);
		panelNavegacion.add(botonDashboard);
		panelNavegacion.add(botonBusqueda);
		panelNavegacion.add(botonPortfolio);
		panelNavegacion.add(botonAprendizaje);
		panelNavegacion.add(botonPerfil);
		
		this.add(panelNavegacion, BorderLayout.WEST);
		
		// Ejemplo de las 5 ventanas
        panelDashboard = new JPanel();
        panelDashboard.add(new JLabel("Aquí irá el Dashboard")); 

        panelBusqueda = new JPanel();
        panelBusqueda.add(new JLabel("Aquí irá el Módulo de Búsqueda")); 

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

                if (nombreBoton.equals("Dashboard")) {
                    panelContenido.add(panelDashboard, BorderLayout.CENTER);
                } else if (nombreBoton.equals("Búsqueda")) {
                    panelContenido.add(panelBusqueda, BorderLayout.CENTER);
                } else if (nombreBoton.equals("Portfolio")) {
                    panelContenido.add(panelPortfolio, BorderLayout.CENTER);
                } else if (nombreBoton.equals("Aprendizaje")) {
                    panelContenido.add(panelAprendizaje, BorderLayout.CENTER);
                } else if (nombreBoton.equals("Mi perfil")) {
                    panelContenido.add(panelPerfil, BorderLayout.CENTER);
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
	
	private JButton crearBotonNavegacion(String texto, String rutaIcono) {
        JButton boton = new JButton(texto);
        
        // 1. Cargar el Icono
        try {
            ImageIcon icono = new ImageIcon(getClass().getResource(rutaIcono));
            boton.setIcon(icono);
        } catch (Exception e) {
            // Si el icono no se encuentra, el botón se mostrará solo con texto
            System.err.println("Error al cargar icono: " + rutaIcono);
        }
        
        boton.setIcon(new ImageIcon(rutaIcono));
        
        // 2. Configurar la Posición del Texto (¡Lo que estabas pidiendo!)
        boton.setVerticalTextPosition(SwingConstants.BOTTOM); 
        boton.setHorizontalTextPosition(SwingConstants.CENTER); 
        
        // Opcional: Estilo de botón para menú (para que no parezca un botón 3D estándar)
        // Puedes ajustar esto según tu diseño
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setFocusPainted(false);
        boton.setOpaque(false); // Puede ser útil si el panel de navegación tiene color
        
        return boton;
    }
	
}