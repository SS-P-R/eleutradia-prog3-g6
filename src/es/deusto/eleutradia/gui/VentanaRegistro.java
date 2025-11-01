package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
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

import es.deusto.eleutradia.domain.Curso;
import es.deusto.eleutradia.domain.Empresa;
import es.deusto.eleutradia.domain.Pais;
import es.deusto.eleutradia.domain.Particular;
import es.deusto.eleutradia.domain.Usuario;
import es.deusto.eleutradia.main.MainEleutradia;

public class VentanaRegistro extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JLabel labelNombre, labelTitulo, labelEmail, labelPassword, labelConfirmarPassword, labelRegistro;
	private JTextField textoNombre, textoEmail;
	private JPasswordField textoPassword, textoConfirmarPassword;
	private JButton botonCancelar, botonConfirmarRegistro;
	
	private JLabel labelTipo, labelDNI, labelNIF;
	private JRadioButton radioParticular, radioEmpresa;
	private ButtonGroup grupoTipoUsuario;
	private JTextField textoDNI, textoNIF;
	private JPanel panelCamposParticular, panelCamposEmpresa;
	
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
        
        panelCamposParticular.setVisible(true);
        panelCamposEmpresa.setVisible(false);
        
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
        labelTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panel.add(labelTitulo);

        JPanel panelTipoUsuario = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTipoUsuario.setBackground(COLOR_FONDO_FORM);
        panelTipoUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelTipoUsuario.setMaximumSize(new Dimension(300,250));
        panelTipoUsuario.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        labelTipo = new JLabel("Tipo de cuenta:");
        labelTipo.setFont(FONT_ETIQUETA);
        labelTipo.setForeground(COLOR_TEXTO_ETIQUETA_BOTON_CANCELAR);        
        
        radioParticular = new JRadioButton("Particular");
        radioParticular.setBackground(COLOR_FONDO_FORM);
        radioParticular.setSelected(true);
        
        radioEmpresa = new JRadioButton("Empresa");
        radioEmpresa.setBackground(COLOR_FONDO_FORM);
        
        grupoTipoUsuario = new ButtonGroup();
        grupoTipoUsuario.add(radioParticular);
        grupoTipoUsuario.add(radioEmpresa);
        
        panelTipoUsuario.add(labelTipo);
        panelTipoUsuario.add(radioParticular);
        panelTipoUsuario.add(radioEmpresa);
        
        panel.add(panelTipoUsuario);
        
        panel.add(panelCamposParticular = crearCampoFormulario(labelDNI = new JLabel("DNI"),textoDNI = new JTextField(20)));
        panel.add(panelCamposEmpresa = crearCampoFormulario(labelNIF= new JLabel("NIF"),textoNIF = new JTextField(20)));
        
        panel.add(crearCampoFormulario(labelNombre = new JLabel("Nombre Completo"),textoNombre = new JTextField(15)));
        panel.add(crearCampoFormulario(labelEmail = new JLabel("E-mail"),textoEmail = new JTextField(15)));
        panel.add(crearCampoFormulario(labelPassword = new JLabel("Contraseña"),textoPassword = new JPasswordField(15)));
        panel.add(crearCampoFormulario(labelConfirmarPassword = new JLabel("Confirmar Contraseña"),textoConfirmarPassword = new JPasswordField(15)));

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(Color.WHITE);
        panelBotones.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
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
	
	private JPanel crearCampoFormulario(JLabel labelCampo, Component textoCampo) {

		JPanel panelCampo = new JPanel(new GridLayout(2, 1));
		panelCampo.setMaximumSize(new Dimension(350, 250));
		panelCampo.setBackground(COLOR_FONDO_FORM);
		panelCampo.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelCampo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		
		labelCampo.setFont(FONT_ETIQUETA);
		labelCampo.setForeground(COLOR_TEXTO_ETIQUETA_BOTON_CANCELAR);
		labelCampo.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		textoCampo.setFont(FONT_CAMPO);
        
        panelCampo.add(labelCampo);
        panelCampo.add(textoCampo);
        
		return panelCampo;
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
		
		ActionListener radioListener= new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if (radioParticular.isSelected()) {
					panelCamposParticular.setVisible(true);
					panelCamposEmpresa.setVisible(false);
				} else {
					panelCamposParticular.setVisible(false);
					panelCamposEmpresa.setVisible(true);
				}

			}
		};
		
		radioParticular.addActionListener(radioListener);
		radioEmpresa.addActionListener(radioListener);
		
		botonCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
                dispose(); // Cerrar la ventana de que se ha abierto de registro y ahce que vuelva a la ventana de login
            }
		});	
		
		botonConfirmarRegistro.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				registrarUsuario();
			}
		});
	}
	
	public void registrarUsuario() {
		
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
		
        Usuario nuevoUsuario = null;

        try {
			if (radioParticular.isSelected()) {
				Particular p = new Particular(textoDNI.getText(), nombre, null, null, null);
				p.setEmail(email);
				p.setPassword(password);
				nuevoUsuario = p;
				
		        MainEleutradia.listaParticulares.add((Particular)nuevoUsuario);
				
			} else {
				
				Empresa e = new Empresa(null, nombre, textoNIF.getText());
				e.setEmail(email);
				e.setPassword(password);
				nuevoUsuario = e;
				
		        MainEleutradia.listaEmpresas.add((Empresa)nuevoUsuario);
			}
			
	        JOptionPane.showMessageDialog(VentanaRegistro.this,
							                "¡Registro (simulado) exitoso!\nNombre: " + nombre,
							                "Registro Completado",
							                JOptionPane.INFORMATION_MESSAGE);
	        
	        dispose();
				
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,
											"Error al crear usuario" + e,
											"Error",
											JOptionPane.ERROR_MESSAGE);
		}
	}
}
