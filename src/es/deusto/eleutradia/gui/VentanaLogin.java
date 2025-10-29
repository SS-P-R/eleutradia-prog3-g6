package es.deusto.eleutradia.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class VentanaLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JLabel labelEmail, labelPassword;
	private JTextField textoEmail;
	private JPasswordField textoPassword;
	private JButton botonLogin, botonRegister;
	
	public VentanaLogin() {
		
		// --- Configuracion del JFrame ---
		this.setTitle("Login/Registro");
		this.setSize(400,250);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		// --- Creacion componentes ---
		JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		
		// Fila 1: Email
		labelEmail = new JLabel("Email:");
		panel.add(labelEmail);
		
		textoEmail = new JTextField(20);
		panel.add(textoEmail);
		
		//Fila 2: Password
		labelPassword = new JLabel("Password:");
		panel.add(labelPassword);
		
		textoPassword = new JPasswordField(20);
		panel.add(textoPassword);
		
		//Fila 3: Botones
		botonLogin = new JButton("Login");
		panel.add(botonLogin);
		
		botonRegister = new JButton("Register");
		panel.add(botonRegister);
		
		// Añadir panel al JFrame
		this.add(panel);
		
		botonLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// ---Implementar logica inicio de sesion---
				
				
				// Abrir la ventana principal
				new VentanaPrincipal();
				
			}
		});
		
		
		
	}
	

}
