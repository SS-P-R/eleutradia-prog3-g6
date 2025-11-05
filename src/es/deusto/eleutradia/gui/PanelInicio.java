package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import es.deusto.eleutradia.domain.Particular;
import es.deusto.eleutradia.domain.Usuario;

public class PanelInicio extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;

	Font font;

	public PanelInicio(Usuario usuario) {
		this.usuario = usuario;
		//Fondo
		setLayout(new BorderLayout(10,10));
		
		JPanel panelSaludo = PanelSaludo();
		JPanel panelCursos = PanelCursos();
		JPanel panelLecciones = PanelLecciones();
		JPanel panelRecordatorio = PanelRecordatorio();
		
		setVisible(true);
		add(panelSaludo, BorderLayout.NORTH);
		add(panelLecciones, BorderLayout.EAST);
		add(panelCursos, BorderLayout.CENTER);
		add(panelRecordatorio, BorderLayout.SOUTH);
		
	}

	private JPanel PanelRecordatorio() {
		JPanel recordatorio = new JPanel(new FlowLayout());
		JButton completarPerfil = new JButton("Ir al perfil");
		if (usuario instanceof Particular) {
			Particular particular = (Particular) usuario;
			if (particular.getPaisResidencia()==null) {
				recordatorio.add(completarPerfil);
				recordatorio.add(new JLabel("Todavía no has completado tu perfil, añade tu país"));
			}else if (particular.getDireccion()==null) {
				recordatorio.add(completarPerfil);
				recordatorio.add(new JLabel("Todavía no has completado tu perfil, añade tu dirección"));
			}else {
				recordatorio.add(new JLabel("Todo correcto mi pana"));
			}
		}
		return recordatorio;
	}

	private JPanel PanelLecciones() {
		JPanel proxLecJPanel = new JPanel();
		return proxLecJPanel;
	}

	private JPanel PanelCursos() {
		JPanel cursosPanel = new JPanel();
		return cursosPanel;
		
	}

	private JPanel PanelSaludo() {
		JPanel panelSaludo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel saludoTxt = new JLabel("Hola, " + usuario.getNombre());
		
		saludoTxt.setBackground(Color.cyan);
		saludoTxt.setFont(new Font("Times new roman",Font.BOLD,18));
		
		Border recuadro = BorderFactory.createLineBorder(Color.gray, 2);
		Border posicion = (BorderFactory.createEmptyBorder(0,15,5,5));
		saludoTxt.setBorder(BorderFactory.createCompoundBorder(recuadro, posicion));
		
		panelSaludo.add(saludoTxt);
		return panelSaludo;
	}	
}

