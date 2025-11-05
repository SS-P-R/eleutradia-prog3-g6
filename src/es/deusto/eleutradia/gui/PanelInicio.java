package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import es.deusto.eleutradia.domain.Particular;
import es.deusto.eleutradia.domain.Usuario;

public class PanelInicio extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private JPanel proxLeccionPanel, cursosProgresoPanel, recordatorio;
	private JTextField saludoTxt;
	private Usuario usuario;

	Font font;

	public void PanelInicio () {
		//Fondo
		setLayout(new BorderLayout(10,10));
		
		//Saludo
		saludoTxt.setText("Hola, " + usuario.getNombre());
		saludoTxt.setFont(new Font("Times new roman",Font.BOLD,18));
		saludoTxt.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
		saludoTxt.setBackground(Color.cyan);
		
		//Cursos en progreso
		
		
		//Próxima lección
		
		
		//Recordatorio
		JPanel recordatorio = new JPanel(new FlowLayout());
		JButton completarPerfil = new JButton("Ir al perfil");
		if (usuario instanceof Particular) {
			Particular particular = (Particular) usuario;
			if (particular.getPaisResidencia()==null) {
				recordatorio.add(completarPerfil);
				recordatorio.add(new JTextField("Todavía no has completado tu perfil, añade tu país"));
			}else if (particular.getDireccion()==null) {
				recordatorio.add(completarPerfil);
				recordatorio.add(new JTextField("Todavía no has completado tu perfil, añade tu dirección"));
			}if(particular.getPaisResidencia()==null || particular.getDireccion()==null){
				add(recordatorio, BorderLayout.SOUTH);
			}
		}
		
		add(saludoTxt, BorderLayout.NORTH);
		add(proxLeccionPanel, BorderLayout.EAST);
		add(cursosProgresoPanel, BorderLayout.CENTER);
	}	
}

