package es.deusto.eleutradia.gui;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import es.deusto.eleutradia.domain.Usuario;

public class VentanaPrincipal extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JLabel labelBienvenida;
	private Usuario usuarioLogueado;
	
	public VentanaPrincipal(Usuario usuario) {
		
		this.usuarioLogueado = usuario;
		
		this.setTitle("Plataforma de Inversion - Bienvenido/a " + usuarioLogueado.getNombre());
		this.setSize(800,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		labelBienvenida = new JLabel("Login exitoso. USUARIO: " + usuarioLogueado.getNombre(), SwingConstants.CENTER);
		labelBienvenida.setFont(new Font("Arial", Font.BOLD, 20));
		this.add(labelBienvenida);
		
		this.setVisible(true);
	}
	

}
