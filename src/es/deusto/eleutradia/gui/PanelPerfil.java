package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import es.deusto.eleutradia.domain.Particular;
import es.deusto.eleutradia.domain.Usuario;

public class PanelPerfil extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Usuario usuarioLogeado;
	
	private JLabel labelTitulo;
	private JTextField textoNombre, textoEmail;
	private JPasswordField textoPassNueva, textoConfirmarPassNueva;
	private JButton botonGuardarDatos, botonCambiarPass, botonCerrarSesion;
	
	
    private static final Color COLOR_BOTON_GUARDAR = new Color(0, 100, 255); // Azul
    private static final Color COLOR_BOTON_PELIGRO = new Color(220, 53, 69); // Rojo
    private static final Color COLOR_TEXTO_BOTON = Color.WHITE;
    private static final Font FONT_TITULO = new Font("Arial", Font.BOLD, 24);
    private static final Font FONT_SUBTITULO = new Font("Arial", Font.BOLD, 16);
    private static final Font FONT_ETIQUETA = new Font("Arial", Font.PLAIN, 14);
    private static final Font FONT_CAMPO = new Font("Arial", Font.PLAIN, 14);
    
    public PanelPerfil(Usuario usuario) {
		
    	this.usuarioLogeado = usuario;
		inicializarUI();
    	
	}

	private void inicializarUI() {
		
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.setBackground(Color.WHITE);
		
		labelTitulo = new JLabel("Modulo Perfil: Configuracion de ");
	}

}
