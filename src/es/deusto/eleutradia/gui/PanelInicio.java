package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

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

public class PanelInicio extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private VentanaPrincipal ventanaPrincipal;
	private Usuario usuario;
	private DecimalFormat formatoMoneda = new DecimalFormat("#, ##0.00");
	private DecimalFormat formatoPorcentaje = new DecimalFormat("0.00");
	private JFrame frame;
	
    private List<ProductoFinanciero> productos;
    private int indiceProductoActual = 0;


	public PanelInicio(Usuario usuario, VentanaPrincipal ventanaPrincipal) {
		this.usuario = usuario;
		this.ventanaPrincipal = ventanaPrincipal;
		
		// Fondo
		this.setLayout(new BorderLayout(10,10));
		this.setBackground(MAIN_FONDO);
		this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		// Lista productos
		if (MainEleutradia.listaProductos!=null) {
			productos = new ArrayList<>(MainEleutradia.listaProductos);
		}else {
			productos = new ArrayList<>();
		}
		
        // Panel superior: bienvenida
        JPanel panelSuperior = crearPanelBienvenida();
        this.add(panelSuperior, BorderLayout.NORTH);
        
        // Panel central: información general
        JPanel panelCentral = crearPanelCentral();
        this.add(configurarScrollPane(panelCentral), BorderLayout.CENTER);
        
        // Panel lateral: estadísticas
        JPanel panelInferior = crearPanelInferior();
        this.add(panelInferior, BorderLayout.SOUTH);
	}
	
	private JPanel crearPanelBienvenida() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MAIN_BORDE, 1),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        
        // Panel de saludo
        JPanel panelSaludo = new JPanel();
        panelSaludo.setLayout(new BoxLayout(panelSaludo, BoxLayout.Y_AXIS));
        panelSaludo.setBackground(Color.WHITE);
        
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy");
        String fechaFormateada = hoy.format(formato);
        
        JLabel labelFecha = new JLabel(fechaFormateada);
        labelFecha.setFont(CUERPO_PEQUENO);
        labelFecha.setForeground(GRIS_CLARO);
        labelFecha.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel labelBienvenida = new JLabel("¡Hola, " + usuario.getNombre().split(" ")[0] + "!");
        labelBienvenida.setFont(TITULO_GRANDE);
        labelBienvenida.setForeground(Color.BLACK);
        labelBienvenida.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel labelSubtitulo = new JLabel("Visión general");
        labelSubtitulo.setFont(SUBTITULO_MEDIO);
        labelSubtitulo.setForeground(GRIS_MEDIO);
        labelSubtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelSaludo.add(labelFecha);
        panelSaludo.add(Box.createRigidArea(new Dimension(0, 5)));
        panelSaludo.add(labelBienvenida);
        panelSaludo.add(Box.createRigidArea(new Dimension(0, 3)));
        panelSaludo.add(labelSubtitulo);
        
        // Panel de noticias
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
        
        JTextArea areaNoticia = new JTextArea();
        areaNoticia.setText(noticias.get(0).getTitular());
        areaNoticia.setFont(CUERPO_PEQUENO);
        areaNoticia.setForeground(GRIS_CLARO);
        areaNoticia.setLineWrap(true);
        areaNoticia.setWrapStyleWord(true);
        areaNoticia.setEditable(false);
        areaNoticia.setOpaque(false);
        areaNoticia.setAlignmentX(Component.LEFT_ALIGNMENT);
        areaNoticia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        areaNoticia.setBorder(null);
        areaNoticia.setBackground(GRIS_SUAVE);
        
        final int[] indice = {0};
        
        // Hilo de noticias
        Thread hiloNoticias = new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(5000);
                    indice[0] = (indice[0] + 1) % noticias.size();
                    SwingUtilities.invokeLater(() -> {
                        areaNoticia.setText(noticias.get(indice[0]).getTitular());
                    });
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        hiloNoticias.setDaemon(true);
        hiloNoticias.start();
        
        areaNoticia.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mostrarVentanaNoticia(noticias.get(indice[0]));
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
            	areaNoticia.setForeground(AZUL_CLARO);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
            	areaNoticia.setForeground(GRIS_CLARO);
            }
        });
        
        panel.add(labelTitulo);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(areaNoticia);
        
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
        
        JLabel labelTitulo = new JLabel("Noticia financiera");
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
        
        // Cargar paneles
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
        
        // Encabezado
        JPanel panelEncabezado = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelEncabezado.setBackground(Color.WHITE);
        panelEncabezado.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));
        
        JLabel labelTitulo = new JLabel("Cursos en progreso:");
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
            
            if (cursosActivos.isEmpty()) {
                JLabel labelVacio = new JLabel("Aún no tiene cursos activos");
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
        
        // Focus y click
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBorder(BorderFactory.createLineBorder(AZUL_CLARO, 1));
                panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
	        
	    JLabel labelTitulo = new JLabel("Próximas lecciones");
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
	                
	                // Primeras lecciones de cursos no inscritos
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
	                        JLabel labelFelicitacion = new JLabel("¡Ha completado todos los cursos!");
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
	                    JLabel labelSubtitulo = new JLabel("Nuestras recomendaciones:");
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
	        
	        // Focus y click
	        panel.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseEntered(MouseEvent e) {
	                panel.setBorder(BorderFactory.createLineBorder(AZUL_CLARO, 1));
	                panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
	        
	        // Encabezado
	        JPanel panelEncabezado = new JPanel(new FlowLayout(FlowLayout.LEFT));
	        panelEncabezado.setBackground(Color.WHITE);
	        panelEncabezado.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));
	        
	        JLabel labelTitulo = new JLabel("Resumen de su cartera:");
	        labelTitulo.setFont(SUBTITULO_GRANDE);
	        labelTitulo.setForeground(AZUL_OSCURO);
	        panelEncabezado.add(labelTitulo);
	        
	        // Contenido
	        JPanel panelContenido = new JPanel();
	        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
	        panelContenido.setBackground(Color.WHITE);
	        panelContenido.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));
	        
	        List<Cartera> carteras = usuario.getCarteras();
	        
	        if (carteras == null || carteras.isEmpty()) {
	            JLabel labelVacio = new JLabel("No tiene carteras creadas");
	            labelVacio.setFont(CUERPO_GRANDE);
	            labelVacio.setForeground(GRIS_CLARO);
	            labelVacio.setAlignmentX(Component.LEFT_ALIGNMENT);
	            panelContenido.add(labelVacio);
	            
	            panelContenido.add(Box.createRigidArea(new Dimension(0, 10)));
	            
	            JLabel labelAnimacion = new JLabel("¡Cree su primera cartera!");
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
	            
	            // Valor total
	            JPanel panelTotal = crearFilaEstadistica("Valor total:", formatoMoneda.format(valorTotal) + " €", AZUL_OSCURO);
	            panelContenido.add(panelTotal);
	            panelContenido.add(Box.createRigidArea(new Dimension(0, 8)));
	            
	            // Número de carteras
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
	        
	        // Focus y click
	        panel.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseEntered(MouseEvent e) {
	                panel.setBorder(BorderFactory.createLineBorder(AZUL_CLARO, 1));
	                panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
		panel.setBackground(Color.WHITE);
		panel.setBorder(BorderFactory.createLineBorder(MAIN_BORDE, 1));
	        
		// Encabezado
		JPanel panelEncabezado = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelEncabezado.setBackground(Color.WHITE);
		panelEncabezado.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));
	        
		JLabel labelTitulo = new JLabel("Producto destacado");
		labelTitulo.setFont(SUBTITULO_GRANDE);
		labelTitulo.setForeground(AZUL_OSCURO);
		panelEncabezado.add(labelTitulo);
    
		// Contenido
		JPanel panelContenido = new JPanel();
		panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
		panelContenido.setBackground(Color.WHITE);
		panelContenido.setBorder(BorderFactory.createEmptyBorder(5, 15, 10, 15));
	        
		if (productos == null || productos.isEmpty()) {
			JLabel labelVacio = new JLabel("No hay productos disponibles");
			labelVacio.setFont(CUERPO_GRANDE);
			labelVacio.setForeground(GRIS_CLARO);
			labelVacio.setAlignmentX(Component.LEFT_ALIGNMENT);
			panelContenido.add(labelVacio);
		} else {
	        JLabel labelNombreProducto = new JLabel();
            labelNombreProducto.setFont(CUERPO_GRANDE);
            labelNombreProducto.setForeground(Color.BLACK);
            labelNombreProducto.setAlignmentX(Component.LEFT_ALIGNMENT);
            panelContenido.add(labelNombreProducto);
            panelContenido.add(Box.createRigidArea(new Dimension(0, 5)));
            
            JLabel labelPrecioProducto = new JLabel();
            labelPrecioProducto.setFont(SUBTITULO_MEDIO);
            labelPrecioProducto.setForeground(AZUL_OSCURO);
            labelPrecioProducto.setAlignmentX(Component.LEFT_ALIGNMENT);
            panelContenido.add(labelPrecioProducto);
            panelContenido.add(Box.createRigidArea(new Dimension(0, 8)));
            
            JLabel labelVariacionProducto = new JLabel();
            labelVariacionProducto.setFont(CUERPO_PEQUENO);
            labelVariacionProducto.setAlignmentX(Component.LEFT_ALIGNMENT);
            panelContenido.add(labelVariacionProducto);
            
            JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panelBoton.setBackground(Color.WHITE);
            panelBoton.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
            
            JButton botonSiguiente = new JButton("Siguiente");
            botonSiguiente.setFont(CUERPO_MEDIO);
            botonSiguiente.setBackground(AZUL_CLARO);
            botonSiguiente.setForeground(Color.WHITE);
            botonSiguiente.setBorderPainted(false);
            botonSiguiente.setContentAreaFilled(false);
            botonSiguiente.setOpaque(true);
            botonSiguiente.setFocusPainted(false);
            botonSiguiente.addMouseListener(myAdapterAzul);

            panelBoton.add(botonSiguiente);
            panelContenido.add(panelBoton);

            // Actualizar producto
            Runnable actualizarProducto = () -> {
                ProductoFinanciero productoActual = productos.get(indiceProductoActual);

                // Aleatorio
                Random random = new Random();
                double variacion = (random.nextDouble() * 6) - 3;

                String simbolo;
                Color colorVariacion;

                if (variacion >= 0) {
                    simbolo = "▲";
                    colorVariacion = VERDE_CLARO;
                } else {
                    simbolo = "▼";
                    colorVariacion = Color.RED;
                }

                labelNombreProducto.setText(productoActual.getNombre());
                labelPrecioProducto.setText(formatoMoneda.format(productoActual.getValorUnitario()) + " €");
                labelVariacionProducto.setText(simbolo + " " + formatoPorcentaje.format(Math.abs(variacion)) + "%");
                labelVariacionProducto.setForeground(colorVariacion);
            };

            // Botón para actualizar el producto
            botonSiguiente.addActionListener(e -> {
                indiceProductoActual = (indiceProductoActual + 1) % productos.size();
                SwingUtilities.invokeLater(actualizarProducto);
            });

            // Hilo para rotar de manera automática sin pulsar el botón
            Thread hiloProductos = new Thread(() -> {
                try {
                    while (true) {
                        Thread.sleep(8000);
                        indiceProductoActual = (indiceProductoActual + 1) % productos.size();
                        SwingUtilities.invokeLater(actualizarProducto);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            hiloProductos.setDaemon(true);
            hiloProductos.start();

            SwingUtilities.invokeLater(actualizarProducto);
        }
        
	    panel.add(panelEncabezado, BorderLayout.NORTH);
	    panel.add(panelContenido, BorderLayout.CENTER);
        
        // Focus y click
	    panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	panel.setBorder(BorderFactory.createLineBorder(AZUL_CLARO, 1));
            	panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
            	panel.setBorder(BorderFactory.createLineBorder(MAIN_BORDE, 1));
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                if (frame == null) {
                	frame = new JFrame("Detalles del Producto");
                	frame.setSize(800, 600);
                	frame.setLocationRelativeTo(null);
                	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                }
                
                JPanel panelDetalles = crearPanelDetallesProducto();
                frame.getContentPane().removeAll();
                frame.add(panelDetalles);
                frame.revalidate();
                frame.repaint();
                frame.setVisible(true);
            }
        });
	    return panel;
	}
	    
	private JPanel crearPanelDetallesProducto() {
		JPanel panel = new JPanel(new BorderLayout(15, 15));
		panel.setBackground(Color.WHITE);
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	        
		ProductoFinanciero producto = productos.get(indiceProductoActual);
	        
		JLabel labelTitulo = new JLabel(producto.getNombre());
		labelTitulo.setFont(TITULO_GRANDE);
		labelTitulo.setForeground(AZUL_OSCURO);
	        
		JPanel panelInfo = new JPanel();
		panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
		panelInfo.setBackground(Color.WHITE);
		panelInfo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
	        
		JLabel labelPrecio = new JLabel("Precio actual: " + formatoMoneda.format(producto.getValorUnitario()) + " €");
		labelPrecio.setFont(SUBTITULO_GRANDE);
		labelPrecio.setForeground(Color.BLACK);
		labelPrecio.setAlignmentX(Component.LEFT_ALIGNMENT);
	        
		JTextArea areaDescripcion = new JTextArea("Información detallada del producto financiero.");
		areaDescripcion.setFont(CUERPO_GRANDE);
		areaDescripcion.setForeground(GRIS_MEDIO);
		areaDescripcion.setLineWrap(true);
		areaDescripcion.setWrapStyleWord(true);
		areaDescripcion.setEditable(false);
		areaDescripcion.setOpaque(false);
		areaDescripcion.setAlignmentX(Component.LEFT_ALIGNMENT);
		areaDescripcion.setBorder(null);
	        
		panelInfo.add(labelPrecio);
		panelInfo.add(Box.createRigidArea(new Dimension(0, 15)));
		panelInfo.add(areaDescripcion);
		panelInfo.add(Box.createRigidArea(new Dimension(0, 20)));
	        
		// Panel con gráfico de evolución
		JPanel panelGrafico = crearGrafico(producto);
		panelGrafico.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		panelInfo.add(panelGrafico);

	        
		panel.add(labelTitulo, BorderLayout.NORTH);
		panel.add(panelInfo, BorderLayout.CENTER);
	        
		return panel;
	    }
	
	private JPanel crearGrafico (ProductoFinanciero producto) {
		JPanel panel = new JPanel(new BorderLayout(10,10));
		panel.setBackground(Color.WHITE);
		Border borde1 = BorderFactory.createLineBorder(MAIN_BORDE,1);
		Border borde2 = BorderFactory.createEmptyBorder(15,15,15,15);
		panel.setBorder(BorderFactory.createCompoundBorder(borde1,borde2));
		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 350));
		
		JLabel labelTitulo = new JLabel("Evolución del producto durante los últimos 7 días");
		labelTitulo.setFont(SUBTITULO_MEDIO);
		labelTitulo.setForeground(AZUL_OSCURO);
		
        Random random = new Random();
        double precioBase = producto.getValorUnitario();
        List<Double> valores = new ArrayList<>();
        List<String> fechas = new ArrayList<>();
        
        for (int i = 6; i >= 0; i--) {
            double variacion = (random.nextDouble() * 0.1 - 0.05);
            double precio = precioBase * (1 + variacion);
            valores.add(precio);
            
            LocalDate fecha = LocalDate.now().minusDays(i);
            fechas.add(fecha.format(DateTimeFormatter.ofPattern("dd/MM")));
        }
        
        // Gráfico
        JPanel panelGrafico = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dibujarGrafico(g, valores, fechas);
            }
        };
        panelGrafico.setBackground(Color.WHITE);
        panelGrafico.setPreferredSize(new Dimension(700, 250));
        
        panel.add(labelTitulo, BorderLayout.NORTH);
        panel.add(panelGrafico, BorderLayout.CENTER);

		
		return panel;
	}
	
	
	//IAG (ChatGPT)
	//SIN CAMBIOS
    private void dibujarGrafico(Graphics g, List<Double> valores, List<String> fechas) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = g2d.getClipBounds().width;
        int height = g2d.getClipBounds().height;
        int padding = 40;
        int ejeYPadding = 60;
        int labelPadding = 20;
        
        // Calcular valores máximos y mínimos
        double maxValor = valores.stream().max(Double::compare).orElse(100.0);
        double minValor = valores.stream().min(Double::compare).orElse(0.0);
        double rango = maxValor - minValor;
        if (rango == 0) rango = 1;
        
        // Dibujar ejes
        g2d.setColor(GRIS_CLARO);
        g2d.drawLine(ejeYPadding, height - padding, width - padding, height - padding); // Eje X
        g2d.drawLine(ejeYPadding, padding, ejeYPadding, height - padding); // Eje Y
        
        // Dibujar líneas de cuadrícula horizontales
        g2d.setColor(new Color(230, 230, 230));
        for (int i = 0; i < 5; i++) {
            int y = padding + (height - 2 * padding) * i / 4;
            g2d.drawLine(ejeYPadding, y, width - padding, y);
        }
        
        // Dibujar etiquetas del eje Y
        g2d.setColor(GRIS_MEDIO);
        g2d.setFont(CUERPO_PEQUENO);
        for (int i = 0; i < 5; i++) {
            double valor = maxValor - (rango * i / 4);
            String label = String.format("%.2f€", valor);
            int y = padding + (height - 2 * padding) * i / 4;
            FontMetrics fm = g2d.getFontMetrics();
            int labelWidth = fm.stringWidth(label);
            g2d.drawString(label, ejeYPadding - labelWidth - 8, y + fm.getAscent() / 2);
        }
        
        // Dibujar puntos y líneas
        int espacioX = (width - ejeYPadding - padding) / (valores.size() - 1);
        
        // Dibujar la línea
        g2d.setColor(AZUL_CLARO);
        g2d.setStroke(new java.awt.BasicStroke(2f));
        for (int i = 0; i < valores.size() - 1; i++) {
            int x1 = ejeYPadding  + i * espacioX;
            int x2 = ejeYPadding  + (i + 1) * espacioX;
            
            int y1 = height - padding - (int)((valores.get(i) - minValor) / rango * (height - 2 * padding));
            int y2 = height - padding - (int)((valores.get(i + 1) - minValor) / rango * (height - 2 * padding));
            
            g2d.drawLine(x1, y1, x2, y2);
        }
        
        // Dibujar puntos
        for (int i = 0; i < valores.size(); i++) {
            int x = ejeYPadding + i * espacioX;
            int y = height - padding - (int)((valores.get(i) - minValor) / rango * (height - 2 * padding));
            
            // Punto
            g2d.setColor(AZUL_OSCURO);
            g2d.fillOval(x - 4, y - 4, 8, 8);
            
            // Borde del punto
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new java.awt.BasicStroke(2f));
            g2d.drawOval(x - 4, y - 4, 8, 8);
            
            // Etiqueta fecha
            g2d.setColor(GRIS_MEDIO);
            g2d.setFont(CUERPO_PEQUENO);
            FontMetrics fm = g2d.getFontMetrics();
            int labelWidth = fm.stringWidth(fechas.get(i));
            g2d.drawString(fechas.get(i), x - labelWidth / 2, height - padding + labelPadding);
        }
        
        // Dibujar área bajo la curva (efecto de relleno)
        g2d.setColor(new Color(AZUL_CLARO.getRed(), AZUL_CLARO.getGreen(), AZUL_CLARO.getBlue(), 30));
        int[] xPoints = new int[valores.size() + 2];
        int[] yPoints = new int[valores.size() + 2];
        
        for (int i = 0; i < valores.size(); i++) {
            xPoints[i] = ejeYPadding  + i * espacioX;
            yPoints[i] = height - padding - (int)((valores.get(i) - minValor) / rango * (height - 2 * padding));
        }
        xPoints[valores.size()] = ejeYPadding + (valores.size() - 1) * espacioX;
        yPoints[valores.size()] = height - padding;
        xPoints[valores.size() + 1] = ejeYPadding;
        yPoints[valores.size() + 1] = height - padding;
        
        g2d.fillPolygon(xPoints, yPoints, valores.size() + 2);
    }

    //END IAG                   
	
	private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 20, 0));
        panel.setBackground(MAIN_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        // Algunas estadísticas
        JPanel tarjetaEstadisticas = crearPanelEstadisticasBasicas();
        panel.add(tarjetaEstadisticas);
        
        // Algunas acciones
        JPanel tarjetaAcciones = crearPanelAccionesBasicas();
        panel.add(tarjetaAcciones);
        
        return panel;
	}
	
    private JPanel crearPanelEstadisticasBasicas() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MAIN_BORDE, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel labelTitulo = new JLabel("Estadísticas:");
        labelTitulo.setFont(SUBTITULO_MEDIO);
        labelTitulo.setForeground(AZUL_OSCURO);
        labelTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(labelTitulo);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Últimas operaciones
        int numOperaciones = 0;
        for (Cartera c : usuario.getCarteras()) {
            if (c.getOperaciones() != null) {
                numOperaciones += c.getOperaciones().size();
            }
        } 
        JPanel panelOperaciones = crearFilaEstadistica("Operaciones:", String.valueOf(numOperaciones), GRIS_MEDIO);
        panel.add(panelOperaciones);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Cursos activos
        if (usuario instanceof Particular) {
            Particular particular = (Particular) usuario;
            int numCursos = particular.getCursos().size();
            JPanel panelCursos = crearFilaEstadistica("Cursos activos:", String.valueOf(numCursos), VERDE_CLARO);
            panel.add(panelCursos);
        }
        
        return panel;
    }
    
    private JPanel crearPanelAccionesBasicas() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MAIN_BORDE, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel labelTitulo = new JLabel("Continúe aprendiendo e invirtiendo");
        labelTitulo.setFont(SUBTITULO_MEDIO);
        labelTitulo.setForeground(AZUL_OSCURO);
        labelTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(labelTitulo);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        JButton btnNuevaCartera = new JButton("Nueva cartera");
        btnNuevaCartera.setFont(CUERPO_MEDIO);
        btnNuevaCartera.setBackground(AZUL_CLARO);
        btnNuevaCartera.setForeground(Color.WHITE);
        btnNuevaCartera.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        btnNuevaCartera.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnNuevaCartera.setBorderPainted(false);
        btnNuevaCartera.setContentAreaFilled(false);
        btnNuevaCartera.setOpaque(true);
        btnNuevaCartera.setFocusPainted(false);
        btnNuevaCartera.addMouseListener(myAdapterAzul);
        btnNuevaCartera.addActionListener(e -> ventanaPrincipal.mostrarPanel("Portfolio"));
        panel.add(btnNuevaCartera);
        
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton btnNuevoCurso = new JButton("Explorar cursos");
        btnNuevoCurso.setFont(CUERPO_MEDIO);
        btnNuevoCurso.setBackground(AZUL_CLARO);
        btnNuevoCurso.setForeground(Color.WHITE);
        btnNuevoCurso.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        btnNuevoCurso.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnNuevoCurso.setBorderPainted(false);
        btnNuevoCurso.setContentAreaFilled(false);
        btnNuevoCurso.setOpaque(true);
        btnNuevoCurso.setFocusPainted(false);
        btnNuevoCurso.addMouseListener(myAdapterAzul);
        btnNuevoCurso.addActionListener(e -> ventanaPrincipal.mostrarPanel("Aprender"));
        panel.add(btnNuevoCurso);
        
        return panel;
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