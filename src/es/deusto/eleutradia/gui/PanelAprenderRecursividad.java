package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import es.deusto.eleutradia.domain.Curso;
import es.deusto.eleutradia.domain.NivelConocimiento;
import es.deusto.eleutradia.main.MainEleutradia;

import static es.deusto.eleutradia.gui.style.UITema.*;

public class PanelAprenderRecursividad extends JPanel {
	

	private static final long serialVersionUID = 1L;
	
	private JComboBox<String> comboNivelObjetivo;
	private JButton btnBuscarRuta;
	private JButton btnVolver;
	private JPanel panelResultado;
	private ActionListener accionVolver;
	
	public PanelAprenderRecursividad() {

		setLayout(new BorderLayout(20, 20));
		setBackground(MAIN_FONDO);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		JPanel panelTitulo = crearPanelTitulo();
		add(panelTitulo, BorderLayout.NORTH);
		
		JPanel panelCentral = crearPanelFormulario();
		add(panelCentral, BorderLayout.CENTER);
		
		panelResultado = new JPanel(new BorderLayout());
		panelResultado.setBackground(MAIN_FONDO);
		add(panelResultado, BorderLayout.SOUTH);
		
		JPanel panelBotones = crearPanelBotones();
		add(panelBotones, BorderLayout.EAST);

	}
	
	private JPanel crearPanelTitulo() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(MAIN_FONDO);
		panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		
		JLabel labelTitulo = new JLabel("La forma mas rápida para Aprender");
		labelTitulo.setFont(TITULO_GRANDE);
		labelTitulo.setForeground(AZUL_OSCURO);
		labelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel labelSubtitulo = new JLabel("Encuentra el camino óptimo desde PRINCIPIANTE hasta tu nivel objetivo");
		labelSubtitulo.setFont(CUERPO_GRANDE);
		labelSubtitulo.setForeground(GRIS_CLARO);
		labelSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		panel.add(labelTitulo);
		panel.add(Box.createRigidArea(new Dimension(0, 10)));
		panel.add(labelSubtitulo);
		
		return panel;
	}
	
	private JPanel crearPanelFormulario() {
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.WHITE);
		panel.setBorder(BorderFactory.createCompoundBorder(
		        BorderFactory.createLineBorder(MAIN_BORDE, 1),
		        BorderFactory.createEmptyBorder(30, 30, 30, 30)
		));
		
		JLabel labelNivel = new JLabel("Selecciona tu nivel objetivo:");
		labelNivel.setFont(SUBTITULO_MEDIO);
		labelNivel.setForeground(Color.BLACK);
		labelNivel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(labelNivel);

		panel.add(Box.createVerticalStrut(15));
		
		comboNivelObjetivo = new JComboBox<>();
		comboNivelObjetivo.addItem("INTERMEDIO");
		comboNivelObjetivo.addItem("PROFESIONAL");
		comboNivelObjetivo.addItem("AVANZADO");
		comboNivelObjetivo.setFont(CUERPO_GRANDE);
		comboNivelObjetivo.setPreferredSize(new Dimension(300, 40));
		comboNivelObjetivo.setMaximumSize(new Dimension(300, 40));
		comboNivelObjetivo.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(comboNivelObjetivo);
		
		panel.add(Box.createVerticalStrut(25));
		
		btnBuscarRuta = new JButton("Buscar Ruta de Aprendizaje");
		btnBuscarRuta.setFont(SUBTITULO_MEDIO);
		btnBuscarRuta.setBackground(AZUL_CLARO);
		btnBuscarRuta.setForeground(Color.WHITE);
		btnBuscarRuta.setBorderPainted(false);
		btnBuscarRuta.setContentAreaFilled(false);
		btnBuscarRuta.setOpaque(true);
		btnBuscarRuta.setFocusPainted(false);
		btnBuscarRuta.addMouseListener(myAdapterAzul);
		btnBuscarRuta.setPreferredSize(new Dimension(300, 50));
		btnBuscarRuta.setMaximumSize(new Dimension(300, 50));
		btnBuscarRuta.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnBuscarRuta.addActionListener(e -> buscarRutaAprendizaje());
		panel.add(btnBuscarRuta);


		
		return panel;
	}

	private JPanel crearPanelBotones() {

		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel.setBackground(MAIN_FONDO);
		
		btnVolver = new JButton("Volver");
		btnVolver.setFont(SUBTITULO_MEDIO);
		btnVolver.setBackground(GRIS_MEDIO);
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setPreferredSize(new Dimension(150, 45));
		btnVolver.setBorderPainted(false);
		btnVolver.setContentAreaFilled(false);
		btnVolver.setOpaque(true);
		btnVolver.setFocusPainted(false);
		btnVolver.addActionListener(e -> {
			if (accionVolver != null) {
				accionVolver.actionPerformed(e);
			}
		});
		btnVolver.addMouseListener(myAdapterGris);
		
		panel.add(btnVolver);
		
		return panel;

	}
	
	private void buscarRutaAprendizaje() {
		String nivelObjetivoStr = (String) comboNivelObjetivo.getSelectedItem();
		NivelConocimiento nivelObjetivo = NivelConocimiento.valueOf(nivelObjetivoStr);
		
		new Thread(() -> {
			SwingUtilities.invokeLater(() -> {
				panelResultado.removeAll();
				JLabel labelCargando = new JLabel("Buscando rutas de aprendizaje...");
				labelCargando.setFont(CUERPO_GRANDE);
				labelCargando.setForeground(AZUL_CLARO);
				labelCargando.setHorizontalAlignment(JLabel.CENTER);
				panelResultado.add(labelCargando, BorderLayout.CENTER);
				panelResultado.revalidate();
				panelResultado.repaint();
			});
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			List<List<Curso>> rutas = busquedaRecursivaRutas(nivelObjetivo);

			
			SwingUtilities.invokeLater(() -> {
				panelResultado.removeAll();
				panelResultado.revalidate();
				panelResultado.repaint();
			});
			
			System.out.println(rutas.toString());
			
		}).start();
	}
	
	private List<List<Curso>> busquedaRecursivaRutas(NivelConocimiento nivelObjetivo) {

		List<Curso> todosCursos = MainEleutradia.listaCursos;

		List<List<Curso>> result = new ArrayList<>();

		busquedaRecursivaAux(result, new ArrayList<>(), NivelConocimiento.PRINCIPIANTE, 
				nivelObjetivo, todosCursos);
		
		return result;
	}
	
	private void busquedaRecursivaAux(List<List<Curso>> result, List<Curso> rutaActual,
			NivelConocimiento nivelActual, NivelConocimiento nivelObjetivo, 
			List<Curso> todosCursos) {
		
		if (nivelActual == nivelObjetivo && !rutaActual.isEmpty()) {
			result.add(new ArrayList<>(rutaActual));
			return;
		}
		
		if (nivelActual.ordinal() > nivelObjetivo.ordinal()) {
			return;
		}
		
		NivelConocimiento siguienteNivel = obtenerSiguienteNivel(nivelActual);
		
		if (siguienteNivel == null) {
			return;
		}
		
		for (Curso curso : todosCursos) {
			if (curso.getNivelRecomendado() == siguienteNivel && !rutaActual.contains(curso)) {
				rutaActual.add(curso);
				
				busquedaRecursivaAux(result, rutaActual, siguienteNivel, nivelObjetivo, todosCursos);
				
				rutaActual.remove(rutaActual.size() - 1);
			}
		}
	}
	
	private NivelConocimiento obtenerSiguienteNivel(NivelConocimiento nivelActual) {
		NivelConocimiento[] niveles = NivelConocimiento.values();
		int indiceActual = nivelActual.ordinal();
		
		if (indiceActual < niveles.length - 1) {
			return niveles[indiceActual + 1];
		}
		
		return null;
	}


	public void addAccionVolver(ActionListener listener) {
		this.accionVolver = listener;
	}
}
