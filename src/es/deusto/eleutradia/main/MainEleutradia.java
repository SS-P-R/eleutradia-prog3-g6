package es.deusto.eleutradia.main;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import es.deusto.eleutradia.domain.Cartera;
import es.deusto.eleutradia.domain.Curso;
import es.deusto.eleutradia.domain.Divisa;
import es.deusto.eleutradia.domain.Empresa;
import es.deusto.eleutradia.domain.NivelConocimiento;
import es.deusto.eleutradia.domain.Pais;
import es.deusto.eleutradia.domain.Particular;
import es.deusto.eleutradia.domain.PerfilFinanciero;
import es.deusto.eleutradia.domain.PerfilRiesgo;
import es.deusto.eleutradia.domain.PeriodicidadPago;
import es.deusto.eleutradia.domain.PlazoRentabilidad;
import es.deusto.eleutradia.domain.ProductoFinanciero;
import es.deusto.eleutradia.domain.RegionGeografica;
import es.deusto.eleutradia.domain.TipoProducto;
import es.deusto.eleutradia.gui.VentanaInicial;

public class MainEleutradia {
	
	public static List<Particular> listaParticulares = new ArrayList<Particular>();
	public static List<Empresa> listaEmpresas = new ArrayList<Empresa>();
	public static List<ProductoFinanciero> listaProductos = new ArrayList<ProductoFinanciero>();
	
	public static void inicializarUsuarios() {
		//START-CHATGPT-MODIFICADO - Un particular añadido para acceso rápido

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

    	// === EMPRESAS ===
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
	
	public static void inicializarProductos() {
		//START-CHATGPT-MODIFICADO - Rentabilidades ajustadas para realismo
		//						   - Nombres de productos cambiados
		
		// === PRODUCTOS FINANCIEROS ===
	    listaProductos = new ArrayList<>();

	    	// --- Rentabilidades tipo ejemplo ---
	    Map<PlazoRentabilidad, BigDecimal> rentabConservadora = Map.of(
	            PlazoRentabilidad.YTD, new BigDecimal("0.01"),
	            PlazoRentabilidad.UN_ANO, new BigDecimal("0.02"),
	            PlazoRentabilidad.TRES_ANOS, new BigDecimal("0.03"),
	            PlazoRentabilidad.CINCO_ANOS, new BigDecimal("0.035"),
	            PlazoRentabilidad.MAX, new BigDecimal("0.04")
	        );

	        Map<PlazoRentabilidad, BigDecimal> rentabModerada = Map.of(
	            PlazoRentabilidad.YTD, new BigDecimal("0.03"),
	            PlazoRentabilidad.UN_ANO, new BigDecimal("0.05"),
	            PlazoRentabilidad.TRES_ANOS, new BigDecimal("0.07"),
	            PlazoRentabilidad.CINCO_ANOS, new BigDecimal("0.09"),
	            PlazoRentabilidad.MAX, new BigDecimal("0.10")
	        );

	        Map<PlazoRentabilidad, BigDecimal> rentabAgresiva = Map.of(
	            PlazoRentabilidad.YTD, new BigDecimal("0.06"),
	            PlazoRentabilidad.UN_ANO, new BigDecimal("0.10"),
	            PlazoRentabilidad.TRES_ANOS, new BigDecimal("0.15"),
	            PlazoRentabilidad.CINCO_ANOS, new BigDecimal("0.20"),
	            PlazoRentabilidad.MAX, new BigDecimal("0.25")
	        );

	    // --- DEPÓSITOS ---
	    listaProductos.add(new ProductoFinanciero(
	        "DEP001",
	        "Depósito IberInvest 12M",
	        YearMonth.of(2025, 12),
	        rentabConservadora,
	        1000.0,
	        1020.0,
	        TipoProducto.DEPOSITO,
	        RegionGeografica.EUROPA_OCCIDENTAL,
	        PeriodicidadPago.ANUAL,
	        Divisa.EUR
	    ));

	    listaProductos.add(new ProductoFinanciero(
	        "DEP002",
	        "Depósito CapitalFrance Plus",
	        YearMonth.of(2026, 6),
	        rentabModerada,
	        2000.0,
	        2100.0,
	        TipoProducto.DEPOSITO,
	        RegionGeografica.EUROPA_OCCIDENTAL,
	        PeriodicidadPago.SEMESTRAL,
	        Divisa.EUR
	    ));

	    // --- ACCIONES ---
	    listaProductos.add(new ProductoFinanciero(
	        "ACC001",
	        "Acción IberInvest",
	        null,
	        rentabAgresiva,
	        100.0,
	        115.0,
	        TipoProducto.ACCION,
	        RegionGeografica.EUROPA_OCCIDENTAL,
	        PeriodicidadPago.SIN_PAGO,
	        Divisa.EUR
	    ));

	    listaProductos.add(new ProductoFinanciero(
	        "ACC002",
	        "Acción FinTech México",
	        null,
	        rentabAgresiva,
	        80.5,
	        97.0,
	        TipoProducto.ACCION,
	        RegionGeografica.AMERICA_NORTE,
	        PeriodicidadPago.SIN_PAGO,
	        Divisa.MXN
	    ));

	    // --- FONDOS DE INVERSIÓN ---
	    listaProductos.add(new ProductoFinanciero(
	        "FON001",
	        "Fondo CapitalFrance Global",
	        null,
	        rentabModerada,
	        1500.0,
	        1650.0,
	        TipoProducto.FONDO_INVERSION,
	        RegionGeografica.EUROPA_OCCIDENTAL,
	        PeriodicidadPago.TRIMESTRAL,
	        Divisa.EUR
	    ));

	    listaProductos.add(new ProductoFinanciero(
	        "FON002",
	        "Fondo SwissBank Moderado",
	        null,
	        rentabModerada,
	        950.0,
	        1000.0,
	        TipoProducto.FONDO_INVERSION,
	        RegionGeografica.EUROPA_OCCIDENTAL,
	        PeriodicidadPago.MENSUAL,
	        Divisa.CHF
	    ));

	    // --- CRIPTOMONEDAS ---
	    listaProductos.add(new ProductoFinanciero(
	        "CRP001",
	        "BitTrust",
	        null,
	        rentabAgresiva,
	        35.0,
	        50.0,
	        TipoProducto.CRIPTOMONEDA,
	        RegionGeografica.AMERICA_NORTE,
	        PeriodicidadPago.SIN_PAGO,
	        Divisa.USD
	    ));

	    listaProductos.add(new ProductoFinanciero(
	        "CRP002",
	        "HongCoin",
	        null,
	        rentabAgresiva,
	        2.5,
	        3.8,
	        TipoProducto.CRIPTOMONEDA,
	        RegionGeografica.ASIA_PACIFICO,
	        PeriodicidadPago.SIN_PAGO,
	        Divisa.HKD
	    ));

	    // --- ETFS ---
	    listaProductos.add(new ProductoFinanciero(
	        "ETF001",
	        "ETF Renta Fija Europa",
	        null,
	        rentabConservadora,
	        120.0,
	        125.0,
	        TipoProducto.ETF_RF,
	        RegionGeografica.EUROPA_OCCIDENTAL,
	        PeriodicidadPago.ANUAL,
	        Divisa.EUR
	    ));

	    listaProductos.add(new ProductoFinanciero(
	        "ETF002",
	        "ETF Renta Variable Global",
	        null,
	        rentabAgresiva,
	        250.0,
	        280.0,
	        TipoProducto.ETF_RV,
	        RegionGeografica.AMERICA_NORTE,
	        PeriodicidadPago.ANUAL,
	        Divisa.USD
	    ));
		//END-CHATGPT
	}
	
	public static void main(String[] args) {
		
		MainEleutradia.inicializarUsuarios();
		MainEleutradia.inicializarProductos();
		
		// ---Ejecutar la creacion de la GUI---
		SwingUtilities.invokeLater(() -> new VentanaInicial().setVisible(true));
	}
	
}
