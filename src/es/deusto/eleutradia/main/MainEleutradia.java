package es.deusto.eleutradia.main;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import es.deusto.eleutradia.domain.Cartera;
import es.deusto.eleutradia.domain.Curso;
import es.deusto.eleutradia.domain.Divisa;
import es.deusto.eleutradia.domain.Empresa;
import es.deusto.eleutradia.domain.Gestora;
import es.deusto.eleutradia.domain.NivelConocimiento;
import es.deusto.eleutradia.domain.Operacion;
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
import es.deusto.eleutradia.domain.Leccion;
import es.deusto.eleutradia.domain.Modulo;

public class MainEleutradia {
	
	public static List<Particular> listaParticulares = new ArrayList<Particular>();
	public static List<Empresa> listaEmpresas = new ArrayList<Empresa>();
	public static List<ProductoFinanciero> listaProductos = new ArrayList<ProductoFinanciero>();
	public static List<Curso> listaCursos = new ArrayList<Curso>();
	
	// --- PAÍSES ---
    public static Pais espana = new Pais("España", RegionGeografica.EUROPA_OCCIDENTAL);
    public static Pais francia = new Pais("Francia", RegionGeografica.EUROPA_OCCIDENTAL);
    public static Pais mexico = new Pais("México", RegionGeografica.AMERICA_NORTE);
    public static Pais argentina = new Pais("Argentina", RegionGeografica.AMERICA_SUR);
    public static Pais eeuu = new Pais("Estados Unidos", RegionGeografica.AMERICA_NORTE);
    public static Pais alemania = new Pais("Alemania", RegionGeografica.EUROPA_OCCIDENTAL);
    public static Pais reinounido = new Pais("Reino Unido", RegionGeografica.EUROPA_OCCIDENTAL);
	
	public static void inicializarUsuarios() {
		//IAG (ChatGPT)
		//ADAPTADO: Un particular añadido para acceso rápido

		// === PARTICULARES ===

    		// --- PERFILES FINANCIEROS ---
	    PerfilFinanciero pCons1 = new PerfilFinanciero(PerfilRiesgo.CONSERVADOR, 5,
            NivelConocimiento.PRINCIPIANTE, List.of(TipoProducto.DEPOSITO, TipoProducto.ETF_RF));
	    PerfilFinanciero pMddo1 = new PerfilFinanciero(PerfilRiesgo.MODERADO, 10,
            NivelConocimiento.INTERMEDIO, List.of(TipoProducto.ACCION, TipoProducto.FONDO_INVERSION));
	    PerfilFinanciero pAgvo1 = new PerfilFinanciero(PerfilRiesgo.AGRESIVO, 3,
            NivelConocimiento.AVANZADO, List.of(TipoProducto.CRIPTOMONEDA, TipoProducto.ETF_RV));

    		// --- CARTERAS ---
	    Cartera c1 = new Cartera();
	    Cartera c2 = new Cartera("Cartera de Sergio", 15.0, PerfilRiesgo.MODERADO, Divisa.EUR, new ArrayList<Operacion>());
	    ArrayList<Cartera> carterasVacia = new ArrayList<>();
	    ArrayList<Cartera> carterasEjemplo = new ArrayList<>(Arrays.asList(c1, c2));

    		// --- CURSOS ---
	    ArrayList<Curso> cursosBasicos = new ArrayList<>();
	    ArrayList<Curso> cursosAvanzados = new ArrayList<>();

	    listaParticulares.add(new Particular("12345678A", "Unai Molinuevo", LocalDate.of(2006, 12, 26), null,
	                "unai.moli@email.com", "12345678", "600500401", "P/ Deusto 25, Bilbao",
	                espana, pMddo1, carterasVacia, cursosBasicos)
	    		);
	    listaParticulares.add(new Particular("01234567A", "Sergio Pena", LocalDate.of(2006, 12, 26), espana,
	                "sergio.pena@email.com", "01234567", "600500400", "P/ Deusto 25, Bilbao",
	                espana, pMddo1, carterasEjemplo, cursosBasicos)
	    		);
	    listaParticulares.add(new Particular("12345679X", "Carlos Ruiz", LocalDate.of(1988, 3, 15), espana,
	                "carlos.ruiz@email.com", "carlos2025", "600123456", "Calle Mayor 10, Madrid",
	                espana, pMddo1, carterasVacia, cursosBasicos)
	    		);
	    listaParticulares.add(new Particular("87654321B", "Lucía Gómez", LocalDate.of(1995, 7, 10), mexico,
	                "lucia.gomez@email.com", "lucia1995", "5556789123", "Av. Insurgentes 123, CDMX",
	                mexico, pCons1, carterasVacia, cursosBasicos)
	    		);
	    listaParticulares.add(new Particular("11223344C", "David Martín", LocalDate.of(1979, 1, 20), francia,
	                "david.martin@email.com", "martin79", "0623456789", "Rue Lafayette 33, Paris",
	                francia, pAgvo1, carterasVacia, cursosAvanzados)
	    		);
	    listaParticulares.add(new Particular("44332211D", "Sofía López", LocalDate.of(1992, 11, 5), argentina,
	                "sofia.lopez@email.com", "sofia2024", "+54 911 456789", "Calle Florida 22, Buenos Aires",
	                argentina, pMddo1, carterasVacia, cursosAvanzados)
	    		);
	    listaParticulares.add(new Particular("55667788E", "Marcos Torres", LocalDate.of(1985, 9, 22), alemania,
	                "marcos.torres@email.com", "marcos85", "611445566", "Gran Vía 18, Madrid",
	                espana, pAgvo1, carterasVacia, cursosBasicos)
	    		);

    	// === EMPRESAS ===
	    listaEmpresas.add(new Empresa("A1234567B", "IberInvest S.A.", "contacto@iberinvest.com", "1234", "914445566",
	                "Calle Alcalá 55, Madrid", espana, pMddo1, carterasVacia)
	    		);
	    listaEmpresas.add(new Empresa("C1234567D", "FinTech México", "info@fintechmx.com", "finmex2024", "5589654321",
	                "Av. Reforma 100, CDMX", mexico, pAgvo1, carterasVacia)
	    		);
	    listaEmpresas.add(new Empresa("E1234567F", "CapitalFrance", "admin@capitalfr.fr", "capfr2025", "0145678901",
	                "Boulevard Haussmann 12, Paris", francia, pCons1, carterasVacia)
	    		);
	    listaEmpresas.add(new Empresa("G1234567H", "InvestUS LLC", "support@investus.com", "investus2024", "+1 202 555 0198",
	                "Wall Street 45, New York", eeuu, pAgvo1, carterasVacia)
	    		);
	    listaEmpresas.add(new Empresa("I1234567J", "ArgentBank", "info@argentbank.com", "argent2025", "1134567890",
	                "Av. Corrientes 200, Buenos Aires", argentina, pMddo1, carterasVacia)
	    		);
	    //END-IAG
	}
	
	public static void inicializarCursos() {
        
        // --- 1. CURSO: 2 Módulos (2 Lecciones + 2 Lecciones) ---
        Leccion l1_1 = new Leccion(101, "Renta Fija vs. Renta Variable", 1);
        Leccion l1_2 = new Leccion(102, "El papel de los Brokers", 2);
        List<Leccion> leccionesC1M1 = List.of(l1_1, l1_2);
        Leccion l1_3 = new Leccion(103, "Introducción a las Acciones", 1);
        Leccion l1_4 = new Leccion(104, "Introducción a los ETFs", 2);
        List<Leccion> leccionesC1M2 = List.of(l1_3, l1_4);
        Modulo m1_1 = new Modulo(10, "Conceptos Fundamentales", 1, leccionesC1M1);
        Modulo m1_2 = new Modulo(11, "Principales Activos", 2, leccionesC1M2);
        List<Modulo> modulosCurso1 = List.of(m1_1, m1_2);
        Curso cursoIntro = new Curso(1, "Introducción a la Inversión", modulosCurso1, NivelConocimiento.AVANZADO, "/imagenes/curso11.png");
        listaCursos.add(cursoIntro);
        
        // --- 2. CURSO: 1 Módulo (3 Lecciones) ---
        Leccion l2_1 = new Leccion(201, "Interpretación de Gráficos de Velas", 1);
        Leccion l2_2 = new Leccion(202, "Medias Móviles (SMA/EMA)", 2);
        Leccion l2_3 = new Leccion(203, "Indicador RSI y MACD", 3);
        List<Leccion> leccionesC2M1 = List.of(l2_1, l2_2, l2_3);
        Modulo m2_1 = new Modulo(20, "Indicadores Técnicos", 1, leccionesC2M1);
        List<Modulo> modulosCurso2 = List.of(m2_1);
        Curso cursoAnalisis = new Curso(2, "Análisis Técnico Básico", modulosCurso2, NivelConocimiento.AVANZADO, "/imagenes/curso11.png");
        listaCursos.add(cursoAnalisis);

        // --- 3. CURSO: 3 Módulos (2 Lecciones + 2 Lecciones + 1 Lección) ---
        Leccion l3_1 = new Leccion(301, "Diversificación y Riesgo", 1);
        Leccion l3_2 = new Leccion(302, "Asignación de Activos (Asset Allocation)", 2);
        List<Leccion> leccionesC3M1 = List.of(l3_1, l3_2);
        Leccion l3_3 = new Leccion(303, "Introducción a Opciones (Call/Put)", 1);
        Leccion l3_4 = new Leccion(304, "Introducción a Futuros", 2);
        List<Leccion> leccionesC3M2 = List.of(l3_3, l3_4);
        Leccion l3_5 = new Leccion(305, "Estrategias de Cobertura (Hedging)", 1);
        List<Leccion> leccionesC3M3 = List.of(l3_5);
        Modulo m3_1 = new Modulo(30, "Gestión del Riesgo", 1, leccionesC3M1);
        Modulo m3_2 = new Modulo(31, "Instrumentos Derivados", 2, leccionesC3M2);
        Modulo m3_3 = new Modulo(32, "Estrategias Avanzadas", 3, leccionesC3M3);
        List<Modulo> modulosCurso3 = List.of(m3_1, m3_2, m3_3);
        Curso cursoAvanzado = new Curso(3, "Gestión de Carteras y Derivados", modulosCurso3, NivelConocimiento.AVANZADO, "/imagenes/curso11.png");
        listaCursos.add(cursoAvanzado);
        
    }
	
	public static void inicializarProductos() {
		//IAG (herramienta: ChatGPT)
		//ADAPTADO (Rentabilidades ajustadas para realismo y algunis productos añadidos
		
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
        
        Gestora blackrock = new Gestora(
    		"iShares",
    	    "BlackRock Inc.",
    	    "50 Hudson Yards, New York, NY, Estados Unidos",
    	    eeuu,
    	    new ArrayList<>()
    	);

        Gestora vanguard = new Gestora(
    		"Vanguard",
    	    "Vanguard Group",
    	    "100 Vanguard Blvd, Malvern, PA, Estados Unidos",
    	    eeuu,
    	    new ArrayList<>()
    	);

        Gestora fidelity = new Gestora(
    		"Fidelity",
    	    "Fidelity Investments",
    	    "245 Summer Street, Boston, MA, Estados Unidos",
    	    eeuu,
    	    new ArrayList<>()
    	);

        Gestora jpmorgan = new Gestora(
    		"J.P. Morgan",
    	    "J.P. Morgan Asset Management",
    	    "25 Bank Street, Canary Wharf, Londres, Reino Unido",
    	    reinounido,
    	    new ArrayList<>()
    	);

        Gestora amundi = new Gestora(
    		"Amundi",
    	    "Amundi Asset Management",
    	    "90 Boulevard Pasteur, París, Francia",
    	    francia,
    	    new ArrayList<>()
    	);
        
        Gestora bbva = new Gestora(
    		"BBVA",
    		"BBVA Asset Management",
    		"Paseo Castellana 81, Madrid, España",
    		espana,
    		new ArrayList<>()
    	);
	        
        // === DEPÓSITOS ===
        listaProductos.addAll(List.of(
            new ProductoFinanciero("DEP001", "Depósito 12M", YearMonth.of(2025, 12), rentabConservadora, 1020.0,
            		TipoProducto.DEPOSITO, RegionGeografica.EUROPA_OCCIDENTAL, PeriodicidadPago.ANUAL, Divisa.EUR, jpmorgan),
            new ProductoFinanciero("DEP002", "Depósito CapFrance", YearMonth.of(2026, 6), rentabModerada, 2100.0,
            		TipoProducto.DEPOSITO, RegionGeografica.EUROPA_OCCIDENTAL, PeriodicidadPago.SEMESTRAL, Divisa.EUR, amundi)
        ));

        // === BONOS ===
        listaProductos.addAll(List.of(
            new ProductoFinanciero("BON001", "Global Bond Index Fund", YearMonth.of(2026, 12), rentabConservadora, 110.0,
            		TipoProducto.BONO, RegionGeografica.MUNDO, PeriodicidadPago.ANUAL, Divisa.USD, vanguard),
            new ProductoFinanciero("BON002", "Global S-T Corp. Bond", YearMonth.of(2026, 12), rentabConservadora, 155.0,
            		TipoProducto.BONO, RegionGeografica.MUNDO, PeriodicidadPago.ANUAL, Divisa.EUR, blackrock)
        ));
        
        // === LETRAS DEL TESORO ===
        listaProductos.addAll(List.of(
            new ProductoFinanciero("LDT001", "Deuda España 12M", YearMonth.of(2026, 3), rentabConservadora, 1000.0,
            		TipoProducto.LETRA_TESORO, RegionGeografica.EUROPA_OCCIDENTAL, PeriodicidadPago.SIN_PAGO, Divisa.EUR, bbva),
            new ProductoFinanciero("LDT002", "Deuda USA 6M", YearMonth.of(2025, 8), rentabConservadora, 500.0,
            		TipoProducto.LETRA_TESORO, RegionGeografica.AMERICA_NORTE, PeriodicidadPago.SIN_PAGO, Divisa.USD, jpmorgan)
        ));

        // === ACCIONES ===
        listaProductos.addAll(List.of(
            new ProductoFinanciero("ACC001", "NVIDIA", null, rentabAgresiva, 175.0,
            		TipoProducto.ACCION, RegionGeografica.AMERICA_NORTE, PeriodicidadPago.SIN_PAGO, Divisa.USD, null),
            new ProductoFinanciero("ACC002", "Xiaomi", null, rentabAgresiva, 4.78,
            		TipoProducto.ACCION, RegionGeografica.ASIA_PACIFICO, PeriodicidadPago.SIN_PAGO, Divisa.CNY, null),
            new ProductoFinanciero("ACC003", "Banco Santander", null, rentabModerada, 3.55,
            		TipoProducto.ACCION, RegionGeografica.EUROPA_OCCIDENTAL, PeriodicidadPago.ANUAL, Divisa.EUR, null),
            new ProductoFinanciero("ACC004", "Tesla", null, rentabAgresiva, 250.0,
            		TipoProducto.ACCION, RegionGeografica.AMERICA_NORTE, PeriodicidadPago.SIN_PAGO, Divisa.USD, null)
        ));

        // === FONDOS DE INVERSIÓN ===
        listaProductos.addAll(List.of(
            new ProductoFinanciero("FON001", "Fondo CapFrance Global", null, rentabModerada, 1650.0,
            		TipoProducto.FONDO_INVERSION, RegionGeografica.EUROPA_OCCIDENTAL, PeriodicidadPago.TRIMESTRAL, Divisa.EUR, amundi),
            new ProductoFinanciero("FON002", "Fondo SwissBank", null, rentabModerada, 950.0,
            		TipoProducto.FONDO_INVERSION, RegionGeografica.EUROPA_OCCIDENTAL, PeriodicidadPago.MENSUAL, Divisa.CHF, vanguard),
            new ProductoFinanciero("FON003", "S&P 500 Index", null, rentabModerada, 950.0,
            		TipoProducto.FONDO_INVERSION, RegionGeografica.AMERICA_NORTE, PeriodicidadPago.DIARIA, Divisa.USD, fidelity),
            new ProductoFinanciero("FON004", "Asia Growth Fund", null, rentabAgresiva, 1800.0,
            		TipoProducto.FONDO_INVERSION, RegionGeografica.ASIA_PACIFICO, PeriodicidadPago.MENSUAL, Divisa.USD, blackrock)
        ));

        // === PLANES DE PENSIONES ===
        listaProductos.addAll(List.of(
            new ProductoFinanciero("PDP001", "Plan Jubilación RF", null, rentabConservadora, 2.0,
            		TipoProducto.PLAN_PENSIONES_RF, RegionGeografica.EUROPA_OCCIDENTAL, PeriodicidadPago.MENSUAL, Divisa.EUR, bbva),
            new ProductoFinanciero("PDP002", "Plan Jubilación RV", null, rentabModerada, 5.0,
            		TipoProducto.PLAN_PENSIONES_RV, RegionGeografica.MUNDO, PeriodicidadPago.SIN_PAGO, Divisa.EUR, bbva)
        ));

        // === ETFS ===
        listaProductos.addAll(List.of(
            new ProductoFinanciero("ETF001", "ETF R. Fija Europa", null, rentabConservadora, 120.0,
            		TipoProducto.ETF_RF, RegionGeografica.EUROPA_OCCIDENTAL, PeriodicidadPago.ANUAL, Divisa.EUR, vanguard),
            new ProductoFinanciero("ETF002", "ETF R. Variable Global", null, rentabAgresiva, 280.0,
            		TipoProducto.ETF_RV, RegionGeografica.MUNDO, PeriodicidadPago.ANUAL, Divisa.USD, fidelity),
            new ProductoFinanciero("ETF003", "ETF Emergentes Asia", null, rentabAgresiva, 210.0,
            		TipoProducto.ETF_RV, RegionGeografica.ASIA_PACIFICO, PeriodicidadPago.SEMESTRAL, Divisa.USD, blackrock)
        ));

        // === CRIPTOMONEDAS ===
        listaProductos.addAll(List.of(
            new ProductoFinanciero("CRP001", "Bitcoin", null, rentabAgresiva, 110_000,
            		TipoProducto.CRIPTOMONEDA, RegionGeografica.MUNDO, PeriodicidadPago.SIN_PAGO, Divisa.USD, null),
            new ProductoFinanciero("CRP002", "XRP", null, rentabAgresiva, 2.5,
            		TipoProducto.CRIPTOMONEDA, RegionGeografica.MUNDO, PeriodicidadPago.SIN_PAGO, Divisa.USD, null),
            new ProductoFinanciero("CRP003", "Ethereum", null, rentabAgresiva, 3100.0,
            		TipoProducto.CRIPTOMONEDA, RegionGeografica.MUNDO, PeriodicidadPago.SIN_PAGO, Divisa.USD, null)
        ));

        // === MATERIAS PRIMAS ===
        listaProductos.addAll(List.of(
            new ProductoFinanciero("CMD001", "Physical Gold", null, rentabModerada, 77.88,
            		TipoProducto.COMMODITY, RegionGeografica.MUNDO, PeriodicidadPago.SIN_PAGO, Divisa.USD, amundi),
            new ProductoFinanciero("CMD002", "Physical Platinum", null, rentabAgresiva, 22.72,
            		TipoProducto.COMMODITY, RegionGeografica.MUNDO, PeriodicidadPago.SIN_PAGO, Divisa.USD, blackrock),
            new ProductoFinanciero("CMD003", "Crude Oil WTI", null, rentabModerada, 83.45,
            		TipoProducto.COMMODITY, RegionGeografica.AMERICA_NORTE, PeriodicidadPago.SIN_PAGO, Divisa.USD, fidelity)
        ));

        // === PRIVATE EQUITY ===
        listaProductos.addAll(List.of(
            new ProductoFinanciero("PEQ001", "Global Venture Growth", null, rentabAgresiva, 25000.0, TipoProducto.PRIVATE_EQUITY, RegionGeografica.MUNDO, PeriodicidadPago.SIN_PAGO, Divisa.USD, blackrock)
        ));

        // === CROWDFUNDING INMOBILIARIO ===
        listaProductos.addAll(List.of(
            new ProductoFinanciero("CRI001", "CrowdMadrid Plaza", null, rentabModerada, 3200.0, TipoProducto.CROWDFUND_INM, RegionGeografica.EUROPA_OCCIDENTAL, PeriodicidadPago.TRIMESTRAL, Divisa.EUR, amundi)
        ));

	}
	
	public static void main(String[] args) {
		MainEleutradia.inicializarUsuarios();
		MainEleutradia.inicializarProductos();
		MainEleutradia.inicializarCursos();
		
		// ---Ejecutar la creacion de la GUI---
		SwingUtilities.invokeLater(() -> new VentanaInicial().setVisible(true));
	}
	
}
