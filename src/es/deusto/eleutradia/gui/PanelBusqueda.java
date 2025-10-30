package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class PanelBusqueda extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel labelTituloBusqueda;
	private JTextField campoBusqueda;
	private JTable tablaResultados;
	private DefaultTableModel modeloTablaResultado;
	
	public PanelBusqueda() {
		this.setLayout(new BorderLayout(10, 10));
		this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		
		JPanel panelBusqueda = new JPanel(new BorderLayout(5, 5));
		
		this.add(new JLabel("Prueba ventana busqueda"));
		
	}

}
