package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class VentanaLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private CardLayout layout;
	private JPanel contenedor;
	private JButton botonParticular = new JButton("Acceder como Particular");
	private JButton botonEmpresa = new JButton("Acceder como Empresa");
	private JLabel labelTitulo, labelDni, labelPassword, labelRegistro;
	private JTextField textoDni;
	private JPasswordField textoPassword;
	private JButton botonLogin, botonRegister;
	private JLabel espacio, imageLabel;
    private ImageIcon originalIcon;
	
	private static final Color COLOR_FONDO_FORM = Color.WHITE;					// Blanco
    private static final Color COLOR_BOTON_LOGIN = new Color(0, 100, 255); 		// Azul
    private static final Color COLOR_TEXTO_ETIQUETA = new Color(100, 100, 100); // Gris
    private static final Font FONT_TITULO = new Font("Serif", Font.BOLD, 30);
    private static final Font FONT_ETIQUETA = new Font("Serif", Font.BOLD, 12);
    private static final Font FONT_CAMPO = new Font("Serif", Font.PLAIN, 14);
	
	public VentanaLogin() {
		super("EleuTradia: Inicio");
		configurarVentana();
        crearYOrganizarPaneles();
        registrarActionListeners();
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
	
	private void crearYOrganizarPaneles() {
		JPanel panelBienvenida = construirPanelBienvenida();
	    JPanel panelLoginParticular = construirPanelLoginParticular();
	    JPanel panelLoginEmpresa = construirPanelLoginEmpresa();
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
		

		JLabel titulo = new JLabel("Bienvenido/a a EleuTradia", SwingConstants.CENTER);
		titulo.setFont(new Font("Serif", Font.BOLD, 28));
		titulo.setAlignmentX(CENTER_ALIGNMENT);
		panelIzdo.add(titulo);
		panelIzdo.add(Box.createVerticalStrut(50));
		
		botonParticular.setAlignmentX(CENTER_ALIGNMENT);
		botonEmpresa.setAlignmentX(Component.CENTER_ALIGNMENT);
	    panelIzdo.add(botonParticular);
	    panelIzdo.add(Box.createVerticalStrut(20));
	    panelIzdo.add(botonEmpresa);
		
		JPanel panelDcho = construirPanelImagen();
		panelDcho.setPreferredSize(new Dimension(this.getWidth()/2, this.getHeight()));
		
		mainPanel.add(panelIzdo);
		mainPanel.add(panelDcho);
		
		return mainPanel;
	}
	
	private JPanel construirPanelLoginParticular() {
		JPanel mainPanel = new JPanel(new GridLayout(1, 2));
		
	    JLabel label = new JLabel("Login de Particular - Introduzca sus datos");
	    label.setHorizontalAlignment(SwingConstants.CENTER);
	    label.setFont(new Font("Serif", Font.BOLD, 22));
	    mainPanel.add(label, BorderLayout.CENTER);
		
		JPanel panelFormulario = construirPanelFormulario();
		JPanel panelImagen = construirPanelImagen();
		mainPanel.add(panelFormulario);
		mainPanel.add(panelImagen);
		
		return mainPanel;
	}
	
	private JPanel construirPanelLoginEmpresa() {
		JPanel mainPanel = new JPanel(new GridLayout(1, 2));
		
		JLabel label = new JLabel("Login de Empresa - Introduzca los datos de la empresa");
	    label.setHorizontalAlignment(SwingConstants.CENTER);
	    label.setFont(new Font("Serif", Font.BOLD, 22));
	    mainPanel.add(label, BorderLayout.CENTER);
	    
	    JPanel panelFormulario = construirPanelFormulario();
		JPanel panelImagen = construirPanelImagen();
		mainPanel.add(panelFormulario);
		mainPanel.add(panelImagen);
	    
		return mainPanel;
	}
	
	private JPanel construirPanelFormulario() {
		
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_FONDO_FORM);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        labelTitulo = new JLabel("Accede a tu cuenta");
        labelTitulo.setFont(FONT_TITULO);
        labelTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(labelTitulo);

        espacio = new JLabel();
        espacio.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        panel.add(espacio);
        
        labelDni = new JLabel("DNI:");
        labelDni.setFont(FONT_ETIQUETA);
        labelDni.setForeground(COLOR_TEXTO_ETIQUETA);
        labelDni.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(labelDni);

        textoDni = new JTextField(20);
        textoDni.setFont(FONT_CAMPO);
        textoDni.setMaximumSize(new Dimension(500, 100)); // Tengo que ver mejor como funciona las dimensiones
        textoDni.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(textoDni);
        
        espacio = new JLabel();
        espacio.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        panel.add(espacio);

        labelPassword = new JLabel("Contraseña:");
        labelPassword.setFont(FONT_ETIQUETA);
        labelPassword.setForeground(COLOR_TEXTO_ETIQUETA);
        labelPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(labelPassword);

        textoPassword = new JPasswordField(20);
        textoPassword.setFont(FONT_CAMPO);
        textoPassword.setMaximumSize(new Dimension(350, 100));
        textoPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(textoPassword);
        
        espacio = new JLabel();
        espacio.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        panel.add(espacio);
        
        botonLogin = new JButton("Login");
        botonLogin.setFont(FONT_ETIQUETA);
        botonLogin.setBackground(COLOR_BOTON_LOGIN);
        botonLogin.setForeground(Color.WHITE);
        botonLogin.setFocusPainted(false);
        botonLogin.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        botonLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, botonLogin.getPreferredSize().height));
        botonLogin.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(botonLogin);

        espacio = new JLabel();
        espacio.setBorder(BorderFactory.createEmptyBorder(0, 0, 200, 0));
        panel.add(espacio);        

        JPanel panelRegistrarse = new JPanel(new FlowLayout());
        panelRegistrarse.setBackground(Color.WHITE);
        panelRegistrarse.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        labelRegistro = new JLabel("¿No tiene cuenta?");
        labelRegistro.setFont(FONT_ETIQUETA);
        labelRegistro.setForeground(COLOR_BOTON_LOGIN);
        labelRegistro.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelRegistrarse.add(labelRegistro);
        

        botonRegister = new JButton("Pulse aquí para registrarse");
        botonRegister.setFont(FONT_ETIQUETA);
        botonRegister.setBackground(Color.WHITE);
        botonRegister.setForeground(COLOR_BOTON_LOGIN);
        botonRegister.setFocusPainted(false);
        botonRegister.setBorder(null);
        botonRegister.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelRegistrarse.add(botonRegister);
        
        panel.add(panelRegistrarse);

        return panel;
    }
	
	private JPanel construirPanelImagen() {
        JPanel panel = new JPanel(new BorderLayout());
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
        panel.add(imageLabel, BorderLayout.CENTER);
        
        return panel;
    }
	
	private void registrarActionListeners() {
		
		botonParticular.addActionListener(e -> layout.show(contenedor, "loginParticular"));
		botonEmpresa.addActionListener(e -> layout.show(contenedor, "loginEmpresa"));
		
		botonLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
                String dni = textoDni.getText().trim();
                String password = new String(textoPassword.getPassword());

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
                    JOptionPane.showMessageDialog(VentanaLogin.this, 
                                                  "Email o contraseña incorrectos.", 
                                                  "Error de Login.", 
                                                  JOptionPane.ERROR_MESSAGE);
                }
            }
		});
		
		botonRegister.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				new VentanaRegistro();
			}
		});
	}
}
