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
import javax.swing.Box;
import javax.swing.BoxLayout;
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
	
	private JPanel panelBotonTodosLosCursos, panelBotonMisCursos;
	private JButton botonTodosLosCursos, botonMisCursos;
	
	private JPanel panelTodosLosCursos, panelMisCursos;
	
	private JPanel panelCursosInfo;
	private Curso cursoInfo;
	
	private JProgressBar progressBarRacha;
	
    private static final Color COLOR_FONDO_SECUNDARIO = new Color(48, 46, 43); // Gris claro
    private static final Color COLOR_FONDO_PRINCIPAL = new Color(38, 37, 34); // Gris medio
    private static final Color COLOR_BOTON_INACTIVO = new Color(33, 32, 29); // Gris oscuro
    private static final Color COLOR_TEXTO = new Color(223, 223, 222); // Gris oscuro
    private static final Color COLOR_TARJETA_COMPLETADA = new Color(220, 255, 220); // Verde claro
    
    private ArrayList<Curso> listaCursos;
	
	public PanelAprender(Particular usuario) {
		
		usuarioLogeado = usuario;
		
		this.setLayout(new BorderLayout());
		this.setBackground(COLOR_FONDO_SECUNDARIO);
		this.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		
		layoutPanelAprender = new CardLayout();
		panelAprender = new JPanel(layoutPanelAprender);
		
		JPanel panelCursos = crearPanelCursos();
		panelCursosInfo = new JPanel(new BorderLayout(20, 20));
		panelCursosInfo.setBackground(COLOR_FONDO_SECUNDARIO);
		
		panelAprender.add(panelCursos, "PANEL_CURSOS");
		panelAprender.add(panelCursosInfo, "PANEL_CURSOS_INFO");
		
		this.add(panelAprender, BorderLayout.CENTER);
		
		actualizarPanelTodosLosCursos();
		actualizarProgressBar();
		layoutPanelAprender.show(panelAprender, "PANEL_CURSOS");
				
	}

	private JPanel crearPanelCursos() {

		JPanel panelPrincipalPestanasCurso = new JPanel(new BorderLayout(20, 20));
		panelPrincipalPestanasCurso.setBackground(COLOR_FONDO_SECUNDARIO);
		
		JPanel panelPestanasCursos = new JPanel(new BorderLayout());
		JPanel panelPestanas = crearPanelPestanas();
		panelPestanasCursos.add(panelPestanas, BorderLayout.NORTH);
		
		
		layoutContenedorCentro = new CardLayout();
		panelContenedorCentro = new JPanel(layoutContenedorCentro);
		panelContenedorCentro.setBackground(COLOR_FONDO_PRINCIPAL);
		panelContenedorCentro.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		
		panelTodosLosCursos = new JPanel(new GridLayout(0, 2, 20, 0));
		panelTodosLosCursos.setBackground(COLOR_FONDO_PRINCIPAL);
		JScrollPane scrollTodos = new JScrollPane(panelTodosLosCursos);
		scrollTodos.setBorder(BorderFactory.createEmptyBorder());
		
		panelMisCursos = new JPanel(new GridLayout(0, 2, 0, 20));
		panelMisCursos.setBackground(COLOR_FONDO_PRINCIPAL);
		JScrollPane scrollMis = new JScrollPane(panelMisCursos);
		scrollMis.setBorder(BorderFactory.createEmptyBorder());
		
		panelContenedorCentro.add(scrollTodos, "TODOS_LOS_CURSOS");
		panelContenedorCentro.add(scrollMis, "MIS_CURSOS");
		
		panelPestanasCursos.add(panelContenedorCentro, BorderLayout.CENTER);
		
		panelPrincipalPestanasCurso.add(panelPestanasCursos, BorderLayout.CENTER);
		
		JPanel panelDerecho = new JPanel(new GridLayout(2, 1, 10, 10));
		panelDerecho.setBackground(COLOR_FONDO_PRINCIPAL);
		panelDerecho.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelDerecho.setPreferredSize(new Dimension(160, 0));
		
		JPanel panelProgreso = new JPanel();
		panelProgreso.setLayout(new BoxLayout(panelProgreso, BoxLayout.Y_AXIS));
		panelProgreso.setMaximumSize(new Dimension(160, 50));
		panelProgreso.setBackground(COLOR_FONDO_PRINCIPAL);
		JLabel labelProgreso = new JLabel("Progreso Cursos");
		labelProgreso.setForeground(COLOR_TEXTO);
		labelProgreso.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		labelProgreso.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		progressBarRacha = new JProgressBar(0, 100);
		progressBarRacha.setStringPainted(true);
		progressBarRacha.setMaximumSize(new Dimension(100, 30));
		
		panelProgreso.add(labelProgreso);
		panelProgreso.add(progressBarRacha);
		panelDerecho.add(panelProgreso);
		
		panelPrincipalPestanasCurso.add(panelDerecho, BorderLayout.EAST);
		
		return panelPrincipalPestanasCurso;
	}
	
	private JPanel crearPanelPestanas() {
		
		JPanel panel = new JPanel(new GridLayout(1, 2));
		panel.setBackground(COLOR_FONDO_PRINCIPAL);
		
		botonMisCursos = crearBotonPestanas("Mis Cursos");
		botonTodosLosCursos = crearBotonPestanas("Todos los Cursos");
		
		botonMisCursos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panelBotonTodosLosCursos.setBackground(COLOR_BOTON_INACTIVO);
				panelBotonMisCursos.setBackground(COLOR_FONDO_PRINCIPAL);
				actualizarPanelMisCursos();
				layoutContenedorCentro.show(panelContenedorCentro, "MIS_CURSOS");				
			}
		});
		
		botonTodosLosCursos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panelBotonMisCursos.setBackground(COLOR_BOTON_INACTIVO);
				panelBotonTodosLosCursos.setBackground(COLOR_FONDO_PRINCIPAL);
				actualizarPanelTodosLosCursos();
				layoutContenedorCentro.show(panelContenedorCentro, "TODOS_LOS_CURSOS");				
			}
		});
		
		panelBotonTodosLosCursos = new JPanel();
		panelBotonTodosLosCursos.setBackground(COLOR_FONDO_PRINCIPAL);
		panelBotonMisCursos = new JPanel();
		panelBotonMisCursos.setBackground(COLOR_BOTON_INACTIVO);
				
		panelBotonTodosLosCursos.add(botonTodosLosCursos);
		panelBotonMisCursos.add(botonMisCursos);
		
		panel.add(panelBotonTodosLosCursos);
		panel.add(panelBotonMisCursos);
		
		return panel;
	}
	
	private JButton crearBotonPestanas(String texto) {
		
		JButton boton = new JButton(texto);
		boton.setForeground(COLOR_TEXTO);
		boton.setPreferredSize(new Dimension(180, 40));
		boton.setContentAreaFilled(false);
		boton.setBorder(BorderFactory.createEmptyBorder());
		boton.setFocusPainted(false);
		return boton;
	}
	
	private void actualizarPanelTodosLosCursos() {
		
		panelTodosLosCursos.removeAll();
		
		List<Curso> listaCursos = MainEleutradia.listaCursos;
		
		for (Curso curso : listaCursos) {
			
			JButton botonCurso = new JButton(curso.getNombre());
			botonCurso.setPreferredSize(new Dimension(220, 150));
			botonCurso.setMaximumSize(new Dimension(220, 150));
			
			botonCurso.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					cursoInfo = curso;
					actualizarPanelInfoCurso();
					layoutPanelAprender.show(panelAprender, "PANEL_CURSOS_INFO");
				}
			});
			
			JPanel panelEnvolver = new JPanel(new GridBagLayout());
			panelEnvolver.setBackground(COLOR_FONDO_PRINCIPAL);
			panelEnvolver.add(botonCurso);
			panelTodosLosCursos.add(panelEnvolver);
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
		
		JPanel panelInfoCentro = new JPanel(new BorderLayout(20, 20));
		panelInfoCentro.setBackground(COLOR_FONDO_PRINCIPAL);
		panelInfoCentro.add(new JLabel("Informacion sobre el curso " + cursoInfo.getNombre()));
		panelCursosInfo.add(panelInfoCentro, BorderLayout.CENTER);
		
		JPanel panelLateral = new JPanel(new GridLayout());
		panelLateral.setPreferredSize(new Dimension(160, 0));
		
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
		
		JButton botonVolver = new JButton("Volver");
		
		botonVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				layoutPanelAprender.show(panelAprender, "PANEL_CURSOS");
			}
		});
		
		
		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
		panelBotones.setMaximumSize(new Dimension(160, 50));
		panelBotones.setBackground(COLOR_FONDO_PRINCIPAL);
		
		panelBotones.add(botonApuntar);
		panelBotones.add(Box.createRigidArea(new Dimension(0, 200)));
		panelBotones.add(botonVolver);
		
		panelLateral.add(panelBotones);
		
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
