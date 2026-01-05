package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.time.Year;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingUtilities;

import es.deusto.eleutradia.domain.ClaseActivo;
import es.deusto.eleutradia.domain.Gestora;
import es.deusto.eleutradia.domain.PlazoRentabilidad;
import es.deusto.eleutradia.domain.ProductoFinanciero;
import es.deusto.eleutradia.domain.RegionGeografica;
import es.deusto.eleutradia.domain.TipoProducto;
import es.deusto.eleutradia.domain.Usuario;
import es.deusto.eleutradia.gui.style.UITema;
import es.deusto.eleutradia.main.MainEleutradia;

import static es.deusto.eleutradia.gui.style.UITema.*;

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
    
    public PanelExplorar(Usuario usuario) {
    	this.usuario = usuario;
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(MAIN_FONDO);
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
                BorderFactory.createLineBorder(MAIN_BORDE, 1),
                BorderFactory.createEmptyBorder(5, 10, 10, 20)
            ));
        
        // Título
        JLabel titulo = new JLabel("Descubra nuestra selección de productos de inversión", JLabel.CENTER);
        titulo.setFont(TITULO_MEDIO);
        titulo.setForeground(AZUL_OSCURO);
        
        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panelBusqueda.setBackground(Color.WHITE);
        
        JLabel labelBuscar = new JLabel("Buscar:");
        labelBuscar.setFont(SUBTITULO_MEDIO);
        
        campoBusqueda = new JTextField();
        campoBusqueda.setFont(CUERPO_PEQUENO);
        campoBusqueda.setPreferredSize(new Dimension(150, 30));
        
        JButton botonBuscar = new JButton("Buscar");
        botonBuscar.setFont(CUERPO_PEQUENO);
        botonBuscar.setBackground(AZUL_CLARO);
        botonBuscar.setForeground(Color.WHITE);
        botonBuscar.setBorderPainted(false);
        botonBuscar.setContentAreaFilled(false);
        botonBuscar.setOpaque(true);
        botonBuscar.setFocusPainted(false);
        
        JButton botonLimpiar = new JButton("Limpiar filtros");
        botonLimpiar.setFont(CUERPO_PEQUENO);
        botonLimpiar.setBackground(GRIS_MEDIO);
        botonLimpiar.setForeground(Color.WHITE);
        botonLimpiar.setBorderPainted(false);
        botonLimpiar.setContentAreaFilled(false);
        botonLimpiar.setOpaque(true);
        botonLimpiar.setFocusPainted(false);
        
        JButton botonVerFiltros = new JButton((verFiltros ? "Ocultar" : "Mostrar") + " filtros");
        botonVerFiltros.setFont(CUERPO_PEQUENO);
        botonVerFiltros.setBackground(ROSA_CLARO);
        botonVerFiltros.setForeground(Color.WHITE);
        botonVerFiltros.setPreferredSize(botonVerFiltros.getPreferredSize());
        botonVerFiltros.setBorderPainted(false);
        botonVerFiltros.setContentAreaFilled(false);
        botonVerFiltros.setOpaque(true);
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
        
        // Mouse Listener visual para entrada en los botones
        botonBuscar.addMouseListener(myAdapterAzul);
        botonLimpiar.addMouseListener(myAdapterGris);
        botonVerFiltros.addMouseListener(myAdapterRosa);
        
        return mainPanelSuperior;
    }
    
    private JPanel construirPanelFiltros() {
        JPanel mainPanelFiltros = new JPanel();
        mainPanelFiltros.setLayout(new BoxLayout(mainPanelFiltros, BoxLayout.Y_AXIS));
        mainPanelFiltros.setBackground(Color.WHITE);
        mainPanelFiltros.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MAIN_BORDE, 1),
            BorderFactory.createEmptyBorder(30, 5, 5, 20)
        ));
        mainPanelFiltros.setPreferredSize(new Dimension(150, 0));
        
        JLabel tituloFiltros = new JLabel("- FILTROS -");
        tituloFiltros.setFont(SUBTITULO_GRANDE);
        tituloFiltros.setForeground(AZUL_OSCURO);
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
        selectTipoProducto.setAlignmentX(JComboBox.CENTER_ALIGNMENT);
        selectTipoProducto.setAlignmentX(LEFT_ALIGNMENT);
        selectTipoProducto.addActionListener(e -> aplicarFiltros());
        UITema.personalizarComboBox(selectTipoProducto);
        aplicarTooltipPorItem(selectTipoProducto);
        mainPanelFiltros.add(selectTipoProducto);
        mainPanelFiltros.add(Box.createVerticalStrut(15));
        
        // Filtro: Clase de Activo
        mainPanelFiltros.add(crearLabelFiltro("Clase de Activo:"));
        mainPanelFiltros.add(Box.createVerticalStrut(5));
        selectClaseActivo = new JComboBox<>(obtenerClasesActivo());
        selectClaseActivo.setMaximumSize(dimensionSelector);
        selectClaseActivo.setAlignmentX(JComboBox.CENTER_ALIGNMENT);
        selectClaseActivo.setAlignmentX(LEFT_ALIGNMENT);
        selectClaseActivo.addActionListener(e -> aplicarFiltros());
        UITema.personalizarComboBox(selectClaseActivo);
        aplicarTooltipPorItem(selectClaseActivo);
        mainPanelFiltros.add(selectClaseActivo);
        mainPanelFiltros.add(Box.createVerticalStrut(15));
        
        // Filtro: Región
        mainPanelFiltros.add(crearLabelFiltro("Región:"));
        mainPanelFiltros.add(Box.createVerticalStrut(5));
        selectRegion = new JComboBox<>(obtenerRegiones());
        selectRegion.setMaximumSize(dimensionSelector);
        selectRegion.setAlignmentX(JComboBox.CENTER_ALIGNMENT);
        selectRegion.setAlignmentX(LEFT_ALIGNMENT);
        selectRegion.addActionListener(e -> aplicarFiltros());
        UITema.personalizarComboBox(selectRegion);
        aplicarTooltipPorItem(selectRegion);
        mainPanelFiltros.add(selectRegion);
        mainPanelFiltros.add(Box.createVerticalStrut(15));
        
        // Filtro: Nivel de Riesgo
        mainPanelFiltros.add(crearLabelFiltro("Nivel de Riesgo:"));
        mainPanelFiltros.add(Box.createVerticalStrut(5));
        selectRiesgo = new JComboBox<>(new String[]{
            "Todo", "1-Muy Bajo", "2-Bajo", "3-Moderado",
            "4-Medio", "5-Elevado", "6-Alto", "7-Muy Alto"
        });
        selectRiesgo.setMaximumSize(dimensionSelector);
        selectRiesgo.setAlignmentX(JComboBox.CENTER_ALIGNMENT);
        selectRiesgo.setAlignmentX(LEFT_ALIGNMENT);
        selectRiesgo.addActionListener(e -> aplicarFiltros());
        UITema.personalizarComboBox(selectRiesgo);
        aplicarTooltipPorItem(selectRiesgo);
        mainPanelFiltros.add(selectRiesgo);
        mainPanelFiltros.add(Box.createVerticalStrut(15));
        
        // Filtro: Gestora
        mainPanelFiltros.add(crearLabelFiltro("Gestora:"));
        mainPanelFiltros.add(Box.createVerticalStrut(5));
        selectGestora = new JComboBox<>(obtenerGestoras());
        selectGestora.setMaximumSize(dimensionSelector);
        selectGestora.setAlignmentX(JComboBox.CENTER_ALIGNMENT);
        selectGestora.setAlignmentX(LEFT_ALIGNMENT);
        selectGestora.addActionListener(e -> aplicarFiltros());
        UITema.personalizarComboBox(selectGestora);
        aplicarTooltipPorItem(selectGestora);
        mainPanelFiltros.add(selectGestora);
        
        mainPanelFiltros.add(Box.createVerticalGlue());
        
        return mainPanelFiltros;
    }
    
    private JLabel crearLabelFiltro(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(CUERPO_PEQUENO);
        label.setForeground(GRIS_MEDIO);
        label.setAlignmentX(LEFT_ALIGNMENT);
        return label;
    }
    
    private JPanel construirPanelTabla() {
        JPanel mainPanelTabla = new JPanel(new BorderLayout());
        mainPanelTabla.setBackground(Color.WHITE);
        mainPanelTabla.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MAIN_BORDE, 1),
                BorderFactory.createEmptyBorder(20, 10, 10, 10)
            ));
        
        // Crear modelo de tabla
        String anoActual = String.valueOf(Year.now().getValue());
        String[] columnas = {"Nombre", "Región", "Precio", "Div.", anoActual, "Riesgo", "Gestora"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabla no editable
            }
        };
        
        tablaProductos = new JTable(modeloTabla);
        tablaProductos.setFont(CUERPO_PEQUENO);
        tablaProductos.setRowHeight(30);
        
        // Asegurarse de líneas de tabla finas y uniformes
        tablaProductos.setShowGrid(true);
        tablaProductos.setGridColor(MAIN_BORDE);
        tablaProductos.setSelectionBackground(new Color(200, 210, 240));
        tablaProductos.setIntercellSpacing(new Dimension(1, 1));
        tablaProductos.setFocusable(false);
        tablaProductos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        tablaProductos.getTableHeader().setPreferredSize(new Dimension(0, 30));
        tablaProductos.getTableHeader().setFont(SUBTITULO_MEDIO);
        tablaProductos.getTableHeader().setBackground(AZUL_OSCURO);
        tablaProductos.getTableHeader().setForeground(Color.WHITE);
        tablaProductos.getTableHeader().setOpaque(true);        
        tablaProductos.getTableHeader().setReorderingAllowed(false); // No mover columnas
        tablaProductos.getTableHeader().setResizingAllowed(false);   // No redimensionar columnas
        
        // Iluminar fila al pasar el cursor por encima
        tablaProductos.addMouseMotionListener(new MouseAdapter() {
            private int lastRow = -1;
            
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = tablaProductos.rowAtPoint(e.getPoint());
                if (row != lastRow) {
                    lastRow = row;
                    tablaProductos.repaint();
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                tablaProductos.repaint();
            }
        });
        
        // Ajustar anchos de columna
        tablaProductos.getColumnModel().getColumn(0).setPreferredWidth(120); // Nombre
        tablaProductos.getColumnModel().getColumn(1).setPreferredWidth(100); // Región
        tablaProductos.getColumnModel().getColumn(2).setPreferredWidth(40);  // Precio
        tablaProductos.getColumnModel().getColumn(3).setPreferredWidth(25);  // Divisa
        tablaProductos.getColumnModel().getColumn(4).setPreferredWidth(55);  // YTD
        tablaProductos.getColumnModel().getColumn(5).setPreferredWidth(40);  // Riesgo
        tablaProductos.getColumnModel().getColumn(6).setPreferredWidth(90);  // Gestora
        
        tablaProductos.getColumnModel().getColumn(0).setCellRenderer(new UITema.RendererHover());
        tablaProductos.getColumnModel().getColumn(1).setCellRenderer(new UITema.RendererHover());
        tablaProductos.getColumnModel().getColumn(2).setCellRenderer(new UITema.RightRendererHover());
		tablaProductos.getColumnModel().getColumn(3).setCellRenderer(new UITema.RendererLogoDivisa(productosFiltrados));
        tablaProductos.getColumnModel().getColumn(4).setCellRenderer(new UITema.CenterRendererHover());
        tablaProductos.getColumnModel().getColumn(5).setCellRenderer(new UITema.CenterRendererHover());
        tablaProductos.getColumnModel().getColumn(6).setCellRenderer(new UITema.RendererLogoGestora(productosFiltrados));
        
        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        scrollPane.setBorder(BorderFactory.createLineBorder(MAIN_BORDE, 1));
        scrollPane.getVerticalScrollBar().setUI(personalizarScrollBarUI());
        
        mainPanelTabla.add(scrollPane, BorderLayout.CENTER);
        
        // Panel inferior con botones de acción
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelAcciones.setBackground(Color.WHITE);
        
        JButton botonVerDetalle = new JButton("Ver detalle");
        botonVerDetalle.setFont(CUERPO_PEQUENO);
        botonVerDetalle.setBackground(AZUL_CLARO);
        botonVerDetalle.setForeground(Color.WHITE);
        botonVerDetalle.setBorderPainted(false);
        botonVerDetalle.setContentAreaFilled(false);
        botonVerDetalle.setOpaque(true);
        botonVerDetalle.setFocusPainted(false);
        
        JButton botonAnadirACartera = new JButton("Añadir a cartera");
        botonAnadirACartera.setFont(CUERPO_PEQUENO);
        botonAnadirACartera.setBackground(VERDE_CLARO);
        botonAnadirACartera.setForeground(Color.WHITE);
        botonAnadirACartera.setBorderPainted(false);
        botonAnadirACartera.setContentAreaFilled(false);
        botonAnadirACartera.setOpaque(true);
        botonAnadirACartera.setFocusPainted(false);
        
        panelAcciones.add(botonVerDetalle);
        panelAcciones.add(botonAnadirACartera);
        
        mainPanelTabla.add(panelAcciones, BorderLayout.SOUTH);
        
        // Action Listeners
        botonVerDetalle.addActionListener(e -> verDetalleProducto());
        botonAnadirACartera.addActionListener(e -> anadirACartera());
        
        // Mouse Listener visual para entrada en los botones
        botonVerDetalle.addMouseListener(myAdapterAzul);
        botonAnadirACartera.addMouseListener(myAdapterVerde);
        
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
                    !String.valueOf(producto.getId()).contains(textoBusqueda)) {
                    cumpleFiltros = false;
                }
            }
            
            // Filtro por tipo de producto
            if (!selectedTipo.equals("Todo") && cumpleFiltros) {
                if (!producto.getTipoProducto().getNombre().equals(selectedTipo)) {
                    cumpleFiltros = false;
                }
            }
            
            // Filtro por clase de activo
            if (!selectedClase.equals("Todo") && cumpleFiltros) {
                if (!producto.getTipoProducto().getClaseActivo().getNombre().equals(selectedClase)) {
                    cumpleFiltros = false;
                }
            }
            
            // Filtro por región
            if (!selectedRegion.equals("Todo") && cumpleFiltros) {
                if (!producto.getRegionGeografica().getNombre().equals(selectedRegion)) {
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
            	String nombreGestora = (producto.getGestora() != null) ? producto.getGestora().getNombreComercial() : "---";
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
        	
        	String rutaImgGestora = null;
        	ImageIcon iconoGestora = null;
            if (p.getGestora() != null) {
                rutaImgGestora = "/images/gestoras/gestora" + p.getGestora().getNombreComercial().toLowerCase() + ".png";
            }
            if (getClass().getResource(rutaImgGestora) != null) {
                iconoGestora = new ImageIcon(getClass().getResource(rutaImgGestora));
            }
            if (iconoGestora != null) {
                Image imagen = iconoGestora.getImage();
                //IAG (ChatGPT)
                //ADAPTADO: Calcular proporción de anchura deseada
                int altoDeseado = 25;
                int anchoOriginal = imagen.getWidth(null);
                int altoOriginal = imagen.getHeight(null);
                
                int anchoDeseado = (int) ((double) altoDeseado / altoOriginal * anchoOriginal);
                
                int anchoMax = 80;
                if (anchoDeseado > anchoMax) {
                    double factor = (double) anchoMax / anchoDeseado;
                    anchoDeseado = anchoMax;
                    altoDeseado = (int) (altoDeseado * factor);
                }
                //END IAG
                Image imagenEscalada = imagen.getScaledInstance(anchoDeseado, altoDeseado, Image.SCALE_SMOOTH);
                iconoGestora = new ImageIcon(imagenEscalada);
            }
            
            String rutaImgDivisa = null;
            ImageIcon iconoDivisa = null;
			if (p.getDivisa() != null) {
				rutaImgDivisa = "/images/divisas/" + p.getDivisa().toString().toLowerCase() + ".png";
			}
			if (getClass().getResource(rutaImgDivisa) != null) {
				iconoDivisa = new ImageIcon(getClass().getResource(rutaImgDivisa));
			}
			if (iconoDivisa != null) {
				iconoDivisa = new ImageIcon(iconoDivisa.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
			}
            
            Object[] fila = {
        		p.getNombre(),
                p.getRegionGeografica().getNombre(),
                String.format("%.2f", p.getValorUnitario()),
                iconoDivisa,
                formatearRentabilidad(p.getRentabilidades().get(PlazoRentabilidad.YTD)),
                p.getTipoProducto().getStringRiesgo(),
                iconoGestora
            };
            modeloTabla.addRow(fila);
        }
    }
    
    private String formatearRentabilidad(BigDecimal rentabilidad) {
        if (rentabilidad == null) {
            return "---";
        }
        return String.format("%.2f%%", rentabilidad);
    }
    
    private void verDetalleProducto() {
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            ProductoFinanciero producto = productosFiltrados.get(filaSeleccionada);
            //IAG (ChatGPT)
            //SIN CAMBIOS
            JFrame framePadre = (JFrame) SwingUtilities.getWindowAncestor(this);
            new VentanaDetalleProducto(framePadre, producto, true);
            //END IAG
        } else {
        	UITema.mostrarWarning(this,
        			"Por favor, seleccione un producto para ver sus detalles.",
        			"Selección necesaria");
        }
    }
    
    private void anadirACartera() {
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            ProductoFinanciero producto = productosFiltrados.get(filaSeleccionada);
            JFrame framePadre = (JFrame) SwingUtilities.getWindowAncestor(this);
            new VentanaAnadirACartera(framePadre, usuario, producto, true);
        } else {
        	UITema.mostrarWarning(this,
        			"Por favor, seleccione un producto para añadirlo a su cartera.",
        			"Selección necesaria");
        }
    }
    
    private String[] obtenerTiposProducto() {
        List<String> tipos = new ArrayList<>();
        tipos.add("Todo");
        for (TipoProducto tipo : TipoProducto.values()) {
            tipos.add(tipo.getNombre());
        }
        return tipos.toArray(new String[0]);
    }
    
    private String[] obtenerClasesActivo() {
        List<String> clases = new ArrayList<>();
        clases.add("Todo");
        for (ClaseActivo clase : ClaseActivo.values()) {
            clases.add(clase.getNombre());
        }
        return clases.toArray(new String[0]);
    }
    
    private String[] obtenerRegiones() {
        List<String> regiones = new ArrayList<>();
        regiones.add("Todo");
        for (RegionGeografica region : RegionGeografica.values()) {
            regiones.add(region.getNombre());
        }
        return regiones.toArray(new String[0]);
    }
    
    private String[] obtenerGestoras() {
        Set<String> gestoras = new LinkedHashSet<>();
        gestoras.add("Todo");
        for (ProductoFinanciero p : MainEleutradia.listaProductos) {
        	Gestora g = p.getGestora();
        	if (g != null) {
        		gestoras.add(g.getNombreComercial());
        	}
        }
        return gestoras.toArray(new String[0]);
    }
    
}