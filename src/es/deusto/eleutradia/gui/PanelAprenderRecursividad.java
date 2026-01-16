package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import static es.deusto.eleutradia.gui.style.UITema.*;

public class PanelAprenderRecursividad extends JPanel {
	

	private static final long serialVersionUID = 1L;
	
	private JButton btnVolver;
	private ActionListener accionVolver;
	
	public PanelAprenderRecursividad() {

		setLayout(new BorderLayout(20, 20));
		setBackground(MAIN_FONDO);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		JPanel panelBotones = crearPanelBotones();
		add(panelBotones, BorderLayout.EAST);

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
