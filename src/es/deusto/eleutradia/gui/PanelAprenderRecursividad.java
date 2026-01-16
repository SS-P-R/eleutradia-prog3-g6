package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import static es.deusto.eleutradia.gui.style.UITema.*;

public class PanelAprenderRecursividad extends JPanel {
	

	private static final long serialVersionUID = 1L;
	
	private JComboBox<String> comboNivelObjetivo;
	private JButton btnVolver;
	private ActionListener accionVolver;
	
	public PanelAprenderRecursividad() {

		setLayout(new BorderLayout(20, 20));
		setBackground(MAIN_FONDO);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		JPanel panelTitulo = crearPanelTitulo();
		add(panelTitulo, BorderLayout.NORTH);
		
		JPanel panelCentral = crearPanelFormulario();
		add(panelCentral, BorderLayout.CENTER);
		
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


	public void addAccionVolver(ActionListener listener) {
		this.accionVolver = listener;
	}
}
