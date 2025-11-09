package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import es.deusto.eleutradia.domain.Cartera;
import es.deusto.eleutradia.domain.PerfilFinanciero;
import es.deusto.eleutradia.domain.Usuario;

public class PanelPerfil extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private Usuario usuarioActual;

    // Estilos
    private boolean temaOscuro = false;
    private static final Color COLOR_FONDO_CLARO = new Color(248, 249, 250);
    private static final Color COLOR_CARD_CLARO = Color.WHITE;
    private static final Color COLOR_BORDE = new Color(222, 226, 230);
    private static final Color COLOR_TEXTO_PRINCIPAL = new Color(33, 37, 41);
    private static final Color COLOR_TEXTO_SECUNDARIO = new Color(108, 117, 125);
    private static final Color COLOR_ACENTO = new Color(0, 123, 255);
    private static final Color COLOR_EXITO = new Color(40, 167, 69);
    private static final Color COLOR_FONDO_OSCURO = new Color(33, 37, 41);
    private static final Color COLOR_CARD_OSCURO = new Color(52, 58, 64);
    private static final Color COLOR_TEXTO_OSCURO = new Color(248, 249, 250);
    
    private static final Font FONT_TITULO = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FONT_SUBTITULO = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONT_NORMAL1 = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_NORMAL2 = new Font("Segoe UI", Font.PLAIN, 12);
	
    public PanelPerfil(Usuario usuario) {
        this.usuarioActual = usuario;
        this.setLayout(new BorderLayout(15, 15));
        this.actualizarColores();
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        initComponents();
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(getColorFondo());
        mainPanel.add(crearPanelCabecera(), BorderLayout.NORTH);
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        centerPanel.setBackground(getColorFondo());
        centerPanel.add(crearPanelInformacionPersonal());
        centerPanel.add(crearPanelPerfilFinanciero());
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        bottomPanel.setBackground(getColorFondo());
        bottomPanel.add(crearPanelConfiguracion());
        bottomPanel.add(crearPanelEstadisticas());
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private JPanel crearPanelCabecera() {
        JPanel panel = crearCard();
        panel.setLayout(new BorderLayout(20, 10));
        String inicialNombre = String.valueOf(usuarioActual.getNombre().charAt(0)).toUpperCase();
        String inicialApellido = String.valueOf(usuarioActual.getNombre().split("\\s+")[1].charAt(0)).toUpperCase();
        JLabel lblAvatar = new JLabel(inicialNombre + inicialApellido);
        lblAvatar.setForeground(Color.LIGHT_GRAY);
        lblAvatar.setFont(new Font("Segoe UI", Font.BOLD, 56));
        lblAvatar.setHorizontalAlignment(SwingConstants.CENTER);
        lblAvatar.setPreferredSize(new Dimension(100, 100));
        panel.add(lblAvatar, BorderLayout.WEST);
        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        infoPanel.setBackground(getColorCard());
        
        JLabel lblNombre = new JLabel(usuarioActual.getNombre());
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblNombre.setForeground(getColorTextoPrincipal());
        
        JLabel lblEmail = new JLabel(usuarioActual.getEmail());
        lblEmail.setFont(FONT_NORMAL1);
        lblEmail.setForeground(COLOR_TEXTO_SECUNDARIO);
        
        JLabel lblTipo = new JLabel("Cuenta Activa • " + usuarioActual.getCarteras().size() + " Cartera(s)");
        lblTipo.setFont(FONT_NORMAL1);
        lblTipo.setForeground(COLOR_EXITO);
        
        infoPanel.add(lblNombre);
        infoPanel.add(lblEmail);
        infoPanel.add(lblTipo);
        
        panel.add(infoPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelInformacionPersonal() {
        JPanel panel = crearCard();
        panel.setLayout(new BorderLayout(0, 15));
        JLabel lblTitulo = new JLabel("Información Personal");
        lblTitulo.setFont(FONT_TITULO);
        lblTitulo.setForeground(getColorTextoPrincipal());
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panel.add(lblTitulo, BorderLayout.NORTH);
        
        // Content
        JPanel contentPanel = new JPanel(new GridLayout(5, 1, 0, 15));
        contentPanel.setBackground(getColorCard());
        
        contentPanel.add(crearCampoInfo("Nombre Completo", usuarioActual.getNombre()));
        contentPanel.add(crearCampoInfo("Email", usuarioActual.getEmail()));
        contentPanel.add(crearCampoInfo("Teléfono", 
            usuarioActual.getTelefono().isEmpty() ? "No especificado" : usuarioActual.getTelefono()));
        contentPanel.add(crearCampoInfo("Dirección", 
            usuarioActual.getDireccion().isEmpty() ? "No especificada" : usuarioActual.getDireccion()));
        contentPanel.add(crearCampoInfo("Domicilio Fiscal", 
            usuarioActual.getDomicilioFiscal() != null ? 
            usuarioActual.getDomicilioFiscal().getNombre() : "No especificado"));
        
        panel.add(contentPanel, BorderLayout.CENTER);
        JButton btnEditar = new JButton("Editar Información");
        btnEditar.setFont(FONT_SUBTITULO);
        btnEditar.setBackground(COLOR_TEXTO_SECUNDARIO);
        btnEditar.setForeground(Color.WHITE);
        btnEditar.setFocusPainted(false);
        btnEditar.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        btnEditar.setEnabled(false);
        btnEditar.setToolTipText("Funcionalidad próximamente disponible");
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(getColorCard());
        buttonPanel.add(btnEditar);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelPerfilFinanciero() {
        JPanel panel = crearCard();
        panel.setLayout(new BorderLayout(0, 15));
        JLabel lblTitulo = new JLabel("Perfil Financiero");
        lblTitulo.setFont(FONT_TITULO);
        lblTitulo.setForeground(getColorTextoPrincipal());
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panel.add(lblTitulo, BorderLayout.NORTH);
        PerfilFinanciero perfil = usuarioActual.getPerfilFinanciero();
        JPanel contentPanel = new JPanel(new GridLayout(4, 1, 0, 15));
        contentPanel.setBackground(getColorCard());
        if (perfil != null) {
        	contentPanel.add(crearCampoInfo("Perfil de Riesgo", perfil.getPerfilRiesgo().toString()));
            contentPanel.add(crearCampoInfo("Horizonte Temporal", perfil.getHorizonte() + " años"));
            contentPanel.add(crearCampoInfo("Nivel de Conocimiento", perfil.getNivel().toString()));
        } else {
        	contentPanel.add(crearCampoInfo("Perfil de Riesgo", "No disponible"));
            contentPanel.add(crearCampoInfo("Horizonte Temporal", "No disponible"));
            contentPanel.add(crearCampoInfo("Nivel de Conocimiento", "No disponible"));
        }
        StringBuilder tiposProducto = new StringBuilder();
        if (perfil != null) {
            if (perfil.getTiposProducto().isEmpty()) {
                tiposProducto.append("No especificado");
            } else {
                for (int i = 0; i < perfil.getTiposProducto().size(); i++) {
                    tiposProducto.append(perfil.getTiposProducto().get(i).toString());
                    if (i < perfil.getTiposProducto().size() - 1) {
                        tiposProducto.append(", ");
                    }
                }
            }
        } else {
        	tiposProducto.append("Perfil no configurado");
        }

        contentPanel.add(crearCampoInfo("Productos de Interés", tiposProducto.toString()));
        
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelConfiguracion() {
        JPanel panel = crearCard();
        panel.setLayout(new BorderLayout(0, 15));
        JLabel lblTitulo = new JLabel("Configuración de Cuenta");
        lblTitulo.setFont(FONT_TITULO);
        lblTitulo.setForeground(getColorTextoPrincipal());
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panel.add(lblTitulo, BorderLayout.NORTH);
        JPanel contentPanel = new JPanel(new GridLayout(3, 1, 0, 10));
        contentPanel.setBackground(getColorCard());
        JPanel panelPassword = new JPanel(new BorderLayout(10, 5));
        panelPassword.setBackground(getColorCard());
        JLabel lblPassword = new JLabel("Contraseña");
        lblPassword.setFont(FONT_SUBTITULO);
        lblPassword.setForeground(getColorTextoPrincipal());
        JButton btnCambiarPassword = new JButton("Cambiar contraseña");
        btnCambiarPassword.setFont(FONT_NORMAL2);
        btnCambiarPassword.setBackground(COLOR_ACENTO);
        btnCambiarPassword.setForeground(Color.WHITE);
        btnCambiarPassword.setPreferredSize(new Dimension(140, 25));
        btnCambiarPassword.setFocusPainted(false);
        btnCambiarPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCambiarPassword.addActionListener(e -> mostrarDialogoCambiarPassword());
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        panelBoton.setBackground(getColorCard());
        panelBoton.add(Box.createVerticalStrut(90));
        panelBoton.add(btnCambiarPassword);
        panelPassword.add(lblPassword, BorderLayout.WEST);
        panelPassword.add(panelBoton, BorderLayout.EAST);
        contentPanel.add(panelPassword);
        JPanel panelTema = new JPanel(new BorderLayout(10, 5));
        panelTema.setBackground(getColorCard());
        JLabel lblTema = new JLabel("Tema de Interfaz");
        lblTema.setFont(FONT_SUBTITULO);
        lblTema.setForeground(getColorTextoPrincipal());
        JToggleButton toggleTema = new JToggleButton(temaOscuro ? "Oscuro" : "Claro");
        toggleTema.setFont(FONT_NORMAL2);
        toggleTema.setSelected(temaOscuro);
        toggleTema.setFocusPainted(false);
        toggleTema.setCursor(new Cursor(Cursor.HAND_CURSOR));
        toggleTema.addActionListener(e -> {
            temaOscuro = !temaOscuro;
            toggleTema.setText(temaOscuro ? "Oscuro" : "Claro");
            actualizarTema();
        });
        panelTema.add(lblTema, BorderLayout.WEST);
        panelTema.add(toggleTema, BorderLayout.EAST);
        contentPanel.add(panelTema);
        JPanel panelNotif = new JPanel(new BorderLayout(10, 5));
        panelNotif.setBackground(getColorCard());
        JLabel lblNotif = new JLabel("Notificaciones por Email");
        lblNotif.setFont(FONT_SUBTITULO);
        lblNotif.setForeground(getColorTextoPrincipal());
        JCheckBox checkNotif = new JCheckBox("Activar");
        checkNotif.setFont(FONT_NORMAL2);
        checkNotif.setSelected(true);
        checkNotif.setBackground(getColorCard());
        checkNotif.setForeground(getColorTextoPrincipal());
        checkNotif.setFocusPainted(false);
        checkNotif.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                "Preferencia guardada (demostración)", 
                "Notificaciones", 
                JOptionPane.INFORMATION_MESSAGE);
        });
        panelNotif.add(lblNotif, BorderLayout.WEST);
        panelNotif.add(checkNotif, BorderLayout.EAST);
        contentPanel.add(panelNotif);
        
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelEstadisticas() {
        JPanel panel = crearCard();
        panel.setLayout(new BorderLayout(0, 15));
        JLabel lblTitulo = new JLabel("Estadísticas de Cuenta");
        lblTitulo.setFont(FONT_TITULO);
        lblTitulo.setForeground(getColorTextoPrincipal());
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panel.add(lblTitulo, BorderLayout.NORTH);
        JPanel contentPanel = new JPanel(new GridLayout(4, 1, 0, 15));
        contentPanel.setBackground(getColorCard());
        int totalCarteras = usuarioActual.getCarteras().size();
        int totalOperaciones = 0;
        for (Cartera c : usuarioActual.getCarteras()) {
            totalOperaciones += c.getOperaciones().size();
        }
        double patrimonioTotal = usuarioActual.calcularPatrimonioTotal();
        
        contentPanel.add(crearEstadistica("Carteras Activas", String.valueOf(totalCarteras), "Icono"));
        contentPanel.add(crearEstadistica("Operaciones Totales", String.valueOf(totalOperaciones), "Icono"));
        contentPanel.add(crearEstadistica("Patrimonio Total", String.format("%.2f €", patrimonioTotal), "Icono"));
        contentPanel.add(crearEstadistica("Estado de Cuenta", "Verificada", "Icono"));
        
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearCampoInfo(String label, String valor) {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(getColorCard());
        
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(FONT_NORMAL2);
        lblLabel.setForeground(COLOR_TEXTO_SECUNDARIO);
        
        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(FONT_SUBTITULO);
        lblValor.setForeground(getColorTextoPrincipal());
        
        panel.add(lblLabel, BorderLayout.NORTH);
        panel.add(lblValor, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearEstadistica(String label, String valor, String icono) {
        JPanel panel = new JPanel(new BorderLayout(15, 5));
        panel.setBackground(getColorCard());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDE, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel lblIcono = new JLabel(icono);
        lblIcono.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        
        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 2));
        textPanel.setBackground(getColorCard());
        
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(FONT_NORMAL2);
        lblLabel.setForeground(COLOR_TEXTO_SECUNDARIO);
        
        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(FONT_SUBTITULO);
        lblValor.setForeground(getColorTextoPrincipal());
        
        textPanel.add(lblLabel);
        textPanel.add(lblValor);
        
        panel.add(lblIcono, BorderLayout.WEST);
        panel.add(textPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void mostrarDialogoCambiarPassword() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), 
                                      "Cambiar Contraseña", true);
        dialog.setLayout(new BorderLayout(15, 15));
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        
        JPanel mainPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JPanel panelActual = new JPanel(new BorderLayout(5, 5));
        panelActual.add(new JLabel("Contraseña Actual:"), BorderLayout.NORTH);
        JPasswordField txtActual = new JPasswordField();
        txtActual.setFont(FONT_NORMAL1);
        panelActual.add(txtActual, BorderLayout.CENTER);
        mainPanel.add(panelActual);
        JPanel panelNueva = new JPanel(new BorderLayout(5, 5));
        panelNueva.add(new JLabel("Contraseña Nueva:"), BorderLayout.NORTH);
        JPasswordField txtNueva = new JPasswordField();
        txtNueva.setFont(FONT_NORMAL1);
        panelNueva.add(txtNueva, BorderLayout.CENTER);
        mainPanel.add(panelNueva);
        JPanel panelConfirmar = new JPanel(new BorderLayout(5, 5));
        panelConfirmar.add(new JLabel("Confirmar Contraseña:"), BorderLayout.NORTH);
        JPasswordField txtConfirmar = new JPasswordField();
        txtConfirmar.setFont(FONT_NORMAL1);
        panelConfirmar.add(txtConfirmar, BorderLayout.CENTER);
        mainPanel.add(panelConfirmar);
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dialog.dispose());
        
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(COLOR_ACENTO);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.addActionListener(e -> {
            String actual = new String(txtActual.getPassword());
            String nueva = new String(txtNueva.getPassword());
            String confirmar = new String(txtConfirmar.getPassword());
            if (!actual.equals(usuarioActual.getPassword())) {
                JOptionPane.showMessageDialog(dialog, 
                    "La contraseña actual es incorrecta", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (nueva.length() < 6) {
                JOptionPane.showMessageDialog(dialog, 
                    "La contraseña debe tener al menos 6 caracteres", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!nueva.equals(confirmar)) {
                JOptionPane.showMessageDialog(dialog, 
                    "Las contraseñas no coinciden", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            usuarioActual.setPassword(nueva);
            JOptionPane.showMessageDialog(dialog, 
                "Contraseña cambiada exitosamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });
        
        panelBotones.add(btnCancelar);
        panelBotones.add(btnGuardar);
        mainPanel.add(panelBotones);
        
        dialog.add(mainPanel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }
    
    private JPanel crearCard() {
        JPanel card = new JPanel();
        card.setBackground(getColorCard());
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDE, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        return card;
    }
    
    private void actualizarColores() {
        setBackground(getColorFondo());
    }
    
    private void actualizarTema() {
        removeAll();
        initComponents();
        revalidate();
        repaint();
        
        JOptionPane.showMessageDialog(this, 
            "Tema actualizado. Algunas partes pueden requerir reiniciar la aplicación.", 
            "Tema Cambiado", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private Color getColorFondo() {
        return temaOscuro ? COLOR_FONDO_OSCURO : COLOR_FONDO_CLARO;
    }
    
    private Color getColorCard() {
        return temaOscuro ? COLOR_CARD_OSCURO : COLOR_CARD_CLARO;
    }
    
    private Color getColorTextoPrincipal() {
        return temaOscuro ? COLOR_TEXTO_OSCURO : COLOR_TEXTO_PRINCIPAL;
    }
    
    public void refrescarDatos() {
        removeAll();
        initComponents();
        revalidate();
        repaint();
    }
}