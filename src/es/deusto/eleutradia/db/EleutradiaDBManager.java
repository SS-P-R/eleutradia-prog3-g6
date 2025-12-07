package es.deusto.eleutradia.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import es.deusto.eleutradia.domain.ClaseActivo;
import es.deusto.eleutradia.domain.Divisa;
import es.deusto.eleutradia.domain.Gestora;
import es.deusto.eleutradia.domain.NivelConocimiento;
import es.deusto.eleutradia.domain.Pais;
import es.deusto.eleutradia.domain.PerfilRiesgo;
import es.deusto.eleutradia.domain.PeriodicidadPago;
import es.deusto.eleutradia.domain.PlazoRentabilidad;
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
	
	public void initializeDB() {
		this.createDB();
		
		if (properties.getProperty("db.loadCSV", "false").equals("true")) {
			if (properties.getProperty("db.clean", "false").equals("true")) {
	            this.cleanDB();
	        }
			
			insertEnumData();
			
			List<Pais> paises = this.loadCSV(CSV_PAISES, Pais::parseCSV);
			this.insertPaises(paises.toArray(new Pais[0]));
			
			List<String[]> gestorasData = this.loadCSV(CSV_GESTORAS, Gestora::parseCSV);
			this.insertGestoras(gestorasData);
			
			List<String[]> productosData = this.loadCSV(CSV_PRODUCTOS, ProductoFinanciero::parseCSV);
	        this.insertProductos(productosData);
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
							nombre TEXT NOT NULL,
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
	
	// INSERCIÓN DE DATOS EN TABLAS DE ENUMS
	
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
	
	// INSERCIÓN DE DATOS EN TABLAS DE CLASES
	
	private void insertPaises(Pais... paises) {
		String sql = "INSERT OR IGNORE INTO Pais (id, nombre, regionGeografica) VALUES (?, ?, ?);";
		
		try (Connection conn = DriverManager.getConnection(connectionUrl);
			 PreparedStatement pStmt = conn.prepareStatement(sql)) {
			
			for (Pais p : paises) {
				pStmt.setInt(1, p.getId());
				pStmt.setString(2, p.getNombre());
				pStmt.setInt(3, p.getRegion().ordinal());
				pStmt.executeUpdate();
			}
			
		} catch (Exception ex) {
			System.err.format("Error insertando países de la lista: %s%n", ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	private void insertGestoras(List<String[]> gestorasData) {
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
	
	private void insertProductos(List<String[]> productosData) {
	    String sql1 = "INSERT OR IGNORE INTO ProductoFinanciero (nombre, plazo, valorUnitario, tipoProducto, regionGeografica, perPago, divisa, gestora) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
	    String sql2 = "INSERT OR IGNORE INTO Rentabilidad (productoFinanciero, plazoRentabilidad, porcentaje) VALUES (?, ?, ?);";
	    
	    try (Connection conn = DriverManager.getConnection(connectionUrl);
	         PreparedStatement pStmt1 = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
	    	 PreparedStatement pStmt2 = conn.prepareStatement(sql2)) {
	        
	        for (String[] data : productosData) {
	            if (data == null) continue;
	            
	            String nombre = data[0];
	            String plazoStr = data[1];
	            String rentabilidadesStr = data[2];
	            double valorUnitario = Double.parseDouble(data[3]);
	            String tipoProductoStr = data[4];
	            String regionGeograficaStr = data[5];
	            String periodicidadPagoStr = data[6];
	            String divisaStr = data[7];
	            String gestoraNombre = data[8];
	            
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
	            pStmt1.setString(2, plazo);
	            pStmt1.setDouble(3, valorUnitario);
	            pStmt1.setInt(4, tipoProductoId);
	            pStmt1.setInt(5, regionGeograficaId);
	            pStmt1.setInt(6, periodicidadPagoId);
	            pStmt1.setInt(7, divisaId);
	            pStmt1.setInt(8, gestoraId);
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
	        }
	        
	    } catch (Exception ex) {
	        System.err.format("Error insertando productos financieros: %s%n", ex.getMessage());
	        ex.printStackTrace();
	    }
	}
	
	// MÉTODOS AUXILIARES PARA INSERCIÓN
	
	private int getPaisIdByNombre(Connection conn, String nombrePais) {
	    String sql = "SELECT id FROM Pais WHERE nombre = ?;";
	    
	    try (PreparedStatement pStmt = conn.prepareStatement(sql)) {
	        pStmt.setString(1, nombrePais);
	        ResultSet rs = pStmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt("id");
	        }
	        
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
	        
	    } catch (Exception ex) {
	        System.err.format("Error buscando gestora '%s': %s%n", nombreComercial, ex.getMessage());
	    }
	    
	    return -1;
	}
	
	//IAG (Claude)
	//SIN MODIFICAR
	@FunctionalInterface
    private interface CSVParser<T> {
        T parse(String line);
    }
	//END IAG
	
	// CARGA DE DATOS DESDE CSV
	
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
