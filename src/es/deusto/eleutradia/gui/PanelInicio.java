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
import javax.swing.border.Border;

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

    
    private java.util.List<ProductoFinanciero> productos;


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
		
		//Paneles accesorios con saludo incial y recordatorios
		setVisible(true);
		add(panelSaludo, BorderLayout.NORTH);
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
			}else if (particular.getDireccion()==null) {
				recordatorio.add(completarPerfil);
				recordatorio.add(new JLabel("Todavía no ha completado su perfil, añada su dirección."));
			}else {
//				recordatorio.add(new JLabel("Todo correcto.")); // Inecesario
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

		JLabel leccion1 = new JLabel();
		if (usuario instanceof Particular) {
			Particular particular = (Particular) usuario;
			List<Curso> listaCursosActivos = particular.getCursos();
			List<Curso> todosCursos = MainEleutradia.listaCursos;
			List<Leccion> listaLecciones = new ArrayList<Leccion>();
			
			for (Curso curso : todosCursos) {
				if (!listaCursosActivos.contains(curso)){
					listaLecciones.add(curso.getModulos().getFirst().getLecciones().getFirst());
				}if(listaLecciones.getLast().getPosicion()==4) {
					break;
				}
			}
			for (Leccion leccion : listaLecciones) {
				leccionesPanel.add(Box.createVerticalStrut(10));
				JLabel leccionSumada  = new JLabel(leccion.getTitulo());
				leccionSumada.setFont(FONT_NORMAL1);
				leccionesPanel.add(leccionSumada);
			}
			if(listaCursosActivos.size()==todosCursos.size()) {
				leccionesPanel.add(Box.createVerticalStrut(10));
				leccionesPanel.add(new JLabel("Asombroso, no hay lecciones nuevas que recomendar."));
				leccionesPanel.add(Box.createVerticalStrut(10));
				//TODO Añadir imagen de un trofeo
			}
			
			
		}else {
			leccion1.setText("Esta función no está disponible");
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
		
		JLabel mensajeActivos = new JLabel();
		mensajeActivos.setText("Resumen de sus activos: ");
		mensajeActivos.setFont(FONT_TITULO2);
		activosPanel.add(mensajeActivos);
		
		String[] columnas = {"Nombre","Varianza", "Valor total (€)"};
		Object[][] datos = {{"Nombre","Varianza", "Valor total (€)"},{"Total","X%","x€"},{"Hoy","X%","x€"}};
		JTable tablaResumen = new JTable(datos, columnas);
		JScrollPane scroll = new JScrollPane(tablaResumen);
		activosPanel.add(scroll);
		
		tablaResumen.setFont(FONT_NORMAL2);
		tablaResumen.setRowHeight(20);
		tablaResumen.getTableHeader().setFont(FONT_NORMAL2);		
		//TODO Mejorar tabla
		activosPanel.add(tablaResumen);

		activosPanel.setName("Portfolio");
		PanelFocus(activosPanel,null);
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
		
		int i = 0;
		for (ProductoFinanciero producto: productos) {
			if (productoRandom.getFirst()==producto) {
				break;
			}else {
				i+=1;
			}
		}
//		while (productos.size()>productoRandom.size()) {
//			if (productos.size()>i+1) {
//			}else {
//				i=0;
//				productoRandom.add(productos.get(i));
//			}
//		}
		
	
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