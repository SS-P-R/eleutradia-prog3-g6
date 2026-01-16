package es.deusto.eleutradia.service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import es.deusto.eleutradia.db.EleutradiaDBManager;
import es.deusto.eleutradia.domain.ProductoFinanciero;

public class MarketDataService implements Runnable {

    private static MarketDataService instance;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private Thread workerThread;
    private MarketDataService() {
    }

    public static synchronized MarketDataService getInstance() {
        if (instance == null) {
            instance = new MarketDataService();
        }
        return instance;
    }
    public void startService() {
        if (running.get()) {
            return;
        }
        
        running.set(true);
        workerThread = new Thread(this, "Hilo-MarketData");
        workerThread.start();
        System.out.println(">>> Motor de Datos de Mercado: INICIADO 🚀");
    }

    public void stopService() {
        running.set(false);
        if (workerThread != null) {
            workerThread.interrupt();
        }
        System.out.println(">>> Motor de Datos de Mercado: DETENIENDO...");
    }

    @Override
    public void run() {
        EleutradiaDBManager db = new EleutradiaDBManager();
        Random random = new Random();
        
        while (running.get()) {
            try {
                List<ProductoFinanciero> productos = db.obtenerTodosLosProductos();
                for (ProductoFinanciero p : productos) {
                    if (p.getTicker() == null || p.getTicker().equals("UNKNOWN") || p.getTipoProducto() == null) {
                        continue;
                    }

                    double precioActual = p.getValorUnitario();
                    double volatilidad = 0.0;
                    switch (p.getTipoProducto()) {
                        // MUY ALTO RIESGO 
                        case CRP: // Cripto
                            volatilidad = 0.08;
                            break;
                        
                        // ALTO RIESGO 
                        case ACC: // Acciones
                        case PEQ: // Private Equity
                        case CFI: // Crowdfunding
                            volatilidad = 0.025;
                            break;
                            
                        // RIESGO MEDIO
                        case ETF_RV: // ETF Renta Variable
                        case FDI:    // Fondos de Inversión
                        case PP_RV:  // Planes Pensiones RV
                            volatilidad = 0.015;
                            break;

                        // RIESGO BAJO
                        case BND:    // Bonos
                        case ETF_RF: // ETF Renta Fija
                        case PP_RF:  // Planes Pensiones RF
                            volatilidad = 0.005;
                            break;

                        // RIESGO CASI NULO (Muy estables)
                        case DEP: // Depósitos
                        case LDT: // Letras del Tesoro
                            volatilidad = 0.0001;
                            break;
                            
                        default:
                            volatilidad = 0.01;
                    }
                    double factorCambio = (random.nextDouble() - 0.5) * 2 * volatilidad;
                    double nuevoPrecio = precioActual * (1 + factorCambio);
                    if (nuevoPrecio < 0.01) nuevoPrecio = 0.01;
                    if (Math.abs(nuevoPrecio - precioActual) > 0.00001) {
                         db.actualizarPrecioProducto(p.getId(), nuevoPrecio);
                    }
                }

                Thread.sleep(5000); 

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("[MarketDataService] Hilo interrumpido. Cerrando.");
            } catch (Exception e) {
                System.err.println("[MarketDataService] Error crítico: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}