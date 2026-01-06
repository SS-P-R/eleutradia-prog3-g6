package es.deusto.eleutradia.gui.style;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
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
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.DefaultListCellRenderer;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import es.deusto.eleutradia.domain.ProductoFinanciero;

public class UITema {
	
	private static UITema instancia;
	
	// Colores del tema
	public static final Color MAIN_FONDO = new Color(250, 250, 250);
	public static final Color MAIN_PANEL = Color.WHITE;
	public static final Color MAIN_BORDE = new Color(220, 220, 230);
    
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
	public static final Color NARANJA_OSCURO = new Color(200, 100, 0);
    
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
    
    public static MouseAdapter myAdapterNaranja = new MouseAdapter() {
    	@Override
		public void mouseEntered(MouseEvent e) {e.getComponent().setBackground(NARANJA_OSCURO);}
		@Override
		public void mouseExited(MouseEvent e) {e.getComponent().setBackground(NARANJA_CLARO);}
		@Override
		public void mousePressed(MouseEvent e) {e.getComponent().setBackground(NARANJA_OSCURO);}
		@Override
		public void mouseReleased(MouseEvent e) {e.getComponent().setBackground(NARANJA_CLARO);}
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
    	personalizarTooltips();
    	personalizarDialogs();
    }
    
    public static UITema getInstancia() {
    	if (instancia == null) {
    		instancia = new UITema();
    	}
    	return instancia;
    }
    
    public static void personalizarRadioButton(JRadioButton radio) {
        radio.setFont(CUERPO_MEDIO);
        radio.setBackground(Color.WHITE);
        radio.setForeground(GRIS_OSCURO);
        radio.setFocusPainted(false);
        radio.setBorderPainted(false);
        radio.setOpaque(false);
        radio.setCursor(new Cursor(Cursor.HAND_CURSOR));

        radio.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!radio.isSelected()) {
                    radio.setForeground(AZUL_CLARO);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                radio.setForeground(radio.isSelected() ? AZUL_OSCURO : GRIS_OSCURO);
            }
        });

        radio.addActionListener(e -> {
            radio.setForeground(AZUL_OSCURO);
        });
    }

	
    // Método para crear ToolTips personalizados
    private void personalizarTooltips() {
    	UIManager.put("ToolTip.background", Color.BLACK);
    	UIManager.put("ToolTip.foreground", Color.WHITE);
    	UIManager.put("ToolTip.border", BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
    	UIManager.put("ToolTip.font", CUERPO_PEQUENO);
    }
    
    // Método para personalizar los diálogos JOptionPane
    private static void personalizarDialogs() {                
        UIManager.put("OptionPane.background", MAIN_FONDO);
        UIManager.put("OptionPane.messageForeground", Color.BLACK);
        UIManager.put("Panel.background", MAIN_FONDO);
        UIManager.put("Button.background", GRIS_MEDIO);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.select", GRIS_OSCURO);
        UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
        UIManager.put("Button.border", BorderFactory.createEmptyBorder(5, 15, 5, 15));
        UIManager.put("OptionPane.messageFont", CUERPO_GRANDE);
        UIManager.put("OptionPane.buttonFont", SUBTITULO_PEQUENO);
    }
    
    /**
     * Muestra un diálogo de información personalizado
     */
    public static void mostrarInfo(Component parent, String mensaje, String titulo) {
        JOptionPane.showMessageDialog(parent, mensaje, titulo, 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Muestra un diálogo de advertencia personalizado
     */
    public static void mostrarWarning(Component parent, String mensaje, String titulo) {
        JOptionPane.showMessageDialog(parent, mensaje, titulo, 
            JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Muestra un diálogo de error personalizado
     */
    public static void mostrarError(Component parent, String mensaje, String titulo) {
        JOptionPane.showMessageDialog(parent, mensaje, titulo, 
            JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Muestra un diálogo de confirmación personalizado
     * @return true si el usuario acepta, false si cancela
     */
    public static boolean mostrarConfirmacion(Component parent, Object mensaje, String titulo) {
        int resultado = JOptionPane.showConfirmDialog(parent, mensaje, titulo,
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return resultado == JOptionPane.YES_OPTION;
    }
    
    /**
     * Muestra un diálogo de confirmación con opciones Aceptar/Cancelar
     * @return true si el usuario acepta, false si cancela
     */
    public static boolean mostrarConfirmacionOkCancel(Component parent, Object mensaje, String titulo) {
        int resultado = JOptionPane.showConfirmDialog(parent, mensaje, titulo,
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        return resultado == JOptionPane.OK_OPTION;
    }
    
    /**
     * Muestra un diálogo de entrada de texto personalizado
     * @return el texto ingresado o null si se cancela
     */
    public static String mostrarInputTexto(Component parent, Object mensaje, String titulo, String valorInicial) {
        return (String) JOptionPane.showInputDialog(parent, mensaje, titulo,
            JOptionPane.PLAIN_MESSAGE, null, null, valorInicial);
    }
    
    /**
     * Muestra un diálogo de selección personalizado
     * @return el elemento seleccionado o null si se cancela
     */
    public static Object mostrarInputSeleccion(Component parent, String mensaje, String titulo, 
            Object[] opciones, Object seleccionInicial) {
        return JOptionPane.showInputDialog(parent, mensaje, titulo,
            JOptionPane.PLAIN_MESSAGE, null, opciones, seleccionInicial);
    }
    
    //END IAG
    
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
                this.trackColor = MAIN_FONDO;
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

    // Método para personalizar JComboBoxes
    public static void personalizarComboBox(JComboBox<?> combo) {
        combo.setUI(new BasicComboBoxUI() {

            @Override
            protected JButton createArrowButton() {
                JButton arrowButton = new JButton();

                arrowButton.setBackground(GRIS_MEDIO);
                arrowButton.setBorder(BorderFactory.createEmptyBorder());
                arrowButton.setFocusPainted(false);
                arrowButton.setOpaque(true);
                arrowButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

                // Colores propios (independientes de Button.select)
                arrowButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        arrowButton.setBackground(GRIS_OSCURO);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        arrowButton.setBackground(GRIS_MEDIO);
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        arrowButton.setBackground(GRIS_OSCURO);
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        arrowButton.setBackground(GRIS_MEDIO);
                    }
                });

                return arrowButton;
            }

            @Override
            protected BasicComboPopup createPopup() {
                BasicComboPopup popup = (BasicComboPopup) super.createPopup();
                JScrollPane scroll = (JScrollPane) popup.getComponent(0);
                scroll.getVerticalScrollBar().setUI(personalizarScrollBarUI());
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
        if (isSelected) {
        	renderer.setBackground(new Color(200, 210, 240));
            renderer.setForeground(Color.BLACK);
        } else {
            // Comprobar si el mouse está sobre la fila
            Point mousePos = table.getMousePosition();
            if (mousePos != null && table.rowAtPoint(mousePos) == row) {
            	renderer.setBackground(new Color(220, 235, 255));
            } else {
                // Colores alternos
                if (row % 2 == 0) {
                    renderer.setBackground(MAIN_PANEL);
                } else {
                    renderer.setBackground(MAIN_FONDO);
                }
            }
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
                	setForeground(Color.BLACK);
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
	
	//IAG (ChatGPT)
	//SIN MODIFICAR: Método para forzar mayúsculas en campos de texto
	public static class UppercaseDocumentFilter extends DocumentFilter {

	    @Override
	    public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr)
	            throws BadLocationException {
	        if (text != null) {
	            text = text.toUpperCase();
	        }
	        super.insertString(fb, offset, text, attr);
	    }

	    @Override
	    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
	            throws BadLocationException {
	        if (text != null) {
	            text = text.toUpperCase();
	        }
	        super.replace(fb, offset, length, text, attrs);
	    }
	}
	//END IAG
}
