package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
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

public class VentanaLoginParticular extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JLabel labelTitulo, labelDni, labelPassword, labelRegistro;
	private JTextField textoDni;
	private JPasswordField textoPassword;
	private JButton botonLogin, botonRegister;
	private JLabel espacio, imageLabel;
    private ImageIcon originalIcon;
	
	private static final Color COLOR_FONDO_FORM = Color.WHITE;
    private static final Color COLOR_BOTON_LOGIN = new Color(0, 100, 255); // Azul
    private static final Color COLOR_TEXTO_ETIQUETA = new Color(100, 100, 100); // Gris
    private static final Font FONT_TITULO = new Font("Arial", Font.BOLD, 22);
    private static final Font FONT_ETIQUETA = new Font("Arial", Font.BOLD, 12);
    private static final Font FONT_CAMPO = new Font("Arial", Font.PLAIN, 14);
	
	public VentanaLoginParticular() {
		
		configurarVentana();
        crearYOrganizarPaneles();
        registrarActionListeners();
        
        this.setVisible(true);
	}
	
	private void configurarVentana() {
		
		this.setTitle("Login/Registro");
		this.setSize(800,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setMinimumSize(new Dimension(800, 600));
		
	}
	
	private void crearYOrganizarPaneles() {
		
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));

        JPanel panelFormulario = construirPanelFormulario();

        JPanel panelImagen = construirPanelImagen();

        mainPanel.add(panelFormulario);
        mainPanel.add(panelImagen);

        this.add(mainPanel, BorderLayout.CENTER);

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
        
        labelDni = new JLabel("DNI / NIF:");
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
            originalIcon = new ImageIcon(getClass().getResource("/imagenes/fondoLogin" + (random.nextInt(2)+1) + ".png"));
        } catch (Exception e) {
            System.err.println("No se pudo cargar la imagen");
            originalIcon = null;
        }
        
        // Tengo que ver como escalar la imagen
        imageLabel.setIcon(originalIcon);
        
        panel.add(imageLabel, BorderLayout.CENTER);
        return panel;
    }
	
	private void registrarActionListeners() {
		
		botonLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
                String dni = textoDni.getText().trim();
                String password = new String(textoPassword.getPassword());

                Particular usuarioFound = null;
                
                for (Particular p : MainEleutradia.listaParticulares) {
                	System.out.println(p);
                	if (p.getDni().equalsIgnoreCase(dni) && p.getPassword().equals(password)) {
                        usuarioFound = p;
                        break;
                    }
                }

                if (usuarioFound != null) {
                    new VentanaPrincipal(usuarioFound);
                    dispose(); // Cerrar la ventana de login
                } else {
                    JOptionPane.showMessageDialog(VentanaLoginParticular.this, 
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
