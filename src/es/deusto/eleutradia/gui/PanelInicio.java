package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

import es.deusto.eleutradia.domain.Particular;
import es.deusto.eleutradia.domain.Usuario;

public class PanelInicio extends JPanel{

	private static final long serialVersionUID = 1L;
	private VentanaPrincipal ventanaPrincipal;
	private Usuario usuario;

	Font font;

	public PanelInicio(Usuario usuario, VentanaPrincipal ventanaPrincipal) {
		this.usuario = usuario;
		this.ventanaPrincipal = ventanaPrincipal;
		//Fondo
		setLayout(new BorderLayout(10,10));
		setBackground(new Color(144, 238, 144));
		
		JPanel panelSaludo = PanelSaludo();
		JPanel panelCursos = PanelCursos();
		JPanel panelLecciones = PanelLecciones();
		JPanel panelRecordatorio = PanelRecordatorio();
		JPanel panelActivos = PanelActivos();
		
		setVisible(true);
		add(panelSaludo, BorderLayout.NORTH);
		add(panelRecordatorio, BorderLayout.SOUTH);
		
		JPanel centro = new JPanel(new GridLayout(2,2,10,10));
		centro.setBackground(new Color(144, 238, 144));
		centro.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		centro.add(panelCursos);
		centro.add(panelLecciones);
		centro.add(panelActivos);
		add(centro, BorderLayout.CENTER);
		
	}
	

	private JPanel PanelSaludo() {
		JPanel panelSaludo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel saludoTxt = new JLabel("Hola " + usuario.getNombre()+ ",");
		
		saludoTxt.setBackground(Color.cyan);
		saludoTxt.setFont(new Font("Times new roman",Font.BOLD,20));
		
		Border recuadro = BorderFactory.createLineBorder(Color.gray, 2);
		Border posicion = BorderFactory.createEmptyBorder(5,10,5,10);
		saludoTxt.setBorder(BorderFactory.createCompoundBorder(recuadro, posicion));
		
		panelSaludo.setBackground(new Color(144, 238, 144));
		panelSaludo.add(saludoTxt);
		return panelSaludo;
	}	
	
	
	private JPanel PanelRecordatorio() {
		JPanel recordatorio = new JPanel(new FlowLayout());
//		recordatorio.setBackground(new Color(144, 238, 144) );
		JButton completarPerfil = new JButton("Ir al perfil");
		completarPerfil.addActionListener(e->{
			ventanaPrincipal.mostrarPanel("Perfil");
		});
		if (usuario instanceof Particular) {
			Particular particular = (Particular) usuario;
			if (particular.getPaisResidencia()==null) {
				recordatorio.add(completarPerfil);
				recordatorio.add(new JLabel("Todavía no has completado tu perfil, añade tu país"));
			}else if (particular.getDireccion()==null) {
				recordatorio.add(completarPerfil);
				recordatorio.add(new JLabel("Todavía no has completado tu perfil, añade tu dirección"));
			}else {
				recordatorio.add(new JLabel("Todo correcto"));
			}
		}
		return recordatorio;
	}

	
	private JPanel PanelLecciones() {
		JPanel proxLecJPanel = new JPanel();
		proxLecJPanel.setBackground(new Color(173, 216, 230));
		proxLecJPanel.setBorder(BorderFactory.createLineBorder(Color.black,3));
		proxLecJPanel.setLayout(new BoxLayout(proxLecJPanel, BoxLayout.Y_AXIS));
		
		JLabel mensajeLecciones = new JLabel();
		mensajeLecciones.setText("¿Preparado para tu próxima lección?");
		mensajeLecciones.setFont(new Font("Times new roman", Font.BOLD,16));
		proxLecJPanel.add(mensajeLecciones);

		JLabel mensajeLecciones2 = new JLabel("Lecciones recomendadas para continuar aprendiendo:");
		mensajeLecciones2.setFont(new Font("Times new roman", Font.BOLD,12));
		proxLecJPanel.add(Box.createVerticalStrut(10));
		proxLecJPanel.add(mensajeLecciones2);

		PanelFocus(proxLecJPanel);
		return proxLecJPanel;
	}

	private JPanel PanelCursos() {
		JPanel cursosPanel = new JPanel();
		cursosPanel.setBackground(new Color(173, 216, 230));
		cursosPanel.setBorder(BorderFactory.createLineBorder(Color.black,3));
		
		JLabel mensajeCursos = new JLabel();
		mensajeCursos.setText("Cursos en progreso: ");
		mensajeCursos.setFont(new Font("Times new roman", Font.BOLD,16));
		cursosPanel.add(mensajeCursos);
		
		PanelFocus(cursosPanel);
		return cursosPanel;
	}

	private JPanel PanelActivos() {
		JPanel activosPanel = new JPanel();
		activosPanel.setBackground(new Color(173, 216, 230));
		activosPanel.setBorder(BorderFactory.createLineBorder(Color.black,3));
		
		JLabel mensajeActivos = new JLabel();
		mensajeActivos.setText("Resumen de mis activos: ");
		mensajeActivos.setFont(new Font("Times new roman", Font.BOLD,16));
		activosPanel.add(mensajeActivos);
		
		String[] columnas = {"Nombre","Varianza", "Valor total (€)"};
		Object[][] datos = {{"Nombre","Varianza", "Valor total (€)"},{"Total","X%","x€"},{"Hoy","X%","x€"}};
		JTable tablaResumen = new JTable(datos, columnas);
		JScrollPane scroll = new JScrollPane(tablaResumen);
		activosPanel.add(scroll);
		
		tablaResumen.setFont(new Font("Times new roman", Font.BOLD,12));
		tablaResumen.setRowHeight(20);
		tablaResumen.getTableHeader().setFont(new Font("Times new roman", Font.BOLD,12));		
		activosPanel.add(tablaResumen);

		PanelFocus(activosPanel);
		return activosPanel;
	};
	
	
	private void PanelFocus(JPanel panel) {
		Color colorOg = panel.getBackground();
		panel.addMouseListener(new MouseListener() {
			@Override
			public void mouseExited(MouseEvent e) {
				panel.setBackground(colorOg);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				panel.setBackground(new Color(150, 206, 240));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}

