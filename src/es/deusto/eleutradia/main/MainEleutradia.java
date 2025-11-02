package es.deusto.eleutradia.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import es.deusto.eleutradia.domain.Cartera;
import es.deusto.eleutradia.domain.Curso;
import es.deusto.eleutradia.domain.Empresa;
import es.deusto.eleutradia.domain.NivelConocimiento;
import es.deusto.eleutradia.domain.Pais;
import es.deusto.eleutradia.domain.Particular;
import es.deusto.eleutradia.domain.PerfilFinanciero;
import es.deusto.eleutradia.domain.PerfilRiesgo;
import es.deusto.eleutradia.domain.RegionGeografica;
import es.deusto.eleutradia.domain.TipoProducto;
import es.deusto.eleutradia.gui.VentanaInicial;

public class MainEleutradia {
	
	public static List<Particular> listaParticulares = new ArrayList<Particular>();
	public static List<Empresa> listaEmpresas = new ArrayList<Empresa>();
	
	public static void inicializarUsuarios() {
		//START-CHATGPT-MODIFICADO

		// === PARTICULARES ===
	
			// --- PAISES ---
	    Pais espana = new Pais("España", RegionGeografica.EUROPA_OCCIDENTAL);
	    Pais francia = new Pais("Francia", RegionGeografica.EUROPA_OCCIDENTAL);
	    Pais mexico = new Pais("México", RegionGeografica.AMERICA_NORTE);
	    Pais argentina = new Pais("Argentina", RegionGeografica.AMERICA_SUR);
	    Pais eeuu = new Pais("Estados Unidos", RegionGeografica.AMERICA_NORTE);
	    Pais alemania = new Pais("Alemania", RegionGeografica.EUROPA_OCCIDENTAL);

    		// --- PERFILES FINANCIEROS ---
	    PerfilFinanciero pCons1 = new PerfilFinanciero(PerfilRiesgo.CONSERVADOR, 5,
            NivelConocimiento.PRINCIPIANTE, List.of(TipoProducto.DEPOSITO, TipoProducto.ETF_RF));
	    PerfilFinanciero pMddo1 = new PerfilFinanciero(PerfilRiesgo.MODERADO, 10,
            NivelConocimiento.INTERMEDIO, List.of(TipoProducto.ACCION, TipoProducto.FONDO_INVERSION));
	    PerfilFinanciero pAgvo1 = new PerfilFinanciero(PerfilRiesgo.AGRESIVO, 3,
            NivelConocimiento.AVANZADO, List.of(TipoProducto.CRIPTOMONEDA, TipoProducto.ETF_RV));

    		// --- CARTERAS ---
	    ArrayList<Cartera> carterasBasicas = new ArrayList<>();
	    ArrayList<Cartera> carterasPremium = new ArrayList<>();
	    ArrayList<Cartera> carterasVacia = new ArrayList<>();

    		// --- CURSOS ---
	    ArrayList<Curso> cursosBasicos = new ArrayList<>();
	    ArrayList<Curso> cursosAvanzados = new ArrayList<>();

	    listaParticulares.add(new Particular("1234", "Sergio Pena", LocalDate.of(2006, 12, 26), espana,
	                "sergio.pena@email.com", "1234", "600500400", "P/ Deusto 25, Bilbao",
	                espana, pMddo1, carterasBasicas, cursosBasicos)
	    		);
	    listaParticulares.add(new Particular("12345678A", "Carlos Ruiz", LocalDate.of(1988, 3, 15), espana,
	                "carlos.ruiz@email.com", "carlos2025", "600123456", "Calle Mayor 10, Madrid",
	                espana, pMddo1, carterasBasicas, cursosBasicos)
	    		);
	    listaParticulares.add(new Particular("87654321B", "Lucía Gómez", LocalDate.of(1995, 7, 10), mexico,
	                "lucia.gomez@email.com", "lucia95", "5556789123", "Av. Insurgentes 123, CDMX",
	                mexico, pCons1, carterasBasicas, cursosBasicos)
	    		);
	    listaParticulares.add(new Particular("11223344C", "David Martín", LocalDate.of(1979, 1, 20), francia,
	                "david.martin@email.com", "martin79", "0623456789", "Rue Lafayette 33, Paris",
	                francia, pAgvo1, carterasPremium, cursosAvanzados)
	    		);
	    listaParticulares.add(new Particular("44332211D", "Sofía López", LocalDate.of(1992, 11, 5), argentina,
	                "sofia.lopez@email.com", "sofia2024", "+54 911 456789", "Calle Florida 22, Buenos Aires",
	                argentina, pMddo1, carterasBasicas, cursosAvanzados)
	    		);
	    listaParticulares.add(new Particular("55667788E", "Marcos Torres", LocalDate.of(1985, 9, 22), alemania,
	                "marcos.torres@email.com", "marcos85", "611445566", "Gran Vía 18, Madrid",
	                espana, pAgvo1, carterasPremium, cursosBasicos)
	    		);

    	// --- EMPRESAS ---
	    listaEmpresas.add(new Empresa("EMP001", "IberInvest S.A.", "contacto@iberinvest.com", "iber2025", "914445566",
	                "Calle Alcalá 55, Madrid", espana, pMddo1, carterasPremium)
	    		);
	    listaEmpresas.add(new Empresa("EMP002", "FinTech México", "info@fintechmx.com", "finmex2024", "5589654321",
	                "Av. Reforma 100, CDMX", mexico, pAgvo1, carterasBasicas)
	    		);
	    listaEmpresas.add(new Empresa("EMP003", "CapitalFrance", "admin@capitalfr.fr", "capfr2025", "0145678901",
	                "Boulevard Haussmann 12, Paris", francia, pCons1, carterasVacia)
	    		);
	    listaEmpresas.add(new Empresa("EMP004", "InvestUS LLC", "support@investus.com", "investus2024", "+1 202 555 0198",
	                "Wall Street 45, New York", eeuu, pAgvo1, carterasPremium)
	    		);
	    listaEmpresas.add(new Empresa("EMP005", "ArgentBank", "info@argentbank.com", "argent2025", "1134567890",
	                "Av. Corrientes 200, Buenos Aires", argentina, pMddo1, carterasBasicas)
	    		);
	//END-CHATGPT
	}
	
	public static void main(String[] args) {
		
		MainEleutradia.inicializarUsuarios();
		
		// ---Ejecutar la creacion de la GUI---
		SwingUtilities.invokeLater(() -> new VentanaInicial().setVisible(true));
	}
	
}
