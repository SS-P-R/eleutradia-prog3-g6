package es.deusto.eleutradia.gui;

import es.deusto.eleutradia.db.EleutradiaDBManager;
import es.deusto.eleutradia.domain.*;
import es.deusto.eleutradia.gui.style.UITema;
import es.deusto.eleutradia.main.MainEleutradia;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

import static es.deusto.eleutradia.gui.style.UITema.*;

public class PanelPortfolio extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private EleutradiaDBManager dbManager;
    private Usuario usuario;
    private Cartera carteraSeleccionada;
    private UITema uiTema;
    
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
    
    public PanelPortfolio(Usuario usuario) {
        this.usuario = usuario;
        this.dbManager = MainEleutradia.getDBManager();
        this.uiTema = UITema.getInstancia();
        if (!usuario.getCarteras().isEmpty()) {
            this.carteraSeleccionada = usuario.getCarteras().get(0);
        }
        
        this.setLayout(new BorderLayout(15, 15));
        this.setBackground(uiTema.colorFondo);
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        inicializarPaneles();
        cargarDatosPortfolio();
    }

    
    private void inicializarPaneles() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(uiTema.colorFondo);
        JPanel topPanel = new JPanel(new BorderLayout(15, 15));
        topPanel.setBackground(uiTema.colorFondo);
        topPanel.add(crearPanelSelector(), BorderLayout.NORTH);
        topPanel.add(crearPanelResumenUsuario(), BorderLayout.CENTER);
        mainPanel.add(topPanel, BorderLayout.NORTH);  
        mainPanel.add(crearPanelResumenCartera(), BorderLayout.CENTER);
        JPanel bottomContainer = new JPanel(new BorderLayout(15, 15));
        bottomContainer.setBackground(uiTema.colorFondo);
        bottomContainer.add(crearPanelPosiciones(), BorderLayout.CENTER);  
        bottomContainer.add(crearPanelOperacionesRecientes(), BorderLayout.SOUTH);
        mainPanel.add(bottomContainer, BorderLayout.SOUTH);
        
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUI(crearScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(crearScrollBarUI());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);
        add(crearPanelBotones(), BorderLayout.SOUTH);
    }
    private JPanel crearPanelSelector() {
        JPanel panel = crearCard();
        panel.setLayout(new BorderLayout(15, 10));
        
        JLabel lblTitulo = new JLabel("Mis Carteras");
        lblTitulo.setFont(SUBTITULO_GRANDE);
        panel.add(lblTitulo, BorderLayout.WEST);
        
        comboCarteras = new JComboBox<>();
        comboCarteras.setFont(CUERPO_GRANDE);
        comboCarteras.setPreferredSize(new Dimension(250, 35));
        
        // 1. Añadimos las carteras existentes
        for (Cartera cartera : usuario.getCarteras()) {
            comboCarteras.addItem(cartera.getNombre());
        }
        
        String opcionCrear = "+ Crear nueva cartera...";
        comboCarteras.addItem(opcionCrear);

        if (usuario.getCarteras().isEmpty()) {
            comboCarteras.setSelectedItem(opcionCrear);
        }

        comboCarteras.addActionListener(e -> {
            String seleccionado = (String) comboCarteras.getSelectedItem();
            
            if (opcionCrear.equals(seleccionado)) {
                abrirDialogoCrearCartera();
            } else {
                int index = comboCarteras.getSelectedIndex();
                if (index >= 0 && index < usuario.getCarteras().size()) {
                    carteraSeleccionada = usuario.getCarteras().get(index);
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
                
                boolean esParticular = usuario instanceof Particular;
                String idUsuario;
                
                if (esParticular) {
                    idUsuario = ((Particular) usuario).getDni();
                } else {
                    idUsuario = ((Empresa) usuario).getNif();
                }
                
                boolean exito = dbManager.insertCartera(nuevaCartera, idUsuario, esParticular);
                
                if (exito) {
                    usuario.getCarteras().add(nuevaCartera);
                    carteraSeleccionada = nuevaCartera;
                    
                    ActionListener[] listeners = comboCarteras.getActionListeners();
                    for (ActionListener l : listeners) comboCarteras.removeActionListener(l);
                    
                    comboCarteras.removeAllItems();
                    for (Cartera c : usuario.getCarteras()) comboCarteras.addItem(c.getNombre());
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
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.X_AXIS));
        panelPrincipal.setBackground(uiTema.colorFondo);
        
        // Patrimonio total
        JPanel card1 = crearCard();
        card1.setLayout(new BorderLayout(5, 5));
        JLabel lbl1 = new JLabel("Patrimonio total");
        lbl1.setFont(CUERPO_PEQUENO);
        lbl1.setForeground(GRIS_MEDIO);
        lblPatrimonioTotalUsuario = new JLabel("0,00 €", JLabel.RIGHT);
        lblPatrimonioTotalUsuario.setFont(TITULO_GRANDE);
        card1.add(lbl1, BorderLayout.NORTH);
        card1.add(lblPatrimonioTotalUsuario, BorderLayout.CENTER);
        panelPrincipal.add(card1);
        
        panelPrincipal.add(Box.createRigidArea(new Dimension(15, 0)));
        
        // Efectivo total
        JPanel card2 = crearCard();
        card2.setLayout(new BorderLayout(5, 5));
        JLabel lbl2 = new JLabel("Efectivo total");
        lbl2.setFont(CUERPO_PEQUENO);
        lbl2.setForeground(GRIS_MEDIO);
        lblPatrimonioLiquido = new JLabel("0,00 €", JLabel.RIGHT);
        lblPatrimonioLiquido.setFont(TITULO_GRANDE);
        card2.add(lbl2, BorderLayout.NORTH);
        card2.add(lblPatrimonioLiquido, BorderLayout.CENTER);
        panelPrincipal.add(card2);
        
        panelPrincipal.add(Box.createRigidArea(new Dimension(15, 0)));
        
        // Inversiones totales
        JPanel card3 = crearCard();
        card3.setLayout(new BorderLayout(5, 5));
        JLabel lbl3 = new JLabel("Inversiones totales");
        lbl3.setFont(CUERPO_PEQUENO);
        lbl3.setForeground(GRIS_MEDIO);
        lblPatrimonioInvertido = new JLabel("0,00 €", JLabel.RIGHT);
        lblPatrimonioInvertido.setFont(TITULO_GRANDE);
        card3.add(lbl3, BorderLayout.NORTH);
        card3.add(lblPatrimonioInvertido, BorderLayout.CENTER);
        panelPrincipal.add(card3);
        
        return panelPrincipal;
    }
    
    private JPanel crearPanelResumenCartera() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.X_AXIS));
        panelPrincipal.setBackground(uiTema.colorFondo);
        panelPrincipal.add(crearCardPatrimonioCartera());
        panelPrincipal.add(Box.createRigidArea(new Dimension(15, 0)));
        panelPrincipal.add(crearCardDesgloseCartera());
        return panelPrincipal;
    }
    
    private JPanel crearCardPatrimonioCartera() {
        JPanel card = crearCard();
        card.setLayout(new BorderLayout(10, 10));
        lblNombreCartera = new JLabel("Cartera seleccionada");
        lblNombreCartera.setFont(SUBTITULO_GRANDE);
        lblNombreCartera.setForeground(AZUL_CLARO);
        card.add(lblNombreCartera, BorderLayout.NORTH);
        lblPatrimonioCartera = new JLabel("0,00 €", JLabel.CENTER);
        lblPatrimonioCartera.setFont(new Font("Arial", Font.BOLD, 36));
        lblPatrimonioCartera.setForeground(GRIS_OSCURO);
        card.add(lblPatrimonioCartera, BorderLayout.CENTER);
        lblGananciasTotal = new JLabel("▲ +0,00 € (0,00%)", JLabel.CENTER);
        lblGananciasTotal.setFont(SUBTITULO_MEDIO);
        lblGananciasTotal.setForeground(VERDE_OSCURO);
        card.add(lblGananciasTotal, BorderLayout.SOUTH);
        return card;
    }
    
    private JPanel crearCardDesgloseCartera() {
        JPanel card = crearCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        
        // Saldo disponible
        JPanel panelSaldo = crearItemDesglose("Saldo disponible");
        panelSaldo.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        lblSaldoDisponible = new JLabel("0,00 €", JLabel.RIGHT);
        lblSaldoDisponible.setFont(SUBTITULO_GRANDE);
        panelSaldo.add(lblSaldoDisponible);
        card.add(panelSaldo);
        
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Valor de inversiones
        JPanel panelInversiones = crearItemDesglose("Valor de inversiones");
        panelInversiones.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        lblValorInversiones = new JLabel("0,00 €", JLabel.RIGHT);
        lblValorInversiones.setFont(SUBTITULO_GRANDE);
        panelInversiones.add(lblValorInversiones);
        card.add(panelInversiones);
        
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Perfil de riesgo
        JPanel panelRiesgo = crearItemDesglose("Perfil de riesgo");
        panelRiesgo.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        JLabel lblRiesgo = new JLabel("-", JLabel.RIGHT);
        lblRiesgo.setFont(SUBTITULO_GRANDE);
        panelRiesgo.add(lblRiesgo);
        card.add(panelRiesgo);
        
        return card;
    }
    
    private JPanel crearItemDesglose(String titulo) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(uiTema.colorPanel);
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(CUERPO_PEQUENO);
        lblTitulo.setForeground(GRIS_MEDIO);
        panel.add(lblTitulo, BorderLayout.NORTH);
        
        return panel;
    }
    
    private JPanel crearPanelPosiciones() {
        JPanel panel = crearCard();
        panel.setLayout(new BorderLayout(0, 15));
        panel.setPreferredSize(new Dimension(0, 400));
        JLabel lblTitulo = new JLabel("Posiciones Actuales");
        lblTitulo.setFont(TITULO_MEDIO);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panel.add(lblTitulo, BorderLayout.NORTH);
        
        String[] columnNames = {
            "Producto", "Cant.", "P. Medio",
            "P. Actual", "Valor", "Rent.", "%"
        };
        
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablePosiciones = new JTable(tableModel);
        tablePosiciones.setFont(CUERPO_PEQUENO);
        tablePosiciones.setRowHeight(35);
        
        tablePosiciones.setShowGrid(true);
        tablePosiciones.setGridColor(uiTema.colorBorde);
        tablePosiciones.setSelectionBackground(new Color(200, 210, 240));
        tablePosiciones.setSelectionForeground(Color.BLACK);
        tablePosiciones.setIntercellSpacing(new Dimension(1, 1));
        tablePosiciones.setFocusable(false);
        
        tablePosiciones.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        JTableHeader header = tablePosiciones.getTableHeader();
        header.setFont(SUBTITULO_MEDIO);
        header.setBackground(uiTema.esTemaOscuro() ? GRIS_MEDIO : AZUL_OSCURO);
        header.setForeground(uiTema.esTemaOscuro() ? GRIS_SUAVE : Color.WHITE);
        header.setOpaque(true);
        header.setReorderingAllowed(false);
        header.setResizingAllowed(false);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, uiTema.colorBorde));
        
        tablePosiciones.getColumnModel().getColumn(0).setPreferredWidth(150); // Producto
        tablePosiciones.getColumnModel().getColumn(1).setPreferredWidth(50);  // Cantidad
        tablePosiciones.getColumnModel().getColumn(2).setPreferredWidth(80);  // Precio Medio
        tablePosiciones.getColumnModel().getColumn(3).setPreferredWidth(80);  // Precio Actual
        tablePosiciones.getColumnModel().getColumn(4).setPreferredWidth(60);  // Valor
        tablePosiciones.getColumnModel().getColumn(5).setPreferredWidth(70);  // Rentabilidad
        tablePosiciones.getColumnModel().getColumn(6).setPreferredWidth(60);  // Porcentaje
        
        DefaultTableCellRenderer centerRenderer = new UITema.CenterRendererHover();
        tablePosiciones.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        
        DefaultTableCellRenderer rightRenderer = new UITema.RightRendererHover();
        tablePosiciones.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        tablePosiciones.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        tablePosiciones.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);

        DefaultTableCellRenderer rentRenderer = new UITema.RendererRentabilidad();
        tablePosiciones.getColumnModel().getColumn(5).setCellRenderer(rentRenderer);
        tablePosiciones.getColumnModel().getColumn(6).setCellRenderer(rentRenderer);
        
        JScrollPane scrollPane = new JScrollPane(tablePosiciones);
        scrollPane.setBorder(BorderFactory.createLineBorder(uiTema.colorBorde));
        scrollPane.getVerticalScrollBar().setUI(crearScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(crearScrollBarUI());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelOperacionesRecientes() {
        JPanel panel = crearCard();
        panel.setLayout(new BorderLayout(0, 15));
        panel.setPreferredSize(new Dimension(0, 200));
        JLabel lblTitulo = new JLabel("Operaciones Recientes");
        lblTitulo.setFont(SUBTITULO_GRANDE);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panel.add(lblTitulo, BorderLayout.NORTH);
        operationsListModel = new DefaultListModel<>();
        JList<String> operationsList = new JList<>(operationsListModel);
        operationsList.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        operationsList.setFixedCellHeight(30);
        operationsList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JScrollPane scrollPane = new JScrollPane(operationsList);
        scrollPane.setBorder(BorderFactory.createLineBorder(uiTema.colorBorde));
        scrollPane.getVerticalScrollBar().setUI(crearScrollBarUI());
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(uiTema.colorFondo);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        JButton btnActualizar = new JButton("Actualizar Datos");
        btnActualizar.setFont(SUBTITULO_MEDIO);
        btnActualizar.setBackground(AZUL_CLARO);
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
        card.setBackground(uiTema.colorPanel);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(uiTema.colorBorde, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        return card;
    }
    
    private void cargarDatosPortfolio() {
        double patrimonioTotal = usuario.calcularPatrimonioTotal();
        double patrimonioLiquido = usuario.calcularPatrimonioLiquido();
        double patrimonioInvertido = usuario.calcularPatrimonioInvertido();
        
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
        lblGananciasTotal.setForeground(gananciaTotal >= 0 ? VERDE_OSCURO : ROJO_CLARO);
        tableModel.setRowCount(0);
        
        for (Posicion posicion : posiciones) {
            ProductoFinanciero producto = posicion.getProducto();
            Object[] row = {
                producto.getNombre(),
                String.format("%.2f", posicion.getCantidadTotal()),
                String.format("%.2f %s", posicion.getPrecioMedioCompra(), posicion.getProducto().getDivisa().getSimbolo()),
                String.format("%.2f %s", producto.getValorUnitario(), posicion.getProducto().getDivisa().getSimbolo()),
                String.format("%.2f %s", posicion.getValorTotal(), posicion.getProducto().getDivisa().getSimbolo()),
                String.format("%.2f %s", posicion.getGanancia(), posicion.getProducto().getDivisa().getSimbolo()),
                String.format("%.2f%%", posicion.getPorcentajeGanancia())
            };
            tableModel.addRow(row);
        }
        
        if (posiciones.isEmpty()) {
            Object[] row = {
                "No hay posiciones activas en esta cartera", "", "", "", "", "", ""
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
    
    public void refrescarDatos() {
        cargarDatosPortfolio();
    }
    
    public void refrescarColores() {
        this.setBackground(uiTema.colorFondo);
        inicializarPaneles();
    }
}