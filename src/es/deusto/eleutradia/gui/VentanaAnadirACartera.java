package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    private JComboBox<String> comboTipoCantidad;
    
    // Estilos
    private static final Color MY_AZUL_CLARO = new Color(0, 100, 255);
    private static final Color MY_AZUL_OSCURO = new Color(10, 60, 170);
    private static final Color MY_GRIS_CLARO = new Color(120, 120, 120);
    private static final Color MY_GRIS_OSCURO = new Color(70, 70, 70);
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
		this.setSize(600, 300);
		this.setBackground(COLOR_FONDO_PRINCIPAL);
        this.setLocationRelativeTo(padre);
        this.setLayout(new BorderLayout(10, 10));
        this.setResizable(false);
	}
	
	private void construirVentana() {
		JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(COLOR_FONDO_PRINCIPAL);
        
        JLabel titulo = new JLabel("Seleccione la cartera donde añadir el producto", JLabel.CENTER);
        titulo.setFont(FONT_TITULO);
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelTitulo.add(titulo, BorderLayout.CENTER);
        
        this.add(panelTitulo, BorderLayout.NORTH);
        
        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBackground(COLOR_FONDO_PRINCIPAL);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Cartera
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel labelCartera = new JLabel("Cartera:");
        labelCartera.setFont(FONT_NORMAL);
        panelCentral.add(labelCartera, gbc);
        
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        comboCarteras = new JComboBox<>();
        comboCarteras.addItem("Ninguna cartera seleccionada");
        if (usuario.getCarteras().isEmpty()) {
            comboCarteras.setEnabled(false);
        } else {
            for (Cartera c : usuario.getCarteras()) {
                comboCarteras.addItem(c.getNombre());
            }
        }
        panelCentral.add(comboCarteras, gbc);
        
        // Cantidad
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel labelCantidad = new JLabel("Cantidad:");
        labelCantidad.setFont(FONT_NORMAL);
        panelCentral.add(labelCantidad, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        Double[] cantidadesTipicas = new Double[]{1.0, 5.0, 10.0, 20.0, 50.0, 100.0, 200.0, 500.0};
        comboCantidad = new JComboBox<>(cantidadesTipicas);
        comboCantidad.setEditable(true);
        panelCentral.add(comboCantidad, gbc);
        
        // Tipo de cantidad
        gbc.gridx = 2;
        String[] tiposCantidad = {"Acciones", "Euros (€)"};
        comboTipoCantidad = new JComboBox<>(tiposCantidad);
        comboTipoCantidad.setFont(FONT_NORMAL);
        panelCentral.add(comboTipoCantidad, gbc);
        
        this.add(panelCentral, BorderLayout.CENTER);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(COLOR_FONDO_PRINCIPAL);
        
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
        botonCancelar.addMouseListener(myAdapterGris);
        botonConfirmar.addActionListener(e -> seleccionarCartera());
        botonConfirmar.addMouseListener(myAdapterAzul);
	}
	
	private void seleccionarCartera() {
	    Object carteraSeleccionada = comboCarteras.getSelectedItem();
	    if (carteraSeleccionada == null || carteraSeleccionada.toString().trim().isEmpty() 
	    		|| carteraSeleccionada.equals("Ninguna cartera seleccionada")) {
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
		
		// Obtener cantidad
        try {
            Object cantidadDeseada = comboCantidad.getEditor().getItem();
            cantidad = Double.parseDouble(cantidadDeseada.toString());
            
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, 
                		"La cantidad debe ser mayor que cero.", 
                		"Cantidad inválida", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
            		"Por favor, introduzca una cantidad válida.", 
            		"Cantidad inválida", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Convertir a acciones si se ingresa en euros
        String tipoCantidad = (String) comboTipoCantidad.getSelectedItem();
        double cantidadAcciones = cantidad;
        
        if (tipoCantidad.equals("Euros (€)")) {
            double precioProducto = producto.getValorUnitario();
            if (precioProducto <= 0) {
                JOptionPane.showMessageDialog(this, 
                		"El producto no tiene un precio válido.", 
                		"Error de precio", JOptionPane.ERROR_MESSAGE);
                return;
            }
            cantidadAcciones = cantidad / precioProducto;
            
            // Confirmar conversión al usuario
            int respuesta = JOptionPane.showConfirmDialog(this,
                    String.format("Se añadirán %.4f acciones por %.2f€\n¿Desea continuar?", 
                    		cantidadAcciones, cantidad),
                    "Confirmar conversión",
                    JOptionPane.YES_NO_OPTION);
            
            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }
        }
		
		if (carteraSel != null) {
			carteraSel.addProducto(producto, cantidadAcciones);
			JOptionPane.showMessageDialog(this,
					String.format("Producto añadido correctamente:\n%.4f acciones de %s", 
							cantidadAcciones, producto.getNombre()),
					"Producto añadido",
					JOptionPane.INFORMATION_MESSAGE);
            dispose();
		}
	}
	
	MouseAdapter myAdapterAzul = new MouseAdapter() {
    	@Override
		public void mouseEntered(MouseEvent e) {e.getComponent().setBackground(MY_AZUL_OSCURO);}
		@Override
		public void mouseExited(MouseEvent e) {e.getComponent().setBackground(MY_AZUL_CLARO);}
		@Override
		public void mousePressed(MouseEvent e) {e.getComponent().setBackground(MY_AZUL_OSCURO);}
		@Override
		public void mouseReleased(MouseEvent e) {e.getComponent().setBackground(MY_AZUL_CLARO);}
    };
	
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