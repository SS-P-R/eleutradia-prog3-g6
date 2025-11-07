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
	
    private java.util.List<ProductoFinanciero> productos;

	Font font;

	public PanelInicio(Usuario usuario, VentanaPrincipal ventanaPrincipal) {
		this.usuario = usuario;
		this.ventanaPrincipal = ventanaPrincipal;
		
		//Fondo
		setLayout(new BorderLayout(10,10));
		setBackground(getBackground());
		
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
		JLabel saludoTxt = new JLabel("Hola " + usuario.getNombre()+ ",");
		
		saludoTxt.setBackground(Color.cyan);
		saludoTxt.setFont(new Font("Segoe UI",Font.BOLD,20));
		
		Border recuadro = BorderFactory.createLineBorder(Color.gray, 2);
		Border posicion = BorderFactory.createEmptyBorder(5,10,5,10);
		saludoTxt.setBorder(BorderFactory.createCompoundBorder(recuadro, posicion));
		
		panelSaludo.setBackground(getBackground());
		panelSaludo.add(saludoTxt);
		return panelSaludo;
	}	
	
	
	private JPanel PanelRecordatorio() {
		JPanel recordatorio = new JPanel(new FlowLayout());
		recordatorio.setBackground(getBackground());
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
				recordatorio.add(new JLabel("Todo correcto."));
			}
		}
		return recordatorio;
	}

	
	private JPanel PanelLecciones() {
		JPanel proxLecJPanel = new JPanel();
		proxLecJPanel.setBackground(new Color(173, 216, 230));
		proxLecJPanel.setBorder(BorderFactory.createLineBorder(Color.black,3));
		
		JLabel mensajeLecciones = new JLabel();
		mensajeLecciones.setText("¿Preparado para su próxima lección?");
		mensajeLecciones.setFont(new Font("Segoe UI", Font.BOLD,16));
		proxLecJPanel.add(mensajeLecciones);

		
		JPanel leccionesPanel = new JPanel();
		leccionesPanel.setLayout(new BoxLayout(leccionesPanel, BoxLayout.Y_AXIS));
		leccionesPanel.setBackground(proxLecJPanel.getBackground());
		proxLecJPanel.add(leccionesPanel);

		JLabel mensajeLecciones2 = new JLabel("Lecciones recomendadas para continuar aprendiendo:");
		mensajeLecciones2.setFont(new Font("Segoe UI", Font.BOLD,12));
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
				leccionesPanel.add(new JLabel(leccion.getTitulo()));
			}
			if(listaCursosActivos.isEmpty()) {
				leccionesPanel.add(Box.createVerticalStrut(10));
				leccionesPanel.add(new JLabel("Asombroso, has terminado todos las lecciones."));
				leccionesPanel.add(Box.createVerticalStrut(10));
				//TODO Añadir imagen de un trofeo
			}
			
			
		}else {
			leccion1.setText("Esta función no esta disponible");
		}
		
		proxLecJPanel.setName("Aprender");
		PanelFocus(proxLecJPanel, leccionesPanel);
		return proxLecJPanel;
	}

	
	private JPanel PanelCursos() {
		JPanel cursosPanel = new JPanel();
		cursosPanel.setBackground(new Color(173, 216, 230));
		cursosPanel.setBorder(BorderFactory.createLineBorder(Color.black,3));
		
		JLabel mensajeCursos = new JLabel();
		mensajeCursos.setText("Cursos en progreso: ");
		mensajeCursos.setFont(new Font("Segoe UI", Font.BOLD,16));
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
				cursosProgreso.add(Box.createVerticalStrut(10));
				cursosProgreso.add(new JLabel("Es hora de empezar un curso."));
				cursosProgreso.add(Box.createVerticalStrut(10));
				cursosProgreso.add(new JLabel("¿A que esperas?"));
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
		activosPanel.setBackground(new Color(173, 216, 230));
		activosPanel.setBorder(BorderFactory.createLineBorder(Color.black,3));
		
		JLabel mensajeActivos = new JLabel();
		mensajeActivos.setText("Resumen de sus activos: ");
		mensajeActivos.setFont(new Font("Segoe UI", Font.BOLD,16));
		activosPanel.add(mensajeActivos);
		
		String[] columnas = {"Nombre","Varianza", "Valor total (€)"};
		Object[][] datos = {{"Nombre","Varianza", "Valor total (€)"},{"Total","X%","x€"},{"Hoy","X%","x€"}};
		JTable tablaResumen = new JTable(datos, columnas);
		JScrollPane scroll = new JScrollPane(tablaResumen);
		activosPanel.add(scroll);
		
		tablaResumen.setFont(new Font("Segoe UI", Font.BOLD,12));
		tablaResumen.setRowHeight(20);
		tablaResumen.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD,12));		
		//TODO Mejorar tabla
		activosPanel.add(tablaResumen);

		activosPanel.setName("Portfolio");
		PanelFocus(activosPanel,null);
		return activosPanel;
	};
	
	
	private JPanel PanelGraficos() {
		JPanel panelGraficos = new JPanel();
		panelGraficos.setBackground(new Color(173, 216, 230));
		panelGraficos.setBorder(BorderFactory.createLineBorder(Color.black,3));
		
		String productoRandom = RandomizadorProductos().getNombre();
		JLabel mensajeGraficos = new JLabel("Echele un vistazo a " + "'"+productoRandom+"'");
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
        		+ "datasets:[{label:'"+productoRandom+"',data:["+RandomizadorValores()+","+RandomizadorValores()+","+RandomizadorValores()+","+RandomizadorValores()+"],"
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
        
		panelGraficos.setName("Grafico");
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
				if (panel.getName()=="Grafico") {
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