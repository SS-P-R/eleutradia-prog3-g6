package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import es.deusto.eleutradia.domain.Cartera;
import es.deusto.eleutradia.domain.Curso;
import es.deusto.eleutradia.domain.Leccion;
import es.deusto.eleutradia.domain.Particular;
import es.deusto.eleutradia.domain.ProductoFinanciero;
import es.deusto.eleutradia.domain.Usuario;
import es.deusto.eleutradia.main.MainEleutradia;

public class PanelInicio extends JPanel{

	private static final long serialVersionUID = 1L;
	private VentanaPrincipal ventanaPrincipal;
	private Usuario usuario;
	private JFrame frame;
	
	private static final Color COLOR_FONDO_PRINCIPAL = new Color(248, 249, 250);
    private static final Color COLOR_CARD = Color.WHITE;
    private static final Color COLOR_BORDE = new Color(222, 226, 230);

    private static final Font FONT_TITULO1 = new Font("Segoe UI", Font.BOLD, 20);
    private static final Font FONT_TITULO2 = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font FONT_NORMAL1 = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_NORMAL2 = new Font("Segoe UI", Font.PLAIN, 12);
	
    private ArrayList<ProductoFinanciero> productoRandom = new ArrayList<>();

    
    private List<ProductoFinanciero> productos;


	public PanelInicio(Usuario usuario, VentanaPrincipal ventanaPrincipal) {
		this.usuario = usuario;
		this.ventanaPrincipal = ventanaPrincipal;
		
		//Fondo
		this.setLayout(new BorderLayout(10,10));
		this.setBackground(COLOR_FONDO_PRINCIPAL);
		this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		//Cargar paneles
		JPanel panelSaludo = PanelSaludo();
		JPanel panelCursos = PanelCursos(); 
		JPanel panelLecciones = PanelLecciones();
		JPanel panelRecordatorio = PanelRecordatorio();
		JPanel panelActivos = PanelActivos();
		JPanel panelGraficos = PanelGraficos();
		JPanel panelNoticias = PanelTitularesNoticias();
		JPanel panelSuperior = new JPanel();
		
		panelSuperior.setLayout(new BorderLayout());
		panelSuperior.add(panelSaludo, BorderLayout.WEST);
		panelSuperior.add(panelNoticias, BorderLayout.EAST);
		//Paneles accesorios con saludo incial y recordatorios
		setVisible(true);
		panelSuperior.setBackground(COLOR_FONDO_PRINCIPAL);
		add(panelSuperior, BorderLayout.NORTH);
		add(panelRecordatorio, BorderLayout.SOUTH);

		
		//Panel central
		JPanel centro = new JPanel(new GridLayout(2,2,10,10));
		centro.setBackground(getBackground());
		centro.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		centro.add(panelCursos);
		centro.add(panelLecciones);
		centro.add(panelActivos);
		centro.add(panelGraficos);
		add(centro, BorderLayout.CENTER);
		
	}
	

	private JPanel PanelSaludo() {
		JPanel panelSaludo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel saludoTxt = new JLabel("¡Hola, " + usuario.getNombre().split(" ")[0] + "!");
		saludoTxt.setOpaque(true);
		saludoTxt.setBackground(COLOR_FONDO_PRINCIPAL);
		saludoTxt.setFont(FONT_TITULO1);
		
//		Border recuadro = BorderFactory.createLineBorder(Color.gray, 1);
		Border posicion = BorderFactory.createEmptyBorder(5,10,5,10);
		saludoTxt.setBorder(posicion);
		
		panelSaludo.setBackground(getBackground());
		panelSaludo.add(saludoTxt);
		return panelSaludo;
	}	
	
	
	private JPanel PanelRecordatorio() {
		JPanel recordatorio = new JPanel(new FlowLayout());
		recordatorio.setBackground(COLOR_FONDO_PRINCIPAL);
		JButton completarPerfil = new JButton("Ir al perfil");
		completarPerfil.addActionListener(e->{
			ventanaPrincipal.mostrarPanel("Perfil");
		});
		if (usuario instanceof Particular) {
			Particular particular = (Particular) usuario;
			if (particular.getPaisResidencia()==null) {
				recordatorio.add(completarPerfil);
				recordatorio.add(new JLabel("Todavía no ha completado su perfil, añada su país."));
			} else if (particular.getDireccion()==null) {
				recordatorio.add(completarPerfil);
				recordatorio.add(new JLabel("Todavía no ha completado su perfil, añada su dirección."));
			} else {
//				recordatorio.add(new JLabel("Todo correcto.")); // Innecesario
			}
		}
		return recordatorio;
	}

	private JPanel PanelLecciones() {
	    JPanel proxLecJPanel = new JPanel();
	    proxLecJPanel.setBackground(COLOR_CARD);
	    proxLecJPanel.setBorder(BorderFactory.createLineBorder(COLOR_BORDE,1));
	    
	    JLabel mensajeLecciones = new JLabel();
	    mensajeLecciones.setText("¿Preparado para su próxima lección?");
	    mensajeLecciones.setFont(FONT_TITULO2);
	    proxLecJPanel.add(mensajeLecciones);

	    JPanel leccionesPanel = new JPanel();
	    leccionesPanel.setLayout(new BoxLayout(leccionesPanel, BoxLayout.Y_AXIS));
	    leccionesPanel.setBackground(proxLecJPanel.getBackground());
	    proxLecJPanel.add(leccionesPanel);

	    JLabel mensajeLecciones2 = new JLabel("Lecciones recomendadas para continuar aprendiendo:");
	    mensajeLecciones2.setFont(FONT_NORMAL2);
	    leccionesPanel.add(Box.createVerticalStrut(10));
	    leccionesPanel.add(mensajeLecciones2);

	    if (usuario instanceof Particular) {
	        Particular particular = (Particular) usuario;
	        List<Curso> listaCursosActivos = particular.getCursos();
	        List<Curso> todosCursos = MainEleutradia.listaCursos;
	        
	        if (todosCursos == null || todosCursos.isEmpty()) {
	            leccionesPanel.add(Box.createVerticalStrut(10));
	            JLabel mensajeError = new JLabel("No hay cursos disponibles en este momento.");
	            mensajeError.setFont(FONT_NORMAL1);
	            leccionesPanel.add(mensajeError);
	            
	            proxLecJPanel.setName("Aprender");
	            PanelFocus(proxLecJPanel, leccionesPanel);
	            return proxLecJPanel;
	        }
	        
	        List<Leccion> listaLecciones = new ArrayList<Leccion>();
	        
	        for (Curso curso : todosCursos) {
	            if (!listaCursosActivos.contains(curso)) {
	                if (curso.getModulos() != null && !curso.getModulos().isEmpty()) {
	                    if (curso.getModulos().get(0).getLecciones() != null 
	                            && !curso.getModulos().get(0).getLecciones().isEmpty()) {
	                        
	                        listaLecciones.add(curso.getModulos().get(0).getLecciones().get(0));
	                        
	                        if (listaLecciones.size() >= 4) {
	                            break;
	                        }
	                    }
	                }
	            }
	        }
	        
	        if (!listaLecciones.isEmpty()) {
	            for (Leccion leccion : listaLecciones) {
	                leccionesPanel.add(Box.createVerticalStrut(10));
	                JLabel leccionSumada = new JLabel("• " + leccion.getTitulo());
	                leccionSumada.setFont(FONT_NORMAL1);
	                leccionesPanel.add(leccionSumada);
	            }
	        }
	        
	        if (listaCursosActivos.size() == todosCursos.size()) {
	            leccionesPanel.add(Box.createVerticalStrut(10));
	            JLabel mensajeFelicitacion = new JLabel("¡Asombroso! No hay lecciones nuevas que recomendar.");
	            mensajeFelicitacion.setFont(FONT_NORMAL1);
	            leccionesPanel.add(mensajeFelicitacion);
	            leccionesPanel.add(Box.createVerticalStrut(10));
	            //TODO Añadir imagen de un trofeo
	        } else if (listaLecciones.isEmpty()) {
	            leccionesPanel.add(Box.createVerticalStrut(10));
	            JLabel mensajeVacio = new JLabel("No hay lecciones disponibles en este momento.");
	            mensajeVacio.setFont(FONT_NORMAL1);
	            leccionesPanel.add(mensajeVacio);
	        }
	        
	    } else {
	        leccionesPanel.add(Box.createVerticalStrut(10));
	        JLabel mensajeNoDisponible = new JLabel("Esta función no está disponible para empresas.");
	        mensajeNoDisponible.setFont(FONT_NORMAL1);
	        leccionesPanel.add(mensajeNoDisponible);
	    }
	    
	    proxLecJPanel.setName("Aprender");
	    PanelFocus(proxLecJPanel, leccionesPanel);
	    return proxLecJPanel;
	}
	
	private JPanel PanelCursos() {
		JPanel cursosPanel = new JPanel();
		cursosPanel.setBackground(COLOR_CARD);
		cursosPanel.setBorder(BorderFactory.createLineBorder(COLOR_BORDE,1));
		
		JLabel mensajeCursos = new JLabel();
		mensajeCursos.setText("Cursos en progreso: ");
		mensajeCursos.setFont(FONT_TITULO2);
		cursosPanel.add(mensajeCursos);
		
		JPanel cursosProgreso = new JPanel();
		cursosProgreso.setLayout(new BoxLayout(cursosProgreso, BoxLayout.Y_AXIS));
		cursosProgreso.setBackground(cursosPanel.getBackground());
		cursosPanel.add(cursosProgreso);

		
		if (usuario instanceof Particular) {
			Particular particular = (Particular) usuario;
			List<Curso> listaCursosActivos = particular.getCursos();
			for (Curso curso : listaCursosActivos) {
				cursosProgreso.add(Box.createVerticalStrut(10));
				cursosProgreso.add(new JLabel(curso.getNombre()));
			}
			if (listaCursosActivos.isEmpty()) {
				cursosProgreso.add(Box.createVerticalStrut(20));
				JLabel mensaje1 = new JLabel("Todavía vacío, es hora de empezar un curso.");
				mensaje1.setFont(FONT_NORMAL1);
				cursosProgreso.add(mensaje1);
				
				
				cursosProgreso.add(Box.createVerticalStrut(10));
				JLabel mensaje2 = new JLabel("¿A qué espera?");
				mensaje2.setFont(FONT_NORMAL1);
				cursosProgreso.add(mensaje2);
				
				cursosProgreso.add(Box.createVerticalStrut(10));
				//TODO Añadir imagen de un reloj
			}
		}
		
		cursosPanel.setName("Aprender");
		PanelFocus(cursosPanel,cursosProgreso);
		return cursosPanel;
	}

	
	private JPanel PanelActivos() {
		JPanel activosPanel = new JPanel();
		activosPanel.setBackground(Color.WHITE);
		activosPanel.setBorder(BorderFactory.createLineBorder(COLOR_BORDE,1));
		activosPanel.setLayout(new BorderLayout());
		
		JLabel mensajeActivos = new JLabel();
		mensajeActivos.setText("Resumen de sus activos: ");
		mensajeActivos.setFont(FONT_TITULO2);
		mensajeActivos.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		activosPanel.add(mensajeActivos, BorderLayout.NORTH);
		
		String[] columnas = {"Concepto","Cartera", "Valor (€)"};
		DefaultTableModel modelo = new DefaultTableModel(columnas,0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		List<Cartera> carteras = usuario.getCarteras();
		
		if (carteras == null || carteras.isEmpty()) {
			// Sin carteras - mostrar mensaje amigable
			modelo.addRow(new Object[]{"Sin carteras", "---", "0.00 €"});
			modelo.addRow(new Object[]{"Cree una cartera", "---", "---"});
			
			JTable tablaResumen = new JTable(modelo);
			tablaResumen.setFont(FONT_NORMAL2);
			tablaResumen.setRowHeight(20);
			tablaResumen.getTableHeader().setFont(FONT_NORMAL2);
			
			JScrollPane scroll = new JScrollPane(tablaResumen);
			Border bordet1 = BorderFactory.createLineBorder(COLOR_BORDE, 1);
			Border bordet2 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
			scroll.setBorder(BorderFactory.createCompoundBorder(bordet1, bordet2));
			activosPanel.add(scroll, BorderLayout.CENTER);
			
			// Botón para crear cartera
			JButton btnCrearCartera = new JButton("Crear nueva cartera");
			btnCrearCartera.addActionListener(e -> {
				ventanaPrincipal.mostrarPanel("Portfolio");
			});
			activosPanel.add(btnCrearCartera, BorderLayout.SOUTH);
			
			activosPanel.setName("Portfolio");
			PanelFocus(activosPanel, null);
			return activosPanel;
		}
		
		double valorTotal = 0;
		Cartera mayor = null;
		Cartera menor = null;
		double max = 0;
		double min = Double.POSITIVE_INFINITY;
		
		for (Cartera c : carteras) {
			double saldo = c.getSaldo();
			
			if (mayor == null || saldo > max) {
				max = saldo;
				mayor = c;
			}
			if (menor == null || saldo < min) {
				min = saldo;
				menor = c;
			}
			valorTotal += saldo;
		}
		
		modelo.addRow(new Object[]{"Total", carteras.size() + " cartera(s)", String.format("%.2f €", valorTotal)});
		if (mayor != null) {
			modelo.addRow(new Object[]{"Mayor valor", mayor.getNombre(), String.format("%.2f €", mayor.getSaldo())});
		}
		if (menor != null && carteras.size() > 1) {
			modelo.addRow(new Object[]{"Menor valor", menor.getNombre(), menor.getSaldo() + "€"});
		}

		JTable tablaResumen = new JTable(modelo);
		JScrollPane scroll = new JScrollPane(tablaResumen);
		Border bordet1 = BorderFactory.createLineBorder(COLOR_BORDE, 1);
		Border bordet2 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		scroll.setBorder(BorderFactory.createCompoundBorder(bordet1, bordet2));		
		activosPanel.add(scroll, BorderLayout.CENTER);
		
		tablaResumen.setFont(FONT_NORMAL2);
		tablaResumen.setRowHeight(20);
		tablaResumen.getTableHeader().setFont(FONT_NORMAL2);		

		activosPanel.setName("Portfolio");
		PanelFocus(activosPanel, null);
		return activosPanel;	
	};
	
	
	int sel = 0;
	private JPanel PanelGraficos() {
		JPanel panelGraficos = new JPanel();
		panelGraficos.setBackground(COLOR_CARD);
		panelGraficos.setBorder(BorderFactory.createLineBorder(COLOR_BORDE,1));
		
		if (MainEleutradia.listaProductos != null) {
        	productos = new ArrayList<>(MainEleutradia.listaProductos);
        }
		
		if (productoRandom.isEmpty()) {
			productoRandom.add(RandomizadorProductos());
		}
		
	
		String productoRandomNombre = productoRandom.get(sel).getNombre();
		JLabel mensajeGraficos = new JLabel("Échele un vistazo a " + "'"+productoRandomNombre+"'");
		panelGraficos.add(mensajeGraficos);
		
		frame = new JFrame("Gráfico en Panel");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 500);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setMinimumSize(new Dimension(500,300));
		
		//IAG (ChatGPT)
        try {
        // Configuración del gráfico (codificada)
        String chartConfig = "{type:'line',data:"
        		+ "{labels:['Lun','Mar','Mie','Jue'],"
        		+ "datasets:[{label:'"+productoRandomNombre+"',data:["+RandomizadorValores()+","+RandomizadorValores()+","+RandomizadorValores()+","+RandomizadorValores()+"],"
        		+ "borderColor:'blue',fill:false}]},"
        	    + "options:{"
        	    + "plugins:{legend:{display:false}}," //TODO no funciona
        	    + "scales:{"
        	    + "x:{ticks:{color:'black',font:{size:8}}},"
        	    + "y:{ticks:{color:'black',font:{size:1}}}"
        	    + "}"
        	    + "}"
        		+ "}";
        String encodedConfig = URLEncoder.encode(chartConfig, "UTF-8");
        String urlString = "https://quickchart.io/chart?width=150&height=85&c=" + encodedConfig;
        String urlString2 = "https://quickchart.io/chart?width=400&height=200&c=" + encodedConfig;

        // Forma moderna: usar URI y luego convertir a URL
        URI uri = URI.create(urlString);
        URL url = uri.toURL();
        URI uri2 = URI.create(urlString2);
        URL url2 = uri2.toURL();
        
        ImageIcon icon = new ImageIcon(url);
        JLabel label = new JLabel(icon);
        panelGraficos.add(label, BorderLayout.CENTER);
        ImageIcon icon2 = new ImageIcon(url2);
        JLabel label2 = new JLabel(icon2);
	    //END-IAG

        frame.add(label2);
        frame.setVisible(false);
        
        }catch (Exception e) {
			System.err.println("Error");
		}
        
        JButton siguiente = new JButton("Siguiente");
        siguiente.addActionListener(e->{
        	sel += 1;
        	if (sel>=productos.size()) {
        		sel=0;
        	}
        	PanelGraficos();
        });
        
        panelGraficos.add(siguiente);
		panelGraficos.setName("Gráfico");
		PanelFocus(panelGraficos,null);
		return panelGraficos;
	}
	
	private JPanel PanelTitularesNoticias() {
	    JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	    panel.setBackground(COLOR_FONDO_PRINCIPAL);

	    List<String[]> noticias = new ArrayList<>();
	    noticias.add(new String[]{
	        "IBEX 35 sube un 2%",
	        "El IBEX 35 ha subido un 2% tras la publicación de los datos de inflación."
	    });
	    noticias.add(new String[]{
	        "El euro se fortalece",
	        "El euro gana fuerza frente al dólar debido a las decisiones del BCE."
	    });
	    noticias.add(new String[]{
	        "Caen las tecnológicas",
	        "Las grandes tecnológicas han sufrido caídas en Wall Street."
	    });

	    JLabel titularLabel = new JLabel("📰 " + noticias.get(0)[0]);
	    titularLabel.setFont(FONT_NORMAL2);
	    titularLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	    panel.add(titularLabel);

	    final int[] indice = {0};

	    // 🔁 HILO que cambia titulares
	    Thread hiloTitulares = new Thread(() -> {
	        try {
	            while (true) {
	                Thread.sleep(4000);
	                indice[0] = (indice[0] + 1) % noticias.size();

	                javax.swing.SwingUtilities.invokeLater(() -> {
	                    titularLabel.setText("📰 " + noticias.get(indice[0])[0]);
	                });
	            }
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
	    });
	    hiloTitulares.setDaemon(true);
	    hiloTitulares.start();

	    titularLabel.addMouseListener(new java.awt.event.MouseAdapter() {
	        @Override
	        public void mouseClicked(java.awt.event.MouseEvent e) {
	            JFrame ventana = new JFrame("Noticia");
	            ventana.setSize(500, 300);
	            ventana.setLocationRelativeTo(null);
	            ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	            JTextArea texto = new JTextArea(noticias.get(indice[0])[1]);
	            texto.setFont(FONT_NORMAL1);
	            texto.setLineWrap(true);
	            texto.setWrapStyleWord(true);
	            texto.setEditable(false);

	            ventana.add(new JScrollPane(texto));
	            ventana.setVisible(true);
	        }
	    });

	    return panel;
	}

	
	private ProductoFinanciero RandomizadorProductos() {
		productos = new ArrayList<ProductoFinanciero>();
        if (MainEleutradia.listaProductos != null) {
        	productos = new ArrayList<>(MainEleutradia.listaProductos);
        }
        Random miRandom = new Random();
        int n = productos.size();
        int random = miRandom.nextInt(n);
        
        return productos.get(random);
	}
	
	private String RandomizadorValores() {
		Random miRandom = new Random();
		int random = miRandom.nextInt(1750)+875;
		return String.valueOf(random);
	}
	
	
	private void PanelFocus(JPanel panel, JPanel panelSecundario) {
		Color colorOg = panel.getBackground();
		panel.addMouseListener(new MouseListener() {
			@Override
			public void mouseExited(MouseEvent e) {
				panel.setBackground(colorOg);
				if (panelSecundario!=null) {
					panelSecundario.setBackground(colorOg);
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				panel.setBackground(new Color(150, 206, 240));
				if (panelSecundario!=null){
					panelSecundario.setBackground(new Color(150, 206, 240));
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (panel.getName()=="Gráfico") {
				frame.setVisible(true);	
				}else {
				ventanaPrincipal.mostrarPanel(panel.getName());
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}