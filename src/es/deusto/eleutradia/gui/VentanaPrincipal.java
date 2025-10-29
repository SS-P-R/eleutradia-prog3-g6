package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import es.deusto.eleutradia.domain.Particular;
import es.deusto.eleutradia.domain.Usuario;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JLabel labelBienvenida;
	private JButton botonDashboard, botonBusqueda, botonPortfolio, botonAprendizaje, BotonPerfil;
	private Particular usuarioLogueado;

	public VentanaPrincipal(Particular usuario) {

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
		
		usuarioLogueado = usuario;
		
		this.add(panelNavegacion, BorderLayout.WEST);
		labelBienvenida = new JLabel("Login exitoso. USUARIO: " + usuarioLogueado.getNombre(), SwingConstants.CENTER);
		labelBienvenida.setFont(new Font("Arial", Font.BOLD, 20));
		this.add(labelBienvenida);
	
		this.setVisible(true);
	}
}
