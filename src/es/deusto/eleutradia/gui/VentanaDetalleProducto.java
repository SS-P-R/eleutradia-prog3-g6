package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import es.deusto.eleutradia.domain.PlazoRentabilidad;
import es.deusto.eleutradia.domain.ProductoFinanciero;

public class VentanaDetalleProducto extends JDialog {

    private static final long serialVersionUID = 1L;
    
    // Colores y fuentes
    private static final Color COLOR_FONDO_PRINCIPAL = new Color(250, 250, 250);
    private static final Color MY_AZUL_CLARO = new Color(0, 120, 255);
    private static final Color MY_GRIS_CLARO = new Color(120, 120, 120);
    private static final Color MY_GRIS_OSCURO = new Color(70, 70, 70);
    private static final Font FONT_TITULO = new Font("Segoe UI", Font.BOLD, 26);
    private static final Font FONT_SUBTITULO = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONT_NORMAL = new Font("Segoe UI", Font.PLAIN, 12);

    public VentanaDetalleProducto(JFrame padre, ProductoFinanciero producto, boolean modal) {
        super(padre, "Detalles del producto", modal);
        this.setSize(650, 400);
        this.setLocationRelativeTo(padre);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(COLOR_FONDO_PRINCIPAL);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(COLOR_FONDO_PRINCIPAL);

        // Panel superior: nombre + logo
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(COLOR_FONDO_PRINCIPAL);

        JLabel labelNombre = new JLabel(producto.getNombre());
        labelNombre.setFont(FONT_TITULO);
        labelNombre.setForeground(MY_AZUL_CLARO);
        panelSuperior.add(labelNombre, BorderLayout.WEST);

        if (producto.getGestora() != null) {
            String rutaImagen = "/imagenes/gestora" + producto.getGestora().getNombreComercial().toLowerCase() + ".png";
            ImageIcon iconoGestora = null;
            if (getClass().getResource(rutaImagen) != null) {
                iconoGestora = new ImageIcon(getClass().getResource(rutaImagen));
                Image imagen = iconoGestora.getImage();
                int altoDeseado = 40;
                int anchoDeseado = (int) ((double) altoDeseado / imagen.getHeight(null) * imagen.getWidth(null));
                Image imagenEscalada = imagen.getScaledInstance(anchoDeseado, altoDeseado, Image.SCALE_SMOOTH);
                iconoGestora = new ImageIcon(imagenEscalada);
                JLabel labelLogo = new JLabel(iconoGestora);
                panelSuperior.add(labelLogo, BorderLayout.EAST);
            }
        }

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

        String[] columnas = {"Plazo / Info", "Rentabilidad / Detalle"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tablaInfo = new JTable(modeloTabla);
        tablaInfo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createEmptyBorder(5, 10, 10, 5)
            ));
        tablaInfo.setFont(FONT_NORMAL);
        tablaInfo.setRowHeight(25);

        // Renderizado centrado y en negrita
        DefaultTableCellRenderer rendererNegritaYCentro = new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setFont(FONT_NORMAL.deriveFont(Font.BOLD));
                setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        };
        tablaInfo.getColumnModel().getColumn(0).setCellRenderer(rendererNegritaYCentro);
        tablaInfo.getColumnModel().getColumn(1).setCellRenderer(rendererNegritaYCentro);

        // Agregar filas de información general
        modeloTabla.addRow(new Object[]{"Tipo de producto:", producto.getTipoProducto().getNombre()});
        modeloTabla.addRow(new Object[]{"Riesgo:", producto.getTipoProducto().getStringRiesgo()});
        modeloTabla.addRow(new Object[]{"Importe mínimo:", producto.getTipoProducto().getImporteMin() + " " + producto.getDivisa().getSimbolo()});
        modeloTabla.addRow(new Object[]{"", ""});
        modeloTabla.addRow(new Object[]{"- Periodo -", "- Rentabilidad histórica -"});

        // Agregar rentabilidades
        for (Map.Entry<PlazoRentabilidad, BigDecimal> entry : producto.getRentabilidades().entrySet()) {
            String plazo = entry.getKey().getDefinicion().toUpperCase();
            BigDecimal valor = entry.getValue();
            JLabel texto = new JLabel((valor != null) ? String.format("%.2f%%", valor) : "---");
            if ((valor.compareTo(BigDecimal.ZERO) == 1)) {texto.setForeground(Color.GREEN);}
            else if ((valor.compareTo(BigDecimal.ZERO) == 0)) {texto.setForeground(MY_GRIS_OSCURO);}
            else {texto.setForeground(Color.RED);}
            modeloTabla.addRow(new Object[]{plazo, texto.getText()});
        }
        
        panelPrincipal.add(tablaInfo, BorderLayout.CENTER);
        
        // Panel inferior con botón cerrar
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.setBackground(COLOR_FONDO_PRINCIPAL);
        JButton botonCerrar = new JButton("Cerrar");
        botonCerrar.setBackground(MY_GRIS_CLARO);
        botonCerrar.setForeground(Color.WHITE);
        botonCerrar.setBorderPainted(false);
        botonCerrar.setContentAreaFilled(false);
        botonCerrar.setOpaque(true);
        botonCerrar.setFocusPainted(false);
        botonCerrar.addActionListener(e -> dispose());
        botonCerrar.addMouseListener(myAdapterGris);
        botonCerrar.setPreferredSize(new Dimension(100, 30));
        panelInferior.add(botonCerrar);

        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        this.add(panelPrincipal, BorderLayout.CENTER);

        setVisible(true);
    }
    
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
