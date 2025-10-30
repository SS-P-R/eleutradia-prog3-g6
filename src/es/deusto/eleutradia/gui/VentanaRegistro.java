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
import es.deusto.eleutradia.domain.Usuario;

public class VentanaRegistro extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JLabel labelNombre, labelTitulo, labelEmail, labelPassword, labelConfirmarPassword, labelRegistro;
	private JTextField textoNombre, textoEmail;
	private JPasswordField textoPassword, textoConfirmarPassword;
	private JButton botonCancelar, botonConfirmarRegistro;
	private JLabel espacio, imageLabel;
    private ImageIcon originalIcon;
    
    private JPanel mainPanel, panelFormulario, panelImagen;
	
	private static final Color COLOR_FONDO_FORM = Color.WHITE;
    private static final Color COLOR_BOTON_REGISTRO = new Color(0, 100, 255); // Azul
    private static final Color COLOR_TEXTO_ETIQUETA_BOTON_CANCELAR = new Color(100, 100, 100); // Gris
    private static final Font FONT_TITULO = new Font("Arial", Font.BOLD, 22);
    private static final Font FONT_ETIQUETA = new Font("Arial", Font.BOLD, 12);
    private static final Font FONT_CAMPO = new Font("Arial", Font.PLAIN, 14);
	
	public VentanaRegistro() {
		
		configurarVentana();
        crearYOrganizarPaneles();
        registrarActionListeners();
        
        this.setVisible(true);
	}
	
	private void configurarVentana() {
		
		this.setTitle("Registro de Nuevo Usuario");
		this.setSize(800,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setMinimumSize(new Dimension(800, 600));
		
	}
	
	private void crearYOrganizarPaneles() {
		
        mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.setBackground(COLOR_FONDO_FORM);
        
        panelFormulario = construirPanelFormulario();
        mainPanel.add(panelFormulario, BorderLayout.CENTER);

        panelImagen = construirPanelImagen();

        mainPanel.add(panelFormulario);
        mainPanel.add(panelImagen);

        this.add(mainPanel, BorderLayout.CENTER);

	}
	
	private JPanel construirPanelFormulario() {
		
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_FONDO_FORM);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        labelTitulo = new JLabel("Crea tu cuenta");
        labelTitulo.setFont(FONT_TITULO);
        labelTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(labelTitulo);

        espacio = new JLabel();
        espacio.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        panel.add(espacio);
        
        labelNombre = new JLabel("Nombre Completo");
        labelNombre.setFont(FONT_ETIQUETA);
        labelNombre.setForeground(COLOR_TEXTO_ETIQUETA_BOTON_CANCELAR);
        labelNombre.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(labelNombre);
        
        textoNombre = new JTextField(20);
        textoNombre.setFont(FONT_CAMPO);
        textoNombre.setMaximumSize(new Dimension(500, 100)); // Tengo que ver mejor como funciona las dimensiones
        textoNombre.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(textoNombre);
        
        espacio = new JLabel();
        espacio.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        panel.add(espacio);
        
        labelEmail = new JLabel("E-mail");
        labelEmail.setFont(FONT_ETIQUETA);
        labelEmail.setForeground(COLOR_TEXTO_ETIQUETA_BOTON_CANCELAR);
        labelEmail.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(labelEmail);

        textoEmail = new JTextField(20);
        textoEmail.setFont(FONT_CAMPO);
        textoEmail.setMaximumSize(new Dimension(500, 100)); // Tengo que ver mejor como funciona las dimensiones
        textoEmail.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(textoEmail);
        
        espacio = new JLabel();
        espacio.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        panel.add(espacio);

        labelPassword = new JLabel("Contraseña");
        labelPassword.setFont(FONT_ETIQUETA);
        labelPassword.setForeground(COLOR_TEXTO_ETIQUETA_BOTON_CANCELAR);
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
        
        labelConfirmarPassword = new JLabel("Confirmar Contraseña");
        labelConfirmarPassword.setFont(FONT_ETIQUETA);
        labelConfirmarPassword.setForeground(COLOR_TEXTO_ETIQUETA_BOTON_CANCELAR);
        labelConfirmarPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(labelConfirmarPassword);

        textoConfirmarPassword = new JPasswordField(20);
        textoConfirmarPassword.setFont(FONT_CAMPO);
        textoConfirmarPassword.setMaximumSize(new Dimension(350, 100));
        textoConfirmarPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(textoConfirmarPassword);
        
        espacio = new JLabel();
        espacio.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        panel.add(espacio);

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(Color.WHITE);
        panelBotones.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        botonCancelar = new JButton("Cancelar");
        botonCancelar.setFont(FONT_ETIQUETA);
        botonCancelar.setBackground(COLOR_TEXTO_ETIQUETA_BOTON_CANCELAR);
        botonCancelar.setForeground(Color.WHITE);
        botonCancelar.setFocusPainted(false);
        botonCancelar.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        botonCancelar.setPreferredSize(new Dimension(150, 50));
        botonCancelar.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        botonConfirmarRegistro = new JButton("Confirmar");
        botonConfirmarRegistro.setFont(FONT_ETIQUETA);
        botonConfirmarRegistro.setBackground(COLOR_BOTON_REGISTRO);
        botonConfirmarRegistro.setForeground(Color.WHITE);
        botonConfirmarRegistro.setFocusPainted(false);
        botonConfirmarRegistro.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        botonConfirmarRegistro.setPreferredSize(new Dimension(150, 50));
        botonConfirmarRegistro.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelBotones.add(botonCancelar);
        panelBotones.add(botonConfirmarRegistro);
        
        panel.add(panelBotones);

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
        
        imageLabel.setIcon(originalIcon);
        
        panel.add(imageLabel, BorderLayout.CENTER);
        return panel;
    }
	
	private void registrarActionListeners() {
		
		botonCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
                dispose(); // Cerrar la ventana de que se ha abierto de registro y ahce que vuelva a la ventana de login
            }
		});	
		
		botonConfirmarRegistro.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String nombre = textoNombre.getText();
				String email = textoEmail.getText();
				String password = new String(textoPassword.getPassword());
				String passwordConfirmar = new String(textoConfirmarPassword.getPassword());
				
				if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
					JOptionPane.showMessageDialog(VentanaRegistro.this, 
													"Todos los campos son obligatorios",
													"Error de Registro",
													JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				if (!password.equals(passwordConfirmar)) {
					JOptionPane.showMessageDialog(VentanaRegistro.this, 
													"Las contraseñas no coinciden",
													"Error de Registro",
													JOptionPane.WARNING_MESSAGE);
					return;
				}
				
			}
		});
	}
}
