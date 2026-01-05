package es.deusto.eleutradia.domain;

import java.util.HashMap;
import java.util.Map;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cartera {
    private int id;
    private String nombre;
    private double saldo;
    private PerfilRiesgo perfilRiesgo;
    private Divisa divisa;
    private List<Operacion> operaciones;
    private List<Posicion> posiciones;
    
    public Cartera(String nombre, double saldo, PerfilRiesgo perfilRiesgo, Divisa divisa) {
		this.id = 0;
		this.nombre = nombre;
		this.saldo = saldo;
		this.perfilRiesgo = perfilRiesgo;
		this.divisa = divisa;
		this.operaciones = new ArrayList<>();
		this.posiciones = new ArrayList<>();
	}

	public Cartera(int id, String nombre, double saldo, PerfilRiesgo perfilRiesgo, Divisa divisa) {
		this.id = id;
		this.nombre = nombre;
		this.saldo = saldo;
		this.perfilRiesgo = perfilRiesgo;
		this.divisa = divisa;
		this.operaciones = new ArrayList<>();
		this.posiciones = new ArrayList<>();
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
	    this.id = id;
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
		return List.copyOf(operaciones);
	}

    public void addOperacion(Operacion op) {
        operaciones.add(op);
    }
	
	public List<Posicion> getPosiciones() {
		return List.copyOf(posiciones);
	}

	public void addPosicion(Posicion pos) {
        posiciones.add(pos);
    }

	/**
	 * Calcula el valor total de todas las inversiones en la divisa de la cartera
	 */
	public double calcularValorInversiones() {
	    double total = 0.0;
	    List<Posicion> posiciones = obtenerPosicionesActuales();
	    for (Posicion posicion : posiciones) {
	        total += posicion.getValorTotalEnDivisa(this.divisa);
	    }
	    return total;
	}
	
	/**
	 * Obtiene las posiciones actuales con cálculo correcto del precio medio
	 * considerando conversiones de divisa
	 */
	public List<Posicion> obtenerPosicionesActuales() {
	    Map<String, DatosPosicion> posicionesMap = new HashMap<>();
	    
	    for (Operacion op : operaciones) {
	        String nombreProducto = op.getProdFinanciero().getNombre();
	        
	        if (!posicionesMap.containsKey(nombreProducto)) {
	            posicionesMap.put(nombreProducto, new DatosPosicion(op.getProdFinanciero()));
	        }
	        
	        DatosPosicion datos = posicionesMap.get(nombreProducto);
	        
	        if (op.getTipoOp()) { // COMPRA
	        	// Convertimos el coste de la operación a la divisa de la cartera
				double costoOperacionEnDivisaCartera = op.getImporteTotalEnDivisa(this.divisa);
				
				// Acumulamos el coste total en divisa de la cartera
				double costoTotalAcumulado = datos.cantidadTotal * datos.precioMedioCompraEnDivisaCartera;
				costoTotalAcumulado += costoOperacionEnDivisaCartera;
				
				// Actualizamos la cantidad
				datos.cantidadTotal += op.getCantidad();
				
				// Recalculamos el precio medio en divisa de la cartera
				if (datos.cantidadTotal > 0) {
					datos.precioMedioCompraEnDivisaCartera = costoTotalAcumulado / datos.cantidadTotal;
				}
	        } else { // VENTA
	            datos.cantidadTotal -= op.getCantidad();
	        }
	    }
	    
	    List<Posicion> posiciones = new ArrayList<>();
	    for (DatosPosicion datos : posicionesMap.values()) {
	        if (datos.cantidadTotal > 0) {
	            posiciones.add(new Posicion(datos.producto, datos.cantidadTotal, datos.precioMedioCompraEnDivisaCartera));
	        }
	    }
	    
	    return posiciones;
	}
	
	/**
	 * Calcula el patrimonio total (saldo + inversiones) en la divisa de la cartera
	 */
	public double calcularPatrimonio() {
	    return saldo + calcularValorInversiones();
	}
	
	/**
	 * Añade un producto a la cartera realizando una compra
	 */
	public boolean addProducto(ProductoFinanciero producto, double cantidad) {
		if (producto == null || cantidad <= 0) {
			return false;
		}

		// Convertimos el coste del producto a la divisa de la cartera
		double precioUnitario = producto.getValorUnitario();
		BigDecimal precioEnDivisaProducto = BigDecimal.valueOf(precioUnitario);
		BigDecimal precioEnDivisaCartera = producto.getDivisa()
			.convertirA(precioEnDivisaProducto, this.divisa);
		
		double costeEnDivisaCartera = precioEnDivisaCartera.doubleValue() * cantidad;

		if (costeEnDivisaCartera > saldo) {
			return false;
		}

		// Creamos la operación con el precio unitario original del producto
		this.operaciones.add(new Operacion(
			producto, 
			cantidad, 
			LocalDate.now(), 
			true
		));
		
		saldo -= costeEnDivisaCartera;
		return true;
	}
	
	/**
	 * Verifica si la cartera cierto producto financiero
	 */
	public boolean contieneProducto(ProductoFinanciero producto) {
	    for (Operacion op : this.operaciones) {
	        ProductoFinanciero p = op.getProdFinanciero();
	        if (p.getNombre().equalsIgnoreCase(producto.getNombre())) {
	            return true;
	        }
	    }
	    return false;
	}

	@Override
	public String toString() {
	    
	    return "Cartera [nombre=" + nombre
	            + ", saldo=" + saldo
	            + ", perfilRiesgo=" + perfilRiesgo
	            + ", divisa=" + divisa
	            + ", operaciones=" + operaciones.size() + "]";
	}
	
	// Clase interna para almacenar datos intermedios de posiciones
	private class DatosPosicion {
	    ProductoFinanciero producto;
	    double cantidadTotal;
	    double precioMedioCompraEnDivisaCartera;
	    
	    DatosPosicion(ProductoFinanciero producto) {
	        this.producto = producto;
	        this.cantidadTotal = 0;
	        this.precioMedioCompraEnDivisaCartera = 0;
	    }
	}
    
}