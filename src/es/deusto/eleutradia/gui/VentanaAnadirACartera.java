package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import es.deusto.eleutradia.domain.Cartera;
import es.deusto.eleutradia.domain.ProductoFinanciero;
import es.deusto.eleutradia.domain.Usuario;

public class VentanaAnadirACartera extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	private ProductoFinanciero producto;
	private double cantidad = 0.0;
    private JComboBox<String> comboCarteras;
    private JButton botonConfirmar, botonCancelar;
    private JComboBox<Double> comboCantidad;
    
    // Estilos
    private static final Color MY_AZUL_CLARO = new Color(0, 100, 255);
    private static final Color MY_GRIS_CLARO = new Color(120, 120, 120);
    private static final Color COLOR_FONDO_PRINCIPAL = new Color(250, 250, 250);
    private static final Font FONT_TITULO = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FONT_NORMAL = new Font("Segoe UI", Font.PLAIN, 12);
	
	public VentanaAnadirACartera(JFrame padre, Usuario usuario, ProductoFinanciero producto, boolean modal) {
		super(padre, "Selección de cartera", modal);
		this.usuario = usuario;
		this.producto = producto;
		this.configurarVentana(padre);
        this.construirVentana();
        this.setVisible(true);
	}

	private void configurarVentana(JFrame padre) {
		this.setSize(600, 250);
		this.setBackground(COLOR_FONDO_PRINCIPAL);
        this.setLocationRelativeTo(padre);
        this.setLayout(new BorderLayout(10, 10));
        this.setResizable(false);
	}
	
	private void construirVentana() {
        JLabel titulo = new JLabel("Seleccione la cartera donde añadir el producto", JLabel.CENTER);
        titulo.setFont(FONT_TITULO);
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(titulo, BorderLayout.NORTH);
        
        JPanel panelCentral = new JPanel(new FlowLayout());
        
        comboCarteras = new JComboBox<>();
        comboCarteras.addItem("Ninguna cartera seleccionada");
        if (usuario.getCarteras().isEmpty()) {
            comboCarteras.setEnabled(false);
        } else {
            for (Cartera c : usuario.getCarteras()) {
                comboCarteras.addItem(c.getNombre());
            }
        }
        panelCentral.add(comboCarteras);
        
        Double[] cantidadesTipicas = new Double[]{10.0, 50.0, 100.0, 200.0};
        comboCantidad = new JComboBox<>(cantidadesTipicas);
        comboCantidad.setEditable(true);
        panelCentral.add(comboCantidad);        
        
        this.add(panelCentral, BorderLayout.CENTER);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        
        botonCancelar = new JButton("Cancelar");
        botonCancelar.setFont(FONT_NORMAL);
        botonCancelar.setBackground(MY_GRIS_CLARO);
        botonCancelar.setForeground(Color.WHITE);
        botonCancelar.setBorderPainted(false);
        botonCancelar.setContentAreaFilled(false);
        botonCancelar.setOpaque(true);
        botonCancelar.setFocusPainted(false);
        
        botonConfirmar = new JButton("Añadir");
        botonConfirmar.setFont(FONT_NORMAL);
        botonConfirmar.setBackground(MY_AZUL_CLARO);
        botonConfirmar.setForeground(Color.WHITE);
        botonConfirmar.setBorderPainted(false);
        botonConfirmar.setContentAreaFilled(false);
        botonConfirmar.setOpaque(true);
        botonConfirmar.setFocusPainted(false);
        
        panelBotones.add(botonCancelar);
        panelBotones.add(botonConfirmar);
        
        this.add(panelBotones, BorderLayout.SOUTH);
		
        botonCancelar.addActionListener(e -> dispose());

        botonConfirmar.addActionListener(e -> seleccionarCartera());
	}
	
	private void seleccionarCartera() {
	    Object carteraSeleccionada = comboCarteras.getSelectedItem();
	    if (carteraSeleccionada == null || carteraSeleccionada.toString().trim().isEmpty()) {
	        JOptionPane.showMessageDialog(this,
	                "Por favor, seleccione una cartera antes de continuar.",
	                "Ninguna cartera seleccionada", JOptionPane.WARNING_MESSAGE);
	        return;
	    }
		
		if (usuario.getCarteras().isEmpty()) {
			JOptionPane.showMessageDialog(this, 
					"Debe tener al menos una cartera creada para realizar esta acción.",
					"Cartera necesaria", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		String nombreCarteraSel = (String) comboCarteras.getSelectedItem();
		Cartera carteraSel = null;
		for (Cartera c : usuario.getCarteras()) {
			if (c.getNombre().equals(nombreCarteraSel))	{
				carteraSel = c;
				break;
			}
		}
		
        try {
            Object cantidadDeseada = comboCantidad.getEditor().getItem();
            cantidad = Double.parseDouble(cantidadDeseada.toString());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, introduzca una cantidad válida.", "Cantidad inválida", JOptionPane.ERROR_MESSAGE);
            return;
        }
		
		if (carteraSel != null) {
			carteraSel.anadirProducto(producto, cantidad);
            dispose();
		}
	}
}
