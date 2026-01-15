package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import es.deusto.eleutradia.domain.Cartera;
import es.deusto.eleutradia.domain.Curso;
import es.deusto.eleutradia.domain.Leccion;
import es.deusto.eleutradia.domain.Modulo;
import es.deusto.eleutradia.domain.Noticia;
import es.deusto.eleutradia.domain.Particular;
import es.deusto.eleutradia.domain.ProductoFinanciero;
import es.deusto.eleutradia.domain.Usuario;
import es.deusto.eleutradia.main.MainEleutradia;

import static es.deusto.eleutradia.gui.style.UITema.*;

public class PanelInicio extends JPanel{

	private static final long serialVersionUID = 1L;
	private VentanaPrincipal ventanaPrincipal;
	private Usuario usuario;
	private DecimalFormat formatoMoneda = new DecimalFormat("#, ##0.00");
	private DecimalFormat formatoPorcentaje = new DecimalFormat("0.00");
	private JFrame frame;
	
    private static final Dimension TAMANO_TARJETA_PEQUENA = new Dimension(140, 100);
    private ArrayList<ProductoFinanciero> productoRandom = new ArrayList<>();
    private List<ProductoFinanciero> productos;
    private int indiceProductoActual = 0;


	public PanelInicio(Usuario usuario, VentanaPrincipal ventanaPrincipal) {
		this.usuario = usuario;
		this.ventanaPrincipal = ventanaPrincipal;
		
		//Fondo
		this.setLayout(new BorderLayout(10,10));
		this.setBackground(MAIN_FONDO);
		this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		//Lista productos
		if (MainEleutradia.listaProductos!=null) {
			productos = new ArrayList<>(MainEleutradia.listaProductos);
		}else {
			productos = new ArrayList<>();
		}
		
        // Panel superior, bienvenida
        JPanel panelSuperior = crearPanelBienvenida();
        this.add(panelSuperior, BorderLayout.NORTH);
        
        // Panel central, info general
        JPanel panelCentral = crearPanelCentral();
        JScrollPane scrollCentral = new JScrollPane(panelCentral);
        scrollCentral.setBorder(BorderFactory.createEmptyBorder());
        scrollCentral.getVerticalScrollBar().setUI(personalizarScrollBarUI());
        scrollCentral.getHorizontalScrollBar().setUI(personalizarScrollBarUI());
        this.add(scrollCentral, BorderLayout.CENTER);
        
        // Panel lateral, estadísticas
        JPanel panelLateral = crearPanelLateral();
        this.add(panelLateral, BorderLayout.EAST);

		
//		//Cargar paneles
//		JPanel panelSaludo = construirPanelSaludo();
//		JPanel panelCursos = construirPanelCursos(); 
//		JPanel panelLecciones = construirPanelLecciones();
//		JPanel panelActivos = construirPanelActivos();
//		JPanel panelGraficos = construirPanelGraficos();
//		JPanel panelNoticias = panelTitularesNoticias();
//		JPanel panelSuperior = new JPanel();
//		
//		panelSuperior.setLayout(new BorderLayout());
//		panelSuperior.add(panelSaludo, BorderLayout.WEST);
//		panelSuperior.add(panelNoticias, BorderLayout.EAST);
//		//Paneles accesorios con saludo incial y recordatorios
//		setVisible(true);
//		panelSuperior.setBackground(MAIN_FONDO);
//		add(panelSuperior, BorderLayout.NORTH);
//		
//		//Panel central
//		JPanel centro = new JPanel(new GridLayout(2, 2, 10, 10));
//		centro.setBackground(getBackground());
//		centro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//		centro.add(panelCursos);
//		centro.add(panelLecciones);
//		centro.add(panelActivos);
//		centro.add(panelGraficos);
//		add(centro, BorderLayout.CENTER);
		
	}
	
	private JPanel crearPanelBienvenida() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MAIN_BORDE, 1),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        
        // Panel saludo
        JPanel panelSaludo = new JPanel();
        panelSaludo.setLayout(new BoxLayout(panelSaludo, BoxLayout.Y_AXIS));
        panelSaludo.setBackground(Color.WHITE);
        
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        String fechaFormateada = hoy.format(formato);
        
        JLabel labelFecha = new JLabel(fechaFormateada);
        labelFecha.setFont(CUERPO_PEQUENO);
        labelFecha.setForeground(GRIS_CLARO);
        labelFecha.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel labelBienvenida = new JLabel("¡Hola, " + usuario.getNombre().split(" ")[0] + "!");
        labelBienvenida.setFont(TITULO_GRANDE);
        labelBienvenida.setForeground(Color.BLACK);
        labelBienvenida.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel labelSubtitulo = new JLabel("Resumen de tu actividad financiera");
        labelSubtitulo.setFont(SUBTITULO_MEDIO);
        labelSubtitulo.setForeground(GRIS_MEDIO);
        labelSubtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelSaludo.add(labelFecha);
        panelSaludo.add(Box.createRigidArea(new Dimension(0, 5)));
        panelSaludo.add(labelBienvenida);
        panelSaludo.add(Box.createRigidArea(new Dimension(0, 3)));
        panelSaludo.add(labelSubtitulo);
        
        // Panel noticias
        JPanel panelNoticias = crearPanelNoticias();
        
        panel.add(panelSaludo, BorderLayout.WEST);
        panel.add(panelNoticias, BorderLayout.EAST);
        
        return panel;
    }
	
	
    private JPanel crearPanelNoticias() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(GRIS_SUAVE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MAIN_BORDE, 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        panel.setPreferredSize(new Dimension(350, 0));
        
        JLabel labelTitulo = new JLabel("Noticias Financieras");
        labelTitulo.setFont(SUBTITULO_MEDIO);
        labelTitulo.setForeground(AZUL_OSCURO);
        labelTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        List<Noticia> noticias = new ArrayList<>();
        noticias.add(new Noticia( "IBEX 35 sube un 3% tras datos de inflación positivos",     
        		"El IBEX 35 cerró la sesión de hoy con una subida del 3%, impulsado por "
        		  + "la publicación de datos de inflación mejores de lo esperado en la eurozona.\n\n"
        		  + "El índice se vio especialmente favorecido por el buen comportamiento de "
        		  + "los valores bancarios y energéticos, que lideraron las ganancias durante "
        		  + "toda la jornada bursátil.\n\n"
        		  + "Según analistas del mercado, la moderación de la inflación refuerza las "
        		  + "expectativas de una posible bajada de tipos de interés por parte del "
        		  + "Banco Central Europeo en los próximos meses, lo que ha incrementado "
        		  + "el optimismo entre los inversores."));
        noticias.add(new Noticia("El euro se fortalece frente al dólar por decisiones del BCE",
        	    "El euro se apreció frente al dólar estadounidense tras las últimas "
        	      + "declaraciones del Banco Central Europeo sobre su política monetaria.\n\n"
        	      + "El organismo confirmó su compromiso con la estabilidad de precios, "
        	      + "lo que fue interpretado por los mercados como una señal de confianza "
        	      + "en la recuperación económica de la zona euro.\n\n"
        	      + "Los expertos señalan que este movimiento podría tener un impacto "
        	      + "negativo en las exportaciones europeas, aunque beneficiaría "
        	      + "a los consumidores en la compra de productos importados."));
        noticias.add(new Noticia("Caen las tecnológicas en Wall Street por regulaciones",
        	    "Las principales compañías tecnológicas de Wall Street cerraron "
        	      + "la jornada con importantes descensos tras el anuncio de nuevas "
        	      + "regulaciones por parte de las autoridades estadounidenses.\n\n"
        	      + "Las medidas buscan reforzar la protección de datos y limitar "
        	      + "prácticas monopolísticas, lo que generó preocupación entre "
        	      + "los inversores del sector.\n\n"
        	      + "Empresas como Apple, Amazon y Meta lideraron las caídas, "
        	      + "arrastrando a los principales índices bursátiles a terreno negativo."));
        noticias.add(new Noticia("Sector bancario español muestra buenos resultados trimestrales",
        	    "Los principales bancos españoles presentaron resultados trimestrales "
        	      + "mejores de lo previsto, impulsados por el aumento del margen de intereses "
        	      + "y una mejora en la calidad crediticia.\n\n"
        	      + "Las entidades destacaron un crecimiento sostenido del negocio minorista "
        	      + "y una reducción de la morosidad, factores que han fortalecido la confianza "
        	      + "del mercado.\n\n"
        	      + "Estos resultados refuerzan la percepción positiva del sector, "
        	      + "que continúa beneficiándose del entorno actual de tipos de interés."));
        
		//IAG (ChatGPT)
        JLabel labelNoticia = new JLabel("<html><div style='width:300px'>" + noticias.get(0) + "</div></html>");
        //END-IAG
        labelNoticia.setFont(CUERPO_PEQUENO);
        labelNoticia.setForeground(GRIS_CLARO);
        labelNoticia.setAlignmentX(Component.LEFT_ALIGNMENT);
        labelNoticia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        final int[] indice = {0};
        
        //Hilo noticias
        Thread hiloNoticias = new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(5000);
                    indice[0] = (indice[0] + 1) % noticias.size();
                    SwingUtilities.invokeLater(() -> {
            			//IAG (ChatGPT)
                        labelNoticia.setText("<html><div style='width:300px'>" + noticias.get(0).getTitular() + "</div></html>");
                        //END-IAG
                    });
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        hiloNoticias.setDaemon(true);
        hiloNoticias.start();
        
        labelNoticia.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mostrarVentanaNoticia(noticias.get(indice[0]));
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                labelNoticia.setForeground(AZUL_CLARO);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                labelNoticia.setForeground(GRIS_CLARO);
            }
        });
        
        panel.add(labelTitulo);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(labelNoticia);
        
        return panel;
    }
    
    private void mostrarVentanaNoticia(Noticia noticia) {
        JFrame ventana = new JFrame("Noticia Completa");
        ventana.setSize(600, 400);
        ventana.setLocationRelativeTo(this);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel panelContenido = new JPanel(new BorderLayout(10, 10));
        panelContenido.setBackground(Color.WHITE);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel labelTitulo = new JLabel("Noticia Financiera");
        labelTitulo.setFont(TITULO_GRANDE);
        labelTitulo.setForeground(AZUL_OSCURO);
        
        JTextArea texto = new JTextArea(noticia.getContenido());
        texto.setFont(CUERPO_GRANDE);
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        texto.setEditable(false);
        texto.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scroll = new JScrollPane(texto);
        scroll.setBorder(BorderFactory.createLineBorder(MAIN_BORDE, 1));
        
        panelContenido.add(labelTitulo, BorderLayout.NORTH);
        panelContenido.add(scroll, BorderLayout.CENTER);
        
        ventana.add(panelContenido);
        ventana.setVisible(true);
    }
    
    
	private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 20, 20));
        panel.setBackground(MAIN_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        //Cargar paneles
        panel.add(crearPanelCursos());
        panel.add(crearPanelLecciones());
        panel.add(crearPanelCartera());
        panel.add(crearPanelGrafico());
        
        return panel;
	}
	
	private JPanel crearPanelCursos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(MAIN_BORDE, 1));
        
        //Encabezado
        JPanel panelEncabezado = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelEncabezado.setBackground(Color.WHITE);
        panelEncabezado.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));
        
        JLabel labelTitulo = new JLabel("Cursos en progreso:");
        labelTitulo.setFont(SUBTITULO_GRANDE);
        labelTitulo.setForeground(AZUL_OSCURO);
        panelEncabezado.add(labelTitulo);
        
        //Contenido
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBackground(Color.WHITE);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));
        
        if (usuario instanceof Particular) {
            Particular particular = (Particular) usuario;
            List<Curso> cursosActivos = particular.getCursos();
            
            if (cursosActivos.isEmpty()) {
                JLabel labelVacio = new JLabel("Aún no tienes cursos activos");
                labelVacio.setFont(CUERPO_GRANDE);
                labelVacio.setForeground(GRIS_CLARO);
                labelVacio.setAlignmentX(Component.LEFT_ALIGNMENT);
                panelContenido.add(labelVacio);
                panelContenido.add(Box.createRigidArea(new Dimension(0, 10)));
                
                JLabel labelAnimacion = new JLabel("¡Es hora de empezar!");
                labelAnimacion.setFont(CUERPO_MEDIO);
                labelAnimacion.setForeground(AZUL_CLARO);
                labelAnimacion.setAlignmentX(Component.LEFT_ALIGNMENT);
                panelContenido.add(labelAnimacion);
                
            } else {
                int contador = 0;
                for (Curso curso : cursosActivos) {
                    if (contador >= 4) break;
                    
                    JLabel labelCurso = new JLabel("- " + curso.getNombre());
                    labelCurso.setFont(CUERPO_GRANDE);
                    labelCurso.setForeground(Color.BLACK);
                    labelCurso.setAlignmentX(Component.LEFT_ALIGNMENT);
                    panelContenido.add(labelCurso);
                    panelContenido.add(Box.createRigidArea(new Dimension(0, 5)));
                    contador++;
                }
                
                if (cursosActivos.size() > 4) {
                    JLabel labelMas = new JLabel("... y " + (cursosActivos.size() - 4) + " más");
                    labelMas.setFont(CUERPO_PEQUENO);
                    labelMas.setForeground(GRIS_CLARO);
                    labelMas.setAlignmentX(Component.LEFT_ALIGNMENT);
                    panelContenido.add(labelMas);
                }
            }
        } else {
            JLabel labelNoDisponible = new JLabel("Función no disponible para empresas");
            labelNoDisponible.setFont(CUERPO_GRANDE);
            labelNoDisponible.setForeground(GRIS_CLARO);
            labelNoDisponible.setAlignmentX(Component.LEFT_ALIGNMENT);
            panelContenido.add(labelNoDisponible);
        }
        
        panel.add(panelEncabezado, BorderLayout.NORTH);
        panel.add(panelContenido, BorderLayout.CENTER);
        
        //Focus y click
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBorder(BorderFactory.createLineBorder(AZUL_CLARO, 2));
                panel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBorder(BorderFactory.createLineBorder(MAIN_BORDE, 1));
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                ventanaPrincipal.mostrarPanel("Aprender");
            }
        });
        
        return panel;
    }
	
	private JPanel crearPanelLecciones() {
	        JPanel panel = new JPanel(new BorderLayout(10, 10));
	        panel.setBackground(Color.WHITE);
	        panel.setBorder(BorderFactory.createLineBorder(MAIN_BORDE, 1));
	        
	        // Encabezado
	        JPanel panelEncabezado = new JPanel(new FlowLayout(FlowLayout.LEFT));
	        panelEncabezado.setBackground(Color.WHITE);
	        panelEncabezado.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));
	        
	        JLabel labelTitulo = new JLabel("📖 Próximas Lecciones");
	        labelTitulo.setFont(SUBTITULO_GRANDE);
	        labelTitulo.setForeground(AZUL_OSCURO);
	        panelEncabezado.add(labelTitulo);
	        
	        // Contenido
	        JPanel panelContenido = new JPanel();
	        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
	        panelContenido.setBackground(Color.WHITE);
	        panelContenido.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));
	        
	        if (usuario instanceof Particular) {
	            Particular particular = (Particular) usuario;
	            List<Curso> cursosActivos = particular.getCursos();
	            List<Curso> todosCursos = MainEleutradia.listaCursos;
	            
	            if (todosCursos == null || todosCursos.isEmpty()) {
	                JLabel labelError = new JLabel("No hay cursos disponibles");
	                labelError.setFont(CUERPO_GRANDE);
	                labelError.setForeground(GRIS_CLARO);
	                labelError.setAlignmentX(Component.LEFT_ALIGNMENT);
	                panelContenido.add(labelError);
	            } else {
	                List<Leccion> leccionesRecomendadas = new ArrayList<>();
	                
	                // Buscar primeras lecciones de cursos no inscritos
	                for (Curso curso : todosCursos) {
	                    if (!cursosActivos.contains(curso)) {
	                        if (curso.getModulos() != null && !curso.getModulos().isEmpty()) {
	                            Modulo primerModulo = curso.getModulos().get(0);
	                            if (primerModulo.getLecciones() != null && !primerModulo.getLecciones().isEmpty()) {
	                                leccionesRecomendadas.add(primerModulo.getLecciones().get(0));
	                                if (leccionesRecomendadas.size() >= 4) break;
	                            }
	                        }
	                    }
	                }
	                
	                if (leccionesRecomendadas.isEmpty()) {
	                    if (cursosActivos.size() == todosCursos.size()) {
	                        JLabel labelFelicitacion = new JLabel("¡Has completado todos los cursos! 🏆");
	                        labelFelicitacion.setFont(CUERPO_GRANDE);
	                        labelFelicitacion.setForeground(VERDE_CLARO);
	                        labelFelicitacion.setAlignmentX(Component.LEFT_ALIGNMENT);
	                        panelContenido.add(labelFelicitacion);
	                    } else {
	                        JLabel labelVacio = new JLabel("No hay lecciones nuevas disponibles");
	                        labelVacio.setFont(CUERPO_GRANDE);
	                        labelVacio.setForeground(GRIS_CLARO);
	                        labelVacio.setAlignmentX(Component.LEFT_ALIGNMENT);
	                        panelContenido.add(labelVacio);
	                    }
	                } else {
	                    JLabel labelSubtitulo = new JLabel("Recomendadas para ti:");
	                    labelSubtitulo.setFont(CUERPO_PEQUENO);
	                    labelSubtitulo.setForeground(GRIS_MEDIO);
	                    labelSubtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
	                    panelContenido.add(labelSubtitulo);
	                    panelContenido.add(Box.createRigidArea(new Dimension(0, 8)));
	                    
	                    for (Leccion leccion : leccionesRecomendadas) {
	                        JLabel labelLeccion = new JLabel("- " + leccion.getTitulo());
	                        labelLeccion.setFont(CUERPO_GRANDE);
	                        labelLeccion.setForeground(Color.BLACK);
	                        labelLeccion.setAlignmentX(Component.LEFT_ALIGNMENT);
	                        panelContenido.add(labelLeccion);
	                        panelContenido.add(Box.createRigidArea(new Dimension(0, 5)));
	                    }
	                }
	            }
	        } else {
	            JLabel labelNoDisponible = new JLabel("Función no disponible para empresas");
	            labelNoDisponible.setFont(CUERPO_GRANDE);
	            labelNoDisponible.setForeground(GRIS_CLARO);
	            labelNoDisponible.setAlignmentX(Component.LEFT_ALIGNMENT);
	            panelContenido.add(labelNoDisponible);
	        }
	        
	        panel.add(panelEncabezado, BorderLayout.NORTH);
	        panel.add(panelContenido, BorderLayout.CENTER);
	        
	        //Focus y click
	        panel.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseEntered(MouseEvent e) {
	                panel.setBorder(BorderFactory.createLineBorder(AZUL_CLARO, 2));
	                panel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	            }
	            
	            @Override
	            public void mouseExited(MouseEvent e) {
	            	panel.setBorder(BorderFactory.createLineBorder(MAIN_BORDE, 1));
	            }
	            
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                ventanaPrincipal.mostrarPanel("Aprender");
	            }
	        });
	        
	        return panel;
	    }
	  
	  private JPanel crearPanelCartera() {
	        JPanel panel = new JPanel(new BorderLayout(10, 10));
	        panel.setBackground(Color.WHITE);
	        panel.setBorder(BorderFactory.createLineBorder(MAIN_BORDE, 1));
	        
	        //Encabezado
	        JPanel panelEncabezado = new JPanel(new FlowLayout(FlowLayout.LEFT));
	        panelEncabezado.setBackground(Color.WHITE);
	        panelEncabezado.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));
	        
	        JLabel labelTitulo = new JLabel("Resumen de tu cartera:");
	        labelTitulo.setFont(SUBTITULO_GRANDE);
	        labelTitulo.setForeground(AZUL_OSCURO);
	        panelEncabezado.add(labelTitulo);
	        
	        //Contenido
	        JPanel panelContenido = new JPanel();
	        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
	        panelContenido.setBackground(Color.WHITE);
	        panelContenido.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));
	        
	        List<Cartera> carteras = usuario.getCarteras();
	        
	        if (carteras == null || carteras.isEmpty()) {
	            JLabel labelVacio = new JLabel("No tienes carteras creadas");
	            labelVacio.setFont(CUERPO_GRANDE);
	            labelVacio.setForeground(GRIS_CLARO);
	            labelVacio.setAlignmentX(Component.LEFT_ALIGNMENT);
	            panelContenido.add(labelVacio);
	            
	            panelContenido.add(Box.createRigidArea(new Dimension(0, 10)));
	            
	            JLabel labelAnimacion = new JLabel("¡Crea tu primera cartera!");
	            labelAnimacion.setFont(CUERPO_MEDIO);
	            labelAnimacion.setForeground(AZUL_CLARO);
	            labelAnimacion.setAlignmentX(Component.LEFT_ALIGNMENT);
	            panelContenido.add(labelAnimacion);
	            
	        } else {
	            double valorTotal = 0;
	            Cartera carteraMayor = null;
	            double valorMaximo = 0;
	            
	            for (Cartera c : carteras) {
	                double saldo = c.getSaldo();
	                valorTotal += saldo;
	                
	                if (carteraMayor == null || saldo > valorMaximo) {
	                    valorMaximo = saldo;
	                    carteraMayor = c;
	                }
	            }
	            
	            //Valor total
	            JPanel panelTotal = crearFilaEstadistica("Valor Total:", formatoMoneda.format(valorTotal) + " €", AZUL_OSCURO);
	            panelContenido.add(panelTotal);
	            panelContenido.add(Box.createRigidArea(new Dimension(0, 8)));
	            
	            //Número de carteras
	            JPanel panelNumero = crearFilaEstadistica("Carteras:", String.valueOf(carteras.size()), GRIS_MEDIO);
	            panelContenido.add(panelNumero);
	            panelContenido.add(Box.createRigidArea(new Dimension(0, 8)));
	            
	            //Mejor cartera
	            if (carteraMayor != null) {
	                JPanel panelMayor = crearFilaEstadistica("Mayor:", carteraMayor.getNombre(), VERDE_CLARO);
	                panelContenido.add(panelMayor);
	                
	                JLabel labelValorMayor = new JLabel("   " + formatoMoneda.format(valorMaximo) + " €");
	                labelValorMayor.setFont(CUERPO_PEQUENO);
	                labelValorMayor.setForeground(GRIS_CLARO);
	                labelValorMayor.setAlignmentX(Component.LEFT_ALIGNMENT);
	                panelContenido.add(labelValorMayor);
	            }
	        }
	        
	        panel.add(panelEncabezado, BorderLayout.NORTH);
	        panel.add(panelContenido, BorderLayout.CENTER);
	        
	        //Focus y click
	        panel.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseEntered(MouseEvent e) {
	                panel.setBorder(BorderFactory.createLineBorder(AZUL_CLARO, 2));
	                panel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	            }
	            
	            @Override
	            public void mouseExited(MouseEvent e) {
	                panel.setBorder(BorderFactory.createLineBorder(MAIN_BORDE, 1));
	            }
	            
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                ventanaPrincipal.mostrarPanel("Portfolio");
	            }
	        });
	        
	        return panel;
	    }
	
	    private JPanel crearPanelGrafico() {
	        JPanel panel = new JPanel(new BorderLayout(10, 10));
	        return panel;
	    }
	                       
	
	private JPanel crearPanelLateral() {
		return null;
	}

    private JPanel crearFilaEstadistica(String etiqueta, String valor, Color colorValor) {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        
        JLabel labelEtiqueta = new JLabel(etiqueta);
        labelEtiqueta.setFont(CUERPO_MEDIO);
        labelEtiqueta.setForeground(GRIS_CLARO);
        
        JLabel labelValor = new JLabel(valor);
        labelValor.setFont(CUERPO_GRANDE);
        labelValor.setForeground(colorValor);
        labelValor.setHorizontalAlignment(SwingConstants.RIGHT);
        
        panel.add(labelEtiqueta, BorderLayout.WEST);
        panel.add(labelValor, BorderLayout.EAST);
        
        return panel;
    }

}