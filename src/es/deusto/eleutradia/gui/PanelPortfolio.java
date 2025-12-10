package es.deusto.eleutradia.gui;

import es.deusto.eleutradia.db.EleutradiaDBManager;
import es.deusto.eleutradia.domain.*;
import es.deusto.eleutradia.main.MainEleutradia;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class PanelPortfolio extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private EleutradiaDBManager dbManager;

    private Usuario usuarioActual;
    private Cartera carteraSeleccionada;
    private GestorTema gestorTema = GestorTema.getInstancia();
    
    private JComboBox<String> comboCarteras;
    private JLabel lblPatrimonioTotalUsuario;
    private JLabel lblPatrimonioLiquido;
    private JLabel lblPatrimonioInvertido;
    private JLabel lblNombreCartera;
    private JLabel lblPatrimonioCartera;
    private JLabel lblSaldoDisponible;
    private JLabel lblValorInversiones;
    private JLabel lblGananciasTotal;
    private JTable tablePosiciones;
    private DefaultTableModel tableModel;
    private DefaultListModel<String> operationsListModel;
    
    // Estilos
    private static final Color COLOR_SCROLLBAR = new Color(180, 180, 180);
    private Color COLOR_GANANCIA = gestorTema.getColorGanancia();
    private Color COLOR_PERDIDA = gestorTema.getColorPerdida();
    private Color COLOR_FONDO_PRINCIPAL = gestorTema.getColorFondo();
    private Color COLOR_VENTANA = gestorTema.getColorVentana();
    private Color COLOR_BORDE = gestorTema.getColorBorde();
    private Color COLOR_TEXTO_SECUNDARIO = gestorTema.getColorTextoSecundario();
    private Color COLOR_ACENTO = gestorTema.getColorAcento();
    
    private static final Font FONT_TITULO1 = new Font("Segoe UI", Font.BOLD, 20);
    private static final Font FONT_TITULO2 = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font FONT_SUBTITULO = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONT_NORMAL1 = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_NORMAL2 = new Font("Segoe UI", Font.PLAIN, 12);
    
    public PanelPortfolio(Usuario usuario) {
        this.usuarioActual = usuario;
        this.dbManager = MainEleutradia.getDBManager();
        
        if (!usuario.getCarteras().isEmpty()) {
            this.carteraSeleccionada = usuario.getCarteras().get(0);
        }
        
        this.setLayout(new BorderLayout(15, 15));
        this.setBackground(COLOR_FONDO_PRINCIPAL);
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        inicializarPaneles();
        cargarDatosPortfolio();
    }

    
    private void inicializarPaneles() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(COLOR_FONDO_PRINCIPAL);
        JPanel topPanel = new JPanel(new BorderLayout(15, 15));
        topPanel.setBackground(COLOR_FONDO_PRINCIPAL);
        topPanel.add(crearPanelSelector(), BorderLayout.NORTH);
        topPanel.add(crearPanelResumenUsuario(), BorderLayout.CENTER);
        mainPanel.add(topPanel, BorderLayout.NORTH);  
        mainPanel.add(crearPanelResumenCartera(), BorderLayout.CENTER);
        JPanel bottomContainer = new JPanel(new BorderLayout(15, 15));
        bottomContainer.setBackground(COLOR_FONDO_PRINCIPAL);
        bottomContainer.add(crearPanelPosiciones(), BorderLayout.CENTER);  
        bottomContainer.add(crearPanelOperacionesRecientes(), BorderLayout.SOUTH);
        mainPanel.add(bottomContainer, BorderLayout.SOUTH);
        
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
    		@Override
    	    protected void configureScrollBarColors() {
    	        this.thumbColor = COLOR_SCROLLBAR;
    	        this.thumbDarkShadowColor = COLOR_SCROLLBAR;
    	        this.thumbHighlightColor = COLOR_SCROLLBAR;
    	        this.trackColor = Color.WHITE; 
    	    }

    	    @Override
    	    protected JButton createDecreaseButton(int orientation) {
    	        return createInvisibleButton();
    	    }

    	    @Override
    	    protected JButton createIncreaseButton(int orientation) {
    	        return createInvisibleButton();
    	    }

    	    private JButton createInvisibleButton() {
    	        JButton button = new JButton();
    	        button.setPreferredSize(new Dimension(0, 0));
    	        button.setVisible(false);
    	        return button;
    	    }
        });
        scrollPane.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
    		@Override
    	    protected void configureScrollBarColors() {
    	        this.thumbColor = COLOR_SCROLLBAR;
    	        this.thumbDarkShadowColor = COLOR_SCROLLBAR;
    	        this.thumbHighlightColor = COLOR_SCROLLBAR;
    	        this.trackColor = Color.WHITE; 
    	    }

    	    @Override
    	    protected JButton createDecreaseButton(int orientation) {
    	        return createInvisibleButton();
    	    }

    	    @Override
    	    protected JButton createIncreaseButton(int orientation) {
    	        return createInvisibleButton();
    	    }

    	    private JButton createInvisibleButton() {
    	        JButton button = new JButton();
    	        button.setPreferredSize(new Dimension(0, 0));
    	        button.setVisible(false);
    	        return button;
    	    }
        });
        add(scrollPane, BorderLayout.CENTER);
        add(crearPanelBotones(), BorderLayout.SOUTH);
    }
    private JPanel crearPanelSelector() {
        JPanel panel = crearCard();
        panel.setLayout(new BorderLayout(15, 10));
        
        JLabel lblTitulo = new JLabel("Mis Carteras");
        lblTitulo.setFont(FONT_TITULO2);
        panel.add(lblTitulo, BorderLayout.WEST);
        
        comboCarteras = new JComboBox<>();
        comboCarteras.setFont(FONT_NORMAL1);
        comboCarteras.setPreferredSize(new Dimension(300, 35));
        
        // 1. Añadimos las carteras existentes
        for (Cartera cartera : usuarioActual.getCarteras()) {
            comboCarteras.addItem(cartera.getNombre());
        }
        
        String opcionCrear = "+ Crear nueva cartera...";
        comboCarteras.addItem(opcionCrear);

        if (usuarioActual.getCarteras().isEmpty()) {
            comboCarteras.setSelectedItem(opcionCrear);
        }

        comboCarteras.addActionListener(e -> {
            String seleccionado = (String) comboCarteras.getSelectedItem();
            
            if (opcionCrear.equals(seleccionado)) {
                abrirDialogoCrearCartera();
            } else {
                int index = comboCarteras.getSelectedIndex();
                if (index >= 0 && index < usuarioActual.getCarteras().size()) {
                    carteraSeleccionada = usuarioActual.getCarteras().get(index);
                    cargarDatosPortfolio();
                }
            }
        });
        
        panel.add(comboCarteras, BorderLayout.EAST);
        return panel;
    }
    private void abrirDialogoCrearCartera() {
        JPanel panelFormulario = new JPanel(new GridLayout(0, 1));
        JTextField txtNombre = new JTextField();
        JTextField txtSaldo = new JTextField("0.0");
        JComboBox<PerfilRiesgo> comboPerfil = new JComboBox<>(PerfilRiesgo.values());
        JComboBox<Divisa> comboDivisa = new JComboBox<>(Divisa.values());

        panelFormulario.add(new JLabel("Nombre de la Cartera:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Saldo Inicial:"));
        panelFormulario.add(txtSaldo);
        panelFormulario.add(new JLabel("Perfil de Riesgo:"));
        panelFormulario.add(comboPerfil);
        panelFormulario.add(new JLabel("Divisa:"));
        panelFormulario.add(comboDivisa);

        int resultado = JOptionPane.showConfirmDialog(this, panelFormulario, 
                "Nueva Cartera", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                String nombre = txtNombre.getText().trim();
                double saldo = Double.parseDouble(txtSaldo.getText().trim());
                PerfilRiesgo perfil = (PerfilRiesgo) comboPerfil.getSelectedItem();
                Divisa divisa = (Divisa) comboDivisa.getSelectedItem();

                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Cartera nuevaCartera = new Cartera(nombre, saldo, perfil, divisa);
                
                // GUARDAR EN BASE DE DATOS
                
                boolean esParticular = usuarioActual instanceof Particular;
                String idUsuario;
                
                if (esParticular) {
                    idUsuario = ((Particular) usuarioActual).getDni();
                } else {
                    idUsuario = ((Empresa) usuarioActual).getNif();
                }
                
                boolean exito = dbManager.insertCartera(nuevaCartera, idUsuario, esParticular);
                
                if (exito) {
                    usuarioActual.getCarteras().add(nuevaCartera);
                    carteraSeleccionada = nuevaCartera;
                    
                    ActionListener[] listeners = comboCarteras.getActionListeners();
                    for (ActionListener l : listeners) comboCarteras.removeActionListener(l);
                    
                    comboCarteras.removeAllItems();
                    for (Cartera c : usuarioActual.getCarteras()) comboCarteras.addItem(c.getNombre());
                    comboCarteras.addItem("+ Crear nueva cartera...");
                    comboCarteras.setSelectedItem(nuevaCartera.getNombre());
                    
                    for (ActionListener l : listeners) comboCarteras.addActionListener(l);
                    
                    cargarDatosPortfolio();
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El saldo debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al crear la cartera.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            if (carteraSeleccionada != null) {
                comboCarteras.setSelectedItem(carteraSeleccionada.getNombre());
            }
        }
    }
    
    private JPanel crearPanelResumenUsuario() {
        JPanel panelPrincipal = new JPanel(new GridLayout(1, 3, 15, 0));
        panelPrincipal.setBackground(COLOR_FONDO_PRINCIPAL);
        
        JPanel card1 = crearCard();
        card1.setLayout(new BorderLayout(5, 5));
        JLabel lbl1 = new JLabel("Patrimonio Total (Todas las Carteras)");
        lbl1.setFont(FONT_NORMAL2);
        lbl1.setForeground(COLOR_TEXTO_SECUNDARIO);
        lblPatrimonioTotalUsuario = new JLabel("0,00 €");
        lblPatrimonioTotalUsuario.setFont(FONT_TITULO1);
        card1.add(lbl1, BorderLayout.NORTH);
        card1.add(lblPatrimonioTotalUsuario, BorderLayout.CENTER);
        panelPrincipal.add(card1);
        JPanel card2 = crearCard();
        card2.setLayout(new BorderLayout(5, 5));
        JLabel lbl2 = new JLabel("Efectivo Total");
        lbl2.setFont(FONT_NORMAL2);
        lbl2.setForeground(COLOR_TEXTO_SECUNDARIO);
        lblPatrimonioLiquido = new JLabel("0,00 €");
        lblPatrimonioLiquido.setFont(FONT_TITULO1);
        card2.add(lbl2, BorderLayout.NORTH);
        card2.add(lblPatrimonioLiquido, BorderLayout.CENTER);
        panelPrincipal.add(card2);
        JPanel card3 = crearCard();
        card3.setLayout(new BorderLayout(5, 5));
        JLabel lbl3 = new JLabel("Inversiones Totales");
        lbl3.setFont(FONT_NORMAL2);
        lbl3.setForeground(COLOR_TEXTO_SECUNDARIO);
        lblPatrimonioInvertido = new JLabel("0,00 €");
        lblPatrimonioInvertido.setFont(FONT_TITULO1);
        card3.add(lbl3, BorderLayout.NORTH);
        card3.add(lblPatrimonioInvertido, BorderLayout.CENTER);
        panelPrincipal.add(card3);
        
        return panelPrincipal;
    }
    
    private JPanel crearPanelResumenCartera() {
        JPanel panelPrincipal = new JPanel(new GridLayout(1, 2, 15, 0));
        panelPrincipal.setBackground(COLOR_FONDO_PRINCIPAL);
        panelPrincipal.add(crearCardPatrimonioCartera());
        panelPrincipal.add(crearCardDesgloseCartera());
        return panelPrincipal;
    }
    
    private JPanel crearCardPatrimonioCartera() {
        JPanel card = crearCard();
        card.setLayout(new BorderLayout(10, 10));
        lblNombreCartera = new JLabel("Cartera Seleccionada");
        lblNombreCartera.setFont(FONT_TITULO2);
        lblNombreCartera.setForeground(COLOR_ACENTO);
        card.add(lblNombreCartera, BorderLayout.NORTH);
        lblPatrimonioCartera = new JLabel("0,00 €");
        lblPatrimonioCartera.setFont(new Font("Arial", Font.BOLD, 36));
        lblPatrimonioCartera.setForeground(new Color(33, 37, 41));
        card.add(lblPatrimonioCartera, BorderLayout.CENTER);
        lblGananciasTotal = new JLabel("▲ +0,00 € (0,00%)");
        lblGananciasTotal.setFont(FONT_SUBTITULO);
        lblGananciasTotal.setForeground(COLOR_GANANCIA);
        card.add(lblGananciasTotal, BorderLayout.SOUTH);
        return card;
    }
    
    private JPanel crearCardDesgloseCartera() {
        JPanel card = crearCard();
        card.setLayout(new GridLayout(3, 1, 0, 15));
        JPanel panelSaldo = crearItemDesglose("Saldo Disponible");
        lblSaldoDisponible = new JLabel("0,00 €");
        lblSaldoDisponible.setFont(FONT_TITULO2);
        panelSaldo.add(lblSaldoDisponible);
        card.add(panelSaldo);
        JPanel panelInversiones = crearItemDesglose("Valor de Inversiones");
        lblValorInversiones = new JLabel("0,00 €");
        lblValorInversiones.setFont(FONT_TITULO2);
        panelInversiones.add(lblValorInversiones);
        card.add(panelInversiones);
        JPanel panelRiesgo = crearItemDesglose("Perfil de Riesgo");
        JLabel lblRiesgo = new JLabel("-");
        lblRiesgo.setFont(FONT_TITULO2);
        panelRiesgo.add(lblRiesgo);
        card.add(panelRiesgo);
        
        return card;
    }
    
    private JPanel crearItemDesglose(String titulo) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(COLOR_VENTANA);
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(FONT_NORMAL2);
        lblTitulo.setForeground(COLOR_TEXTO_SECUNDARIO);
        panel.add(lblTitulo, BorderLayout.NORTH);
        
        return panel;
    }
    
    private JPanel crearPanelPosiciones() {
        JPanel panel = crearCard();
        panel.setLayout(new BorderLayout(0, 15));
        panel.setPreferredSize(new Dimension(0, 400));
        JLabel lblTitulo = new JLabel("Posiciones Actuales");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panel.add(lblTitulo, BorderLayout.NORTH);
        String[] columnNames = {
            "Producto", "Tipo", "Cantidad", "Precio Medio", 
            "Precio Actual", "Valor Total", "Ganancia/Pérdida", "%"
        };
        
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablePosiciones = new JTable(tableModel);
        tablePosiciones.setRowHeight(35);
        tablePosiciones.setFont(FONT_NORMAL2);
        tablePosiciones.setGridColor(COLOR_BORDE);
        tablePosiciones.setSelectionBackground(new Color(232, 244, 253));
        tablePosiciones.setSelectionForeground(Color.BLACK);
        JTableHeader header = tablePosiciones.getTableHeader();
        header.setFont(FONT_SUBTITULO);
        header.setBackground(COLOR_FONDO_PRINCIPAL);
        header.setForeground(COLOR_TEXTO_SECUNDARIO);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, COLOR_BORDE));
        tablePosiciones.getColumnModel().getColumn(0).setPreferredWidth(200);
        tablePosiciones.getColumnModel().getColumn(1).setPreferredWidth(120);
        tablePosiciones.getColumnModel().getColumn(2).setPreferredWidth(100);
        tablePosiciones.getColumnModel().getColumn(3).setPreferredWidth(120);
        tablePosiciones.getColumnModel().getColumn(4).setPreferredWidth(120);
        tablePosiciones.getColumnModel().getColumn(5).setPreferredWidth(130);
        tablePosiciones.getColumnModel().getColumn(6).setPreferredWidth(150);
        tablePosiciones.getColumnModel().getColumn(6).setCellRenderer(new RendererGananciaPerdida());
        tablePosiciones.getColumnModel().getColumn(7).setCellRenderer(new RendererGananciaPerdida());
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tablePosiciones.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tablePosiciones.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        JScrollPane scrollPane = new JScrollPane(tablePosiciones);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_BORDE));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelOperacionesRecientes() {
        JPanel panel = crearCard();
        panel.setLayout(new BorderLayout(0, 15));
        panel.setPreferredSize(new Dimension(0, 200));
        JLabel lblTitulo = new JLabel("Operaciones Recientes");
        lblTitulo.setFont(FONT_TITULO2);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panel.add(lblTitulo, BorderLayout.NORTH);
        operationsListModel = new DefaultListModel<>();
        JList<String> operationsList = new JList<>(operationsListModel);
        operationsList.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        operationsList.setFixedCellHeight(30);
        operationsList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JScrollPane scrollPane = new JScrollPane(operationsList);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_BORDE));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(COLOR_FONDO_PRINCIPAL);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        JButton btnActualizar = new JButton("Actualizar Datos");
        btnActualizar.setFont(FONT_SUBTITULO);
        btnActualizar.setBackground(COLOR_ACENTO);
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFocusPainted(false);
        btnActualizar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnActualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnActualizar.addActionListener(e -> cargarDatosPortfolio());
        panel.add(btnActualizar);
        
        return panel;
    }
    
    private JPanel crearCard() {
        JPanel card = new JPanel();
        card.setBackground(COLOR_VENTANA);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDE, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        return card;
    }
    
    private void cargarDatosPortfolio() {
        double patrimonioTotal = usuarioActual.calcularPatrimonioTotal();
        double patrimonioLiquido = usuarioActual.calcularPatrimonioLiquido();
        double patrimonioInvertido = usuarioActual.calcularPatrimonioInvertido();
        
        lblPatrimonioTotalUsuario.setText(String.format("%,.2f €", patrimonioTotal));
        lblPatrimonioLiquido.setText(String.format("%,.2f €", patrimonioLiquido));
        lblPatrimonioInvertido.setText(String.format("%,.2f €", patrimonioInvertido));
        
        if (carteraSeleccionada == null) {
            lblNombreCartera.setText("No hay carteras disponibles");
            lblPatrimonioCartera.setText("0,00 €");
            lblSaldoDisponible.setText("0,00 €");
            lblValorInversiones.setText("0,00 €");
            lblGananciasTotal.setText("N/A");
            tableModel.setRowCount(0);
            operationsListModel.clear();
            return;
        }
        
        double patrimonio = carteraSeleccionada.calcularPatrimonio();
        double saldo = carteraSeleccionada.getSaldo();
        double inversiones = carteraSeleccionada.calcularValorInversiones();
        lblNombreCartera.setText(carteraSeleccionada.getNombre());
        lblPatrimonioCartera.setText(String.format("%,.2f %s", patrimonio, carteraSeleccionada.getDivisa()));
        lblSaldoDisponible.setText(String.format("%,.2f %s", saldo, carteraSeleccionada.getDivisa()));
        lblValorInversiones.setText(String.format("%,.2f %s", inversiones, carteraSeleccionada.getDivisa()));
        List<Posicion> posiciones = carteraSeleccionada.obtenerPosicionesActuales();
        double gananciaTotal = 0;
        double inversionTotal = 0;
        for (Posicion pos : posiciones) {
            gananciaTotal += pos.getGanancia();
            inversionTotal += pos.getCantidadTotal() * pos.getPrecioMedioCompra();
        }
        
        double porcentajeGanancia = inversionTotal > 0 ? (gananciaTotal / inversionTotal) * 100 : 0;
        String simbolo = gananciaTotal >= 0 ? "▲" : "▼";
        String signo = gananciaTotal >= 0 ? "+" : "";
        lblGananciasTotal.setText(String.format("%s %s%,.2f %s (%s%.2f%%)", 
            simbolo, signo, gananciaTotal, carteraSeleccionada.getDivisa(), signo, porcentajeGanancia));
        lblGananciasTotal.setForeground(gananciaTotal >= 0 ? COLOR_GANANCIA : COLOR_PERDIDA);
        tableModel.setRowCount(0);
        
        for (Posicion posicion : posiciones) {
            ProductoFinanciero producto = posicion.getProducto();
            Object[] row = {
                producto.getNombre(),
                producto.getTipoProducto().toString(),
                String.format("%.2f", posicion.getCantidadTotal()),
                String.format("%.2f %s", posicion.getPrecioMedioCompra(), producto.getDivisa()),
                String.format("%.2f %s", producto.getValorUnitario(), producto.getDivisa()),
                String.format("%.2f %s", posicion.getValorTotal(), producto.getDivisa()),
                String.format("%.2f %s", posicion.getGanancia(), producto.getDivisa()),
                String.format("%.2f%%", posicion.getPorcentajeGanancia())
            };
            tableModel.addRow(row);
        }
        
        if (posiciones.isEmpty()) {
            Object[] row = {
                "No hay posiciones activas en esta cartera", "", "", "", "", "", "", ""
            };
            tableModel.addRow(row);
        }
        
        operationsListModel.clear();
        List<Operacion> ops = carteraSeleccionada.getOperaciones();
        int start = Math.max(0, ops.size() - 10);
        
        for (int i = ops.size() - 1; i >= start; i--) {
            Operacion op = ops.get(i);
            String tipo = op.getTipoOp() ? "COMPRA" : "VENTA ";
            String simboloOp = op.getTipoOp() ? "↑" : "↓";
            operationsListModel.addElement(String.format("%s %s | %s | %.2f unidades de %s",
                simboloOp,
                op.getFechaOp().toString(),
                tipo,
                op.getCantidad(),
                op.getProdFinanciero().getNombre()
            ));
        }
        
        if (ops.isEmpty()) {
            operationsListModel.addElement("No hay operaciones registradas en esta cartera");
        }
    }
    
    private class RendererGananciaPerdida extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if (!isSelected) {
                c.setBackground(Color.WHITE);
            }
            
            if (value != null && !value.toString().isEmpty()) {
                String strValue = value.toString();
                
                try {
                    strValue = strValue.replace("%", "").replace("€", "").replace("$", "")
                                       .replace("USD", "").replace("EUR", "").trim();
                    double numValue = Double.parseDouble(strValue);
                    
                    if (numValue > 0) {
                        c.setForeground(COLOR_GANANCIA);
                    } else if (numValue < 0) {
                        c.setForeground(COLOR_PERDIDA);
                    } else {
                        c.setForeground(Color.BLACK);
                    }
                } catch (NumberFormatException e) {
                    c.setForeground(Color.BLACK);
                }
                setFont(FONT_SUBTITULO);
            }
            
            setHorizontalAlignment(JLabel.RIGHT);
            return c;
        }
    }
    
    public void refrescarDatos() {
        cargarDatosPortfolio();
    }
    
    public void refrescarColores() {
        this.setBackground(COLOR_FONDO_PRINCIPAL);
        inicializarPaneles();
    }
}