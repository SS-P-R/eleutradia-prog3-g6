package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class PanelAprender extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JPanel panelPrincipal, panelProgresoFiltros;
	
	private JTable tablaCursos;
	private DefaultTableModel modeloTabla;
	private JLabel labelContenidos;
	
	private JButton botonTodosCursos, botonMisCursos;
	
	private JLabel labelCompletados;
	private JProgressBar progressBarRacha;
	
	private JTextField textoBusqueda;
	private JCheckBox checkNivelBasico, checkNivelMedio, checkNivelAvanzado;
	
	public PanelAprender() {
		
		this.setLayout(new BorderLayout(10, 10));
		this.setBorder(BorderFactory.createEmptyBorder());
		
		panelPrincipal = crearPanelPrincipal();
		panelProgresoFiltros = crearPanelProgresoFiltros();

		this.add(panelPrincipal, BorderLayout.CENTER);
		this.add(panelProgresoFiltros, BorderLayout.EAST);
		
	}

	private JPanel crearPanelPrincipal() {

		JPanel panel = new JPanel(new BorderLayout(10, 10));
		
		
		return panel;
	}

	private JPanel crearPanelProgresoFiltros() {
		// TODO Auto-generated method stub
		return null;
	}


	
}
