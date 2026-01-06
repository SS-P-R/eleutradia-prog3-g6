package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import es.deusto.eleutradia.domain.Cartera;
import es.deusto.eleutradia.domain.Divisa;
import es.deusto.eleutradia.domain.PlazoRentabilidad;
import es.deusto.eleutradia.domain.ProductoFinanciero;
import es.deusto.eleutradia.domain.Usuario;
import es.deusto.eleutradia.gui.style.UITema;

import static es.deusto.eleutradia.gui.style.UITema.*;

public class VentanaVerOpciones extends JDialog {
	
	private static final long serialVersionUID = 1L;
    private Usuario usuario;
    private List<ProductoFinanciero> productosDisponibles;
    private JComboBox<String> comboCarteras;
    private JList<String> listaOpciones;
    private DefaultListModel<String> modeloLista;
    private List<CombinacionProductos> combinacionesActuales;
    
    private static final int MAX_PRODUCTOS_POR_COMBINACION = 5;
    private static final int MAX_COMBINACIONES_MOSTRAR = 20;
    
    public VentanaVerOpciones(JFrame padre, Usuario usuario, 
            List<ProductoFinanciero> productos, boolean modal) {
		super(padre, "Combinaciones de Inversión Sugeridas", modal);
		this.usuario = usuario;
		this.productosDisponibles = productos;
		this.combinacionesActuales = new ArrayList<>();
		
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
        
        panelPrincipal.add(construirPanelSuperior(), BorderLayout.NORTH);
        panelPrincipal.add(construirPanelCentral(), BorderLayout.CENTER);
        panelPrincipal.add(construirPanelInferior(), BorderLayout.SOUTH);
        
        this.add(panelPrincipal);
    }
    
    private JPanel construirPanelSuperior() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(MAIN_PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MAIN_BORDE, 1),
            new EmptyBorder(15, 20, 15, 20)));
        
        JLabel titulo = new JLabel("Mis opciones de inversión");
        titulo.setFont(TITULO_GRANDE);
        titulo.setForeground(AZUL_CLARO);
        titulo.setAlignmentX(LEFT_ALIGNMENT);
        
        JLabel subtitulo = new JLabel("Explore diferentes combinaciones de productos según el saldo disponible.");
        subtitulo.setFont(CUERPO_MEDIO);
        subtitulo.setForeground(GRIS_MEDIO);
        subtitulo.setAlignmentX(LEFT_ALIGNMENT);
        
        panel.add(titulo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(subtitulo);
        panel.add(Box.createVerticalStrut(10));
        
        JSeparator separador = new JSeparator(JSeparator.HORIZONTAL);
        separador.setAlignmentX(LEFT_ALIGNMENT);
        separador.setForeground(GRIS_MEDIO);
        panel.add(separador);
        panel.add(Box.createVerticalStrut(10));
        
        JPanel panelSelector = new JPanel(new GridBagLayout());
        panelSelector.setBackground(MAIN_PANEL);
        panelSelector.setAlignmentX(LEFT_ALIGNMENT);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.0;
        JLabel labelCartera = new JLabel("Seleccione una cartera:");
        labelCartera.setFont(SUBTITULO_MEDIO);
        labelCartera.setForeground(GRIS_OSCURO);
        panelSelector.add(labelCartera, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        comboCarteras = new JComboBox<>();
        comboCarteras.setFont(CUERPO_MEDIO);
        comboCarteras.setPreferredSize(new Dimension(400, 40));
        UITema.personalizarComboBox(comboCarteras);
        
        comboCarteras.addItem("Seleccione una cartera...");
        for (Cartera c : usuario.getCarteras()) {
            comboCarteras.addItem(String.format("%s (Saldo: %.2f %s)", 
                c.getNombre(), c.getSaldo(), c.getDivisa().getSimbolo()));
        }
        panelSelector.add(comboCarteras, gbc);
        
        gbc.gridx = 2; gbc.weightx = 0.0;
        JButton botonGenerar = new JButton("Generar opciones");
        botonGenerar.setFont(CUERPO_MEDIO);
        botonGenerar.setBackground(NARANJA_CLARO);
        botonGenerar.setForeground(Color.WHITE);
        botonGenerar.setPreferredSize(new Dimension(150, 35));
        botonGenerar.setBorderPainted(false);
        botonGenerar.setContentAreaFilled(false);
        botonGenerar.setOpaque(true);
        botonGenerar.setFocusPainted(false);
        botonGenerar.addActionListener(e -> generarCombinaciones());
        botonGenerar.addMouseListener(myAdapterNaranja);
        panelSelector.add(botonGenerar, gbc);
        
        panel.add(panelSelector);
        
        return panel;
    }
    
    private JPanel construirPanelCentral() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(MAIN_PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MAIN_BORDE, 1),
            new EmptyBorder(15, 15, 15, 10)));
        
        JLabel tituloLista = new JLabel("Combinaciones sugeridas ordenadas por diversificación y rentabilidad:");
        tituloLista.setFont(SUBTITULO_MEDIO);
        tituloLista.setForeground(AZUL_OSCURO);
        
        modeloLista = new DefaultListModel<>();
        modeloLista.addElement("Seleccione una cartera para ver las opciones de inversión disponibles.");
        
        listaOpciones = new JList<>(modeloLista);
        listaOpciones.setFont(CUERPO_MEDIO);
        listaOpciones.setBackground(MAIN_FONDO);
        listaOpciones.setOpaque(true);
        listaOpciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(listaOpciones);
        scrollPane.setBorder(BorderFactory.createLineBorder(MAIN_BORDE, 1));
        scrollPane.setPreferredSize(new Dimension(0, 350));
        scrollPane.getVerticalScrollBar().setUI(personalizarScrollBarUI());
        
        panel.add(tituloLista, BorderLayout.NORTH);
        panel.add(Box.createVerticalStrut(10), BorderLayout.AFTER_LINE_ENDS);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel construirPanelInferior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panel.setBackground(MAIN_FONDO);
        
        JButton botonCerrar = new JButton("Cerrar");
        botonCerrar.setFont(CUERPO_MEDIO);
        botonCerrar.setBackground(GRIS_MEDIO);
        botonCerrar.setForeground(Color.WHITE);
        botonCerrar.setPreferredSize(new Dimension(90, 30));
        botonCerrar.setBorderPainted(false);
        botonCerrar.setContentAreaFilled(false);
        botonCerrar.setOpaque(true);
        botonCerrar.setFocusPainted(false);
        botonCerrar.addActionListener(e -> dispose());
        botonCerrar.addMouseListener(myAdapterGris);
        
        panel.add(botonCerrar);
        return panel;
    }

    private void generarCombinaciones() {
        int indiceCartera = comboCarteras.getSelectedIndex();
        
        if (indiceCartera <= 0) {
            UITema.mostrarWarning(this,
                    "Por favor, seleccione una cartera para generar las opciones.",
                    "Cartera no seleccionada");
            return;
        }
        
        Cartera carteraSeleccionada = usuario.getCarteras().get(indiceCartera - 1);
        
        if (carteraSeleccionada.getSaldo() <= 0) {
            UITema.mostrarWarning(this,
                    "La cartera seleccionada no tiene saldo disponible.",
                    "Saldo insuficiente");
            modeloLista.clear();
            modeloLista.addElement("La cartera no tiene saldo disponible para invertir.");
            return;
        }
        
        modeloLista.clear();
        modeloLista.addElement("Generando combinaciones... Por favor, espere.");
                
        combinacionesActuales = new ArrayList<>();
        
        List<ProductoFinanciero> productosCompatibles = filtrarProductosCompatibles(
                productosDisponibles, carteraSeleccionada, carteraSeleccionada.getSaldo());
        
        if (productosCompatibles.isEmpty()) {
            modeloLista.clear();
            modeloLista.addElement("No hay productos compatibles con el saldo disponible.");
            return;
        }
        
        generarCombinacionesRecursivo(new ArrayList<>(), productosCompatibles, 
        		carteraSeleccionada.getSaldo(), 0, carteraSeleccionada.getDivisa());
        
        ordenarCombinaciones();
                
        mostrarCombinaciones(carteraSeleccionada);

    }
    
    private List<ProductoFinanciero> filtrarProductosCompatibles(
            List<ProductoFinanciero> productos, Cartera cartera, double presupuesto) {
        
        List<ProductoFinanciero> compatibles = new ArrayList<>();
        Divisa divisaCartera = cartera.getDivisa();
        
        for (ProductoFinanciero p : productos) {
            double importeMinimo = p.getTipoProducto().getImporteMin();
            BigDecimal importeMinimoConvertido = p.getDivisa().convertirA(
                    BigDecimal.valueOf(importeMinimo), divisaCartera);
            
            if (importeMinimoConvertido.doubleValue() <= presupuesto) {
                compatibles.add(p);
            }
        }
        
        return compatibles;
    }
    
    private void generarCombinacionesRecursivo(
            List<ProductoFinanciero> combinacionActual,
            List<ProductoFinanciero> productosDisponibles,
            double presupuestoRestante,
            int indiceInicio,
            Divisa divisaCartera) {
        
        if (combinacionesActuales.size() >= MAX_COMBINACIONES_MOSTRAR * 2) {
            return;
        }
        
        if (!combinacionActual.isEmpty() && combinacionActual.size() <= MAX_PRODUCTOS_POR_COMBINACION) {
            double costoTotal = calcularCostoTotal(combinacionActual, divisaCartera);
            
            if (costoTotal > 0 && costoTotal <= presupuestoRestante) {
                CombinacionProductos combinacion = new CombinacionProductos(
                        new ArrayList<>(combinacionActual), costoTotal);
                combinacionesActuales.add(combinacion);
            }
        }
        
        if (combinacionActual.size() >= MAX_PRODUCTOS_POR_COMBINACION) {
            return;
        }
        
        for (int i = indiceInicio; i < productosDisponibles.size(); i++) {
            ProductoFinanciero producto = productosDisponibles.get(i);
            
            double importeMinimo = producto.getTipoProducto().getImporteMin();
            BigDecimal importeMinimoConvertido = producto.getDivisa().convertirA(
                    BigDecimal.valueOf(importeMinimo), divisaCartera);
            
            if (importeMinimoConvertido.doubleValue() <= presupuestoRestante) {
                combinacionActual.add(producto);
                
                generarCombinacionesRecursivo(
                        combinacionActual,
                        productosDisponibles,
                        presupuestoRestante - importeMinimoConvertido.doubleValue(),
                        i + 1,
                        divisaCartera);
                
                combinacionActual.remove(combinacionActual.size() - 1);
            }
        }
    }
    
    private double calcularCostoTotal(List<ProductoFinanciero> productos, Divisa divisaCartera) {
        double total = 0.0;
        for (ProductoFinanciero p : productos) {
            double importeMinimo = p.getTipoProducto().getImporteMin();
            BigDecimal importeConvertido = p.getDivisa().convertirA(
                    BigDecimal.valueOf(importeMinimo), divisaCartera);
            total += importeConvertido.doubleValue();
        }
        return total;
    }
    
    // Comparator con lambda para ordenar combinaciones por puntuación descendente
    private void ordenarCombinaciones() {
        combinacionesActuales.sort(
            (c1, c2) -> Double.compare(c2.calcularScore(), c1.calcularScore())
        );
    }
    
    private void mostrarCombinaciones(Cartera cartera) {
        modeloLista.clear();
        
        if (combinacionesActuales.isEmpty()) {
            modeloLista.addElement("No se encontraron combinaciones viables con el saldo disponible.");
            return;
        }
        
        int limite = Math.min(MAX_COMBINACIONES_MOSTRAR, combinacionesActuales.size());
        
        for (int i = 0; i < limite; i++) {
            CombinacionProductos comb = combinacionesActuales.get(i);
            String descripcion = String.format(
            	    "<html><b>Opción %d</b><br>" +
            	    "Productos: %s<br>" +
            	    "Coste total: %.2f %s<br>" +
            	    "Saldo restante: %.2f %s</html>",
            	    (i + 1),
            	    comb.getDescripcion(),
            	    comb.getCostoTotal(),
            	    cartera.getDivisa().getSimbolo(),
            	    cartera.getSaldo() - comb.getCostoTotal(),
            	    cartera.getDivisa().getSimbolo()
            	);
            
            modeloLista.addElement(descripcion);
        }
        
        if (combinacionesActuales.size() > MAX_COMBINACIONES_MOSTRAR) {
            modeloLista.addElement("");
            modeloLista.addElement(String.format("... y %d combinaciones más", 
                    combinacionesActuales.size() - MAX_COMBINACIONES_MOSTRAR));
        }
    }
    
    private static class CombinacionProductos {
        private List<ProductoFinanciero> productos;
        private double costoTotal;
        
        public CombinacionProductos(List<ProductoFinanciero> productos, 
                                    double costoTotal) {
            this.productos = productos;
            this.costoTotal = costoTotal;
        }
        
        public double getCostoTotal() {
            return costoTotal;
        }
        
        public String getDescripcion() {
        	StringBuilder sb = new StringBuilder();
            for (int i = 0; i < productos.size(); i++) {
                sb.append(productos.get(i).getNombre());
                if (i < productos.size() - 1) {
                    sb.append(" + ");
                }
            }
            return sb.toString();
        }
        
        public double calcularScore() {
            double scoreDiversificacion = productos.size() * 10.0;
            
            double rentabilidadPromedio = 0.0;
            int contadorRentabilidades = 0;
            
            for (ProductoFinanciero p : productos) {
                if (p.getRentabilidades().get(
                        PlazoRentabilidad.YTD) != null) {
                    rentabilidadPromedio += p.getRentabilidades().get(
                            PlazoRentabilidad.YTD).doubleValue();
                    contadorRentabilidades++;
                }
            }
            
            if (contadorRentabilidades > 0) {
                rentabilidadPromedio /= contadorRentabilidades;
            }
            
            double scoreRiesgo = 0.0;
            for (ProductoFinanciero p : productos) {
                int riesgo = p.getTipoProducto().getRiesgo();
                scoreRiesgo += (8 - riesgo);
            }
            scoreRiesgo = scoreRiesgo / productos.size() * 3;
            
            long regionesUnicas = productos.stream()
                    .map(p -> p.getRegionGeografica())
                    .distinct()
                    .count();
            double scoreRegion = regionesUnicas * 5.0;
            
            long tiposUnicos = productos.stream()
                    .map(p -> p.getTipoProducto().getClaseActivo())
                    .distinct()
                    .count();
            double scoreTipo = tiposUnicos * 7.0;
            
            return scoreDiversificacion + rentabilidadPromedio + 
                   scoreRiesgo + scoreRegion + scoreTipo;
        }
    }
    
}
