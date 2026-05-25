import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Gestisce la verifica delle credenziali di login leggendo la tabella
 * "utenti" del database MySQL.
 *
 * Sostituisce la vecchia versione che leggeva da file "utenti.txt".
 */
public class UtenteManager {

    /**
     * Verifica se username e password corrispondono ad un utente esistente
     * nella tabella "utenti".
     *
     * @return true se le credenziali sono corrette
     * @throws SQLException se ci sono problemi di connessione o di query
     *         (il chiamante deve mostrarli all'utente)
     */
    public boolean verificaCredenziali(String username, String password) throws SQLException {
        String sql = "SELECT id FROM utenti WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // true se la query ha trovato almeno una riga
            }
        }
    }
}
