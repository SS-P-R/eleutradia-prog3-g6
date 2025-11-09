package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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

        // Panel central con información
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBackground(COLOR_FONDO_PRINCIPAL);
        panelInfo.setBorder(new EmptyBorder(10, 0, 0, 0));

        panelInfo.add(new JLabel("Tipo: " + producto.getTipoProducto().getNombre()));
        panelInfo.add(Box.createVerticalStrut(5));
        panelInfo.add(new JLabel("Riesgo: " + producto.getTipoProducto().getStringRiesgo()));
        panelInfo.add(Box.createVerticalStrut(5));
        panelInfo.add(new JLabel("Importe mínimo: " + producto.getTipoProducto().getImporteMin() + " " + producto.getDivisa().getSimbolo()));
        panelInfo.add(Box.createVerticalStrut(10));

        JLabel rentabilidadesLabel = new JLabel("Rentabilidades históricas:");
        rentabilidadesLabel.setFont(FONT_SUBTITULO);
        panelInfo.add(rentabilidadesLabel);
        panelInfo.add(Box.createVerticalStrut(5));

        // Rentabilidades
        for (Map.Entry<PlazoRentabilidad, BigDecimal> entry : producto.getRentabilidades().entrySet()) {
            String plazo = entry.getKey().getDefinicion();
            BigDecimal valor = entry.getValue();
            JLabel lbl = new JLabel("  • " + plazo + ": " + (valor != null ? valor + "%" : "---"));
            lbl.setFont(FONT_NORMAL);
            panelInfo.add(lbl);
        }

        panelPrincipal.add(panelInfo, BorderLayout.CENTER);

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
