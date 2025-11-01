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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import es.deusto.eleutradia.domain.Particular;
import es.deusto.eleutradia.main.MainEleutradia;

public class VentanaInicial extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private CardLayout layout;
	private JPanel contenedor;
	private JButton botonParticular = new JButton("Acceder como Particular");
	private JButton botonEmpresa = new JButton("Acceder como Empresa");
	private JLabel labelRegistro;
	private JTextField campoId;
	private JPasswordField campoPassword;
	private JButton botonLogin, botonRegistro, botonVolver;
	private JLabel imageLabel;
    private ImageIcon originalIcon;
	
    private static final Color COLOR_BOTON_LOGIN = new Color(0, 100, 255); 		// Azul
    private static final Color COLOR_TEXTO_ETIQUETA = new Color(100, 100, 100); // Gris
    private static final Font FONT_TITULO_BIENVENIDA = new Font("SansSerif", Font.BOLD, 18);
    private static final Font FONT_TITULO_LOGIN = new Font("Serif", Font.BOLD, 18);
    private static final Font FONT_ETIQUETA = new Font("Serif", Font.BOLD, 12);
    private static final Font FONT_CAMPO = new Font("Serif", Font.PLAIN, 14);
	
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
		
		JPanel panelIzdo = new JPanel();
		panelIzdo.setBackground(Color.WHITE);
		panelIzdo.setLayout(new BoxLayout(panelIzdo, BoxLayout.Y_AXIS));
		panelIzdo.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));

		JLabel titulo = new JLabel("Bienvenido/a a EleuTradia", JLabel.CENTER);
		titulo.setFont(FONT_TITULO_BIENVENIDA);
		titulo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		titulo.setMaximumSize(new Dimension(Integer.MAX_VALUE, titulo.getPreferredSize().height));
		panelIzdo.add(titulo);
		panelIzdo.add(Box.createVerticalStrut(50));
		
		botonParticular.setHorizontalAlignment(JButton.CENTER);
		botonEmpresa.setHorizontalAlignment(JButton.CENTER);
	    panelIzdo.add(botonParticular);
	    panelIzdo.add(Box.createVerticalStrut(20));
	    panelIzdo.add(botonEmpresa);
	    panelIzdo.add(Box.createVerticalStrut(160));
	    
        JPanel panelRegistro = new JPanel(new FlowLayout());
        panelRegistro.setBackground(Color.WHITE);
        panelRegistro.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        labelRegistro = new JLabel("¿No es cliente?");
        labelRegistro.setFont(FONT_ETIQUETA);
        labelRegistro.setForeground(COLOR_BOTON_LOGIN);
        labelRegistro.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelRegistro.add(labelRegistro);
        
        botonRegistro = new JButton("Pulse aquí para abrir una cuenta");
        botonRegistro.setFont(FONT_ETIQUETA);
        botonRegistro.setBackground(Color.WHITE);
        botonRegistro.setForeground(COLOR_BOTON_LOGIN);
        botonRegistro.setFocusPainted(false);
        botonRegistro.setBorder(null);
        botonRegistro.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelRegistro.add(botonRegistro);
        
        panelIzdo.add(panelRegistro);
		
		JPanel panelDcho = construirPanelImagen();
		panelDcho.setPreferredSize(new Dimension(this.getWidth()/2, this.getHeight()));
		
		mainPanel.add(panelIzdo);
		mainPanel.add(panelDcho);
		
		return mainPanel;
	}
	
	private JPanel construirPanelLogin(boolean esParticular) {
		JPanel mainPanel = new JPanel(new GridLayout(1, 2));
		
		String tipoUsuario = esParticular ? "Particular" : "Empresa";
		
        JPanel panelLogin = new JPanel();
        panelLogin.setLayout(new BoxLayout(panelLogin, BoxLayout.Y_AXIS));
        panelLogin.setBackground(Color.WHITE);
        panelLogin.setBorder(BorderFactory.createEmptyBorder(50, 60, 50, 60));
        
	    JLabel labelTituloLogin = new JLabel("Login de " + tipoUsuario, JLabel.CENTER);
	    labelTituloLogin.setFont(FONT_TITULO_LOGIN);
	    labelTituloLogin.setAlignmentX(JLabel.CENTER_ALIGNMENT);
	    panelLogin.add(labelTituloLogin);
	    panelLogin.add(Box.createVerticalStrut(10));
	    
	    JLabel labelSubtituloLogin = new JLabel("Introduzca sus datos", JLabel.CENTER);
	    labelSubtituloLogin.setFont(new Font("SansSerif", Font.PLAIN, 14));
	    labelSubtituloLogin.setForeground(COLOR_TEXTO_ETIQUETA);
	    labelSubtituloLogin.setAlignmentX(JLabel.CENTER_ALIGNMENT);
	    panelLogin.add(labelSubtituloLogin);
	    panelLogin.add(Box.createVerticalStrut(30));
        
        JLabel labelId = new JLabel(esParticular ? "DNI:" : "NIF:");
        labelId.setFont(FONT_ETIQUETA);
        labelId.setForeground(COLOR_TEXTO_ETIQUETA);
        labelId.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        panelLogin.add(labelId);
        panelLogin.add(Box.createVerticalStrut(20));

        campoId = new JTextField(20);
        campoId.setFont(FONT_CAMPO);
        campoId.setMaximumSize(new Dimension(350, 35));
        campoId.setPreferredSize(new Dimension(350, 35));
        panelLogin.add(campoId);
        panelLogin.add(Box.createVerticalStrut(20));
        
        JLabel labelPassword = new JLabel("Contraseña:");
        labelPassword.setFont(FONT_ETIQUETA);
        labelPassword.setForeground(COLOR_TEXTO_ETIQUETA);
        labelPassword.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        panelLogin.add(labelPassword);
        panelLogin.add(Box.createVerticalStrut(20));

        campoPassword = new JPasswordField(20);
        campoPassword.setFont(FONT_CAMPO);
        campoPassword.setMaximumSize(new Dimension(350, 35));
        campoPassword.setPreferredSize(new Dimension(350, 35));
        panelLogin.add(campoPassword);
        panelLogin.add(Box.createVerticalStrut(20));
        
        botonLogin = new JButton("Login");
        botonLogin.setFont(FONT_ETIQUETA);
        botonLogin.setBackground(COLOR_BOTON_LOGIN);
        botonLogin.setForeground(Color.WHITE);
        botonLogin.setFocusPainted(false);
        botonLogin.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        botonLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, botonLogin.getPreferredSize().height));
        panelLogin.add(botonLogin);

        JPanel panelDcho = construirPanelImagen();
        
        mainPanel.add(panelLogin);
        mainPanel.add(panelDcho);

        return mainPanel;
    }
	
	private JPanel construirPanelImagen() {
        JPanel panel = new JPanel(new GridLayout());
        panel.setBackground(Color.BLACK);

        imageLabel = new JLabel();
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
		
		botonParticular.addActionListener(e -> layout.show(contenedor, "loginParticular"));
		botonEmpresa.addActionListener(e -> layout.show(contenedor, "loginEmpresa"));
		
		botonLogin.addActionListener(e -> {
				
                String dni = campoId.getText().trim();
                String password = new String(campoPassword.getPassword());

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
                    		"Email o contraseña incorrectos.", 
                    		"Error de Login.", 
                    		JOptionPane.ERROR_MESSAGE);
                }
		});
		
		botonRegistro.addActionListener(e -> new VentanaRegistro());
	}
}
