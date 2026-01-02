package es.deusto.eleutradia.domain;

import java.util.HashMap;
import java.util.Map;
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

	public double calcularValorInversiones() {
	    double total = 0.0;
	    List<Posicion> posiciones = obtenerPosicionesActuales();
	    for (Posicion posicion : posiciones) {
	        total += posicion.getValorTotal();
	    }
	    return total;
	}
	
	public List<Posicion> obtenerPosicionesActuales() {
	    Map<String, DatosPosicion> posicionesMap = new HashMap<>();
	    
	    for (Operacion op : operaciones) {
	        String nombreProducto = op.getProdFinanciero().getNombre();
	        
	        if (!posicionesMap.containsKey(nombreProducto)) {
	            posicionesMap.put(nombreProducto, new DatosPosicion(op.getProdFinanciero()));
	        }
	        
	        DatosPosicion datos = posicionesMap.get(nombreProducto);
	        
	        if (op.getTipoOp()) { // COMPRA
	            double costoTotal = datos.cantidadTotal * datos.precioMedioCompra;
	            costoTotal += op.getCantidad() * op.getProdFinanciero().getValorUnitario();
	            datos.cantidadTotal += op.getCantidad();
	            if (datos.cantidadTotal > 0) {
	                datos.precioMedioCompra = costoTotal / datos.cantidadTotal;
	            }
	        } else { // VENTA
	            datos.cantidadTotal -= op.getCantidad();
	        }
	    }
	    
	    List<Posicion> posiciones = new ArrayList<>();
	    for (DatosPosicion datos : posicionesMap.values()) {
	        if (datos.cantidadTotal > 0) {
	            posiciones.add(new Posicion(datos.producto, datos.cantidadTotal, datos.precioMedioCompra));
	        }
	    }
	    
	    return posiciones;
	}
	
	public double calcularPatrimonio() {
	    return saldo + calcularValorInversiones();
	}
	
	public boolean addProducto(ProductoFinanciero producto, double cantidad) {
	    if (producto == null || cantidad <= 0) {
	        return false;
	    }
	    
	    double coste = cantidad * producto.getValorUnitario();
	    if (coste > saldo) {
	        return false;
	    }

	    this.operaciones.add(new Operacion(producto, cantidad, LocalDate.now(), true));
	    saldo -= coste;
	    return true;
	}
	
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
	private class DatosPosicion {
	    ProductoFinanciero producto;
	    double cantidadTotal;
	    double precioMedioCompra;
	    
	    DatosPosicion(ProductoFinanciero producto) {
	        this.producto = producto;
	        this.cantidadTotal = 0;
	        this.precioMedioCompra = 0;
	    }
	}
    
}