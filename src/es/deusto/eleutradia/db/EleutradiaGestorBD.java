package es.deusto.eleutradia.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class EleutradiaGestorBD {
	
	protected static final String DRIVER_NAME = "org.sqlite.JDBC";
	protected static final String DATABASE_FILE = "resources/db/eleutradia.db";
	protected static final String CONNECTION_STRING = "jdbc:sqlite:" + DATABASE_FILE;
	
	public EleutradiaGestorBD() {
		try {
			Class.forName(DRIVER_NAME);
		} catch (ClassNotFoundException ex) {
			System.err.println(ex);
			ex.printStackTrace();
		}
	}
	
	public void crearBBDD() {
		try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
			 Statement stmt = conn.createStatement()) {
			
	        // Tabla: Particular
			stmt.execute("""
					CREATE TABLE IF NOT EXISTS Particular (
		                dni TEXT PRIMARY KEY,
		                nombre TEXT NOT NULL,
		                email TEXT NOT NULL UNIQUE,
		                password TEXT NOT NULL,
		                telefono TEXT NOT NULL,
		                direccion TEXT NOT NULL,
		                fechaNacimiento TEXT NOT NULL,
		                paisResidencia INTEGER NOT NULL,
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
		                email TEXT NOT NULL,
		                password TEXT NOT NULL,
		                telefono TEXT NOT NULL,
		                direccion TEXT NOT NULL,
		                domicilioFiscal INTEGER,
		                perfilFinanciero INTEGER,
		                FOREIGN KEY (domicilioFiscal) REFERENCES Pais(id),
		                FOREIGN KEY (perfilFinanciero) REFERENCES PerfilFinanciero(id)
	                );
			""");
			
			// Tabla: País
			stmt.execute("""
					CREATE TABLE IF NOT EXISTS Pais (
		                id INTEGER PRIMARY KEY AUTOINCREMENT,
						nombre TEXT NOT NULL,
						regionGeografica TEXT NOT NULL
					);
			""");
			
			// Tabla: Región Geográfica
			stmt.execute("""
					CREATE TABLE IF NOT EXISTS RegionGeografica (
		                id INTEGER PRIMARY KEY AUTOINCREMENT,
						nombre TEXT NOT NULL
					);
			""");
			
			// Tabla: Producto Financiero
			stmt.execute("""
					CREATE TABLE ProductoFinanciero (
						id INTEGER PRIMARY KEY,
						nombre TEXT NOT NULL,
						tipo TEXT NOT NULL, -- Aquí se guarda el enum
						riesgo INTEGER,
						importeMin REAL
					);
			""");
			
		} catch (Exception ex) {
			System.err.println("No se pudo crear la BD");
			ex.printStackTrace();
		}
	}
	
}
