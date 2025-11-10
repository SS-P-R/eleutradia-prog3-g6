package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import es.deusto.eleutradia.domain.Curso;
import es.deusto.eleutradia.domain.Leccion;
import es.deusto.eleutradia.domain.Modulo;
import es.deusto.eleutradia.domain.Particular;
import es.deusto.eleutradia.main.MainEleutradia;

public class PanelAprender extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static final Dimension TAMANO_TARJETA = new Dimension(180, 160);
	private static final int ALTURA_IMAGEN = 90;
	
	private static final Font FONT_TITULO = new Font("Segoe UI", Font.BOLD, 14);
	private static final Font FONT_NIVEL = new Font("Segoe UI", Font.PLAIN, 12);
	
	private static final int ALTURA_IMAGEN_DETALLE = 200;
	private static final Font FONT_CURSO_TITULO = new Font("Segoe UI", Font.BOLD, 22);
	private static final Font FONT_MODULO_TITULO = new Font("Segoe UI", Font.BOLD, 16);
	private static final Font FONT_LECCION = new Font("Segoe UI", Font.PLAIN, 14);
	private static final Font FONT_ACCION_TITULO = new Font("Segoe UI", Font.BOLD, 16);
	
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
	
    private static final Color COLOR_FONDO_SECUNDARIO = new Color(248, 249, 250); // Blanco
    private static final Color COLOR_FONDO_PRINCIPAL = Color.WHITE; // Gris claro
    private static final Color COLOR_BOTON_INACTIVO = new Color(223, 223, 222); // Gris medio
    private static final Color COLOR_TEXTO = new Color(169, 168, 162);// Gris oscuro
    private static final Color COLOR_BOTON_TEXTO = Color.WHITE;// Gris oscuro
	private static final Color COLOR_BOTON_EXITO = new Color(40, 167, 69);
    private static final Color COLOR_BOTON_CURSOS = new Color(249, 249, 249); // Verde claro
    private static final Color COLOR_BOTON_VOLVER = new Color(100, 100, 100); // Verde medio
    private static final Color COLOR_BOTON_APUNTAR = new Color(0, 100, 255); // Azul medio
    private static final Color COLOR_BORDE = new Color(222, 226, 230);
	
	public PanelAprender(Particular usuario) {
		
		usuarioLogeado = usuario;
		
		this.setLayout(new BorderLayout());
		this.setBackground(COLOR_FONDO_SECUNDARIO);
		this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
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
		panelPestanasCursos.setBorder(BorderFactory.createLineBorder(COLOR_BORDE, 1));
		JPanel panelPestanas = crearPanelPestanas();
		panelPestanasCursos.add(panelPestanas, BorderLayout.NORTH);
		
		
		layoutContenedorCentro = new CardLayout();
		panelContenedorCentro = new JPanel(layoutContenedorCentro);
		panelContenedorCentro.setBackground(COLOR_FONDO_PRINCIPAL);
		panelContenedorCentro.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		
		panelTodosLosCursos = new JPanel(new GridLayout(0, 2, 20, 20));
		panelTodosLosCursos.setBackground(COLOR_FONDO_PRINCIPAL);
		JScrollPane scrollTodos = new JScrollPane(panelTodosLosCursos);
		scrollTodos.setBorder(BorderFactory.createEmptyBorder());
		
		panelMisCursos = new JPanel(new GridLayout(0, 2, 20, 20));
		panelMisCursos.setBackground(COLOR_FONDO_PRINCIPAL);
		JScrollPane scrollMis = new JScrollPane(panelMisCursos);
		scrollMis.setBorder(BorderFactory.createEmptyBorder());
		
		panelContenedorCentro.add(scrollTodos, "TODOS_LOS_CURSOS");
		panelContenedorCentro.add(scrollMis, "MIS_CURSOS");
		
		panelPestanasCursos.add(panelContenedorCentro, BorderLayout.CENTER);
		
		panelPrincipalPestanasCurso.add(panelPestanasCursos, BorderLayout.CENTER);
		
		JPanel panelDerecho = new JPanel(new BorderLayout());
		panelDerecho.setBackground(COLOR_FONDO_PRINCIPAL);
		panelDerecho.setBorder(BorderFactory.createLineBorder(COLOR_BORDE, 1));
		panelDerecho.setPreferredSize(new Dimension(160, 0));
		
		JPanel panelProgreso = new JPanel();
		panelProgreso.setLayout(new BoxLayout(panelProgreso, BoxLayout.Y_AXIS));
		panelProgreso.setBackground(COLOR_FONDO_PRINCIPAL);
		panelProgreso.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
		
		JLabel labelProgreso = new JLabel("Progreso Cursos");
		labelProgreso.setForeground(COLOR_TEXTO);
		labelProgreso.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		progressBarRacha = new JProgressBar(0, 100);
		progressBarRacha.setStringPainted(true);
		progressBarRacha.setMaximumSize(new Dimension(120, 30));
		
		panelProgreso.add(labelProgreso);
		panelProgreso.add(Box.createRigidArea(new Dimension(0, 10)));
		panelProgreso.add(progressBarRacha);
		
		panelDerecho.add(panelProgreso, BorderLayout.NORTH);
		
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
			
			JPanel panelCurso = crearPanelCurso(curso);
			
			JPanel panelEnvolver = new JPanel(new GridBagLayout());
			panelEnvolver.setBackground(COLOR_FONDO_PRINCIPAL);
			
			panelEnvolver.add(panelCurso);
			panelTodosLosCursos.add(panelEnvolver);
		}
		
		panelTodosLosCursos.revalidate();
		panelTodosLosCursos.repaint();
		
	}
	
	private void actualizarPanelMisCursos() {
		
		panelMisCursos.removeAll();
		
		List<Curso> listaCursos = usuarioLogeado.getCursos();
		
		if (listaCursos.isEmpty()) {
			
			panelMisCursos.setLayout(new BorderLayout());
			
			JLabel labelVacio = new JLabel("Aún no te has inscrito a ningun curso");
			labelVacio.setHorizontalAlignment(SwingConstants.CENTER);
			labelVacio.setForeground(COLOR_TEXTO);
			labelVacio.setFont(FONT_NIVEL);
			
			panelMisCursos.add(labelVacio, BorderLayout.CENTER);
			
			
		} else {
			
			panelMisCursos.setLayout(new GridLayout(0, 2, 20, 20));
			
			for (Curso curso : listaCursos) {
				
				JPanel panelCurso = crearPanelCurso(curso);
				
				JPanel panelEnvolver = new JPanel(new GridBagLayout());
				panelEnvolver.setBackground(COLOR_FONDO_PRINCIPAL);
				
				
				panelEnvolver.add(panelCurso);
				panelMisCursos.add(panelEnvolver);
			}
			
		}
		
		panelTodosLosCursos.revalidate();
		panelTodosLosCursos.repaint();
		
	}
	
	private JPanel crearPanelCurso(Curso curso) {
		
		JPanel panelCurso = new JPanel(new BorderLayout());
		panelCurso.setPreferredSize(TAMANO_TARJETA);
		panelCurso.setMaximumSize(TAMANO_TARJETA);
		panelCurso.setBackground(COLOR_BOTON_CURSOS);
		panelCurso.setBorder(BorderFactory.createLineBorder(COLOR_BORDE, 1));
		
		try {
			
			ImageIcon icono = new ImageIcon(getClass().getResource(curso.getRutaImagen()));
			Image imagen = icono.getImage().getScaledInstance(TAMANO_TARJETA.width, ALTURA_IMAGEN, Image.SCALE_SMOOTH);
			JLabel labelImagen = new JLabel(new ImageIcon(imagen));

			panelCurso.add(labelImagen, BorderLayout.NORTH);
			
		} catch (Exception e) {

		}
		
		JPanel panelInfotexto = new JPanel();
		panelInfotexto.setLayout(new BoxLayout(panelInfotexto, BoxLayout.Y_AXIS));
		panelInfotexto.setBackground(COLOR_BOTON_CURSOS);
		panelInfotexto.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
		
		JLabel labelTitulo = new JLabel(curso.getNombre());
		labelTitulo.setFont(FONT_TITULO);
		labelTitulo.setForeground(Color.BLACK);
		
		String textoNivel = "Nivel: No especificado";
		if (curso.getNivelRecomendado() != null) {
			textoNivel = "Nivel: " + curso.getNivelRecomendado().toString();
		}
		
		JLabel labelNivel = new JLabel(textoNivel);
		labelNivel.setFont(FONT_NIVEL);
		labelNivel.setForeground(COLOR_TEXTO);
		
		panelInfotexto.add(labelTitulo);
		panelInfotexto.add(Box.createRigidArea(new Dimension(0, 4)));
		panelInfotexto.add(labelNivel);
		
		panelCurso.add(panelInfotexto, BorderLayout.CENTER);
		
		panelCurso.addMouseListener(new MouseAdapter() {
		
			@Override
			public void mouseEntered(MouseEvent e) {
				panelCurso.setBorder(BorderFactory.createLineBorder(COLOR_BOTON_APUNTAR, 1));
				
				int cantidadModulos = 0;
				int cantidadLeciones = 0;
				for (Modulo modulo : curso.getModulos()) {
					cantidadModulos++;
					for (Leccion leccion : modulo.getLecciones()) {
						cantidadLeciones++;
					}
				}
				panelCurso.setToolTipText("Módulos: " + cantidadModulos + " | Lecciones:" + cantidadLeciones);
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				panelCurso.setBorder(BorderFactory.createLineBorder(COLOR_BORDE, 1));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				cursoInfo = curso;
				actualizarPanelInfoCurso();
				layoutPanelAprender.show(panelAprender, "PANEL_CURSOS_INFO");
			}
		
		});
		
		return panelCurso;
	}
	
	private void actualizarPanelInfoCurso() {
		
		panelCursosInfo.removeAll();
		
		JScrollPane panelInfoCentro = mostrarInfoCursos(cursoInfo);
		panelCursosInfo.add(panelInfoCentro, BorderLayout.CENTER);
		

		JPanel panelLateral = crearPanelLateralAcciones();
		panelCursosInfo.add(panelLateral, BorderLayout.EAST);
		
		panelCursosInfo.revalidate();
		panelCursosInfo.repaint();
		
	}
	
	private JPanel crearPanelLateralAcciones() {
		
		JPanel panelLateral = new JPanel(new BorderLayout(0, 10));
		panelLateral.setBackground(COLOR_FONDO_SECUNDARIO);
		panelLateral.setPreferredSize(new Dimension(160, 0));
		
		JPanel panelTexto = new JPanel();
		panelTexto.setLayout(new BoxLayout(panelTexto, BoxLayout.Y_AXIS));
		panelTexto.setBackground(COLOR_FONDO_PRINCIPAL);
		panelTexto.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(COLOR_BORDE),
				BorderFactory.createEmptyBorder(15, 15, 15, 15)
		));

		if (usuarioLogeado.getCursos().contains(cursoInfo)) {
			
			JLabel labelInscrito = new JLabel("Inscrito", SwingConstants.CENTER);
			labelInscrito.setFont(FONT_TITULO); 
			labelInscrito.setMinimumSize(new Dimension(150, 45));
			labelInscrito.setPreferredSize(new Dimension(150, 45));
			labelInscrito.setMaximumSize(new Dimension(150, 45));
			labelInscrito.setBackground(COLOR_BOTON_EXITO);
			labelInscrito.setForeground(Color.WHITE);
			labelInscrito.setOpaque(true); 
			
			labelInscrito.setAlignmentX(Component.CENTER_ALIGNMENT);
			
			panelTexto.add(labelInscrito);
			
		} else {
			
			JLabel labelTitulo = new JLabel("¡Únete al curso!");
			labelTitulo.setFont(FONT_ACCION_TITULO);;
			labelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
			labelTitulo.setForeground(Color.BLACK);
			
			panelTexto.add(labelTitulo);
			panelTexto.add(Box.createRigidArea(new Dimension(0, 15)));
			
			JButton botonApuntar = new JButton("Apuntarse");
			botonApuntar.setMinimumSize(new Dimension(150, 45));
			botonApuntar.setPreferredSize(new Dimension(150, 45));
			botonApuntar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
			botonApuntar.setBackground(COLOR_BOTON_APUNTAR);
			botonApuntar.setForeground(COLOR_BOTON_TEXTO);
			botonApuntar.setAlignmentX(Component.CENTER_ALIGNMENT);
			botonApuntar.setFocusPainted(false);
			
			botonApuntar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					usuarioLogeado.addCurso(cursoInfo);
					JOptionPane.showMessageDialog(panelCursosInfo, "Te has inscrito a " + cursoInfo.getNombre() + "!");
					
					actualizarPanelInfoCurso();					
					actualizarProgressBar();
				}
			});
			
			panelTexto.add(botonApuntar);
			
		}

		JPanel panelVolver = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panelVolver.setBackground(COLOR_FONDO_SECUNDARIO);
		
		JButton botonVolver = new JButton("Volver");
		botonVolver.setMinimumSize(new Dimension(150, 45));
		botonVolver.setPreferredSize(new Dimension(150, 45));
		botonVolver.setBackground(COLOR_BOTON_VOLVER);
		botonVolver.setForeground(COLOR_BOTON_TEXTO);
		botonVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
		botonVolver.setFocusPainted(false);
		
		botonVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				layoutPanelAprender.show(panelAprender, "PANEL_CURSOS");
			}
		});
		
		panelVolver.add(botonVolver);
		
		panelLateral.add(panelTexto, BorderLayout.NORTH);
		panelLateral.add(botonVolver, BorderLayout.SOUTH);
		
		return panelLateral;
	}
	
	private JScrollPane mostrarInfoCursos(Curso curso) {
		
		JPanel panelContenido = new JPanel();
		panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
		panelContenido.setBackground(COLOR_FONDO_PRINCIPAL);
		panelContenido.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		try {
			
			ImageIcon icono = new ImageIcon(getClass().getResource(curso.getRutaImagen()));
			Image imagen = icono.getImage().getScaledInstance(-1, ALTURA_IMAGEN_DETALLE, Image.SCALE_SMOOTH);
			JLabel labelImagen = new JLabel(new ImageIcon(imagen));
			labelImagen.setAlignmentX(Component.LEFT_ALIGNMENT);
			labelImagen.setHorizontalAlignment(SwingConstants.CENTER); 
			labelImagen.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0)); 

			panelContenido.add(labelImagen);
		} catch (Exception e) {
			
		}
		
		JLabel labelCurso = new JLabel(curso.getNombre());
		labelCurso.setFont(FONT_CURSO_TITULO);
		labelCurso.setForeground(Color.BLACK);
		labelCurso.setAlignmentX(Component.LEFT_ALIGNMENT);
		labelCurso.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		panelContenido.add(labelCurso);
		
		int numeroModulo = 1;
		int numeroLeccion = 1;
		
		for (Modulo modulo : curso.getModulos()) {
			
			JLabel labelModulo = new JLabel("   " + numeroModulo + ". Módulo: " + modulo.getNombre());
			labelModulo.setFont(FONT_MODULO_TITULO);
			labelModulo.setForeground(Color.BLACK);
			labelModulo.setAlignmentX(Component.LEFT_ALIGNMENT);
			labelModulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
			panelContenido.add(labelModulo);
			numeroLeccion = 1;
			
			for (Leccion leccion : modulo.getLecciones()) {
				JLabel labelLeccion = new JLabel("      " + numeroModulo + "." + numeroLeccion + ". Lección: " + leccion.getTitulo());
				labelLeccion.setFont(FONT_LECCION);
				labelLeccion.setForeground(COLOR_TEXTO);
				labelLeccion.setAlignmentX(Component.LEFT_ALIGNMENT);
				labelLeccion.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
				panelContenido.add(labelLeccion);
				numeroLeccion++;
			}
			
			numeroModulo++;
		}
		
		panelContenido.add(Box.createVerticalGlue());
		
		JScrollPane scrollPane = new JScrollPane(panelContenido);
		scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_BORDE));
		
		return scrollPane;
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
