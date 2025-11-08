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
import javax.swing.table.DefaultTableCellRenderer;
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
    private JComboBox<String> selectTipoProducto;
    private JComboBox<String> selectClaseActivo;
    private JComboBox<String> selectRegion;
    private JComboBox<String> selectRiesgo;
    private JComboBox<String> selectGestora;
    private Dimension dimensionSelector = new Dimension(180, 30);
    
    private boolean verFiltros = false;
    private JPanel panelFiltros;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    
    private List<ProductoFinanciero> productosTotales;
    private List<ProductoFinanciero> productosFiltrados;
    
    // Estilos
    private static final Color MY_AZUL1 = new Color(0, 120, 255);
    private static final Color MY_AZUL2 = new Color(10, 60, 170);
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
        panelFiltros = construirPanelFiltros();
        panelFiltros.setVisible(false);
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
        JPanel mainPanelSuperior = new JPanel(new BorderLayout(10, 10));
        mainPanelSuperior.setBackground(Color.WHITE);
        mainPanelSuperior.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 1),
                BorderFactory.createEmptyBorder(5, 10, 10, 20)
            ));
        
        // Título
        JLabel titulo = new JLabel("Descubra nuestra selección de productos de inversión", JLabel.CENTER);
        titulo.setFont(FONT_TITULO);
        titulo.setForeground(MY_AZUL1);
        
        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panelBusqueda.setBackground(Color.WHITE);
        
        JLabel labelBuscar = new JLabel("Buscar:");
        labelBuscar.setFont(FONT_SUBTITULO);
        
        campoBusqueda = new JTextField();
        campoBusqueda.setFont(FONT_NORMAL);
        campoBusqueda.setPreferredSize(new Dimension(150, 30));
        
        JButton botonBuscar = new JButton("Buscar");
        botonBuscar.setFont(FONT_NORMAL);
        botonBuscar.setBackground(MY_AZUL1);
        botonBuscar.setForeground(Color.WHITE);
        botonBuscar.setBorderPainted(false);
        botonBuscar.setFocusPainted(false);
        
        JButton botonLimpiar = new JButton("Limpiar filtros");
        botonLimpiar.setFont(FONT_NORMAL);
        botonLimpiar.setBackground(MY_GRIS);
        botonLimpiar.setForeground(Color.WHITE);
        botonLimpiar.setBorderPainted(false);
        botonLimpiar.setFocusPainted(false);
        
        JButton botonVerFiltros = new JButton((verFiltros ? "Ocultar" : "Mostrar") + " filtros");
        botonVerFiltros.setFont(FONT_NORMAL);
        botonVerFiltros.setBackground(MY_GRIS);
        botonVerFiltros.setForeground(Color.WHITE);
        botonVerFiltros.setPreferredSize(botonVerFiltros.getPreferredSize());
        botonVerFiltros.setBorderPainted(false);
        botonVerFiltros.setFocusPainted(false);
        
        panelBusqueda.add(labelBuscar);
        panelBusqueda.add(campoBusqueda);
        panelBusqueda.add(botonBuscar);
        panelBusqueda.add(botonLimpiar);
        panelBusqueda.add(botonVerFiltros);
        
        mainPanelSuperior.add(titulo, BorderLayout.NORTH);
        mainPanelSuperior.add(panelBusqueda, BorderLayout.CENTER);
        
        // Action Listeners
        botonBuscar.addActionListener(e -> aplicarFiltros());
        campoBusqueda.addActionListener(e -> aplicarFiltros());
        botonLimpiar.addActionListener(e -> limpiarFiltros());
        botonVerFiltros.addActionListener(e -> {
        	verFiltros = !verFiltros;
        	botonVerFiltros.setText((verFiltros ? "Ocultar" : "Mostrar") + " filtros");
            panelFiltros.setVisible(!panelFiltros.isVisible());
            revalidate();
        });
        
        // Mouse Listener visual para entrada en el panel
        botonBuscar.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mouseEntered(MouseEvent e) {
    	        botonBuscar.setBackground(MY_AZUL2);
    		}
    		
    		@Override
    		public void mouseExited(MouseEvent e) {
    			botonBuscar.setBackground(MY_AZUL1);
    		}
        });
        
        return mainPanelSuperior;
    }
    
    private JPanel construirPanelFiltros() {
        JPanel mainPanelFiltros = new JPanel();
        mainPanelFiltros.setLayout(new BoxLayout(mainPanelFiltros, BoxLayout.Y_AXIS));
        mainPanelFiltros.setBackground(Color.WHITE);
        mainPanelFiltros.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDE, 1),
            BorderFactory.createEmptyBorder(30, 5, 5, 20)
        ));
        mainPanelFiltros.setPreferredSize(new Dimension(150, 0));
        
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
        
        // Mouse Listener visual para entrada en el panel
        mainPanelFiltros.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mouseEntered(MouseEvent e) {
    	        JPanel panel = (JPanel) e.getSource();
    	        panel.setBorder(BorderFactory.createCompoundBorder(
    	                BorderFactory.createLineBorder(Color.BLACK, 1),
    	                BorderFactory.createEmptyBorder(30, 5, 5, 20)
    	            ));
    		}
    		
    		@Override
    		public void mouseExited(MouseEvent e) {
    			JPanel panel = (JPanel) e.getSource();
    	        panel.setBorder(BorderFactory.createCompoundBorder(
    	                BorderFactory.createLineBorder(COLOR_BORDE, 1),
    	                BorderFactory.createEmptyBorder(30, 5, 5, 20)
    	            ));
    		}
        });
        
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
        JPanel mainPanelTabla = new JPanel(new BorderLayout());
        mainPanelTabla.setBackground(Color.WHITE);
        mainPanelTabla.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 1),
                BorderFactory.createEmptyBorder(20, 10, 10, 10)
            ));
        
        // Crear modelo de tabla
        String[] columnas = {"Nombre", "Región", "Precio", "Divisa", "Gestora", "Riesgo"};
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
        
        // Ajustar anchos de columna
        tablaProductos.getColumnModel().getColumn(0).setPreferredWidth(120); // Nombre
        tablaProductos.getColumnModel().getColumn(1).setPreferredWidth(100); // Región
        tablaProductos.getColumnModel().getColumn(2).setPreferredWidth(80);  // Precio
        tablaProductos.getColumnModel().getColumn(3).setPreferredWidth(40);  // Divisa
        tablaProductos.getColumnModel().getColumn(4).setPreferredWidth(150); // Gestora
        tablaProductos.getColumnModel().getColumn(5).setPreferredWidth(50);  // Riesgo
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tablaProductos.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // Divisa
        tablaProductos.getColumnModel().getColumn(5).setCellRenderer(centerRenderer); // Riesgo
        
        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        
        mainPanelTabla.add(scrollPane, BorderLayout.CENTER);
        
        // Panel inferior con botones de acción
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelAcciones.setBackground(Color.WHITE);
        
        JButton botonVerDetalle = new JButton("Ver detalle");
        botonVerDetalle.setFont(FONT_NORMAL);
        botonVerDetalle.setBackground(MY_AZUL1);
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
        
        // Mouse Listener visual para entrada en el panel
        mainPanelTabla.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mouseEntered(MouseEvent e) {
    	        JPanel panel = (JPanel) e.getSource();
    	        panel.setBorder(BorderFactory.createCompoundBorder(
    	                BorderFactory.createLineBorder(Color.BLACK, 1),
    	                BorderFactory.createEmptyBorder(20, 10, 10, 10)
    	            ));
    		}
    		
    		@Override
    		public void mouseExited(MouseEvent e) {
    			JPanel panel = (JPanel) e.getSource();
    	        panel.setBorder(BorderFactory.createCompoundBorder(
    	                BorderFactory.createLineBorder(COLOR_BORDE, 1),
    	                BorderFactory.createEmptyBorder(20, 10, 10, 10)
    	            ));
    		}
        });
        
        return mainPanelTabla;
    }
    
    private void aplicarFiltros() {
        String textoBusqueda = campoBusqueda.getText().trim().toLowerCase();
        String selectedTipo = (String) selectTipoProducto.getSelectedItem();
        String selectedClase = (String) selectClaseActivo.getSelectedItem();
        String selectedRegion = (String) selectRegion.getSelectedItem();
        String selectedGestora = (String) selectGestora.getSelectedItem();
        String selectedRiesgo = (String) selectRiesgo.getSelectedItem();
        
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
                p.getRegionGeografica().getNombre(),
                String.format("%.2f", p.getValorUnitario()),
                p.getDivisa(),
                (p.getGestora() != null ? p.getGestora().getNombreCompleto() : "---"),
                p.getTipoProducto().getStringRiesgo(),
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
}