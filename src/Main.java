import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Punto di ingresso dell'applicazione Rubrica.
 */
public class Main {
    public static void main(String[] args) {
        // Usa il look and feel del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Se non funziona, usa il look and feel di default
        }
        // Avvia la GUI sul thread dedicato all'interfaccia grafica (EDT)
        // Mostra prima la finestra di login; la MainFrame si aprirà solo dopo un login corretto.
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}