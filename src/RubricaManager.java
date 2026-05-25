import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 * Gestisce la lista di persone e la persistenza su DATABASE MySQL.
 *
 * Mantiene una cache in memoria (Vector) che rispecchia il contenuto
 * della tabella "persone": viene popolata al primo avvio con una SELECT
 * e aggiornata in sincrono dopo ogni INSERT / UPDATE / DELETE.
 *
 * In questo modo l'interfaccia grafica (MainFrame) non deve cambiare:
 * continua ad usare gli stessi metodi pubblici della vecchia versione.
 */
public class RubricaManager {

    private Vector<Persona> persone;

    public RubricaManager() {
        persone = new Vector<>();
        caricaDaDatabase();
    }

    // CRUD
    public void aggiungiPersona(Persona p) {
        String sql = "INSERT INTO persone (nome, cognome, indirizzo, telefono, eta) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getNome());
            ps.setString(2, p.getCognome());
            ps.setString(3, p.getIndirizzo());
            ps.setString(4, p.getTelefono());
            ps.setInt   (5, p.getEta());

            ps.executeUpdate();

            // Recupera l'id generato dal database e lo associa all'oggetto
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setId(rs.getInt(1));
                }
            }

            // Aggiorna anche la cache in memoria
            persone.add(p);

        } catch (SQLException e) {
            System.err.println("Errore INSERT persona: " + e.getMessage());
        }
    }

    public void modificaPersona(int indice, Persona nuoviDati) {
        Persona p = persone.get(indice);

        String sql = "UPDATE persone "
                   + "SET nome = ?, cognome = ?, indirizzo = ?, telefono = ?, eta = ? "
                   + "WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuoviDati.getNome());
            ps.setString(2, nuoviDati.getCognome());
            ps.setString(3, nuoviDati.getIndirizzo());
            ps.setString(4, nuoviDati.getTelefono());
            ps.setInt   (5, nuoviDati.getEta());
            ps.setInt   (6, p.getId());

            ps.executeUpdate();

            // Aggiorna la cache in memoria
            p.setNome(nuoviDati.getNome());
            p.setCognome(nuoviDati.getCognome());
            p.setIndirizzo(nuoviDati.getIndirizzo());
            p.setTelefono(nuoviDati.getTelefono());
            p.setEta(nuoviDati.getEta());

        } catch (SQLException e) {
            System.err.println("Errore UPDATE persona: " + e.getMessage());
        }
    }

    public void eliminaPersona(int indice) {
        Persona p = persone.get(indice);
        String sql = "DELETE FROM persone WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, p.getId());
            ps.executeUpdate();

            persone.remove(indice);

        } catch (SQLException e) {
            System.err.println("Errore DELETE persona: " + e.getMessage());
        }
    }

    public Persona getPersona(int indice) {
        return persone.get(indice);
    }

    public Vector<Persona> getPersone() {
        return persone;
    }

    public int getNumeroPersone() {
        return persone.size();
    }

    // PERSISTENZA
    /**
     * Carica tutte le persone dalla tabella "persone" e popola la cache.
     * Eventuali errori vengono solo loggati: l'applicazione parte comunque.
     */
    private void caricaDaDatabase() {
        persone.clear();

        String sql = "SELECT id, nome, cognome, indirizzo, telefono, eta "
                   + "FROM persone ORDER BY id";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Persona p = new Persona(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("indirizzo"),
                        rs.getString("telefono"),
                        rs.getInt   ("eta")
                );
                p.setId(rs.getInt("id"));
                persone.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Errore caricamento persone dal database: " + e.getMessage());
        }
    }
}