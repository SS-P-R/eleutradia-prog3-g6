package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import es.deusto.eleutradia.domain.ClaseActivo;
import es.deusto.eleutradia.domain.ProductoFinanciero;
import es.deusto.eleutradia.domain.RegionGeografica;
import es.deusto.eleutradia.domain.TipoProducto;
import es.deusto.eleutradia.main.MainEleutradia;

public class PanelExplorar extends JPanel {
    
    private static final long serialVersionUID = 1L;
    
    // Componentes de búsqueda y filtros
    private JTextField campoBusqueda;
    private JButton botonBuscar;
    private JComboBox<String> selectTipoProducto;
    private JComboBox<String> selectClaseActivo;
    private JComboBox<String> selectRegion;
    private JComboBox<String> selectRiesgo;
    
    // Tabla de resultados
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    
    // Lista de productos (vendrá de MainEleutradia.listaProductos)
    private List<ProductoFinanciero> productosOriginales;
    private List<ProductoFinanciero> productosFiltrados;
    
    // Estilos
    private static final Color COLOR_FONDO = Color.WHITE;
    private static final Color COLOR_PRIMARIO = new Color(0, 100, 255);
    private static final Color COLOR_GRIS = new Color(100, 100, 100);
    private static final Font FONT_TITULO = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FONT_SUBTITULO = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONT_NORMAL = new Font("Segoe UI", Font.PLAIN, 12);
    
    public PanelExplorar() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(COLOR_FONDO);
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        cargarProductos();
        
        // Construir componentes
        JPanel panelSuperior = construirPanelSuperior();
        JPanel panelFiltros = construirPanelFiltros();
        JPanel panelTabla = construirPanelTabla();
        
        this.add(panelSuperior, BorderLayout.NORTH);
        this.add(panelFiltros, BorderLayout.WEST);
        this.add(panelTabla, BorderLayout.CENTER);
        
        // Cargar todos los productos inicialmente
        actualizarTabla(productosOriginales);
    }
    
    private void cargarProductos() {
        // Cargamos los productos desde MainEleutradia
        productosOriginales = new ArrayList<>();
        if (MainEleutradia.listaProductos != null) {
        	productosOriginales = new ArrayList<>(MainEleutradia.listaProductos);
        }
        productosFiltrados = new ArrayList<>(productosOriginales);
    }
    
    private JPanel construirPanelSuperior() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Título
        JLabel titulo = new JLabel("Explorar Productos Financieros");
        titulo.setFont(FONT_TITULO);
        titulo.setForeground(COLOR_PRIMARIO);
        
        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelBusqueda.setBackground(COLOR_FONDO);
        
        JLabel labelBuscar = new JLabel("Buscar:");
        labelBuscar.setFont(FONT_NORMAL);
        
        campoBusqueda = new JTextField(25);
        campoBusqueda.setFont(FONT_NORMAL);
        campoBusqueda.setPreferredSize(new Dimension(250, 30));
        
        botonBuscar = new JButton("Buscar");
        botonBuscar.setFont(FONT_NORMAL);
        botonBuscar.setBackground(COLOR_PRIMARIO);
        botonBuscar.setForeground(Color.WHITE);
        botonBuscar.setFocusPainted(false);
        
        JButton botonLimpiar = new JButton("Limpiar filtros");
        botonLimpiar.setFont(FONT_NORMAL);
        botonLimpiar.setBackground(COLOR_GRIS);
        botonLimpiar.setForeground(Color.WHITE);
        botonLimpiar.setFocusPainted(false);
        
        panelBusqueda.add(labelBuscar);
        panelBusqueda.add(campoBusqueda);
        panelBusqueda.add(botonBuscar);
        panelBusqueda.add(botonLimpiar);
        
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(panelBusqueda, BorderLayout.CENTER);
        
        // Action Listeners
        botonBuscar.addActionListener(e -> aplicarFiltros());
        campoBusqueda.addActionListener(e -> aplicarFiltros());
        botonLimpiar.addActionListener(e -> limpiarFiltros());
        
        return panel;
    }
    
    private JPanel construirPanelFiltros() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_GRIS, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setPreferredSize(new Dimension(200, 0));
        
        JLabel tituloFiltros = new JLabel("Filtros");
        tituloFiltros.setFont(FONT_SUBTITULO);
        tituloFiltros.setAlignmentX(LEFT_ALIGNMENT);
        panel.add(tituloFiltros);
        panel.add(Box.createVerticalStrut(15));
        
        // Filtro: Tipo de Producto
        panel.add(crearLabelFiltro("Tipo de Producto:"));
        panel.add(Box.createVerticalStrut(5));
        selectTipoProducto = new JComboBox<>(obtenerTiposProducto());
        selectTipoProducto.setMaximumSize(new Dimension(180, 30));
        selectTipoProducto.setAlignmentX(LEFT_ALIGNMENT);
        selectTipoProducto.addActionListener(e -> aplicarFiltros());
        panel.add(selectTipoProducto);
        panel.add(Box.createVerticalStrut(15));
        
        // Filtro: Clase de Activo
        panel.add(crearLabelFiltro("Clase de Activo:"));
        panel.add(Box.createVerticalStrut(5));
        selectClaseActivo = new JComboBox<>(obtenerClasesActivo());
        selectClaseActivo.setMaximumSize(new Dimension(180, 30));
        selectClaseActivo.setAlignmentX(LEFT_ALIGNMENT);
        selectClaseActivo.addActionListener(e -> aplicarFiltros());
        panel.add(selectClaseActivo);
        panel.add(Box.createVerticalStrut(15));
        
        // Filtro: Región
        panel.add(crearLabelFiltro("Región:"));
        panel.add(Box.createVerticalStrut(5));
        selectRegion = new JComboBox<>(obtenerRegiones());
        selectRegion.setMaximumSize(new Dimension(180, 30));
        selectRegion.setAlignmentX(LEFT_ALIGNMENT);
        selectRegion.addActionListener(e -> aplicarFiltros());
        panel.add(selectRegion);
        panel.add(Box.createVerticalStrut(15));
        
        // Filtro: Nivel de Riesgo
        panel.add(crearLabelFiltro("Nivel de Riesgo:"));
        panel.add(Box.createVerticalStrut(5));
        selectRiesgo = new JComboBox<>(new String[]{
            "Todos", "1 - Muy Bajo", "2 - Bajo", "3 - Moderado-Bajo",
            "4 - Moderado", "5 - Moderado-Alto", "6 - Alto", "7 - Muy Alto"
        });
        selectRiesgo.setMaximumSize(new Dimension(180, 30));
        selectRiesgo.setAlignmentX(LEFT_ALIGNMENT);
        selectRiesgo.addActionListener(e -> aplicarFiltros());
        panel.add(selectRiesgo);
        
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }
    
    private JLabel crearLabelFiltro(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(FONT_NORMAL);
        label.setForeground(COLOR_GRIS);
        label.setAlignmentX(LEFT_ALIGNMENT);
        return label;
    }
    
    private JPanel construirPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_FONDO);
        
        // Crear modelo de tabla
        String[] columnas = {"Código", "Nombre", "Tipo", "Región", "Riesgo", "Precio", "Divisa"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabla no editable
            }
        };
        
        tablaProductos = new JTable(modeloTabla);
        tablaProductos.setFont(FONT_NORMAL);
        tablaProductos.setRowHeight(25);
        tablaProductos.getTableHeader().setFont(FONT_SUBTITULO);
        tablaProductos.setSelectionBackground(new Color(200, 220, 255));
        
        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_GRIS, 1));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Panel inferior con botones de acción
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelAcciones.setBackground(COLOR_FONDO);
        
        JButton botonVerDetalle = new JButton("Ver Detalle");
        botonVerDetalle.setFont(FONT_NORMAL);
        botonVerDetalle.setBackground(COLOR_PRIMARIO);
        botonVerDetalle.setForeground(Color.WHITE);
        botonVerDetalle.setFocusPainted(false);
        
        JButton botonAñadirCartera = new JButton("Añadir a Cartera");
        botonAñadirCartera.setFont(FONT_NORMAL);
        botonAñadirCartera.setBackground(new Color(40, 167, 69));
        botonAñadirCartera.setForeground(Color.WHITE);
        botonAñadirCartera.setFocusPainted(false);
        
        panelAcciones.add(botonVerDetalle);
        panelAcciones.add(botonAñadirCartera);
        
        panel.add(panelAcciones, BorderLayout.SOUTH);
        
        // Action Listeners
        botonVerDetalle.addActionListener(e -> verDetalleProducto());
        botonAñadirCartera.addActionListener(e -> añadirACartera());
        
        return panel;
    }
    
    private void aplicarFiltros() {
        String textoBusqueda = campoBusqueda.getText().trim().toLowerCase();
        String tipoSeleccionado = (String) selectTipoProducto.getSelectedItem();
        String claseSeleccionada = (String) selectClaseActivo.getSelectedItem();
        String regionSeleccionada = (String) selectRegion.getSelectedItem();
        String riesgoSeleccionado = (String) selectRiesgo.getSelectedItem();
        
        productosFiltrados = new ArrayList<>();
        
        for (ProductoFinanciero producto : productosOriginales) {
            boolean cumpleFiltros = true;
            
            // Filtro de búsqueda por texto
            if (!textoBusqueda.isEmpty()) {
                if (!producto.getNombre().toLowerCase().contains(textoBusqueda) &&
                    !String.valueOf(producto.getCodigo()).contains(textoBusqueda)) {
                    cumpleFiltros = false;
                }
            }
            
            // Filtro por tipo de producto
            if (!tipoSeleccionado.equals("Todos") && cumpleFiltros) {
                if (!producto.getTipoProducto().toString().equals(tipoSeleccionado)) {
                    cumpleFiltros = false;
                }
            }
            
            // Filtro por clase de activo
            if (!claseSeleccionada.equals("Todos") && cumpleFiltros) {
                if (!producto.getTipoProducto().getClaseActivo().toString().equals(claseSeleccionada)) {
                    cumpleFiltros = false;
                }
            }
            
            // Filtro por región
            if (!regionSeleccionada.equals("Todas") && cumpleFiltros) {
                if (!producto.getRegionGeografica().toString().equals(regionSeleccionada)) {
                    cumpleFiltros = false;
                }
            }
            
            // Filtro por nivel de riesgo
            if (!riesgoSeleccionado.equals("Todos") && cumpleFiltros) {
                int nivelRiesgo = Integer.parseInt(riesgoSeleccionado.substring(0, 1));
                if (producto.getTipoProducto().getRiesgo() != nivelRiesgo) {
                    cumpleFiltros = false;
                }
            }
            
            if (cumpleFiltros) {
                productosFiltrados.add(producto);
            }
        }
        
        actualizarTabla(productosFiltrados);
    }
    
    private void limpiarFiltros() {
        campoBusqueda.setText("");
        selectTipoProducto.setSelectedIndex(0);
        selectClaseActivo.setSelectedIndex(0);
        selectRegion.setSelectedIndex(0);
        selectRiesgo.setSelectedIndex(0);
        actualizarTabla(productosOriginales);
    }
    
    private void actualizarTabla(List<ProductoFinanciero> productos) {
        modeloTabla.setRowCount(0);
        
        for (ProductoFinanciero p : productos) {
            Object[] fila = {
                p.getCodigo(),
                p.getNombre(),
                p.getTipoProducto(),
                p.getRegionGeografica(),
                p.getTipoProducto().getRiesgo(),
                String.format("%.2f", p.getValorUnitario()),
                p.getDivisa()
            };
            modeloTabla.addRow(fila);
        }
    }
    
    private void verDetalleProducto() {
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            ProductoFinanciero producto = productosFiltrados.get(filaSeleccionada);
            // Aquí podrías abrir una ventana de diálogo con los detalles
            System.out.println("Ver detalle de: " + producto.getNombre());
            // new VentanaDetalleProducto(producto);
        }
    }
    
    private void añadirACartera() {
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            ProductoFinanciero producto = productosFiltrados.get(filaSeleccionada);
            // Aquí implementarías la lógica para añadir a la cartera
            System.out.println("Añadir a cartera: " + producto.getNombre());
        }
    }
    
    private String[] obtenerTiposProducto() {
        List<String> tipos = new ArrayList<>();
        tipos.add("Todos");
        for (TipoProducto tipo : TipoProducto.values()) {
            tipos.add(tipo.toString());
        }
        return tipos.toArray(new String[0]);
    }
    
    private String[] obtenerClasesActivo() {
        List<String> clases = new ArrayList<>();
        clases.add("Todos");
        for (ClaseActivo clase : ClaseActivo.values()) {
            clases.add(clase.toString());
        }
        return clases.toArray(new String[0]);
    }
    
    private String[] obtenerRegiones() {
        List<String> regiones = new ArrayList<>();
        regiones.add("Todas");
        for (RegionGeografica region : RegionGeografica.values()) {
            regiones.add(region.toString());
        }
        return regiones.toArray(new String[0]);
    }
}