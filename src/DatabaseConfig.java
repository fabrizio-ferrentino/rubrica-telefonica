import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Classe di utilita' per la connessione al database MySQL.
 *
 * Legge i parametri di connessione dal file esterno
 * "credenziali_database.properties"
 *
 * Espone un solo metodo statico: {@link #getConnection()}, che apre
 * e restituisce una nuova connessione JDBC.
 */
public class DatabaseConfig {

    private static final String CONFIG_FILE = "credenziali_database.properties";

    private static Properties props;

    /**
     * Carica il file .properties al primo utilizzo
     */
    private static void caricaProperties() {
        if (props != null) return;

        props = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            props.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(
                "Impossibile leggere il file di configurazione: " + CONFIG_FILE
                + "\nVerifica che si trovi nella stessa cartella di Rubrica.jar.",
                e
            );
        }

        // Carica esplicitamente il driver JDBC di MySQL (Connector/J).
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                "Driver MySQL (com.mysql.cj.jdbc.Driver) non trovato nel classpath.",
                e
            );
        }
    }

    /**
     * Apre e restituisce una nuova connessione al database.
     * Il chiamante e' responsabile della chiusura (usare try-with-resources).
     */
    public static Connection getConnection() throws SQLException {
        caricaProperties();

        String host     = props.getProperty("db.host",     "localhost");
        String port     = props.getProperty("db.port",     "3306");
        String dbName   = props.getProperty("db.name",     "rubrica_db");
        String user     = props.getProperty("db.user",     "root");
        String password = props.getProperty("db.password", "");

        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName
                   + "?useSSL=false"
                   + "&serverTimezone=UTC"
                   + "&allowPublicKeyRetrieval=true";

        return DriverManager.getConnection(url, user, password);
    }
}
