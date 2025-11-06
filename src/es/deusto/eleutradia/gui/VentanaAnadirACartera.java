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
    private static final Color MY_AZUL = new Color(0, 100, 255);   // Azul
    private static final Color MY_GRIS = new Color(100, 100, 100); // Gris
	
	public VentanaAnadirACartera(JFrame padre, Usuario usuario, ProductoFinanciero producto, boolean modal) {
		super(padre, "Selección de cartera", modal);
		this.usuario = usuario;
		this.producto = producto;
		this.configurarVentana(padre);
        this.construirVentana();
        this.setVisible(true);
	}

	private void configurarVentana(JFrame padre) {
		this.setSize(600, 450);
        this.setLocationRelativeTo(padre);
        this.setLayout(new BorderLayout(10, 10));
        this.setResizable(false);
	}
	
	private void construirVentana() {
        JLabel titulo = new JLabel("Selecciona la cartera donde añadir el producto", JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(titulo, BorderLayout.NORTH);
        
        JPanel panelCentral = new JPanel(new FlowLayout());
        
        comboCarteras = new JComboBox<>();
        if (usuario.getCarteras().isEmpty()) {
            comboCarteras.addItem("No tienes carteras disponibles");
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
        botonCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        botonCancelar.setBackground(MY_GRIS);
        botonCancelar.setForeground(Color.WHITE);
        botonCancelar.setFocusPainted(false);
        
        botonConfirmar = new JButton("Añadir");
        botonConfirmar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        botonConfirmar.setBackground(MY_AZUL);
        botonConfirmar.setForeground(Color.WHITE);
        botonConfirmar.setFocusPainted(false);
        
        panelBotones.add(botonCancelar);
        panelBotones.add(botonConfirmar);
        
        this.add(panelBotones, BorderLayout.SOUTH);
		
        botonCancelar.addActionListener(e -> dispose());

        botonConfirmar.addActionListener(e -> seleccionarCartera());
	}
	
	private void seleccionarCartera() {
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
            JOptionPane.showMessageDialog(this,
                    "Añadido " + cantidad + " de " + producto.getNombre() +
                            " a la cartera \"" + carteraSel.getNombre() + "\"",
                    "Compra exitosa", JOptionPane.INFORMATION_MESSAGE);
            
            dispose();
		}
	}
}
