package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingUtilities;

import es.deusto.eleutradia.domain.ClaseActivo;
import es.deusto.eleutradia.domain.Gestora;
import es.deusto.eleutradia.domain.ProductoFinanciero;
import es.deusto.eleutradia.domain.RegionGeografica;
import es.deusto.eleutradia.domain.TipoProducto;
import es.deusto.eleutradia.domain.Usuario;
import es.deusto.eleutradia.main.MainEleutradia;

public class PanelExplorar extends JPanel {
    
    private static final long serialVersionUID = 1L;
    
    private Usuario usuario;
    private JTextField campoBusqueda;
    private JButton botonBuscar;
    private JComboBox<String> selectTipoProducto;
    private JComboBox<String> selectClaseActivo;
    private JComboBox<String> selectRegion;
    private JComboBox<String> selectRiesgo;
    private JComboBox<String> selectGestora;
    private Dimension dimensionSelector = new Dimension(180, 30);
    
    private JPanel mainPanelSuperior;
    private JPanel mainPanelFiltros;
    private JPanel mainPanelTabla;
    
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    
    private List<ProductoFinanciero> productosTotales;
    private List<ProductoFinanciero> productosFiltrados;
    
    // Estilos
    private static final Color MY_AZUL = new Color(0, 100, 255);
    private static final Color MY_GRIS = new Color(100, 100, 100);
    private static final Color MY_VERDE = new Color(40, 167, 69);
    private static final Color COLOR_FONDO_PRINCIPAL = new Color(248, 249, 250);
    private static final Color COLOR_BORDE = new Color(222, 226, 230);
    private static final Font FONT_TITULO = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FONT_SUBTITULO = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONT_NORMAL = new Font("Segoe UI", Font.PLAIN, 12);
    
    public PanelExplorar(Usuario usuario) {
    	this.usuario = usuario;
    	
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(COLOR_FONDO_PRINCIPAL);
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        cargarProductos();
        
        JPanel panelSuperior = construirPanelSuperior();
        JPanel panelFiltros = construirPanelFiltros();
        JPanel panelTabla = construirPanelTabla();
        
        this.add(panelSuperior, BorderLayout.NORTH);
        this.add(panelFiltros, BorderLayout.WEST);
        this.add(panelTabla, BorderLayout.CENTER);

        actualizarTabla(productosTotales);
        
    }
    
    private void cargarProductos() {
        productosTotales = new ArrayList<>();
        if (MainEleutradia.listaProductos != null) {
        	productosTotales = new ArrayList<>(MainEleutradia.listaProductos);
        }
        productosFiltrados = new ArrayList<>(productosTotales);
    }
    
    private JPanel construirPanelSuperior() {
        mainPanelSuperior = new JPanel(new BorderLayout(10, 10));
        mainPanelSuperior.setBackground(Color.WHITE);
        mainPanelSuperior.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 1),
                BorderFactory.createEmptyBorder(5, 10, 10, 20)
            ));
        
        // Título
        JLabel titulo = new JLabel("Descubra nuestra selección de productos de inversión", JLabel.CENTER);
        titulo.setFont(FONT_TITULO);
        titulo.setForeground(MY_AZUL);
        
        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelBusqueda.setBackground(Color.WHITE);
        
        JLabel labelBuscar = new JLabel("Buscar:");
        labelBuscar.setFont(FONT_SUBTITULO);
        
        campoBusqueda = new JTextField(25);
        campoBusqueda.setFont(FONT_NORMAL);
        campoBusqueda.setPreferredSize(new Dimension(250, 30));
        
        botonBuscar = new JButton("Buscar");
        botonBuscar.setFont(FONT_NORMAL);
        botonBuscar.setBackground(MY_AZUL);
        botonBuscar.setForeground(Color.WHITE);
        botonBuscar.setFocusPainted(false);
        
        JButton botonLimpiar = new JButton("Limpiar filtros");
        botonLimpiar.setFont(FONT_NORMAL);
        botonLimpiar.setBackground(MY_GRIS);
        botonLimpiar.setForeground(Color.WHITE);
        botonLimpiar.setFocusPainted(false);
        
        panelBusqueda.add(labelBuscar);
        panelBusqueda.add(campoBusqueda);
        panelBusqueda.add(botonBuscar);
        panelBusqueda.add(botonLimpiar);
        
        mainPanelSuperior.add(titulo, BorderLayout.NORTH);
        mainPanelSuperior.add(panelBusqueda, BorderLayout.CENTER);
        
        // Action Listeners
        botonBuscar.addActionListener(e -> aplicarFiltros());
        campoBusqueda.addActionListener(e -> aplicarFiltros());
        botonLimpiar.addActionListener(e -> limpiarFiltros());
        
        return mainPanelSuperior;
    }
    
    private JPanel construirPanelFiltros() {
        mainPanelFiltros = new JPanel();
        mainPanelFiltros.setLayout(new BoxLayout(mainPanelFiltros, BoxLayout.Y_AXIS));
        mainPanelFiltros.setBackground(Color.WHITE);
        mainPanelFiltros.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDE, 1),
            BorderFactory.createEmptyBorder(30, 10, 10, 20)
        ));
        mainPanelFiltros.setPreferredSize(new Dimension(200, 0));
        
        JLabel tituloFiltros = new JLabel("- FILTROS -");
        tituloFiltros.setFont(FONT_SUBTITULO);
        tituloFiltros.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        tituloFiltros.setHorizontalAlignment(JLabel.CENTER);
        tituloFiltros.setMaximumSize(new Dimension(Integer.MAX_VALUE, tituloFiltros.getPreferredSize().height));
        mainPanelFiltros.add(tituloFiltros);
        mainPanelFiltros.add(Box.createVerticalStrut(15));
        
        // Filtro: Tipo de Producto
        mainPanelFiltros.add(crearLabelFiltro("Tipo de Producto:"));
        mainPanelFiltros.add(Box.createVerticalStrut(5));
        selectTipoProducto = new JComboBox<>(obtenerTiposProducto());
        selectTipoProducto.setMaximumSize(dimensionSelector);
        selectTipoProducto.setAlignmentX(LEFT_ALIGNMENT);
        selectTipoProducto.addActionListener(e -> aplicarFiltros());
        mainPanelFiltros.add(selectTipoProducto);
        mainPanelFiltros.add(Box.createVerticalStrut(15));
        
        // Filtro: Clase de Activo
        mainPanelFiltros.add(crearLabelFiltro("Clase de Activo:"));
        mainPanelFiltros.add(Box.createVerticalStrut(5));
        selectClaseActivo = new JComboBox<>(obtenerClasesActivo());
        selectClaseActivo.setMaximumSize(dimensionSelector);
        selectClaseActivo.setAlignmentX(LEFT_ALIGNMENT);
        selectClaseActivo.addActionListener(e -> aplicarFiltros());
        mainPanelFiltros.add(selectClaseActivo);
        mainPanelFiltros.add(Box.createVerticalStrut(15));
        
        // Filtro: Región
        mainPanelFiltros.add(crearLabelFiltro("Región:"));
        mainPanelFiltros.add(Box.createVerticalStrut(5));
        selectRegion = new JComboBox<>(obtenerRegiones());
        selectRegion.setMaximumSize(dimensionSelector);
        selectRegion.setAlignmentX(LEFT_ALIGNMENT);
        selectRegion.addActionListener(e -> aplicarFiltros());
        mainPanelFiltros.add(selectRegion);
        mainPanelFiltros.add(Box.createVerticalStrut(15));
        
        // Filtro: Nivel de Riesgo
        mainPanelFiltros.add(crearLabelFiltro("Nivel de Riesgo:"));
        mainPanelFiltros.add(Box.createVerticalStrut(5));
        selectRiesgo = new JComboBox<>(new String[]{
            "Todo", "1 - Muy Bajo", "2 - Bajo", "3 - Moderado-Bajo",
            "4 - Moderado", "5 - Moderado-Alto", "6 - Alto", "7 - Muy Alto"
        });
        selectRiesgo.setMaximumSize(dimensionSelector);
        selectRiesgo.setAlignmentX(LEFT_ALIGNMENT);
        selectRiesgo.addActionListener(e -> aplicarFiltros());
        mainPanelFiltros.add(selectRiesgo);
        mainPanelFiltros.add(Box.createVerticalStrut(15));
        
        // Filtro: Gestora
        mainPanelFiltros.add(crearLabelFiltro("Gestora:"));
        mainPanelFiltros.add(Box.createVerticalStrut(5));
        selectGestora = new JComboBox<>(obtenerGestoras());
        selectGestora.setMaximumSize(dimensionSelector);
        selectGestora.setAlignmentX(LEFT_ALIGNMENT);
        selectGestora.addActionListener(e -> aplicarFiltros());
        mainPanelFiltros.add(selectGestora);
        
        mainPanelFiltros.add(Box.createVerticalGlue());
        
        return mainPanelFiltros;
    }
    
    private JLabel crearLabelFiltro(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(FONT_NORMAL);
        label.setForeground(MY_GRIS);
        label.setAlignmentX(LEFT_ALIGNMENT);
        return label;
    }
    
    private JPanel construirPanelTabla() {
        mainPanelTabla = new JPanel(new BorderLayout());
        mainPanelTabla.setBackground(Color.WHITE);
        mainPanelTabla.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 1),
                BorderFactory.createEmptyBorder(20, 10, 10, 10)
            ));
        
        // Crear modelo de tabla
        String[] columnas = {"Nombre", "Tipo", "Región", "Riesgo", "Precio", "Divisa", "Gestora"};
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
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        
        mainPanelTabla.add(scrollPane, BorderLayout.CENTER);
        
        // Panel inferior con botones de acción
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelAcciones.setBackground(Color.WHITE);
        
        JButton botonVerDetalle = new JButton("Ver detalle");
        botonVerDetalle.setFont(FONT_NORMAL);
        botonVerDetalle.setBackground(MY_AZUL);
        botonVerDetalle.setForeground(Color.WHITE);
        botonVerDetalle.setFocusPainted(false);
        
        JButton botonAñadirCartera = new JButton("Añadir a cartera");
        botonAñadirCartera.setFont(FONT_NORMAL);
        botonAñadirCartera.setBackground(MY_VERDE);
        botonAñadirCartera.setForeground(Color.WHITE);
        botonAñadirCartera.setFocusPainted(false);
        
        panelAcciones.add(botonVerDetalle);
        panelAcciones.add(botonAñadirCartera);
        
        mainPanelTabla.add(panelAcciones, BorderLayout.SOUTH);
        
        // Action Listeners
        botonVerDetalle.addActionListener(e -> verDetalleProducto());
        botonAñadirCartera.addActionListener(e -> anadirACartera());
        
        return mainPanelTabla;
    }
    
    private void aplicarFiltros() {
        String textoBusqueda = campoBusqueda.getText().trim().toLowerCase();
        String selectedTipo = (String) selectTipoProducto.getSelectedItem();
        String selectedClase = (String) selectClaseActivo.getSelectedItem();
        String selectedRegion = (String) selectRegion.getSelectedItem();
        String selectedRiesgo = (String) selectRiesgo.getSelectedItem();
        String selectedGestora = (String) selectGestora.getSelectedItem();
        
        productosFiltrados = new ArrayList<>();
        
        for (ProductoFinanciero producto : productosTotales) {
            boolean cumpleFiltros = true;
            
            // Filtro de búsqueda por texto
            if (!textoBusqueda.isEmpty()) {
                if (!producto.getNombre().toLowerCase().contains(textoBusqueda) &&
                    !String.valueOf(producto.getCodigo()).contains(textoBusqueda)) {
                    cumpleFiltros = false;
                }
            }
            
            // Filtro por tipo de producto
            if (!selectedTipo.equals("Todo") && cumpleFiltros) {
                if (!producto.getTipoProducto().toString().equals(selectedTipo)) {
                    cumpleFiltros = false;
                }
            }
            
            // Filtro por clase de activo
            if (!selectedClase.equals("Todo") && cumpleFiltros) {
                if (!producto.getTipoProducto().getClaseActivo().toString().equals(selectedClase)) {
                    cumpleFiltros = false;
                }
            }
            
            // Filtro por región
            if (!selectedRegion.equals("Todo") && cumpleFiltros) {
                if (!producto.getRegionGeografica().toString().equals(selectedRegion)) {
                    cumpleFiltros = false;
                }
            }
            
            // Filtro por nivel de riesgo
            if (!selectedRiesgo.equals("Todo") && cumpleFiltros) {
                int nivelRiesgo = Integer.parseInt(selectedRiesgo.substring(0, 1));
                if (producto.getTipoProducto().getRiesgo() != nivelRiesgo) {
                    cumpleFiltros = false;
                }
            }
            
            // Filtro por gestora
            if (!selectedGestora.equals("Todo") && cumpleFiltros) {
            	String nombreGestora = (producto.getGestora() != null) ? producto.getGestora().getNombreCompleto() : "---";
                if (!(nombreGestora.equals(selectedGestora))) {
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
        actualizarTabla(productosTotales);
    }
    
    private void actualizarTabla(List<ProductoFinanciero> productos) {
        modeloTabla.setRowCount(0);
        for (ProductoFinanciero p : productos) {
            Object[] fila = {
                p.getNombre(),
                p.getTipoProducto(),
                p.getRegionGeografica(),
                p.getTipoProducto().getRiesgo(),
                String.format("%.2f", p.getValorUnitario()),
                p.getDivisa(),
                (p.getGestora() != null ? p.getGestora().getNombreCompleto() : "---")
            };
            modeloTabla.addRow(fila);
        }
    }
    
    private void verDetalleProducto() {
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            ProductoFinanciero producto = productosFiltrados.get(filaSeleccionada);
            System.out.println("Viendo detalle de: " + producto.getNombre());
            //IAG (ChatGPT)
            //SIN CAMBIOS
            JFrame framePadre = (JFrame) SwingUtilities.getWindowAncestor(this);
            new VentanaDetalleProducto(framePadre, producto, true);
            //END-IAG
        } else {
        	JOptionPane.showMessageDialog(this,
        			"Por favor, seleccione un producto de la tabla para ver los detalles.",
        			"Selección necesaria", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void anadirACartera() {
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            ProductoFinanciero producto = productosFiltrados.get(filaSeleccionada);
            JFrame framePadre = (JFrame) SwingUtilities.getWindowAncestor(this);
            new VentanaAnadirACartera(framePadre, usuario, producto, true);
        }
    }
    
    private String[] obtenerTiposProducto() {
        List<String> tipos = new ArrayList<>();
        tipos.add("Todo");
        for (TipoProducto tipo : TipoProducto.values()) {
            tipos.add(tipo.toString());
        }
        return tipos.toArray(new String[0]);
    }
    
    private String[] obtenerClasesActivo() {
        List<String> clases = new ArrayList<>();
        clases.add("Todo");
        for (ClaseActivo clase : ClaseActivo.values()) {
            clases.add(clase.toString());
        }
        return clases.toArray(new String[0]);
    }
    
    private String[] obtenerRegiones() {
        List<String> regiones = new ArrayList<>();
        regiones.add("Todo");
        for (RegionGeografica region : RegionGeografica.values()) {
            regiones.add(region.toString());
        }
        return regiones.toArray(new String[0]);
    }
    
    private String[] obtenerGestoras() {
        Set<String> gestoras = new LinkedHashSet<>();
        gestoras.add("Todo");
        for (ProductoFinanciero p : MainEleutradia.listaProductos) {
        	Gestora g = p.getGestora();
        	if (g != null) {
        		gestoras.add(g.getNombreCompleto());
        	}
        }
        return gestoras.toArray(new String[0]);
    }
    
//    MouseAdapter listenerPanel = new MouseAdapter() {
//		@Override
//		public void mouseEntered(MouseEvent e) {
//	        JPanel panel = (JPanel) e.getSource();
//	        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
//		}
//		
//		@Override
//		public void mouseExited(MouseEvent e) {
//			JPanel panel = (JPanel) e.getSource();
//	        panel.setBorder(BorderFactory.createLineBorder(COLOR_BORDE, 1));
//		}
//    };
}