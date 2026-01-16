package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import static es.deusto.eleutradia.gui.style.UITema.*;

public class PanelAprenderRecursividad extends JPanel {
	

	private static final long serialVersionUID = 1L;
	
	private ActionListener accionVolver;
	
	public PanelAprenderRecursividad() {

		setLayout(new BorderLayout(20, 20));
		setBackground(MAIN_FONDO);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

	}
	
	
	public void addAccionVolver(ActionListener listener) {
		this.accionVolver = listener;
	}
}
