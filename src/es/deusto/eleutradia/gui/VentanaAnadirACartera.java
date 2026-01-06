package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import es.deusto.eleutradia.db.EleutradiaDBManager;
import es.deusto.eleutradia.domain.Cartera;
import es.deusto.eleutradia.domain.Divisa;
import es.deusto.eleutradia.domain.Empresa;
import es.deusto.eleutradia.domain.Operacion;
import es.deusto.eleutradia.domain.Particular;
import es.deusto.eleutradia.domain.ProductoFinanciero;
import es.deusto.eleutradia.domain.TipoProducto;
import es.deusto.eleutradia.domain.Usuario;
import es.deusto.eleutradia.gui.style.UITema;
import es.deusto.eleutradia.main.MainEleutradia;

import static es.deusto.eleutradia.gui.style.UITema.*;

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
	
	public VentanaAnadirACartera(JFrame padre, Usuario usuario, ProductoFinanciero producto, boolean modal) {
		super(padre, "Selección de cartera", modal);
		this.usuario = usuario;
		this.producto = producto;
		this.dbManager = MainEleutradia.getDBManager();
		this.setSize(700, 600);
		this.setLocationRelativeTo(padre);
		this.setBackground(MAIN_FONDO);
		this.setLayout(new BorderLayout(0, 0));
		this.setResizable(false);
        this.construirVentana();
        this.setVisible(true);
	}
	
	private void construirVentana() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(MAIN_FONDO);
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
            BorderFactory.createLineBorder(MAIN_BORDE, 1), 
            new EmptyBorder(15, 20, 15, 20)));
		
		JPanel panelLogo = new JPanel(new BorderLayout());
		panelLogo.setBackground(Color.WHITE);
		panelLogo.setPreferredSize(new Dimension(100, 80));
		
		String rutaLogoGestora = null;
        if (producto.getGestora() != null) {
            rutaLogoGestora = "/images/gestoras/gestora" + producto.getGestora().getNombreComercial().toLowerCase() + ".png";
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
            labelSinLogo.setForeground(GRIS_MEDIO);
            panelLogo.add(labelSinLogo, BorderLayout.CENTER);
        }
        
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBackground(Color.WHITE);
        
        JLabel labelNombre = new JLabel(producto.getNombre());
        labelNombre.setFont(TITULO_GRANDE);
        labelNombre.setForeground(AZUL_CLARO);
        labelNombre.setAlignmentX(LEFT_ALIGNMENT);
        
        JLabel labelTipo = new JLabel(producto.getTipoProducto().getNombre() + 
                " • " + producto.getRegionGeografica().getNombre());
		labelTipo.setFont(CUERPO_MEDIO);
		labelTipo.setForeground(GRIS_MEDIO);
		labelTipo.setAlignmentX(LEFT_ALIGNMENT);
		
		JPanel panelPrecio = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelPrecio.setBackground(Color.WHITE);
        panelPrecio.setAlignmentX(LEFT_ALIGNMENT);
        
        JLabel labelPrecio = new JLabel(String.format("%.2f", producto.getValorUnitario()));
        labelPrecio.setFont(SUBTITULO_GRANDE);
        labelPrecio.setForeground(AZUL_OSCURO);
        JLabel labelDivisa = new JLabel(" " + producto.getDivisa().getSimbolo() + "/acción");
        labelDivisa.setFont(CUERPO_MEDIO);
        labelDivisa.setForeground(GRIS_MEDIO);
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
        labelRiesgoTitulo.setFont(CUERPO_PEQUENO);
        labelRiesgoTitulo.setForeground(GRIS_MEDIO);
        labelRiesgoTitulo.setAlignmentX(CENTER_ALIGNMENT);
        JLabel labelRiesgoValor = new JLabel(producto.getTipoProducto().getStringRiesgo());
        labelRiesgoValor.setFont(SUBTITULO_MEDIO);
        labelRiesgoValor.setForeground(TipoProducto.getColorRiesgo(producto.getTipoProducto().getRiesgo()));
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
            BorderFactory.createLineBorder(MAIN_BORDE, 1), new EmptyBorder(20, 20, 20, 20)));
        
        JLabel tituloSeccion = new JLabel("Configurar operación");
        tituloSeccion.setFont(SUBTITULO_MEDIO);
        tituloSeccion.setForeground(AZUL_OSCURO);
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
        labelCartera.setFont(CUERPO_MEDIO);
        labelCartera.setForeground(GRIS_OSCURO);
        panelCampos.add(labelCartera, gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.WEST; gbc.weightx = 1.0;
        comboCarteras = new JComboBox<>();
        comboCarteras.setFont(CUERPO_MEDIO);
        comboCarteras.setPreferredSize(new Dimension(300, 35));
        UITema.personalizarComboBox(comboCarteras);
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
        comboCarteras.addActionListener(e -> {
        	actualizarTipoCantidad();
        	actualizarCalculos();
        });
        panelCampos.add(comboCarteras, gbc);
        
        // CANTIDAD
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; gbc.weightx = 0.0; gbc.anchor = GridBagConstraints.EAST;
        JLabel labelCantidad = new JLabel("Cantidad:");
        labelCantidad.setFont(CUERPO_MEDIO);
        labelCantidad.setForeground(GRIS_OSCURO);
        panelCampos.add(labelCantidad, gbc);
        
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; gbc.weightx = 0.5;
        Double[] cantidadesTipicas = {1.0, 5.0, 10.0, 20.0, 50.0, 100.0, 200.0, 500.0};
        comboCantidad = new JComboBox<>(cantidadesTipicas);
        comboCantidad.setFont(CUERPO_MEDIO);
        comboCantidad.setEditable(true);
        comboCantidad.setPreferredSize(new Dimension(150, 35));
        UITema.personalizarComboBox(comboCantidad);
        comboCantidad.setToolTipText("Ingrese o seleccione la cantidad que desea comprar");
        comboCantidad.addActionListener(e -> actualizarCalculos());
        comboCantidad.getEditor().getEditorComponent().addKeyListener(
            new KeyAdapter() {
                public void keyReleased(KeyEvent evt) {actualizarCalculos();}
            });
        panelCampos.add(comboCantidad, gbc);
        
        gbc.gridx = 2; gbc.weightx = 0.5;
        comboTipoCantidad = new JComboBox<>();
        comboTipoCantidad.setFont(CUERPO_MEDIO);
        comboTipoCantidad.setPreferredSize(new Dimension(130, 35));
        UITema.personalizarComboBox(comboTipoCantidad);
        comboTipoCantidad.setToolTipText("Seleccione si desea comprar por acciones o por importe");
        comboTipoCantidad.addItem("Acciones");
        comboTipoCantidad.addActionListener(e -> actualizarCalculos());
        panelCampos.add(comboTipoCantidad, gbc);
        
        panel.add(panelCampos);
        return panel;
    }
	
	private void actualizarTipoCantidad() {
	    int index = comboCarteras.getSelectedIndex();

	    comboTipoCantidad.removeAllItems();
	    comboTipoCantidad.addItem("Acciones");

	    if (index <= 0) return;

	    Cartera carteraSeleccionada = usuario.getCarteras().get(index - 1);
	    Divisa divisa = carteraSeleccionada.getDivisa();
	    String textoDivisa = String.format("%s (%s)", 
	            divisa.toString(),
	            divisa.getSimbolo());
	    comboTipoCantidad.addItem(textoDivisa);
	    comboTipoCantidad.setSelectedIndex(0);
	}
	
	private JPanel construirPanelInferior() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(MAIN_FONDO);
        
        JPanel panelResumen = new JPanel(new GridBagLayout());
        panelResumen.setBackground(Color.WHITE);
        panelResumen.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MAIN_BORDE, 1), new EmptyBorder(15, 20, 15, 20)));
        panelResumen.setAlignmentX(LEFT_ALIGNMENT);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel tituloResumen = new JLabel("Resumen de la operación");
        tituloResumen.setFont(SUBTITULO_MEDIO);
        tituloResumen.setForeground(AZUL_OSCURO);
        panelResumen.add(tituloResumen, gbc);
        
        gbc.gridy = 1;
        JSeparator separador = new JSeparator(JSeparator.HORIZONTAL);
        separador.setPreferredSize(new Dimension(400, 1));
        panelResumen.add(separador, gbc);
        
        gbc.gridy = 2; gbc.gridwidth = 1;
        JLabel labelCostoTitulo = new JLabel("Costo total:");
        labelCostoTitulo.setFont(CUERPO_MEDIO);
        labelCostoTitulo.setForeground(GRIS_OSCURO);
        panelResumen.add(labelCostoTitulo, gbc);
        gbc.gridx = 1;
        labelCostoTotal = new JLabel("0.00 €");
        labelCostoTotal.setFont(SUBTITULO_GRANDE);
        labelCostoTotal.setForeground(AZUL_OSCURO);
        panelResumen.add(labelCostoTotal, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel labelSaldoDispTitulo = new JLabel("Saldo disponible:");
        labelSaldoDispTitulo.setFont(CUERPO_MEDIO);
        labelSaldoDispTitulo.setForeground(GRIS_OSCURO);
        panelResumen.add(labelSaldoDispTitulo, gbc);
        gbc.gridx = 1;
        labelSaldoDisponible = new JLabel("-- €");
        labelSaldoDisponible.setFont(CUERPO_MEDIO);
        labelSaldoDisponible.setForeground(GRIS_MEDIO);
        panelResumen.add(labelSaldoDisponible, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel labelSaldoRestTitulo = new JLabel("Saldo restante:");
        labelSaldoRestTitulo.setFont(CUERPO_MEDIO);
        labelSaldoRestTitulo.setForeground(GRIS_OSCURO);
        panelResumen.add(labelSaldoRestTitulo, gbc);
        gbc.gridx = 1;
        labelSaldoRestante = new JLabel("-- €");
        labelSaldoRestante.setFont(CUERPO_MEDIO);
        labelSaldoRestante.setForeground(VERDE_CLARO);
        panelResumen.add(labelSaldoRestante, gbc);
        
        panel.add(panelResumen);
        panel.add(Box.createVerticalStrut(15));
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.setBackground(MAIN_FONDO);
        panelBotones.setAlignmentX(LEFT_ALIGNMENT);
        
        botonCancelar = new JButton("Cancelar");
        botonCancelar.setFont(CUERPO_MEDIO);
        botonCancelar.setBackground(GRIS_MEDIO);
        botonCancelar.setForeground(Color.WHITE);
        botonCancelar.setPreferredSize(new Dimension(110, 35));
        botonCancelar.setBorderPainted(false);
        botonCancelar.setContentAreaFilled(false);
        botonCancelar.setOpaque(true);
        botonCancelar.setFocusPainted(false);
        botonCancelar.setToolTipText("Cancelar y cerrar esta ventana");
        
        botonConfirmar = new JButton("Confirmar compra");
        botonConfirmar.setFont(CUERPO_MEDIO);
        botonConfirmar.setBackground(AZUL_CLARO);
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
                labelSaldoRestante.setForeground(GRIS_MEDIO);
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
                labelSaldoRestante.setForeground(VERDE_CLARO);
                return;
            }
            
            Divisa divisaProducto = producto.getDivisa();
            Divisa divisaCartera = carteraSeleccionada.getDivisa();
            BigDecimal precioProducto = BigDecimal.valueOf(producto.getValorUnitario());
            BigDecimal precioEnDivisaCartera = divisaProducto.convertirA(precioProducto, divisaCartera);
            double precioConvertido = precioEnDivisaCartera.doubleValue();
            
            boolean porImporte = comboTipoCantidad.getSelectedIndex() == 1;
            double costoTotal = porImporte ? cantidadIngresada 
                : cantidadIngresada * precioConvertido;
            double saldoRestante = carteraSeleccionada.getSaldo() - costoTotal;
            labelCostoTotal.setText(String.format("%.2f %s", costoTotal, carteraSeleccionada.getDivisa().getSimbolo()));
            labelSaldoDisponible.setText(String.format("%.2f %s", 
                carteraSeleccionada.getSaldo(), carteraSeleccionada.getDivisa().getSimbolo()));
            labelSaldoRestante.setText(String.format("%.2f %s", saldoRestante, carteraSeleccionada.getDivisa().getSimbolo()));
            if (saldoRestante < 0) {
                labelSaldoRestante.setForeground(Color.RED);
                labelCostoTotal.setForeground(Color.RED);
            } else if (saldoRestante < carteraSeleccionada.getSaldo() * 0.1) {
                labelSaldoRestante.setForeground(NARANJA_CLARO);
                labelCostoTotal.setForeground(AZUL_OSCURO);
            } else {
                labelSaldoRestante.setForeground(VERDE_CLARO);
                labelCostoTotal.setForeground(AZUL_OSCURO);
            }
        } catch (NumberFormatException ex) {
            labelCostoTotal.setText("-- €");
            labelSaldoRestante.setText("-- €");
        }
    }
	
	private void seleccionarCartera() {
	    int indexCartera = comboCarteras.getSelectedIndex();
	    if (indexCartera <= 0) {
	        UITema.mostrarWarning(this,
	                "Por favor, seleccione una cartera antes de continuar.",
	                "Ninguna cartera seleccionada");
	        return;
	    }
	    
	    if (usuario.getCarteras().isEmpty()) {
	        UITema.mostrarWarning(this, 
	                "Debe tener al menos una cartera creada para realizar esta acción.",
	                "Cartera necesaria");
	        return;
	    }
	    
	    Cartera carteraSel = usuario.getCarteras().get(indexCartera - 1);
	    
	    // Obtener cantidad
	    try {
	        Object cantidadDeseada = comboCantidad.getEditor().getItem();
	        cantidad = Double.parseDouble(cantidadDeseada.toString());
	        if (cantidad <= 0) {
	            UITema.mostrarWarning(this, 
	                    "La cantidad debe ser mayor que cero.", 
	                    "Cantidad inválida");
	            return;
	        }
	    } catch (NumberFormatException ex) {
	        	UITema.mostrarWarning(this, 
	                "Por favor, introduzca una cantidad válida.", 
	                "Cantidad inválida");
	        return;
	    }
	    
	    // Convertir a acciones si se ingresa en importe
	    double cantidadAcciones;
	    
	    Divisa divisaProducto = producto.getDivisa();
	    Divisa divisaCartera = carteraSel.getDivisa();
	    BigDecimal precioProducto = BigDecimal.valueOf(producto.getValorUnitario());
	    BigDecimal precioEnDivisaCartera = divisaProducto.convertirA(precioProducto, divisaCartera);
	    double precioConvertido = precioEnDivisaCartera.doubleValue();
	    
	    if (precioConvertido <= 0) {
	        UITema.mostrarError(this,
	                "El producto no tiene un precio válido.",
	                "Error de precio");
	        return;
	    }
	    
	    boolean porImporte = comboTipoCantidad.getSelectedIndex() == 1;
	    if (porImporte) {
	        cantidadAcciones = cantidad / precioConvertido;
	    } else {
	        cantidadAcciones = cantidad;
	    }
        
	    double costeTotal = cantidadAcciones * precioConvertido;
        if (carteraSel.getSaldo() < costeTotal) {
            UITema.mostrarError(this,
        		String.format("Saldo insuficiente.\nCoste: %.2f %s\nSaldo disponible: %.2f %s",
                        costeTotal, carteraSel.getDivisa().getSimbolo(), 
                        carteraSel.getSaldo(), carteraSel.getDivisa().getSimbolo()),
                "Saldo insuficiente");
            return;
        }
	        
        // Comprobamos si el producto ya existe en la cartera
        boolean productoExiste = carteraSel.contieneProducto(producto);
        String mensajeConfirmacion = productoExiste ?
        		// Si existe, preguntamos si desea añadir más acciones
        		String.format("Esta cartera ya contiene el producto '%s'.\n¿Desea añadir %.4f acciones adicionales por un coste de %.2f %s?",
                    producto.getNombre(), cantidadAcciones, costeTotal, carteraSel.getDivisa().getSimbolo()) :
                // Si no existe, confirmamos la compra normal
                String.format("¿Desea comprar %.4f acciones de '%s' por un coste de %.2f %s?",
                    cantidadAcciones, producto.getNombre(), costeTotal, carteraSel.getDivisa().getSimbolo());

        if (!UITema.mostrarConfirmacion(this,
                mensajeConfirmacion,
                "Confirmar operación")) return;
        
        Operacion operacion = new Operacion(producto, cantidadAcciones, producto.getValorUnitario(), LocalDate.now(), true);
            
        // Guardamos en base de datos
        boolean exito = dbManager.insertOperacion(operacion, carteraSel.getId());
        
        if (exito) {
            
        	String idUsuario;
            boolean esParticular;
            
            if (usuario instanceof Particular) {
                idUsuario = ((Particular) usuario).getDni();
                esParticular = true;
            } else {
                idUsuario = ((Empresa) usuario).getNif();
                esParticular = false;
            }
            
            // Recargar carteras actualizadas desde la BD
            List<Cartera> carterasActualizadas = dbManager.getCarterasPorUsuario(idUsuario, esParticular);
            
            usuario.getCarteras().clear();
            for (Cartera c : carterasActualizadas) {
                usuario.addCartera(c);
            }
                        
            UITema.mostrarInfo(this,
                    String.format("Producto añadido correctamente:\n%.4f acciones de %s\nCoste total: %.2f %s", 
                            cantidadAcciones, producto.getNombre(), costeTotal, carteraSel.getDivisa().getSimbolo()),
                    "Operación exitosa");
            dispose();
            
        } else {
            UITema.mostrarError(this,
                    "Error al guardar la operación en la base de datos.",
                    "Error");
        }
    }

}