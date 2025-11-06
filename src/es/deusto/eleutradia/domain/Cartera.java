package es.deusto.eleutradia.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Cartera {
    private String nombre;
    private double saldo;
    private PerfilRiesgo perfilRiesgo;
    private Divisa divisa;
    private List<Operacion> operaciones;
    
	public Cartera() {
		this.nombre = "";
		this.saldo = 0.0;
		this.perfilRiesgo = PerfilRiesgo.CONSERVADOR;
		this.divisa = Divisa.EUR;
		this.operaciones = new ArrayList<Operacion>();
	}

	public Cartera(String nombre, double saldo, PerfilRiesgo perfilRiesgo,
			Divisa divisa, List<Operacion> operaciones) {
		this.nombre = nombre;
		this.saldo = saldo;
		this.perfilRiesgo = perfilRiesgo;
		this.divisa = divisa;
		this.operaciones = operaciones;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		if (saldo < 0) {
            throw new IllegalArgumentException("El saldo no puede ser negativo");
        }
		this.saldo = saldo;
	}

	public PerfilRiesgo getPerfilRiesgo() {
		return perfilRiesgo;
	}

	public void setPerfilRiesgo(PerfilRiesgo perfilRiesgo) {
		this.perfilRiesgo = perfilRiesgo;
	}

	public Divisa getDivisa() {
		return divisa;
	}

	public void setDivisa(Divisa divisa) {
		this.divisa = divisa;
	}

	public List<Operacion> getOperaciones() {
		return operaciones;
	}

	public void setOperaciones(List<Operacion> operaciones) {
		this.operaciones = operaciones;
	}
	
	public double calcularValorInversiones() {
		double total = 0.0;
		for (Operacion operacion : operaciones) {
			total += operacion.getProdFinanciero().getValorUnitario() * operacion.getCantidad();
		}
		return total;
	}
	
	public double calcularPatrimonio() {
	    return saldo + calcularValorInversiones();
	}
	
	public boolean anadirProducto(ProductoFinanciero producto, double cantidad) {
	    if (producto == null || cantidad <= 0) {
	        JOptionPane.showMessageDialog(null, "Cantidad inválida.", "Error", JOptionPane.ERROR_MESSAGE);
	        return false;
	    };
	    
	    double coste = cantidad*producto.getValorUnitario();
	    if (coste > saldo) {
	        JOptionPane.showMessageDialog(null, "Saldo insuficiente.", "Error", JOptionPane.ERROR_MESSAGE);
	        return false;
	    }
	    
	    boolean productoExiste = false;
	    
	    for (Operacion op : this.operaciones) {
	    	ProductoFinanciero p = op.getProdFinanciero();
	        if (p.getNombre().equalsIgnoreCase(producto.getNombre())) {
	        	productoExiste = true;
	    	    int confirmacionRecompra = JOptionPane.showConfirmDialog(
	    	            null,
	    	            "Esta cartera ya contiene el producto seleccionado."
	    	            + "¿Desea añadir " + cantidad + producto.getDivisa() + " a la cantidad existente?",
	    	            "Verificación de compra",
	    	            JOptionPane.YES_NO_OPTION,
	    	            JOptionPane.QUESTION_MESSAGE
	    	    );
	    	    
	    	    if (confirmacionRecompra != JOptionPane.YES_OPTION) {
	    	    	return false;
	    	    }
	    	    break;
	        }
	    }
	    
	    if (!productoExiste) {
		    int confirmacionCompra = JOptionPane.showConfirmDialog(
		            null,
		            "¿Desea comprar " + cantidad + producto.getDivisa() + " de " + producto.getNombre() + "?",
		            "Verificación de compra",
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE
		    );
		    
	        if (confirmacionCompra != JOptionPane.YES_OPTION) {
	            return false;
	        }
	    }

		this.operaciones.add(new Operacion(producto, cantidad, LocalDate.now(), true));
		saldo -= coste;
		JOptionPane.showMessageDialog(
			    null,
			    "Compra de " + cantidad + " " + producto.getDivisa() + " de " + producto.getNombre() + " realizada con éxito.",
			    "Confirmación de compra",
			    JOptionPane.INFORMATION_MESSAGE
			);
		return true;
	}

	@Override
	public String toString() {
	    
	    return "Cartera [nombre=" + nombre
	            + ", saldo=" + saldo
	            + ", perfilRiesgo=" + perfilRiesgo
	            + ", divisa=" + divisa
	            + ", operaciones=" + operaciones.size() + "]";
	}
    
}