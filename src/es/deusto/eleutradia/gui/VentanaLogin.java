package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import es.deusto.eleutradia.domain.Pais;
import es.deusto.eleutradia.domain.Particular;
import es.deusto.eleutradia.domain.RegionGeografica;
import es.deusto.eleutradia.domain.Usuario;

public class VentanaLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JLabel labelEmail, labelPassword;
	private JTextField textoEmail;
	private JPasswordField textoPassword;
	private JButton botonLogin, botonRegister;
	
	public VentanaLogin() {
		
		configurarVentana();
        crearYOrganizarPaneles();
        registrarActionListeners();
        
        // Importante: hacer visible la ventana al final
        this.setVisible(true);
	}
	
	private void configurarVentana() {
		
		this.setTitle("Login/Registro");
		this.setSize(400,250);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
	}
	
	private void crearYOrganizarPaneles() {
		
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		botonLogin = new JButton("Login");
		panelBotones.add(botonLogin);
		botonRegister = new JButton("Register");
		panelBotones.add(botonRegister);
		
		JPanel panelFormulario = new JPanel(new BorderLayout(10, 10));
		panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
		
		JPanel panelEtiquetas = new JPanel(new GridLayout(2, 1, 5,5));
		labelEmail = new JLabel("Email:");
		labelEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		labelPassword = new JLabel("Password:");
		labelPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		panelEtiquetas.add(labelEmail);
		panelEtiquetas.add(labelPassword);
		
		JPanel panelCampos = new JPanel(new GridLayout(2, 1, 5, 5));
		textoEmail = new JTextField(20);
		panelCampos.add(textoEmail);
		textoPassword = new JPasswordField(20);
		panelCampos.add(textoPassword);
		
		panelFormulario.add(panelEtiquetas, BorderLayout.WEST);
		panelFormulario.add(panelCampos, BorderLayout.CENTER);

		this.getContentPane().add(panelFormulario, BorderLayout.CENTER);
		this.getContentPane().add(panelBotones, BorderLayout.SOUTH);
	}
	
	private void registrarActionListeners() {
		
		botonLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// ---Implementar logica inicio de sesion---
                String email = textoEmail.getText();
                String password = new String(textoPassword.getPassword());

                Particular usuario = null;
                if ("user@test.com".equals(email) && "1234".equals(password)) {
                	usuario = new Particular("79123456", "Usuario Prueba", LocalDate.of(2000, 1, 1), new Pais(1, "Suiza", RegionGeografica.EUROPA_OCCIDENTAL), email, "689123456", "Calle Prueba", new Pais(1, "Suiza", RegionGeografica.EUROPA_OCCIDENTAL));
                }

                if (usuario != null) {
                    new VentanaPrincipal(usuario);
                    dispose(); // Cerrar la ventana de login
                } else {
                    JOptionPane.showMessageDialog(VentanaLogin.this, 
                                                  "Email o contraseña incorrectos.", 
                                                  "Error de Login", 
                                                  JOptionPane.ERROR_MESSAGE);
                }
            }
		});
			
	
		
		
	}
	

}
