package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import es.deusto.eleutradia.db.EleutradiaDBManager;
import es.deusto.eleutradia.domain.Cartera;
import es.deusto.eleutradia.domain.PerfilFinanciero;
import es.deusto.eleutradia.domain.TipoProducto;
import es.deusto.eleutradia.domain.Usuario;
import es.deusto.eleutradia.gui.style.UITema;

import static es.deusto.eleutradia.gui.style.UITema.*;

public class PanelPerfil extends JPanel {
	
    private static final long serialVersionUID = 1L;
    
    private EleutradiaDBManager dbManager = new EleutradiaDBManager();
    private Usuario usuario;
    private VentanaPrincipal ventanaPrincipal;
    private UITema uiTema;
    
    public PanelPerfil(Usuario usuario, VentanaPrincipal ventanaPrincipal) {
        this.usuario = usuario;
        this.ventanaPrincipal = ventanaPrincipal;
        this.uiTema = UITema.getInstancia();
        this.setLayout(new BorderLayout(15, 15));
        this.setBackground(uiTema.colorFondo);
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        initComponents();
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(uiTema.colorFondo);
        mainPanel.add(crearPanelCabecera(), BorderLayout.NORTH);
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        centerPanel.setBackground(uiTema.colorFondo);
        centerPanel.add(crearPanelInformacionPersonal());
        centerPanel.add(crearPanelPerfilFinanciero());
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        bottomPanel.setBackground(uiTema.colorFondo);
        bottomPanel.add(crearPanelConfiguracion());
        bottomPanel.add(crearPanelEstadisticas());
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setUI(personalizarScrollBarUI());
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private JPanel crearPanelCabecera() {
        JPanel panel = crearCard();
        panel.setLayout(new BorderLayout(20, 10));
        String inicialNombre = String.valueOf(usuario.getNombre().charAt(0)).toUpperCase();
        String inicialApellido = String.valueOf(usuario.getNombre().split("\\s+")[1].charAt(0)).toUpperCase();
        JLabel lblAvatar = new JLabel(inicialNombre + inicialApellido);
        lblAvatar.setForeground(uiTema.colorTexto);
        lblAvatar.setFont(new Font("Segoe UI", Font.BOLD, 56));
        lblAvatar.setHorizontalAlignment(JLabel.CENTER);
        lblAvatar.setPreferredSize(new Dimension(100, 100));
        panel.add(lblAvatar, BorderLayout.WEST);
        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        infoPanel.setBackground(uiTema.colorPanel);
        
        JLabel lblNombre = new JLabel(usuario.getNombre());
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblNombre.setForeground(uiTema.colorTexto);
        
        JLabel lblEmail = new JLabel(usuario.getEmail());
        lblEmail.setFont(CUERPO_GRANDE);
        lblEmail.setForeground(GRIS_MEDIO);
        
        JLabel lblTipo = new JLabel("Cuenta Activa • " + usuario.getCarteras().size() + " Cartera(s)");
        lblTipo.setFont(CUERPO_GRANDE);
        lblTipo.setForeground(VERDE_CLARO);
        
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
        lblTitulo.setFont(TITULO_MEDIO);
        lblTitulo.setForeground(uiTema.colorTexto);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panel.add(lblTitulo, BorderLayout.NORTH);
        
        // Content
        JPanel contentPanel = new JPanel(new GridLayout(5, 1, 0, 15));
        contentPanel.setBackground(uiTema.colorPanel);
        
        contentPanel.add(crearCampoInfo("Nombre Completo", usuario.getNombre()));
        contentPanel.add(crearCampoInfo("Email", usuario.getEmail()));
        contentPanel.add(crearCampoInfo("Teléfono", 
            usuario.getTelefono().isEmpty() ? "No especificado" : usuario.getTelefono()));
        contentPanel.add(crearCampoInfo("Dirección", 
            usuario.getDireccion().isEmpty() ? "No especificada" : usuario.getDireccion()));
        contentPanel.add(crearCampoInfo("Domicilio Fiscal", 
            usuario.getDomicilioFiscal() != null ? 
            usuario.getDomicilioFiscal().getNombre() : "No especificado"));
        
        panel.add(contentPanel, BorderLayout.CENTER);
        JButton btnEditar = new JButton("Editar Información");
        btnEditar.setFont(SUBTITULO_MEDIO);
        btnEditar.setBackground(GRIS_MEDIO);
        btnEditar.setForeground(Color.WHITE);
        btnEditar.setFocusPainted(false);
        btnEditar.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        btnEditar.setEnabled(true);
        btnEditar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEditar.setBackground(AZUL_CLARO);
        btnEditar.setToolTipText("Editar información personal");

        btnEditar.addActionListener(e -> mostrarEditarInformacion());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(uiTema.colorPanel);
        buttonPanel.add(btnEditar);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelPerfilFinanciero() {
        JPanel panel = crearCard();
        panel.setLayout(new BorderLayout(0, 15));
        JLabel lblTitulo = new JLabel("Perfil Financiero");
        lblTitulo.setFont(TITULO_MEDIO);
        lblTitulo.setForeground(uiTema.colorTexto);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panel.add(lblTitulo, BorderLayout.NORTH);
        PerfilFinanciero perfil = usuario.getPerfilFinanciero();
        JPanel contentPanel = new JPanel(new GridLayout(4, 1, 0, 15));
        contentPanel.setBackground(uiTema.colorPanel);
        if (perfil != null) {
        	contentPanel.add(crearCampoInfo("Perfil de Riesgo", perfil.getPerfilRiesgo().toString()));
            contentPanel.add(crearCampoInfo("Horizonte Temporal", perfil.getHorizonte() + " años"));
            contentPanel.add(crearCampoInfo("Nivel de Conocimiento", perfil.getNivelConocimiento().toString()));
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
                for (TipoProducto tp : perfil.getTiposProducto()) {
                    if (!tiposProducto.isEmpty()) {
                        tiposProducto.append(", ");
                    }
                    tiposProducto.append(tp.getNombre());
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
        lblTitulo.setFont(TITULO_MEDIO);
        lblTitulo.setForeground(uiTema.colorTexto);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panel.add(lblTitulo, BorderLayout.NORTH);
        JPanel contentPanel = new JPanel(new GridLayout(3, 1, 0, 10));
        contentPanel.setBackground(uiTema.colorPanel);
        JPanel panelPassword = new JPanel(new BorderLayout(10, 5));
        panelPassword.setBackground(uiTema.colorPanel);
        JLabel lblPassword = new JLabel("Contraseña");
        lblPassword.setFont(SUBTITULO_MEDIO);
        lblPassword.setForeground(uiTema.colorTexto);
        JButton btnCambiarPassword = new JButton("Cambiar contraseña");
        btnCambiarPassword.setFont(CUERPO_PEQUENO);
        btnCambiarPassword.setBackground(AZUL_CLARO);
        btnCambiarPassword.setForeground(Color.WHITE);
        btnCambiarPassword.setPreferredSize(new Dimension(140, 25));
        btnCambiarPassword.setFocusPainted(false);
        btnCambiarPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCambiarPassword.addActionListener(e -> mostrarDialogoCambiarPassword());
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        panelBoton.setBackground(uiTema.colorPanel);
        panelBoton.add(btnCambiarPassword);
        panelPassword.add(lblPassword, BorderLayout.WEST);
        panelPassword.add(panelBoton, BorderLayout.EAST);
        contentPanel.add(panelPassword);
        JPanel panelTema = new JPanel(new BorderLayout(10, 5));
        panelTema.setBackground(uiTema.colorPanel);
        JLabel lblTema = new JLabel("Tema de Interfaz");
        lblTema.setFont(SUBTITULO_MEDIO);
        lblTema.setForeground(uiTema.colorTexto);
        JToggleButton toggleTema = new JToggleButton(uiTema.esTemaOscuro() ? "Oscuro" : "Claro");
        toggleTema.setFont(CUERPO_PEQUENO);
        toggleTema.setBackground(AZUL_CLARO);
        toggleTema.setForeground(Color.WHITE);
        toggleTema.setMaximumSize(new Dimension(100, 25));
        toggleTema.setSelected(uiTema.esTemaOscuro());
        toggleTema.setFocusPainted(false);
        toggleTema.setCursor(new Cursor(Cursor.HAND_CURSOR));
        toggleTema.addActionListener(e -> {
            uiTema.cambiarTema();
            toggleTema.setText(uiTema.esTemaOscuro() ? "Oscuro" : "Claro");
            if (ventanaPrincipal != null) {
                ventanaPrincipal.actualizarTema();
            }
        });
        panelTema.add(lblTema, BorderLayout.WEST);
        panelTema.add(toggleTema, BorderLayout.EAST);
        contentPanel.add(panelTema);
        
        JPanel panelNotif = new JPanel(new BorderLayout(10, 5));
        panelNotif.setBackground(uiTema.colorPanel);
        JLabel lblNotif = new JLabel("Notificaciones por Email");
        lblNotif.setFont(SUBTITULO_MEDIO);
        lblNotif.setForeground(uiTema.colorTexto);
        JCheckBox checkNotif = new JCheckBox("Activar");
        checkNotif.setFont(CUERPO_PEQUENO);
        checkNotif.setSelected(true);
        checkNotif.setBackground(uiTema.colorPanel);
        checkNotif.setForeground(uiTema.colorTexto);
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
        lblTitulo.setFont(TITULO_MEDIO);
        lblTitulo.setForeground(uiTema.colorTexto);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panel.add(lblTitulo, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new GridLayout(4, 1, 0, 15));
        contentPanel.setBackground(uiTema.colorPanel);

        int totalCarteras = usuario.getCarteras().size();
        int totalOperaciones = 0;
        for (Cartera c : usuario.getCarteras()) {
            totalOperaciones += c.getOperaciones().size();
        }
        double patrimonioTotal = usuario.calcularPatrimonioTotal();

        contentPanel.add(crearIcono("Carteras Activas", String.valueOf(totalCarteras), "/images/iconos/wallet.png"));
        contentPanel.add(crearIcono("Operaciones Totales", String.valueOf(totalOperaciones), "/images/iconos/pie-chart.png"));
        contentPanel.add(crearIcono("Patrimonio Total", String.format("%.2f €", patrimonioTotal), "/images/iconos/money-bag.png"));
        contentPanel.add(crearIcono("Estado de Cuenta", "Verificada", "/images/iconos/setting.png"));

        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }
    
    private JPanel crearIcono(String titulo, String valor, String iconPath) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setOpaque(false);

        // --- Icon label ---
        JLabel lblIcono = new JLabel();
        lblIcono.setHorizontalAlignment(SwingConstants.CENTER);
        lblIcono.setPreferredSize(new Dimension(30, 30));

        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
            Image scaled = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            lblIcono.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            System.err.println("Icon not found: " + iconPath);
            lblIcono.setText("?");
        }

        // --- Text labels ---
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(CUERPO_GRANDE);
        lblTitulo.setForeground(uiTema.colorTexto);

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(CUERPO_GRANDE);
        lblValor.setForeground(GRIS_MEDIO);

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        textPanel.add(lblTitulo);
        textPanel.add(lblValor);

        panel.add(lblIcono, BorderLayout.WEST);
        panel.add(textPanel, BorderLayout.CENTER);

        return panel;
    }
    
    private JPanel crearCampoInfo(String label, String valor) {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(uiTema.colorPanel);
        
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(CUERPO_PEQUENO);
        lblLabel.setForeground(GRIS_MEDIO);
        
        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(SUBTITULO_MEDIO);
        lblValor.setForeground(uiTema.colorTexto);
        
        panel.add(lblLabel, BorderLayout.NORTH);
        panel.add(lblValor, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearEstadistica(String label, String valor, String icono) {
        JPanel panel = new JPanel(new BorderLayout(15, 5));
        panel.setBackground(uiTema.colorPanel);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MAIN_BORDE_CLARO, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel lblIcono = new JLabel(icono);
        lblIcono.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        
        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 2));
        textPanel.setBackground(uiTema.colorPanel);
        
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(CUERPO_PEQUENO);
        lblLabel.setForeground(GRIS_MEDIO);
        
        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(SUBTITULO_MEDIO);
        lblValor.setForeground(uiTema.colorTexto);
        
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
        txtActual.setFont(CUERPO_GRANDE);
        panelActual.add(txtActual, BorderLayout.CENTER);
        mainPanel.add(panelActual);
        JPanel panelNueva = new JPanel(new BorderLayout(5, 5));
        panelNueva.add(new JLabel("Contraseña Nueva:"), BorderLayout.NORTH);
        JPasswordField txtNueva = new JPasswordField();
        txtNueva.setFont(CUERPO_GRANDE);
        panelNueva.add(txtNueva, BorderLayout.CENTER);
        mainPanel.add(panelNueva);
        JPanel panelConfirmar = new JPanel(new BorderLayout(5, 5));
        panelConfirmar.add(new JLabel("Confirmar Contraseña:"), BorderLayout.NORTH);
        JPasswordField txtConfirmar = new JPasswordField();
        txtConfirmar.setFont(CUERPO_GRANDE);
        panelConfirmar.add(txtConfirmar, BorderLayout.CENTER);
        mainPanel.add(panelConfirmar);
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dialog.dispose());
        
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(AZUL_CLARO);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.addActionListener(e -> {
            String passActual = new String(txtActual.getPassword());
            String passNueva = new String(txtNueva.getPassword());
            String passConfirm = new String(txtConfirmar.getPassword());

            if (!passActual.equals(usuario.getPassword())) {
                JOptionPane.showMessageDialog(dialog, "La contraseña actual es incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!passNueva.equals(passConfirm)) {
                JOptionPane.showMessageDialog(dialog, "Las contraseñas nuevas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (passNueva.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "La contraseña no puede estar vacía.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            boolean exito = dbManager.editarContrasena(usuario, passNueva);

            if (exito) {
                usuario.setPassword(passNueva); 
                JOptionPane.showMessageDialog(dialog, "Contraseña cambiada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Error al guardar la nueva contraseña en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        panelBotones.add(btnCancelar);
        panelBotones.add(btnGuardar);
        mainPanel.add(panelBotones);
        
        dialog.add(mainPanel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }
    
    private JPanel crearCard() {
        JPanel card = new JPanel();
        card.setBackground(uiTema.colorPanel);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MAIN_BORDE_CLARO, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        return card;
    }
    
    private void mostrarEditarInformacion() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), 
                                     "Editar Información Personal", true);
        dialog.setLayout(new BorderLayout(15, 15));
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);

        JPanel mainPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelNombre = new JPanel(new BorderLayout(5, 5));
        panelNombre.add(new JLabel("Nombre Completo:"), BorderLayout.NORTH);
        JTextField txtNombre = new JTextField(usuario.getNombre());
        txtNombre.setFont(CUERPO_GRANDE);
        panelNombre.add(txtNombre, BorderLayout.CENTER);
        mainPanel.add(panelNombre);

        JPanel panelEmail = new JPanel(new BorderLayout(5, 5));
        panelEmail.add(new JLabel("Email:"), BorderLayout.NORTH);
        JTextField txtEmail = new JTextField(usuario.getEmail());
        txtEmail.setFont(CUERPO_GRANDE);
        panelEmail.add(txtEmail, BorderLayout.CENTER);
        mainPanel.add(panelEmail);

        JPanel panelTelefono = new JPanel(new BorderLayout(5, 5));
        panelTelefono.add(new JLabel("Teléfono:"), BorderLayout.NORTH);
        JTextField txtTelefono = new JTextField(usuario.getTelefono());
        txtTelefono.setFont(CUERPO_GRANDE);
        panelTelefono.add(txtTelefono, BorderLayout.CENTER);
        mainPanel.add(panelTelefono);

        JPanel panelDireccion = new JPanel(new BorderLayout(5, 5));
        panelDireccion.add(new JLabel("Dirección:"), BorderLayout.NORTH);
        JTextField txtDireccion = new JTextField(usuario.getDireccion());
        txtDireccion.setFont(CUERPO_GRANDE);
        panelDireccion.add(txtDireccion, BorderLayout.CENTER);
        mainPanel.add(panelDireccion);

        JPanel panelFiscal = new JPanel(new BorderLayout(5, 5));
        panelFiscal.add(new JLabel("Domicilio Fiscal:"), BorderLayout.NORTH);
        JTextField txtFiscal = new JTextField(
            usuario.getDomicilioFiscal() != null ? usuario.getDomicilioFiscal().getNombre() : ""
        );
        txtFiscal.setFont(CUERPO_GRANDE);
        panelFiscal.add(txtFiscal, BorderLayout.CENTER);
        mainPanel.add(panelFiscal);
       
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCancelar = new JButton("Cancelar");
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(AZUL_CLARO);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);

        btnCancelar.addActionListener(e -> dialog.dispose());
        btnGuardar.addActionListener(e -> {
            usuario.setEmail(txtEmail.getText());
            usuario.setTelefono(txtTelefono.getText());
            usuario.setDireccion(txtDireccion.getText());
            boolean exito = dbManager.editarPerfil(usuario);
            
            if (exito) {
                JOptionPane.showMessageDialog(dialog,
                    "Información actualizada y guardada correctamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
                refrescarDatos();
            } else {
                JOptionPane.showMessageDialog(dialog,
                    "Error al guardar los datos en la base de datos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        panelBotones.add(btnCancelar);
        panelBotones.add(btnGuardar);

        dialog.add(mainPanel, BorderLayout.CENTER);
        dialog.add(panelBotones, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    public void refrescarDatos() {
        removeAll();
        initComponents();
        revalidate();
        repaint();
    }
}