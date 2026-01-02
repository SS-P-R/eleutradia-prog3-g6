package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
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
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicScrollBarUI;

import es.deusto.eleutradia.domain.Curso;
import es.deusto.eleutradia.domain.Leccion;
import es.deusto.eleutradia.domain.Modulo;
import es.deusto.eleutradia.domain.Particular;
import es.deusto.eleutradia.main.MainEleutradia;

import static es.deusto.eleutradia.gui.style.UITema.*;

public class PanelAprender extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static final Dimension TAMANO_TARJETA = new Dimension(180, 160);
	private static final int ALTURA_IMAGEN = 90;
	private static final int ALTURA_IMAGEN_DETALLE = 200;
	
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
		
	public PanelAprender(Particular usuario) {
		
		usuarioLogeado = usuario;
		
		this.setLayout(new BorderLayout());
		this.setBackground(MAIN_FONDO);
		this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		layoutPanelAprender = new CardLayout();
		panelAprender = new JPanel(layoutPanelAprender);
		
		JPanel panelCursos = crearPanelCursos();
		
		panelCursosInfo = new JPanel(new BorderLayout(20, 20));
		panelCursosInfo.setBackground(MAIN_FONDO);
		
		panelAprender.add(panelCursos, "PANEL_CURSOS");
		panelAprender.add(panelCursosInfo, "PANEL_CURSOS_INFO");
		
		this.add(panelAprender, BorderLayout.CENTER);
		
		actualizarPanelTodosLosCursos();
		actualizarProgressBar();
		layoutPanelAprender.show(panelAprender, "PANEL_CURSOS");
				
	}

	private JPanel crearPanelCursos() {

		JPanel panelPrincipalPestanasCurso = new JPanel(new BorderLayout(20, 20));
		panelPrincipalPestanasCurso.setBackground(MAIN_FONDO);
		
		
		JPanel panelPestanasCursos = new JPanel(new BorderLayout());
		panelPestanasCursos.setBorder(BorderFactory.createLineBorder(MAIN_BORDE, 1));
		JPanel panelPestanas = crearPanelPestanas();
		panelPestanasCursos.add(panelPestanas, BorderLayout.NORTH);
		
		
		layoutContenedorCentro = new CardLayout();
		panelContenedorCentro = new JPanel(layoutContenedorCentro);
		panelContenedorCentro.setBackground(Color.WHITE);
		panelContenedorCentro.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		
		panelTodosLosCursos = new JPanel(new GridLayout(0, 2, 20, 20));
		panelTodosLosCursos.setBackground(Color.WHITE);
		JScrollPane scrollTodos = new JScrollPane(panelTodosLosCursos);
		scrollTodos.getVerticalScrollBar().setUI(crearScrollBarUI());
		scrollTodos.getHorizontalScrollBar().setUI(crearScrollBarUI());
		scrollTodos.setBorder(BorderFactory.createEmptyBorder());
		
		panelMisCursos = new JPanel(new GridLayout(0, 2, 20, 20));
		panelMisCursos.setBackground(Color.WHITE);
		JScrollPane scrollMis = new JScrollPane(panelMisCursos);
		scrollMis.getVerticalScrollBar().setUI(crearScrollBarUI());
		scrollMis.getHorizontalScrollBar().setUI(crearScrollBarUI());
		scrollMis.setBorder(BorderFactory.createEmptyBorder());
		
		panelContenedorCentro.add(scrollTodos, "TODOS_LOS_CURSOS");
		panelContenedorCentro.add(scrollMis, "MIS_CURSOS");
		
		panelPestanasCursos.add(panelContenedorCentro, BorderLayout.CENTER);
		
		panelPrincipalPestanasCurso.add(panelPestanasCursos, BorderLayout.CENTER);
		
		JPanel panelDerecho = new JPanel(new BorderLayout());
		panelDerecho.setBackground(Color.WHITE);
		panelDerecho.setBorder(BorderFactory.createLineBorder(MAIN_BORDE, 1));
		panelDerecho.setPreferredSize(new Dimension(160, 0));
		
		JPanel panelProgreso = new JPanel();
		panelProgreso.setLayout(new BoxLayout(panelProgreso, BoxLayout.Y_AXIS));
		panelProgreso.setBackground(Color.WHITE);
		panelProgreso.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
		
		JLabel labelProgreso = new JLabel("Progreso Cursos");
		labelProgreso.setForeground(GRIS_CLARO);
		labelProgreso.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		progressBarRacha = new JProgressBar(0, 100);
		progressBarRacha.setStringPainted(true);
		progressBarRacha.setMaximumSize(new Dimension(120, 30));
		
		panelProgreso.add(labelProgreso);
		panelProgreso.add(Box.createRigidArea(new Dimension(0, 10)));
		panelProgreso.add(progressBarRacha);
		
		JPanel panelBotonAbajo = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelBotonAbajo.setBackground(Color.WHITE);
		panelBotonAbajo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		
		JButton botonSimulacion = new JButton("Simulación");
		botonSimulacion.setPreferredSize(new Dimension(150, 45));
		botonSimulacion.setBackground(AZUL_CLARO);
		botonSimulacion.setForeground(Color.WHITE);
		botonSimulacion.setFont(SUBTITULO_GRANDE);
		botonSimulacion.setBorderPainted(false);
		botonSimulacion.setContentAreaFilled(false);
		botonSimulacion.setOpaque(true);
		botonSimulacion.setFocusPainted(false);
		
		//IAG (Gemini)
		//MODIFICADO
		botonSimulacion.addActionListener(e -> {
            
            JFrame ventanaPrincipal = (JFrame) SwingUtilities.getWindowAncestor(this);
            
            if (ventanaPrincipal != null) {
            	
            	Container vistaOriginalCompleta = ventanaPrincipal.getContentPane();
            	
                PanelSimulador panelSimulacion = new PanelSimulador();

                panelSimulacion.addAccionVolver(eventoVolver -> {

                    ventanaPrincipal.setContentPane(vistaOriginalCompleta);

                    ventanaPrincipal.revalidate();
                    ventanaPrincipal.repaint();

                    botonSimulacion.setFocusable(false);
                    botonSimulacion.setFocusable(true);
                });
                
                ventanaPrincipal.setContentPane(panelSimulacion);
                ventanaPrincipal.revalidate();
                ventanaPrincipal.repaint();
            }
        });
		//END IAG
		
		botonSimulacion.addMouseListener(myAdapterAzul);
		
		panelBotonAbajo.add(botonSimulacion);
		
		panelDerecho.add(panelProgreso, BorderLayout.NORTH);
		panelDerecho.add(panelBotonAbajo, BorderLayout.SOUTH);
		
		panelPrincipalPestanasCurso.add(panelDerecho, BorderLayout.EAST);
		
		return panelPrincipalPestanasCurso;
	}
	
	private JPanel crearPanelPestanas() {
		
		JPanel panel = new JPanel(new GridLayout(1, 2));
		panel.setBackground(Color.WHITE);
		
		botonMisCursos = crearBotonPestanas("Mis Cursos");
		botonTodosLosCursos = crearBotonPestanas("Todos los Cursos");
		
		botonMisCursos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panelBotonTodosLosCursos.setBackground(GRIS_SUAVE);
				panelBotonMisCursos.setBackground(Color.WHITE);
				actualizarPanelMisCursos();
				layoutContenedorCentro.show(panelContenedorCentro, "MIS_CURSOS");				
			}
		});
		
		botonTodosLosCursos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panelBotonMisCursos.setBackground(GRIS_SUAVE);
				panelBotonTodosLosCursos.setBackground(Color.WHITE);
				actualizarPanelTodosLosCursos();
				layoutContenedorCentro.show(panelContenedorCentro, "TODOS_LOS_CURSOS");				
			}
		});
		
		panelBotonTodosLosCursos = new JPanel();
		panelBotonTodosLosCursos.setBackground(Color.WHITE);
		panelBotonMisCursos = new JPanel();
		panelBotonMisCursos.setBackground(GRIS_SUAVE);
				
		panelBotonTodosLosCursos.add(botonTodosLosCursos);
		panelBotonMisCursos.add(botonMisCursos);
		
		panel.add(panelBotonTodosLosCursos);
		panel.add(panelBotonMisCursos);
		
		return panel;
	}
	
	private JButton crearBotonPestanas(String texto) {
		
		JButton boton = new JButton(texto);
		boton.setForeground(GRIS_CLARO);
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
			panelEnvolver.setBackground(Color.WHITE);
			
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
			
			JLabel labelVacio = new JLabel("Aún no te has inscrito a ningún curso.");
			labelVacio.setHorizontalAlignment(SwingConstants.CENTER);
			labelVacio.setForeground(GRIS_CLARO);
			labelVacio.setFont(CUERPO_PEQUENO);
			
			panelMisCursos.add(labelVacio, BorderLayout.CENTER);
			
			
		} else {
			
			panelMisCursos.setLayout(new GridLayout(0, 2, 20, 20));
			
			for (Curso curso : listaCursos) {
				
				JPanel panelCurso = crearPanelCurso(curso);
				
				JPanel panelEnvolver = new JPanel(new GridBagLayout());
				panelEnvolver.setBackground(Color.WHITE);
				
				
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
		panelCurso.setBackground(MAIN_FONDO);
		panelCurso.setBorder(BorderFactory.createLineBorder(MAIN_BORDE, 1));
		
		try {
			//IAG (Gemini)
			//SIN MODIFICAR
			ImageIcon icono = new ImageIcon(getClass().getResource(curso.getRutaImagen()));
			Image imagen = icono.getImage().getScaledInstance(TAMANO_TARJETA.width, ALTURA_IMAGEN, Image.SCALE_SMOOTH);
			//END IAG
			JLabel labelImagen = new JLabel(new ImageIcon(imagen));
			panelCurso.add(labelImagen, BorderLayout.NORTH);
			
		} catch (Exception e) {
			System.out.println("Error al cargar la imagen: " + curso.getRutaImagen());
		}
		
		JPanel panelInfotexto = new JPanel();
		panelInfotexto.setLayout(new BoxLayout(panelInfotexto, BoxLayout.Y_AXIS));
		panelInfotexto.setBackground(MAIN_FONDO);
		panelInfotexto.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
		
		JLabel labelTitulo = new JLabel(curso.getNombre());
		labelTitulo.setFont(SUBTITULO_MEDIO);
		labelTitulo.setForeground(Color.BLACK);
		
		String textoNivel = "Nivel: No especificado";
		if (curso.getNivelRecomendado() != null) {
			textoNivel = "Nivel: " + curso.getNivelRecomendado().toString();
		}
		
		JLabel labelNivel = new JLabel(textoNivel);
		labelNivel.setFont(CUERPO_PEQUENO);
		labelNivel.setForeground(GRIS_CLARO);
		
		panelInfotexto.add(labelTitulo);
		panelInfotexto.add(Box.createRigidArea(new Dimension(0, 4)));
		panelInfotexto.add(labelNivel);
		
		panelCurso.add(panelInfotexto, BorderLayout.CENTER);
		
		panelCurso.addMouseListener(new MouseAdapter() {
		
			@Override
			public void mouseEntered(MouseEvent e) {
				panelCurso.setBorder(BorderFactory.createLineBorder(AZUL_CLARO, 1));
				
				int cantidadModulos = curso.getModulos().size();
				int cantidadLeciones = 0;
				for (Modulo modulo : curso.getModulos()) {
					cantidadLeciones += modulo.getLecciones().size();
				}
				
				panelCurso.setToolTipText("Módulos: " + cantidadModulos + " | Lecciones:" + cantidadLeciones);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				panelCurso.setBorder(BorderFactory.createLineBorder(MAIN_BORDE, 1));
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
		panelLateral.setBackground(MAIN_FONDO);
		panelLateral.setPreferredSize(new Dimension(160, 0));
		
		JPanel panelTexto = new JPanel();
		panelTexto.setLayout(new BoxLayout(panelTexto, BoxLayout.Y_AXIS));
		panelTexto.setBackground(Color.WHITE);
		panelTexto.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(MAIN_BORDE),
				BorderFactory.createEmptyBorder(15, 15, 15, 15)
		));

		if (usuarioLogeado.getCursos().stream()
		        .anyMatch(c -> c.getId() == cursoInfo.getId())) {
			
			JLabel labelInscrito = new JLabel("Inscrito", SwingConstants.CENTER);
			labelInscrito.setFont(SUBTITULO_MEDIO); 
			labelInscrito.setMinimumSize(new Dimension(150, 45));
			labelInscrito.setPreferredSize(new Dimension(150, 45));
			labelInscrito.setMaximumSize(new Dimension(150, 45));
			labelInscrito.setBackground(VERDE_CLARO);
			labelInscrito.setForeground(Color.WHITE);
			labelInscrito.setOpaque(true); 
			labelInscrito.setAlignmentX(JLabel.CENTER_ALIGNMENT);
			
			panelTexto.add(labelInscrito);
			panelTexto.add(Box.createRigidArea(new Dimension(0, 15)));
			
			JButton botonDesinscribir = new JButton("Desinscribirse");
		    botonDesinscribir.setMinimumSize(new Dimension(150, 45));
		    botonDesinscribir.setPreferredSize(new Dimension(150, 45));
		    botonDesinscribir.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
		    botonDesinscribir.setBackground(GRIS_MEDIO);
		    botonDesinscribir.setForeground(Color.WHITE);
		    botonDesinscribir.setAlignmentX(Component.CENTER_ALIGNMENT);
		    botonDesinscribir.setBorderPainted(false);
		    botonDesinscribir.setContentAreaFilled(false);
		    botonDesinscribir.setOpaque(true);
		    botonDesinscribir.setFocusPainted(false);
		    botonDesinscribir.addActionListener(e -> {

		        int respuesta = JOptionPane.showConfirmDialog(panelCursosInfo,
		            "¿Estás seguro de que quieres desinscribirte de " + cursoInfo.getNombre() + "?",
		            "Confirmar desinscripción",
		            JOptionPane.YES_NO_OPTION);
		        
		        if (respuesta == JOptionPane.YES_OPTION) {

		            JRootPane rootPane = SwingUtilities.getRootPane(panelCursosInfo);
		            PanelCargaThreads panelCarga = new PanelCargaThreads();
		            rootPane.setGlassPane(panelCarga);
		            panelCarga.setVisible(true); // Bloquea la pantalla

		            Runnable logicaDesinscripcion = () -> {

		                usuarioLogeado.removeCurso(cursoInfo);

		                boolean exito = MainEleutradia.getDBManager()
		                    .desinscribirParticularDeCurso(usuarioLogeado.getDni(), cursoInfo.getId());
		                
		                if (exito) {
		                    JOptionPane.showMessageDialog(panelCursosInfo, 
		                        "Te has desinscrito de " + cursoInfo.getNombre());
		                } else {
		                     JOptionPane.showMessageDialog(panelCursosInfo, 
		                        "Error al conectar con la base de datos.", 
		                        "Error", JOptionPane.ERROR_MESSAGE);
		                }
		                
		                actualizarPanelInfoCurso();
		                actualizarProgressBar();
		                actualizarPanelMisCursos();
		            };

		            HiloVisual hilo = new HiloVisual(panelCarga, logicaDesinscripcion, false);
		            hilo.start();
		        }
		    });
		    botonDesinscribir.addMouseListener(myAdapterGris);
		    
		    panelTexto.add(botonDesinscribir);
			
		} else {
			
			JLabel labelTitulo = new JLabel("¡Únete al curso!");
			labelTitulo.setFont(SUBTITULO_GRANDE);
			labelTitulo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
			labelTitulo.setForeground(Color.BLACK);
			
			panelTexto.add(labelTitulo);
			panelTexto.add(Box.createRigidArea(new Dimension(0, 15)));
			
			JButton botonApuntar = new JButton("Apuntarse");
			botonApuntar.setMinimumSize(new Dimension(150, 45));
			botonApuntar.setPreferredSize(new Dimension(150, 45));
			botonApuntar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
			botonApuntar.setBackground(AZUL_CLARO);
			botonApuntar.setForeground(Color.WHITE);
			botonApuntar.setAlignmentX(Component.CENTER_ALIGNMENT);
			botonApuntar.setBorderPainted(false);
	        botonApuntar.setContentAreaFilled(false);
	        botonApuntar.setOpaque(true);
			botonApuntar.setFocusPainted(false);
			botonApuntar.addActionListener(e -> {
			    
			    boolean yaInscrito = usuarioLogeado.getCursos().stream()
			            .anyMatch(c -> c.getId() == cursoInfo.getId());
			    if (yaInscrito) { return; }
			    
			    //IAG (Gemini)
				//SIN MODIFICAR
			    JRootPane rootPane = SwingUtilities.getRootPane(panelCursosInfo);
			    PanelCargaThreads panelCarga = new PanelCargaThreads();
			    rootPane.setGlassPane(panelCarga);
			    panelCarga.setVisible(true);
				//END IAG

			    Runnable logicaReal = () -> {
			        
			        boolean exito = MainEleutradia.getDBManager()
			                .inscribirParticularACurso(usuarioLogeado.getDni(), cursoInfo.getId());
			        
			        if (exito) {

			            usuarioLogeado.addCurso(cursoInfo);

			            JOptionPane.showMessageDialog(panelCursosInfo, 
			                "¡Te has inscrito a " + cursoInfo.getNombre() + "!");
			            actualizarPanelInfoCurso();
			            actualizarProgressBar();
			            actualizarPanelMisCursos();
			            
			        } else {
			            JOptionPane.showMessageDialog(panelCursosInfo, "Error en la BD");
			        }
			    };

			    HiloVisual hilo = new HiloVisual(panelCarga, logicaReal, true);
			    hilo.start();
			});
			botonApuntar.addMouseListener(myAdapterAzul);
			
			panelTexto.add(botonApuntar);
			
		}

		JPanel panelVolver = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panelVolver.setBackground(MAIN_FONDO);
		
		JButton botonVolver = new JButton("Volver");
		botonVolver.setMinimumSize(new Dimension(150, 45));
		botonVolver.setPreferredSize(new Dimension(150, 45));
		botonVolver.setBackground(GRIS_MEDIO);
		botonVolver.setForeground(Color.WHITE);
		botonVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
		botonVolver.setBorderPainted(false);
        botonVolver.setContentAreaFilled(false);
        botonVolver.setOpaque(true);
		botonVolver.setFocusPainted(false);
		botonVolver.addActionListener(e -> {layoutPanelAprender.show(panelAprender, "PANEL_CURSOS");});
		botonVolver.addMouseListener(myAdapterGris);
		
		panelVolver.add(botonVolver);
		
		panelLateral.add(panelTexto, BorderLayout.NORTH);
		panelLateral.add(botonVolver, BorderLayout.SOUTH);
		
		return panelLateral;
	}
	
	private JScrollPane mostrarInfoCursos(Curso curso) {
		
		JPanel panelContenido = new JPanel();
		panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
		panelContenido.setBackground(Color.WHITE);
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
			System.out.println("Error al cargar la imagen: " + curso.getRutaImagen());
		}
		
		JLabel labelCurso = new JLabel(curso.getNombre());
		labelCurso.setFont(TITULO_GRANDE);
		labelCurso.setForeground(Color.BLACK);
		labelCurso.setAlignmentX(Component.LEFT_ALIGNMENT);
		labelCurso.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		panelContenido.add(labelCurso);
		
		int numeroModulo = 1;
		int numeroLeccion = 1;
		
		for (Modulo modulo : curso.getModulos()) {
			
			JLabel labelModulo = new JLabel("   " + numeroModulo + ". Módulo: " + modulo.getNombre());
			labelModulo.setFont(SUBTITULO_GRANDE);
			labelModulo.setForeground(Color.BLACK);
			labelModulo.setAlignmentX(Component.LEFT_ALIGNMENT);
			labelModulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
			panelContenido.add(labelModulo);
			numeroLeccion = 1;
			
			for (Leccion leccion : modulo.getLecciones()) {
				JLabel labelLeccion = new JLabel("      " + numeroModulo + "." + numeroLeccion + ". Lección: " + leccion.getTitulo());
				labelLeccion.setFont(CUERPO_GRANDE);
				labelLeccion.setForeground(GRIS_CLARO);
				labelLeccion.setAlignmentX(Component.LEFT_ALIGNMENT);
				labelLeccion.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
				panelContenido.add(labelLeccion);
				numeroLeccion++;
			}
			
			numeroModulo++;
		}
		
		panelContenido.add(Box.createVerticalGlue());
		
		JScrollPane scrollPane = new JScrollPane(panelContenido);
		scrollPane.setBorder(BorderFactory.createLineBorder(MAIN_BORDE));
		
		scrollPane.getVerticalScrollBar().setUI(crearScrollBarUI());
	    scrollPane.getHorizontalScrollBar().setUI(crearScrollBarUI());
		
		return scrollPane;
	}
	
	private void actualizarProgressBar() {

		int totalCursos = MainEleutradia.listaCursos.size();
		int misCursos = usuarioLogeado.getCursos().size();
		
		if (totalCursos == 0) {
			
			progressBarRacha.setValue(0);
			progressBarRacha.setString("No hay cursos");
			
		} else {
			
			int porcentajeBarra = (int) (((double) misCursos / totalCursos) * 100);
			progressBarRacha.setValue(porcentajeBarra);
			progressBarRacha.setString(misCursos + "/" + totalCursos);
		}
	}
	
	private BasicScrollBarUI crearScrollBarUI() {
		return new BasicScrollBarUI() {
			@Override
		    protected void configureScrollBarColors() {
		        this.thumbColor = GRIS_SCROLLBAR;
		        this.thumbDarkShadowColor = GRIS_SCROLLBAR;
		        this.thumbHighlightColor = GRIS_SCROLLBAR;
		        this.trackColor = Color.WHITE; 
		    }
	
		    @Override
		    protected JButton createDecreaseButton(int orientation) {
		        return createInvisibleButton();
		    }
	
		    @Override
		    protected JButton createIncreaseButton(int orientation) {
		        return createInvisibleButton();
		    }
	
		    private JButton createInvisibleButton() {
		        JButton button = new JButton();
		        button.setPreferredSize(new Dimension(0, 0));
		        button.setVisible(false);
		        return button;
		    }
		};
    };
    
    private class PanelCargaThreads extends JPanel {
        private static final long serialVersionUID = 1L;
        private JProgressBar progressBar;
        private JLabel lblMensaje;

        public PanelCargaThreads() {

            setOpaque(false);
            setLayout(new GridBagLayout());

            JPanel panelCentral = new JPanel();
            panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
            panelCentral.setBackground(Color.WHITE);
            panelCentral.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AZUL_CLARO, 2),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
            ));
            
            lblMensaje = new JLabel("Procesando solicitud...");
            lblMensaje.setFont(new Font("Segoe UI", Font.BOLD, 14));
            lblMensaje.setForeground(AZUL_OSCURO);
            lblMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);

            progressBar = new JProgressBar(0, 100);
            progressBar.setPreferredSize(new Dimension(200, 20));
            progressBar.setForeground(AZUL_CLARO);
            progressBar.setStringPainted(true);
            progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);

            panelCentral.add(lblMensaje);
            panelCentral.add(Box.createVerticalStrut(15)); 
            panelCentral.add(progressBar);

            add(panelCentral);
            
            addMouseListener(new MouseAdapter() {});
            addMouseMotionListener(new MouseAdapter() {});
        }

        public void actualizar(int valor, String texto) {
            SwingUtilities.invokeLater(() -> {
                progressBar.setValue(valor);
                lblMensaje.setText(texto);
            });
        }
    }
    
    private class HiloVisual extends Thread {
        
        private PanelCargaThreads panelInscripcion;
        private Runnable accionAlTerminar;
        private boolean inscribirse;

        public HiloVisual(PanelCargaThreads panelInscripcion, Runnable accionAlTerminar, boolean inscribirse) {
            this.panelInscripcion = panelInscripcion;
            this.accionAlTerminar = accionAlTerminar;
            this.inscribirse = inscribirse;
        }

        @Override
        public void run() {
            try {
            	List<String> frasesCarga;
            	
            	if (inscribirse) {
            		frasesCarga = Arrays.asList("Conectando...", "Verificando disponibilidad...", "Registrando usuario...", "¡Procesando!") ;
                } else {
                	frasesCarga = Arrays.asList("Procesando solicitud...", "Cancelando tu matrícula...", "Actualizando tu perfil...", "¡Baja completada!");
                }
            	
            	panelInscripcion.actualizar(10, frasesCarga.get(0));
                Thread.sleep(600); 
               
                
                if (isInterrupted()) return;
                
                panelInscripcion.actualizar(50, frasesCarga.get(1));
                Thread.sleep(800);
                
                if (isInterrupted()) return;
                
                panelInscripcion.actualizar(80, frasesCarga.get(2));
                Thread.sleep(600);
                
                panelInscripcion.actualizar(100, frasesCarga.get(3));
                Thread.sleep(300);

                SwingUtilities.invokeLater(() -> {
                    panelInscripcion.setVisible(false); 
                    accionAlTerminar.run();
                });

            } catch (InterruptedException e) {
                SwingUtilities.invokeLater(() -> panelInscripcion.setVisible(false));
            }
        }
    }
}
