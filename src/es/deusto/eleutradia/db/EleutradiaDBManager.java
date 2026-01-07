package es.deusto.eleutradia.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import es.deusto.eleutradia.domain.Cartera;
import es.deusto.eleutradia.domain.ClaseActivo;
import es.deusto.eleutradia.domain.Curso;
import es.deusto.eleutradia.domain.Divisa;
import es.deusto.eleutradia.domain.Empresa;
import es.deusto.eleutradia.domain.Gestora;
import es.deusto.eleutradia.domain.Leccion;
import es.deusto.eleutradia.domain.Modulo;
import es.deusto.eleutradia.domain.NivelConocimiento;
import es.deusto.eleutradia.domain.Operacion;
import es.deusto.eleutradia.domain.Pais;
import es.deusto.eleutradia.domain.Particular;
import es.deusto.eleutradia.domain.PerfilFinanciero;
import es.deusto.eleutradia.domain.PerfilRiesgo;
import es.deusto.eleutradia.domain.PeriodicidadPago;
import es.deusto.eleutradia.domain.PlazoRentabilidad;
import es.deusto.eleutradia.domain.Posicion;
import es.deusto.eleutradia.domain.ProductoFinanciero;
import es.deusto.eleutradia.domain.RegionGeografica;
import es.deusto.eleutradia.domain.TipoProducto;

public class EleutradiaDBManager {
	
	private final String PROPERTIES_FILE = "resources/config/app.properties";
	
	private static final String CSV_PAISES = "resources/data/paises.csv";
	private static final String CSV_GESTORAS = "resources/data/gestoras.csv";
	private static final String CSV_PRODUCTOS = "resources/data/productos.csv";
	private static final String CSV_CURSOS = "resources/data/cursos.csv";
	private static final String CSV_MODULOS = "resources/data/modulos.csv";
	private static final String CSV_LECCIONES = "resources/data/lecciones.csv";
	
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
	
	// MÉTODOS PRINCIPALES DE LA BASE DE DATOS
	
	public void initializeDB() {
	    if (properties.getProperty("db.clean", "false").equals("true") 
	            && properties.getProperty("db.loadCSV", "false").equals("true")) {
	        System.out.println("Limpiando base de datos...");
	        File db = new File(dbPath).getAbsoluteFile();
	        if (db.exists()) {
	            if (db.delete()) {
	                System.out.println("Base de datos eliminada correctamente.");
	            } else {
	                System.err.println("No se pudo eliminar la base de datos.");
	            }
	        }
	    }
	    
	    this.createDB();
	    
	    if (properties.getProperty("db.loadCSV", "false").equals("true") && isEmptyDB()) {
	        insertEnumData();
	        
	        List<Pais> paises = this.loadCSV(CSV_PAISES, Pais::parseCSV);
	        this.insertPaises(paises.toArray(new Pais[0]));
	        
	        List<String[]> gestorasData = this.loadCSV(CSV_GESTORAS, Gestora::parseCSV);
	        this.insertGestoras(gestorasData);
	        
	        List<String[]> productosData = this.loadCSV(CSV_PRODUCTOS, ProductoFinanciero::parseCSV);
	        this.insertProductos(productosData);
	        
	        List<Curso> cursos = this.loadCSV(CSV_CURSOS, Curso::parseCSV);
	        this.insertCursos(cursos.toArray(new Curso[0]));
	        
	        List<String[]> modulosData = this.loadCSV(CSV_MODULOS, Modulo::parseCSV);
	        this.insertModulos(modulosData);
	        
	        List<String[]> leccionesData = this.loadCSV(CSV_LECCIONES, Leccion::parseCSV);
	        this.insertLecciones(leccionesData);
	        
	        System.out.println("Datos cargados desde CSV correctamente.");
	    } else if (!isEmptyDB()) {
	        System.out.println("La base de datos ya contiene datos. No se cargan los CSV.");
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
							codigo TEXT NOT NULL UNIQUE,
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
						    paisSede INTEGER NOT NULL,
	
						    FOREIGN KEY (paisSede) REFERENCES Pais(id)
						);
				""");
				
				// Tabla: Producto Financiero
				stmt.execute("""
						CREATE TABLE IF NOT EXISTS ProductoFinanciero (
							id INTEGER PRIMARY KEY AUTOINCREMENT,
							ticker TEXT NOT NULL UNIQUE,
							nombre TEXT NOT NULL UNIQUE,
							plazo TEXT,
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
						    precioUnitario REAL NOT NULL,
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
						    divisaReferencia INTEGER NOT NULL,
						    cartera INTEGER NOT NULL,
						    
						    FOREIGN KEY (prodFinanciero) REFERENCES ProductoFinanciero(id),
						    FOREIGN KEY (cartera) REFERENCES Cartera(id)
						);
				""");
				
				// Tabla: Curso
				stmt.execute("""
						CREATE TABLE IF NOT EXISTS Curso (
						    id INTEGER PRIMARY KEY AUTOINCREMENT,
						    nombre TEXT NOT NULL UNIQUE,
						    nivelRecomendado INTEGER NOT NULL,
	
						    FOREIGN KEY (nivelRecomendado) REFERENCES NivelConocimiento(id)
						);
				""");
				
				// Tabla: Módulo
				stmt.execute("""
						CREATE TABLE IF NOT EXISTS Modulo (
						    id INTEGER PRIMARY KEY AUTOINCREMENT,
						    nombre TEXT NOT NULL UNIQUE,
						    posicion INTEGER NOT NULL,
						    curso INTEGER NOT NULL,
	
						    FOREIGN KEY (curso) REFERENCES Curso(id)
						);
				""");
				
				// Tabla: Lección
				stmt.execute("""
						CREATE TABLE IF NOT EXISTS Leccion (
						    id INTEGER PRIMARY KEY AUTOINCREMENT,
						    titulo TEXT NOT NULL UNIQUE,
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
				
				// Tabla: Rentabilidad (Mapa ProductoFinanciero-PlazoRentabilidad)
				stmt.execute("""
				        CREATE TABLE IF NOT EXISTS Rentabilidad (
				            id INTEGER PRIMARY KEY AUTOINCREMENT,
				            productoFinanciero INTEGER NOT NULL,
				            plazoRentabilidad INTEGER NOT NULL,
				            porcentaje REAL NOT NULL,
				            
				            FOREIGN KEY (productoFinanciero) REFERENCES ProductoFinanciero(id),
				            FOREIGN KEY (plazoRentabilidad) REFERENCES PlazoRentabilidad(id),
				            UNIQUE(productoFinanciero, plazoRentabilidad)
				        );
				""");
				
			} catch (Exception ex) {
				System.err.format("Error al crear las tablas: %s%n", ex.getMessage());
				ex.printStackTrace();
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
	
	// MÉTODO PARA COMPROBAR SI LA BD ESTÁ VACÍA
	
	private boolean isEmptyDB() {
	    String sql = "SELECT COUNT(*) FROM ProductoFinanciero";
	    try (Connection conn = DriverManager.getConnection(connectionUrl);
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {
	        if (rs.next()) {
	            return rs.getInt(1) == 0;
	        }
	    } catch (Exception ex) {
	        // Si la tabla no existe, se cuenta como vacía
	        return true;
	    }
	    return true;
	}
	
	// MÉTODOS DE INSERCIÓN DE DATOS EN TABLAS DE ENUMERACIONES
	
	public void insertEnumData() {
		try (Connection conn = DriverManager.getConnection(connectionUrl)) {
			insertClaseActivo(conn);
			insertTipoProducto(conn);
			insertRegionGeografica(conn);
			insertNivelConocimiento(conn);
			insertPerfilRiesgo(conn);
			insertPlazoRentabilidad(conn);
			insertPeriodicidadPago(conn);
			insertDivisa(conn);
						
		} catch (Exception ex) {
			System.err.format("Error al insertar datos de enumeraciones: %s%n", ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void insertClaseActivo(Connection conn) throws Exception {
		String sql = "INSERT OR IGNORE INTO ClaseActivo (id, nombre) VALUES (?, ?);";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			for (ClaseActivo ca : ClaseActivo.values()) {
				pstmt.setInt(1, ca.ordinal());
				pstmt.setString(2, ca.name());
				pstmt.executeUpdate();
			}
		}
	}
	
	public void insertTipoProducto(Connection conn) throws Exception {
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
	
	public void insertRegionGeografica(Connection conn) throws Exception {
		String sql = "INSERT OR IGNORE INTO RegionGeografica (id, nombre) VALUES (?, ?);";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			for (RegionGeografica rg : RegionGeografica.values()) {
				pstmt.setInt(1, rg.ordinal());
				pstmt.setString(2, rg.getNombre());
				pstmt.executeUpdate();
			}
		}
	}
	
	public void insertNivelConocimiento(Connection conn) throws Exception {
		String sql = "INSERT OR IGNORE INTO NivelConocimiento (id, nombre) VALUES (?, ?);";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			for (NivelConocimiento nc : NivelConocimiento.values()) {
				pstmt.setInt(1, nc.ordinal());
				pstmt.setString(2, nc.name());
				pstmt.executeUpdate();
			}
		}
	}
	
	public void insertPerfilRiesgo(Connection conn) throws Exception {
		String sql = "INSERT OR IGNORE INTO PerfilRiesgo (id, nombre) VALUES (?, ?);";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			for (PerfilRiesgo pr : PerfilRiesgo.values()) {
				pstmt.setInt(1, pr.ordinal());
				pstmt.setString(2, pr.name());
				pstmt.executeUpdate();
			}
		}
	}
	
	public void insertPlazoRentabilidad(Connection conn) throws Exception {
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
	
	public void insertPeriodicidadPago(Connection conn) throws Exception {
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
	
	public void insertDivisa(Connection conn) throws Exception {
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
	
	// MÉTODOS DE INSERCIÓN DE DATOS EN TABLAS DE ENTIDADES
	
	public void insertPaises(Pais... paises) {
		String sql = "INSERT OR IGNORE INTO Pais (nombre, regionGeografica) VALUES (?, ?);";
		
		try (Connection conn = DriverManager.getConnection(connectionUrl);
			 PreparedStatement pStmt = conn.prepareStatement(sql)) {
			
			for (Pais p : paises) {
				pStmt.setString(1, p.getNombre());
				pStmt.setInt(2, p.getRegion().ordinal());
				pStmt.executeUpdate();
			}
			
		} catch (Exception ex) {
			System.err.format("Error insertando países de la lista: %s%n", ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void insertGestoras(List<String[]> gestorasData) {
	    String sql = "INSERT OR IGNORE INTO Gestora (nombreComercial, nombreCompleto, direccion, paisSede) VALUES (?, ?, ?, ?);";
	    
	    try (Connection conn = DriverManager.getConnection(connectionUrl);
	         PreparedStatement pStmt = conn.prepareStatement(sql)) {
	        
	        for (String[] data : gestorasData) {
	        	if (data == null) continue;
	        	
	        	String nombreComercial = data[0];
	            String nombreCompleto = data[1];
	            String direccion = data[2];
	            String nombrePaisSede = data[3];
	        	
	        	 int paisId = getPaisIdByNombre(conn, nombrePaisSede);
	             
	             if (paisId == -1) {
	                 System.err.println("País no encontrado para gestora: " + nombreComercial);
	                 continue;
	             }
	            
	            pStmt.setString(1, nombreComercial);
	            pStmt.setString(2, nombreCompleto);
	            pStmt.setString(3, direccion);
	            pStmt.setInt(4, paisId);
	            pStmt.executeUpdate();
	        }
	        
	    } catch (Exception ex) {
	        System.err.format("Error insertando gestoras: %s%n", ex.getMessage());
	        ex.printStackTrace();
	    }
	}
	
	public void insertProductos(List<String[]> productosData) {
	    String sql1 = "INSERT OR IGNORE INTO ProductoFinanciero (nombre, ticker, plazo, valorUnitario, tipoProducto, regionGeografica, perPago, divisa, gestora) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
	    String sql2 = "INSERT OR IGNORE INTO Rentabilidad (productoFinanciero, plazoRentabilidad, porcentaje) VALUES (?, ?, ?);";
	    
	    try (Connection conn = DriverManager.getConnection(connectionUrl);
	         PreparedStatement pStmt1 = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
	    	 PreparedStatement pStmt2 = conn.prepareStatement(sql2)) {
	        
	        for (String[] data : productosData) {
	            if (data == null) continue;
	            
	            String nombre = data[0];
	            String ticker = data[1];
	            String plazoStr = data[2];
	            String rentabilidadesStr = data[3];
	            double valorUnitario = Double.parseDouble(data[4]);
	            String tipoProductoStr = data[5];
	            String regionGeograficaStr = data[6];
	            String periodicidadPagoStr = data[7];
	            String divisaStr = data[8];
	            String gestoraNombre = data[9];
	            
	            String plazo = (plazoStr.isEmpty()) ? null : plazoStr;
	            
	            int tipoProductoId = TipoProducto.valueOf(tipoProductoStr).ordinal();
	            int regionGeograficaId = RegionGeografica.valueOf(regionGeograficaStr).ordinal();
	            int periodicidadPagoId = PeriodicidadPago.valueOf(periodicidadPagoStr).ordinal();
	            int divisaId = Divisa.valueOf(divisaStr).ordinal();
	            
	            int gestoraId = getGestoraIdByNombre(conn, gestoraNombre);
	            
	            if (gestoraId == -1) {
	                System.err.println("Gestora no encontrada para producto: " + nombre);
	                continue;
	            }
	            
	            pStmt1.setString(1, nombre);
	            pStmt1.setString(2, ticker);
	            pStmt1.setString(3, plazo);
	            pStmt1.setDouble(4, valorUnitario);
	            pStmt1.setInt(5, tipoProductoId);
	            pStmt1.setInt(6, regionGeograficaId);
	            pStmt1.setInt(7, periodicidadPagoId);
	            pStmt1.setInt(8, divisaId);
	            pStmt1.setInt(9, gestoraId);
	            pStmt1.executeUpdate();
	            
	            ResultSet rs = pStmt1.getGeneratedKeys();
	            if (rs.next()) {
	                int productoId = rs.getInt(1);
	                
	                if (!rentabilidadesStr.isEmpty()) {
	                    String[] rentabilidades = rentabilidadesStr.split(",");
	                    PlazoRentabilidad[] plazos = PlazoRentabilidad.values();
	                    
	                    // YTD, 1año, 3años, 5años, MAX
	                    for (int i = 0; i < rentabilidades.length; i++) {
	                        double porcentaje = Double.parseDouble(rentabilidades[i]);
	                        
	                        pStmt2.setInt(1, productoId);
	                        pStmt2.setInt(2, plazos[i].ordinal());
	                        pStmt2.setDouble(3, porcentaje);
	                        pStmt2.executeUpdate();
	                    }
	                }
	            }
	            rs.close();
	        }
	        
	    } catch (Exception ex) {
	        System.err.format("Error insertando productos financieros: %s%n", ex.getMessage());
	        ex.printStackTrace();
	    }
	}
	
	public void insertCursos(Curso... cursos) {
		String sql = "INSERT OR IGNORE INTO Curso (nombre, nivelRecomendado) VALUES (?, ?);";
		
		try (Connection conn = DriverManager.getConnection(connectionUrl);
			 PreparedStatement pStmt = conn.prepareStatement(sql)) {
			
			for (Curso c : cursos) {
				pStmt.setString(1, c.getNombre());
				pStmt.setInt(2, c.getNivelRecomendado().ordinal());
				pStmt.executeUpdate();
			}
			
		} catch (Exception ex) {
			System.err.format("Error insertando cursos de la lista: %s%n", ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void insertModulos(List<String[]> modulosData) {
	    String sql = "INSERT OR IGNORE INTO Modulo (nombre, posicion, curso) VALUES (?, ?, ?);";
	    
	    try (Connection conn = DriverManager.getConnection(connectionUrl);
	         PreparedStatement pStmt = conn.prepareStatement(sql)) {
	        
	        for (String[] data : modulosData) {
	            if (data == null) continue;
	            
	            String nombre = data[0];
	            String nombreCurso = data[1];
	            int posicion = Integer.parseInt(data[2]);
	            
	            int cursoId = getCursoIdByNombre(conn, nombreCurso);
	            
	            if (cursoId == -1) {
	                System.err.println("Curso no encontrado para módulo: " + nombre);
	                continue;
	            }
	            
	            pStmt.setString(1, nombre);
	            pStmt.setInt(2, posicion);
	            pStmt.setInt(3, cursoId);
	            pStmt.executeUpdate();
	        }
	        
	    } catch (Exception ex) {
	        System.err.format("Error insertando módulos: %s%n", ex.getMessage());
	        ex.printStackTrace();
	    }
	}
	
	public void insertLecciones(List<String[]> leccionesData) {
	    String sql = "INSERT OR IGNORE INTO Leccion (titulo, posicion, modulo) VALUES (?, ?, ?);";
	    
	    try (Connection conn = DriverManager.getConnection(connectionUrl);
	         PreparedStatement pStmt = conn.prepareStatement(sql)) {
	        
	        for (String[] data : leccionesData) {
	            if (data == null) continue;
	            
	            String titulo = data[0];
	            String nombreModulo = data[1];
	            int posicion = Integer.parseInt(data[2]);
	            
	            int moduloId = getModuloIdByNombre(conn, nombreModulo);
	            
	            if (moduloId == -1) {
	                System.err.println("Módulo no encontrado para lección: " + titulo);
	                continue;
	            }
	            
	            pStmt.setString(1, titulo);
	            pStmt.setInt(2, posicion);
	            pStmt.setInt(3, moduloId);
	            pStmt.executeUpdate();
	        }
	        
	    } catch (Exception ex) {
	        System.err.format("Error insertando lecciones: %s%n", ex.getMessage());
	        ex.printStackTrace();
	    }
	}
	
	public boolean insertUsuario(String id, String nombre, String email, String telefono, 
            String password, boolean esParticular, String direccion, 
            String fechaNacimiento, String paisResidencia) {
		if (esParticular) {
			String sql = "INSERT INTO Particular (dni, nombre, email, password, telefono, direccion, fechaNacimiento, paisResidencia) " + 
						 "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

			try (Connection conn = DriverManager.getConnection(connectionUrl);
				 PreparedStatement pStmt = conn.prepareStatement(sql)) {
				pStmt.setString(1, id);
				pStmt.setString(2, nombre);
				pStmt.setString(3, email);
				pStmt.setString(4, password);
				pStmt.setString(5, telefono);
				pStmt.setString(6, direccion);
				if (fechaNacimiento != null && !fechaNacimiento.isEmpty()) {
	                try {
	                    String[] partes = fechaNacimiento.split("/");
	                    LocalDate fecha = LocalDate.of(
	                        Integer.parseInt(partes[2]), // año
	                        Integer.parseInt(partes[1]), // mes
	                        Integer.parseInt(partes[0])  // día
	                    );
	                    pStmt.setString(7, fecha.toString());
	                } catch (Exception e) {
	                    pStmt.setString(7, LocalDate.now().toString());
	                }
	            } else {
	                pStmt.setString(7, LocalDate.now().toString());
	            }
				pStmt.setString(8, paisResidencia != null ? paisResidencia : "");
				pStmt.executeUpdate();
				return true;

			} catch (Exception ex) {
				System.err.format("Error al insertar particular: %s%n", ex.getMessage());
				ex.printStackTrace();
			}
		} else {
			String sql = "INSERT INTO Empresa (nif, nombre, email, password, telefono, direccion) " +
						 "VALUES (?, ?, ?, ?, ?, ?)";

			try (Connection conn = DriverManager.getConnection(connectionUrl);
				 PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setString(1, id);
				pstmt.setString(2, nombre);
				pstmt.setString(3, email);
				pstmt.setString(4, password);
				pstmt.setString(5, telefono);
				pstmt.setString(6, direccion);
				pstmt.executeUpdate();
				return true;

			} catch (Exception ex) {
				System.err.format("Error al insertar empresa: %s%n", ex.getMessage());
				ex.printStackTrace();
			}
		}

		return false;
	}
	
	public boolean insertCartera(Cartera cartera, String idUsuario, boolean esParticular) {
	    String sql = "INSERT INTO Cartera (nombre, saldo, perfilRiesgo, divisa, idParticular, idEmpresa) " +
	                 "VALUES (?, ?, ?, ?, ?, ?)";
	    
	    try (Connection conn = DriverManager.getConnection(connectionUrl);
	         PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	        
	        pstmt.setString(1, cartera.getNombre());
	        pstmt.setDouble(2, cartera.getSaldo());
	        pstmt.setInt(3, cartera.getPerfilRiesgo().ordinal());
	        pstmt.setInt(4, cartera.getDivisa().ordinal());
	        
	        if (esParticular) {
	            pstmt.setString(5, idUsuario);
	            pstmt.setNull(6, Types.VARCHAR);
	        } else {
	            pstmt.setNull(5, Types.VARCHAR);
	            pstmt.setString(6, idUsuario);
	        }
	        
	        int rows = pstmt.executeUpdate();
	        
	        if (rows > 0) {
	            ResultSet rs = pstmt.getGeneratedKeys();
	            if (rs.next()) {
	                int id = rs.getInt(1);
	                cartera.setId(id);
	                rs.close();
	                return true;
	            }
	            rs.close();
	        }
	        
	    } catch (Exception ex) {
	        System.err.format("Error al insertar cartera: %s%n", ex.getMessage());
	        ex.printStackTrace();
	    }
	    
	    return false;
	}
	
	public boolean insertOperacion(Operacion operacion, int idCartera) {
	    String sql = "INSERT INTO Operacion (prodFinanciero, cantidad, precioUnitario, fechaOp, tipoOp, cartera) " +
	                 "VALUES (?, ?, ?, ?, ?, ?)";
	    
	    try (Connection conn = DriverManager.getConnection(connectionUrl);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setInt(1, operacion.getProdFinanciero().getId());
	        pstmt.setDouble(2, operacion.getCantidad());
	        pstmt.setDouble(3, operacion.getPrecioUnitario());
	        pstmt.setString(4, operacion.getFechaOp().toString());
	        pstmt.setInt(5, operacion.getTipoOp() ? 1 : 0);
	        pstmt.setInt(6, idCartera);
	        
	        int rows = pstmt.executeUpdate();

	        if (rows > 0) {
	            // Obtener el saldo actual de la cartera desde la BD
	            String sqlSaldo = "SELECT saldo FROM Cartera WHERE id = ?";
	            try (PreparedStatement pstmtSaldo = conn.prepareStatement(sqlSaldo)) {
	                pstmtSaldo.setInt(1, idCartera);
	                ResultSet rs = pstmtSaldo.executeQuery();
	                
	                if (rs.next()) {
	                    double saldoActual = rs.getDouble("saldo");
	                    
	                    String sqlDivisa = "SELECT divisa FROM Cartera WHERE id = ?";
	                    Divisa divisaCartera = null;
	                    try (PreparedStatement pstmtDivisa = conn.prepareStatement(sqlDivisa)) {
	                        pstmtDivisa.setInt(1, idCartera);
	                        ResultSet rsDivisa = pstmtDivisa.executeQuery();
	                        if (rsDivisa.next()) {
	                            divisaCartera = Divisa.values()[rsDivisa.getInt("divisa")];
	                        }
	                        rsDivisa.close();
	                    }
	                    
	                    double costoOperacion = operacion.getImporteTotalEnDivisa(divisaCartera);
	                    
	                    // Si es compra (true), resta del saldo. Si es venta (false), suma al saldo
	                    double nuevoSaldo = operacion.getTipoOp() ? 
	                        saldoActual - costoOperacion : 
	                        saldoActual + costoOperacion;
	                    
	                    // Actualizar el saldo en la BD
	                    String sqlUpdate = "UPDATE Cartera SET saldo = ? WHERE id = ?";
	                    try (PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {
	                        pstmtUpdate.setDouble(1, nuevoSaldo);
	                        pstmtUpdate.setInt(2, idCartera);
	                        pstmtUpdate.executeUpdate();
	                    }
	                }
	                rs.close();
	            }
	            
	            return true;
	        }
	    } catch (Exception ex) {
	        System.err.format("Error al insertar operación: %s%n", ex.getMessage());
	        ex.printStackTrace();
	    }
	    
	    return false;
	}
	
	public boolean actualizarSaldoCartera(int idCartera, double nuevoSaldo) {
	    String sql = "UPDATE Cartera SET saldo = ? WHERE id = ?";
	    
	    try (Connection conn = DriverManager.getConnection(connectionUrl);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setDouble(1, nuevoSaldo);
	        pstmt.setInt(2, idCartera);
	        
	        int rows = pstmt.executeUpdate();
	        return rows > 0;
	        
	    } catch (Exception ex) {
	        System.err.format("Error al actualizar saldo de cartera: %s%n", ex.getMessage());
	        ex.printStackTrace();
	    }
	    
	    return false;
	}
	
	// MÉTODOS DE AUTENTICACIÓN Y REGISTRO
	
	public Particular buscarParticular(String dni, String password) {
	    String sql = "SELECT * FROM Particular WHERE dni = ? AND password = ?";
	    
	    try (Connection conn = DriverManager.getConnection(connectionUrl);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setString(1, dni);
	        pstmt.setString(2, password);
	        
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            Particular p = getParticularFromRS(rs, conn);
	            rs.close();
	            return p;
	        }
	        rs.close();
	        
	    } catch (Exception ex) {
	        System.err.format("Error al buscar particular: %s%n", ex.getMessage());
	        ex.printStackTrace();
	    }
	    
	    return null;
	}
	
	public Empresa buscarEmpresa(String nif, String password) {
	    String sql = "SELECT * FROM Empresa WHERE nif = ? AND password = ?";
	    
	    try (Connection conn = DriverManager.getConnection(connectionUrl);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setString(1, nif);
	        pstmt.setString(2, password);
	        
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            Empresa e = getEmpresaFromRS(rs, conn);
	            rs.close();
	            return e;
	        }
	        rs.close();
	        
	    } catch (Exception ex) {
	        System.err.format("Error al buscar empresa: %s%n", ex.getMessage());
	        ex.printStackTrace();
	    }
	    
	    return null;
	}
	
	public boolean existeUsuario(String id, boolean esParticular) {
	    String tabla = esParticular ? "Particular" : "Empresa";
	    String campo = esParticular ? "dni" : "nif";
	    String sql = "SELECT COUNT(*) FROM " + tabla + " WHERE " + campo + " = ?";
	    
	    try (Connection conn = DriverManager.getConnection(connectionUrl);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setString(1, id);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            boolean existe = rs.getInt(1) > 0;
	            rs.close();
	            return existe;
	        }
	        rs.close();
	        
	    } catch (Exception ex) {
	        System.err.format("Error al verificar usuario: %s%n", ex.getMessage());
	        ex.printStackTrace();
	    }
	    
	    return false;
	}
	
	public boolean existeEmail(String email) {
	    String sql1 = "SELECT COUNT(*) FROM Particular WHERE email = ?";
	    String sql2 = "SELECT COUNT(*) FROM Empresa WHERE email = ?";
	    
	    try (Connection conn = DriverManager.getConnection(connectionUrl)) {
	        
	        // Verificar en Particular
	        try (PreparedStatement pstmt = conn.prepareStatement(sql1)) {
	            pstmt.setString(1, email);
	            ResultSet rs = pstmt.executeQuery();
	            if (rs.next() && rs.getInt(1) > 0) {
	                rs.close();
	                return true;
	            }
	            rs.close();
	        }
	        
	        // Verificar en Empresa
	        try (PreparedStatement pstmt = conn.prepareStatement(sql2)) {
	            pstmt.setString(1, email);
	            ResultSet rs = pstmt.executeQuery();
	            if (rs.next() && rs.getInt(1) > 0) {
	                rs.close();
	                return true;
	            }
	            rs.close();
	        }
	        
	    } catch (Exception ex) {
	        System.err.format("Error al verificar email: %s%n", ex.getMessage());
	        ex.printStackTrace();
	    }
	    
	    return false;
	}
	
	public boolean existeTelefono(String telefono) {
	    String sql1 = "SELECT COUNT(*) FROM Particular WHERE telefono = ?";
	    String sql2 = "SELECT COUNT(*) FROM Empresa WHERE telefono = ?";
	    
	    try (Connection conn = DriverManager.getConnection(connectionUrl)) {
	        
	        // Verificar en Particular
	        try (PreparedStatement pstmt = conn.prepareStatement(sql1)) {
	            pstmt.setString(1, telefono);
	            ResultSet rs = pstmt.executeQuery();
	            if (rs.next() && rs.getInt(1) > 0) {
	                rs.close();
	                return true;
	            }
	            rs.close();
	        }
	        
	        // Verificar en Empresa
	        try (PreparedStatement pstmt = conn.prepareStatement(sql2)) {
	            pstmt.setString(1, telefono);
	            ResultSet rs = pstmt.executeQuery();
	            if (rs.next() && rs.getInt(1) > 0) {
	                rs.close();
	                return true;
	            }
	            rs.close();
	        }
	        
	    } catch (Exception ex) {
	        System.err.format("Error al verificar teléfono: %s%n", ex.getMessage());
	        ex.printStackTrace();
	    }
	    
	    return false;
	}
	
	// MÉTODOS DE CONSULTA
	
	public List<Particular> getParticulares() {
	    List<Particular> particulares = new ArrayList<>();
	    String sql = "SELECT * FROM Particular";
	    
	    try (Connection conn = DriverManager.getConnection(connectionUrl);
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {
	        
	        while (rs.next()) {
	            Particular p = getParticularFromRS(rs, conn);
	            if (p != null) {
	                particulares.add(p);
	            }
	        }
	        
	    } catch (Exception ex) {
	        System.err.format("Error al obtener particulares: %s%n", ex.getMessage());
	        ex.printStackTrace();
	    }
	    
	    return particulares;
	}
	
	public List<Empresa> getEmpresas() {
	    List<Empresa> empresas = new ArrayList<>();
	    String sql = "SELECT * FROM Empresa";
	    
	    try (Connection conn = DriverManager.getConnection(connectionUrl);
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {
	        
	        while (rs.next()) {
	            Empresa e = getEmpresaFromRS(rs, conn);
	            if (e != null) {
	                empresas.add(e);
	            }
	        }
	        
	    } catch (Exception ex) {
	        System.err.format("Error al obtener empresas: %s%n", ex.getMessage());
	        ex.printStackTrace();
	    }
	    
	    return empresas;
	}
	
	public List<ProductoFinanciero> getProductos() {
	    List<ProductoFinanciero> productos = new ArrayList<>();
	    String sql = "SELECT * FROM ProductoFinanciero";
	    
	    try (Connection conn = DriverManager.getConnection(connectionUrl);
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {
	        
	        while (rs.next()) {
	            ProductoFinanciero p = getProductoFromRS(rs, conn);
	            if (p != null) {
	                productos.add(p);
	            }
	        }
	        
	    } catch (Exception ex) {
	        System.err.format("Error al obtener productos: %s%n", ex.getMessage());
	        ex.printStackTrace();
	    }
	    
	    return productos;
	}
	
	public List<Curso> getCursos() {
	    List<Curso> cursos = new ArrayList<>();
	    String sql = "SELECT * FROM Curso";
	    
	    try (Connection conn = DriverManager.getConnection(connectionUrl);
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {
	        
	        while (rs.next()) {
	            int cursoId = rs.getInt("id");
	            Curso curso = new Curso(
	                cursoId,
	                rs.getString("nombre"),
	                NivelConocimiento.values()[rs.getInt("nivelRecomendado")]
	            );
	            
	            // Cargar módulos del curso
	            List<Modulo> modulos = getModulosByCursoId(cursoId, conn);
	            for (Modulo m : modulos) {
	                curso.addModulo(m);
	            }
	            
	            cursos.add(curso);
	        }
	        
	    } catch (Exception ex) {
	        System.err.format("Error al obtener cursos: %s%n", ex.getMessage());
	        ex.printStackTrace();
	    }
	    
	    return cursos;
	}
	
	// MÉTODOS DE INSCRIPCIÓN A CURSOS
	
	public boolean inscribirParticularACurso(String dni, int idCurso) {
	    // Verificar si ya está inscrito
	    String sqlCheck = "SELECT COUNT(*) FROM ParticularCurso WHERE dniParticular = ? AND idCurso = ?";
	    
	    try (Connection conn = DriverManager.getConnection(connectionUrl);
	         PreparedStatement pstmtCheck = conn.prepareStatement(sqlCheck)) {
	        
	        pstmtCheck.setString(1, dni);
	        pstmtCheck.setInt(2, idCurso);
	        ResultSet rs = pstmtCheck.executeQuery();
	        
	        if (rs.next() && rs.getInt(1) > 0) {
	            System.out.println("El usuario ya está inscrito a este curso");
	            rs.close();
	            return false; // Ya inscrito
	        }
	        rs.close();
	        
	    } catch (Exception ex) {
	        System.err.format("Error al verificar inscripción: %s%n", ex.getMessage());
	        return false;
	    }
	    
	    // Si no está inscrito, inscribir
	    String sql = "INSERT INTO ParticularCurso (dniParticular, idCurso) VALUES (?, ?)";
	    
	    try (Connection conn = DriverManager.getConnection(connectionUrl);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setString(1, dni);
	        pstmt.setInt(2, idCurso);
	        int rows = pstmt.executeUpdate();
	        
	        System.out.println("Usuario inscrito correctamente al curso");
	        return rows > 0;
	        
	    } catch (Exception ex) {
	        System.err.format("Error al inscribir particular a curso: %s%n", ex.getMessage());
	        ex.printStackTrace();
	    }
	    
	    return false;
	}

	public boolean desinscribirParticularDeCurso(String dni, int idCurso) {
	    String sql = "DELETE FROM ParticularCurso WHERE dniParticular = ? AND idCurso = ?";
	    
	    try (Connection conn = DriverManager.getConnection(connectionUrl);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setString(1, dni);
	        pstmt.setInt(2, idCurso);
	        int rows = pstmt.executeUpdate();
	        
	        return rows > 0;
	        
	    } catch (Exception ex) {
	        System.err.format("Error al desinscribir particular de curso: %s%n", ex.getMessage());
	        ex.printStackTrace();
	    }
	    
	    return false;
	}
	
	// MÉTODOS DE CONSULTA DE CURSOS Y CARTERAS POR USUARIO
	
	public List<Curso> getCursosPorParticular(String dni) {
	    List<Curso> cursos = new ArrayList<>();
	    String sql = """
	        SELECT c.* FROM Curso c
	        INNER JOIN ParticularCurso pc ON c.id = pc.idCurso
	        WHERE pc.dniParticular = ?
	    """;
	    
	    try (Connection conn = DriverManager.getConnection(connectionUrl);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setString(1, dni);
	        ResultSet rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            int cursoId = rs.getInt("id");
	            Curso curso = new Curso(
	                cursoId,
	                rs.getString("nombre"),
	                NivelConocimiento.values()[rs.getInt("nivelRecomendado")]
	            );
	            
	            // Cargar módulos del curso
	            List<Modulo> modulos = getModulosByCursoId(cursoId, conn);
	            for (Modulo m : modulos) {
	                curso.addModulo(m);
	            }
	            
	            cursos.add(curso);
	        }
	        rs.close();
	        
	    } catch (Exception ex) {
	        System.err.format("Error al obtener cursos del particular: %s%n", ex.getMessage());
	        ex.printStackTrace();
	    }
	    
	    return cursos;
	}
	
	public List<Cartera> getCarterasPorUsuario(String idUsuario, boolean esParticular) {
	    List<Cartera> carteras = new ArrayList<>();
	    String campo = esParticular ? "idParticular" : "idEmpresa";
	    String sql = "SELECT * FROM Cartera WHERE " + campo + " = ?";
	    
	    try (Connection conn = DriverManager.getConnection(connectionUrl);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setString(1, idUsuario);
	        ResultSet rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            Cartera cartera = getCarteraFromRS(rs, conn);
	            if (cartera != null) {
	                carteras.add(cartera);
	            }
	        }
	        rs.close();
	        
	    } catch (Exception ex) {
	        System.err.format("Error al obtener carteras del usuario: %s%n", ex.getMessage());
	        ex.printStackTrace();
	    }
	    
	    return carteras;
	}
	
	public boolean actualizarPosicion(Posicion posicion, int idCartera) {
	    String sqlCheck = "SELECT id FROM Posicion WHERE prodFinanciero = ? AND cartera = ?";
	    String sqlUpdate = "UPDATE Posicion SET cantidadTotal = ?, precioMedio = ?, divisaReferencia = ? WHERE id = ?";
	    String sqlInsert = "INSERT INTO Posicion (prodFinanciero, cantidadTotal, precioMedio, divisaReferencia, cartera) VALUES (?, ?, ?, ?, ?)";
	    
	    try (Connection conn = DriverManager.getConnection(connectionUrl)) {
	        
	        // Verificar si existe
	        int posicionId = -1;
	        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheck)) {
	            pstmt.setInt(1, posicion.getProducto().getId());
	            pstmt.setInt(2, idCartera);
	            ResultSet rs = pstmt.executeQuery();
	            if (rs.next()) {
	                posicionId = rs.getInt("id");
	            }
	            rs.close();
	        }
	        
	        // Actualizar o insertar
	        if (posicionId != -1) {
	            // Actualizar
	            try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {
	                pstmt.setDouble(1, posicion.getCantidadTotal());
	                pstmt.setDouble(2, posicion.getPrecioMedioCompra());
	                pstmt.setInt(3, posicion.getDivisaReferencia().ordinal());
	                pstmt.setInt(4, posicionId);
	                return pstmt.executeUpdate() > 0;
	            }
	        } else {
	            // Insertar
	            try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
	                pstmt.setInt(1, posicion.getProducto().getId());
	                pstmt.setDouble(2, posicion.getCantidadTotal());
	                pstmt.setDouble(3, posicion.getPrecioMedioCompra());
	                pstmt.setInt(4, posicion.getDivisaReferencia().ordinal());
	                pstmt.setInt(5, idCartera);
	                return pstmt.executeUpdate() > 0;
	            }
	        }
	        
	    } catch (Exception ex) {
	        System.err.format("Error al actualizar posición: %s%n", ex.getMessage());
	        ex.printStackTrace();
	    }
	    
	    return false;
	}
	
	public boolean eliminarPosicion(int idProducto, int idCartera) {
	    String sql = "DELETE FROM Posicion WHERE prodFinanciero = ? AND cartera = ?";
	    
	    try (Connection conn = DriverManager.getConnection(connectionUrl);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setInt(1, idProducto);
	        pstmt.setInt(2, idCartera);
	        int rows = pstmt.executeUpdate();
	        return rows > 0;
	        
	    } catch (Exception ex) {
	        System.err.format("Error al eliminar posición: %s%n", ex.getMessage());
	        ex.printStackTrace();
	    }
	    
	    return false;
	}
	
	// MÉTODOS PARA EDITAR EL PERFIL Y LA CONTRASEÑA
	
	public boolean editarPerfil(Object usuario) {
	    String sql;
	    String id;
	    String email, telefono, direccion;

	    // Detectamos si es Particular o Empresa para saber qué tabla tocar
	    if (usuario instanceof Particular) {
	        Particular p = (Particular) usuario;
	        sql = "UPDATE Particular SET email = ?, telefono = ?, direccion = ? WHERE dni = ?";
	        id = p.getDni();
	        email = p.getEmail();
	        telefono = p.getTelefono();
	        direccion = p.getDireccion();
	    } else if (usuario instanceof Empresa) {
	        Empresa e = (Empresa) usuario;
	        sql = "UPDATE Empresa SET email = ?, telefono = ?, direccion = ? WHERE nif = ?";
	        id = e.getNif();
	        email = e.getEmail();
	        telefono = e.getTelefono();
	        direccion = e.getDireccion();
	    } else {
	        return false; // No sabemos qué es
	    }

	    try (Connection conn = DriverManager.getConnection(connectionUrl);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setString(1, email);
	        pstmt.setString(2, telefono);
	        pstmt.setString(3, direccion);
	        pstmt.setString(4, id); // El DNI o NIF actúa como filtro WHERE
	        
	        return pstmt.executeUpdate() > 0;
	        
	    } catch (Exception ex) {
	        System.err.println("Error al actualizar perfil: " + ex.getMessage());
	        return false;
	    }
	}

	// Método específico para cambiar la contraseña
	public boolean editarContrasena(Object usuario, String nuevaPassword) {
	    String sql;
	    String id;

	    if (usuario instanceof Particular) {
	        sql = "UPDATE Particular SET password = ? WHERE dni = ?";
	        id = ((Particular) usuario).getDni();
	    } else if (usuario instanceof Empresa) {
	        sql = "UPDATE Empresa SET password = ? WHERE nif = ?";
	        id = ((Empresa) usuario).getNif();
	    } else {
	        return false;
	    }

	    try (Connection conn = DriverManager.getConnection(connectionUrl);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setString(1, nuevaPassword);
	        pstmt.setString(2, id);
	        
	        return pstmt.executeUpdate() > 0;
	        
	    } catch (Exception ex) {
	        System.err.println("Error al actualizar password: " + ex.getMessage());
	        return false;
	    }
	}
	
	// MÉTODOS PRIVADOS AUXILIARES
	
	private int getPaisIdByNombre(Connection conn, String nombrePais) {
	    String sql = "SELECT id FROM Pais WHERE nombre = ?;";
	    
	    try (PreparedStatement pStmt = conn.prepareStatement(sql)) {
	        pStmt.setString(1, nombrePais);
	        ResultSet rs = pStmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt("id");
	        }
	        rs.close();
	        
	    } catch (Exception ex) {
	        System.err.format("Error buscando país '%s': %s%n", nombrePais, ex.getMessage());
	    }
	    
	    return -1;
	}
	
	private int getGestoraIdByNombre(Connection conn, String nombreComercial) {
	    String sql = "SELECT id FROM Gestora WHERE nombreComercial = ?;";
	    
	    try (PreparedStatement pStmt = conn.prepareStatement(sql)) {
	        pStmt.setString(1, nombreComercial);
	        ResultSet rs = pStmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt("id");
	        }
	        rs.close();
	        
	    } catch (Exception ex) {
	        System.err.format("Error buscando gestora '%s': %s%n", nombreComercial, ex.getMessage());
	    }
	    
	    return -1;
	}
	
	private int getCursoIdByNombre(Connection conn, String nombreCurso) {
	    String sql = "SELECT id FROM Curso WHERE nombre = ?;";
	    
	    try (PreparedStatement pStmt = conn.prepareStatement(sql)) {
	        pStmt.setString(1, nombreCurso);
	        ResultSet rs = pStmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt("id");
	        }
	        rs.close();
	        
	    } catch (Exception ex) {
	        System.err.format("Error buscando curso '%s': %s%n", nombreCurso, ex.getMessage());
	    }
	    
	    return -1;
	}

	private int getModuloIdByNombre(Connection conn, String nombreModulo) {
	    String sql = "SELECT id FROM Modulo WHERE nombre = ?;";
	    
	    try (PreparedStatement pStmt = conn.prepareStatement(sql)) {
	        pStmt.setString(1, nombreModulo);
	        ResultSet rs = pStmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt("id");
	        }
	        rs.close();
	        
	    } catch (Exception ex) {
	        System.err.format("Error buscando módulo '%s': %s%n", nombreModulo, ex.getMessage());
	    }
	    
	    return -1;
	}
	
	private Particular getParticularFromRS(ResultSet rs, Connection conn) throws Exception {
	    String fechaNacStr = rs.getString("fechaNacimiento");
	    LocalDate fechaNac = (fechaNacStr != null && !fechaNacStr.isEmpty()) ? 
	        LocalDate.parse(fechaNacStr) : null;
	    
	    int paisResId = rs.getInt("paisResidencia");
	    int domFiscalId = rs.getInt("domicilioFiscal");
	    int perfilId = rs.getInt("perfilFinanciero");
	    
	    String dni = rs.getString("dni");
	    
	    Particular p = new Particular(
	        dni,
	        rs.getString("nombre"),
	        fechaNac,
	        (paisResId > 0) ? getPaisById(paisResId, conn) : null,
	        rs.getString("email"),
	        rs.getString("password"),
	        rs.getString("telefono"),
	        rs.getString("direccion"),
	        (domFiscalId > 0) ? getPaisById(domFiscalId, conn) : null,
	        (perfilId > 0) ? getPerfilFinancieroById(perfilId, conn) : null
	    );
	    
	    List<Curso> cursos = getCursosPorParticular(dni);
	    for (Curso curso : cursos) {
	        p.addCurso(curso);
	    }
	    
	    List<Cartera> carteras = getCarterasPorUsuario(dni, true);
	    for (Cartera cartera : carteras) {
	        p.addCartera(cartera);
	    }
	    
	    return p;
	}

	private Empresa getEmpresaFromRS(ResultSet rs, Connection conn) throws Exception {
	    int domFiscalId = rs.getInt("domicilioFiscal");
	    int perfilId = rs.getInt("perfilFinanciero");
	    
	    String nif = rs.getString("nif");
	    
	    Empresa e = new Empresa(
	        nif,
	        rs.getString("nombre"),
	        rs.getString("email"),
	        rs.getString("password"),
	        rs.getString("telefono"),
	        rs.getString("direccion"),
	        (domFiscalId > 0) ? getPaisById(domFiscalId, conn) : null,
	        (perfilId > 0) ? getPerfilFinancieroById(perfilId, conn) : null
	    );
	    
	    List<Cartera> carteras = getCarterasPorUsuario(nif, false);
	    for (Cartera cartera : carteras) {
	        e.addCartera(cartera);
	    }
	    
	    return e;
	}
	
	private ProductoFinanciero getProductoFromRS(ResultSet rs, Connection conn) throws Exception {
	    int id = rs.getInt("id");
	    String plazoStr = rs.getString("plazo");
	    YearMonth plazo = (plazoStr != null && !plazoStr.isEmpty()) ? 
	        YearMonth.parse(plazoStr) : null;
	    
	    return new ProductoFinanciero(
	        id,
	        rs.getString("nombre"),
	        rs.getString("ticker"),
	        plazo,
	        getRentabilidadesByProductoId(id, conn),
	        rs.getDouble("valorUnitario"),
	        TipoProducto.values()[rs.getInt("tipoProducto")],
	        RegionGeografica.values()[rs.getInt("regionGeografica")],
	        PeriodicidadPago.values()[rs.getInt("perPago")],
	        Divisa.values()[rs.getInt("divisa")],
	        getGestoraById(rs.getInt("gestora"), conn)
	        
	    );
	}
	
	private Cartera getCarteraFromRS(ResultSet rs, Connection conn) throws Exception {
	    int id = rs.getInt("id");
	    String nombre = rs.getString("nombre");
	    double saldo = rs.getDouble("saldo");
	    PerfilRiesgo perfilRiesgo = PerfilRiesgo.values()[rs.getInt("perfilRiesgo")];
	    Divisa divisa = Divisa.values()[rs.getInt("divisa")];
	    
	    Cartera cartera = new Cartera(nombre, saldo, perfilRiesgo, divisa);
	    cartera.setId(id); // Necesitas agregar este método a la clase Cartera
	    
	    // Cargar posiciones de la cartera
	    List<Posicion> posiciones = getPosicionesByCarteraId(id, conn);
	    for (Posicion pos : posiciones) {
	        cartera.addPosicion(pos); // Necesitas este método en Cartera
	    }
	    
	    // Cargar operaciones de la cartera
	    List<Operacion> operaciones = getOperacionesByCarteraId(id, conn);
	    for (Operacion op : operaciones) {
	        cartera.addOperacion(op); // Necesitas este método en Cartera
	    }
	    
	    return cartera;
	}
	
	private Pais getPaisById(int paisId, Connection conn) throws Exception {
	    if (paisId == 0) return null;
	    
	    String sql = "SELECT * FROM Pais WHERE id = ?";
	    
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, paisId);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            Pais pais = new Pais(
	                rs.getInt("id"),
	                rs.getString("nombre"),
	                RegionGeografica.values()[rs.getInt("regionGeografica")]
	            );
	            rs.close();
	            return pais;
	        }
	        rs.close();
	    }
	    
	    return null;
	}
	
	private Gestora getGestoraById(int gestoraId, Connection conn) throws Exception {
	    String sql = "SELECT * FROM Gestora WHERE id = ?";
	    
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, gestoraId);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            Gestora g = new Gestora(
	                rs.getInt("id"),
	                rs.getString("nombreComercial"),
	                rs.getString("nombreCompleto"),
	                rs.getString("direccion"),
	                getPaisById(rs.getInt("paisSede"), conn),
	                new ArrayList<>() // Lista de productos vacía
	            );
	            rs.close();
	            return g;
	        }
	        rs.close();
	    }
	    
	    return null;
	}
	
	private PerfilFinanciero getPerfilFinancieroById(int perfilId, Connection conn) throws Exception {
	    if (perfilId == 0) return null;
	    
	    String sql = "SELECT * FROM PerfilFinanciero WHERE id = ?";
	    
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, perfilId);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            PerfilFinanciero perfil = new PerfilFinanciero(
	                rs.getInt("horizonte"),
	                PerfilRiesgo.values()[rs.getInt("perfilRiesgo")],
	                NivelConocimiento.values()[rs.getInt("nivelConocimiento")],
	                getTiposProductoByPerfil(perfilId, conn)
	            );
	            rs.close();
	            return perfil;
	        }
	        rs.close();
	    }
	    
	    return null;
	}
	
	private List<TipoProducto> getTiposProductoByPerfil(int perfilId, Connection conn) throws Exception {
	    List<TipoProducto> tipos = new ArrayList<>();
	    String sql = "SELECT tipoProducto FROM PerfilFinancieroTipoProducto WHERE perfilFinanciero = ?";
	    
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, perfilId);
	        ResultSet rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            tipos.add(TipoProducto.values()[rs.getInt("tipoProducto")]);
	        }
	        rs.close();
	    }
	    
	    return tipos;
	}
	
	private ProductoFinanciero getProductoById(int productoId, Connection conn) throws Exception {
	    String sql = "SELECT * FROM ProductoFinanciero WHERE id = ?";
	    
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, productoId);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            ProductoFinanciero producto = getProductoFromRS(rs, conn);
	            rs.close();
	            return producto;
	        }
	        rs.close();
	    }
	    
	    return null;
	}
	
	private List<Operacion> getOperacionesByCarteraId(int carteraId, Connection conn) throws Exception {
	    List<Operacion> operaciones = new ArrayList<>();
	    String sql = "SELECT * FROM Operacion WHERE cartera = ? ORDER BY fechaOp DESC";
	    
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, carteraId);
	        ResultSet rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            int productoId = rs.getInt("prodFinanciero");
	            ProductoFinanciero producto = getProductoById(productoId, conn);
	            
	            if (producto != null) {
	                Operacion operacion = new Operacion(
	                	rs.getInt("id"),
	                    producto,
	                    rs.getDouble("cantidad"),
	                    rs.getDouble("precioUnitario"),
	                    LocalDate.parse(rs.getString("fechaOp")),
	                    rs.getInt("tipoOp") == 1
	                );	
	                operaciones.add(operacion);
	            }
	        }
	        rs.close();
	    }
	    
	    return operaciones;
	}
	
	private List<Posicion> getPosicionesByCarteraId(int carteraId, Connection conn) throws Exception {
	    List<Posicion> posiciones = new ArrayList<>();
	    String sql = "SELECT * FROM Posicion WHERE cartera = ?";
	    
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, carteraId);
	        ResultSet rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            int productoId = rs.getInt("prodFinanciero");
	            ProductoFinanciero producto = getProductoById(productoId, conn);
	            
	            if (producto != null) {
	                Posicion posicion = new Posicion(
	                	rs.getInt("id"),
	                    producto,
	                    rs.getDouble("cantidadTotal"),
	                    rs.getDouble("precioMedio"),
	                    Divisa.values()[rs.getInt("divisaReferencia")]
	                );
	                posiciones.add(posicion);
	            }
	        }
	        rs.close();
	    }
	    
	    return posiciones;
	}
	
	private Map<PlazoRentabilidad, BigDecimal> getRentabilidadesByProductoId(
	        int productoId, Connection conn) throws Exception {
	    Map<PlazoRentabilidad, BigDecimal> rentabilidades = new java.util.HashMap<>();
	    String sql = "SELECT plazoRentabilidad, porcentaje FROM Rentabilidad WHERE productoFinanciero = ?";
	    
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, productoId);
	        ResultSet rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            rentabilidades.put(
	                PlazoRentabilidad.values()[rs.getInt("plazoRentabilidad")],
	                BigDecimal.valueOf(rs.getDouble("porcentaje"))
	            );
	        }
	        rs.close();
	    }
	    
	    return rentabilidades;
	}
	
	private List<Modulo> getModulosByCursoId(int cursoId, Connection conn) throws Exception {
	    List<Modulo> modulos = new ArrayList<>();
	    String sql = "SELECT * FROM Modulo WHERE curso = ? ORDER BY posicion";
	    
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, cursoId);
	        ResultSet rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            int moduloId = rs.getInt("id");
	            Modulo modulo = new Modulo(
	                moduloId,
	                rs.getString("nombre"),
	                rs.getInt("posicion"),
	                getLeccionesByModuloId(moduloId, conn)
	            );
	            modulos.add(modulo);
	        }
	        rs.close();
	    }
	    
	    return modulos;
	}
	
	private List<Leccion> getLeccionesByModuloId(int moduloId, Connection conn) throws Exception {
	    List<Leccion> lecciones = new ArrayList<>();
	    String sql = "SELECT * FROM Leccion WHERE modulo = ? ORDER BY posicion";
	    
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, moduloId);
	        ResultSet rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            Leccion leccion = new Leccion(
	                rs.getInt("id"),
	                rs.getString("titulo"),
	                rs.getInt("posicion")
	            );
	            lecciones.add(leccion);
	        }
	        rs.close();
	    }
	    
	    return lecciones;
	}
	
	// CARGA DE DATOS DESDE CSV
	
	//IAG (Claude)
	//SIN MODIFICAR
	@FunctionalInterface
    private interface CSVParser<T> {
        T parse(String line);
    }
	//END IAG
		
	private <T> List<T> loadCSV(String rutaCSV, CSVParser<T> parser) {
		List<T> elementos = new ArrayList<>();
		
		File fileCSV = new File(rutaCSV);
        if (!fileCSV.exists()) {
            System.err.println("Archivo CSV no encontrado: " + rutaCSV);
            return elementos;
        }
		
		try (BufferedReader br = new BufferedReader(new FileReader(rutaCSV))) {
			br.readLine(); // Saltar cabecera
			String linea;
			while ((linea = br.readLine()) != null) {
				T elem = parser.parse(linea);
				if (elem != null) elementos.add(elem);
			}			
			
		} catch (Exception ex) {
			System.err.format("Error leyendo elementos del CSV: %s%n", ex.getMessage());
			ex.printStackTrace();
		}
		
		return elementos;
	}
	
}
