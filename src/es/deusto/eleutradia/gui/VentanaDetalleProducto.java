package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import es.deusto.eleutradia.domain.PlazoRentabilidad;
import es.deusto.eleutradia.domain.ProductoFinanciero;

public class VentanaDetalleProducto extends JDialog {

    private static final long serialVersionUID = 1L;
    private ProductoFinanciero producto;
    
    // Colores y fuentes
    private static final Color COLOR_FONDO_PRINCIPAL = new Color(250, 250, 250);
    private static final Color COLOR_BORDE = new Color(220, 220, 230);
    private static final Color MY_AZUL_CLARO = new Color(0, 120, 255);
    private static final Color MY_AZUL_OSCURO = new Color(10, 60, 170);
    private static final Color MY_GRIS_CLARO = new Color(120, 120, 120);
    private static final Color MY_GRIS_OSCURO = new Color(70, 70, 70);
    private static final Color MY_VERDE_OSCURO = new Color(0, 130, 40);
    private static final Color MY_ROJO_CLARO = new Color(220, 50, 50);
    private static final Font FONT_TITULO = new Font("Segoe UI", Font.BOLD, 20);
    private static final Font FONT_SUBTITULO = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONT_NORMAL = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_PEQUENO = new Font("Segoe UI", Font.PLAIN, 11);
    private static final Font FONT_NUMERO_GRANDE = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font FONT_NUMERO_MEDIO = new Font("Segoe UI", Font.BOLD, 14);

    public VentanaDetalleProducto(JFrame padre, ProductoFinanciero producto, boolean modal) {
        super(padre, "Detalles del producto", modal);
        this.producto = producto;
        this.setSize(700, 700);
        this.setLocationRelativeTo(padre);
        this.setBackground(COLOR_FONDO_PRINCIPAL);
        this.setLayout(new BorderLayout(0, 0));
        this.setResizable(false);
        this.construirVentana();
        this.setVisible(true);
    }

    private void construirVentana() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(COLOR_FONDO_PRINCIPAL);
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        panelPrincipal.add(construirPanelProducto(), BorderLayout.NORTH);
        panelPrincipal.add(construirPanelDetalles(), BorderLayout.CENTER);
        panelPrincipal.add(construirPanelInferior(), BorderLayout.SOUTH);
        
        this.add(panelPrincipal);
    }
    
    private JPanel construirPanelProducto() {
        JPanel panel = new JPanel(new BorderLayout(15, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDE, 1), 
            new EmptyBorder(15, 20, 15, 20)));
        
        // Panel Logo
        JPanel panelLogo = new JPanel(new BorderLayout());
        panelLogo.setBackground(Color.WHITE);
        panelLogo.setPreferredSize(new Dimension(100, 80));
        
        String rutaLogoGestora = null;
        if (producto.getGestora() != null) {
            rutaLogoGestora = "/images/gestora" + producto.getGestora().getNombreComercial().toLowerCase() + ".png";
        }
        ImageIcon iconoGestora = cargarIconoEscalado(rutaLogoGestora, 80, 60);
        if (iconoGestora != null) {
            JLabel labelConLogo = new JLabel(iconoGestora);
            labelConLogo.setHorizontalAlignment(JLabel.CENTER);
            labelConLogo.setToolTipText(producto.getGestora().getNombreCompleto());
            panelLogo.add(labelConLogo, BorderLayout.CENTER);
        } else {
            JLabel labelSinLogo = new JLabel("📊");
            labelSinLogo.setFont(new Font("Segoe UI", Font.PLAIN, 40));
            labelSinLogo.setHorizontalAlignment(JLabel.CENTER);
            labelSinLogo.setForeground(MY_GRIS_CLARO);
            panelLogo.add(labelSinLogo, BorderLayout.CENTER);
        }
        
        // Panel Info
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBackground(Color.WHITE);
        
        JLabel labelNombre = new JLabel(producto.getNombre());
        labelNombre.setFont(FONT_TITULO);
        labelNombre.setForeground(MY_AZUL_CLARO);
        labelNombre.setAlignmentX(LEFT_ALIGNMENT);
        
        JLabel labelTipo = new JLabel(producto.getTipoProducto().getNombre() + 
                " • " + producto.getRegionGeografica().getNombre());
        labelTipo.setFont(FONT_NORMAL);
        labelTipo.setForeground(MY_GRIS_CLARO);
        labelTipo.setAlignmentX(LEFT_ALIGNMENT);
        
        JPanel panelPrecio = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelPrecio.setBackground(Color.WHITE);
        panelPrecio.setAlignmentX(LEFT_ALIGNMENT);
        
        JLabel labelPrecio = new JLabel(String.format("%.2f", producto.getValorUnitario()));
        labelPrecio.setFont(FONT_NUMERO_GRANDE);
        labelPrecio.setForeground(MY_AZUL_OSCURO);
        JLabel labelDivisa = new JLabel(" " + producto.getDivisa().getSimbolo() + "/acción");
        labelDivisa.setFont(FONT_NORMAL);
        labelDivisa.setForeground(MY_GRIS_CLARO);
        panelPrecio.add(labelPrecio);
        panelPrecio.add(labelDivisa);
        
        panelInfo.add(labelNombre);
        panelInfo.add(Box.createVerticalStrut(5));
        panelInfo.add(labelTipo);
        panelInfo.add(Box.createVerticalStrut(8));
        panelInfo.add(panelPrecio);
        
        // Panel Riesgo
        JPanel panelRiesgo = new JPanel();
        panelRiesgo.setLayout(new BoxLayout(panelRiesgo, BoxLayout.Y_AXIS));
        panelRiesgo.setBackground(Color.WHITE);
        panelRiesgo.setPreferredSize(new Dimension(100, 80));
        
        JLabel labelRiesgoTitulo = new JLabel("Nivel de riesgo");
        labelRiesgoTitulo.setFont(FONT_PEQUENO);
        labelRiesgoTitulo.setForeground(MY_GRIS_CLARO);
        labelRiesgoTitulo.setAlignmentX(CENTER_ALIGNMENT);
        
        JLabel labelRiesgoValor = new JLabel(producto.getTipoProducto().getStringRiesgo());
        labelRiesgoValor.setFont(FONT_SUBTITULO);
        labelRiesgoValor.setForeground(obtenerColorRiesgo(producto.getTipoProducto().getRiesgo()));
        labelRiesgoValor.setAlignmentX(CENTER_ALIGNMENT);
        
        panelRiesgo.add(Box.createVerticalGlue());
        panelRiesgo.add(labelRiesgoTitulo);
        panelRiesgo.add(Box.createVerticalStrut(5));
        panelRiesgo.add(labelRiesgoValor);
        panelRiesgo.add(Box.createVerticalGlue());
        
        panel.add(panelLogo, BorderLayout.WEST);
        panel.add(panelInfo, BorderLayout.CENTER);
        panel.add(panelRiesgo, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel construirPanelDetalles() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_FONDO_PRINCIPAL);
        
        // Sección: Información General
        panel.add(construirSeccionInformacion());
        panel.add(Box.createVerticalStrut(10));
        
        // Sección: Rentabilidades Históricas
        panel.add(construirSeccionRentabilidades());
        
        return panel;
    }
    
    private JPanel construirSeccionInformacion() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDE, 1), 
            new EmptyBorder(15, 20, 15, 20)));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        
        JLabel tituloSeccion = new JLabel("Información General");
        tituloSeccion.setFont(FONT_SUBTITULO);
        tituloSeccion.setForeground(MY_AZUL_OSCURO);
        tituloSeccion.setAlignmentX(LEFT_ALIGNMENT);
        panel.add(tituloSeccion);
        panel.add(Box.createVerticalStrut(10));
        
        JSeparator separador = new JSeparator(JSeparator.HORIZONTAL);
        separador.setAlignmentX(LEFT_ALIGNMENT);
        panel.add(separador);
        panel.add(Box.createVerticalStrut(15));
        
        JPanel panelGrid = new JPanel(new GridBagLayout());
        panelGrid.setBackground(Color.WHITE);
        panelGrid.setAlignmentX(LEFT_ALIGNMENT);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Tipo de producto
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.4;
        panelGrid.add(crearLabelInfo("Tipo de producto:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.6;
        panelGrid.add(crearLabelValor(producto.getTipoProducto().getNombre()), gbc);
        
        // Región geográfica
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.4;
        panelGrid.add(crearLabelInfo("Región geográfica:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.6;
        panelGrid.add(crearLabelValor(producto.getRegionGeografica().getNombre()), gbc);
        
        // Gestora
        if (producto.getGestora() != null) {
            gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.4;
            panelGrid.add(crearLabelInfo("Gestora:"), gbc);
            gbc.gridx = 1; gbc.weightx = 0.6;
            panelGrid.add(crearLabelValor(producto.getGestora().getNombreCompleto()), gbc);
        }
        
        // Importe mínimo
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.4;
        panelGrid.add(crearLabelInfo("Importe mínimo:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.6;
        String importeMin = String.format("%.2f %s", 
            producto.getTipoProducto().getImporteMin(), 
            producto.getDivisa().getSimbolo());
        panelGrid.add(crearLabelValor(importeMin), gbc);
        
        // Divisa
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.4;
        panelGrid.add(crearLabelInfo("Divisa:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.6;
        panelGrid.add(crearLabelValor(producto.getDivisa().getNombre() + 
            " (" + producto.getDivisa().getSimbolo() + ")"), gbc);
        
        panel.add(panelGrid);
        return panel;
    }
    
    private JPanel construirSeccionRentabilidades() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDE, 1), 
            new EmptyBorder(15, 20, 15, 20)));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        
        JLabel tituloSeccion = new JLabel("Rentabilidades Históricas");
        tituloSeccion.setFont(FONT_SUBTITULO);
        tituloSeccion.setForeground(MY_AZUL_OSCURO);
        tituloSeccion.setAlignmentX(LEFT_ALIGNMENT);
        panel.add(tituloSeccion);
        panel.add(Box.createVerticalStrut(10));
        
        JSeparator separador = new JSeparator(JSeparator.HORIZONTAL);
        separador.setAlignmentX(LEFT_ALIGNMENT);
        panel.add(separador);
        panel.add(Box.createVerticalStrut(15));
        
        JPanel panelGrid = new JPanel(new GridBagLayout());
        panelGrid.setBackground(Color.WHITE);
        panelGrid.setAlignmentX(LEFT_ALIGNMENT);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int fila = 0;
        for (Map.Entry<PlazoRentabilidad, BigDecimal> entry : producto.getRentabilidades().entrySet()) {
            String plazo = entry.getKey().getDefinicion();
            BigDecimal valor = entry.getValue();
            
            gbc.gridx = 0; gbc.gridy = fila; gbc.weightx = 0.4;
            panelGrid.add(crearLabelInfo(plazo + ":"), gbc);
            
            gbc.gridx = 1; gbc.weightx = 0.6;
            if (valor != null) {
                String textoValor = String.format("%.2f%%", valor);
                JLabel labelValor = new JLabel(textoValor);
                labelValor.setFont(FONT_NUMERO_MEDIO);
                
                if (valor.compareTo(BigDecimal.ZERO) > 0) {
                    labelValor.setForeground(MY_VERDE_OSCURO);
                    labelValor.setText("▲ " + textoValor);
                } else if (valor.compareTo(BigDecimal.ZERO) < 0) {
                    labelValor.setForeground(MY_ROJO_CLARO);
                    labelValor.setText("▼ " + textoValor);
                } else {
                    labelValor.setForeground(MY_GRIS_OSCURO);
                    labelValor.setText("― " + textoValor);
                }
                panelGrid.add(labelValor, gbc);
            } else {
                panelGrid.add(crearLabelValor("No disponible"), gbc);
            }
            
            fila++;
        }
        
        panel.add(panelGrid);
        return panel;
    }
    
    private JPanel construirPanelInferior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panel.setBackground(COLOR_FONDO_PRINCIPAL);
        
        JButton botonCerrar = new JButton("Cerrar");
        botonCerrar.setFont(FONT_NORMAL);
        botonCerrar.setBackground(MY_GRIS_CLARO);
        botonCerrar.setForeground(Color.WHITE);
        botonCerrar.setPreferredSize(new Dimension(110, 35));
        botonCerrar.setBorderPainted(false);
        botonCerrar.setContentAreaFilled(false);
        botonCerrar.setOpaque(true);
        botonCerrar.setFocusPainted(false);
        botonCerrar.setToolTipText("Cerrar esta ventana");
        botonCerrar.addActionListener(e -> dispose());
        botonCerrar.addMouseListener(myAdapterGris);
        
        panel.add(botonCerrar);
        return panel;
    }
    
    private JLabel crearLabelInfo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(FONT_NORMAL);
        label.setForeground(MY_GRIS_OSCURO);
        return label;
    }
    
    private JLabel crearLabelValor(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(FONT_NORMAL);
        label.setForeground(MY_AZUL_OSCURO);
        return label;
    }
    
    private ImageIcon cargarIconoEscalado(String ruta, int anchoMax, int altoMax) {
        if (ruta == null || getClass().getResource(ruta) == null) return null;
        ImageIcon icono = new ImageIcon(getClass().getResource(ruta));
        Image img = icono.getImage();
        int anchoOriginal = img.getWidth(null);
        int altoOriginal = img.getHeight(null);
        double ratio = Math.min((double)anchoMax/anchoOriginal, (double)altoMax/altoOriginal);
        int anchoNuevo = (int)(anchoOriginal * ratio);
        int altoNuevo = (int)(altoOriginal * ratio);
        return new ImageIcon(img.getScaledInstance(anchoNuevo, altoNuevo, Image.SCALE_SMOOTH));
    }
    
    //IAG (Claude)
  	//SIN MODIFICAR: Devuelve un color para cada nivel de riesgo (1-7)
  	private Color obtenerColorRiesgo(int riesgo) {
          switch (riesgo) {
              case 1: return new Color(0, 160, 60);    // Verde intenso
              case 2: return new Color(80, 190, 80);   // Verde claro
              case 3: return new Color(190, 200, 70);  // Amarillo verdoso
              case 4: return new Color(255, 200, 0);   // Amarillo
              case 5: return new Color(255, 150, 0);   // Naranja
              case 6: return new Color(255, 90, 0);    // Naranja oscuro
              case 7: return new Color(200, 40, 40);   // Rojo
              default: return MY_GRIS_CLARO;
          }
      }
  	//END IAG
    
    MouseAdapter myAdapterGris = new MouseAdapter() {
    	@Override
		public void mouseEntered(MouseEvent e) {e.getComponent().setBackground(MY_GRIS_OSCURO);}
		@Override
		public void mouseExited(MouseEvent e) {e.getComponent().setBackground(MY_GRIS_CLARO);}
		@Override
		public void mousePressed(MouseEvent e) {e.getComponent().setBackground(MY_GRIS_OSCURO);}
		@Override
		public void mouseReleased(MouseEvent e) {e.getComponent().setBackground(MY_GRIS_CLARO);}
    };
}
