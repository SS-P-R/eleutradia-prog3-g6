package es.deusto.eleutradia.main;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import es.deusto.eleutradia.db.EleutradiaDBManager;
import es.deusto.eleutradia.domain.Curso;
import es.deusto.eleutradia.domain.Empresa;
import es.deusto.eleutradia.domain.Particular;
import es.deusto.eleutradia.domain.ProductoFinanciero;
import es.deusto.eleutradia.gui.VentanaInicial;
import es.deusto.eleutradia.gui.style.UITema;

public class MainEleutradia {
	
	private static EleutradiaDBManager dbManager;
	
	public static List<Particular> listaParticulares = new ArrayList<Particular>();
	public static List<Empresa> listaEmpresas = new ArrayList<Empresa>();
	public static List<ProductoFinanciero> listaProductos = new ArrayList<ProductoFinanciero>();
	public static List<Curso> listaCursos = new ArrayList<Curso>();
	
	public static void main(String[] args) {
		
		dbManager = new EleutradiaDBManager();
	    dbManager.initializeDB();
	    
	    listaParticulares = dbManager.getParticulares();
	    listaEmpresas = dbManager.getEmpresas();
	    listaProductos = dbManager.getProductos();
	    listaCursos = dbManager.getCursos();
	    
	    // Inicializar la personalización del tema
	    UITema.getInstancia();
		
		// Ejecutar la creacion de la GUI
		SwingUtilities.invokeLater(() -> new VentanaInicial());
	}
	
	public static EleutradiaDBManager getDBManager() {
		return dbManager;
	}
}