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
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import es.deusto.eleutradia.db.EleutradiaDBManager;
import es.deusto.eleutradia.domain.Cartera;
import es.deusto.eleutradia.domain.Operacion;
import es.deusto.eleutradia.domain.ProductoFinanciero;
import es.deusto.eleutradia.domain.Usuario;
import es.deusto.eleutradia.main.MainEleutradia;

public class VentanaAnadirACartera extends JDialog {
	private static final long serialVersionUID = 1L;
	private EleutradiaDBManager dbManager;
	private Usuario usuario;
	private ProductoFinanciero producto;
	private double cantidad = 0.0;
    private JComboBox<String> comboCarteras;
    private JButton botonConfirmar, botonCancelar;
    private JComboBox<Double> comboCantidad;
    private JComboBox<String> comboTipoCantidad;
    private JLabel labelCostoTotal, labelSaldoDisponible, labelSaldoRestante;
    
    // Estilos
    private static final Color COLOR_FONDO_PRINCIPAL = new Color(250, 250, 250);
    private static final Color COLOR_BORDE = new Color(220, 220, 230);
    private static final Color MY_AZUL_CLARO = new Color(0, 100, 255);
    private static final Color MY_AZUL_OSCURO = new Color(10, 60, 170);
    private static final Color MY_GRIS_CLARO = new Color(120, 120, 120);
    private static final Color MY_GRIS_OSCURO = new Color(70, 70, 70);
    private static final Color MY_VERDE_CLARO = new Color(40, 170, 70);
    private static final Color MY_NARANJA_CLARO = new Color(255, 140, 0);
    private static final Font FONT_TITULO = new Font("Segoe UI", Font.BOLD, 20);
    private static final Font FONT_SUBTITULO = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONT_NORMAL = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_PEQUENO = new Font("Segoe UI", Font.PLAIN, 11);
    private static final Font FONT_NUMERO_GRANDE = new Font("Segoe UI", Font.BOLD, 16);
	
	public VentanaAnadirACartera(JFrame padre, Usuario usuario, ProductoFinanciero producto, boolean modal) {
		super(padre, "Selección de cartera", modal);
		this.usuario = usuario;
		this.producto = producto;
		this.dbManager = MainEleutradia.getDBManager();
		this.setSize(700, 550);
		this.setLocationRelativeTo(padre);
		this.setBackground(COLOR_BORDE);
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
        panelPrincipal.add(construirPanelFormulario(), BorderLayout.CENTER);
        panelPrincipal.add(construirPanelInferior(), BorderLayout.SOUTH);
        this.add(panelPrincipal);
    }
	
	private JPanel construirPanelProducto() {
		JPanel panel = new JPanel(new BorderLayout(15, 10));
		panel.setBackground(Color.WHITE);
		panel.setBorder(BorderFactory.createCompoundBorder(
	            BorderFactory.createLineBorder(COLOR_BORDE, 1), new EmptyBorder(15, 20, 15, 20)));
		
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
	
	private JPanel construirPanelFormulario() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDE, 1), new EmptyBorder(20, 20, 20, 20)));
        
        JLabel tituloSeccion = new JLabel("Configurar operación");
        tituloSeccion.setFont(FONT_SUBTITULO);
        tituloSeccion.setForeground(MY_AZUL_OSCURO);
        tituloSeccion.setAlignmentX(LEFT_ALIGNMENT);
        panel.add(tituloSeccion);
        panel.add(Box.createVerticalStrut(15));
        
        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setBackground(Color.WHITE);
        panelCampos.setAlignmentX(LEFT_ALIGNMENT);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // CARTERA
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST; gbc.weightx = 0.0;
        JLabel labelCartera = new JLabel("Cartera:");
        labelCartera.setFont(FONT_NORMAL);
        labelCartera.setForeground(MY_GRIS_OSCURO);
        panelCampos.add(labelCartera, gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.WEST; gbc.weightx = 1.0;
        comboCarteras = new JComboBox<>();
        comboCarteras.setFont(FONT_NORMAL);
        comboCarteras.setPreferredSize(new Dimension(300, 32));
        comboCarteras.addItem("-- Seleccione una cartera --");
        if (usuario.getCarteras().isEmpty()) {
            comboCarteras.setEnabled(false);
            comboCarteras.setToolTipText("No tiene carteras disponibles. Cree una cartera primero.");
        } else {
            for (Cartera c : usuario.getCarteras()) {
                comboCarteras.addItem(String.format("%s (%.2f %s)", 
                    c.getNombre(), c.getSaldo(), c.getDivisa().getSimbolo()));
            }
            comboCarteras.setToolTipText("Seleccione la cartera donde desea añadir el producto");
        }
        comboCarteras.addActionListener(e -> actualizarCalculos());
        panelCampos.add(comboCarteras, gbc);
        
        // CANTIDAD
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; gbc.weightx = 0.0; gbc.anchor = GridBagConstraints.EAST;
        JLabel labelCantidad = new JLabel("Cantidad:");
        labelCantidad.setFont(FONT_NORMAL);
        labelCantidad.setForeground(MY_GRIS_OSCURO);
        panelCampos.add(labelCantidad, gbc);
        
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; gbc.weightx = 0.5;
        Double[] cantidadesTipicas = {1.0, 5.0, 10.0, 20.0, 50.0, 100.0, 200.0, 500.0};
        comboCantidad = new JComboBox<>(cantidadesTipicas);
        comboCantidad.setFont(FONT_NORMAL);
        comboCantidad.setEditable(true);
        comboCantidad.setPreferredSize(new Dimension(150, 32));
        comboCantidad.setToolTipText("Ingrese o seleccione la cantidad que desea comprar");
        comboCantidad.addActionListener(e -> actualizarCalculos());
        comboCantidad.getEditor().getEditorComponent().addKeyListener(
            new KeyAdapter() {
                public void keyReleased(java.awt.event.KeyEvent evt) {actualizarCalculos();}
            });
        panelCampos.add(comboCantidad, gbc);
        
        gbc.gridx = 2; gbc.weightx = 0.5;
        String[] tiposCantidad = {"Acciones", "Euros (€)"};
        comboTipoCantidad = new JComboBox<>(tiposCantidad);
        comboTipoCantidad.setFont(FONT_NORMAL);
        comboTipoCantidad.setPreferredSize(new Dimension(130, 32));
        comboTipoCantidad.setToolTipText("Seleccione si desea comprar por acciones o por importe");
        comboTipoCantidad.addActionListener(e -> actualizarCalculos());
        panelCampos.add(comboTipoCantidad, gbc);
        
        panel.add(panelCampos);
        return panel;
    }
	
	private JPanel construirPanelInferior() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_FONDO_PRINCIPAL);
        
        JPanel panelResumen = new JPanel(new GridBagLayout());
        panelResumen.setBackground(new Color(245, 248, 255));
        panelResumen.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 215, 240), 1), new EmptyBorder(15, 20, 15, 20)));
        panelResumen.setAlignmentX(LEFT_ALIGNMENT);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel tituloResumen = new JLabel("Resumen de la operación");
        tituloResumen.setFont(FONT_SUBTITULO);
        tituloResumen.setForeground(MY_AZUL_OSCURO);
        panelResumen.add(tituloResumen, gbc);
        
        gbc.gridy = 1;
        JSeparator separador = new JSeparator(SwingConstants.HORIZONTAL);
        separador.setPreferredSize(new Dimension(400, 1));
        panelResumen.add(separador, gbc);
        
        gbc.gridy = 2; gbc.gridwidth = 1;
        JLabel labelCostoTitulo = new JLabel("Costo total:");
        labelCostoTitulo.setFont(FONT_NORMAL);
        labelCostoTitulo.setForeground(MY_GRIS_OSCURO);
        panelResumen.add(labelCostoTitulo, gbc);
        gbc.gridx = 1;
        labelCostoTotal = new JLabel("0.00 €");
        labelCostoTotal.setFont(FONT_NUMERO_GRANDE);
        labelCostoTotal.setForeground(MY_AZUL_OSCURO);
        panelResumen.add(labelCostoTotal, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel labelSaldoDispTitulo = new JLabel("Saldo disponible:");
        labelSaldoDispTitulo.setFont(FONT_NORMAL);
        labelSaldoDispTitulo.setForeground(MY_GRIS_OSCURO);
        panelResumen.add(labelSaldoDispTitulo, gbc);
        gbc.gridx = 1;
        labelSaldoDisponible = new JLabel("-- €");
        labelSaldoDisponible.setFont(FONT_NORMAL);
        labelSaldoDisponible.setForeground(MY_GRIS_CLARO);
        panelResumen.add(labelSaldoDisponible, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel labelSaldoRestTitulo = new JLabel("Saldo restante:");
        labelSaldoRestTitulo.setFont(FONT_NORMAL);
        labelSaldoRestTitulo.setForeground(MY_GRIS_OSCURO);
        panelResumen.add(labelSaldoRestTitulo, gbc);
        gbc.gridx = 1;
        labelSaldoRestante = new JLabel("-- €");
        labelSaldoRestante.setFont(FONT_NORMAL);
        labelSaldoRestante.setForeground(MY_VERDE_CLARO);
        panelResumen.add(labelSaldoRestante, gbc);
        
        panel.add(panelResumen);
        panel.add(Box.createVerticalStrut(15));
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.setBackground(COLOR_FONDO_PRINCIPAL);
        panelBotones.setAlignmentX(LEFT_ALIGNMENT);
        
        botonCancelar = new JButton("Cancelar");
        botonCancelar.setFont(FONT_NORMAL);
        botonCancelar.setBackground(MY_GRIS_CLARO);
        botonCancelar.setForeground(Color.WHITE);
        botonCancelar.setPreferredSize(new Dimension(110, 35));
        botonCancelar.setBorderPainted(false);
        botonCancelar.setContentAreaFilled(false);
        botonCancelar.setOpaque(true);
        botonCancelar.setFocusPainted(false);
        botonCancelar.setToolTipText("Cancelar y cerrar esta ventana");
        
        botonConfirmar = new JButton("Confirmar compra");
        botonConfirmar.setFont(FONT_NORMAL);
        botonConfirmar.setBackground(MY_AZUL_CLARO);
        botonConfirmar.setForeground(Color.WHITE);
        botonConfirmar.setPreferredSize(new Dimension(160, 35));
        botonConfirmar.setBorderPainted(false);
        botonConfirmar.setContentAreaFilled(false);
        botonConfirmar.setOpaque(true);
        botonConfirmar.setFocusPainted(false);
        botonConfirmar.setToolTipText("Confirmar y añadir el producto a la cartera seleccionada");
        
        panelBotones.add(botonCancelar);
        panelBotones.add(botonConfirmar);
        panel.add(panelBotones);
        
        botonCancelar.addActionListener(e -> dispose());
        botonCancelar.addMouseListener(myAdapterGris);
        botonConfirmar.addActionListener(e -> seleccionarCartera());
        botonConfirmar.addMouseListener(myAdapterAzul);
        return panel;
    }
	
	private void actualizarCalculos() {
        try {
            int indiceCartera = comboCarteras.getSelectedIndex();
            if (indiceCartera <= 0) {
                labelCostoTotal.setText("0.00 €");
                labelSaldoDisponible.setText("-- €");
                labelSaldoRestante.setText("-- €");
                labelSaldoRestante.setForeground(MY_GRIS_CLARO);
                return;
            }
            Cartera carteraSeleccionada = usuario.getCarteras().get(indiceCartera - 1);
            Object cantidadObj = comboCantidad.getEditor().getItem();
            double cantidadIngresada = Double.parseDouble(cantidadObj.toString());
            if (cantidadIngresada <= 0) {
                labelCostoTotal.setText("0.00 €");
                labelSaldoDisponible.setText(String.format("%.2f %s", 
                    carteraSeleccionada.getSaldo(), carteraSeleccionada.getDivisa().getSimbolo()));
                labelSaldoRestante.setText(String.format("%.2f %s", 
                    carteraSeleccionada.getSaldo(), carteraSeleccionada.getDivisa().getSimbolo()));
                labelSaldoRestante.setForeground(MY_VERDE_CLARO);
                return;
            }
            String tipoCantidad = (String) comboTipoCantidad.getSelectedItem();
            double costoTotal = tipoCantidad.equals("Euros (€)") ? cantidadIngresada 
                : cantidadIngresada * producto.getValorUnitario();
            double saldoRestante = carteraSeleccionada.getSaldo() - costoTotal;
            labelCostoTotal.setText(String.format("%.2f %s", costoTotal, carteraSeleccionada.getDivisa().getSimbolo()));
            labelSaldoDisponible.setText(String.format("%.2f %s", 
                carteraSeleccionada.getSaldo(), carteraSeleccionada.getDivisa().getSimbolo()));
            labelSaldoRestante.setText(String.format("%.2f %s", saldoRestante, carteraSeleccionada.getDivisa().getSimbolo()));
            if (saldoRestante < 0) {
                labelSaldoRestante.setForeground(Color.RED);
                labelCostoTotal.setForeground(Color.RED);
            } else if (saldoRestante < carteraSeleccionada.getSaldo() * 0.1) {
                labelSaldoRestante.setForeground(MY_NARANJA_CLARO);
                labelCostoTotal.setForeground(MY_AZUL_OSCURO);
            } else {
                labelSaldoRestante.setForeground(MY_VERDE_CLARO);
                labelCostoTotal.setForeground(MY_AZUL_OSCURO);
            }
        } catch (NumberFormatException ex) {
            labelCostoTotal.setText("-- €");
            labelSaldoRestante.setText("-- €");
        }
    }
	
	private void seleccionarCartera() {
	    int indexCartera = comboCarteras.getSelectedIndex();
	    if (indexCartera <= 0) {
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
	    
	    Cartera carteraSel = usuario.getCarteras().get(indexCartera - 1);
	    
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
	    double cantidadAcciones;
	    
	    double precioProducto = producto.getValorUnitario();
	    if (precioProducto <= 0) {
	        JOptionPane.showMessageDialog(this,
	                "El producto no tiene un precio válido.",
	                "Error de precio", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    if (tipoCantidad.equals("Euros (€)")) {
	        cantidadAcciones = cantidad / precioProducto;
	    } else {
	        cantidadAcciones = cantidad;
	    }
	    
        double costeTotal = cantidadAcciones * precioProducto;
        if (carteraSel.getSaldo() < costeTotal) {
            JOptionPane.showMessageDialog(this,
        		String.format("Saldo insuficiente.\nCoste: %.2f %s\nSaldo disponible: %.2f %s",
                        costeTotal, carteraSel.getDivisa(), carteraSel.getSaldo(), carteraSel.getDivisa()),
                "Saldo insuficiente",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
	        
        // Verificar si el producto ya existe en la cartera
        boolean productoExiste = carteraSel.contieneProducto(producto);
        String mensajeConfirmacion = productoExiste ?
        		// Si existe, preguntar si desea añadir más acciones
        		String.format("Esta cartera ya contiene el producto '%s'.\n¿Desea añadir %.4f acciones adicionales por un coste de %.2f %s?",
                    producto.getNombre(), cantidadAcciones, costeTotal, carteraSel.getDivisa()) :
                // Si no existe, confirmar la compra normal
                String.format("¿Desea comprar %.4f acciones de '%s' por un coste de %.2f %s?",
                    cantidadAcciones, producto.getNombre(), costeTotal, carteraSel.getDivisa());
        
        int confirmacion = JOptionPane.showConfirmDialog(this,
                mensajeConfirmacion,
                "Confirmar operación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (confirmacion != JOptionPane.YES_OPTION) return;
        
        Operacion operacion = new Operacion(producto, cantidadAcciones, LocalDate.now(), true);
            
        // Guardar en base de datos
        boolean exito = dbManager.insertOperacion(operacion, carteraSel.getId());
        
        if (exito) {
            carteraSel.addProducto(producto, cantidadAcciones);
            
            dbManager.actualizarPosicion(
                carteraSel.obtenerPosicionesActuales().stream()
                    .filter(p -> p.getProducto().getId() == producto.getId())
                    .findFirst()
                    .orElse(null),
                carteraSel.getId()
            );
            
            JOptionPane.showMessageDialog(this,
                    String.format("Producto añadido correctamente:\n%.4f acciones de %s\nCoste total: %.2f %s", 
                            cantidadAcciones, producto.getNombre(), costeTotal, carteraSel.getDivisa()),
                    "Operación exitosa",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
            
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar la operación en la base de datos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
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
	
	private Color obtenerColorRiesgo(int riesgo) {
	    switch (riesgo) {
	        case 1:
	            return new Color(0, 160, 60);    // Verde intenso (muy bajo)
	        case 2:
	            return new Color(80, 190, 80);   // Verde claro (bajo)
	        case 3:
	            return new Color(190, 200, 70);  // Amarillo verdoso (medio-bajo)
	        case 4:
	            return new Color(255, 200, 0);   // Amarillo (medio)
	        case 5:
	            return new Color(255, 150, 0);   // Naranja (medio-alto)
	        case 6:
	            return new Color(255, 90, 0);    // Naranja oscuro (alto)
	        case 7:
	            return new Color(200, 40, 40);   // Rojo (muy alto)
	        default:
	            return MY_GRIS_CLARO;
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