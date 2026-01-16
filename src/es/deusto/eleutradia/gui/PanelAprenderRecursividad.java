package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import es.deusto.eleutradia.domain.Curso;
import es.deusto.eleutradia.domain.NivelConocimiento;
import es.deusto.eleutradia.domain.Particular;
import es.deusto.eleutradia.gui.style.UITema;
import es.deusto.eleutradia.main.MainEleutradia;

import static es.deusto.eleutradia.gui.style.UITema.*;

public class PanelAprenderRecursividad extends JPanel {
	

	private static final long serialVersionUID = 1L;
	
	private final Icon iconRuta = new ImageIcon(getClass().getResource("/images/iconos/aprenderCerrado.png"));

	private final Icon iconPaso = new ImageIcon(getClass().getResource("/images/iconos/aprenderAbierto.png"));
	
	private JComboBox<String> comboNivelObjetivo;
	private JButton btnBuscarRuta;
	private JButton btnVolver;
	private JButton btnAnadirRuta;
	private JPanel panelResultado;
	private JPanel panelBotones;
	
	private ActionListener accionVolver;
	
	private Particular usuarioLogeado;
	private List<Curso> rutaSeleccionada;
	private JTree treeResultados;
	
	public PanelAprenderRecursividad(Particular usuario) {
		
		this.usuarioLogeado = usuario;
		this.rutaSeleccionada = null;
		
		setLayout(new BorderLayout(20, 20));
		setBackground(MAIN_FONDO);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setBackground(MAIN_FONDO);
		
		JPanel panelTitulo = crearPanelTitulo();
		panelSuperior.add(panelTitulo);
        
		panelSuperior.add(Box.createVerticalStrut(15));
		
		JPanel panelCentral = crearPanelFormulario();
		panelSuperior.add(panelCentral);
		
		add(panelSuperior, BorderLayout.NORTH);
		
		panelResultado = new JPanel(new BorderLayout());
		panelResultado.setBackground(MAIN_FONDO);
		add(panelResultado, BorderLayout.CENTER);
		
		panelBotones = crearPanelBotones();
		add(panelBotones, BorderLayout.EAST);

	}
	
	private JPanel crearPanelTitulo() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(MAIN_FONDO);
		
		JLabel labelTitulo = new JLabel("La forma más rápida para aprender");
		labelTitulo.setFont(TITULO_GRANDE);
		labelTitulo.setForeground(AZUL_OSCURO);
		labelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel labelSubtitulo = new JLabel("Encuentre el camino adecuado desde PRINCIPIANTE hasta su nivel objetivo.");
		labelSubtitulo.setFont(CUERPO_GRANDE);
		labelSubtitulo.setForeground(GRIS_CLARO);
		labelSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		panel.add(labelTitulo);
		panel.add(Box.createVerticalStrut(8));
		panel.add(labelSubtitulo);
		
		return panel;
	}
	
	private JPanel crearPanelFormulario() {
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.WHITE);
		panel.setBorder(BorderFactory.createCompoundBorder(
		        BorderFactory.createLineBorder(MAIN_BORDE),
		        BorderFactory.createEmptyBorder(25, 25, 25, 25)
		));
		
		JLabel labelNivel = new JLabel("Selecciona tu nivel objetivo:");
		labelNivel.setFont(SUBTITULO_MEDIO);
		labelNivel.setForeground(Color.BLACK);
		labelNivel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(labelNivel);

		panel.add(Box.createVerticalStrut(12));
		
		comboNivelObjetivo = new JComboBox<>();
		comboNivelObjetivo.addItem("INTERMEDIO");
		comboNivelObjetivo.addItem("PROFESIONAL");
		comboNivelObjetivo.addItem("AVANZADO");
		comboNivelObjetivo.setFont(CUERPO_GRANDE);
		comboNivelObjetivo.setPreferredSize(new Dimension(300, 40));
		comboNivelObjetivo.setMaximumSize(new Dimension(300, 40));
		comboNivelObjetivo.setAlignmentX(JComboBox.CENTER_ALIGNMENT);
		UITema.personalizarComboBox(comboNivelObjetivo);
		panel.add(comboNivelObjetivo);
		
		panel.add(Box.createVerticalStrut(20));
		
		btnBuscarRuta = new JButton("Buscar ruta de aprendizaje");
		btnBuscarRuta.setFont(SUBTITULO_MEDIO);
		btnBuscarRuta.setBackground(AZUL_CLARO);
		btnBuscarRuta.setForeground(Color.WHITE);
		btnBuscarRuta.setBorderPainted(false);
		btnBuscarRuta.setContentAreaFilled(false);
		btnBuscarRuta.setOpaque(true);
		btnBuscarRuta.setFocusPainted(false);
		btnBuscarRuta.addMouseListener(myAdapterAzul);
		btnBuscarRuta.setPreferredSize(new Dimension(300, 40));
		btnBuscarRuta.setMaximumSize(new Dimension(300, 40));
		btnBuscarRuta.setAlignmentX(JButton.CENTER_ALIGNMENT);
		btnBuscarRuta.addActionListener(e -> buscarRutaAprendizaje());
		panel.add(btnBuscarRuta);
		
		return panel;
	}

	private JPanel crearPanelBotones() {

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(MAIN_FONDO);
		
		btnAnadirRuta = new JButton("Añadir ruta");
		btnAnadirRuta.setFont(SUBTITULO_MEDIO);
		btnAnadirRuta.setBackground(VERDE_CLARO);
		btnAnadirRuta.setForeground(Color.WHITE);
		btnAnadirRuta.setPreferredSize(new Dimension(150, 40));
		btnAnadirRuta.setMinimumSize(new Dimension(150, 40));
		btnAnadirRuta.setMaximumSize(new Dimension(150, 40));
		btnAnadirRuta.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnAnadirRuta.setBorderPainted(false);
		btnAnadirRuta.setContentAreaFilled(false);
		btnAnadirRuta.setOpaque(true);
		btnAnadirRuta.setFocusPainted(false);
		
		btnAnadirRuta.addActionListener(e -> inscribirseARuta());
		btnAnadirRuta.addMouseListener(myAdapterVerde);
		btnAnadirRuta.setVisible(false);
		
		panel.add(btnAnadirRuta);

		panel.add(Box.createVerticalGlue());
		
		btnVolver = new JButton("Volver");
		btnVolver.setFont(SUBTITULO_MEDIO);
		btnVolver.setBackground(GRIS_MEDIO);
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setPreferredSize(new Dimension(150, 45));
		btnVolver.setMinimumSize(new Dimension(150, 45));
		btnVolver.setMaximumSize(new Dimension(150, 45));
		btnVolver.setBorderPainted(false);
		btnVolver.setContentAreaFilled(false);
		btnVolver.setOpaque(true);
		btnVolver.setFocusPainted(false);
		
		//IAG (Gemini)
		//SIN CAMBIOS
		btnVolver.addActionListener(e -> {
			if (accionVolver != null) {
				accionVolver.actionPerformed(e);
			}
		});
		//END IAG
		
		btnVolver.addMouseListener(myAdapterGris);
		
		panel.add(btnVolver);
		
		return panel;
	}
	
	private void buscarRutaAprendizaje() {
		
		String nivelObjetivoStr = (String) comboNivelObjetivo.getSelectedItem();
		NivelConocimiento nivelObjetivo = NivelConocimiento.valueOf(nivelObjetivoStr);
		
		rutaSeleccionada = null;
		btnAnadirRuta.setVisible(false);
		
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
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			List<List<Curso>> rutas = busquedaRecursivaRutas(nivelObjetivo);
			
			SwingUtilities.invokeLater(() -> mostrarResultados(rutas, nivelObjetivo));
			
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
	
	private void mostrarResultados(List<List<Curso>> rutas, NivelConocimiento nivelObjetivo) {
		
		panelResultado.removeAll();
		
		if (rutas.isEmpty()) {
			JLabel labelSinResultados = new JLabel("No se encontraron rutas de aprendizaje disponibles.");
			labelSinResultados.setFont(CUERPO_GRANDE);
			labelSinResultados.setForeground(ROJO_CLARO);
			labelSinResultados.setHorizontalAlignment(JLabel.CENTER);
			panelResultado.add(labelSinResultados, BorderLayout.CENTER);
		} else {

			DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(
					String.format("Rutas encontradas: %d (de PRINCIPIANTE a %s)", 
							rutas.size(), nivelObjetivo));
			
			for (int i = 0; i < rutas.size(); i++) {
				List<Curso> ruta = rutas.get(i);
				
				DefaultMutableTreeNode rutaNode = new DefaultMutableTreeNode(
						String.format("Ruta %d", i + 1));
				rutaNode.setUserObject(new RutaInfo(String.format("Ruta %d", i + 1), ruta));
				rootNode.add(rutaNode);
				
				DefaultMutableTreeNode nivelInicialNode = new DefaultMutableTreeNode(
						"Nivel inicial: PRINCIPIANTE");
				rutaNode.add(nivelInicialNode);
				
				for (int j = 0; j < ruta.size(); j++) {
					Curso curso = ruta.get(j);
					DefaultMutableTreeNode cursoNode = new DefaultMutableTreeNode(
							String.format("Paso %d: %s (Nivel: %s)", 
									j + 1, curso.getNombre(), curso.getNivelRecomendado()));
					rutaNode.add(cursoNode);
				}
			}
			
			treeResultados = new JTree(rootNode);
			treeResultados.setFont(CUERPO_GRANDE);
			treeResultados.setBackground(Color.WHITE);
			
			treeResultados.setCellRenderer(new DefaultTreeCellRenderer() {

			    private static final long serialVersionUID = 1L;

			    @Override
			    public Component getTreeCellRendererComponent(
			            JTree tree, Object value, boolean selected,
			            boolean expanded, boolean leaf, int row, boolean hasFocus) {

			        super.getTreeCellRendererComponent(
			                tree, value, selected, expanded, leaf, row, hasFocus);

			        setFont(CUERPO_GRANDE);
			        setBackgroundNonSelectionColor(Color.WHITE);
			        setBackgroundSelectionColor(new Color(200, 210, 240));

			        if (value instanceof DefaultMutableTreeNode) {
			            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			            Object userObject = node.getUserObject();

			            String texto;
			            if (userObject instanceof RutaInfo) {
			                texto = ((RutaInfo) userObject).nombre;
			            } else {
			                texto = userObject.toString();
			            }

			            if (texto.startsWith("Ruta ")) {
			                setForeground(AZUL_OSCURO);
			                setFont(SUBTITULO_MEDIO);
			                setIcon(iconRuta);

			            } else if (texto.startsWith("Paso ")) {
			                setForeground(AZUL_CLARO);
			                setIcon(iconPaso);

			            } else if (texto.startsWith("Nivel inicial")) {
			                setForeground(VERDE_CLARO);
			                setIcon(null);

			            } else if (texto.startsWith("Rutas encontradas")) {
			                setForeground(GRIS_OSCURO);
			                setFont(SUBTITULO_GRANDE);
			                setIcon(null); // sin icono
			            }
			        }

			        return this;
			    }
			});

			
			//IAG (Gemini)
			//NO MODIFICADO
			treeResultados.addTreeSelectionListener(new TreeSelectionListener() {
				@Override
				public void valueChanged(TreeSelectionEvent e) {
					TreePath path = e.getNewLeadSelectionPath();
					if (path != null) {
						DefaultMutableTreeNode selectedNode = 
								(DefaultMutableTreeNode) path.getLastPathComponent();
						
						// Verificar si el nodo seleccionado es una ruta
						if (selectedNode.getUserObject() instanceof RutaInfo) {
							RutaInfo info = (RutaInfo) selectedNode.getUserObject();
							rutaSeleccionada = info.cursos;
							btnAnadirRuta.setVisible(true);
						} else {
							rutaSeleccionada = null;
							btnAnadirRuta.setVisible(false);
						}
					} else {
						rutaSeleccionada = null;
						btnAnadirRuta.setVisible(false);
					}
				}
			});
			//END IAG

			JScrollPane scrollPane = new JScrollPane(treeResultados);
			scrollPane.setPreferredSize(new Dimension(0, 300));
			scrollPane.setBorder(BorderFactory.createLineBorder(MAIN_BORDE, 1));
			
			panelResultado.add(scrollPane, BorderLayout.CENTER);
		}
		
		panelResultado.revalidate();
		panelResultado.repaint();
	}
	
	private void inscribirseARuta() {
	       if (rutaSeleccionada == null || rutaSeleccionada.isEmpty()) {
	           mostrarError(this, "No hay ninguna ruta seleccionada.", "Error");
	           return;
	       }

	       List<Curso> cursosAInscribir = new ArrayList<>();
	       List<Curso> cursosYaInscritos = new ArrayList<>();
	       
	       for (Curso curso : rutaSeleccionada) {
	           boolean yaInscrito = usuarioLogeado.getCursos().stream()
	                   .anyMatch(c -> c.getId() == curso.getId());
	           if (yaInscrito) cursosYaInscritos.add(curso);
	           else cursosAInscribir.add(curso);
	       }
	       if (cursosAInscribir.isEmpty()) {
	           mostrarInfo(this, "Ya estás inscrito en todos los cursos de esta ruta.", "Información");
	           return;
	       }


	       String mensaje = "¿Deseas inscribirte en los siguientes cursos?\n";
	       for (Curso curso : cursosAInscribir) {
	       	mensaje += "• " + curso.getNombre().toString() + "\n";
	       }
	      
	       if (!cursosYaInscritos.isEmpty()) {
	           mensaje += "\nYa estás inscrito en:\n";
	           for (Curso curso : cursosYaInscritos) {
	           	mensaje += "• " + curso.getNombre().toString() + "\n";
	           }
	       }
	       if (!mostrarConfirmacion(this, mensaje.toString(), "Confirmar inscripción")) {
	           return;
	       }


	       btnAnadirRuta.setEnabled(false);
	       
	       new Thread(() -> {
	    	   
	           int exitosos = 0;
	           int fallidos = 0;
	           
	           for (Curso curso : cursosAInscribir) {
	               
	        	   boolean exito = MainEleutradia.getDBManager().inscribirParticularACurso(usuarioLogeado.getDni(), curso.getId());
	               
	               if (exito) {
	                   usuarioLogeado.addCurso(curso);
	                   exitosos++;
	               } else {
	                   fallidos++;
	               }
	              
	               try { 
	            	   Thread.sleep(300); 
	               } catch (InterruptedException ignored) {
	            	   
	               }
	           }
	           
	           final int totalExitosos = exitosos;
	           final int totalFallidos = fallidos;
	           
	           SwingUtilities.invokeLater(() -> {

	        	   btnAnadirRuta.setEnabled(true);
	        	   
	               if (totalFallidos == 0) {
	                   mostrarInfo(PanelAprenderRecursividad.this,
	                       "¡Se ha inscrito exitosamente en " + totalExitosos + " curso(s)!",
	                       "Inscripción exitosa");
	               } else {
	                   mostrarWarning(PanelAprenderRecursividad.this,
	                       String.format("Inscripciones exitosas: %d\nFallidas: %d", totalExitosos, totalFallidos),
	                       "Inscripción con errores");
	               }
	              
	               rutaSeleccionada = null;
	               btnAnadirRuta.setVisible(false);
	               
	               if (treeResultados != null) {
	            	   treeResultados.clearSelection();
	               }
	           });
	       }).start();
	       
	}


	public void addAccionVolver(ActionListener listener) {
		this.accionVolver = listener;
	}
	
	//IAG (Gemini)
	//NO MODIFICADO
	private static class RutaInfo {
		String nombre;
		List<Curso> cursos;
		
		RutaInfo(String nombre, List<Curso> cursos) {
			this.nombre = nombre;
			this.cursos = cursos;
		}
		
		@Override
		public String toString() {
			return nombre;
		}
	}
	//END IAG

}