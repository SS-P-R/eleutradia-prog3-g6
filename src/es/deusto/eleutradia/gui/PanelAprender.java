package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import es.deusto.eleutradia.domain.Curso;

public class PanelAprender extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JPanel panelPrincipal, panelProgresoFiltros, panelCursos;
	
	private JTable tablaCursos;
	private DefaultTableModel modeloTabla;
	private JLabel labelContenidos;
	
	private JButton botonTodosCursos, botonMisCursos;
	
	private JLabel labelCompletados;
	private JProgressBar progressBarRacha;
	
	private JTextField textoBusqueda;
	private JCheckBox checkNivelBasico, checkNivelMedio, checkNivelAvanzado;
	
    private static final Color COLOR_FONDO_PRINCIPAL = Color.WHITE;
    private static final Color COLOR_BOTON_INACTIVO = new Color(220, 220, 220); // Gris claro
    private static final Color COLOR_TARJETA_COMPLETADA = new Color(220, 255, 220); // Verde claro
    
    private ArrayList<Curso> listaCursos;
	
	public PanelAprender() {
		
		this.setLayout(new BorderLayout(10, 10));
		this.setBorder(BorderFactory.createEmptyBorder());
		
		panelPrincipal = crearPanelPrincipal();
		panelProgresoFiltros = crearPanelProgresoFiltros();

		this.add(panelPrincipal, BorderLayout.CENTER);
		this.add(panelProgresoFiltros, BorderLayout.EAST);
		
		registrarListeners();
		
	}

	private JPanel crearPanelPrincipal() {

		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBackground(COLOR_FONDO_PRINCIPAL);
		
		JPanel panelPestanas = crearPanelPestanas();
		panel.add(panelPestanas, BorderLayout.NORTH);
		
		JPanel panelBusquedaCursos = new JPanel(new BorderLayout(0, 10));
		panelBusquedaCursos.setBackground(COLOR_FONDO_PRINCIPAL);
		
		textoBusqueda = new JTextField();
		panelBusquedaCursos.add(textoBusqueda, BorderLayout.NORTH);
		JScrollPane panelTarjetaScroll = crearPanelCursos();
		panelBusquedaCursos.add(panelTarjetaScroll, BorderLayout.CENTER);
		
		panel.add(panelBusquedaCursos, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel crearPanelPestanas() {
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panel.setBackground(COLOR_FONDO_PRINCIPAL);
		
		botonMisCursos = new JButton("Todas los Cursos");
		botonTodosCursos = new JButton("Tus Cursos");
		
		JPanel fondoMisCursos = new JPanel(new BorderLayout());
		fondoMisCursos.add(botonMisCursos, BorderLayout.CENTER);
		JPanel fondoTodosCurso = new JPanel(new BorderLayout());
		fondoTodosCurso.add(botonTodosCursos, BorderLayout.CENTER);
		
		panel.add(fondoMisCursos);
		panel.add(fondoTodosCurso);
		
		return panel;
	}
	
	private JScrollPane crearPanelCursos() {
		
		panelCursos = new JPanel(new GridLayout(0, 2, 5, 5));
		panelCursos.setBackground(COLOR_FONDO_PRINCIPAL);
		
		JScrollPane scrollPane = new JScrollPane(panelCursos);
		scrollPane.setBorder(null);
		scrollPane.getViewport().setBackground(COLOR_FONDO_PRINCIPAL);
		
		return scrollPane;
	}

	private JPanel crearPanelProgresoFiltros() {

		JPanel panelLateral = new JPanel(new BorderLayout(10, 10));
		panelLateral.setPreferredSize(new Dimension(250, 10));
		
		JPanel panelRacha = crearPanelRacha();
		JPanel panelFiltros = crearPanelFiltros();
		
		return null;
	}
	
	private JPanel crearPanelRacha() {
		
		JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5));
		
		JLabel labelTitulo = new JLabel("Lecciones Completadas");
		progressBarRacha = new JProgressBar(0, 100);
		progressBarRacha.setStringPainted(true);
		
		panel.add(labelTitulo);
		panel.add(progressBarRacha);
		
		return null;
	}
	
	private JPanel crearPanelFiltros() {
		
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		
		JPanel panelFiltrosAvanzados = new JPanel(new GridLayout(2, 1, 5, 10));
		
		JPanel panelNivel = new JPanel(new GridLayout(3, 1));
		
		checkNivelBasico = new JCheckBox("Basico");
		checkNivelMedio = new JCheckBox("Medio");
		checkNivelAvanzado = new JCheckBox("Avanzado");
		
		panelNivel.add(checkNivelBasico);
		panelNivel.add(checkNivelMedio);
		panelNivel.add(checkNivelAvanzado);
		
		panelFiltrosAvanzados.add(panelNivel);
		panel.add(panelFiltrosAvanzados, BorderLayout.CENTER);
		
		return null;
	}
	
	private JButton crearBotonCurso(Curso curso) {
		
		JButton boton = new JButton(curso.getNombre());
		boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		
		return boton;
	}
	
	private void registrarListeners() {
		
		textoBusqueda.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				filtrarCursos();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				filtrarCursos();
			}

			@Override
			public void changedUpdate(DocumentEvent e) { }			
		});	
	}
	
	private void filtrarCursos() {
		
		String texto = textoBusqueda.getText().toLowerCase();
		
		panelCursos.removeAll();
		
	}

	
}
