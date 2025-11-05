package es.deusto.eleutradia.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.JTextComponent;

import es.deusto.eleutradia.domain.Empresa;
import es.deusto.eleutradia.domain.Particular;
import es.deusto.eleutradia.main.MainEleutradia;

public class VentanaInicial extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private CardLayout layout;
	private JPanel contenedor;
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
		this.generarImagenRandom();
        this.inicializarPaneles();
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
		// Construimos los diferentes paneles y
		// los añadimos como opciones ("cartas") al contenedor
		JPanel panelBienvenida = construirPanelBienvenida();
	    JPanel panelLoginParticular = construirPanelLogin(true);
	    JPanel panelLoginEmpresa = construirPanelLogin(false);
	    JPanel panelReg = construirPanelRegistro(true);
	    
	    contenedor.add(panelBienvenida, "bienvenida");
	    contenedor.add(panelLoginParticular, "loginParticular");
	    contenedor.add(panelLoginEmpresa, "loginEmpresa");
	    contenedor.add(panelReg, "registro");
	    
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
		
		JButton botonParticular = new JButton("Acceder como Particular");
		botonParticular.setAlignmentX(JButton.CENTER_ALIGNMENT);
		botonParticular.setMaximumSize(new Dimension(220, 40));
		botonParticular.setBackground(MY_AZUL);
		botonParticular.setForeground(Color.WHITE);
		botonParticular.setFocusPainted(false);
		panelDcho.add(botonParticular);
		botonParticular.addActionListener(e -> {
			layout.show(contenedor, "loginParticular");
			setTitle("EleuTradia: Iniciar sesión");
		});
		panelDcho.add(Box.createVerticalStrut(20));
		
		JButton botonEmpresa = new JButton("Acceder como Empresa");
		botonEmpresa.setAlignmentX(JButton.CENTER_ALIGNMENT);
		botonEmpresa.setMaximumSize(new Dimension(220, 40));
		botonEmpresa.setBackground(MY_AZUL);
		botonEmpresa.setForeground(Color.WHITE);
		botonEmpresa.setFocusPainted(false);
		panelDcho.add(botonEmpresa);
		botonEmpresa.addActionListener(e -> {
			layout.show(contenedor, "loginEmpresa");
			setTitle("EleuTradia: Iniciar sesión");
		});
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
        
        JButton botonRegistro = new JButton("Pulse aquí para abrir una cuenta");
        botonRegistro.setFont(FONT_NORMAL);
        botonRegistro.setBackground(Color.WHITE);
        botonRegistro.setForeground(MY_AZUL);
        botonRegistro.setFocusPainted(false);
        botonRegistro.setBorder(null);
        botonRegistro.setAlignmentX(JButton.CENTER_ALIGNMENT);
        panelRegistro.add(botonRegistro);
        botonRegistro.addActionListener(e -> {
			layout.show(contenedor, "registro");
			setTitle("EleuTradia: Registro");
        });
        
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
	    panelAcceso.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        
	    // Campo de identificador
        JLabel labelId = new JLabel(esParticular ? "DNI:" : "NIF:");
        panelAcceso.add(crearLabel(labelId));
        panelAcceso.add(Box.createVerticalStrut(10));

        JTextField campoId = (JTextField) crearCampo(false);
        panelAcceso.add(campoId);
        panelAcceso.add(Box.createVerticalStrut(20));
        
        // Campo de contraseña
        panelAcceso.add(crearLabel(new JLabel("Contraseña:")));
        panelAcceso.add(Box.createVerticalStrut(10));

        JPasswordField campoPassword = (JPasswordField) crearCampo(true);
        panelAcceso.add(campoPassword);
        panelAcceso.add(Box.createVerticalStrut(40));
        
        // Key Listener
    	KeyListener myKeyListener = new KeyListener() {
    		@Override
    		public void keyTyped(KeyEvent e) {
    		}

    		@Override
    		public void keyPressed(KeyEvent e) {
    			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
    				procesarLogin(esParticular, campoId, campoPassword);
    			}
    		}

    		@Override
    		public void keyReleased(KeyEvent e) {
    		}
    	};
    	campoId.addKeyListener(myKeyListener);
    	campoPassword.addKeyListener(myKeyListener);
        
        // Botones
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
        panelBotones.add(botonVolver);
        botonVolver.addActionListener(e -> {
            layout.show(contenedor, "bienvenida");
            setTitle("EleuTradia: Inicio");
        });
        
        JButton botonLogin = new JButton("Iniciar sesión");
        botonLogin.setFont(FONT_NORMAL);
        botonLogin.setBackground(MY_AZUL);
        botonLogin.setForeground(Color.WHITE);
        botonLogin.setFocusPainted(false);
        panelBotones.add(botonLogin);
        botonLogin.addActionListener(e -> procesarLogin(esParticular, campoId, campoPassword));
        
        panelAcceso.add(panelBotones);
        
        panelDcho.add(panelAcceso);

        JPanel panelIzdo = construirPanelImagen();
        
        mainPanel.add(panelIzdo);
        mainPanel.add(panelDcho);

        return mainPanel;
    }
	
	private JPanel construirPanelRegistro(boolean esParticular) {
		JPanel mainPanel = new JPanel(new GridLayout(1, 2));
		
		JPanel panelDcho = new JPanel();
        panelDcho.setLayout(new BoxLayout(panelDcho, BoxLayout.Y_AXIS));
        panelDcho.setBackground(Color.WHITE);
        panelDcho.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 20));
        
        JLabel tituloReg = new JLabel("Abra su cuenta:");
        tituloReg.setFont(FONT_TITULO);
        tituloReg.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        tituloReg.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panelDcho.add(tituloReg);
        
	    JPanel panelRegistro = new JPanel();
	    panelRegistro.setLayout(new BoxLayout(panelRegistro, BoxLayout.Y_AXIS));
	    panelRegistro.setBackground(Color.WHITE);
	    panelRegistro.setAlignmentX(JPanel.CENTER_ALIGNMENT);

	    // Selector de tipo de usuario
        JPanel panelTipoUsuario = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTipoUsuario.setBackground(Color.WHITE);
        panelTipoUsuario.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        panelTipoUsuario.setMaximumSize(new Dimension(300, 250));
        panelTipoUsuario.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));     
        
        JRadioButton radioParticular = new JRadioButton("Particular", true);
        radioParticular.setBackground(Color.WHITE);
        JRadioButton radioEmpresa = new JRadioButton("Empresa");
        radioEmpresa.setBackground(Color.WHITE);
        ButtonGroup grupoTipoUsuario = new ButtonGroup();
        grupoTipoUsuario.add(radioParticular);
        grupoTipoUsuario.add(radioEmpresa);
        
        panelTipoUsuario.add(crearLabel(new JLabel("Tipo de cuenta:")));
        panelTipoUsuario.add(radioParticular);
        panelTipoUsuario.add(radioEmpresa);
        
        panelRegistro.add(panelTipoUsuario);
        panelRegistro.add(Box.createVerticalStrut(10));
        
        // Campo de identificador
        JLabel labelRegId = new JLabel(esParticular ? "DNI:" : "NIF:");
        panelRegistro.add(crearLabel(labelRegId));
        panelRegistro.add(Box.createVerticalStrut(10));
        
        JTextField campoRegId = (JTextField) crearCampo(false);
        panelRegistro.add(campoRegId);
        panelRegistro.add(Box.createVerticalStrut(20));
        
        // Campo de nombre
        JLabel labelRegNombre = new JLabel(esParticular ? "Nombre completo:" : "Nombre de la empresa:");
        panelRegistro.add(crearLabel(labelRegNombre));
        panelRegistro.add(Box.createVerticalStrut(10));
        
        JTextField campoRegNombre = (JTextField) crearCampo(false);
        panelRegistro.add(campoRegNombre);
        panelRegistro.add(Box.createVerticalStrut(20));
        
        // Action Listeners del selector
        radioParticular.addActionListener(e -> {
        	labelRegId.setText("DNI:");
            labelRegNombre.setText("Nombre completo:");
        });
        radioEmpresa.addActionListener(e -> {
        	labelRegId.setText("NIF:");
            labelRegNombre.setText("Nombre de la empresa:");
        });
        
        // Campo de e-mail
        panelRegistro.add(crearLabel(new JLabel("E-mail:")));
        panelRegistro.add(Box.createVerticalStrut(10));
        
        JTextField campoRegEmail = (JTextField) crearCampo(false);
        panelRegistro.add(campoRegEmail);
        panelRegistro.add(Box.createVerticalStrut(20));
        
        // Campo de teléfono
        panelRegistro.add(crearLabel(new JLabel(esParticular ? "Teléfono:" : "Teléfono de empresa")));
        panelRegistro.add(Box.createVerticalStrut(10));
        
        JTextField campoRegTlf = (JTextField) crearCampo(false);
        panelRegistro.add(campoRegTlf);
        panelRegistro.add(Box.createVerticalStrut(20));
        
        // Campo de contraseña
        panelRegistro.add(crearLabel(new JLabel("Contraseña:")));
        panelRegistro.add(Box.createVerticalStrut(10));
        
        JPasswordField campoRegPassword = (JPasswordField) crearCampo(true);
        panelRegistro.add(campoRegPassword);
        panelRegistro.add(Box.createVerticalStrut(20));
        
        // Campo de confirmación de contraseña
        panelRegistro.add(crearLabel(new JLabel("Confirme la contraseña:")));
        panelRegistro.add(Box.createVerticalStrut(10));
        
        JPasswordField campoRegConfirmPassword = (JPasswordField) crearCampo(true);
        panelRegistro.add(campoRegConfirmPassword);
        panelRegistro.add(Box.createVerticalStrut(20));

        // Botones
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 0));;
        panelBotones.setBackground(Color.WHITE);
        panelBotones.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        panelBotones.setMaximumSize(new Dimension(300, 25));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.setFont(FONT_NORMAL);
        botonCancelar.setBackground(MY_GRIS);
        botonCancelar.setForeground(Color.WHITE);
        botonCancelar.setFocusPainted(false);
        botonCancelar.setAlignmentX(JButton.LEFT_ALIGNMENT);
        panelBotones.add(botonCancelar);
		botonCancelar.addActionListener(e -> {
			layout.show(contenedor, "bienvenida");
			setTitle("EleuTradia: Inicio");
		});
        
        JButton botonReg = new JButton("Confirmar");
        botonReg.setFont(FONT_NORMAL);
        botonReg.setBackground(MY_AZUL);
        botonReg.setForeground(Color.WHITE);
        botonReg.setFocusPainted(false);
        botonReg.setAlignmentX(JButton.LEFT_ALIGNMENT);
        panelBotones.add(botonReg);
        botonReg.addActionListener(e -> {
        	
            String id = campoRegId.getText().trim();
            String nombre = campoRegNombre.getText().trim();
            String email = campoRegEmail.getText().trim();
            String tlf = campoRegTlf.getText().trim();
            String pass = new String(campoRegPassword.getPassword());
            String conf = new String(campoRegConfirmPassword.getPassword());

            if (id.isEmpty() || nombre.isEmpty() || email.isEmpty() || tlf.isEmpty() || pass.isEmpty() || conf.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Rellene todos los campos.", "Campos incompletos", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!pass.equals(conf)) {
                JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Campos erróneos", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (esParticular) {
                MainEleutradia.listaParticulares.add(new Particular(id, nombre, null, null, email, pass, tlf, "", null, null, null, null));
            } else {
                MainEleutradia.listaEmpresas.add(new Empresa(id, nombre, email, pass, tlf, "", null, null, null));
            }
            
            JOptionPane.showMessageDialog(this, "Usuario registrado correctamente.");
            layout.show(contenedor, "bienvenida");
            setTitle("EleuTradia: Inicio");
        });
        
        panelRegistro.add(panelBotones);
        
        JScrollPane scrollRegistro = new JScrollPane(panelRegistro);
        scrollRegistro.setBorder(null); // elimina el borde del scroll
        scrollRegistro.setBackground(Color.WHITE);
        scrollRegistro.getVerticalScrollBar().setUnitIncrement(16); // suaviza el scroll
        scrollRegistro.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panelDcho.add(scrollRegistro);
        
        JPanel panelIzdo = construirPanelImagen();
        
        mainPanel.add(panelIzdo);
        mainPanel.add(panelDcho);
		
		return mainPanel;
	}
	
	private JPanel construirPanelImagen() {
        JPanel panel = new JPanel(new GridLayout());
        panel.setBackground(Color.BLACK);

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setIcon(originalIcon);
        panel.add(imageLabel);
        
        return panel;
	}
	
	private void generarImagenRandom() {
		try {
        	Random random = new Random();
            ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/fondoLogin" + (random.nextInt(2)+1) + ".png"));
            Image imagen = icon.getImage().getScaledInstance(this.getWidth()/2, this.getHeight(), java.awt.Image.SCALE_SMOOTH);
            originalIcon = new ImageIcon(imagen);
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen");
            originalIcon = null;
        }
	}
	
	private JLabel crearLabel(JLabel myLabel) {
        myLabel.setFont(FONT_NORMAL);
        myLabel.setForeground(MY_GRIS);
        myLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        return myLabel;
	}
	
	private JTextComponent crearCampo(boolean oculto) {
	    JTextComponent campo = oculto ? new JPasswordField(20) : new JTextField(20);
	    campo.setFont(FONT_CAMPO);
	    campo.setMaximumSize(new Dimension(300, 25));
	    campo.setPreferredSize(new Dimension(300, 25));
	    campo.setAlignmentX(LEFT_ALIGNMENT);
	    return campo;
	}
	
	private void procesarLogin(boolean esParticular, JTextField campoId, JPasswordField campoPassword) {
	    String id = campoId.getText().trim();
	    String password = new String(campoPassword.getPassword());

	    if (id.isBlank() || password.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Por favor, rellene ambos campos.", "Campos incompletos", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    if (esParticular) {
	        for (Particular p : MainEleutradia.listaParticulares) {
	            if (p.getDni().equalsIgnoreCase(id) && p.getPassword().equals(password)) {
	                new VentanaPrincipal(p);
	                dispose();
	                return;
	            }
	        }
	        JOptionPane.showMessageDialog(this, "DNI o contraseña incorrectos.", "Error de Login.", JOptionPane.ERROR_MESSAGE);
	    } else {
	        for (Empresa e : MainEleutradia.listaEmpresas) {
	            if (e.getNif().equalsIgnoreCase(id) && e.getPassword().equals(password)) {
	                new VentanaPrincipal(e);
	                dispose();
	                return;
	            }
	        }
	        JOptionPane.showMessageDialog(this, "NIF o contraseña incorrectos.", "Error de Login.", JOptionPane.ERROR_MESSAGE);
	    }
	}
}
