package es.deusto.eleutradia.gui.style;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.DefaultListCellRenderer;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;

import es.deusto.eleutradia.domain.ProductoFinanciero;

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
    	personalizarTooltips();
    }
	
    // Método para crear ToolTips personalizados
    private void personalizarTooltips() {
    	UIManager.put("ToolTip.background", temaOscuro ? MAIN_PANEL_OSCURO : Color.BLACK);
    	UIManager.put("ToolTip.foreground", temaOscuro ? MAIN_FONDO_CLARO : Color.WHITE);
    	UIManager.put("ToolTip.border", BorderFactory.createLineBorder(
    		temaOscuro ? MAIN_BORDE_OSCURO : Color.DARK_GRAY, 1));
    	UIManager.put("ToolTip.font", CUERPO_PEQUENO);
    }
    
    private static void personalizarDialogs() {
        UITema tema = UITema.getInstancia();
        
        Color fondoBoton = tema.esTemaOscuro() ? GRIS_SUAVE : Color.WHITE;
        
        UIManager.put("OptionPane.background", tema.esTemaOscuro() ? GRIS_OSCURO : AZUL_OSCURO);
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("Panel.background", tema.esTemaOscuro() ? GRIS_OSCURO : AZUL_OSCURO);
        UIManager.put("Button.background", fondoBoton);
        UIManager.put("Button.foreground", tema.esTemaOscuro() ? Color.BLACK : AZUL_OSCURO);
        UIManager.put("Button.select", fondoBoton.darker());
        UIManager.put("Button.hover", true);
        UIManager.put("Button.hoverBackground", fondoBoton.darker());
        UIManager.put("Button.focus", fondoBoton);
        UIManager.put("Button.focusPainted", false);
        UIManager.put("Button.border", BorderFactory.createEmptyBorder(5, 15, 5, 15));
        UIManager.put("OptionPane.messageFont", CUERPO_GRANDE);
        UIManager.put("OptionPane.buttonFont", SUBTITULO_MEDIO);
    }
    
    /**
     * Muestra un diálogo de información personalizado
     */
    public static void mostrarInfo(Component parent, String mensaje, String titulo) {
        personalizarDialogs();
        JOptionPane.showMessageDialog(parent, mensaje, titulo, 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Muestra un diálogo de advertencia personalizado
     */
    public static void mostrarAdvertencia(Component parent, String mensaje, String titulo) {
        personalizarDialogs();
        JOptionPane.showMessageDialog(parent, mensaje, titulo, 
            JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Muestra un diálogo de error personalizado
     */
    public static void mostrarError(Component parent, String mensaje, String titulo) {
        personalizarDialogs();
        JOptionPane.showMessageDialog(parent, mensaje, titulo, 
            JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Muestra un diálogo de confirmación personalizado
     * @return true si el usuario acepta, false si cancela
     */
    public static boolean mostrarConfirmacion(Component parent, String mensaje, String titulo) {
        personalizarDialogs();
        int resultado = JOptionPane.showConfirmDialog(parent, mensaje, titulo,
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return resultado == JOptionPane.YES_OPTION;
    }
    
    /**
     * Muestra un diálogo de confirmación con opciones Aceptar/Cancelar
     * @return true si el usuario acepta, false si cancela
     */
    public static boolean mostrarConfirmacionOkCancel(Component parent, String mensaje, String titulo) {
        personalizarDialogs();
        int resultado = JOptionPane.showConfirmDialog(parent, mensaje, titulo,
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        return resultado == JOptionPane.OK_OPTION;
    }
    
    /**
     * Muestra un diálogo de entrada de texto personalizado
     * @return el texto ingresado o null si se cancela
     */
    public static String mostrarInputTexto(Component parent, String mensaje, String titulo, String valorInicial) {
        personalizarDialogs();
        return (String) JOptionPane.showInputDialog(parent, mensaje, titulo,
            JOptionPane.PLAIN_MESSAGE, null, null, valorInicial);
    }
    
    /**
     * Muestra un diálogo de selección personalizado
     * @return el elemento seleccionado o null si se cancela
     */
    public static Object mostrarInputSeleccion(Component parent, String mensaje, String titulo, 
            Object[] opciones, Object seleccionInicial) {
        personalizarDialogs();
        return JOptionPane.showInputDialog(parent, mensaje, titulo,
            JOptionPane.PLAIN_MESSAGE, null, opciones, seleccionInicial);
    }
    
    //IAG (Claude)
    //ADAPTADO: Diseño adaptado al tema oscuro/claro
    
    // Método para crear ScrollBars personalizadas
    public static BasicScrollBarUI personalizarScrollBarUI() {
        return new BasicScrollBarUI() {

            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = GRIS_SCROLLBAR;
                this.thumbDarkShadowColor = GRIS_SCROLLBAR;
                this.thumbHighlightColor = GRIS_SCROLLBAR;
                this.trackColor = MAIN_FONDO_CLARO;
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
                scrollPane.getVerticalScrollBar().setUI(personalizarScrollBarUI());
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
    
    // Método para aplicar colores de fila personalizados a una JTable
    public static void aplicarColoresHover(DefaultTableCellRenderer renderer, JTable table, 
            boolean isSelected, int row) {
        
        UITema tema = UITema.getInstancia();
        
        if (isSelected) {
            if (tema.esTemaOscuro()) {
                renderer.setBackground(new Color(70, 80, 100));
                renderer.setForeground(MAIN_FONDO_CLARO);
            } else {
                renderer.setBackground(new Color(200, 210, 240));
                renderer.setForeground(Color.BLACK);
            }
        } else {
            // Comprobar si el mouse está sobre la fila
            Point mousePos = table.getMousePosition();
            if (mousePos != null && table.rowAtPoint(mousePos) == row) {
                // Color hover según el tema
                if (tema.esTemaOscuro()) {
                    renderer.setBackground(new Color(65, 65, 75));
                } else {
                    renderer.setBackground(new Color(220, 235, 255));
                }
            } else {
                // Colores alternos según el tema
                if (row % 2 == 0) {
                    renderer.setBackground(tema.colorPanel);
                } else {
                    renderer.setBackground(tema.colorFondo);
                }
            }
            
            // Color del texto según el tema (solo si no está seleccionado)
            renderer.setForeground(tema.colorTexto);
        }
    }
    
    public static class RendererHover extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            aplicarColoresHover(this, table, isSelected, row);
            setHorizontalAlignment(JLabel.LEFT);
            return this;
        }
    }
    
    public static class RightRendererHover extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            aplicarColoresHover(this, table, isSelected, row);
            setHorizontalAlignment(JLabel.RIGHT);
            return this;
        }
    }
    
    public static class CenterRendererHover extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            aplicarColoresHover(this, table, isSelected, row);
            setHorizontalAlignment(JLabel.CENTER);
            return this;
        }
    }
    
    public static class RendererImagen extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
        public void setValue(Object value) {
            if (value instanceof ImageIcon) {
                setIcon((ImageIcon) value);
                setText("");
            } else {
                setIcon(null);
                setText(value != null ? value.toString() : "");
            }
            setHorizontalAlignment(JLabel.CENTER);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            aplicarColoresHover(this, table, isSelected, row);
            return this;
        }
    }
    
    public static class RendererRentabilidad extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            aplicarColoresHover(this, table, isSelected, row);
            
            if (!isSelected && value != null && !value.toString().isEmpty()) {
                String strValue = value.toString();
                
                try {
                    strValue = strValue.replace("%", "").replace("€", "").replace("$", "")
                                       .replace("USD", "").replace("EUR", "").trim();
                    double numValue = Double.parseDouble(strValue);
                    
                    if (numValue > 0) {
                        setForeground(VERDE_OSCURO);
                    } else if (numValue < 0) {
                        setForeground(ROJO_CLARO);
                    } else {
                        setForeground(Color.BLACK);
                    }
                } catch (NumberFormatException e) {
                    // Mantener el color del tema si no se puede parsear
                	setForeground(UITema.getInstancia().colorTexto);
                }
                setFont(SUBTITULO_MEDIO);
            }
            
            setHorizontalAlignment(JLabel.CENTER);
            return this;
        }
    }
    
    public static class RendererLogoGestora extends RendererImagen {
		private static final long serialVersionUID = 1L;
		
		private List<ProductoFinanciero> productos;
        
        public RendererLogoGestora(List<ProductoFinanciero> productos) {
            this.productos = productos;
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if (productos != null && row < productos.size()) {
                ProductoFinanciero producto = productos.get(row);
                if (producto.getGestora() != null) {
                    setToolTipText(producto.getGestora().getNombreCompleto());
                }
            }
            
            return this;
        }
    }
    
    public static class RendererLogoDivisa extends RendererImagen {
		private static final long serialVersionUID = 1L;
		
		private List<ProductoFinanciero> productos;
        
        public RendererLogoDivisa(List<ProductoFinanciero> productos) {
            this.productos = productos;
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if (productos != null && row < productos.size()) {
                ProductoFinanciero producto = productos.get(row);
                if (producto.getDivisa() != null) {
                    setToolTipText(producto.getDivisa().getNombre());
                }
            }
            
            return this;
        }
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
