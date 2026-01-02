package es.deusto.eleutradia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import static es.deusto.eleutradia.gui.style.UITema.*;

public class PanelSimulador extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int NUM_PRODUCTOS = 4;
    private static final double SALDO_INICIAL = 1000.0;

    private JButton btnIniciar;
    private JButton btnParar;
    private JButton btnVolver;
    private JLabel lblSaldo;
    
    private JLabel[] lblNombreProducto = new JLabel[NUM_PRODUCTOS];
    private JLabel[] lblPrecioActual = new JLabel[NUM_PRODUCTOS];
    private JLabel[] lblEstado = new JLabel[NUM_PRODUCTOS]; // Flecha y texto
    private JLabel[] lblCantidadProducto = new JLabel[NUM_PRODUCTOS];
    
    private JButton[] btnComprar = new JButton[NUM_PRODUCTOS];
    private JButton[] btnVender = new JButton[NUM_PRODUCTOS];

    private static final String[] NOMBRES = {"Tecnológica", "Oro", "Criptomoneda", "Renovables"};
    private static final double[] PRECIOS_BASE = {150.0, 1200.0, 500.0, 80.0};
    
    private double saldoActual = SALDO_INICIAL;
    private int[] cartera = new int[NUM_PRODUCTOS];

    public enum EstadoMercado {
        ESTABLE(Color.GRAY, "="),
        SUBIENDO(new Color(40, 167, 69), "▲"),
        BAJANDO(new Color(220, 53, 69), "▼");
        
        private Color color;
        private String simbolo;
        
        private EstadoMercado(Color color, String simbolo) {
            this.color = color;
            this.simbolo = simbolo;
        }
        
        public Color getColor() { return color; }
        public String getSimbolo() { return simbolo; }
    }

    private HiloProducto[] hilosProductos = new HiloProducto[NUM_PRODUCTOS];
    
    private final DecimalFormat df = new DecimalFormat("#,##0.00 $");

    public PanelSimulador() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Simulador de Trading"));

        JPanel panelCentral = new JPanel(new GridLayout(1, NUM_PRODUCTOS, 10, 0));
        
        JPanel panelInfo = new JPanel();
        lblSaldo = new JLabel("SALDO: " + df.format(saldoActual));
        lblSaldo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panelInfo.add(lblSaldo);
        add(panelInfo, BorderLayout.NORTH);
        
        for (int i = 0; i < NUM_PRODUCTOS; i++) {
            panelCentral.add(crearPanelProducto(i));
        }

        add(panelCentral, BorderLayout.CENTER);

        JPanel panelBotonesPrincipales = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotonesPrincipales.setBackground(new Color(245, 245, 245));
        panelBotonesPrincipales.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        
        btnIniciar = new JButton("Iniciar Simulación");
        btnIniciar.setMinimumSize(new Dimension(150, 45));
        btnIniciar.setPreferredSize(new Dimension(150, 45));
        btnIniciar.setBackground(VERDE_CLARO);
        btnIniciar.setForeground(Color.WHITE);
        btnIniciar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnIniciar.setBorderPainted(false);
        btnIniciar.setContentAreaFilled(false);
        btnIniciar.setOpaque(true);
        btnIniciar.setFocusPainted(false);
        btnIniciar.addActionListener(e -> {        
    		
            saldoActual = SALDO_INICIAL;
            lblSaldo.setText("SALDO: " + df.format(saldoActual));
            
            btnIniciar.setEnabled(false);
            btnParar.setEnabled(true);
            
            for (int i = 0; i < NUM_PRODUCTOS; i++) {
            	
                cambiarEstadoProducto(i, EstadoMercado.ESTABLE);
                cambiarPrecioProducto(i, PRECIOS_BASE[i]);
                actualizarCantidad(i, 0);
                habilitarBotonesProducto(i, true);
                cartera[i] = 0;
                
                hilosProductos[i] = new HiloProducto(i, PRECIOS_BASE[i]);
                hilosProductos[i].start();            
            }
        });
        btnIniciar.addMouseListener(myAdapterVerde);


        btnParar = new JButton("Detener Simulación");
        btnParar.setMinimumSize(new Dimension(150, 45));
        btnParar.setPreferredSize(new Dimension(150, 45));
        btnParar.setEnabled(false);
        btnParar.setBackground(AZUL_CLARO);
        btnParar.setForeground(Color.WHITE);
        btnParar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnParar.setBorderPainted(false);
        btnParar.setContentAreaFilled(false);
        btnParar.setOpaque(true);
        btnParar.setFocusPainted(false);
        btnParar.addActionListener(e -> detenerJuego("Simulación detenida manualmente."));
        btnParar.addMouseListener(myAdapterAzul);

        
        btnVolver = new JButton("Volver a Cursos");
        btnVolver.setMinimumSize(new Dimension(150, 45));
		btnVolver.setPreferredSize(new Dimension(150, 45));
        btnVolver.setBackground(GRIS_CLARO);
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnVolver.setBorderPainted(false);
        btnVolver.setContentAreaFilled(false);
        btnVolver.setOpaque(true);
        btnVolver.setFocusPainted(false);
        btnVolver.addMouseListener(myAdapterGris);


        panelBotonesPrincipales.add(btnIniciar);
        panelBotonesPrincipales.add(btnParar);
        panelBotonesPrincipales.add(btnVolver);
        
        add(panelBotonesPrincipales, BorderLayout.SOUTH);
    }

    private JPanel crearPanelProducto(int num) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        lblNombreProducto[num] = new JLabel(NOMBRES[num], JLabel.CENTER);
        lblNombreProducto[num].setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        lblPrecioActual[num] = new JLabel(df.format(PRECIOS_BASE[num]), JLabel.CENTER);
        lblPrecioActual[num].setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblPrecioActual[num].setOpaque(true);
        lblPrecioActual[num].setBackground(Color.WHITE);

        lblEstado[num] = new JLabel("=", JLabel.CENTER);
        lblEstado[num].setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblEstado[num].setOpaque(true);
        lblEstado[num].setForeground(Color.GRAY);

        JPanel panelBotones = new JPanel(new GridLayout(3, 1));
        
        lblCantidadProducto[num] = new JLabel("En cartera: 0", JLabel.CENTER);
        
        btnComprar[num] = new JButton("Comprar");
        btnComprar[num].setEnabled(false);
        btnComprar[num].setBackground(new Color(200, 230, 200));
        btnComprar[num].addActionListener(e -> {

            double precio = hilosProductos[num].getPrecioActual();
            realizarTransaccion(num, -precio);
        });

        btnVender[num] = new JButton("Vender");
        btnVender[num].setEnabled(false);
        btnVender[num].setBackground(new Color(230, 200, 200));
        btnVender[num].addActionListener(e -> {

            if (cartera[num] > 0) {
                double precio = hilosProductos[num].getPrecioActual();
                realizarTransaccion(num, precio);
            }
        });

        panelBotones.add(lblCantidadProducto[num]);
        panelBotones.add(btnComprar[num]);
        panelBotones.add(btnVender[num]);

        panel.add(lblNombreProducto[num], BorderLayout.NORTH);
        panel.add(lblPrecioActual[num], BorderLayout.CENTER);
        panel.add(lblEstado[num], BorderLayout.SOUTH);
        
        JPanel envoltorio = new JPanel(new BorderLayout());
        envoltorio.add(lblEstado[num], BorderLayout.NORTH);
        envoltorio.add(panelBotones, BorderLayout.CENTER);
        
        panel.add(envoltorio, BorderLayout.SOUTH);

        return panel;
    }
    
    
    protected void cambiarPrecioProducto(int num, double precio) {
        SwingUtilities.invokeLater(() -> lblPrecioActual[num].setText(df.format(precio)));
    }
    
    protected void cambiarEstadoProducto(int num, EstadoMercado estado) {
        SwingUtilities.invokeLater(() -> {
            lblEstado[num].setText(estado.getSimbolo());
            lblEstado[num].setForeground(estado.getColor());    
        });
    }
    
    protected void actualizarCantidad(int num, int cantidad) {
        SwingUtilities.invokeLater(() -> lblCantidadProducto[num].setText("En cartera: " + cantidad));
    }
    
    protected void habilitarBotonesProducto(int num, boolean value) {
        SwingUtilities.invokeLater(() -> {
            btnComprar[num].setEnabled(value);
            btnVender[num].setEnabled(value);
        });
    }

    private void detenerJuego(String mensaje) {
        btnParar.setEnabled(false);
        btnIniciar.setEnabled(true);

        for (int i = 0; i < NUM_PRODUCTOS; i++) {
            if (hilosProductos[i] != null) {
                hilosProductos[i].interrupt();
            }
            habilitarBotonesProducto(i, false);
        }
        
        if (mensaje != null) {
            JOptionPane.showMessageDialog(this, mensaje);
        }
    }

    private synchronized void realizarTransaccion(int numProducto, double cantidad) {
        double nuevoSaldo = saldoActual + cantidad;
        
        if (cantidad < 0 && nuevoSaldo < 0) {
            JOptionPane.showMessageDialog(this, "Saldo insuficiente");
            return;
        }

        saldoActual = nuevoSaldo;

        if (cantidad < 0) cartera[numProducto]++;
        else cartera[numProducto]--;
        
        lblSaldo.setText("SALDO: " + df.format(saldoActual));
        actualizarCantidad(numProducto, cartera[numProducto]);
        
        if (saldoActual >= SALDO_INICIAL * 2) {
            detenerJuego("¡VICTORIA! Has doblado tu dinero.");
        } else if (saldoActual < 500 && totalCartera() == 0) {
            detenerJuego("GAME OVER. Bancarrota.");
        }
    }
    
    private int totalCartera() {
        int total = 0;
        for (int c : cartera) total += c;
        return total;
    }

    private class HiloProducto extends Thread {
        private int idProducto;
        private double precioActual;
        
        public HiloProducto(int id, double precioInicial) {
            this.idProducto = id;
            this.precioActual = precioInicial;
        }
        
        public double getPrecioActual() {
            return precioActual;
        }

        @Override
        public void run() {
            Random rand = new Random();
          
            while (!isInterrupted()) {

                try {
                    Thread.sleep(500 + rand.nextInt(1000));
                } catch (InterruptedException e) {
                    this.interrupt();
                    return;
                }
                
                double precioAnterior = precioActual;
                
                double factor = 1.0 + ((rand.nextDouble() * 0.10) - 0.05);
                precioActual = precioActual * factor;
                if (precioActual < 1.0) precioActual = 1.0;
                
                cambiarPrecioProducto(idProducto, precioActual);
                
                if (precioActual > precioAnterior) {
                    cambiarEstadoProducto(idProducto, EstadoMercado.SUBIENDO);
                } else if (precioActual < precioAnterior) {
                    cambiarEstadoProducto(idProducto, EstadoMercado.BAJANDO);
                } else {
                    cambiarEstadoProducto(idProducto, EstadoMercado.ESTABLE);
                }
            }
        }       
    }
    
    //IAG (Gemini)
  	//SIN MODIFICAR
    public void addAccionVolver(ActionListener listener) {
        if (btnVolver != null) {
            btnVolver.addActionListener(listener);
        }
    }
	//END IAG

}