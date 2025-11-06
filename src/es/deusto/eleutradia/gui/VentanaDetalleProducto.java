package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.math.BigDecimal;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.deusto.eleutradia.domain.PlazoRentabilidad;
import es.deusto.eleutradia.domain.ProductoFinanciero;

public class VentanaDetalleProducto extends JDialog {

	private static final long serialVersionUID = 1L;

	public VentanaDetalleProducto(JFrame padre, ProductoFinanciero producto, boolean modal) {
        super(padre, "Detalles del producto", modal);
        this.setSize(600, 450);
        this.setLocationRelativeTo(padre);
        this.setLayout(new BorderLayout());
        this.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Nombre: " + producto.getNombre()));
        panel.add(new JLabel("Tipo: " + producto.getTipoProducto()));
        for (Map.Entry<PlazoRentabilidad, BigDecimal> entry : producto.getRentabilidades().entrySet()) {
        	String plazo = entry.getKey().getDefinicion();
        	BigDecimal valor = entry.getValue();
            panel.add(new JLabel("Rentabilidad a " + plazo + ": " + valor + "%"));
        }
        panel.add(new JLabel("Riesgo: " + producto.getTipoProducto().getRiesgo()));
        panel.add(new JLabel("Importe mínimo: " + producto.getTipoProducto().getImporteMin()));
        if (producto.getGestora() != null) {
            panel.add(new JLabel("Gestora: " + producto.getGestora().getNombreCompleto()));
        }

        add(panel, BorderLayout.CENTER);

        JButton cerrar = new JButton("Cerrar");
        cerrar.setBackground(Color.GRAY);
        cerrar.setFocusPainted(false);
        cerrar.addActionListener(e -> dispose());
        this.add(cerrar, BorderLayout.SOUTH);

        setVisible(true);
    }
	
	
}
