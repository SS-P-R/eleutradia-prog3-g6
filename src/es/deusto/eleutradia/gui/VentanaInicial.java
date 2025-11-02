package es.deusto.eleutradia.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import es.deusto.eleutradia.domain.Empresa;
import es.deusto.eleutradia.domain.Particular;
import es.deusto.eleutradia.main.MainEleutradia;

public class VentanaInicial extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private CardLayout layout;
	private JPanel contenedor;
	private JButton botonParticular = new JButton("Acceder como Particular");
	private JButton botonEmpresa = new JButton("Acceder como Empresa");
	private JButton botonRegistro = new JButton("Pulse aquí para abrir una cuenta");
	private JTextField campoIdParticular, campoIdEmpresa;
	private JPasswordField campoPasswordParticular, campoPasswordEmpresa;
	private JButton botonLoginParticular, botonLoginEmpresa, botonVolverParticular, botonVolverEmpresa;
    private ImageIcon originalIcon;
	
    // Estilos
    private static final Color MY_AZUL = new Color(0, 100, 255);   // Azul
    private static final Color MY_GRIS = new Color(100, 100, 100); // Gris
    private static final Font FONT_TITULO = new Font("Segoe UI", Font.BOLD, 20);
    private static final Font FONT_SUBTITULO = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONT_NORMAL = new Font("Segoe UI", Font.BOLD, 12);
    private static final Font FONT_CAMPO = new Font("Segoe UI", Font.PLAIN, 14);
	
	public VentanaInicial() {
		super("EleuTradia: Inicio");
		this.configurarVentana();
        this.inicializarPaneles();
        this.registrarActionListeners();
        this.setVisible(true);
	}
	
	private void configurarVentana() {
		this.setSize(800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setMinimumSize(new Dimension(800, 600));
		
		layout = new CardLayout();
		contenedor = new JPanel(layout);
		this.add(contenedor);
	}
	
	private void inicializarPaneles() {
		JPanel panelBienvenida = construirPanelBienvenida();
	    JPanel panelLoginParticular = construirPanelLogin(true);
	    JPanel panelLoginEmpresa = construirPanelLogin(false);
	    contenedor.add(panelBienvenida, "bienvenida");
	    contenedor.add(panelLoginParticular, "loginParticular");
	    contenedor.add(panelLoginEmpresa, "loginEmpresa");
	    layout.show(contenedor, "bienvenida");
	}
	
	private JPanel construirPanelBienvenida() {		
		JPanel mainPanel = new JPanel(new GridLayout(1, 2));
		
		JPanel panelDcho = new JPanel();
		panelDcho.setLayout(new BoxLayout(panelDcho, BoxLayout.Y_AXIS));
		panelDcho.setBackground(Color.WHITE);
		panelDcho.setBorder(BorderFactory.createEmptyBorder(100, 50, 60, 50));
		panelDcho.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		panelDcho.setAlignmentY(JPanel.CENTER_ALIGNMENT);

		JLabel titulo = new JLabel("¡Bienvenido/a a EleuTradia!", JLabel.CENTER);
		titulo.setFont(FONT_TITULO);
		titulo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		titulo.setMaximumSize(new Dimension(Integer.MAX_VALUE, titulo.getPreferredSize().height));
		panelDcho.add(titulo);
		panelDcho.add(Box.createVerticalStrut(10));
		
		JLabel subtitulo = new JLabel("Seleccione su método de acceso:", JLabel.CENTER);
		subtitulo.setFont(FONT_SUBTITULO);
		subtitulo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		subtitulo.setMaximumSize(new Dimension(Integer.MAX_VALUE, subtitulo.getPreferredSize().height));
		panelDcho.add(subtitulo);
		panelDcho.add(Box.createVerticalStrut(90));
		
		botonParticular.setAlignmentX(JButton.CENTER_ALIGNMENT);
		botonEmpresa.setAlignmentX(JButton.CENTER_ALIGNMENT);
		botonParticular.setMaximumSize(new Dimension(220, 40));
		botonEmpresa.setMaximumSize(new Dimension(220, 40));
		panelDcho.add(botonParticular);
		panelDcho.add(Box.createVerticalStrut(20));
		panelDcho.add(botonEmpresa);
		panelDcho.add(Box.createVerticalStrut(120));
	    
        JPanel panelRegistro = new JPanel();
        panelRegistro.setLayout(new BoxLayout(panelRegistro, BoxLayout.Y_AXIS));
        panelRegistro.setBackground(Color.WHITE);
        panelRegistro.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        
        JLabel labelRegistro = new JLabel("¿No es cliente?");
        labelRegistro.setFont(FONT_NORMAL);
        labelRegistro.setForeground(MY_AZUL);
        labelRegistro.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panelRegistro.add(labelRegistro);
        
        botonRegistro.setFont(FONT_NORMAL);
        botonRegistro.setBackground(Color.WHITE);
        botonRegistro.setForeground(MY_AZUL);
        botonRegistro.setFocusPainted(false);
        botonRegistro.setBorder(null);
        botonRegistro.setAlignmentX(JButton.CENTER_ALIGNMENT);
        panelRegistro.add(botonRegistro);
        
        panelDcho.add(panelRegistro);
		
		JPanel panelIzdo = construirPanelImagen();
		
		mainPanel.add(panelIzdo);
		mainPanel.add(panelDcho);
		
		return mainPanel;
	}
	
	private JPanel construirPanelLogin(boolean esParticular) {
		JPanel mainPanel = new JPanel(new GridLayout(1, 2));
		
		String tipoUsuario = esParticular ? "Particular" : "Empresa";
		
        JPanel panelDcho = new JPanel();
        panelDcho.setLayout(new BoxLayout(panelDcho, BoxLayout.Y_AXIS));
        panelDcho.setBackground(Color.WHITE);
        panelDcho.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));
        
	    JLabel tituloLogin = new JLabel("Inicio de sesión - " + tipoUsuario, JLabel.CENTER);
	    tituloLogin.setFont(FONT_TITULO);
	    tituloLogin.setAlignmentX(JLabel.CENTER_ALIGNMENT);
	    panelDcho.add(tituloLogin);
	    panelDcho.add(Box.createVerticalStrut(10));
	    
	    JLabel subtituloLogin = new JLabel("Introduzca sus datos:", JLabel.CENTER);
	    subtituloLogin.setFont(FONT_SUBTITULO);
	    subtituloLogin.setAlignmentX(JLabel.CENTER_ALIGNMENT);
	    panelDcho.add(subtituloLogin);
	    panelDcho.add(Box.createVerticalStrut(30));
	    
	    JPanel panelAcceso = new JPanel();
	    panelAcceso.setLayout(new BoxLayout(panelAcceso, BoxLayout.Y_AXIS));
	    panelAcceso.setBackground(Color.WHITE);
	    panelAcceso.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel labelId = new JLabel(esParticular ? "DNI:" : "NIF:");
        labelId.setFont(FONT_NORMAL);
        labelId.setForeground(MY_GRIS);
        labelId.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        panelAcceso.add(labelId);
        panelAcceso.add(Box.createVerticalStrut(10));

        JTextField campoId = new JTextField(20);
        campoId.setFont(FONT_CAMPO);
        campoId.setMaximumSize(new Dimension(350, 35));
        campoId.setPreferredSize(new Dimension(350, 35));
        campoId.setAlignmentX(LEFT_ALIGNMENT);
        panelAcceso.add(campoId);
        panelAcceso.add(Box.createVerticalStrut(20));
        
        JLabel labelPassword = new JLabel("Contraseña:");
        labelPassword.setFont(FONT_NORMAL);
        labelPassword.setForeground(MY_GRIS);
        labelPassword.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        panelAcceso.add(labelPassword);
        panelAcceso.add(Box.createVerticalStrut(10));

        JPasswordField campoPassword = new JPasswordField(20);
        campoPassword.setFont(FONT_CAMPO);
        campoPassword.setMaximumSize(new Dimension(350, 35));
        campoPassword.setPreferredSize(new Dimension(350, 35));
        campoPassword.setAlignmentX(LEFT_ALIGNMENT);
        panelAcceso.add(campoPassword);
        panelAcceso.add(Box.createVerticalStrut(40));
        
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 20, 0));
        panelBotones.setBackground(Color.WHITE);
        panelBotones.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelBotones.setMaximumSize(new Dimension(350, 50));
        
        JButton botonVolver = new JButton("Volver");
        botonVolver.setFont(FONT_NORMAL);
        botonVolver.setBackground(MY_GRIS);
        botonVolver.setForeground(Color.WHITE);
        botonVolver.setFocusPainted(false);
        
        JButton botonLogin = new JButton("Iniciar sesión");
        botonLogin.setFont(FONT_NORMAL);
        botonLogin.setBackground(MY_AZUL);
        botonLogin.setForeground(Color.WHITE);
        botonLogin.setFocusPainted(false);
        
        if (esParticular) {
            campoIdParticular = campoId;
            campoPasswordParticular = campoPassword;
            botonLoginParticular = botonLogin;
            botonVolverParticular = botonVolver;
        } else {
            campoIdEmpresa = campoId;
            campoPasswordEmpresa = campoPassword;
            botonLoginEmpresa = botonLogin;
            botonVolverEmpresa = botonVolver;
        }
        
        panelBotones.add(botonVolver);
        panelBotones.add(botonLogin);
        
        panelAcceso.add(panelBotones);
        
        panelDcho.add(panelAcceso);

        JPanel panelIzdo = construirPanelImagen();
        
        mainPanel.add(panelIzdo);
        mainPanel.add(panelDcho);

        return mainPanel;
    }
	
	private JPanel construirPanelImagen() {
        JPanel panel = new JPanel(new GridLayout());
        panel.setBackground(Color.BLACK);

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        try {
        	Random random = new Random();
            ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/fondoLogin" + (random.nextInt(2)+1) + ".png"));
            Image imagen = icon.getImage().getScaledInstance(this.getWidth()/2, this.getHeight(), java.awt.Image.SCALE_SMOOTH);
            originalIcon = new ImageIcon(imagen);
        } catch (Exception e) {
            System.err.println("No se pudo cargar la imagen");
            originalIcon = null;
        }
        
        imageLabel.setIcon(originalIcon);
        panel.add(imageLabel);
        
        return panel;
    }
	
	private void registrarActionListeners() {
		
		botonParticular.addActionListener(e -> {
			layout.show(contenedor, "loginParticular");
			setTitle("EleuTradia: Iniciar sesión");
		});
		
		botonEmpresa.addActionListener(e -> {
			layout.show(contenedor, "loginEmpresa");
			setTitle("EleuTradia: Iniciar sesión");
		});
		
		botonVolverParticular.addActionListener(e -> {
			layout.show(contenedor, "bienvenida");
			setTitle("EleuTradia: Inicio");
		});
		
		botonVolverEmpresa.addActionListener(e -> {
			layout.show(contenedor, "bienvenida");
			setTitle("EleuTradia: Inicio");
		});
		
		botonLoginParticular.addActionListener(e -> {
                String dni = campoIdParticular.getText().trim();
                String password = new String(campoPasswordParticular.getPassword());
                
                if (!(dni.isBlank() || password.isEmpty())) {
	                Particular usuarioFound = null;
	                for (Particular p : MainEleutradia.listaParticulares) {
	                	if (p.getDni().equalsIgnoreCase(dni) && p.getPassword().equals(password)) {
	                        usuarioFound = p;
	                        break;
	                    }
	                }
	
	                if (usuarioFound != null) {
	                    new VentanaPrincipal(usuarioFound);
	                    dispose(); // Cerrar la ventana de login
	                } else {
	                    JOptionPane.showMessageDialog(
	                    		VentanaInicial.this, 
	                    		"DNI o contraseña incorrectos.", 
	                    		"Error de Login.", 
	                    		JOptionPane.ERROR_MESSAGE);
	                }
                } else {
    				JOptionPane.showMessageDialog(
                    		VentanaInicial.this, 
                    		"Por favor, rellene ambos campos.", 
                    		"Campos incompletos", 
                    		JOptionPane.ERROR_MESSAGE);
    			}
		});
		
		botonLoginEmpresa.addActionListener(e -> {
            String nif = campoIdEmpresa.getText().trim();
            String password = new String(campoPasswordEmpresa.getPassword());
            
            if (!(nif.isBlank()) && !(password.isEmpty())) {
	            Empresa usuarioFound = null;
	            for (Empresa emp : MainEleutradia.listaEmpresas) {
	            	if (emp.getIdEmpresa().equalsIgnoreCase(nif) && emp.getPassword().equals(password)) {
	                    usuarioFound = emp;
	                    break;
	                }
	            }
	
	            if (usuarioFound != null) {
	                new VentanaPrincipal(usuarioFound);
	                dispose(); // Cerrar la ventana de login
	            } else {
	                JOptionPane.showMessageDialog(
	                		VentanaInicial.this, 
	                		"NIF o contraseña incorrectos.", 
	                		"Error de inicio de sesión", 
	                		JOptionPane.ERROR_MESSAGE);
	            }
			} else {
				JOptionPane.showMessageDialog(
                		VentanaInicial.this, 
                		"Por favor, rellene ambos campos.", 
                		"Campos incompletos", 
                		JOptionPane.ERROR_MESSAGE);
			}
	});
		
		botonRegistro.addActionListener(e -> {
			new VentanaRegistro();
		});
	}
}
