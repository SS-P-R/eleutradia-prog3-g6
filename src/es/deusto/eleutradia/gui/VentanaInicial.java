package es.deusto.eleutradia.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

import es.deusto.eleutradia.db.EleutradiaDBManager;
import es.deusto.eleutradia.domain.Empresa;
import es.deusto.eleutradia.domain.Particular;
import es.deusto.eleutradia.gui.style.UITema;
import es.deusto.eleutradia.main.MainEleutradia;

import static es.deusto.eleutradia.gui.style.UITema.*;

public class VentanaInicial extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private EleutradiaDBManager dbManager;
	
	private CardLayout layout;
	private JPanel contenedor;
    private ImageIcon originalIcon;
    
    // Expresiones para validar identificadores
    //IAG (ChatGPT)
    //SIN MODIFICAR
    private static final String DNI_REGEX = "^[0-9]{8}[A-Za-z]$";
    private static final String NIF_REGEX = "^[ABCDEFGHJNPQRSUVW]\\d{7}[0-9A-Z]$";
    private static final String NOMBRE_REGEX = "[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String TELEFONO_REGEX = "^\\d{9}$";
    //END IAG
    
    // Campos temporales para el registro multipaso
    private String tempId, tempNombre, tempEmail, tempTlf, tempPass;
    private boolean tempEsParticular;
	
	public VentanaInicial() {
		super("EleuTradia: Inicio");
		this.dbManager = MainEleutradia.getDBManager();
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
	    JPanel panelReg1 = construirPanelRegistro1();
	    JPanel panelReg2 = construirPanelRegistro2();
	    
	    contenedor.add(panelBienvenida, "bienvenida");
	    contenedor.add(panelLoginParticular, "loginParticular");
	    contenedor.add(panelLoginEmpresa, "loginEmpresa");
	    contenedor.add(panelReg1, "registro1");
	    contenedor.add(panelReg2, "registro2");
	    
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
		titulo.setFont(TITULO_GRANDE);
		titulo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		titulo.setMaximumSize(new Dimension(Integer.MAX_VALUE, titulo.getPreferredSize().height));
		panelDcho.add(titulo);
		panelDcho.add(Box.createVerticalStrut(10));
		
		JLabel subtitulo = new JLabel("Seleccione su método de acceso:", JLabel.CENTER);
		subtitulo.setFont(SUBTITULO_GRANDE);
		subtitulo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		subtitulo.setMaximumSize(new Dimension(Integer.MAX_VALUE, subtitulo.getPreferredSize().height));
		panelDcho.add(subtitulo);
		panelDcho.add(Box.createVerticalStrut(90));
		
		JButton botonParticular = new JButton("Acceder como Particular");
		botonParticular.setAlignmentX(JButton.CENTER_ALIGNMENT);
		botonParticular.setMaximumSize(new Dimension(220, 40));
		botonParticular.setBackground(AZUL_CLARO);
		botonParticular.setForeground(Color.WHITE);
        botonParticular.setBorderPainted(false);
        botonParticular.setContentAreaFilled(false);
        botonParticular.setOpaque(true);
		botonParticular.setFocusPainted(false);
		panelDcho.add(botonParticular);
		botonParticular.addActionListener(e -> {
			layout.show(contenedor, "loginParticular");
			setTitle("EleuTradia: Iniciar sesión");
		});
		botonParticular.addMouseListener(myAdapterAzul);
		panelDcho.add(Box.createVerticalStrut(20));
		
		JButton botonEmpresa = new JButton("Acceder como Empresa");
		botonEmpresa.setAlignmentX(JButton.CENTER_ALIGNMENT);
		botonEmpresa.setMaximumSize(new Dimension(220, 40));
		botonEmpresa.setBackground(AZUL_CLARO);
		botonEmpresa.setForeground(Color.WHITE);
        botonEmpresa.setBorderPainted(false);
        botonEmpresa.setContentAreaFilled(false);
        botonEmpresa.setOpaque(true);
		botonEmpresa.setFocusPainted(false);
		panelDcho.add(botonEmpresa);
		botonEmpresa.addActionListener(e -> {
			layout.show(contenedor, "loginEmpresa");
			setTitle("EleuTradia: Iniciar sesión");
		});
		botonEmpresa.addMouseListener(myAdapterAzul);
		panelDcho.add(Box.createVerticalStrut(120));
	    
        JPanel panelRegistro = new JPanel();
        panelRegistro.setLayout(new BoxLayout(panelRegistro, BoxLayout.Y_AXIS));
        panelRegistro.setBackground(Color.WHITE);
        panelRegistro.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        
        JLabel labelRegistro = new JLabel("¿No es cliente?");
        labelRegistro.setFont(SUBTITULO_PEQUENO);
        labelRegistro.setForeground(AZUL_CLARO);
        labelRegistro.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panelRegistro.add(labelRegistro);
        
        JButton botonRegistro = new JButton("Pulse aquí para abrir una cuenta");
        botonRegistro.setFont(SUBTITULO_PEQUENO);
        botonRegistro.setBackground(Color.WHITE);
        botonRegistro.setForeground(AZUL_CLARO);
        botonRegistro.setBorderPainted(false);
        botonRegistro.setContentAreaFilled(false);
        botonRegistro.setOpaque(true);
        botonRegistro.setFocusPainted(false);
        botonRegistro.setAlignmentX(JButton.CENTER_ALIGNMENT);
        panelRegistro.add(botonRegistro);
        botonRegistro.addActionListener(e -> {
			layout.show(contenedor, "registro1");
			setTitle("EleuTradia: Registro");
        });
        botonRegistro.addMouseListener(myAdapterRegistro);
        
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
	    tituloLogin.setFont(TITULO_GRANDE);
	    tituloLogin.setAlignmentX(JLabel.CENTER_ALIGNMENT);
	    panelDcho.add(tituloLogin);
	    panelDcho.add(Box.createVerticalStrut(10));
	    
	    JLabel subtituloLogin = new JLabel("Introduzca sus datos:", JLabel.CENTER);
	    subtituloLogin.setFont(SUBTITULO_GRANDE);
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
    	KeyAdapter myKeyListener = new KeyAdapter() {
    		@Override
    		public void keyPressed(KeyEvent e) {
    			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
    				procesarLogin(esParticular, campoId, campoPassword);
    			}
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
        botonVolver.setFont(SUBTITULO_PEQUENO);
        botonVolver.setBackground(GRIS_MEDIO);
        botonVolver.setForeground(Color.WHITE);
        botonVolver.setBorderPainted(false);
        botonVolver.setContentAreaFilled(false);
        botonVolver.setOpaque(true);
        botonVolver.setFocusPainted(false);
        panelBotones.add(botonVolver);
        botonVolver.addActionListener(e -> {
            layout.show(contenedor, "bienvenida");
            setTitle("EleuTradia: Inicio");
        });
        botonVolver.addMouseListener(myAdapterGris);
        
        JButton botonLogin = new JButton("Iniciar sesión");
        botonLogin.setFont(SUBTITULO_PEQUENO);
        botonLogin.setBackground(AZUL_CLARO);
        botonLogin.setForeground(Color.WHITE);
        botonLogin.setBorderPainted(false);
        botonLogin.setContentAreaFilled(false);
        botonLogin.setOpaque(true);
        botonLogin.setFocusPainted(false);
        panelBotones.add(botonLogin);
        botonLogin.addActionListener(e -> procesarLogin(esParticular, campoId, campoPassword));
        botonLogin.addMouseListener(myAdapterAzul);
        
        panelAcceso.add(panelBotones);
        
        panelDcho.add(panelAcceso);

        JPanel panelIzdo = construirPanelImagen();
        
        mainPanel.add(panelIzdo);
        mainPanel.add(panelDcho);

        return mainPanel;
    }
	
	private JPanel construirPanelRegistro1() {
		JPanel mainPanel = new JPanel(new GridLayout(1, 2));
		
		JPanel panelDcho = new JPanel();
        panelDcho.setLayout(new BoxLayout(panelDcho, BoxLayout.Y_AXIS));
        panelDcho.setBackground(Color.WHITE);
        panelDcho.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 20));
        
        JLabel tituloReg = new JLabel("Abra su cuenta (1/2):");
        tituloReg.setFont(TITULO_GRANDE);
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
        JRadioButton radioEmpresa = new JRadioButton("Empresa");
        UITema.personalizarRadioButton(radioParticular);
        UITema.personalizarRadioButton(radioEmpresa);
        
        ButtonGroup grupoTipoUsuario = new ButtonGroup();
        grupoTipoUsuario.add(radioParticular);
        grupoTipoUsuario.add(radioEmpresa);
        
        panelTipoUsuario.add(crearLabel(new JLabel("Tipo de cuenta:")));
        panelTipoUsuario.add(radioParticular);
        panelTipoUsuario.add(radioEmpresa);
        
        panelRegistro.add(panelTipoUsuario);
        panelRegistro.add(Box.createVerticalStrut(10));
        
        // Campo de identificador
        JLabel labelRegId = new JLabel("DNI:");
        panelRegistro.add(crearLabel(labelRegId));
        panelRegistro.add(Box.createVerticalStrut(10));
        
        JTextField campoRegId = (JTextField) crearCampo(false);
        panelRegistro.add(campoRegId);
        panelRegistro.add(Box.createVerticalStrut(20));
        
        // Campo de nombre
        JLabel labelRegNombre = new JLabel("Nombre completo:");
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
        panelRegistro.add(crearLabel(new JLabel("Teléfono:")));
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
        botonCancelar.setFont(SUBTITULO_PEQUENO);
        botonCancelar.setBackground(GRIS_MEDIO);
        botonCancelar.setForeground(Color.WHITE);
        botonCancelar.setBorderPainted(false);
        botonCancelar.setContentAreaFilled(false);
        botonCancelar.setOpaque(true);
        botonCancelar.setFocusPainted(false);
        botonCancelar.setAlignmentX(JButton.LEFT_ALIGNMENT);
        panelBotones.add(botonCancelar);
		botonCancelar.addActionListener(e -> {
			layout.show(contenedor, "bienvenida");
			setTitle("EleuTradia: Inicio");
		});
		botonCancelar.addMouseListener(myAdapterGris);
        
        JButton botonContinuar = new JButton("Continuar");
        botonContinuar.setFont(SUBTITULO_PEQUENO);
        botonContinuar.setBackground(AZUL_CLARO);
        botonContinuar.setForeground(Color.WHITE);
        botonContinuar.setBorderPainted(false);
        botonContinuar.setContentAreaFilled(false);
        botonContinuar.setOpaque(true);
        botonContinuar.setFocusPainted(false);
        botonContinuar.setAlignmentX(JButton.LEFT_ALIGNMENT);
        panelBotones.add(botonContinuar);
        botonContinuar.addActionListener(e -> validarYContinuar(radioParticular.isSelected(), 
        		campoRegId, campoRegNombre, campoRegEmail, campoRegTlf, campoRegPassword, campoRegConfirmPassword));
        botonContinuar.addMouseListener(myAdapterAzul);
        
        panelRegistro.add(panelBotones);
        
        JScrollPane scrollRegistro = new JScrollPane(panelRegistro);
        scrollRegistro.setBorder(null); // elimina el borde del scroll
        scrollRegistro.setBackground(Color.WHITE);
        scrollRegistro.getVerticalScrollBar().setUI(personalizarScrollBarUI());
        scrollRegistro.getVerticalScrollBar().setUnitIncrement(16); // suaviza el scroll
        scrollRegistro.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panelDcho.add(scrollRegistro);
        
        JPanel panelIzdo = construirPanelImagen();
        
        mainPanel.add(panelIzdo);
        mainPanel.add(panelDcho);
		
		return mainPanel;
	}
	
	private JPanel construirPanelRegistro2() {
		JPanel mainPanel = new JPanel(new GridLayout(1, 2));
		
		JPanel panelDcho = new JPanel();
        panelDcho.setLayout(new BoxLayout(panelDcho, BoxLayout.Y_AXIS));
        panelDcho.setBackground(Color.WHITE);
        panelDcho.setBorder(BorderFactory.createEmptyBorder(30, 50, 10, 20));
        
        JLabel tituloReg = new JLabel("Abra su cuenta (2/2):");
        tituloReg.setFont(TITULO_GRANDE);
        tituloReg.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        tituloReg.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panelDcho.add(tituloReg);
        
	    JPanel panelRegistro = new JPanel();
	    panelRegistro.setLayout(new BoxLayout(panelRegistro, BoxLayout.Y_AXIS));
	    panelRegistro.setBackground(Color.WHITE);
	    panelRegistro.setAlignmentX(JPanel.CENTER_ALIGNMENT);
	    
	    // Tipo de dirección
        panelRegistro.add(crearLabel(new JLabel("Tipo de dirección:")));
        panelRegistro.add(Box.createVerticalStrut(10));
        
        String[] tiposDireccion = {"Plaza", "Calle", "Avenida", "Paseo", "Ronda", "Camino"};
        JComboBox<String> comboTipoDireccion = new JComboBox<>(tiposDireccion);
        comboTipoDireccion.setFont(CUERPO_GRANDE);
        comboTipoDireccion.setMaximumSize(new Dimension(300, 50));
        comboTipoDireccion.setAlignmentX(LEFT_ALIGNMENT);
        panelRegistro.add(comboTipoDireccion);
        panelRegistro.add(Box.createVerticalStrut(20));
        
        // Nombre de dirección
        panelRegistro.add(crearLabel(new JLabel("Nombre de la dirección:")));
        panelRegistro.add(Box.createVerticalStrut(10));
        
        JTextField campoNombreDireccion = (JTextField) crearCampo(false);
        panelRegistro.add(campoNombreDireccion);
        panelRegistro.add(Box.createVerticalStrut(20));
        
        // Domicilio fiscal
        panelRegistro.add(crearLabel(new JLabel("Domicilio fiscal:")));
        panelRegistro.add(Box.createVerticalStrut(10));
        
        JTextField campoDomicilioFiscal = (JTextField) crearCampo(false);
        panelRegistro.add(campoDomicilioFiscal);
        panelRegistro.add(Box.createVerticalStrut(20));
        
        // Campos específicos para Particular
        JLabel labelFechaNacimiento = crearLabel(new JLabel("Fecha de nacimiento - DD/MM/AAAA:"));
        JTextField campoFechaNacimiento = (JTextField) crearCampo(false);
        
        JLabel labelPaisResidencia = crearLabel(new JLabel("País de residencia:"));
        JTextField campoPaisResidencia = (JTextField) crearCampo(false);
        
        JPanel panelCamposParticular = new JPanel();
        panelCamposParticular.setLayout(new BoxLayout(panelCamposParticular, BoxLayout.Y_AXIS));
        panelCamposParticular.setBackground(Color.WHITE);
        panelCamposParticular.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        
        panelCamposParticular.add(labelFechaNacimiento);
        panelCamposParticular.add(Box.createVerticalStrut(10));
        panelCamposParticular.add(campoFechaNacimiento);
        panelCamposParticular.add(Box.createVerticalStrut(20));
        
        panelCamposParticular.add(labelPaisResidencia);
        panelCamposParticular.add(Box.createVerticalStrut(10));
        panelCamposParticular.add(campoPaisResidencia);
        panelCamposParticular.add(Box.createVerticalStrut(20));
        
        panelRegistro.add(panelCamposParticular);

        // Botones
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 0));
        panelBotones.setBackground(Color.WHITE);
        panelBotones.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        panelBotones.setMaximumSize(new Dimension(300, 25));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JButton botonVolver = new JButton("Volver");
        botonVolver.setFont(SUBTITULO_PEQUENO);
        botonVolver.setBackground(GRIS_MEDIO);
        botonVolver.setForeground(Color.WHITE);
        botonVolver.setBorderPainted(false);
        botonVolver.setContentAreaFilled(false);
        botonVolver.setOpaque(true);
        botonVolver.setFocusPainted(false);
        botonVolver.setAlignmentX(JButton.LEFT_ALIGNMENT);
        panelBotones.add(botonVolver);
		botonVolver.addActionListener(e -> {
			layout.show(contenedor, "registro1");
		});
		botonVolver.addMouseListener(myAdapterGris);
        
        JButton botonConfirmar = new JButton("Confirmar");
        botonConfirmar.setFont(SUBTITULO_PEQUENO);
        botonConfirmar.setBackground(AZUL_CLARO);
        botonConfirmar.setForeground(Color.WHITE);
        botonConfirmar.setBorderPainted(false);
        botonConfirmar.setContentAreaFilled(false);
        botonConfirmar.setOpaque(true);
        botonConfirmar.setFocusPainted(false);
        botonConfirmar.setAlignmentX(JButton.LEFT_ALIGNMENT);
        panelBotones.add(botonConfirmar);
        botonConfirmar.addActionListener(e -> procesarRegistro(comboTipoDireccion, campoNombreDireccion,
        		campoDomicilioFiscal, campoFechaNacimiento, campoPaisResidencia));
        botonConfirmar.addMouseListener(myAdapterAzul);
        
        panelRegistro.add(Box.createVerticalStrut(60));
        panelRegistro.add(panelBotones, JPanel.BOTTOM_ALIGNMENT);
        
        JScrollPane scrollRegistro = new JScrollPane(panelRegistro);
        scrollRegistro.setBorder(null);
        scrollRegistro.setBackground(Color.WHITE);
        scrollRegistro.getVerticalScrollBar().setUI(personalizarScrollBarUI());
        scrollRegistro.getVerticalScrollBar().setUnitIncrement(16);
        scrollRegistro.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panelDcho.add(scrollRegistro);
        
        JPanel panelIzdo = construirPanelImagen();
        
        mainPanel.add(panelIzdo);
        mainPanel.add(panelDcho);
        
        // Listener para mostrar/ocultar campos según el tipo de usuario
        panelDcho.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
            	boolean mostrarCamposParticular = tempEsParticular;
                panelCamposParticular.setVisible(mostrarCamposParticular);
                panelRegistro.revalidate();
                panelRegistro.repaint();
            }
        });
		
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
			File carpeta = new File(getClass().getResource("/images/fondos/").toURI());
			File[] fondos = carpeta.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));
			
			if (fondos == null || fondos.length == 0) throw new RuntimeException("No hay imágenes en la carpeta");
			
        	Random random = new Random();
        	File fondoRandom = fondos[random.nextInt(fondos.length)];
        	ImageIcon icon = new ImageIcon(fondoRandom.getAbsolutePath());
            Image imagen = icon.getImage().getScaledInstance(this.getWidth()/2, this.getHeight(), Image.SCALE_SMOOTH);
            originalIcon = new ImageIcon(imagen);
            
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen");
            originalIcon = null;
        }
	}
	
	private JLabel crearLabel(JLabel myLabel) {
        myLabel.setFont(SUBTITULO_PEQUENO);
        myLabel.setForeground(GRIS_OSCURO);
        myLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        return myLabel;
	}
	
	private JTextComponent crearCampo(boolean oculto) {
	    JTextComponent campo = oculto ? new JPasswordField(20) : new JTextField(20);
	    campo.setFont(CUERPO_GRANDE);
	    campo.setMaximumSize(new Dimension(300, 25));
	    campo.setPreferredSize(new Dimension(300, 25));
	    campo.setAlignmentX(LEFT_ALIGNMENT);
	    return campo;
	}
	
	private void procesarLogin(boolean esParticular, JTextField campoId, JPasswordField campoPassword) {
	    String id = campoId.getText().trim();
	    String password = new String(campoPassword.getPassword());

	    if (id.isBlank() || password.isEmpty()) {
	    	UITema.mostrarError(this, "Por favor, rellene ambos campos.", "Campos incompletos");
	        return;
	    }

	    if (esParticular) {
	    	if (!id.matches(DNI_REGEX)) {
	    		UITema.mostrarError(this, "Formato de DNI inválido.", "Error de formato");
	    		return;
	    	}
	    	
	    	Particular p = dbManager.buscarParticular(id, password);
	        if (p != null) {
	            new VentanaPrincipal(p);
	            dispose();
	            return;
	        }
	        UITema.mostrarError(this, "DNI o contraseña incorrectos.", "Error de login");
	        
	    } else {
	    	if (!id.matches(NIF_REGEX)) {
	    		UITema.mostrarError(this, "Formato de NIF inválido.", "Error de formato");
	    		return;
	    	}
	    	
	    	Empresa e = dbManager.buscarEmpresa(id, password);
	        if (e != null) {
	            new VentanaPrincipal(e);
	            dispose();
	            return;
	        }
	        UITema.mostrarError(this, "NIF o contraseña incorrectos.", "Error de login");
	    }
	}
	
	private void validarYContinuar(boolean esParticular, JTextField campoRegId, JTextField campoRegNombre, 
	        JTextField campoRegEmail, JTextField campoRegTlf, JPasswordField campoRegPassword, 
	        JPasswordField campoRegConfirmPassword) {
	    String id = campoRegId.getText().trim();
	    String nombre = campoRegNombre.getText().trim();
	    String email = campoRegEmail.getText().trim();
	    String tlf = campoRegTlf.getText().trim();
	    String pass = new String(campoRegPassword.getPassword());
	    String conf = new String(campoRegConfirmPassword.getPassword());

	    if (id.isEmpty() || nombre.isEmpty() || email.isEmpty() || tlf.isEmpty() || pass.isEmpty() || conf.isEmpty()) {
	    	UITema.mostrarError(this, "Rellene todos los campos.", "Campos incompletos");
	        return;
	    }
	    if (!nombre.matches(NOMBRE_REGEX)) {
	    	UITema.mostrarError(this, "El nombre solo puede contener letras.", "Nombre inválido");
	        return;
	    }
	    if (esParticular && nombre.split("\\s+").length < 2) {
	    	UITema.mostrarError(this, "Introduzca su nombre completo.", "Nombre incompleto");
	        return;
	    }
	    if (!email.matches(EMAIL_REGEX)) {
	    	UITema.mostrarError(this, "Formato de email inválido", "Error de formato");
	        return;
	    }
	    if (!tlf.matches(TELEFONO_REGEX)) {
	    	UITema.mostrarError(this, "Formato de teléfono inválido", "Error de formato");
	        return;
	    }
	    if (pass.length()<8) {
	    	UITema.mostrarError(this, "La contraseña debe tener al menos 8 caracteres.", "Contraseña demasiado corta");
	        return;
	    }
	    if (!pass.equals(conf)) {
	    	UITema.mostrarError(this, "Las contraseñas no coinciden.", "Campos erróneos");
	        return;
	    }
	    if (esParticular) {
	        if (!id.matches(DNI_REGEX)) {
	        	UITema.mostrarError(this, "Formato de DNI inválido.", "Error de formato");
	            return;
	        }
	    } else {
	        if (!id.matches(NIF_REGEX)) {
	        	UITema.mostrarError(this, "Formato de NIF inválido.", "Error de formato");
	            return;
	        }
	    }
	    
	    if (dbManager.existeUsuario(id, esParticular)) {
	    	UITema.mostrarError(this, 
	            "Ya existe un usuario con ese " + (esParticular ? "DNI" : "NIF"), 
	            "Usuario duplicado");
	        return;
	    }
	    
	    if (dbManager.existeEmail(email)) {
	    	UITema.mostrarError(this, 
	            "Ya existe un usuario con ese email", 
	            "Email duplicado");
	        return;
	    }
	    
	    if (dbManager.existeTelefono(tlf)) {
	        UITema.mostrarError(this, 
	            "Ya existe un usuario con ese teléfono", 
	            "Teléfono duplicado");
	        return;
	    }
	    
	    // Guardamos los datos temporalmente y pasamos a la segunda pantalla
	    tempId = id;
	    tempNombre = nombre;
	    tempEmail = email;
	    tempTlf = tlf;
	    tempPass = pass;
	    tempEsParticular = esParticular;
	    
	    layout.show(contenedor, "registro2");
	}
	
	private void procesarRegistro(JComboBox<String> comboTipoDireccion, JTextField campoNombreDireccion,
        JTextField campoDomicilioFiscal, JTextField campoFechaNacimiento, JTextField campoPaisResidencia) {
    
	    String tipoDireccion = (String) comboTipoDireccion.getSelectedItem();
	    String nombreDireccion = campoNombreDireccion.getText().trim();
	    String domicilioFiscal = campoDomicilioFiscal.getText().trim();
	
	    if (tipoDireccion == null || nombreDireccion.isEmpty() || domicilioFiscal.isEmpty()) {
	        UITema.mostrarError(this, "Rellene todos los campos obligatorios.", 
	                "Campos incompletos");
	        return;
	    }
	    
	    // Validaciones para Particular
	    String fechaNacimiento = "";
	    String paisResidencia = "";
	    if (tempEsParticular) {
	        fechaNacimiento = campoFechaNacimiento.getText().trim();
	        paisResidencia = campoPaisResidencia.getText().trim();
	        
	        if (fechaNacimiento.isEmpty() || paisResidencia.isEmpty()) {
	            UITema.mostrarError(this, "Rellene todos los campos obligatorios.", 
	                    "Campos incompletos");
	            return;
	        }
	        
	        // Validar formato de fecha (DD/MM/AAAA)
	        if (!fechaNacimiento.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
	            UITema.mostrarError(this, "Formato de fecha inválido. Use DD/MM/AAAA", 
	                    "Error de formato");
	            return;
	        }
	    }
	    
	    // Construir la dirección completa
	    String direccionCompleta = tipoDireccion + " " + nombreDireccion;
	    
	    // Aquí deberías modificar tu método insertarUsuario para incluir los nuevos campos
	    // Por ahora, usamos el método existente
	    boolean registrado = dbManager.insertUsuario(tempId, tempNombre, tempEmail, tempTlf, tempPass, tempEsParticular,
	    		direccionCompleta, fechaNacimiento, paisResidencia);
	    
	    if (registrado) {
	        mostrarCarga();
	        UITema.mostrarInfo(this, "Usuario registrado correctamente.", "Registro exitoso");
	        layout.show(contenedor, "bienvenida");
	        setTitle("EleuTradia: Inicio");
	        
	        // Limpiar variables temporales
	        tempId = tempNombre = tempEmail = tempTlf = tempPass = null;
	    } else {
	        UITema.mostrarError(this, 
	            "Error al registrar el usuario. Inténtelo de nuevo.", 
	            "Error de registro");
	    }
	}
    
    private void mostrarCarga() {
        // Diálogo modal que bloquea al usuario pero no al EDT
        JDialog dialogoCarga = new JDialog(this, "Procesando registro...", true);
        dialogoCarga.setSize(200, 120);
        dialogoCarga.setLocationRelativeTo(this);
        dialogoCarga.setResizable(false);
        dialogoCarga.getContentPane().setBackground(Color.WHITE);
        dialogoCarga.setUndecorated(true);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE); 
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel label = new JLabel("Creando cuenta, por favor espere...");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setForeground(new Color(30, 30, 30));
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        JProgressBar barra = new JProgressBar(0, 100);
        barra.setAlignmentX(JProgressBar.CENTER_ALIGNMENT);
        barra.setPreferredSize(new Dimension(300, 25));

        panel.add(label);
        panel.add(Box.createVerticalStrut(10));
        panel.add(barra);

        dialogoCarga.add(panel);

        // --- Thread en segundo plano ---
        Thread hilo = new Thread(() -> {
            try {
                for (int i = 0; i <= 100; i += 5) {
                    int progreso = i;

                    // Como estamos fuera del EDT, actualizamos barra con invokeLater
                    SwingUtilities.invokeLater(() -> barra.setValue(progreso));

                    Thread.sleep(80); // Simula trabajo real
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Cerrar el diálogo cuando termine
            SwingUtilities.invokeLater(dialogoCarga::dispose);
        });

        hilo.start();
        dialogoCarga.setVisible(true); // Se muestra mientras el hilo trabaja
    }

}
