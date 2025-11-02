package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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

import es.deusto.eleutradia.domain.Particular;
import es.deusto.eleutradia.domain.Usuario;
import es.deusto.eleutradia.domain.Empresa;

public class VentanaPrincipal extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private Usuario usuarioLogueado;
	
	private JButton botonInicio, botonExplorar, botonPortfolio, botonAprender, botonPerfil;
	
	private JPanel panelInicio;
    private JPanel panelExplorar;
    private JPanel panelPortfolio;
    private JPanel panelAprender;
    private JPanel panelPerfil;
    
    private JPanel panelContenido;
    
    private ImageIcon iconoInicio, iconoInicioActivo;
    private ImageIcon iconoExplorar, iconoExplorarActivo;
    private ImageIcon iconoPortfolio, iconoPortfolioActivo;
    private ImageIcon iconoAprender, iconoAprenderActivo;
    private ImageIcon iconoPerfil, iconoPerfilActivo;
	
	public VentanaPrincipal(Usuario usuario) {
		
		this.usuarioLogueado = usuario;
		
		cargarIconos();
		
		if (usuario instanceof Particular) {
			this.setTitle("EleuTradia - Bienvenido/a " + ((Particular)usuarioLogueado).getNombre());
		} else {
			this.setTitle("EleuTradia - Bienvenido/a " + ((Empresa)usuarioLogueado).getNombre());
		}
		this.setSize(800,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(800, 600));
		
		this.setLayout(new BorderLayout());
		
		JPanel panelNavegacion = new JPanel();
		panelNavegacion.setLayout(new BoxLayout(panelNavegacion, BoxLayout.Y_AXIS));
		panelNavegacion.setBackground(Color.WHITE);
		panelNavegacion.setAlignmentY(CENTER_ALIGNMENT);
		
		panelNavegacion.add(Box.createVerticalGlue());
		
		botonInicio = crearBotonNavegacion("", iconoInicioActivo);
		panelNavegacion.add(botonInicio);
		panelNavegacion.add(Box.createVerticalStrut(30));
		
		botonExplorar = crearBotonNavegacion("Explorar", iconoExplorar);
		panelNavegacion.add(botonExplorar);
		panelNavegacion.add(Box.createVerticalStrut(30));
		
		botonPortfolio = crearBotonNavegacion("Portfolio", iconoPortfolio);
		panelNavegacion.add(botonPortfolio);
		panelNavegacion.add(Box.createVerticalStrut(30));
		
		botonAprender = crearBotonNavegacion("Aprender", iconoAprender);
		panelNavegacion.add(botonAprender);
		panelNavegacion.add(Box.createVerticalStrut(30));
		
		botonPerfil = crearBotonNavegacion("Mi perfil", iconoPerfil);
		panelNavegacion.add(botonPerfil);
		
		panelNavegacion.add(Box.createVerticalGlue());
		
		this.add(panelNavegacion, BorderLayout.WEST);
		
		// Ejemplo de las 5 ventanas
        panelInicio = new JPanel();
        panelInicio.add(new JLabel("Aquí irá el Módulo de Inicio")); 

        panelExplorar = new PanelExplorar();

        panelPortfolio = new JPanel();
        panelPortfolio.add(new JLabel("Aquí irá el Módulo de Portfolio"));

        panelAprender = new JPanel();
        panelAprender.add(new JLabel("Aquí irá el Módulo de Aprender"));
        
        panelPerfil = new JPanel();
        panelPerfil.add(new JLabel("Aquí irá el Módulo de Perfil"));

        // panel donde irian todo el contenido de las ventanas, ahora el texto de ejmplo
        panelContenido = new JPanel(new BorderLayout());
        
        // Por defecto se muestra el Dashboard
        panelContenido.add(panelInicio, BorderLayout.CENTER); 
        this.add(panelContenido, BorderLayout.CENTER);
		
		ActionListener listenerNavegacion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String nombreBoton = e.getActionCommand();
                
                panelContenido.removeAll();
                
                botonInicio.setIcon(iconoInicio);
                botonInicio.setText("Inicio");
                
                botonExplorar.setIcon(iconoExplorar);
                botonExplorar.setText("Explorar");

                botonPortfolio.setIcon(iconoPortfolio);
                botonPortfolio.setText("Portfolio");

                botonAprender.setIcon(iconoAprender);
                botonAprender.setText("Aprender");

                botonPerfil.setIcon(iconoPerfil);
                botonPerfil.setText("Mi perfil");

                if (nombreBoton.equals("Inicio")) {
            		panelContenido.add(panelInicio, BorderLayout.CENTER);
                    botonInicio.setIcon(iconoInicioActivo); 
                    botonInicio.setText("");
            	} else if (nombreBoton.equals("Explorar")) {
            		panelContenido.add(panelExplorar, BorderLayout.CENTER);
                    botonExplorar.setIcon(iconoExplorarActivo); 
                    botonExplorar.setText("");
            	} else if (nombreBoton.equals("Portfolio")) {
            		panelContenido.add(panelPortfolio, BorderLayout.CENTER);
                    botonPortfolio.setIcon(iconoPortfolioActivo); 
                    botonPortfolio.setText("");
            	} else if (nombreBoton.equals("Aprender")) {
            		panelContenido.add(panelAprender, BorderLayout.CENTER);
                    botonAprender.setIcon(iconoAprenderActivo); 
                    botonAprender.setText("");
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
		
        botonInicio.addActionListener(listenerNavegacion);
        botonExplorar.addActionListener(listenerNavegacion);
        botonPortfolio.addActionListener(listenerNavegacion);
        botonAprender.addActionListener(listenerNavegacion);
        botonPerfil.addActionListener(listenerNavegacion);
        
		this.setVisible(true);
	}
	
	private void cargarIconos() {

		iconoInicio = cargarIcono("/imagenes/casaNegro.png");
        iconoInicioActivo = cargarIcono("/imagenes/casaAzul.png");
        
        iconoExplorar = cargarIcono("/imagenes/busquedaNegro.png"); // Asumiendo nombres
        iconoExplorarActivo = cargarIcono("/imagenes/busquedaAzul.png");
        
        iconoPortfolio = cargarIcono("/imagenes/portfolioNegro.png");
        iconoPortfolioActivo = cargarIcono("/imagenes/portfolioAzul.png");
        
        iconoAprender = cargarIcono("/imagenes/aprendizajeNegro.png");
        iconoAprenderActivo = cargarIcono("/imagenes/aprendizajeAzul.png");
        
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