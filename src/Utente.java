/**
 * Classe di dominio che rappresenta un utente dell'applicazione Rubrica.
 * Contiene le credenziali di accesso: username e password.
 */
public class Utente {

    private String username;
    private String password;

    public Utente(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    @Override
    public String toString() {
        return username;
    }
}
