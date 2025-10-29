package es.deusto.eleutradia.domain;

import java.time.LocalDate;
import java.util.ArrayList;

public class Usuario {
	private String dni;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String email;
    private String telefono;
    private String direccion;
    private Pais domicilioFiscal;
    
    // Relaciones
    private PerfilFinanciero perfilFinanciero;
    private ArrayList<CarteraVirtual> carterasVirtuales;
}