package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
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
import es.deusto.eleutradia.domain.Particular;
import es.deusto.eleutradia.main.MainEleutradia;

public class PanelAprender extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Particular usuarioLogeado;
	
	private CardLayout layoutPanelAprender;
	private JPanel panelAprender;
	
	private CardLayout layoutContenedorCentro;
	private JPanel panelContenedorCentro;
	
	private JPanel panelTodosLosCursos, panelMisCursos;
	private JButton botonTodosLosCursos, botonMisCursos;
	
	private JPanel panelCursosInfo;
	private Curso cursoInfo;
	
	private JProgressBar progressBarRacha;
	
    private static final Color COLOR_FONDO_PRINCIPAL = Color.WHITE;
    private static final Color COLOR_BOTON_INACTIVO = new Color(220, 220, 220); // Gris claro
    private static final Color COLOR_TARJETA_COMPLETADA = new Color(220, 255, 220); // Verde claro
    
    private ArrayList<Curso> listaCursos;
	
	public PanelAprender(Particular usuario) {
		
		usuarioLogeado = usuario;
		
		this.setLayout(new BorderLayout(10, 10));
		this.setBorder(BorderFactory.createEmptyBorder());
		
		layoutPanelAprender = new CardLayout();
		panelAprender = new JPanel(layoutPanelAprender);
		
		JPanel panelCursos = crearPanelCursos();
		panelCursosInfo = new JPanel(new BorderLayout(10, 10));
		
		panelAprender.add(panelCursos, "PANEL_CURSOS");
		panelAprender.add(panelCursosInfo, "PANEL_CURSOS_INFO");
		
		this.add(panelAprender, BorderLayout.CENTER);
		
		actualizarPanelTodosLosCursos();
		actualizarProgressBar();
		layoutPanelAprender.show(panelAprender, "PANEL_CURSOS");
				
	}

	private JPanel crearPanelCursos() {

		JPanel panelPrincipalPestanasCurso = new JPanel(new BorderLayout(20, 20));
		panelPrincipalPestanasCurso.setBackground(COLOR_FONDO_PRINCIPAL);
		
		JPanel panelPestanas = crearPanelPestanas();
		panelPrincipalPestanasCurso.add(panelPestanas, BorderLayout.NORTH);
		
		
		layoutContenedorCentro = new CardLayout();
		panelContenedorCentro = new JPanel(layoutContenedorCentro);
		
		panelTodosLosCursos = new JPanel(new GridLayout(0, 2, 10, 10));
		JScrollPane scrollTodos = new JScrollPane(panelTodosLosCursos);
		
		panelMisCursos = new JPanel(new GridLayout(0, 2, 10, 10));
		JScrollPane scrollMis = new JScrollPane(panelMisCursos);
		
		panelContenedorCentro.add(scrollTodos, "TODOS_LOS_CURSOS");
		panelContenedorCentro.add(scrollMis, "MIS_CURSOS");
		
		panelPrincipalPestanasCurso.add(panelContenedorCentro, BorderLayout.CENTER);
		
		
		JPanel panelDerecho = new JPanel(new GridLayout(2, 1, 10, 10));
		panelDerecho.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelDerecho.setPreferredSize(new Dimension(160, 0));
		
		JLabel labelProgreso = new JLabel("Progreso Cursos");
		labelProgreso.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		progressBarRacha = new JProgressBar(0, 100);
		progressBarRacha.setStringPainted(true);
		progressBarRacha.setMaximumSize(new Dimension(150, 25));
		
		panelDerecho.add(labelProgreso);
		panelDerecho.add(progressBarRacha);
		
		panelPrincipalPestanasCurso.add(panelDerecho, BorderLayout.EAST);
		
		return panelPrincipalPestanasCurso;
	}
	
	private JPanel crearPanelPestanas() {
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panel.setBackground(COLOR_FONDO_PRINCIPAL);
		
		botonMisCursos = new JButton("Mis Cursos");
		botonTodosLosCursos = new JButton("Todos los Cursos");
		
		botonMisCursos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				actualizarPanelMisCursos();
				layoutContenedorCentro.show(panelContenedorCentro, "MIS_CURSOS");				
			}
		});
		
		botonTodosLosCursos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				actualizarPanelTodosLosCursos();
				layoutContenedorCentro.show(panelContenedorCentro, "TODOS_LOS_CURSOS");				
			}
		});
				
		panel.add(botonTodosLosCursos);
		panel.add(botonMisCursos);
		
		return panel;
	}
	
	private void actualizarPanelTodosLosCursos() {
		
		panelTodosLosCursos.removeAll();
		
		List<Curso> listaCursos = MainEleutradia.listaCursos;
		
		for (Curso curso : listaCursos) {
			
			JButton botonCurso = new JButton(curso.getNombre());
			botonCurso.setPreferredSize(new Dimension(150, 80));
			
			botonCurso.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					cursoInfo = curso;
					actualizarPanelInfoCurso();
					layoutPanelAprender.show(panelAprender, "PANEL_CURSOS_INFO");
				}
			});
			panelTodosLosCursos.add(botonCurso);
		}
		
		panelTodosLosCursos.revalidate();
		panelTodosLosCursos.repaint();
		
	}
	
	private void actualizarPanelMisCursos() {
		
		panelMisCursos.removeAll();
		
		List<Curso> listaCursos = usuarioLogeado.getCursos();
		
		if (listaCursos.isEmpty()) {
			
		} else {
			panelMisCursos.setLayout(new GridLayout(0, 2, 10, 10));
			for (Curso curso : listaCursos) {
				
				JButton botonCurso = new JButton(curso.getNombre());
				botonCurso.setPreferredSize(new Dimension(120, 60));
				
				botonCurso.setEnabled(false);
				panelMisCursos.add(botonCurso);
			}	
		}
		
		panelTodosLosCursos.revalidate();
		panelTodosLosCursos.repaint();
		
	}
	
	private void actualizarPanelInfoCurso() {
		
		panelCursosInfo.removeAll();
		
		JPanel panelInfoCentro = new JPanel(new GridLayout());
		panelInfoCentro.add(new JLabel("Informacion sobre el curso " + cursoInfo.getNombre()));
		panelCursosInfo.add(panelInfoCentro, BorderLayout.CENTER);
		
		JPanel panelLateral = new JPanel(new GridLayout(2, 1, 10, 10));
		
		JButton botonApuntar = new JButton("Apuntarse");
		
		if (usuarioLogeado.getCursos().contains(cursoInfo)) {
			botonApuntar.setEnabled(false);
			botonApuntar.setText("Ya estas inscrito");
		}
		
		botonApuntar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				usuarioLogeado.addCurso(cursoInfo);
				JOptionPane.showMessageDialog(panelCursosInfo, "Te has inscrito a " + cursoInfo.getNombre() + "!");
				
				botonApuntar.setEnabled(false);
				botonApuntar.setText("Ya estas inscrito");
				
				actualizarProgressBar();
			}
		});
		
		panelLateral.add(botonApuntar);
		
		JButton botonVolver = new JButton("Volver");
		
		botonVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				layoutPanelAprender.show(panelAprender, "PANEL_CURSOS");
			}
		});
		
		panelLateral.add(botonVolver);
		
		panelCursosInfo.add(panelLateral, BorderLayout.EAST);
		
		panelCursosInfo.revalidate();
		panelCursosInfo.repaint();
		
	}	
	
	private void actualizarProgressBar() {

		int totalCursos = MainEleutradia.listaCursos.size();
		int misCursos = usuarioLogeado.getCursos().size();
		
		if (totalCursos == 0) {
			
			progressBarRacha.setValue(0);
			progressBarRacha.setString("No hay Cursos");
			
		} else {
			
			int porcentajeBarra = (int) (((double) misCursos / totalCursos) * 100);
			progressBarRacha.setValue(porcentajeBarra);
			progressBarRacha.setString(misCursos + "/" + totalCursos);
		}
	}

}
