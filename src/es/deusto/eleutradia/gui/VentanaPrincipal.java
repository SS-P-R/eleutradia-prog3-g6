package es.deusto.eleutradia.gui;

import javax.swing.JFrame;

import es.deusto.eleutradia.domain.Usuario;

public class VentanaPrincipal extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private Usuario usuarioLogueado;
	
	public VentanaPrincipal(Usuario usuario) {
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setVisible(true);
	}
	

}
