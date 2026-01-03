package es.deusto.eleutradia.gui.style;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.DefaultListCellRenderer;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class UITema {
	
	private static UITema instancia;
	private boolean temaOscuro;
	
	// Colores del tema claro
	public static final Color MAIN_FONDO_CLARO = new Color(250, 250, 250);
	public static final Color MAIN_PANEL_CLARO = Color.WHITE;
	public static final Color MAIN_BORDE_CLARO = new Color(220, 220, 230);
	
	// Colores del tema oscuro
    public static final Color MAIN_FONDO_OSCURO = new Color(40, 40, 40);
    public static final Color MAIN_PANEL_OSCURO = new Color(55, 55, 55);
    public static final Color MAIN_BORDE_OSCURO = new Color(90, 90, 90);
	
    // Colores según el tema actual
    public Color colorTexto;
    public Color colorFondo;
    public Color colorPanel;
    public Color colorBorde;
    
    // Colores generales
	public static final Color GRIS_SCROLLBAR = new Color(180, 180, 180);
	public static final Color GRIS_SUAVE = new Color(220, 220, 220);
	public static final Color GRIS_CLARO = new Color(170, 170, 170);
	public static final Color GRIS_MEDIO = new Color(120, 120, 120);
	public static final Color GRIS_OSCURO = new Color(70, 70, 70);
	public static final Color AZUL_CLARO = new Color(0, 120, 255);
	public static final Color AZUL_OSCURO = new Color(10, 60, 170);
	public static final Color VERDE_CLARO = new Color(40, 170, 70);
	public static final Color VERDE_OSCURO = new Color(25, 120, 50);
	public static final Color ROSA_CLARO = new Color(220, 90, 130);
	public static final Color ROSA_OSCURO = new Color(180, 50, 100);
	public static final Color ROJO_CLARO = new Color(220, 50, 50);
	public static final Color NARANJA_CLARO = new Color(255, 140, 0);
    
	// Fuentes generales
	public static final Font TITULO_GRANDE = new Font("Segoe UI", Font.BOLD, 20);
	public static final Font TITULO_MEDIO = new Font("Segoe UI", Font.BOLD, 18);
	public static final Font SUBTITULO_GRANDE = new Font("Segoe UI", Font.BOLD, 16);
	public static final Font SUBTITULO_MEDIO = new Font("Segoe UI", Font.BOLD, 14);
	public static final Font SUBTITULO_PEQUENO = new Font("Segoe UI", Font.BOLD, 12);
	public static final Font CUERPO_GRANDE = new Font("Segoe UI", Font.PLAIN, 14);
	public static final Font CUERPO_MEDIO = new Font("Segoe UI", Font.PLAIN, 13);
	public static final Font CUERPO_PEQUENO = new Font("Segoe UI", Font.PLAIN, 12);
	public static final Font CUERPO_CURSIVA = new Font("Segoe UI", Font.ITALIC, 14);

	// Mouse Adapters
    public static MouseAdapter myAdapterAzul = new MouseAdapter() {
    	@Override
		public void mouseEntered(MouseEvent e) {e.getComponent().setBackground(AZUL_OSCURO);}
		@Override
		public void mouseExited(MouseEvent e) {e.getComponent().setBackground(AZUL_CLARO);}
		@Override
		public void mousePressed(MouseEvent e) {e.getComponent().setBackground(AZUL_OSCURO);}
		@Override
		public void mouseReleased(MouseEvent e) {e.getComponent().setBackground(AZUL_CLARO);}
    };
    
    public static MouseAdapter myAdapterGris = new MouseAdapter() {
    	@Override
		public void mouseEntered(MouseEvent e) {e.getComponent().setBackground(GRIS_OSCURO);}
		@Override
		public void mouseExited(MouseEvent e) {e.getComponent().setBackground(GRIS_MEDIO);}
		@Override
		public void mousePressed(MouseEvent e) {e.getComponent().setBackground(GRIS_OSCURO);}
		@Override
		public void mouseReleased(MouseEvent e) {e.getComponent().setBackground(GRIS_MEDIO);}
    };
    
    public static MouseAdapter myAdapterVerde = new MouseAdapter() {
    	@Override
		public void mouseEntered(MouseEvent e) {e.getComponent().setBackground(VERDE_OSCURO);}
		@Override
		public void mouseExited(MouseEvent e) {e.getComponent().setBackground(VERDE_CLARO);}
		@Override
		public void mousePressed(MouseEvent e) {e.getComponent().setBackground(VERDE_OSCURO);}
		@Override
		public void mouseReleased(MouseEvent e) {e.getComponent().setBackground(VERDE_CLARO);}
    };
    
    public static MouseAdapter myAdapterRosa = new MouseAdapter() {
    	@Override
		public void mouseEntered(MouseEvent e) {e.getComponent().setBackground(ROSA_OSCURO);}
		@Override
		public void mouseExited(MouseEvent e) {e.getComponent().setBackground(ROSA_CLARO);}
		@Override
		public void mousePressed(MouseEvent e) {e.getComponent().setBackground(ROSA_OSCURO);}
		@Override
		public void mouseReleased(MouseEvent e) {e.getComponent().setBackground(ROSA_CLARO);}
    };
    
    public static MouseAdapter myAdapterRegistro = new MouseAdapter() {
    	@Override
		public void mouseEntered(MouseEvent e) {e.getComponent().setForeground(AZUL_OSCURO);}
		@Override
		public void mouseExited(MouseEvent e) {e.getComponent().setForeground(AZUL_CLARO);}
		@Override
		public void mousePressed(MouseEvent e) {e.getComponent().setForeground(AZUL_OSCURO);}
		@Override
		public void mouseReleased(MouseEvent e) {e.getComponent().setForeground(AZUL_CLARO);}
    };
    
    private UITema() {
    	this.temaOscuro = false;
    	actualizarColores();
    }
    
    public static UITema getInstancia() {
    	if (instancia == null) {
    		instancia = new UITema();
    	}
    	return instancia;
    }
    
    // Métodos para gestionar el tema
    public boolean esTemaOscuro() {
    	return this.temaOscuro;
    }
    
	public void cambiarTema() {
		this.temaOscuro = !this.temaOscuro;
		actualizarColores();
	}
	
	public void establecerTemaOscuro(boolean oscuro) {
    	if (this.temaOscuro != oscuro) {
    		this.temaOscuro = oscuro;
    		actualizarColores();
    	}
    }
	
	// Método para actualizar los colores según el tema actual
	private void actualizarColores() {
    	if (temaOscuro) {
    		colorTexto = MAIN_FONDO_CLARO;
    		colorFondo = MAIN_FONDO_OSCURO;
    		colorPanel = MAIN_PANEL_OSCURO;
    		colorBorde = MAIN_BORDE_OSCURO;
    	} else {
    		colorTexto = MAIN_FONDO_OSCURO;
    		colorFondo = MAIN_FONDO_CLARO;
    		colorPanel = MAIN_PANEL_CLARO;
    		colorBorde = MAIN_BORDE_CLARO;
    	}
    	actualizarTooltips();
    }
	
    // Método para crear ToolTips personalizados
    private void actualizarTooltips() {
    	UIManager.put("ToolTip.background", temaOscuro ? MAIN_PANEL_OSCURO : Color.BLACK);
    	UIManager.put("ToolTip.foreground", temaOscuro ? MAIN_FONDO_CLARO : Color.WHITE);
    	UIManager.put("ToolTip.border", BorderFactory.createLineBorder(
    		temaOscuro ? MAIN_BORDE_OSCURO : Color.DARK_GRAY, 1));
    	UIManager.put("ToolTip.font", CUERPO_PEQUENO);
    }
    
    //IAG (Claude)
    //ADAPTADO: Diseño adaptado al tema oscuro/claro
    
    // Método para crear ScrollBars personalizadas
    public static BasicScrollBarUI crearScrollBarUI() {
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
    }

    // Método para aplicar el método anterior a un JComboBox
    public static void aplicarScrollBarCombo(JComboBox<?> combo) {
        combo.setUI(new BasicComboBoxUI() {

            @Override
            protected BasicComboPopup createPopup() {
                BasicComboPopup popup = (BasicComboPopup) super.createPopup();
                JScrollPane scrollPane = (JScrollPane) popup.getComponent(0);
                scrollPane.getVerticalScrollBar().setUI(crearScrollBarUI());
                return popup;
            }
        });
    }
    //END IAG
    
    // Método para aplicar ToolTips personalizados a los items de un JComboBox
    public static void aplicarTooltipPorItem(JComboBox<?> combo) {
        combo.setRenderer(new DefaultListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {

                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);

                label.setToolTipText(value != null ? value.toString() : null);
                return label;
            }
        });
    }
    
    // Método para cargar y escalar iconos
	public static ImageIcon cargarIconoEscalado(String ruta, int anchoMax, int altoMax) {
        if (ruta == null || UITema.class.getResource(ruta) == null) return null;
        ImageIcon icono = new ImageIcon(UITema.class.getResource(ruta));
        Image img = icono.getImage();
        int anchoOriginal = img.getWidth(null);
        int altoOriginal = img.getHeight(null);
        double ratio = Math.min((double)anchoMax/anchoOriginal, (double)altoMax/altoOriginal);
        int anchoNuevo = (int)(anchoOriginal * ratio);
        int altoNuevo = (int)(altoOriginal * ratio);
        return new ImageIcon(img.getScaledInstance(anchoNuevo, altoNuevo, Image.SCALE_SMOOTH));
    }
}
