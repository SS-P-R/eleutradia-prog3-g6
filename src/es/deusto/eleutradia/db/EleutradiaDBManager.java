package es.deusto.eleutradia.db;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;

import es.deusto.eleutradia.domain.ClaseActivo;
import es.deusto.eleutradia.domain.Divisa;
import es.deusto.eleutradia.domain.NivelConocimiento;
import es.deusto.eleutradia.domain.PerfilRiesgo;
import es.deusto.eleutradia.domain.PeriodicidadPago;
import es.deusto.eleutradia.domain.PlazoRentabilidad;
import es.deusto.eleutradia.domain.RegionGeografica;
import es.deusto.eleutradia.domain.TipoProducto;

public class EleutradiaDBManager {
	
	private final String PROPERTIES_FILE = "resources/config/app.properties";
	
	private static final String CSV_PAISES = "resources/data/paises.csv";
	private static final String CSV_PERFILES = "resources/data/perfilesFinancieros.csv";
	private static final String CSV_USUARIOS = "resources/data/usuarios.csv";
	private static final String CSV_GESTORAS = "resources/data/gestoras.csv";
	private static final String CSV_PRODUCTOS = "resources/data/productos.csv";
	
	private Properties properties;
	private String driver;
	private String dbPath;
	private String connectionUrl;
	
	public EleutradiaDBManager() {
		try {
			properties = new Properties();
			properties.load(new FileReader(PROPERTIES_FILE));
			
			driver = properties.getProperty("driver");
			dbPath = properties.getProperty("file");
			connectionUrl = properties.getProperty("connection");
			
			Class.forName(driver);
		} catch (Exception ex) {
			System.err.format("Error al cargar el driver: %s%n", ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void initializeDB() {
		createDB();
		insertEnumData();
		if (properties.getProperty("db.loadCSV", "false").equals("true")) {
			// Todos los metodos cargar desde CSV
		}
	}
	
	public void createDB() {
		if (properties.getProperty("db.create", "false").equals("true")) {
			try (Connection conn = DriverManager.getConnection(connectionUrl);
				 Statement stmt = conn.createStatement()) {
				
				// ===== ENUMERACIONES =====
				
				// Tabla: Clase Activo
				stmt.execute("""
						CREATE TABLE IF NOT EXISTS ClaseActivo (
							id INTEGER PRIMARY KEY,
							nombre TEXT NOT NULL UNIQUE
						);
				""");
				
				// Tabla: Tipo Producto
				stmt.execute("""
						CREATE TABLE IF NOT EXISTS TipoProducto (
							id INTEGER PRIMARY KEY,
							nombre TEXT NOT NULL UNIQUE,
							claseActivo INTEGER NOT NULL,
							riesgo INTEGER NOT NULL CHECK(riesgo BETWEEN 1 AND 7),
							importeMin REAL,
							
							FOREIGN KEY (claseActivo) REFERENCES ClaseActivo(id)
						);
				""");
				
				// Tabla: Región Geográfica
				stmt.execute("""
						CREATE TABLE IF NOT EXISTS RegionGeografica (
			                id INTEGER PRIMARY KEY,
							nombre TEXT NOT NULL UNIQUE
						);
				""");
				
				// Tabla: Nivel Conocimiento
				stmt.execute("""
						CREATE TABLE IF NOT EXISTS NivelConocimiento (
							id INTEGER PRIMARY KEY,
							nombre TEXT NOT NULL UNIQUE
						);
				""");
				
				// Tabla: Perfil Riesgo
				stmt.execute("""
						CREATE TABLE IF NOT EXISTS PerfilRiesgo (
							id INTEGER PRIMARY KEY,
							nombre TEXT NOT NULL UNIQUE
						);
				""");
				
				// Tabla: Plazo Rentabilidad
				stmt.execute("""
						CREATE TABLE IF NOT EXISTS PlazoRentabilidad (
							id INTEGER PRIMARY KEY,
							nombre TEXT NOT NULL UNIQUE,
							definicion TEXT NOT NULL UNIQUE
						);
				""");
				
				// Tabla: Periodicidad Pago
				stmt.execute("""
						CREATE TABLE IF NOT EXISTS PeriodicidadPago (
							id INTEGER PRIMARY KEY,
							nombre TEXT NOT NULL UNIQUE,
							dias INTEGER NOT NULL UNIQUE
						);
				""");
				
				// Tabla: Divisa
				stmt.execute("""
						CREATE TABLE IF NOT EXISTS Divisa (
							id INTEGER PRIMARY KEY,
							nombre TEXT NOT NULL UNIQUE,
							tasaCambioUSD REAL NOT NULL,
							simbolo TEXT NOT NULL
						);
				""");
				
				// ===== ENTIDADES =====
				
				// Tabla: País
				stmt.execute("""
						CREATE TABLE IF NOT EXISTS Pais (
			                id INTEGER PRIMARY KEY AUTOINCREMENT,
							nombre TEXT NOT NULL UNIQUE,
							regionGeografica INTEGER NOT NULL,
							
							FOREIGN KEY (regionGeografica) REFERENCES RegionGeografica(id)
						);
				""");
				
				// Tabla: Perfil Financiero
				stmt.execute("""
						CREATE TABLE IF NOT EXISTS PerfilFinanciero (
			                id INTEGER PRIMARY KEY AUTOINCREMENT,
							horizonte INTEGER NOT NULL,
							perfilRiesgo INTEGER NOT NULL,
							nivelConocimiento INTEGER NOT NULL,
							
							FOREIGN KEY (perfilRiesgo) REFERENCES PerfilRiesgo(id),
							FOREIGN KEY (nivelConocimiento) REFERENCES NivelConocimiento(id)
						);
				""");
				
		        // Tabla: Particular
				stmt.execute("""
						CREATE TABLE IF NOT EXISTS Particular (
			                dni TEXT PRIMARY KEY,
			                nombre TEXT NOT NULL,
			                email TEXT NOT NULL UNIQUE,
			                password TEXT NOT NULL,
			                telefono TEXT NOT NULL UNIQUE,
			                direccion TEXT NOT NULL,
			                fechaNacimiento TEXT NOT NULL,
			                paisResidencia INTEGER,
			                domicilioFiscal INTEGER,
			                perfilFinanciero INTEGER,
			                
			                FOREIGN KEY (paisResidencia) REFERENCES Pais(id),
			                FOREIGN KEY (domicilioFiscal) REFERENCES Pais(id),
			                FOREIGN KEY (perfilFinanciero) REFERENCES PerfilFinanciero(id)
			            );
		        """);
				
				// Tabla: Empresa
				stmt.execute("""
						CREATE TABLE IF NOT EXISTS Empresa (
			                nif TEXT PRIMARY KEY,
			                nombre TEXT NOT NULL,
			                email TEXT NOT NULL UNIQUE,
			                password TEXT NOT NULL,
			                telefono TEXT NOT NULL UNIQUE,
			                direccion TEXT NOT NULL,
			                domicilioFiscal INTEGER,
			                perfilFinanciero INTEGER,
			                
			                FOREIGN KEY (domicilioFiscal) REFERENCES Pais(id),
			                FOREIGN KEY (perfilFinanciero) REFERENCES PerfilFinanciero(id)
		                );
				""");
				
				// Tabla: Gestora
				stmt.execute("""
						CREATE TABLE IF NOT EXISTS Gestora (
						    id INTEGER PRIMARY KEY AUTOINCREMENT,
						    nombreComercial TEXT NOT NULL UNIQUE,
						    nombreCompleto TEXT NOT NULL UNIQUE,
						    direccion TEXT NOT NULL,
						    sede INTEGER NOT NULL,
	
						    FOREIGN KEY (sede) REFERENCES Pais(id)
						);
				""");
				
				// Tabla: Producto Financiero
				stmt.execute("""
						CREATE TABLE IF NOT EXISTS ProductoFinanciero (
							id INTEGER PRIMARY KEY AUTOINCREMENT,
							nombre TEXT NOT NULL,
							plazoYear INTEGER,
							plazoMonth INTEGER,
							valorUnitario REAL NOT NULL,
							tipoProducto INTEGER NOT NULL,
							regionGeografica INTEGER NOT NULL,
							perPago INTEGER NOT NULL,
							divisa INTEGER NOT NULL,
							gestora INTEGER,
							
							FOREIGN KEY (tipoProducto) REFERENCES TipoProducto(id),
							FOREIGN KEY (regionGeografica) REFERENCES RegionGeografica(id),
							FOREIGN KEY (perPago) REFERENCES PeriodicidadPago(id),
							FOREIGN KEY (divisa) REFERENCES Divisa(id),
							FOREIGN KEY (gestora) REFERENCES Gestora(id)
						);
				""");
				
				// Tabla: Cartera
				stmt.execute("""
						CREATE TABLE IF NOT EXISTS Cartera (
							id INTEGER PRIMARY KEY AUTOINCREMENT,
							nombre TEXT NOT NULL,
							saldo REAL NOT NULL,
							perfilRiesgo INTEGER NOT NULL,
							divisa INTEGER NOT NULL,
							idParticular TEXT,
							idEmpresa TEXT,
							
							FOREIGN KEY (perfilRiesgo) REFERENCES PerfilRiesgo(id),
							FOREIGN KEY (divisa) REFERENCES Divisa(id),
							FOREIGN KEY (idParticular) REFERENCES Particular(dni),
							FOREIGN KEY (idEmpresa) REFERENCES Empresa(nif),
							CHECK (
								(idParticular IS NOT NULL AND idEmpresa IS NULL) OR
								(idParticular IS NULL AND idEmpresa IS NOT NULL)
							)
						);
				""");
				
				// Tabla: Operacion
				stmt.execute("""
						CREATE TABLE IF NOT EXISTS Operacion (
						    id INTEGER PRIMARY KEY AUTOINCREMENT,
						    prodFinanciero INTEGER NOT NULL,
						    cantidad REAL NOT NULL,
						    fechaOp TEXT NOT NULL,
						    tipoOp INTEGER NOT NULL, -- 1 = compra, 0 = venta
						    cartera INTEGER NOT NULL,
	
						    FOREIGN KEY (prodFinanciero) REFERENCES ProductoFinanciero(id),
							FOREIGN KEY (cartera) REFERENCES Cartera(id)
						);
				""");
				
				// Tabla: Posición
				stmt.execute("""
						CREATE TABLE IF NOT EXISTS Posicion (
						    id INTEGER PRIMARY KEY AUTOINCREMENT,
						    prodFinanciero INTEGER NOT NULL,
						    cantidadTotal REAL NOT NULL,
						    precioMedio REAL NOT NULL,
						    cartera INTEGER NOT NULL,
						    
						    FOREIGN KEY (prodFinanciero) REFERENCES ProductoFinanciero(id),
						    FOREIGN KEY (cartera) REFERENCES Cartera(id)
						);
				""");
				
				// Tabla: Curso
				stmt.execute("""
						CREATE TABLE IF NOT EXISTS Curso (
						    id INTEGER PRIMARY KEY AUTOINCREMENT,
						    nombre TEXT NOT NULL,
						    nivelRecomendado INTEGER NOT NULL,
						    rutaImagen TEXT,
	
						    FOREIGN KEY (nivelRecomendado) REFERENCES NivelConocimiento(id)
						);
				""");
				
				// Tabla: Módulo
				stmt.execute("""
						CREATE TABLE IF NOT EXISTS Modulo (
						    id INTEGER PRIMARY KEY AUTOINCREMENT,
						    nombre TEXT NOT NULL,
						    posicion INTEGER NOT NULL,
						    curso INTEGER NOT NULL,
	
						    FOREIGN KEY (curso) REFERENCES Curso(id)
						);
				""");
				
				// Tabla: Lección
				stmt.execute("""
						CREATE TABLE IF NOT EXISTS Leccion (
						    id INTEGER PRIMARY KEY AUTOINCREMENT,
						    titulo TEXT NOT NULL,
						    posicion INTEGER NOT NULL,
						    modulo INTEGER NOT NULL,
	
						    FOREIGN KEY (modulo) REFERENCES Modulo(id)
						);
				""");
				
				// Tabla intermedia: Particular y Curso
				stmt.execute("""
				        CREATE TABLE IF NOT EXISTS ParticularCurso (
				            dniParticular TEXT NOT NULL,
				            idCurso INTEGER NOT NULL,
							PRIMARY KEY (dniParticular, idCurso),
							
				            FOREIGN KEY (dniParticular) REFERENCES Particular(dni) ON DELETE CASCADE,
				            FOREIGN KEY (idCurso) REFERENCES Curso(id) ON DELETE CASCADE
				        );
				""");
				
				// Tabla intermedia: PerfilFinanciero y TipoProducto
				stmt.execute("""
				        CREATE TABLE IF NOT EXISTS PerfilFinancieroTipoProducto (
				            perfilFinanciero INTEGER NOT NULL,
				            tipoProducto INTEGER NOT NULL,
	
				            PRIMARY KEY (perfilFinanciero, tipoProducto),
	
				            FOREIGN KEY (perfilFinanciero) REFERENCES PerfilFinanciero(id) ON DELETE CASCADE,
				            FOREIGN KEY (tipoProducto) REFERENCES TipoProducto(id) ON DELETE CASCADE
				        );
				""");
				
			} catch (Exception ex) {
				System.err.format("Error al crear las tablas: %s%n", ex.getMessage());
				ex.printStackTrace();
			}
		}
	}
	
	private void insertEnumData() {
		try (Connection conn = DriverManager.getConnection(connectionUrl)) {
			insertClaseActivo(conn);
			insertTipoProducto(conn);
			insertRegionGeografica(conn);
			insertNivelConocimiento(conn);
			insertPerfilRiesgo(conn);
			insertPlazoRentabilidad(conn);
			insertPeriodicidadPago(conn);
			insertDivisa(conn);
			
			System.out.println("Datos de enumeraciones insertados correctamente.");
			
		} catch (Exception ex) {
			System.err.format("Error al insertar datos de enumeraciones: %s%n", ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	private void insertClaseActivo(Connection conn) throws Exception {
		String sql = "INSERT OR IGNORE INTO ClaseActivo (id, nombre) VALUES (?, ?);";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			for (ClaseActivo ca : ClaseActivo.values()) {
				pstmt.setInt(1, ca.ordinal());
				pstmt.setString(2, ca.name());
				pstmt.executeUpdate();
			}
		}
	}
	
	private void insertTipoProducto(Connection conn) throws Exception {
		String sql = "INSERT OR IGNORE INTO TipoProducto (id, nombre, claseActivo, riesgo, importeMin) VALUES (?, ?, ?, ?, ?);";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			for (TipoProducto tp : TipoProducto.values()) {
				pstmt.setInt(1, tp.ordinal());
				pstmt.setString(2, tp.getNombre());
				pstmt.setInt(3, tp.getClaseActivo().ordinal());
				pstmt.setInt(4, tp.getRiesgo());
				pstmt.setDouble(5, tp.getImporteMin());
				pstmt.executeUpdate();
			}
		}
	}
	
	private void insertRegionGeografica(Connection conn) throws Exception {
		String sql = "INSERT OR IGNORE INTO RegionGeografica (id, nombre) VALUES (?, ?);";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			for (RegionGeografica rg : RegionGeografica.values()) {
				pstmt.setInt(1, rg.ordinal());
				pstmt.setString(2, rg.getNombre());
				pstmt.executeUpdate();
			}
		}
	}
	
	private void insertNivelConocimiento(Connection conn) throws Exception {
		String sql = "INSERT OR IGNORE INTO NivelConocimiento (id, nombre) VALUES (?, ?);";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			for (NivelConocimiento nc : NivelConocimiento.values()) {
				pstmt.setInt(1, nc.ordinal());
				pstmt.setString(2, nc.name());
				pstmt.executeUpdate();
			}
		}
	}
	
	private void insertPerfilRiesgo(Connection conn) throws Exception {
		String sql = "INSERT OR IGNORE INTO PerfilRiesgo (id, nombre) VALUES (?, ?);";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			for (PerfilRiesgo pr : PerfilRiesgo.values()) {
				pstmt.setInt(1, pr.ordinal());
				pstmt.setString(2, pr.name());
				pstmt.executeUpdate();
			}
		}
	}
	
	private void insertPlazoRentabilidad(Connection conn) throws Exception {
		String sql = "INSERT OR IGNORE INTO PlazoRentabilidad (id, nombre, definicion) VALUES (?, ?, ?);";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			for (PlazoRentabilidad plr : PlazoRentabilidad.values()) {
				pstmt.setInt(1, plr.ordinal());
				pstmt.setString(2, plr.name());
				pstmt.setString(3, plr.getDefinicion());
				pstmt.executeUpdate();
			}
		}
	}
	
	private void insertPeriodicidadPago(Connection conn) throws Exception {
		String sql = "INSERT OR IGNORE INTO PeriodicidadPago (id, nombre, dias) VALUES (?, ?, ?);";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			for (PeriodicidadPago pp : PeriodicidadPago.values()) {
				pstmt.setInt(1, pp.ordinal());
				pstmt.setString(2, pp.name());
				pstmt.setInt(3, pp.getDias());
				pstmt.executeUpdate();
			}
		}
	}
	
	private void insertDivisa(Connection conn) throws Exception {
		String sql = "INSERT OR IGNORE INTO Divisa (id, codigo, nombre, tasaCambioUSD, simbolo) VALUES (?, ?, ?, ?, ?);";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			for (Divisa div : Divisa.values()) {
				pstmt.setInt(1, div.ordinal());
				pstmt.setString(2, div.name());
				pstmt.setString(3, div.getNombre());
				pstmt.setDouble(4, div.getTasaCambioUSD().doubleValue());
				pstmt.setString(5, div.getSimbolo());
				pstmt.executeUpdate();
			}
		}
	}
	
	public void deleteDB() {
		if (properties.getProperty("db.delete", "false").equals("true")) {
		    File db = new File(dbPath).getAbsoluteFile();
		    if (db.exists()) {
		        if (db.delete()) {
		            System.out.println("Base de datos eliminada correctamente.");
		        } else {
		            System.err.println("No se pudo eliminar la base de datos.");
		        }
		    } else {
		    	System.err.println("La base de datos no existe.");
		    }
		}
	}
	
	public void cleanDB() {
		if (properties.getProperty("db.clean", "false").equals("true")) {
			deleteDB();
			createDB();
			System.out.println("Base de datos limpiada correctamente.");
		}
	}
	
}
