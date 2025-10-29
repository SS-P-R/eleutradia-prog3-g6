package es.deusto.eleutradia.main;

import javax.swing.SwingUtilities;

import es.deusto.eleutradia.gui.VentanaLogin;

public class MainEleutradia {
	
	public static void main(String[] args) {
		
		// ---Ejecutar la creacion de la GUI---
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
				// Crear una instancia de VentanaLogin
				new VentanaLogin().setVisible(true);
				
			}
		});
		
	}
	
}
