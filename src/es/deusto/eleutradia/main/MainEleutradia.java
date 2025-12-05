package es.deusto.eleutradia.main;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import es.deusto.eleutradia.domain.Curso;
import es.deusto.eleutradia.domain.Divisa;
import es.deusto.eleutradia.domain.Empresa;
import es.deusto.eleutradia.domain.Gestora;
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
    public static Pais canada = new Pais("Canadá", RegionGeografica.AMERICA_NORTE);
    public static Pais japon = new Pais("Japón", RegionGeografica.ASIA_PACIFICO);
    public static Pais hongkong = new Pais("Hong Kong", RegionGeografica.ASIA_PACIFICO);
	
	public static void inicializarUsuarios() {
		//IAG (ChatGPT)
		//ADAPTADO: Un particular añadido para acceso rápido

		// === PARTICULARES ===

    		// --- PERFILES FINANCIEROS ---
	    PerfilFinanciero pCons1 = new PerfilFinanciero(5, PerfilRiesgo.CONSERVADOR,
            NivelConocimiento.PRINCIPIANTE, List.of(TipoProducto.DEPOSITO, TipoProducto.ETF_RF));
	    PerfilFinanciero pMddo1 = new PerfilFinanciero(10, PerfilRiesgo.MODERADO,
            NivelConocimiento.INTERMEDIO, List.of(TipoProducto.ACCION, TipoProducto.FONDO_INVERSION));
	    PerfilFinanciero pAgvo1 = new PerfilFinanciero(3, PerfilRiesgo.AGRESIVO,
            NivelConocimiento.AVANZADO, List.of(TipoProducto.CRIPTOMONEDA, TipoProducto.ETF_RV));

	    listaParticulares.add(new Particular("12345678A", "Unai Molinuevo", LocalDate.of(2006, 12, 26), null,
	                "unai.moli@email.com", "12345678", "600500401", "P/ Deusto 25, Bilbao",
	                espana, pMddo1)
	    		);
	    listaParticulares.add(new Particular("01234567A", "Sergio Pena", LocalDate.of(2006, 12, 26), espana,
	                "sergio.pena@email.com", "01234567", "600500400", "P/ Deusto 25, Bilbao",
	                espana, pMddo1)
	    		);
	    listaParticulares.add(new Particular("12345679X", "Carlos Ruiz", LocalDate.of(1988, 3, 15), espana,
	                "carlos.ruiz@email.com", "carlos2025", "600123456", "Calle Mayor 10, Madrid",
	                espana, pMddo1)
	    		);
	    listaParticulares.add(new Particular("87654321B", "Lucía Gómez", LocalDate.of(1995, 7, 10), mexico,
	                "lucia.gomez@email.com", "lucia1995", "5556789123", "Av. Insurgentes 123, CDMX",
	                mexico, pCons1)
	    		);
	    listaParticulares.add(new Particular("11223344C", "David Martín", LocalDate.of(1979, 1, 20), francia,
	                "david.martin@email.com", "martin79", "0623456789", "Rue Lafayette 33, Paris",
	                francia, pAgvo1)
	    		);
	    listaParticulares.add(new Particular("44332211D", "Sofía López", LocalDate.of(1992, 11, 5), argentina,
	                "sofia.lopez@email.com", "sofia2024", "+54 911 456789", "Calle Florida 22, Buenos Aires",
	                argentina, pMddo1)
	    		);
	    listaParticulares.add(new Particular("55667788E", "Marcos Torres", LocalDate.of(1985, 9, 22), alemania,
	                "marcos.torres@email.com", "marcos85", "611445566", "Gran Vía 18, Madrid",
	                espana, pAgvo1)
	    		);

    	// === EMPRESAS ===
	    listaEmpresas.add(new Empresa("A1234567B", "IberInvest S.A.", "contacto@iberinvest.com", "1234", "914445566",
	                "Calle Alcalá 55, Madrid", espana, pMddo1)
	    		);
	    listaEmpresas.add(new Empresa("C1234567D", "FinTech México", "info@fintechmx.com", "finmex2024", "5589654321",
	                "Av. Reforma 100, CDMX", mexico, pAgvo1)
	    		);
	    listaEmpresas.add(new Empresa("E1234567F", "CapitalFrance", "admin@capitalfr.fr", "capfr2025", "0145678901",
	                "Boulevard Haussmann 12, Paris", francia, pCons1)
	    		);
	    listaEmpresas.add(new Empresa("G1234567H", "InvestUS LLC", "support@investus.com", "investus2024", "+1 202 555 0198",
	                "Wall Street 45, New York", eeuu, pAgvo1)
	    		);
	    listaEmpresas.add(new Empresa("I1234567J", "ArgentBank", "info@argentbank.com", "argent2025", "1134567890",
	                "Av. Corrientes 200, Buenos Aires", argentina, pMddo1)
	    		);
	    //END-IAG
	}
	//IAG (GEMINI)
	//NO MODIFICADO
	public static void inicializarCursos() {
        
        // --- 1. CURSO: Introducción a la Inversión (Ya existente) ---
        Leccion l1_1 = new Leccion(101, "Renta Fija vs. Renta Variable", 1);
        Leccion l1_2 = new Leccion(102, "El papel de los Brokers", 2);
        List<Leccion> leccionesC1M1 = List.of(l1_1, l1_2);
        Leccion l1_3 = new Leccion(103, "Introducción a las Acciones", 1);
        Leccion l1_4 = new Leccion(104, "Introducción a los ETFs", 2);
        List<Leccion> leccionesC1M2 = List.of(l1_3, l1_4);
        Modulo m1_1 = new Modulo(10, "Conceptos Fundamentales", 1, leccionesC1M1);
        Modulo m1_2 = new Modulo(11, "Principales Activos", 2, leccionesC1M2);
        List<Modulo> modulosCurso1 = List.of(m1_1, m1_2);
        Curso cursoIntro = new Curso(1, "Introducción a la Inversión", modulosCurso1, NivelConocimiento.PRINCIPIANTE, "/imagenes/curso11.png");
        listaCursos.add(cursoIntro);
        
        // --- 2. CURSO: Análisis Técnico Básico (Ya existente) ---
        Leccion l2_1 = new Leccion(201, "Interpretación de Gráficos de Velas", 1);
        Leccion l2_2 = new Leccion(202, "Medias Móviles (SMA/EMA)", 2);
        Leccion l2_3 = new Leccion(203, "Indicador RSI y MACD", 3);
        List<Leccion> leccionesC2M1 = List.of(l2_1, l2_2, l2_3);
        Modulo m2_1 = new Modulo(20, "Indicadores Técnicos", 1, leccionesC2M1);
        List<Modulo> modulosCurso2 = List.of(m2_1);
        Curso cursoAnalisis = new Curso(2, "Análisis Técnico Básico", modulosCurso2, NivelConocimiento.INTERMEDIO, "/imagenes/curso4.png");
        listaCursos.add(cursoAnalisis);

        // --- 3. CURSO: Gestión de Carteras y Derivados (Ya existente) ---
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
        Curso cursoAvanzado = new Curso(3, "Gestión de Carteras y Derivados", modulosCurso3, NivelConocimiento.AVANZADO, "/imagenes/curso3.png");
        listaCursos.add(cursoAvanzado);
        
        // --- 4. CURSO: Finanzas Personales y Ahorro ---
        // (Relacionado con curso7.jpg, curso12.png)
        Leccion l4_1 = new Leccion(401, "Creación de un Presupuesto", 1);
        Leccion l4_2 = new Leccion(402, "El Fondo de Emergencia", 2);
        List<Leccion> leccionesC4M1 = List.of(l4_1, l4_2);
        Leccion l4_3 = new Leccion(403, "Del Ahorro a la Inversión", 1);
        Leccion l4_4 = new Leccion(404, "Interés Compuesto: La Magia de Crecer", 2);
        List<Leccion> leccionesC4M2 = List.of(l4_3, l4_4);
        Modulo m4_1 = new Modulo(40, "Control de Finanzas", 1, leccionesC4M1);
        Modulo m4_2 = new Modulo(41, "Primeros Pasos", 2, leccionesC4M2);
        List<Modulo> modulosCurso4 = List.of(m4_1, m4_2);
        Curso cursoFinanzasP = new Curso(4, "Gestión de Finanzas Personales", modulosCurso4, NivelConocimiento.PRINCIPIANTE, "/imagenes/curso12.png");
        listaCursos.add(cursoFinanzasP);
        
        // --- 5. CURSO: Planificación para la Jubilación ---
        // (Relacionado con curso9.png)
        Leccion l5_1 = new Leccion(501, "¿Qué son los Planes de Pensiones?", 1);
        Leccion l5_2 = new Leccion(502, "Planes de Pensiones de Renta Fija (PLAN_PENSIONES_RF)", 2);
        List<Leccion> leccionesC5M1 = List.of(l5_1, l5_2);
        Leccion l5_3 = new Leccion(503, "Planes de Pensiones de Renta Variable (PLAN_PENSIONES_RV)", 1);
        Leccion l5_4 = new Leccion(504, "Estrategias de Aportación y Rescate", 2);
        List<Leccion> leccionesC5M2 = List.of(l5_3, l5_4);
        Modulo m5_1 = new Modulo(50, "Conceptos Básicos de Jubilación", 1, leccionesC5M1);
        Modulo m5_2 = new Modulo(51, "Tipos de Planes", 2, leccionesC5M2);
        List<Modulo> modulosCurso5 = List.of(m5_1, m5_2);
        Curso cursoJubilacion = new Curso(5, "Planificación para la Jubilación", modulosCurso5, NivelConocimiento.PRINCIPIANTE, "/imagenes/curso9.png");
        listaCursos.add(cursoJubilacion);
        
        // --- 6. CURSO: Inversión en Bienes Raíces ---
        // (Relacionado con curso8.png)
        Leccion l6_1 = new Leccion(601, "Inversión Inmobiliaria Tradicional", 1);
        Leccion l6_2 = new Leccion(602, "Financiación: Hipotecas y Préstamos", 2);
        List<Leccion> leccionesC6M1 = List.of(l6_1, l6_2);
        Leccion l6_3 = new Leccion(603, "El Auge del Crowdfunding Inmobiliario (CROWDFUND_INM)", 1);
        Leccion l6_4 = new Leccion(604, "Invertir en SOCIMIs (REITs)", 2);
        List<Leccion> leccionesC6M2 = List.of(l6_3, l6_4);
        Modulo m6_1 = new Modulo(60, "Fundamentos Inmobiliarios", 1, leccionesC6M1);
        Modulo m6_2 = new Modulo(61, "Nuevas Modalidades", 2, leccionesC6M2);
        List<Modulo> modulosCurso6 = List.of(m6_1, m6_2);
        Curso cursoInmuebles = new Curso(6, "Inversión en Bienes Raíces", modulosCurso6, NivelConocimiento.INTERMEDIO, "/imagenes/curso8.png");
        listaCursos.add(cursoInmuebles);

        // --- 7. CURSO: Renta Fija (Bonos y Depósitos) ---
        // (Relacionado con curso6.png - monedas/tesoro, y curso7.png - ahorro seguro)
        Leccion l7_1 = new Leccion(701, "Depósitos a Plazo Fijo (DEPOSITO)", 1);
        Leccion l7_2 = new Leccion(702, "Entendiendo los Bonos (BONO)", 2);
        List<Leccion> leccionesC7M1 = List.of(l7_1, l7_2);
        Leccion l7_3 = new Leccion(703, "Letras del Tesoro (LETRA_TESORO)", 1);
        Leccion l7_4 = new Leccion(704, "ETFs de Renta Fija (ETF_RF)", 2);
        List<Leccion> leccionesC7M2 = List.of(l7_3, l7_4);
        Modulo m7_1 = new Modulo(70, "Productos de Ahorro", 1, leccionesC7M1);
        Modulo m7_2 = new Modulo(71, "Deuda Pública y Corporativa", 2, leccionesC7M2);
        List<Modulo> modulosCurso7 = List.of(m7_1, m7_2);
        Curso cursoRentaFija = new Curso(7, "Explorando la Renta Fija", modulosCurso7, NivelConocimiento.PRINCIPIANTE, "/imagenes/curso6.png");
        listaCursos.add(cursoRentaFija);

        // --- 8. CURSO: Inversiones Alternativas (Cripto y Commodities) ---
        // (Relacionado con curso3.png - trading, y curso6.png - oro/commodities)
        Leccion l8_1 = new Leccion(801, "Introducción a las Criptomonedas (CRIPTOMONEDA)", 1);
        Leccion l8_2 = new Leccion(802, "Blockchain y Wallets", 2);
        List<Leccion> leccionesC8M1 = List.of(l8_1, l8_2);
        Leccion l8_3 = new Leccion(803, "Invertir en Materias Primas (COMMODITY)", 1);
        Leccion l8_4 = new Leccion(804, "Oro, Petróleo y más", 2);
        List<Leccion> leccionesC8M2 = List.of(l8_3, l8_4);
        Modulo m8_1 = new Modulo(80, "Activos Digitales", 1, leccionesC8M1);
        Modulo m8_2 = new Modulo(81, "Activos Tangibles", 2, leccionesC8M2);
        List<Modulo> modulosCurso8 = List.of(m8_1, m8_2);
        // Re-uso curso3.png porque encaja perfecto con Criptomonedas
        Curso cursoAlternativos = new Curso(8, "Nuevos Horizontes: Cripto y Materias Primas", modulosCurso8, NivelConocimiento.AVANZADO, "/imagenes/curso3.png");
        listaCursos.add(cursoAlternativos);

        // --- 9. CURSO: Valoración y Riesgo ---
        // (Relacionado con curso2.png - balanza)
        Leccion l9_1 = new Leccion(901, "El binomio Riesgo-Rentabilidad", 1);
        Leccion l9_2 = new Leccion(902, "Qué es la Volatilidad", 2);
        List<Leccion> leccionesC9M1 = List.of(l9_1, l9_2);
        Leccion l9_3 = new Leccion(903, "Diversificación: No pongas todos los huevos en la misma cesta", 1);
        Leccion l9_4 = new Leccion(904, "Análisis Fundamental vs. Técnico", 2);
        List<Leccion> leccionesC9M2 = List.of(l9_3, l9_4);
        Modulo m9_1 = new Modulo(90, "Midiendo el Riesgo", 1, leccionesC9M1);
        Modulo m9_2 = new Modulo(91, "Estrategias de Valoración", 2, leccionesC9M2);
        List<Modulo> modulosCurso9 = List.of(m9_1, m9_2);
        Curso cursoValoracion = new Curso(9, "Valoración de Activos y Gestión del Riesgo", modulosCurso9, NivelConocimiento.INTERMEDIO, "/imagenes/curso2.png");
        listaCursos.add(cursoValoracion);

        // --- 10. CURSO: Capital Inversión (Private Equity) ---
        // (Relacionado con curso10.png - apretón de manos, negocio)
        Leccion l10_1 = new Leccion(1001, "¿Qué es el Capital Inversión (PRIVATE_EQUITY)?", 1);
        Leccion l10_2 = new Leccion(1002, "Venture Capital vs. Buyouts", 2);
        List<Leccion> leccionesC10M1 = List.of(l10_1, l10_2);
        Leccion l10_3 = new Leccion(1003, "El proceso de 'Deal Making'", 1);
        Leccion l10_4 = new Leccion(1004, "Riesgos y Oportunidades del Capital Privado", 2);
        List<Leccion> leccionesC10M2 = List.of(l10_3, l10_4);
        Modulo m10_1 = new Modulo(100, "Fundamentos del Private Equity", 1, leccionesC10M1);
        Modulo m10_2 = new Modulo(101, "El Mercado", 2, leccionesC10M2);
        List<Modulo> modulosCurso10 = List.of(m10_1, m10_2);
        Curso cursoPrivateEquity = new Curso(10, "Inversión en Capital Privado", modulosCurso10, NivelConocimiento.AVANZADO, "/imagenes/curso10.png");
        listaCursos.add(cursoPrivateEquity);
    }
	//END IAG
	public static void inicializarProductos() {
		//IAG (herramienta: ChatGPT)
		//ADAPTADO (Rentabilidades ajustadas para realismo y algunis productos añadidos
		
		// === PRODUCTOS FINANCIEROS ===
	    listaProductos = new ArrayList<>();

	    	// --- Rentabilidades tipo ejemplo ---
    	Map<PlazoRentabilidad, BigDecimal> rentabConservadora = Map.of(
            PlazoRentabilidad.YTD, new BigDecimal("1.95"),
            PlazoRentabilidad.UN_ANO, new BigDecimal("1.28"),
            PlazoRentabilidad.TRES_ANOS, new BigDecimal("3.45"),
            PlazoRentabilidad.CINCO_ANOS, new BigDecimal("2.56"),
            PlazoRentabilidad.MAX, new BigDecimal("3.09")
        );

        Map<PlazoRentabilidad, BigDecimal> rentabModerada = Map.of(
            PlazoRentabilidad.YTD, new BigDecimal("4.50"),
            PlazoRentabilidad.UN_ANO, new BigDecimal("4.21"),
            PlazoRentabilidad.TRES_ANOS, new BigDecimal("7.60"),
            PlazoRentabilidad.CINCO_ANOS, new BigDecimal("6.25"),
            PlazoRentabilidad.MAX, new BigDecimal("6.10")
        );

        Map<PlazoRentabilidad, BigDecimal> rentabAgresiva = Map.of(
            PlazoRentabilidad.YTD, new BigDecimal("12.75"),
            PlazoRentabilidad.UN_ANO, new BigDecimal("11.02"),
            PlazoRentabilidad.TRES_ANOS, new BigDecimal("14.09"),
            PlazoRentabilidad.CINCO_ANOS, new BigDecimal("18.51"),
            PlazoRentabilidad.MAX, new BigDecimal("15.22")
        );
        
        Gestora eleutradia = new Gestora(
    		"EleuTradia",
    	    "EleuTradia Asset Management",
    	    "Universidad de Deusto, Bilbao, España",
    	    espana,
    	    new ArrayList<>()
        );
        
        Gestora blackrock = new Gestora(
    		"BlackRock",
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
    		"JPMorgan",
    	    "J.P. Morgan Asset Management",
    	    "25 Bank Street, Canary Wharf, Londres, Reino Unido",
    	    eeuu,
    	    new ArrayList<>()
    	);
        
        Gestora bbva = new Gestora(
    		"BBVA",
    		"BBVA Asset Management",
    		"Paseo Castellana 81, Madrid, España",
    		espana,
    		new ArrayList<>()
    	);

        Gestora amundi = new Gestora(
    		"Amundi",
    	    "Amundi Asset Management",
    	    "90 Boulevard Pasteur, París, Francia",
    	    francia,
    	    new ArrayList<>()
    	);
        
        Gestora schroders = new Gestora(
    	    "Schroders",
    	    "Schroders PLC",
    	    "1 London Wall Place, Londres, Reino Unido",
    	    reinounido,
    	    new ArrayList<>()
    	);
        
        Gestora nomura = new Gestora(
    	    "Nomura",
    	    "Nomura Asset Management",
    	    "1-12-1 Nihonbashi, Chuo-ku, Tokio, Japón",
    	    japon,
    	    new ArrayList<>()
    	);
        
        Gestora hsbc = new Gestora(
    	    "HSBC",
    	    "HSBC Asset Management",
    	    "1 Queen’s Road Central, Hong Kong",
    	    hongkong,
    	    new ArrayList<>()
    	);
	        
        // === DEPÓSITOS ===
        listaProductos.addAll(List.of(
            new ProductoFinanciero(1, "Depósito 12M", YearMonth.of(2025, 12), rentabConservadora, 1020.0,
            		TipoProducto.DEPOSITO, RegionGeografica.EUROPA_OCCIDENTAL, PeriodicidadPago.ANUAL, Divisa.USD, jpmorgan),
            new ProductoFinanciero(2, "Depósito CapFrance", YearMonth.of(2026, 6), rentabModerada, 2100.0,
            		TipoProducto.DEPOSITO, RegionGeografica.EUROPA_OCCIDENTAL, PeriodicidadPago.SEMESTRAL, Divisa.EUR, amundi)
        ));

        // === BONOS ===
        listaProductos.addAll(List.of(
            new ProductoFinanciero(3, "Global Bond Index Fund", YearMonth.of(2026, 12), rentabConservadora, 110.0,
            		TipoProducto.BONO, RegionGeografica.MUNDO, PeriodicidadPago.ANUAL, Divisa.USD, vanguard),
            new ProductoFinanciero(4, "Global S-T Corp. Bond", YearMonth.of(2026, 12), rentabConservadora, 155.0,
            		TipoProducto.BONO, RegionGeografica.MUNDO, PeriodicidadPago.ANUAL, Divisa.EUR, blackrock)
        ));
        
        // === LETRAS DEL TESORO ===
        listaProductos.addAll(List.of(
            new ProductoFinanciero(5, "Deuda España 12M", YearMonth.of(2026, 3), rentabConservadora, 1000.0,
            		TipoProducto.LETRA_TESORO, RegionGeografica.EUROPA_OCCIDENTAL, PeriodicidadPago.SIN_PAGO, Divisa.EUR, bbva),
            new ProductoFinanciero(6, "US Debt 6M", YearMonth.of(2025, 8), rentabConservadora, 500.0,
            		TipoProducto.LETRA_TESORO, RegionGeografica.AMERICA_NORTE, PeriodicidadPago.SIN_PAGO, Divisa.USD, jpmorgan)
        ));

        // === ACCIONES ===
        listaProductos.addAll(List.of(
            new ProductoFinanciero(7, "NVIDIA", null, rentabAgresiva, 175.0,
            		TipoProducto.ACCION, RegionGeografica.AMERICA_NORTE, PeriodicidadPago.SIN_PAGO, Divisa.USD, eleutradia),
            new ProductoFinanciero(8, "Xiaomi", null, rentabAgresiva, 4.78,
            		TipoProducto.ACCION, RegionGeografica.ASIA_PACIFICO, PeriodicidadPago.SIN_PAGO, Divisa.CNY, eleutradia),
            new ProductoFinanciero(9, "Banco Santander", null, rentabModerada, 3.55,
            		TipoProducto.ACCION, RegionGeografica.EUROPA_OCCIDENTAL, PeriodicidadPago.ANUAL, Divisa.EUR, eleutradia),
            new ProductoFinanciero(10, "Tesla", null, rentabAgresiva, 250.0,
            		TipoProducto.ACCION, RegionGeografica.AMERICA_NORTE, PeriodicidadPago.SIN_PAGO, Divisa.USD, eleutradia)
        ));

        // === FONDOS DE INVERSIÓN ===
        listaProductos.addAll(List.of(
            new ProductoFinanciero(11, "Fondo CapFrance Global", null, rentabModerada, 1650.0,
            		TipoProducto.FONDO_INVERSION, RegionGeografica.EUROPA_OCCIDENTAL, PeriodicidadPago.TRIMESTRAL, Divisa.EUR, amundi),
            new ProductoFinanciero(12, "Fondo SwissBank", null, rentabModerada, 950.0,
            		TipoProducto.FONDO_INVERSION, RegionGeografica.EUROPA_OCCIDENTAL, PeriodicidadPago.MENSUAL, Divisa.CHF, vanguard),
            new ProductoFinanciero(13, "S&P 500 Index", null, rentabModerada, 950.0,
            		TipoProducto.FONDO_INVERSION, RegionGeografica.AMERICA_NORTE, PeriodicidadPago.DIARIA, Divisa.USD, fidelity),
            new ProductoFinanciero(14, "Asia Growth Fund", null, rentabAgresiva, 1800.0,
            		TipoProducto.FONDO_INVERSION, RegionGeografica.ASIA_PACIFICO, PeriodicidadPago.MENSUAL, Divisa.USD, blackrock),
            new ProductoFinanciero(15, "UK Corporate Bond Fund", null, rentabConservadora, 1250.0,
            		TipoProducto.FONDO_INVERSION, RegionGeografica.EUROPA_OCCIDENTAL, PeriodicidadPago.SEMESTRAL, Divisa.GBP, schroders)
        ));

        // === PLANES DE PENSIONES ===
        listaProductos.addAll(List.of(
            new ProductoFinanciero(16, "Plan Jubilación RF", null, rentabConservadora, 2.0,
            		TipoProducto.PLAN_PENSIONES_RF, RegionGeografica.EUROPA_OCCIDENTAL, PeriodicidadPago.MENSUAL, Divisa.EUR, bbva),
            new ProductoFinanciero(17, "Plan Jubilación RV", null, rentabModerada, 5.0,
            		TipoProducto.PLAN_PENSIONES_RV, RegionGeografica.MUNDO, PeriodicidadPago.SIN_PAGO, Divisa.EUR, bbva)
        ));

        // === ETFS ===
        listaProductos.addAll(List.of(
            new ProductoFinanciero(18, "ETF R. Fija Europa", null, rentabConservadora, 120.0,
            		TipoProducto.ETF_RF, RegionGeografica.EUROPA_OCCIDENTAL, PeriodicidadPago.ANUAL, Divisa.EUR, vanguard),
            new ProductoFinanciero(19, "ETF R. Variable Global", null, rentabAgresiva, 280.0,
            		TipoProducto.ETF_RV, RegionGeografica.MUNDO, PeriodicidadPago.ANUAL, Divisa.USD, fidelity),
            new ProductoFinanciero(20, "ETF Emergentes Asia", null, rentabAgresiva, 210.0,
            		TipoProducto.ETF_RV, RegionGeografica.ASIA_PACIFICO, PeriodicidadPago.SEMESTRAL, Divisa.USD, blackrock),
            new ProductoFinanciero(21, "ETF Japan Equity Index", null, rentabModerada, 15000.0,
            		TipoProducto.ETF_RV, RegionGeografica.ASIA_PACIFICO, PeriodicidadPago.ANUAL, Divisa.JPY, nomura),
            new ProductoFinanciero(22, "Asia Tech Leaders ETF", null, rentabAgresiva, 320.0,
            		TipoProducto.ETF_RV, RegionGeografica.ASIA_PACIFICO, PeriodicidadPago.ANUAL, Divisa.HKD, hsbc)
        ));

        // === CRIPTOMONEDAS ===
        listaProductos.addAll(List.of(
            new ProductoFinanciero(23, "Bitcoin", null, rentabAgresiva, 110_000,
            		TipoProducto.CRIPTOMONEDA, RegionGeografica.MUNDO, PeriodicidadPago.SIN_PAGO, Divisa.USD, eleutradia),
            new ProductoFinanciero(24, "XRP", null, rentabAgresiva, 2.5,
            		TipoProducto.CRIPTOMONEDA, RegionGeografica.MUNDO, PeriodicidadPago.SIN_PAGO, Divisa.USD, eleutradia),
            new ProductoFinanciero(25, "Ethereum", null, rentabAgresiva, 3100.0,
            		TipoProducto.CRIPTOMONEDA, RegionGeografica.MUNDO, PeriodicidadPago.SIN_PAGO, Divisa.USD, eleutradia)
        ));

        // === MATERIAS PRIMAS ===
        listaProductos.addAll(List.of(
            new ProductoFinanciero(26, "Physical Gold", null, rentabModerada, 77.88,
            		TipoProducto.COMMODITY, RegionGeografica.MUNDO, PeriodicidadPago.SIN_PAGO, Divisa.USD, amundi),
            new ProductoFinanciero(27, "Physical Platinum", null, rentabAgresiva, 22.72,
            		TipoProducto.COMMODITY, RegionGeografica.MUNDO, PeriodicidadPago.SIN_PAGO, Divisa.USD, blackrock),
            new ProductoFinanciero(28, "Crude Oil WTI", null, rentabModerada, 83.45,
            		TipoProducto.COMMODITY, RegionGeografica.AMERICA_NORTE, PeriodicidadPago.SIN_PAGO, Divisa.USD, fidelity)
        ));

        // === PRIVATE EQUITY ===
        listaProductos.addAll(List.of(
            new ProductoFinanciero(29, "Global Venture Growth", null, rentabAgresiva, 25000.0,
            		TipoProducto.PRIVATE_EQUITY, RegionGeografica.MUNDO, PeriodicidadPago.SIN_PAGO, Divisa.USD, blackrock)
        ));

        // === CROWDFUNDING INMOBILIARIO ===
        listaProductos.addAll(List.of(
            new ProductoFinanciero(30, "CrowdMadrid Plaza", null, rentabModerada, 3200.0,
            		TipoProducto.CROWDFUND_INM, RegionGeografica.EUROPA_OCCIDENTAL, PeriodicidadPago.TRIMESTRAL, Divisa.EUR, amundi)
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
