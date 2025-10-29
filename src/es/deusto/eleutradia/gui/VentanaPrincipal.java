package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
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
	private JButton botonDashboard, botonBusqueda, botonPortfolio, botonAprendizaje, BotonPerfil;
	private Usuario usuarioLogueado;
	
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
		
		botonDashboard = new JButton("Dashboard");
		botonBusqueda = new JButton("Búsqueda");
		botonPortfolio = new JButton("Portfolio");
		botonAprendizaje = new JButton("Aprendizaje");
		BotonPerfil = new JButton("Mi perfil");
		
		panelNavegacion.add(botonDashboard);
		panelNavegacion.add(botonBusqueda);
		panelNavegacion.add(botonPortfolio);
		panelNavegacion.add(botonAprendizaje);
		panelNavegacion.add(BotonPerfil);
		
		this.add(panelNavegacion, BorderLayout.WEST);
		labelBienvenida = new JLabel("Login exitoso. USUARIO: " + usuarioLogueado.getEmail(), SwingConstants.CENTER);
		labelBienvenida.setFont(new Font("Arial", Font.BOLD, 20));
		this.add(labelBienvenida);
		
		this.setVisible(true);
	}
	
}